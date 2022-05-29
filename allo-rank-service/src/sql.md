-- 榜单相关

-- ----------------------------
-- Table structure for act_center_activity
-- ----------------------------
CREATE TABLE `act_center_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) DEFAULT NULL COMMENT 'app中文名称',
  `app_key` varchar(30) DEFAULT NULL COMMENT 'app英文标识',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for act_center_rank_config
-- ----------------------------
CREATE TABLE `act_center_rank_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `app_key` varchar(30) DEFAULT NULL COMMENT 'appKey',
  `name` varchar(255) DEFAULT NULL COMMENT '榜单名',
  `rank_key` varchar(60) DEFAULT NULL COMMENT '榜单key(用于生成榜单redis key 不能重复)',
  `data_source_id` int(11) DEFAULT NULL COMMENT '数据来源（只支持单选）',
  `time_type` int(2) DEFAULT NULL COMMENT '榜单时间类型 1小时、2日、3周、4月、5年、99不限时',
  `calc_type` int(1) DEFAULT NULL COMMENT '计算方式（1按分值累计、2按次累计）',
  `divide_by_biz_id` tinyint(1) DEFAULT NULL COMMENT '是否需要分割榜单（如果需要，将根据数据中的bizId进行榜单分割，可用于实现周星榜类型这样由多个礼物的榜单组成的复合榜单）',
  `member_type` int(11) DEFAULT NULL COMMENT '成员类型(1用户，2频道)',
  `specify_room_type` tinyint(1) DEFAULT NULL COMMENT '是否区分频道分类',
  `room_type_id` int(11) DEFAULT NULL COMMENT '频道分类id 为空则记录全部分类',
  `direction` int(1) DEFAULT NULL COMMENT '方向(0无方向 1接收方的榜单 2发送方的榜单  频道榜不存在方向的概念)',
  `gen_relation` tinyint(1) DEFAULT NULL COMMENT '是否生成关联榜单（对于用户榜：关联榜单是每个人的反方向子榜，对于频道榜：关联榜单是频道内送礼and收礼榜）',
  `start_time` datetime DEFAULT NULL COMMENT '榜单记录开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '榜单记录结束时间',
  `expire_unit` int(1) DEFAULT NULL COMMENT '过期时间单位(1秒 2分 3时 4日 5周 6月 7年)',
  `expire_value` int(11) DEFAULT NULL COMMENT '过期时间值',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_unique_appId_rankKey` (`app_key`,`rank_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for act_center_rank_data_record
