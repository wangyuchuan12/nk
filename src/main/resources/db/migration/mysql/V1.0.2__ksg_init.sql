--用户信息
CREATE TABLE uc_user (
  id                 VARCHAR(32)  NOT NULL,
  username           VARCHAR(32)  NOT NULL,
  password           VARCHAR(128) NULL,
  mobile             VARCHAR(32)  NULL,
  email              VARCHAR(128) NULL,
  name               VARCHAR(32)  NULL,
  area               VARCHAR(16)  NULL,
  full_area          VARCHAR(128) NULL,
  gender             VARCHAR(8)   NULL,
  avatar             VARCHAR(256) NULL,
  description        VARCHAR(256) NULL,
  extattr            LONGTEXT     NULL,
  status             VARCHAR(16)  NULL,
  deleted            BOOLEAN      NOT NULL DEFAULT FALSE,
  creator            INT(11)      NULL,
  modifier           INT(11)      NULL,
  created_date       BIGINT(13)   NULL,
  last_modified_date BIGINT(13)   NULL,
  PRIMARY KEY (id),
  KEY (username),
  KEY (mobile),
  KEY (email)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

/**
角色信息..
 */
CREATE TABLE `uc_role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '角色名称',
  `code` varchar(32) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `alias` varchar(64) NOT NULL DEFAULT '' COMMENT '角色别名',
  `state` bigint(20) NOT NULL DEFAULT '0' COMMENT '记录状态 State=1表示纪录有效；State=9，表示纪录无效（已被删除纪录）。纪录撤消或变更后，将原纪录的状态值置为==1。状态的缺省值(默认值)为0',
  `type` smallint(6) DEFAULT NULL COMMENT '角色类型 1=内部角色，2=外部角色，3=人员小组',
  `states` varchar(64) NOT NULL DEFAULT '' COMMENT '记录的状态名',
  `types` varchar(64) NOT NULL DEFAULT '' COMMENT '角色类型名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10008 DEFAULT CHARSET=utf8;

INSERT INTO `uc_role` VALUES (1, '超级管理员','10001', '这是测试','administrator',1,1,"记录有效","内部角色");

CREATE TABLE uc_group (
  id          INT(11)      NOT NULL AUTO_INCREMENT,
  pid         INT(11)      NULL,
  name        VARCHAR(32)  NOT NULL,
  code        VARCHAR(32)  NULL,
  description VARCHAR(256) NULL,
  lft         INT(11)      NULL,
  rgt         INT(11)      NULL,
  sort        INT(8)       NULL,
  extattr     LONGTEXT     NULL,
  PRIMARY KEY (id),
  KEY (name),
  UNIQUE KEY (code)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10001
  DEFAULT CHARSET = UTF8;

--菜单信息表
CREATE TABLE tbase_menuinfo (
  menuinfo_id          INT(11)      NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  menuinfo_upmenuid         INT(11)      NULL COMMENT '上级菜单ID',
  menuinfo_showname        VARCHAR(32)   NULL COMMENT '菜单显示名称，菜单的页面显示名称。对于固定菜单，系统缺省默认MenuInfo_ShowName=MenuInfo_Name，用户可修改',
  code        VARCHAR(32)  NULL COMMENT '编码',
  menuinfo_url         VARCHAR(512) NULL COMMENT '菜单链接地址，对于固定类型的菜单，菜单的链接地址是按规则生成的。对于用户自定义的菜单，链接地址由用户指定。链接地址都可修改。此地址是指菜单缺省页面的链接地址。',
  menuinfo_icon        VARCHAR(64)  NULL COMMENT '图标',
  resource    TEXT         NULL,
  menuinfo_memo VARCHAR(256) NULL COMMENT '备注',
  hidden      BOOLEAN      NULL,
  lft         INT(11)      NULL,
  rgt         INT(11)      NULL,
  sort        INT(8)       NULL,
  target      TEXT         NULL,
  extattr     LONGTEXT     NULL,
  `menuinfo_tabid` bigint(20) DEFAULT NULL COMMENT '表单ID，由表单生成菜单时回填',
  `menuinfo_layrec` varchar(512) DEFAULT NULL COMMENT '菜单层次记录',
  `menuinfo_layno` int(11) DEFAULT NULL COMMENT '菜单层次级别，菜单的缺省级别为一级（最高级）',
  `menuinfo_type` smallint(6) DEFAULT NULL COMMENT '菜单类型，1=系统菜单；2=用户自定义菜单；3=表单菜单',
  `menuinfo_name` varchar(64) DEFAULT NULL COMMENT '菜单内部名称',
  `menuinfo_intro` varchar(512) DEFAULT NULL COMMENT '菜单说明，菜单的内容及使用说明',
  `menuinfo_opentype` smallint(6) DEFAULT NULL COMMENT '菜单的打开方式，1=可用，9=禁用，系统默认值=1。记录修改或变更后，将原记录状态变为0。',
  `menuinfo_status` smallint(6) DEFAULT NULL COMMENT '菜单状态，1=可用，9=禁用，系统默认值=1。记录修改或变更后，将原记录状态变为0。',
  `menuinfo_attribute` smallint(6) DEFAULT NULL COMMENT '菜单属性，在树形的菜单结构中，用该字段来区别是文件夹（TabType_Attribute=1）还是文件（TabType_Attribute=0）',
  `menuinfo_varparama` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `menuinfo_varparamb` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `menuinfo_varparamc` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `menuinfo_varparamd` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `menuinfo_varparame` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `menuinfo_intparama` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `menuinfo_intparamb` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `menuinfo_intparamc` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `menuinfo_intparamd` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `menuinfo_intparame` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `menuinfo_floatparama` float DEFAULT NULL COMMENT '浮点备用字段',
  `menuinfo_floatparamb` float DEFAULT NULL COMMENT '浮点备用字段',
  `menuinfo_floatparamc` float DEFAULT NULL COMMENT '浮点备用字段',
  `menuinfo_floatparamd` float DEFAULT NULL COMMENT '浮点备用字段',
  `menuinfo_floatparame` float DEFAULT NULL COMMENT '浮点备用字段',
  PRIMARY KEY (menuinfo_id),
  KEY (menuinfo_showname),
  UNIQUE KEY (code)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10001
  DEFAULT CHARSET = UTF8;
-- 菜单操作
CREATE TABLE tbase_menuoperate (
  id          INT(11)      NOT NULL AUTO_INCREMENT,
  menuoperate_menuid     INT(11)      NULL  COMMENT '菜单ID，与“菜单信息表”TBase_MenuInfo表中的MenuInfo_ID字段相对应',
  menuoperate_operatename        VARCHAR(32)  NOT NULL  COMMENT '操作显示名称',
  code        VARCHAR(32)  NULL,
  menuoperate_link         VARCHAR(512) NULL COMMENT '菜单链接地址，从对应的操作的Url地址字段获取。',
  menuoperate_icon        VARCHAR(64)  NULL COMMENT '按钮图标',
  resource    TEXT         NULL,
  menuoperate_memo VARCHAR(256) NULL COMMENT '备注',
  hidden      BOOLEAN      NULL,
  target      LONGTEXT     NULL,
  extattr     LONGTEXT     NULL,
  `menuoperate_tabid` bigint(20) DEFAULT NULL COMMENT '表单ID，由表单操作生成菜单操作时回填',
  `menuoperate_dealid` bigint(20) DEFAULT NULL COMMENT '菜单操作ID，由表单操作生成菜单操作时回填',
  `menuoperate_operate` varchar(64) DEFAULT NULL COMMENT '菜单操作内部名称',
  `menuoperate_state` smallint(6) DEFAULT NULL COMMENT '纪录状态，1=有效；9=失效',
  `menuoperate_type` smallint(6) DEFAULT NULL COMMENT '类型，1=模块操作；2=列表操作；0=其他',
  `menuoperate_varparama` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `menuoperate_varparamb` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `menuoperate_varparamc` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `menuoperate_varparamd` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `menuoperate_varparame` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `menuoperate_intparama` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `menuoperate_intparamb` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `menuoperate_intparamc` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `menuoperate_intparamd` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `menuoperate_intparame` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `menuoperate_floatparama` float DEFAULT NULL COMMENT '浮点备用字段',
  `menuoperate_floatparamb` float DEFAULT NULL COMMENT '浮点备用字段',
  `menuoperate_floatparamc` float DEFAULT NULL COMMENT '浮点备用字段',
  `menuoperate_floatparamd` float DEFAULT NULL COMMENT '浮点备用字段',
  `menuoperate_floatparame` float DEFAULT NULL COMMENT '浮点备用字段',

  PRIMARY KEY (id),
  KEY (menuoperate_operatename),
  UNIQUE KEY (code)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10001
  DEFAULT CHARSET = UTF8;

  CREATE TABLE `tapp_tabdealinfo` (
  `appdeal_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appdeal_tabid` bigint(20) NOT NULL,
  `appdeal_typeid` bigint(20) NOT NULL,
  `appdeal_uptabid` bigint(20) NOT NULL,
  `appdeal_dealerid` bigint(20) NOT NULL,
  `appdeal_dealername` bigint(20) NOT NULL,
  `appdeal_dealtypeid` bigint(20) NOT NULL,
  `appdeal_dealname` varchar(64) DEFAULT NULL,
  `appdeal_data` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `appdeal_recorderid` bigint(20) DEFAULT NULL,
  `appdeal_content` varchar(4000) DEFAULT NULL,
  `appdeal_historytabid` bigint(20) DEFAULT NULL,
  `appdeal_tableno` varchar(200) DEFAULT NULL,
  `appdeal_state` bigint(20) NOT NULL,
  `appdeal_result` bigint(20) NOT NULL,
  `appdeal_resulttext` varchar(20) NOT NULL,
  `appdeal_audiourl` varchar(200) DEFAULT NULL,
  `appdeal_memo` varchar(200) DEFAULT NULL,
  `appdeal_varparama` varchar(1024) DEFAULT NULL,
  `appdeal_varparamb` varchar(1024) DEFAULT NULL,
  `appdeal_varparamc` varchar(1024) DEFAULT NULL,
  `appdeal_varparamd` varchar(1024) DEFAULT NULL,
  `appdeal_varparame` varchar(1024) DEFAULT NULL,
  `appdeal_intparama` bigint(20) DEFAULT NULL,
  `appdeal_intparamb` bigint(20) DEFAULT NULL,
  `appdeal_intparamc` bigint(20) DEFAULT NULL,
  `appdeal_intparamd` bigint(20) DEFAULT NULL,
  `appdeal_intparame` bigint(20) DEFAULT NULL,
  `appdeal_floatparama` float DEFAULT NULL,
  `appdeal_floatparamb` float DEFAULT NULL,
  `appdeal_floatparamc` float DEFAULT NULL,
  `appdeal_floatparamd` float DEFAULT NULL,
  `appdeal_floatparame` float DEFAULT NULL,
  PRIMARY KEY (`appdeal_id`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8;



-- 用户 角色中间表
-- CREATE TABLE uc_role_user (
--   role_id INT(11)     NOT NULL,
--   user_id VARCHAR(32) NOT NULL,
--   PRIMARY KEY (role_id, user_id)
-- )
--   ENGINE = InnoDB
--   DEFAULT CHARSET = UTF8;
-- 分组角色表
CREATE TABLE uc_role_group (
  role_id  INT(11) NOT NULL,
  group_id INT(11) NOT NULL,
  PRIMARY KEY (role_id, group_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;
-- 分组用户表
CREATE TABLE uc_group_user (
  group_id INT(11)     NOT NULL,
  user_id  VARCHAR(32) NOT NULL,
  PRIMARY KEY (group_id, user_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

-- CREATE TABLE uc_role_menu (
--   role_id INT(11) NOT NULL,
--   menu_id INT(11) NOT NULL,
--   PRIMARY KEY (role_id, menu_id)
-- )
--   ENGINE = InnoDB
--   DEFAULT CHARSET = UTF8;

CREATE TABLE uc_role_operate (
  role_id    INT(11) NOT NULL,
  operate_id INT(11) NOT NULL,
  PRIMARY KEY (role_id, operate_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;
--菜单信息表
CREATE TABLE   uc_menu (
  id   INT(11)        NOT NULL AUTO_INCREMENT,
  pid           INT(11)        NULL,
  name          VARCHAR(32)    NOT NULL,
  code          VARCHAR(32)    NULL,
  url           VARCHAR(512)   NULL,
  icon          VARCHAR(64)    NULL,
  resource      TEXT           NULL,
  description   VARCHAR(256)   NULL,
  hidden        BOOLEAN        NULL,
  lft           INT(11)        NULL,
  rgt           INT(11)        NULL,
  sort          INT(8)         NULL,
  target        TEXT           NULL,
  extattr       LONGTEXT       NULL,
  PRIMARY KEY (id),
  KEY (name)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10001
  DEFAULT CHARSET = UTF8;


  CREATE TABLE uc_operate (
  id          INT(11)      NOT NULL AUTO_INCREMENT,
  menu_id     INT(11)      NULL,
  name        VARCHAR(32)  NOT NULL,
  code        VARCHAR(32)  NULL,
  url         VARCHAR(512) NULL,
  icon        VARCHAR(64)  NULL,
  resource    TEXT         NULL,
  description VARCHAR(256) NULL,
  hidden      BOOLEAN      NULL,
  target      LONGTEXT     NULL,
  extattr     LONGTEXT     NULL,
  PRIMARY KEY (id),
  KEY (name),
  UNIQUE KEY (code)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 10001
  DEFAULT CHARSET = UTF8;

-- Tourage@123
INSERT INTO `uc_user` (id, username, password, name, gender, status)
VALUES ('10001', 'admin', '$2a$10$TffNg0qdTpTEbWVnql.aM.6C3CD7J1tcHh6NAGTNgYID7F.MbUmSa',
        '超级管理员', 'unknown', 'normal');

-- INSERT INTO `uc_role_user` VALUES ('10001', '10001');

-- INSERT INTO uc_menu (id, pid, name, code, url, icon, resource, description, hidden, lft, rgt, sort, target, extattr) VALUES (10001, 0, '运行大盘', 'dashboard', null, 'dashboard', null, null, null, 1, 4, null, null, null);
-- INSERT INTO uc_menu (id, pid, name, code, url, icon, resource, description, hidden, lft, rgt, sort, target, extattr) VALUES (10002, 10001, '我的工作台', 'dashboard_workplace', '/dashboard/workplace', null, null, null, null, 2, 3, null, null, null);
--
-- INSERT INTO uc_menu (id, pid, name, code, url, icon, resource, description, hidden, lft, rgt, sort, target, extattr) VALUES (10003, 0, '用户中心', 'usercenter', null, 'sys', null, null, null, 5, 18, null, null, null);
-- INSERT INTO uc_menu (id, pid, name, code, url, icon, resource, description, hidden, lft, rgt, sort, target, extattr) VALUES (10004, 10003, '用户管理', 'usercenter_user', '/usercenter/user', 'usermanagement', null, null, null, 6, 7, null, null, null);
-- INSERT INTO uc_menu (id, pid, name, code, url, icon, resource, description, hidden, lft, rgt, sort, target, extattr) VALUES (10005, 10003, '角色管理', 'usercenter_role', '/usercenter/role', 'role', null, null, null, 8, 9, null, null, null);
-- INSERT INTO uc_menu (id, pid, name, code, url, icon, resource, description, hidden, lft, rgt, sort, target, extattr) VALUES (10006, 10003, '用户组管理', 'usercenter_group', '/usercenter/group', 'usergroup', null, null, null, 10, 11, null, null, null);
-- INSERT INTO uc_menu (id, pid, name, code, url, icon, resource, description, hidden, lft, rgt, sort, target, extattr) VALUES (10007, 10003, '菜单管理', 'usercenter_menu', '/usercenter/menu', 'menu', null, null, null, 12, 13, null, null, null);
-- INSERT INTO uc_menu (id, pid, name, code, url, icon, resource, description, hidden, lft, rgt, sort, target, extattr) VALUES (10008, 10003, '操作管理', 'usercenter_operate', '/usercenter/operate', 'operate', null, null, null, 14, 15, null, null, null);
-- --系统参数
-- INSERT INTO uc_menu (id, pid, name, code, url, icon, resource, description, hidden, lft, rgt, sort, target, extattr) VALUES (10009, 10003, '系统参数管理', 'usercenter_system', '/usercenter/system', 'usergroup', null, null, null, 16, 17, null, null, null);
-- INSERT INTO uc_menu (id, pid, name, code, url, icon, resource, description, hidden, lft, rgt, sort, target, extattr) VALUES (10010, 0, '运单中心', 'express', null, 'eco', null, null, null, 19, 26, null, null, null);
-- INSERT INTO uc_menu (id, pid, name, code, url, icon, resource, description, hidden, lft, rgt, sort, target, extattr) VALUES (10011, 10010, '客户管理', 'express_customer', '/express/customer', null, null, null, null, 20, 21, null, null, null);
-- INSERT INTO uc_menu (id, pid, name, code, url, icon, resource, description, hidden, lft, rgt, sort, target, extattr) VALUES (10012, 10010, '运单管理', 'express_waybill', '/express/waybill', null, null, null, null, 22, 23, null, null, null);
-- #INSERT INTO uc_menu (id, pid, name, code, url, icon, resource, description, hidden, lft, rgt, sort, target, extattr) VALUES (10013, 10010, '人员基本信息', 'express_info', '/express/staff ','usermanagement', null, null, null, 24, 25, null, null, null);
-- INSERT INTO uc_menu (id, pid, name, code, url, icon, resource, description, hidden, lft, rgt, sort, target, extattr) VALUES (10014, 0, '客户关系', 'crm', null, 'menu', null, null, null, 27, 28, null, null, null);

-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10001, 0, '运行大盘', 'dashboard', null, 'dashboard', null, null, null, 1, 4, null, null, null);
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10002, 10001, '我的工作台', 'dashboard_workplace', '/dashboard/workplace', null, null, null, null, 2, 3, null, null, null);
--
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10003, 0, '用户中心', 'usercenter', null, 'sys', null, null, null, 5, 18, null, null, null);
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10004, 10003, '用户管理', 'usercenter_user', '/usercenter/user', 'usermanagement', null, null, null, 6, 7, null, null, null);
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10005, 10003, '角色管理', 'usercenter_role', '/usercenter/role', 'role', null, null, null, 8, 9, null, null, null);
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10006, 10003, '用户组管理', 'usercenter_group', '/usercenter/group', 'usergroup', null, null, null, 10, 11, null, null, null);
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10007, 10003, '菜单管理', 'usercenter_menu', '/usercenter/menu', 'menu', null, null, null, 12, 13, null, null, null);
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10008, 10003, '操作管理', 'usercenter_operate', '/usercenter/operate', 'operate', null, null, null, 14, 15, null, null, null);
-- --系统参数
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10009, 10003, '系统参数管理', 'usercenter_system', '/usercenter/system', 'usergroup', null, null, null, 16, 17, null, null, null);
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10010, 0, '运单中心', 'express', null, 'eco', null, null, null, 19, 27, null, null, null);
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10011, 10010, '客户管理', 'express_customer', '/express/customer', null, null, null, null, 20, 21, null, null, null);
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10012, 10010, '运单管理', 'express_waybill', '/express/waybill', null, null, null, null, 22, 23, null, null, null);
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10013, 10010, '人员基本信息', 'express_info', '/express/staff', null, null, null, null, 24, 25, null, null, null);
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10016, 10010, '代办列表页', 'usercenter_joblist', '/usercenter/jobList', null, null, null, null, 32, 33, null, null, null);
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10017, 10010, '问题件查询', 'usercenter_appproblemparts', '/usercenter/appproblemparts', null, null, null, null, 34, 35, null, null, null);
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10014, 0, '客户关系', 'crm', null, 'menu', null, null, null, 26, 28, null, null, null);
-- INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10018, 10010, '问题件上报入口', 'problem_parts', '/problem/parts','operate', null, null, null, 36, 37, null, null, null);
--


-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10001);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10002);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10003);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10004);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10005);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10006);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10007);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10008);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10009);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10010);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10011);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10012);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10013);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10014);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10015);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10016);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10017);
-- INSERT INTO uc_role_menu (role_id, menu_id) VALUES (10001, 10018);
-- 刘磊--人员基本信息表
CREATE TABLE `tbase_staffinfo` (
  `staff_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '人员ID',
  `staff_code` varchar(64) DEFAULT NULL COMMENT '人员编码',
  `staff_name` varchar(32) DEFAULT NULL COMMENT '人员姓名',
  `staff_idcard` varchar(64) DEFAULT NULL COMMENT '身份证号码',
  `staff_email` varchar(64) DEFAULT NULL COMMENT 'e-mail',
  `staff_mobil` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `staff_usertype` smallint(6) DEFAULT NULL COMMENT '用户类型,系统默认有四种用户0=系统管理员；1=客服人员；2=机器人；3=外部用户；10000=测试用户；',
  `staff_username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `staff_password` varchar(1024) DEFAULT NULL COMMENT '用户密码',
  `staff_companyid` bigint(20) DEFAULT NULL COMMENT '网点ID,关联网点表中的主键',
  `staff_date` date DEFAULT NULL COMMENT '创建日期',
  `staff_jobstate` smallint(6) DEFAULT NULL COMMENT '人员状态,1=在职，2=休息',
  `staff_state` smallint(6) DEFAULT NULL COMMENT '记录状态,1=有效，9=失效。系统默认值=1',
  `staff_memo` varchar(512) DEFAULT NULL COMMENT '备注',
  `staff_varparama` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `staff_varparamb` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `staff_varparamc` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `staff_varparamd` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `staff_varparame` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `staff_intparama` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `staff_intparamb` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `staff_intparamc` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `staff_intparamd` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `staff_intparame` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `staff_floatparama` float DEFAULT NULL COMMENT '浮点备用字段',
  `staff_floatparamb` float DEFAULT NULL COMMENT '浮点备用字段',
  `staff_floatparamc` float DEFAULT NULL COMMENT '浮点备用字段',
  `staff_floatparamd` float DEFAULT NULL COMMENT '浮点备用字段',
  `staff_floatparame` float DEFAULT NULL COMMENT '浮点备用字段',
  PRIMARY KEY (`Staff_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert  into `tbase_staffinfo`(`staff_id`,`staff_code`,`staff_name`,`staff_idcard`,`staff_email`,`staff_mobil`,`staff_usertype`,`staff_username`,`staff_password`,`staff_companyid`,`staff_date`,`staff_jobstate`,`staff_state`,`staff_memo`,`staff_varparama`,`staff_varparamb`,`staff_varparamc`,`staff_varparamd`,`staff_varparame`,`staff_intparama`,`staff_intparamb`,`staff_intparamc`,`staff_intparamd`,`staff_intparame`,`staff_floatparama`,`staff_floatparamb`,`staff_floatparamc`,`staff_floatparamd`,`staff_floatparame`) values
(10001,'asdfasdfasfd','liulei','342222198604082031','690283915@qq.com','18167159492',0,'admin','$2a$10$gZg5sFozSCPpCpv28tt/u.38KsIzU046gD8UMmozMZo5qpMfcHYSO',NULL,'2018-03-20',2,1,'aaaaaaaaa',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);



-- 刘磊数据关联关系表
CREATE TABLE `tbase_sysrltn` (
  `sysrltn_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `sysrltn_type` int(11) DEFAULT NULL COMMENT '关联类型,1=角色与人员关联表',
  `sysrltn_mainid` bigint(20) DEFAULT NULL COMMENT '主数据',
  `sysrltn_assistid` bigint(20) DEFAULT NULL COMMENT '关联数据',
  `sysrltn_state` smallint(6) DEFAULT '1' COMMENT '状态,默认=1，有效。0=失效',
  `sysrltn_memo` varchar(512) DEFAULT NULL COMMENT '备注',
  `sysrltn_varparama` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `sysrltn_varparamb` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `sysrltn_varparamc` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `sysrltn_varparamd` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `sysrltn_varparame` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `sysrltn_intparama` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `sysrltn_intparamb` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `sysrltn_intparamc` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `sysrltn_intparamd` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `sysrltn_intparame` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `sysrltn_floatparama` float DEFAULT NULL COMMENT '浮点备用字段',
  `sysrltn_floatparamb` float DEFAULT NULL COMMENT '浮点备用字段',
  `sysrltn_floatparamc` float DEFAULT NULL COMMENT '浮点备用字段',
  `sysrltn_floatparamd` float DEFAULT NULL COMMENT '浮点备用字段',
  `sysrltn_floatparame` float DEFAULT NULL COMMENT '浮点备用字段',
  PRIMARY KEY (`Sysrltn_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- CREATE TABLE  `tbase_sysrltn` (
-- `sysrltn_id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
-- `sysrltn_type`  int(11) NULL COMMENT '关联类型,1=角色与人员关联表',
-- `sysrltn_mainid`  bigint(20) NULL COMMENT '主数据',
-- `sysrltn_assistid`  bigint(20) NULL COMMENT '关联数据',
-- `sysrltn_state`  smallint(6) DEFAULT '1' COMMENT '状态,默认=1，有效。0=失效',
-- `sysrltn_memo`  varchar(512) NULL COMMENT '备注',
-- `sysrltn_varparama`  varchar(1024) NULL COMMENT '字符备用字段',
-- `sysrltn_varparamb`  varchar(1024) NULL COMMENT '字符备用字段',
-- `sysrltn_varparamc`  varchar(1024) NULL COMMENT '字符备用字段',
-- `sysrltn_varparamd`  varchar(1024) NULL COMMENT '字符备用字段',
-- `sysrltn_varparame`  varchar(1024) NULL COMMENT '字符备用字段',
-- `sysrltn_intparama`  bigint(20) NULL COMMENT '数字备用字段',
-- `sysrltn_intparamb`  bigint(20) NULL COMMENT '数字备用字段',
-- `sysrltn_intparamc`  bigint(20) NULL COMMENT '数字备用字段',
-- `sysrltn_intparamd`  bigint(20) NULL COMMENT '数字备用字段',
-- `sysrltn_intparame`  bigint(20) NULL COMMENT '数字备用字段',
-- `sysrltn_floatparama`  float NULL COMMENT '浮点备用字段',
-- `sysrltn_floatparamb`  float NULL COMMENT '浮点备用字段',
-- `sysrltn_floatparamc`  float NULL COMMENT '浮点备用字段',
-- `sysrltn_floatparamd`  float NULL COMMENT '浮点备用字段',
-- `sysrltn_floatparame`  float NULL COMMENT '浮点备用字段',
--   PRIMARY KEY (`sysrltn_id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;



insert  into `tbase_sysrltn`(`SysRltn_ID`,`SysRltn_Type`,`SysRltn_MainID`,`SysRltn_AssistID`,`SysRltn_State`,`SysRltn_Memo`,`SysRltn_VarParamA`,`SysRltn_VarParamB`,`SysRltn_VarParamC`,`SysRltn_VarParamD`,`SysRltn_VarParamE`,`SysRltn_IntParamA`,`SysRltn_IntParamB`,`SysRltn_IntParamC`,`SysRltn_IntParamD`,`SysRltn_IntParamE`,`SysRltn_FloatParamA`,`SysRltn_FloatParamB`,`SysRltn_FloatParamC`,`SysRltn_FloatParamD`,`SysRltn_FloatParamE`) values
(1,1,1,10001,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(2,2,1,10001,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(3,2,1,10002,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(4,2,1,10003,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(5,2,1,10004,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(6,2,1,10005,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(7,2,1,10006,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(8,2,1,10007,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(9,2,1,10008,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(10,2,1,10009,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(11,2,1,10010,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(12,2,1,10011,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(13,2,1,10012,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(14,2,1,10013,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(15,2,1,10014,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(16,2,1,10015,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(17,2,1,10016,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(18,2,1,10017,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
(19,2,1,10018,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);


INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10001, 0, '运行大盘', 'dashboard', null, 'dashboard', null, null, null, 1, 4, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10002, 10001, '我的工作台', 'dashboard_workplace', '/dashboard/workplace', 'group', null, null, null, 2, 3, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10003, 0, '用户中心', 'usercenter', null, 'sys', null, null, null, 5, 18, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10004, 10003, '人员基本信息', 'express_info', 'usercenter/user', 'group', null, null, null, 24, 25, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10005, 10003, '网点管理', 'usercenter_company', '/usercenter/company', 'group', null, null, null, 8, 9, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10006, 10003, '角色管理', 'usercenter_role', '/usercenter/role', 'role', null, null, null, 8, 9, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10007, 10003, '用户组管理', 'usercenter_group', '/usercenter/group', 'usergroup', null, null, null, 10, 11, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10008, 10003, '菜单管理', 'usercenter_menu', '/usercenter/menu', 'menu', null, null, null, 12, 13, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10009, 10003, '操作管理', 'usercenter_operate', '/usercenter/operate', 'operate', null, null, null, 14, 15, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10010, 10003, '系统参数管理', 'usercenter_system', '/usercenter/system', 'usergroup', null, null, null, 16, 17, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10011, 0, '工单中心', 'express', null, 'eco', null, null, null, 19, 27, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10012, 10011, '客户管理', 'express_customer', '/express/customer', 'operate', null, null, null, 20, 21, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10013, 10011, '运单管理', 'express_waybill', '/express/waybill', 'operate', null, null, null, 22, 23, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10014, 10011, '代办列表页', 'usercenter_joblist', '/usercenter/jobList', 'operate', null, null, null, 32, 33, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10015, 10011, '问题件查询', 'usercenter_appproblemparts', '/usercenter/appproblemparts', 'operate', null, null, null, 34, 35, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10016, 10011, '问题件上报入口', 'problem_parts', '/problem/parts','operate', null, null, null, 36, 37, null, null, null);
--INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10014, 0, '客户关系', 'crm', null, 'menu', null, null, null, 26, 28, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10044, 0, '报表中心', 'ureport', null,'operate', null, null, null, 47, 54, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10045, 10044, '客服工作汇总', 'ureport_reportcustomer', '/ureport/reportcustomer','menu', null, null, null, 48, 49, null, null, null);
INSERT INTO tbase_menuinfo (menuinfo_id, menuinfo_upmenuid, menuinfo_showname, code, menuinfo_url, menuinfo_icon, resource, menuinfo_memo, hidden, lft, rgt, sort, target, extattr) VALUES (10046, 10044, '客服运单号分布', 'ureport_reportwaybill', '/ureport/reportwaybill','operate', null, null, null, 50, 51, null, null, null);


--李邵妙人员基本信息表
-- CREATE TABLE tbase_staffinfo (
--   id   int(10)    NOT NULL AUTO_INCREMENT COMMENT '人员ID',
--   staff_recordstate smallint(6) DEFAULT NULL COMMENT '记录状态,1=有效，9=失效。系统默认值=1',
--   staff_state smallint(6) DEFAULT NULL COMMENT '人员状态,1=在职，2=离职，3=退休，4=病休等，10=试用，11=实习，12=未签约等。系统默认值=未签约。',
--   staff_companyid smallint(6) DEFAULT NULL COMMENT '企业ID,关联企业表中的主键',
--   staff_code varchar(64) DEFAULT NULL COMMENT '人员编码,系统流水号',
--   staff_workcode varchar(32) DEFAULT NULL COMMENT '人员工号,录入',
--   staff_date datetime(6) DEFAULT NULL COMMENT '记录日期',
--   staff_name varchar(32) DEFAULT NULL COMMENT '人员姓名',
--   staff_sex smallint(6) DEFAULT NULL COMMENT '人员性别,0=女，1=男',
--   staff_nation varchar(32) DEFAULT NULL COMMENT '民族',
--   staff_workdate date DEFAULT NULL COMMENT '参加工作时间',
--   staff_indate date DEFAULT NULL COMMENT '进公司时间',
--   staff_outdate date DEFAULT NULL COMMENT '离开公司时间',
--   staff_innerdegree varchar(64) DEFAULT NULL COMMENT '内部职称',
--   staff_outdegree varchar(64) DEFAULT NULL COMMENT '外部职称',
--   staff_type smallint(6) DEFAULT NULL COMMENT '是否为联络人,0=否，1=是',
--   staff_birthday date DEFAULT NULL COMMENT '员工生日',
--   staff_idcard varchar(64) DEFAULT NULL COMMENT '身份证号码',
--   staff_native varchar(32) DEFAULT NULL COMMENT '籍贯',
--   staff_marriage smallint(6) DEFAULT NULL COMMENT '结婚情况,0=已婚，1=未婚',
--   staff_iscreateuser smallint(6) DEFAULT NULL COMMENT '是否生成用户,1=生成，2=未生成',
--   staff_education varchar(16) DEFAULT NULL COMMENT '学历',
--   staff_university varchar(128) DEFAULT NULL COMMENT '毕业学校',
--   staff_graduatetime datetime(6) DEFAULT NULL COMMENT '毕业时间',
--   staff_specialty varchar(128) DEFAULT NULL COMMENT '专业',
--   staff_address varchar(256) DEFAULT NULL COMMENT '家庭地址',
--   staff_tel varchar(32) DEFAULT NULL COMMENT '家庭电话',
--   staff_postcode varchar(32) DEFAULT NULL COMMENT '家庭邮编',
--   staff_localaddress varchar(256) DEFAULT NULL COMMENT '本地地址',
--   staff_localtel varchar(128) DEFAULT NULL COMMENT '本地电话',
--   staff_localpost varchar(16) DEFAULT NULL COMMENT '本地邮编',
--   staff_linkman varchar(32) DEFAULT NULL COMMENT '有关联系人姓名',
--   staff_linktel varchar(32) DEFAULT NULL COMMENT '联系人电话',
--   staff_linktpost varchar(256) DEFAULT NULL COMMENT '联系人地址和邮编',
--   staff_email varchar(64) DEFAULT NULL COMMENT 'e-mail',
--   staff_politicsinfo varchar(50) DEFAULT NULL COMMENT '政治面貌',
--   staff_resume varchar(1024) DEFAULT NULL COMMENT '个人简历',
--   staff_officaltel varchar(32) DEFAULT NULL COMMENT '办公室电话',
--   staff_mobil varchar(32) DEFAULT NULL COMMENT '手机号码',
--   staff_memo varchar(255) DEFAULT NULL COMMENT '备注',
--   PRIMARY KEY (id)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- sunjiajian:新增系统参数表
CREATE TABLE `tbase_userreport` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) DEFAULT NULL  COMMENT '参数标识符 某一类值的唯一标识',
  `state` smallint(6) NOT NULL DEFAULT '0' COMMENT '记录状态 1=有效，0=无效，9=删除',
  `states` varchar(32) DEFAULT NULL  COMMENT 'state字段所对应的中文',
  `type` bigint(20) DEFAULT NULL COMMENT '类型 1=系统参数；2=用户自定义',
  `types` varchar(32) DEFAULT NULL  COMMENT 'type字段所对应的中文',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `value` varchar(128) DEFAULT NULL COMMENT '值',
  `description` varchar(512) DEFAULT NULL COMMENT '说明',
  `staffname`    varchar(50) DEFAULT  NUll COMMENT '绑定的用户',
  `companyid` bigint(20) DEFAULT NULL COMMENT '网点ID 标注属于哪个网点的参数',
  `varparam1` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `varparam2` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `intparam1` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `intparam2` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `floatparam1` float DEFAULT NULL COMMENT '浮点备用字段',
  `floatparam2` float DEFAULT NULL COMMENT '浮点备用字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统参数';


-- sunjiajian:新增接口信息记录表
CREATE TABLE `ex_info_record` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `express_number` varchar(64) NOT NULL DEFAULT '0' COMMENT '运单号',
  `express_type` smallint(6) NOT NULL DEFAULT '0' COMMENT '无物流=10，已签收=20，派件中=30，从离开出港网点到最后一站中心发出前的所有状态=40，从最后一站中心到派送前的所有状态=50',
  `express_updatestate` smallint(6) NOT NULL DEFAULT '0' COMMENT '任意物流节点，是否正常更新10=正常，20=异常',
  `express_lastdate` varchar(60) NOT NULL DEFAULT '0000-00-00' COMMENT '最后一条物流信息的时间，可用于打延误或者验证物流信息是否停滞',
  `interface_state` smallint(6) NOT NULL DEFAULT '0' COMMENT '正常，超时，不完整',
  `area_varchar1` varchar(255) DEFAULT NULL COMMENT '字符备用字段',
  `area_int1` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `area_float1` float DEFAULT NULL COMMENT '浮点备用字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快递信息记录表';

--    sunjiajian:新增接口信息明细表
CREATE TABLE `ex_info_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `express_number` varchar(64) NOT NULL DEFAULT '' COMMENT '运单号',
  `express_detaildate` varchar(64) NOT NULL DEFAULT '0000-00-00' COMMENT '物流记录时间',
  `express_detailtype` smallint(6) NOT NULL DEFAULT '0' COMMENT '物流明细类型 ：类型分为：1=揽收，2=运输中，3=派件中，4=已签收',
  `begin_companyid` varchar(64) NOT NULL DEFAULT '' COMMENT '起始站点ID',
  `begin_companyname` varchar(200) NOT NULL DEFAULT '' COMMENT '起始站点名称',
  `begin_companytype` smallint(6) NOT NULL DEFAULT '0' COMMENT '起始网点类型 类型分为：1为派件网点，2为分拨中心',
  `end_companyid` varchar(64) NOT NULL DEFAULT '' COMMENT '终止站点ID',
  `end_companyname` varchar(200) NOT NULL DEFAULT '' COMMENT '终止站点名称',
  `end_companytype` smallint(6) NOT NULL DEFAULT '0' COMMENT '终止网点类型 类型分为：1为派件网点，2为分拨中心',
  `is_lastdetail` smallint(6) DEFAULT NULL COMMENT '是否最末条记录 0为否 1为是',
  `area_varchar1` varchar(255) DEFAULT NULL COMMENT '字符备用字段',
  `area_int1` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `area_float1` float DEFAULT NULL COMMENT '浮点备用字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='接口信息明细表';


 CREATE TABLE tbase_company (
company_id       BigInt       NOT NULL  COMMENT '网点ID' AUTO_INCREMENT,
company_name               VARCHAR(256) NOT NULL COMMENT '网点全称（工商执照上的名称）',
company_code      BigInt          COMMENT '内部编码 ',
company_type      SmallInt          COMMENT '网点类型 ',
company_extcode      varchar(128)    COMMENT '外部编码',
company_parentid      BigInt          COMMENT '上级网点ID ',
company_layrec      varchar(512)    COMMENT '层次记录',
company_layno      int        NOT NULL      COMMENT     '层次级别 ',
company_province      varchar(64)     COMMENT '省',
company_city      varchar(64)     COMMENT '市',
company_area      varchar(64)     COMMENT '区',
company_address      varchar(256)    COMMENT '网点地址',
company_postzip      varchar(32)     COMMENT '邮政编码',
company_email      varchar(128)    COMMENT '网点电子信箱地址',
company_linker      varchar(32)     NOT NULL    COMMENT    '联系人',
company_linktel      varchar(32)     NOT NULL    COMMENT   '联系电话',
company_tel      varchar(64)     NOT NULL    COMMENT        '网点电话号码',
company_fax      varchar(32)     COMMENT '网点传真',
company_state      SmallInt        COMMENT '记录状态 ',
company_iscontract 	SmallInt COMMENT '是否签约网点',
company_begincontractdate 	Date COMMENT '签约开始时间',
company_endcontractdate Date COMMENT '签约结束时间',
company_memo      varchar(1024)   COMMENT '备注',
company_adddate      Date            COMMENT '建档时间',
company_originalcredit  decimal(20,2)     COMMENT '网点初始信用额',
company_credit  decimal(20,2)  COMMENT '网点当前信用额',
company_price  decimal(20,2)  COMMENT '单价',
company_own  decimal(20,2) COMMENT '应收总金额',
company_paid  decimal(20,2) COMMENT '已收总金额',
company_sum  decimal(20,2)  COMMENT '剩余应收金额',
company_varparama      varchar(1024)   COMMENT '字符备用字段',
company_varparamb      varchar(1024)   COMMENT '字符备用字段',
company_varparamc      varchar(1024)   COMMENT '字符备用字段',
company_varparamd      varchar(1024)   COMMENT '字符备用字段',
company_varparame      varchar(1024)   COMMENT '字符备用字段',
company_intparama      bigint          COMMENT '数字备用字段',
company_intparamb      bigint          COMMENT '数字备用字段',
company_intparamc      bigint          COMMENT '数字备用字段',
company_intparamd      bigint          COMMENT '数字备用字段',
company_intparame      bigint          COMMENT '数字备用字段',
company_floatparama      float           COMMENT '浮点备用字段',
company_floatparamb      float           COMMENT '浮点备用字段',
company_floatparamc      float           COMMENT '浮点备用字段',
company_floatparamd      float           COMMENT '浮点备用字段',
company_floatparame      float           COMMENT '浮点备用字段',

  PRIMARY KEY (company_id)
  )
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8 COMMENT='网点信息表';



-- 李绍妙 问题件上报
CREATE TABLE `tapp_problemparts` (
  `problemparts_id` bigint(12) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `problemparts_tabid` bigint(12) DEFAULT NULL COMMENT '表单id',
  `problemparts_state` smallint(12) DEFAULT NULL COMMENT '表单状态 10=待确认，20=审核不通过，1=流程正常结束，4=作废',
  `problemparts_expressnumber` bigint(32) DEFAULT NULL COMMENT '运单号',
  `problemparts_type` smallint(32) DEFAULT NULL COMMENT '问题类型',
  `problemparts_insideitem` varchar(255) DEFAULT NULL COMMENT '内物',
  `problemparts_receivename` varchar(64) DEFAULT NULL COMMENT '收件人名字',
  `problemparts_receivephone` varchar(64) DEFAULT NULL COMMENT '收件人电话',
  `problemparts_receiveaddress` varchar(64) DEFAULT NULL COMMENT '收件人地址',
  `problemparts_sendname` varchar(255) DEFAULT NULL COMMENT '发件人名字',
  `problemparts_sendphone` varchar(255) DEFAULT NULL COMMENT '发件人电话',
  `problemparts_sendaddress` varchar(255) DEFAULT NULL COMMENT '发件人地址',
  `problemparts_submittername` varchar(255) DEFAULT NULL COMMENT '投诉人姓名',
  `problemparts_submitterphone` varchar(255) DEFAULT NULL COMMENT '投诉人电话',
  `problemparts_newreceivename` varchar(255) DEFAULT NULL COMMENT '新收件人姓名',
  `problemparts_newreceivephone` varchar(255) DEFAULT NULL COMMENT '新收件人电话',
  `problemparts_newreceiveaddress` varchar(255) DEFAULT NULL COMMENT '新收件人地址',
  `problemparts_companyid` varchar(255) DEFAULT NULL COMMENT '派件网点ID',
  `problemparts_insideitempicurl` varchar(255) DEFAULT NULL COMMENT '内物图片路径',
  `problemparts_outsideitempicurl` varchar(255) DEFAULT NULL COMMENT '外包装图片路径',
  `problemparts_returnexpressnumber` bigint(255) DEFAULT NULL COMMENT '退回运单号',
  `problemparts_expresstype` smallint(255) DEFAULT NULL COMMENT '物流状态：无物流=10，已签收=20，派件中=30，从离开出港网点到最后一站中心发出前的所有状态=40，从最后一站中心到派送前的所有状态=50',
  `problemparts_expressupdatestate` smallint(255) DEFAULT NULL COMMENT '物流是否更新:任意物流节点，是否正常更新10=正常，20=异常',
  `problemparts_dutystate` smallint(255) DEFAULT NULL COMMENT '是否免责:10=免责，20=非免责',
  `problemparts_obtainstate` smallint(255) DEFAULT NULL COMMENT '是否收到货:10=收到，20=未收到',
  `problemparts_returnstate` smallint(255) DEFAULT NULL COMMENT '是否退回:10=继续退回，20=取消退回',
  `problemparts_alteraddr` smallint(255) DEFAULT NULL COMMENT '是否改地址:10=继续改地址，20=取消改地址',
  `problemparts_alteraddrstate` smallint(255) DEFAULT NULL COMMENT '地址是否改出:10=地址已改出，20=地址未改出',
  `problemparts_callsendercount` smallint(255) DEFAULT NULL COMMENT '拨打发件人电话次数:默认值为0,逐1累加',
  `problemparts_callrecipientscount` smallint(255) DEFAULT NULL COMMENT '拨打收件人电话次数',
  `problemparts_callcompanycount` smallint(255) DEFAULT NULL COMMENT '拨打网点电话次数：默认值为0,逐1累加。网点可以是派件点，停滞点或者上级网点',
  `problemparts_uncollecteddays` smallint(255) DEFAULT NULL COMMENT '未收到天数:默认值为0,逐1累加',
  `problemparts_receivestate` smallint(255) DEFAULT NULL COMMENT '是否签收:10=签收，20=未签收',
  `problemparts_loststate` smallint(255) DEFAULT NULL COMMENT '是否遗失:10=遗失，20=未遗失',
  `problemparts_receivedate` date DEFAULT NULL COMMENT '签收日期:催单流程获取的日期',
  `problemparts_reminderstate` smallint(255) DEFAULT NULL COMMENT '催单流程分支:10=继续系统流程等待，20=智能语音催中心，30=上报遗失',
  `problemparts_source` smallint(255) DEFAULT '10' COMMENT '工单来源:10=系统，20=中天',
  `problemparts_operatorid` bigint(255) DEFAULT NULL COMMENT '操作员ID:上报人ID',
  `problemparts_createdate` datetime  DEFAULT CURRENT_TIMESTAMP() COMMENT '创建时间',
  `problemparts_changedate` datetime  DEFAULT NULL COMMENT '修改时间',
  `problemparts_memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `problemparts_varparama` varchar(255) DEFAULT NULL,
  `problemparts_varparamb` varchar(255) DEFAULT NULL,
  `problemparts_varparamc` varchar(255) DEFAULT NULL,
  `problemparts_intparama` bigint(255) DEFAULT NULL,
  `problemparts_intparamb` bigint(255) DEFAULT NULL,
  `problemparts_intparamc` bigint(255) DEFAULT NULL,
  `problemparts_floatparama` float(255,0) DEFAULT NULL,
  `problemparts_floatparamb` float(255,0) DEFAULT NULL,
  `problemparts_floatparamc` float(255,0) DEFAULT NULL,
  PRIMARY KEY (`problemparts_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='问题件上报表';


/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50639
Source Host           : 127.0.0.1:3306
Source Database       : biz

Target Server Type    : MYSQL
Target Server Version : 50639
File Encoding         : 65001

Date: 2018-03-19 11:33:03
*/

/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.7
Source Server Version : 50721
Source Host           : 192.168.1.7:3306
Source Database       : nk

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-03-23 16:16:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tapp_joblist http://localhost:8000/ksg/#/usercenter/jobList
-- ----------------------------
-- DROP TABLE IF EXISTS `tapp_joblist`;
CREATE TABLE `tapp_joblist` (
  `id` bigint(64) NOT NULL,
  `joblist_tabid` bigint(64) DEFAULT NULL COMMENT '表单id,与TApp_Table表中的TableID相对应',
  `joblist_dealid` bigint(64) DEFAULT NULL COMMENT '历史处理记录ID:与TApp_DealInfo：AppDeal_ID对应',
  `joblist_typeid` bigint(64) DEFAULT NULL COMMENT '表单类型ID:参见TBase_TableType表单类型对应表',
  `joblist_dealtype` bigint(64) DEFAULT NULL COMMENT '表单的处理类型ID:参见TBase_DealType表单处理类型表，与Deal_ID相对应',
  `joblist_dealstate` bigint(64) DEFAULT NULL COMMENT '表单处理状态（执行结果状态）:参见TBase_DealState表单处理状态一览表',
  `joblist_content` varchar(64) DEFAULT NULL COMMENT '表单的处理意见',
  `joblist_uptabid` bigint(64) DEFAULT NULL COMMENT '对应的上级表单ID:表明了产生本表单记录的原因，即上级表单号',
  `joblist_departid` bigint(64) DEFAULT NULL COMMENT '表单的处理部门ID:与部门信息表中的部门ID相对应',
  `joblist_dealerid` bigint(64) DEFAULT NULL COMMENT '表单的处理人ID',
  `joblist_projectname` varchar(64) DEFAULT NULL COMMENT '关联的项目名称:如果该表单与项目相关，记录该项目ID',
  `joblist_projectid` bigint(64) DEFAULT NULL COMMENT '关联的项目ID:如果该表单与项目相关，记录该项目ID',
  `joblist_historytabid` bigint(64) DEFAULT NULL COMMENT '历史关联表单ID:在表单修改产生新表单号的时候，记录与新表单相关联的历史表单号（原表单）供追述',
  `joblist_recorderid` bigint(64) DEFAULT NULL COMMENT '共用信息记录ID:该字段用来记录该类表单与相应部件的共用信息记录的ID，请勿随意占用该字段',
  `joblist_companyid` bigint(64) DEFAULT NULL COMMENT '网点ID:',
  `joblist_groupid` bigint(64) DEFAULT NULL COMMENT '表单处理小组ID:参见TBase_UserReport表',
  `joblist_result` bigint(64) DEFAULT NULL COMMENT '表单的处理结果',
  `joblist_tableno` varchar(200) DEFAULT NULL COMMENT '表单编号:其内容组成是表单头部信息+年月日时间+表单ID',
  `joblist_type` tinyint(64) DEFAULT NULL COMMENT '待办类型:待办类型1=人工；2=系统',
  `joblist_memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `joblist_staffname` varchar(255) DEFAULT NULL COMMENT '业务员',
  `joblist_comname` varchar(255) DEFAULT NULL COMMENT '网点名称:网点名称',
  `joblist_state` tinyint(255) DEFAULT NULL COMMENT '待办状态:待办状态1=有效；9=无效',
  `joblist_varparama` varchar(255) DEFAULT NULL,
  `joblist_varparamb` varchar(255) DEFAULT NULL,
  `joblist_varparamc` varchar(255) DEFAULT NULL,
  `joblist_intparama` bigint(255) DEFAULT NULL,
  `joblist_intparamb` bigint(255) DEFAULT NULL,
  `joblist_intparamc` bigint(255) DEFAULT NULL,
  `joblist_floatparama` float(255,0) DEFAULT NULL,
  `joblist_floatparamb` float(255,0) DEFAULT NULL,
  `joblist_floatparamc` float(255,0) DEFAULT NULL,
  `joblist_problempartsid` int(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- 创建角色表
CREATE TABLE `tbase_roleinfo` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '角色名称',
  `code` varchar(32) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `alias` varchar(64) NOT NULL DEFAULT '' COMMENT '角色别名',
  `state` bigint(20) NOT NULL DEFAULT '0' COMMENT '记录状态 State=1表示纪录有效；State=9，表示纪录无效（已被删除纪录）。纪录撤消或变更后，将原纪录的状态值置为==1。状态的缺省值(默认值)为0',
  `type` smallint(6) DEFAULT NULL COMMENT '角色类型 1=内部角色，2=外部角色，3=人员小组',
  `states` varchar(64) NOT NULL DEFAULT '' COMMENT '记录的状态名',
  `types` varchar(64) NOT NULL DEFAULT '' COMMENT '角色类型名',
  `roleinfo_varparama` varchar(255) DEFAULT NULL,
  `roleinfo_varparamb` varchar(255) DEFAULT NULL,
  `roleinfo_varparamc` varchar(255) DEFAULT NULL,
  `roleinfo_intparama` bigint(64) DEFAULT NULL,
  `roleinfo_intparamc` bigint(64) DEFAULT NULL,
  `roleinfo_intparamb` bigint(64) DEFAULT NULL,
  `roleinfo_floatparama` float(255,0) DEFAULT NULL,
  `roleinfo_floatparamb` float(255,0) DEFAULT NULL,
  `roleinfo_floatparamc` float(255,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10008 DEFAULT CHARSET=utf8;

-- 角色表初始化记录
INSERT INTO `tbase_roleinfo` VALUES ('1', '超级管理员', '10001', '初始化', 'administrator', '1', '1', '记录有效', '内部角色',0,0,0,0,0,0,0,0,0);
INSERT INTO `tbase_roleinfo` VALUES ('2', '人工客服', '10002', '初始化', 'artificialService', '1', '1', '记录有效', '内部角色',0,0,0,0,0,0,0,0,0);
INSERT INTO `tbase_roleinfo` VALUES ('3', '智能客服', '10003', '初始化', 'intelligentService', '1', '1', '记录有效', '内部角色',0,0,0,0,0,0,0,0,0);
INSERT INTO `tbase_roleinfo` VALUES ('4', '客户', '10004', '初始化', 'customer', '1', '1', '记录有效', '内部角色',0,0,0,0,0,0,0,0,0);
INSERT INTO `tbase_roleinfo` VALUES ('5', '网点员工', '10005', '初始化', 'networkStaff', '1', '1', '记录有效', '内部角色',0,0,0,0,0,0,0,0,0);


CREATE TABLE `uc_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `content` longtext,
  `updateDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uc_flow_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


----网点结算表
CREATE TABLE `ex_info_network` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `dot_state` varchar(5) DEFAULT NULL,
  `dot_name` varchar(20) DEFAULT NULL,
  `dot_phone` varchar(20) DEFAULT NULL,
  `dot_address` varchar(50) DEFAULT NULL,
  `dot_createdate` varchar(200) DEFAULT NULL,
  `dot_changedate` varchar(200) DEFAULT NULL,
  `dot_memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网点结算表';


