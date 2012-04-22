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
 * Module: DashboardServiceImpl.java
 ******************************************************************************/
package binky.reportrunner.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.DashboardData;
import binky.reportrunner.data.RunnerDashboardGrid;
import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.data.RunnerDashboardItem.ItemType;
import binky.reportrunner.data.RunnerDashboardSampler;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.sampling.SamplingData;
import binky.reportrunner.data.sampling.TrendData;
import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.scheduler.SchedulerException;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.service.DatasourceService;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

public class DashboardServiceImpl implements DashboardService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8560022031641311268L;


	private static final Logger logger = Logger.getLogger(DashboardServiceImpl.class);
	

	private DatasourceService datasourceService;
	
	private ReportRunnerDao<RunnerDashboardItem,Integer> dashboardDao;
	private ReportRunnerDao<RunnerGroup,String> groupDao;
	private Scheduler scheduler;

	public RunnerDashboardItem getItem(Integer id) {
		return dashboardDao.get(id);
	}
	@TriggersRemove(cacheName="dashboardCache")
	public void deleteItem(Integer id) throws SchedulerException {
		dashboardDao.delete(id);
		scheduler.removedDashboardAlert(id);		
	}
	@Cacheable(cacheName="dashboardCache")
	public List<RunnerDashboardItem> getItemsForGroup(String groupName) {
		List<RunnerDashboardItem> as = dashboardDao.findByNamedQuery("getItemsByGroup", new String[]{groupName});			
		List<RunnerDashboardItem> alerts=new LinkedList<RunnerDashboardItem>();
		
		//temp hack
		for (RunnerDashboardItem a: as) 
		{
			long visualRefreshTime=60000;

			try {
				Date last = scheduler.getPreviousRunTime(a.getItemId());
				Date next = scheduler.getNextRunTime(a.getItemId());
				if ((last!=null) && (next!=null) && (last.getTime()<next.getTime())) {
					visualRefreshTime=(next.getTime()-last.getTime());
				}				
			} catch (SchedulerException e) {
				logger.error(e.getMessage(),e);
			}		
			
			a.setVisualRefreshTime(visualRefreshTime);
			alerts.add(a);
			
		}
		return alerts;
		
		
	}

	@Cacheable(cacheName="dashboardCache")
	public List<RunnerDashboardItem> getAllItems() {
		return dashboardDao.getAll();
	}

	public Integer saveUpdateItem(RunnerDashboardItem alert) throws SchedulerException {
		if (alert.getItemId()!=null){
			scheduler.removedDashboardAlert(alert.getItemId());
		}
		Integer ret;
		//hack to try to fix a batch update error		
		RunnerGroup group = groupDao.get(alert.getGroup().getGroupName());
		alert.setGroup(group);
		
		//hack to deal with interval change
		if ((alert.getItemType()==ItemType.Sampler) && (alert.getItemId()!=null)) {
			
				RunnerDashboardSampler s=(RunnerDashboardSampler)alert;		

				RunnerDashboardSampler comp = (RunnerDashboardSampler)dashboardDao.get(alert.getItemId());
				s.setData(comp.getData());
				s.setTrendData(comp.getTrendData());

				//need to do a compare
				if (s.isRecordTrendData() && !s.getInterval().equals(comp.getInterval())&& s.getTrendData()!=null) {
						s.getTrendData().clear();																		
				}
						
			logger.debug("saving sampler");
			ret=dashboardDao.saveOrUpdate(s);
		} else {
			logger.debug("saving alert");
			ret=dashboardDao.saveOrUpdate(alert);
		}
					
		scheduler.addDashboardAlert(alert.getItemId(),alert.getCronTab());
		return ret;
	}
	public void setDashboardDao(ReportRunnerDao<RunnerDashboardItem,Integer> dashboardDao) {
		this.dashboardDao = dashboardDao;
	}
	public Scheduler getScheduler() {
		return scheduler;
	}
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	@Cacheable(cacheName="dashboardCache")
	public List<RunnerDashboardItem> getRunningItems() {
		List<String> runningJobs = scheduler.getCurrentRunningJobs();
		List<RunnerDashboardItem> alerts = new LinkedList<RunnerDashboardItem>();
		for (String string : runningJobs) {
			String groupName = string.split(":|:")[0];
			if (groupName.equals(Scheduler.dashboardSchedulerGroup)) {
				logger.debug("job name: " + string);
				Integer id = Integer.parseInt(string.split(":|:")[2]);
				RunnerDashboardItem alert = dashboardDao.get(id);
				alerts.add(alert);
			}			
		}
		return alerts;
	}
	
	public void interruptRunningDashboardItem(Integer alertId)
	throws SchedulerException {
		logger.debug("interrupt alert: " + alertId);		
		scheduler.interruptRunningDashboardAlert(alertId);		
	}
	
	public void invokeDashboardItem(Integer itemId) throws SchedulerException {
		scheduler.invokeDashboardItem(itemId);
		
	}

	public void setGroupDao(ReportRunnerDao<RunnerGroup,String> groupDao)  {
		this.groupDao = groupDao;
	}
	@Override
	public void processDashboardItem(int itemId)throws SQLException {
		RunnerDashboardItem item = dashboardDao.get(itemId);
		if (item!=null) {
		switch (item.getItemType()) {
		case Sampler:
			processSampler(item);
			break;
		default:
			processQueryItem(item);
		}
		}
		else {
		logger.warn("null item found for item id: " + itemId);	
		}
		
	}
	
	private void processSampler(RunnerDashboardItem item) throws SQLException {
		DataSource ds = datasourceService.getJDBCDataSource(item.getDatasource()); 
		String sql = item.getAlertQuery();
		Connection conn = ds.getConnection();

		try {
			long start = item.getLastUpdated()!=null?item.getLastUpdated().getTime():0;
			logger.trace("running SQL for sampler");
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
			
			//get the value - as this is a sampler we only grab the first row
			BigDecimal val= BigDecimal.ZERO;
			if (rs.next()) {
				val = rs.getBigDecimal(sampler.getValueColumn());							
			}
			rs.close();

			sampler.getSamplingData().add(new SamplingData(sampler,now.getTime(),val));
			
						
			
			
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
					if  (d.getTimeString().equals(timeString)) {
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
					if (sampler.getTrendData()==null) sampler.setTrendData(new TreeSet<TrendData>());					
				}
				sampler.getTrendData().add(t);
			}
		
			
		
			
			if (start >0) {							
				sampler.setVisualRefreshTime(now.getTime()-start);
			}
			sampler.setLastUpdated(now);
		
			logger.trace("deleting entries older than " + cutoff);
			//first of all we need to clean out any that now fall outside of our window
			logger.trace("current sample size is :" + sampler.getData().size());
			
			for (Iterator<SamplingData> it=sampler.getSamplingData().iterator();it.hasNext();) {
				SamplingData d = it.next();
				if (d.getSampleTime()<cutoff.getTime()) it.remove();
			}
			
			dashboardDao.saveOrUpdate(sampler);	

			
		} finally {
			if (!conn.isClosed())
				conn.close();
		}
	}

	private void processQueryItem(RunnerDashboardItem item) throws SQLException {

		DataSource ds = datasourceService.getJDBCDataSource(item.getDatasource()); 
		String sql = item.getAlertQuery();
		Connection conn = ds.getConnection();

		try {
			logger.trace("running SQL for item");
			Statement stmt = conn.createStatement();
			if (item.getItemType() == ItemType.Grid) {
				int rows = ((RunnerDashboardGrid) item).getRowsToDisplay();
				if (rows > 0) {
					logger.trace("limiting grid result to " + rows + " rows");
					stmt.setFetchSize(rows);
					stmt.setMaxRows(rows);
				}
			}
			ResultSet rs = stmt.executeQuery(sql);
			Set<DashboardData> data = new LinkedHashSet<DashboardData>();
			int colCount=rs.getMetaData().getColumnCount();
			ResultSetMetaData meta=rs.getMetaData();
			Date now = Calendar.getInstance().getTime();
			int rowNumber=0;
			while (rs.next()) {
				rowNumber++;
				for (int i=1;i<=colCount;i++) {					
					Object value;
					switch (meta.getColumnType(i)) {
					case Types.TIME:
					case Types.TIMESTAMP:
					case Types.DATE:
						value=Long.valueOf(rs.getDate(i).getTime());
						break;	
					default:
						value=rs.getObject(i);
					}
					data.add(new DashboardData(item, now, meta.getColumnName(i), value.toString(), meta.getColumnType(i),rowNumber));
				}
			}
			rs.close();
			item.setData(data);			
			item.setLastUpdated(now);
			dashboardDao.saveOrUpdate(item);			
		} finally {
			if (!conn.isClosed())
				conn.close();
		}

	}

	public void setDatasourceService(DatasourceService datasourceService) {
		this.datasourceService = datasourceService;
	}
	@Override
	public void clearTrendData(int itemId) {
		
		RunnerDashboardItem item = dashboardDao.get(itemId);
		
		if (item!=null&&item.getItemType()==ItemType.Sampler) {
			RunnerDashboardSampler s = (RunnerDashboardSampler)item;
			if (s.isRecordTrendData()) {
				s.getTrendData().clear();
				dashboardDao.saveOrUpdate(s);
			}
		}
		
	}

}
