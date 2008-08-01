package binky.reportrunner.engine.impl;

import java.util.HashMap;

import binky.reportrunner.engine.RunnerEngineAbstract;
import binky.reportrunner.engine.RunnerException;

@SuppressWarnings("unchecked")
public class BasicSQLEngine extends RunnerEngineAbstract {

	{
		parameters = new HashMap<String, Class>();
		parameters.put("sqlQuery", String.class);
	}
	
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
