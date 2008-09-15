package binky.reportrunner.engine.renderers.exporters;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XLSExporter extends AbstractExporter {

	@Override
	public void export(ResultSet resultSet, OutputStream outputStream)
			throws ExportException {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Report");
			ResultSetMetaData metaData;

			metaData = resultSet.getMetaData();

			short rowCount = 0;
			// logger.debug("writing header");
			HSSFRow headerRow = sheet.createRow(rowCount);
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				HSSFCell cell = headerRow.createCell((short) (i - 1));
				HSSFRichTextString string = new HSSFRichTextString(metaData
						.getColumnName(i));
				string.applyFont(HSSFFont.BOLDWEIGHT_BOLD);
				cell.setCellValue(string);
			}

			while (resultSet.next()) {
				rowCount++;
				HSSFRow row = sheet.createRow(rowCount);
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					HSSFCell cell = row.createCell((short) (i - 1));

					// TODO:make this better by using types
					HSSFRichTextString string = new HSSFRichTextString(""
							+ resultSet.getObject(i));
					cell.setCellValue(string);

				}
			}

			// Write the output to the stream file
			wb.write(outputStream);
		} catch (SQLException e) {
			throw new ExportException(e.getMessage(), e);
		} catch (IOException e) {
			throw new ExportException(e.getMessage(), e);
		}
	}

}
