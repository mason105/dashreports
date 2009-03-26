package binky.reportrunner.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@Entity
public class RunnerDataSource implements Serializable {

	private static final long serialVersionUID = 7230871954372088509L;

	@Id
	private String dataSourceName;

	public RunnerDataSource() {
	};

	public RunnerDataSource(String dataSourceName, String jndiName,
			String jdbcClass, String jdbcUrl, String username, String password,
			Integer initialPoolSize, Integer minPoolSize, Integer maxPoolSize) {
		super();
		this.dataSourceName = dataSourceName;
		this.jndiName = jndiName;
		this.jdbcClass = jdbcClass;
		this.jdbcUrl = jdbcUrl;
		this.username = username;
		this.password = password;
		this.initialPoolSize = initialPoolSize;
		this.minPoolSize = minPoolSize;
		this.maxPoolSize = maxPoolSize;
	}

	private String jndiName;

	private String jdbcClass;

	private String jdbcUrl;

	private String username;

	private String password;

	private Integer initialPoolSize=3;

	private Integer minPoolSize=2;

	private Integer maxPoolSize=5;

	@RequiredStringValidator
	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

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

	public Integer getInitialPoolSize() {
		return initialPoolSize;
	}

	public void setInitialPoolSize(Integer intialPoolSize) {
		this.initialPoolSize = intialPoolSize;
	}

	public Integer getMinPoolSize() {
		return minPoolSize;
	}

	public void setMinPoolSize(Integer minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public Integer getMaxPoolSize() {
		return maxPoolSize;
	}

	public void setMaxPoolSize(Integer maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	@Override
	public String toString() {
		return this.dataSourceName;
	}
}
