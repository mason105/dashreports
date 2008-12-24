package binky.reportrunner.util;

import java.util.Vector;

import junit.framework.TestCase;

public class JDBCDriverFinderTest extends TestCase {

	public void testGetDriverList() {
		JDBCDriverFinder find = JDBCDriverFinder.getInstance();

		Vector<Class<?>> drivers = find.getDriverList();
		
		assertNotNull(drivers);
	}

}
