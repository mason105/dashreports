package binky.reportrunner.dao.impl;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.data.RunnerUser;

public class RunnerUserDaoImplTest extends TestCase {

	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	RunnerUserDao dao;
	
	@Override
	protected void setUp() throws Exception {
		this.dao = (RunnerUserDao) ctx.getBean("runnerUserDao");
	}

	private RunnerUser getDemoUser(String userId){
		RunnerUser user = new RunnerUser();
		user.setFullName(userId);
		user.setUserName(userId);
		user.setPassword("password");
		return user;
	}
	
	public void testDeleteUser() {
		RunnerUser user = getDemoUser("demouser");
		dao.saveUpdateUser(user);
		dao.deleteUser(user.getUserName());
		RunnerUser userComp= dao.getUser(user.getUserName());
		assertNull(userComp);
	}


	public void testListUsers() {
		RunnerUser user1 = getDemoUser("demouser1");
		RunnerUser user2 = getDemoUser("demouser2");
		dao.saveUpdateUser(user1);
		dao.saveUpdateUser(user2);
		List<RunnerUser> users=dao.listUsers();
		assertTrue(users.size()>=2);
		dao.deleteUser(user1.getUserName());
		dao.deleteUser(user2.getUserName());
	}

	public void testSaveUpdateUser() {
		RunnerUser user = getDemoUser("demouser");
		dao.saveUpdateUser(user);
		RunnerUser userComp= dao.getUser(user.getUserName());
		assertNotNull(userComp);
		assertEquals(user.getUserName(),userComp.getUserName());
		assertEquals(user.getFullName(),userComp.getFullName());
		assertEquals(user.getPassword(),userComp.getPassword());
		dao.deleteUser(user.getUserName());
	}

	/*public void testCreateAdminUser() {
		RunnerUser user = getDemoUser("administrator");
		user.setFullName("Administration Account");
		user.setIsAdmin(true);
		dao.saveUpdateUser(user);
	}*/
}
