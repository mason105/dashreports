package binky.reportrunner.service.impl;

import java.util.Collection;
import java.util.LinkedList;

import binky.reportrunner.dao.ReportRunnerDao;
import binky.reportrunner.data.Configuration;
import binky.reportrunner.data.Configuration.ConfigurationType;
import binky.reportrunner.service.ConfigurationService;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;

public class ConfigurationServiceImpl implements ConfigurationService {

	private ReportRunnerDao<Configuration, ConfigurationType> configurationDao;

	@Override
	@Cacheable(cacheName = "configCache")
	public Collection<Configuration> getConfigurationItems() {
		Collection<Configuration> configs = new LinkedList<Configuration>();
		for (ConfigurationType t : ConfigurationType.values()) {
			configs.add(this.getConfigurationItem(t));
		}
		return configs;
	}

	@Override
	@Cacheable(cacheName = "configCache")
	public Configuration getConfigurationItem(ConfigurationType type) {
		Configuration c = configurationDao.get(type);
		if (c == null)
			c = new Configuration(type);
		return c;
	}

	@Override
	@Cacheable(cacheName = "configCache")
	public void saveOrUpdate(Configuration configuration) {
		configurationDao.saveOrUpdate(configuration);
	}

	@Override
	@TriggersRemove(cacheName = "configCache")
	public void delete(ConfigurationType type) {
		configurationDao.delete(type);
	}

	public void setConfigurationDao(
			ReportRunnerDao<Configuration, ConfigurationType> configurationDao) {
		this.configurationDao = configurationDao;
	}

}
