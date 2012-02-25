package binky.reportrunner.ui.actions.admin.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;

import binky.reportrunner.data.Configuration;
import binky.reportrunner.data.Configuration.ConfigurationType;
import binky.reportrunner.service.ConfigurationService;
import binky.reportrunner.ui.actions.base.StandardRunnerAction;

public class SaveConfig extends StandardRunnerAction {

	private static final long serialVersionUID = 1929058653431804656L;
	private List<Configuration> configurations;
	private List<File> files;
	
	private static final Logger logger = Logger.getLogger(SaveConfig.class);
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String execute() throws Exception {
		int x=0;
				
		super.clearErrorsAndMessages();
		if (configurations!=null) logger.debug("config size  "+ configurations.size());
		
		//hack to force image size.
		for (Configuration c: configurations) {
			if (files!=null&& files.size()>x) {
				if (c.getType()==ConfigurationType.LOGO) {
					try {
						int height=ImageIO.read(files.get(x)).getHeight();
						if (height > 50) {
							super.addActionError("Image must be no more than 50px high - this is " + height + "px");
							return INPUT;					
						}
					} catch (Throwable t) {
						super.addActionError(t.getMessage());
						return INPUT;
					}
				}
			}		
			x++;
		}
		
		//actually save the stuff if we have got this far without the image check bouncing us out
		x=0;
		for (Configuration c: configurations) {
			if (c==null) logger.warn("null config item?");
			logger.debug(c.getType());
			if (c.getType()==ConfigurationType.LOGO) {
				if (files!=null&& files.size()>x) {
					c.setBinaryValue(getBytesFromFile(files.get(x)));
					logger.debug("saving new logo");
					configurationService.saveOrUpdate(c);
				}
			} else {
				configurationService.saveOrUpdate(c);
			}
			x++;
		}
		
		super.addActionMessage("Configuration saved");
		return SUCCESS;
	}
	private ConfigurationService configurationService;
	public void setConfigurationService(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}
	public void setConfigurations(List<Configuration> configurations) {
		this.configurations = configurations;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}
	// Returns the contents of the file in a byte array.
	private byte[] getBytesFromFile(File file) throws IOException {

		// if the file is null then return a null byte array to show this
		if (file == null) {
			logger.warn("getBytesFromFile called with null file object");
			return null;
		}

		logger.debug("getBytesFromFile called for: " + file.getName());
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();
		logger.debug("file len: " + length);

		if (length > Integer.MAX_VALUE) {
			throw new IOException("file too large");
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			IOException e = new IOException("Could not completely read file "
					+ file.getName());
			logger.error("error reading file", e);
			throw e;
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}
	public List<Configuration> getConfigurations() {
		return configurations;
	}
	public List<File> getFiles() {
		return files;
	}
	public ConfigurationService getConfigurationService() {
		return configurationService;
	}
}
