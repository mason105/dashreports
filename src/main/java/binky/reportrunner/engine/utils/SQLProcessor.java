package binky.reportrunner.engine.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import binky.reportrunner.data.RunnerJobParameter;

public interface SQLProcessor {

	public abstract ResultSet getResults(Connection connection, String sql)
			throws SQLException;

	public abstract ResultSet getResults(Connection connection, String sql,
			List<RunnerJobParameter> parameters) throws SQLException,
			NumberFormatException, ParseException;

}