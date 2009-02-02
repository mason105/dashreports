package binky.reportrunner.engine.renderers;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

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
		
		JasperDesign jasperDesign = JRXmlLoader.load(new String(templateFile));
		JasperReport report = JasperCompileManager
				.compileReport(jasperDesign);
	
		
		this.report = report;
	}

	public void generateReport(ResultSet resultSet, OutputStream outputStream,
			String extension) throws RenderException {

		JRResultSetDataSource jrDs = new JRResultSetDataSource(resultSet);

		JasperPrint jp;
		try {
			jp = JasperFillManager.fillReport(report,
					new HashMap<String, Object>(), jrDs);

			// logger.debug("finished filling report");

			JRExporter exporter = (JRExporter) Class.forName(
					fileFormats.get(extension)).newInstance();

			// logger.debug(reportName + " :exporting report");
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
		}

	}

}
