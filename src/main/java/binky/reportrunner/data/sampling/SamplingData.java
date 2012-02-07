package binky.reportrunner.data.sampling;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import binky.reportrunner.data.DatabaseObject;
import binky.reportrunner.data.RunnerDashboardSampler;

@Entity(name = "T_S_DATA")
public class SamplingData extends DatabaseObject<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7337434800918457411L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	private BigDecimal  value;

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public SamplingData() {
		
	}
	public SamplingData(RunnerDashboardSampler sampler, Long sampleDate, BigDecimal value) {
		this.sampler=sampler;
		this.sampleTime=sampleDate;
		this.value = value;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	private RunnerDashboardSampler sampler;
	private Long sampleTime;
	public RunnerDashboardSampler getSampler() {
		return sampler;
	}
	public void setSampler(RunnerDashboardSampler sampler) {
		this.sampler = sampler;
	}
	public Long getSampleTime() {
		return sampleTime;
	}
	public void setSampleTime(Long sampleTime) {
		this.sampleTime = sampleTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

}
