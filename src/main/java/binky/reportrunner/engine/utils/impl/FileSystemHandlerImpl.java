/*******************************************************************************
 * Copyright (c) 2009 Daniel Grout.
 * 
 * GNU GENERAL PUBLIC LICENSE - Version 3
 * 
 * This file is part of Report Runner (http://code.google.com/p/reportrunner).
 * 
 * Report Runner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Runner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Runner. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Module: FileSystemHandler.java
 ******************************************************************************/
package binky.reportrunner.engine.utils.impl;

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

import binky.reportrunner.engine.utils.FileSystemHandler;

public class FileSystemHandlerImpl implements FileSystemHandler {
	FileSystemManager fsManager;
	private static final Logger logger = Logger
			.getLogger(FileSystemHandlerImpl.class);

	public FileSystemHandlerImpl() throws IOException {
		this.fsManager = VFS.getManager();
	}

	/* (non-Javadoc)
	 * @see binky.reportrunner.engine.utils.FileSystemHandler#getFileObjectForUrl(java.lang.String)
	 */
	public FileObject getFileObjectForUrl(String url) throws IOException {
		FileObject file = fsManager.resolveFile(url);
		return file;
	}

	/* (non-Javadoc)
	 * @see binky.reportrunner.engine.utils.FileSystemHandler#getOutputStreamForUrl(java.lang.String)
	 */
	public OutputStream getOutputStreamForUrl(String url) throws IOException {

		FileObject file = fsManager.resolveFile(url);

		return file.getContent().getOutputStream();

	}

	/* (non-Javadoc)
	 * @see binky.reportrunner.engine.utils.FileSystemHandler#deleteFile(java.lang.String)
	 */
	public void deleteFile(String url) throws IOException {

		FileObject file = fsManager.resolveFile(url);
		file.delete();
	}

	/* (non-Javadoc)
	 * @see binky.reportrunner.engine.utils.FileSystemHandler#getFileName(java.lang.String)
	 */
	public String getFileName(String url) throws IOException {
		FileObject file = fsManager.resolveFile(url);
		return file.getName().getBaseName();
	}

	/* (non-Javadoc)
	 * @see binky.reportrunner.engine.utils.FileSystemHandler#getURL(java.lang.String)
	 */
	public URL getURL(String url) throws IOException {
		FileObject file = fsManager.resolveFile(url);
		return file.getURL();
	}

	/* (non-Javadoc)
	 * @see binky.reportrunner.engine.utils.FileSystemHandler#copyFile(java.lang.String, java.lang.String)
	 */
	public void copyFile(String url, String destinationUrl) throws IOException {

		FileObject dest = fsManager.resolveFile(destinationUrl);
		FileObject src = fsManager.resolveFile(url);
		dest.copyFrom(src, Selectors.SELECT_ALL);
	}

	/* (non-Javadoc)
	 * @see binky.reportrunner.engine.utils.FileSystemHandler#getFinalUrl(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String getFinalUrl(String url, String jobName, String groupName,
			String fileExt, String burstValue) {
		String returnUrl = getFinalUrl(url, jobName, groupName, fileExt);
		returnUrl = returnUrl.replace("!VALUE!", burstValue);
		return returnUrl;
	}

	/* (non-Javadoc)
	 * @see binky.reportrunner.engine.utils.FileSystemHandler#getFinalUrl(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
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
