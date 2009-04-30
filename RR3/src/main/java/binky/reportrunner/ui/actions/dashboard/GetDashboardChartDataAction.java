package binky.reportrunner.ui.actions.dashboard;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.log4j.Logger;

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

	private static final Logger logger = Logger.getLogger(GetDashboardChartDataAction.class);
	
	@Override
	public String execute() throws Exception {
		RunnerDashboardAlert alert = dashboardService.getAlert(alertId);

		// noddy security check
		if (!doesUserHaveGroup(alert.getGroup().getGroupName())) {
			return ERROR;
		}

		Double max = 0d;
		RowSetDynaClass data = alert.getCurrentDataset();
		Map<String, List<Double>> dataMap = new HashMap<String, List<Double>>();

		for (Object o : data.getRows()) {
			DynaBean b = (DynaBean) o;
			String seriesName = "";
			if ((alert.getSeriesNameColumn() == null)
					|| (alert.getSeriesNameColumn().isEmpty())) {
				seriesName = "VALUE";
			} else {
				seriesName = (String) b.get(alert.getSeriesNameColumn());
			}
			Double value = Double.parseDouble(b.get(alert.getValueColumn())
					.toString());
			if (value > max)
				max = value;
			List<Double> values = dataMap.get(seriesName);

			if (values == null) {
				values = new LinkedList<Double>();
			}
			values.add(value);

			dataMap.put(seriesName, values);

		}

		List<String> xLabels = new LinkedList<String>();
		for (Object o : data.getRows()) {
			DynaBean b = (DynaBean) o;
			String label = b.get(alert.getXaxisColumn()).toString();
			if (!xLabels.contains(label)) {
				xLabels.add(label);
			}
		}

		List<DefaultOFCGraphDataModel> models = new LinkedList<DefaultOFCGraphDataModel>();
		for (String modName : dataMap.keySet()) {
			List<Double> d = dataMap.get(modName);

			/* hack for colours */
			int c = 15;
			int y = 1;
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
			logger.debug("colouring series " + modName+ "  with: " + mainHex);
			/* end hack */

			switch (alert.getChartType()) {
			case AREA:
				DefaultOFCGraphDataModel modelArea = new DefaultOFCGraphDataModel();
				modelArea.setData(d);
				modelArea.setFormat(new DecimalFormat("###0.000"));
				modelArea.setSeriesType(new OFCLineAreaSeriesType(3, mainHex,
						modName));
				models.add(modelArea);
				break;
			case BAR:
				DefaultOFCGraphDataModel modelBar = new DefaultOFCGraphDataModel();
				modelBar.setData(d);
				modelBar.setFormat(new DecimalFormat("###0.000"));
				modelBar.setSeriesType(new OFCBarSeriesType(50, mainHex,
						modName));
				models.add(modelBar);
				break;
			case LINE:
				DefaultOFCGraphDataModel modelLine = new DefaultOFCGraphDataModel();
				modelLine.setData(d);
				modelLine.setFormat(new DecimalFormat("###0.000"));
				modelLine.setSeriesType(new OFCLineSeriesType(3, mainHex,
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
			stdController.getYMax().setMax((int) (Math.round(max + 0.5d)));
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
