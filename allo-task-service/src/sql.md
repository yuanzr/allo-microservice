# allo-task-service

## 简介
相关建表sql：

1) 签到任务相关：

CREATE TABLE `sign` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app` varchar(32) NOT NULL COMMENT '如allo',
  `task_id` bigint(20) NOT NULL COMMENT '签到任务id',
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `sign_date` varchar(32) NOT NULL COMMENT '签到任务日期',
  `sign_status` int(2) NOT NULL COMMENT '签到状态：0正常；1补签',
  `sign_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '签到时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid_taskid_app_signDate_unique` (`uid`,`task_id`,`app`,`sign_date`) USING BTREE,
  KEY `signTime_idx` (`sign_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签到记录表';

CREATE TABLE `sign_task_conf` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app` varchar(32) NOT NULL,
  `task_name` varchar(64) NOT NULL,
  `task_type` int(2) NOT NULL COMMENT '0有期限任务；1无期限',
  `stime` timestamp NULL DEFAULT NULL COMMENT '开始日期，type为0时有效',
  `etime` timestamp NULL DEFAULT NULL COMMENT '结束日期，type为0时有效',
  `ctime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签到任务配置表';

CREATE TABLE `sign_continuous` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `app` varchar(32) NOT NULL COMMENT '如allo',
  `task_id` bigint(11) NOT NULL COMMENT '签到任务id',
  `uid` bigint(11) NOT NULL COMMENT '用户id',
  `c_num` int(2) NOT NULL DEFAULT '0' COMMENT '连签次数',
  `pre_sign_date` date NOT NULL COMMENT '上一次签到日期',
  `utime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid_app_taskid_idx` (`uid`,`app`,`task_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='连签记录表';


--生成签到系统按uid后两位hash存储过程
DROP PROCEDURE IF EXISTS sqlExcute;
DELIMITER // 
CREATE PROCEDURE sqlExcute()
BEGIN 
  -- SET @deleteSql = CONCAT('truncate TABLE sign '); 
  -- prepare stmt from @deleteSql; 
  -- execute stmt;   
 set @idx = 0; 
 WHILE  
  @idx < 100 
 DO   
  SET @createSql = CONCAT('create table sign_', @idx,' like sign'); 
  prepare stmt from @createSql; 
  execute stmt;                             
  SET @idx = @idx+1; 
 END WHILE;
END // 
DELIMITER ;

call sqlExcute();

2) 


3) 


