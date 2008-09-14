package binky.reportrunner.engine;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class JasperRenderer {

	protected Map<String, String> fileFormats = new HashMap<String, String>();
	{
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
	}

	public void generateReport(JasperReport report, ResultSet resultSet, OutputStream outputStream, String extension) throws InstantiationException, IllegalAccessException, ClassNotFoundException, JRException {

		JRResultSetDataSource jrDs = new JRResultSetDataSource(resultSet);

		JasperPrint jp = JasperFillManager.fillReport(report,
				new HashMap<String, Object>(), jrDs);

		// logger.debug("finished filling report");

		JRExporter exporter = (JRExporter) Class.forName(
				fileFormats.get(extension)).newInstance();

		//logger.debug(reportName + " :exporting report");
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
		exporter.exportReport();

	}

}
