package com.dc.allo.finance.mapper;

import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rpc.domain.finance.BillRecord;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/8.
 */
@Mapper
public interface BillRecordMapper {
    @InsertProvider(type = BillRecordMapperProvider.class, method = "addSql")
    int addBillRecord(@Param("record") BillRecord record);

    class BillRecordMapperProvider {
        public String addSql(@Param("record") BillRecord record){
            String tableSuffix = TimeUtils.toStr(new Date(), TimeUtils.YEAR2MONTH4SQL);
            StringBuffer sqlBuf = new StringBuffer();
            sqlBuf.append("REPLACE INTO bill_record_").append(tableSuffix);
            sqlBuf.append(" (bill_id,uid,target_uid,room_uid,bill_status,obj_id,obj_type" +
                    ",gift_id,gift_num,diamond_num,gold_num,money,create_time,update_time" +
                    ",coins_before,coins_after,diamond_before,diamond_after,remark) VALUES " +
                    "(#{record.billId},#{record.uid},#{record.targetUid},#{record.roomUid},#{record.billStatus}," +
                    "#{record.objId},#{record.objType},#{record.giftId},#{record.giftNum},#{record.diamondNum}," +
                    "#{record.goldNum},#{record.money},#{record.createTime}," +
                    "#{record.updateTime},#{record.coinsBefore},#{record.coinsAfter}" +
                    ",#{record.diamondBefore},#{record.diamondAfter},#{record.remark})");
            return sqlBuf.toString();
        }
    }
}
