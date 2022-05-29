package com.dc.allo.common.constants;



/**
 * Created by liuguofu on 2017/4/28.
 */
public enum BusiStatus implements BaseBusiStatus {
    UpdateFailed(0, "Update_failed"),
    SUCCESS(200, "success"),            //成功
    NOTEXISTS(404, "not_exists"),
    NOAUTHORITY(401, "no_authority"),
    ACCOUNT_ERROR(407,"account_error"),//账号异常-账号被封
    SERVERERROR(500, "server_error"),
    SERVERBUSY(503, "serverbusy"),
    SERVER_BUSY(503, "server_busy"),

    SERVICE_STOP(503, "service_stop"),

    REQUEST_FAST(1000, "request_fast"),
    REQUEST_PARAM_ERROR(1000, "request_param_error"),
    ACTIVITYNOTSTART(3100, "activity_not_start"),
    ACTIVITYNOTEND(3200, "activity_not_end"),

    INVALID_SERVICE(199, "invalid_service"),//服务不可用

    SERVEXCEPTION(5000, "serv_exception"),

    UNKNOWN(999, "unknown"),//未知错误

    BUSIERROR(4000, "busi_error"),

    PARAMETERILLEGAL(1444, "Error"),

    ALERT_SUCCESS(1,"alert_success"),
    ALERT_PARAMETER_ILLEGAL(-1,"alert_parameter_illegal"),
    ALERT_FAIL(-2,"alert_fail"),
    ALERT_SIGN_FAIL(-3,"alert_sign_fail"),


	PHONE_REGISTERED (1402, "phone_registered"),
	PHONE_UNREGISTERED (1403, "phone_unregistered"),
    USERNOTEXISTS(1404, "user_not_exists"),

    USERINFONOTEXISTS(1405, "userinfo_not_exists"),

    NICKTOOLONG(1406, "nick_too_long"),
    NICKTOOSHORT(1406, "nick too short"),
    NEEDLOGIN(1407, "need_login"),
    SMALL_THAN_EIGHTEEN(1408, "small_than_eighteen"),
    USER_INCORRECT_PAYMENT_PASS(1409, "user_incorrect_payment_pass"),
    USER_INCORRECT_LOGIN_PASS(1410, "user_incorrect_login_pass"),
    USER_LOGIN_PASS_NULL(1411,"user_login_pass_null"),

    VIDEONOTEXISTS(1604, "video_not_exists"),
    COLLECTEXISTS(1605, "collect_exists"),

    NOTLIKEONESELF(1500, "not_like_oneself"),

    WEEKNOTWITHCASHTOWNUMS(1600, "week_not_with_cashtownums"),

    ALIAPYACCOUNTNOTEXISTS(16001, "aliapy_account_not_exists"),

    PRIVATEPHOTOSUPMAX(1700, "private_photo_supmax"),

    ROOMRUNNING(1500, "room_running"),
    ROOMRCLOSED(1501, "roomr_closed"),
    ROOMNOTEXIST(1502, "room_not_exist_2"),
    NOTHAVINGLIST(1600, "not_having_list"),

    AUCTCURDOING(2100, "auctcur_doing"),

    AUCTCURLESSTHANMAXMONEY(2101, "auctcur_less_than_max_money"),
    AUCTCURYOURSELFERROR(2102, "auctcur_your_self_error"),
    PURSEMONEYNOTENOUGH(2103, "purse_money_not_enough"),
    DIAMONDNUMNOTENOUGH(2104, "diamond_num_not_enough"),
    REDPACKETNUMNOTENOUGH(2105, "red_packet_num_not_enough"),
    RED_PACKET_RATE_NOT_ENOUGH(2106, "red_packet_rate_not_enough"),
    ORDERNOTEXISTS(3404, "the_order_does_not_exist"),
    ORDERFINISHED(3100, "the_order_has_been_completed"),

    SMSSENDERROR(4001, "sms_send_error"),
    PHONEINVALID(4002, "phone_invalid"),
    SMSCODEERROR(4003, "sms_code_error"),
    ILLEGAL_PHONE(4004, "illegal_phone"),
    FAILED_SEND_SMS(4005,"failed_send_sms"),
    SUCCESS_SEND_SMS(4006,"success_send_sms"),

    MICRONOTININVIATEDLIST(6000, "micro_not_in_inviated_list"),
    MICRONUMLIMIT(60001, "micro_num_limit"),

    GOLODEXCHANGEDIAMONDNOTENOUGH(70001, "golod_exchange_diamond_not_enough"),
    EXCHANGEINPUTERROR(70002, "exchange_input_error"),

    GIFTDOWNORNOTEXISTS(8000, "gift_down_or_not_exists"),
    GIFT_IS_NOT_ALLOWED_TO_BE_SENT(8000, "gift_is_not_allowed_to_be_sent"), //客户端写死了8000，否则无法提示
    GIFTISEMPTY(8001, "gift_isempty"),
    GIFTNOAUTHORITY(8401, "gift_no_authority"),

    ALTERNATIVE_TOTAL_PRICE_LESS_MIN_GOLD(8409, "alte_total_price_less_gold"),

    NOBLENORECOMCOUNT(3401, "noble_no_recomcount"),
    NOBLENOAUTH(3402, "noble_no_auth"),
    NOBLENOTEXISTS(3404, "noble_not_exists"),
    NOBLEUSEREXIST(3301, "noble_user_exist"),
    NOBLEOPENERROR(3500, "noble_open_error"),
    NOBLEEXPIRE(3501, "noble_expire"),

    SMSIPTOOFTEN(302, "sms_ip_too_ften"),

    PRETTYNOERROR(1200, "pretty_no_error"),
    HASPRETTYNOERROR(1300, "has_pretty_no_error"),
    iSPROVING(1400, "is_proving"),
    PARAMERROR(1900, "param_error"),
    NORIGHT(1401, "no_right"),

    ISUNBANGSTATUS(1901, "is_unbang_status"),

    NOMORECHANCE(9000, "no_more_chance"),
    USER_DRAW_TO_MORE(9001, "user_draw_to_more"),

    VERSIONISNULL(80001, "version_is_null"),
    VERSIONTOLOW(80002, "version_too_low"),

