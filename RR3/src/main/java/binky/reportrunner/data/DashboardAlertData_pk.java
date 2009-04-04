package binky.reportrunner.data;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;

@Embeddable
public class DashboardAlertData_pk {

	@GeneratedValue
	private Integer id;
	@ManyToOne
	private RunnerDashboardAlert parentAlert;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public RunnerDashboardAlert getParentAlert() {
		return parentAlert;
	}
	public void setParentAlert(RunnerDashboardAlert parentAlert) {
		this.parentAlert = parentAlert;
	}
	
}
