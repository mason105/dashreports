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
 * Module: GetDashboardChartDataAction.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.dashboard;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerDashboardChart;
import binky.reportrunner.ui.actions.dashboard.base.BaseDashboardAction;

public class GetDashboardChartDataAction extends BaseDashboardAction {

	private static final long serialVersionUID = 2460177730972381778L;


	private RunnerDashboardChart chart;
	private Integer itemId;
	private Map<String,Map<String,Number>> values;


	
	private static final Logger logger = Logger
			.getLogger(GetDashboardChartDataAction.class);

	private String seriesFilter;

	@Override
	public String execute() throws Exception {
		chart=(RunnerDashboardChart)super.getDashboardService().getItem(itemId);
		values=new HashMap<String,Map<String,Number>>();		
		RowSetDynaClass rs= chart.getCurrentDataset();			
		
		for (Object o : rs.getRows()) {
			DynaBean b = (DynaBean) o;
			String seriesName=chart.getSeriesNameColumn()==null?"VALUE":(String)b.get(chart.getSeriesNameColumn());
			Number value= BigDecimal.valueOf(Double.parseDouble((String)b.get(chart.getValueColumn())));
			String xValue = (String)b.get(chart.getXaxisColumn());
			
			if (values.get(seriesName)!=null) {
				//store into existing series
				values.get(seriesName).put(xValue, value);
			} else {
				//create new entry
				Map<String,Number> entry = new HashMap<String, Number>();
				entry.put(xValue, value);
				values.put(seriesName, entry);
			}
		}
		return SUCCESS;
	}



	public String getSeriesFilter() {
		return seriesFilter;
	}

	public void setSeriesFilter(String seriesFilter) {
		this.seriesFilter = seriesFilter;
	}

}