    CANOTUSEAPPLEPAY(7500, "canot_use_apple_pay"),

    CARPORTHADEXPIRE(6405, "carport_had_expire"),
    CARPORTNOTEXIST(6406, "carport_not_exist"),
    CARGOODSEXIST(6301, "cargoods_exist"),
    CAR_GOODS_NOT_EXISTS(6201, "car_goods_not_exists"),
    CAR_GOODS_EXPIRED(6202, "car_goods_expired"),
    CAR_GOODS_LIMIT(6203, "car_goods_limit"),
    CAR_NOT_GOLD_SALE(6210, "car_not_gold_sale"),
    CAR_NOT_RADISH_SALE(6211, "该座驾不支持萝卜购买"),

    MONSTER_NOT_APPEAR(6501, "怪兽不在出现时间段"),
    MONSTER_DEFEATED(6502, "怪兽已经被打败"),
    MONSTER_ESCAPED(6503, "怪兽已经逃跑"),
    MONSTER_NOT_EXISTS(6504, "怪兽不存在"),
    WEEK_STAR_CONFIG(8200, "week_star_config"),

    ILLEGAL_TRANSFER_REQUEST(1700, "illegal_transfer_request"),
    TRANSFER_COMPLETED(1701, "transfer_completed"),
    DUPLICATE_TRANSFER_REQUEST(1702, "duplicate_transfer_request"),

    HEADWEAR_NOT_EXISTS(1800, "headwear_not_exists"),
    HEADWEAR_SOLD_OUT(1801, "headwear_sold_out"),
    HEADWEAR_EXPIRED(1802, "headwear_expired"),
    HEADWEAR_LIMIT_NOBLE(1803, "headwear_limit_noble"),
    HEADWEAR_LIMIT_ACTIVITY(1804, "headwear_limit_activity"),
    HEADWEAR_LIMIT_WEEK_STAR(1805, "headwear_limit_week_star"),
    HEADWEAR_LIMIT_MONSTER(1806, "怪兽奖品限定产品"),
    HEADWEAR_NOT_BUY(1807, "headwear_not_gold_sale"),
    HEADWEAR_NOT_GOLD_SALE(1808, "headwear_not_radish_sale"),
    HEADWEAR_NOT_RADISH_SALE(1809, "headwear_not_radish_sale"),


    FAMILY_NOT_EXISTS(7000, "family_not_exists"),
    HAVE_JOIN_IN_FAMILY(7001, "have_join_in_family"),
    HAVE_JOIN_IN_THIS_FAMILY(7002, "have_join_in_this_family"),
    YOU_HAVE_BEEN_REJECT(7003, "you_have_been_reject"),
    FAMILY_HAVE_APPLY(7004, "family_have_apply"),
    FAMILY_MESSAGE_EXPIRE(7005, "family_message_expire"),
    FAMILY_PERMISSION_DENIED(7006, "family_permission_denied"),
    FAMILY_LEADER_NOT_ALLOW_QUIT(7007, "family_leader_not_allow_quit"),
    FAMILY_QUIT_LIMIT_TIME(7008, "family_quit_limit_time"),
    FAMILY_NOT_JOIN(7009, "family_not_join"),
    FAMILY_TARGET_NOT_JOIN(7010, "family_target_not_join"),
    FAMILY_NOT_SAME(7010, "family_not_same"),
    FAMILY_MONEY_NOT_ENOUGH(7011, "family_money_not_enough"),
    FAMILY_MONEY_TRADE_IN_FAILED(7011, "family_money_trade_in_failed"),
    FAMILY_INVITE_FREQUENCY_TO_MUCH(7012, "family_invite_frequency_to_much"),
    FAMILY_INVITE_NOT_EXISTS(7013, "family_invite_not_exists"),
    FAMILY_INVITE_EXPIRE(7013, "family_invite_expire"),

    FAMILY_RED_PACKGE_COUNT_ERROR(7014, "family_red_packge_count_error"),
    FAMILY_RED_PACKGE_AMOUNT_NOT_ENOUGH(7015, "family_red_packge_amount_not_enough"),
    RED_PACKET_NOT_EXISTS(7017, "red_packet_not_exists"),
    RED_PACKET_PARAM_ILLEGAL(7016, "red_packet_param_illegal"),
    RED_PACKET_NOT_EXSIST(7016, "red_packet_not_exsist"),
    RED_PACKET_NOT_IN_SAME_GROUP(7017, "red_packet_not_in_same_group"),
    RED_PACKET_HAS_RECEVICED(7018, "red_packet_has_receviced"),
    RED_PACKET_RECEVICED_OVER(7019, "red_packet_receviced_over"),
    FAMILY_NAME_IS_SENSITIVE(7020, "family_name_is_sensitive"),
    FAMILY_LEADER_CANT_CONTRIBUTE(7021, "family_leader_cant_contribute"),
    RED_PACKET_EXPIRE(7022, "red_packet_expire"),

    MODIFY_FAILD(7023, "modify_faild"),
    FAMILY_APPLY_RECORD_NOT_EXISTS(7024, "family_apply_record_not_exists"),
    FAMILY_APPLY_AGREED(7025, "family_apply_agreed"),
    FAMILY_NAME_REPEAT(7026, "family_name_repeat"),
    FAMILY_NAME_TOO_LONG(7027, "family_name_too_long"),
    FAMILY_MONEY_NAME_REPEAT(7028, "family_money_name_repeat"),
    FAMILY_MONEY_NAME_TOO_LONG(7029, "family_money_name_too_long"),
    ALREADY_IN_FAMILY(7030, "already_in_family"),
    FAMILY_GROUP_REMOVE_FAILED(7031, "family_group_remove_failed"),
    KICK_OUT_GROUP_FAILED(7032, "kick_out_group_failed"),
    LEAVE_GROUP_FAILED(7033, "leave_group_failed"),
    LEAVE_FAMILY_FAILED(7034, "leave_family_failed"),
    LESS_THAN_MINIMUM_AMOUT(7036, "less_than_minimum_amout"),
    FAMILY_REJECT_TOO_MUCH_TIMES(7037, "family_reject_too_much_times"),
    GROUP_REJECT_TOO_MUCH_TIMES(7038, "group_reject_too_much_times"),
    MANAGE_COUNT_TOO_MANY(7039, "manage_count_too_many"),
    GROUP_NAME_IS_SENSITIVE(7040, "group_name_is_sensitive"),
    HAS_LEAVED_CHAT(7041, "has_leaved_chat"),
    HAS_JOINED_FAMILY(7042, "has_joined_family"),
    HAS_CLOSE_FAMILY_MONEY(7043, "has_close_family_money"),
    GROUP_MEMBER_LIMIT(7044, "group_member_limit"),
    HAS_JOINED_OTHER_FAMILY_GROUP(7045, "has_joined_other_family_group"),
    ROOM_MEM_NOT_EXISTS(7046, "room_mem_not_exists"),
    ROOM_MEM_MANAGER_LIMIT(7047, "room_mem_manager_limit"),
    ROOM_MEM_MAX(7048, "room_mem_max"),
    ROOM_ADD_ADMIN_FORBIDDEN(7049, "room_add_admin_forbidden"),


