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
 * Module: DeleteDataSource.java
 ******************************************************************************/
package binky.reportrunner.ui.actions.datasource;

import org.springframework.dao.DataIntegrityViolationException;

import binky.reportrunner.service.DatasourceService;
import binky.reportrunner.ui.actions.base.AdminRunnerAction;

public class DeleteDataSource extends AdminRunnerAction {

	private static final long serialVersionUID = 1L;

	private String dataSourceName;

	@Override
	public String execute() throws Exception {
		try {
			dataSourceService.deleteDataSource(dataSourceName);
		} catch (DataIntegrityViolationException e) {
			super.addActionError("Unable to delete - " + dataSourceName + " is being used by one or more jobs!");
			return ERROR;
		} 
		return SUCCESS;
	}

	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	private DatasourceService dataSourceService;



	public DatasourceService getDataSourceService() {
		return dataSourceService;
	}

	public void setDataSourceService(DatasourceService dataSourceService) {
		this.dataSourceService = dataSourceService;
	}

}
