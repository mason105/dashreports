package binky.reportrunner.dao;

import java.util.List;

import binky.reportrunner.data.RunnerJobParameter;

public interface RunnerJobParameterDao {

	public void updateParametersForJob(String jobName, String groupName, List<RunnerJobParameter> parameters);
	
}
