
/* Insert user roles */
Insert into USER_ROLE (ROLE_ID,RL_NAM,RL_SHRT_NAM,RL_LVL,RL_DESC) values (1,'ROLE_ADMIN','Admin',1,'Admin');
Insert into USER_ROLE (ROLE_ID,RL_NAM,RL_SHRT_NAM,RL_LVL,RL_DESC) values (2,'ROLE_USER','User',2,'User');
Insert into USER_ROLE (ROLE_ID,RL_NAM,RL_SHRT_NAM,RL_LVL,RL_DESC) values (3,'ROLE_EXT_APP','External Application',3,'External Application');
Insert into USER_ROLE (ROLE_ID,RL_NAM,RL_SHRT_NAM,RL_LVL,RL_DESC) values (4,'ROLE_TASK_APPROVER','Task Approver',4,'Task Approver');
Insert into USER_ROLE (ROLE_ID,RL_NAM,RL_SHRT_NAM,RL_LVL,RL_DESC) values (5,'ROLE_TASK_PROCESSOR','Task Processor',5,'Task Processor');
Insert into USER_ROLE (ROLE_ID,RL_NAM,RL_SHRT_NAM,RL_LVL,RL_DESC) values (6,'ROLE_RAW_USER','Hunter Raw Message Receiver User',6,'Hunter Raw Message Receiver User');
Insert into USER_ROLE (ROLE_ID,RL_NAM,RL_SHRT_NAM,RL_LVL,RL_DESC) values (7,'ROLE_EML_TMPLT_ROLE_USER','Hunter Email Template Role',7,'Hunter Email Template Role');
Insert into USER_ROLE (ROLE_ID,RL_NAM,RL_SHRT_NAM,RL_LVL,RL_DESC) values (8,'HUNTR_ASSGNBL_USER_ROLE','Hunter Assignable User Role',8,'Hunter Assignable User Role');


/* Insert user service providers */
Insert into SRVC_PRVDR (PROVIDER_ID,PROVIDER_NAM,CST_PR_AUDIO_MSG,CST_PR_TXT_MSG) values (1,'Safaricom',2,1);
Insert into SRVC_PRVDR (PROVIDER_ID,PROVIDER_NAM,CST_PR_AUDIO_MSG,CST_PR_TXT_MSG) values (2,'Celtel',3,2);