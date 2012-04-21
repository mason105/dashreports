package binky.reportrunner.service.impl;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.data.RunnerDashboardSampler;
import binky.reportrunner.data.RunnerDashboardSampler.Interval;
import binky.reportrunner.data.RunnerDashboardSampler.Window;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.scheduler.SchedulerException;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.GroupService;

public class DashboardServiceImplTest extends TestCase {
	
	GroupService groupService;
	private RunnerGroup group;
	DatasourceService dss;
	private RunnerDataSource ds;
	private RunnerDashboardSampler i;
	private DashboardService dash;
	private Integer id;
	protected void setUp() throws Exception {


		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		dss = (DatasourceService)ctx.getBean("runnerDatasourceService");
		groupService = (GroupService)ctx.getBean("groupService");
		dash=(DashboardService)ctx.getBean("dashboardService");
		
		group = new RunnerGroup();
		group.setGroupName(getUID());
		groupService.saveOrUpdate(group);
		
		
		ds = new RunnerDataSource();
		ds.setDataSourceName(getUID());
		List<RunnerGroup> groups = new LinkedList<RunnerGroup>();
		groups.add(group);
		ds.setGroups(groups);

		//per environment
		ds.setJdbcClass("com.mysql.jdbc.Driver");
		ds.setJdbcUrl("jdbc:mysql://localhost:3306/dashtest"); //dashtest
		ds.setUsername("dashtest");  //dashtest
		ds.setPassword("password"); 
		
		dss.saveUpdateDataSource(ds);

		i = new RunnerDashboardSampler();
		i.setDatasource(ds);
		i.setGroup(group);
		i.setAlertQuery("select rand(1000) val from dual");
		i.setInterval(Interval.MONTH);
		i.setRecordTrendData(true);
		i.setValueColumn("val");
		i.setWindow(Window.MONTH);
		
		//pop the id back in so we can track it
		id = dash.saveUpdateItem(i);
		i.setItemId(id);
		
	}

	protected void tearDown() throws Exception {
		dash.deleteItem(id);
		dss.deleteDataSource(ds.getDataSourceName());
		groupService.delete(group.getGroupName());
	}

	private String getUID() {
		return UUID.randomUUID().toString();
	}

	public void testGetItem() {
	 assertNotNull(dash.getItem(id));
	}

	public void testDeleteItem() {
		try {
			dash.deleteItem(id);
		} catch (SchedulerException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertNull(dash.getItem(id));
	}

	public void testGetItemsForGroup() {
		assertTrue(dash.getItemsForGroup(group.getGroupName()).size()>0);
	}

	public void testGetAllItems() {
		assertTrue(dash.getAllItems().size()>0);
	}

	public void testInvokeDashboardItem() {	
		try {
			dash.invokeDashboardItem(id);
		} catch (SchedulerException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testProcessDashboardItem() {
		try {
			dash.processDashboardItem(id);
		} catch (SQLException e) {			
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testClearTrendData() {
		dash.clearTrendData(id);
	}

}