    USER_DOLL_NOT_EXISTS(6600, "娃娃不存在"),
    USER_DOLL_EXISTS(6601, "娃娃已经存在"),
    USER_DOLL_SEX_ERROR(6602, "娃娃性别设置错误"),
    ACCOUNT_INIT_FAILD(6603, "初始化账户失败"),
    WEIXIN_USER_NOT_EXITSTS(6618,"微信用户不存在"),
    CATCH_DOLL_NOT_EXIST(6404,"抓取娃娃不存在"),
    CUSTOM_DOLL_FAILD(6604, "自定义娃娃失败"),
    CATCH_DOLL_FAILD(6605, "抓取娃娃失败"),
    CATCH_DOLL_ERORR(6606, "抓取娃娃错误"),
    CATCH_DOLL_TIMES_NOT_ENOUGH(6607, "抓取娃娃次数不充足"),
    CATCH_DOLL_DEAL_ERROR(6608, "抓取娃娃处理错误"),
    CATCH_DOLL_DEAL_TYPE_ERROR(6609, "抓取娃娃处理类型错误"),
    CATCH_DOLL_HAS_DEALED(6610, "抓取娃娃已经被处理了"),
    SHARD_DOLL_TIMES_ENOUGH(6611, "分享次数已经足够"),
    SHARD_DOLL_FAILD(6612, "分享失败"),
    QUERY_FAILD(6613, "query_faild"),
    BED_NOT_ENOUGH(6614, "床位不够"),
    DOLL_NOT_ENOUGH(6615, "娃娃不够"),
    ACCOUNT_NOT_EXISTS(6616, "account_not_exists"),
    NICK_IS_NULL(6617, "nick_is_null"),
    AVATAR_IS_NULL(6618, "avatar_is_null"),

    LIMIT_MAX_CHAT(7071671, "limit_max_chat"),
    NOT_FOUND_CHAT(7071672, "not_found_chat"),
    LIMIT_MAX_MEMBER(7071673, "limit_max_member"),
    ALREADY_EXISTS_MEMBER(7071674, "already_exists_member"),
    NO_PERMIT_OPERATOR(7071675, "no_permit_operator"),
    MEMBER_NOT_IN_CHAT(7071676, "member_not_in_chat"),
    MEMBER_ALREADY_MANAGER(7071677, "member_already_manager"),
    MEMBER_NOT_MANAGER(7071677, "member_not_manager"),
    MEMBER_ALREADY_DISABLE(7071678, "member_already_disable"),
    MEMBER_NOT_DISABLE(7071679, "member_not_disable"),

    PK_ACT_RECORD_EXIST(9100, "pk_act_record_exist"),
    PK_ACT_RECORD_TIME_ERROR(9101, "pk_act_record_time_error"),
    PK_ACT_RANK_KEY_NOT_EXIST(9404, "pk_act_rank_key_not_exist"),
    PK_ACT_RECORD_IP_REPEAT(9501, "pk_act_record_ip_repeat"),
    ACT_GIFT_NOT_EXITST(9102, "act_gift_not_exitst"),

    SEARCH_NOT_SUPPORT(9502, "search_not_support"),

    OPEN_BOX_POOL_NEED_THANKS_PRODUCT(9999, "open_box_pool_need_thanks_product"),
    OPEN_BOX_KEY_NOT_ENOUGH(10000, "open_box_key_not_enough"),
    OPEN_BOX_USER_TO_MORE(10001, "open_box_user_to_more"),
    WHITE_USER_PRIZE_FAILED(10002, "white_user_prize_failed"),
    OPEN_BOX_BAD_POOL_CONFIG(10003, "open_box_bad_pool_config"),

    SMS_NOT_EXPIRED(2001, "sms_not_expired"),
    SMS_DEVICE_LIMIT(2002, "sms_device_limit"),
    ACT_LIKE_WISH_LIMIT(10004, "act_like_wish_limit"),
    ACT_LIKE_WISH_FAILED(10004, "act_like_wish_failed"),
    ACT_WISH_NOEXIST(10004, "act_wish_noexist"),
    ACT_WISH_PER_NOEXIST(10004, "act_wish_per_noexist"),
    ACT_WISH_TO_MYSELF(10004, "act_wish_to_myself"),
    ACT_PRIZE_KEY_LIMIT(10005, "act_prize_key_limit"),
    ACT_PRIZE_KEY_LEVEL_LIMIT(10005, "act_prize_key_level_limit"),
    ACT_PRIZE_KEY_FAILED(10005, "act_prize_key_failed"),
    ACT_NATIONAL_HAS_GIVE(10006, "act_national_has_give"),
    ACT_NATIONAL_GIVE_FAILED(10006, "act_national_has_give"),
    ACT_NATIONAL_TIME_OUT(10007, "act_national_time_out"),
    ACT_SIGN_NOT_IN_TIME(10008, "act_sign_not_in_time"),

    IOS_CHARGE_DEVICE_LIMIT(7062,"ios_charge_device_limit"),
    IOS_CHARGE_CLIENTIP_LIMIT(7063,"ios_charge_clientip_limit"),

    ROOM_TITLE_IS_SENSITIVE(7060,"room_title_is_sensitive"),
    ROOM_TITLE_TOO_LONG(7035,"room_title_too_long"),


