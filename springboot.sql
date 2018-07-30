/*
Navicat MySQL Data Transfer

Source Server         : haru
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-07-30 14:21:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for base_entity
-- ----------------------------
DROP TABLE IF EXISTS `base_entity`;
CREATE TABLE `base_entity` (
  `dtype` varchar(31) NOT NULL,
  `id` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `valid` bit(1) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `city_name` varchar(255) DEFAULT NULL,
  `country` tinyblob,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of base_entity
-- ----------------------------
INSERT INTO `base_entity` VALUES ('Country', '4a937e7c-c988-4b23-94c0-0cac9df568b3', '2018-07-17 15:17:58', '', '0', 'UK', '英国', '0', null, null, null);

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` varchar(40) NOT NULL COMMENT '城市id',
  `country_id` varchar(40) DEFAULT NULL COMMENT '国家',
  `city_name` varchar(25) DEFAULT NULL COMMENT '城市名称',
  `description` varchar(25) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL,
  `valid` bit(1) NOT NULL,
  `version` int(11) NOT NULL,
  `country` tinyblob,
  PRIMARY KEY (`id`),
  KEY `FK_COUNTRY` (`country_id`),
  CONSTRAINT `FK_COUNTRY` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES ('38afeba0-9fbb-45fa-be7d-51c810593a1b', '38afeba0-9fbb-45fa-be7d-51c810593a1a', '上海', '谁知道', '2018-07-11 17:46:10', '', '1', null);
INSERT INTO `city` VALUES ('42e5bebb-5c2e-430a-890e-9a6944797bec', '4299902d-ceb1-4f1d-820a-a6df2f3c8c1c', '东京', 'otaku', '2018-07-12 16:51:14', '', '1', null);
INSERT INTO `city` VALUES ('e0527c9b-7c0f-4ce0-b052-600769a087b0', '4a937e7c-c988-4b23-94c0-0cac9df568b2', '华盛顿', null, '2018-07-12 17:45:36', '', '1', null);
INSERT INTO `city` VALUES ('ffad506d-c82b-4b2d-9c45-7ab357496dde', '4a937e7c-c988-4b23-94c0-0cac9df568b2', '纽约', 'NY', '2018-07-12 17:45:36', '', '1', null);

-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country` (
  `id` varchar(40) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `valid` bit(1) NOT NULL,
  `state` varchar(20) DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of country
-- ----------------------------
INSERT INTO `country` VALUES ('364b4734-6b7c-49dc-9b8a-e8a82ba0a8bf', '英国', 'UK', '2018-07-17 16:45:41', '', '启用', '0');
INSERT INTO `country` VALUES ('38afeba0-9fbb-45fa-be7d-51c810593a1a', '中华', 'CN', '2018-07-16 14:17:58', '', '启用', '3');
INSERT INTO `country` VALUES ('4299902d-ceb1-4f1d-820a-a6df2f3c8c1c', '日本', 'JP', '2018-07-12 16:51:05', '', '启用', '0');
INSERT INTO `country` VALUES ('4a937e7c-c988-4b23-94c0-0cac9df568b2', '美国', 'USA', '2018-07-16 14:17:58', '\0', '启用', '3');
INSERT INTO `country` VALUES ('4a937e7c-c988-4b23-94c0-0cac9df568b3', '德国', 'GM', '2018-07-17 15:17:58', '', '启用', '0');

-- ----------------------------
-- Table structure for visitor
-- ----------------------------
DROP TABLE IF EXISTS `visitor`;
CREATE TABLE `visitor` (
  `id` varchar(40) NOT NULL COMMENT '城市id',
  `create_time` datetime DEFAULT NULL,
  `valid` bit(1) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of visitor
-- ----------------------------
