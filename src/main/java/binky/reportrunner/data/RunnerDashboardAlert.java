/*******************************************************************************
 * Copyright (c) 2009 Daniel Grout.
 * 
 * GNU GENERAL PUBLIC LICENSE - Version 3
 * 
 * This file is part of Report Runner (http://code.google.com/p/reportrunner).
 * 
 * Report Runner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Runner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Runner. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Module: RunnerDashboardAlert.java
 ******************************************************************************/
package binky.reportrunner.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.beanutils.RowSetDynaClass;

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

	public enum XAxisStep {

		One("One"), Two("Two"), Four("Four"), Eight("Eight"), Sixteen(""), ThirtyTwo("Thirty Two");
		private String displayName;

		XAxisStep(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

	}
	
	public enum ChartSize {

		Small("Small"), Medium("Medium"), Large("Large");
		private String displayName;

		ChartSize(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

	}
	public enum ChartType {

		/*DIAL("Dial"),*/ 
		LINE("Line Graph"), BAR("Bar Chart"), AREA("Area Graph"), PIE(
				"Pie Chart");

		private String displayName;

		ChartType(String displayName) {
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
	private String yLabel;
	private String valueColumn;
	private String seriesNameColumn;
	private String xaxisColumn;
	private XAxisStep xAxisStep;
	private ChartSize chartSize;
	
	@ManyToOne
	private RunnerDataSource datasource;

	private DisplayType displayType;
	
	private ChartType chartType;

	private Integer displayRow=1;
	private Integer displayColumn=1;
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


	public String getValueColumn() {
		return valueColumn;
	}


	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
	}


	public String getSeriesNameColumn() {
		return seriesNameColumn;
	}


	public void setSeriesNameColumn(String seriesNameColumn) {
		this.seriesNameColumn = seriesNameColumn;
	}


	public ChartSize getChartSize() {
		return chartSize;
	}


	public void setChartSize(ChartSize chartSize) {
		this.chartSize = chartSize;
	}


	public String getYLabel() {
		return yLabel;
	}


	public void setYLabel(String label) {
		yLabel = label;
	}


	public Integer getDisplayColumn() {
		return displayColumn;
	}


	public void setDisplayColumn(Integer displayColumn) {
		this.displayColumn = displayColumn;
	}


	public XAxisStep getXAxisStep() {
		return xAxisStep;
	}


	public void setXAxisStep(XAxisStep axisStep) {
		xAxisStep = axisStep;
	}


	
	
}
