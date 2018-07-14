/*
Navicat MySQL Data Transfer

Source Server         : leftovers
Source Server Version : 50634
Source Host           : localhost:3306
Source Database       : hotelos

Target Server Type    : MYSQL
Target Server Version : 50634
File Encoding         : 65001

Date: 2016-12-21 02:59:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `city` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `trading_area` varchar(20) CHARACTER SET utf8mb4 NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES ('南京市', '珠江路商圈');
INSERT INTO `address` VALUES ('南京市', '新街口商圈');
INSERT INTO `address` VALUES ('南京市', '江东门商圈');
INSERT INTO `address` VALUES ('南京市', '江北商圈');
INSERT INTO `address` VALUES ('南京市', '夫子庙商圈');
INSERT INTO `address` VALUES ('南京市', '河西商圈');
INSERT INTO `address` VALUES ('南京市', '仙林商圈');
INSERT INTO `address` VALUES ('南京市', '山西路商圈');
INSERT INTO `address` VALUES ('杭州市', '城北商圈');
INSERT INTO `address` VALUES ('杭州市', '吴山商圈');
INSERT INTO `address` VALUES ('杭州市', '武林商圈');
INSERT INTO `address` VALUES ('杭州市', '滨江商圈');
INSERT INTO `address` VALUES ('上海市', '淮海路商圈');
INSERT INTO `address` VALUES ('上海市', '徐家汇商圈');
INSERT INTO `address` VALUES ('上海市', '浦东商圈');
INSERT INTO `address` VALUES ('上海市', '五角场商圈');
INSERT INTO `address` VALUES ('上海市', '南京路商圈');
INSERT INTO `address` VALUES ('广州市', '珠江新城商圈');
INSERT INTO `address` VALUES ('广州市', '东山商圈');
INSERT INTO `address` VALUES ('广州市', '北京路商圈');
INSERT INTO `address` VALUES ('广州市', '小北商圈');
INSERT INTO `address` VALUES ('广州市', '番禺大北路商圈');
INSERT INTO `address` VALUES ('广州市', '天河商圈');
INSERT INTO `address` VALUES ('广州市', '流花商圈');
INSERT INTO `address` VALUES ('广州市', '西关商圈');
INSERT INTO `address` VALUES ('北京市', '望京商圈');
INSERT INTO `address` VALUES ('北京市', '北苑商圈');
INSERT INTO `address` VALUES ('北京市', '回龙观商圈');
INSERT INTO `address` VALUES ('北京市', '天通苑商圈');
INSERT INTO `address` VALUES ('北京市', '朝青商圈');
INSERT INTO `address` VALUES ('北京市', '亦庄商圈');
INSERT INTO `address` VALUES ('北京市', '清河商圈');
INSERT INTO `address` VALUES ('北京市', '亚运村商圈');
INSERT INTO `address` VALUES ('北京市', '黄村北商圈');

-- ----------------------------
-- Table structure for credit_record
-- ----------------------------
DROP TABLE IF EXISTS `credit_record`;
CREATE TABLE `credit_record` (
  `record_id` bigint(6) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `current_credit` double(8,2) NOT NULL,
  `changed_credit` double(8,2) NOT NULL,
  `changed_time` datetime NOT NULL,
  `cause` varchar(20) NOT NULL DEFAULT '',
  `order_id` char(20) NOT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=893038 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of credit_record
-- ----------------------------
INSERT INTO `credit_record` VALUES ('111466', '123456', '80.00', '80.00', '2016-12-18 18:28:57', '撤销异常订单', '15165165');
INSERT INTO `credit_record` VALUES ('131750', 'user1', '1080.00', '-75.00', '2016-12-21 00:12:58', '撤销订单', '52200020161220359');
INSERT INTO `credit_record` VALUES ('251817', 'sb', '0.00', '0.00', '2016-12-18 09:46:36', '执行订单', '52200020161218792');
INSERT INTO `credit_record` VALUES ('291751', 'user1', '1155.00', '-2.50', '2016-12-21 00:05:24', '撤销订单', '29226120161220394');
INSERT INTO `credit_record` VALUES ('350188', 'user1', '1157.50', '-2.50', '2016-12-21 00:02:06', '撤销订单', '29226120161220393');
INSERT INTO `credit_record` VALUES ('394094', 'sb', '0.00', '0.00', '2016-12-18 10:55:44', '执行订单', '52200020161218160');
INSERT INTO `credit_record` VALUES ('411451', 'sb', '1000.00', '1000.00', '2016-12-18 17:30:13', '执行订单', '52200020161218232');
INSERT INTO `credit_record` VALUES ('441883', 'user1', '6080.00', '5000.00', '2016-12-21 00:14:38', '信用充值', '');
INSERT INTO `credit_record` VALUES ('500619', 'user1', '6470.00', '390.00', '2016-12-21 00:38:06', '执行订单', '29226120161221525');
INSERT INTO `credit_record` VALUES ('573594', 'sb', '0.00', '0.00', '2016-12-17 19:14:37', '撤销订单', '52200020161217419');
INSERT INTO `credit_record` VALUES ('617668', 'user1', '6560.00', '90.00', '2016-12-21 01:43:29', '执行订单', '18502920161221864');
INSERT INTO `credit_record` VALUES ('694223', 'user1', '1160.00', '1000.00', '2016-12-20 23:51:49', '信用充值', '');
INSERT INTO `credit_record` VALUES ('711505', 'sb', '3020.00', '10.00', '2016-12-18 21:33:55', '执行订单', '52200020161218641');
INSERT INTO `credit_record` VALUES ('761310', 'user1', '160.00', '160.00', '2016-12-20 22:55:21', '执行订单', '29226120161220903');
INSERT INTO `credit_record` VALUES ('766988', 'sb', '3010.00', '0.00', '2016-12-18 21:24:22', '执行订单', '52200020161218770');
INSERT INTO `credit_record` VALUES ('871687', 'sb', '3030.00', '10.00', '2016-12-18 21:35:12', '执行订单', '52200020161218239');
INSERT INTO `credit_record` VALUES ('893037', 'sb', '3010.00', '2010.00', '2016-12-18 18:34:29', '信用充值', '');

-- ----------------------------
-- Table structure for hotel
-- ----------------------------
DROP TABLE IF EXISTS `hotel`;
CREATE TABLE `hotel` (
  `hotel_id` int(6) unsigned zerofill NOT NULL,
  `hotel_name` char(60) NOT NULL,
  `star` mediumint(10) unsigned NOT NULL,
  `address` varchar(60) NOT NULL,
  `trading_area` varchar(40) NOT NULL,
  `description` text NOT NULL,
  `service` text NOT NULL,
  PRIMARY KEY (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hotel
-- ----------------------------
INSERT INTO `hotel` VALUES ('185029', '仙林酒店2', '5', '南京市', '仙林商圈', '', '');
INSERT INTO `hotel` VALUES ('292261', '3栋豪华宿舍', '4', '南京市', '仙林商圈', '电话：89685000 非常好的酒店 进出记得刷卡', '24小时不断电不断网');
INSERT INTO `hotel` VALUES ('346436', '全季酒店', '5', '杭州市', '滨江商圈', '江畔', '西湖美景尽收眼底');
INSERT INTO `hotel` VALUES ('511454', '越台酒店', '2', '杭州市', '武林商圈', '在浙江大学附近', '前台大妈服务');
INSERT INTO `hotel` VALUES ('515765', '凯悦酒店', '5', '广州市', '天河商圈', '真正五星级', '只有你想不到的服务');
INSERT INTO `hotel` VALUES ('516515', '君亭酒店', '2', '杭州市', '武林商圈', '位于武林广场', '还行的服务');
INSERT INTO `hotel` VALUES ('522000', '榕江大酒店', '4', '南京市', '仙林商圈', '', '');
INSERT INTO `hotel` VALUES ('522001', '榕江中酒店', '3', '南京市', '夫子庙商圈', '包吃包住', '没有特殊服务');
INSERT INTO `hotel` VALUES ('522002', '榕江小酒店', '5', '南京市', '新街口商圈', '包吃包住', '没有特殊服务');
INSERT INTO `hotel` VALUES ('522003', '儒家酒店', '1', '上海市', '浦东商圈', '地势可以', '泳池');
INSERT INTO `hotel` VALUES ('522151', '凯乐酒店', '4', '上海市', '南京路商圈', '地势可以', '一流服务');
INSERT INTO `hotel` VALUES ('646919', '仙林酒店1', '3', '南京市', '仙林商圈', '6666', '没有特殊服务哦');
INSERT INTO `hotel` VALUES ('664889', '仙林酒店3', '1', '南京市', '仙林商圈', '', '');
INSERT INTO `hotel` VALUES ('732723', '南大招待所', '5', '南京市', '仙林商圈', '', '');
INSERT INTO `hotel` VALUES ('984512', '英尊酒店', '1', '南京市', '仙林商圈', '南大对面，很方柏霓', '普普通通');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `order_id` varchar(20) NOT NULL,
  `hotel_id` int(6) unsigned zerofill NOT NULL,
  `username` varchar(20) NOT NULL,
  `order_type` varchar(20) NOT NULL,
  `hotel_name` char(60) NOT NULL,
  `room_type` varchar(20) NOT NULL,
  `room_amount` smallint(10) unsigned NOT NULL,
  `room_number` varchar(20) NOT NULL DEFAULT '',
  `person_amount` smallint(10) unsigned NOT NULL,
  `with_children` tinyint(4) NOT NULL,
  `generate_time` datetime NOT NULL,
  `expected_checkin_time` datetime NOT NULL,
  `checkin_time` datetime NOT NULL,
  `expected_leave_time` datetime NOT NULL,
  `leave_time` datetime NOT NULL,
  `last_execute_time` datetime NOT NULL,
  `cancel_time` datetime NOT NULL,
  `original_price` double(8,2) NOT NULL,
  `actual_price` double(8,2) NOT NULL,
  `promotion_type` varchar(20) NOT NULL DEFAULT '',
  `review_time` datetime NOT NULL,
  `rating` smallint(10) unsigned NOT NULL DEFAULT '0',
  `review` text NOT NULL,
  `ha_time` datetime NOT NULL,
  `ha_result` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('18502920161221864', '185029', 'user1', '已执行订单', '仙林酒店2', '单人房', '1', '312', '1', '1', '2016-12-21 01:41:24', '2016-12-21 14:00:00', '2016-12-21 01:43:00', '2016-12-21 01:43:00', '0001-01-01 00:00:00', '2016-12-21 20:00:00', '0001-01-01 00:00:00', '100.00', '90.00', '会员折扣', '0001-01-01 00:00:00', '0', '', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220393', '292261', 'user1', '已撤销订单', '3栋豪华宿舍', '总统套房', '1', '', '1', '1', '2016-12-20 23:01:27', '2016-12-20 23:01:27', '0001-01-01 00:00:00', '2016-12-21 12:00:00', '0001-01-01 00:00:00', '2016-12-21 05:01:27', '0001-01-01 00:00:00', '500.00', '5.00', '会员折扣', '0001-01-01 00:00:00', '0', '', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220394', '292261', 'user1', '已撤销订单', '3栋豪华宿舍', '总统套房', '1', '', '1', '1', '2016-12-20 23:01:27', '2016-12-20 23:01:27', '0001-01-01 00:00:00', '2016-12-21 12:00:00', '0001-01-01 00:00:00', '2016-12-21 05:01:27', '0001-01-01 00:00:00', '500.00', '5.00', '会员折扣', '0001-01-01 00:00:00', '0', '', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220395', '292261', 'user1', '未执行订单', '3栋豪华宿舍', '总统套房', '1', '', '1', '1', '2016-12-20 23:01:27', '2016-12-20 23:01:27', '0001-01-01 00:00:00', '2016-12-21 12:00:00', '0001-01-01 00:00:00', '2016-12-21 05:01:27', '0001-01-01 00:00:00', '500.00', '5.00', '会员折扣', '0001-01-01 00:00:00', '0', '', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220396', '292261', 'user1', '未执行订单', '3栋豪华宿舍', '总统套房', '1', '', '1', '1', '2016-12-20 23:01:27', '2016-12-20 23:01:27', '0001-01-01 00:00:00', '2016-12-21 12:00:00', '0001-01-01 00:00:00', '2016-12-21 05:01:27', '0001-01-01 00:00:00', '500.00', '5.00', '会员折扣', '0001-01-01 00:00:00', '0', '', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220903', '292261', 'user1', '已执行订单', '3栋豪华宿舍', '单人房', '1', '110', '1', '0', '2016-12-20 22:52:56', '2016-12-20 22:52:56', '2016-12-20 22:55:00', '2016-12-21 22:55:00', '2016-12-21 22:55:00', '2016-12-21 04:52:56', '0001-01-01 00:00:00', '160.00', '160.00', '', '2016-12-20 22:58:20', '5', '五分好评！', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220904', '292261', 'user1', '已执行订单', '3栋豪华宿舍', '单人房', '1', '110', '1', '0', '2016-12-20 22:52:56', '2016-12-20 22:52:56', '2016-12-20 22:55:00', '2016-12-21 22:55:00', '2016-12-21 22:55:00', '2016-12-21 04:52:56', '0001-01-01 00:00:00', '160.00', '160.00', '', '2016-12-20 22:58:20', '5', '酒店服务特别的好，从前台办理入住，到餐厅就餐，到房间服务，特别满意。看到有小孩还特意送来了小孩的洗漱用品。客房服务员张小婷和夏杰还给宝宝叠了兔子。酒店楼下就是德基广场，吃饭购物都很方便。唯一小遗憾是酒店内的高脚杯是正着放的，关抽屉的时候就给摔下来碎了，希望下次高脚杯可以倒着放，还稳当点，或者放在安全固定的地方。一定要到酒店梅苑餐厅吃饭，味道一级棒。', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220905', '292261', 'user1', '已执行订单', '3栋豪华宿舍', '单人房', '1', '110', '1', '0', '2016-12-20 22:52:56', '2016-12-20 22:52:56', '2016-12-20 22:55:00', '2016-12-21 22:55:00', '2016-12-21 22:55:00', '2016-12-21 04:52:56', '0001-01-01 00:00:00', '160.00', '160.00', '', '2016-12-20 22:58:20', '5', '前台给免费升了房，但是行政房的房间也不大，早餐在36F，品种不多，酒店地理位置超级棒，出门就是地铁，去南京各景点都非常方便，另外送了2F茶座的下午茶，服务很棒！', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220906', '292261', 'user1', '已执行订单', '3栋豪华宿舍', '单人房', '1', '110', '1', '0', '2016-12-20 22:52:56', '2016-12-20 22:52:56', '2016-12-20 22:55:00', '2016-12-21 22:55:00', '2016-12-21 22:55:00', '2016-12-21 04:52:56', '0001-01-01 00:00:00', '160.00', '160.00', '', '2016-12-20 22:58:20', '4', '不亏是老牌饭店，服务和各项设施都没的说，酒店整体环境也很棒，地理位置很好，交通便捷，有机会下次还会选择入住！！！', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220907', '292261', 'user1', '已执行订单', '3栋豪华宿舍', '单人房', '1', '110', '1', '0', '2016-12-20 22:52:56', '2016-12-20 22:52:56', '2016-12-20 22:55:00', '2016-12-21 22:55:00', '2016-12-21 22:55:00', '2016-12-21 04:52:56', '0001-01-01 00:00:00', '160.00', '160.00', '', '2016-12-20 22:58:20', '5', '五分好评！', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220908', '292261', 'user1', '已执行订单', '3栋豪华宿舍', '单人房', '1', '110', '1', '0', '2016-12-20 22:52:56', '2016-12-20 22:52:56', '2016-12-20 22:55:00', '2016-12-21 22:55:00', '2016-12-21 22:55:00', '2016-12-21 04:52:56', '0001-01-01 00:00:00', '160.00', '160.00', '', '2016-12-20 22:58:20', '5', '位置奇佳，服务极好。', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220909', '292261', 'user1', '已执行订单', '3栋豪华宿舍', '单人房', '1', '110', '1', '0', '2016-12-20 22:52:56', '2016-12-20 22:52:56', '2016-12-20 22:55:00', '2016-12-21 22:55:00', '2016-12-21 22:55:00', '2016-12-21 04:52:56', '0001-01-01 00:00:00', '160.00', '160.00', '', '2016-12-20 22:58:20', '1', '还可以的酒店吧，整体坏境跟价格都比较不错。。', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220910', '292261', 'user1', '已执行订单', '3栋豪华宿舍', '单人房', '1', '110', '1', '0', '2016-12-20 22:52:56', '2016-12-20 22:52:56', '2016-12-20 22:55:00', '2016-12-21 22:55:00', '2016-12-21 22:55:00', '2016-12-21 04:52:56', '0001-01-01 00:00:00', '160.00', '160.00', '', '2016-12-20 22:58:20', '2', '地理位置优越，环境不错', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220911', '292261', 'user1', '已执行订单', '3栋豪华宿舍', '单人房', '1', '110', '1', '0', '2016-12-20 22:52:56', '2016-12-20 22:52:56', '2016-12-20 22:55:00', '2016-12-21 22:55:00', '2016-12-21 22:55:00', '2016-12-21 04:52:56', '0001-01-01 00:00:00', '160.00', '160.00', '', '2016-12-20 22:58:20', '3', '浴缸，依然是那么的小', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220912', '292261', 'user1', '已执行订单', '3栋豪华宿舍', '单人房', '1', '110', '1', '0', '2016-12-20 22:52:56', '2016-12-20 22:52:56', '2016-12-20 22:55:00', '2016-12-21 22:55:00', '2016-12-21 22:55:00', '2016-12-21 04:52:56', '0001-01-01 00:00:00', '160.00', '160.00', '', '2016-12-20 22:58:20', '4', '还可以的酒店吧，整体坏境跟价格都比较不错。。', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161220913', '292261', 'user1', '异常订单', '3栋豪华宿舍', '单人房', '1', '110', '1', '0', '2016-12-20 22:52:56', '2016-12-20 22:52:56', '2016-12-20 22:55:00', '2016-12-21 22:55:00', '2016-12-21 22:55:00', '2016-12-21 04:52:56', '0001-01-01 00:00:00', '160.00', '160.00', '', '2016-12-20 22:58:20', '4', '还可以的酒店吧，整体坏境跟价格都比较不错。。', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('29226120161221525', '292261', 'user1', '已执行订单', '3栋豪华宿舍', '总统套房', '1', '120', '1', '1', '2016-12-21 00:37:35', '2016-12-21 14:00:00', '2016-12-21 00:37:00', '2016-12-22 00:37:00', '0001-01-01 00:00:00', '2016-12-21 20:00:00', '0001-01-01 00:00:00', '500.00', '390.00', '特定期间折扣', '0001-01-01 00:00:00', '0', '', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('52200020161217419', '522000', 'sb', '已撤销订单', '榕江大酒店', '单人房', '1', '', '1', '1', '2016-12-17 12:51:47', '2016-12-17 14:00:00', '0001-01-01 00:00:00', '2016-12-28 12:00:00', '0001-01-01 00:00:00', '2016-12-17 20:00:00', '0001-01-01 00:00:00', '0.00', '0.00', '', '0001-01-01 00:00:00', '0', '', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('52200020161218160', '522000', 'sb', '已执行订单', '榕江大酒店', '单人房', '1', '588', '1', '1', '2016-12-18 10:54:19', '2016-12-18 14:00:00', '2016-12-18 10:55:00', '2016-12-20 10:55:00', '2016-12-19 10:55:00', '2016-12-18 20:00:00', '0001-01-01 00:00:00', '0.00', '0.00', '', '2016-12-19 23:29:26', '3', '151665sadasdsadjasdjasodjaosdjasodjasojdaosdjoasjdoasjdoasjdoasjdoajsdoasjdoasjdoasjdoajsdasd', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('52200020161218232', '522000', 'sb', '已执行订单', '榕江大酒店', '情侣房', '1', '250', '2', '1', '2016-12-18 11:32:01', '2016-12-18 14:00:00', '2016-12-20 17:29:00', '2016-12-20 17:29:00', '2016-12-21 01:16:00', '2016-12-18 20:00:00', '0001-01-01 00:00:00', '1000.00', '1000.00', '', '2016-12-20 01:17:18', '4', 'ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('52200020161218641', '522000', 'sb', '已执行订单', '榕江大酒店', '单人房', '1', '55', '1', '1', '2016-12-18 21:33:20', '2016-12-18 21:33:20', '2016-12-18 21:33:00', '2016-12-19 21:33:00', '2016-12-20 23:51:00', '2016-12-19 03:33:20', '0001-01-01 00:00:00', '100.00', '10.00', '多间预定特惠', '2016-12-19 23:53:05', '1', '6666', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('52200020161218770', '522000', 'sb', '已执行订单', '榕江大酒店', '单人房', '1', '220', '1', '1', '2016-12-18 21:21:27', '2016-12-18 21:21:27', '2016-12-19 21:23:00', '2016-12-18 21:23:00', '2016-12-22 01:22:00', '2016-12-19 03:21:27', '0001-01-01 00:00:00', '0.00', '0.00', '', '2016-12-20 01:23:37', '5', '22', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('52200020161218792', '522000', 'sb', '已执行订单', '榕江大酒店', '单人房', '1', '666', '1', '0', '2016-12-18 09:41:51', '2016-12-18 14:00:00', '2016-12-18 09:44:00', '2016-12-20 16:44:00', '2016-12-19 09:52:00', '2016-12-18 20:00:00', '0001-01-01 00:00:00', '0.00', '0.00', '', '2016-12-18 10:00:52', '3', '爱上大家圣诞节阿斯of草if好is还哦啊好大手大脚奥斯丁骄傲叫撒到大哦啊搜街道上哦对奇偶阿萨德偶家阿萨德教室哦氨基酸哦啊搜到就搜到加嗖嗖的凹静安寺激动啊京东囧', '0001-01-01 00:00:00', '');
INSERT INTO `order_info` VALUES ('52200020161220359', '522000', 'user1', '已撤销订单', '榕江大酒店', '标准房', '5', '', '5', '0', '2016-12-20 23:57:22', '2016-12-20 23:57:22', '0001-01-01 00:00:00', '2016-12-21 12:00:00', '0001-01-01 00:00:00', '2016-12-21 05:57:22', '0001-01-01 00:00:00', '600.00', '150.00', '多间预定特惠', '0001-01-01 00:00:00', '0', '', '0001-01-01 00:00:00', '');

-- ----------------------------
-- Table structure for personnel
-- ----------------------------
DROP TABLE IF EXISTS `personnel`;
CREATE TABLE `personnel` (
  `personnel_id` int(6) unsigned zerofill NOT NULL,
  `password` char(20) NOT NULL,
  `personnel_type` varchar(20) NOT NULL DEFAULT '',
  `name` varchar(20) NOT NULL DEFAULT '',
  `hotel_id` int(6) unsigned zerofill NOT NULL DEFAULT '000000',
  PRIMARY KEY (`personnel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of personnel
-- ----------------------------
INSERT INTO `personnel` VALUES ('100000', '123456', '酒店工作人员', 'leftovers', '522000');
INSERT INTO `personnel` VALUES ('120110', 'password', '网站营销人员', '哦', '000000');
INSERT INTO `personnel` VALUES ('120120', 'pass', '网站管理人员', '呃', '000000');
INSERT INTO `personnel` VALUES ('138047', '123456', '酒店工作人员', 'worker2', '185029');
INSERT INTO `personnel` VALUES ('568762', '123456', '酒店工作人员', '东哥', '292261');
INSERT INTO `personnel` VALUES ('685782', '123456', '酒店工作人员', 'hitigerzzz', '732723');
INSERT INTO `personnel` VALUES ('741196', '123456', '酒店工作人员', 'worker1', '646919');
INSERT INTO `personnel` VALUES ('996844', '123456', '酒店工作人员', 'worker3', '664889');

-- ----------------------------
-- Table structure for promotion
-- ----------------------------
DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion` (
  `promotion_id` int(6) unsigned zerofill NOT NULL,
  `promotion_type` varchar(40) NOT NULL DEFAULT '',
  `hotel_id` bigint(6) NOT NULL,
  `discount` double NOT NULL DEFAULT '0',
  `least_rooms` smallint(10) unsigned NOT NULL DEFAULT '0',
  `begin_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `enterprise1` varchar(60) NOT NULL DEFAULT '',
  `enterprise2` varchar(60) NOT NULL DEFAULT '',
  `enterprise3` varchar(60) NOT NULL DEFAULT '',
  `enterprise4` varchar(60) NOT NULL DEFAULT '',
  `enterprise5` varchar(60) NOT NULL DEFAULT '',
  `trading_area1` varchar(40) NOT NULL DEFAULT '',
  `tra_discount1` double NOT NULL,
  `trading_area2` varchar(40) NOT NULL DEFAULT '',
  `tra_discount2` double NOT NULL,
  `trading_area3` varchar(40) NOT NULL DEFAULT '',
  `tra_discount3` double NOT NULL,
  `trading_area4` varchar(40) NOT NULL DEFAULT '',
  `tra_discount4` double NOT NULL,
  `trading_area5` varchar(40) NOT NULL DEFAULT '',
  `tra_discount5` double NOT NULL,
  `credit1` double(8,2) NOT NULL,
  `mem_discount1` double NOT NULL,
  `credit2` double(8,2) NOT NULL,
  `mem_discount2` double NOT NULL,
  `credit3` double(8,2) NOT NULL,
  `mem_discount3` double NOT NULL,
  `credit4` double(8,2) NOT NULL,
  `mem_discount4` double NOT NULL,
  `credit5` double(8,2) NOT NULL,
  `mem_discount5` double NOT NULL,
  `credit6` double(8,2) NOT NULL,
  `mem_discount6` double NOT NULL,
  PRIMARY KEY (`promotion_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of promotion
-- ----------------------------
INSERT INTO `promotion` VALUES ('110327', '合作企业优惠', '292261', '0.8', '0', '0001-01-01 00:00:00', '0001-01-01 00:00:00', '南京大学', '', '', '', '', '', '1', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');
INSERT INTO `promotion` VALUES ('170384', '特定商圈优惠', '999999', '0', '0', '0001-01-01 00:00:00', '0001-01-01 00:00:00', '', '', '', '', '', '仙林商圈', '0.88', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');
INSERT INTO `promotion` VALUES ('205538', '特定期间折扣', '999999', '0.8', '0', '2016-12-18 17:50:00', '2016-12-18 17:50:00', '', '', '', '', '', '', '1', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');
INSERT INTO `promotion` VALUES ('211717', '会员折扣', '999999', '0', '0', '0001-01-01 00:00:00', '0001-01-01 00:00:00', '', '', '', '', '', '', '1', '', '1', '', '1', '', '1', '', '1', '1000.00', '0.95', '2000.00', '0.93', '5000.00', '0.9', '10000.00', '0.89', '12000.00', '0.85', '15000.00', '0.8');
INSERT INTO `promotion` VALUES ('240186', '合作企业优惠', '522000', '0.9', '0', '0001-01-01 00:00:00', '0001-01-01 00:00:00', '南京大学', '', '', '', '', '', '1', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');
INSERT INTO `promotion` VALUES ('245418', '特定期间折扣', '292261', '0.78', '0', '2016-12-21 00:00:00', '2016-12-23 00:00:00', '', '', '', '', '', '', '1', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');
INSERT INTO `promotion` VALUES ('286939', '特定期间折扣', '522000', '0.06', '0', '2016-12-20 14:24:00', '2016-12-20 14:24:00', '', '', '', '', '', '', '1', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');
INSERT INTO `promotion` VALUES ('443351', '特定商圈优惠', '999999', '0', '0', '0001-01-01 00:00:00', '0001-01-01 00:00:00', '', '', '', '', '', '新街口商圈', '0.98', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');
INSERT INTO `promotion` VALUES ('450692', '多间预定特惠', '522000', '0.56', '2', '0001-01-01 00:00:00', '0001-01-01 00:00:00', '', '', '', '', '', '', '1', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');
INSERT INTO `promotion` VALUES ('457782', '特定期间折扣', '522000', '0.56', '0', '2016-12-20 14:24:00', '2016-12-20 14:24:00', '', '', '', '', '', '', '1', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');
INSERT INTO `promotion` VALUES ('512887', '多间预定特惠', '292261', '0.85', '2', '0001-01-01 00:00:00', '0001-01-01 00:00:00', '', '', '', '', '', '', '1', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');
INSERT INTO `promotion` VALUES ('611829', '生日特惠折扣', '522000', '0.255', '0', '0001-01-01 00:00:00', '0001-01-01 00:00:00', '', '', '', '', '', '', '1', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');
INSERT INTO `promotion` VALUES ('641314', '特定商圈优惠', '999999', '0', '0', '0001-01-01 00:00:00', '0001-01-01 00:00:00', '', '', '', '', '', '珠江路商圈', '0.89', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');
INSERT INTO `promotion` VALUES ('698895', '多间预定特惠', '522000', '0.25', '3', '0001-01-01 00:00:00', '0001-01-01 00:00:00', '', '', '', '', '', '', '1', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');
INSERT INTO `promotion` VALUES ('715868', '合作企业优惠', '522000', '0.7', '0', '0001-01-01 00:00:00', '0001-01-01 00:00:00', '666', '', '', '', '', '', '1', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');
INSERT INTO `promotion` VALUES ('766269', '生日特惠折扣', '292261', '0.9', '0', '0001-01-01 00:00:00', '0001-01-01 00:00:00', '', '', '', '', '', '', '1', '', '1', '', '1', '', '1', '', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1', '0.00', '1');

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `room_id` bigint(6) NOT NULL AUTO_INCREMENT,
  `hotel_id` int(6) unsigned zerofill NOT NULL,
  `room_type` varchar(20) NOT NULL,
  `total` smallint(10) unsigned NOT NULL,
  `available` smallint(10) unsigned NOT NULL,
  `price` double(8,2) NOT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=900541 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES ('109799', '292261', '总统套房', '5', '5', '500.00');
INSERT INTO `room` VALUES ('121121', '522000', '单人房', '20', '19', '220.00');
INSERT INTO `room` VALUES ('184325', '522000', '双人房', '5', '5', '500.00');
INSERT INTO `room` VALUES ('208543', '292261', '商务套房', '12', '12', '198.00');
INSERT INTO `room` VALUES ('209402', '292261', '套房', '8', '8', '80.00');
INSERT INTO `room` VALUES ('251982', '185029', '单人房', '10', '10', '100.00');
INSERT INTO `room` VALUES ('535404', '292261', '家庭房', '10', '10', '300.00');
INSERT INTO `room` VALUES ('671956', '292261', '标准房', '20', '20', '100.00');
INSERT INTO `room` VALUES ('692149', '292261', '双人房', '10', '10', '200.00');
INSERT INTO `room` VALUES ('722831', '292261', '单人房', '15', '12', '160.00');
INSERT INTO `room` VALUES ('900540', '522000', '标准房', '20', '20', '120.00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `password` char(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `gender` tinyint(4) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `member_type` varchar(20) NOT NULL,
  `level` smallint(10) unsigned NOT NULL,
  `birthday` date NOT NULL,
  `enterprise` char(80) NOT NULL DEFAULT '',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('ɩ', 'ɩ', 'ɻɪ', '1', 'ȹȽȱȹȽȾȽȹȼȸȾ', '普通用户', '0', '2016-12-01', '');
INSERT INTO `user` VALUES ('ɻɪ', 'ɻɪ', 'ɻɪ', '1', 'ȹȽȱȹȽȾȽȹȼȸȾ', '至尊会员', '0', '2016-11-27', '666');
INSERT INTO `user` VALUES ('ɽɻɭɺȹ', 'ȹȺȻȼȽȾ', 'ɥɥɸ', '1', 'ȹȺȻȼȽȾȿȰȱȹȸ', '至尊会员', '0', '2016-12-01', '南京大学');
INSERT INTO `user` VALUES ('ɿɲɫ', 'ȹȺȻ', '嘼巟怘', '1', 'ȹȽȱȹȽȾȽȹȼȸȾ', '普通用户', '0', '2016-11-27', '');
SET FOREIGN_KEY_CHECKS=1;
