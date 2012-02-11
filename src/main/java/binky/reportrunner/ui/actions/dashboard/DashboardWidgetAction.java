/**
 * 
 */
package binky.reportrunner.ui.actions.dashboard;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import binky.reportrunner.data.DashboardData;
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

	private Map<String, ThresholdItem> thresholdData;
	private Integer itemId;
	private RunnerDashboardItem item;
	private long random;
	private List<Map<String, Object>> values;

	@Override
	public String execute() throws Exception {

		RunnerDashboardItem item = super.getDashboardService().getItem(itemId);
		// noddy security check
		if (!doesUserHaveGroup(item.getGroup().getGroupName())) {
			return ERROR;
		}

		this.item = item;
		values = new LinkedList<Map<String, Object>>();

		// populate the values
		if (item.getData() != null) {
			int currentRowNumber = 0;

			Map<String, Object> currentRow = new HashMap<String, Object>();
			for (DashboardData d : item.getData()) {
				if (d.getRowNumber() > currentRowNumber) {
					if (currentRowNumber>0) {
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
					value = new BigDecimal(d.getValue());
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
		}

		if ((item instanceof RunnerDashboardThreshold)) {
			Map<String, ThresholdItem> data = new HashMap<String, ThresholdItem>();

			for (Map<String, Object> row : values) {
				String label = row.get(
						((RunnerDashboardThreshold) item).getLabelColumn())
						.toString();
				Number value = (Number) row
						.get(((RunnerDashboardThreshold) item).getValueColumn());
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

				data.put(label, new ThresholdItem(value, result));
			}
			this.thresholdData = data;
		}

		this.random = Math.round(Math.random() * 100000);
		return SUCCESS;
	}

	public Map<String, ThresholdItem> getThresholdData() {
		return this.thresholdData;
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

	public long getRandom() {
		return random;
	}

	public List<Map<String, Object>> getValues() {
		return values;
	}

	public void setValues(List<Map<String, Object>> values) {
		this.values = values;
	}

	public void setThresholdData(Map<String, ThresholdItem> thresholdData) {
		this.thresholdData = thresholdData;
	}

	public void setRandom(long random) {
		this.random = random;
	}

}
