/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 80016
Source Host           : localhost:3306
Source Database       : parking

Target Server Type    : MYSQL
Target Server Version : 80016
File Encoding         : 65001

Date: 2019-10-10 17:36:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for buildelem
-- ----------------------------
DROP TABLE IF EXISTS `buildelem`;
CREATE TABLE `buildelem` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '区域名字',
  `posX` int(16) DEFAULT NULL COMMENT '文字显示的位置x',
  `posY` int(16) DEFAULT NULL COMMENT '文字显示的位置y',
  `path` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '具体位置建筑物',
  `buildCode` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `AreaId` varchar(32) COLLATE utf8_bin NOT NULL,
  `paths` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '' COMMENT 'AH_H8',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=194 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of buildelem
-- ----------------------------
INSERT INTO `buildelem` VALUES ('149', 'name0', '652', '497', 'M652.50 497.00', 'Blc471666BULUM', 'AreId471661DViqN', 'id0');
INSERT INTO `buildelem` VALUES ('150', 'name1', '47', '0', 'M47.50 0.00', 'Blc471666SsvHi', 'AreId471661DViqN', 'id1');
INSERT INTO `buildelem` VALUES ('151', 'name2', '227', '144', 'M227.33 144.50c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H227.33 0.00z', 'Blc471666QoPOG', 'AreId471661DViqN', 'id2');
INSERT INTO `buildelem` VALUES ('152', 'name3', '270', '144', 'M270.67 144.50c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H270.67 0.00z', 'Blc471666WXanK', 'AreId471661DViqN', 'id3');
INSERT INTO `buildelem` VALUES ('153', 'name4', '316', '144', 'M316.24 144.50c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H316.24 0.00z', 'Blc471666uVrLQ', 'AreId471661DViqN', 'id4');
INSERT INTO `buildelem` VALUES ('154', 'name5', '361', '144', 'M361.00 144.50c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H361.00 0.00z', 'Blc471666Akhug', 'AreId471661DViqN', 'id5');
INSERT INTO `buildelem` VALUES ('155', 'name6', '227', '289', 'M227.33 289.83c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H227.33 0.00z', 'Blc471666fGcID', 'AreId471661DViqN', 'id6');
INSERT INTO `buildelem` VALUES ('156', 'name7', '270', '289', 'M270.67 289.83c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H270.67 0.00z', 'Blc471666wOidy', 'AreId471661DViqN', 'id7');
INSERT INTO `buildelem` VALUES ('157', 'name8', '316', '289', 'M316.24 289.83c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H316.24 0.00z', 'Blc471666HdCXP', 'AreId471661DViqN', 'id8');
INSERT INTO `buildelem` VALUES ('158', 'name9', '361', '289', 'M361.00 289.83c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H361.00 0.00z', 'Blc471666Qxtzc', 'AreId471661DViqN', 'id9');
INSERT INTO `buildelem` VALUES ('159', 'name10', '39', '0', 'M39.00 0.00c4.00 -1.65 51.33 43.33 51.33 43.33L62.00 71.67L-3.00 40.55', 'Blc471667DdqZI', 'AreId471661DViqN', 'id10');
INSERT INTO `buildelem` VALUES ('160', 'name11', '243', '39', 'M243.30 39.65c-0.00 -0.18 75.63 -0.05 71.00 -0.26l-0.09 -25.50l-71.00 0.26L243.30 39.65z', 'Blc471667kWQlG', 'AreId471661DViqN', 'id11');
INSERT INTO `buildelem` VALUES ('161', 'name12', '331', '39', 'M331.37 39.65c-0.00 -0.18 75.63 -0.05 71.00 -0.26l-0.09 -25.50l-71.00 0.26L331.37 39.65z', 'Blc471667NCJgf', 'AreId471661DViqN', 'id12');
INSERT INTO `buildelem` VALUES ('162', 'name13', '406', '1', 'M406.22 1.95c0.17 0.00 -0.18 479.27 -0.03 506.82c0.01 0.00 0.02 0.01 0.02 0.01c0.00 0.00 0.01 0.00 0.01 0.00c0.00 -0.07 0.00 -0.13 0.00 -0.21h25.50 0.00V0.00 1.95H406.22 0.00z', 'Blc471667IjynV', 'AreId471661DViqN', 'id13');
INSERT INTO `buildelem` VALUES ('163', 'name14', '321', '480', 'M321.87 480.30c0.00 -0.18 75.64 -0.05 71.00 -0.26l-0.09 -25.50l-71.00 0.26L321.87 480.30z', 'Blc471667miWbQ', 'AreId471661DViqN', 'id14');
INSERT INTO `buildelem` VALUES ('164', 'name15', '154', '39', 'M154.72 39.65c-0.00 -0.18 75.63 -0.05 71.00 -0.26l-0.09 -25.50l-71.00 0.26L154.72 39.65z', 'Blc471667NDaHs', 'AreId471661DViqN', 'id15');
INSERT INTO `buildelem` VALUES ('165', 'name16', '2', '480', 'M2.14 480.30c0.00 1.23 404.01 0.00 404.01 0.00v0.00 28.28H2.14 0.00V0.00 480.30L2.14 480.30z', 'Blc471667Uzjnz', 'AreId471661DViqN', 'id16');
INSERT INTO `buildelem` VALUES ('166', 'name17', '230', '480', 'M230.59 480.30c0.00 -0.18 75.64 -0.05 71.00 -0.26l-0.09 -25.50l-71.00 0.26L230.59 480.30z', 'Blc471667hRnYU', 'AreId471661DViqN', 'id17');
INSERT INTO `buildelem` VALUES ('167', 'name18', '137', '480', 'M137.59 480.30c0.00 -0.18 75.64 -0.05 71.00 -0.26l-0.09 -25.50l-71.00 0.26L137.59 480.30z', 'Blc471667IBKzN', 'AreId471661DViqN', 'id18');
INSERT INTO `buildelem` VALUES ('168', 'name19', '46', '480', 'M46.87 480.30c0.00 -0.18 75.64 -0.05 71.00 -0.26l-0.09 -25.50l-71.00 0.26L46.87 480.30z', 'Blc471667uphWA', 'AreId471661DViqN', 'id19');
INSERT INTO `buildelem` VALUES ('169', 'name20', '2', '401', 'M2.21 401.83c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H2.21 0.00z', 'Blc471667lHVMZ', 'AreId471661DViqN', 'id20');
INSERT INTO `buildelem` VALUES ('170', 'name21', '2', '302', 'M2.21 302.83c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H2.21 0.00z', 'Blc471667hIiFq', 'AreId471661DViqN', 'id21');
INSERT INTO `buildelem` VALUES ('171', 'name22', '2', '204', 'M2.21 204.53c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H2.21 0.00z', 'Blc471667eHNSp', 'AreId471661DViqN', 'id22');
INSERT INTO `buildelem` VALUES ('172', 'name23', '182', '290', 'M182.33 290.23c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H182.33 0.00z', 'Blc471667XTiac', 'AreId471661DViqN', 'id23');
INSERT INTO `buildelem` VALUES ('173', 'name24', '137', '290', 'M137.67 290.23c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H137.67 0.00z', 'Blc471667AEgxt', 'AreId471661DViqN', 'id24');
INSERT INTO `buildelem` VALUES ('174', 'name25', '182', '144', 'M182.33 144.50c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H182.33 0.00z', 'Blc471667WWvRo', 'AreId471661DViqN', 'id25');
INSERT INTO `buildelem` VALUES ('175', 'name26', '137', '144', 'M137.67 144.50c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H137.67 0.00z', 'Blc471667DuzTY', 'AreId471661DViqN', 'id26');
INSERT INTO `buildelem` VALUES ('176', 'name27', '353', '402', 'M353.25 402.42c12.23 0.00 12.06 7.03 0.00 7.03C341.18 409.44 341.01 402.42 353.25 402.42z', 'Blc471667TXCWL', 'AreId471661DViqN', 'id27');
INSERT INTO `buildelem` VALUES ('177', 'name28', '308', '402', 'M308.00 402.42c12.23 0.00 12.06 7.03 0.00 7.03C295.93 409.44 295.76 402.42 308.00 402.42z', 'Blc471667pWXVd', 'AreId471661DViqN', 'id28');
INSERT INTO `buildelem` VALUES ('178', 'name29', '266', '402', 'M266.14 402.42c12.23 0.00 12.06 7.03 0.00 7.03C254.08 409.44 253.91 402.42 266.14 402.42z', 'Blc471667fuknS', 'AreId471661DViqN', 'id29');
INSERT INTO `buildelem` VALUES ('179', 'name30', '218', '402', 'M218.15 402.42c12.23 0.00 12.06 7.03 0.00 7.03C206.08 409.44 205.92 402.42 218.15 402.42z', 'Blc471667Rtsbn', 'AreId471661DViqN', 'id30');
INSERT INTO `buildelem` VALUES ('180', 'name31', '173', '402', 'M173.15 402.42c12.23 0.00 12.06 7.03 0.00 7.03C161.08 409.44 160.92 402.42 173.15 402.42z', 'Blc471667MApIK', 'AreId471661DViqN', 'id31');
INSERT INTO `buildelem` VALUES ('181', 'name32', '362', '84', 'M362.36 84.06c12.23 0.00 12.06 7.03 0.00 7.03C350.29 91.09 350.13 84.06 362.36 84.06z', 'Blc471667wmrXe', 'AreId471661DViqN', 'id32');
INSERT INTO `buildelem` VALUES ('182', 'name33', '395', '402', 'M395.61 402.42c12.23 0.00 12.06 7.03 0.00 7.03C383.55 409.44 383.38 402.42 395.61 402.42z', 'Blc471667HrfzA', 'AreId471661DViqN', 'id33');
INSERT INTO `buildelem` VALUES ('183', 'name34', '126', '402', 'M126.14 402.42c12.23 0.00 12.06 7.03 0.00 7.03C114.08 409.44 113.91 402.42 126.14 402.42z', 'Blc471667CObtf', 'AreId471661DViqN', 'id34');
INSERT INTO `buildelem` VALUES ('184', 'name35', '85', '396', 'M85.94 396.74c0.29 12.23 -6.74 12.23 -7.03 0.17C78.63 384.84 85.65 384.51 85.94 396.74z', 'Blc471667kQWVi', 'AreId471661DViqN', 'id35');
INSERT INTO `buildelem` VALUES ('185', 'name36', '85', '346', 'M85.94 346.13c0.29 12.23 -6.74 12.23 -7.03 0.17C78.63 334.23 85.65 333.90 85.94 346.13z', 'Blc471667lqvgR', 'AreId471661DViqN', 'id36');
INSERT INTO `buildelem` VALUES ('186', 'name37', '85', '291', 'M85.94 291.94c0.29 12.23 -6.74 12.23 -7.03 0.17C78.63 280.05 85.65 279.71 85.94 291.94z', 'Blc471667ZIciM', 'AreId471661DViqN', 'id37');
INSERT INTO `buildelem` VALUES ('187', 'name38', '85', '237', 'M85.94 237.74c0.29 12.23 -6.74 12.23 -7.03 0.17C78.63 225.84 85.65 225.51 85.94 237.74z', 'Blc471667QuzHM', 'AreId471661DViqN', 'id38');
INSERT INTO `buildelem` VALUES ('188', 'name39', '85', '184', 'M85.94 184.06c0.29 12.23 -6.74 12.23 -7.03 0.17C78.63 172.17 85.65 171.84 85.94 184.06z', 'Blc471667MFyYq', 'AreId471661DViqN', 'id39');
INSERT INTO `buildelem` VALUES ('189', 'name40', '317', '84', 'M317.69 84.06c12.23 0.00 12.06 7.03 0.00 7.03C305.63 91.09 305.46 84.06 317.69 84.06z', 'Blc471667DrhKd', 'AreId471661DViqN', 'id40');
INSERT INTO `buildelem` VALUES ('190', 'name41', '266', '84', 'M266.14 84.06c12.23 0.00 12.06 7.03 0.00 7.03C254.08 91.09 253.91 84.06 266.14 84.06z', 'Blc471667QUuEZ', 'AreId471661DViqN', 'id41');
INSERT INTO `buildelem` VALUES ('191', 'name42', '214', '84', 'M214.69 84.06c12.23 0.00 12.06 7.03 0.00 7.03C202.63 91.09 202.46 84.06 214.69 84.06z', 'Blc471667HTgJh', 'AreId471661DViqN', 'id42');
INSERT INTO `buildelem` VALUES ('192', 'name43', '137', '264', 'M137.90 264.95c-0.00 -0.18 284.93 -0.05 267.48 -0.26l-0.35 -25.50l-267.48 0.26L137.90 264.95z', 'Blc471667qNIHq', 'AreId471661DViqN', 'id43');
INSERT INTO `buildelem` VALUES ('193', 'name44', '2', '103', 'M2.21 103.83c0.18 0.00 -0.23 75.63 0.00 71.00h25.50 0.00v0.00 -71.00H2.21 0.00z', 'Blc471667ahzON', 'AreId471661DViqN', 'id44');

-- ----------------------------
-- Table structure for parkingarea
-- ----------------------------
DROP TABLE IF EXISTS `parkingarea`;
CREATE TABLE `parkingarea` (
  `Id` int(32) NOT NULL AUTO_INCREMENT COMMENT '停车场id',
  `Depotname` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `storey` int(3) NOT NULL COMMENT '楼层',
  `width` double(16,3) NOT NULL COMMENT 'svg的宽度',
  `height` double(16,3) NOT NULL COMMENT '区域的id键值，例子：BH-A1',
  `AreaId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`Id`,`AreaId`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of parkingarea
-- ----------------------------
INSERT INTO `parkingarea` VALUES ('36', '科技大学停车场1', '1', '595.281', '841.890', 'AreId471661DViqN');

-- ----------------------------
-- Table structure for userinformation
-- ----------------------------
DROP TABLE IF EXISTS `userinformation`;
CREATE TABLE `userinformation` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `license` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of userinformation
-- ----------------------------
