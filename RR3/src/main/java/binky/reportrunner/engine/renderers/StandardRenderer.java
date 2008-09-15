package binky.reportrunner.engine.renderers;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import binky.reportrunner.engine.renderers.exporters.AbstractExporter;
import binky.reportrunner.engine.renderers.exporters.ExportException;

public class StandardRenderer extends AbstractRenderer {
	protected Map<String, String> fileFormats;

	public StandardRenderer() {
		this.fileFormats = new HashMap<String, String>();
		// TODO: this lot needs sticking in an xml config file

		// CSV Renderer
		fileFormats.put("CSV",
				"binky.reportrunner.engine.renderers.exporter.CSVExporter");
		// excel renderer
		fileFormats.put("XLS",
				"binky.reportrunner.engine.renderers.exporter.XLSExporter");
		// pdf renderer
		fileFormats.put("PDF",
				"binky.reportrunner.engine.renderers.exporter.PDFExporter");
		// rtf renderer
		fileFormats.put("RTF",
				"binky.reportrunner.engine.renderers.exporter.RTFExporter");
		// html renderer
		fileFormats.put("HTML",
				"binky.reportrunner.engine.renderers.exporter.HTMLExporter");
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
