package binky.reportrunner.engine.impl;

import binky.reportrunner.engine.RunnerEngineAbstract;
import binky.reportrunner.engine.RunnerException;

public class BasicSQLEngine extends RunnerEngineAbstract {

	@Override
	public String getConfigMessage() {
		return "This is a basic SQL query runner.  The only parameters are SQL statement and output file";
	}

	@Override
	public String getEngineName() {
		return "Basic SQL Runner";
	}

	@Override
	protected void runReport() throws RunnerException {
		// TODO Auto-generated method stub

	}

}
