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
 * Module: SQLProcessor.java
 ******************************************************************************/
package binky.reportrunner.engine.utils.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.engine.utils.SQLProcessor;

public class SQLProcessorImpl implements SQLProcessor {

	private static final Logger logger = Logger.getLogger(SQLProcessorImpl.class);

	/* (non-Javadoc)
	 * @see binky.reportrunner.engine.utils.SQLProcessor#getResults(java.sql.Connection, java.lang.String)
	 */
	public ResultSet getResults(Connection connection, String sql)
			throws SQLException {
		logger.debug("Executing sql: " + sql);

	Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}

	/* (non-Javadoc)
	 * @see binky.reportrunner.engine.utils.SQLProcessor#getResults(java.sql.Connection, java.lang.String, java.util.List)
	 */
	public ResultSet getResults(Connection connection, String sql,
			List<RunnerJobParameter> parameters) throws SQLException,
			NumberFormatException, ParseException {
		PreparedStatement stmt = connection.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
		
		// 1=String 2=Date 3=Boolean 4=int 5=Long 6=Double
		//date format yyyy-MM-dd HH:mm:ss
		logger.debug("executing sql: " + sql);
		logger.debug("parsing " + parameters.size() + " parameters");
		for (RunnerJobParameter param : parameters) {
			switch (param.getParameterType()) {
			case STRING:
				stmt.setString(param.getPk().getParameterIdx()+1, param
						.getParameterValue());
				logger.debug("param: " + param.getPk().getParameterIdx()+1
						+ " type String value=" + param.getParameterValue());
				break;
			case DATE:
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				java.util.Date date = sdf.parse(param.getParameterValue());
				logger.debug("param: " + param.getPk().getParameterIdx()
						+ " type Timestamp value=" +date);
				stmt.setTimestamp(param.getPk().getParameterIdx()+1,
						new java.sql.Timestamp(date.getTime()));
				break;
			case BOOLEAN:
				boolean bool = Boolean.parseBoolean(param.getParameterValue());
				stmt.setBoolean(param.getPk().getParameterIdx()+1, bool);
				logger.debug("param: " + param.getPk().getParameterIdx()+1
						+ " type Boolean value="
						+ bool);
				break;
			case INTEGER:
				int intg = Integer.parseInt(param.getParameterValue());
				stmt.setInt(param.getPk().getParameterIdx()+1, intg);
				logger.debug("param: " + param.getPk().getParameterIdx()+1
						+ " type Integer value=" + intg);
				break;
			case LONG:
				long lng = Long.parseLong(param.getParameterValue());
				stmt.setLong(param.getPk().getParameterIdx()+1, lng);
				logger.debug("param: " + param.getPk().getParameterIdx()
						+ " type Long value=" + lng);
				break;
			case DOUBLE:
				double dbl = Double.parseDouble(param.getParameterValue());
				stmt.setDouble(param.getPk().getParameterIdx()+1, dbl);
				logger.debug("param: " + param.getPk().getParameterIdx()
						+ " type Double value=" + dbl);
				break;
			}
		}
		ResultSet rst = stmt.executeQuery();
		return rst;
	}

}
