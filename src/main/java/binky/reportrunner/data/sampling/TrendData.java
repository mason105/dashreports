package binky.reportrunner.data.sampling;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import binky.reportrunner.data.DatabaseObject;
import binky.reportrunner.data.RunnerDashboardSampler;

@Entity(name="T_T_DATA")
public class TrendData extends  DatabaseObject<TrendData_pk>  {

	private static final long serialVersionUID = 396314789157070381L;

	public TrendData() {}
	public TrendData(RunnerDashboardSampler sampler, String timeString) {
		this.pk=new TrendData_pk(sampler,timeString);
	}
	
	@Id
	private TrendData_pk pk;

	@Override
	public TrendData_pk getId() {
		return pk;
	}
	
	
	private int sampleSize;
	
	private BigDecimal meanValue;
	@Column(name="maximumValue")
	private BigDecimal maxValue;
	private BigDecimal minValue;

	public TrendData_pk getPk() {
		return pk;
	}
	public void setPk(TrendData_pk pk) {
		this.pk = pk;
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
	
	
	//mean calc when adding - new = ((mean*sampleSize)+val)/(sampleSize+1)
	
	
}
