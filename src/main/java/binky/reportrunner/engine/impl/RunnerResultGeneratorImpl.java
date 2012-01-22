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
package binky.reportrunner.engine.impl;

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
import binky.reportrunner.engine.RunnerResultGenerator;
import binky.reportrunner.engine.renderers.AbstractRenderer;
import binky.reportrunner.engine.renderers.JasperRenderer;
import binky.reportrunner.engine.renderers.StandardRenderer;
import binky.reportrunner.engine.utils.FileSystemHandler;
import binky.reportrunner.engine.utils.SQLProcessor;
import binky.reportrunner.engine.utils.impl.FileSystemHandlerImpl;
import binky.reportrunner.engine.utils.impl.SQLProcessorImpl;
import binky.reportrunner.exceptions.RenderException;

public class RunnerResultGeneratorImpl implements RunnerResultGenerator {
	private Logger logger = Logger.getLogger(RunnerResultGeneratorImpl.class);

	Connection conn;

	public RunnerResultGeneratorImpl(Connection conn) {
		this.conn = conn;
	}

	/* (non-Javadoc)
	 * @see binky.reportrunner.engine.impl.RunnerResultGenerator#getResultsForJob(binky.reportrunner.data.RunnerJob, java.util.Map)
	 */
	public boolean getResultsForJob(RunnerJob job,
			Map<String, ResultSet> results) throws SQLException,
			NumberFormatException, ParseException {
		SQLProcessor sqlProcessor = new SQLProcessorImpl();
		
		List<String> processed = new LinkedList<String>();
		
		if ((job.getIsBurst() != null) && (job.getIsBurst())) {

			ResultSet burstResults = sqlProcessor.getResults(conn, job
					.getBurstQuery());

			List<RunnerJobParameter> params = job.getParameters();
			while (burstResults.next()) {
				// populate the parameters
				List<RunnerJobParameter> populatedParams = new LinkedList<RunnerJobParameter>();
				String name="";
				for (RunnerJobParameter param : params) {
					RunnerJobParameter paramNew = new RunnerJobParameter();
					//copy the parameter
					paramNew.setPk(param.getPk());
					paramNew.setParameterBurstColumn(param.getParameterBurstColumn());
					paramNew.setParameterType(param.getParameterType());
					paramNew.setParameterValue(param.getParameterValue());
					//is this parameter populated via the burst query
					if ((param.getParameterBurstColumn() != null)
							&& (!param.getParameterBurstColumn().isEmpty())) {
						//Check that the parameter has not already been assigned a value by the user
						if (((param.getParameterValue()== null) || param.getParameterValue().isEmpty())) {
							//no value already assigned so pull from DB
							
							paramNew.setParameterValue(burstResults.
									getObject(param.getParameterBurstColumn()).toString());
							
							populatedParams.add(paramNew);
		
							logger.trace("added populated param"
									+ param.getPk().getParameterIdx()
									+ " - value - " + param.getParameterValue());
						} else {
							//value already assigned so carry on using that.
							logger.trace("using overide value" + param.getParameterValue());
							populatedParams.add(paramNew);
						}
					} else {
						//not populated via the bursting query
						logger.trace("standard parameter");
						populatedParams.add(paramNew);						
					}
					if (!name.isEmpty()) {
						name=name + "_";
					}
					name=name+paramNew.getParameterValue();
				}
				
				logger.trace("file/tab is called:" + name);
				
				
				// process the query with the results in
				if (!processed.contains(name)){
					//hack to prevent it repeating its self on the viewer				
						ResultSet rs = sqlProcessor.getResults(conn, job.getQuery(), populatedParams);
						if (rs.next()) {
							rs.beforeFirst();
							results.put(name, rs);
						}
					processed.add(name);
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

	/* (non-Javadoc)
	 * @see binky.reportrunner.engine.impl.RunnerResultGenerator#renderReport(java.sql.ResultSet, java.lang.String, byte[], binky.reportrunner.data.RunnerJob.Template, java.lang.String)
	 */
	public void renderReport(ResultSet results, String url,
			byte[] templateFile, Template templateType, String fileFormat)
			throws RenderException, IOException, SQLException {
		FileSystemHandler fs = new FileSystemHandlerImpl();
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
