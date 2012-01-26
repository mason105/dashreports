package binky.reportrunner.ui.actions.dashboard;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.ClusteredXYBarRenderer;
import org.jfree.chart.renderer.xy.StackedXYBarRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.Rotation;

import binky.reportrunner.data.RunnerDashboardChart;
import binky.reportrunner.ui.actions.dashboard.base.BaseDashboardAction;

public class GetChart extends BaseDashboardAction {

	private static final long serialVersionUID = 1L;

	private JFreeChart chart;

	private Integer itemId;

	private static final Logger logger = Logger.getLogger(GetChart.class);

	@Override
	public String execute() throws Exception {

		RunnerDashboardChart item = (RunnerDashboardChart) super
				.getDashboardService().getItem(itemId);

		// noddy security check
		if (!doesUserHaveGroup(item.getGroup().getGroupName())) {
			throw new SecurityException(
					"user does not have permission to group: " + groupName);
		}

		RowSetDynaClass data = item.getCurrentDataset();
		
		List<String> xLabels = new LinkedList<String>();
		for (Object o : data.getRows()) {
			DynaBean b = (DynaBean) o;
			String label = b.get(item.getXaxisColumn()).toString();
			if (!xLabels.contains(label)) {
				xLabels.add(label);
			}
		}

		if (xLabels.size() == 0) {
			Exception e = new Exception("invalid labels column identifier "
					+ item.getXaxisColumn() + " for item " + item.getItemId());
			logger.error(e.getMessage(), e);
			throw e;
		}

		List<Object> series = new LinkedList<Object>();
		for (Object o : data.getRows()) {
			DynaBean b = (DynaBean) o;
			Object seriesName;
			if ((item.getSeriesNameColumn() == null)
					|| (item.getSeriesNameColumn().isEmpty())) {
				seriesName = "VALUE";

			} else {
				seriesName = (String) b.get(item.getSeriesNameColumn());
				logger.debug("found series" + seriesName);
			}
			if (!series.contains(seriesName)) {
				series.add(seriesName);
			}
		}

		if (series.size() == 0) {
			Exception e = new Exception("invalid series column identifier "
					+ item.getSeriesNameColumn() + " for item id "
					+ item.getItemId());
			logger.error(e.getMessage(), e);
			throw e;
		}

		
		XYItemRenderer renderer;
		switch (item.getChartType()) {
		case PIE:

			// need to figure what to do about multiple series

			if (series != null && series.size() > 0) {
				Object s = series.get(0);
				DefaultPieDataset result = new DefaultPieDataset();
				for (Object o : data.getRows()) {
					DynaBean b = (DynaBean) o;
					Object sValue = b.get(item.getSeriesNameColumn());
					Number yValue = (Number) b.get(item.getValueColumn());
					Object xValue = b.get(item.getXaxisColumn());
					if (sValue.equals(s)) {
						// find the index of the x label
						result.setValue(xValue.toString(), yValue);
					}

				}

				chart = ChartFactory.createPieChart3D(item.getItemName()
						+ " - " + s, // chart title
						result, // data
						true, // include legend
						true, false);

				PiePlot3D plot = (PiePlot3D) chart.getPlot();
				plot.setStartAngle(290);
				plot.setDirection(Rotation.CLOCKWISE);
				plot.setForegroundAlpha(0.5f);
			}

			break;
		default:
			AbstractXYDataset xyDataset;
			switch (item.getChartType()) {
			case AREA:
				renderer = new XYAreaRenderer(XYAreaRenderer.AREA);
				xyDataset = new XYSeriesCollection();
				break;
			case CLUSTERED_BAR:
				renderer = new ClusteredXYBarRenderer();
				xyDataset = new DefaultTableXYDataset();
				break;
			case STACKED_BAR:
				renderer = new StackedXYBarRenderer();
				xyDataset = new DefaultTableXYDataset();
				break;
			case LINE:
				renderer = new StandardXYItemRenderer(StandardXYItemRenderer.LINES);
				xyDataset = new XYSeriesCollection();
				break;
			default:
				renderer = new StackedXYBarRenderer();
				xyDataset = new DefaultTableXYDataset();
			}
			ValueAxis xAxis = new SymbolAxis("", xLabels
					.toArray(new String[] {}));
			ValueAxis yAxis = new NumberAxis(item.getYLabel());
			
			
			
			
			
			
			for (Object s : series) {
				XYSeries xySeries = new XYSeries(s.toString(),true,false);

				for (Object o : data.getRows()) {
					DynaBean b = (DynaBean) o;

					Object sValue = b.get(item.getSeriesNameColumn());
					Number yValue = (Number) b.get(item.getValueColumn());
					Object xValue = b.get(item.getXaxisColumn());
					if (sValue.equals(s)) {
						// find the index of the x label
						int xIndex = xLabels.indexOf(xValue);
						xySeries.add(xIndex, yValue);
					}
				}
				switch (item.getChartType()) {
				case AREA:
				case LINE:
					((XYSeriesCollection)xyDataset).addSeries(xySeries);
				break;
				default:
					((DefaultTableXYDataset)xyDataset).addSeries(xySeries);
				}
				

			}
			Plot plot = new XYPlot(xyDataset, xAxis,
					yAxis, renderer);
			
			plot.setBackgroundPaint(Color.decode(item.getBackGroundColour()));
			
			chart = new JFreeChart(null,
					JFreeChart.DEFAULT_TITLE_FONT, plot, true);
			
		}
		return SUCCESS;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

}
