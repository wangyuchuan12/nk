-- dealinfo sunjiajian
ALTER TABLE `tapp_tabdealinfo` ADD appdeal_tabletype INT NULL COMMENT '表单类型,工作流业务模型的ID(process ID)';
ALTER TABLE `tapp_tabdealinfo` ADD Appdeal_tabletypename VARCHAR(100) NULL COMMENT '表单类型名称,工作流业务模型的名称(processID对应的名称)';
ALTER TABLE `tapp_tabdealinfo` ADD appdeal_taskid BIGINT NULL COMMENT '工作流流程环节ID';
ALTER TABLE `tapp_tabdealinfo` ADD appdeal_problemid BIGINT NOT NULL COMMENT '问题件ID';
ALTER TABLE `tapp_tabdealinfo` ADD appdeal_date TIMESTAMP NULL COMMENT '表单的处理时间';
ALTER TABLE `tapp_tabdealinfo` MODIFY appdeal_dealername VARCHAR(64) DEFAULT NULL;
ALTER TABLE `tapp_tabdealinfo` MODIFY appdeal_result BIGINT(20) DEFAULT NULL;
ALTER TABLE `tapp_tabdealinfo` MODIFY appdeal_resulttext VARCHAR(64) DEFAULT NULL;
-- tableinfo sunjiajian
ALTER TABLE `tapp_tableinfo` ADD tableinfo_problemid BIGINT NOT NULL COMMENT '问题件id';
ALTER TABLE `tapp_tableinfo` ADD tableinfo_tabid BIGINT NOT NULL COMMENT '工作流实例ID（instantID)';
ALTER TABLE `tapp_tableinfo` ADD tableinfo_tabletype INT NOT NULL COMMENT '表单类型 工作流业务模型的名称(取自工作流)';
ALTER TABLE `tapp_tableinfo` ADD tableinfo_createdate TIMESTAMP NULL COMMENT '表单的生成时间';
ALTER TABLE `tapp_tableinfo` ADD tableinfo_dealname VARCHAR(64) NULL COMMENT '表单处理类型的名称,流程环节名称';
ALTER TABLE `tapp_tableinfo` MODIFY tableinfo_dealstate INT(11) DEFAULT NULL;
ALTER TABLE `tapp_tableinfo` MODIFY tableinfo_dealername VARCHAR(64) DEFAULT NULL;
ALTER TABLE `tapp_tableinfo` MODIFY tableinfo_result INT(20) DEFAULT NULL;
-- joblist sunjiajian
ALTER TABLE `tapp_joblist` ADD joblist_problemid BIGINT NOT NULL COMMENT '问题件id';
ALTER TABLE `tapp_joblist` ADD joblist_expressnumber VARCHAR(100) NULL COMMENT '运单号';
ALTER TABLE `tapp_joblist` ADD joblist_tabletypename VARCHAR(100) NULL COMMENT '表单类型名称,工作流业务模型的名称(processID对应的名称)';
ALTER TABLE `tapp_joblist` ADD joblist_dealname VARCHAR(100) NOT NULL COMMENT '流程环节名称';
ALTER TABLE `tapp_joblist` ADD joblist_time TIMESTAMP NULL COMMENT '代办产生的时间';

