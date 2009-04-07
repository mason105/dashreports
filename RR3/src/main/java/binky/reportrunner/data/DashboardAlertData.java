package binky.reportrunner.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DashboardAlertData {

	@Id
	private DashboardAlertData_pk pk;
	private Date timeDataCollected;
	private Object value;
	public DashboardAlertData_pk getPk() {
		return pk;
	}
	public void setPk(DashboardAlertData_pk pk) {
		this.pk = pk;
	}
	public Date getTimeDataCollected() {
		return timeDataCollected;
	}
	public void setTimeDataCollected(Date timeDataCollected) {
		this.timeDataCollected = timeDataCollected;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
