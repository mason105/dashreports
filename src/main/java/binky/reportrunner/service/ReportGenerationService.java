package binky.reportrunner.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.mail.EmailException;

import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.engine.beans.ViewerResults;
import binky.reportrunner.exceptions.RenderException;

public interface ReportGenerationService {

	public void processReport(String jobName, String groupName)
			throws IOException, RenderException, EmailException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, NamingException,
			NumberFormatException, ParseException;

	public List<String> processBurstedReport(String jobName, String groupName)
			throws IOException, RenderException, EmailException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, NamingException,
			NumberFormatException, ParseException;

	public String processSingleReport(String jobName, String groupName)
			throws IOException, RenderException, EmailException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException, NamingException,
			NumberFormatException, ParseException;

	public Map<RunnerJobParameter, List<Object>> getPossibleParameterValues(String jobName, String groupName) throws SQLException,NumberFormatException, ParseException;
	
	public Map<String, ViewerResults> getResultsForJob(String jobName, String groupName, List<RunnerJobParameter> parameters) throws SQLException, NumberFormatException, ParseException,RenderException, IOException;
	public Map<String, ViewerResults> getResultsForJob(String jobName, String groupName) throws SQLException, NumberFormatException, ParseException,RenderException, IOException;

	public Map<String, RowSetDynaClass > getResultSet(String groupName,String jobName,List<RunnerJobParameter> parameters) throws NumberFormatException, SQLException, ParseException;
	public Map<String, RowSetDynaClass > getResultSet(String groupName,String jobName) throws NumberFormatException, SQLException, ParseException;

}
