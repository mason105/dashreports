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
 * Module: ChartRenderer.java
 ******************************************************************************/
package binky.reportrunner.engine.renderers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import binky.reportrunner.engine.utils.FileSystemHandler;
import binky.reportrunner.engine.utils.impl.FileSystemHandlerImpl;

public class ChartRenderer {

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
	/**
	 * @param title
	 *            chart title
	 * @param data
	 *            chart data
	 * @param xAxisColumn -
	 *            name of x axis column
	 * @param chartType -
	 *            type of chart
	 * @return a string of UID for the chart file to be prefixed with _small or
	 *         _large and .tmp located in tmp://
	 * @throws IOException,
	 *             NumberFormatException
	 */
	public String renderChart(String title, RowSetDynaClass data,
			String xAxisColumn, binky.reportrunner.data.RunnerDashboardAlert.ChartType chartType) throws IOException,
			NumberFormatException {
		JFreeChart chart;
		switch (chartType) {
		/*case DIAL:
			chart = renderDial(title, data, xAxisColumn);
			break;*/
		case AREA:
			chart = renderArea(title, data, xAxisColumn);
			break;
		case BAR:
			chart = renderBar(title, data, xAxisColumn);
			break;
		case LINE:
			chart = renderXYLine(title, data, xAxisColumn);
			break;
		case PIE:
			chart = renderPie(title, data, xAxisColumn);
			break;
		default:
			chart = renderXYLine(title, data, xAxisColumn);
		}

		BufferedImage smallChart = chart.createBufferedImage(200, 150);
		BufferedImage largeChart = chart.createBufferedImage(800, 600);

		String uid = UUID.randomUUID().toString();

		FileSystemHandler fs = new FileSystemHandlerImpl();

		OutputStream osSmall = fs.getOutputStreamForUrl("tmp://" + uid
				+ "_small.tmp");
		OutputStream osLarge = fs.getOutputStreamForUrl("tmp://" + uid
				+ "_large.tmp");

		try {
			ChartUtilities.writeBufferedImageAsPNG(osSmall, smallChart);
			ChartUtilities.writeBufferedImageAsPNG(osLarge, largeChart);
		} finally {
			osSmall.close();
			osLarge.close();
		}

		return uid;
	}

	private JFreeChart renderXYLine(String title, RowSetDynaClass data,
			String xAxisColumn) throws NumberFormatException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Object o : data.getRows()) {
			DynaBean b = (DynaBean) o;

			Object xAxisValue = b.get(xAxisColumn);

			for (DynaProperty prop : data.getDynaProperties()) {
				String propName = prop.getName();

				if (!propName.equalsIgnoreCase(xAxisColumn)) {

					dataset.setValue(Double.parseDouble(b.get(propName)
							.toString()), propName, xAxisValue.toString());

				}

			}

		}

		JFreeChart chart = ChartFactory
				.createLineChart(title, xAxisColumn, "y-axis", dataset,
						PlotOrientation.VERTICAL, true, false, false);

		return chart;
	}

	private JFreeChart renderPie(String title, RowSetDynaClass data,
			String xAxisColumn) {
		Map<String, Double> values = new HashMap<String, Double>();
		for (Object o : data.getRows()) {
			DynaBean b = (DynaBean) o;

			// Object xAxisValue=b.get(xAxisColumn);

			for (DynaProperty prop : data.getDynaProperties()) {
				String propName = prop.getName();

				if (!propName.equalsIgnoreCase(xAxisColumn)) {

					values.put(propName, Double.parseDouble(b.get(propName)
							.toString()));

				}

			}

		}

		double sumValue = 0;

		for (Double d : values.values()) {
			sumValue = sumValue + d;
		}

		DefaultPieDataset pieDataset = new DefaultPieDataset();

		for (String key : values.keySet()) {
			Double d = values.get(key);
			pieDataset.setValue(key, ((d / sumValue) * 100));
		}

		JFreeChart chart = ChartFactory.createPieChart(title, pieDataset, true,
				false, false);

		return chart;
	}

/*	private JFreeChart renderDial(String title, RowSetDynaClass data,
			String xAxisColumn) {
		return null;
	}*/

	private JFreeChart renderArea(String title, RowSetDynaClass data,
			String xAxisColumn) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Object o : data.getRows()) {
			DynaBean b = (DynaBean) o;

			Object xAxisValue = b.get(xAxisColumn);

			for (DynaProperty prop : data.getDynaProperties()) {
				String propName = prop.getName();

				if (!propName.equalsIgnoreCase(xAxisColumn)) {

					dataset.setValue(Double.parseDouble(b.get(propName)
							.toString()), propName, xAxisValue.toString());

				}

			}

		}

		JFreeChart chart = ChartFactory
				.createAreaChart(title, xAxisColumn, "y-axis", dataset,
						PlotOrientation.VERTICAL, true, false, false);

		return chart;
	}

	private JFreeChart renderBar(String title, RowSetDynaClass data,
			String xAxisColumn) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (Object o : data.getRows()) {
			DynaBean b = (DynaBean) o;

			Object xAxisValue = b.get(xAxisColumn);

			for (DynaProperty prop : data.getDynaProperties()) {
				String propName = prop.getName();

				if (!propName.equalsIgnoreCase(xAxisColumn)) {

					dataset.setValue(Double.parseDouble(b.get(propName)
							.toString()), propName, xAxisValue.toString());

				}

			}

		}

		JFreeChart chart = ChartFactory
				.createBarChart3D(title, xAxisColumn, "y-axis", dataset,
						PlotOrientation.VERTICAL, true, false, false);

		return chart;
	}
}
