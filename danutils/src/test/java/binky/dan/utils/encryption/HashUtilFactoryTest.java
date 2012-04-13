package binky.dan.utils.encryption;

import java.util.Calendar;

import binky.dan.utils.encryption.HashUtilFactory.HashEngine;


public class HashUtilFactoryTest extends EncTestCase {

	public void testGetHashUtil() {
		String testString= super.getTestString();
		for (HashEngine e: HashEngine.values()) {
			HashUtil hu =HashUtilFactory.getHashUtil(e);
			doTest(hu,testString);
		}
	}

	private void doTest(HashUtil hu, String testString) {		
		try {
		long encStart = Calendar.getInstance().getTimeInMillis();	
		String hashed=hu.hashString(testString);
		assertNotNull(hashed);
		assertTrue(!testString.equals(hashed));
		long encEnd = Calendar.getInstance().getTimeInMillis();
		System.out.println("Time to encrypt: " + (encEnd-encStart)+ "ms");
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}		
	}
	
}
