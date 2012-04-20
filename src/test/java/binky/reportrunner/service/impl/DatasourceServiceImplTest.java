package binky.reportrunner.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xml.sax.SAXException;

import binky.dan.utils.encryption.EncryptionException;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.service.GroupService;
import binky.reportrunner.service.misc.JDBCDriverDefinition;
import binky.reportrunner.util.EncryptionUtil;

public class DatasourceServiceImplTest extends TestCase {
	//sneakliy grab the old key via the backdoor
	String currentKey;
	
	DatasourceService dss;
	private RunnerDataSource ds;
	GroupService groupService;
	private RunnerGroup group;
	protected void setUp() throws Exception {

		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		dss = (DatasourceService)ctx.getBean("runnerDatasourceService");
		groupService = (GroupService)ctx.getBean("groupService");
		
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
		ds.setJdbcUrl("jdbc:mysql://localhost:3306/dashreports"); //dashtest
		ds.setUsername("root");  //dashtest
		ds.setPassword("password"); 
		currentKey="BAD0858CD6A7161901AE9BCB195EFD5E9D6BE0FE7643A7E9";
		
		dss.saveUpdateDataSource(ds);

		
	}

	private String getUID() {
		return UUID.randomUUID().toString();
	}
	
	protected void tearDown() throws Exception {
		dss.deleteDataSource(ds.getDataSourceName());
		groupService.delete(group.getGroupName());
	}

	public void testPurgeConnections() {
		try {
			dss.purgeConnections(ds.getDataSourceName());
		} catch (SQLException e) {	
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

	public void testTestDataSource() {
		RunnerDataSource ds2=dss.getDataSource(ds.getDataSourceName());
		//hack to take out the encryption part as this function is done via UI there not encrypted as it comes in
		ds2.setPassword("password");
		String result=dss.testDataSource(ds2);
		System.out.println(result);
		assertFalse(result.contains("ERROR"));
	}

	public void testGetJDBCDataSource() {
		try {
			DataSource d = dss.getJDBCDataSource(ds);
			assertNotNull(d.getConnection().getClientInfo());
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}


	public void testDeleteDataSource() {
		dss.deleteDataSource(ds.getDataSourceName());
		assertNull(dss.getDataSource(ds.getDataSourceName()));
	}

	public void testGetDataSource() {
		assertNotNull(ds.getDataSourceName());
	}

	public void testListDataSources() {
		assertTrue(dss.listDataSources().size()>0);
	}

	public void testGetJDBCDriverDefinitions() {

		try {			
			for (JDBCDriverDefinition def:dss.getJDBCDriverDefinitions().getDefinitions().values()) {
				System.out.println(def.getLabel() + ", " + def.getDriverName() + ", "  + def.getUrl());
			}
			
			assertTrue(dss.getJDBCDriverDefinitions().getDefinitions().values().size()>0);

		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (SAXException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	public void testGetDataSourcesForGroup() {
		assertTrue(dss.getDataSourcesForGroup(group.getGroupName()).size()>0);
	}

	public void testReEncryptPasswords() {


		try {
			//setup a new key
			String newKey =new EncryptionUtil().generateKey();
			//encrypt under the new key 
			dss.reEncryptPasswords(newKey);
	
			 dss.setSecureKey(newKey);
			 //grap the datasource under the new key
			 RunnerDataSource comp1 = dss.getDataSource(ds.getDataSourceName());
		
			 dss.reEncryptPasswords(currentKey);
			 dss.setSecureKey(currentKey);
			 //grap the datasource under the old key
			 RunnerDataSource comp2 = dss.getDataSource(ds.getDataSourceName());
			 System.out.println(ds.getPassword() + " "+ comp1.getPassword() + " "+  comp2.getPassword());
			 //compare the original with the rencrypted and then with the re-re-encrypted.
			 assertTrue(!ds.getPassword().equals(comp1.getPassword())&&ds.getPassword().equals(comp2.getPassword()));
		} catch (EncryptionException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		
	}

}