DROP TABLE IF EXISTS `tapp_calldetail`;
CREATE TABLE `tapp_calldetail` (
  `calldetail_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `calldetail_tableid` bigint(20) NOT NULL COMMENT '表单id',
  `calldetail_dealid` bigint(20) NOT  NULL COMMENT  '任务id',
  `calldetail_problemid` bigint(20) DEFAULT NULL COMMENT '问题件id',
  `calldetail_phonenumber` VARCHAR(100) NOT NULL COMMENT '电话号码',
  `calldetail_result` int(11) DEFAULT NULL COMMENT '拨打结果',
  `calldetail_expressnumber` varchar(100) DEFAULT NULL COMMENT '运单号',
  `calldetail_nodename` varchar(100) DEFAULT NULL COMMENT '工作流环节标识',
  `calldetail_calltime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '实际拨打时间',
  `calldetail_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '计划拨打时间',
  `calldetail_contentid` smallint(6) DEFAULT NULL COMMENT '电话内容id',
  `calldetail_content` VARCHAR(100)  DEFAULT NULL COMMENT '电话内容',
  `calldetail_answerid` smallint(6) DEFAULT NULL COMMENT '答案id',
  `calldetail_name` varchar(100) DEFAULT NULL COMMENT '拨打电话的对象名称',
  `calldetail_companyid` bigint(20) DEFAULT NULL COMMENT '网点公司id',
  `calldetail_calltype` smallint(6) NOT NULL COMMENT '拨打电话类型',
  `calldetail_ivrid` VARCHAR(100) DEFAULT NULL COMMENT '设置语音模块的id',
  `calldetail_answer` varchar(100) DEFAULT NULL COMMENT '拨打电话回复的结果',
  `calldetail_period` float(10,2) DEFAULT NULL,
  `calldetail_state` smallint(6) NOT NULL COMMENT '拨打状态',
  `mwordlevel_varparama` varchar(4000) DEFAULT NULL,
  `mwordlevel_varparamb` varchar(4000) DEFAULT NULL,
  `mwordlevel_varparamc` varchar(4000) DEFAULT NULL,
  `mwordlevel_varparamd` varchar(4000) DEFAULT NULL,
  `mwordlevel_varparame` varchar(4000) DEFAULT NULL,
  `mwordlevel_intparama` bigint(20) DEFAULT NULL,
  `mwordlevel_intparamb` bigint(20) DEFAULT NULL,
  `mwordlevel_intparamc` bigint(20) DEFAULT NULL,
  `mwordlevel_intparamd` bigint(20) DEFAULT NULL,
  `mwordlevel_intparame` bigint(20) DEFAULT NULL,
  `mwordlevel_floatparama` float DEFAULT NULL,
  `mwordlevel_floatparamb` float DEFAULT NULL,
  `mwordlevel_floatparamc` float DEFAULT NULL,
  `mwordlevel_floatparamd` float DEFAULT NULL,
  `mwordlevel_floatparame` float DEFAULT NULL,
  PRIMARY KEY (`calldetail_id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

 -- lishaomiao 9.4号 该数据库脚本在tbase_menuinfo表上
insert into tbase_menuinfo(code,menuinfo_memo,extattr,hidden,menuinfo_icon,lft,menuinfo_tabid,menuinfo_layrec,menuinfo_layno,
menuinfo_type,menuinfo_name,menuinfo_intro,menuinfo_status,menuinfo_attribute,menuinfo_varparama,menuinfo_varparamb,menuinfo_varparamc,
menuinfo_varparamd,menuinfo_varparame,menuinfo_intparama,menuinfo_intparamb,menuinfo_intparamc,menuinfo_intparamd,menuinfo_intparame,
menuinfo_floatparama,menuinfo_floatparamb,menuinfo_floatparamc,menuinfo_floatparamd,menuinfo_floatparame,menuinfo_showname,menuinfo_upmenuid,
resource,rgt,sort,target,menuinfo_url) values('usercenter_systemtel', null, null, null,'usergroup', null, null, null, null, null, null, null, null, null, null, null,
 null, null, null, null, null, null, null, null, null, null, null, null, null,
'电话参数配置', '10003', null, null, null, null, '/usercenter/systemtel'
)


-- 新增几个joblist字段 sunjiajian
ALTER TABLE `tapp_joblist` ADD joblist_owner VARCHAR(20) DEFAULT 'NULL' COMMENT '这个代办的拥有人';
ALTER TABLE `tapp_joblist` ADD joblist_tabletype INT NULL COMMENT '表单类型,工作流业务模型的ID(process ID)';
ALTER TABLE `tapp_joblist` ADD joblist_time TIMESTAMP NULL COMMENT '代办产生的时间';
ALTER TABLE `tapp_joblist` ADD joblist_varparamd VARCHAR(64) NULL;
ALTER TABLE `tapp_joblist` ADD joblist_varparame VARCHAR(64) NULL;
ALTER TABLE `tapp_joblist` ADD joblist_intparamd BIGINT NULL;
ALTER TABLE `tapp_joblist` ADD joblist_intparame BIGINT NULL;
ALTER TABLE `tapp_joblist` ADD joblist_floatparamd FLOAT NULL;
ALTER TABLE `tapp_joblist` ADD joblist_floatparame FLOAT NULL;
ALTER TABLE `tapp_joblist` ADD joblist_problemtype INT NULL COMMENT '问题件类型';
ALTER TABLE `tapp_joblist` ADD joblist_submitterphone VARCHAR(25) NULL COMMENT '投诉人电话';

-- 9.5 sunjiajian 新增tbase_dealtype表
DROP TABLE IF EXISTS `tbase_dealtype`;
CREATE TABLE `tbase_dealtype` (
  `deal_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '表单处理类型ID',
  `deal_tabtypeid` bigint(20) DEFAULT NULL COMMENT 'Process_ID业务模型ID',
  `deal_tabname` VARCHAR(100) DEFAULT NULL COMMENT '流程名称',
  `deal_type` varchar(512) NOT NULL DEFAULT '0' COMMENT '处理类型代码,类似于111,112等',
  `deal_taskno` smallint(6) DEFAULT NULL COMMENT '工作流环节编码',
  `deal_url` varchar(512) DEFAULT NULL COMMENT '程序链接路径',
  `deal_joburl` varchar(512) DEFAULT NULL COMMENT '待办路径',
  `deal_dealname` varchar(64) DEFAULT NULL COMMENT '处理类型的名称,表单的处理行为类型包括如制表、检验、确认、更改、评审、审核、审批等',
  `deal_state` smallint(6) NOT NULL DEFAULT '0' COMMENT '记录状态.,state=9表示记录失效。删除的时候并不删除记录，而是将记录状态值设为失效。失效的记录将不会在界面上显示出来。系统缺省状态State=1，表示记录有效。',
  `deal_memo` varchar(512) DEFAULT NULL COMMENT '说明',
  `deal_varparama` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `deal_varparamb` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `deal_varparamc` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `deal_varparamd` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `deal_varparame` varchar(1024) DEFAULT NULL COMMENT '字符备用字段',
  `deal_intparama` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `deal_intparamb` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `deal_intparamc` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `deal_intparamd` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `deal_intparame` bigint(20) DEFAULT NULL COMMENT '数字备用字段',
  `deal_floatparama` float DEFAULT NULL COMMENT '浮点备用字段',
  `deal_floatparamb` float DEFAULT NULL COMMENT '浮点备用字段',
  `deal_floatparamc` float DEFAULT NULL COMMENT '浮点备用字段',
  `deal_floatparamd` float DEFAULT NULL COMMENT '浮点备用字段',
  `deal_floatparame` float DEFAULT NULL COMMENT '浮点备用字段',
  PRIMARY KEY (`Deal_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--tbase_menuinfo表增加字段
ALTER TABLE `tbase_menuinfo` ADD menuinfo_dealid  INT  COMMENT 'Deal_ID表单操作信息表ID';


 -- lishaomiao 9.5号 该数据库脚本在tbase_menuinfo表上
insert into tbase_menuinfo(code,menuinfo_memo,extattr,hidden,menuinfo_icon,lft,menuinfo_tabid,menuinfo_layrec,menuinfo_layno,
menuinfo_type,menuinfo_name,menuinfo_intro,menuinfo_status,menuinfo_attribute,menuinfo_varparama,menuinfo_varparamb,menuinfo_varparamc,
menuinfo_varparamd,menuinfo_varparame,menuinfo_intparama,menuinfo_intparamb,menuinfo_intparamc,menuinfo_intparamd,menuinfo_intparame,
menuinfo_floatparama,menuinfo_floatparamb,menuinfo_floatparamc,menuinfo_floatparamd,menuinfo_floatparame,menuinfo_showname,menuinfo_upmenuid,
resource,rgt,sort,target,menuinfo_url) values('express_dealtype', null, null, null,'usergroup', null, null, null, null, null, null, null, null, null, null, null,
 null, null, null, null, null, null, null, null, null, null, null, null, null,
'表单操作类型', '10003', null, null, null, null, '/express/dealtype'
)

--增加系统参数  流程key 王岩   2018.09.05
insert into tbase_userreport(code,companyid,description,floatparam1,intparam1,name,state,staffname,states,type,types,value,varparam1)
 values('QSWSDLC', null, '签收未收到流程key', null, null, '签收未收到流程', 1, null, '有效', 1, '系统参数', '签收未收到精简版', null),
('PSLC', null, '破损流程key', null, null, '破损流程', 1, null, '有效', 1, '系统参数', '破损B', null),
('GDZLC', null, '改地址流程key', null, null, '改地址流程', 1, null, '有效', 1, '系统参数', '改地址人工任务版', null),
('CDLC', null, '催单流程key', null, null, '催单流程', 1, null, '有效', 1, '系统参数', '催单', null),
('THLC', null, '退回流程key', null, null, '催单流程', 1, null, '有效', 1, '系统参数', '退回', null);


-- 修改tapp_joblist表字符集改为utf8格式,讲表字段修改成utf8格式,解决乱码 sunjiajian 2018.09.06
ALTER TABLE `tapp_joblist` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `tapp_joblist` CHANGE joblist_tabletypename joblist_tabletypename VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci;

ALTER TABLE `tapp_joblist` CHANGE joblist_dealname joblist_dealname VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci;

-- 修改tapp_joblist表的id字段属性为自增sunjiajian 2018.09.06
ALTER TABLE `tapp_joblist` change id id BIGINT(20) NOT NULL AUTO_INCREMENT;



 -- lishaomiao 9.5号 该数据库脚本在tbase_userreport表上
ALTER TABLE `tbase_userreport` MODIFY description VARCHAR(1024);



-- 9.6 sunjiajian 在ex_info_record表中新增字段
ALTER TABLE `ex_info_record` ADD express_taskid BIGINT NOT NULL COMMENT '任务ID';
ALTER TABLE `ex_info_record` ADD express_dealtype SMALLINT NULL COMMENT '分类编码';
ALTER TABLE `ex_info_record` ADD express_nodename VARCHAR(64) NULL COMMENT '当前节点名';
ALTER TABLE `ex_info_record` ADD express_state SMALLINT NULL COMMENT '处理状态，0未处理，1已处理';
ALTER TABLE `ex_info_record` ADD express_querytime TIMESTAMP NULL COMMENT '实际查询时间';
ALTER TABLE `ex_info_record` ADD express_plantime TIMESTAMP NULL COMMENT '计划查询时间';
ALTER TABLE `ex_info_record` ADD express_ipaddress VARCHAR(64) NULL COMMENT '主机地址';
ALTER TABLE `ex_info_record` ADD express_problemid BIGINT NULL COMMENT '问题件ID';
ALTER TABLE `ex_info_record` ADD express_result VARCHAR(64) NULL COMMENT '查询结果';

-- 9.7 sunjiajian 在ex_info_record表中修改字段属性
alter table `ex_info_record` modify column express_result INT COMMENT '物流结果';
-- 9.7 wangyan calldetail增加字段
 -- ALTER TABLE `tapp_calldetail` add  calldetail_dealid bigint(20) NOT  NULL COMMENT '任务id';

-- 9.8 sunjiajian tapp_joblist、tapp_tableinfo、tapp_tabdealinfo增加一个字段
ALTER TABLE `tapp_joblist` ADD joblist_record VARCHAR(1024) DEFAULT NULL COMMENT '记录';
ALTER TABLE `tapp_tableinfo` ADD tableinfo_record VARCHAR(1024) DEFAULT NULL COMMENT '记录';
ALTER TABLE `tapp_tabdealinfo` MODIFY appdeal_record VARCHAR(1024) DEFAULT NULL COMMENT '记录';

-- 9.8 sunjiajian ex_info_record增加二个字段，修改一个字段属性
alter table `ex_info_record` add column express_updateid BIGINT COMMENT '查找最新的record';
ALTER TABLE `ex_info_record` MODIFY express_taskid BIGINT DEFAULT 0 COMMENT'任务id';

-- 2018.9.14 sunjiajian tbase_dealtype表增加一个字段
ALTER TABLE `tbase_dealtype` ADD deal_tabname VARCHAR(50) DEFAULT NULL COMMENT '表单名字';

-- 2018.9.14 sunjiajian tapp_tabdealinfo、tapp_tableinfo、tapp_joblist修改字段属性
ALTER TABLE `tapp_tabdealinfo` MODIFY appdeal_dealtype VARCHAR(50) DEFAULT NULL COMMENT '处理类型';
ALTER TABLE `tapp_tableinfo` MODIFY tableinfo_dealtype VARCHAR(50) DEFAULT NULL COMMENT '处理类型';
ALTER TABLE `tapp_joblist` MODIFY joblist_dealtype VARCHAR(50) DEFAULT NULL COMMENT '处理类型';

-- 2018.9.16 sunjiajian ex_info_record修改字段属性
ALTER TABLE `ex_info_record` MODIFY express_dealtype VARCHAR(64) DEFAULT NULL COMMENT '分类编码';

-- 2018.9.17 sunjiajian ex_info_record表增加一个字段
ALTER TABLE `ex_info_record` ADD express_stagnationcompanyname VARCHAR(50) DEFAULT NULL COMMENT'停滞网点名';
-- 2018.9.17 sunjiajian ex_info_record表增加一个字段
ALTER TABLE `ex_info_record` ADD express_processinstanceid BIGINT DEFAULT NULL COMMENT'实例ID';
-- 2018.9.17 sunjiajian ex_info_record表删除一个字段
ALTER TABLE `ex_info_record` drop column express_do;
-- 2018.9.19 sunjiajian ex_info_record表增加一个字段
ALTER TABLE `ex_info_record` ADD express_networknumber SMALLINT DEFAULT NULL COMMENT'计数停滞网点个数';


-- 修改属性 2018.9.19sunjiajian
ALTER TABLE `tapp_tableinfo` MODIFY tableinfo_dealid BIGINT DEFAULT NULL COMMENT 'dealinfo的ID';

ALTER TABLE `tapp_tableinfo` MODIFY tableinfo_bussinessid BIGINT DEFAULT NULL COMMENT '';

ALTER TABLE `tapp_tableinfo` MODIFY tableinfo_uptabid BIGINT DEFAULT NULL COMMENT '父流程instanceID';

ALTER TABLE `tapp_tableinfo` MODIFY tableinfo_tabletype INT(11) DEFAULT NULL COMMENT '工作流业务模型的名称';

-- sunjiajian 2018.9.20 新增物流查询测试记录表
DROP TABLE IF EXISTS `ex_info_test`;
CREATE TABLE `ex_info_test` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `express_number` varchar(64) DEFAULT NULL DEFAULT '0' COMMENT '运单号',
  `express_taskid` bigint(20) DEFAULT NULL COMMENT '任务ID',
  `express_dealtype` int(64) DEFAULT NULL COMMENT '分类编码',
  `express_nodename` varchar(64) DEFAULT NULL COMMENT '当前节点名',
  `express_querytime` timestamp NULL DEFAULT NULL COMMENT '实际查询时间',
  `express_plantime` timestamp NULL DEFAULT NULL COMMENT '计划查询时间',
  `express_problemid` bigint(20) DEFAULT NULL COMMENT '问题件ID',
  `express_result` varchar(64) DEFAULT NULL COMMENT '查询结果',
  `express_modal` varchar(64) DEFAULT NULL COMMENT '物流查询类型',
  `express_processinstanceid` bigint(20) DEFAULT NULL COMMENT '实例ID',
  `express_state` smallint(6) DEFAULT NULL COMMENT '处理状态，0未处理，1已处理',
  `express_ipaddress` varchar(64) DEFAULT NULL COMMENT '主机地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='解析快递信息测试记录表';

-- sunjiajian 2018.9.20 修改字段属性 增加字段
ALTER TABLE `tapp_tabdealinfo` MODIFY appdeal_dealtype INT(64) DEFAULT NULL COMMENT '处理类型';
ALTER TABLE `tapp_tableinfo` MODIFY tableinfo_dealtype INT(64) DEFAULT NULL COMMENT '处理类型';
ALTER TABLE `tapp_joblist` MODIFY joblist_dealtype INT(64) DEFAULT NULL COMMENT '处理类型';
ALTER TABLE `ex_info_record` MODIFY express_dealtype INT(64) DEFAULT NULL COMMENT '处理类型';

ALTER TABLE `ex_info_record` ADD express_lasttime TIMESTAMP NULL DEFAULT NULL COMMENT '上一次查询时间';

-- sunjiajian 2018.9.26 增加字段
 ALTER TABLE `ex_info_record` ADD express_dealid BIGINT DEFAULT NULL COMMENT 'dealInfo表的主键ID';
 ALTER TABLE `ex_info_record` ADD express_tabid BIGINT DEFAULT NULL COMMENT 'tableInfo表的主键ID';

 ALTER TABLE `tapp_joblist` ADD joblist_problemtype INT DEFAULT NULL COMMENT '问题类型';
 ALTER TABLE `tapp_joblist` ADD joblist_submitterphone VARCHAR(30) DEFAULT NULL COMMENT '发件人电话';

-- lishaomiao 2018.9.26 修改字段属性
ALTER TABLE tapp_problemparts MODIFY problemparts_receiveaddress VARCHAR (255);
ALTER TABLE tapp_problemparts MODIFY problemparts_sendaddress VARCHAR (255);
ALTER TABLE tapp_problemparts MODIFY problemparts_newreceiveaddress VARCHAR (255);

-- sunjiajian 2018.9.26 增加字段
 ALTER TABLE `ex_info_record` ADD express_errorcompanyname VARCHAR(500) DEFAULT NULL COMMENT '错误网点名';

-- sunjiajian 2018.9.27 修改字段属性
ALTER TABLE `tapp_tableinfo` MODIFY tableinfo_problemid BIGINT DEFAULT NULL COMMENT'问题件ID';