    NOT_LOST_USER(8005,"not_lost_user"),
    HAS_CLAIMED_AWARD(8006,"has_claimed_award"),
    INVITE_CODE_ERROR(8007,"invite_code_error"),
    SAME_IP_DEVICE(8008,"same_ip_device"),
    NOT_INVITE_OWN(8009,"not_invite_own"),
    LOST_USER_EXIST(8010,"lost_user_exist"),

    MUSIC_NOT_EXISTS(10100, "music_not_exists"),
    ROOM_IS_NOT_KTV(10101, "room_is_not_karaoke"),
    ROOM_IS_KTV(10102, "room_is_ktv"),
    CHOOSE_MUSIC_STATION_IS_FULL(10103, "choose_music_station_is_full"),
    CHOOSE_MUSIC_RECORD_IS_EMPTY(10104, "choose_music_record_is_empty"),
    MUSIC_IS_CHOOSE_BY_USER(10105, "music_is_choose_by_user"),
    MUSIC_IS_SINGING(10106, "music_is_singing"),
    NO_KTV_PRIV(10107, "no_ktv_priv"),

    SEND_KEY_NUM_INCORRENT(10009,"send_key_num_incorrent"),

    VKISS_PRIZE_ACTIVITY_ERROR(9588,"vkiss暗号领取奖励活动配置异常"),
    VKISS_API_ERROR(9688,"vkiss接口异常"),
    VKISS_BUSINESS_ERROR(9788,"出错了"),
    VKISS_BUSINESS_ALREADY_GET(9888,"你已经领取过啦，不能重复领取哦"),


    PACK_NOT_EXIST(8500, "pack_not_exist"),
    PACK_NOT_OPEN(8501, "pack_not_open"),
    PACK_UNDER_STOCK(8502, "pack_under_stock"),
    PACK_REACH_BUY_LIMIT(8503, "pack_reach_buy_limit"),
    PACK_OVERCROWDED(8504, "pack_overcrowded"),

    IS_IN_MIRCO_QUEUE(1503,"is_in_mirco_queue"),
    MIRCO_QUEUE_IS_FULL(1504,"mirco_queue_is_full"),
    QUEUE_MODEL_CLOSED(1505,"queue_model_closed"),

    NEED_CERTIFICATION(10108,"need_certification"),
    ID_CARD_BIND_LIMIT(10109,"id_card_bind_limit"),
    USER_HAS_BIND_ID(10110,"user_has_bind_id"),
    WITHDRAW_NEED_CERTIFICATION(10111,"withdraw_need_certification"),

    CHRISTMAS_TREE_FAMILY_NOT_JOIN(11002,"christmas_tree_family_not_join"),
    CHRISTMAS_TREE_ALREADY_RECEIVE(11003,"christmas_tree_already_receive"),
    CHRISTMAS_TREE_UNMET_RULE(11004,"christmas_tree_unmet_rule"),

    PRETTY_NUMBER_NOT_EXIST(80781, "pretty_number_not_exist"),
    PRETTY_NUMBER_NOT_VALID(80782, "pretty_number_not_valid"),
    PRETTY_NUMBER_ALREADY_USED(80783, "pretty_number_already_used"),
    PRETTY_NUMBER_NOT_USED(80784, "pretty_number_not_used"),
    PRETTY_NUMBER_ALREADY_BIND(80785, "pretty_number_already_bind"),
    PRETTY_NUMBER_ALREADY_OCCUPY(80786, "pretty_number_already_occupy"),
    PRETTY_NUMBER_NOT_OCCUPY(80787, "pretty_number_not_occupy"),

    MASTER_APPRENTICE_ELT_TEN(90011, "master_apprentice_elt_ten"),
    MASTER_APPRENTICE_EGT_THREE(90012, "master_apprentice_egt_three"),
    MASTER_APPRENTICE_NOT_ENOUGH(90013, "master_apprentice_not_enough"),
    MASTER_APPRENTICE_WAIT(90014, "master_apprentice_wait"),
    MASTER_APPRENTICE_ALREADY_OTHER_BIND(90015, "master_apprentice_already_other_bind"),
    MASTER_APPRENTICE_ALREADY_BIND(90016, "master_apprentice_already_bind"),
    MASTER_APPRENTICE_NOT_EXISTS(90017, "master_apprentice_not_exists"),
    MASTER_APPRENTICE_NOT_EXPIRE(90018, "master_apprentice_not_expire"),
    MASTER_APPRENTICE_MISSION_ALREADY_EXPIRE(90019, "master_apprentice_mission_already_expire"),
    MASTER_APPRENTICE_HAS_OTHER_MASTER(90020,"master_apprentice_has_other_master"),
    MASTER_APPRENTICE_ALREADY_ENOUGH(90021,"master_apprentice_already_enough"),

    PK_USER_NOT_NULL(1506,"pk_user_not_null"),
    PK_NOT_EXIST(1507,"pk_not_exist"),
    PK_MODEL_CLOSED(1505,"pk_model_closed"),
    PK_HAS_EXIST(1507,"pk_has_exist"),
    PK_TEAM_ERROR(1508,"pk_team_error"),
    PK_SATUS_ERROR(1509,"pk_satus_error"),

    HALL_NAME_IS_SENSITIVE(90101, "hall_name_is_sensitive"),
    HALL_NOT_JOIN(90102, "hall_not_join"),
    ALREADY_IN_HALL(90103, "already_in_hall"),
    ALREADY_IN_OTHER_HALL(90104, "already_in_other_hall"),
    HALL_MEMBER_MAX(90105, "hall_member_max"),
    HALL_NOT_EXIST(90106, "hall_not_exist"),
    HAS_ONE_HALL(90107, "has_one_hall"),
    HALL_ALREADY_APPLY(90108, "hall_already_apply"),
    HALL_QUIT_LIMIT(90109, "hall_quit_limit"),
    HALL_OWNER_NOT_ALLOW_QUIT(90110, "hall_owner_not_allow_quit"),
    HALL_PERMISSION_DENIED(90111, "hall_permission_denied"),
    HALL_TARGET_NOT_JOIN(90112, "hall_target_not_join"),
    HAS_QUIT_HALL_APPLY(90113, "has_quit_hall_apply"),
    HAVE_JOIN_IN_HALL(90114, "have_join_in_hall"),
    HALL_MANAGER_COUNT_LIMIT(90115, "hall_manager_count_limit"),

