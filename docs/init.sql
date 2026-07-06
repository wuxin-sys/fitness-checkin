-- ============================================
-- 健身打卡系统 - MySQL 建表脚本
-- 数据库版本：MySQL 5.7+
-- 字符集：utf8mb4
-- ============================================

-- 1. 创建数据库
CREATE DATABASE IF NOT EXISTS `fitness_checkin`
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

USE `fitness_checkin`;

-- ============================================
-- 2. 创建数据表
-- ============================================

-- 2.1 管理员表
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
    `username`    VARCHAR(50)  NOT NULL                 COMMENT '管理员用户名',
    `password`    VARCHAR(100) NOT NULL                 COMMENT '密码（MD5加盐加密）',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

-- 2.2 用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
    `username`    VARCHAR(50)  NOT NULL                 COMMENT '用户名',
    `password`    VARCHAR(100) NOT NULL                 COMMENT '密码（MD5加盐加密）',
    `nickname`    VARCHAR(50)  DEFAULT NULL             COMMENT '昵称',
    `gender`      TINYINT(1)   DEFAULT 0                COMMENT '性别：0-未知 1-男 2-女',
    `phone`       VARCHAR(20)  DEFAULT NULL             COMMENT '手机号',
    `role`        TINYINT(1)   NOT NULL DEFAULT 0       COMMENT '角色：0-普通用户 1-管理员',
    `avatar`      VARCHAR(255) DEFAULT NULL             COMMENT '头像地址',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 2.3 健身打卡表
DROP TABLE IF EXISTS `checkin`;
CREATE TABLE `checkin` (
    `id`           BIGINT(20)   NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
    `user_id`      BIGINT(20)   NOT NULL                 COMMENT '用户ID，关联user.id',
    `checkin_date` DATE         NOT NULL                 COMMENT '打卡日期',
    `sport_type`   VARCHAR(20)  NOT NULL                 COMMENT '训练类型',
    `duration`     INT(11)      NOT NULL DEFAULT 0       COMMENT '训练时长（分钟）',
    `calorie`      INT(11)      NOT NULL DEFAULT 0       COMMENT '消耗热量（千卡）',
    `remark`       VARCHAR(200) DEFAULT NULL             COMMENT '备注',
    `create_time`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_user_date` (`user_id`, `checkin_date`),
    KEY `idx_checkin_date` (`checkin_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健身打卡表';

-- 2.4 公告表
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT  COMMENT '主键ID',
    `title`       VARCHAR(100) NOT NULL                 COMMENT '公告标题',
    `content`     TEXT         NOT NULL                 COMMENT '公告内容',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='公告表';

-- ============================================
-- 3. 插入初始化数据
-- ============================================

-- 3.1 默认管理员账号（用户名: admin  密码: admin123）
-- 加密方式：MD5(MD5(原始密码) + salt)，salt = 'fitness_checkin_salt'
INSERT INTO `admin` (`username`, `password`) VALUES
('admin', 'f01e5700b65288f61d0bce1d666f2d85');

-- 3.2 测试用户（用户名: zhangsan  密码: 123456）
INSERT INTO `user` (`username`, `password`, `nickname`, `gender`) VALUES
('zhangsan', 'e44ea21cc06ffd59bcd5ce5c47e18a81', '张三', 1);

-- 3.3 测试用户（用户名: lisi  密码: 123456）
INSERT INTO `user` (`username`, `password`, `nickname`, `gender`) VALUES
('lisi', 'e44ea21cc06ffd59bcd5ce5c47e18a81', '李四', 1);

-- ============================================
-- 脚本执行完毕
-- ============================================
