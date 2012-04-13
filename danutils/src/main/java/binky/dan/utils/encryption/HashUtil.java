package binky.dan.utils.encryption;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author daniel.grout
 *
 */
public abstract class HashUtil extends EncryptionCommon{
	
	/**
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public abstract String hashString(String input)
	throws NoSuchAlgorithmException, UnsupportedEncodingException;
	}
