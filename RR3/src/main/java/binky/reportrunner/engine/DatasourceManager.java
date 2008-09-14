package binky.reportrunner.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import binky.reportrunner.data.RunnerDataSource;

public class DatasourceManager {
	
	//TODO:need to think about a datasource service that pre-loads all the stored connections
	//and pools/manages them - v2?
	
	/**
	 * @return a connection to the datasource for the job
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws NamingException
	 */
	public Connection getDataConnection(RunnerDataSource runnerDs)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, NamingException {

		String jndiDataSource = runnerDs.getJndiName();
		if (jndiDataSource == null) {

			String jdbcUser = runnerDs.getUsername();
			String jdbcPassword = runnerDs.getPassword();
			String jdbcUrl = runnerDs.getJdbcUrl();
			String databaseDriver = runnerDs.getJdbcClass();

			Class.forName(databaseDriver).newInstance();

			// logger.debug("Url = "+jdbcUrl);

			return DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
		} else {
			Context initContext = new InitialContext();
			// logger.debug(jndiDataSource);
			DataSource ds = (DataSource) initContext.lookup("java:/comp/env/"
					+ jndiDataSource);
			Connection conn = ds.getConnection();

			return conn;
		}
	}
}
