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

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.InterruptableJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerDashboardGrid;
import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.data.RunnerDashboardItem.ItemType;
import binky.reportrunner.data.RunnerDashboardSampler;
import binky.reportrunner.data.sampling.SamplingData;
import binky.reportrunner.data.sampling.TrendData;

public class AlertProcessor implements Job, InterruptableJob {

	DataSource ds;

	ReportRunnerDao<RunnerDashboardItem, Integer> dashboardDao;
		Connection conn;
	private static final Logger logger = Logger.getLogger(AlertProcessor.class);

	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		// Grab the elements of the job from the context to pass on
		Integer itemId = (Integer)context.getJobDetail()
				.getJobDataMap().get("itemId");
		this.ds = (DataSource) context.getJobDetail().getJobDataMap().get(
				"dataSource");

		try {
			ds.setLogWriter(new PrintWriter(System.out));
		} catch (SQLException e1) {
			logger.warn(e1.getMessage(), e1);
		}

		this.dashboardDao = (ReportRunnerDao<RunnerDashboardItem, Integer>) context
				.getJobDetail().getJobDataMap().get("dashboardDao");
		
		
		try {
			if (itemId == null) {
				logger.fatal("item is null!");
				throw new Exception("item is null!");
			}
			if (ds == null) {
				logger.fatal("datasource is null!");
				throw new Exception("datasource is null!");
			}
			if (dashboardDao == null) {
				logger.fatal("dao is null!");
				throw new Exception("dao is null!");
			}
			//in the land of the hack here - all to do with collections, lazy initialising and 
			Session session=dashboardDao.openSession();
			RunnerDashboardItem item = dashboardDao.getInSession(itemId,session);
			try {
				if (item.getItemType() != ItemType.Sampler) {
					processQueryItem(item);
				} else {
					processSampler(item,session);
					
				}
			} finally{
				session.close();
			}
			
		} catch (Exception e) {
			throw new JobExecutionException(e);
		} 

	}

	private void processSampler(RunnerDashboardItem item, Session session) throws SQLException {

		String sql = item.getAlertQuery();
		conn = ds.getConnection();

		try {
			long start = item.getLastUpdated()!=null?item.getLastUpdated().getTime():0;
			logger.debug("running SQL for sampler");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			RunnerDashboardSampler sampler = (RunnerDashboardSampler)item;
			int period = Calendar.MINUTE;
			int amount=-1;
			
			//calculate the start of the window 
			switch (sampler.getWindow()) {
			case YEAR:
				period = Calendar.YEAR;
				break;
			case MONTH:
				period = Calendar.MONTH;
				break;
			case WEEK:
				period = Calendar.DAY_OF_YEAR;
				amount=-7;
				break;
			case DAY:
				period = Calendar.DAY_OF_YEAR;
				break;
			case HOUR:
				period = Calendar.HOUR;
				break;				
			}
			
			Calendar cal=Calendar.getInstance();
			Date now = cal.getTime();
			cal.add(period, amount);
			Date cutoff= cal.getTime();
			logger.trace("deleting entries older than " + cutoff);
			//first of all we need to clean out any that now fall outside of our window
			List<SamplingData> old = new LinkedList<SamplingData>();
			logger.trace("current sample size is :" + sampler.getData().size());
			Transaction trans = session.beginTransaction();
			for (SamplingData d: sampler.getData()) {
				logger.trace("testing entry for : " + d.getPk().getSampleTime());
				if (d.getPk().getSampleTime()<cutoff.getTime()) {
					old.add(d);
				}
			}
			for (SamplingData d:old) {
				logger.trace("deleting entry for : " + d.getPk().getSampleTime());
				sampler.getData().remove(d);
			}	
			
			
			//get the value - as this is a sampler we only grab the first row
			BigDecimal val= new BigDecimal(0);
			if (rs.next()) {
				val = rs.getBigDecimal(sampler.getValueColumn());							
			}
			sampler.getData().add(new SamplingData(sampler,now.getTime(),val));
			
			if (sampler.isRecordTrendData()) {

				//record trending data 
				SimpleDateFormat sdf;
				switch (sampler.getInterval()) {
				case DAY:				
					sdf = new SimpleDateFormat("EEEEE");
					break;
				case HOUR:					
					sdf = new SimpleDateFormat("HH");
					break;
				case MINUTE:
					sdf = new SimpleDateFormat("mm");
					break;
				case MONTH:
					sdf = new SimpleDateFormat("MMMMM");
					break;
				case SECOND:
				default:
					sdf = new SimpleDateFormat("ss");
				}
			
				String timeString = sdf.format(now);
				boolean found=false;
				TrendData t = new TrendData(sampler, timeString);
				for (TrendData d: sampler.getTrendData()) {
					if  (d.getPk().getTimeString().equals(timeString)) {
						t=d;
						found = true;
						break;
					}
				}
				
				
				
				if (found) {
					sampler.getTrendData().remove(t);
					BigDecimal newVal = new BigDecimal(((t.getMeanValue().doubleValue()* t.getSampleSize())+val.doubleValue()) /(t.getSampleSize()+1));
					t.setMeanValue(newVal);
					t.setSampleSize(t.getSampleSize()+1);
					if (val.doubleValue()>t.getMaxValue().doubleValue()) t.setMaxValue(val);
					if (val.doubleValue()<t.getMinValue().doubleValue()) t.setMinValue(val);					
				}else {					
					//create new entry					
					t.setMaxValue(val);
					t.setMeanValue(val);
					t.setMinValue(val);
					t.setSampleSize(1);
					if (sampler.getTrendData()==null) sampler.setTrendData(new LinkedList<TrendData>());					
				}
				sampler.getTrendData().add(t);
			}
		
			
		
			
			if (start >0) {							
				sampler.setVisualRefreshTime(now.getTime()-start);
			}
			sampler.setLastUpdated(now);
			session.saveOrUpdate(sampler);			
			for (SamplingData d:old) {
				session.delete(d);
			}
			
			trans.commit();
			session.flush();
			rs.close();
		} finally {
			if (!conn.isClosed())
				conn.close();
		}
	}

	private void processQueryItem(RunnerDashboardItem item) throws SQLException {

		String sql = item.getAlertQuery();
		conn = ds.getConnection();

		try {
			logger.debug("running SQL for item");
			Statement stmt = conn.createStatement();
			if (item.getItemType() == ItemType.Grid) {
				int rows = ((RunnerDashboardGrid) item).getRowsToDisplay();
				if (rows > 0) {
					logger.debug("limiting grid result to " + rows + " rows");
					stmt.setFetchSize(rows);
					stmt.setMaxRows(rows);
				}
			}
			ResultSet rs = stmt.executeQuery(sql);
			RowSetDynaClass rsdc = new RowSetDynaClass(rs, false);
			if (logger.isDebugEnabled()) {
				logger.debug("record set size: " + rsdc.getRows().size());
				for (DynaProperty col : rsdc.getDynaProperties()) {
					logger.debug("found column: " + col.getName() + " of type "
							+ col.getType().getName());
				}
			}
			item.setCurrentDataset(rsdc);
			item.setLastUpdated(Calendar.getInstance().getTime());
			dashboardDao.saveOrUpdate(item);
			rs.close();
		} finally {
			if (!conn.isClosed())
				conn.close();
		}

	}

	public void interrupt() throws UnableToInterruptJobException {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			throw new UnableToInterruptJobException(e);
		}
	}

}
