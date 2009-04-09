package binky.reportrunner.data;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;

@Embeddable
public class DashboardAlertData_pk implements Serializable {

	private static final long serialVersionUID = 819372468218173921L;

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
