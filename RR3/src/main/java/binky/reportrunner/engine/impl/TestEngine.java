package binky.reportrunner.engine.impl;

import binky.reportrunner.engine.RunnerEngineAbstract;
import binky.reportrunner.engine.RunnerException;

public class TestEngine extends RunnerEngineAbstract {

	@Override
	public String getConfigMessage() {
		return "This is an engine used in the unit tests";
	}

	@Override
	public String getEngineName() {	
		return "TestEngine";
	}

	@Override
	protected void runReport() throws RunnerException {
		System.out.println("Test Engine");
	}

}
