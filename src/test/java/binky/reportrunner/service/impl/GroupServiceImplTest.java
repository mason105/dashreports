package binky.reportrunner.service.impl;

import java.util.Calendar;
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
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJob_pk;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.GroupService;
import binky.reportrunner.service.ReportService;
import binky.reportrunner.service.UserService;

public class GroupServiceImplTest extends TestCase {
	GroupService groupService;
	private RunnerGroup group;
	private ReportService reportService;
	private DashboardService dash;
	private UserService userService;
	private RunnerUser u;
	
	DatasourceService dss;
	private RunnerDataSource ds;
	protected void setUp() throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		reportService = (ReportService)ctx.getBean("runnerJobService");
		groupService = (GroupService) ctx.getBean("groupService");
		dash=(DashboardService)ctx.getBean("dashboardService");
		
		userService= (UserService) ctx.getBean("userService");
		dss = (DatasourceService)ctx.getBean("runnerDatasourceService");
		group = new RunnerGroup();
		group.setGroupName(getUID());
		groupService.saveOrUpdate(group);
		reportService.addUpdateJob(getTestJob());
		
		
		
		RunnerDashboardSampler i = new RunnerDashboardSampler();
		i.setGroup(group);
		i.setAlertQuery("select rand(1000) val from dual");
		i.setInterval(Interval.MONTH);
		i.setRecordTrendData(true);
		i.setValueColumn("val");
		i.setWindow(Window.MONTH);
		
		//pop the id back in so we can track it
		 dash.saveUpdateItem(i);
		
		 List<RunnerGroup> groups = new LinkedList<RunnerGroup>();		 
		u= userService.createUser(getUID(), "test", "test", false, false, false, groups);
		
		ds = new RunnerDataSource();
		ds.setDataSourceName(getUID());
			ds.setGroups(groups);

		//per environment
		ds.setJdbcClass("com.mysql.jdbc.Driver");
		ds.setJdbcUrl("jdbc:mysql://localhost:3306/dashtest"); //dashtest
		ds.setUsername("dashtest");  //dashtest
		ds.setPassword("password"); 
	
		
		dss.saveUpdateDataSource(ds);

		 
	}
	private RunnerJob getTestJob() {				
		
		RunnerJob j = new RunnerJob();
		
		RunnerJob_pk pk = new RunnerJob_pk();
		pk.setGroup(group);
		pk.setJobName(getUID());
		j.setPk(pk);
		j.setScheduled(true);
		j.setStartDate(Calendar.getInstance().getTime());
		j.setCronString("0 0 * ? * *");
		RunnerJobParameter p = new RunnerJobParameter();
		p.setRunnerJob(j);
		p.setParameterIdx(1);
		List<RunnerJobParameter> ps = new LinkedList<RunnerJobParameter>();
		ps.add(p);
		j.setParameters(ps);
		return j;
	}
	@Override
	protected void tearDown() throws Exception {		
		groupService.delete(group.getGroupName());
		userService.deleteUser(u.getUsername());
		dss.deleteDataSource(ds.getDataSourceName());
	}
	
	private String getUID() {
		return UUID.randomUUID().toString();
	}
	
	public void testDelete() {
		try {
			groupService.delete(group.getGroupName());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		assertNull(groupService.getGroup(group.getGroupName()));
	}

	public void testGetAll() {
		assertTrue(groupService.getAll().size()>0);
	}

	public void testGetGroup() {
		assertNotNull(groupService.getGroup(group.getGroupName()));
	}

}
