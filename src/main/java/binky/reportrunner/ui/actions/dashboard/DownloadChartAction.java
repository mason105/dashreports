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
 * Module: DownloadChartAction.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.dashboard;

import java.io.InputStream;

import binky.reportrunner.engine.utils.FileSystemHandler;
import binky.reportrunner.engine.utils.impl.FileSystemHandlerImpl;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class DownloadChartAction extends StandardRunnerAction {

	private static final long serialVersionUID = 1276486788757993980L;

	private String contentDisposition;

	private String id;

	private InputStream inputStream;

	@Override
	public String execute() throws Exception {
		FileSystemHandler fs = new FileSystemHandlerImpl();
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
