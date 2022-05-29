package com.dc.allo.roomplay.mapper;

import com.dc.allo.common.domain.room.RoomHeatValueTotay;
import com.dc.allo.roomplay.domain.example.RoomHeatValueTotayExample;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RoomHeatValueTotayMapper {
    int countByExample(RoomHeatValueTotayExample example);

    int deleteByExample(RoomHeatValueTotayExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoomHeatValueTotay record);

    int insertSelective(RoomHeatValueTotay record);

    List<RoomHeatValueTotay> selectByExample(RoomHeatValueTotayExample example);

    RoomHeatValueTotay selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoomHeatValueTotay record, @Param("example") RoomHeatValueTotayExample example);

    int updateByExample(@Param("record") RoomHeatValueTotay record, @Param("example") RoomHeatValueTotayExample example);

    int updateByPrimaryKeySelective(RoomHeatValueTotay record);

    int updateByPrimaryKey(RoomHeatValueTotay record);
}