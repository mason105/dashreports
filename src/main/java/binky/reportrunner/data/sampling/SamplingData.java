package binky.reportrunner.data.sampling;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import binky.reportrunner.data.DatabaseObject;
import binky.reportrunner.data.RunnerDashboardSampler;

@Entity(name = "T_S_DATA")
public class SamplingData extends DatabaseObject<SamplingData_pk> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7337434800918457411L;
	@Id
	private SamplingData_pk pk;

	@Override
	public SamplingData_pk getId() {
		return pk;
	}

	private BigDecimal  value;

	public SamplingData_pk getPk() {
		return pk;
	}

	public void setPk(SamplingData_pk pk) {
		this.pk = pk;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public SamplingData() {
		
	}
	public SamplingData(RunnerDashboardSampler sampler, Long sampleDate, BigDecimal value) {
		this.pk = new SamplingData_pk(sampler,sampleDate);
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SamplingData && obj!=null && this.pk!=null && ((SamplingData)obj).getPk()!=null) {
			SamplingData comp = (SamplingData)obj;
			if (this.pk.getSampleTime()!=null&&comp.getPk().getSampleTime()!=null &&this.getPk().getSampler()!=null && comp.getPk().getSampler()!=null) {
				return (this.getPk().getSampler().getItemId().equals(comp.getPk().getSampler().getItemId())&&this.getPk().getSampleTime().equals(comp.getPk().getSampleTime()));
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}

}
