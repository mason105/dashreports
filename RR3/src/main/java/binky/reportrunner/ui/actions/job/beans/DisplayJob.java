package binky.reportrunner.ui.actions.job.beans;


public class DisplayJob {
	
	private String jobName;
	private String groupName;
	private String nextRunTime;
	private String previousRunTime;
	private Boolean isScheduled;
	private Boolean isScheduleActive;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Boolean getIsScheduled() {
		return isScheduled;
	}

	public void setIsScheduled(Boolean isScheduled) {
		this.isScheduled = isScheduled;
	}

	public Boolean getIsScheduleActive() {
		return isScheduleActive;
	}

	public void setIsScheduleActive(Boolean isScheduleActive) {
		this.isScheduleActive = isScheduleActive;
	}

	public String getNextRunTime() {
		return nextRunTime;
	}

	public void setNextRunTime(String nextRunTime) {
		this.nextRunTime = nextRunTime;
	}

	public String getPreviousRunTime() {
		return previousRunTime;
	}

	public void setPreviousRunTime(String previousRunTime) {
		this.previousRunTime = previousRunTime;
	}

}
