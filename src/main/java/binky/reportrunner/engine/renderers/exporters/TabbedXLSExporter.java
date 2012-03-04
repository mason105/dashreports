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
 * Module: XLSExporter.java
 ******************************************************************************/
package binky.reportrunner.engine.renderers.exporters;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import binky.reportrunner.exceptions.ExportException;

public class TabbedXLSExporter extends AbstractExporter {

	private OutputStream outputStream;

	private HSSFWorkbook wb;

	public void writeData() throws IOException {
		// Write the output to the stream file
		logger.debug("flusing xls data");
		wb.write(outputStream);
		outputStream.flush();
	}
	private static final Logger logger = Logger.getLogger(TabbedXLSExporter.class);
	@SuppressWarnings("deprecation")
	@Override
	public void export(ResultSet resultSet, String label,
			OutputStream outputStream) throws ExportException {
		
		if (this.outputStream == null)
			this.outputStream = outputStream;

		try {
			if (wb == null) {
				logger.trace("creating new workbook");
				wb = new HSSFWorkbook();
			}
	
			logger.trace("creaing worksheet " + label);
			HSSFSheet sheet = wb.createSheet(label);
			ResultSetMetaData metaData;

			metaData = resultSet.getMetaData();

			short rowCount = 0;
			// logger.debug("writing header");
			HSSFRow headerRow = sheet.createRow(rowCount);
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				// TODO:fix
				HSSFCell cell = headerRow.createCell((short) (i - 1));
				HSSFRichTextString string = new HSSFRichTextString(
						metaData.getColumnName(i));
				string.applyFont(HSSFFont.BOLDWEIGHT_BOLD);
				cell.setCellValue(string);
			}

			while (resultSet.next()) {
				rowCount++;
				HSSFRow row = sheet.createRow(rowCount);
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					// TODO:fix
					HSSFCell cell = row.createCell((short) (i - 1));

					// TODO:make this better by using types
					HSSFRichTextString string = new HSSFRichTextString(""
							+ resultSet.getObject(i));
					cell.setCellValue(string);

				}
			}

		} catch (SQLException e) {
			throw new ExportException(e.getMessage(), e);
		}
	}

}
