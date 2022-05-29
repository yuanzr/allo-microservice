package com.dc.allo.common.utils;

import java.util.*;

/**
 * Created by zhangzhenjun on 2020/3/15.
 */
public class NumberUtil {

    /**
     * 从一个连续的数里[start,end)，获取N个不重复的数
     * @param start
     * @param end
     * @param N
     * @return
     */
    public static List<Integer> getMutilRamdomNum(int start,int end,int N) {
        if(end<=start) {
            throw new IllegalArgumentException("end <= start");
        }

        if(N>end-start) {
            throw new IllegalArgumentException("N > end - start");
        }


        List<Integer> retList = new ArrayList<>();

        if(N == end - start) {
            for (int i=start;i<end;i++) {
                retList.add(i);
            }

            return retList;
        }

        /**
         * 先用真随机算法，拿出连续的随机数
         */
        int end0 = end - start; // 减掉start的意义是可以让整堆数可以从0开始

        Set<Integer> existNum = new HashSet<>();
        for(int i=0;i<N*6;i++) {
            int num = new Random().nextInt(end0);
            if(existNum.contains(num)) {
                continue;
            }

            existNum.add(num);
            retList.add(num+start);

            if(retList.size()>=N) {
                return retList;
            }
        }

        /**
         * 如果6*N次都还没拿到随机数，用假随机算法，就是从一个下标开始，拿N个连续的数
         */
        retList = new ArrayList<>();
        int start0 = new Random().nextInt(end0);
        for (int i=0;i<N;i++) {
            int index = ((start0+i) % N)+start; // 因为end0是减掉了start的
            retList.add(index);
        }

        return retList;
    }

}
