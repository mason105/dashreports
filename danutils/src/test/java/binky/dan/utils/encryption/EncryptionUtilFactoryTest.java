package binky.dan.utils.encryption;

import java.util.Calendar;

import binky.dan.utils.encryption.EncryptionUtilFactory.SymmetricEncryptionEngine;

public class EncryptionUtilFactoryTest extends EncTestCase {

	
	public void testHexTranslation() {
		String testData = getTestString();
		SymmeticEncryptionUtil u= EncryptionUtilFactory.getSymmetricEncryptionUtil(SymmetricEncryptionEngine.Blowfish_448);
		String hex = u.bytesToHex(testData.getBytes());
		String back = new String(u.hexToBytes(hex));
		assertTrue(back.equals(testData));
	}
	
	public void testGetSymmetricEncryptionUtil() {
		String testString = getTestString();
		for (SymmetricEncryptionEngine e : SymmetricEncryptionEngine.values()) {		

			System.out.println("engine: "+e.name());
			doTest(EncryptionUtilFactory.getSymmetricEncryptionUtil(e),testString);			
		}
	}
	

	
	
	private void doTest(SymmeticEncryptionUtil eu, String testString) {
		try {
			
			String key=eu.generateKey();
			System.out.println("random generated key  : " + key );
			key=eu.generateKey("Password123","SALTYBALLS");
			System.out.println("password generated key: " + key );
			long encStart = Calendar.getInstance().getTimeInMillis();
			String enc = eu.encrpyt(key, testString);
			long encEnd = Calendar.getInstance().getTimeInMillis();
//			System.out.println("enc: " + enc);
			long decStart = Calendar.getInstance().getTimeInMillis();
			String dec = eu.decrpyt(key, enc);
			long decEnd = Calendar.getInstance().getTimeInMillis();
			//System.out.println(testString);
			//System.out.println(dec);
			System.out.println("Time to encrypt: " + (encEnd-encStart) + "ms Time to Decrypt: + "+(decEnd-decStart) + "ms");
			assertTrue(dec.equals(testString));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}		
	}

}
