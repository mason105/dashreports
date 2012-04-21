package binky.reportrunner.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerUser;
import binky.reportrunner.service.GroupService;
import binky.reportrunner.service.UserService;

public class UserServiceImplTest extends TestCase {

	
	UserService us;
	private RunnerUser u;
	GroupService groupService;
	private RunnerGroup group;
	protected void setUp() throws Exception {

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		us = (UserService)ctx.getBean("userService");
		groupService = (GroupService)ctx.getBean("groupService");
		
		group = new RunnerGroup();
		group.setGroupName(getUID());
		groupService.saveOrUpdate(group);

		
		List<RunnerGroup> groups = new LinkedList<RunnerGroup>();
		groups.add(group);
		u=us.createUser(getUID(),"password", "test",false, false,false, groups);
	}
	private String getUID() {
		return UUID.randomUUID().toString();
	}
	protected void tearDown() throws Exception {
		us.deleteUser(u.getUserName());
		groupService.delete(group.getGroupName());
	}

	public void testGetUser() {
		assertNotNull(us.getUser(u.getUserName()));
	}

	public void testGetGroupsForUser() {
		assertTrue(us.getGroupsForUser(u.getUserName()).size()>0);
	}

	public void testGetAll() {
		assertTrue(us.getAll().size()>0);
	}

	public void testChangePassword() {		
		try {
			us.changePassword(u.getUsername(), "password", "password2");
			RunnerUser comp = us.getUser(u.getUsername());
			assertFalse((u.getPassword().equals(comp.getPassword())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

}
