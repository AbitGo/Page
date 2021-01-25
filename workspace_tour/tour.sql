/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 80016
Source Host           : localhost:3306
Source Database       : tour

Target Server Type    : MYSQL
Target Server Version : 80016
File Encoding         : 65001

Date: 2021-01-06 13:59:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comment_t
-- ----------------------------
DROP TABLE IF EXISTS `comment_t`;
CREATE TABLE `comment_t` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT 'id\\',
  `tourCode` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '与tour_t对应的数据',
  `commentCode` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '评论唯一id',
  `commentText` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '评论内容',
  `userCode` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`,`tourCode`,`commentCode`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of comment_t
-- ----------------------------
INSERT INTO `comment_t` VALUES ('1', 'asdas1d', 'COM1609847608798GnCDQ', '很好玩,下次还会过来', '123123');
INSERT INTO `comment_t` VALUES ('2', 'asdas1d', 'COM1609847634281jGUyQ', '很好玩,下次还会过111111来', '123123');

-- ----------------------------
-- Table structure for dreamlist_t
-- ----------------------------
DROP TABLE IF EXISTS `dreamlist_t`;
CREATE TABLE `dreamlist_t` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT,
  `tourCode` varchar(32) COLLATE utf8_bin NOT NULL,
  `timeAdd` bigint(15) DEFAULT NULL,
  `userCode` varchar(32) COLLATE utf8_bin NOT NULL,
  `dreamListCode` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`,`tourCode`,`userCode`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of dreamlist_t
-- ----------------------------
INSERT INTO `dreamlist_t` VALUES ('8', '888979whus', '1609813305868', '123123', 'DLC1609813305868dRsZD');
INSERT INTO `dreamlist_t` VALUES ('9', 'tou1609813225327SEEkT', '1609813309353', '123123', 'DLC1609813309353CwYnx');
INSERT INTO `dreamlist_t` VALUES ('10', 'tou1609813243446DNMvv', '1609813313900', '123123', 'DLC1609813313900BlkeI');
INSERT INTO `dreamlist_t` VALUES ('11', 'tou1609813259921JeIKL', '1609813320046', '123123', 'DLC1609813320046GxYwg');
INSERT INTO `dreamlist_t` VALUES ('12', 'tou1609759690270KiNeT', '1609813321046', '123123', 'DLC1609813320146GxYws');
INSERT INTO `dreamlist_t` VALUES ('13', 'tou1609759690270KiNeT', '1609813321046', '123123', 'DLC1609813320146GxYws');
INSERT INTO `dreamlist_t` VALUES ('14', 'tou1609759690270KiNeT', '1609813321046', '123123', 'DLC1609813320146GxYws');
INSERT INTO `dreamlist_t` VALUES ('15', 'tou1609813259921JeIKL', '1609813320046', '123123', 'DLC1609813320046GxYwg');
INSERT INTO `dreamlist_t` VALUES ('16', 'tou1609813225327SEEkT', '1609813309353', '123123', 'DLC1609813309353CwYnx');
INSERT INTO `dreamlist_t` VALUES ('17', '888979whus', '1609813309353', '123123', 'DLC1609813309353CwYnx');

-- ----------------------------
-- Table structure for pic_t
-- ----------------------------
DROP TABLE IF EXISTS `pic_t`;
CREATE TABLE `pic_t` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT,
  `picId` varchar(32) COLLATE utf8_bin NOT NULL,
  `tourCode` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`,`picId`,`tourCode`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of pic_t
-- ----------------------------
INSERT INTO `pic_t` VALUES ('1', 'PIC1609912344713Wguwb.svg', '888979whus');
INSERT INTO `pic_t` VALUES ('2', 'PIC1609912607426cqTYr.png', '888979whus');

-- ----------------------------
-- Table structure for tour_t
-- ----------------------------
DROP TABLE IF EXISTS `tour_t`;
CREATE TABLE `tour_t` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tourName` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '旅游景点名称',
  `tourCode` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '唯一code',
  `posLat` varchar(10) COLLATE utf8_bin NOT NULL,
  `poslng` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `timeAdd` bigint(15) DEFAULT NULL,
  PRIMARY KEY (`id`,`poslng`,`posLat`,`tourName`),
  UNIQUE KEY `tourName` (`tourName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tour_t
-- ----------------------------
INSERT INTO `tour_t` VALUES ('1', '恐龙园', 'tou1609759690270KiNeT', '11.111', '22.222', '1609759690269');
INSERT INTO `tour_t` VALUES ('9', '井冈山带学', 'tou1609813210741lXegf', '11.111', '22.222', '1609813210741');
INSERT INTO `tour_t` VALUES ('10', '常州带学', 'tou1609813225327SEEkT', '11.111', '22.222', '1609813225327');
INSERT INTO `tour_t` VALUES ('11', '苏州科技带学', 'tou1609813243446DNMvv', '00.899', '88.123', '1609813243446');
INSERT INTO `tour_t` VALUES ('12', '江苏信息职业技术带院', 'tou1609813259921JeIKL', '11.899', '88.222', '1609813259921');
INSERT INTO `tour_t` VALUES ('13', 'bababab', '888979whus', '00.11', '7890.1', '1609813269921');

-- ----------------------------
-- Table structure for user_t
-- ----------------------------
DROP TABLE IF EXISTS `user_t`;
CREATE TABLE `user_t` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT,
  `loginName` varchar(15) COLLATE utf8_bin NOT NULL,
  `loginPassword` varchar(15) COLLATE utf8_bin NOT NULL,
  `userCode` varchar(32) COLLATE utf8_bin NOT NULL,
  `userPhone` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `loginName` (`loginName`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user_t
-- ----------------------------
INSERT INTO `user_t` VALUES ('1', '123456swdwsd', 'asdasd', '123123', '890789');
INSERT INTO `user_t` VALUES ('3', '123456777777', 'qweqwe', 'Usc1609841750365bpNVx', '110');
