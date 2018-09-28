 -- lishaomiao 8.24号 该数据库脚本在tbase_userreport表上
 insert into tbase_userreport(code,companyid,description,floatparam1,intparam1,name,state,staffname,states,type,types,value,varparam1)
  values
 ('wtjsbsjfw',null,null,null,null,'问题件上报时间范围',1,null,'有效',1,'系统参数','08:20-18:00',null);

--  sunjiajian 8.24 tbase_userreport插入数据
 INSERT INTO `tbase_userreport` VALUES ('hxxcs',1,'有效',1,'系统参数','电话核心线程数','0','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('kkzxcs',1,'有效',1,'系统参数','电话线程可扩展数量','0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('cxdhsjjg',1,'有效',1,'系统参数','查询电话线程时间间隔','1','以毫秒为单位',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('dhrwsl',1,'有效',1,'系统参数','电话线程任务数量','0',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

--  sunjiajian 8.24 tbase_userreport插入数据
INSERT INTO `tbase_userreport` VALUES ('sysjc',1,NULL,1,NULL,'时间戳','1535093750000','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);


--  sunjiajian 8.25 重新导入TApp_CallDetail表
CREATE TABLE `TApp_CallDetail` (
  `calldetail_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `calldetail_tableid` bigint(20) DEFAULT NULL COMMENT 'tableinfo表的id',
  `calldetail_taskid` bigint(20) DEFAULT NULL COMMENT 'uflo的id',
  `calldetail_businessid` bigint(1) DEFAULT NULL COMMENT 'uflo的businessid',
  `calldetail_phonenumber` varchar(24) NOT NULL DEFAULT '' COMMENT '致电人',
  `calldetail_uqianshou` varchar(255) DEFAULT NULL COMMENT '签收结果',
  `calldeatil_uline` varchar(255) DEFAULT NULL COMMENT '网点线路号',
  `calldetail_calltime` smallint(6) DEFAULT '0',
  `calldetail_calldate` smallint(6) DEFAULT '0',
  `calldetail_result` int(11) DEFAULT '0',
  `calldetail_expressnumber` varchar(100) NOT NULL DEFAULT '' COMMENT '运单号',
  `calldetail_nodename` varchar(11) DEFAULT NULL COMMENT 'uflo的节点名',
  `calldetail_contentid` smallint(6) DEFAULT NULL,
  `calldetail_answerid` smallint(6) DEFAULT NULL,
  `calldetail_name` varchar(100) DEFAULT NULL,
  `calldetail_companyid` bigint(20) DEFAULT NULL COMMENT '网点公司id',
  `calldetail_calltype` smallint(6) NOT NULL,
  `calldetail_ivrid` varchar(200) DEFAULT NULL COMMENT 'ivr模板id',
  `calldetail_callphonenumber` varchar(255) DEFAULT NULL COMMENT 'ivr拨打的电话实际拨打的号码经过转化：去除''0''跟''-''、空格',
  `calldetail_callexpressnumber` varchar(255) DEFAULT NULL COMMENT 'ivr播报的运单号',
  `calldetail_period` float(10,2) DEFAULT NULL,
  `calldetail_dealid` bigint(20) DEFAULT NULL COMMENT 'dealinfo的id',
  `calldetail_inside_item` varchar(255) NOT NULL DEFAULT '' COMMENT '内物',
  `calldetail_state` smallint(6) NOT NULL DEFAULT '0' COMMENT '用于标记当前状态  0=未执行  1= 已执行  2=等待执行',
  `calldeatil_umessage` varchar(255) DEFAULT NULL COMMENT '回复消息',
  `calldetail_outcome` varchar(255) DEFAULT NULL COMMENT '电话是否打通',
  `calldetail_uuid` varchar(255) DEFAULT NULL COMMENT 'uuid',
  `calldetail_callnumber` int(11) DEFAULT NULL COMMENT '一天打电话的次数',
  `calldeatil_utype` varchar(255) DEFAULT NULL COMMENT '内物',
  `calldeatil_uname` varchar(255) DEFAULT NULL COMMENT '致电人的名字',
  `calldeatil_ureturn` varchar(255) DEFAULT NULL COMMENT '是否改地址',
  `calldeatil_uitems` varchar(255) DEFAULT NULL COMMENT '内物是否收到',
  `calldeatil_uselflf` varchar(255) DEFAULT NULL COMMENT '是否本人签收',
  `calldeatil_uoutside` varchar(255) DEFAULT NULL COMMENT '外物是否破损',
  `calldeatil_uinside` varchar(255) DEFAULT NULL COMMENT '内物是否破损',
  `calldeatil_udate` varchar(255) DEFAULT NULL COMMENT '是否知道签收日期，暂时不用',
  `calldetail_time` timestamp DEFAULT NULL,
  PRIMARY KEY (`calldetail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

INSERT INTO `TApp_CallDetail` VALUES (16,170671,NULL,NULL,'18635098663',NULL,NULL,1,4,NULL,'734999743971',NULL,1,1,'肆叁贰壹网点',31581,1,'10000204',NULL,NULL,NULL,10351,'您好，中通快递,请问您购买的衣服有没有收到',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(17,114683,NULL,NULL,'13175031563',NULL,NULL,1,5,NULL,'968178139728',NULL,1,1,'肆叁贰壹网点',93973,2,'10000183',NULL,NULL,NULL,75558,'您好，中通快递,请问您购买的衣服有没有收到',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(18,119713,NULL,NULL,'13175031563',NULL,NULL,6,2,NULL,'669133297456',NULL,1,1,'贰壹网点',45409,1,'10000183',NULL,NULL,NULL,1775,'您好，中通快递,请问您购买的衣服有没有收到',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(19,197588,NULL,NULL,'021-12345678',NULL,NULL,9,6,NULL,'531677341542',NULL,1,1,'捌柒陆伍肆叁贰壹网点',43201,2,'10000204',NULL,NULL,NULL,10668,'您好，中通快递,请问您购买的衣服有没有收到',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,110019,NULL,NULL,'021-12345678',NULL,NULL,4,0,NULL,'164989688531',NULL,1,1,'肆叁贰壹网点',48904,1,'10000204',NULL,NULL,NULL,55945,'您好，中通快递,请问您购买的衣服有没有收到',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(22,137391,NULL,NULL,'15958040870',NULL,NULL,1,8,NULL,'354289884189',NULL,1,1,'柒陆伍肆叁贰壹网点',49361,1,'10000204',NULL,NULL,NULL,27629,'您好，中通快递,请问您购买的衣服有没有收到',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);


--  sunjiajian 8.25 tbase_userreport插入新数据
INSERT INTO `tbase_userreport` VALUES ('sjjg',1,'有效',1,'系统参数','时间间隔','60','单位为秒',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

insert into `tbase_userreport`(code,description,name,state,states,type,types,value) value('psbdsjrIVRid','破损件拨打收件人IVR','破损件拨打收件人IVR', 1,'有效', 1,'系统参数','10000682');

--  sunjiajian 8.30 tapp_calldetail插入新字段
ALTER TABLE `TApp_CallDetail` ADD calldetail_ip VARCHAR(50) NOT NULL COMMENT '本机ip地址,区分任务';
