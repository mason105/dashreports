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
 * Module: SaveJob.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.job.edit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.jasperreports.engine.JRException;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerJobParameterDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.scheduler.SchedulerException;

public class SaveJob  extends BaseEditJob  {

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(SaveJob.class);
	private RunnerJobParameterDao parameterDao;
	@Override
	public String execute() throws Exception {

		this.groupName = job.getPk().getGroup().getGroupName();
		String jobName = job.getPk().getJobName();

		
		//stuff to allow the sql validation
		this.job.setQuery(query);
		this.job.setBurstQuery(burstQuery);
		
		RunnerDataSource ds = dataSourceDao.getDataSource(dataSourceName);
		job.setDatasource(ds);
		
		if (groupName != null && !groupName.isEmpty()
				&& (jobName != null && !jobName.isEmpty())) {
			// security check
			if (doesUserHaveGroup(groupName) && !isUserReadOnly()) {
				
					logger.debug("dispatching to save job");
					
					boolean ok = validateJob(job);
											
					if (!ok) {
						return INPUT; 
					} else {
						doSaveJob(jobName, groupName);
						return SUCCESS;
					}
				

			} else {
				SecurityException se = new SecurityException("Group "
						+ groupName + " not valid for user "
						+ super.getSessionUser().getUserName());
				logger.fatal(se.getMessage(), se);
				throw se;
			}

		} else {
			logger.error("groupName or jobName missing");
			super.addActionError("Job Name missing");
			return INPUT;
		}
		
	}


	private boolean validateJob(RunnerJob job) {
		boolean valid=true;
		
		if (job.getPk()==null)  {
			super.addActionError("Error with job definition - name and group were not set!");
			valid=false;
		};
		if ((job.getPk().getJobName()==null)||(job.getPk().getJobName().trim().isEmpty()))  {
			super.addActionError("Job name not set");
			valid=false;
		}		
		if ((job.getPk().getGroup()==null)||(job.getPk().getGroup().getGroupName()==null)||(job.getPk().getGroup().getGroupName().trim().isEmpty()))  {
			super.addActionError("Group name not set");
			valid=false;
		}
		if ((job.getQuery()==null)||(job.getQuery().trim().isEmpty())) {
			super.addActionError("Query not set");
			valid=false;
		}
		
		return valid;
	}
	
	private boolean doSaveJob(String jobName, String groupName)
			throws JRException, SchedulerException {
		this.activeTab = "report";
		/*
		//make the cron string
		if ((job.getCronString()==null)||(job.getCronString().trim().length()==0)) {
			//use the simple cron stuff
			if (simpleCron==null) {
				simpleCron = new QuartzCronSchedule();
			}
			job.setCronString(simpleCron.toString());
		}
		*/
		// Get the uploaded File 
		if (logger.isDebugEnabled()) {
			logger.debug("file uploaded is: " + templateFileName);
			logger.debug("upload object is null: "+ (template==null));
			if (template !=null){
				logger.debug("upload object is file: " + template.isFile());
				logger.debug("upload object exists: " + template.exists());
			}
		}
		if ((template != null) && template.isFile() && template.exists()) {
			
			try {
				byte[] file = getBytesFromFile(template);
				job.setTemplateFile(file);
				job.setTemplateFileName(templateFileName);
			} catch (IOException e) {
				logger.warn(e.getMessage(),e);
				super.addActionError(e.getMessage());
				return false;
			}
			
		} else {
			//hack to preserve template file
			RunnerJob job2=jobService.getJob(jobName, groupName);
			if ((job2!=null) && (job2.getTemplateFile()!=null)) {
					job.setTemplateFile(job2.getTemplateFile().clone());				
			}
		}
		// part of my hack work :(
		job.setParameters(null);
		jobService.addUpdateJob(job);
		// hack to do the tabular stuff with parameters
		if (parameters != null) {

			logger.debug("parameter count:" + parameters.size());

			for (RunnerJobParameter p : this.parameters) {
				if (p != null) {
					logger.debug(p.getParameterValue());
					p.getPk().setRunnerJob_pk(job.getPk());
				} else {
					logger.warn("null parameter");
				}
			}
			parameterDao.updateParametersForJob(jobName, groupName, parameters);
		}
		return true;
	}


	// Returns the contents of the file in a byte array.
	private byte[] getBytesFromFile(File file) throws IOException {
		
		//if the file is null then return a null byte array to show this
		if (file==null) {
			logger.warn("getBytesFromFile called with null file object");
			return null;
		}
		
		logger.debug("getBytesFromFile called for: " + file.getName());
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();
		logger.debug("file len: " + length);
		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			IOException e = new IOException("Could not completely read file "
					+ file.getName());
			logger.error("error reading file", e);
			throw e;
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}
/*
	public QuartzCronSchedule getSimpleCron() {
		return simpleCron;
	}

	public void setSimpleCron(QuartzCronSchedule simpleCron) {
		this.simpleCron = simpleCron;
	}
	*/
	
	public RunnerJobParameterDao getParameterDao() {
		return parameterDao;
	}

	public void setParameterDao(RunnerJobParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}
	
}