package com.dc.allo.roomplay.mapper.pk;

import com.dc.allo.roomplay.domain.example.RoomPkRecordExample;
import com.dc.allo.rpc.domain.roomplay.RoomPkRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomPkRecordMapper {
    int countByExample(RoomPkRecordExample example);

    int deleteByExample(RoomPkRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RoomPkRecord record);

    int insertSelective(RoomPkRecord record);

    List<RoomPkRecord> selectByExample(RoomPkRecordExample example);

    RoomPkRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RoomPkRecord record, @Param("example") RoomPkRecordExample example);

    int updateByExample(@Param("record") RoomPkRecord record, @Param("example") RoomPkRecordExample example);

    int updateByPrimaryKeySelective(RoomPkRecord record);

    int updateByPrimaryKey(RoomPkRecord record);
}