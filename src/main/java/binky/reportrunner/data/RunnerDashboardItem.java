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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import org.apache.commons.beanutils.RowSetDynaClass;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class RunnerDashboardItem implements Serializable {

	private static final long serialVersionUID = -4719560825938162696L;

	@Id
	@GeneratedValue
	private Integer id;

	private String alertName;

	@ManyToOne
	private RunnerGroup group;

	private Date lastUpdated;

	private String alertQuery;

	private String cronTab;

	private Width width;
	private Height height;
	// seconds to refresh the widget
	private long visualRefreshTime = 60000;

	@ManyToOne
	private RunnerDataSource datasource;

	private Integer displayRow = 1;
	private Integer displayColumn = 1;
	private RowSetDynaClass currentDataset;

	private String numberFormat = "###0.000";

	private String backGroundColour = "#FFFFFF";

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

	public String toString() {
		return id + alertName + alertQuery + cronTab;
	}

	public Integer getDisplayRow() {
		return displayRow;
	}

	public void setDisplayRow(Integer displayRow) {
		this.displayRow = displayRow;
	}

	public Integer getDisplayColumn() {
		return displayColumn;
	}

	public void setDisplayColumn(Integer displayColumn) {
		this.displayColumn = displayColumn;
	}

	public Width getWidth() {
		return width;
	}

	public void setWidth(Width width) {
		this.width = width;
	}

	public Height getHeight() {
		return height;
	}

	public void setHeight(Height height) {
		this.height = height;
	}

	public String getNumberFormat() {
		if ((numberFormat != null) && (!numberFormat.isEmpty())) {
			return numberFormat;
		} else {
			return "###0.000";
		}
	}

	public void setNumberFormat(String numberFormat) {
		this.numberFormat = numberFormat;
	}

	public String getBackGroundColour() {
		if ((backGroundColour != null) && (!backGroundColour.isEmpty())) {
			return backGroundColour;
		} else {
			return "#FFFFFF";
		}
	}

	public void setBackGroundColour(String backGroundColour) {
		this.backGroundColour = backGroundColour;
	}

	public long getVisualRefreshTime() {
		return visualRefreshTime;
	}

	public void setVisualRefreshTime(long visualRefreshTime) {
		this.visualRefreshTime = visualRefreshTime;
	}

	public enum Width {

		Small("Small"), Medium("Medium"), Large("Large");
		private String displayName;

		Width(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

	}

	public enum Height {

		Small("Small"), Medium("Medium"), Large("Large");
		private String displayName;

		Height(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

	}

}
