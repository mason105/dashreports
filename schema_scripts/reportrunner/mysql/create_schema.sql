
DROP TABLE IF EXISTS `reportrunner`.`runnerdatasource`;
CREATE TABLE  `reportrunner`.`runnerdatasource` (
  `dataSourceName` varchar(255) NOT NULL,
  `initialPoolSize` int(11) DEFAULT NULL,
  `jdbcClass` varchar(255) DEFAULT NULL,
  `jdbcUrl` varchar(255) DEFAULT NULL,
  `jndiName` varchar(255) DEFAULT NULL,
  `maxPoolSize` int(11) DEFAULT NULL,
  `minPoolSize` int(11) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dataSourceName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `reportrunner`.`runneruser`;
CREATE TABLE  `reportrunner`.`runneruser` (
  `userName` varchar(255) NOT NULL,
  `fullName` varchar(255) DEFAULT NULL,
  `isAdmin` tinyint(3) unsigned zerofill DEFAULT NULL,
  `isLocked` tinyint(3) unsigned zerofill DEFAULT NULL,
  `isReadOnly` tinyint(3) unsigned zerofill DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `reportrunner`.`runnergroup`;
CREATE TABLE  `reportrunner`.`runnergroup` (
  `groupName` varchar(255) NOT NULL,
  `groupDescription` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`groupName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `reportrunner`.`runnergroup_runneruser`;
CREATE TABLE  `reportrunner`.`runnergroup_runneruser` (
  `RunnerGroup_groupName` varchar(255) NOT NULL,
  `users_userName` varchar(255) NOT NULL,
  KEY `FK6EC21C6BB4A88336` (`RunnerGroup_groupName`),
  KEY `FK6EC21C6B5001AF3B` (`users_userName`),
  CONSTRAINT `FK6EC21C6B5001AF3B` FOREIGN KEY (`users_userName`) REFERENCES `runneruser` (`userName`),
  CONSTRAINT `FK6EC21C6BB4A88336` FOREIGN KEY (`RunnerGroup_groupName`) REFERENCES `runnergroup` (`groupName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `reportrunner`.`runnerhistoryevent`;
CREATE TABLE  `reportrunner`.`runnerhistoryevent` (
  `eventId` bigint(20) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(255) DEFAULT NULL,
  `jobName` varchar(255) DEFAULT NULL,
  `message` text,
  `runTime` bigint(20) DEFAULT NULL,
  `success` bit(1) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  PRIMARY KEY (`eventId`)
) ENGINE=InnoDB AUTO_INCREMENT=59641 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `reportrunner`.`runnerjob`;
CREATE TABLE  `reportrunner`.`runnerjob` (
  `jobName` varchar(255) NOT NULL,
  `alertEmailAddress` varchar(255) DEFAULT NULL,
  `alertOnSuccess` tinyint(3) unsigned NOT NULL,
  `burstFileNameParameterName` varchar(255) DEFAULT NULL,
  `burstQuery` text,
  `cronString` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `fileFormat` int(11) DEFAULT NULL,
  `isBurst` tinyint(3) unsigned DEFAULT NULL,
  `outputUrl` varchar(255) DEFAULT NULL,
  `query` text,
  `startDate` datetime DEFAULT NULL,
  `targetEmailAddress` varchar(255) DEFAULT NULL,
  `templateFile` tinyblob,
  `templateType` int(11) DEFAULT NULL,
  `group_groupName` varchar(255) NOT NULL DEFAULT '',
  `datasource_dataSourceName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`group_groupName`,`jobName`),
  KEY `FK743BD64D1FB5A866` (`group_groupName`),
  KEY `FK743BD64D6235B7F2` (`datasource_dataSourceName`),
  CONSTRAINT `FK743BD64D1FB5A866` FOREIGN KEY (`group_groupName`) REFERENCES `runnergroup` (`groupName`),
  CONSTRAINT `FK743BD64D6235B7F2` FOREIGN KEY (`datasource_dataSourceName`) REFERENCES `runnerdatasource` (`dataSourceName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `reportrunner`.`runnerjobparameter`;
CREATE TABLE  `reportrunner`.`runnerjobparameter` (
  `parameterIdx` int(11) NOT NULL,
  `jobName` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  `parameterBurstColumn` varchar(255) DEFAULT NULL,
  `parameterType` int(11) DEFAULT NULL,
  `parameterValue` varchar(255) DEFAULT NULL,
  `group_groupName` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`parameterIdx`,`group_groupName`,`jobName`),
  KEY `FKE8BCC3DCCFCF20AC` (`group_groupName`,`jobName`),
  KEY `FKE8BCC3DC1FB5A866` (`group_groupName`),
  CONSTRAINT `FKE8BCC3DC1FB5A866` FOREIGN KEY (`group_groupName`) REFERENCES `runnergroup` (`groupName`),
  CONSTRAINT `FKE8BCC3DCCFCF20AC` FOREIGN KEY (`group_groupName`, `jobName`) REFERENCES `runnerjob` (`group_groupName`, `jobName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `reportrunner`.`runnergroup_runnerjob`;
CREATE TABLE  `reportrunner`.`runnergroup_runnerjob` (
  `RunnerGroup_groupName` varchar(255) NOT NULL,
  `runnerJobs_group_groupName` varchar(255) NOT NULL,
  `runnerJobs_jobName` varchar(255) NOT NULL,
  UNIQUE KEY `runnerJobs_group_groupName` (`runnerJobs_group_groupName`,`runnerJobs_jobName`),
  KEY `FKB0FDD71DD93AB23A` (`runnerJobs_group_groupName`,`runnerJobs_jobName`),
  KEY `FKB0FDD71DB4A88336` (`RunnerGroup_groupName`),
  CONSTRAINT `FKB0FDD71DB4A88336` FOREIGN KEY (`RunnerGroup_groupName`) REFERENCES `runnergroup` (`groupName`),
  CONSTRAINT `FKB0FDD71DD93AB23A` FOREIGN KEY (`runnerJobs_group_groupName`, `runnerJobs_jobName`) REFERENCES `runnerjob` (`group_groupName`, `jobName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `reportrunner`.`runneruser_runnergroup`;
CREATE TABLE  `reportrunner`.`runneruser_runnergroup` (
  `RunnerUser_userName` varchar(255) NOT NULL,
  `groups_groupName` varchar(255) NOT NULL,
  KEY `FKFDED2E0B4782257B` (`groups_groupName`),
  KEY `FKFDED2E0BE5066BA8` (`RunnerUser_userName`),
  CONSTRAINT `FKFDED2E0B4782257B` FOREIGN KEY (`groups_groupName`) REFERENCES `runnergroup` (`groupName`),
  CONSTRAINT `FKFDED2E0BE5066BA8` FOREIGN KEY (`RunnerUser_userName`) REFERENCES `runneruser` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `reportrunner`.`runnerdashboardalert`;
CREATE TABLE  `reportrunner`.`runnerdashboardalert` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alertName` varchar(255) DEFAULT NULL,
  `alertQuery` text,
  `chartType` int(11) DEFAULT NULL,
  `cronTab` varchar(255) DEFAULT NULL,
  `currentDataset` blob,
  `displayType` int(11) DEFAULT NULL,
  `lastUpdated` datetime DEFAULT NULL,
  `xaxisColumn` varchar(255) DEFAULT NULL,
  `datasource_dataSourceName` varchar(255) DEFAULT NULL,
  `group_groupName` varchar(255) DEFAULT NULL,
  `displayRow` int(11) DEFAULT NULL,
  `seriesNameColumn` varchar(255) DEFAULT NULL,
  `valueColumn` varchar(255) DEFAULT NULL,
  `chartSize` int(11) DEFAULT NULL,
  `yLabel` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9F5B6981FB5A866` (`group_groupName`),
  KEY `FK9F5B6986235B7F2` (`datasource_dataSourceName`),
  CONSTRAINT `FK9F5B6981FB5A866` FOREIGN KEY (`group_groupName`) REFERENCES `runnergroup` (`groupName`),
  CONSTRAINT `FK9F5B6986235B7F2` FOREIGN KEY (`datasource_dataSourceName`) REFERENCES `runnerdatasource` (`dataSourceName`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
