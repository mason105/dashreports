package binky.reportrunner.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.beanutils.RowSetDynaClass;

import binky.reportrunner.engine.renderers.ChartRenderer.ChartType;

@Entity
public class RunnerDashboardAlert implements Serializable {

	private static final long serialVersionUID = -4719560825938162696L;


	public enum DisplayType {

		CHART("Chart"), GRID("Data Grid");
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

	private Date lastUpdated;

	private String alertQuery;

	private String cronTab;

	private String xaxisColumn;

	@ManyToOne
	private RunnerDataSource datasource;

	private DisplayType displayType;

	private ChartType chartType;

	private Integer displayRow=1;
	
	private RowSetDynaClass currentDataset;


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


	public ChartType getChartType() {
		return chartType;
	}


	public void setChartType(ChartType chartType) {
		this.chartType = chartType;
	}


	public String getCronTab() {
		return cronTab;
	}


	public void setCronTab(String cronTab) {
		this.cronTab = cronTab;
	}


	public RowSetDynaClass getCurrentDataset() {
		return currentDataset;
	}


	public void setCurrentDataset(RowSetDynaClass currentDataset) {
		this.currentDataset = currentDataset;
	}


	public RunnerDataSource getDatasource() {
		return datasource;
	}


	public void setDatasource(RunnerDataSource datasource) {
		this.datasource = datasource;
	}


	public DisplayType getDisplayType() {
		return displayType;
	}


	public void setDisplayType(DisplayType displayType) {
		this.displayType = displayType;
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


	public Date getLastUpdated() {
		return lastUpdated;
	}


	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}


	
	public String getXaxisColumn() {
		return xaxisColumn;
	}


	public void setXaxisColumn(String xaxisColumn) {
		this.xaxisColumn = xaxisColumn;
	}


	public String toString() {
		return id+alertName+alertQuery+cronTab;
	}


	public Integer getDisplayRow() {
		return displayRow;
	}


	public void setDisplayRow(Integer displayRow) {
		this.displayRow = displayRow;
	}


	
	
}
