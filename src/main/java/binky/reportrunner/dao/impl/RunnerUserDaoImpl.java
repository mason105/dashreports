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
 * Module: RunnerUserDaoImpl.java
 ******************************************************************************/
package binky.reportrunner.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerUserDao;
import binky.reportrunner.data.RunnerUser;

public class RunnerUserDaoImpl extends HibernateDaoSupport implements
		RunnerUserDao {
	private Logger logger = Logger.getLogger(RunnerUserDaoImpl.class);
	public void deleteUser(String userName) {
		logger.debug("delete user for: " + userName);
		getHibernateTemplate().delete(
				(RunnerUser) getHibernateTemplate().get(RunnerUser.class,
						userName));
	}

	public RunnerUser getUser(String userName) {
		logger.debug("get user for: " + userName);
		return (RunnerUser) getHibernateTemplate().get(RunnerUser.class,
				userName);
	}

	@SuppressWarnings("unchecked")
	public List<RunnerUser> listUsers() {
		logger.debug("list users");
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerUser.class);
		criteria=criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY) ;
		return getHibernateTemplate().findByCriteria(criteria);
	}

	public void saveUpdateUser(RunnerUser user) {
		logger.debug("save update user for: " + user.getUserName());
		getHibernateTemplate().saveOrUpdate(user);
	}

}
