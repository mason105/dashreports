package binky.reportrunner.ui.actions.job.edit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import net.sf.jasperreports.engine.JRException;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerJobParameterDao;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJobParameter_pk;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.scheduler.SchedulerException;

public class EditJob extends BaseEditJob {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EditJob.class);

	private RunnerJobParameterDao parameterDao;

	private int[] parameterId;
	private String deleteParameters;
	private String addParameter;
	private String saveJob;

	@Override
	public String execute() throws Exception {
		if (isStringPopulated(deleteParameters)) {
			logger.debug("delete parameters");
			return deleteParameters();
		} else if (isStringPopulated(addParameter)) {
			logger.debug("add parameter");
			return addParameter();
		} else {
			logger.debug("save job");
			return saveJob();
		}
	}

	private String addParameter() {
		logger.debug("adding parameter to job " + jobName + " of group " + groupName);
		if (parameters == null) {
			logger.debug("parameters are null so creating new list");
			parameters = new LinkedList<RunnerJobParameter>();
		}

		RunnerJobParameter parameter = new RunnerJobParameter();
		RunnerJobParameter_pk pk = new RunnerJobParameter_pk();

		// pk.setParameterIdx(maxIdx);
		pk.setParameterIdx(parameters.size() + 1);
		parameter.setPk(pk);
		logger.debug("created new parameter with index of: "
				+ (parameters.size() + 1));
		parameters.add(parameter);
		job.setParameters(parameters);
		return INPUT;
	}

	private String deleteParameters() {
		for (int paramIdx : parameterId) {
			logger.debug("removing parameter:" + paramIdx + " for job "
					+ jobName + " of group " + groupName);
			parameters.remove(paramIdx);
		}
		job.setParameters(parameters);
		return INPUT;
	}
	
	private String saveJob() throws JRException, SchedulerException,
			SecurityException {
		this.groupName = job.getPk().getGroup().getGroupName();
		String jobName = job.getPk().getJobName();

		// stuff to allow the sql validation
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
		boolean valid = true;

		if (job.getPk() == null) {
			super.addActionError("Error with job definition - name and group were not set!");
			valid = false;
		}
		;
		if ((job.getPk().getJobName() == null)
				|| (job.getPk().getJobName().trim().isEmpty())) {
			super.addActionError("Job name not set");
			valid = false;
		}
		if ((job.getPk().getGroup() == null)
				|| (job.getPk().getGroup().getGroupName() == null)
				|| (job.getPk().getGroup().getGroupName().trim().isEmpty())) {
			super.addActionError("Group name not set");
			valid = false;
		}
		if ((job.getQuery() == null) || (job.getQuery().trim().isEmpty())) {
			super.addActionError("Query not set");
			valid = false;
		}

		return valid;
	}

	private boolean doSaveJob(String jobName, String groupName)
			throws JRException, SchedulerException {
		this.activeTab = "report";
	
		// Get the uploaded File
		if (logger.isDebugEnabled()) {
			logger.debug("file uploaded is: " + templateFileName);
			logger.debug("upload object is null: " + (template == null));
			if (template != null) {
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
				logger.warn(e.getMessage(), e);
				super.addActionError(e.getMessage());
				return false;
			}

		} else {
			// hack to preserve template file
			RunnerJob job2 = jobService.getJob(jobName, groupName);
			if ((job2 != null) && (job2.getTemplateFile() != null)) {
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

		// if the file is null then return a null byte array to show this
		if (file == null) {
			logger.warn("getBytesFromFile called with null file object");
			return null;
		}

		logger.debug("getBytesFromFile called for: " + file.getName());
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();
		logger.debug("file len: " + length);

		if (length > Integer.MAX_VALUE) {
			throw new IOException("file too large");
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
	 * public QuartzCronSchedule getSimpleCron() { return simpleCron; }
	 * 
	 * public void setSimpleCron(QuartzCronSchedule simpleCron) {
	 * this.simpleCron = simpleCron; }
	 */

	public RunnerJobParameterDao getParameterDao() {
		return parameterDao;
	}

	public void setParameterDao(RunnerJobParameterDao parameterDao) {
		this.parameterDao = parameterDao;
	}



	public int[] getParameterId() {
		return parameterId;
	}

	public void setParameterId(int[] parameterId) {
		this.parameterId = parameterId;
	}

	public String getDeleteParameters() {
		return deleteParameters;
	}

	public void setDeleteParameters(String deleteParameters) {
		this.deleteParameters = deleteParameters;
	}

	public String getAddParameter() {
		return addParameter;
	}

	public void setAddParameter(String addParameter) {
		this.addParameter = addParameter;
	}

	public String getSaveJob() {
		return saveJob;
	}

	public void setSaveJob(String saveJob) {
		this.saveJob = saveJob;
	}

	

}
