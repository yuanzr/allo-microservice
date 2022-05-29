
package com.dc.allo.common.constants;


import com.dc.allo.common.utils.PropertyUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by liuguofu on 2017/4/28.
 */
public class Constant {

    public interface App{
        String ALLO = "allo";
    }

    public static class RoomOptStatus {
        public static byte in = 1;
        public static byte out = 2;
    }

    public static class AuctCurStatus {
        public static Byte ing = 1;// 竞拍中
        public static Byte finish = 2;// 竞拍完成
        public static Byte error = 2;// 竞拍中断
    }

    public static class UsersAtt {
        public static Byte A = 1;// A面用户
        public static Byte B = 2;// B面用户
    }

    public static class RoomAtt {
        public static Byte A = 1;// A面房间
        public static Byte B = 2;// B面房间
    }

    public static class OrderServType {
        public static Byte auct = 1;// 竞拍单
        public static Byte reward = 2;// 悬赏单
    }

    public static class OrderServStatus {
        public static Byte create = 1;// 订单生成
        public static Byte ing = 2;// 订单处理中
        public static Byte finish = 3;// 订单已完成
        public static Byte error = 4;// 订单已完成
    }

    public static class OrderTimeOutType {
        public static int oneTimeOut = 1;
        public static int towTimeOut = 2;
    }

    //家族长当前有流水奖励可以领取
    public static final String family_leader_have_reward = "1";

    public static final BigDecimal default_alternative_charge_min_gold = new BigDecimal(450);//代充最少多少金币起　默认450

    public static class AppVersion {
        public static Byte online = 1;
        public static Byte audit = 2;
        public static Byte forceupdate = 3;
        public static Byte recommupdate = 4;
        public static Byte deleted = 5;
    }


    public static class ExchangeDiamondGold {
        // 钻石兑换金币比率
        public static double rate = 1.0;//2018-01-09发布更新

    }

    public static class RedPacket {
        public static Byte withDraw = 5;//红包提现
        public static Byte first = 1;
        public static Byte share = 2;
        public static Byte register = 3;
        public static Byte bouns = 4;
    }

    public static class PacketStatus {
        public static Byte create = 1;//创建提现申请
        public static Byte suc = 2;//成功提现并且已经转账
        public static Byte error = 3;//判定为刷红包行为
    }

    public static class BillStartTime {
        public static Date startTime = new Date(1503072000000L);
    }

    public static class SexRobotType {
        public static Byte man = 1;// 男性
        public static Byte woMan = 2;// 女性
    }

    public static class SexType {
        public static Byte male = 1; // 男性
        public static Byte female = 2; // 女性
    }

    public static class DefMsgType {

        public static int Auction               = 1; //拍卖
        public static int AuctionStart          = 11;//拍卖开始
        public static int AuctionFinish         = 12;//拍卖完成
        public static int AuctionUpdate         = 13;//拍卖结束

        public static int Reward                = 2;
        public static int RewardChange          = 21;

        public static int Gift                  = 3;
        public static int GiftSend              = 31;//单个送礼
        public static int BroadGiftSend         = 32;//全麦送礼

        public static int GiftWhole             = 12;//全麦送礼物，兼容新旧版本，first不能用Gift=3
        public static int GiftSendWholeMicro    = 121;//全麦送礼物

        public static int Micro                 = 4;
        public static int MicroUp               = 41;// 用户自己上麦
        public static int MicroDown             = 42;// 用户自己下麦
        public static int MicroApply            = 43;// 用户申请上麦
        public static int MicroOwnerExchange    = 44;// 更新交换麦序
        public static int MicroOwnerUpUser      = 45;// 房主给用户上麦
        public static int MicroOwnerKickUser    = 46;// 房主踢用户下麦
        public static int MicroOwnerTopUser     = 47;// 房主将用户麦序置顶

        // ---------------micro version2---------------------
        public static int MicroInvite           = 411;// 房主邀请用户上麦
        public static int MicroAccept           = 412;// 用户同意上麦
        public static int MicroOwnerKickUserV2  = 413;// 房主踢用户下麦
        public static int MicroOwnerTopUserV2   = 414;// 房主将麦序用户置顶
        public static int MicroUserLeftV2       = 415;// 用户自行离开麦序
        // ---------------micro version2-----------------------

        public static int Purse                 = 5;
        public static int PurseGoldMinus        = 51;// 用户金币消费
        public static int PurseDiamondMinus     = 52;// 用户被扣钻石

        public static int RoomOpen              = 6;
        public static int RoomOpenNotice        = 61;// 用户金币消费

        public static int DealFinish            = 7;
        public static int DealFinishNotice      = 71; //用户拍卖消费

        public static int OrderReTime           = 8;
        public static int OrderReTimeNotice     = 81; //订单剩余时间


        public static int PointToPointMsg       = 10;  //发送点对点消息
        public static int PushPicWordMsg        = 101; //发送图文消息
        public static int PushTextMsg           = 102; //发送文本消息

        public static int Packet                = 11;  //红包
        public static int PacketFirst           = 111; //新人红包
        public static int PacketShare           = 112; //分享红包
        public static int PacketRegister        = 113; //邀请注册红包
        public static int PacketBouns           = 114; //充值抽成红包

        public static int baoliu                = 12;//客户端已经使用该字段，保留该字段


        public static int Draw                  = 13; //抽奖
        public static int DrawChance            = 131;//获取抽奖机会

        public static int Level                 = 24;   // 等级消息
        public static int Charm_Upgrade         = 242;    // 魅力升级提醒
        public static int Exper_Upgrade         = 241;    // 经验升级提醒
        public static int Online_Upgrade         = 243;    // 在线时长升级提醒

        public static int Hall                  = 32;     // 模厅消息
        public static int HallApply             = 321;    // 申请加入模厅
        public static int HallInvite            = 322;    // 管理者邀请进入模厅
        public static int HallQuit              = 323;    // 申请退出模厅
        public static int HallNormal            = 324;    // 其他文本消息
        public static int AddHall               = 325;    // 设置为模厅

        public static int RoomMember                   = 29;    // 房间会员消息大类
        public static int RoomMember_Apply             = 291;   // 房间会员申请
        public static int RoomMember_Approve           = 292;   // 房间会员通过
        public static int RoomMember_Reject            = 293;   // 房间会员申请拒接
        public static int RoomMember_Remove            = 294;   // 房间会员移除
        public static int RoomMember_add_Manager       = 295;   // 房间管理员添加
        public static int RoomMember_Remove_Manager    = 296;   // 房间管理员移除

        public static int FingerGuessing               = 46; // 猜拳消息
        public static int Promoter_FingerGuessing      = 461; // 发起人出拳消息
        public static int Participant_FingerGuessing   = 462; // 参与者参与后的结果消息
        public static int My_FingerGuessing            = 463; // 发起人所看到的最终结果的消息

        public static int GameWheel                    = 47;  // 大转盘消息
        public static int GameWheel_Apply              = 471; // 转盘[发起]消息
        public static int GameWheel_Join               = 472; // 转盘[加入]消息
        public static int GameWheel_Expired            = 473; // 转盘[过期]消息
        public static int GameWheel_Start              = 474; // 转盘[启动]消息
        public static int GameWheel_End                = 475; // 转盘[结束]消息
        public static int GameWheel_Cancel             = 476; // 转盘[取消]消息
        public static int GameWheel_Notice             = 477; // 转盘奖励全服通知提醒
        public static int GameWheel_Notice_Return      = 478; // 转盘[退款]通知

        public static int GameRocket                   = 48;  // 小火箭（怦然心动）消息
        public static int GameRocketSendGift           = 481; // 小火箭（怦然心动）增加经验值抖动消息。小于百分之九十五每增长百分之五推一次，超过百分之九十五每次都推
        public static int GameRocketUpgrade            = 482; // 小火箭（怦然心动）爆炸消息
        public static int GameRocketRoom               = 483; // 小火箭（怦然心动）房间公屏消息
        public static int GameRocketPublic             = 484; // 小火箭（怦然心动）全服消息
        public static int GameRocketGetPrizeSecretary  = 485; // 小火箭（怦然心动）奖品领取小秘书消息

        public static int UniqueID                     = 49;  // 靓号消息
        public static int UniqueID_Send                = 491; // 靓号赠送消息

        //房间
        public static int Room                    = 51;
        public static int RoomKeepAlive           = 511;  // 房间保活
        public static int RoomNotice              = 512;  // 房间通知
        public static int RoomMicPosition         = 513;  // 房间麦序通知

    }

    /**
     * 上麦操作状态
     */
    public static class MicroHandleStatus {
        public static int invited = 1;// 房主发起邀请上麦
        public static int inmicro = 2;// 已经在麦上
    }

    public static class EventType {
        public static String CONVERSATION = "1";// 表示CONVERSATION消息，即会话类型的消息（目前包括P2P聊天消息，群组聊天消息，群组操作，好友操作）
        public static String LOGIN = "2";// 表示LOGIN消息，即用户登录事件的消息
        public static String LOGOUT = "3";// 表示LOGOUT消息，即用户登出事件的消息
        public static String CHATROOM = "4";// 表示CHATROOM消息，即聊天室中聊天的消息
        public static String AUDIO = "5";// 表示AUDIO/VEDIO/DataTunnel消息，即汇报实时音视频通话时长、白板事件时长的消息
        public static String AUDIOSAVE = "6";// 表示音视频/白板文件存储信息，即汇报音视频/白板文件的大小、下载地址等消息
        public static String DRAWBACK = "7";// 表示单聊消息撤回抄送
        public static String QDRAWBACK = "8";// 表示群聊消息撤回抄送
        public static String CHATROOMINTOUT = "9";// 表示CHATROOM_INOUT信息，即汇报主播或管理员进出聊天室事件消息
        public static String ECPCALLBACK = "10"; // 表示ECP_CALLBACK信息，即汇报专线电话通话结束回调抄送的消息
        public static String SMSCALLBACK = "11"; // 表示SMS_CALLBACK信息，即汇报短信回执抄送的消息
        public static String CHAT_ROOM_QUEUE = "14"; //聊天室队列操作的事件消息　
    }

    /**
     * 设置聊天室内用户角 色 1: 设置为管理员，operator必须是创建者 2:设置普通等级用户，operator必须是创建者或管理员
     * -1:设为黑名单用户，operator必须是创建者或管理员 -2:设为禁言用户，operator必须是创建者或管理员
     */
    public static class RoleOpt {
        public static int admin = 1;
        public static int common = 2;
        public static int bloack = -1;
        public static int shutup = -2;
    }

    /**
     * 充值货币类型
     */
    public static class Currency {
        public static String cny = "cny";// 人民币

    }

    /**
     * 充值记录状态
     */
    public static class ChargeRecordStatus {
        public static Byte create  = 1;
        public static Byte finish  = 2;
        public static Byte error   = 3;
        public static Byte timeout = 4;

    }

    public static class ChargeChannel {
        public static final String alipay           = "alipay";      // 支付宝支付
        public static final String wx               = "wx";          // 微信支付
        public static final String wx_pub           = "wx_pub";      // 微信公众号支付
        public static final String ios_pay          = "ios_pay";     // IOS支付
        public static final String wx_wap           = "wx_wap";      // 微信H5支付
        public static final String alipay_wap       = "alipay_wap";  // 支付宝H5支付
        public static final String exchageByDiamond = "exchange";    //钻石兑换
        public static final String companyAccount   = "companyAccount";//充值打公账
        public static final String google_pay       = "google_pay";  //Google支付
    }

    public static class DepositStatus {
        public static Byte create = 1;
        public static Byte finish = 2;
    }

    public static class DepositUseType {
        public static Byte auct = 1;
        public static Byte reward = 2;
    }

    public static class Visitor {
        public static Long visitorErbanNo = 99999999999L;
    }

    /**
     * 送礼物对象类型 1轻聊房间、竞拍房间给主播直接刷礼物，2私聊送个人礼物,3坑位房中，给坑位中的人刷礼物
     */
    public static class SendGiftType {
        /**
         * 给主播直接刷礼物
         **/
        public static final byte room = 1;
        /**
         * 给个人私聊送礼物
         **/
        public static final byte person = 2;
        /**
         * 房间内给坑位上的人送礼物
         **/
        public static final byte roomToperson = 3;
        /**
         * 公聊大厅给人送礼物
         **/
        public static final byte pubRoomToPerson = 5;
        /**
         * 猜拳给人送礼物
         **/
        public static final byte fingerToPerson = 6;
    }

    /**
     * 礼物类型 1轻聊房，2游戏轰趴房，3福袋, 4套卡（不清楚前两种是什么设定...）
     */
    public static class GiftType {
        public static final byte LIGHT_CHAT = 1;
        public static final byte GAME = 2;
        public static final byte LUCKY_BAG = 3;
        public static final byte CARDS = 4;
    }

    public static class official {
        public static Long uid = 900183L;
    }

    public static class PayloadSkiptype {
        // 1跳app页面，2跳聊天室，3跳h5页面
        public static int apppage = 1;
        public static int room = 2;
        public static int h5 = 3;
    }

    public static class PayloadRoute {
        //1.跳转订单页面2.跳转打电话页面
        public static int order = 1;
        public static int call = 2;
    }

    public static class Rate {
        public static double moneyToGold = 10;
    }

    public static class Commission {
        //        public static double officialGift = 0.6;// 礼物官方佣金
        public static double officialGift = 0.5;// 礼物官方佣金
        public static double officialAuctOrder = 0.57;// 竞拍订单官方佣金
        public static double roomOwnerAuctOrder = 0.03;// 竞拍订单房主佣金
        //        public static double akira = 0.4;// 声优，收礼物方、订单服务者佣金
        // 2018-04-29 修改收礼物的分成（0.5->0.7）
        public static double akira = 0.7;// 声优，收礼物方、订单服务者佣金
        public static double diamondNumAkira = 0.3;// 声优，收礼物方、订单服务者佣金
        public static double goldAkira       = 0.3;// 声优，收礼物方、订单服务者佣金
    }

    /**
     * 预扣款操作类型，1是在原来基础上增加，2是直接替换
     */
    public static class DepositUpdateType {
        public static int plus = 1;
        public static int replace = 2;
    }

    public static class BillType {
        // 账单类型:1充值2提现3消费订单支出4服务订单收入5刷礼物消费6收礼物收入7发个人红包消费8收到个人红包收入9房主佣金收入10注册送金币11分享送金币12官方送金币13关注公众号送金币
        public static Byte charge                    = 1;// 充值
        public static Byte getCash                   = 2;// 提现
        public static Byte orderPay                  = 3;// 消费订单支出
        public static Byte orderIncome               = 4;// 服务订单收入
        public static Byte giftPay                   = 5;// 送礼物支出
        public static Byte giftIncome                = 6;// 收礼物收入
        public static Byte redPackPay                = 7;// 发个人红包消费
        public static Byte redPackIncome             = 8;// 收到个人红包收入
        public static Byte roomOwnerIncome           = 9;// 房主佣金收入
        public static Byte registSendGift            = 10;// 注册送金币
        public static Byte shareGold                 = 11;// 分享送金币
        public static Byte interSendGold             = 12;// 官方直接送金币
        public static Byte followSendGold            = 13;// 关注公众号送金币
        public static Byte exchangeDimondToGoldPay   = 14;// 兑换钻石支出
        public static Byte exchangeDimondToGoldIncome = 15;// 兑换钻石收入

        public static Byte hallowActRewardGold       = 16;// 万圣节金币奖励
        public static Byte actReward                 = 17;//活动奖励
        public static Byte chargeByCompanyAccount    = 20;// 打款至公账充值金币

        public static Byte redeemCode                = 21;  //兑换码兑换金币
        public static Byte draw                      = 23;  //抽奖得金币
        public static Byte bonusPerDaySend           = 24;  //发送的
        public static Byte bonusPerDayRecv           = 25;  //钻石回馈账单
        public static Byte openNoble                 = 26;  //开通贵族
        public static Byte renewNoble                = 27;  //续费贵族
        public static Byte roomNoble                 = 28;  //房间内开通贵族分成
        public static Byte openNobleReturn           = 29;  //开通贵族返还金币
        public static Byte renewNobleReturn          = 30;  //续费贵族返还金币
        public static Byte purchaseCarGoods          = 31;  //购买座驾

        public static Byte renewCarGoods             = 32;  //续费座驾
        public static Byte duibaDraw                 = 33;  // 摇奖机金币支出
        public static Byte duibaDrawReward           = 34;  // 摇奖机金币收入
        public static Byte giftMagicPay              = 35;  // 发送魔法礼物支出
        public static Byte giftMagicIncome           = 36;  // 接收魔法收入

        public static Byte monsterDrawReward         = 37; // 打怪夺宝奖励

        public static Byte giveCarGoods              = 38; //赠送座驾

        public static Byte purchaseHeadwear          = 39; // 购买头饰
        public static Byte renewHeadwear             = 40; // 续费头饰
        public static Byte donateHeadwear            = 41; // 赠送头饰

        //        public static Byte purchaseBackground        = 42; // 购买背景商品
        //        public static Byte renewBackground           = 43; // 续费背景商品
        public static Byte donateaseBackground       = 44; // 赠送背景商品

