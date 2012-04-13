package binky.dan.utils.encryption;

public abstract class SymmeticEncryptionUtil extends EncryptionCommon {

	

	/**
	 * @return
	 * @throws EncryptionException
	 */
	public abstract String generateKey() throws EncryptionException;

	/**
	 * @return
	 * @throws EncryptionException
	 */
	public abstract String generateKey(String password, String salt) throws EncryptionException;

	
	/**
	 * @param key
	 * @param data
	 * @return
	 * @throws EncryptionException
	 */
	public abstract String encrpyt(String key, String data)
			throws EncryptionException;

	/**
	 * @param key
	 * @param data
	 * @return
	 * @throws EncryptionException
	 */
	public abstract String decrpyt(String key, String data)
			throws EncryptionException;

	public abstract String getEngine() ;
	
}
