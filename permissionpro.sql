/*
 Navicat Premium Data Transfer

 Source Server         : MyLocal
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : permissionpro

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 12/03/2020 18:09:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, '技术部');
INSERT INTO `department` VALUES (2, '宣传部');
INSERT INTO `department` VALUES (3, '人力资源部');
INSERT INTO `department` VALUES (4, '法务部');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '采用md5加密, 盐为用户名, 散列次数为2次',
  `inputtime` datetime(0) NULL DEFAULT NULL,
  `tel` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` tinyint(1) NULL DEFAULT NULL,
  `admin` tinyint(1) NULL DEFAULT NULL,
  `dep_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `emp_dep_key`(`dep_id`) USING BTREE,
  CONSTRAINT `emp_dep_key` FOREIGN KEY (`dep_id`) REFERENCES `department` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4461 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '乔纳森乔斯达', 'e3276d3c73ff24b5c00fcbef3cc4a9e5', '2020-01-27 18:34:19', '13912004861', 'jojo1@gmail.com', 0, 0, 1);
INSERT INTO `employee` VALUES (2, '乔瑟夫乔斯达', '39bb7aaa680c5f2e6f31645f90be09dc', '2020-03-01 00:00:00', '13912004862', 'jojo2@gmail.com', 1, 0, 4);
INSERT INTO `employee` VALUES (3, '空条承太郎', '02803bfec4ba6916bcd05a95956fd0a1', '2019-12-10 00:00:00', '13912004863', 'jojo3@gmail.com', 1, 1, 1);
INSERT INTO `employee` VALUES (4, '东方仗助', '1c578c3af9ee3e7ff8e38188ff57a806', '2020-03-06 18:35:51', '13912004864', 'jojo4@gmail.com', 1, 0, 4);
INSERT INTO `employee` VALUES (5, '乔鲁诺乔巴纳', '8d118935be5f1655e21123fbf0383472', '2020-02-25 18:33:39', '13912004865', 'jojo5@gmail.com', 1, 0, 2);
INSERT INTO `employee` VALUES (6, '迪奥', '4eaa842fbe39d45bce23a51dffbac8b5', '2020-03-05 00:00:00', '13912004800', 'dio@gmail.com', 0, 0, 1);
INSERT INTO `employee` VALUES (7, '花京院典明', 'bab998ddbbcf2f768e37b71c937c93e9', '2020-02-27 23:51:59', '13912004801', 'hjy@gmail.com', 0, 0, 2);
INSERT INTO `employee` VALUES (8, '波鲁纳雷夫', '7621f377efd1d38cef79df47df52f9ba', '2020-02-26 23:52:44', '13912004802', 'blnlf@gmail.com', 1, 0, 4);
INSERT INTO `employee` VALUES (9, '岸边露伴', '5d37e194248a393bf40616405283f225', '2020-01-27 23:53:26', '13912004803', 'ablb@gmail.com', 1, 0, 3);
INSERT INTO `employee` VALUES (10, '吉良吉影', '4cb557aeed962dbdcda28e594b834819', '2020-02-26 23:54:11', '13912004804', 'jljy@gmail.com', 0, 0, 1);
INSERT INTO `employee` VALUES (11, '布加拉提', '942eb4acbc559fcef3dbe1850e771038', '2020-02-28 00:00:00', '13912004805', 'bjlt@gmail.com', 0, 0, 2);
INSERT INTO `employee` VALUES (12, '阿帕基', 'ea1c698d89af4caf75715acdd6712c33', '2020-02-26 23:55:15', '13912004806', 'apj@gmail.com', 0, 0, 2);
INSERT INTO `employee` VALUES (13, '米斯达', '6455d656281dbedb27fd746286582b5a', '2020-03-03 23:55:50', '13912004807', 'msd@gmail.com', 1, 0, 3);
INSERT INTO `employee` VALUES (14, '毛毛的大脚1', 'cd41cf32d76b59abec4951e053661a32', '2020-02-25 19:58:44', '12345678901', 'zzq1@gmail.com', 1, 0, 1);
INSERT INTO `employee` VALUES (15, '毛毛的大脚2', '955f8084563e0a0355e1e766cd94a3c3', '2020-02-26 20:22:22', '12345678902', 'zzq2@gmail.com', 1, 0, 1);

-- ----------------------------
-- Table structure for employee_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `employee_role_rel`;
CREATE TABLE `employee_role_rel`  (
  `eid` bigint(20) NOT NULL,
  `rid` bigint(20) NOT NULL,
  PRIMARY KEY (`eid`, `rid`) USING BTREE,
  INDEX `employee_role_rel_role`(`rid`) USING BTREE,
  CONSTRAINT `employee_role_rel_employee` FOREIGN KEY (`eid`) REFERENCES `employee` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `employee_role_rel_role` FOREIGN KEY (`rid`) REFERENCES `role` (`rid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee_role_rel
-- ----------------------------
INSERT INTO `employee_role_rel` VALUES (3, 1);
INSERT INTO `employee_role_rel` VALUES (2, 2);
INSERT INTO `employee_role_rel` VALUES (6, 2);
INSERT INTO `employee_role_rel` VALUES (1, 3);
INSERT INTO `employee_role_rel` VALUES (4, 3);
INSERT INTO `employee_role_rel` VALUES (5, 3);
INSERT INTO `employee_role_rel` VALUES (7, 3);
INSERT INTO `employee_role_rel` VALUES (8, 3);
INSERT INTO `employee_role_rel` VALUES (9, 3);
INSERT INTO `employee_role_rel` VALUES (10, 3);
INSERT INTO `employee_role_rel` VALUES (11, 3);
INSERT INTO `employee_role_rel` VALUES (12, 3);
INSERT INTO `employee_role_rel` VALUES (13, 3);
INSERT INTO `employee_role_rel` VALUES (14, 3);
INSERT INTO `employee_role_rel` VALUES (14, 4);
INSERT INTO `employee_role_rel` VALUES (15, 4);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `text` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_id` bigint(20) NULL DEFAULT NULL,
  `permission_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `menu_menu`(`parent_id`) USING BTREE,
  INDEX `menu_permission`(`permission_id`) USING BTREE,
  CONSTRAINT `menu_menu` FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `menu_permission` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`pid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '系统管理', NULL, NULL, NULL);
INSERT INTO `menu` VALUES (2, '员工管理', '/employee', 1, 1);
INSERT INTO `menu` VALUES (3, '角色权限管理', '/role', 1, 5);
INSERT INTO `menu` VALUES (4, '菜单管理', '/menu', 1, 9);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `pid` bigint(20) NOT NULL AUTO_INCREMENT,
  `pname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `presource` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '员工主页', 'employee:index');
INSERT INTO `permission` VALUES (2, '员工添加', 'employee:add');
INSERT INTO `permission` VALUES (3, '员工编辑', 'employee:edit');
INSERT INTO `permission` VALUES (4, '员工删除', 'employee:delete');
INSERT INTO `permission` VALUES (5, '角色主页', 'role:index');
INSERT INTO `permission` VALUES (6, '角色添加', 'role:add');
INSERT INTO `permission` VALUES (7, '角色编辑', 'role:edit');
INSERT INTO `permission` VALUES (8, '角色删除', 'role:delete');
INSERT INTO `permission` VALUES (9, '菜单主页', 'menu:index');
INSERT INTO `permission` VALUES (10, '菜单添加', 'menu:add');
INSERT INTO `permission` VALUES (11, '菜单编辑', 'menu:edit');
INSERT INTO `permission` VALUES (12, '菜单删除', 'menu:delete');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `rid` bigint(20) NOT NULL AUTO_INCREMENT,
  `rnum` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`rid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin', '管理员');
INSERT INTO `role` VALUES (2, 'hr', '人事');
INSERT INTO `role` VALUES (3, 'employee', '普通员工');
INSERT INTO `role` VALUES (4, 'rubbish', '废物');

-- ----------------------------
-- Table structure for role_permission_rel
-- ----------------------------
DROP TABLE IF EXISTS `role_permission_rel`;
CREATE TABLE `role_permission_rel`  (
  `rid` bigint(20) NOT NULL,
  `pid` bigint(20) NOT NULL,
  PRIMARY KEY (`rid`, `pid`) USING BTREE,
  INDEX `role_permission_rel_permission`(`pid`) USING BTREE,
  CONSTRAINT `role_permission_rel_permission` FOREIGN KEY (`pid`) REFERENCES `permission` (`pid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_permission_rel_role` FOREIGN KEY (`rid`) REFERENCES `role` (`rid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission_rel
-- ----------------------------
INSERT INTO `role_permission_rel` VALUES (1, 1);
INSERT INTO `role_permission_rel` VALUES (2, 1);
INSERT INTO `role_permission_rel` VALUES (3, 1);
INSERT INTO `role_permission_rel` VALUES (1, 2);
INSERT INTO `role_permission_rel` VALUES (1, 3);
INSERT INTO `role_permission_rel` VALUES (2, 3);
INSERT INTO `role_permission_rel` VALUES (1, 4);
INSERT INTO `role_permission_rel` VALUES (1, 5);
INSERT INTO `role_permission_rel` VALUES (2, 5);
INSERT INTO `role_permission_rel` VALUES (1, 6);
INSERT INTO `role_permission_rel` VALUES (1, 7);
INSERT INTO `role_permission_rel` VALUES (1, 8);
INSERT INTO `role_permission_rel` VALUES (1, 9);
INSERT INTO `role_permission_rel` VALUES (1, 10);
INSERT INTO `role_permission_rel` VALUES (1, 11);
INSERT INTO `role_permission_rel` VALUES (1, 12);

SET FOREIGN_KEY_CHECKS = 1;
