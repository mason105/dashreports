package binky.reportrunner.service.impl;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllServiceTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllServiceTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(DashboardServiceImplTest.class);
		suite.addTestSuite(DatasourceServiceImplTest.class);
		suite.addTestSuite(GroupServiceImplTest.class);
		suite.addTestSuite(ReportServiceImplTest.class);
		suite.addTestSuite(UserServiceImplTest.class);
		suite.addTestSuite(AuditServiceImplTest.class);
		//$JUnit-END$
		return suite;
	}

}
