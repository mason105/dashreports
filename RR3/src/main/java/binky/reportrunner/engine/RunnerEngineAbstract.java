package binky.reportrunner.engine;

import javax.sql.DataSource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerResult;

/**
 * @author Daniel Grout
 **/
public abstract class RunnerEngineAbstract implements Job {

	public final void execute(JobExecutionContext context) throws JobExecutionException {
		
		//Grab the elements of the job from the context to pass on
		RunnerJob job = (RunnerJob)context.get("job");
		DataSource ds = (DataSource)context.get("ds");
		
		RunnerResult result;
		try {
			result = runReport(ds,job);
			context.setResult(result);
		} catch (RunnerException e) {
			throw new JobExecutionException(e);
		}
	}

	protected abstract RunnerResult runReport(DataSource ds, RunnerJob job) throws RunnerException;	

}
