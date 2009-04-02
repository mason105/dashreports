package binky.reportrunner.engine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;

public class RunnerResultGenerator {
	private Logger logger = Logger.getLogger(RunnerResultGenerator.class);

	Connection conn;

	public RunnerResultGenerator(Connection conn) {
		this.conn = conn;
	}

	public boolean getResultsForJob(RunnerJob job,
			Map<String, ResultSet> results) throws SQLException,
			NumberFormatException, ParseException {
		SQLProcessor sqlProcessor = new SQLProcessor();

		if ((job.getIsBurst()!=null) && (job.getIsBurst())) {

			ResultSet burstResults = sqlProcessor.getResults(conn, job
					.getBurstQuery());

			List<RunnerJobParameter> params = job.getParameters();
			while (burstResults.next()) {
				// populate the parameters
				List<RunnerJobParameter> populatedParams = new LinkedList<RunnerJobParameter>();
				for (RunnerJobParameter param : params) {
					param.setParameterValue(""
							+ burstResults.getObject(param
									.getParameterBurstColumn()));
					populatedParams.add(param);
					logger.debug("added populated param"
							+ param.getPk().getParameterIdx() + " - value - "
							+ param.getParameterValue());
				}

				String name = burstResults.getObject(
						job.getBurstFileNameParameterName()).toString();

				// process the query with the results in

				ResultSet rs = sqlProcessor.getResults(conn, job.getQuery(),
						populatedParams);

				results.put(name, rs);

			}

		} else {
			String name = "Results";

			ResultSet rs;
			if ((job.getParameters() != null)
					&& (job.getParameters().size() > 0)) {
				rs = sqlProcessor.getResults(conn, job.getQuery(), job
						.getParameters());
			} else {
				rs = sqlProcessor.getResults(conn, job.getQuery());
			}

			results.put(name, rs);
		}

		return true;
	}

}
