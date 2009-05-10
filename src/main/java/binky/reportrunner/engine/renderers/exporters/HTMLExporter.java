package binky.reportrunner.engine.renderers.exporters;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import binky.reportrunner.exceptions.ExportException;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.html.HtmlWriter;

public class HTMLExporter extends AbstractExporter {

	@Override
	public void export(ResultSet resultSet, OutputStream outputStream)
			throws ExportException {
		try {
			Document document = new Document();
			HtmlWriter.getInstance(document, outputStream);

			// open the document object
			document.open();

			ResultSetMetaData metaData = resultSet.getMetaData();
			Table table = new Table(metaData.getColumnCount());
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				Paragraph para = new Paragraph(metaData.getColumnName(i),
						new Font(Font.HELVETICA, 10, Font.BOLD));
				Cell cell = new Cell(para);
				table.addCell(cell);
			}

			while (resultSet.next()) {
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					Paragraph para = new Paragraph("" + resultSet.getObject(i),
							new Font(Font.HELVETICA, 10, Font.NORMAL));
					Cell cell = new Cell(para);
					table.addCell(cell);
				}

			}
			document.add(table);
			document.close();
		} catch (DocumentException e) {
			throw new ExportException(e.getMessage(), e);
		} catch (SQLException e) {
			throw new ExportException(e.getMessage(), e);
		}
	}

}
