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

import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import binky.reportrunner.engine.renderers.exporters.AbstractExporter;
import binky.reportrunner.exceptions.ExportException;
import binky.reportrunner.exceptions.RenderException;

public class StandardRenderer extends AbstractRenderer {
	protected Map<String, String> fileFormats;

	public StandardRenderer() {
		this.fileFormats = new HashMap<String, String>();
		// TODO: this lot needs sticking in an xml config file

		// CSV Renderer
		fileFormats.put("CSV",
				"binky.reportrunner.engine.renderers.exporters.CSVExporter");
		// excel renderer
		fileFormats.put("XLS",
				"binky.reportrunner.engine.renderers.exporters.XLSExporter");
		// pdf renderer
		fileFormats.put("PDF",
				"binky.reportrunner.engine.renderers.exporters.PDFExporter");
		// rtf renderer
		fileFormats.put("RTF",
				"binky.reportrunner.engine.renderers.exporters.RTFExporter");
		// html renderer
		fileFormats.put("HTML",
				"binky.reportrunner.engine.renderers.exporters.HTMLExporter");
	}

	public void generateReport(ResultSet resultSet, OutputStream outputStream,
			String extension) throws RenderException {
		try {
			AbstractExporter exporter = (AbstractExporter) Class.forName(
					fileFormats.get(extension)).newInstance();
			exporter.export(resultSet, outputStream);
		} catch (InstantiationException e) {
			throw new RenderException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new RenderException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new RenderException(e.getMessage(), e);
		} catch (ExportException e) {
			throw new RenderException(e.getMessage(), e);
		}
	}
}
