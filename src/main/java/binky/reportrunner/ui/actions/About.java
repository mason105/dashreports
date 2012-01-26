package binky.reportrunner.ui.actions;

import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class About extends StandardRunnerAction {

	private String versionId;
	
	private static final long serialVersionUID = -2445708209232186033L;

	private float totalMem;
	private float freeMem;
	private int processors;
	private float maxMem;
	private String javaVersion;
	
	@Override
	public String execute() throws Exception {
		
		Runtime runTime= Runtime.getRuntime();
		float mb=(1024*1024);
		totalMem=runTime.totalMemory()/mb;
		freeMem=runTime.freeMemory()/mb;
		processors=runTime.availableProcessors();
		maxMem=runTime.maxMemory()/mb;
		javaVersion=System.getProperty("java.version")  + " (" + System.getProperty("java.vendor") + ")";
		
		return SUCCESS;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public float getTotalMem() {
		return totalMem;
	}

	public void setTotalMem(float totalMem) {
		this.totalMem = totalMem;
	}

	public float getFreeMem() {
		return freeMem;
	}

	public void setFreeMem(float freeMem) {
		this.freeMem = freeMem;
	}

	public int getProcessors() {
		return processors;
	}

	public void setProcessors(int processors) {
		this.processors = processors;
	}

	public float getMaxMem() {
		return maxMem;
	}

	public void setMaxMem(float maxMem) {
		this.maxMem = maxMem;
	}

	public String getJavaVersion() {
		return javaVersion;
	}

	public void setJavaVersion(String javaVersion) {
		this.javaVersion = javaVersion;
	}



}
