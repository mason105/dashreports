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
 * Module: RunnerResultGenerator.java
 ******************************************************************************/
package binky.reportrunner.engine;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

import org.apache.log4j.Logger;

import binky.reportrunner.data.RunnerJob;
import binky.reportrunner.data.RunnerJobParameter;
import binky.reportrunner.data.RunnerJob.Template;
import binky.reportrunner.engine.renderers.AbstractRenderer;
import binky.reportrunner.engine.renderers.JasperRenderer;
import binky.reportrunner.engine.renderers.StandardRenderer;
import binky.reportrunner.exceptions.RenderException;

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

		if ((job.getIsBurst() != null) && (job.getIsBurst())) {

			ResultSet burstResults = sqlProcessor.getResults(conn, job
					.getBurstQuery());

			List<RunnerJobParameter> params = job.getParameters();
			while (burstResults.next()) {
				// populate the parameters
				List<RunnerJobParameter> populatedParams = new LinkedList<RunnerJobParameter>();
				for (RunnerJobParameter param : params) {
					if ((param.getParameterBurstColumn() != null)
							&& (!param.getParameterBurstColumn().isEmpty())) {
						if (((param.getParameterValue()== null) || param.getParameterValue().isEmpty())) {
							param.setParameterValue(burstResults.
									getObject(param.getParameterBurstColumn()).toString());
							populatedParams.add(param);
							logger.debug("added populated param"
									+ param.getPk().getParameterIdx()
									+ " - value - " + param.getParameterValue());
						} else {
							logger.debug("using overide value" + param.getParameterValue());
							populatedParams.add(param);
						}
					} else {
						logger.debug("standard parameter");
						populatedParams.add(param);						
					}
				}

				String name = burstResults.getObject(
						job.getBurstFileNameParameterName()).toString();

				// process the query with the results in
				if (!results.containsKey(name)){
					//hack to prevent it repeating its self on the viewer
					boolean add=true;
					
					for (RunnerJobParameter p :params) {
						if (p.getParameterBurstColumn().equalsIgnoreCase(job.getBurstFileNameParameterName())) {
							if (((p.getParameterValue()!= null) && !p.getParameterValue().isEmpty())) {
								if (!name.equals(p.getParameterValue())) add=false;
							}
						}
					}
					
					if (add) {
						ResultSet rs = sqlProcessor.getResults(conn, job.getQuery(),
						populatedParams);

						results.put(name, rs);
					}
				}

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

	public void renderReport(ResultSet results, String url,
			byte[] templateFile, Template templateType, String fileFormat)
			throws RenderException, IOException {
		FileSystemHandler fs = new FileSystemHandler();
		OutputStream os = fs.getOutputStreamForUrl(url);
		AbstractRenderer renderer;

		switch (templateType) {
		case JASPER:

			try {
				renderer = new JasperRenderer(templateFile);
			} catch (JRException e) {
				logger.error(e.getMessage(), e);
				throw new RenderException(e.getMessage(), e);
			}
			break;
		default:
			renderer = new StandardRenderer();
		}
		renderer.generateReport(results, os, fileFormat);
		os.close();
	}

}
