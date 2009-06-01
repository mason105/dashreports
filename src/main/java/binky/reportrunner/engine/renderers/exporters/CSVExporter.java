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
 * Module: CSVExporter.java
 ******************************************************************************/
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
