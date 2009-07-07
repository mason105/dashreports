/*******************************************************************************
 * Copyright (c) 2009 Daniel Grout.
 * 
 * GNU GENERAL PUBLIC LICENSE - Version 3
 * 
 * This file is part of Report Runner (http://code.google.com/p/reportrunner).
 * 
 * Report Runner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Runner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Runner. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Module: RunnerJob.java
 ******************************************************************************/
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

@Entity
public class RunnerJob implements Serializable {

	private static final long serialVersionUID = 2036013437864145537L;

	public enum Template {
		NONE("No Template"), JASPER("Jasper Reports");
		private String displayName;

		Template(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

	}

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
			String burstQuery,
			String targetEmailAddress, String alertEmailAddress,
			byte[] templateFile, Template templateType, FileFormat fileFormat,
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
		this.targetEmailAddress = targetEmailAddress;
		this.alertEmailAddress = alertEmailAddress;
		this.templateFile=templateFile;
		this.templateType=templateType;
		this.fileFormat = fileFormat;
		this.alertOnSuccess = alertOnSuccess;
		this.parameters = parameters;
	}

	@Id
	private RunnerJob_pk pk;

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


	private String targetEmailAddress;

	private String alertEmailAddress;

	private byte[] templateFile;
	
	private Template templateType;

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

	public byte[] getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(byte[] templateFile) {
		this.templateFile = templateFile;
	}

	public Template getTemplateType() {
		return templateType;
	}

	public void setTemplateType(Template templateType) {
		this.templateType = templateType;
	}

}