    HALL_LIMIT_MAX_CHAT(90116, "hall_limit_max_chat"),
    HALL_GROUP_NAME_IS_SENSITIVE(90117, "hall_group_name_is_sensitive"),
    HALL_CHAT_NOT_EXIST(90118, "hall_chat_not_exist"),
    HALL_LIMIT_MAX_MEMBER(90119, "hall_limit_max_member"),
    REMOVE_HALL_CHAT_FAILED(90119, "remove_hall_chat_failed"),
    HALL_ALREADY_INVITE(90120, "hall_already_invite"),
    HALL_OPERATE_RECORD_EXPIRE(90121, "hall_operate_record_expire"),
    HALL_OPERATE_RECORD_DEAL(90122, "hall_operate_record_deal"),
    HALL_IS_MANAGER(90123, "hall_is_manager"),
    HALL_IS_MEMBER(90124, "hall_is_member"),
    HALL_APPLY_LIMIT(90125, "hall_apply_limit"),
    HALL_CREATE_GROUP_FAIL(90126, "hall_create_group_fail"),
    HALL_JOIN_PRIVATE_CHAT(90127, "hall_join_private_chat"),
    HALL_HAS_IN_CHAT(90128, "hall_has_in_chat"),
    HALL_CHAT_MANAGER_TOO_MANY(90129, "hall_chat_manager_too_many"),


    HALL_MANAGER_NOT_FOUND_MENU_AUTH(80789, "hall_manager_not_found_menu_auth"),
    HALL_MANAGER_NOT_FOUND(80790, "hall_manager_not_found"),
    HALL_MANAGER_NOT_BELONG(80791, "hall_manager_not_belong"),
    HALL_MEMBER_OWNER_NOT_FOUND(80604, "hall_member_owner_not_found"),

    HALL_GIFT_SEND_RECORD_NULL(80601, "hall_gift_send_record_null"),
    HALL_GIFT_SEND_RECORD_DB_NULL(80602, "hall_gift_send_record_db_null"),
    HALL_INCOME_SEL_TIME_ERROR(80603, "hall_income_sel_time_error"),

    ROOM_NOT_EXIST(1601,"room_not_exist_1"),
    ROOM_IS_PUBLIC(1602,"room_is_public"),
    ROOM_IN_FRIEND_LIMIT(1603,"room_in_friend_limit"),
    ROOM_IN_INVITE_LIMIT(1604,"room_in_invite_limit"),
    ROOM_IOS_VERSION(1605,"room_ios_version"),
    ONLINE_SENSATION_NOT_EXIST(80604, "online_sensation_not_exist"),

    H5_EXCHANGE_OUT_OF_LIMIT(6619,"h5_exchange_out_of_limit"),

    LIKE_UID_ERROR(80605, "like_uid_error"),
    LIKE_COUNT_OUT_OF_MAX(80606, "like_count_out_of_max"),
    LIKE__OUT_OF_TIME(80607, "like__out_of_time"),

    RECOMMEND_STOCK_NOT_ENOUGH(7567001, "recommend_stock_not_enough"),
    NOT_FOUND_AVAILABLE_RECOMMEND_CARD(7567002, "not_found_available_recommend_card"),
    RECOMMEND_ALREADY_APPLY(7567004, "recommend_already_apply"),
    RECOMMEND_APPLY_TIME_ERROR(7567003, "recommend_apply_time_error"),

    GAME_INVITE_EXPIRED(20001,"game_invite_expired"),
    GAME_INVITE_INVALID(20002,"game_invite_invalid"),
    GAME_INVITE_OVER(20003,"game_invite_over"),
    GAME_INVITE_BEGUN(20004,"game_invite_begun"),
    EMOJI_CODE_NULL(90130, "emoji_code_null"),
    EMOJI_CODE_NOTEXIST(90131, "emoji_code_null"),
    USER_ALREADY_IN_HALL(90132, "user_already_in_hall"),

    OUT_OF_LIMIT(80608,"out_of_limit"),

    ACT_AWARD_HAS_GET(80609, "act_award_has_get"),
    ACT_AWARD_NOT_ACCORD(80610, "act_award_not_accord"),

    PHONE_NO_BIND(30001, "phone_no_bind"),
    USER_NO_CERTIFY(30002, "user_no_certify"),
    FANS_COUNT_INSUFFICIENT(30003, "fans_count_insufficient"),
    SKILL_NO_ACTIVATE(30004,"skill_no_activate"),
    CHOOSE_SKILL(30005, "choose_skill"),
    BANNER_ROOM_NOT_EXISTS(20000, "banner_room_not_exists"),

    SEND_GIFT_NUM_TOO_LARGE(8005,"send_gift_num_too_large"),

    MISSION_SYS_NOT_COMPLETE(892001, "mission_sys_not_complete"),
    MISSION_SYS_NOT_EXISTS(892002, "mission_sys_not_exists"),
    MISSION_SYS_NOT_DEVICEID_LIMIT(892003, "mission_sys_not_deviceid_limit"),
    MISSION_SYS_VERSION_LIMIT(892004, "mission_sys_version_limit"),

    WALLET_AMOUNT_NOT_ENOUGH(881201, "wallet_amount_not_enough"),

    GOLD_PRIZE_NOT_FOUND(20001, "gold_prize_not_found"),
    GOLD_PRIZE_FOUND(20002, "gold_prize_found"),
    GOLD_PRIZE_POOL_NOT_FOUND(20003, "gold_prize_pool_not_found"),
    GOLD_PRIZE_POOL_FOUND(20004, "gold_prize_pool_found"),
    LOCK_FAIL(20005, "lock_fail"),
    PRIZE_GOLD_POOL_RATIO_EXCEED(20006, "prize_gold_pool_ratio_exceed"),
    USER_DRAW_STATIS_NOT_FOUND(20007, "user_draw_statis_not_found"),
    DRAW_GOLD_SWITCH_CLOSE(20008, "draw_gold_switch_close"),
    DISSATISFY_DRAW(20009, "dissatisfy_draw"),
    ALREADY_DRAW(20010, "already_draw"),
    DRAW_GOLD_TO_MORE(20011, "draw_gold_to_more"),
    PRIZE_GOLD_POOL_NULL(20012,"prize_gold_pool_null"),
    PRIZE_GOLD_POOL_DEF_NULL(20013,"prize_gold_pool_def_null"),
    GOLD_PRIZE_NULL(20014, "gold_prize_null"),
    TODAY_USER_ALREADY_SIGN(20015, "today_user_already_sign"),