        public static Byte packetGetCash             = 42; //红包提现

        public static Byte buyOpenBoxKey             = 45; //购买开箱子钥匙

        public static Byte eggPrizeGold              = 46; // 砸金蛋得金币

        public static Byte roomFlowExtract           = 47; //公会房间流水提成

        public static Byte playNumberGame            = 48; //房间幸运数

        public static Byte drawGold                  = 49; //瓜分金币

        public static Byte buyActivityPack           = 50; //购买礼包

        public static Byte sign_gold                 = 51; //签到发送金币

        public static Byte mission_gold              = 52;//任务赠送金币

        public static Byte purchaseCarGoods_diamond  = 53;//钻石购买座驾

        public static Byte renewCarGoods_diamond     = 54;//钻石续费座驾

        public static Byte giveCarGoods_diamond      = 55;//钻石赠送座驾

        public static Byte purchaseHeadwear_diamond  = 56;//钻石购买头饰

        public static Byte donateHeadwear_diamond    = 57;//钻石赠送头饰

        public static Byte renewHeadwear_diamond     = 58;//钻石续费头饰

        public static Byte start_mora                = 59;//发起猜拳扣减金币

        public static Byte participate_mora          = 60;//参与猜拳扣减金币

        public static Byte mora_expires              = 61;//发起猜拳过期退还金币

        public static Byte winning_mora              = 62;//赢得猜拳退还金币

        public static Byte draw_mora                 = 63;// 猜拳平局退还金币

        public static Byte create_family_gold = 64;//创建家族扣减金币

        public static Byte family_leader_reward = 65;//家族长家族流水奖励

        public static Byte africa_country_vote = 69;//非洲杯投票

        public static Byte admin_alternative_charge = 66;//用户代充　后台充值给指定用户

        public static Byte user_alternative_charge = 67;//用户代充　指定用户充值给其它用户

        public static Byte receive_alternative_charge = 68;//用户代充　其它用户收到代充的金币

        public static Byte mission_recev_diamond   = 70;//任务收到钻石

        public static Byte mission_not_prizeType = 71;//任务礼物类型没有对应的帐单类型

        public static Byte admin_reduce_gold = 72;//后台减掉用户金币

        public static Byte sign_send_diamond = 73;//签到收到钻石

        public static Byte participate_reward_send = 74;//宰牲节活动参与奖发放

        public static Byte lucky_draw_gold = 75;//幸运7活动发放金币
        public static Byte lucky_used_gold            = 76; //

        public static Byte game_wheel_join            = 77; //参加转盘扣减金币
        public static Byte game_wheel_win             = 78; //参加转盘赢得的金币
        public static Byte game_wheel_return_cancel   = 80; //转盘过期/取消金币返回
        public static Byte game_wheel_return_start    = 81; //发起转盘,金币5%抽成奖励

        public static Byte admin_reduce_diamond = 79;//后台扣钻石

        public static Byte exchange_reduce_diamond = 82;//兑换活动换钻石
        public static Byte diamond_gift_send            = 84;//钻石礼物支出

        public static Byte diamond_activity_draw        = 85;//钻石抽奖发放金币
        public static Byte game_rocket_win              = 86; // 小火箭获得

        public static Byte uniqueID_buy                 = 87; // 购买
        public static Byte uniqueID_renew               = 88; // 续费
        public static Byte uniqueID_send                = 89; // 赠送


        public static Byte crystalGiftIncome            = 90; // 水晶收入:收礼
        public static Byte exchangeCrystalToGoldOutcome = 91; // 水晶支出:兑换金币
        public static Byte exchangeCrystalToGoldIncome  = 92; // 金币收入:水晶兑换金币
        public static Byte crystalWithdrawAdmin         = 93; // 水晶支出:admin提现


        public static Byte USER_INVITE_RECHARGE_FIRSR_PRIZE         = 94; // 用户邀请首充奖励
        public static Byte USER_INVITE_RECHARGE_PRIZE         = 95; // 用户邀请充值奖励
        public static Byte USER_INVITE_GIFT_PRIZE         = 96; // 用户邀请礼包奖励



    }

    public static final Integer personallyRelevantRoomDefNum=200;

    public static class ActiveMq {
        public static final String send_gift = "sendGift";
        public static final String sum_list = "sumList";
        public static final String MESS_FIELD_TYPE = "field_type";
        public static final String MESS_FIELD_GIFT = "field_gift";
        public static final String MESS_FIELD_STAT = "field_stat";
        public static final int MESS_TYPE_GIFT = 1; // 送礼物
        public static final int MESS_TYPE_STAT = 2; // 统计

    }


    public static class SendGold {
        public static final BigDecimal boundPhone = new BigDecimal("50"); //绑定手机送50金币
        public static final BigDecimal shareSendGold = new BigDecimal("20"); //分享人送20金币
        public static final BigDecimal followWX = new BigDecimal("20"); //关注微信公众号送20金币
    }

    public static class WithDrawStatus {
        public static final Byte deleted = 0;  //作废
        public static final Byte ing = 1; //发起提现
        public static final Byte APPROVED = 2; //提现完成
        public static final Byte reject = 3;   //驳回请求
        public static final Byte error = 4; //提现异常
    }

    public static class WithdrawAccountType {
        // 提现账号类型-支付宝
        public static final Byte ALIPAY = 1;
        // 提现账号类型-微信支付
        public static final Byte WEIXINPAY = 2;
        // 提现账号类型-银行账号
        public static final Byte BANK = 3;
    }

    public static class WithdrawApplyType {
        public static final Byte USER = 1;
        public static final Byte ADMIN = 2;
    }

    public static class WithdrawPayStatus {
        public static final String ALL = "all";
        public static final String PENDING = "pending";
        public static final String PAID = "paid";
        public static final String FAILED = "failed";
        public static final String SCHEDULED = "scheduled";
        /**
         * 批量支付部分支付成功
         **/
        public static final String PARTIALLY_SUCCEEDED = "partially_succeeded";
        /**
         * 批量支付全部支付成功
         **/
        public static final String SUCCEEDED = "succeeded";
    }

    public static class usersType {
        public static final Byte account = 1; //普通账号
        public static final Byte vipAccount = 2; //官方账号
        public static final Byte robotAccount = 3; //机器人账号
    }

    public static class ChargeProdStatus {
        public static final Byte using = 1;
        public static final Byte deleted = 2;
        public static final Byte audit = 3;
    }

    public static class FaceStatus {
        public static final Byte using = 1;
        public static final Byte deleted = 2;

    }

    public static class IOSAuditAccount {
        public static final List auditAccountList = Arrays.asList(90003762L, 90003764L, 90003769L, 90003771L);
        public static final List auditAccountRoomList = Arrays.asList(11463893L, 11472789L, 11465903L, 11477213L);
    }

    public static class ActStatus {
        public static final Byte using = 1;
        public static final Byte deleted = 2;

    }

    public static class PacketProStauts {
        public static final Byte using = 1;
        public static final Byte deleted = 2;
    }

    /**
     * 2017-10-26升级
     * 1.分享红包,前七次分享0.5到1.5，后面分享0.1到0.5
     * 2.邀请红包，前七次邀请1到3，后面邀请0.5到1，限同一设备
     * 3.分成5%,限同一设备
     */
    public static class PacketNumRandomRegion {
        public static final Double ShareLess7[] = {0.50, 1.50};
        public static final Double ShareMore7[] = {0.10, 0.50};

        public static final Double RegisterLess7[] = {1.00, 3.00};
        public static final Double RegisterMore7[] = {0.50, 1.50};

    }

    public static class PacketConst {
        public static final double fistrtPacketNum = 20.00;
        public static final double bonusRate = 0.05;
    }

    public static class RoomType {
        public static final Byte auct = 1; // 拍卖房
        public static final Byte radio = 2;// 轻聊房
        public static final Byte game = 3; // 轰趴房
        public static final Byte companion = 5; // 陪伴房

    }

    public static class RoomTagType {
        public static final int NORMAL = 1; // 普通
        public static final int NEWSHOW = 2; // 萌新
        public static final int GATHER = 3; // 聚类
        public static final int ACTIVI = 4; // 活动,
        public static final int RADIOS = 5; // 电台
        public static final int PERSON = 6;   // 个人
        public static final int HOT = 7;    //热门
        public static final int TEMP = 8; //临时兔兔首页改版(为了兼容旧版本)
        public static final int NEW_OPEN  = 13;    //7-30天新开的房间
        public static final int COUNTRY  = 14;    //国家房间
    }

    public static final Integer TAG_MENXIN_DAY = 30; // 萌新标签定义的天数
    public static final Integer ROOM_PAGE_SIZE = 10; // 房间列表的默认分页
    public static final Integer HOME_PAGE_SIZE = 12; // 首页列表的默认分页
    public static final Integer CACHE_MAX_SIZE = 180;// 缓存最大房间的数量

    public static class HomePageTag {
        public static final String hot = "1";
        public static final String radio = "2";
        public static final String game = "3";

    }

    public static class RankType {
        public static final Byte star = 1;//巨星
        public static final Byte noble = 2;//土豪
        public static final Byte room = 3;//房间
    }

    public static class RankDatetype {
        public static final Byte day = 1;
        public static final Byte week = 2;
        public static final Byte total = 3;
        public static final Byte halfHour = 4;
    }

    public static class ActRankDateType {
        public static final Byte day = 1;
        public static final Byte week = 2;
        public static final Byte month = 3;
        public static final Byte total = 4;
        public static final Byte halfHour = 5;
    }

    public static class PermitRoomType {
        public static final Byte hotType = 1;  // 热门
        public static final Byte newType = 3;  // 新秀
    }

    /**
     * 首页排行榜计算占比
     */
    public static class HomeRankCalc {
        public static final double personNumValue = 0.4;//人气值
        public static final double goldFlow = 0.6;//流水

    }

    /**
     * 抽奖记录状态
     */
    public static class DrawStatus {
        public static final Byte create = 1;
        public static final Byte nonePrize = 2;
        public static final Byte hasPrize = 3;
    }

    public static class DrawSwitch {
        public static final String open = "1";
        public static final String close = "2";
    }

    public static class DrawRecordType {
        public static final Byte charge = 1;
        public static final Byte share = 2;
        public static final Byte monster = 3;
    }

    public static class RoomMic {
        public static final String ROOM_INFO = "roomInfo";
        public static final String MIC_QUEUE = "micQueue";
        public static final String MIC_INFO = "micInfo";
        public static final String KEY_INVITEUID = "inviteUid";
        public static final String KEY_POSITION = "position";
        public static final int ROOM_NOTIFY_TYPE_ROOM = 1;     // 房间更新
        public static final int ROOM_NOTIFY_TYPE_MIC = 2;      // 坑位更新
        public static final int ROOM_NOTIFY_TYPE_ROOM_MIC = 3; //房间坑位同时更新
    }


    // Banner的类型
    public static class BannerType {
        public static final byte BANNER_TOP = 1;    // 显示在顶部的banner
        public static final byte BANNER_BOTTOM = 2; // 显示在底部的banner
        public static final byte BANNER_GAME = 3; // 显示在游戏首页的banner
        public static final byte BANNER_WELFARE = 4; // 显示在声控福利房
        public static final byte BANNER_TITLE = 5; // 显示在标题列表
        public static final byte BANNER_DISCOVERY = 6; // 显示在发现页
        public static final byte BANNER_HOME_FUNCTION = 7;//首页-功能位
    }

    public static class DrawRange {

        public static final double shareOr8[] = {0, 0.7, 1};
        public static final double charge48[] = {0, 0.4, 1};
        public static final double charge98[] = {0, 0.3, 0.8, 1};
        public static final double charge198[] = {0, 0.7, 0.9, 1};
        public static final double charge498[] = {0, 0.1, 0.7, 0.9, 1};
        public static final double charge998[] = {0, 0.4, 0.85, 0.95, 1};
        public static final double charge4998[] = {0, 0.2, 0.6, 0.8, 1};
        public static final double charge9999[] = {0, 0.2, 0.5, 0.8, 1};
        public static final double charge30000[] = {0, 0.7, 0.9, 1};
        public static final double charge60000[] = {0, 0.4, 1};
    }

    public static class DrawProd {
        public static final Integer none = 0;
        public static final Integer gold5 = 5;
        public static final Integer gold20 = 20;
        public static final Integer gold30 = 30;
        public static final Integer gold100 = 100;
        public static final Integer gold300 = 300;
        public static final Integer gold500 = 500;   // 金币500
        public static final Integer gold1000 = 1000;
        public static final Integer gold3000 = 3000;
        public static final Integer gold8888 = 8888;
        public static final Integer prettySix = 10000;//六位靓号
        public static final Integer prettyFour = 15000;//四位靓号
        public static final Integer nobleDuke = 20000; // 贵族公爵
        public static final Integer nobleKing = 25000; // 贵族国王
        public static final Integer nobleMarquis = 25500; // 贵族侯爵
        public static final Integer carFerrari = 30000;   // 座驾法拉利488
        public static final Integer carZhuang = 35000;  // 庄周座驾
        public static final Integer carMotorbike = 37000; // 摩托车座驾
        public static final Integer carQiQiu = 38000; // 告白气球
        public static final Integer carPlane = 39000; // 纸飞机
        public static final Integer nobleEmperor = 40000; // 贵族皇帝
        public static final Integer duibaPrettyNo = 16000; // 兑吧靓号
        public static final Integer gongzai = 17000;    // 耳伴公仔
        public static final Integer baozhen = 18000;   // 耳伴抱枕
        public static final Integer tumao = 19000;   // 耳伴兔帽
        public static final Integer headwearCat = 50000; // 猫咪头饰
        public static final Integer headwearLove = 50001; // 缤纷爱心头饰
        public static final Integer headwearDemon = 50002; // 小恶魔头饰
        public static final Integer headwear520 = 50003; // 520头饰
        public static final Integer headwear1314 = 50004; // 1314头饰
        public static final Integer carChicken = 30001; // 小飞鸡座驾
        public static final Integer carPorsche = 30002; // 保时捷座驾
        public static final Integer carHorse = 30003; // 天马座驾
        public static final Integer carShining = 30004; // 赤耀光辉座驾
    }

    public static class DrawPrettyErbanNoType {
        public static final Byte four = new Byte("1");
        public static final Byte six = new Byte("2");

    }

    /**
     * 支付业务的类型
     */
    public static class PayBussType {
        public static final byte charge = 0; // 充值金币
        public static final byte openNoble = 1;  // 开通贵族
        public static final byte renewNoble = 2; // 续费贵族
    }

    public static class NobleOptType {
        public static final byte open = 1;   // 开通贵族
        public static final byte renew = 2;  // 续费贵族
        public static final byte exper = 3;  // 体验贵族
        public static final byte openEmper = 4; // 开通皇帝特殊渠道（慎用）;
        public static final byte renewEmper = 5; // 续费皇帝特殊渠道（慎用）;

    }

    public static class CarOptType {
        public static final byte purchase = 1;  // 购买座驾
        public static final byte renew    = 2;  // 续费座驾
        public static final byte exper    = 3;  // 座驾赠送体验
        public static final byte give     = 4;  //座驾赠送
    }

    public static class CarComeFrom {
        // 购买
        public static final byte BUY = 1;
        // 用户赠送
        public static final byte USER_DONATE = 2;
        // 官方赠送
        public static final byte OFFICIAL_DONATE = 3;
        // 钻石购买
        public static final byte DIAMOND = 4;
    }

    /**
     * 贵族开通或者续费的支付类型，支付类型，1：金币开通，2：自己付款开通，3：打款公账开通
     */
    public static class NoblePayType {
        public static final byte gold = 1;     // 金币开通
        public static final byte money = 2;    // 付款开通
        public static final byte company = 3;  // 公账开通
        public static final byte experByOfficial = 4; // 官方赠送体验
        public static final byte experByDraw = 5; // 摇一摇中奖获得
        public static final byte iosPay = 6; // 苹果内购开通
        public static final byte diamond = 7; //钻石开通
    }

    public static class NobleResType {
        public static final byte roombg = 1;  // 房间背景
        public static final byte bubble = 2;  // 聊天气泡
        public static final byte michalo = 3; // 上麦光晕
        public static final byte headwear = 4;// 个人头饰
        public static final byte badge = 5;   // 个人勋章
        public static final byte cardbg = 6;  // 卡片背景
        public static final byte zonebg = 7;  // 主页背景

    }


    /**
     * 推送云信自定义消息的子协议
     */
    public static class DefineProtocol {

        public static final int CUSTOM_MESS_DEFINE                              = 100;   // 表示自定义消息
        public static final int CUSTOM_MESS_HEAD_QUEUE                          = 8;     // 队列

        public static final int CUSTOM_MESS_SUB_INVITE                          = 81;    // 邀请上麦
        public static final int CUSTOM_MESS_SUB_KICKIT                          = 82;    // 踢它下麦

