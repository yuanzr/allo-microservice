package com.dc.allo.user.mapper.baseinfo;

import com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo;
import com.dc.allo.rpc.domain.user.baseinfo.UserInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by zhangzhenjun on 2020/3/22.
 */
@Mapper
public interface UserInfoMapper {

    /**
     * 根据UID查询用户详情
     * @param uid
     * @return com.dc.allo.rpc.domain.user.baseinfo.UserInfo
     * @author qinrenchuan
     * @date 2020/4/1/0001 19:23
     */
    @Select({" SELECT "
                + " uid, erban_no, has_pretty_erban_no, phone, birth, "
                + " nick, hobby, signture, user_voice, voice_dura, "
                + " follow_num, fans_num, def_user, fortune, channel_type,"
                + " gender, avatar, country_id, region, user_desc, "
                + " create_time, update_time, os, osVersion, app, "
                + " channel, isp_type, net_type, model,"
                + " device_id, new_device, app_version, noble_id, noble_name  "
            + " FROM users "
            + " WHERE uid=#{uid} "})
    UserInfo getById(@Param("uid") Long uid);

    /**
     * 根据耳伴号查询用户信息
     * @param erbanNo
     * @return com.dc.allo.rpc.domain.user.baseinfo.UserInfo
     * @author qinrenchuan
     * @date 2020/4/1/0001 20:33
     */
    @Select({" SELECT "
                + " uid, erban_no, has_pretty_erban_no, phone, birth, "
                + " nick, hobby, signture, user_voice, voice_dura, "
                + " follow_num, fans_num, def_user, fortune, channel_type,"
                + " gender, avatar, country_id, region, user_desc, "
                + " create_time, update_time, os, osVersion, app, "
                + " channel, isp_type, net_type, model,"
                + " device_id, new_device, app_version, noble_id, noble_name  "
            + " FROM users "
            + " WHERE erban_no=#{erbanNo} "})
    UserInfo getByErbanNo(@Param("erbanNo") Long erbanNo);

    /**
     * 根据id列表查询用户列表
     * @param list
     * @return java.util.List<com.dc.allo.rpc.domain.user.baseinfo.UserInfo>
     * @author qinrenchuan
     * @date 2020/4/29/0029 15:54
     */
    @Select({"<script>"
                + " SELECT "
                    + " uid, erban_no, has_pretty_erban_no, phone, birth, "
                    + " nick, hobby, signture, user_voice, voice_dura, "
                    + " follow_num, fans_num, def_user, fortune, channel_type,"
                    + " gender, avatar, country_id, region, user_desc, "
                    + " create_time, update_time, os, osVersion, app, "
                    + " channel, isp_type, net_type, model,"
                    + " device_id, new_device, app_version, noble_id, noble_name  "
                + " FROM users "
                    + "WHERE 1=1 "
                    + "<if test=\"list != null and list.size() > 0\"> "
                        + " AND uid in"
                        + "<foreach collection='list' item='item' index='index' separator=',' open='(' close=')'>"
                            +   "(#{item}) "
                        + "</foreach>"
                    + "</if>"
            + "</script>"})
    List<UserInfo> queryByUids(@Param("list") List<Long> list);



    @Select({"<script>"
            + " SELECT "
            + " uid, erban_no, has_pretty_erban_no, phone, birth, "
            + " nick, hobby, signture, user_voice, voice_dura, "
            + " follow_num, fans_num, def_user, fortune, channel_type,"
            + " gender, avatar, country_id, region, user_desc, "
            + " create_time, update_time, os, osVersion, app, "
            + " channel, isp_type, net_type, model,"
            + " device_id, new_device, app_version, noble_id, noble_name  "
            + " FROM users "
            + "WHERE 1=1 "
            + "<if test='list != null and list.size() > 0'> "
            + " AND erban_no in"
            + "<foreach collection='list' item='item' index='index' separator=',' open='(' close=')'>"
            +   "(#{item}) "
            + "</foreach>"
            + "</if>"
            + "</script>"})
    @Deprecated
    List<UserInfo> queryByErbanNos(List<String> list);

    /**
     * 根据耳伴查询用户列表（简体）
     * @param list
     * @return java.util.List<com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo>
     * @author qinrenchuan
     * @date 2020/6/4/0004 10:55
     */
    @Select({"<script>"
            + " SELECT "
                + " uid, "
                + " erban_no, "
                + " nick, "
                + " gender, "
                + "avatar "
            + " FROM users "
                + "WHERE 1=1 "
                + "<if test='list != null and list.size() > 0'> "
                    + " AND erban_no in"
                    + "<foreach collection='list' item='item' index='index' separator=',' open='(' close=')'>"
                        +   "(#{item}) "
                    + "</foreach>"
                + "</if>"
            + "</script>"})
    List<SimpleUserInfo> querySimpleUsersByErbanNos(@Param("list")List<Long> list);

    /**
     * 根据id列表查询用户（简单信息）列表
     * @param uidList
     * @return java.util.List<com.dc.allo.rpc.domain.user.baseinfo.SimpleUserInfo>
     * @author qinrenchuan
     * @date 2020/6/2/0002 18:07
     */
    @Select({"<script>"
                + " SELECT "
                    + " uid, "
                    + " erban_no, "
                    + " phone, "
                    + " nick, "
                    + " gender, "
                    + " avatar  "
                + " FROM users "
                    + "WHERE 1=1 "
                    + "<if test=\"list != null and list.size() > 0\"> "
                        + " AND uid in"
                        + "<foreach collection='list' item='item' index='index' separator=',' open='(' close=')'>"
                            +   "(#{item}) "
                        + "</foreach>"
                    + "</if>"
            + "</script>"})
    List<SimpleUserInfo> querySimpleUsesByUids(@Param("list")List<Long> uidList);

}
