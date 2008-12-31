package binky.reportrunner.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import binky.reportrunner.dao.RunnerJobParameterDao;
import binky.reportrunner.data.RunnerJobParameter;

public class RunnerJobParameterDaoImpl extends HibernateDaoSupport implements RunnerJobParameterDao {
	private static final Logger logger = Logger.getLogger(RunnerJobParameterDaoImpl.class);
	@SuppressWarnings("unchecked")
	public void updateParametersForJob(String jobName, String groupName,
			List<RunnerJobParameter> parameters) {		
		logger.debug("updating parameters for job/group:" + jobName + "/" + groupName);
		//delete any parameters first
		
		logger.debug("deleting existing parameters");
		DetachedCriteria criteria = DetachedCriteria.forClass(RunnerJobParameter.class);
		criteria.add(Expression.eq("pk.runnerJob.pk.jobName", jobName)).add(Expression.eq("pk.runnerJob.pk.group.groupName", groupName));
		List<RunnerJobParameter> params= getHibernateTemplate().findByCriteria(criteria);
		if (params.size()>0) getHibernateTemplate().deleteAll(params);
		getHibernateTemplate().flush();
		for (RunnerJobParameter p:parameters) {
			logger.debug("saving parameter idx:" + p.getPk().getParameterIdx());
			getHibernateTemplate().saveOrUpdate(p);
		}
		
	}

}