        public static final int CUSTOM_MESS_HEAD_NOBLE                          = 14;        // 贵族
        public static final int CUSTOM_MESS_SUB_OPENNOBLE                       = 142;    // 开通贵族
        public static final int CUSTOM_MESS_SUB_RENEWNOBLE                      = 143;   // 续费贵族
        public static final int CUSTOM_MESS_SUB_WILLEXPIRE                      = 144;   // 贵族快到期
        public static final int CUSTOM_MESS_SUB_HADEXPIRE                       = 145;    // 贵族已到期
        public static final int CUSTOM_MESS_SUB_GOODNUM_OK                      = 146;   // 靓号生效
        public static final int CUSTOM_MESS_SUB_GOODNUM_NOTOK                   = 147;// 靓号未生效
        public static final int CUSTOM_MESS_SUB_ROOM_INCOME                     = 148;  // 房主分成
        public static final int CUSTOM_MESS_SUB_RECOM_ROOM                      = 149;   // 推荐房间

        public static final int CUSTOM_MESS_HEAD_CAR                            = 15;          // 座驾
        public static final int CUSTOM_MESS_SUB_CAR_EXPIRE                      = 151;   // 座驾已到期

        public static final int CUSTOM_MESS_HEAD_MONSTER                        = 17;
        public static final int CUSTOM_MESS_SUB_MONSTER_COMING                  = 171;   // 打怪开始
        public static final int CUSTOM_MESS_SUB_MONSTER_COMPLETED               = 172; // 打怪结束
        public static final int CUSTOM_MESS_SUB_MONSTER_RESULT                  = 173;   // 打怪结果
        public static final int CUSTOM_MESS_SUB_MONSTER_ATTACK                  = 174;   // 攻击怪兽

        public static final int CUSTOM_MESS_HEAD_SECRETARY                      = 19;    // 小秘书通用消息
        public static final int CUSTOM_MESS_SUB_SECRETARY_INTRACTION            = 191;   // 页面跳转交互


        public static final int CUSTOM_MESS_HEAD_FAMILY                         = 23;// 家族相关消息
        public static final int CUSTOM_MESS_SUB_FAMILY_NORMAL                   = 231; // 无操作的正常家族信息
        public static final int CUSTOM_MESS_SUB_FAMILY_CHOICE                   = 232; // 带选择操作的家族信息

        public static final int CUSTOM_MESS_HEAD_BOX_PRIZE                      = 26;    // 中奖通知
        public static final int CUSTOM_MESS_HEAD_BOX_PRIZE_SINGLE_USER          = 261;    // 单个用户公屏通知
        public static final int CUSTOM_MESS_HEAD_BOX_PRIZE_SINGLE_ROOM          = 262;    // 单个房间公屏通知
        public static final int CUSTOM_MESS_HEAD_BOX_PRIZE_ALL_ROOM             = 263;    // 所有房间公屏通知
        public static final int CUSTOM_MESS_HEAD_BOX_PRIZE_ALL_ROOM_NIUBI       = 264;    // 所有房间悬浮飘过通知

        public static final int CUSTOM_MESS_HEAD_SWITCH_MUSIC                   = 27; //切歌通知
        public static final int CUSTOM_MESS_HEAD_SWITCH_MUSIC_SINGLE_ROOM       = 274; //手动切歌/自动切歌,都发送通知
        public static final int CUSTOM_MESS_HEAD_CHOOSE_MUSIC_LIST_IS_EMPTY     = 276; //切歌时,下一首歌曲是空时,发送通知

        public static final int CUSTOM_MESS_HEAD_PUBLIC_CHATROOM                = 28; //公聊大厅
        public static final int CUSTOM_MESS_SUB_PUBLIC_CHATROOM_SEND_GIFT       = 281;//公聊大厅送礼物
        public static final int CUSTOM_MESS_SUB_PUBLIC_CHATROOM_SEND_AT         = 282;//公聊大厅@人
        public static final int CUSTOM_MESS_SUB_PUBLIC_CHATROOM_SEND_BROADCAST  = 283;//霸屏（预留字段）
        public static final int CUSTOM_MESS_SUB_PUBLIC_CHATROOM_SEND_PRIVATE_AT = 284;//私聊@人
        public static final int CUSTOM_MESS_SUB_PUBLIC_CHATROOM_SEND_ALERT      = 285;//公聊大厅反垃圾提醒消息

        public static final int CUSTOM_MESS_HEAD_MATCH_PLAYER                   = 29; //游戏匹配
        public static final int CUSTOM_MESS_HEAD_MATCH_PLAYER_ALL_OUT_ROOM      = 291;//两个用户匹配,都是房间外的
        public static final int CUSTOM_MESS_HEAD_MATCH_PLAYER_ONE_OUT_ROOM      = 292;//两个用户匹配   ,一个是房间外的
        public static final int CUSTOM_MESS_HEAD_MATCH_PLAYER_AI_OUT_ROOM       = 293;//两个用户匹配,一个是AI

        public static final int CUSTOM_MESS_HEAD_QUEUE_MICRO                    = 30; //排麦模式
        public static final int CUSTOM_MESS_SUB_QUEUE_MICRO_NON_EMPTY           = 301; //从无人排麦到有人排麦
        public static final int CUSTOM_MESS_SUB_QUEUE_MICRO_EMPTY               = 302; //从有人排麦到无人排麦
        public static final int CUSTOM_MESS_SUB_QUEUE_MICRO_MODE_OPEN           = 303; //开启排麦模式
        public static final int CUSTOM_MESS_SUB_QUEUE_MICRO_MODE_CLOSE          = 304; //关闭排麦模式
        public static final int CUSTOM_MESS_SUB_QUEUE_MICRO_FREE_MIC_OPEN       = 305; //将坑位设置为自由麦
        public static final int CUSTOM_MESS_SUB_QUEUE_MICRO_FREE_MIC_CLOSE      = 306; //将坑位设置为排麦
        public static final int CUSTOM_MESS_SUB_QUEUE_MICRO_UP_MIC              = 307; //管理员将用户抱上麦
        public static final int ACT_YEAR_RED_PACKET_REMIND                      = 308; //春节红包雨活动提醒

        public static final int CUSTOM_MESS_HEAD_ROOM_PK                        = 31; //pk模式
        public static final int CUSTOM_MESS_SUB_ROOM_PK_NON_EMPTY               = 311; //从无人报名pk排麦到有人报名pk排麦
        public static final int CUSTOM_MESS_SUB_ROOM_PK_EMPTY                   = 312; //从有人报名pk排麦到无人报名pk排麦
        public static final int CUSTOM_MESS_SUB_ROOM_PK_MODE_OPEN               = 313; //开启pk模式
        public static final int CUSTOM_MESS_SUB_ROOM_PK_MODE_CLOSE              = 314; //关闭pk模式
        public static final int CUSTOM_MESS_SUB_ROOM_PK_MODE_START              = 315; // 开始 PK
        public static final int CUSTOM_MESS_SUB_ROOM_PK_RESULT                  = 316; // pk 结果
        public static final int CUSTOM_MESS_SUB_ROOM_PK_RE_START                = 317; // 重开

        // 师徒关系
        public static final int CUSTOM_MSG_MENTORING_RELATIONSHIP                               = 34;
        public static final int CUSTOM_MSG_SUB_MENTORING_RELATIONSHIP_MISSION_ONE_MASTER        = 341; // 师父收到的任务一消息
        public static final int CUSTOM_MSG_SUB_MENTORING_RELATIONSHIP_MISSION_ONE_APPRENTICE    = 342; // 徒弟收到的任务一消息
        public static final int CUSTOM_MSG_SUB_MENTORING_RELATIONSHIP_MISSION_ONE_TIPS          = 343; // 徒弟完成任务一的提示消息
        public static final int CUSTOM_MSG_SUB_MENTORING_RELATIONSHIP_MISSION_TWO_MASTER        = 344; // 师父收到的任务二消息
        public static final int CUSTOM_MSG_SUB_MENTORING_RELATIONSHIP_MISSION_TWO_APPRENTICE    = 345; // 徒弟收到的任务二消息
        public static final int CUSTOM_MSG_SUB_MENTORING_RELATIONSHIP_MISSION_THREE_MASTER      = 346; // 师父收到的任务三消息
        public static final int CUSTOM_MSG_SUB_MENTORING_RELATIONSHIP_MISSION_THREE_APPRENTICE  = 347; // 徒弟收到的任务三消息
        public static final int CUSTOM_MSG_SUB_MENTORING_RELATIONSHIP_MISSION_FOUR_MASTER       = 348; // 师父收到的任务四消息
        public static final int CUSTOM_MSG_SUB_MENTORING_RELATIONSHIP_MISSION_FOUR_APPRENTICE   = 349; // 徒弟收到的任务四消息
        public static final int CUSTOM_MSG_SUB_MENTORING_RELATIONSHIP_MISSION_RESULT            = 3410; // 任务成功提示
        public static final int CUSTOM_MSG_SUB_MENTORING_RELATIONSHIP_MISSION_ONE_MASTER_TIPS   = 3411; // 师父完成任务一的提示消息
        public static final int CUSTOM_MSG_SUB_MENTORING_RELATIONSHIP_MISSION_FAIL_TIPS         = 3412; // 任务失败提示

        // 签到提醒
        public static final int SIGN_IN_REMIND_HEAD                             = 41;
        public static final int SIGN_IN_REMIND_SUB_SKIP                         = 411; // 跳签到页
        public static final int SIGN_DRAW_GOLD_ALL_ROOM                         = 412;    // 瓜分金币所有房间公屏通知
        public static final int SIGN_DRAW_GOLD_ALL_USER                         = 413;    // 瓜分金币所有用户小秘书通知


        public static final int CUSTOM_MSG_GIFT_NOTIFICATION              = 42; // 礼物全服通知
        public static final int CUSTOM_MSG_GIFT_NOTIFICATION_COMMON       = 421;// 普通礼物全服通知
    }

    /**
     * 云信自定义系统通知toast类型
     */
    public static class NimAttachType{

        //云信默认的toast
        public static final int DEFUALT = 1;

        //师徒关系的toast
        public static final int MENTORING = 2;
    }

    public static class status {
        public static final Byte delete = 0; //删除
        public static final Byte valid = 1; //有效
        public static final Byte invalid = 2; //无效
    }

    public static class nobleStatus {
        public static final Byte valid = 1; // 有效
        public static final Byte invalid = 0; // 无效
    }

    public static String DEFAULT_NICK = "New User";
    public static String DEFAULT_HEAD = "https://img.scarllet.cn/default_head.png";
    public static String DEFAULT_CAR = "https://img.erbanyy.com/default_car.png";
    public static String DEFAULT_LOGO = "https://img.erbanyy.com/logo.png";
    public static String MASK_USER_IMG = "https://img.erbanyy.com/maskUser.png";
    //新秀之星角标
    public static String NEW_SHOW_STAR = "https://img.erbanyy.com/new_star.png";

    public static String OLD_DEFAULT_HEAD_1 = "https://img.erbanyy.com/tutu_default_avatar.png";
    public static String OLD_DEFAULT_HEAD_2 = "https://img.erbanyy.com/FsAUM98tFPpL4ip4kJHsJrH5hNnv?imageslim";

    /**
     * 用户等级区间
     */
    public static class LevelRange {

    }

    /**
     * 车库中车的状态
     */
    public static class CarportStatus {
        public static final Byte invalid = 1; //下架
        public static final Byte expire = 2;  //过期
        public static final Byte valid = 3;   //有效
    }

    /**
     * 车库中车使用状态
     */
    public static class CarportUsed {
        public static final Byte IN_USED = 1;
        public static final Byte NOT_IN_USED = 0;
    }

    /**
     * 商城中车的状态
     */
    public static class CarGoodsEnable {
        // 下架
        public static final Byte DISABLE = 1;
        // 上架
        public static final Byte ENABLE = 2;
    }

    /**
     * 兑吧订单状态
     */
    public static class ActDrawRecordStatus {
        public static final Byte EXCHANGEPRIMARY = 0; // 初始兑换状态
        public static final Byte EXCHANGE        = 1; // 兑换成功
        public static final Byte EXCHANGEFAIL    = 2; // 兑换失败
        public static final Byte WIN             = 3; // 中奖
        public static final Byte FAILED          = 4; // 未中奖
    }

    public static class ActDrawRecordHasGiveOut {
        public static final Byte HASGIVEOUT = 2; // 已发放礼物
        public static final Byte NOTGIVEOUT = 1; // 没发放礼物
    }

    public static class BusinessType {
        public static final String OFFICIAL_GOLD          = "buss_official_gold"; // 官方赠送加金币
        public static final String CHARGE_GOLD            = "buss_charge_gold";     // 充值加金币
        public static final String DRAW_GOLD              = "buss_draw_gold";         // 抽奖加金币
        public static final String REDEEMCODE_GOLD        = "buss_redeemcode_gold";   // 兑换码加金币
        public static final String RETURN_NOBLE_GOLD      = "buss_returnnoble_gold";// 返还贵族金币
        public static final String ROOM_NOBLE_GOLD        = "buss_roomnoble_gold";    // 贵族开通房主分成加金币
        public static final String FOLLOW_PUB_GOLD        = "buss_follow_pub_gold";  // 关注公众号加金币

        public static final String SENDGIFT_GOLD          = "buss_sendgift_gold";      // 送礼物减金币
        public static final String OPEN_NOBLE_GOLD        = "buss_opennoble_gold";   // 开通贵族减金币
        public static final String RENEW_NOBLE_GOLD       = "buss_renewnoble_gold"; // 续费贵族减金币
        public static final String FEEDBACK_DIAMOND       = "buss_feedback_diamond";// 回馈加钻石
        public static final String WITHDRAW_DIAMOND       = "buss_withdraw_diamond";// 提现减钻石
        public static final String SENDMAGIC_GOLD         = "buss_sendmagic_gold";  // 发送魔法表情减金币
        public static final String MONSTER_DRAW_GOLD      = "buss_monster_gold"; //打怪夺宝奖励金币
        public static final String BUY_ACTIVITY_PACK_GOLD = "buy_activity_pack_gold"; //购买活动礼包减金币

        public static final String EGG_PRIZE_GOLD         = "buss_egg_prize_gold";  // 砸金蛋赠送金币
        public static final String RECEIVE_GIFT_DIAMOND   = "buss_receive_gift_diamond"; // 收到礼物钻石
        public static final String RECEIVE_GIFT_GOLD_DIAMOND   = "buss_receive_gift_gold_diamond"; // 收到普通礼物加0.3金币和0.4钻石

        public static final String SIGN_DRAW_GOLD         = "sign_draw_gold";  // 签到瓜分赠送金币

        public static final String BACKGROUND_GOLD        = "buss_background_gold";    // 购买背景扣减金币
        public static final String GAME_NUMBER_GOLD       = "buss_game_number_gold";   // 数字游戏扣减金币
        public static final String BUY_CAR_DIAMOND            = "buss_buy_car_diamond";   // 购买钻石座驾扣减钻石
        public static final String RENEW_CAR_DIAMOND          = "buss_renew_car_diamond"; // 续费钻石座驾扣减钻石
        public static final String SEND_CAR_DIAMOND           = "buss_send_car_diamond";  // 赠送钻石座驾扣减钻石
        public static final String BUY_HEADWEAR_DIAMOND       = "buss_buy_headwear_diamond";   // 购买钻石头饰扣减钻石
        public static final String RENEW_HEADWEAR_DIAMOND     = "buss_renew_headwear_diamond"; // 续费钻石头饰扣减钻石
        public static final String SEND_HEADWEAR_DIAMOND      = "buss_send_headwear_diamond";  // 赠送钻石头饰扣减钻石

        public static final String MISSON_RECV_SEND_DIAMOND      = "mission_recive_send_diamond";  //领取任务奖励赠送钻石
        public static final String FINGER_PK_START            = "buss_finger_pk_start";    // 发起PK
        public static final String FINGER_PK_JOIN             = "buss_finger_pk_join";     // 加入PK
        public static final String WHEEL_GAME_JOIN            = "buss_wheel_game_join";    // 加入转盘扣减金币
        public static final String WHEEL_GAME_WIN             = "buss_wheel_game_win";     // 转盘胜利获得金币
        public static final String WHEEL_GAME_RETURN          = "buss_wheel_game_return";  // 转盘过期或取消返还金币
        public static final String DIAMOND_GIFT_SEND          = "buss_diamond_gift_send";  // 转盘过期或取消返还金币

        public static final String GAME_ROCKET_PRIZE_GOLD         = "game_rocket_prize_gold";  // 小火箭游戏送金币

        public static final String USER_INVITE_RECHARGE_PRIZE_GOLD = "user_invite_recharge_prize_gold";  // 用户邀请充值金币奖励
    }

    public static class MonsterStatus {
        //未出现
        public static final Byte NOT_APPEAR = 1;
        //进行中
        public static final Byte IN_PROGRESS = 2;
        //已打爆
        public static final Byte DEFEATED = 3;
        //已逃跑
        public static final Byte ESCAPED = 4;
    }

    public static class MonsterNotifyType {
        // 全服
        public static final Byte ALL = 1;
        // 广播
        public static final Byte BROADCASt = 2;
        // 房间
        public static final Byte ROOM = 3;
    }

    public static final String EMPTY_JSON = "{}";

