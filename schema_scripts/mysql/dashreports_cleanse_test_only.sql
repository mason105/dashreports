/* cleanse dash reports - WARNING this will trash your schema - leaving only basic config */

/* this was meant only for cleaning up test environments */

SET FOREIGN_KEY_CHECKS = 0;

delete from T_GROUP;
delete from T_ITEM;
delete from T_GROUP_T_ITEM;
delete from T_JOB;
delete from T_GROUP_T_JOB;
delete from T_EVENT;
delete from T_CHART;
delete from T_CONFIG;
delete from T_DATASOURCE;
delete from T_DATASOURCE_T_GROUP;
delete from T_D_DATA;
delete from T_S_DATA;
delete from T_SAMPLING;
delete from T_T_DATA;
delete from T_THRESHOLD;
delete from T_GRID;
delete from T_USER_T_GROUP;
delete from T_USER;

delete from QRTZ_JOB_LISTENERS;
delete from QRTZ_TRIGGER_LISTENERS;
delete from QRTZ_FIRED_TRIGGERS;
delete from QRTZ_PAUSED_TRIGGER_GRPS;
delete from QRTZ_SCHEDULER_STATE;
delete from QRTZ_LOCKS;
delete from QRTZ_SIMPLE_TRIGGERS;
delete from QRTZ_CRON_TRIGGERS;
delete from QRTZ_BLOB_TRIGGERS;
delete from QRTZ_TRIGGERS;
delete from QRTZ_JOB_DETAILS;
delete from QRTZ_CALENDARS;

INSERT INTO QRTZ_LOCKS values('TRIGGER_ACCESS');
INSERT INTO QRTZ_LOCKS values('JOB_ACCESS');
INSERT INTO QRTZ_LOCKS values('CALENDAR_ACCESS');
INSERT INTO QRTZ_LOCKS values('STATE_ACCESS');
INSERT INTO QRTZ_LOCKS values('MISFIRE_ACCESS');

/* create an admin user with the password of password user:admin*/ 
insert into T_USER values ('admin','admin',1,0,0,'5f4dcc3b5aa765d61d8327deb882cf99');

/* create a test user with the password of password user:test*/ 
insert into T_USER values ('test','Test User',0,0,0,'5f4dcc3b5aa765d61d8327deb882cf99');

/* create an example group to make the menu populated */
insert into T_GROUP values('Example Group','Please delete me if not needed');

/* link the test user to the example group - admins can see all groups */
insert into T_USER_T_GROUP values('test','Example Group');

/* populate some default configuration values */
insert into T_CONFIG values(0,null,'dashreports@yourdomain.com');
insert into T_CONFIG values(1,null,'smtp.yourdomain.com');
insert into T_CONFIG values(2,null,'7');
insert into T_CONFIG values(3,null,'Dash Reports User');
insert into T_CONFIG values(4,null,'admin@yourdomain.com');

commit;

SET FOREIGN_KEY_CHECKS = 1;