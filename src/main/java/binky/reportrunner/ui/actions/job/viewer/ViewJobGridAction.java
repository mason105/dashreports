package binky.reportrunner.ui.actions.job.viewer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.RowSetDynaClass;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class ViewJobGridAction extends StandardRunnerAction {

	
	//http://trirand.com/blog/jqgrid/jqgrid.html - use array data
	private Map<String,List<String>> columns;
	private RunnerJobService jobService;
	private String jobName;
	private List<RunnerJobParameter> parameters;
	private Map<String,RowSetDynaClass> results;
	
	@Override
	public String execute() throws Exception {
		if ((this.parameters != null) && (this.parameters.size() > 0)) {
			RunnerJob job = jobService.getJob(jobName, groupName);
			
			
			
			List<RunnerJobParameter> jobParameters = job.getParameters();
			
			for (RunnerJobParameter p: this.parameters) {								
				
				for (RunnerJobParameter jp:jobParameters) {
					if (jp.getPk().getParameterIdx().equals(p.getPk().getParameterIdx())) {
						jp.setParameterValue(p.getParameterValue());
						break;
					}
				}			
			}	
			this.results= jobService.getResultSet(groupName, jobName, parameters);

		} else {
			this.results=jobService.getResultSet(groupName, jobName);
		}
		
		//populate columns
		this.columns=new HashMap<String, List<String>>();
		
		for(String key:results.keySet()) {
			List<String> cols = new LinkedList<String>();
			RowSetDynaClass rsdc = results.get(key);
			for (DynaProperty d  :rsdc.getDynaProperties()) {
				cols.add(d.getName());
			}
			columns.put(key, cols);
		}
		
		
		return SUCCESS;
	}



	public Map<String, List<String>> getColumns() {
		return columns;
	}



	public void setColumns(Map<String, List<String>> columns) {
		this.columns = columns;
	}



	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public List<RunnerJobParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<RunnerJobParameter> parameters) {
		this.parameters = parameters;
	}

	public Map<String, RowSetDynaClass> getResults() {
		return results;
	}

	public void setResults(Map<String, RowSetDynaClass> results) {
		this.results = results;
	}

}
