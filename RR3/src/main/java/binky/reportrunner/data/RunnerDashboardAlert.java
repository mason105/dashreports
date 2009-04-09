package binky.reportrunner.data;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class RunnerDashboardAlert implements Serializable {

	private static final long serialVersionUID = -5476492059229671740L;

	public enum AlertType {

		GreaterThan("Positive Threshold"), LessThan("Negative Threshold");
		private String displayName;

		AlertType(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

	}

	public enum DisplayType {

		Basic("Basic"), Dial("Dial");
		private String displayName;

		DisplayType(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

	}

	
	@Id
	@GeneratedValue
	private Integer id;

	private String alertName;

	@ManyToOne
	private RunnerGroup group;
	
	private String subGroupName;
	
	//for use in line graphs on dash
	private Integer historicValues;
	
	private String alertQuery;

	private String cronTab;


	@ManyToOne
	private RunnerDataSource dataSource;

	private Integer lower;

	private Integer mid;

	private Integer upper;

	private AlertType alertType;
	
	private DisplayType displayType;
	
	private char unit;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<DashboardAlertData> collectedValues;

	public String getAlertName() {
		return alertName;
	}

	public void setAlertName(String alertName) {
		this.alertName = alertName;
	}

	public String getAlertQuery() {
		return alertQuery;
	}

	public void setAlertQuery(String alertQuery) {
		this.alertQuery = alertQuery;
	}

	public AlertType getAlertType() {
		return alertType;
	}

	public void setAlertType(AlertType alertType) {
		this.alertType = alertType;
	}

	public List<DashboardAlertData> getCollectedValues() {
		return collectedValues;
	}

	public void setCollectedValues(List<DashboardAlertData> collectedValues) {
		this.collectedValues = collectedValues;
	}

	public String getCronTab() {
		return cronTab;
	}

	public void setCronTab(String cronTab) {
		this.cronTab = cronTab;
	}

	public RunnerDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(RunnerDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public RunnerGroup getGroup() {
		return group;
	}

	public void setGroup(RunnerGroup group) {
		this.group = group;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLower() {
		return lower;
	}

	public void setLower(Integer lower) {
		this.lower = lower;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Integer getUpper() {
		return upper;
	}

	public void setUpper(Integer upper) {
		this.upper = upper;
	}

	public Integer getHistoricValues() {
		return historicValues;
	}

	public void setHistoricValues(Integer historicValues) {
		this.historicValues = historicValues;
	}

	public DisplayType getDisplayType() {
		return displayType;
	}

	public void setDisplayType(DisplayType displayType) {
		this.displayType = displayType;
	}

	public char getUnit() {
		return unit;
	}

	public void setUnit(char unit) {
		this.unit = unit;
	}

	public String getSubGroupName() {
		return subGroupName;
	}

	public void setSubGroupName(String subGroupName) {
		this.subGroupName = subGroupName;
	}

}
