package binky.reportrunner.ui.actions.dashboard;

import java.awt.Color;
import java.math.BigInteger;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

import binky.reportrunner.data.DashboardData;
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
		
		
		
		List<Map<String, Object>> values = new LinkedList<Map<String, Object>>();

		// populate the values
				if (item.getData() != null && item.getData().size()>0) {
					int currentRowNumber = 0;

					Map<String, Object> currentRow = new HashMap<String, Object>();
					for (DashboardData d : item.getData()) {
						if (d.getRowNumber() > currentRowNumber) {
							if (currentRowNumber>0) {
								if (logger.isTraceEnabled()) {
									for (String s: currentRow.keySet()) {
										logger.trace("current row " + currentRowNumber
												+ " has column " + s + " with value of " + currentRow.get(s) + " key has hash value of " + s.hashCode());
									}
								}
								values.add(currentRow);
								currentRow = new HashMap<String, Object>();
							}
							currentRowNumber = d.getRowNumber();
						}
						Object value;
						switch (d.getDataType()) {
						case Types.BIGINT:
						case Types.DECIMAL:
						case Types.FLOAT:
						case Types.DOUBLE:
						case Types.INTEGER:
						case Types.REAL:
						case Types.SMALLINT:
						case Types.TINYINT:
							value = new BigInteger(d.getValue());
							break;
						case Types.DATE:
						case Types.TIME:
						case Types.TIMESTAMP:
							value = new Date(Long.parseLong(d.getValue()));
							break;
						default:
							value = d.getValue();
						}
						currentRow.put(d.getColumnName(), value);
					
					}
				} else {
					throw new Exception("Empty dataset for item: " + itemId);
				}
		
		List<String> xLabels = new LinkedList<String>();
		for (Map<String,Object> row : values) {
			logger.trace("fetching value for column: " + item.getXaxisColumn() + " hashcode of " + item.getXaxisColumn().hashCode());
			
			if (logger.isTraceEnabled()) {
				for (String s: row.keySet()) {
					logger.trace("current row has column " + s + " with value of " + row.get(s) + " key has hash value of " + s.hashCode());
				}
			}
			
			String label = row.get(item.getXaxisColumn()).toString();
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
		for (Map<String,Object> row : values) {
			Object seriesName;
			if ((item.getSeriesNameColumn() == null)
					|| (item.getSeriesNameColumn().isEmpty())) {
				seriesName = "VALUE";

			} else {
				seriesName = (String) row.get(item.getSeriesNameColumn());
				logger.trace("found series" + seriesName);
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
				for (Map<String,Object> row : values) {
					if (StringUtils.isBlank(item.getSeriesNameColumn())) {
						Number yValue = (Number) row.get(item.getValueColumn());
						Object xValue = row.get(item.getXaxisColumn());
						result.setValue(xValue.toString(), yValue);				
					} else {
						Object sValue = row.get(item.getSeriesNameColumn());
						
						Number yValue = (Number) row.get(item.getValueColumn());
						Object xValue = row.get(item.getXaxisColumn());
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
				
			for (Map<String,Object> row : values) {						
					Number yValue = (Number) row.get(item.getValueColumn());
					Comparable xValue = (Comparable)row.get(item.getXaxisColumn());
					dataSet.addValue(yValue,"Value" , xValue);
				}
								
			} else {
				for (Object s : series) {
					
	
					for (Map<String,Object> row : values) {	
						Object sValue = row.get(item.getSeriesNameColumn());
						Number yValue = (Number) row.get(item.getValueColumn());
						Comparable xValue = (Comparable)row.get(item.getXaxisColumn());
						if (sValue.equals(s)) {							
							dataSet.addValue(yValue,(Comparable)s , xValue);
						}
					}				
				}
			}
			switch (item.getChartType()) {
			case BAR_STACKED_3D:
				chart = ChartFactory.createStackedBarChart3D("","",item.getYLabel(),dataSet, PlotOrientation.VERTICAL, true,false,false);								
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
				chart = ChartFactory.createStackedBarChart("","",item.getYLabel(),dataSet, item.getOrientation()==Orientation.VERTICAL? PlotOrientation.VERTICAL:PlotOrientation.HORIZONTAL, true,false,false);								
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
				chart = ChartFactory.createBarChart3D("","",item.getYLabel(),dataSet, item.getOrientation()==Orientation.VERTICAL? PlotOrientation.VERTICAL:PlotOrientation.HORIZONTAL, true,false,false);								
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
				chart = ChartFactory.createBarChart("","",item.getYLabel(),dataSet, item.getOrientation()==Orientation.VERTICAL? PlotOrientation.VERTICAL:PlotOrientation.HORIZONTAL, true,false,false);								
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
				chart = ChartFactory.createAreaChart("","",item.getYLabel(),dataSet, item.getOrientation()==Orientation.VERTICAL? PlotOrientation.VERTICAL:PlotOrientation.HORIZONTAL, true,false,false);								
				CategoryPlot areaPlot = (CategoryPlot)chart.getPlot();			
				areaPlot.setBackgroundPaint(Color.decode(item.getBackGroundColour()));
				areaPlot.setDomainGridlinesVisible(item.isGridLines());
				areaPlot.setRangeGridlinesVisible(item.isGridLines());
				areaPlot.setRangeGridlinePaint(Color.black);
				areaPlot.setDomainGridlinePaint(Color.black);
				break;
			case LINE_3D:
				chart = ChartFactory.createLineChart3D("","",item.getYLabel(),dataSet, item.getOrientation()==Orientation.VERTICAL? PlotOrientation.VERTICAL:PlotOrientation.HORIZONTAL, true,false,false);								
				CategoryPlot linePlot3D = (CategoryPlot)chart.getPlot();			
				linePlot3D.setBackgroundPaint(Color.decode(item.getBackGroundColour()));
				linePlot3D.setDomainGridlinesVisible(item.isGridLines());
				linePlot3D.setRangeGridlinesVisible(item.isGridLines());
				linePlot3D.setRangeGridlinePaint(Color.black);
				linePlot3D.setDomainGridlinePaint(Color.black);
				break;				
			case LINE:
				chart = ChartFactory.createLineChart("","",item.getYLabel(),dataSet,item.getOrientation()==Orientation.VERTICAL? PlotOrientation.VERTICAL:PlotOrientation.HORIZONTAL, true,false,false);								
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
