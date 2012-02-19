package binky.reportrunner.data.sampling;

import java.math.BigDecimal;

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
	@ManyToOne
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((maxValue == null) ? 0 : maxValue.hashCode());
		result = prime * result
				+ ((meanValue == null) ? 0 : meanValue.hashCode());
		result = prime * result
				+ ((minValue == null) ? 0 : minValue.hashCode());
		result = prime * result + sampleSize;
		result = prime * result + ((sampler == null) ? 0 : sampler.hashCode());
		result = prime * result
				+ ((timeString == null) ? 0 : timeString.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrendData other = (TrendData) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (maxValue == null) {
			if (other.maxValue != null)
				return false;
		} else if (!maxValue.equals(other.maxValue))
			return false;
		if (meanValue == null) {
			if (other.meanValue != null)
				return false;
		} else if (!meanValue.equals(other.meanValue))
			return false;
		if (minValue == null) {
			if (other.minValue != null)
				return false;
		} else if (!minValue.equals(other.minValue))
			return false;
		if (sampleSize != other.sampleSize)
			return false;
		if (sampler == null) {
			if (other.sampler != null)
				return false;
		} else if (!sampler.equals(other.sampler))
			return false;
		if (timeString == null) {
			if (other.timeString != null)
				return false;
		} else if (!timeString.equals(other.timeString))
			return false;
		return true;
	}
	
	
	//mean calc when adding - new = ((mean*sampleSize)+val)/(sampleSize+1)
	
	
}
