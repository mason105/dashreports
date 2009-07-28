/**
 * 
 */
package binky.reportrunner.ui.actions.dashboard;

import org.apache.commons.beanutils.RowSetDynaClass;

import binky.reportrunner.data.RunnerDashboardAlert;
import binky.reportrunner.service.DashboardService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

/**
 * @author Daniel Grout
 *
 */
public class GetDashboardGridDataAction extends StandardRunnerAction {

	/* (non-Javadoc)
	 * @see binky.reportrunner.ui.actions.base.StandardRunnerAction#execute()
	 */
	private static final long serialVersionUID = 0L;

	private DashboardService dashboardService;

	private Integer alertId;
	private String gridData;
	private RowSetDynaClass currentDataset;
	
	@Override
	public String execute() throws Exception {
		
		RunnerDashboardAlert alert = dashboardService.getAlert(alertId);
		
		// noddy security check
		if (!doesUserHaveGroup(alert.getGroup().getGroupName())) {
			return ERROR;
		}
		
		currentDataset=alert.getCurrentDataset();
		
		/**
		 * Will implement the XML stuff somewhere else - has a place in some sort of soap interface
		 */
		
		/*
		//Quick hack to change the dynaset into something more useful...should probably extend the DynaSet to incorp this...but I'm lazy
		List<Map<String, Object>> rows = new LinkedList<Map<String,Object>>();
		for (Object o : alert.getCurrentDataset().getRows()) {
			DynaBean b = (DynaBean) o;
			Map<String,Object> columns = new HashMap<String,Object>();
			for (DynaProperty p : alert.getCurrentDataset().getDynaProperties()) {
				String key=p.getName();
				Object value = b.get(key);
				columns.put(key, value);
			}
			rows.add(columns);
		}
		
		//Need to turn the rows into an XML doc - again should extend DynaSet...will do it...honest
		
		Document doc = new DocumentImpl();
		
		Element root = doc.createElement("DASHBOARD");
		root.setAttribute("ID", alertId.toString());
		for (Map<String, Object> column : rows) {
			
			Element eRow = doc.createElementNS(null,"ROW");
			for (String key : column.keySet()) {
				Object value=column.get(key);
				Element eColumn =  doc.createElementNS(null, "COLUMN");
				eColumn.setAttributeNS(null, "NAME", key);
				eColumn.setAttributeNS(null, "VALUE", value.toString());
				eRow.appendChild(eColumn);
			}
			root.appendChild(eRow);
		}

		//output to a commons byte array stream and dump into our text var (possibly not the most efficient!)
		doc.appendChild(root);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
		of.setIndent(1);
		of.setIndenting(true);
		XMLSerializer serializer = new XMLSerializer(os,of);
		serializer.asDOMSerializer();
		serializer.serialize( doc.getDocumentElement() );
		this.gridData=os.toString();
		*/
		return SUCCESS;
	}


	public DashboardService getDashboardService() {
		return dashboardService;
	}


	public void setDashboardService(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}


	public Integer getAlertId() {
		return alertId;
	}


	public void setAlertId(Integer alertId) {
		this.alertId = alertId;
	}


	public String getGridData() {
		return gridData;
	}


	public void setGridData(String gridData) {
		this.gridData = gridData;
	}


	public RowSetDynaClass getCurrentDataset() {
		return currentDataset;
	}


	public void setCurrentDataset(RowSetDynaClass currentDataset) {
		this.currentDataset = currentDataset;
	}

}
