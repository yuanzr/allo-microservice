# allo-finance-service

## 简介
相关建表sql：

1) 虚拟道具相关：

CREATE TABLE `virtual_item_def` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `price` int(11) DEFAULT '0' COMMENT '真实单价',
  `inner_flag` bigint(20) NOT NULL DEFAULT '0' COMMENT '位图标记',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='虚拟道具定义表';

CREATE TABLE `virtual_item_app_def` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `app` varchar(64) NOT NULL COMMENT 'allo 或 其它',
  `item_id` int(11) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0 有效；1失效',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_itemId_unique` (`app`,`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='APP虚拟道具定义表';

CREATE TABLE `virtual_item_bill_2020_04` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `app` varchar(64) NOT NULL COMMENT 'appId allo or 国内',
  `item_id` int(11) NOT NULL COMMENT '道具id',
  `pre_val` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改前钱包的值',
  `val` bigint(20) NOT NULL COMMENT '减少为负数，如: -45',
  `after_val` bigint(20) NOT NULL COMMENT '修改后钱包的值',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `biz_id` varchar(256) DEFAULT NULL COMMENT '业务ID',
  `context` varchar(1024) DEFAULT NULL COMMENT '上下文',
  `price` int(11) DEFAULT '0' COMMENT '发生流水时道具单价',
  PRIMARY KEY (`id`),
  KEY `biz_id` (`biz_id`(191)),
  KEY `uid` (`uid`,`biz_id`(191)),
  KEY `uid_2` (`uid`,`app`,`item_id`,`ctime`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `virtual_item_wallet` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL COMMENT '用户ID',
  `app` varchar(64) NOT NULL,
  `item_id` int(11) NOT NULL COMMENT '道具ID',
  `pre_val` bigint(20) NOT NULL DEFAULT '0' COMMENT '道具前值',
  `val` bigint(20) NOT NULL COMMENT '道具数量',
  `ctime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `utime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid` (`uid`,`app`,`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户虚拟道具钱包';

--生成钱包按uid后两位hash存储过程
DROP PROCEDURE IF EXISTS sqlExcute;
DELIMITER // 
CREATE PROCEDURE sqlExcute()
BEGIN 
  -- SET @deleteSql = CONCAT('truncate TABLE virtual_item_wallet '); 
  -- prepare stmt from @deleteSql; 
  -- execute stmt;   
 set @idx = 0; 
 WHILE  
  @idx < 100 
 DO   
  SET @createSql = CONCAT('create table virtual_item_wallet_', @idx,' like virtual_item_wallet'); 
  prepare stmt from @createSql; 
  execute stmt;                             
  SET @idx = @idx+1; 
 END WHILE;
END // 
DELIMITER ;

call sqlExcute();

2) 礼物流水月表
CREATE TABLE `gift_send_record_2020_04` (
  `send_record_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) DEFAULT NULL COMMENT '礼物发送人UID',
  `recive_uid` bigint(20) DEFAULT NULL COMMENT '礼物接收人UID',
  `recive_type` tinyint(2) DEFAULT '1' COMMENT '接收者类型，1用户，2怪兽',
  `send_env` tinyint(2) DEFAULT NULL COMMENT '送礼物对象类型 1轻聊房间、竞拍房间给主播直接刷礼物，2私聊送个人礼物,3坑位房中，给坑位中的人刷礼物',
  `room_uid` bigint(20) DEFAULT NULL COMMENT '礼物发送房间，send_env为1和3时需要记录',
  `room_type` tinyint(2) DEFAULT NULL COMMENT '房间类型，见room表',
  `gift_id` int(10) DEFAULT NULL COMMENT '礼物ID',
  `gift_num` int(10) DEFAULT NULL,
  `gift_type` tinyint(2) DEFAULT '1' COMMENT '礼物类型，1、普通礼物，2、魔法礼物',
  `play_effect` tinyint(1) DEFAULT '0' COMMENT '是否触发大动画，0否，1是',
  `total_gold_num` decimal(20,2) DEFAULT NULL COMMENT '金币总额',
  `total_diamond_num` double(12,2) DEFAULT NULL COMMENT '转换后钻石总额',
  `create_time` timestamp NULL DEFAULT NULL,
  `gift_source` int(5) DEFAULT NULL COMMENT '礼物来源(1、普通  2\r\n\r\n、背包)',
  `total_crystal_num` decimal(20,2) DEFAULT '0.00' COMMENT '水晶总额',
  PRIMARY KEY (`send_record_id`) USING BTREE,
  KEY `ix_gift_id` (`gift_id`) USING BTREE,
  KEY `ix_recive_uid` (`recive_uid`) USING BTREE,
  KEY `ix_room_uid` (`room_uid`) USING BTREE,
  KEY `ix_create_time` (`create_time`) USING BTREE,
  KEY `ix_uid` (`uid`,`recive_uid`,`room_uid`) USING BTREE,
  KEY `idx_roomuid_createtime_totalgoldnum` (`room_uid`,`create_time`,`total_gold_num`) USING BTREE,
  KEY `idx_uid_totalgoldnum` (`uid`,`total_gold_num`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

3) 账单表月表
CREATE TABLE `bill_record_2020_04` (
  `bill_id` varchar(36) NOT NULL,
  `uid` bigint(32) DEFAULT NULL COMMENT '消费者UID',
  `target_uid` bigint(32) DEFAULT NULL COMMENT '目标用户UID',
  `room_uid` bigint(32) DEFAULT NULL,
  `bill_status` tinyint(2) DEFAULT NULL COMMENT '状态，提现时1是发起申请，2是提现完成，3是提现异常',
  `obj_id` varchar(32) DEFAULT NULL COMMENT '账单对象',
  `obj_type` tinyint(2) DEFAULT NULL COMMENT '账单类型:1充值2提现3消费订单支出4服务订单收入5刷礼物消费6收礼物收入7发个人红包消费8收到个人红包收入9房主佣金收入10注册送金币11推荐注册送金币12官方直送金币',
  `gift_id` int(10) DEFAULT NULL COMMENT 'obj_type为5或者6时，写入礼物ID',
  `gift_num` int(10) DEFAULT NULL,
  `diamond_num` double(20,2) DEFAULT NULL,
  `gold_num` decimal(20,2) DEFAULT NULL,
  `money` decimal(20,2) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `coins_before` decimal(20,2) DEFAULT NULL COMMENT '钱包加减前的金币',
  `coins_after` decimal(20,2) DEFAULT NULL COMMENT '钱包加减后的金币',
  `diamond_before` decimal(20,2) DEFAULT NULL COMMENT '钱包加减前的钻石',
  `diamond_after` decimal(20,2) DEFAULT NULL COMMENT '钱包加减后的钻石',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`bill_id`) USING BTREE,
  KEY `ix_uid` (`uid`) USING BTREE,
  KEY `bill_target_uid_inx` (`target_uid`) USING BTREE,
  KEY `br_obj_type_inx` (`obj_type`) USING BTREE,
  KEY `bill_room_uid_inx` (`room_uid`) USING BTREE,
  KEY `ctime_idx` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;