    public static class MonsterTaskCode {
        // 提前3分钟通知
        public static final String AHEAD_NOTIF_THREE_MINUTES = "ahead_notify_3_minutes";
        // 提前15秒通知
        public static final String AHEAD_NOTIFY_FIFTEEN_SECONDS = "ahead_notify_15_seconds";
        // 出现通知
        public static final String APPEAR_NOTIFY = "appear_notify";
        // 逃跑通知
        public static final String ESCAPE_NOTIFY = "escape_notify";
    }

    public static class MonsterTaskStatus {
        //未开始
        public static final Byte NOT_START = 1;
        //执行成功
        public static final Byte COMPLETED = 2;
        //执行失败
        public static final Byte FAILED = 3;
    }

    public static class GiftReceiverType {
        /**
         * 礼物/魔法接收方类型： 用户
         */
        public static final Byte USER = 1;

        /**
         * 礼物/魔法接收方类型： 怪兽
         */
        public static final Byte MONSTER = 2;
    }

    // 奖品类型
    public static class UserDrawType {
        public static final Byte GOLD = 1;  // 金币
        public static final Byte PRETTY_NO = 2;    // 靓号
        public static final Byte CAR = 3;   //座驾
        public static final Byte ENTITY = 4;    //实物
    }

    public static class DrawExchange {
        public static final Byte YES = 2;
        public static final Byte NO = 1;
    }

    // 小秘书消息跳转类型
    public static class SecretarySkipType {
        // 房间
        public static final Byte ROOM = 1;
        // H5页面
        public static final Byte H5 = 2;
        // 钱包页
        public static final Byte PURSE = 3;
        // 红包
        public static final Byte RED_PACKET = 4;
        // 充值页
        public static final Byte RECHARGE = 5;
        // 个人页
        public static final Byte PERSON = 6;
        // 座驾
        public static final Byte CAR = 7;
        // 头饰
        public static final Byte HEADWEAR = 8;
        // 背景
        public static final Byte BACKGROUND = 9;
        // 升级
        public static final Byte LEVEL = 10;
        //公聊大厅
        public static final Byte PUBLIC_CHATROOM = 15;

        //模厅
        public static final Byte HALL = 22;
        // 推荐卡
        public static final Byte RECOMMEND_CARD = 20;
        // 靓号背包
        public static final Byte UNIQUEID = 31;
    }

    // 小秘书消息跳转值
    public static class SecretarySkipValue {
        // 座驾
        public static final Byte CAR = 0;
        // 头饰
        public static final Byte HEADWEAR = 1;
    }

    // 操作系统类型
    public static class OsType {
        public static final String IOS = "ios";
        public static final String ANDROID = "android";
        public static final String ALL = "all";
    }

    public static class RankingType {
        public static final String DAY = "day";
        public static final String WEEK = "week";
        public static final String TOTAL = "total";
    }

    // 房间类型
    public static class PermitType {
        public static final Byte PERMIT_ROOM = 1;   // 牌照房
        public static final Byte NO_PERMIT_ROOM = 2;    // 非牌照房
        public static final Byte NEW_SHOW_ROOM = 3; // 新秀房
    }

    public static final Integer personallyRelevantRoomShowNum = 3;

    // 怪兽抽奖策略
    public static class MonsterDrawStrategy {
        public static final Byte RANDOM = 1;
        public static final Byte TOP_ONE = 2;
    }

    public static final Integer DEFAULT_PAGE = 1;
    public static final Integer DEFAULT_PAGE_SIZE = 20;


    public static class NobleRankHide {
        public static final Byte HIDE = 1;
        public static final Byte DISPLAY = 0;
    }


    public static final Integer DEVICEID_LIMIT_TIMES = 8; //支付宝提现绑定时 发短信的次数 限制设备id

    public static final Integer USER_CERTIFY_LIMIT_TIMES = 10; //用户实名认证验证码限制

    public static final Integer TOP_LINE_LEN = 37;// 头条后台设置的长度

    // 全服通知触发类型
    public static class NotifyTriggerType {
        public static final int GOLD_TRIGGER = 1;   //金额触发
        public static final int GIFT_TRIGGER = 2;   //指定礼物触发
    }

    public static final String IOS = "IOS"; //苹果系统


    public static final int SMS_MAX = 1000;// 特殊模式下的发送短信的最大条数

    public static class DefUser {
        // 普通用户
        public static final Byte NORMAL = 1;
        // 官方账号
        public static final Byte OFFICIAL = 2;
        // 机器账号
        public static final Byte ROBOT = 3;
        // 工会账号
        public static final Byte LABOR_UNION = 4;
    }

    public static final Integer FAMILY_LIST_PAGE = 1; //家族查询默认页
    public static final Integer FAMILY_LIST_PAGE_SIZE = 10; //家族查询默认大小

    public static final int FAMILY_LEADER = 10;// 族长职位id

    public static final int FAMILY_NOMORL = 0;// 家族普通成员职位id

    public static final long FAMILY_REJECT_TIME = 3; //每个家族每日可被拒绝次数

    public static final long GROUP_REJECT_TIME = 3;//每个群聊每日可拒绝次数

    public static final long FAMILY_QUIT_LIMIT_TIME = 7 * 24 * 3600 * 1000;//离开家族 需要满足的时长

    public static final long FAMILY_INVITE_EXPIRE_TIME = 15 * 24 * 3600 * 1000;//邀请过期时间

    public static final float FAMILY_RED_PACKGE_RATE = 0.05f; //家族红包手续费率

    public static final float FAMILY_GAME_RATE = 0.01f;//家族游戏支出手续费率

    public static final long RED_PACKET_EXPIRE = 24 * 3600 * 1000; //红包存在时间
    public static final float RED_PACKGE_MIN_AMOUNT_PER = 0.01f;
    //最小交易金额
    public static final double MINIMUM_TRADE_MONEY = 0.01D;
    public static final int RED_PACKGE_COUNT_MAX = 500;
    public static final int RED_PACKGE_COUNT_MIN = 1;
    public static final int QUERY_MAX = 30;//模糊查询的只查询30条

    public static final Integer TRUE = 1;
    public static final Integer FALSE = 0;


    public static final int BED_INIT = 10; // 娃娃机 初始床位

    public static final int FREE_TIMES_INIT = 3; // 娃娃机 每日免费次数


    public static final double CATCH_RATE = 0.8;   //抓取娃娃概率

    public static class ChatMemberRole {
        public static final Byte CHAT_ROLE_MASTER = 1;  // 群主
        public static final Byte CHAT_ROLE_MANAGER = 2; // 管理员
        public static final Byte CHAT_ROLE_NORMAL = 3;  // 普通成员
    }

    public static final String EMPTY_MSG_DEAL = "熬过异地，就是一生";//广播消息为时自动添加默认信息

    /**
     * 熔断服务名称
     */
    public static class StopService {
        public static final String USER_SEARCH = "user_search";
        public static final String ROOM_SEARCH = "room_search";

        public static final String SEVICE_ON = "on";
        public static final String SERVICE_OFF = "off";
    }

    public static final String[] FAMILY_MONEY_SOURCE = {"", "lucky_box_group", "create_rewards", "family_rewards", "contribution", "transfer", "lucky_box_group", "games", "system_deduction", "lucky_box_returned"};

    public static final String[] FAMILY_MONEY_OPRATION = {"", "来自", "来自", "来自", "来自", "来自", "来自", "", "来自", "来自"};

    public static final String[] FAMILY_MONEY_OPRATION_TARGET = {"", "给", "来自", "来自", "给", "给", "给", "", "来自", "来自"};

    public static final byte APPLY_FAMILY = 1;// 申请家族

    public static final byte APPLY_GROUP = 2;// 申请群聊

    public static final byte APPLY_FAMILY_AND_GROUP = 3;// 申请家族并且申请群

    public static final String APPLY_FAMILY_DEFAULT_CONTENT = "hope_join_family";//我希望加入贵家族

    public static final byte EXPIRE = 1;// 失效

    public static final byte REJECT = 2;// 拒绝

    public static final byte AGREE = 3;// 同意

    //家族广场展示家族数量
    public static final Integer FAMILY_CHARM_RANK_NUM = 6;

    public static final String DEFAULT_SYSTEM_AVATAR = "https://img.erbanyy.com/default_system_avatar.png";

    public static final String RED_PACKET_MESSAGE = "恭喜发财，大吉大利";

    // 官方账号
    public static class OfficialAccount {
        public static final Long SECRETARY = Long.parseLong(PropertyUtil.getProperty("SECRETARY_UID"));
        public static final Long SYSTEM = Long.parseLong(PropertyUtil.getProperty("SYSTEM_MESSAGE_UID"));
    }

    // 用户性别
    public static class UserGender {
        public static final Byte MALE = 1;
        public static final Byte FEMALE = 2;
    }

    //开箱子常量
    public static class OpenBoxConstant {

        /**
         * 谢谢参与奖品id`
         */
        public static final int DEFAULT_THANKS_PRIZE_ID = 0;

        /**
         * 抽奖记录，查询前多少条中奖记录
         */
        public static final int DRAW_LOTTERY_RECORD_LIMIT_NUM = 300;

        /**
         * 奖品概率总值，10万，这样可以是得概率百分比，精确到后面3位小数
         */
        public static final int MAX_OCCUPATION_RATIO = 100000;
    }

    public static class SearchType {
        public static Byte total = 1;
        public static Byte detail = 2;
    }

    public static class Source {
        public static Byte msNo = 1;
        public static Byte distance = 2;
    }


    /**
     * 礼物的来源，1:普通，2:背包，2:师徒
     */
    public static class GiftSource {
        public static final int COMMON    = 1;
        public static final int BACKPACK  = 2;
        public static final int MENTORING = 3;
        public static final int MAGIC     = 4;//魔法礼物
        public static final int FINGER    = 5;//猜拳礼物
    }

    // 账户登录类型
    public static class AccountType {
        public static Byte wx_account     = 1;  //微信账户
        public static Byte qq_account     = 2;  //qq账户
        public static Byte erban_account  = 3;  //萌声号或手机号
        public static Byte fb_account     = 4;  //facebook
        public static Byte google_account = 5;  //google+
        public static Byte ins_account    = 6;  //instagram
        public static Byte twi_account    = 7;  //twitter
        public static Byte phone_account  = 8;  //手机号码登录(用户发送短信)
        public static Byte apple_account  = 9;  //appleID登录
    }

    //后起之秀房间
    public static final Byte RISE_STAR_ROOM = 1;

    // 流失用户回归类型
    public static class ReturnType {
        public static Byte OWN_RETURN = 1;  //自回归
        public static Byte INVITE_RETURN = 2;  // 邀请回归
    }

    // 邀请类型
    public static class InviteType {
        public static Byte NEW_USER = 1;  //新用户
        public static Byte LOST_USER = 2;  // 流失用户
    }

    // 邀请类型
    public static class AwardSource {
        public static Byte INVITE_USER_RETURN = 1;  //邀请用户回归
    }

    public static class KTVRoomListOrderType {
        public static final int THE_LATEST = 1; //最新
        public static final int THE_HOTTEST = 2; //最热
    }

    //elasticsearch index 常量
    public static class ElasticsearchIndex {
        // KTV音乐曲库索引
        public static final String MUSIC_LIBRARY = "tutu_music_library";
        // 共享音乐曲库索引
        public static final String PUBLIC_MUSIC = "tutu_public_music";
    }

    //elasticsearch type 常量
    public static class ElasticsearchType {
        public static final String MV = "mv";
        public static final String MUSIC = "music";
    }

    /**
     * 公聊大厅参数
     */
    public static class PublicChatRoom {
        public static final Long PUB_CHATROOM_ID = 57147316L;

    }

    /**
     * 聊天室消息类型
     */
    public static class ChatRoomMsgType {
        /**
         * 文本消息
         **/
        public static final String TEXT = "TEXT";
        /**
         * 图片消息
         **/
        public static final String PICTURE = "PICTURE";
        /**
         * 音频消息
         **/
        public static final String AUDIO = "AUDIO";
        /**
         * 视频消息
         **/
        public static final String VIDEO = "VIDEO";
        /**
         * 位置
         **/
        public static final String LOCATION = "LOCATION";
        /**
         * 通知
         **/
        public static final String NOTIFICATION = "NOTIFICATION";
        /**
         * 文件消息
         **/
        public static final String FILE = "FILE";
        /**
         * 网络电话音频聊天
         **/
        public static final String NETCALL_AUDIO = "NETCALL_AUDIO";
        /**
         * 网络电话视频聊天
         **/
        public static final String NETCALL_VEDIO = "NETCALL_VEDIO";
        /**
         * 新的数据通道请求通知
         **/
        public static final String DATATUNNEL_NEW = "DATATUNNEL_NEW";
        /**
         * 提示类型消息
         **/
        public static final String TIPS = "TIPS";
        /**
         * 自定义消息
         **/
        public static final String CUSTOM = "CUSTOM";
    }

    /**
     * 服务端存储的公聊大厅自定义消息类型
     */
    public static class MsgTypeDef {
        /**
         * 普通文本消息
         **/
        public static final Integer COMMON_MSG = 1;
        /**
         * 送礼物消息
         **/
        public static final Integer GIFT_MSG = 2;
        /**
         * 霸屏消息
         **/
        public static final Integer BROADCAST_MSG = 3;
        /**
         * 公聊大厅@ 人的消息
         **/
        public static final Integer AT_MSG = 4;
    }

    /**
     * 公聊大厅消息查询类型
     **/
    public static class SearchMsgType {

        /**
         * @ 我的
         **/
        public static final Integer AT_ME = 1;
        /**
         * 我发送的
         **/
        public static final Integer SELF_SEND = 2;
    }

    //钥匙记录类型
    public static class PrizeKeyRecordType {
        //购买钥匙
        public static Byte BUY_KEY = 1;
        //使用钥匙
        public static Byte USE_KEY = 2;
        //钻石兑换金币送钥匙
        public static Byte EXCHG_SEND_KEY = 3;
        //活动送钥匙
        public static Byte ACT_SEND_KEY = 4;
        //购买大礼包
        public static Byte ACTIVITY_PACK = 5;
    }


    /**
     * 年终盛典榜单类型
     */
    public static class AnnualRankType {
        public static final Byte WARMUP = 1;  // 热身赛
        public static final Byte PROMOTION = 2;  // 晋级赛
        public static final Byte FINAl = 3;  // 总决赛
    }

    /**
     * 年度榜单数量
     */
    public static class AnnualRankCount {

        //年度决赛榜单展示前三十
        public static final Long FINAL_COUNT = 30L;
    }

    public static class IOSChargeLimit {

        /**
         * IOS内购限制一个设备每天只能充值10次
         **/
        public static Integer DEVICE_LIMITE_COUNT = 10;

        /**
         * IOS内购限制一个IP每天只能充值10次
         **/
        public static Integer CLIENTIP_LIMITE_COUNT = 10;

    }

    /**
     * IOS内购开通贵族赠送10个贵族币
     **/
    public static final BigDecimal IOS_OPEN_NOBLE_RETURN = BigDecimal.TEN;

    public static class FaceJsonConfig {
        public static String FACE_JSON_KEY = "1ea53d260ecf11e7b56e00163e046a26";
    }

    /**
     * 用户实名认证
     **/
    /**
     * 默认的排麦队列大小
     **/
    public static final Integer DEFUALT_MIRCO_QUEUE_SIZE = 20;

    /**
     * 默认的pk排麦队列大小
     **/
    public static final Integer DEFUALT_PK_MIRCO_QUEUE_SIZE = 20;

    /**
     * 房间玩法类型
     */
    public static class RoomModeType {
        public static final Integer NORMAL_MODE = 0; //普通模式
        public static final Integer OPEN_MICRO_MODE = 1; //开启排麦模式
        public static final Integer CLOSE_MICRO_MODE = 2; //关闭排麦模式
        public static final Integer OPEN_PK_MODE = 3;//开启PK模式
        public static final Integer CLOSE_PK_MODE = 4;//开启PK模式
    }

    /**
     * 用户实名认证
     **/
    public static class usersCertification {

        /**
         * 充值金额大于2000元需要进行实名认证
         **/
        public static final Long USER_CERTIFICATION_MONEY = 2000L;

        /**
         * 充值金额大于20000金币需要进行实名认证
         **/
        public static final BigDecimal USER_CERTIFICATION_GOLD =  new BigDecimal("20000");

        /**
         * 一个身份证号只能绑定五个账号
         **/
        public static final Integer ID_CARD_BIND_USER_LIMIT = 5;

        public static final String APP_KEY = PropertyUtil.getProperty("cert_app_key");

        public static final String APP_SECRET = PropertyUtil.getProperty("cert_app_secret");

        public static final String APP_CODE = PropertyUtil.getProperty("cert_app_code");

        public static final String USER_ID = PropertyUtil.getProperty("ali_user_id");

        public static final String VERIFY_KEY = PropertyUtil.getProperty("verify_key");

        public static final String CALL_BACK = PropertyUtil.getProperty("cert_call_back");

    }

    public static class ChargeRecordSearchType {
        public static final Byte CHARGE_ID = 1;
        public static final Byte PERSONAL = 2;
    }

