package binky.reportrunner.ui.actions.admin;

import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class ListCurrentExecutingJobsAction extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;

	private RunnerJobService jobService;

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return SUCCESS;
	}

	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

}
