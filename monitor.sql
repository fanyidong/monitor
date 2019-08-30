/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云 - 39.108.52.40
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : 39.108.52.40:3306
 Source Schema         : monitor

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 04/04/2019 11:39:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mail
-- ----------------------------
DROP TABLE IF EXISTS `mail`;
CREATE TABLE `mail` (
  `mailid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userid` varchar(45) DEFAULT NULL COMMENT 'userId用作与user表关联',
  `validationcode` varchar(45) DEFAULT NULL COMMENT 'md5加密后的值',
  `outtime` datetime DEFAULT NULL COMMENT '邮件过期时间',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`mailid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COMMENT='邮箱更改密码表';

-- ----------------------------
-- Table structure for monitor
-- ----------------------------
DROP TABLE IF EXISTS `monitor`;
CREATE TABLE `monitor` (
  `monitorid` varchar(45) NOT NULL COMMENT '监控id',
  `userid` varchar(45) DEFAULT NULL COMMENT '用户id',
  `name` varchar(45) DEFAULT NULL COMMENT '监控项目名称',
  `url` varchar(255) DEFAULT NULL COMMENT '监控地址',
  `watchtime` int(11) DEFAULT NULL COMMENT '监控频率（分钟）',
  `type` varchar(25) DEFAULT NULL COMMENT '监控类型（web-网站 api-接口 server-服务）',
  `warnmethod` varchar(25) DEFAULT '1' COMMENT '警告方式（mail邮件 phone手机）',
  `state` int(11) DEFAULT '1' COMMENT '启用状态（0禁用 1启用）',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `requestmethod` varchar(25) DEFAULT NULL COMMENT '请求方式（get post）默认get',
  `matchtarget` int(11) DEFAULT NULL COMMENT '匹配目标（1响应内容 2响应头信息）',
  `matchtype` int(11) DEFAULT NULL COMMENT '匹配方式（0不包含匹配内容 1包含匹配内容）',
  `matchcontent` varchar(255) DEFAULT NULL COMMENT '匹配内容',
  `commitcontent` varchar(255) DEFAULT NULL COMMENT 'post请求时的提交内容',
  PRIMARY KEY (`monitorid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='监控表';

-- ----------------------------
-- Table structure for result
-- ----------------------------
DROP TABLE IF EXISTS `result`;
CREATE TABLE `result` (
  `resultid` varchar(45) NOT NULL COMMENT '监控结果id',
  `monitorid` varchar(45) DEFAULT NULL COMMENT '监控id',
  `status` int(11) DEFAULT NULL COMMENT '请求状态码',
  `responsetime` bigint(11) DEFAULT NULL COMMENT '响应时间(单位ms)',
  `others` varchar(1024) DEFAULT NULL COMMENT '预留字段',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `usable` int(11) DEFAULT NULL COMMENT '监控结果是否匹配（0不可用 1可用）',
  `disabledreason` varchar(200) DEFAULT NULL COMMENT '不可用原因',
  PRIMARY KEY (`resultid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='监控结果记录表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userid` varchar(55) NOT NULL COMMENT '用户主键id',
  `account` varchar(55) DEFAULT NULL COMMENT '账号',
  `password` varchar(55) DEFAULT NULL COMMENT '密码',
  `email` varchar(55) DEFAULT NULL COMMENT '邮箱',
  `mobile` bigint(12) DEFAULT NULL COMMENT '手机号',
  `createtime` datetime DEFAULT NULL COMMENT '创建日期',
  `updatetime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;
