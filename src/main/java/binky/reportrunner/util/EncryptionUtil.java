package binky.reportrunner.util;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class EncryptionUtil {
	
	public String generateKey() throws NoSuchAlgorithmException,
			InvalidKeySpecException {

		KeyGenerator keygen = KeyGenerator.getInstance("DESede");

		SecretKey key = keygen.generateKey();

		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");

		DESedeKeySpec keyspec = (DESedeKeySpec) keyfactory.getKeySpec(key,
				DESedeKeySpec.class);

		return bytesToHex(keyspec.getKey());
	}

	public String hashString(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();
		return bytesToHex(md.digest(input.getBytes()));		
	}
	
	private String bytesToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			int v = bytes[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();

	}

	private byte[] hexToBytes(String hex) {
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0; i < b.length; i++) {
			int index = i * 2;
			int v = Integer.parseInt(hex.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;

	}

	private SecretKey bytesToKey(byte[] bytes) throws InvalidKeyException,
			NoSuchAlgorithmException, InvalidKeySpecException {
		DESedeKeySpec keyspec = new DESedeKeySpec(bytes);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
		SecretKey key = keyfactory.generateSecret(keyspec);
		return key;
	}

	public String encrpyt(String key,String data)
			throws NoSuchAlgorithmException, InvalidKeyException,
			NoSuchPaddingException, InvalidKeySpecException,
			IllegalBlockSizeException, BadPaddingException {
		// Create and initialize the encryption engine
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.ENCRYPT_MODE, bytesToKey(hexToBytes(key)));

		return bytesToHex(cipher.doFinal(data.getBytes()));

	}

	public String decrpyt(String key, String data)
			throws NoSuchAlgorithmException, InvalidKeyException,
			NoSuchPaddingException, InvalidKeySpecException,
			IllegalBlockSizeException, BadPaddingException {
		// Create and initialize the encryption engine
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.DECRYPT_MODE, bytesToKey(hexToBytes(key)));

		return new String(cipher.doFinal(hexToBytes(data)));
	}
}
