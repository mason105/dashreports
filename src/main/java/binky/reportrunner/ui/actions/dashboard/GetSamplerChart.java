package binky.reportrunner.ui.actions.dashboard;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import binky.reportrunner.data.RunnerDashboardSampler;
import binky.reportrunner.data.SamplingData;
import binky.reportrunner.data.RunnerDashboardChart.Orientation;
import binky.reportrunner.data.RunnerDashboardSampler.Window;
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
		for (SamplingData d : item.getData()) {
			Number yValue = d.getValue();
			if (first) {
				first=false;
				lower=yValue.doubleValue();
			}
			if (yValue.doubleValue()>upper) upper=yValue.doubleValue();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
			if (item.getWindow()==Window.MINUTE|| item.getWindow()==Window.HOUR) {
				sdf = new SimpleDateFormat("HH:mm:ss");	
			}
			String xValue=sdf.format(new Date(d.getPk().getSampleTime()));
			dataSet.addValue(yValue, item.getValueColumn(), xValue);
			
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
		CategoryPlot linePlot = (CategoryPlot) chart.getPlot();
		linePlot.getRangeAxis().setRange(lower, upper);
		linePlot.setBackgroundPaint(Color.decode(item.getBackGroundColour()));
		linePlot.setDomainGridlinesVisible(item.isGridLines());
		linePlot.setRangeGridlinesVisible(item.isGridLines());
		linePlot.setRangeGridlinePaint(Color.black);
		linePlot.setDomainGridlinePaint(Color.black);
		linePlot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);
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
