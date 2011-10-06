/**
 * 
 */
package binky.reportrunner.ui.actions.dashboard;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.RowSetDynaClass;

import binky.reportrunner.data.RunnerDashboardChart;
import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.data.RunnerDashboardThreshold;
import binky.reportrunner.ui.actions.dashboard.base.BaseDashboardAction;

/**
 * @author Daniel Grout
 * 
 */
public class DashboardWidgetAction extends BaseDashboardAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see binky.reportrunner.ui.actions.base.StandardRunnerAction#execute()
	 */
	private static final long serialVersionUID = 0L;

	private Map<String, Map<String, Number>> values;
	private Integer itemId;
	private RunnerDashboardItem item;

	@Override
	public String execute() throws Exception {

		RunnerDashboardItem item = super.getDashboardService().getItem(itemId);
		// noddy security check
		if (!doesUserHaveGroup(item.getGroup().getGroupName())) {
			return ERROR;
		}

		this.item = item;

		values = new HashMap<String, Map<String, Number>>();
		RowSetDynaClass rs = item.getCurrentDataset();
		if (item.getItemType().equals(RunnerDashboardItem.ItemType.Chart)) {
			RunnerDashboardChart chart=(RunnerDashboardChart)item;
			for (Object o : rs.getRows()) {
				DynaBean b = (DynaBean) o;
				String seriesName = chart.getSeriesNameColumn() == null ? "VALUE": (String) b.get(chart.getSeriesNameColumn());
				Number value = BigDecimal.valueOf(Double.parseDouble((String) b.get(chart.getValueColumn())));
				String xValue = (String) b.get(chart.getXaxisColumn());

				if (values.get(seriesName) != null) {
					// store into existing series
					values.get(seriesName).put(xValue, value);
				} else {
					// create new entry
					Map<String, Number> entry = new HashMap<String, Number>();
					entry.put(xValue, value);
					values.put(seriesName, entry);
				}
			}
		}
		return SUCCESS;
	}

	public Map<String, Integer> getThresholdData() {

		if ((item.getCurrentDataset() != null)
				&& (item instanceof RunnerDashboardThreshold)) {
			Map<String, Integer> data = new HashMap<String, Integer>();

			for (Object o : item.getCurrentDataset().getRows()) {
				DynaBean b = (DynaBean) o;
				String label = b.get(
						((RunnerDashboardThreshold) item).getLabelColumn())
						.toString();
				Number value = (Number) b.get(((RunnerDashboardThreshold) item)
						.getValueColumn());
				Integer result = 2;
				switch (((RunnerDashboardThreshold) item).getType()) {
				case GreaterThan:
					if (value.floatValue() <= ((RunnerDashboardThreshold) item)
							.getLowerValue()) {
						result = 1;
					}
					if (value.floatValue() >= ((RunnerDashboardThreshold) item)
							.getUpperValue()) {
						result = 3;
					}
					break;
				case LessThan:
					if (value.floatValue() <= ((RunnerDashboardThreshold) item)
							.getLowerValue()) {
						result = 3;
					}					

					if (value.floatValue() >= ((RunnerDashboardThreshold) item)
							.getUpperValue()) {
						result = 1;
					}
					break;
				}
				data.put(label, result);
			}
			return data;
		} else {
			return null;
		}

	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public RunnerDashboardItem getItem() {
		return item;
	}

	public void setItem(RunnerDashboardItem item) {
		this.item = item;
	}

	public Map<String, Map<String, Number>> getValues() {
		return values;
	}

}
