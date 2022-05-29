package com.dc.allo.common.constants;

public class RoomConstant {
    /**
     * 发起pk的用户类型
     */
    public static class ChooseRoomPkUserType{
        public static final byte NORMAL_USER  = 1;//普通用户
        public static final byte ADMIN     = 2;//后台
    }

    /**
     * 发起pk的用户类型
     */
    public static class RoomPkStatus{
        public static final byte NOT_PK     = 0;//未在Pk中
        public static final byte PLEASE_PK  = 1;//请求pk中
        public static final byte REJECT_PK  = 2;//请求pk中
        public static final byte PKing      = 3;//PK中
        public static final byte PK_END     =4;//PK结束
        public static final byte PK_CANCEL  =6;//PK被取消
    }

    /**
     * 房间PK结果
     */
    public static class RoomPkResult{
        public static final byte ROOM_WIN    = 1;//发起Pk的房间赢
        public static final byte TARGET_WIN  = 2;//接受Pk的房间赢
        public static final byte NO_WIN      = 3;//平局
    }

    /**
     * 房间PK事件类型
     */
    public static class RoomPkEventType{
        public static final byte CANCEL_PK    = 1;//取消PK
        public static final byte ACTIVITY_PRIZE_RECORD_UP   = 2;//PK活动房间升级奖励
        public static final byte ACTIVITY_PRIZE_RECORD_OWNER   = 3;//PK活动房主奖励
    }
    /**
     * 房间成员角色
     */
    public static class RoomMemberRole{
        public static Byte OWNER            = 1;//房主
        public static Byte MANAGER          = 2;//房间管理员
        public static Byte MEMBER           = 3;//房间成员
        public static Byte AUDIT            = 4;//审核中
        public static Byte VISITOR          = 5;//游客
        public static Byte FAMILY           = 6;//家族(房间成员没有家族这个角色，只是在做个人相关房间需要这个)
    }

    public static class RoomPkHeatValueEventType{
        public static final byte SUC_OPEN         = 1;//成功开启Pk
        public static final byte PK_WIN           = 2;//PK胜利
        public static final byte PK_GIFT_TOTAL= 3;//PK胜利赢方的礼物总流水
    }

    public static class RoomPkActivityAwardType{
        public static final byte OWNER         = 1;//房主奖励
        public static final byte SEND           = 2;//送礼最多的人奖励
    }
}
