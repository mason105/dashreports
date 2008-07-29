package binky.reportrunner.data;


public class RunnerDataSource {


	private String jndiName;
	private String jdbcClass;
	private String jdbcUrl;
	private String username;
	private String password;
	

	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getJdbcClass() {
		return jdbcClass;
	}
	public void setJdbcClass(String jdbcClass) {
		this.jdbcClass = jdbcClass;
	}
	public String getJndiName() {
		return jndiName;
	}
	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}	
}
