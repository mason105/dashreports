package binky.reportrunner.ui.actions;

import java.util.LinkedList;
import java.util.List;

import binky.reportrunner.dao.RunnerJobDao;
import binky.reportrunner.data.RunnerJob;

import com.opensymphony.xwork2.ActionSupport;

public class ListJobsAction extends ActionSupport {

	private static final long serialVersionUID = 6919067344312363024L;
	private RunnerJobDao runnerJobDao;
	private String groupName;
	private List<RunnerJob> jobs;
	
	
	@Override
	public String execute() throws Exception {
		if ((groupName!=null)&&(!groupName.isEmpty())){
			jobs =runnerJobDao.listJobs(groupName);
		} else {
			jobs = new LinkedList<RunnerJob>();
		}
		return SUCCESS;
	}

	public RunnerJobDao getRunnerJobDao() {
		return runnerJobDao;
	}

	public void setRunnerJobDao(RunnerJobDao runnerJobDao) {
		this.runnerJobDao = runnerJobDao;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<RunnerJob> getJobs() {
		return jobs;
	}
	

}
