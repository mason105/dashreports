package binky.reportrunner.engine.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.vfs2.FileObject;

public interface FileSystemHandler {

	public abstract FileObject getFileObjectForUrl(String url)
			throws IOException;

	public abstract OutputStream getOutputStreamForUrl(String url)
			throws IOException;

	public abstract void deleteFile(String url) throws IOException;

	public abstract String getFileName(String url) throws IOException;

	public abstract URL getURL(String url) throws IOException;

	public abstract void copyFile(String url, String destinationUrl)
			throws IOException;

	public abstract String getFinalUrl(String url, String jobName,
			String groupName, String fileExt, String burstValue);

	public abstract String getFinalUrl(String url, String jobName,
			String groupName, String fileExt);

}