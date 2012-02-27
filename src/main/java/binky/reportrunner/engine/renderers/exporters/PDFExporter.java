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
 * Module: PDFExporter.java
 ******************************************************************************/
package binky.reportrunner.engine.renderers.exporters;

import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import binky.reportrunner.exceptions.ExportException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class PDFExporter extends AbstractExporter {

	@Override
	public void export(ResultSet resultSet,String label, OutputStream outputStream)
			throws ExportException {
		try {
			Document document = new Document();
			PdfWriter.getInstance(document, outputStream);

			// open the document object
			document.open();

			ResultSetMetaData metaData = resultSet.getMetaData();
			PdfPTable table = new PdfPTable(metaData.getColumnCount());
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				Paragraph para = new Paragraph(metaData.getColumnName(i),
						new Font(Font.HELVETICA, 10, Font.BOLD));
				PdfPCell cell = new PdfPCell(para);
				table.addCell(cell);
			}

			while (resultSet.next()) {
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					Paragraph para = new Paragraph("" + resultSet.getObject(i),
							new Font(Font.HELVETICA, 10, Font.NORMAL));
					PdfPCell cell = new PdfPCell(para);
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
