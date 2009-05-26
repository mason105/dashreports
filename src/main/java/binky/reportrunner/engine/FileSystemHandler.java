package binky.reportrunner.engine;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.VFS;
import org.apache.log4j.Logger;

public class FileSystemHandler {
	FileSystemManager fsManager;
	private static final Logger logger = Logger
			.getLogger(FileSystemHandler.class);

	public FileSystemHandler() throws IOException {
		this.fsManager = VFS.getManager();
	}

	public FileObject getFileObjectForUrl(String url) throws IOException {
		FileObject file = fsManager.resolveFile(url);
		return file;
	}

	public OutputStream getOutputStreamForUrl(String url) throws IOException {

		FileObject file = fsManager.resolveFile(url);

		return file.getContent().getOutputStream();

	}

	public void deleteFile(String url) throws IOException {

		FileObject file = fsManager.resolveFile(url);
		file.delete();
	}

	public String getFileName(String url) throws IOException {
		FileObject file = fsManager.resolveFile(url);
		return file.getName().getBaseName();
	}

	public URL getURL(String url) throws IOException {
		FileObject file = fsManager.resolveFile(url);
		return file.getURL();
	}

	public void copyFile(String url, String destinationUrl) throws IOException {

		FileObject dest = fsManager.resolveFile(destinationUrl);
		FileObject src = fsManager.resolveFile(url);
		dest.copyFrom(src, Selectors.SELECT_ALL);
	}

	public String getFinalUrl(String url, String jobName, String groupName,
			String fileExt, String burstValue) {
		String returnUrl = getFinalUrl(url, jobName, groupName, fileExt);
		returnUrl = returnUrl.replace("!VALUE!", burstValue);
		return returnUrl;
	}

	public String getFinalUrl(String url, String jobName, String groupName,
			String fileExt) {
		String returnUrl;
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyymmhhss");
		Calendar cal = Calendar.getInstance();
		String dateFormatted = dateFormat.format(cal.getTime());
		if ((url == null) || (url.isEmpty())) {
			returnUrl = "file://" + System.getProperty("java.io.tmpdir")
					+ dateFormatted + "_" + jobName.replace(" ", "_")
					+ groupName.replace(" ", "_") + "." + fileExt;
		} else {

			returnUrl = url;

			int start = returnUrl.indexOf("@", 0);
			start++;
			int end = returnUrl.indexOf("@", start);
			logger.debug("start " + start + " end " + end);
			if ((start >= 1) && (end > start)) {
				String format = returnUrl.substring((start), (end));
				logger.debug("format: " + format);
				SimpleDateFormat df = new SimpleDateFormat(format);
				Calendar calen = Calendar.getInstance();
				// ***
				String formatted = df.format(calen.getTime());
				logger.debug("date formatted: " + formatted.toUpperCase());
				returnUrl = returnUrl.replaceAll("@" + format + "@", formatted
						.toUpperCase());
				logger.debug("fileName: " + returnUrl);
			}

		}

		return returnUrl;
	}

}
