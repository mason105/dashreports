package binky.dan.utils.encryption;

/**
 * @author daniel.grout
 *
 */
public abstract class HashUtilFactory {
	public enum HashEngine {MD5};
	
	/**
	 * @param engine
	 * @return instance of HashUtil - defaults to MD5HashUtil
	 */
	public static HashUtil getHashUtil(HashEngine engine) {
		switch (engine) {
		default:
			return new MD5HashUtil();
		}
	}
}
