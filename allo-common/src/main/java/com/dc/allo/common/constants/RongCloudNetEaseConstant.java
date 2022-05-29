package com.dc.allo.common.constants;

public class RongCloudNetEaseConstant {
    public static class UserUrl {
        public static String getToken = "/user/getToken.json";
        public static String addBlackUser = "/user/blacklist/add.json";
        public static String removeBlackUser = "/user/blacklist/remove.json";
        public static String conversationNotificationSet = "/conversation/notification/set.json";
    }

    public static class MessageUrl {
        //单聊
        public static String privatePublish = "/message/private/publish.json";
        //聊天室
        public static String chatRoomPublish = "/message/chatroom/publish.json";
        //广播
        public static String broadcastMsg = "/push.json";
        //系统消息
        public static String systemPublish = "/message/system/publish.json";
    }

    public static class RoomUrl {
        public static String createChatRoom = "/chatroom/create.json";
        public static String closeChatRoom = "/chatroom/destroy.json";
        public static String queryRoomOnlineNum = "/chatroom/user/query.json";
        public static String queryUserInRoom = "/chatroom/user/exist.json";
        public static String cannotTalk = "/chatroom/user/gag/add.json";
        public static String canTalk = "/chatroom/user/gag/rollback.json";

        public static String addBlockUser = "/chatroom/user/block/add.json";
        public static String cancelBlockUser = "/chatroom/user/block/rollback.json";
    }

    public static class GroupUrl {
        public static String createGroup = "/group/create.json";
        public static String quitGroup = "/group/quit.json";
        public static String updateGroup = "/group/refresh.json";
        public static String removeGroup = "/group/dismiss.json";
        public static String addGroupNotTalk = "/group/user/gag/add.json";
        public static String cancelGroupNotTalk = "/group/user/gag/rollback.json";
        public static String joinGroup = "/group/join.json";
    }
}