-- ----------------------------
CREATE TABLE `act_center_rank_data_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `app_key` varchar(30) DEFAULT NULL COMMENT 'app英文标识key，标识是哪个app的数据',
  `data_source_key` varchar(30) DEFAULT NULL COMMENT '数据源key，标识是哪个数据源的数据',
  `data_source_secret` varchar(255) DEFAULT NULL COMMENT '数据源密钥，创建数据源时生成，用于check数据合法性',
  `send_id` varchar(50) DEFAULT NULL COMMENT '发送id',
  `recv_id` varchar(50) DEFAULT NULL COMMENT '接受id',
  `count` int(10) DEFAULT '1' COMMENT '个数',
  `score` decimal(20,2) DEFAULT NULL COMMENT '分值',
  `room_id` varchar(50) DEFAULT NULL COMMENT '频道id(非必需，目前仅礼物流水需要)',
  `room_type` int(11) DEFAULT NULL COMMENT '房间类型(非必需，目前仅礼物流水需要)',
  `biz_id` varchar(255) DEFAULT NULL COMMENT '分割id(非必需，如礼物流水中的礼物id，或者接唱点赞流水中的歌曲id)',
  `timestamp` bigint(20) DEFAULT NULL COMMENT '时间戳(用来判断时效性)',
  `check_pass` int(1) DEFAULT NULL COMMENT '数据是否合法 1合法 0不合法',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_app_ds_checkpass` (`app_key`,`data_source_key`,`check_pass`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `act_center_rank_data_record_allo_2020_07` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `app_key` varchar(30) DEFAULT NULL COMMENT 'app英文标识key，标识是哪个app的数据',
  `data_source_key` varchar(30) DEFAULT NULL COMMENT '数据源key，标识是哪个数据源的数据',
  `data_source_secret` varchar(255) DEFAULT NULL COMMENT '数据源密钥，创建数据源时生成，用于check数据合法性',
  `send_id` varchar(50) DEFAULT NULL COMMENT '发送id',
  `recv_id` varchar(50) DEFAULT NULL COMMENT '接受id',
  `count` int(10) DEFAULT '1' COMMENT '个数',
  `score` decimal(20,2) DEFAULT NULL COMMENT '分值',
  `room_id` varchar(50) DEFAULT NULL COMMENT '频道id(非必需，目前仅礼物流水需要)',
  `room_type` int(11) DEFAULT NULL COMMENT '房间类型(非必需，目前仅礼物流水需要)',
  `biz_id` varchar(255) DEFAULT NULL COMMENT '分割id(非必需，如礼物流水中的礼物id，或者接唱点赞流水中的歌曲id)',
  `timestamp` bigint(20) DEFAULT NULL COMMENT '时间戳(用来判断时效性)',
  `check_pass` int(1) DEFAULT NULL COMMENT '数据是否合法 1合法 0不合法',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_app_ds_checkpass` (`app_key`,`data_source_key`,`check_pass`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for act_center_rank_datasource_config
-- ----------------------------
CREATE TABLE `act_center_rank_datasource_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `app_id` int(11) DEFAULT NULL COMMENT 'appid',
  `app_key` varchar(30) DEFAULT NULL COMMENT 'appkey',
  `name` varchar(100) DEFAULT NULL COMMENT '数据源名称',
  `data_source_key` varchar(30) DEFAULT NULL COMMENT '数据源key',
  `secret` varchar(255) DEFAULT NULL COMMENT 'appid+appkey+key+ctime md5加盐后的结果 用于过滤非法数据',
  `type` int(2) DEFAULT '1' COMMENT '数据源类型(用于数据具体值校验)：1礼物流水、2礼物流水不带频道id、3无发送方(如接唱成功)、4无接收方(神兽战攻击怪兽)',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `appkey_dskey_uniq` (`app_key`,`data_source_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for act_center_rank_room_type
-- ----------------------------
CREATE TABLE `act_center_rank_room_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `app_id` int(11) DEFAULT NULL COMMENT 'appid',
  `room_type_id` varchar(50) DEFAULT NULL COMMENT '频道类型id',
  `room_type_name` varchar(255) DEFAULT NULL COMMENT '频道类型名称',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for act_center_rank_score_calc_config
-- ----------------------------
CREATE TABLE `act_center_rank_score_calc_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rank_id` bigint(20) DEFAULT NULL COMMENT '榜单id',
  `coefficient` decimal(10,2) DEFAULT NULL COMMENT '系数（用来乘以原分值得到最终分值）',
  `biz_id` varchar(255) DEFAULT NULL COMMENT '生效业务id(用来实现只对指定礼物分值加倍类型的需求)',
  `start_time` datetime DEFAULT NULL COMMENT '生效开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '生效结束时间',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for act_center_rank_score_notice_config
-- ----------------------------
CREATE TABLE `act_center_rank_score_notice_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `rank_id` bigint(20) DEFAULT NULL COMMENT '榜单id',
  `score_list` varchar(255) DEFAULT NULL COMMENT '分值列表(多个用逗号分隔，达到这些分值时触发分值提醒事件)',
  `score_step` int(11) DEFAULT NULL COMMENT '分值步长（每达到步长分值时触发分值提醒）',
  `start_time` datetime DEFAULT NULL COMMENT '生效开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '生效结束时间',
  `ctime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for act_center_rank_white_black_list
-- ----------------------------
CREATE TABLE `act_center_rank_white_black_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `scope` int(1) DEFAULT NULL COMMENT '生效域（1app全局域 2指定榜单域 全局域优先级高于榜单域）',
  `app_id` int(11) DEFAULT NULL COMMENT 'appid',
  `rank_id` bigint(20) DEFAULT NULL COMMENT '榜单id（app全局域时该字段值为null）',
  `white_black` int(1) DEFAULT NULL COMMENT '白名单or黑名单（同一个rank_data_id不能同时存在于白名单和黑名单）',
  `rank_data_id` mediumtext COMMENT '名单id（根据榜单的方向取recvId或者sendId来判断是否存在于该列表，存在才记录(白名单)or不记录(黑名单)）',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `act_center_rank_biz_id` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rank_id` bigint(20) NOT NULL,
  `biz_ids` varchar(250) NOT NULL COMMENT '业务id列表，用英文逗号分隔',
  PRIMARY KEY (`id`),
  UNIQUE KEY `rank_id_uq` (`rank_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `act_center_rank_auto_reward_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rank_id` bigint(20) NOT NULL COMMENT '榜单id',
  `award_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '奖励类型：0连续；1 一次性（总榜）',
  `award_package_id` bigint(20) NOT NULL COMMENT '奖励礼包id',
  `begin` tinyint(4) NOT NULL COMMENT '开始名次（包含）',
  `end` tinyint(4) NOT NULL COMMENT '结束名次（包含）',
  `limit_score` bigint(20) DEFAULT '0' COMMENT '门槛',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `rankId_idx` (`rank_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `act_center_rank_auto_reward_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rank_id` bigint(20) NOT NULL COMMENT '榜单id',
  `rank_key` varchar(100) NOT NULL COMMENT '榜单key，用于区分连续性分时段的榜单',
  `award_config_id` bigint(20) NOT NULL COMMENT '榜单奖励配置id',
  `member_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '获奖成员类型1用户；2房间',
  `member_id` varchar(50) NOT NULL COMMENT '获奖成员id',
  `award_result` tinyint(4) NOT NULL COMMENT '发放结果 0失败；1成功',
  `award_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `rankId_idx` (`rank_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- 礼包相关
-- ----------------------------
-- Table structure for common_award
-- ----------------------------
CREATE TABLE `common_award` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(60) DEFAULT NULL COMMENT '奖励名称',
  `icon` varchar(100) DEFAULT NULL COMMENT '奖励图片',
  `release_type` int(3) DEFAULT NULL COMMENT '奖励发放类型 1礼物 2头像框 3座驾 4勋章 5金币 6贵族 7背景 8 虚拟道具 999实物 -1空奖励',
  `release_id` varchar(30) DEFAULT NULL COMMENT '奖励发放对应id',
  `price` int(10) DEFAULT NULL COMMENT '价值（用于查询统计）',
  `price_display` int(10) DEFAULT NULL COMMENT '显示价值',
  `extend` varchar(255) DEFAULT NULL COMMENT '扩展字段',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for common_award_of_package
-- ----------------------------
CREATE TABLE `common_award_of_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `package_id` int(11) DEFAULT NULL COMMENT '礼包id',
  `award_id` int(11) DEFAULT NULL COMMENT '奖励id',
  `act_id` int(10) DEFAULT NULL COMMENT '活动id',
  `rate` decimal(10,2) DEFAULT NULL COMMENT '概率',
  `display_rate` decimal(10,2) DEFAULT NULL COMMENT '展示概率',
  `daily_count_limit` int(10) DEFAULT '0' COMMENT '每日中奖上限',
  `total_count` int(10) DEFAULT '-1' COMMENT '总库存数量 -1为无限',
  `release_count` int(10) DEFAULT '0' COMMENT '发放数量',
  `award_count` int(10) DEFAULT '1' COMMENT '奖励数量(目前仅对礼物和抽奖券类型生效)',
  `valid_days` int(10) DEFAULT '0' COMMENT '有效时间(对于免费礼物、头像框类型生效 后续支持进场秀、勋章)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `enable` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for common_award_package
-- ----------------------------
CREATE TABLE `common_award_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(60) DEFAULT NULL COMMENT '礼包名称',
  `icon` varchar(100) DEFAULT NULL COMMENT '礼包图片',
  `need_rate` tinyint(1) DEFAULT '0' COMMENT '是否需要概率',
  `need_purchase` tinyint(1) DEFAULT '0' COMMENT '是否需要购买',
  `purchase_type` int(2) DEFAULT NULL COMMENT '购买类型 1金币 2道具',
  `total_count` int(10) DEFAULT '-1' COMMENT '总库存',
  `release_count` int(10) DEFAULT '0' COMMENT '发放数量',
  `day_count_limit` int(10) DEFAULT '-1' COMMENT '每日数量限制',
  `price` int(10) DEFAULT NULL COMMENT '价格',
  `enable` tinyint(4) DEFAULT '1' COMMENT '是否启用',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for common_award_release_record
-- ----------------------------
CREATE TABLE `common_award_release_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint(20) DEFAULT NULL COMMENT 'uid',
  `package_id` int(11) DEFAULT NULL COMMENT '礼包id',
  `package_name` varchar(60) DEFAULT NULL COMMENT '礼包名称备份',
  `package_icon` varchar(100) DEFAULT NULL COMMENT '礼包图片备份',
  `seq_id` varchar(100) DEFAULT NULL COMMENT '唯一序列号',
  `status` int(3) DEFAULT NULL COMMENT '0初始化 1发放成功 -1发放失败 2重试发放成功 -2重试发放失败 3支付成功 -3支付失败 -99库存不足',
  `award_back_up` mediumtext COMMENT '礼包内礼物列表json备份 ',
  `remark` varchar(1200) DEFAULT NULL COMMENT '备注',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `seqId_packageId_idx` (`package_id`,`seq_id`),
  KEY `uid_idx` (`uid`) USING BTREE,
  KEY `ctime_idx` (`ctime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `common_award_release_record_2020_07` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint(20) DEFAULT NULL COMMENT 'uid',
  `package_id` int(11) DEFAULT NULL COMMENT '礼包id',
  `package_name` varchar(60) DEFAULT NULL COMMENT '礼包名称备份',
  `package_icon` varchar(100) DEFAULT NULL COMMENT '礼包图片备份',
  `seq_id` varchar(100) DEFAULT NULL COMMENT '唯一序列号',
  `status` int(3) DEFAULT NULL COMMENT '0初始化 1发放成功 -1发放失败 2重试发放成功 -2重试发放失败 3支付成功 -3支付失败 -99库存不足',
  `award_back_up` mediumtext COMMENT '礼包内礼物列表json备份 ',
  `remark` varchar(1200) DEFAULT NULL COMMENT '备注',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `seqId_packageId_idx` (`package_id`,`seq_id`),
  KEY `uid_idx` (`uid`) USING BTREE,
  KEY `ctime_idx` (`ctime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `common_award_release_record_2020_08` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint(20) DEFAULT NULL COMMENT 'uid',
  `package_id` int(11) DEFAULT NULL COMMENT '礼包id',
  `package_name` varchar(60) DEFAULT NULL COMMENT '礼包名称备份',
  `package_icon` varchar(100) DEFAULT NULL COMMENT '礼包图片备份',
  `seq_id` varchar(100) DEFAULT NULL COMMENT '唯一序列号',
  `status` int(3) DEFAULT NULL COMMENT '0初始化 1发放成功 -1发放失败 2重试发放成功 -2重试发放失败 3支付成功 -3支付失败 -99库存不足',
  `award_back_up` mediumtext COMMENT '礼包内礼物列表json备份 ',
  `remark` varchar(1200) DEFAULT NULL COMMENT '备注',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `seqId_packageId_idx` (`package_id`,`seq_id`),
  KEY `uid_idx` (`uid`) USING BTREE,
  KEY `ctime_idx` (`ctime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for common_award_release_record_detail
-- ----------------------------
CREATE TABLE `common_award_release_record_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint(20) DEFAULT NULL COMMENT 'uid',
  `seq_id` varchar(100) DEFAULT NULL COMMENT '唯一序列号',
  `award_id` int(11) DEFAULT NULL COMMENT '奖励id备份',
  `award_of_package_id` int(10) DEFAULT NULL COMMENT '礼包与礼物的关联id',
  `award_name` varchar(60) DEFAULT NULL COMMENT '奖励名称备份',
  `award_icon` varchar(100) DEFAULT NULL COMMENT '奖励图片备份',
  `award_release_type` int(3) DEFAULT NULL COMMENT '奖励类型备份',
  `award_release_id` varchar(30) DEFAULT NULL COMMENT '奖励对应id备份',
  `award_count` int(10) DEFAULT NULL COMMENT '奖励数量备份',
  `award_valid_days` int(10) DEFAULT NULL COMMENT '奖励有效时间备份',
  `award_price` int(10) DEFAULT NULL COMMENT '奖励价值备份',
  `award_extend` varchar(255) DEFAULT NULL COMMENT '奖励扩展字段备份',
  `thirdparty_seq_id` varchar(100) DEFAULT NULL COMMENT '外部seqid 外部接口调用失败时用于重试，保证幂等',
  `status` int(3) DEFAULT NULL COMMENT '0初始化 1发放成功 -1发放失败 2重试发放成功 -2重试发放失败 3支付成功 -3支付失败 -99库存不足',
  `remark` varchar(1200) DEFAULT NULL COMMENT '备注',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `seqId_awardofpackageidx` (`seq_id`,`award_of_package_id`),
  KEY `uid_idx` (`uid`) USING BTREE,
  KEY `ctime_idx` (`ctime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `common_award_release_record_2020_07` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint(20) DEFAULT NULL COMMENT 'uid',
  `package_id` int(11) DEFAULT NULL COMMENT '礼包id',
  `package_name` varchar(60) DEFAULT NULL COMMENT '礼包名称备份',
  `package_icon` varchar(100) DEFAULT NULL COMMENT '礼包图片备份',
  `seq_id` varchar(100) DEFAULT NULL COMMENT '唯一序列号',
  `status` int(3) DEFAULT NULL COMMENT '0初始化 1发放成功 -1发放失败 2重试发放成功 -2重试发放失败 3支付成功 -3支付失败 -99库存不足',
  `award_back_up` mediumtext COMMENT '礼包内礼物列表json备份 ',
  `remark` varchar(1200) DEFAULT NULL COMMENT '备注',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `seqId_packageId_idx` (`package_id`,`seq_id`),
  KEY `uid_idx` (`uid`) USING BTREE,
  KEY `ctime_idx` (`ctime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `common_award_release_record_2020_08` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint(20) DEFAULT NULL COMMENT 'uid',
  `package_id` int(11) DEFAULT NULL COMMENT '礼包id',
  `package_name` varchar(60) DEFAULT NULL COMMENT '礼包名称备份',
  `package_icon` varchar(100) DEFAULT NULL COMMENT '礼包图片备份',
  `seq_id` varchar(100) DEFAULT NULL COMMENT '唯一序列号',
  `status` int(3) DEFAULT NULL COMMENT '0初始化 1发放成功 -1发放失败 2重试发放成功 -2重试发放失败 3支付成功 -3支付失败 -99库存不足',
  `award_back_up` mediumtext COMMENT '礼包内礼物列表json备份 ',
  `remark` varchar(1200) DEFAULT NULL COMMENT '备注',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `seqId_packageId_idx` (`package_id`,`seq_id`),
  KEY `uid_idx` (`uid`) USING BTREE,
  KEY `ctime_idx` (`ctime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- 投注相关
-- 活动配置
CREATE TABLE `bet_act_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '活动名称',
  `game_duration` tinyint(4) NOT NULL DEFAULT '90' COMMENT '投注时长，单位秒',
  `trans_animation_duration` tinyint(4) NOT NULL DEFAULT '3' COMMENT '转场动画时长，单位秒',
  `animation_duration` tinyint(4) NOT NULL DEFAULT '15' COMMENT '动画时长，单位秒',
  `show_duration` tinyint(4) NOT NULL COMMENT '结果展示时长，单位秒',
  `stime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '开始时间',
  `etime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '结束时间',
  `banker_model` tinyint(4) NOT NULL DEFAULT '0' COMMENT '坐庄模式：0官方；1无庄',
  `bet_model` tinyint(4) NOT NULL COMMENT '投注模式：0单次；1多次',
  `animation_model` tinyint(4) NOT NULL DEFAULT '0' COMMENT '动画模式：0赛车类；',
  `warn_score` bigint(20) NOT NULL DEFAULT '0' COMMENT '告警分值：官方坐庄有效，0不告警，大于0，触发告警，亏损超过此值重新选择获胜方',
  `deduct_rate` double NOT NULL DEFAULT '0' COMMENT '抽成比例：无庄模式有效，默认0，一般配置小于1的两位小数',
  `pay_type` tinyint(4) DEFAULT '1' COMMENT '支付方式：1金币',
  `join_spirit_num` tinyint(4) NOT NULL DEFAULT '2' COMMENT '随机上场精灵数量',
  `pay_prices` varchar(200) NOT NULL DEFAULT '100,500,1000,2000' COMMENT '支付金额列表，逗号分隔',
  `remark` varchar(255) DEFAULT NULL COMMENT '活动备注信息',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

//奖励配置表
CREATE TABLE `bet_award_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_id` bigint(20) NOT NULL COMMENT '活动id',
  `package_id` bigint(20) NOT NULL COMMENT '礼包组件的礼包id',
  `award_target` tinyint(4) NOT NULL DEFAULT '1' COMMENT '奖励目标：1胜方;2败方',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- 投注精灵表
CREATE TABLE `bet_spirit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '精灵名字',
  `url` varchar(150) DEFAULT NULL COMMENT '精灵图片',
  `svga_url` varchar(150) DEFAULT NULL COMMENT '精灵svga资源',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- 投注精灵配置表
CREATE TABLE `bet_spirit_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_id` bigint(20) NOT NULL COMMENT '活动id',
  `spirit_id` bigint(20) NOT NULL COMMENT '参与活动精灵id',
  `rate` double NOT NULL DEFAULT '20' COMMENT '获胜概率',
  `odds` double NOT NULL DEFAULT '1' COMMENT '固定赔率',
  `win_num` int(11) NOT NULL DEFAULT '0' COMMENT '获胜次数',
  `loss_num` int(11) NOT NULL DEFAULT '0' COMMENT '失败次数',
  `total_amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '投注总数',
  `total_award_amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '获胜奖金（包括本金）',
  `ctime` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- 投注详情表(按月分表）
CREATE TABLE `bet_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_id` bigint(20) NOT NULL COMMENT '活动id',
  `game_id` bigint(20) NOT NULL COMMENT '游戏id',
  `spirit_id` bigint(20) NOT NULL COMMENT '所投精灵id',
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `pay_price` int(11) NOT NULL DEFAULT '0' COMMENT '付款价格',
  `pay_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态：0初始；1成功；-1失败',
  `pay_seq_id` varchar(50) NOT NULL COMMENT '支付唯一序号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ctime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `actId_gameId_payStatus_idx` (`act_id`,`game_id`,`pay_status`) USING BTREE,
  KEY `uid_ctime_idx` (`uid`,`ctime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 投注结果表（按月分表）
CREATE TABLE `bet_detail_2020_07` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_id` bigint(20) NOT NULL COMMENT '活动id',
  `game_id` bigint(20) NOT NULL COMMENT '游戏id',
  `spirit_id` bigint(20) NOT NULL COMMENT '所投精灵id',
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `pay_price` int(11) NOT NULL DEFAULT '0' COMMENT '付款价格',
  `pay_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态：0初始；1成功；-1失败',
  `pay_seq_id` varchar(50) NOT NULL COMMENT '支付唯一序号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `actId_gameId_payStatus_idx` (`act_id`,`game_id`,`pay_status`) USING BTREE,
  KEY `uid_ctime_idx` (`uid`,`ctime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- 投注游戏表
CREATE TABLE `bet_game_round` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_id` bigint(20) NOT NULL COMMENT '活动id',
  `spirits` varchar(3000) NOT NULL COMMENT '参与活动精灵id',
  `win_spirit_id` bigint(20) DEFAULT NULL COMMENT '获胜精灵id',
  `bet_amounts` varchar(1500) DEFAULT NULL COMMENT '每个精灵投注数',
  `game_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '游戏状态：0游戏中；1动画中；2展示结果',
  `is_settle` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已结算：0未结算；1已结算',
  `animations` varchar(1500) NOT NULL DEFAULT '' COMMENT '动画数据，根据不同场景生成',
  `trans_animation_duration` tinyint(4) NOT NULL DEFAULT '3' COMMENT '转场动画时长，单位秒',
  `stime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '开始时间',
  `atime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '动画开始时间',
  `rtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '结果展示时间',
  `etime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '结束时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `actId_stime_idx` (`act_id`,`stime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- 结果表
CREATE TABLE `bet_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_id` bigint(20) NOT NULL COMMENT '活动id',
  `game_id` bigint(20) NOT NULL COMMENT '游戏id',
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `spirit_id` bigint(20) NOT NULL COMMENT '投注精灵id',
  `win_spirit_id` bigint(20) DEFAULT '0',
  `is_win` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否胜方：0败方；1胜方',
  `pay_total_amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '投注总额',
  `take_back_amount` bigint(20) NOT NULL DEFAULT '0',
  `award_amount` bigint(20) NOT NULL COMMENT '奖励总额',
  `award_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '发奖状态：0初始；1成功；-1失败',
  `package_content` varchar(1500) DEFAULT NULL COMMENT '奖励礼包内容',
  `animations` varchar(1500) DEFAULT '' COMMENT '动画数据，冗余游戏动画数据',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ctime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `actId_gameId_uid_ctime_idx` (`act_id`,`game_id`,`uid`,`ctime`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 月表
CREATE TABLE `bet_result_2020_07` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_id` bigint(20) NOT NULL COMMENT '活动id',
  `game_id` bigint(20) NOT NULL COMMENT '游戏id',
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `spirit_id` bigint(20) NOT NULL COMMENT '投注精灵id',
  `win_spirit_id` bigint(20) DEFAULT '0',
  `is_win` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否胜方：0败方；1胜方',
  `pay_total_amount` bigint(20) NOT NULL DEFAULT '0' COMMENT '投注总额',
  `take_back_amount` bigint(20) NOT NULL DEFAULT '0',
  `award_amount` bigint(20) NOT NULL COMMENT '奖励总额',
  `award_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '发奖状态：0初始；1成功；-1失败',
  `package_content` varchar(1500) DEFAULT NULL COMMENT '奖励礼包内容',
  `animations` varchar(1500) DEFAULT '' COMMENT '动画数据，冗余游戏动画',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ctime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `actId_gameId_uid_ctime_idx` (`act_id`,`game_id`,`uid`,`ctime`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- 统计表
CREATE TABLE `bet_statistic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_id` bigint(20) NOT NULL COMMENT '活动id',
  `total_game_rounds` int(11) NOT NULL DEFAULT '0' COMMENT '总游戏场次',
  `total_pay_amounts` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户总支出',
  `total_award_amounts` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户总获奖金额',
  `total_win_amounts` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户获胜总投注金额',
  `ctime` date NOT NULL COMMENT '统计时间',
  PRIMARY KEY (`id`),
  KEY `ctime_actId_idx` (`ctime`,`act_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


-- 活动配置
CREATE TABLE `act_center_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `app_id` int(11) DEFAULT NULL COMMENT 'appid',
  `name` varchar(255) DEFAULT NULL COMMENT '活动名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '活动描述',
  `type` int(2) DEFAULT '1' COMMENT '活动类型：1榜单、2抽奖等',
  `stime` datetime DEFAULT NULL COMMENT '活动开始时间',
  `etime` datetime DEFAULT NULL COMMENT '活动结束时间',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `act_center_activity_bg_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_id` bigint(20) NOT NULL COMMENT '活动id',
  `bg_urls` varchar(2000) NOT NULL COMMENT '多语言背景地址，json对象',
  `bg_color` varchar(20) DEFAULT NULL COMMENT '背景色',
  PRIMARY KEY (`id`),
  UNIQUE KEY `actId_uq` (`act_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `act_center_activity_rank_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_id` bigint(20) NOT NULL COMMENT '活动id',
  `top3_style` varchar(500) DEFAULT NULL COMMENT '前3样式，json数据',
  `bg_color` varchar(30) DEFAULT NULL COMMENT '整体背景色',
  `tab_bg_color` varchar(30) DEFAULT NULL COMMENT '榜单组背景色',
  `tab_bg_color_select` varchar(30) DEFAULT NULL COMMENT '榜单组背景色（选中）',
  `tab_rank_bg_color` varchar(30) DEFAULT NULL COMMENT '榜单背景色',
  `tab_rank_bg_color_select` varchar(30) DEFAULT NULL COMMENT '榜单背景色（选中）',
  `rank_groups` varchar(2000) DEFAULT NULL COMMENT '榜单组配置（json对象）',
  `with_one` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否生成我的榜单',
  PRIMARY KEY (`id`),
  UNIQUE KEY `actId_uq` (`act_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `act_center_activity_rule_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_id` bigint(20) NOT NULL COMMENT '活动id',
  `style_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '悬浮窗位置；0不动；1跟随屏幕滑动',
  `jump_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '跳转类型：0 h5；',
  `jump_url` varchar(200) NOT NULL COMMENT '跳转地址',
  `bg_url` varchar(200) NOT NULL COMMENT '悬浮窗背景url',
  PRIMARY KEY (`id`),
  UNIQUE KEY `actId_uq` (`act_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `act_center_activity_gift_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `act_id` bigint(20) NOT NULL COMMENT '活动id',
  `name` varchar(100) DEFAULT NULL COMMENT '礼物名',
  `icon` varchar(300) DEFAULT NULL COMMENT '礼物图片',
  `cost` bigint(20) DEFAULT NULL COMMENT '礼物价值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



