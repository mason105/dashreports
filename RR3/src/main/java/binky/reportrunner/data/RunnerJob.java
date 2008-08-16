package binky.reportrunner.data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import net.sf.jasperreports.engine.JasperReport;

@Entity
public class RunnerJob implements Serializable {

	private static final long serialVersionUID = 2036013437864145537L;
	
	@Id 
	private RunnerJob_pk pk;
	
	private String outputUrl;
	private final String runnerEngine="binky.reportrunner.engine.RunnerEngine";
	
	@ManyToOne
	private RunnerDataSource datasource;
	
	private Map<String,Object> parameterNames;
	private Map<String, Object> parameterValues;
	private Map<String, Integer> parameterBurstMappings;
	
	private String query;
	
	private Date startDate;
	private Date endDate;
	
	private String cronString;
	private Boolean isBurst;
	private String burstQuery;
	private String burstFileNameParameterName;	
	private JasperReport jasperReport;
	
	public RunnerJob_pk getPk() {
		return pk;
	}
	public void setPk(RunnerJob_pk pk) {
		this.pk = pk;
	}
	public Map<String, Object> getParameterNames() {
		return parameterNames;
	}
	public void setParameterNames(Map<String, Object> parameterNames) {
		this.parameterNames = parameterNames;
	}
	public Map<String, Object> getParameterValues() {
		return parameterValues;
	}
	public void setParameterValues(Map<String, Object> parameterValues) {
		this.parameterValues = parameterValues;
	}
	public Map<String, Integer> getParameterBurstMappings() {
		return parameterBurstMappings;
	}
	public void setParameterBurstMappings(
			Map<String, Integer> parameterBurstMappings) {
		this.parameterBurstMappings = parameterBurstMappings;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getBurstFileNameParameterName() {
		return burstFileNameParameterName;
	}
	public void setBurstFileNameParameterName(String burstFileNameParameterName) {
		this.burstFileNameParameterName = burstFileNameParameterName;
	}
	public JasperReport getJasperReport() {
		return jasperReport;
	}
	public void setJasperReport(JasperReport jasperReport) {
		this.jasperReport = jasperReport;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public Boolean getIsBurst() {
		return isBurst;
	}
	public void setIsBurst(Boolean isBurst) {
		this.isBurst = isBurst;
	}
	public String getBurstQuery() {
		return burstQuery;
	}
	public void setBurstQuery(String burstQuery) {
		this.burstQuery = burstQuery;
	}
	public String getRunnerEngine() {
		return runnerEngine;
	}

	public String getOutputUrl() {
		return outputUrl;
	}
	public void setOutputUrl(String outputUrl) {
		this.outputUrl = outputUrl;
	}
	public RunnerDataSource getDatasource() {
		return datasource;
	}
	public void setDatasource(RunnerDataSource datasource) {
		this.datasource = datasource;
	}

	public String getCronString() {
		return cronString;
	}
	public void setCronString(String cronString) {
		this.cronString = cronString;
	}	
	
	
}
