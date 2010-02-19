package binky.reportrunner.ui.actions.job.edit;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJobParameter_pk;

public class AddParameter extends BaseEditJob {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(AddParameter.class);
	@Override
	public String execute() throws Exception {
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
				+ parameters.size() + 1);
		parameters.add(parameter);
		job.setParameters(parameters);
		return SUCCESS;
	}

}
