package binky.reportrunner.ui.actions.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerHistoryDao;
import binky.reportrunner.data.RunnerHistoryEvent;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;
import binky.reportrunner.ui.actions.job.beans.DisplayJob;

public class ViewJobDetail extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;

	private RunnerHistoryDao historyDao;


	private String jobName;
	private String groupName;
	private DisplayJob job;
	private List<RunnerHistoryEvent> events;
	private static final Logger logger = Logger.getLogger(SetupEditJob.class);
	private RunnerJobService jobService;

	@Override
	public String execute() throws Exception {
		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (doesUserHaveGroup(groupName)) {
				RunnerJob job = jobService.getJob(jobName, groupName);
				this.job=new DisplayJob();
				this.job.setDescription(job.getDescription());
				this.job.setGroupName(groupName);
				this.job.setJobName(jobName);
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				this.job.setNextRunTime(sdf.format(jobService.getNextRunTime(jobName, groupName)));
				Date prt = jobService.getPreviousRunTime(jobName, groupName);
				if (prt!=null) {
					this.job.setPreviousRunTime(sdf.format(prt));
				}
				this.job.setIsScheduleActive(jobService.isJobActive(jobName, groupName));
				events = historyDao.getEvents(groupName, jobName);
			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getSessionUser().getUserName());
				logger.fatal(se.getMessage(), se);
				throw se;
			}

		} else {
			job = new DisplayJob();
		}
		return SUCCESS;
	}

	public final RunnerJobService getJobService() {
		return jobService;
	}

	public final void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public final String getJobName() {
		return jobName;
	}

	public final void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public final String getGroupName() {
		return groupName;
	}

	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public final DisplayJob getJob() {
		return job;
	}

	public final void setJob(DisplayJob job) {
		this.job = job;
	}

	public final RunnerHistoryDao getHistoryDao() {
		return historyDao;
	}

	public final void setHistoryDao(RunnerHistoryDao historyDao) {
		this.historyDao = historyDao;
	}

	public List<RunnerHistoryEvent> getEvents() {
		return events;
	}
	
	

}