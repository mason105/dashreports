package binky.reportrunner.engine;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJob.Template;
import binky.reportrunner.exceptions.RenderException;

public interface RunnerResultGenerator {

	public abstract boolean getResultsForJob(RunnerJob job,
			Map<String, ResultSet> results) throws SQLException,
			NumberFormatException, ParseException;

	public abstract void renderReport(ResultSet results, String url,
			byte[] templateFile, Template templateType, String fileFormat)
			throws RenderException, IOException;

}