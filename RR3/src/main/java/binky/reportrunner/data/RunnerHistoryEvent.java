package binky.reportrunner.data;

import java.util.Date;

import javax.persistence.Id;

import org.hibernate.annotations.Entity;

@Entity
public class RunnerHistoryEvent {

	@Id
	private String eventId;
	private Date timestamp;
	private String jobName;
	private String groupName;
	private String message;
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
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
	
	
	
}
