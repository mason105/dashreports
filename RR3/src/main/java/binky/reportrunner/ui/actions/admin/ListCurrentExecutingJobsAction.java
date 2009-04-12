package binky.reportrunner.ui.actions.admin;

import java.util.List;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class ListCurrentExecutingJobsAction extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;

	private RunnerJobService jobService;
	private List<RunnerJob> jobs;
	@Override
	public String execute() throws Exception {
		this.jobs=jobService.getRunningJobs();
		return SUCCESS;
	}

	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public List<RunnerJob> getJobs() {
		return jobs;
	}

	public void setJobs(List<RunnerJob> jobs) {
		this.jobs = jobs;
	}

}
