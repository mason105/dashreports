package binky.reportrunner.engine.dashboard;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import binky.reportrunner.dao.RunnerDashboardAlertDao;
import binky.reportrunner.data.RunnerDashboardAlert;

public class AlertProcessor implements Job {

	DataSource ds;

	RunnerDashboardAlertDao dashboardDao;

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
		Connection conn = ds.getConnection();
		
		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);
			alert.setCurrentDataset(new RowSetDynaClass(rs,false));
			
			dashboardDao.saveUpdateAlert(alert);
			rs.close();
		} finally {
			conn.close();
		}
		
		
	}

}
