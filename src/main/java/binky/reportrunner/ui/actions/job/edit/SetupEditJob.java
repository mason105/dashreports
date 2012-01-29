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
 * Module: SetupEditJob.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.job.edit;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJobParameter.DataType;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.ui.util.QuartzCronSchedule;

public class SetupEditJob extends BaseEditJob {

	private static final long serialVersionUID = 1L;
	
	
	private Integer paramCount;
	private QuartzCronSchedule simpleCron=new QuartzCronSchedule();

	private static final Logger logger = Logger.getLogger(SetupEditJob.class);

	

	// TODO:clean up this mess
	@Override
	public String execute() throws Exception {
		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (super.doesUserHaveGroup(groupName) && !isUserReadOnly()) {
				job = jobService.getJob(jobName, groupName);
				if (job.getParameters() == null) {
					paramCount = 0;
				} else {
					paramCount = job.getParameters().size();
				}
				simpleCron=new QuartzCronSchedule(job.getCronString());
				logger.debug("cron is set to: "+ simpleCron);
				if (job.getDatasource() != null) {
					dsName=job.getDatasource().getDataSourceName();
				}
				if (StringUtils.isNotEmpty(job.getOutputUrl())){
					String[] outSplit = job.getOutputUrl().split("://");
					outputPrefix=outSplit[0];
					outputUrl=outSplit[1];
				}
				
			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getSessionUser().getUserName());
				logger.fatal(se.getMessage(), se);
				throw se;
			}

		} else if (groupName != null && !groupName.isEmpty()) {
			job = new RunnerJob();
			paramCount = 0;
		} else {
			SecurityException se = new SecurityException("Group not passed");
			logger.fatal(se.getMessage(), se);
		}
		super.setDataSources(this.dataSourceService.getDataSourcesForGroup(groupName));
		return SUCCESS;
	}




	public Integer getParamCount() {
		return paramCount;
	}

	public List<RunnerJob.FileFormat> getFileFormats() {
		return Arrays.asList(RunnerJob.FileFormat.values());
	}
	

	public List<RunnerJob.Template> getTemplateTypes() {
		return Arrays.asList(RunnerJob.Template.values());
	}
	
	public List<DataType> getDataTypes() {
		return Arrays.asList(RunnerJobParameter.DataType.values());
	}





	public QuartzCronSchedule getSimpleCron() {
		return simpleCron;
	}




	public void setSimpleCron(QuartzCronSchedule simpleCron) {
		this.simpleCron = simpleCron;
	}




	
}
