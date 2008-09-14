package binky.reportrunner.engine;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class CSVRenderer {

	private static final String delimeter=",";
	
	public void generateReport(ResultSet resultSet, OutputStream outputStream) throws IOException, SQLException {
		
		ResultSetMetaData metaData = resultSet.getMetaData();
		
		OutputStreamWriter osw = new OutputStreamWriter(outputStream);
		
		//logger.debug("writing header");
		StringBuilder header = new StringBuilder();
		for (int i = 1; i <= metaData.getColumnCount(); i++) {
			if (i > 1)
				header.append(delimeter);
			header.append(metaData.getColumnName(i));
		}
		osw.write(header.toString());
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
	}
}
