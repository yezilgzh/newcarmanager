--数据库安装脚本，此脚本将在studio第一次启动时自动执行

--数据库版本(升级)信息
DROP TABLE dbversion IF EXISTS;
CREATE TABLE dbversion  (
   dbversionkey       IDENTITY                        NOT NULL,
   versionnum         VARCHAR(32)                     NOT NULL,
   PATH               VARCHAR(2048)                   NOT NULL,
   DESC               VARCHAR(64),
   createtime         TIMESTAMP                       NOT NULL,
   CONSTRAINT pk_dbversion PRIMARY KEY (dbversionkey)
);

--用户
DROP TABLE userinfo IF EXISTS;	
CREATE TABLE userinfo (
   userkey           VARCHAR(16)   NOT NULL,
   username          VARCHAR(16)   NOT NULL,
   password          VARCHAR(128)  NOT NULL,
   name              VARCHAR(64),
   role              VARCHAR(1)    NOT NULL,
   CONSTRAINT pk_userinfo PRIMARY KEY (userkey),
   CONSTRAINT un_username UNIQUE(username)
);

INSERT INTO userinfo(userkey, username, password, name, role) VALUES('u001', 'admin', 'E5EC507FA14C1126', '管理员', '9');
INSERT INTO userinfo(userkey, username, password, name, role) VALUES('u002', 'shsim', '63051506F9118E78', '炒手shsim', '1');

 

--车主信息历史表
CREATE TABLE caruser 
(
   id                               IDENTITY                       NOT NULL,
   carno                            VARCHAR(16)                    NOT NULL,
   name                             VARCHAR(16)                    			,
   cartype                         	VARCHAR(32)                    			,
   color                    		VARCHAR(32)                    			,
   phone                         	VARCHAR(32)                    			,
   sex                         		INT                  					,
   age                         		INT                  					,
   createtime                       TIMESTAMP                      NOT NULL,
   remark                          	VARCHAR(64)                    			,
   PRIMARY KEY(id),
   CONSTRAINT un_hiscarno UNIQUE(carno)
);

--进店表
CREATE TABLE instorerecord 
(
   id                                IDENTITY                      NOT NULL,
   intime                            TIMESTAMP                	   NOT NULL,  
   caruserkey      					 VARCHAR(16)                   NOT NULL,
   remark                          	 VARCHAR(64)                    		,
   PRIMARY KEY(id),
   CONSTRAINT fk_caruser_record FOREIGN KEY(caruserkey) REFERENCES caruser (id) ON DELETE CASCADE 
);

--进店表
CREATE TABLE his_instorerecord
(
   id                                IDENTITY                        NOT NULL,
   intime                            TIMESTAMP                		 NOT NULL,  
   caruserkey      					 VARCHAR(16)     				 NOT NULL,
   remark                          	 VARCHAR(64)                    		 ,
   PRIMARY KEY(id),
   CONSTRAINT fk_his_caruser_record FOREIGN KEY(caruserkey) REFERENCES caruser (id) ON DELETE CASCADE 
);

--保养项目表
CREATE TABLE  project 
(
   id                               IDENTITY                       NOT NULL,
   projectname                      VARCHAR(32)                    NOT NULL,
   projectcost                      INT                      	   NOT NULL,
   remark                           VARCHAR(64)                    			,
   recordkey						VARCHAR(16)					   NOT NULL,
   PRIMARY KEY(id),
   CONSTRAINT fk_project_record FOREIGN KEY(recordkey) REFERENCES instorerecord (id) ON DELETE CASCADE 
);
--颜色表
CREATE TABLE  color 
(
   id                               IDENTITY                       NOT NULL,
   color                      		VARCHAR(16)                    NOT NULL,
   PRIMARY KEY(id),
   CONSTRAINT un_color UNIQUE(color)
);
INSERT INTO color(id, color) VALUES(null,'黑色');
INSERT INTO color(id, color) VALUES(null,'白色');
INSERT INTO color(id, color) VALUES(null,'灰色');
INSERT INTO color(id, color) VALUES(null,'红色');
INSERT INTO color(id, color) VALUES(null,'黄色');
INSERT INTO color(id, color) VALUES(null,'蓝色');
INSERT INTO color(id, color) VALUES(null,'褐色');

--保养项目表
CREATE TABLE  projectitem 
(
   id                               IDENTITY                       NOT NULL,
   name                      		VARCHAR(32)                    NOT NULL,
   cost                      		INT                      	   NOT NULL,
   desc                             VARCHAR(64)                    			,
   PRIMARY KEY(id),
   CONSTRAINT un_item UNIQUE(name)
);
INSERT INTO projectitem(id,name,cost,desc) VALUES(null,'洗车',20,'');
INSERT INTO projectitem(id,name,cost,desc) VALUES(null,'封釉',20,'');




