/*
SQLyog Professional v12.09 (64 bit)
MySQL - 5.7.20-log : Database - flower
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`flower` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `flower`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_user_name` varchar(20) DEFAULT NULL,
  `admin_user_pass` varchar(20) DEFAULT NULL,
  `admin_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`admin_id`,`admin_user_name`,`admin_user_pass`,`admin_name`) values (1,'admin','admin','Lemon');

/*Table structure for table `flower` */

DROP TABLE IF EXISTS `flower`;

CREATE TABLE `flower` (
  `flower_id` int(11) NOT NULL AUTO_INCREMENT,
  `flower_name` varchar(20) NOT NULL,
  `flower_count` int(11) NOT NULL,
  `flower_price` double NOT NULL,
  `flower_sale` int(11) NOT NULL,
  PRIMARY KEY (`flower_name`),
  UNIQUE KEY `flower_id` (`flower_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `flower` */

insert  into `flower`(`flower_id`,`flower_name`,`flower_count`,`flower_price`,`flower_sale`) values (4,'康乃馨',1013,56,87),(2,'曼陀罗',1999,15,55),(5,'牡丹花',1096,78,145),(17,'玫瑰',830,88,167),(20,'罂粟花',956,8888,20),(7,'芙蓉',1033,30,30),(19,'蓝色妖姬',1000,199,0);

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(20) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `order_time` varchar(20) DEFAULT NULL,
  `order_condition` enum('提交','配货','派送','签收') DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `id` (`id`),
  KEY `user_name` (`user_name`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `user` (`user_user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

/*Data for the table `order` */

insert  into `order`(`id`,`order_id`,`user_name`,`order_time`,`order_condition`) values (60,'20180403180106','张柳进','2018-04-03-18-01-06','派送'),(61,'20180403180139','张柳进','2018-04-03-18-01-39','派送'),(63,'20180403182945','fgg','2018-04-03-18-29-45','派送'),(74,'20180403234218','FMZKQLING','2018-04-03-23-42-18','签收'),(75,'20180403234233','FMZKQLING','2018-04-03-23-42-33','签收');

/*Table structure for table `order_detial` */

DROP TABLE IF EXISTS `order_detial`;

CREATE TABLE `order_detial` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(20) NOT NULL,
  `flower_name` varchar(20) NOT NULL,
  `num` int(11) NOT NULL,
  `flag` enum('0','1') NOT NULL DEFAULT '0',
  PRIMARY KEY (`order_id`,`flower_name`),
  UNIQUE KEY `id` (`id`),
  KEY `flower_name` (`flower_name`),
  CONSTRAINT `order_detial_ibfk_1` FOREIGN KEY (`flower_name`) REFERENCES `flower` (`flower_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_detial_ibfk_2` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;

/*Data for the table `order_detial` */

insert  into `order_detial`(`id`,`order_id`,`flower_name`,`num`,`flag`) values (68,'20180403180106','芙蓉',2,'1'),(69,'20180403180139','康乃馨',3,'1'),(73,'20180403182945','曼陀罗',55,'1'),(85,'20180403234218','玫瑰',90,'1'),(86,'20180403234233','玫瑰',20,'1');

/*Table structure for table `shop_car` */

DROP TABLE IF EXISTS `shop_car`;

CREATE TABLE `shop_car` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `flower_name` varchar(20) NOT NULL,
  `collect_num` int(11) NOT NULL,
  `add_time` varchar(30) NOT NULL,
  `last_modify_time` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`user_name`,`flower_name`),
  UNIQUE KEY `id` (`id`),
  KEY `shop_car_ibfk_2` (`flower_name`),
  CONSTRAINT `shop_car_ibfk_1` FOREIGN KEY (`user_name`) REFERENCES `user` (`user_user_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `shop_car_ibfk_2` FOREIGN KEY (`flower_name`) REFERENCES `flower` (`flower_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=121 DEFAULT CHARSET=utf8;

/*Data for the table `shop_car` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_user_name` varchar(20) NOT NULL,
  `user_user_pass` varchar(20) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  `user_user_sex` enum('男','女') DEFAULT NULL,
  `user_age` int(11) DEFAULT NULL,
  `user_phone` varchar(20) DEFAULT NULL,
  `user_addres` varchar(50) DEFAULT NULL,
  `user_email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`user_user_name`),
  UNIQUE KEY `user_user_name` (`user_user_name`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`user_id`,`user_user_name`,`user_user_pass`,`user_name`,`user_user_sex`,`user_age`,`user_phone`,`user_addres`,`user_email`) values (2,'ada','456465','ADFA','男',21,'15274925554','ADFASD','lemon@qq.com'),(4,'fgg','abc123','请问','男',12,'13487718771','133','2111111@qq.COM'),(5,'FMZKQLING','FMZKQLING','FMZKQLING','男',15,'15274925554','FASDFAS','FASDFASD@QQ.COM'),(6,'test','ZKQ1415..','test','男',12,'15274925554','test','test@qq.com'),(1,'userOne','userOne','userOne','男',18,'15674558946','张家界','lemonzuo@qq.com'),(7,'ZKQ980724','ZKQ980724','ZKQ','男',7,'15274925554','IUHU','750583875@qq.com'),(3,'张柳进','zhang5620','张柳进','女',18,'18874400079','湖南省张家界市永定区吉首大学张家界学院','46546@qq.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
