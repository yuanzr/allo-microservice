package com.dc.allo.common.component.aggregation;

import com.dc.allo.common.redis.RedisLock;
import com.dc.allo.common.utils.JsonUtils;
import com.dc.allo.common.utils.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhenjun on 2020/4/2.
 */
@Slf4j
public class RedisAggregationQueue<T> extends AbstractAggregationQueue<T> {

    private RedisTemplate<String, String> stringTemplate;

    public RedisAggregationQueue(String queueName, RedisTemplate<String, String> stringRedisTemplate, Class<T> delayMessage) {
        this.stringTemplate = stringRedisTemplate;
        this.setQueueName(queueName);
        this.setClazz(delayMessage);
    }

    @Override
    protected void doEnQueue(T msg) {
        if(msg == null){
            return;
        }
        stringTemplate.opsForList().rightPush(getQueueName(), JsonUtils.toJson(msg));
    }

    @Override
    protected List<T> doDeQueue(long count) {
        List<T> list = new ArrayList<>();
        String lockKey = RedisKeyUtil.appendCacheKeyByColon(getQueueName(), "lock");
        String lockValue = UUID.randomUUID().toString();
        boolean lockSuccess = RedisLock.lock(stringTemplate, lockKey, lockValue, LOCK_EXPIRED_TIME);
        if (lockSuccess) {
            try{
                List<String> jsons = stringTemplate.opsForList().range(getQueueName(), 0, count-1);
                if(CollectionUtils.isEmpty(jsons)){
                    return list;
                }
                jsons.forEach(json->{
                    if(StringUtils.isNotBlank(json)) {
                        T msg = JsonUtils.fromJson(json, getClazz());
                        list.add(msg);
                    }
                });
                //获取实际得到的数据长度，管道操作pop出队
                int realSize = jsons.size();
                stringTemplate.execute(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
                        for(int i=0;i<realSize;i++){
                            connection.lPop(getQueueName().getBytes());
                        }
                        return null;
                    }
                });
                return list;
            }finally {
                RedisLock.unlock(stringTemplate, lockKey,lockValue);
            }
        }
        return null;
    }

    @Override
    protected long doGetQueueSize() {
        return stringTemplate.opsForList().size(getQueueName());
    }

    @Override
    public void setStartTime(long timestamp) {
        stringTemplate.opsForValue().set(RedisKeyUtil.appendCacheKeyByColon(getQueueName(),"start-time"),String.valueOf(timestamp),30, TimeUnit.DAYS);
    }

    @Override
    public long getStartTime() {
        String json = stringTemplate.opsForValue().get(RedisKeyUtil.appendCacheKeyByColon(getQueueName(),"start-time"));
        if(StringUtils.isNotBlank(json)){
            return Long.valueOf(json);
        }
        return 0;
    }
}