    /**
     * 房间PK投票类型
     */
    public static class RoomPKVoteType {
        //按礼物价值统计
        public static final byte GIFT = 1;
        //按送礼人数统计
        public static final byte PERSON = 2;
    }

    /**
     * 房间PK模式队伍类型
     */
    public static class RoomPKTeamType {
        //队伍1 (蓝队)
        public static final byte BULE_TEAM = 1;

        //队伍2 (红队)
        public static final byte RED_TEAM = 2;

    }

    public static final Byte[] PK_TEAMS = {1, 2};

    /**
     * 房间PK状态
     */
    public static class RoomPKStatus {
        //尚未开始
        public static final byte NOT_BEGIN = 1;
        //正在进行中
        public static final byte BEGINING = 2;
        //已经结束
        public static final byte FINISH = 3;
        //强制结束
        public static final byte FORCE_FINISH = 4;
    }

    public static class PushMsgType {
        public final static int special = 0; // 特定用户
        public final static int all = 1;     // 全部用户
    }

    public static class ActYearRedPacketPrizeRange {

        public static final double greetingRange[] = {0, 0.02, 0.04, 0.06, 0.07, 0.09, 0.11, 0.13, 0.15, 0.19, 0.21};
        public static final double goldRange[] = {0.21, 0.21838, 0.21938, 0.21993, 0.21998, 0.22};
        public static final double carRange[] = {0.22, 0.35, 0.47, 0.55, 0.63};
        public static final double headWearRange[] = {0.63, 0.69, 0.80, 0.92, 1};

    }

    public static class ActYearRedPacketPrize {

        public static final String greeting[] = {"爱你依旧", "诸事如意", "猪年大吉", "猪事顺利", "猪你有钱", "猪你健康", "福气多多", "财源滚滚", "告别单身", "颜值爆表"};
        public static final Integer gold[] = {1, 5, 8, 66, 188};
        public static final Integer car[] = {8, 19, 20, 12};
        public static final Integer head[] = {17, 22, 21, 23};
        public static final Integer awardId = 18;
    }

    /**
     * 兑换奖品类型 1礼物，2头饰，3座驾, 4背景
     */
    public static class ExchangeType {
        public static final byte GIFT = 1;
        public static final byte HEADWEAR = 2;
        public static final byte CAR = 3;
        public static final byte BACKGROUND = 4;
    }

    // 奖励类型
    public static class AwardType {
        public static Byte HEADWEAR = 1;  //头饰
        public static Byte CAR = 2;  //座驾
        public static Byte GIFT = 3;  //礼物
    }

    /**
     * 师徒
     */
    public static class MasterApprentice {
        public final static String EXPER_LEVEL_SEQ = "apprentice_exper_level_seq";        // 收徒等级要求
        public final static String COUNT_UPPER_LIMIT = "apprentice_count_upper_limit";    // 当天收徒数上限
        public final static String WAIT_MINUTE = "apprentice_wait_minute";                // 去收徒等待时长    分钟
        public final static String NEW_USER_LIMIT_DAY = "apprentice_new_user_limit_day";  // 被收徒新人注册时间限制
        public final static String UNBIND_LIMIT_DAY = "apprentice_unbind_limit_day";      // 解除师徒关系天数限制
        public final static String ANDROID_APP_VERSION = "apprentice_android_app_version";   // 安卓最低版本
        public final static String IOS_APP_VERSION = "apprentice_ios_app_version";       // ios最低版本
        public final static int MISSION_ONE = 1;                    // 任务1
        public final static int MISSION_TWO = 2;                    // 任务2
        public final static int MISSION_THREE = 3;                  // 任务3
        public final static int MISSION_FOUR = 4;                   // 任务4
    }

    /**
     * 在线状态
     */
    public static class OnlineStatus {
        public final static int OFFLINE = 0;// 离线
        public final static int ONLINE = 1;// 在线
    }

    /**
     * 会话类型
     */
    public static class ConversationType {
        public final static String PERSON = "PERSON";                  // 二人会话数据
        public final static String TEAM = "TEAM";                      // 群聊数据
        public final static String CUSTOM_PERSON = "CUSTOM_PERSON";    // 个人自定义系统通知
        public final static String CUSTOM_TEAM = "CUSTOM_TEAM";        // 群组自定义系统通知
    }

    /**
     * 模厅使用状态
     */
    public static class HallStatus {
        public static final Byte DISBAND = 0;//解散
        public static final Byte USE = 1;//使用中
    }

    /**
     * 模厅成员角色类型
     */
    public static class HallRoleType {
        public static final Byte OWNER = 1;//厅主
        public static final Byte MANAGER = 2;//高管
        public static final Byte NORMAL = 3;//普通成员
    }

    /**
     * 模厅成员状态
     */
    public static class HallMemberStatus {
        public static final Byte NOT_EXIST = 0;//离开
        public static final Byte EXIST = 1;//存在
    }

    public static class HallMember {
        public static final int MAX_COUNT = 100;//模厅成员数量
        public static final int LIMIT_TIME = 60 * 60 * 24 * 7;//离开模厅重新申请 需要满足的时长
        public static final int MANAGER_MAX_COUNT = 5;//模厅高管数量
        public static final int INVITE_CODE_TIME = 60 * 60 * 24 * 7;//暗号邀请码失效时长


    }

    /**
     * 模厅操作类型
     */
    public static class HallOperateType {
        public static final Byte INVITE_USER = 1;//邀请成员
        public static final Byte REMOVE_USER = 2;//移除成员
        public static final Byte ADD_MANAGER = 3;//添加高管
        public static final Byte APPLY_HALL = 4;//申请加入
        public static final Byte QUIT_HALL = 5;//退出模厅
        public static final Byte REMOVE_MANAGER = 6;//移除高管
        public static final Byte INVITE_BY_CODE = 7;//通过暗号邀请码进入模厅

    }

    /**
     * 模厅操作状态
     */
    public static class HallOperateStatus {

        public static final Byte NOT_DEAL = 1;//未处理

        public static final Byte AGREE = 2;//同意

        public static final Byte REJECT = 3;//拒绝

        public static final Byte EXPIRE = 4;//过期

    }

    /**
     * 模厅操作状态
     */
    public static class HallApproveType {
        public static final int REJECT = 0;//拒绝

        public static final int AGREE = 1;//同意
    }

    public static class HallAuthType {
        public final static byte RESOURCE = 0; //资源权限,如删除成员按钮
        public final static byte MENU = 1; // 菜单权限,如收入统计功能
        public final static byte RESOURCE_MENU = 2; // 菜单权限,如添加成员
    }

    public static class HallAuthRoleType {
        public final static byte OWNER = 1; //厅主
        public final static byte MANAGER = 2; //高管
        public final static byte OWNER_MANAGER = 3; //厅主高管通用
        public final static byte MANAGER_NORMAL = 4;//高管成员通用
    }

    public static class HallAuthCode {
        /**
         * 添加成员
         * 审批用户加入申请
         */
        public static final String MEMBER_JOIN_MANAGER = "member_join_manager";
        /**
         * 移除成员
         * 审批成员退出申请
         */
        public static final String MEMBER_EXIT_MANAGER = "member_exit_manager";
        /**
         * 查看收入统计
         */
        public static final String LOOK_HALL_INCOME = "look_hall_income";

        /**
         * 高管设置
         */
        public static final String HALL_MANAGER_SET = "hall_manager_set";
        /**
         * 退出模厅
         */
        public static final String HALL_EXIT = "hall_exit";

        /**
         * 施工中
         */
        public static final String HALL_BUILD = "hall_build";
    }

    /**
     * 模厅群聊限制
     */
    public static class HallGroupLimit {

        public static final int MAX_PUBLIC_CHAT = 1; //可创建公开群聊的最大数量

        public static final int MAX_PRIVATE_CHAT = 5; //可创建内部群聊的最大数量

        public static final int MAX_MEMBER = 100;//群聊最大人数

        public static final int MAX_MANAGER = 5;//群聊管理员最大人数

    }


    /**
     * 模厅群聊类型
     */
    public static class HallGroupType {

        public static final byte PUBLIC_CHAT = 1; //公开群聊

        public static final byte PRIVATE_CHAT = 2; //内部群聊

    }

    public static class HallChatMemberRole {
        public static final Byte CHAT_OWNER = 1;  // 群主
        public static final Byte CHAT_MANAGER = 2; // 管理员
        public static final Byte CHAT_NORMAL = 3;  // 普通成员
    }

    /**
     * 送礼物对象类型
     */
    public static class GiftSendRecordSendEnv {
        public static final Byte ENV_ROOM = 1;  // 轻聊房间、竞拍房间给主播直接刷礼物
        public static final Byte ENV_PERSONAL = 2; // 私聊送个人礼物
    }

    public static class HallAuthStatus {
        /**
         * 没有这个权限,当请求校验权限时,根据Code在缓存查不到时,会去查DB,DB也查不到时,会先将此权限设为0存在Redis,避免每次都查数据库
         */
        public static final Byte AUTH_NULL = 0;
        public static final Byte AUTH_EXISTS = 1; // 有这个权限
    }

    public static class HallAuthIsNeedSet {
        public static final Byte NOT = 0;
        public static final Byte YES = 1;
    }


    public static class HallMessageUrl {
        public static final String DEAL_APPLY = "/hall/dealApply?recordId=";
        public static final String DEAL_QUIT = "/hall/dealQuit?recordId=";
        public static final String DEAL_INVITE = "/hall/dealInvite?recordId=";
    }


    public static class HallKeySaveDate {
        public static final int hall_manage_auth = 60 * 60 * 24 * 7;
        public static final int hall_member_menus = 60 * 30;
        public static final int hall_member_auths = 60 * 30;

        public static final int hall_income_list = 60 * 60 * 24;
        public static final int hall_income_list_min = 60 * 5;
        public static final int hall_income_member = 60 * 60 * 24;
        public static final int hall_income_gift_list = 60 * 60 * 24;
        public static final int hall_income_gift_detail = 60 * 60 * 24;

        public static final int hall_auth = 60 * 60 * 24 * 7;
    }

    /**
     * 群聊类型
     */
    public static class GroupChatType {
        public static final int hall = 1;//模厅群聊
    }

    public static final String REDIS_KEY_HASH_TAG_HALL = "hall_Income";

    /**
     * 登录应用类型
     */
    public static class LoginAppType {
        public static final Byte ExchangeSys = 1; // 提现兑换系统登录
    }

    public static final Integer USER_H5_BOUND_LIMIT_TIMES = 10; //用户实名认证验证码限制
    public static final Integer IP_H5_BOUND_LIMIT_TIMES = 10; //用户实名认证验证码限制

    public static class FinanceStatisticsConfig {

        public static final Byte FINANCE_STATISTICS_GOLD_INCREASE = 1;//用户金币收入
        public static final Byte FINANCE_STATISTICS_GOLD_DECREASE = 2;//用户金币支出
        public static final Byte FINANCE_STATISTICS_DIAMOND_INCREASE = 3;//用户钻石收入
        public static final Byte FINANCE_STATISTICS_DIAMOND_DECREASE = 4;//用户钻石支出
        public static final Byte FINANCE_STATISTICS_CRYSTAL_INCREASE = 7;//用户水晶支出
        public static final Byte FINANCE_STATISTICS_CRYSTAL_DECREASE = 8;//用户水晶支出
    }

    /**
     * emoji暗号类型
     */
    public static class EmojiType {
        public static final byte NOT_EXIST = 0;//暗号不存在
        public static final byte HALL_INVITE = 1;//模厅邀请
    }


    /**
     * 房间分类标题类型
     */
    public static class TitleType {
        public static final byte ROOM_TAG = 1;//标签
        public static final byte ROOM_TYPE = 2;//房间类型
        public static final byte SPECIFY_ROOM = 3;//指定房间
    }

    public static class MissionStatus {
        public static final Byte invalid = 0; // 无效
        public static final Byte valid = 1; // 有效
    }

    public static class WalletStatus {
        public static final Byte frozen = 0; // 冻结
        public static final Byte normal = 1; // 正常
    }

    public static class WalletCurrencyType {
        public static final Byte gold    = 0; // 金币
        public static final Byte diamond = 1; // 钻石
        public static final Byte radish  = 2; // 萝卜
        public static final Byte crystal  = 3; // 水晶
    }

    public static class HeadCurrencyType {
        public static final int gold    = 0; // 金币
        public static final int diamond = 1; // 钻石
        public static final int radish  = 2; // 萝卜
    }

    public static class GoodsCurrencyType {
        public static final int gold    = 0; // 金币
        public static final int diamond = 1; // 钻石
        public static final int radish  = 2; // 萝卜
    }

    public static class WalletUpdateType {
        public static final Byte reduce = 0; // 减少
        public static final Byte increase = 1; // 增加
    }


    public static final int DEFAULT_BIG_PICTURE_NUM = 3;
    //列表默认不限
    public static final int DEFAULT_LIST_NUM = 180;

    public static class NobleExperienceStatus {
        //未使用
        public static final int NOT_USE = 1 ;
        //使用中
        public static final int USEING = 2 ;
        //已失效
        public static final int USED = 3;
    }

    public static final String GOLD_NUM="goldNum";
    public static final String DEF_GOLD_NUM="defGoldNum";
    public static final String SHOW_GOLD_NUM="showGoldNum";
    public static final String REMAIN_GOLD_NUM="remainGoldNum";

    public static class PrizePoolType {
        public static final Byte ORDINARY = 1;//普通
        public static final Byte WHITE = 2;//白名单奖池
    }

    public static class PrizePoolStatus {
        public static final byte DEL = 0;
        public static final byte NO_DEL = 1;
    }

    public static final int ROOM_HALF_HOUR_RANK_NUM = 10;//房间半小时榜上榜数量
    public static final int ROOM_HALF_HOUR_ACTUAL_SCORE_RANK = 20;//房间半小时榜显示实际流水的排名阈值


    /**
     * 萝卜礼物赠送 收入类型
     */
    public static class RadishGiftRecordType {
        // 赠送
        public static final byte SEND = 1;
        // 收入
        public static final byte RECEIVE = 2;
    }

    /**
     * 萝卜账单收入支出类型
     */
    public static class RadishBillPayType {
        // 支出
        public static final byte PAY = 1;
        // 收入
        public static final byte INCOME = 2;
    }

    public static class UserDrawStatisWhiteStatus {
        public static final Integer NO_SET = 0;//未设置为白名单用户
        public static final Integer SET = 1;//已设置为白名单用户
    }

    /**
     * 礼物消费类型
     */
    public static class GiftConsumeType {

        public static final byte GOLD_GIFT = 1;// 金币礼物

        public static final byte RADISH_GIFT = 2;// 萝卜礼物

        public static final byte CHARGE_GIFT = 3;// 代充礼物

        public static final byte DIAMOND_GIFT = 4;// 钻石礼物
    }

    public static class UserSignRoundPrizeStatus {
        public static final Byte NO_DRAW = 0;//未瓜分
        public static final Byte DRAW = 1;//已瓜分
    }

    //瓜分金币常量
    public static class DrawGoldConstant {

        /**
         * 默认奖品id`
         */
        public static final int DEFAULT_PRIZE_ID = 1;

        /**
         * 奖品概率总值，10万，这样可以是得概率百分比，精确到后面3位小数
         */
        public static final int MAX_OCCUPATION_RATIO = 100000;
    }

    public static class SignRewardConfigStatus {
        public static final byte DEL = 0;
        public static final byte NO_DEL = 1;
    }

    /**
     * banner是否删除
     */
    public static class BannerDel {
        public static final byte NO_DEL = 0;//未删除
        public static final byte DEL = 1;//删除
    }

    /**最大送礼物数量**/
    public static final int GIFT_SEND_MAX_NUM = 9999;

    /**
     * 音乐
     */
    public static class MusicConfig {
        public static final String MUSIC_OSS = "https://vkiss.oss-cn-shenzhen.aliyuncs.com/music/share/";
    }

    /**
     * 模块用途(1用于推荐页,2用于其它标题页)
     */
    public static class RecommendModuleUse {
        public static final byte RECOMMEND = 1;//推荐页
        public static final byte TITLE = 2;//标题页
    }

    /**
     * 模块默认数量
     */
    public static class RecommendModuleDefaultNum {
        public static final byte bigPictureNum = 3;//大图显示数量
        public static final byte listNum = 5;//列表显示数量
    }

    /**
     * 发现页活动类型
     */
    public static class DiscoveryActivityType {

        public static final byte SIGN = 1;// 签到

        public static final byte MISSION = 2;// 任务
    }

    /**
     * 发现页活动类型
     */
    public static class DiscoveryJumpyType {

        public static final byte JUMP_LINK = 1;// 跳转链接

        public static final byte JUMP_ROOM = 2;// 跳转房间

        public static final byte JUMP_GAME = 3;// 跳转游戏

        public static final byte JUMP_APP = 5;// 跳转app页面
    }

    public static class SignRewardConfigType {
        public static final Byte DAY = 0;//每日签到
        public static final Byte TOTAL = 1;//累计签到
    }

