package binky.reportrunner.dao.impl;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;

public class RunnerGroupDaoImplTest extends TestCase {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	RunnerGroupDao dao;

	protected void setUp() throws Exception {
		dao = (RunnerGroupDao) ctx.getBean("runnerGroupDao");
	}

	public static RunnerGroup getDemoGroup(String id) {
		RunnerGroup group = new RunnerGroup();
		group.setGroupName(id);
		group.setGroupDescription("test group " + id);
		return group;
	}
	
	public void testDeleteGroup() {
		RunnerGroup group =getDemoGroup("group1");
		dao.saveUpdateGroup(group);				
		assertNotNull(dao.getGroup(group.getGroupName()));
		dao.deleteGroup(group.getGroupName());
		assertNull(dao.getGroup(group.getGroupName()));		
	}


	public void testListGroups() {
		RunnerGroup group1 =getDemoGroup("group1");
		dao.saveUpdateGroup(group1);
		RunnerGroup group2 =getDemoGroup("group2");
		dao.saveUpdateGroup(group2);
		assertTrue(dao.listGroups().size()>=2);
		dao.deleteGroup("group1");
		dao.deleteGroup("group2");
	}

	public void testSaveUpdateGroup() {
		RunnerGroup group =getDemoGroup("group1");
		dao.saveUpdateGroup(group);
		RunnerGroup tGroup = dao.getGroup(group.getGroupName());
		assertEquals(group.getGroupName(),tGroup.getGroupName());
		assertEquals(group.getGroupDescription(), tGroup.getGroupDescription());
		dao.deleteGroup(group.getGroupName());
	}

}
