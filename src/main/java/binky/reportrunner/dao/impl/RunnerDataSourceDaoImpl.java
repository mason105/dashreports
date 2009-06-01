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
 * Module: RunnerDataSourceDaoImpl.java
 ******************************************************************************/
package binky.reportrunner.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerDataSourceDao;
import binky.reportrunner.data.RunnerDataSource;

public class RunnerDataSourceDaoImpl extends HibernateDaoSupport implements
		RunnerDataSourceDao {
	private Logger logger = Logger.getLogger(RunnerDataSourceDaoImpl.class);
	public void saveUpdateDataSource(RunnerDataSource dataSource) {
		logger.debug("save or update for: " + dataSource.getDataSourceName());
		getHibernateTemplate().saveOrUpdate(dataSource);
	}

	public void deleteDataSource(String dataSourceName) {
		logger.debug("delete: " + dataSourceName); 
		getHibernateTemplate().delete((RunnerDataSource)
				getHibernateTemplate().get(RunnerDataSource.class,
						dataSourceName));
	}

	public RunnerDataSource getDataSource(String dataSourceName) {
		logger.debug("get datasource: "+dataSourceName);
		return (RunnerDataSource) getHibernateTemplate().get(
				RunnerDataSource.class, dataSourceName);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerDataSource> listDataSources() {
		logger.debug("list data sources");
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerDataSource.class);
		return getHibernateTemplate().findByCriteria(criteria);
	}

}