    public static class SignRewardConfigGiftType {
        public static final byte GOLD = 1;//金币
        public static final byte GIFT = 2;//礼物
        public static final byte HEADWEAR = 3;//头饰
        public static final byte CAR = 4;//座驾
        public static final byte NOBLE = 5;//贵族
        public static final byte DIAMOND = 6;//钻石
        public static final byte RADISH = 7;//萝卜
    }

    public static class PictureType {
        public static final int GAME_SHARE = 1;//游戏分享
        public static final int SIGN_SHARE = 2;//签到分享
    }

    public static class PictureStatus {
        public static final int ENABLE = 1;//启用
        public static final int DISCARD = 2;//废弃
    }

    public static class SignShareType {
        public static final int COMMON = 1;//普通
        public static final int RECEIVE_GIFT = 2;//领取礼物
        public static final int DRAW_GOLD = 3;//瓜分金币
    }

    public static final int DEF_SIGN_REWARD_COUNT = 3;//默认签到奖励配置的累计奖励数为3个

    public static class LabelType {
        public static final byte NO = 0;//无
        public static final byte NEW = 1;//新品
        public static final byte DISCOUNT = 2;//折扣
        public static final byte LIMIT = 3;//限定
        public static final byte SPECIAL = 4;//专属
    }

    public static class SignRewardConfigGiftName {
        public static final String RADISH = "radish";
        public static final String HEADER = "header";
        public static final String DIAMOND = "diamond";
        public static final String GOLD = "gold";
    }

    public static final int RECOMMEND_ROOM_SIZE = 4;//推荐房配置数量

    /**
     * 账户状态
     */
    public static class AccountState {
        public static String  valid     = "1";  // 有效
        public static String  in_valid  = "0";  //无效
        public static String  block  = "2";  //封禁
    }

    //国家是否展示
    public static class Country{
        public static Boolean IS  = true;
        public static Boolean NOT = false;
        public static String  BASE_URL       = "https://img.scarllet.cn/country_ID.png";//国旗图片的baseurl
    }

    //国家是否展示
    public static class alphabet{
        public static Boolean IS  = true;
        public static Boolean NOT = false;
        public static String  BASE_URL       = "https://img.scarllet.cn/country_ID.png";//国旗图片的baseurl
    }

    public static class TopLineType {
        public static final int BROADCAST = 3;//广播消息
    }

    /**
     * 房间成员状态
     */
    public static class RoomMemberStatus{
        public static Byte APPLY            = 1;//申请
        public static Byte APPROVE          = 2;//同意
        public static Byte REFUSE           = 3;//拒绝
        public static Byte REMOVE           = 4;//移除
    }

    /**
     * 退出成员的方式
     */
    public static class RoomMemberExit{
        public static Byte OWNER           = 1;//房主移除
        public static Byte MEMBER          = 2;//成员自己退出
    }
    public static final int home_half_rank =3;

    // 首页参数
    public static class HomeOption {

        // 热门房间显示数量
        public static final int HOT_ROOM_COUNT = 50;
        // 新秀房间显示数量
        public static final int NEW_ROOM_COUNT = 6;
        // 个人房间显示数量
        public static final int PERSON_ROOM_COUNT = 6;
        // 今日头条数量
        public static final int TODAY_TOP_LINE_COUNT = 5;
        //兔兔首页佛系交友展示数量
        public static final int FRIEND_ROOM_COUNT = 6;
        //首页KTV房间展示数量
        public static final int KTV_ROOM_COUNT = 10;

        // 耳伴星推荐图标
        public static final String STAR_RECOM_ICON = "https://img.erbanyy.com/star_recom_icon.png";
        // 热门推荐图标
        public static final String HOT_RECOM_ICON = "https://img.erbanyy.com/hot_recom_icon.png";
        // 更多房间分类图标
        public static final String MORE_TAG_ICON = "https://img.erbanyy.com/more_tag_icon.png";
        // 新秀推荐图标
        public static final String NEW_ROOM_ICON = "https://img.erbanyy.com/new_room_icon.png";
        // 猜你喜欢图标
        public static final String PERSON_ROOM_ICON = "https://img.erbanyy.com/person_room_icon.png";

        // 首页模块类型
        public static final int BANNER_TOP_TYPE = 1;
        public static final int STAR_RECOM_TYPE = 2;
        public static final int HOT_RECOM_TYPE = 3;
        public static final int ROOM_TYPE = 4;
        public static final int NEW_RECOM_TYPE = 5;
        public static final int BANNER_BOTTOM_TYPE = 6;
        public static final int PERSON_RECOM_TYPE = 7;
        public static final int TODAY_TOP_LINE_TYPE = 8;
        public static final int KTV_RECOM_TYPE = 9;

        //V4首页房间改版热门推荐不限制显示数量
        public static final int V4_HOT_ROOM_COUNT = 999;


        //V4首页房间改版推荐页模块类型
        public static final int RECOMMEND_MODULE_STAR = 1;   //星推荐
        public static final int RECOMMEND_MODULE_POPULAR = 2;//热门推荐
        public static final int RECOMMEND_MODULE_TOPIC = 3;
        public static final int RECOMMEND_MODULE_BANNER = 4;
        public static final int RECOMMEND_MODULE_HALF = 5;//半小时榜
        public static final int RECOMMEND_MODULE_OFFICIAL = 6;//官方房间
        public static final int RECOMMEND_MODULE_RANK = 7;//首页前三榜单
        public static final int RECOMMEND_MODULE_FUNCTION = 8;//首页功能位
        public static final int RECOMMEND_MODULE_REC_ROOM = 9;//区域活动推荐房间
        public static final int RECOMMEND_MODULE_NEWS = 10;//头条信息

        public static final int FLOW_WEIGHT = 60;
        public static final int ONLINE_NUM_WEIGHT = 40;
        public static final int SCORE_SCALE = 11;
        public static final int HOME_ROOM_FLOW_VALIDITY = 1 * 60 * 60;


        public static final int personally_relevant_recent = 7;//历史浏览记录

    }


    /**
     * 房间成员角色
     */
    public static class RoomMemberLimit{
        public static Integer MAX           = 40000;//members最大限制值
    }

    public static class BackgroundConstant {
        public static class ComeFrom {
            // 购买
            public static final Byte BUY = 1;
            // 用户赠送
            public static final Byte USER_DONATE = 2;
            // 官方赠送
            public static final Byte OFFICIAL_DONATE = 3;
            // 免费
            public static final Byte FREE = 4;
        }

        public static class OptType {
            public static final Byte PURCHASE = 1;  // 购买背景商品
            public static final Byte RENEW = 2;     // 续费背景商品
            public static final Byte EXPERIENCE = 3;     // 背景商品赠送体验
        }

        public static class Status {
            public static final Byte UNBUY = 0;
            public static final Byte NORMAL = 1;
            public static final Byte EXPIRED = 2;
            public static final Byte INUSER = 4;//正在使用
        }
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

    public static class MessageKeyName {
        public static final String PARAMETER_ILLEGAL="parameter_illegal";
    }

    public static final String LANGUAGE="language";

    public static class LanguageType {
        public static final String EN="en";
        public static final String AR="ar";
        public static final String TR="tr";
        public static final String ID="id";
        public static final String All="all";

    }

    /**
     * 推荐页模块类型
     */
    public static class RecommendModuleType {
        public static final byte TYPE_STAR = 1;//星推荐
        public static final byte TYPE_POPULAR = 2;//热门推荐
        public static final byte TYPE_TOPIC = 3;//自定义话题
        public static final byte TYPE_HALF = 5;//半小时榜
        public static final byte TYPE_OFFICIAL = 6;//官方
        public static final byte TYPE_RANK = 7;//前三日榜
    }

    public static class ShareUrlType {
        public static final int scarllet=1;//代表用img.scarllet.cn域名
        public static final int allolike=2;//代表用img.allolike.cn域名
    }

    public static final String four_gift_id="four_gift_id";
    public static final String four_gift_switch="four_gift_switch";

    public static class FastFourGiftType {
        public static final int recive=1;
        public static final int send=2;
    }

    public static class GiftMagicStatus {
        public static final Byte DEL=0;//删除
        public static final Byte VALID=1;//有效
        public static final Byte NO_VALID=2;//无效
    }

    public static class MissionSkipType {
        public static final Byte app = 1; // 跳app页面
        public static final Byte mission = 2; // 跳任务页面
    }

    public static class MissionRouterType {
        public static final Byte person = 6; // MissionSkipType.app 个人资料页
        public static final Byte public_chat = 15; // MissionSkipType.app 交友大厅
        public static final Byte bind_phone = 17; // MissionSkipType.app 绑定手机页面
        public static final Byte sign = 26; // MissionSkipType.app 签到页面
        public static final Byte game = 29; // MissionSkipType.app 游戏tab页面
        public static final Byte head_pic = 30; // MissionSkipType.app 上传头像页面
        public static final Byte dialog = 40; // MissionSkipType.mission 弹窗
    }

    public static class MissionConfigNum {
        public static final int gift_send_num=7;
        public static final int private_photo_num=3;

        public static final int fans_num =100;

        //秒为单位,注意不能超过24小时即24*60*60=86400
        public static final int oat_five_num = 300;
    }

    public static class UserMicEventNum{
        //下面是云信的事件值
        public static final int upMic=2;
        public static final int downMic=5;

        //下面是自定义事件值
        public static final int onLine=101;
        public static final int offLine=102;

        public static final int inRoom=201;
        public static final int outRoom=202;
    }

    public static class UserMicType {
        public static final Byte ONLINE=0;//在线上
        public static final Byte ROOM=1;//在房里
        public static final Byte MIC=2;//在麦上
    }

    //时长等级只取这个版本及这个版本以后的数据
    public static final String level_online_appVersion= "3.1.1";

    public static final String appVersionCode="30";

    /**
     * 黄图类型
     */
    public static class PornType {
        public static final String avatar = "avatar";//用户个人头像
        public static final String photo  = "libary"; //个人相册
        public static final String cover  = "roomCover"; //房间封面
        public static final String family = "familyIcon";//家族头像
        public static final String group  = "groupIcon"; //家族群头像
    }


    /**
     * 黄图类型
     */
    public static class BlockType {
        public static final byte account = 1;//封禁账号
        public static final byte device  = 2; //封禁设备
        public static final byte ip      = 3; //封禁IP
    }

    public static class  FamilyLeaderRewardStatus {
        public static final Byte NO=0;//未领取
        public static final Byte YES=1;//已领取
    }

    public static class FamilyStatus{
        public static final Byte remove=0;//解散
        public static final Byte use=1;//使用中
        public static final Byte disable=2;//禁用
        public static final Byte tryOut=3;//试用
    }

    public static class FamilyRewardType{
        public static final Byte gold=0;//金币
        public static final Byte cash=1;//现金
    }

    //3.1.1版本的发布时间
    public static final String appVersion311Date="2019-06-21";

    public static class SysConfId {
        public static final String auditing_version = "auditing_version";
        public static final String cur_gift_version = "cur_gift_version";
        public static final String cur_magic_version = "cur_magic_version";
        public static final String face_version = "face_version";
        public static final String country_version = "country_version";
        public static final String homerecomm_sort_type = "homerecomm_sort_type";
        public static final String draw_act_switch = "draw_act_switch";  // 抽奖活动开关
        public static final String splash_pict = "splash_pict";      // 闪屏图片
        public static final String splash_link = "splash_link";      // 闪屏链接
        public static final String splash_type = "splash_type";      // 跳转类型
        public static final String newest_version = "newest_version";  //当前最新版本

        /**
         * 审核中的绿厅
         */
        public static final String audit_green_room = "audit_green_room";

        /**
         * 红包税率
         */
        public static final String redpacket_tax_rate = "redpacket_tax_rate";
        /**
         * 红包税率提示信息
         */
        public static final String redpacket_tax_tips = "redpacket_tax_tips";

        /**
         * IOS上线渠道
         */
        public static final String ios_platform = "ios_platform";
        /**
         * 安卓上线渠道
         */
        public static final String android_platform = "android_platform";

        public static final String open_box_rule_url = "open_box_rule_url";   //开箱子，规则图片URL key
        public static final String open_box_key_price = "open_box_key_price";  //开箱子，钥匙价格，对标萌币
        public static final String open_box_background_url = "open_box_background_url"; //开箱子，背景图片url
        public static final String open_box_max_new_user_pool_size = "open_box_max_new_user_pool_size"; //开箱子，最多使用新手奖品池的次数
        public static final String sys_clean_music_delay_time = "sys_clean_music_delay_time"; //系统清歌的延迟时间
        public static final String hot_recom_room_stock = "hot_recom_room_stock";   // 热门推荐房间库存数
        /**
         * 公聊大厅房间ID
         **/
        public static final String public_chatroom_id = "public_chatroom_id";

        public static final String open_box_switch = "open_box_switch"; //开箱子活动开关
        public static final String open_box_switch_level_no = "open_box_switch_level_no"; // 显示开箱子的最低等级
        public static final String app_download_url= "app_download_url"; // app下载链接
        public static final String home_default_banner_id= "home_default_banner_id"; // 首页banner为空时的默认bannerid


        // 一起玩（掷骰子）开关
        public static final String play_together_switch = "play_together_switch";

        // 实名认证引导类型，0：不提示，1：强制，2：强引导，
        public static final String certification_type = "certification_type";

        /**
         * 实名认证充值限制开关
         */
        public static final String CERTIFICATION_CHARGE_LIMIT_SWITCH = "certification_charge_limit_switch";
        /**
         * 实名认证充值限制金额（元）
         */
        public static final String CERTIFICATION_CHARGE_LIMIT_AMOUNT = "certification_charge_limit_amount";

        /**
         * 实名认证提现限制开关
         */
        public static final String CERTIFICATION_WITHDRAW_LIMIT_SWITCH = "certification_withdraw_limit_switch";
        /**
         * 实名认证提现限制金额（元）
         */
        public static final String CERTIFICATION_WITHDRAW_LIMIT_AMOUNT = "certification_withdraw_limit_amount";
        /**
         * 显示收益模块的最低钻石余额
         */
        public static final String min_display_count = "min_display_count";

        /**
         * 汇聚支付开关
         */
        public static final String JOINPAY_SWITCH = "joinpay_switch";

        /**
         * 汇聚支付开通渠道
         */
        public static final String JOINPAY_CHANNEL = "joinpay_channel";


        /**
         * 瓜分金币开关
         */
        public static final String DRAW_GOLD_SWITCH = "draw_gold_switch";

        //游戏开关
        public static final String GAME_SWITCH = "game_switch";

        //游戏榜单开关
        public static final String GAME__RANK_SWITCH = "game_rank_switch";

        //游戏链接有效期
        public static final String GAME_TIME = "game_time";

        //游戏发起间隔时间(频次)
        public static final String GAME_FREQUENCY = "game_frequency";

        /**
         * 没有人需要关掉房间的标签类别(非牌照房)
         */
        public static final String NO_USER_CLOSE_TAG  = "no_user_close_tag";
        public static final String app_language       = "app_language";


        public static final String room_owner_tag   = "room_owner_tag";  //房间成员列表中房主标签图片
        public static final String room_manager_tag = "room_manager_tag"; //房间成员列表中管理员标签图片

        public static final String private_chat_level = "private_chat_level"; //房间成员列表中管理员标签图片
        public static final String public_hall_chat_level = "public_hall_chat_level"; //房间发图等级
        public static final String room_send_img_level = "room_send_img_level"; //房间发图等级

        public static final String sign_share_tmp_url = "sign_share_tmp_url";   //签到分享模版Url

        public static final String family_share_tmp_url = "family_share_tmp_url";//家族分享模版URL
        public static final String room_admin_size_leve_default = "room_admin_size_leve_default";//房间admin默认人数
        public static final String room_admin_size_leve_1 = "room_admin_size_leve_1";//房间admin人数等级1
        public static final String room_admin_size_leve_2 = "room_admin_size_leve_2";//房间admin人数等级2

        public static final String monster_start_time = "monster_start_time";//怪兽来袭活动开始时间
        public static final String monster_end_time = "monster_end_time";//怪兽来袭活动结束时间

        public static final String create_family_need_gold="create_family_need_gold";//创建家族需要的金币数

        public static final String create_family_how_much_person="create_family_how_much_person";//创建家族后达到多少人正式成为allo家族
        public static final String create_family_how_much_day="create_family_how_much_day";//创建家族后多少天达到人数正式成为allo家族
        public static final String family_totalGold_rate="family_totalGold_rate";//家族流水多少,奖励百分之多少金币,格式为流水#百分比,多条以英文逗号分隔

        public static final String family_param_pic_url="create_family_param_pic";//家族参数图

        public static final String activity_start_time = "activity_start_time";//活动礼物开始时间
        public static final String activity_end_time = "activity_end_time";//活动礼物结束时间

        public static final String alternative_charge_gold_num="alternative_charge_gold_num";//代充的金币数每日限额

        public static final String alternative_charge_user="alternative_charge_user";//代充指定用户

        public static final String alternative_charge_min_gold="alternative_charge_min_gold";//代充最少多少金币起
        public static final String img_verify_switch   =   "img_verify_switch";//图形验证码开关
        public static final String phone_bind_switch   =   "phone_bind_switch";//使用功能需要绑定手机的开关
        public static final String sms_expire_seconds  =   "sms_expire_seconds";//验证码超时时间

