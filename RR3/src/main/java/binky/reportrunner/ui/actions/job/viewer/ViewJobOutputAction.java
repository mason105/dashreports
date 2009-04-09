package binky.reportrunner.ui.actions.job.viewer;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerHistoryDao;
import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.engine.ViewerResults;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ViewJobOutputAction extends StandardRunnerAction {

	private static final long serialVersionUID = 2677747746707641721L;

	private String jobName;

	private String groupName;

	private List<RunnerJobParameter> parameters;

	private Map<String, ViewerResults> results;
	
	private RunnerHistoryDao historyDao;
	
	
	private RunnerJobService jobService;
	private static final Logger logger = Logger.getLogger(ViewJobOutputAction.class);
	@Override
	public String execute() throws Exception {
		long startTime = (new Date()).getTime();
		//TODO: NASTY HACK ALERT
		if ((this.parameters != null) && (this.parameters.size() > 0)) {
			RunnerJob job = jobService.getJob(jobName, groupName);
			
			List<RunnerJobParameter> parameters = job.getParameters();
			
			for (int i=0;i<parameters.size();i++) {
				RunnerJobParameter p = parameters.get(i);
				p.setParameterValue(this.parameters.get(i).getParameterValue());
			}
			
			results = jobService.getResultsForJob(jobName, groupName,
					parameters);
		} else {
			results = jobService.getResultsForJob(jobName, groupName);
		}
		
		if (logger.isDebugEnabled()) {
			//Quick debuggering
			for (String key:results.keySet()) {
				List rows= results.get(key).getRowSet().getRows();
				logger.debug("key="+key+" row count="+rows.size());
				for (Object o:rows) {
					logger.debug("key="+key+" row=" + o);
				}
			}
		}
		
		long endTime = (new Date()).getTime();
		
		//create an event for this so we can track performance of bad queries
		RunnerHistoryEvent event = new RunnerHistoryEvent();
		event.setGroupName(groupName);
		event.setJobName(jobName);
		String message = "User:" + super.getSessionUser().getUserName() + " ran job viewer";
		event.setMessage(message);
		event.setRunTime(endTime-startTime);
		event.setTimestamp(new Date());
		historyDao.saveEvent(event);
		
		return SUCCESS;
	}

	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public List<RunnerJobParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<RunnerJobParameter> parameters) {
		this.parameters = parameters;
	}

	public Map<String, ViewerResults> getResults() {
		return results;
	}

	public void setResults(Map<String, ViewerResults> results) {
		this.results = results;
	}

	public RunnerHistoryDao getHistoryDao() {
		return historyDao;
	}

	public void setHistoryDao(RunnerHistoryDao historyDao) {
		this.historyDao = historyDao;
	}



}
