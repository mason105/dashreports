package binky.reportrunner.ui.actions.navigation;

import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.dao.RunnerGroupDao;
import binky.reportrunner.data.RunnerGroup;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class LeftNavAction extends StandardRunnerAction {
	
	private static final Logger logger = Logger.getLogger(LeftNavAction.class);
	private static final long serialVersionUID = -2321083106251542716L;
	private RunnerGroupDao groupDao;
	private List<RunnerGroup> groups;
	private Boolean expandAdmin;
	private Boolean expandGroups;
	private String actionName;
	
	
	
	@Override
	public String execute() throws Exception {
		//hack to force the tree to keep the correct folders open
		if ((Class.forName(actionName).newInstance() instanceof AdminRunnerAction) 
				&& getSessionUser().getIsAdmin() ){
			expandAdmin=true;
		} else {
			expandAdmin=false;
		}
		
		if (actionName.toLowerCase().contains("setupViewJob")
				||actionName.toLowerCase().contains("viewJobOutput")
				||actionName.toLowerCase().contains("invokeJob")
				||actionName.toLowerCase().contains("changeAllGroupJobStatus")
				||actionName.toLowerCase().contains("viewJobDetail")
				||actionName.toLowerCase().contains("setupEditJob")
				||actionName.toLowerCase().contains("setJobStatus")
				||actionName.toLowerCase().contains("saveJob")
				||actionName.toLowerCase().contains("deleteJob")
				||actionName.toLowerCase().contains("listJobs")
			) {
				expandGroups=true;			       
		} else {
			expandGroups=false;
		}
		
		if (super.getSessionUser().getIsAdmin()) {
			groups = groupDao.listGroups();
		} else {			
			super.getSessionUser().getGroups();
		}
		
		
		return SUCCESS;
	}
	public RunnerGroupDao getGroupDao() {
		return groupDao;
	}
	public void setGroupDao(RunnerGroupDao groupDao) {
		this.groupDao = groupDao;
	}
	public List<RunnerGroup> getGroups() {
		return groups;
	}
	public void setGroups(List<RunnerGroup> groups) {
		this.groups = groups;
	}
	public Boolean getExpandAdmin() {
		return expandAdmin;
	}
	public void setExpandAdmin(Boolean expandAdmin) {
		this.expandAdmin = expandAdmin;
	}
	public Boolean getExpandGroups() {
		return expandGroups;
	}
	public void setExpandGroups(Boolean expandGroups) {
		this.expandGroups = expandGroups;
	}
	
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	

}