    ALREADY_HAS_CONFIG(91000, "already_has_config"),

    SIGN_REWARD_CONFIG_NULL(20016, "sign_reward_config_null"),
    ADD_GIFT_TYPE_REWARD_RECORD_ERROR(20017, "add_gift_type_reward_record_error"),
    SIGN_TO_MORE(20018, "sign_to_more"),
    SIGN_EXCEPTION(20019, "sign_exception"),
    USER_SIGN_ROUND_NULL(20020, "user_sign_round_null"),
    SIGN_REWARD_CONFIG_SIGN_DAYS_NULL(20021, "sign_reward_config_sign_days_null"),
    SIGN_REWARD_CONFIG_NOT_TOTAL(20022, "sign_reward_config_not_total"),
    TOTAL_SIGN_DYAS_SHORT(20023, "total_sign_dyas_short"),
    TOTAL_REWARD_RECORD_EXISTS(20023, "total_reward_record_exists"),
    SIGN_REWARD_CONFIG_ICON_NULL(20024, "sign_reward_config_icon_null"),
    SIGN_REWARD_CONFIG_TOTAL_EXCEED(20025, "sign_reward_config_total_exceed"),
    HANDLER_SIGN_EXCEPTION(20026, "handler_sign_exception"),
    RECEIVE_TOTAL_REWARD_EXCEPTION(20027,"receive_total_reward_exception"),
    DRAW_GOLD_EXCEPTION(20028,"draw_gold_exception"),



    ACCOUNT_NOT_EXIST(12000,"account_not_exist"),
    THRID_ACCOUNT_BIND_EXCEPTION(1201,"thrid_account_bind_exception"),
    THRID_ACCOUNT_UNBIND_EXCEPTION(12002,"thrid_account_unbind_exception"),
    THRID_ACCOUNT_BINDINFO_EXCEPTION(12003,"thrid_account_bindinfo_exception"),
    PHONE_NOT_BIND(12004,"phone_not_bind"),
    THRID_ACCOUNT_ONLY(12005,"thrid_account_only"),

    ROOM_MEMBER_EXISTS(12200,"room_member_exists"),
    ROOM_MEMBER_MAX(12201,"room_member_max"),
    ROOM_MEMBER_APPLY_EXISTS(12202,"room_member_apply_exists"),
    ROOM_MEMBER_EXCEPTION(12203,"room_member_exception"),
    ROOM_MEMBER_MANAGER_LEAVE(12204,"room_member_manager_leave"),

    BACKGROUND_NOT_EXISTS(66710, "background_not_exists"),
    BACKGROUND_SOLD_OUT(66711, "background_sold_out"),
    BACKGROUND_NOT_BUY(66712, "background_not_buy"),
    BACKGROUND_EXPIRED(66712, "background_expired"),
    GOOGLE_PAY_SIGNTURE_FAILURE(11000 ,"google_pay_signture_failure"),//签名验证失败
    GOOGLE_PAY_PURCHASESTATE_FAILURE(11002 ,"google_pay_purchasestate_failure"),
    GOOGLE_PAY_ORDER_FAILURE(11003 ,"google_pay_order_failure"),
    GOOGLE_PAY_ORDER_PARAM_FAILURE(11004 ,"google_pay_order_param_failure"),
    GOOGLE_PAY_PAYLOAD_FAILURE(11005 ,"google_pay_payload_failure"),//developerPayload校验失败
    ORDER_NO_EXISTS(11006 ,"order_no_exists"),
    PAY_PRO_NO_EXISTS(11007 ,"pay_pro_no_exists"),
    PAY_MONEY_UN_EQUELS(11008 ,"pay_money_un_equels"),
    ORDER_FINISHED(11009,"order_finished"),
    ORDER_TIMEOUT(11010,"order_timeout"),
    ORDER_ERROR(11011,"order_error"),
    CHARGE_ERROR(11012,"charge_error"),
    GOOGLE_PAY_PROD_DIFF(11013,"google_pay_prod_diff"),
    GOOGLE_PAY_CONSUMED(11014,"google_pay_consumed"),
    APPLE_PAY_RECEIPT_FAILED(11004,"apple_pay_receipt_failed"),
    GOOGLE_PAY_DIFF(11004,"Google order repeatedlly"),


