package binky.reportrunner.util;


import java.util.Vector;

import junit.framework.TestCase;

public class ClassFinderTest extends TestCase {

	public void testFindSubclasses() {
		ClassFinder cf = ClassFinder.getInstance();
		Vector<Class<?>> classes = cf.findSubclasses("java.sql.Driver","Driver");
		assertTrue(classes.size()>0);
		for (Class<?> c : classes) {			
			System.out.println(c.getName() + " modifiers: " + c.getModifiers() + " is interface: " + c.isInterface() );
		}
	}

}
