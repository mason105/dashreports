package binky.reportrunner.ui.actions.dashboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.MinMaxCategoryRenderer;
import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import binky.reportrunner.data.RunnerDashboardChart.Orientation;
import binky.reportrunner.data.RunnerDashboardSampler;
import binky.reportrunner.data.sampling.SamplingData;
import binky.reportrunner.data.sampling.TrendData;
import binky.reportrunner.ui.actions.dashboard.base.BaseDashboardAction;

public class GetSamplerChart extends BaseDashboardAction {

	private static final long serialVersionUID = 1L;

	private JFreeChart chart;

	private Integer itemId;

	private static final Logger logger = Logger.getLogger(GetChart.class);

	@Override
	public String execute() throws Exception {

		RunnerDashboardSampler item = (RunnerDashboardSampler) super
				.getDashboardService().getItem(itemId);

		// noddy security check
		if (!doesUserHaveGroup(item.getGroup().getGroupName())) {
			throw new SecurityException(
					"user does not have permission to group: " + groupName);
		}

		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		double lower=0d;
		double upper=0d;
		boolean first=true;
		String timeVal="";
		for (SamplingData d : item.getData()) {
			Number yValue = d.getValue();
			if (first) {
				first=false;
				lower=yValue.doubleValue();
			}
			if (yValue.doubleValue()>upper) upper=yValue.doubleValue();
			
			SimpleDateFormat sdf;
			switch (item.getInterval()) {
			case DAY:				
				sdf = new SimpleDateFormat("EEEEE");
				break;
			case HOUR:					
				sdf = new SimpleDateFormat("HH:mm:ss");				
				break;
			case MINUTE:
				sdf = new SimpleDateFormat("HH:mm:ss");
				timeVal= (new SimpleDateFormat("HH")).format(d.getPk().getSampleTime());
				break;
			case MONTH:
				sdf = new SimpleDateFormat("MMMMM");
				break;
			case SECOND:
			default:
				timeVal= (new SimpleDateFormat("HH:mm")).format(d.getPk().getSampleTime());
				sdf = new SimpleDateFormat("HH:mm:ss");
			}
			
			
			String xValue=sdf.format(new Date(d.getPk().getSampleTime()));
			dataSet.addValue(yValue, "actual", xValue);
			
			if (item.isRecordTrendData()&&item.getTrendData()!=null) {
				
				switch (item.getInterval()) {
				case DAY:				
					sdf = new SimpleDateFormat("EEEEE");
					break;
				case HOUR:					
					sdf = new SimpleDateFormat("HH");
					break;
				case MINUTE:
					sdf = new SimpleDateFormat("mm");
					break;
				case MONTH:
					sdf = new SimpleDateFormat("MMMMM");
					break;
				case SECOND:
				default:
					sdf = new SimpleDateFormat("ss");
				}
				
				String timeStringCompare = sdf.format(new Date(d.getPk().getSampleTime()));
				if (item.isRecordTrendData()&&item.getTrendData()!=null) {		
				//hack - needs work				
				for (TrendData t: item.getTrendData()) {
					if (t.getPk().getTimeString().equals(timeStringCompare)) {
						String timeString=t.getPk().getTimeString();
						switch (item.getInterval()) {
							case HOUR:
								timeString=timeString+":00:00";
								break;
							case MINUTE:
								timeString=timeVal+":"+timeString+":00"; 						
								break;
							case SECOND:
								timeString=timeVal+":"+timeString;
						}
						dataSet.addValue(t.getMeanValue(), "mean", timeString);
						dataSet.addValue(t.getMaxValue(), "maximum", timeString);
						dataSet.addValue(t.getMinValue(), "minimum", timeString);	
						if (t.getMinValue().doubleValue()<lower)lower=t.getMinValue().doubleValue();
						if (t.getMeanValue().doubleValue()<lower)lower=t.getMeanValue().doubleValue();
						if (t.getMeanValue().doubleValue()>upper)upper=t.getMeanValue().doubleValue();
						if (t.getMaxValue().doubleValue()>upper)upper=t.getMaxValue().doubleValue();
						break;
					}
				}
				}
			}
			
		}
				
		chart = ChartFactory
				.createLineChart(						
						"",						
						"",
						item.getYAxisLabel(),
						dataSet,
						item.getOrientation() == Orientation.VERTICAL ? PlotOrientation.VERTICAL
								: PlotOrientation.HORIZONTAL, true, false,
						false);
		
		
		CategoryPlot  linePlot = (CategoryPlot) chart.getPlot();
		linePlot.getRangeAxis().setRange(lower, upper);
		linePlot.setBackgroundPaint(Color.decode(item.getBackGroundColour()));
		linePlot.setDomainGridlinesVisible(item.isGridLines());
		linePlot.setRangeGridlinesVisible(item.isGridLines());
		linePlot.setRangeGridlinePaint(Color.black);
		linePlot.setDomainGridlinePaint(Color.black);
		linePlot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
		
		if (item.isRecordTrendData()) {
			
	        MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();
	        renderer.setDrawLines(false);
		        
		        linePlot.setRenderer(renderer);
		}
		
		chart.setAntiAlias(true);
		chart.setTextAntiAlias(true);
		return SUCCESS;
	}
	
	private void doTrendChart(RunnerDashboardSampler sampler){
		
	}
	private void doLineChart(RunnerDashboardSampler sampler) {
		
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
