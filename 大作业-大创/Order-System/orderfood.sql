/*
 Navicat Premium Data Transfer

 Source Server         : Mysql
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : orderfood

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 15/07/2020 09:54:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for foodclass
-- ----------------------------
DROP TABLE IF EXISTS `foodclass`;
CREATE TABLE `foodclass`  (
  `FC_id` int(4) NOT NULL AUTO_INCREMENT COMMENT '食物菜品种类自增id',
  `FC_className` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品类别名称',
  PRIMARY KEY (`FC_id`, `FC_className`) USING BTREE,
  INDEX `FC_id`(`FC_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of foodclass
-- ----------------------------
INSERT INTO `foodclass` VALUES (1, '招牌菜');
INSERT INTO `foodclass` VALUES (2, '甜点');
INSERT INTO `foodclass` VALUES (16, '饮品');

-- ----------------------------
-- Table structure for innerorder
-- ----------------------------
DROP TABLE IF EXISTS `innerorder`;
CREATE TABLE `innerorder`  (
  `I_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `I_foodCode` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品唯一id',
  `I_foodCount` int(2) NOT NULL COMMENT '菜品数量',
  `I_orderCode` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`I_id`, `I_foodCode`) USING BTREE,
  INDEX `orderCode`(`I_orderCode`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of innerorder
-- ----------------------------
INSERT INTO `innerorder` VALUES (4, 'fod123123', 1, 'ord1594645095');
INSERT INTO `innerorder` VALUES (5, 'fod123456', 3, 'ord1594645095');
INSERT INTO `innerorder` VALUES (6, 'fod123123', 0, 'ord1594724616');
INSERT INTO `innerorder` VALUES (7, 'fod123456', 0, 'ord1594724616');
INSERT INTO `innerorder` VALUES (8, 'fod123457', 1, 'ord1594724616');
INSERT INTO `innerorder` VALUES (9, 'fod123450', 0, 'ord1594724616');
INSERT INTO `innerorder` VALUES (10, 'fod123459', 1, 'ord1594724616');
INSERT INTO `innerorder` VALUES (11, 'fod1555521', 0, 'ord1594724616');
INSERT INTO `innerorder` VALUES (12, 'fod1594281894398kZlET', 0, 'ord1594724616');
INSERT INTO `innerorder` VALUES (13, 'fod1594658268805mPTIS', 0, 'ord1594724616');
INSERT INTO `innerorder` VALUES (14, 'food1234', 1, 'ord1594645095');
INSERT INTO `innerorder` VALUES (15, 'food1232', 3, 'ord1594645095');
INSERT INTO `innerorder` VALUES (16, 'food1234', 1, 'ord1594645094');
INSERT INTO `innerorder` VALUES (17, 'food1232', 3, 'ord1594645094');
INSERT INTO `innerorder` VALUES (18, 'food1234', 1, 'ord1594645096');
INSERT INTO `innerorder` VALUES (19, 'food1232', 3, 'ord1594645096');
INSERT INTO `innerorder` VALUES (20, 'food1234', 1, 'ord1594645095');
INSERT INTO `innerorder` VALUES (21, 'food1232', 3, 'ord1594645095');

-- ----------------------------
-- Table structure for menus
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus`  (
  `M_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '自动增长id',
  `M_foodName` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品名称',
  `M_foodCode` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '菜品唯一编码',
  `M_foodPrice` float(7, 2) NOT NULL COMMENT '菜品价格',
  `M_foodClass` int(4) NULL DEFAULT 1 COMMENT '菜品类别',
  `M_foodDesc` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '菜品描述',
  `M_foodEnable` int(1) NULL DEFAULT 1 COMMENT '菜品是否可以点',
  `M_foodPic` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '菜品缩略图',
  PRIMARY KEY (`M_id`, `M_foodName`) USING BTREE,
  INDEX `M_foodClass`(`M_foodClass`) USING BTREE,
  CONSTRAINT `M_foodClass` FOREIGN KEY (`M_foodClass`) REFERENCES `foodclass` (`FC_id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menus
-- ----------------------------
INSERT INTO `menus` VALUES (1, '麻辣烫', 'fod123123', 11.00, 1, '暂无', 1, '麻辣烫');
INSERT INTO `menus` VALUES (2, '酸辣粉', 'fod123456', 11.00, 1, '暂无', 1, 'food');
INSERT INTO `menus` VALUES (3, '小笼包', 'fod123457', 11.00, 1, '暂无', 1, 'food');
INSERT INTO `menus` VALUES (4, '桂花糕', 'fod123450', 11.00, 2, '暂无', 0, 'food');
INSERT INTO `menus` VALUES (5, '绿豆糕', 'fod123459', 14.00, 2, '暂无', 1, 'food');
INSERT INTO `menus` VALUES (6, '小龙虾', 'fod1555521', 32.10, 1, '暂无', 1, 'food');
INSERT INTO `menus` VALUES (7, '鸡公煲', 'fod1594281894398kZlET', 88.88, 1, '就吃一个鸡公煲', 0, 'food');
INSERT INTO `menus` VALUES (9, '可乐', 'fod1594658268805mPTIS', 4.00, 16, '非常好喝', 1, '可口可乐');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `O_id` int(32) NOT NULL AUTO_INCREMENT COMMENT '自动增长id',
  `O_orderCode` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '订单唯一id',
  `O_orderTime` int(32) NOT NULL COMMENT '订单生成时间',
  `O_price` float(7, 0) NOT NULL COMMENT '订单价格',
  `O_status` int(1) NULL DEFAULT 0 COMMENT '0 1 2未支付 已支付 已经完成',
  PRIMARY KEY (`O_id`, `O_orderCode`) USING BTREE,
  INDEX `orderCode`(`O_orderCode`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES (11, 'ord1594645095', 1594645095, 111, 1);
INSERT INTO `order` VALUES (12, 'ord1594724616', 1594724616, 25, 2);
INSERT INTO `order` VALUES (13, 'ord1594645095', 1594645095, 111, 1);
INSERT INTO `order` VALUES (14, 'ord1594645094', 1594645094, 111, 0);
INSERT INTO `order` VALUES (15, 'ord1594645096', 1594645096, 99, 0);
INSERT INTO `order` VALUES (16, 'ord1594645095', 1594645095, 99, 0);

SET FOREIGN_KEY_CHECKS = 1;
