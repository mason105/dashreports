package binky.reportrunner.util;

import java.security.NoSuchAlgorithmException;

import binky.dan.utils.encryption.EncryptionException;
import junit.framework.TestCase;

public class EncryptionUtilTest extends TestCase {

	private EncryptionUtil enc;
	
	@Override
	protected void setUp() throws Exception {

		enc=new EncryptionUtil();
			
	}

	public void testGenerateKey() {
		try {
			assertTrue(enc.generateKey().length()==48);
			
		} catch (EncryptionException e) {			
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testHashString() {
		try {
			String test="THIS is a test 12345";
			assertFalse(enc.hashString(test).equals(test));			
		} catch (NoSuchAlgorithmException e) {			
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testEncrpyt() {
		try {
			String test="THIS is a test 12345";
			String key=enc.generateKey();
			String t1=enc.encrpyt(key, test);
			String t2=enc.decrpyt(key, t1);
			assertTrue(!test.equals(t1)&&test.equals(t2));			
		} catch (EncryptionException e) {			
			e.printStackTrace();
			fail(e.getMessage());
		}
	}


}
