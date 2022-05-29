package com.dc.allo.common.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

/**
 * Created by zhangzhenjun on 2020/3/15.
 */
public class RedisLockExcutor {
    /**
     * 尝试抢锁执行
     * @param stringTemplate stringTemplate
     * @param lockKey 上锁的key
     * @param lockTimoutMills 锁自动过期时间.单位:毫秒.
     * @param task 执行的业务逻辑
     * @return 成功抢锁执行返回true，抢锁失败返回false
     */
    public static boolean execute(RedisTemplate<String, String> stringTemplate, String lockKey, long lockTimoutMills, Task task) throws Exception {
        String lockVal = UUID.randomUUID().toString();
        if (!RedisLock.lock(stringTemplate, lockKey, lockVal, lockTimoutMills)) {
            return false;
        } else {
            run(stringTemplate, lockKey, lockVal, task);
            return true;
        }
    }

    /**
     * 抢锁直到成功，再执行
     * @param stringTemplate stringTemplate
     * @param lockKey 上锁的key
     * @param lockTimoutMills 锁自动过期时间.单位:毫秒.
     * @param task 执行的业务逻辑
     */
    public static void executeUntilLock(RedisTemplate<String, String> stringTemplate, String lockKey, long lockTimoutMills, Task task) throws Exception {
        String lockVal = UUID.randomUUID().toString();
        RedisLock.lockUntilSuccess(stringTemplate, lockKey, lockVal, lockTimoutMills);
        run(stringTemplate, lockKey, lockVal, task);
    }

    private static void run(RedisTemplate<String, String> stringTemplate, String lockKey, String lockVal, Task task) throws Exception {
        try {
            task.run();
        } finally {
            RedisLock.unlock(stringTemplate, lockKey, lockVal);
        }
    }

    public interface Task {
        void run() throws Exception;
    }
}
