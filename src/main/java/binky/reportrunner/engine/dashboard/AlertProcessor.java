/*******************************************************************************
 * Copyright (c) 2009 Daniel Grout.
 * 
 * GNU GENERAL PUBLIC LICENSE - Version 3
 * 
 * This file is part of Report Runner (http://code.google.com/p/reportrunner).
 * 
 * Report Runner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Runner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Runner. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Module: AlertProcessor.java
 ******************************************************************************/
package binky.reportrunner.engine.dashboard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.sql.DataSource;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import binky.reportrunner.dao.RunnerDashboardAlertDao;
import binky.reportrunner.data.RunnerDashboardAlert;

public class AlertProcessor implements Job, InterruptableJob {

	DataSource ds;

	RunnerDashboardAlertDao dashboardDao;
	Connection conn;
	private static final Logger logger = Logger.getLogger(AlertProcessor.class);

	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		// Grab the elements of the job from the context to pass on
		RunnerDashboardAlert alert = (RunnerDashboardAlert) context
				.getJobDetail().getJobDataMap().get("alert");

		this.ds = (DataSource) context.getJobDetail().getJobDataMap().get(
				"dataSource");
		
		
		this.dashboardDao = (RunnerDashboardAlertDao) context.getJobDetail()
				.getJobDataMap().get("dashboardDao");
		try {
			if (alert == null) {
				logger.fatal("alert is null!");
				throw new Exception("alert is null!");
			}
			if (ds == null) {
				logger.fatal("datasource is null!");
				throw new Exception("datasource is null!");
			}
			if (dashboardDao == null) {
				logger.fatal("dao is null!");
				throw new Exception("dao is null!");
			}

			processAlert(alert);
			
		} catch (Exception e) {
			throw new JobExecutionException(e);
		}

	}
	
	private void processAlert(RunnerDashboardAlert alert) throws SQLException {
		
		String sql = alert.getAlertQuery();
		conn = ds.getConnection();
		
		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);
			alert.setCurrentDataset(new RowSetDynaClass(rs,false));
			alert.setLastUpdated(Calendar.getInstance().getTime());
			dashboardDao.saveUpdateAlert(alert);
			rs.close();
		} finally {
			if (!conn.isClosed()) conn.close();
		}
		
		
	}

	public void interrupt() throws UnableToInterruptJobException {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new UnableToInterruptJobException(e);
		}
	}

}
