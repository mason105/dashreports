package binky.reportrunner.service;

import java.util.Collection;

import binky.reportrunner.data.Configuration;
import binky.reportrunner.data.Configuration.ConfigurationType;

public interface ConfigurationService extends Auditable {
	public Collection<Configuration> getConfigurationItems();
	public Configuration getConfigurationItem(ConfigurationType type);	
	public void saveOrUpdate(Configuration configuration);
	public void delete(ConfigurationType type);
	
}
