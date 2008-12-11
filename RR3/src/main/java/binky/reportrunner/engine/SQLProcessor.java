package binky.reportrunner.engine;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerJobParameter;

public class SQLProcessor {

	private static final Logger logger = Logger.getLogger(SQLProcessor.class);
	
	public ResultSet getResults(Connection connection,String sql) throws SQLException{
		logger.debug("Executing sql: " + sql);

		Statement stmt = connection.createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, 
				   ResultSet.CONCUR_READ_ONLY);
		ResultSet rs =stmt.executeQuery(sql);
		rs.last();
		int count = rs.getRow();
		rs.beforeFirst();

		logger.debug("got " + count + " row(s)");
		return rs;
	}
	
	public ResultSet getResults(Connection connection,String sql, List<RunnerJobParameter> parameters) throws SQLException, NumberFormatException{
		PreparedStatement stmt = connection.prepareStatement(sql);
		
		//1=String 2=Timestamp 3=Boolean 4=Float 5=Long 6=Double 7=BigDecimal
		
		for (RunnerJobParameter param:parameters) {
			switch (param.getParameterType()) {
			case 1 :
				stmt.setString(param.getPk().getParameterIdx(), param.getParameterValue());
				break;
			case 2 :
				stmt.setTimestamp(param.getPk().getParameterIdx(), new Timestamp(Long.parseLong(param.getParameterValue())));
				break;
			case 3:
				stmt.setBoolean(param.getPk().getParameterIdx(), Boolean.parseBoolean(param.getParameterValue()));
				break;
			case 4:
				stmt.setFloat(param.getPk().getParameterIdx(), Float.parseFloat(param.getParameterValue()));				
				break;
			case 5:
				stmt.setLong(param.getPk().getParameterIdx(), Long.parseLong(param.getParameterValue()));				
				break;	
			case 6:
				stmt.setDouble(param.getPk().getParameterIdx(), Double.parseDouble(param.getParameterValue()));				
				break;	
			case 7:
				stmt.setBigDecimal(param.getPk().getParameterIdx(), new BigDecimal(Double.parseDouble(param.getParameterValue())));				
				break;			
			}
		}
		
		return stmt.executeQuery();
	}
	
}
