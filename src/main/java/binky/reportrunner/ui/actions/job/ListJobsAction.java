package binky.reportrunner.ui.actions.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;
import binky.reportrunner.ui.actions.job.beans.DisplayJob;

public class ListJobsAction extends StandardRunnerAction {

	private static final long serialVersionUID = 6919067344312363024L;
	private String groupName;

	private static Logger logger = Logger.getLogger(ListJobsAction.class);

	private List<DisplayJob> jobs;
	private RunnerJobService jobService;

	@Override
	public String execute() throws Exception {
		if ((groupName != null) && (!groupName.isEmpty())) {
			logger.debug("looking for group: " + groupName);
			
			if (doesUserHaveGroup(groupName)) {
				List<DisplayJob> jobs = new LinkedList<DisplayJob>();
				for (RunnerJob job : jobService.listJobs(groupName)) {
					DisplayJob dJob = new DisplayJob();
					String jobName = job.getPk().getJobName();
					String groupName = job.getPk().getGroup().getGroupName();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"dd-MM-yyyy HH:mm:ss");
					dJob.setIsScheduleActive(jobService.isJobActive(jobName,
							groupName));
					if (dJob.getIsScheduleActive()) {
						Date prevRun = jobService.getPreviousRunTime(jobName, groupName);
						if (prevRun!=null) {
							dJob.setPreviousRunTime(sdf.format(prevRun)); 
						}
						dJob.setNextRunTime(sdf.format(jobService
								.getNextRunTime(jobName, groupName)));
					}
					dJob.setIsScheduled(((job.getCronString() != null) && !job
							.getCronString().isEmpty()));

					dJob.setGroupName(groupName);
					dJob.setJobName(jobName);
					dJob.setDescription(job.getDescription());
					jobs.add(dJob);
				}
				this.jobs = jobs;
			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getSessionUser().getUserName());
				logger.fatal(se.getMessage(), se);
				throw se;
			}
		} else {
			this.jobs = new LinkedList<DisplayJob>();
		}
		return SUCCESS;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<DisplayJob> getJobs() {
		return jobs;
	}

	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}


}