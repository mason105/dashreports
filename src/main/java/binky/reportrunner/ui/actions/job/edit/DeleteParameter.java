package binky.reportrunner.ui.actions.job.edit;

import org.apache.log4j.Logger;

public class DeleteParameter extends BaseEditJob {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(DeleteParameter.class);
	private int[] parameterId;

	@Override
	public String execute() throws Exception {
		for (int paramIdx : parameterId) {
			logger.debug("removing parameter:" + paramIdx + " for job "
					+ jobName + " of group " + groupName);
			parameters.remove(paramIdx - 1);
		}
		job.setParameters(parameters);
		return SUCCESS;
	}

}
