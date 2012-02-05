package binky.reportrunner.data.sampling;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import binky.reportrunner.data.RunnerDashboardSampler;

@Embeddable
public class TrendData_pk implements Serializable {

	private static final long serialVersionUID = 5295234128553777787L;

	public TrendData_pk(){}

	public TrendData_pk(RunnerDashboardSampler sampler, String timeString) {

		this.sampler = sampler;
		this.timeString = timeString;
	}
	@ManyToOne(cascade=CascadeType.ALL)
	private RunnerDashboardSampler sampler;
	private String timeString;
	public RunnerDashboardSampler getSampler() {
		return sampler;
	}
	public void setSampler(RunnerDashboardSampler sampler) {
		this.sampler = sampler;
	}
	public String getTimeString() {
		return timeString;
	}
	public void setTimeString(String timeString) {
		this.timeString = timeString;
	} 
	
}
