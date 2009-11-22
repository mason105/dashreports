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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;

import binky.ofc2plugin.charts.OFC2Chart;
import binky.ofc2plugin.charts.config.TextItem;
import binky.ofc2plugin.charts.config.XAxis;
import binky.ofc2plugin.charts.config.YAxis;
import binky.ofc2plugin.charts.elements.Element;
import binky.ofc2plugin.charts.elements.ElementAreaLine;
import binky.ofc2plugin.charts.elements.ElementPie;
import binky.ofc2plugin.charts.elements.ElementStandardBar;
import binky.ofc2plugin.charts.elements.ElementStandardLine;
import binky.ofc2plugin.charts.elements.NormalValues;
import binky.ofc2plugin.charts.elements.ElementStandardBar.BarType;
import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.data.RunnerDashboardAlert.ChartType;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class GetDashboardChartDataAction extends StandardRunnerAction {

	private static final long serialVersionUID = 2460177730972381778L;

	private DashboardService dashboardService;

	private Integer alertId;

	private String data;

	private static final Logger logger = Logger
			.getLogger(GetDashboardChartDataAction.class);

	private String seriesFilter;

	@Override
	public String execute() throws Exception {
		RunnerDashboardAlert alert = dashboardService.getAlert(alertId);

		// noddy security check
		if (!doesUserHaveGroup(alert.getGroup().getGroupName())) {
			return ERROR;
		}

		Number max = 0;
		RowSetDynaClass data = alert.getCurrentDataset();
		Map<String, List<Number>> dataMap = new HashMap<String, List<Number>>();

		List<Object> xLabels = new LinkedList<Object>();
		for (Object o : data.getRows()) {
			DynaBean b = (DynaBean) o;
			String label = b.get(alert.getXaxisColumn()).toString();
			if (!xLabels.contains(label)) {
				xLabels.add(label);
			}
		}

		List<String> series = new LinkedList<String>();
		for (Object o : data.getRows()) {
			DynaBean b = (DynaBean) o;
			String seriesName;
			if ((alert.getSeriesNameColumn() == null)
					|| (alert.getSeriesNameColumn().isEmpty())) {
				seriesName = "VALUE";
			} else {
				seriesName = (String) b.get(alert.getSeriesNameColumn());
			}
			if (!series.contains(seriesName)) {
				series.add(seriesName);
			}
		}

		// hack to fill in the gaps in the x series
		for (String s : series) {
			for (Object x : xLabels) {
				boolean found = false;

				for (Object o : data.getRows()) {
					DynaBean b = (DynaBean) o;
					String label = b.get(alert.getXaxisColumn()).toString();
					String seriesName;
					if ((alert.getSeriesNameColumn() == null)
							|| (alert.getSeriesNameColumn().isEmpty())) {
						seriesName = "VALUE";
					} else {
						seriesName = "" + b.get(alert.getSeriesNameColumn());
					}
					Number value;

					if (label.equals(x)
							&& (seriesName.equals(s) || (((alert
									.getSeriesNameColumn() == null) || (alert
									.getSeriesNameColumn().isEmpty()))))) {
						
						value = BigDecimal.valueOf(Double.parseDouble(b.get(
								alert.getValueColumn()).toString()));

						found = true;
						if (value.floatValue() > max.floatValue()) {
							max = value;
						}

						List<Number> values = dataMap.get(s);

						if (values == null) {
							values = new LinkedList<Number>();
						}
						values.add(value);

						dataMap.put(s, values);
						break;
					}
				}

				if (!found) {
					List<Number> values = dataMap.get(s);

					if (values == null) {
						values = new LinkedList<Number>();
					}
					values.add(0);

					dataMap.put(s, values);
				}

			}
		}

		int c = 15;
		int y = 1;

		OFC2Chart chart = new OFC2Chart();

		for (String elementName : dataMap.keySet()) {
			List<Number> d = dataMap.get(elementName);

			/* hack for colours */

			String mainHex1 = Integer.toHexString(c);

			String mainHex = "";
			switch (y) {
			case 1:
				mainHex = "#" + mainHex1 + mainHex1 + "0000";
				break;
			case 2:
				mainHex = "#" + "00" + mainHex1 + mainHex1 + "00";
				break;
			case 3:
				mainHex = "#" + "0000" + mainHex1 + mainHex1;
				break;
			case 4:
				mainHex = "#" + mainHex1 + mainHex1 + mainHex1 + mainHex1
						+ "00";
				break;
			case 5:
				mainHex = "#" + mainHex1 + mainHex1 + "00" + mainHex1
						+ mainHex1;
				break;
			default:
				y = 0;
				c = c - 3;
			}

			y++;
			if (c < 0) {
				c = 15;
			}
			logger.debug("colouring series " + elementName + "  with: "
					+ mainHex);
			/* end hack */

			Element e;

			switch (alert.getChartType()) {
			case AREA:
				e = new ElementAreaLine();
			case BAR:
				e = new ElementStandardBar();
				((ElementStandardBar) e).setBarType(BarType.Glass);
				break;
			case LINE:
				e = new ElementStandardLine();
				break;
			case PIE:
				e = new ElementPie();
				List<String> colours = new LinkedList<String>();
				int a = 15;
				int x = 1;
				for (Number val : d) {
					logger.trace("value: " + val);
					String hex1 = Integer.toHexString(a);

					String hex = "";
					switch (x) {
					case 1:
						hex = "#" + hex1 + hex1 + "0000";
						break;
					case 2:
						hex = "#" + "00" + hex1 + hex1 + "00";
						break;
					case 3:
						hex = "#" + "0000" + hex1 + hex1;
						break;
					case 4:
						hex = "#" + hex1 + hex1 + hex1 + hex1 + "00";
						break;
					case 5:
						hex = "#" + hex1 + hex1 + "00" + hex1 + hex1;
						break;
					default:
						x = 0;
						a = a - 3;
					}
					colours.add(hex);

					x++;
					if (a < 0) {
						a = 15;

					}

				}
				((ElementPie) e).setColoursHex(colours);
				break;
			default:
				e = new ElementStandardBar();
				((ElementStandardBar) e).setBarType(BarType.Standard);
				break;
			}
			
			if (alert.getChartType() != ChartType.PIE) {
				((NormalValues)e).addValues(d);
			} else {
				//pie chart hack
				int pos=0;
				for (Object label : xLabels) {
					((ElementPie) e).addPieValue(label.toString(), d.get(pos));					
					pos++;
				}
			}
			
			e.setColourHex(mainHex);
			chart.addElement(e);
			e.setText(elementName);
			break;

		}

		chart.setTitle(new TextItem(""));
		XAxis xAxis = new XAxis();
		xAxis.setLabels(xLabels);
		chart.setXAxis(xAxis);
		YAxis yAxis = new YAxis();
		yAxis.setMax(max);
		chart.setYAxis(yAxis);
		chart.setBackGroundColour(alert.getBackGroundColour());

		this.data = chart.encodeJSONString();

		return SUCCESS;
	}

	public Integer getAlertId() {
		return alertId;
	}

	public void setAlertId(Integer alertId) {
		this.alertId = alertId;
	}

	public DashboardService getDashboardService() {
		return dashboardService;
	}

	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSeriesFilter() {
		return seriesFilter;
	}

	public void setSeriesFilter(String seriesFilter) {
		this.seriesFilter = seriesFilter;
	}

}
