package binky.reportrunner.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * This makes a big assumption that all JDBC driver classnames have Driver in
 * them and implement java.sql.Driver
 * 
 * @author Daniel Grout
 **/
public class JDBCDriverFinder implements Runnable {
	private static final Logger logger = Logger
			.getLogger(JDBCDriverFinder.class);

	private volatile Vector<Class<?>> driverList;
	private static JDBCDriverFinder instance;

	public static JDBCDriverFinder getInstance() {
		if (instance == null) {
			instance = new JDBCDriverFinder();

		}
		return instance;
	}

	protected JDBCDriverFinder() {
		driverList = new Vector<Class<?>>();
		loadCachedDriverList();
		Thread thread = new Thread(this);
		thread.start();

	}

	ClassFinder cf;

	public void run() {
		cf = ClassFinder.getInstance();
		Vector<Class<?>> classes = cf.findSubclasses("java.sql.Driver",
				"Driver");
		driverList = new Vector<Class<?>>();
		for (Class<?> c : classes) {
			if (!driverList.contains(c) && !c.isInterface()
					&& (c.getModifiers() == 1)) {
				logger.debug("adding jdbc driver: " + c.getName()
						+ " to driver list");
				driverList.add(c);
			}
		}
		saveCachedDriverList();
	}

	private void saveCachedDriverList() {
		logger.debug("saving JDBC driver cache list");
		File file = new File("jdbcDriverList.cache");
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Class<?> c : driverList) {
				bw.write(c.getName() + "\n");
			}
			bw.close();
			fw.close();
		} catch (FileNotFoundException e) {
			logger.error("error reading driver name cache "
					+ "- this will impact performance!", e);
		} catch (IOException e) {
			logger.error("error reading driver name cache "
					+ "- this will impact performance!", e);
		}

	}

	private void loadCachedDriverList() {
		logger.info("loading cached JDBC driver list");
		File file = new File("jdbcDriverList.cache");
		if (file.exists()) {
			logger.debug("driver cache found: " + file.getAbsolutePath());
			try {
				List<String> cNames = new LinkedList<String>();

				try {
					FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);

					String name;
					while ((name = br.readLine()) != null) {
						cNames.add(name.trim());
					}
					br.close();
					fr.close();

				} catch (FileNotFoundException e) {
					logger.error("error reading driver name cache "
							+ "- this will impact performance!", e);
				} catch (IOException e) {
					logger.error("error reading driver name cache "
							+ "- this will impact performance!", e);
				}
				for (String cName : cNames) {
					Class<?> c = Class.forName(cName);
					driverList.add(c);
				}
			} catch (ClassNotFoundException cnfex) {
				logger.error("Error loading driver:" + cnfex);
			} catch (NoClassDefFoundError ncdfe) {
				logger.error("Error loading driver:" + ncdfe);
			} catch (UnsatisfiedLinkError ule) {
				logger.error("Error loading driver:" + ule);
			} catch (Exception exception) {
				logger.error("Error loading driver:" + exception);
			}
		} else {
			logger.trace("no driver cache available");
		}
	}

	public Vector<Class<?>> getDriverList() {
		return driverList;
	}

}
