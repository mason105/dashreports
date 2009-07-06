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
 * Module: JasperRenderer.java
 ******************************************************************************/
package binky.reportrunner.engine.renderers;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import binky.reportrunner.exceptions.RenderException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class JasperRenderer extends AbstractRenderer {

	protected Map<String, String> fileFormats;
	private JasperReport report;

	private Logger logger = Logger.getLogger(JasperRenderer.class);
	
	public JasperRenderer(byte[] templateFile) throws JRException {
		this.fileFormats = new HashMap<String, String>();
		// TODO: this lot needs sticking in an xml config file

		// CSV Renderer
		fileFormats.put("CSV",
				"net.sf.jasperreports.engine.export.JRCsvExporter");
		// excel renderer
		fileFormats.put("XLS",
				"net.sf.jasperreports.engine.export.JRXlsExporter");
		// pdf renderer
		fileFormats.put("PDF",
				"net.sf.jasperreports.engine.export.JRPdfExporter");
		// rtf renderer
		fileFormats.put("RTF",
				"net.sf.jasperreports.engine.export.JRRtfExporter");
		// html renderer
		fileFormats.put("HTML",
				"net.sf.jasperreports.engine.export.JRHtmlExporter");
		
		JasperDesign jasperDesign = JRXmlLoader.load(new ByteArrayInputStream(templateFile));
		JasperReport report = JasperCompileManager
				.compileReport(jasperDesign);
	
		
		this.report = report;
	}

	public void generateReport(ResultSet resultSet, OutputStream outputStream,
			String extension) throws RenderException, SQLException {

		logger.debug("creating datasource from result set");
		JRResultSetDataSource jrDs = new JRResultSetDataSource(resultSet);

		JasperPrint jp;
		try {
			logger.debug("filling report");
			jp = JasperFillManager.fillReport(report,
					new HashMap<String, Object>(), jrDs);

			logger.debug("finished filling report");

			JRExporter exporter = (JRExporter) Class.forName(
					fileFormats.get(extension)).newInstance();

			logger.debug("exporting report");
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					outputStream);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			exporter.exportReport();
		} catch (JRException e) {
			throw new RenderException(e.getMessage(), e);
		} catch (InstantiationException e) {
			throw new RenderException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new RenderException(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			throw new RenderException(e.getMessage(), e);
		} finally {
			resultSet.close();	
		}

	}

}
