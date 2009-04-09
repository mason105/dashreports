package binky.reportrunner.data;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class RunnerHistoryEvent {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long eventId;
	private Date timestamp;
	private String jobName;
	private String groupName;
	private String message;
	private Boolean success;
	private Long runTime;
	public Long getRunTime() {
		return runTime;
	}
	public void setRunTime(Long runTime) {
		this.runTime = runTime;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public String toString() {
		StringBuilder ret = new StringBuilder();
		
		ret.append("Job Name="+jobName);
		ret.append(" ");
		ret.append("Group Name="+groupName);
		ret.append("");
		ret.append("Timestamp=" + timestamp);
		ret.append(" ");
		ret.append("Elapsed Time="+runTime);
		ret.append(" ");
		ret.append("Message="+message);
		
		return ret.toString();
	}
}
