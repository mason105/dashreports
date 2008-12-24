package binky.reportrunner.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

/**
 * This utility class was based originally on <a
 * href="private.php?do=newpm&u=47838">Daniel Le Berre</a>'s <code>RTSI</code>
 * class. This class can be called in different modes, but the principal use is
 * to determine what subclasses/implementations of a given class/interface exist
 * in the current runtime environment.
 * 
 * @author Daniel Le Berre, Elliott Wade - amended by Daniel Grout - turned into
 *         singleton to prevent repeated caching when not needed - also added a
 *         "contains filter" to speed up searching for what I know i am looking
 *         for - also tidyed up
 */
public class ClassFinder {
	private static ClassFinder instance;
	private static final Logger logger = Logger.getLogger(ClassFinder.class);

	public static final ClassFinder getInstance() {
		if (instance == null) {
			logger.debug("new instance created");
			instance = new ClassFinder();
			instance.refreshLocations();
		} else {
			logger.debug("handing back old instance");
		}
		return instance;
	}

	private String classNameContains;
	private Class<?> searchClass = null;
	private Map<URL, String> classpathLocations = new HashMap<URL, String>();
	private Map<Class<?>, URL> results = new HashMap<Class<?>, URL>();
	private List<Throwable> errors = new ArrayList<Throwable>();
	private boolean working = false;

	protected ClassFinder() {

	}

	/**
	 * Rescan the classpath, cacheing all possible file locations.
	 */
	protected final void refreshLocations() {
		logger.debug("refreshing all locations");
		long start = Calendar.getInstance().getTimeInMillis();
		synchronized (classpathLocations) {
			classpathLocations = getClasspathLocations();
		}
		long end = Calendar.getInstance().getTimeInMillis();
		float time = ((end - start) / 1000);
		logger.trace("classpath parse took (s): " + time);
	}

	/**
	 * @param fqcn
	 *            Name of superclass/interface on which to search
	 */
	public final Vector<Class<?>> findSubclasses(String fqcn,
			String classNameContains) {
		this.classNameContains = classNameContains;
		long start = Calendar.getInstance().getTimeInMillis();
		logger.debug("looking for subclasses of: " + fqcn
				+ " and specifically class names containing: "
				+ classNameContains);
		synchronized (classpathLocations) {
			synchronized (results) {
				try {
					working = true;
					searchClass = null;
					errors = new ArrayList<Throwable>();
					results = new TreeMap<Class<?>, URL>(CLASS_COMPARATOR);

					//
					// filter malformed FQCN
					//
					if (fqcn.startsWith(".") || fqcn.endsWith(".")) {
						return new Vector<Class<?>>();
					}

					//
					// Determine search class from fqcn
					//
					try {
						searchClass = Class.forName(fqcn);
					} catch (ClassNotFoundException ex) {
						// if class not found, let empty vector return...
						errors.add(ex);
						return new Vector<Class<?>>();
					}

					return findSubclasses(searchClass, classpathLocations);
				} finally {
					working = false;
					long end = Calendar.getInstance().getTimeInMillis();
					float time = ((end - start) / 1000);
					logger.debug("search took (s): " + time);
				}
			}
		}
	}

	public final List<Throwable> getErrors() {
		return new ArrayList<Throwable>(errors);
	}

	/**
	 * The result of the last search is cached in this object, along with the
	 * URL that corresponds to each class returned. This method may be called to
	 * query the cache for the location at which the given class was found.
	 * <code>null</code> will be returned if the given class was not found
	 * during the last search, or if the result cache has been cleared.
	 */
	public final URL getLocationOf(Class<?> cls) {
		if (results != null)
			return results.get(cls);
		else
			return null;
	}

	/**
	 * Determine every URL location defined by the current classpath, and it's
	 * associated package name.
	 */
	public final Map<URL, String> getClasspathLocations() {
		logger.debug("getting class path locations");
		Map<URL, String> map = new TreeMap<URL, String>(URL_COMPARATOR);
		File file = null;

		String pathSep = System.getProperty("path.separator");
		String classpath = System.getProperty("java.class.path");
		logger.trace("classpath=" + classpath);

		StringTokenizer st = new StringTokenizer(classpath, pathSep);
		while (st.hasMoreTokens()) {
			String path = st.nextToken();
			logger.trace("found: " + path);
			file = new File(path);
			include(null, file, map);
		}
		return map;
	}

