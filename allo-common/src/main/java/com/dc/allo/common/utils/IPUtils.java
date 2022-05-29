package com.dc.allo.common.utils;

/**
 * Created by zhangzhenjun on 2020/3/15.
 */
public class IPUtils {
    /**
     * string ip to long
     * */
    public static long ipStrToLong(String ipaddress) {
        long[] ip = new long[4];
        int i = 0;
        for(String ipStr : ipaddress.split("\\.")){
            ip[i] = Long.parseLong(ipStr);
            i++;
        }
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    /**
     * ip long to String
     * */
    public static String iplongToIp(long ipaddress) {
        StringBuffer sb = new StringBuffer("");
        sb.append(String.valueOf((ipaddress >>> 24)));
        sb.append(".");
        sb.append(String.valueOf((ipaddress & 0x00FFFFFF) >>> 16));
        sb.append(".");
        sb.append(String.valueOf((ipaddress & 0x0000FFFF) >>> 8));
        sb.append(".");
        sb.append(String.valueOf((ipaddress & 0x000000FF)));
        return sb.toString();
    }
}
