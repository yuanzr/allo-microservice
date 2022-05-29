package com.dc.allo.finance.mapper;

import com.dc.allo.common.utils.TimeUtils;
import com.dc.allo.rpc.domain.finance.GiftSendRecord;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by zhangzhenjun on 2020/4/8.
 */
@Mapper
public interface GiftSendRecordMapper {

    @InsertProvider(type = GiftSendMapperProvider.class, method = "addSql")
    int addGiftSendRecord(@Param("record") GiftSendRecord record);

    class GiftSendMapperProvider {
        public String addSql(@Param("record") GiftSendRecord record){
            String tableSuffix = TimeUtils.toStr(new Date(),TimeUtils.YEAR2MONTH4SQL);
            StringBuffer sqlBuf = new StringBuffer();
            sqlBuf.append("REPLACE INTO gift_send_record_").append(tableSuffix);
            sqlBuf.append(" (send_record_id,uid,recive_uid,recive_type,send_env,room_uid,room_type" +
                    ",gift_id,gift_num,gift_type,play_effect,total_gold_num,total_diamond_num,create_time" +
                    ",gift_source,total_crystal_num) VALUES " +
                    "(#{record.sendRecordId},#{record.uid},#{record.reciveUid},#{record.reciveType},#{record.sendEnv}," +
                    "#{record.roomUid},#{record.roomType},#{record.giftId},#{record.giftNum},#{record.giftType}," +
                    "#{record.playEffect},#{record.totalGoldNum},#{record.totalDiamondNum}," +
                    "#{record.createTime},#{record.giftSource},#{record.totalCrystalNum})");
            return sqlBuf.toString();
        }
    }
}