	private final static FileFilter DIRECTORIES_ONLY = new FileFilter() {
		public boolean accept(File f) {
			if (f.exists() && f.isDirectory())
				return true;
			else
				return false;
		}
	};

	private final static Comparator<URL> URL_COMPARATOR = new Comparator<URL>() {
		public int compare(URL u1, URL u2) {
			return String.valueOf(u1).compareTo(String.valueOf(u2));
		}
	};

	private final static Comparator<Class<?>> CLASS_COMPARATOR = new Comparator<Class<?>>() {
		public int compare(Class<?> c1, Class<?> c2) {
			return String.valueOf(c1).compareTo(String.valueOf(c2));
		}
	};

	private final void include(String name, File file, Map<URL, String> map) {
		if (!file.exists())
			return;
		if (!file.isDirectory()) {
			// could be a JAR file
			includeJar(file, map);
			return;
		}

		if (name == null)
			name = "";
		else
			name += ".";

		// add subpackages
		File[] dirs = file.listFiles(DIRECTORIES_ONLY);
		for (int i = 0; i < dirs.length; i++) {
			try {
				// add the present package
				map.put(new URL("file://" + dirs[i].getCanonicalPath()), name
						+ dirs[i].getName());
			} catch (IOException ioe) {
				return;
			}

			include(name + dirs[i].getName(), dirs[i], map);
		}
	}

	private void includeJar(File file, Map<URL, String> map) {
		if (file.isDirectory())
			return;

		URL jarURL = null;
		JarFile jar = null;
		try {
			jarURL = new URL("file:/" + file.getCanonicalPath());
			jarURL = new URL("jar:" + jarURL.toExternalForm() + "!/");
			JarURLConnection conn = (JarURLConnection) jarURL.openConnection();
			jar = conn.getJarFile();
		} catch (Exception e) {
			// not a JAR or disk I/O error
			// either way, just skip
			return;
		}

		if (jar == null || jarURL == null)
			return;

		// include the jar's "default" package (i.e. jar's root)
		map.put(jarURL, "");

		Enumeration<JarEntry> e = jar.entries();
		while (e.hasMoreElements()) {
			JarEntry entry = e.nextElement();

			if (entry.isDirectory()) {
				if (entry.getName().toUpperCase().equals("META-INF/"))
					continue;

				try {
					map.put(new URL(jarURL.toExternalForm() + entry.getName()),
							packageNameFor(entry));
				} catch (MalformedURLException murl) {
					// whacky entry?
					continue;
				}
			}
		}
	}

	private static String packageNameFor(JarEntry entry) {
		if (entry == null)
			return "";
		String s = entry.getName();
		if (s == null)
			return "";
		if (s.length() == 0)
			return s;
		if (s.startsWith("/"))
			s = s.substring(1, s.length());
		if (s.endsWith("/"))
			s = s.substring(0, s.length() - 1);
		return s.replace('/', '.');
	}

	private final Vector<Class<?>> findSubclasses(Class<?> superClass,
			Map<URL, String> locations) {
		Vector<Class<?>> v = new Vector<Class<?>>();

		Vector<Class<?>> w = null;

		for (URL url : locations.keySet()) {
			w = findSubclasses(url, locations.get(url), superClass);
			if (w != null && (w.size() > 0))
				for (Class<?> c : w) {
					if (!v.contains(c)) {
						v.add(c);
					} else {
						logger.trace("excluding: " + c.getName());
					}
				}
			// v.addAll(w);
		}

		return v;
	}