    ACCESS_DENIED(100,"access denied"), //拒绝访问
    INVALID_REQUEST(101,"invalid request"), //请求不合法
    INVALID_REQUEST_SCHEME(102,"invalid request scheme"), //错误的请求协议
    INVALID_REQUEST_METHOD(103,"invalid request method"), //错误的请求方法
    INVALID_CLIENT_ID(104,"invalid client id"), //client id不存在或已删除
    CLIENT_ID_IS_BLOCKED(105,"client id is blocked"), //client id已被禁用
    UNAUTHORIZED_CLIENT_ID(106,"unauthorized client id"), //client id未授权
    USERNAME_PASSWORD_MISMATCH(107,"username_or_password_is_wrong"), //用户名密码不匹配
    INVALID_REQUEST_SCOPE(108,"invalid request scope"), //访问的scope不合法，开发者不用太关注，一般不会出现该错误
    INVALID_USER(109,"Invalid user"), //用户不存在或已删除
    USER_HAS_BLOCKED(110,"User has blocked"), //用户已被屏蔽
    INVALID_TOKEN(111,"invalid token"), //token不存在或已被用户删除，或者用户修改了密码
    ACCESS_TOKEN_IS_MISSING(112,"access token is missing"), //未找到access_token
    ACCESS_TOKEN_HAS_EXPIRED(113,"access token has expired"), //access_token已过期
    INVALID_REQUEST_URI(114,"invalid request uri"), //请求地址未注册
    INVALID_CREDENTIAL_1(115,"invalid credential 1"), //用户未授权访问此数据
    INVALID_CREDENTIAL_2(116,"invalid credential 2"), //client id未申请此权限
    NOT_TRIAL_USER(117,"not trial user"), //未注册的测试用户
    REQUIRED_PARAMETER_IS_MISSING(118,"required parameter is missing"), //缺少参数
    INVALID_GRANT(119,"invalid grant type"),
    UNSUPPORTED_GRANT_TYPE(120,"unsupported grant type"), //错误的grant_type
    UNSUPPORTED_RESPONSE_TYPE(121,"unsupported response type"), //错误的response_type
    CLIENT_SECRET_MISMATCH(122,"client secret mismatch"), //client_secret不匹配
    REDIRECT_URI_MISMATCH(123,"redirect uri mismatch"), //redirect_uri不匹配
    INVALID_AUTHORIZATION_CODE(124,"invalid authorization code"), //authorization_code不存在或已过期
    ACCESS_TOKEN_HAS_EXPIRED_SINCE_PASSWORD_CHANGED(125,"access token has expired since password changed"), //因用户修改密码而导致access_token过期
    ACCESS_TOKEN_HAS_NOT_EXPIRED(126,"access token has not expired"), //access_token未过期;
    UNSUPPORTED_TICKET_ISSUE_TYPE(127,"unsupported ticket issue type"),
    INVALID_TICKET(128,"invalid ticket"),//ticket不存在或已过期
    TICKET_IS_MISSING(129,"ticket is missing"), //未找到ticket
    TICKET_HAS_EXPIRED(130,"ticket has expired"), //ticket过期
    TICKET_HAS_NOT_EXPIRED(131,"ticket has not expired"), //ticket未过期
    TICKET_HAS_EXPIRED_SINCE_PASSWORD_CHANGED(132,"ticket has expired since password changed"), //因为用户修改密码而ticket过期
    INVALID_SCOPE(133,"invalid scope"),
    RATE_LIMIT_EXCEEDED1(134,"rate limit exceeded 1"), //用户访问速度限制
    RATE_LIMIT_EXCEEDED2(135,"rate limit exceeded 2"), //IP访问速度限制
    INVALID_IDENTIFYING_CODE(150, "verification_code_incorrect"), //不可用的验证码
    INVALID_USERNAME(151,"Invalid username"), //用户名不合法
    USER_HAS_SIGNED_UP(152,"phonenumber_registered"), //用户名已被注册
    INVALID_RESET_CODE(153,"invalid reset code"), //重置码无效
    INVALID_NICK(161,"Invalid nick"),   //昵称不合法
    INVALID_THIRD_TOKEN(162,"invalid third token"), //第三方token不合法
    THIRD_ACCOUNT_HAVE_BIND(163,"the third account have bind"), //第三方账户已经绑定或之前已使用该账户登陆过系统
    UNBIND_OPENID_NOT_MATCH(164,"unbind openId not match error" ), //账户解绑失败
    UNBIND_MAIN_ACCOUNT(165,"unbind main account error"), //解绑主账户错误

    PWD_LESS(167,"pwd_less"), //请求不合法

    SIGN_IP_TO_OFTEN(301,"register_too_frequently"),//注册过于频繁
    SMS_IP_TO_OFTEN(302,"get_messages_frequently"),//获取短信过于频繁
    DECEIVE_ERROR(408,"account_exception"),//账号异常-设备被封
    VERSION_ERROR(409,"version_update_notice"),
    ALREADY_BOUND(1799,"account_bound"), //缺少参数
    ALREADY_BOUND_WEIXIN(1811,"account_bound"),
    MOBILE_NUMBER_NOT_EXIST(501,"mobile_number_not_exist"),

    PHONENUMBER_REGISTERED(3000,"phonenumber_registered"),
    YOU_HAVE_NOT_BOUND_MOBILE_NUMBER(3001,"you_have_not_bound_mobile_number"),
    PHONE_NUMBER_EXISTS(3002,"phone_number_exists"),
    REGISTERED_ACCOUNT_ABNORMAL(3003,"registered_account_abnormal"),
    PASSWORD_EMPTY(3004,"password_empty"),
    PARAMTER_ERROR(3005,"paramter_error"),
    ALREADY_RECEIVED_COINS(3006,"already_received_coins"),
    PHONE_HAS_BEEN_BOUND(3007,"phone_has_been_bound"),
    FAILED_TO_POST_A_WISH(3008,"failed_to_post_a_wish"),
    REDEMPTION_CODE_INCORRECT(3009,"redemption_code_incorrect"),

    MISSION_SYS_EXPER_LEVEL_LIMIT(892005, "未达到指定等级"),

    START_MORA(50001,"start_mora"),
    PARTICIPATE_MORA(50002,"participate_mora"),
    MORA_EXPIRES(50003,"mora_expires"),
    WINNING_MORA(50004,"winning_mora"),

    GAME_EXPIRED(50011,"the_game_expired"),
    GAME_ENDED(50012,"the_game_ended"),

    user_not_alternative_charge(50013,"userNotAlternativeCharge"),
    BADGE_ALREADY_DRAW(50014,"badge_already_draw"),
    BADGE_EXPIRE_OR_NULL(50015,"badge_expire_or_null"),

    ACTIVITY_ENDED(50014, Constant.i18n.activityEnded),

    NULL_PHONE_NO_BIND(null, "phone_no_bind"),
    IMG_CODE_ERROR(4004, "img_code_error"),
    APP_VERSION_LOW(4006, "app_version_low"),

    GAME_WHEEL_APPLY_WAIT_NOTICE(4007, "game_wheel_apply_wait_notice"),
    GAME_WHEEL_ERROR(4008, "game_wheel_error"),
    GAME_WHEEL_NO_EXSIST(4009, "game_wheel_no_exsist"),
    GAME_WHEEL_RUNNING(4010, "game_wheel_running"),
    GAME_WHEEL_START_LESS(4011, "game_wheel_start_less"),
    GAME_WHEEL_JOIN_WAIT_NOTICE(4012, "game_wheel_join_wait_notice"),
    GAME_WHEEL_CANCEL_MORE(4013, "game_wheel_cancel_more"),
    GAME_WHEEL_CANNOT_CANCEL(4014, "game_wheel_cannot_cancel"),
    GAME_WHEEL_CANNOT_START(4015, "game_wheel_cannot_start"),
    GAME_WHEEL_FULL(4016, "game_wheel_full"),

