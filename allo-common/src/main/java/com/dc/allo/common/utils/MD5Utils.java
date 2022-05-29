package com.dc.allo.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by zhangzhenjun on 2020/6/22.
 */
public class MD5Utils {
    /**
     * @param text 明文
     * @param key 密钥
     * @return 密文
     */
    // 带秘钥加密
    public static String md5(String text, String key) throws Exception {
        // 加密后的字符串
        String md5str = DigestUtils.md5Hex(text + key);
        return md5str;
    }

    // 不带秘钥加密
    public static String md5NoKey(String text) throws Exception {
        // 加密后的字符串
        String md5str = DigestUtils.md5Hex(text);
        return md5str;
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param key 密钥
     * @param md5 密文
     */
    // 根据传入的密钥进行验证
    public static boolean verify(String text, String key, String md5) throws Exception {
        String md5str = md5(text, key);
        if (md5str.equalsIgnoreCase(md5)) {
            System.out.println("MD5验证通过");
            return true;
        }
        return false;
    }

    // 测试
    public static void main(String[] args) throws Exception {
        String str = "test1test2test3test4test5";
        System.out.println("加密的字符串：" + str + "\t长度：" + str.length());
        String md5 = MD5Utils.md5(str, "abc");
        System.out.println(md5);
        System.out.println(MD5Utils.verify("test1test2test3test4test5", "abc", md5));
    }
}
