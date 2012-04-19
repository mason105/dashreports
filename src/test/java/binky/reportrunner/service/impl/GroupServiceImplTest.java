package binky.reportrunner.service.impl;

import java.util.UUID;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.service.GroupService;

public class GroupServiceImplTest extends TestCase {
	GroupService groupService;
	private RunnerGroup group;
	protected void setUp() throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		groupService = (GroupService) ctx.getBean("groupService");
		
		group = new RunnerGroup();
		group.setGroupName(getUID());
		groupService.saveOrUpdate(group);
	}
	
	@Override
	protected void tearDown() throws Exception {
		groupService.delete(group.getGroupName());
	}
	
	private String getUID() {
		return UUID.randomUUID().toString();
	}
	
	public void testDelete() {
		groupService.delete(group.getGroupName());
		assertNull(groupService.getGroup(group.getGroupName()));
	}

	public void testGetAll() {
		assertTrue(groupService.getAll().size()>0);
	}

	public void testGetGroup() {
		assertNotNull(groupService.getGroup(group.getGroupName()));
	}

}
