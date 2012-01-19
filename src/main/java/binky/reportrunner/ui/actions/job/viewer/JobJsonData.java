package binky.reportrunner.ui.actions.job.viewer;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class JobJsonData extends StandardRunnerAction {

	private String jobName;
	private RunnerJobService jobService;
	private ReportRunnerDao<RunnerHistoryEvent,Long> historyDao;
	private List<RunnerJobParameter> parameters;
	
	private String jsonData;
	
	private static final Logger logger = Logger.getLogger(StandardRunnerAction.class);
	@Override
	public String execute() throws Exception {
		if (!super.doesUserHaveGroup(groupName)) throw new SecurityException("User does not have permissions for group: " + groupName);
		long startTime = (new Date()).getTime();
		//TODO: NASTY HACK ALERT
		Map<String,String> jsons;
		if ((this.parameters != null) && (this.parameters.size() > 0)) {
			RunnerJob job = jobService.getJob(jobName, groupName);
			
			List<RunnerJobParameter> jobParameters = job.getParameters();
			
			for (RunnerJobParameter p: this.parameters) {								
				
				for (RunnerJobParameter jp:jobParameters) {
					if (jp.getPk().getParameterIdx().equals(p.getPk().getParameterIdx())) {
						jp.setParameterValue(p.getParameterValue());
						break;
					}
				}			
			}					
			//running with parameters
			jsons=jobService.getJSONsForJob(groupName, jobName, jobParameters);
		} else {		
		   //running without parameters
			jsons=jobService.getJSONsForJob(groupName, jobName);
		}
		
		long endTime = (new Date()).getTime();
		
		if (jsons!=null&&jsons.values().size()>0) {
			this.jsonData=jsons.values().iterator().next();
		}
		
		//create an event for this so we can track performance of bad queries
		RunnerHistoryEvent event = new RunnerHistoryEvent();
		event.setGroupName(groupName);
		event.setJobName(jobName);
		String message = "User:" + super.getSessionUser().getUserName() + " ran job viewer";
		event.setMessage(message);
		event.setRunTime(endTime-startTime);
		event.setTimestamp(new Date());
		event.setSuccess(true);
		historyDao.saveOrUpdate(event);
		
		return SUCCESS;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public RunnerJobService getJobService() {
		return jobService;
	}
	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}
	public ReportRunnerDao<RunnerHistoryEvent, Long> getHistoryDao() {
		return historyDao;
	}
	public void setHistoryDao(ReportRunnerDao<RunnerHistoryEvent, Long> historyDao) {
		this.historyDao = historyDao;
	}
	public List<RunnerJobParameter> getParameters() {
		return parameters;
	}
	public void setParameters(List<RunnerJobParameter> parameters) {
		this.parameters = parameters;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}


}
