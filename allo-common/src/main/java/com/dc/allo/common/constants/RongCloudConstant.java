package com.dc.allo.common.constants;

/**
 *@description
 *@author   wangchuangbiao
 *@datetime 2019/11/26 19:43
 *@since    v1.0
 */
public class RongCloudConstant {

    /** 处理成功业务码 */
    public static final Integer SUCCESS_CODE = 200;

    public static class RongCloundMessageObjectName{
        public static final String TEXT = "RC:TxtMsg";
        public static final String IMG = "RC:ImgMsg";
        public static final String IMG_TEXT = "RC:ImgTextMsg";

        //公聊大厅用　
        public static final String PUBLIC_CHAT = "DC:GlobalGenericTextMessage";
        //用户私聊
        public static final String GLOBAL_USER_TEXT = "DC:GlobalUserGenericTextMessage";
        //房间通用
        public static final String ROOM_TEXT = "DC:ChatRoomGenericMessage";
        //房间保活
        public static final String KEEP_ALIVE_ROOM = "DC:KeeAliveMessage";
        //签到提醒类提醒
        public static final String CHECK_IN_NOTICE = "DC:CheckinNoticeMessage";
        //通知
        public static final String NOTICE = "DC:NotificationMessage";
        //用户升级通知
        public static final String USER_UP_LEVEL = "DC:UserUpGardeLevelMessage";
        //房间开通通知
        public static final String OPEN_ROOM_NOTICE = "DC:AnchorOnlineNotice";
        //全局单用户通知消息(发给单用户但不显示出来，方便客户端做逻辑的处理)
        public static final String GLOBAL_USER_NOTICE = "DC:GlobalUserNoticeMessage";
        //家族通知消息
        public static final String FAMILY_NOTICE = "DC:FamilyNoticeMessage";
        //房间麦序消息
        public static final String ROOM_MIC_QUEUE = "DC:ChatRoomMicroQueueMessage";
        //小秘书图文推送消息
        public static final String SERVICE_RICH_CONTENT="DC:ServiceRichContentMessage";
        //小火箭
        public static final String ROCKET_GAME_NOTICE="DC:RocketGameNoticeMessage";
        //APP系统级别消息
        public static final String APP_SYS="DC:AppSystemLevelMessage";
        //活动图文推送消息
        public static final String ACTIVITY="DC:ActivityMessage";

        public static final String ROOM_TIP="DC:RoomTipNoticeMessage";

        public static final String PB_PUSH="DC:PbPushMessage";
    }

    public static class RongCloundPushContentType {
        public static final String JSON = "application/json";
    }

    /**
     * 通知类型消息
     */
    public static class NoticeEventType {
        //设置为管理员
        public static final int NIM_CHAT_ROOM_EVENT_TYPE_ADD_MANAGER = 307;

        //移除管理员
        public static final int NIM_CHAT_ROOM_EVENT_TYPE_REMOVE_MANAGER = 308;

        //成员进入聊天室
        public static final int NIM_CHAT_ROOM_EVENT_TYPE_ENTER = 301;

        //成员离开聊天室
        public static final int NIM_CHAT_ROOM_EVENT_TYPE_EXIT = 302;

        //成员被拉黑
        public static final int NIM_CHAT_ROOM_EVENT_TYPE_ADD_BLACK = 303;

        //成员被取消拉黑
        public static final int NIM_CHAT_ROOM_EVENT_TYPE_REMOVE_BLACK = 304;

        //聊天室成员被踢
        public static final int NIM_CHAT_ROOM_EVENT_TYPE_KICKED = 313;

        //聊天室信息更新
        public static final int NIM_CHAT_ROOM_EVENT_TYPE_INFO_UPDATED = 312;

        //聊天室通用队列变更的通知
        public static final int NIM_CHAT_ROOM_EVENT_TYPE_QUEUE_CHANGE = 317;
    }

    /**
     * 心跳默认过期时间,单位毫秒
     */
    public static final long HEART_BEAT_TIME_OUT = 1000 * 60 * 3;

    /**
     * 客户端默认心跳时间,单位毫秒
     */
    public static final long CLIENT_HEART_BEAT_REQUEST_TIME = 1000 * 60;

    /**
     * 聊天室踢人5分钟不能再进聊天室
     */
    public static final int ROOM_BLOCK_USER_MIN = 5;
    /**
     * 融云聊天室拉黑用户43200分钟不能再进聊天室(因为融云只能最高43200分钟)
     * 在本地DB库里,拉黑是永久
     */
    public static final int ROOM_BLACK_USER_MIN = 43200;

    /**
     * 融云聊天室拉黑用户43200分钟不能再进聊天室(因为融云只能最高43200分钟)
     * 在本地DB库里,拉黑是永久(这里设3600天,也就是10年)
     */
    public static final int ROOM_BLACK_USER_LONG = 3600;

    /**
     * 房间麦位数
     */
    public static final int ROOM_MIC_POSITION_NUM = 9;

    /**
     * 融云查询房间在线人数最大值
     */
    public static final String ROOM_ONLINE_USE_MAX = "500";

    public static class RongClundCallbackStatusType {
        //在线
        public static final String ONLINE = "0";
        //离线
        public static final String OFFLINE = "1";
        //登出
        public static final String LOGOUT = "2";
    }

    public static class RongClundMsgReceiveChannelType {
        //聊天室会话
        public static final String TEMPGROUP = "TEMPGROUP";
    }

    public static class RongClundReceiveRoomStatusType {
        //触发融云退出聊天室机制将用户踢出
        public static final String EXCEPTION_OUT = "1";
    }

    public static class RongClundReceiveRoomType {
        //退出
        public static final String OUT = "2";
    }

    public static class ConversationType {
        public static final String GROUP = "3";
    }

    /**
     * 消息免打扰设置状态
     */
    public static class ConversationMuted {
        public static final int CLOSE = 0;
        public static final int OPEN = 1;
    }

    public static final String RONG_CLOUD_DEFAULT_USER_NAME="def_name";
    public static final String RONG_CLOUD_DEFAULT_USER_PORTRAIT="def_portrait";

    //群成员禁言时长（单位分钟)
    public static final Integer RONG_CLOUD_MUTEMEMBERS_MIN=1 * 30 * 24 * 60 ;

    public static class APP_SYS_LEVEL_MSG_TYPE {
        //后台封禁用户
        public static final int block = 1;
    }

    public static final String API_SG01_ADDRESS="http://api-sg01.ronghub.com";
}
