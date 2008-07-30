package binky.reportrunner.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerJob;

/**
 * @author Daniel Grout
 **/
public abstract class RunnerEngineAbstract implements Job {
	
	private RunnerDataSource runnerDs;
	
	protected Map<String, Object> parameters;
	protected Map<String, String> parameterNames;
	
	public final void execute(JobExecutionContext context) throws JobExecutionException {
		
		//Grab the elements of the job from the context to pass on
		RunnerJob job = (RunnerJob)context.get("job");
		this.runnerDs=job.getDatasource();
		try {
			 runReport();
		} catch (RunnerException e) {
			throw new JobExecutionException(e);
		}
	}

	/**
	 * @return a connection to the datasource for the job
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws NamingException
	 */
	protected final Connection getDataConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, NamingException {

		String jndiDataSource = runnerDs.getJndiName();
		if (jndiDataSource == null) {

			String jdbcUser = runnerDs.getUsername();
			String jdbcPassword = runnerDs.getPassword();
			String jdbcUrl = runnerDs.getJdbcUrl();
			String databaseDriver = runnerDs.getJdbcClass();

			Class.forName(databaseDriver).newInstance();

			//logger.debug("Url = "+jdbcUrl);

			return DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
		} else {
			Context initContext = new InitialContext();
			//logger.debug(jndiDataSource);
			DataSource ds = (DataSource)initContext.lookup("java:/comp/env/"+jndiDataSource);
			Connection conn = ds.getConnection();

			return conn;
		}
	}
	
	/**
	 * @return A Map of Parameter ID, Boolean IsList,
	 * 
	 *  This will determine what parameters the UI prompts for when creating a job
	 *  this should be populated in the constructor/init of the implementing class
	 *  
	 *  This first String is the name the second is the class name of the type
	 *  
	 *  It is up to the underlying implementation how it handles the parameter list passed in 
	 *  
	 */
	public final Map<String,String> getParameterNames() {
		return parameterNames;
	}
	
	/**
	 * @param parameters
	 */
	public final void setParameters(Map<String,Object> parameters) {
		this.parameters=parameters;
	}
	
	/**
	 * This is where all the logic to run the report goes
	 * 
	 * @throws RunnerException
	 */
	protected abstract void runReport() throws RunnerException;			
	
	/**
	 * @return the name of the runner engine
	 */
	public abstract String getEngineName();	
	
	
	/**
	 * @return a block of text explaining about the engine and configuration for it 
	 * this will be displayed in <pre> tags on the UI so you can include line spaces etc
	 */
	public abstract String getConfigMessage();

	
	
}
