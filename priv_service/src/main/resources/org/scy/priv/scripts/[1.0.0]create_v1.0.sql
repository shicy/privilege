-- ========================================================
-- 授权管理服务平台
-- Author shicy
-- Created on <2017-08-28>
-- ========================================================

-- CREATE SCHEMA `db_priv` DEFAULT CHARACTER SET utf8mb4 ;
-- USE `db_priv`;

-- -----------------------------------------------------
-- Table `priv`.`account`
-- 帐户表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `account` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL COMMENT '名称：姓名或企业名称',
  `code` VARCHAR(32) NOT NULL COMMENT '编码，第三方应用识别码',
  `secret` VARCHAR(32) NOT NULL COMMENT '密钥，用于第三方验证',
  `mobile` VARCHAR(20) NULL COMMENT '绑定手机号',
  `email` VARCHAR(50) NULL COMMENT '绑定邮箱',
  `remark` VARCHAR(600) NULL COMMENT '备注信息',
  `type` TINYINT NULL DEFAULT 1 COMMENT '0-平台 1-个人 2-企业',
  `ownerId` INT NULL COMMENT '所有者编号',
  `state` TINYINT NULL DEFAULT 1 COMMENT '0-失效 1-有效',
  `creatorId` INT NULL,
  `createTime` BIGINT NULL,
  `updatorId` INT NULL,
  `updateTime` BIGINT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `u_account_name_idx` (`name` ASC),
  UNIQUE INDEX `u_account_code_idx` (`code` ASC))
COMMENT = '账户表，平台账户信息';


-- -----------------------------------------------------
-- Table `priv`.`user`
-- 用户表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(45) NULL COMMENT '用户编码，用于业务系统识别用户',
  `name` VARCHAR(45) NULL COMMENT '用户名，可用于登录',
  `mobile` VARCHAR(20) NULL COMMENT '手机，可用于登录，需要短信验证码',
  `email` VARCHAR(100) NULL COMMENT '电子邮箱，可用于登录',
  `password` VARCHAR(32) NULL COMMENT '登录密码',
  `type` TINYINT NULL DEFAULT 0 COMMENT '用户类型：0-默认',
  `accept` TINYINT NULL DEFAULT 7 COMMENT '允许登录方式：按位运算[email, mobile, name]，默认7-允许全部',
  `remark` VARCHAR(200) NULL,
  `lastLoginType` TINYINT NULL COMMENT '最后一次登录方法：1-帐号 2-手机号 4-邮箱',
  `lastLoginTime` BIGINT NULL COMMENT '最后一次登录时间',
  `state` TINYINT NULL DEFAULT 1 COMMENT '0-无效 1-有效',
  `creatorId` INT NULL,
  `createTime` BIGINT NULL,
  `updatorId` INT NULL,
  `updateTime` BIGINT NULL,
  `paasId` INT NULL,
  PRIMARY KEY (`id`))
COMMENT = '用户表';


-- -----------------------------------------------------
-- Table `priv`.`group`
-- 用户组
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `remark` VARCHAR(200) NULL,
  `state` TINYINT NULL DEFAULT 1 COMMENT '0-失效 1-有效',
  `creatorId` INT NULL,
  `createTime` BIGINT NULL,
  `updatorId` INT NULL,
  `updateTime` BIGINT NULL,
  `paasId` INT NULL,
  PRIMARY KEY (`id`))
COMMENT = '用户分组表';


-- -----------------------------------------------------
-- Table `priv`.`role`
-- 用户角色
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `remark` VARCHAR(200) NULL,
  `type` TINYINT NULL DEFAULT 0 COMMENT '0-默认',
  `state` TINYINT NULL DEFAULT 1 COMMENT '0-无效 1-有效',
  `creatorId` INT NULL,
  `createTime` BIGINT NULL,
  `updatorId` INT NULL,
  `updateTime` BIGINT NULL,
  `paasId` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
COMMENT = '角色表';


-- -----------------------------------------------------
-- Table `priv`.`module`
-- 模块信息，授权项
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `module` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `code` VARCHAR(20) NULL,
  `remark` VARCHAR(200) NULL,
  `parentId` INT NULL DEFAULT 0 COMMENT '上级编号',
  `state` TINYINT NULL DEFAULT 1 COMMENT '0-无效 1-失效',
  `creatorId` INT NULL,
  `createTime` BIGINT NULL,
  `updatorId` INT NULL,
  `updateTime` BIGINT NULL,
  `paasId` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
