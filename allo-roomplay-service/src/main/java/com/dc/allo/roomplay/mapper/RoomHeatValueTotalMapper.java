package com.dc.allo.roomplay.mapper;

import com.dc.allo.common.domain.room.RoomHeatValueTotal;
import com.dc.allo.roomplay.domain.example.RoomHeatValueTotalExample;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomHeatValueTotalMapper {
    int countByExample(RoomHeatValueTotalExample example);

    int deleteByExample(RoomHeatValueTotalExample example);

    int deleteByPrimaryKey(Long roomUid);

    int insert(RoomHeatValueTotal record);

    int insertSelective(RoomHeatValueTotal record);

    List<RoomHeatValueTotal> selectByExample(RoomHeatValueTotalExample example);

    RoomHeatValueTotal selectByPrimaryKey(Long roomUid);

    int updateByExampleSelective(@Param("record") RoomHeatValueTotal record, @Param("example") RoomHeatValueTotalExample example);

    int updateByExample(@Param("record") RoomHeatValueTotal record, @Param("example") RoomHeatValueTotalExample example);

    int updateByPrimaryKeySelective(RoomHeatValueTotal record);

    int updateByPrimaryKey(RoomHeatValueTotal record);
}