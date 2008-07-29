package binky.reportrunner.engine;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import binky.reportrunner.data.RunnerJob;

/**
 * @author Daniel Grout
 **/
public abstract class RunnerEngineAbstract implements Job {
	public final void execute(JobExecutionContext context) throws JobExecutionException {
		
		//Grab the elements of the job from the context to pass on
		RunnerJob job = (RunnerJob)context.get("job");
		
		try {
			 runReport(job);
		} catch (RunnerException e) {
			throw new JobExecutionException(e);
		}
	}

	protected abstract void runReport(RunnerJob job) throws RunnerException;			
	
	/**
	 * @return the name of the runner engine
	 */
	public abstract String getEngineName();	
	
	
	/**
	 * @return A Map of Parameter ID, Boolean IsList,
	 * 
	 *  This will determine what parameters the UI prompts for when creating a job
	 *  
	 *  The parameter list should be a set of parameter names and then a boolean
	 *  switch to say if they are multiple (eg Jasper Parameters)
	 *  
	 *  If they are multiple they will be created as List<String> in the parameters passed
	 *  into the engine as part of the RunnerJob object other wise they will just be plain String
	 *  
	 *  It is up to the underlying implementation how it handles the parameter list passed in 
	 *  
	 */
	public abstract Map<String,Boolean> getParameters();
	
	/**
	 * @return a block of text explaining about the engine and configuration for it 
	 * this will be displayed in <pre> tags on the UI so you can include line spaces etc
	 */
	public abstract String getConfigMessage();
		
}
