package binky.reportrunner.service.misc;

public class JDBCDriverDefinition {
	public JDBCDriverDefinition() {
		
	}
	public JDBCDriverDefinition(String label, String driverName, String url) {
		this.label = label;
		this.driverName = driverName;
		this.url = url;
	}
	private String label;
	private String driverName;
	private String url;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return this.label;
	}
	
	
	
	
}