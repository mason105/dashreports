package binky.reportrunner.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.UrlValidator;

import net.sf.jasperreports.engine.JasperReport;

@Entity
public class RunnerJob implements Serializable {

	private static final long serialVersionUID = 2036013437864145537L;

	public enum FileFormat {
		PDF("PDF"), XLS("XLS"), RTF("RTF"), HTML("HTML"), CSV("CSV");
		private String displayName;

		FileFormat(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}
	};

	public RunnerJob() {
	}

	public RunnerJob(RunnerJob_pk pk, String outputUrl,
			RunnerDataSource datasource, String description, String query,
			Date startDate, Date endDate, String cronString, Boolean isBurst,
			String burstQuery, String burstFileNameParameterName,
			String targetEmailAddress, String alertEmailAddress,
			JasperReport jasperReport, FileFormat fileFormat,
			boolean alertOnSuccess, List<RunnerJobParameter> parameters) {
		super();
		this.pk = pk;
		this.outputUrl = outputUrl;
		this.datasource = datasource;
		this.description = description;
		this.query = query;
		this.startDate = startDate;
		this.endDate = endDate;
		this.cronString = cronString;
		this.isBurst = isBurst;
		this.burstQuery = burstQuery;
		this.burstFileNameParameterName = burstFileNameParameterName;
		this.targetEmailAddress = targetEmailAddress;
		this.alertEmailAddress = alertEmailAddress;
		this.jasperReport = jasperReport;
		this.fileFormat = fileFormat;
		this.alertOnSuccess = alertOnSuccess;
		this.parameters = parameters;
	}

	@Id
	private RunnerJob_pk pk;

	private static final String runnerEngine = "binky.reportrunner.engine.RunnerEngine";
	private String outputUrl;

	@ManyToOne
	private RunnerDataSource datasource;
	private String description;
	
	
	private String query;

	private Date startDate;
	private Date endDate;

	private String cronString;
	private Boolean isBurst;
	private String burstQuery;
	private String burstFileNameParameterName;
	private String targetEmailAddress;
	private String alertEmailAddress;
	private JasperReport jasperReport;
	private FileFormat fileFormat;

	private boolean alertOnSuccess;

	@OneToMany(mappedBy = "pk.runnerJob_pk", fetch = FetchType.EAGER)
	private List<RunnerJobParameter> parameters;

	public boolean isAlertOnSuccess() {
		return alertOnSuccess;
	}

	public void setAlertOnSuccess(boolean alertOnSuccess) {
		this.alertOnSuccess = alertOnSuccess;
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
	
	@RequiredStringValidator
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
	@UrlValidator
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAlertEmailAddress() {
		return alertEmailAddress;
	}

	public void setAlertEmailAddress(String alertEmailAddress) {
		this.alertEmailAddress = alertEmailAddress;
	}

}
