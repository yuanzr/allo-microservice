package com.dc.allo.roomplay.mapper.pk;

import com.dc.allo.common.domain.room.RoomHeatValuePrizeRecord;
import com.dc.allo.roomplay.domain.example.RoomHeatValuePrizeRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomHeatValuePrizeRecordMapper {
    int countByExample(RoomHeatValuePrizeRecordExample example);

    int deleteByExample(RoomHeatValuePrizeRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoomHeatValuePrizeRecord record);

    int insertSelective(RoomHeatValuePrizeRecord record);

    List<RoomHeatValuePrizeRecord> selectByExample(RoomHeatValuePrizeRecordExample example);

    RoomHeatValuePrizeRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoomHeatValuePrizeRecord record, @Param("example") RoomHeatValuePrizeRecordExample example);

    int updateByExample(@Param("record") RoomHeatValuePrizeRecord record, @Param("example") RoomHeatValuePrizeRecordExample example);

    int updateByPrimaryKeySelective(RoomHeatValuePrizeRecord record);

    int updateByPrimaryKey(RoomHeatValuePrizeRecord record);
}