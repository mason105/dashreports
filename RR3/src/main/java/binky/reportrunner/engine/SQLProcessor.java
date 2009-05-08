package binky.reportrunner.engine;

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

public class SQLProcessor {

	private static final Logger logger = Logger.getLogger(SQLProcessor.class);

	public ResultSet getResults(Connection connection, String sql)
			throws SQLException {
		logger.debug("Executing sql: " + sql);

	Statement stmt = connection.createStatement();
				//ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		//boolean last = rs.last();
		//int count;
		//if (last) {
	//		count = rs.getRow();
	//		rs.beforeFirst();
	//	} else {
	//		count = 0;

		//}

		//logger.debug("got " + count + " row(s)");
		return rs;
	}

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
				stmt.setString(param.getPk().getParameterIdx(), param
						.getParameterValue());
				logger.debug("param: " + param.getPk().getParameterIdx()
						+ " type String value=" + param.getParameterValue());
				break;
			case DATE:
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				java.util.Date date = sdf.parse(param.getParameterValue());
				logger.debug("param: " + param.getPk().getParameterIdx()
						+ " type Timestamp value=" +date);
				stmt.setTimestamp(param.getPk().getParameterIdx(),
						new java.sql.Timestamp(date.getTime()));
				break;
			case BOOLEAN:
				boolean bool = Boolean.parseBoolean(param.getParameterValue());
				stmt.setBoolean(param.getPk().getParameterIdx(), bool);
				logger.debug("param: " + param.getPk().getParameterIdx()
						+ " type Boolean value="
						+ bool);
				break;
			case INTEGER:
				int intg = Integer.parseInt(param.getParameterValue());
				stmt.setInt(param.getPk().getParameterIdx(), intg);
				logger.debug("param: " + param.getPk().getParameterIdx()
						+ " type Integer value=" + intg);
				break;
			case LONG:
				long lng = Long.parseLong(param.getParameterValue());
				stmt.setLong(param.getPk().getParameterIdx(), lng);
				logger.debug("param: " + param.getPk().getParameterIdx()
						+ " type Long value=" + lng);
				break;
			case DOUBLE:
				double dbl = Double.parseDouble(param.getParameterValue());
				stmt.setDouble(param.getPk().getParameterIdx(), dbl);
				logger.debug("param: " + param.getPk().getParameterIdx()
						+ " type Double value=" + dbl);
				break;
			}
		}
		ResultSet rst = stmt.executeQuery();
		return rst;
	}

}
