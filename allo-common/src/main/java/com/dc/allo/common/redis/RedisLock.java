package com.dc.allo.common.redis;

import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhenjun on 2020/3/15.
 */
@Slf4j
public class RedisLock {
    private final static String UNLOCK_LUA = "" +
            "if redis.call(\"get\",KEYS[1]) == ARGV[1] " +
            "then " +
            "   return redis.call(\"del\",KEYS[1]) " +
            "else " +
            "   return 0 " +
            "end";

    //等待锁时间
    private final static long WAIT_LOCK_TIME = 300;
    /**
     * 上锁
     * @param stringTemplate stringTemplate
     * @param lockKey 上锁的key
     * @param value 上锁的val,解锁时需用到
     * @param timeout 锁自动过期时间.单位:秒.
     * @return true/false
     */
    public static boolean lock(RedisTemplate<String, String> stringTemplate, String lockKey, String value, long timeout) {

        String result = stringTemplate.execute((RedisCallback<String>) connection -> {

            Object nativeConnection = connection.getNativeConnection();

            String redisResult = "";
            @SuppressWarnings("unchecked")
            RedisSerializer<String> stringRedisSerializer = (RedisSerializer<String>) stringTemplate.getKeySerializer();
            //lettuce连接包下序列化键值，否知无法用默认的ByteArrayCodec解析
            byte[] keyByte = stringRedisSerializer.serialize(lockKey);
            byte[] valueByte = stringRedisSerializer.serialize(value);
            // lettuce连接包下 redis 单机模式setnx
            if (nativeConnection instanceof RedisAsyncCommands) {
                RedisAsyncCommands commands = (RedisAsyncCommands)nativeConnection;
                //同步方法执行、setnx禁止异步
                redisResult = commands
                        .getStatefulConnection()
                        .sync()
                        .set(keyByte, valueByte, SetArgs.Builder.nx().ex(timeout));
            }
            // lettuce连接包下 redis 集群模式setnx
            if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
                RedisAdvancedClusterAsyncCommands clusterAsyncCommands = (RedisAdvancedClusterAsyncCommands) nativeConnection;
                StatefulRedisClusterConnection sconnection = clusterAsyncCommands.getStatefulConnection();
                redisResult = sconnection
                        .sync()
                        .set(keyByte, valueByte, SetArgs.Builder.nx().ex(timeout));
            }
            //返回加锁结果
            return redisResult;
        });
        return "OK".equalsIgnoreCase(result + "");
    }


    /**
     * 上锁，直到成功为止
     * @param stringTemplate stringTemplate
     * @param lockKey 上锁的key
     * @param lockVal 上锁的val,解锁时需用到
     * @param timeout 锁自动过期时间.单位:秒.
     * @return true/false
     */
    public static String lockUntilSuccess(RedisTemplate<String, String> stringTemplate, String lockKey, String lockVal, long timeout) {
        while (true) {
            if (!lock(stringTemplate, lockKey, lockVal, timeout)) {
                try {
                    TimeUnit.MILLISECONDS.sleep(WAIT_LOCK_TIME);
                } catch (InterruptedException e) {
                    log.error("[RedisLock] sleep error.", e);
                }
            } else {
                break;
            }
        }
        return lockVal;
    }

    /**
     * 上锁，根据重等待时间等待 超时时间就返回空
     * @param stringTemplate stringTemplate
     * @param lockKey 上锁的key
     * @param lockVal 上锁的val,解锁时需用到
     * @param timeout 锁自动过期时间.单位:秒.
     * @param waitTime 等待时间 单位毫秒
     * @return lockVal
     */
    public static String addlockWait(RedisTemplate<String, String> stringTemplate, String lockKey, String lockVal, long timeout,long waitTime) {
        String newLockVal = null;
        while (waitTime > 0) {
            if (!lock(stringTemplate, lockKey, lockVal, timeout)) {
                try {
                    waitTime -= WAIT_LOCK_TIME;
                    TimeUnit.MILLISECONDS.sleep(WAIT_LOCK_TIME);
                } catch (InterruptedException e) {
                    log.error("[RedisLock] sleep error.", e);
                }
            } else {
                newLockVal = lockVal;
                break;
            }
        }
        return newLockVal;
    }

    /**
     * 上锁 成不成功都立即返回结果
     * @param stringTemplate stringTemplate
     * @param lockKey 上锁的key
     * @param lockVal 上锁的val,解锁时需用到
     * @param timeout 锁自动过期时间.单位:秒.
     * @return true/false
     */
    public static boolean addlock(RedisTemplate<String, String> stringTemplate, String lockKey, String lockVal, long timeout) {
        return lock(stringTemplate, lockKey, lockVal, timeout);
    }

    /**
     * 解锁
     * @param stringTemplate stringTemplate
     * @param lockKey 锁的key
     * @param value 必须与上锁时的val一致
     */
    public static boolean unlock(RedisTemplate<String, String> stringTemplate, String lockKey, String value) {
        return stringTemplate.execute((RedisCallback<Boolean>) connection -> {
           return  connection.eval(UNLOCK_LUA.getBytes(), ReturnType.BOOLEAN,1, lockKey.getBytes(), value.getBytes());
        });
    }
}
