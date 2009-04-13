package binky.reportrunner.ui.actions.dashboard;

import java.io.InputStream;

import binky.reportrunner.engine.FileSystemHandler;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class DownloadChartAction extends StandardRunnerAction {

	private static final long serialVersionUID = 1276486788757993980L;

	private String contentDisposition;

	private String id;

	private InputStream inputStream;

	@Override
	public String execute() throws Exception {
		FileSystemHandler fs = new FileSystemHandler();
		inputStream = fs.getFileObjectForUrl("tmp://" + id + ".tmp")
				.getContent().getInputStream();
		String fileName = "chart_" + id + ".png";
		contentDisposition = "attachment; filename=\"" + fileName + "\"";
		return "sendFile";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

}