        public static final String sign_mission_bind_phone_switch  =   "sign_mission_bind_phone_switch";//验证码超时时间

        public static final String personally_family_room_recommend="personally_family_room_recommend";//个人相关家族房间推荐

        public static final String home_country_num = "home_country_num";

        public static final String activity_prize_pool_thank_id="activity_prize_pool_thank_id";//活动奖池谢谢参与奖品ID
        public static final String lucky_draw_price = "lucky_draw_price";  //幸运7活动一张卡的价格
        public static final String sys_finger_expire_delay_time = "sys_finger_expire_delay_time";//猜拳过期时间

        public static final String room_sound_version = "room_sound_version";//房间声效版本号
        public static final String room_open_notice_switch = "room_open_notice_switch";//开房粉丝提醒开关
        public static final String family_quit_limit_time = "family_quit_limit_time";

        /** 充值产品新旧切换开关，值为 true/false */
        public static final String switch_new_charge_prod = "switch_new_charge_prod";

        public static final String room_game_number_price = "room_game_number_price";//数字游戏价格
        public static final String user_share_today_limit = "user_share_today_limit";//用户当日分享次数上限
        public static final String share_badge_switch     = "share_badge_switch";    //分享勋章开关

        public static final String open_admn_reduce_diamond_users = "open_admn_reduce_diamond_users";//开放用户钻石提现功能的用户
        public static final String admin_reduce_diamond_num = "admin_reduce_diamond_num";//后台扣除钻石的基数
        //周四快闪活动礼物id
        public static final String thursday_act_gift_id = "thursday_act_gift_id";
        //周四快闪活动正式开始时间(中国时间)
        public static final String thursday_act_start_time = "thursday_act_start_time";
        //周四快闪活动奖励图链接
        public static final String thursday_act_recive_img = "thursday_act_recive_img";
        //周四快闪活动Banner展示几天
        public static final String thursday_act_banner_day = "thursday_act_banner_day";
        //周四快闪活动奖励(奖励名称#赠送天数)
        public static final String thursday_act_recive_reward_detail = "thursday_act_recive_reward_detail";
        //周四快闪活动礼物名称
        public static final String thursday_act_gift_name = "thursday_act_gift_name";
        public static final String thursday_act_banner_ids = "thursday_act_banner_ids";
        public static final String thursday_act_send_reward_detail = "thursday_act_send_reward_detail";
        //周四快闪活动奖励图链接
        public static final String thursday_act_send_img="thursday_act_send_img";
        public static final String in_anchor_ids="in_anchor_ids";

        public static final String game_wheel_fee                    = "game_wheel_fee";                // 大转盘的入场费列表
        public static final String game_wheel_totalfee               = "game_wheel_totalfee";           // 全服通知的奖金限制
        public static final String game_wheel_expired_time           = "game_wheel_expired_time";       // 大转盘发起后,过期的时间
        public static final String game_wheel_return_start_switch    = "game_wheel_return_start_switch";// 发起者是否给抽成开关
        public static final String room_game_show                    = "room_game_show";             // 转盘游戏开关
        public static final String game_wheel_rate_prize             = "game_wheel_rate_prize";         // 转盘入场费抽成xx%到奖池
        public static final String game_wheel_rate_apply             = "game_wheel_rate_apply";         // 转盘总额抽成给发起者

        //3.2.3版本上线时间(时间戳)
        public static final String time_online="323_time_online";

        public static final String family_reward_img="family_reward_img";

        public static final String diamond_exchange_no="diamond_exchange_no";
        public static final String diamond_exchange_noble="diamond_exchange_noble";

        public static final String not_reward_family_commissions="not_reward_family_commissions";

        public static final String diamond_vote_activety_time           = "diamond_vote_activety_time";//钻石投票活动时间
        public static final String diamond_activity_prize_pool_thank_id = "diamond_activity_prize_pool_thank_id";//钻石活动奖池谢谢参与奖品ID
        public static final String diamond_activity_draw_integral       = "diamond_activity_draw_integral";//钻石活动单次抽奖积分
        //可多个
        public static final String activity_gift_id="activity_gift_id";

        //代充版本
        public static final String alternative_appVersion= "3.1.4";
        public static final String room_block_limit   ="room_block_limit";//房间内被拉黑用户不允许进房时间

        public static final String qiniu_audit_suggestion = "qiniu_audit_suggestion";
        public static final String qiniu_audit_label      = "qiniu_audit_label";
        public static final String qiniu_audit_score      = "qiniu_audit_score";
        public static final String qiniu_audit_day_lmit      = "qiniu_audit_day_lmit";

        public static final String DIAMOND_NUM_AKIRA = "diamond_num_akira";//礼物转钻石的比例
        public static final String GOLD_NUM_AKIRA    = "gold_num_akira";   //礼物转金币的比例
        public static final String CRYSTAL_NUM_AKIRA = "crystal_num_akira";//礼物转水晶的比例
        public static final String CRYSTAL_SWITCH    = "crystal_switch";//水晶功能开关
        public static final String crystal_rate      = "crystal_rate";//水晶兑换金币的比例

        public static final String uniqueid_store_list_limit= "uniqueid_store_list_limit";//靓号在商场中展示的个数

        //麦序心跳过期时间(单位秒)
        public static final String MIC_POSITION_HEART_BEAT_TIME_OUT ="mic_position_heart_beat_time_out";

        //客户端心跳请求时间(单位秒)
        public static final String CLIENT_HEART_BEAT_TIME ="client_heart_beat_time";

        /** cp config */
        public static final String cp_config = "cp_config";

        public static final String charge_activity_start_time   = "charge_activity_start_time";//充值活动开始时间
        public static final String charge_activity_week_switch  = "charge_activity_week_switch";//充值活动7天榜开关
        public static final String charge_activity_month_switch = "charge_activity_month_switch";//充值活动30天榜
        public static final String charge_activity_banner_ids   = "charge_activity_banner_ids"; //充值活动banner的id列表
        public static final String charge_activity_current_time = "charge_activity_current_time"; //充值活动测试指定时间
        public static final String charge_activity_week_end_time   = "charge_activity_week_end_time";//充值活动周结束时间
        public static final String charge_activity_month_end_time   = "charge_activity_month_end_time";//充值活动月结束时间

        /** 新年活动配置 */
        public static final String new_year_config = "new_year_config";

        public static final String country_region_language      = "country_region_language"; //国家分区语言
        public static final String hot_room_score_limit         = "hot_room_score_limit";  //热门值n
        public static final String refuel_china_gift_ids ="refuel_china_gift_ids";//加油中国指定礼物id

        public static final String refuel_china_start_time ="refuel_china_start_time";//加油中国开始时间

        public static final String refuel_china_end_time ="refuel_china_end_time";//加油中国结束时间
        public static final String game_wheel_apply_limit_time  = "game_wheel_apply_limit_time";  //普通用户发送转盘申请时间,单位S
        public static final String GIFT_HIT_LIMIT_TIME          = "gift_hit_limit_time";  //礼物连击限制时间

        public static final String send_gift_black_user      = "send_gift_black_user"; //送礼黑名单用户
        public static final String recive_gift_white_user    = "recive_gift_white_user";  //收礼白名单主播

        public static final String qiniu_block_limit  = "qiniu_block_limit";  //用户一天上传换图被封禁的次数

        public static final String BADGE_WEELY_GIFT             = "badge_weely_gift";    //指定礼物送礼勋章
        public static final String SYSTEM_CURRENT_TIME          = "system_current_time"; //指定系统当前时间


        public static final String Auto_Log_Methods = "auto_log_methods";//自动日志配置

        public static final String activity_summer_charge_first_limit = "activity_summer_charge_first_limit";  //夏日充值首充档位
        public static final String activity_summer_charge_total_limit = "activity_summer_charge_total_limit";  //夏日充值累计充档位
        public static final String activity_summer_charge_time        = "activity_summer_charge_time";  //活动时间
    }

    /**
     * 推荐页模块类型
     */
    public static class RecommendModuleTypeName {
        public static final String TYPE_STAR = "recommend_module_star";//星推荐
        public static final String TYPE_POPULAR = "recommend_module_popular";//热门推荐
        public static final String TYPE_HALF ="recommend_module_half";//半小时榜
        public static final String TYPE_OFFICIAL ="recommend_module_official";//半小时榜
    }

    public static class UserPurseReduceObjType{
        public static final Byte Alternative=0;//代充
        public static final Byte gift=1;//礼物
    }

    public static class UserPurseReduceGoldType{
        public static final Byte gratis=0;//免费
        public static final Byte charge=1;//花钱
    }

    public static class ImgSwitch {
        public static final String open = "1";
        public static final String close = "2";
    }


    /**
     * 发送短信类型(type):
     */
    public static class SmsType {
        public static final Byte register           = 1;//1.注册
        public static final Byte login              = 2;//2.登录
        public static final Byte forget_password    = 3;//3.忘记密码
        public static final Byte bind_phone         = 4;//4.绑定手机
        public static final Byte bind_alipay        = 5;//5.绑定支付宝.
        public static final Byte reset_pay_password = 6;//6.重置支付密码
        public static final Byte unbind_phone       = 7;//7.解绑手机号
        public static final Byte authentication     = 8;//8.实名认证
        public static final Byte unbind_alipay      = 9;//9.解绑支付宝
    }


    public static class SwitchVal{
        public static final String open = "1";
        public static final String close = "0";
    }

    public static class KillingAnimalsScore{
        public static final long chat_in_talk =1;
        public static final long share_act =5;
        public static final long invite_register =10;
        public static final long open_box =1;
        public static final long charge =10;
    }

    public static class KillingAnimalsScoreType {
        public static final byte chat_in_talk           = 1;//公聊大厅发言
        public static final byte share_act              = 2;//分享活动
        public static final byte invite_register    = 3;//邀请注册
        public static final byte open_box         = 4;//砸蛋
        public static final byte charge        = 5;//充值
    }

    public static class i18n {
        public static final String error="error";
        public static final String Successful_recommendation_the_customer_service_will_inform_you_later="Successful_recommendation_the_customer_service_will_inform_you_later";
        public static final String No_use_item="No_use_item";
        public static final String REQUEST_FAST="REQUEST_FAST";
        public static final String Order_does_not_exist="Order_does_not_exist";
        public static final String Inconsistent_order_amount="Inconsistent_order_amount";
        public static final String will_stop_your_exchange_function="will_stop_your_exchange_function";
        public static final String entered_5_error="entered_5_error";
        public static final String day="day";
        public static final String Congratulations_on_your_completion="Congratulations_on_your_completion";
        public static final String Congratulations_upgrade = "congratulations_upgrade";

        public static final String you_not_signed_in_today= "You_not_signed_in_today";

        public static final String click_me = "click_me";

        public static final String congratulations = "Congratulations";

        public static final String Alternaive_targe_user_msg = "Alternaive_targe_user_msg";

        public static final String activityEnded = "activityEnded";

        public static final String manager_room="manager_room";
        public static final String joined_rooms="joined_rooms";
        public static final String family_rooms="family_rooms";
        public static final String recent_visit="recent_visit";
        public static final String exchange_noble_notice="exchange_noble_notice";
    }

    /**
     * 计算活跃用户活跃值的各种操作的活跃值
     */
    public static class ActiveOperateScore{
        //送礼 10金币以内 1值
        public static BigDecimal send_gift_1_gold =BigDecimal.ONE;
        //送礼 10金币以上5值
        public static BigDecimal send_gift_5_gold =new BigDecimal(5);
        //砸金蛋1个蛋=10值
        public static double open_box=10;
        //10分钟内进房50值
        public static double in_room_10_min = 50;
        //10分钟内上麦100值
        public static double up_mic_10_min = 100;
        //签到10值
        public static double sign = 10;
        //完成一个任务 5值
        public static double mission_accomplished = 5;
        //关注他人 5值
        public static Double liked=5d;
        //被关注 10值
        public static Double  fans=10d;
        //公聊大厅发言 1值
        public static Double  chat=1d;
        //房间2min 1值
        public static Double in_room_2_min = 1d;
        //麦上1min 1值
        public static Double up_mic_1_min = 1d;
        //购买贵族获取贵族价值1/3的值
        public static BigDecimal buy_noble_percent_30 = new BigDecimal(0.33);
    }

    public static class ActiveOperateLimit {
        //按照赠送的人数增加活跃值，每天赠送一个人增加1或5值（10金币以内 1值  10金币以上5值）
        public static BigDecimal send_gift_gold_val_10 = new BigDecimal(10);

        //按照赠送的人数增加活跃值，每人10次封顶；每天100人封顶；
        public static int send_gift_active_val_person = 10;

        //按照赠送的人数增加活跃值，每人10次封顶；每天100人封顶；
        public static int send_gift_active_val_today = 100;

        //1蛋=10值 每天2500封顶
        public static int open_box_today_limit = 2500;

        //关注他人每天50值封顶
        public static int liked_today_limit = 50;

        //被关注每天150值封顶
        public static int fans_today_limit = 150;

        //公聊大厅发言每天50值封顶
        public static int chat_limit = 50;


        //上麦跟进房共用每天300值的限制
        public static int mic_room_limit=300;

        //房间多少分钟等于 1值
        public static Double in_room_min = 2d;
    }

    /**
     * 计算活跃用户活跃值的各种操作的活跃值
     */
    public static class ActiveOperateType{
        //送礼
        public static final int gift=2;
        //砸蛋
        public static final int openBox = 3;
        //买贵族
        public static final int noble = 4;
        //签到
        public static final int sign = 5;
        //完成任务
        public static final int mission = 6;
        //关注他人
        public static final int like = 7;
        //被关注
        public static final int fans = 8;
        //公聊大厅发言
        public static final int chat = 9;
        //下麦
        public static final int downMic = 10;
        //出房
        public static final int exitRoom = 11;
    }

    /**
     * 贵族ID
     */
    public static class NobleRightId {
        //上麦/进房
        public static final int emperor = 7;
    }

    public static class HomeTagLink{
        public static final String noble_emperor="https://img.scarllet.cn/emperor_tag2.png";
        public static final String deilyRankTag="https://img.scarllet.cn/dayilRankTag2.png";
        public static final String weekRankTag="https://img.scarllet.cn/weekRankTag2.png";
        public static final String coupleTag="https://img.scarllet.cn/coupleTag.png";
        public static final String officialTag="https://img.scarllet.cn/officialTag.png";
        public static final String halfHourTag="https://img.scarllet.cn/half_hour_tag.png";
    }

    public static class KillingAnimalsScoreStatus {
        public static final byte no =0;//未完成
        public static final byte yes =1;//完成
    }

    public static class RankFirstType{
        //日
        public static final String daily = "1";
        //周
        public static final String week="2";

    }

    /**
     * 勋章等级
     */
    public static class BadgeLevel{
        public static final Integer LEVE_0=0;
        public static final Integer LEVE_1=1;
        public static final Integer LEVE_2=2;
        public static final Integer LEVE_3=3;
        public static final Integer LEVE_4=4;
        public static final Integer LEVE_5=5;
    }

    /**
     * 勋章操作类型
     */
    public static class BadgeOpt{
        public static final Byte grant = 1;//系统颁发
        public static final Byte buy   = 2;//购买
        public static final Byte exper = 3;//体验
    }

    /**
     * 勋章来源
     */
    public static class BadgeComeFrom {
        public static final byte SYSTEM = 1;//1系统颁发
        public static final byte RANK   = 2;//2榜单
        public static final byte BUY    = 3;//3购买
        public static final byte SEND   = 4;//4赠送
        public static final byte OFFICE_SEND   = 5;//官方赠送
    }

    /**
     * 勋章来源
     */
    public static class BadgeUsed {
        public static final byte up     = 0;//使用
        public static final byte down   = 1;//未使用
    }

    /**
     * 勋章来源
     */
    public static class BadgeStatus {
        public static final Byte valid     = 0;//使用
        public static final Byte unvalid   = 1;//未使用
    }


    /**
     * 勋章佩戴位置
     */
    public static class BadgeSide {
        public static final int zore      = 0;//未佩戴
        public static final int First     = 1;//1号位置
        public static final int second    = 2;//2号位置
        public static final int third     = 3;//3号位置
    }

    /**
     * 勋章类型
     */
    public static class BadgeType {
        public static final Byte achieve   = 1;//成就勋章
        public static final Byte honor     = 2;//荣誉勋章
    }


    /**
     * 勋章类型
     */
    public static class BadgeI18nKey {
        public static final String BADGE_NAME_BASE    = "badge_name_{CODE}";//勋章名称
        public static final String BADGE_HONOR_NAME   = "badge_honor_{LEVEL}";//勋章资源名称名称
        public static final String BADGE_ACHIEVE_NAME = "badge_achieve_{LEVEL}";//勋章资源名称名称
        public static final String BADGE_DES          = "badge_des_{CODE}";//等级勋章描述新增
    }

    /**
     * 勋章类型
     */
    public static class BadgeOwnStatus {
        public static final Boolean Yes  = true;//勋章名称
        public static final Boolean No   = false;//勋章资源名称名称
    }

