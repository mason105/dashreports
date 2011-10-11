package binky.reportrunner.ui.actions.group;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;
import binky.reportrunner.ui.actions.job.beans.DisplayJob;

public class ShowGroup extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;
	private List<RunnerDashboardItem> items;
	private DashboardService dashboardService;
	private Logger logger = Logger.getLogger(ShowGroup.class);
	private List<DisplayJob> jobs;
	private RunnerJobService jobService;

	@Override
	public String execute() throws Exception {

		items = new LinkedList<RunnerDashboardItem>();

		if (!super.getSessionUser().getIsAdmin()
				&& !super.doesUserHaveGroup(groupName)) {
			return ERROR;
		} else {
			items = dashboardService.getItemsForGroup(this.groupName);

			if (logger.isDebugEnabled()) {

				if (items != null) {
					logger.debug("items size is: " + items.size()
							+ " for group: " + groupName);
				} else {
					logger.debug("items are null for group: " + groupName);
				}

			}
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
		
		}
		return SUCCESS;
	}

	public List<RunnerDashboardItem> getItems() {
		return items;
	}

	public List<DisplayJob> getJobs() {
		return jobs;
	}

	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}


}
