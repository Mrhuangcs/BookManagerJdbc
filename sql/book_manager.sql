/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.7.20 : Database - book_manager
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`book_manager` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `book_manager`;

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(50) DEFAULT NULL,
  `author` varchar(50) DEFAULT NULL,
  `price` decimal(10,1) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `borrow` int(11) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `book` */

insert  into `book`(`book_id`,`book_name`,`author`,`price`,`number`,`borrow`) values 
(1,'水浒传','老李',2.8,2,6),
(2,'操作系统','老王',2.5,0,2),
(3,'大学英语','老黄',4.6,0,4),
(4,'高等数学','老张',4.6,1,2),
(5,'大学语文','老胡',5.0,1,5),
(6,'网络基础','老何',7.0,0,3),
(7,'三国演义','老罗',3.5,0,3),
(9,'帅气小伙','老黄',200.0,1,100),
(11,'物联网导论','老吴',2.0,1,3),
(12,'数据库系统概论','老张',2.5,2,0);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `gender` int(1) DEFAULT NULL,
  `classes` varchar(10) DEFAULT NULL,
  `balance` decimal(10,1) DEFAULT NULL,
  `borrow_book` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`user_id`,`user_name`,`password`,`gender`,`classes`,`balance`,`borrow_book`) values 
(1,'admin','123456',1,'1801',84.0,0),
(3,'jing','123456',1,'1801',0.0,0),
(4,'huang','123456',1,'1801',192.9,5),
(5,'ddd','123',1,'1802',0.0,0),
(8,'ee','123123',1,'1801',0.0,0),
(9,'tt','123456',1,'1803',114.2,5),
(14,'rr','123456',1,'1909',20.0,2),
(19,'mm','123456',0,'1901',20.0,0);

/*Table structure for table `user_book` */

DROP TABLE IF EXISTS `user_book`;

CREATE TABLE `user_book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `book_id` int(11) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `user_book_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_book_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

/*Data for the table `user_book` */

insert  into `user_book`(`id`,`user_id`,`book_id`,`day`) values 
(29,4,6,3),
(32,4,3,3),
(35,4,11,2),
(36,9,1,2),
(37,9,2,3),
(39,4,7,2),
(40,9,3,2),
(43,14,4,2),
(44,14,11,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
