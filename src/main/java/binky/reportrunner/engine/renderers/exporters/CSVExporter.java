package binky.reportrunner.engine.renderers.exporters;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import binky.reportrunner.exceptions.ExportException;

public class CSVExporter  extends AbstractExporter{

	private static final String delimeter=",";
	private static final Logger logger = Logger.getLogger(CSVExporter.class);
	public void export(ResultSet resultSet, OutputStream outputStream) throws ExportException {
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			
			OutputStreamWriter osw = new OutputStreamWriter(outputStream);
			
			logger.debug("writing header");
			StringBuilder header = new StringBuilder();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				if (i > 1)
					header.append(delimeter);
				header.append(metaData.getColumnName(i));
			}
			osw.write(header.toString());
			logger.debug("header of file is: " + header);
			osw.write("\r\n");
			
			while (resultSet.next()) {
				StringBuilder row = new StringBuilder();
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					if (i > 1)
						row.append(delimeter);
					row.append(resultSet.getObject(i));
				}
				if (resultSet.getRow() > 1)
					osw.write("\r\n");
				osw.write(row.toString());
			}
			osw.flush();
		}catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new ExportException(e.getMessage(), e);		
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);			
			throw new ExportException(e.getMessage(), e);
		}
	}
}