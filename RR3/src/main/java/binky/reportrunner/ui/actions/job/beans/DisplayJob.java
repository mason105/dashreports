package binky.reportrunner.ui.actions.job.beans;

import java.util.Date;

public class DisplayJob {
private String jobName;
private String groupName;
private Date nextRunTime;
private Date previousRunTime;
private Boolean isScheduled;
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
public Date getNextRunTime() {
	return nextRunTime;
}
public void setNextRunTime(Date nextRunTime) {
	this.nextRunTime = nextRunTime;
}
public Date getPreviousRunTime() {
	return previousRunTime;
}
public void setPreviousRunTime(Date previousRunTime) {
	this.previousRunTime = previousRunTime;
}
public Boolean getIsScheduled() {
	return isScheduled;
}
public void setIsScheduled(Boolean isScheduled) {
	this.isScheduled = isScheduled;
}

}
