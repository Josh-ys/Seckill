/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : seckill

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2018-05-31 01:49:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('1');
INSERT INTO `hibernate_sequence` VALUES ('1');

-- ----------------------------
-- Table structure for t_seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_goods`;
CREATE TABLE `t_seckill_goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goods_id` bigint(20) DEFAULT NULL COMMENT 'spu ID',
  `item_id` bigint(20) DEFAULT NULL COMMENT 'sku ID',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `small_pic` varchar(150) DEFAULT NULL COMMENT '商品图片',
  `price` decimal(10,2) DEFAULT NULL COMMENT '原价格',
  `cost_price` decimal(10,2) DEFAULT NULL COMMENT '秒杀价格',
  `seller_id` varchar(100) DEFAULT NULL COMMENT '商家ID',
  `create_time` datetime DEFAULT NULL COMMENT '添加日期',
  `check_time` datetime DEFAULT NULL COMMENT '审核日期',
  `status` varchar(1) DEFAULT NULL COMMENT '审核状态',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `num` int(11) DEFAULT NULL COMMENT '秒杀商品数',
  `stock_count` int(11) DEFAULT NULL COMMENT '剩余库存数',
  `introduction` varchar(2000) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_seckill_goods
-- ----------------------------
INSERT INTO `t_seckill_goods` VALUES ('1', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '8000.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '1', '2018-05-26 18:07:27', '2018-06-20 18:07:31', '100', '60', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('2', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '1', '2018-05-26 18:07:27', '2018-06-20 18:07:31', '100', '20', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('3', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '1', '2018-05-26 18:07:27', '2018-06-20 18:07:31', '100', '30', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('4', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '1', '2018-05-26 18:07:27', '2018-06-20 18:07:31', '100', '40', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('5', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '1', '2018-05-26 18:07:27', '2018-06-20 18:07:31', '100', '50', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('6', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '1', '2018-05-26 18:07:27', '2018-06-20 18:07:31', '100', '60', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('7', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '1', '2018-05-26 18:07:27', '2018-06-20 18:07:31', '100', '70', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('8', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '1', '2018-05-26 18:07:27', '2018-06-20 18:07:31', '100', '80', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('9', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '1', '2018-05-26 18:07:27', '2018-06-20 18:07:31', '100', '20', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('10', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '1', '2018-05-26 18:07:27', '2018-06-20 18:07:31', '100', '10', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('11', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '1', '2018-05-26 18:07:27', '2018-06-20 18:07:31', '100', '15', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('12', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '1', '2018-05-01 18:07:27', '2018-05-23 18:07:31', '100', '50', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('13', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '0', '2018-07-05 18:07:27', '2018-08-17 18:07:31', '100', '20', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('14', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '1', '2018-05-26 18:07:27', '2018-06-20 18:07:31', '100', '20', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('15', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '1', '2018-05-26 18:07:27', '2018-06-20 18:07:31', '100', '0', '苹果10打折促销');
INSERT INTO `t_seckill_goods` VALUES ('16', '149187842867960', '1', 'iphoneX', 'https://img10.360buyimg.com/n7/jfs/t10690/249/1626659345/69516/b3643998/59e4279aNff3d63ac.jpg', '8799.00', '7900.00', 'jingdong', '2018-05-02 23:27:04', '2018-05-05 20:07:51', '0', '2018-05-26 18:07:27', '2018-06-20 18:07:31', '100', '20', '苹果10打折促销');

-- ----------------------------
-- Table structure for t_seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `t_seckill_order`;
CREATE TABLE `t_seckill_order` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `seckill_id` bigint(20) DEFAULT NULL COMMENT '秒杀商品ID',
  `money` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `user_id` varchar(50) DEFAULT NULL COMMENT '用户',
  `seller_id` varchar(50) DEFAULT NULL COMMENT '商家',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `status` varchar(1) DEFAULT NULL COMMENT '状态',
  `receiver_address` varchar(200) DEFAULT NULL COMMENT '收货人地址',
  `receiver_mobile` varchar(20) DEFAULT NULL COMMENT '收货人电话',
  `receiver` varchar(20) DEFAULT NULL COMMENT '收货人',
  `transaction_id` varchar(30) DEFAULT NULL COMMENT '交易流水',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_seckill_order
-- ----------------------------
INSERT INTO `t_seckill_order` VALUES ('919473120379723776', null, '0.02', 'lijialong', 'qiandu', '2017-10-15 16:00:49', '2017-10-15 16:03:36', '1', null, null, null, '4200000013201710158227452548');
INSERT INTO `t_seckill_order` VALUES ('919474775091339264', null, '0.02', 'lijialong', 'qiandu', '2017-10-15 16:07:24', '2017-10-15 16:07:58', '1', null, null, null, '4200000007201710158230411417');
INSERT INTO `t_seckill_order` VALUES ('919497114331951104', '2', '0.01', null, 'yijia', '2017-10-15 17:36:10', '2017-10-15 17:37:35', '1', null, null, null, '4200000004201710158248971034');
INSERT INTO `t_seckill_order` VALUES ('919497943340302336', '2', '0.01', null, 'yijia', '2017-10-15 17:39:27', '2017-10-15 17:39:49', '1', null, null, null, '4200000011201710158245347392');
