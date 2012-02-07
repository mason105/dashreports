package binky.reportrunner.data.sampling;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import binky.reportrunner.data.DatabaseObject;
import binky.reportrunner.data.RunnerDashboardSampler;

@Entity(name="T_T_DATA")
public class TrendData extends  DatabaseObject<Long>  {

	private static final long serialVersionUID = 396314789157070381L;

	public TrendData() {}
	public TrendData(RunnerDashboardSampler sampler, String timeString) {
		this.sampler=sampler;
		this.timeString=timeString;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	
	private int sampleSize;
	
	private BigDecimal meanValue;
	@Column(name="maximumValue")
	private BigDecimal maxValue;
	private BigDecimal minValue;
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
	
	public int getSampleSize() {
		return sampleSize;
	}
	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}
	public BigDecimal getMeanValue() {
		return meanValue;
	}
	public void setMeanValue(BigDecimal meanValue) {
		this.meanValue = meanValue;
	}
	public BigDecimal getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}
	public BigDecimal getMinValue() {
		return minValue;
	}
	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	//mean calc when adding - new = ((mean*sampleSize)+val)/(sampleSize+1)
	
	
}
