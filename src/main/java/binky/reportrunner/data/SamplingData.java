package binky.reportrunner.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "T_S_DATA")
public class SamplingData extends DatabaseObject<SamplingData_pk> {

	@Id
	private SamplingData_pk pk;

	@Override
	public SamplingData_pk getId() {
		return pk;
	}

	private Number value;

	public SamplingData_pk getPk() {
		return pk;
	}

	public void setPk(SamplingData_pk pk) {
		this.pk = pk;
	}

	public Number getValue() {
		return value;
	}

	public void setValue(Number value) {
		this.value = value;
	}
	public SamplingData() {
		
	}
	public SamplingData(RunnerDashboardSampler sampler, Date sampleDate, Number value) {
		this.pk = new SamplingData_pk(sampler,sampleDate);
		this.value = value;
	}

}
