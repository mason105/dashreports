package binky.reportrunner.ui.actions.admin;

import binky.reportrunner.scheduler.Scheduler;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SchedulerStateChange extends StandardRunnerAction {

	private static final long serialVersionUID = 778488274157345048L;

	private Scheduler scheduler;
	private int schedulerState;
	
	@Override
	public String execute() throws Exception {
		
		//0=shutdown
		//1=started
		
		switch (schedulerState) {
			case 1:
				scheduler.startScheduler();
				break;
			case 0:
				scheduler.stopScheduler();				
				break;
		}
		
		return SUCCESS;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public int getSchedulerState() {
		return schedulerState;
	}

	public void setSchedulerState(int schedulerState) {
		this.schedulerState = schedulerState;
	}

}
