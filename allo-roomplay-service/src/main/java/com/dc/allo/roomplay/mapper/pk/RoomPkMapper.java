package com.dc.allo.roomplay.mapper.pk;

import com.dc.allo.rpc.domain.roomplay.RoomPkRecord;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RoomPkMapper {
    @Select("select * from room_pk_record where (room_uid = #{uid} or target_room_uid =#{uid}) order by create_time desc limit 1")
    RoomPkRecord getCurrentPkRecord(Long uid);
}
