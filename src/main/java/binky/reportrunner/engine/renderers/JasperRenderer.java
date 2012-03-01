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
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerJob.FileFormat;
import binky.reportrunner.exceptions.RenderException;

public class JasperRenderer extends AbstractRenderer {

	protected Map<String, String> fileFormats;
	private JasperReport report;
	JRExporter exporter;
	private Logger logger = Logger.getLogger(JasperRenderer.class);

	public JasperRenderer(byte[] templateFile, FileFormat format) throws JRException {

		super(format);
		
		JasperDesign jasperDesign = JRXmlLoader.load(new ByteArrayInputStream(
				templateFile));
		JasperReport report = JasperCompileManager.compileReport(jasperDesign);

		this.report = report;
		
		switch (format) {
			case CSV:
				exporter = new JRCsvExporter();
				break;
			case HTML:
				exporter = new JRHtmlExporter();
				break;
			case RTF:
				exporter = new JRRtfExporter();
				break;
			case XLS:
				exporter = new JRXlsExporter();
				break;
			case TABBED_XLS:
				exporter = new JRXlsExporter();
				break;
			case PDF:
			default:
				exporter = new JRPdfExporter();
		}

		
		
	}

	@Override
	public void generateReport(ResultSet resultSet, String label, String url) throws RenderException, SQLException {
		
		
		logger.debug("creating datasource from result set");
		JRResultSetDataSource jrDs = new JRResultSetDataSource(resultSet);

		JasperPrint jp;
		try {
			logger.debug("filling report");
			jp = JasperFillManager.fillReport(report,
					new HashMap<String, Object>(), jrDs);

			logger.debug("finished filling report");

			logger.debug("exporting report");
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					super.getOutputStream(url));
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			exporter.exportReport();
		} catch (JRException e) {
			throw new RenderException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RenderException(e.getMessage(), e);
		} finally {
			resultSet.close();
		}
	}

	@Override
	protected void doFinal() {
		//nothing to do I believe
	}

}
