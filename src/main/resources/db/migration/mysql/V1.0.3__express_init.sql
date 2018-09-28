CREATE TABLE ex_customer (
  id      INT(11)      NOT NULL AUTO_INCREMENT,
  name    VARCHAR(32)  NULL,
  phone   VARCHAR(32)  NULL,
  address VARCHAR(255) NULL,
  PRIMARY KEY (id),
  KEY (name)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE ex_company (
  id      INT(11)      NOT NULL AUTO_INCREMENT,
  name    VARCHAR(32)  NULL,
  phone   VARCHAR(32)  NULL,
  address VARCHAR(255) NULL,
  PRIMARY KEY (id),
  KEY (name)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;


CREATE TABLE ex_waybill (
  id          INT(11)     NOT NULL AUTO_INCREMENT,
  customer_id INT(11)     NULL,
  company_id INT(11)     NULL,
  from_city   VARCHAR(32) NULL,
  to_city     VARCHAR(32) NULL,
  number      VARCHAR(32) NULL,
  process_instance_id int(11) NULL,
  PRIMARY KEY (id),
  KEY (customer_id),
  KEY (number)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;


CREATE TABLE temp_variable (
  id           BIGINT(11)     NOT NULL AUTO_INCREMENT,
  t_key         VARCHAR (60)    NOT NULL,
  t_value      VARCHAR (255)   NOT  NULL,
  process_id    BIGINT(60)      NOT NULL,

  PRIMARY KEY (id),
  KEY (process_id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = UTF8;

CREATE TABLE `TManage_Data` (
  `MData_ID` bigint AUTO_INCREMENT comment 'ID',
  `MData_CompanyID` bigint(20)   comment '网点ID',
  `MData_CompanyName` varchar(64)  comment '网点名称',
  `MData_CompanyCode` varchar(32)   comment '网点编码',
  `MData_UserName`    varchar(50)    comment '客户姓名',
  `MData_UserID`      bigint       comment '客服ID',
  `MData_UserType`    bigint     comment '用户类型',
  `MData_ProblemType` smallint   comment '问题类别',
  `MData_Amount`       Decimal(12,2)   comment '结存数量',
  `MData_Own`          decimal(12,2)   comment  '应收款金额',
  `MData_Sum`          decimal(12,2)   comment  '收款金额',
  `MData_Type`          SmallInt    not null comment '结存类别 用整型值代表统计类型，例如1=阶段日结，2=日结，3=周结，4=月结等',
  `MData_Period`   SmallInt     comment '结存周期标志 在结存类别之上增加的标志，表明结存区间。比如阶段日结，午夜零点到一点期间的结存周期标志为1',
  `MData_Date`     datetime          not null comment '更新时间 数据记录的日期。如果数据记录时间超过午夜12点，则数据记录日期应该自动减一天',
  `MData_Time`     datetime      not null comment '记录时间',
  `MData_State`    smallint      not null  comment  '状态',
  `MData_Memo`    varchar(255)    not null  comment '更新时间',
  `MData_VarParamA`  varchar(255)  comment '字符备用字段',
  `MData_VarParamB`  varchar(255)  comment '字符备用字段',
  `MData_VarParamC`  varchar(255)  comment '字符备用字段',
  `MData_VarParamD`  varchar(255)  comment '字符备用字段',
  `MData_VarParamE`  varchar(255)  comment '字符备用字段',
  `MData_IntParamA`  bigint        comment '数字备用字段',
  `MData_IntParamB`  bigint        comment '数字备用字段',
  `MData_IntParamC`  bigint        comment '数字备用字段',
  `MData_IntParamD`  bigint        comment '数字备用字段',
  `MData_IntParamE`  bigint        comment '数字备用字段',
  `MData_FloatParamA` float        comment '浮点备用字段',
  `MData_FloatParamB` float        comment '浮点备用字段',
  `MData_FloatParamC` float        comment '浮点备用字段',
  `MData_FloatParamD` float        comment '浮点备用字段',
  `MData_FloatParamE` float        comment '浮点备用字段',
  PRIMARY KEY (`MData_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=90329 DEFAULT CHARSET=utf8;


CREATE TABLE `tapp_tableinfo` (
  `tableinfo_id` bigint  not null AUTO_INCREMENT ,
  `tableinfo_dealid` bigint not null,
  `tableinfo_typeid` bigint not null,
  `tableinfo_dealtype`bigint not null,
  `tableinfo_dealstate` bigint not null,
  `tableinfo_content` varchar(4000),
  `tableinfo_uptabid` bigint not null,
  `tableinfo_dealerid` bigint not null,
  `tableinfo_historytabid` bigint,
  `tableinfo_recorderid` bigint,
  `tableinfo_groupid` bigint,
  `tableinfo_date` datetime not null,
  `tableinfo_result` bigint not null,
  `tableinfo_tableno` varchar(200) not null,
  `tableinfo_memo` varchar(200),
  `tableinfo_varparama`  varchar(1024),
  `tableinfo_varparamb`  varchar(1024) ,
  `tableinfo_varparamc`  varchar(1024) ,
  `tableinfo_varparamd`  varchar(1024),
  `tableinfo_varparame`  varchar(1024),
  `tableinfo_intparama`  bigint,
  `tableinfo_intparamb`  bigint,
  `tableinfo_intparamc`  bigint,
  `tableinfo_intparamd`  bigint,
  `tableinfo_intparame`  bigint,
  `tableinfo_floatparama` float,
  `tableinfo_floatparamb` float,
  `tableinfo_floatparamc` float,
  `tableinfo_floatparamd` float,
  `tableinfo_floatparame` float,
  PRIMARY KEY (`tableinfo_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;






