package binky.reportrunner.engine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJobParameter_pk;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class SQLProcessorTest extends TestCase {
	ApplicationContext ctx = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });
		DataSource ds;

	@Override
	protected void setUp() throws Exception {
		 ds = (ComboPooledDataSource) ctx
		 .getBean("dataSource");
		 Connection connection = ds.getConnection();			
		String create = "CREATE TABLE  `reportrunner`.`test_table` ( "
				+ "`testString` varchar(255) NOT NULL, "
				+ "`testInteger` int(11) default NULL, "
				+ "`testBoolean` tinyint(1) default NULL, "
				+ "`testDate` datetime default NULL, "
				+ "`testLong` bigint(20) unsigned default NULL, "
				+ "`testDouble` double default NULL, "
				+ "PRIMARY KEY  (`testString`) "
				+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		connection.createStatement().executeUpdate(create);
		connection.createStatement().executeUpdate("insert into test_table values('test',999,1,'2008-11-11 00:00:01',1000000,2.2)");
		connection.close();
	}

	@Override
	protected void tearDown() throws Exception {
		String drop="DROP TABLE IF EXISTS `reportrunner`.`test_table`;";
		Connection connection = ds.getConnection();
		connection.createStatement().executeUpdate(drop);
		connection.close();
	}

	public void testGetResultsConnectionString() {
		try {
			SQLProcessor proc = new SQLProcessor();
			Connection connection = ds.getConnection();
			String sql = "select * from runneruser";
			ResultSet res = proc.getResults(connection, sql);
			assertTrue(res.last());
			int rowCount = res.getRow();
			assertTrue(rowCount > 0);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testGetResultsConnectionStringListOfRunnerJobParameter() {
		try {
			SQLProcessor proc = new SQLProcessor();
			Connection connection = ds.getConnection();
			String sql = "select * from test_table where testString=? and testInteger=?  and testBoolean=? and testDate=?   and testLong=? and testDouble=?";
			List<RunnerJobParameter> params = new LinkedList<RunnerJobParameter>();
			// 1=String 2=Timestamp 3=Boolean 4=int 5=Long 6=Double			
			params.add(getParam(1,"test",1));
			params.add(getParam(2,"999",4));
			params.add(getParam(3,"TRUE",3));
			params.add(getParam(4,"2008-11-11 00:00:01",2));			
			params.add(getParam(5,"1000000",5));
			params.add(getParam(6,"2.2",6));
			ResultSet res = proc.getResults(connection, sql, params);
			assertTrue(res.last());
			int rowCount = res.getRow();
			assertTrue(rowCount > 0);
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	
	private RunnerJobParameter getParam(Integer idx, String value, Integer paramType) {
		RunnerJobParameter_pk pk = new RunnerJobParameter_pk();
		pk.setParameterIdx(idx);
		RunnerJobParameter param = new RunnerJobParameter();
		param.setPk(pk);
		param.setParameterType(paramType);
		param.setParameterValue(value);
		return param;
	}
}
