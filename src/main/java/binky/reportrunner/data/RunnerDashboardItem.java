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

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "T_ITEM")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries( {
	@NamedQuery(name = "getItemsByGroup", query = "from T_ITEM i where i.group.groupName = ?")
})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public abstract class RunnerDashboardItem extends DatabaseObject<Integer> {

	public Integer getId() {
		return itemId;
	}

	private static final long serialVersionUID = -4719560825938162696L;

	@Id
	@GeneratedValue
	private Integer itemId;

	private String itemName;

	@ManyToOne
	private RunnerGroup group;

	private Date lastUpdated;

	private String alertQuery;

	private String cronTab="0 0 12 * * ?";

	private Width width;
	private Height height;
	// seconds to refresh the widget
	private long visualRefreshTime = 60000;

	@ManyToOne
	private RunnerDataSource datasource;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="item" ,orphanRemoval=true)
	@OrderBy("rowNumber")
	private Set<DashboardData> data;
	
	private Integer displayRow = 1;
	private Integer displayColumn = 1;
	
	private String numberFormat = "###0.000";

	private String backGroundColour = "#FFFFFF";



	public final String getItemName() {
		return itemName;
	}

	public final void setItemName(String itemName) {
		this.itemName = itemName;
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


	public final Integer getItemId() {
		return itemId;
	}

	public final void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String toString() {
		return itemId + itemName + alertQuery + cronTab;
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

	public enum ItemType {

		Chart("Chart"), Grid("Grid"), Threshold("Threshold"), Sampler("Sampler");
		private String displayName;

		ItemType(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}

	}

	public abstract ItemType getItemType();

	public Set<DashboardData> getData() {
		return data;
	}

	public void setData(Set<DashboardData> data) {
		this.data = data;
	}
		

}
