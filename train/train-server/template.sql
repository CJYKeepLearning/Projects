/*
 Navicat Premium Data Transfer

 Source Server         : sky
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : skyemperor.top:3306
 Source Schema         : train

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 23/08/2021 23:38:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_perm
-- ----------------------------
DROP TABLE IF EXISTS `sys_perm`;
CREATE TABLE `sys_perm`  (
  `perm_id` bigint NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `perm_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '权限名称',
  `perm_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '权限key',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '接口地址',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '0' COMMENT '权限状态（0正常 1停用）',
  PRIMARY KEY (`perm_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_perm
-- ----------------------------
INSERT INTO `sys_perm` VALUES (1, '超级管理员权限', '*:*:*', NULL, '0');
INSERT INTO `sys_perm` VALUES (2, '在猫咪详情页评论', 'comment:spectrum:add', '/comment/cat/add', '0');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色权限字符串',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色状态（0正常 1停用）',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'super_admin', '0');
INSERT INTO `sys_role` VALUES (2, '管理员', 'admin', '0');
INSERT INTO `sys_role` VALUES (3, '普通用户', 'general', '0');

-- ----------------------------
-- Table structure for sys_role_perm_link
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_perm_link`;
CREATE TABLE `sys_role_perm_link`  (
  `role_id` bigint NOT NULL,
  `perm_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`, `perm_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_perm_link
-- ----------------------------
INSERT INTO `sys_role_perm_link` VALUES (1, 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户账号',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '密码',
  `avatar` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '头像地址',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', NULL, '$2a$10$1yqHnuS6plSZ1Wnq6Md8.eNid4w3QAAZD4d3fyZLf1TIcJD699DUm', NULL, 0, '127.0.0.1', '2021-01-29 20:03:52');
INSERT INTO `sys_user` VALUES (2, 'sky', NULL, '$2a$10$ARK5E.I98d7x23efT05FLOtQP5DP2nTMEAwUTX80Oq002eD6VorJW', NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (16, '11', NULL, '$2a$10$YGIKtyKLjHkvV6GF2J.z.O/YUlLwFsD2R46QpFF16ADu8SGvsrvau', NULL, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (17, 'aa', NULL, '$2a$10$Pw8TOgHqUhd4KzhpHBPXReP3PRrEi4M7m0zASDWmgfcENXm7jhBH2', NULL, 0, '127.0.0.1', '2021-08-23 23:27:48');

-- ----------------------------
-- Table structure for sys_user_role_link
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_link`;
CREATE TABLE `sys_user_role_link`  (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role_link
-- ----------------------------
INSERT INTO `sys_user_role_link` VALUES (1, 2);
INSERT INTO `sys_user_role_link` VALUES (2, 2);
INSERT INTO `sys_user_role_link` VALUES (16, 3);
INSERT INTO `sys_user_role_link` VALUES (17, 3);

SET FOREIGN_KEY_CHECKS = 1;