    DIAMOND_EXCHANGE_NOBLE_BEFOER(5001, "exchanged_before"),

    DIAMOND_ACTIVITY_DRAW_ERROR(5002, "exchanged_before"),
    DIAMOND_VOTE_PRIZE_LIST_ERROR(5003, "diamond_vote_prize_list_error"),
    DIAMOND_VOTE_DRAW_WAIT_ERROR(5004, "diamond_vote_draw_wait_error"),
    DIAMOND_VOTE_DRAW_ERROR(5005, "diamond_vote_draw_error"),
    DIAMOND_VOTE_DRAW_RECORD_ERROR(5006, "diamond_vote_draw_record_error"),
    DIAMOND_VOTE_DRAW_ROLL_RECORD_ERROR(5007, "diamond_vote_draw_roll_record_error"),
    DIAMOND_VOTE_INTEGRAL_NOT_ENOUGH(5008, "diamond_vote_integral_not_enough"),
    DIAMOND_VOTE_DRAW_PRIZE_NOEXIST(5009, "diamond_vote_draw_prize_noexist"),
    DIAMOND_VOTE_ACTIVITY_EXPIRED(5010, "diamond_vote_activity_expired"),
    ACC_REGISTER_LIMIT_IP(5011, "Your IP registration has exceeded the limit"),
    ACC_REGISTER_LIMIT_DEVICE(5012, "Your device registration has exceeded the limit"),
    QINIU_PORN_WANRING(5013, "qiniu_porn_wanring"),

    public_chat_not_exist(6002, "public_chat_not_exists"),
    up_mic_fail(6003, "take_mic_fail"),
    kicked_user(6004, "kicked_user"),
    on_the_mic(6005, "on_the_mic"),
    down_mic_fail(6006, "down_mic_fail"),
    on_the_other_mic(6007, "on_the_mic"),
    black_user(6008, "black_user"),

    UNIQUEID_SELL_OUT(5013,"uniqueid_sell_out"),
    UNIQUEID_BUY_FAIL(5014,"uniqueid_buy_fail"),
    UNIQUEID_NO_EXIST(5015,"uniqueid_no_exist"),
    UNIQUEID_BUY_CURRENCY_ERROR(5016,"uniqueid_buy_currency_error"),
    UNIQUEID_SET_ID_ERROR(5017,"uniqueid_set_id_error"),
    UNIQUEID_INVALID(5018,"uniqueid_invalid"),
    UNIQUEID_NOT_USED(5019,"uniqueid_not_used"),
    UNIQUEID_NOT_THE_SAME_ID(5020,"uniqueid_not_the_same_id"),
    UNIQUEID_CANCEL_FAIL(5021,"uniqueid_cancel_fail"),
    UNIQUEID_NOT_BELONG_USER(5022,"uniqueid_not_belong_user"),

    CRYSTAL_NOT_ENOUGH(5031, "crystal_not_enough"),
    CRYSTAL_NO_EXCHANEG(5032, "crystal_no_exchaneg"),
    REGISTER_APPLE_ERROR(5033, "Failed to login by AppleID."),
    REGISTER_APPLE_INVALID_IDENTITYTOKEN_ERROR(5034, "invalid token"),
    REGISTER_APPLE_INVALID_AUTHORIZATIONCODE_ERROR(5035, "invalid code"),
    REGISTER_APPLE_INVALID_IDTOKEN_ERROR(5036, "invalid idToken"),


    INVALID_ACTIVITY_CONFIG_ERROR(5037, "invalid config setting"),

    PORN_IMG_WARNING(5038, "porn_img_warning"),

    USER_FEEDBACK_LIMITER_ERROR(5039, "Please take a break and feedback."),
    INVALID_INVITE_CODE(5040, "invalid invite code."),

    ACTIVITY_USER_INVITE_STOP(5043, "Activity of the user invite is stop."),

    //房间正在Pk中
    ROOM_PKING(5051,"room_pking"),
    //不能将PK改成Pk中
    CANT_LAUNCH_PK(5052,"cant_launch_pk"),
    //Pk取消失败
    PK_CANCEL_FAIL(5053,"PK_CANCEL_FAIL"),
    //房间已发起邀请Pk
    ROOM_PLEASE_PK(5054,"room_please_pk"),
    //房间PK已经取消
    ROOM_PK_ALREADY_CANCEL(5055,"room_pk_already_cancel"),
    //房间PK人数不够
    ROOM_PK_MANAGER_NOT_ENOUGH(5056,"room_pk_manager_not_enough"),
    //想要pk超过今天次数
    ROOM_PK_WANTPK_NOT_ENOUGH(5057,"pk_today_wantPk_excessive"),
    //房间发起pk频繁
    ROOM_PK_CHOOSE_PK_BUSY(5058,"room_pk_choose_pk_busy"),
    //麦位人数不够
    MIC_USER_NOT_ENOUGH(5059,"mic_user_not_enough"),
    //今天超过被拒绝次数
    PK_TODAY_REJECT_EXCESSIVE(5060,"pk_today_reject_excessive"),

    VIRTUAL_ITEM_NOT_ENOUGH(900001,"virtual item not enough."),


    NOTICE_MSG_FIREBASE_USER_NOT_REGIST(5061,"not find,user's register token!"),
    NOTICE_MSG_FIREBASE_SUB_ERROE(5062,"Failed to subscribe firebase topic!"),
    NOTICE_MSG_FIREBASE_UNSUB_ERROE(5063,"Failed to unsubscribe firebase topic!"),
    ACTIVITY_EXPIRED_ERROR(5046, "The current time is not active time, can not receive the reward"),
    ACTIVITY_REPEAT_PRIZE_SEND(5048, "You already received the task reward, can't be repeated"),
    ACTIVITY_TARGET_NOT_ENOUGH(5047, "Your quantity isn't enough, please continue to complete the task"),
    ;


    private final Integer value;

    private final String reasonPhrase;

    private BusiStatus(Integer value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }



    public Integer value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public Integer getCode() {
        return this.value;
    }

    @Override
    public String getMsg() {
        return this.reasonPhrase;
    }
}