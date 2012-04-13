package binky.dan.utils.encryption;

public abstract class EncryptionUtilFactory {

	
	//NOTE: if you get the exception - "Illegal key size or default parameters" - you need to download the encryption policy files from Oracle - http://www.oracle.com/technetwork/java/javase/downloads/index.html
	
	public enum SymmetricEncryptionEngine {
		DESede(168,"DESede"), Blowfish_128(128,"Blowfish"),Blowfish_256(256,"Blowfish"),Blowfish_448(448,"Blowfish"), AES_128(128,"AES"),AES_256(256,"AES"), RC2_128(128,"RC2"),RC2_256(256,"RC2"),RC2_512(512,"RC2"),RC2_1024(1024,"RC2");
		
		private int keySize;
		private String engine;
		SymmetricEncryptionEngine(int keySize,String engine) {
			this.keySize=keySize;
			this.engine=engine;
		}
		public int getKeySize() {
			return keySize;
		}
		public String getEngine() {
			return engine;
		}
	}

	/**
	 * @param engine
	 * @return instance of EncrpytionUtil
	 */
	public static SymmeticEncryptionUtil getSymmetricEncryptionUtil(
			SymmetricEncryptionEngine engine) {
		return new StandardSymmetricUtil(engine.getEngine(),engine.keySize);
	}

}
