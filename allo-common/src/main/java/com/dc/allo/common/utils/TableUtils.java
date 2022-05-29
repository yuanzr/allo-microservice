package com.dc.allo.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/5/29.
 */
public class TableUtils {

    /**
     * 月表后缀
     * @return
     */
    public static String getMonthTableSuffix(){
        String tableSuffix = TimeUtils.toStr(new Date(), TimeUtils.YEAR2MONTH4SQL);
        return tableSuffix;
    }

    /**
     * 下月表后缀
     * @return
     */
    public static String getNextMonthTableSuffix(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        String tableSuffix = TimeUtils.toStr(calendar.getTime(), TimeUtils.YEAR2MONTH4SQL);
        return tableSuffix;
    }
}
