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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.UrlValidator;

@Entity(name = "T_JOB")
@NamedQueries( {
	@NamedQuery(name = "getJobsByGroup", query = "from T_JOB j where j.pk.group.groupName = ?")
})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RunnerJob extends DatabaseObject<RunnerJob_pk> {

	public RunnerJob_pk getId() {
		return pk;
	}

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
		PDF("PDF"), XLS("Excel"), HTML("HTML"), CSV("CSV"), TABBED_XLS("Tabbed Excel (Bursted)");
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

private boolean scheduled;

	@Id
	private RunnerJob_pk pk;
	
	private Boolean outputToFile;
	
	private Boolean sendViaEmail;
	
	
	private String outputUrl;

	@ManyToOne
	private RunnerDataSource datasource;

	private String description;

	@Type(type="org.hibernate.type.TextType")
	private String query;

	private Date startDate;

	private Date endDate;

	private String cronString="0 0 12 * * ?";

	private Boolean isBurst;

	@Type(type="org.hibernate.type.TextType")
	private String burstQuery;


	private String targetEmailAddress;

	private String alertEmailAddress;

	@Type(type="org.hibernate.type.MaterializedBlobType")
	private byte[] templateFile;
	
	private Template templateType;

	private String templateFileName;
	
	private FileFormat fileFormat;

	private boolean alertOnSuccess;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="runnerJob" ,orphanRemoval=true)
	@OrderBy("parameterIdx")
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

	public String getTemplateFileName() {
		return templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public boolean isScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}

	@Override
	public int hashCode() {
		
		return this.pk.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RunnerJob other = (RunnerJob) obj;
		if (alertEmailAddress == null) {
			if (other.alertEmailAddress != null)
				return false;
		} else if (!alertEmailAddress.equals(other.alertEmailAddress))
			return false;
		if (alertOnSuccess != other.alertOnSuccess)
			return false;
		if (burstQuery == null) {
			if (other.burstQuery != null)
				return false;
		} else if (!burstQuery.equals(other.burstQuery))
			return false;
		if (cronString == null) {
			if (other.cronString != null)
				return false;
		} else if (!cronString.equals(other.cronString))
			return false;
		if (datasource == null) {
			if (other.datasource != null)
				return false;
		} else if (!datasource.equals(other.datasource))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (fileFormat != other.fileFormat)
			return false;
		if (isBurst == null) {
			if (other.isBurst != null)
				return false;
		} else if (!isBurst.equals(other.isBurst))
			return false;
		if (outputUrl == null) {
			if (other.outputUrl != null)
				return false;
		} else if (!outputUrl.equals(other.outputUrl))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		if (scheduled != other.scheduled)
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (targetEmailAddress == null) {
			if (other.targetEmailAddress != null)
				return false;
		} else if (!targetEmailAddress.equals(other.targetEmailAddress))
			return false;
		if (!Arrays.equals(templateFile, other.templateFile))
			return false;
		if (templateFileName == null) {
			if (other.templateFileName != null)
				return false;
		} else if (!templateFileName.equals(other.templateFileName))
			return false;
		if (templateType != other.templateType)
			return false;
		return true;
	}

	public Boolean getOutputToFile() {
		return outputToFile;
	}

	public void setOutputToFile(Boolean outputToFile) {
		this.outputToFile = outputToFile;
	}

	public Boolean getSendViaEmail() {
		return sendViaEmail;
	}

	public void setSendViaEmail(Boolean sendViaEmail) {
		this.sendViaEmail = sendViaEmail;
	}

}
