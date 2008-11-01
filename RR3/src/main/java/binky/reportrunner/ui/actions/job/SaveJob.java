package binky.reportrunner.ui.actions.job;

import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SaveJob  extends StandardRunnerAction {

	private static final long serialVersionUID = 1L;

	private RunnerJobService jobService;

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public final RunnerJobService getJobService() {
		return jobService;
	}

	public final void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}



}
