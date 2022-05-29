package com.dc.allo.common.component.delay.redis;

import com.dc.allo.common.redis.RedisLock;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.RedisKeyUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Redis实现的延迟队列
 * Created by zhangzhenjun on 2020/3/16.
 */
@Slf4j
public class RedisDelayQueue<T> extends AbstractDelayQueue<T> {


    private RedisTemplate<String, String> stringTemplate;
    private final static String keyFormat = "%s:data:%s";
    private final static int TWO = 2;
    private final String LOG_PRE = "redisDelayQueue";

    public RedisDelayQueue(String queueName, RedisTemplate<String, String> stringRedisTemplate, Class<T> delayMessage) {
        this.stringTemplate = stringRedisTemplate;
        this.setQueueName(queueName);
        this.setClazz(delayMessage);
    }

    @Override
    protected void doEnQueue(DelayMessage<T> delayMessage) {
        if(delayMessage == null){
            return;
        }
        stringTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                String key = String.format(keyFormat, getQueueName(), delayMessage.getDuplicateKey());
                connection.set(key.getBytes(), JsonUtils.toJson(delayMessage).getBytes());
                connection.zAdd(getQueueName().getBytes(), delayMessage.getExpireTime(), delayMessage.getDuplicateKey().getBytes());
                return null;
            }
        });
    }

    @Override
    protected List<DelayMessage<T>> doDeQueue(int count) {
        long now = System.currentTimeMillis();
        String lockKey = RedisKeyUtil.appendCacheKeyByColon(getQueueName(), "lock");
        String lockValue = UUID.randomUUID().toString();
        boolean lockSuccess = RedisLock.lock(stringTemplate, lockKey, lockValue, LOCK_EXPIRED_TIME);
        if (lockSuccess) {
            try {
                boolean shouldRemove = false;
                String queueName = getQueueName();
                Set<ZSetOperations.TypedTuple<String>> set = stringTemplate.opsForZSet().rangeByScoreWithScores(queueName, 0, now, 0, count);
                if (null == set || set.size() < 1) {
                    return null;
                } else {
                    // 如果是但是单数则不出队
                    if (set.size() % TWO != 0 && isShouldReturnSingle()) {
                        shouldRemove = true;
                    }
                    List<DelayMessage<T>> list = new ArrayList<DelayMessage<T>>();
//                    List<byte[]> keys = Lists.newArrayList();
//                    List<byte[]> dataKeys = Lists.newArrayList();
//                    final boolean remove = shouldRemove;
//                    List<String> vals = stringTemplate.execute(new RedisCallback<List<String>>() {
//                        @Override
//                        public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
//                            int index = 0;
//                            for (ZSetOperations.TypedTuple tuple : set) {
//                                if (index == 0 && remove) {
//                                    log.info(LOG_PRE + "[dequeue] cant match， should return the first {}", tuple);
//                                } else {
//                                    String key = String.valueOf(tuple.getValue());
//                                    keys.add(key.getBytes());
//                                    String dataKey = String.format(keyFormat, getQueueName(), key);
//                                    dataKeys.add(dataKey.getBytes());
//                                }
//                                index++;
//                            }
//                            if (!CollectionUtils.isEmpty(keys)) {
//                                connection.mGet(dataKeys.toArray(new byte[dataKeys.size()][]));
//                            }
//                            return null;
//                        }
//                    });
//                    stringTemplate.execute(new RedisCallback<Object>() {
//                        @Override
//                        public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                            if (!CollectionUtils.isEmpty(keys)) {
//                                connection.del(dataKeys.toArray(new byte[dataKeys.size()][]));
//                                connection.zRem(getQueueName().getBytes(),keys.toArray(new byte[keys.size()][]));
//                            }
//                            return null;
//                        }
//                    });
//                    if(!CollectionUtils.isEmpty(vals)){
//                        vals.forEach(val->{
//                            DelayMessage<T> delayEntryMessage = new Gson().fromJson(val,
//                                    TypeToken.getParameterized(DelayMessage.class, getClazz()).getType());
//                            list.add(delayEntryMessage);
//                        });
//                    }
                    int index = 0;
                    for (ZSetOperations.TypedTuple tuple : set) {
                        if (index == 0 && shouldRemove) {
                            log.info(LOG_PRE+"[dequeue] cant match， should return the first {}", tuple);
                        } else {
                            String key = String.valueOf(tuple.getValue());
                            String dataKey = String.format(keyFormat, getQueueName(), key);
                            String value = stringTemplate.opsForValue().get(dataKey);
                            stringTemplate.delete(dataKey);
                            stringTemplate.opsForZSet().remove(getQueueName(), key);
                            log.info(LOG_PRE+"[dequeue] DataKey => {} Value:{}", dataKey, value);
                            DelayMessage<T> delayEntryMessage = new Gson().fromJson(value,
                                    TypeToken.getParameterized(DelayMessage.class, getClazz()).getType());
                            list.add(delayEntryMessage);
                        }
                        index++;
                    }
                    return list;
                }
            } finally {
                RedisLock.unlock(stringTemplate, lockKey,lockValue);
            }
        }
        return null;
    }

    @Override
    protected boolean delFromQueue(String duplicateKey) {
        boolean result = false;
        try {
            String dataKey = String.format(keyFormat,getQueueName(),duplicateKey);
            stringTemplate.delete(dataKey);
            stringTemplate.opsForZSet().remove(getQueueName(), duplicateKey);
            result = true;
        }catch (Exception e){
            String info = String.format(LOG_PRE+"delFromQueue error, queue_name:{%s}, duplicateKey:{%s} e:{}",getQueueName(),duplicateKey);
            log.error(info,e);
        }
        return result;
    }


}

