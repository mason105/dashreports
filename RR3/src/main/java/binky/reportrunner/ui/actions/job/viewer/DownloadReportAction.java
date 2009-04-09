package binky.reportrunner.ui.actions.job.viewer;

import java.io.InputStream;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.engine.FileSystemHandler;
import binky.reportrunner.service.RunnerJobService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class DownloadReportAction extends StandardRunnerAction {


	private static final long serialVersionUID = 1276486788757993980L;

	private String contentDisposition;
	private String id;
	private String groupName;
	private String jobName;
	private RunnerJobService jobService;
	private InputStream inputStream;
	@Override
	public String execute() throws Exception {
		FileSystemHandler fs = new FileSystemHandler();
		inputStream=fs.getFileObjectForUrl("tmp://"+id+".tmp").getContent().getInputStream();
		RunnerJob job = jobService.getJob(jobName, groupName);
		String fileName="output."+job.getFileFormat().toString();
		contentDisposition="attachment; filename=\""+ fileName +"\"";
		return "sendFile";
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}


	public RunnerJobService getJobService() {
		return jobService;
	}

	public void setJobService(RunnerJobService jobService) {
		this.jobService = jobService;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public  String getContentDisposition() {
		return contentDisposition;
	}

}