    /**
     * 默认图片URL-min
     */
    public static final String BADGE_IMG_DEF_MIN    = "https://img.scarllet.cn/badge/badge_default_min_%s_%s.png";//勋章名称


    /**
     * 系统颁发勋章默认有效期:成就勋章
     */
    public static final Integer BADGE_ACHIEVE_DAYS    = 1825;//365*5天

    /**
     * 系统颁发勋章默认有效期:荣誉勋章
     */
    public static final Integer BADGE_HONOR_DAYS    = 7;//7天

    public static class FingerPkStatus{
        public static final Integer waitPk = 0;//等待PK
        public static final Integer expire = 1;//已过期
        public static final Integer end    = 2;//已结束
    }

    public static class FingerPkResult{
        public static final Integer starter = 1;//发起者胜
        public static final Integer joiner  = 2;//参与者胜
        public static final Integer deuce   = 3;//平手
    }

    /**
     * 用户财富值充值和送礼的比例
     */
    public static class WeathExpType {
        public static final double recharge = 0.6;//充值
        public static final double gift = 0.4;//送礼
    }

    public static class ReduceDiamondStatus{
        public static final Byte no = 0;//不可以提现
        public static final Byte yes =1;//可以提现
    }

    public static class UserPurseUpdateKey{
        public static final String diamondBefore ="diamondBefore";
        public static final String diamondAfter  ="diamondAfter";
        public static final String crystalBefore ="crystalBefore";
        public static final String crystalAfter  ="crystalAfter";
    }

    /**
     * Banner状态
     */
    public static class BannerStatus {
        public static Byte use = 1;// 使用
        public static Byte noUse = 2;// 不使用
    }

    /**
     * 粉丝关注
     */
    public static class FansLike {
        public static final Byte Like   = 1;//已关注
        public static final Byte UnLike     = 2;//未关注
    }


    /**
     * 转盘的变化状态
     */
    public static class WheelStatus{
        public static final Byte NO      = 0;//无转盘信息
        public static final Byte wait    = 1;//等待加入
        public static final Byte expired = 2;//游戏过期
        public static final Byte running = 3;//游戏在运行中
        public static final Byte end     = 4;//游戏结束
        public static final Byte cancel  = 5;//游戏取消
        public static final Byte force_cancel  = 6;//游戏取消
    }

    /**
     *  转盘参与者的记录状态
     */
    public static class WheelJoinStatus{
        public static final Byte lose   = 1;//等待加入
        public static final Byte win    = 2;//游戏过期
        public static final Byte cancel = 3;//游戏在运行中
    }

    /**
     *  转盘转动时间,单位(ms)
     */
    public static class WheelTime {
        public static final Integer rotate      = 3000;//转盘转动时间
        public static final Integer lose        = 1000;//展示淘汰者时间
        public static final Integer win         = 5000;//展示胜利者时间
        public static final Integer rotate_lose = 4000;//转盘转动+展示淘汰者时间
        public static final Integer rotate_win  = 8000;//转盘转动+展示胜利者时间
        public static final Integer duration    = 4000;//输赢轮次的时间差
    }

    public static final Integer    wheel_max_num     = 9;//转盘人数最大容量
    public static final Integer    wheel_min_num     = 2;//启动转盘的最小人数限制


    /**
     *  转盘定时Job类型
     */
    public static class WheelJobType {
        public static final Byte expired     = 1;//过期处理的Job
        public static final Byte auto_start  = 2;//达到最大人数后自动启动Job
        public static final Byte game_end    = 3;//游戏结束Job
    }

    /**
     *  转盘定时Job时间
     */
    public static class WheelJobCron {
        public static final Integer expired     = 10;//10min后 过期处理的Job
        public static final Integer auto_start  = 5;//达到最大人数,5s后自动启动Job
    }



    /**
     *  转盘定时Job时间
     */
    public static class GameTagLink{
        public static final String finger = "https://img.scarllet.cn/game/game_finger.png?imageslim";//猜拳
        public static final String wheel  = "https://img.scarllet.cn/game/game_wheel.png?imageslim"; //转盘
    }

    public static final String room_start_game_wheel    = "1";//房间开始转盘游戏

    /**
     *  首页榜单跳转链接  lang=en
     */
    public static class HomeRankLink{
        public static final String noble  = "modules/erbanRank/index.html?position=2&lang="; //财富榜
        public static final String star   = "modules/erbanRank/index.html?position=1&lang="; //魅力榜
        public static final String room   = "modules/erbanRank/index.html?position=3&lang="; //房间榜单
    }

    public static class NobleExchangeStatus {
        public static final Byte no     = 0;//未兑换
        public static final Byte yes  = 1;//已兑换
    }

    public static class UserNobleUsed {
        public static final Byte no     = 0;//未使用
        public static final Byte yes  = 1;//已使用
    }

    public static class UserNobleStatus {
        public static final Byte noValid  = 1;//下架
        public static final Byte expire  = 2;//过期
        public static final Byte valid  = 3;//有效
    }

    public static class UserBlackType {
        public static final Byte black  = 1;//拉黑
        public static final Byte cancelBlack  = 0;//取消拉黑
    }

    /**
     * 房间成员禁言类型
     */
    public static class ChatRoomUserGagType{
        public static final Byte cannot  = 1;//禁言
        public static final Byte can  = 0;//解禁
    }
    /**
     * 房间成员封禁类型
     */
    public static class ChatRoomUserBlockType{
        public static final Byte block  = 1;//封禁
        public static final Byte cancelBlock  = 0;//解禁
    }
    /**
     * 房间成员拉黑类型
     */
    public static class ChatRoomUserBlackType{
        public static final Byte black  = 1;//封禁
        public static final Byte cancelBlack  = 0;//解禁
    }

    public static class RoomMicPositionState{
        //空闲
        public static final int MicroPosStateFree = 0;
        //上锁
        public static final int MicroPosStateLock = 1;
        //不空闲 这个状态下版本，要让ios去掉对这个状态的判断，改判断chatroomMember是否为空来显示用户卡片
        public static final int MicroPosStateNotFree = 2;
    }

    public static class RoomMicPositionMicState{
        //开麦
        public static final int MicroMicStateOpen = 0;
        //静音
        public static final int MicroMicStateClose = 1;
    }

    public static class RoomMicPositionMicType{
        public static final int owner = 0;
        public static final int normal = 1;
        public static final int richMan = 2;
    }

    /**
     * 房间成员拉黑类型
     */
    public static class roomBlackUserType{
        public static final Byte black  = 1;//拉黑
        public static final Byte block  = 0;//踢人
    }


    public static class PersonallyRelevantOptType{
        public static final Byte del  = 1;//删除个人房间
        public static final Byte set  = 0;//添加个人房间
    }

    public static class RoomLockMicPosition{
        //解锁
        public static final int UnLock = 0;
        //锁
        public static final int lock = 1;
    }

    public static class SysMessageDataFamilyFunction{
        public static final Byte familyGroupRemove  = 1;//解散群组
        public static final Byte familyGroupMemberKick  = 2;//将用户踢出群
    }

    /**
     * 钻石榜单类型
     */
    public static class DiamondRankType {
        public static final Byte receive  = 1;//收礼
        public static final Byte send     = 2;//送礼
    }

    /**
     * app内用户类型
     */
    public static class UserRole {
        public static final Byte Normal             = 1;//普通用户
        public static final Byte Official           = 2;//官方用户
        public static final Byte Robot              = 3;//机器人
        public static final Byte guild              = 4;//送礼
        public static final Byte Consumer_Service   = 5;//客服
        public static final Byte Charge_User        = 6;//代充用户
    }

    /**
     * 七牛图片审核建议
     */
    public static class QiniuImgSuggestion {
        public static final String pass             = "pass";//通过
        public static final String review           = "review";//重新审核
        public static final String block            = "block";//封禁
    }

    /**
     * 七牛图片审核标签
     */
    public static class QiniuImgLabel {
        public static final String normal           = "normal";//正常图片,无色情内容
        public static final String sexy             = "sexy";//性感图片
        public static final String pulp             = "pulp";//色情图片
    }

    public static class MsgType{
        public static final byte TEXT = 0;
        public static final byte IMG = 1;
        public static final byte IMG_TEXT = 100;
    }

    public static class CommonValidStatus {
        public static final Boolean invalid = false; // 无效
        public static final Boolean valid   = true; // 有效
    }

    public static class CommonSaleStatus {
        public static final Boolean no_sale = false; // 不售卖
        public static final Boolean sale    = true;// 售卖
    }

    public static class UserUniqueIDUse {
        public static final Boolean no_use  = false;// 未使用
        public static final Boolean used    = true; // 使用
    }

    public static class UserUniqueIDStatus {
        public static final Byte nolmal  = 1;// 正常
        public static final Byte expire  = 2;// 过期
    }


    /**
     * 账单大类类型
     */
    public static class BillTypeClass {
        public static final byte giftSent         = 1;//礼物支出记录
        public static final byte giftReceive      = 2;//礼物收入记录-钻石收入记录
        public static final byte private_record   = 3;//密聊记录
        public static final byte charge           = 4;//充值记录
        public static final byte withdraw         = 5;//提现记录
        public static final byte coinsIncome      = 6;//金币收入记录
        public static final byte coinsOutcome     = 7;//金币支出记录
        public static final byte coinsAll         = 8;//所有的金币记录
        public static final byte diamondsSent     = 9;//钻石支出记录
        public static final byte diamondsAll      = 10;//所有钻石记录
        public static final byte crystalsAll      = 11;//水晶所有记录
        public static final byte crystalsReceive  = 12;//水晶收入
        public static final byte crystalsSent     = 13;//水晶支出
    }



    /**
     * 充值活动类型
     */
    public static class ChargeRankType {
        public static final byte WEEK_DAY = 7;  //周
        public static final byte MONTH_DAY = 30;//月
    }

    /**
     * 充值活动类型
     */
    public static class ChargeAwardType {
        public static final byte ARISTOCRATIC = 1;//贵族
        public static final byte BADGE        = 2;//勋章
        public static final byte BANNER       = 3;//banner
    }

    /**
     * 榜单活动类型:1充值活动
     */
    public static class RankActType {
        public static final byte CHARGE = 1;//充值
        public static final byte CP     = 2;//CP
        public static final byte BADGE_RECEIVER  = 3;//周榜勋章收礼
        public static final byte BADGE_SENDER    = 4;//周榜勋章送礼
        //User Invite
        public static final byte USER_INVITE     = 5;
        /** 首充 */
        public static final byte FIRST_RECHARGE = 7;
        /** 签到 */
        public static final byte SIGN_IN = 8;
        /** 新用户引导首次充值 */
        public static final byte NEW_USER_GUIDE_SIGN   =9;
        public static final byte CAMEL_CHBARGE   = 11;//骆驼充值
    }

    /**
     * 签到活动任务类型
     */
    public static class SignInTaskType {
        public static final byte CUMULATIVE = 1;
    }

    /**
     * 日期类型
     */
    public static class DateType {
        public static final Byte day = 1;
        public static final Byte week = 2;
        public static final Byte month = 3;
        public static final Byte year = 4;
        public static final Byte permanent = 5;
    }

    public static class NobleUserEnterHide {
        public static final byte show = 0;//不隐身
        public static final byte hide = 1;//隐身
    }

    public static class RankActDay {
        // 天
        public static final int DAY_DAY = 1;
        // 周
        public static final int WEEK_DAY = 7;
        // 月
        public static final int MONTH_DAY = 30;
        // 年
        public static final int YEAR_DAY = 365;
        // permanent
        public static final int PERMANENT_DAY = 3650;

    }

    /** IOS 排除勋章 */
    public static List<Byte> excludeAppleBadgeList = new ArrayList<>();
    static {
        /**
         billionaire  1
         richman    2
         weekly richest  13
         new star   12
         magician    7
         celebrity   3
         top room  8
         receiver   16
         charm family  10
         */
        excludeAppleBadgeList.add(Byte.valueOf("1"));
        excludeAppleBadgeList.add(Byte.valueOf("2"));
        excludeAppleBadgeList.add(Byte.valueOf("13"));
        excludeAppleBadgeList.add(Byte.valueOf("12"));
        excludeAppleBadgeList.add(Byte.valueOf("7"));
        excludeAppleBadgeList.add(Byte.valueOf("3"));
        excludeAppleBadgeList.add(Byte.valueOf("8"));
        excludeAppleBadgeList.add(Byte.valueOf("16"));
        excludeAppleBadgeList.add(Byte.valueOf("10"));
    }

    /**
     * 房间流水统计类型:
     * 1.送礼2砸蛋消耗钥匙3转盘4猜拳头饰5.开通贵族6座驾7头饰8靓号
     */
    public static class RoomFlowType {
        public static final Byte GIFT     = 1;
        public static final Byte CRACKEGE = 2;
        public static final Byte WHEEL    = 3;
        public static final Byte FINGER   = 4;
        public static final Byte NOBLE    = 5;
        public static final Byte CAR      = 6;
        public static final Byte HEADWEAR = 7;
        public static final Byte UNIQUEID = 8;
    }

    public static class OfficialRoom{
        public static final Byte NORMAL     = 1;
        public static final Byte OFFICAL    = 2;
    }

    public static class RoomtTop{
        public static final Byte NO     = 0;//不置顶
        public static final Byte TOP    = 1;//置顶
    }
    public static class RoomtPriorityShow{
        public static final Byte NO     = 0;//不优先展示
        public static final Byte YES    = 1;//优先展示
    }
    public static class CountryPriorityShow{
        public static final Byte NO     = 0;//不优先展示
        public static final Byte YES    = 1;//优先展示
    }

    public static class CarGoodCpActivityCar{
        public static final Byte NO        = 0;//不是活动座驾
        public static final Byte CP_CAR    = 1;//活动CP座驾
        public static final Byte NORMAL    = 2;//活动普通座驾
    }

    public static class RoomCanShow{
        public static final Byte YES    = 1;//可以展示
    }

    /**
     * 礼物展示头像类型
     */
    public static class GiftAvatarShowType{
        public static final Byte NO                = 0;//无头像展示
        public static final Byte SENDER_RECEIVE    = 1;//普通礼物展示收送礼人头像展示
        public static final Byte USER_NAMING       = 2;//冠名礼物,冠名用户头像展示
        public static final Byte CP_NAMING         = 3;//CP冠名礼物,冠名CP用户头像展示
    }

    public static class RoomCharmStatus{
        public static final Byte BYTE_OPEN     = 1;//开启魅力值
        public static final Byte BYTE_CLOSE    = 0;//关闭魅力值

        public static final boolean BOOL_OPEN     = true;//开启魅力值
        public static final boolean BOOL_CLOSE    = false;//关闭魅力值
    }

    /**
     * 獎品命名類型
     */
    public static class PrizeNameType{
        public static final byte GIFT              = 1;//冠名礼物
        public static final byte CAR               = 2;//冠名座驾
    }

    /**
     * 冠名奖品来源
     */
    public static class PrizeNameSourceType {
        public static final byte ADMIN      = 1;//1管理员
        public static final byte SYSTEM_ACT = 2;//2.系统活动
    }

    public static final String activity_room_all_area    = "all";//首页活动房间推荐全部区域
    /**
     * 充值类型
     * @author qinrenchuan
     * @date 2020/3/10/0010 15:38
     */
    public static class ChargeType {
        /** IOS充值 */
        public static final Byte IOS = 1;
        /** Google充值 */
        public static final Byte GOOGLE = 2;
        /** 代充礼物充值 */
        public static final Byte CHARGE_GIFT = 3;
        /** admin后台,对指定用户充值类型和公款充值类型  */
        public static final Byte ADMIN_CHARGE = 4;
    }

    /**
     * 斋月活动类型
     */
    public static class RamadanJobType{
        public static final Byte GIFT_RECEIVE  = 1;//收集礼物
        public static final Byte GIFT_SEND     = 2;//赠送礼物
        public static final Byte ITEM_RECEIVE  = 3;//收集碎片
        public static final Byte ITEM_SEND     = 4;//赠送碎片
    }

    /**
     * 活动任务进度
     */
    public static class ActJobProgress {
        public static final String UNFINISH = "0";//未完成
        public static final String PENGDING = "1";//已完成,待领取
        public static final String FINISHED = "2";//已经领取
    }

    /**
     * 斋月活动编号
     * 详情见: http://wiki.dongchenkeji.com/pages/viewpage.action?pageId=9045696
     */
    public static class RamadanJobCode{
        public static final Integer CODE_1  = 1;
        public static final Integer CODE_2  = 2;
        public static final Integer CODE_3  = 3;
        public static final Integer CODE_4  = 4;
        public static final Integer CODE_5  = 5;
        public static final Integer CODE_6  = 6;
        public static final Integer CODE_7  = 7;
        public static final Integer CODE_8  = 8;
        public static final Integer CODE_9  = 9;
        public static final Integer CODE_10 = 10;
        public static final Integer CODE_11 = 11;
        public static final Integer CODE_12 = 12;

    }
    public static final String proto_interceptor_attr_name = "pbHttpBizReq";
}



