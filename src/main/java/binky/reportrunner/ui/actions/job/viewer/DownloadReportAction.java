package binky.reportrunner.ui.actions.job.viewer;

import java.io.InputStream;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.engine.utils.FileSystemHandler;
import binky.reportrunner.engine.utils.impl.FileSystemHandlerImpl;
import binky.reportrunner.service.ReportService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class DownloadReportAction extends StandardRunnerAction {


	private static final long serialVersionUID = 1276486788757993980L;

	private String contentDisposition;
	private String id;
	private String jobName;
	private ReportService jobService;
	private InputStream inputStream;
	@Override
	public String execute() throws Exception {
		FileSystemHandler fs = new FileSystemHandlerImpl();
		inputStream=fs.getFileObjectForUrl("tmp://"+id+".tmp").getContent().getInputStream();
		RunnerJob job = jobService.getJob(jobName, groupName);
		String ext="file";
		switch (job.getFileFormat()) {
		case CSV:
			ext="csv";
			break;
		case HTML:
			ext="html";
			break;
		case PDF:
			ext="pdf";
			break;
		case TABBED_XLS:
		case XLS:
			ext="xls";
		}
		String fileName="output."+ext;
		contentDisposition="attachment; filename=\""+ fileName +"\"";
		return "sendFile";
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


	public ReportService getJobService() {
		return jobService;
	}

	public void setJobService(ReportService jobService) {
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