COMMENT = '权限项表';


-- -----------------------------------------------------
-- Table `priv`.`privs`
-- 用户授权记录
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `privs` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `moduleId` INT NOT NULL COMMENT '模块编号',
  `userId` INT NULL,
  `groupId` INT NULL,
  `roleId` INT NULL,
  `grantType` INT NULL DEFAULT 1 COMMENT '授权方式：0-只读 1-读写',
  `state` TINYINT NULL DEFAULT 1 COMMENT '0-失效 1-有效',
  `creatorId` INT NULL,
  `createTime` BIGINT NULL,
  `paasId` INT NULL,
  PRIMARY KEY (`id`))
COMMENT = '权限明细表';


-- -----------------------------------------------------
-- Table `priv`.`group_users`
-- 用户组关联表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `group_users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `groupId` INT NOT NULL,
  `userId` INT NOT NULL,
  `state` TINYINT NULL DEFAULT 1 COMMENT '0-无效 1-有效',
  `creatorId` INT NULL COMMENT '	',
  `createTime` BIGINT NULL,
  `paasId` INT NULL,
  PRIMARY KEY (`id`))
COMMENT = '用户和用户组关联表';


-- -----------------------------------------------------
-- Table `priv`.`role_users`
-- 角色关联表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `role_users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `roleId` INT NULL,
  `userId` INT NULL,
  `state` TINYINT NULL DEFAULT 1 COMMENT '0-无效 1-有效',
  `creatorId` INT NULL,
  `createTime` BIGINT NULL,
  `paasId` INT NULL,
  PRIMARY KEY (`id`))
COMMENT = '用户和角色关联表';


-- -----------------------------------------------------
-- Table `priv`.`user_privs`
-- 用户权限
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_privs` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NULL,
  `moduleId` INT NULL,
  `grantType` INT NULL DEFAULT 1,
  `paasId` INT NULL,
  PRIMARY KEY (`id`))
COMMENT = '最终的用户权限信息表，综合用户组和角色之后的权限列表';


-- -----------------------------------------------------
-- Table `priv`.`login_recode`
-- 登录记录表
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `login_record` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `loginName` VARCHAR(45) NULL COMMENT '登录名称：用户、手机号或邮箱',
  `loginType` TINYINT NULL COMMENT '登录方式：1-帐号 2-手机号 4-邮箱',
  `validcode` VARCHAR(10) NULL COMMENT '验证码',
  `ip` VARCHAR(20) NULL COMMENT 'IP地址',
  `domain` VARCHAR(30) NULL COMMENT '网站域名',
  `userAgent` VARCHAR(200) NULL COMMENT '用户代理',
  `client` VARCHAR(100) NULL COMMENT '客户端编号',
  `createTime` BIGINT NULL,
  `paasId` INT NULL,
  PRIMARY KEY (`id`))
COMMENT = '用户登录记录表';


-- -----------------------------------------------------
-- Table `priv`.`token`
-- 当前登录用户
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `token` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `token` VARCHAR(64) NULL COMMENT '登录用户token信息',
  `expires` BIGINT NULL COMMENT '到期时间',
  `domain` VARCHAR(30) NULL COMMENT '登录时的域名',
  `client` VARCHAR(100) NULL COMMENT '客户端编号',
  `userAgent` VARCHAR(200) NULL COMMENT '用户代理',
  `createTime` BIGINT NULL,
  `lastActiveTime` BIGINT NULL,
  `paasId` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `u_token_token_idx` (`token` ASC))
COMMENT = '当前用户登录状态表';


-- -----------------------------------------------------
-- Table `priv`.`user_profile`
-- 用户属性 <2020-01-22 14:00:00>
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_profile` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NOT NULL,
  `name` VARCHAR(64) NULL COMMENT '属性名称',
  `value` TEXT NULL COMMENT '属性值',
  `createTime` BIGINT NULL,
  `updateTime` BIGINT NULL,
  `paasId` INT NULL,
  PRIMARY KEY (`id`))
COMMENT = '当前用户属性表';
GO

-- =====================================================
-- 账户表添加登录密码 <2020-10-14 14:00:00>
ALTER TABLE `account` ADD COLUMN `password` VARCHAR(32) NULL COMMENT '登录密码' AFTER `remark`;
GO

-- 修改`account`表索引 <2020-10-19 13:50:00>
DROP INDEX `u_account_name_idx` on `account`;
ALTER TABLE `account` ADD INDEX `account_name_idx` (`name` ASC);
GO
