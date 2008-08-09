package binky.reportrunner.engine;

import java.io.IOException;
import java.io.OutputStream;
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

@SuppressWarnings("unchecked")
public abstract class RunnerEngineAbstract implements Job {
	
	private RunnerDataSource runnerDs;
	
	protected Map<String, Class> parameters;
	
	public final void execute(JobExecutionContext context) throws JobExecutionException {
		
		//Grab the elements of the job from the context to pass on
		RunnerJob job = (RunnerJob)context.getJobDetail().getJobDataMap().get("runnerJob");
		this.runnerDs=job.getDatasource();
		try {
		
			if (job.getIsBurst()) {
				processBurstedReport(job);
			} else {
				processSingleReport(job);
			}
			
		} catch (RunnerException e) {
			throw new JobExecutionException(e);
		} catch (IOException ioe) {
			throw new JobExecutionException(ioe);
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
	
	private void processBurstedReport(RunnerJob job) throws RunnerException,IOException {
		
	}
	
	private void processSingleReport(RunnerJob job) throws RunnerException,IOException {
		runReport(job.getEngineParameters(), getOutputStreamForUrl(job.getOutputUrl()));
	}
	
	private OutputStream getOutputStreamForUrl(String url) throws IOException {
		return null;
	}
	
	/**
	 * @return A Map of Parameter ID, Class,
	 * 
	 *  This will determine what parameters the UI prompts for when creating a job
	 *  this should be populated in the constructor/init of the implementing class
	 *  
	 *  This first String is the name the second is the class name of the type
	 *  
	 *  It is up to the underlying implementation how it handles the parameter list passed in 
	 *  
	 */
	public final Map<String,Class> getParameters() {
		return parameters;
	}
	

	
	/**
	 * This is where all the logic to run the report goes
	 * 
	 * @throws RunnerException
	 */
	protected abstract void runReport(Map<String, Object> parameterValues,OutputStream os) throws RunnerException;			
	
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
