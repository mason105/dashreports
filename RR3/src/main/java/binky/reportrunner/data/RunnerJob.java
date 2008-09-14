package binky.reportrunner.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import net.sf.jasperreports.engine.JasperReport;

@Entity
public class RunnerJob implements Serializable {

	private static final long serialVersionUID = 2036013437864145537L;

	public enum FileFormat {
		PDF, XLS, RTF, HTML, CSV
	};

	@Id
	private RunnerJob_pk pk;

	private String outputUrl;
	private final String runnerEngine = "binky.reportrunner.engine.RunnerEngine";

	@ManyToOne
	private RunnerDataSource datasource;

	private String query;

	private Date startDate;
	private Date endDate;

	private String cronString;
	private Boolean isBurst;
	private String burstQuery;
	private String burstFileNameParameterName;
	private String targetEmailAddress;
	private JasperReport jasperReport;
	private FileFormat fileFormat;
	private String fromAddress;
	private String smtpServer;

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public FileFormat getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(FileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getTargetEmailAddress() {
		return targetEmailAddress;
	}

	public void setTargetEmailAddress(String targetEmailAddress) {
		this.targetEmailAddress = targetEmailAddress;
	}

	@OneToMany(mappedBy = "pk.runnerJob")
	private List<RunnerJobParameter> parameters;

	public List<RunnerJobParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<RunnerJobParameter> parameters) {
		this.parameters = parameters;
	}

	public RunnerJob_pk getPk() {
		return pk;
	}

	public void setPk(RunnerJob_pk pk) {
		this.pk = pk;
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
