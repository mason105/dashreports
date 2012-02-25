package binky.reportrunner.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "T_CONFIG")
public class Configuration extends
		DatabaseObject<Configuration.ConfigurationType> {

	private static final long serialVersionUID = 4948192855616891674L;


	//idea is that new configuration items can be added here and the UI will automatically generate the boxes for them.
	public enum ConfigurationType implements Serializable {

		EMAIL_FROM_ADDRESS("Email From Address", 1), EMAIL_SERVER(
				"Email Server Address", 1), AUDIT_PURGE_DAYS(
				"Days to Hold Audit Information", 2), YOUR_NAME("Your Name",1),ADMIN_EMAIL("Administrator's Email Address",1),LOGO("Logo", 3);

		private String displayName;
		private int dataType; // (1=string,2==numeric,3=binary)

		ConfigurationType(String displayName, int dataType) {
			this.dataType = dataType;
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

		public int getDataType() {
			return dataType;
		}

	}

	@Id
	@Column(name = "configurationType")
	private ConfigurationType type;
	@Column(name = "configurationValue")
	private String value;
	private byte[] binaryValue;

	@Override
	public ConfigurationType getId() {
		return type;
	}

	@Override
	public int hashCode() {
		return (type.getDisplayName() + value).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean eq = false;
		if (obj != null && obj instanceof Configuration) {
			Configuration c = (Configuration) obj;
			if (type != null && c.getType() != null)
				eq = type.equals(c.getType());
			if (value != null && c.getValue() != null)
				eq = value.equals(c.getValue());
		}
		return eq;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Configuration() {

	}

	public ConfigurationType getType() {
		return type;
	}

	public void setType(ConfigurationType type) {
		this.type = type;
	}

	public byte[] getBinaryValue() {
		return binaryValue;
	}

	public void setBinaryValue(byte[] binaryValue) {
		this.binaryValue = binaryValue;
	}

	public Configuration(ConfigurationType type, String value) {
		this.type = type;
		this.value = value;

	}
	public Configuration(ConfigurationType type) {
		this.type = type;
	}
	public Configuration(ConfigurationType type, byte[] binaryValue) {
		this.type = type;
		this.binaryValue = binaryValue;

	}
}
