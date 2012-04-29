package binky.reportrunner.ui.actions;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class About extends StandardRunnerAction {

	private String versionId;
	private static final long serialVersionUID = -2445708209232186033L;

	private float totalMem;
	private float freeMem;
	private int processors;
	private float maxMem;
	private String javaVersion;
	private String databaseSchemaName;
	private DataSource dataSource;
	private Map<String,String> databaseProperties;
	@Override
	public String execute() throws Exception {
		
		Runtime runTime= Runtime.getRuntime();
		float mb=(1024*1024);
		totalMem=runTime.totalMemory()/mb;
		freeMem=runTime.freeMemory()/mb;
		processors=runTime.availableProcessors();
		maxMem=runTime.maxMemory()/mb;
		javaVersion=System.getProperty("java.version")  + " (" + System.getProperty("java.vendor") + ")";
		Connection conn=dataSource.getConnection();
		databaseSchemaName=conn.getCatalog();
		
		DatabaseMetaData meta= conn.getMetaData();
		
		databaseProperties=new HashMap<String, String>();
		
		databaseProperties.put("JDBC Driver", meta.getDriverName()+ " (" + meta.getJDBCMajorVersion() + "." + meta.getJDBCMinorVersion()+")");
		databaseProperties.put("Database URL", meta.getURL());
		databaseProperties.put("Database Server:", meta.getDatabaseProductName() + " (" + meta.getDatabaseProductVersion()+ ")");		
		conn.close();
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

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getDatabaseSchemaName() {
		return databaseSchemaName;
	}

	public Map<String,String> getDatabaseProperties() {
		return databaseProperties;
	}



}