	@SuppressWarnings("unchecked")
	private final Vector<Class<?>> findSubclasses(URL location,
			String packageName, Class<?> superClass) {
		logger.trace("looking in package:" + packageName);
		logger.trace("looking for  class:" + superClass);

		synchronized (results) {

			// hash guarantees unique names...
			Map<Class<?>, URL> thisResult = new TreeMap<Class<?>, URL>(
					CLASS_COMPARATOR);
			Vector<Class<?>> v = new Vector<Class<?>>(); // ...but return a
			// vector

			// TODO: double-check for null search class
			String fqcn = searchClass.getName();

			List<URL> knownLocations = new ArrayList<URL>();
			knownLocations.add(location);
			// TODO: add getResourceLocations() to this list

			// iterate matching package locations...
			for (int loc = 0; loc < knownLocations.size(); loc++) {
				URL url = knownLocations.get(loc);

				// Get a File object for the package
				File directory = new File(url.getFile());

				logger.trace("looking in " + directory);

				if (directory.exists()) {
					// Get the list of the files contained in the package
					String[] files = directory.list();
					for (int i = 0; i < files.length; i++) {
						// we are only interested in .class files
						if (files[i].endsWith(".class")) {
							// removes the .class extension
							String classname = files[i].substring(0, files[i]
									.length() - 6);
							if ((!classname.equalsIgnoreCase(this.getClass()
									.getSimpleName()))
									&& !(!classname.equalsIgnoreCase(this
											.getClass().getSimpleName()
											+ "Test"))
									&& ((classNameContains == null)
											|| (classNameContains.isEmpty()) || (classname
											.contains(classNameContains)))) {
								try {
									Class<?> c = Class.forName(packageName
											+ "." + classname);
									logger.trace("checking: " + classname);
									if (superClass.isAssignableFrom(c)
											&& !fqcn.equals(packageName + "."
													+ classname)) {
										thisResult.put(c, url);
										logger.trace("found: " + classname);
									}
								} catch (ClassNotFoundException cnfex) {
									errors.add(cnfex);
									// System.err.println(cnfex);
								} catch (NoClassDefFoundError ncdfe) {
									errors.add(ncdfe);
								} catch (Exception ex) {
									errors.add(ex);
									// System.err.println (ex);
								}
							} else {
								logger.trace("skipping class: " + classname);
							}
						}
					}
				} else {
					try {
						// It does not work with the filesystem: we must
						// be in the case of a package contained in a jar file.
						JarURLConnection conn = (JarURLConnection) url
								.openConnection();
						// String starts = conn.getEntryName();
						JarFile jarFile = conn.getJarFile();

						// logger.trace ("starts=" + starts);
						logger.trace("JarFile=" + jarFile);

						Enumeration<JarEntry> e = jarFile.entries();
						while (e.hasMoreElements()) {
							JarEntry entry = e.nextElement();
							String entryname = entry.getName();

							logger.trace("considering entry: " + entryname);

							if (!entry.isDirectory()
									&& entryname.endsWith(".class")) {
								String classname = entryname.substring(0,
										entryname.length() - 6);
								if (classname.startsWith("/"))
									classname = classname.substring(1);
								classname = classname.replace('/', '.');

								logger.trace("testing classname: " + classname);
								if (((classNameContains == null)
										|| (classNameContains.isEmpty()) || (classname
										.contains(classNameContains)))) {
									try {
										// TODO: verify this block
										Class c = Class.forName(classname);
										logger.trace("checking: " + classname);
										if (superClass.isAssignableFrom(c)
												&& !fqcn.equals(classname)) {
											if (!thisResult.containsKey(c)) {
												thisResult.put(c, url);
												logger.trace("found: "
														+ c.getName() + " in "
														+ url);
											}
										}
									} catch (ClassNotFoundException cnfex) {
										// that's strange since we're scanning
										// the same classpath the classloader's
										// using... oh, well
										errors.add(cnfex);
									} catch (NoClassDefFoundError ncdfe) {
										// dependency problem... class is
										// unusable anyway, so just ignore it
										errors.add(ncdfe);
									} catch (UnsatisfiedLinkError ule) {
										// another dependency problem... class
										// is
										// unusable anyway, so just ignore it
										errors.add(ule);
									} catch (Exception exception) {
										// unexpected problem
										// System.err.println (ex);
										errors.add(exception);
									} catch (Error error) {
										// lots of things could go wrong
										// that we'll just ignore since
										// they're so rare...
										errors.add(error);
									}
								} else {
									logger.trace("skipping: " + classname);
								}
							}
						}
					} catch (IOException ioex) {
						errors.add(ioex);
					}
				}
			} // while

			results.putAll(thisResult);

			Iterator<Class<?>> it = thisResult.keySet().iterator();
			while (it.hasNext()) {
				v.add(it.next());
			}
			return v;

		} // synch results
	}

	public boolean isWorking() {
		return working;
	}

}
