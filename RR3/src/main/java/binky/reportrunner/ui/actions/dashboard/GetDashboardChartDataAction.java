package binky.reportrunner.ui.actions.dashboard;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.RowSetDynaClass;

import za.co.connext.web.components.DefaultOFCGraphDataModel;
import za.co.connext.web.components.DefaultOFCPieDataModel;
import za.co.connext.web.components.OFCBarSeriesType;
import za.co.connext.web.components.OFCGraphController;
import za.co.connext.web.components.OFCLineAreaSeriesType;
import za.co.connext.web.components.OFCLineSeriesType;
import za.co.connext.web.components.OFCPieController;
import za.co.connext.web.components.OFCPieSeriesType;
import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class GetDashboardChartDataAction extends StandardRunnerAction {

	private static final long serialVersionUID = 2460177730972381778L;

	private DashboardService dashboardService;

	private Integer alertId;

	private String data;

	@Override
	public String execute() throws Exception {
		RunnerDashboardAlert alert = dashboardService.getAlert(alertId);
		
		//noddy security check
		if (doesUserHaveGroup(alert.getGroup().getGroupName())) {
			Double max = 0d;
			RowSetDynaClass data = alert.getCurrentDataset();
			Map<String, List<Double>> dataMap = new HashMap<String, List<Double>>();
			for (DynaProperty prop : data.getDynaProperties()) {
				List<Double> d = new LinkedList<Double>();
				String propName = prop.getName();
				for (Object o : data.getRows()) {
					DynaBean b = (DynaBean) o;
					if (!propName.equalsIgnoreCase(alert.getXaxisColumn())) {
						Double value = Double.parseDouble(b.get(propName)
								.toString());
						if (value > max)
							max = value;
						d.add(value);
					}
				}
				if (!propName.equalsIgnoreCase(alert.getXaxisColumn())) {
					dataMap.put(propName, d);
				}
			}

			List<String> xLabels = new LinkedList<String>();
			for (Object o : data.getRows()) {
				DynaBean b = (DynaBean) o;
				xLabels.add(b.get(alert.getXaxisColumn()).toString());
			}

			List<DefaultOFCGraphDataModel> models = new LinkedList<DefaultOFCGraphDataModel>();
			for (String modName : dataMap.keySet()) {
				List<Double> d = dataMap.get(modName);
				switch (alert.getChartType()) {
				case AREA:
					DefaultOFCGraphDataModel modelArea = new DefaultOFCGraphDataModel();
					modelArea.setData(d);
					modelArea.setFormat(new DecimalFormat("###0.000"));
					modelArea.setSeriesType(new OFCLineAreaSeriesType(3,
							"#8b0000", modName));
					models.add(modelArea);
					break;
				case BAR:
					DefaultOFCGraphDataModel modelBar = new DefaultOFCGraphDataModel();
					modelBar.setData(d);
					modelBar.setFormat(new DecimalFormat("###0.000"));
					modelBar.setSeriesType(new OFCBarSeriesType(3, "#8b0000",
							modName));
					models.add(modelBar);
					break;
				case LINE:
					DefaultOFCGraphDataModel modelLine = new DefaultOFCGraphDataModel();
					modelLine.setData(d);
					modelLine.setFormat(new DecimalFormat("###0.000"));
					modelLine.setSeriesType(new OFCLineSeriesType(3, "#8b0000",
							modName));
					models.add(modelLine);
					break;
				case PIE:
					DefaultOFCPieDataModel modelPie = new DefaultOFCPieDataModel();
					modelPie.setLabels(xLabels);
					modelPie.setData(d);
					List<String> colours = new LinkedList<String>();
					int a = 15;
					int x = 1;
					for (Double val : d) {

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
						case 6:
							hex = "#" + hex1 + hex1 + hex1 + hex1 + hex1 + hex1;
						default:
							x = 0;
							a = a - 3;
						}
						colours.add(hex);

						x++;
						if (a < 0) {
							a = 16;

						}

					}
					modelPie.setColors(colours);
					modelPie.setSeriesType(new OFCPieSeriesType(70, "#FFFFFF",
							"10px", "#404040"));
					modelPie.setFormat(new DecimalFormat("###0.000"));
					models.add(modelPie);
					break;
				}

			}

			// I need to rewrite this chart library - a little bit of OO could
			// have
			// gone a long way!
			switch (alert.getChartType()) {
			case AREA:
			case BAR:
			case LINE:
				OFCGraphController stdController = new OFCGraphController();
				stdController.getTitle().setText(alert.getAlertName());
				stdController.getTitle().setSize(10);
				stdController.getLabels().setLabels(xLabels);
				stdController.getYLegend().setText("");
				stdController.getYLegend().setColor("#8b0000");
				stdController.getYLegend().setSize(10);
				stdController.getXLegend().setText(alert.getXaxisColumn());
				stdController.getXLegend().setColor("#8b0000");
				stdController.getXLegend().setSize(10);
				stdController.getColor().getBgColor().setColor("#FFFFFF");
				stdController.getColor().getXAxisColor().setColor("#e3e3e3");
				stdController.getColor().getYAxisColor().setColor("#e3e3e3");
				stdController.getColor().getXGridColor().setColor("#e3e3e3");
				stdController.getColor().getYGridColor().setColor("#e3e3e3");
				for (DefaultOFCGraphDataModel m : models) {
					stdController.add(m);
				}
				this.data = stdController.render();
				break;
			case PIE:
				OFCPieController pieController = new OFCPieController();
				pieController.getTitle().setText(alert.getAlertName());
				pieController.getTitle().setSize(10);
				pieController.getLabels().setLabels(xLabels);
				pieController.getColor().getBgColor().setColor("#FFFFFF");
				for (DefaultOFCGraphDataModel m : models) {
					pieController.set(m);
				}
				this.data = pieController.render();
				break;
			}
		}
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

}
