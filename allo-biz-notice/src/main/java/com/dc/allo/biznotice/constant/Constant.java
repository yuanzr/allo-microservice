package com.dc.allo.biznotice.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * description: Constant
 *
 * @date: 2020年05月08日 18:00
 * @author: qinrenchuan
 */
public class Constant {
    private Constant() {
    }

    /** 用户钱包查询url */
    public static final String queryUserPurseUrl = "/purse/getByUid";
    //public static final String queryUserPurseUrl = "http://localhost//purse/getByUid";

    /**
     * Banner的类型
     * @since 3.7.0
     */
    public static class BannerType {
        /** 首充 */
        public static final byte BANNER_FIRST_RECHARGE = 9;
        /** 充值页面 */
        public static final byte BANNER_RECHARGE = 10;
    }

    /**
     * banner/firebase-message事件
     * @since 3.7.0
     */
    public static class BannerActionType {
        public static final byte NONE = 0;
        /** 跳转原生页面 */
        public static final byte NATIVE = 1;
        /** 跳转h5页面 */
        public static final byte WEB = 2;
        /** 打开h5弹窗 */
        public static final byte WEB_DIALOG = 3;
    }


    /**
     * 跳转事件
     * @since 3.8.0
     */
    public static class ActionType {
        /** 不跳转 */
        public static final String NONE = "none";
        /** 跳转原生页面 */
        public static final String NATIVE = "native";
        /** 跳转h5页面 */
        public static final String WEB = "web";
        /** 打开h5弹窗 */
        public static final String WEB_ALERT = "webAlert";
    }

    /**
     * 客户端原生页面
     * @since 3.8.0
     */
    public static Map<Integer, String> ActionMap = new HashMap<Integer, String>(){
        {
            put(1,"allolike://Jump/Pager/home/main");//首页
            put(2,"allolike://Jump/Pager/room/av_room?roomUid={0}");//房间
            put(3,"allolike://Jump/Pager/other/recharge");//充值页
            put(4,"allolike://Jump/Pager/user/user_info?userId={0}&from={1}");// 个人页
            put(5,"allolike://Jump/Pager/store/dress_up?userId={0}");//商城座驾/头饰/靓号
            put(6,"allolike://Jump/Pager/store/my_bags?position={0}");//商城我的背包
            put(7,"allolike://Jump/Pager/user/family_home?familyId=xx&createFamilySuccess={0}");//家族
            put(8,"allolike://Jump/Pager/im/group?teamId={0}");//群聊（家族群组）
            put(9,"allolike://Jump/Pager/room/room_background");//背景页
            put(10,"allolike://Jump/Pager/im/pulic_chat_hall");// 公聊大厅
            put(11,"allolike://Jump/Pager/user/bind_phone");//绑定手机号
            put(12,"allolike://Jump/Pager/home/sign_in");//签到
            put(13,"allolike://Jump/Pager/home/task?position={0}");//任务
            put(14,"allolike://Jump/Pager/user/family_square");//家族广场
            put(15,"allolike://Jump/Pager/pk/pk_center");//PK广场
        }
    };

    /**
     * 消息发送对象
     * @since 3.8.0
     */
    public static class MsgToType {
        /** 用户 */
        public static final Byte USERS = 1;
        /** TOPIC */
        public static final Byte TOPIC = 2;
    }

    /**
     * 消息类型
     * @since 3.8.0
     */
    public static class MsgType {
        /** 立即推送 */
        public static final Byte TEXT = 1;
        /** 定时推送 */
        public static final Byte TEXT_IMAGE = 2;
    }


    /**
     * 推送类型
     * @since 3.8.0
     */
    public static class MsgPushType {
        /** 立即推送 */
        public static final Byte IMMEDIATELY = 1;
        /** 定时推送 */
        public static final Byte TIMING = 2;
    }


    /**
     * 推送状态
     * @since 3.8.0
     */
    public static class MsgPushStatusType {
        /** 未推送 */
        public static final Byte UNPUSH = 1;
        /** 已投递,待推送 :已投递的不能进行编辑*/
        public static final Byte PEDNING = 2;
        /** 已发送 */
        public static final Byte PUSHED = 3;
    }

    /**
     * firebase语言区的topic
     */
    public static class FirebaseTopicRegion{
        public final  static  String ALL = "all";
        public final  static  String EN  = "en";
        public final  static  String AR  = "ar";
        public final  static  String IN  = "in";
    }

}
