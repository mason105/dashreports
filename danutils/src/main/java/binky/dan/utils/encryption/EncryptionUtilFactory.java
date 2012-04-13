package binky.dan.utils.encryption;

public abstract class EncryptionUtilFactory {

	public enum SymmetricEncryptionEngine {
		DESede(192,"DESede"), Blowfish_128(128,"Blowfish"),Blowfish_256(256,"Blowfish"),Blowfish_448(448,"Blowfish"), AES_128(128,"AES"),AES_256(256,"AES"), RC2_128(128,"RC2"),RC2_256(256,"RC2"),RC2_512(512,"RC2"),RC2_1024(1024,"RC2");
		
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
	 * @return instance of EncrpytionUtil - Defaults to 3DES
	 */
	public static SymmeticEncryptionUtil getSymmetricEncryptionUtil(
			SymmetricEncryptionEngine engine) {
		return new StandardSymmetricUtil(engine.getEngine(),engine.keySize);
	}

}
