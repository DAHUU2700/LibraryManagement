/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : library-management

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2022-11-10 10:25:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系方式',
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_key` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '书名',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
  `publish_date` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '出版日期',
  `publisher` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '出版社',
  `author` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '作者',
  `category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分类',
  `cover` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '封面',
  `book_no` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标准码',
  `score` int(10) DEFAULT NULL COMMENT '图书积分',
  `nums` int(10) DEFAULT '0' COMMENT '图书数量',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `book_no_index` (`book_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图书名称',
  `book_no` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图书标准码',
  `user_no` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名（user.username）',
  `user_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名称',
  `user_phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户联系方式',
  `score` int(10) DEFAULT NULL COMMENT '借书积分',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '已借出' COMMENT '借书状态',
  `days` int(20) DEFAULT NULL COMMENT '借书天数',
  `return_date` datetime DEFAULT NULL COMMENT '归还天数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '分类名称',
  `remark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `pid` int(11) DEFAULT NULL COMMENT '父级id',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for restore
-- ----------------------------
DROP TABLE IF EXISTS `restore`;
CREATE TABLE `restore` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图书名称',
  `book_no` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图书标准码',
  `user_no` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名（user.username）',
  `user_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名称',
  `user_phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户联系方式',
  `score` int(10) DEFAULT NULL COMMENT '借书积分',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL COMMENT '更新时间',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '已借出' COMMENT '借书状态',
  `days` int(20) DEFAULT NULL COMMENT '借书天数',
  `return_date` datetime DEFAULT NULL COMMENT '归还天数',
  `real_date` datetime DEFAULT NULL COMMENT '实际归还日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系方式',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '住址',
  `account` int(10) DEFAULT '0' COMMENT '账号余额',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1' COMMENT '用户账号状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_index` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
