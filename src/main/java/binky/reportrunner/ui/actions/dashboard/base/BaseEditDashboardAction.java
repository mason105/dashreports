package binky.reportrunner.ui.actions.dashboard.base;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.RunnerDashboardChart;
import binky.reportrunner.data.RunnerDashboardItem;
import binky.reportrunner.data.RunnerDataSource;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.data.RunnerDashboardChart.ChartType;
import binky.reportrunner.data.RunnerDashboardChart.XAxisStep;
import binky.reportrunner.data.RunnerDashboardItem.Height;
import binky.reportrunner.data.RunnerDashboardItem.Width;
import binky.reportrunner.data.RunnerDashboardThreshold.ThresholdType;
import binky.reportrunner.exceptions.SecurityException;
import binky.reportrunner.scheduler.SchedulerException;
import binky.reportrunner.service.DatasourceService;

import com.opensymphony.xwork2.Preparable;

public abstract class BaseEditDashboardAction extends BaseDashboardAction
		implements Preparable {
	private static final long serialVersionUID = 1L;
	private ReportRunnerDao<RunnerDataSource, String> dataSourceDao;
	private DatasourceService dataSourceService;
	private List<RunnerDataSource> runnerDataSources;

	private String itemQuery;
	private String dataSourceName;

	private static final Logger logger = Logger
			.getLogger(BaseEditDashboardAction.class);

	
	protected final String saveItem(RunnerDashboardItem item)
			throws SecurityException {
		logger.debug("item is " + item.toString());
		String groupName = item.getGroup().getGroupName();

		if (super.getSessionUser().getGroups().contains(groupName)
				|| super.getSessionUser().getIsAdmin()) {

			// preserve teh current data
			if (item.getItemId() != null) {
				RunnerDashboardChart currentChart = (RunnerDashboardChart) super
						.getDashboardService().getItem(item.getItemId());
				item.setCurrentDataset(currentChart.getCurrentDataset());
				item.setLastUpdated(currentChart.getLastUpdated());
			}
			RunnerGroup group = new RunnerGroup();
			group.setGroupName(groupName);
			item.setGroup(group);

			item.setDatasource(dataSourceDao.get(dataSourceName));
			item.setAlertQuery(itemQuery);

			try {
				super.getDashboardService().saveUpdateItem(item);
			} catch (SchedulerException e) {
				super.addActionError(e.getMessage());
				logger.fatal(e.getMessage(), e);
				return INPUT;
			}
			this.groupName = item.getGroup().getGroupName();
			return SUCCESS;
		} else {

			SecurityException se = new SecurityException("Group " + groupName
					+ " not valid for user "
					+ super.getSessionUser().getUserName());
			throw se;
		}
	}

	public final void prepare() throws Exception {
		runnerDataSources = this.dataSourceService.getDataSourcesForGroup(groupName);
	}

	public final List<RunnerDataSource> getRunnerDataSources() {
		return runnerDataSources;
	}

	public final void setRunnerDataSources(
			List<RunnerDataSource> runnerDataSources) {
		this.runnerDataSources = runnerDataSources;
	}

	public final void setDataSourceDao(ReportRunnerDao<RunnerDataSource, String> dataSourceDao) {
		this.dataSourceDao = dataSourceDao;
	}

	public final List<ChartType> getChartTypes() {
		return Arrays.asList(ChartType.values());
	}

	public final List<Width> getWidths() {
		return Arrays.asList(Width.values());
	}

	public final List<Height> getHeights() {
		return Arrays.asList(Height.values());
	}

	public final List<XAxisStep> getXAxisSteps() {
		return Arrays.asList(XAxisStep.values());
	}

	public final List<ThresholdType> getThresholdTypes() {
		return Arrays.asList(ThresholdType.values());
	}

	public String getItemQuery() {
		return itemQuery;
	}

	public void setItemQuery(String itemQuery) {
		this.itemQuery = itemQuery;
	}

	public String getDataSourceName() {
		return dataSourceName;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

}
