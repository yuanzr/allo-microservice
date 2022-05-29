package com.dc.allo.user.test;

import com.dc.allo.common.utils.RedisKeyUtil.RedisExpire_Time;
import org.apache.commons.lang3.RandomUtils;

/**
 * description: Test
 *
 * @date: 2020年06月04日 14:27
 * @author: qinrenchuan
 */
public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 1000;i++) {

            long l = RandomUtils.nextLong(1, RedisExpire_Time.OneDay / 10);
            System.out.println(l);
        }
    }
}
