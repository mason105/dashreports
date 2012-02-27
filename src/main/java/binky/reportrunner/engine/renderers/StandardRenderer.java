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
 * Module: StandardRenderer.java
 ******************************************************************************/
package binky.reportrunner.engine.renderers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import binky.reportrunner.data.RunnerJob.FileFormat;
import binky.reportrunner.engine.renderers.exporters.AbstractExporter;
import binky.reportrunner.engine.renderers.exporters.CSVExporter;
import binky.reportrunner.engine.renderers.exporters.HTMLExporter;
import binky.reportrunner.engine.renderers.exporters.PDFExporter;
import binky.reportrunner.engine.renderers.exporters.RTFExporter;
import binky.reportrunner.engine.renderers.exporters.TabbedXLSExporter;
import binky.reportrunner.engine.renderers.exporters.XLSExporter;
import binky.reportrunner.exceptions.RenderException;

public class StandardRenderer extends AbstractRenderer {

	FileFormat format;

	AbstractExporter exporter;

	public StandardRenderer(FileFormat format) {
		super(format);
		switch (format) {
		case CSV:
			this.exporter = new CSVExporter();
			break;
		case HTML:
			this.exporter = new HTMLExporter();
			break;
		case RTF:
			this.exporter = new RTFExporter();
			break;
		case XLS:
			this.exporter = new XLSExporter();
			break;
		case TABBED_XLS: 
			this.exporter = new TabbedXLSExporter();
			break;
		case PDF:
		default:
			this.exporter = new PDFExporter();
		}
	}

	@Override
	public void generateReport(ResultSet resultSet, String label,String url) throws RenderException, SQLException {
	
		try {
			this.exporter.export(resultSet,label,  super.getOutputStream(url));
		}catch (IOException e) {
			throw new RenderException(e.getMessage(), e);
		} finally {
			resultSet.close();
		}

	}

	@Override
	protected void doFinal() throws IOException {
		if (format==FileFormat.TABBED_XLS) ((TabbedXLSExporter)exporter).writeData();
	}
}
