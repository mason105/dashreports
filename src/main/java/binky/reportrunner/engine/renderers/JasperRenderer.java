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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintPage;
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
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerJob.FileFormat;
import binky.reportrunner.exceptions.RenderException;

public class JasperRenderer extends AbstractRenderer {

	private JasperReport report;
	JRExporter exporter;
	private Logger logger = Logger.getLogger(JasperRenderer.class);
	
	private boolean tabbedXLS;
	private String url;
	
	private JasperPrint print;
	
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
				exporter = new JRXlsxExporter();
				this.tabbedXLS=true;
				break;
			case PDF:
			default:
				exporter = new JRPdfExporter();
		}

		
		
	}

	private JasperPrint generateSinglePage(ResultSet rs, String label) throws RenderException, SQLException {

		logger.debug("creating datasource from result set");
		JRResultSetDataSource jrDs = new JRResultSetDataSource(rs);
		
		JasperPrint jp;
		try {
			logger.debug("filling report");					
			jp = JasperFillManager.fillReport(report,
					new HashMap<String, Object>(), jrDs);
			jp.setName(label);
			logger.debug("finished filling report");
			
			return jp;
		
		} catch (JRException e) {
			throw new RenderException(e.getMessage(), e);		
		} finally {
			rs.close();
		}

		
	}
	
	@Override
	public void generateReport(ResultSet resultSet, String label, String url) throws RenderException, SQLException {
		JasperPrint jp = generateSinglePage(resultSet, label);
		if (tabbedXLS) {
			
			//thanks to: http://stackoverflow.com/questions/3977658/how-do-you-export-a-jasperreport-to-an-excel-file-with-multiple-worksheets/3979026#3979026
			
			this.url=url;
			if (this.print==null) {
				this.print=jp;
			} else {
				List<JRPrintPage> pages = new ArrayList<JRPrintPage>(jp.getPages());
				int i = this.print.getPages().size();
				for (int count = 0; count < pages.size(); count++) {
					this.print.addPage(i, (JRPrintPage) pages.get(count));
				    i++;
				}
			}
		} else {
			try {
						logger.debug("exporting report");
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					super.getOutputStream(url));
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
					exporter.exportReport();
			} catch (IOException e) {
				throw new RenderException(e.getMessage(), e);
			} catch (JRException e) {
				throw new RenderException(e.getMessage(), e);					
			}
		}
	}

	@Override
	protected void doFinal() throws RenderException {
		if (tabbedXLS) {
			try {
					logger.debug("exporting report");
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					super.getOutputStream(this.url));
					this.print.setProperty("isIgnorePagination", "true");
					exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, true);
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, this.print);					
					exporter.exportReport();			
			} catch (IOException e) {
				throw new RenderException(e.getMessage(), e);
			} catch (JRException e) {
				throw new RenderException(e.getMessage(), e);					
			}
		}
	}

}
