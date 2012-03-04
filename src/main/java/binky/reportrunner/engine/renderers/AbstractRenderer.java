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
 * Module: AbstractRenderer.java
 ******************************************************************************/
package binky.reportrunner.engine.renderers;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerJob.FileFormat;
import binky.reportrunner.engine.utils.FileSystemHandler;
import binky.reportrunner.engine.utils.impl.FileSystemHandlerImpl;
import binky.reportrunner.exceptions.RenderException;

public abstract class AbstractRenderer {

	protected FileFormat format;
	public AbstractRenderer(FileFormat format) {
		this.format=format;
	}
	
	private OutputStream outputStream;
	private String url;
	protected OutputStream getOutputStream(String url) throws IOException {
		FileSystemHandler fs = new FileSystemHandlerImpl();
		//only retain the output stream if tabbed xls
		if (this.outputStream==null) {
			logger.debug("getting a first time outputstream for url " + url);			
			this.outputStream= fs.getOutputStreamForUrl(url);	
			this.url=url;			
		} else {
			if (!url.equals(this.url)||format!=FileFormat.TABBED_XLS) {
				logger.debug("closing output stream for url: "+ url + " and getting an outputstream for url " + url);
				this.outputStream.flush();
				this.outputStream.close();
				this.outputStream= fs.getOutputStreamForUrl(url);
				this.url=url;
			}			
		}
		return this.outputStream;
	}
	
	private static final Logger logger = Logger.getLogger(AbstractRenderer.class);
	
	public  void closeOutputStream() throws IOException {
		logger.debug("closing the output stream");
		if (this.outputStream!=null) {
			this.doFinal();
			this.outputStream.close();
		}
	}
	
	protected abstract void doFinal()throws IOException;
	
	public abstract void generateReport(ResultSet resultSet, String label, String url) throws RenderException, SQLException;
	
}
