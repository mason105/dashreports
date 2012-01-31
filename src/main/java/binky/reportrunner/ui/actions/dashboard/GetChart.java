package binky.reportrunner.ui.actions.dashboard;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

import binky.reportrunner.data.RunnerDashboardChart;
import binky.reportrunner.data.RunnerDashboardChart.Orientation;
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

		
		
		switch (item.getChartType()) {
		case PIE:

			// need to figure what to do about multiple series

			if (series != null && series.size() > 0) {
				Object s = series.get(0);
				DefaultPieDataset result = new DefaultPieDataset();
				for (Object o : data.getRows()) {
					DynaBean b = (DynaBean) o;
					if (StringUtils.isBlank(item.getSeriesNameColumn())) {
						Number yValue = (Number) b.get(item.getValueColumn());
						Object xValue = b.get(item.getXaxisColumn());
						result.setValue(xValue.toString(), yValue);				
					} else {
						Object sValue = b.get(item.getSeriesNameColumn());
						
						Number yValue = (Number) b.get(item.getValueColumn());
						Object xValue = b.get(item.getXaxisColumn());
						if (sValue.equals(s)) {
							// find the index of the x label
							result.setValue(xValue.toString(), yValue);
						}
						
					}

				}

				chart = ChartFactory.createPieChart3D(null, // chart title
						result, // data
						true, // include legend
						true, false);

				PiePlot3D plot = (PiePlot3D) chart.getPlot();
				plot.setStartAngle(290);
				plot.setDirection(Rotation.CLOCKWISE);
				plot.setBackgroundPaint(Color.decode(item.getBackGroundColour()));
				plot.setForegroundAlpha(0.5f);
			}

			break;
		default:
			DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
			
			if (StringUtils.isBlank(item.getSeriesNameColumn())) {
				
				for (Object o : data.getRows()) {
					DynaBean b = (DynaBean) o;				
					Number yValue = (Number) b.get(item.getValueColumn());
					Comparable xValue = (Comparable)b.get(item.getXaxisColumn());
					dataSet.addValue(yValue,"Value" , xValue);
				}
								
			} else {
				for (Object s : series) {
					
	
					for (Object o : data.getRows()) {
						DynaBean b = (DynaBean) o;				
						Object sValue = b.get(item.getSeriesNameColumn());
						Number yValue = (Number) b.get(item.getValueColumn());
						Comparable xValue = (Comparable)b.get(item.getXaxisColumn());
						if (sValue.equals(s)) {							
							dataSet.addValue(yValue,(Comparable)s , xValue);
						}
					}				
				}
			}
			switch (item.getChartType()) {
			case BAR_STACKED_3D:
				chart = ChartFactory.createStackedBarChart3D("",item.getYLabel(), "",dataSet, PlotOrientation.VERTICAL, true,false,false);								
				CategoryPlot barPlotStack3D = (CategoryPlot)chart.getPlot();
				barPlotStack3D.setDomainGridlinesVisible(item.isGridLines());
				barPlotStack3D.setRangeGridlinesVisible(item.isGridLines());
				barPlotStack3D.setRangeGridlinePaint(Color.black);
				barPlotStack3D.setDomainGridlinePaint(Color.black);
				BarRenderer3D brStack3D = (BarRenderer3D)barPlotStack3D.getRenderer();
				brStack3D.setItemMargin(0.0d);
				barPlotStack3D.setBackgroundPaint(Color.decode(item.getBackGroundColour()));
				break;				
			case BAR_STACKED:
				chart = ChartFactory.createStackedBarChart("",item.getYLabel(), "",dataSet, item.getOrientation()==Orientation.VERTICAL? PlotOrientation.VERTICAL:PlotOrientation.HORIZONTAL, true,false,false);								
				CategoryPlot barPlotStack = (CategoryPlot)chart.getPlot();
				BarRenderer brStack = (BarRenderer)barPlotStack.getRenderer();
				brStack.setItemMargin(0.0d);
				barPlotStack.setDomainGridlinesVisible(item.isGridLines());
				barPlotStack.setRangeGridlinesVisible(item.isGridLines());
				barPlotStack.setBackgroundPaint(Color.decode(item.getBackGroundColour()));
				barPlotStack.setRangeGridlinePaint(Color.black);
				barPlotStack.setDomainGridlinePaint(Color.black);

				break;				
			case BAR_3D:
				chart = ChartFactory.createBarChart3D("",item.getYLabel(), "",dataSet, item.getOrientation()==Orientation.VERTICAL? PlotOrientation.VERTICAL:PlotOrientation.HORIZONTAL, true,false,false);								
				CategoryPlot barPlot3D = (CategoryPlot)chart.getPlot();
				BarRenderer3D br3D = (BarRenderer3D)barPlot3D.getRenderer();
				br3D.setItemMargin(0.0d);
				barPlot3D.setDomainGridlinesVisible(item.isGridLines());
				barPlot3D.setRangeGridlinesVisible(item.isGridLines());
				barPlot3D.setBackgroundPaint(Color.decode(item.getBackGroundColour()));
				barPlot3D.setRangeGridlinePaint(Color.black);
				barPlot3D.setDomainGridlinePaint(Color.black);
				break;
			case BAR:
				chart = ChartFactory.createBarChart("",item.getYLabel(), "",dataSet, item.getOrientation()==Orientation.VERTICAL? PlotOrientation.VERTICAL:PlotOrientation.HORIZONTAL, true,false,false);								
				CategoryPlot barPlot = (CategoryPlot)chart.getPlot();
				BarRenderer br = (BarRenderer)barPlot.getRenderer();
				br.setItemMargin(0.0d);
				barPlot.setDomainGridlinesVisible(item.isGridLines());
				barPlot.setRangeGridlinesVisible(item.isGridLines());
				barPlot.setRangeGridlinePaint(Color.black);
				barPlot.setDomainGridlinePaint(Color.black);				
				barPlot.setBackgroundPaint(Color.decode(item.getBackGroundColour()));
				break;
			case AREA:
				chart = ChartFactory.createAreaChart("",item.getYLabel(), "",dataSet, item.getOrientation()==Orientation.VERTICAL? PlotOrientation.VERTICAL:PlotOrientation.HORIZONTAL, true,false,false);								
				CategoryPlot areaPlot = (CategoryPlot)chart.getPlot();			
				areaPlot.setBackgroundPaint(Color.decode(item.getBackGroundColour()));
				areaPlot.setDomainGridlinesVisible(item.isGridLines());
				areaPlot.setRangeGridlinesVisible(item.isGridLines());
				areaPlot.setRangeGridlinePaint(Color.black);
				areaPlot.setDomainGridlinePaint(Color.black);
				break;
			case LINE_3D:
				chart = ChartFactory.createLineChart3D("",item.getYLabel(), "",dataSet, item.getOrientation()==Orientation.VERTICAL? PlotOrientation.VERTICAL:PlotOrientation.HORIZONTAL, true,false,false);								
				CategoryPlot linePlot3D = (CategoryPlot)chart.getPlot();			
				linePlot3D.setBackgroundPaint(Color.decode(item.getBackGroundColour()));
				linePlot3D.setDomainGridlinesVisible(item.isGridLines());
				linePlot3D.setRangeGridlinesVisible(item.isGridLines());
				linePlot3D.setRangeGridlinePaint(Color.black);
				linePlot3D.setDomainGridlinePaint(Color.black);
				break;				
			case LINE:
				chart = ChartFactory.createLineChart("",item.getYLabel(), "",dataSet,item.getOrientation()==Orientation.VERTICAL? PlotOrientation.VERTICAL:PlotOrientation.HORIZONTAL, true,false,false);								
				CategoryPlot linePlot = (CategoryPlot)chart.getPlot();			
				linePlot.setBackgroundPaint(Color.decode(item.getBackGroundColour()));
				linePlot.setDomainGridlinesVisible(item.isGridLines());
				linePlot.setRangeGridlinesVisible(item.isGridLines());
				linePlot.setRangeGridlinePaint(Color.black);
				linePlot.setDomainGridlinePaint(Color.black);
				break;				
			}
		}
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
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
