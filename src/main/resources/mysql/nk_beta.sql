
   -- lishaomiao 8.12号 该数据库脚本在tapp_problemparts的表上
ALTER TABLE tapp_problemparts ADD (problemparts_details VARCHAR(20));
ALTER TABLE tapp_problemparts ADD (problemparts_expressvalue VARCHAR(20));
ALTER TABLE tapp_problemparts ADD (problemparts_expressdate VARCHAR(20));

   -- lishaomiao 8.13号 该数据库脚本在tapp_message的表上
ALTER TABLE tapp_message ADD (express_number bigint(20));
ALTER TABLE tapp_message ADD (problem_parts_type SMALLINT(20));

-- sunjiajian 8.14号 系统日志表
   CREATE TABLE `system_log` (
     `Id` int(11) NOT NULL AUTO_INCREMENT,
     `create_time` varchar(255) DEFAULT '' COMMENT '创建时间',
     `content` text COMMENT '日志的文本',
     `express_number` varchar(255) DEFAULT '' COMMENT '运单号,搜索条件',
     PRIMARY KEY (`Id`)
   ) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='系统日志表，便于跟踪';

   -- lishaomiao 8.16号 该数据库脚本在tapp_problemparts表上
ALTER TABLE tapp_problemparts ADD (problemparts_tasktype INT(5)) COMMENT '任务进行中=0，结束=1';
ALTER TABLE tapp_problemparts modify problemparts_details VARCHAR(255);
ALTER TABLE tapp_problemparts modify problemparts_expressvalue VARCHAR(255);
ALTER TABLE tapp_problemparts modify problemparts_expressdate VARCHAR(255);
ALTER TABLE tapp_problemparts modify problemparts_varparamd VARCHAR(255);
ALTER TABLE tapp_problemparts modify problemparts_varparame VARCHAR(255);
ALTER TABLE tapp_problemparts modify problemparts_varparamf VARCHAR(255);

-- sunjiajian 8.16号 在tapp_problemparts 加入新字段
ALTER TABLE tapp_problemparts ADD problemparts_finish varchar(255) DEFAULT '' COMMENT '问题件在什么情况下结束的消息语';

 -- lishaomiao 8.17号 该数据库脚本在tapp_tableinfo表上
ALTER TABLE tapp_tableinfo ADD tableinfo_varparamvalue VARCHAR(255);
ALTER TABLE tapp_tableinfo ADD tableinfo_varparamgdate VARCHAR(20);
