package com.dc.allo.roomplay.mapper.pk;

import com.dc.allo.roomplay.domain.example.RoomPkSummaryExample;
import com.dc.allo.rpc.domain.roomplay.RoomPkSummary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomPkSummaryMapper {
    int countByExample(RoomPkSummaryExample example);

    int deleteByExample(RoomPkSummaryExample example);

    int deleteByPrimaryKey(Long roomUid);

    int insert(RoomPkSummary record);

    int insertSelective(RoomPkSummary record);

    List<RoomPkSummary> selectByExample(RoomPkSummaryExample example);

    RoomPkSummary selectByPrimaryKey(Long roomUid);

    int updateByExampleSelective(@Param("record") RoomPkSummary record, @Param("example") RoomPkSummaryExample example);

    int updateByExample(@Param("record") RoomPkSummary record, @Param("example") RoomPkSummaryExample example);

    int updateByPrimaryKeySelective(RoomPkSummary record);

    int updateByPrimaryKey(RoomPkSummary record);
}