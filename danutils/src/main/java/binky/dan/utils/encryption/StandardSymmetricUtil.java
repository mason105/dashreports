package binky.dan.utils.encryption;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class StandardSymmetricUtil extends SymmeticEncryptionUtil {
	private String engine;
	private int keySize;
	StandardSymmetricUtil(String engine,int keySize) {
		this.engine = engine;
		this.keySize=keySize;
	}

	@Override
	public String generateKey() throws EncryptionException {
		try {
			KeyGenerator keyGen=KeyGenerator.getInstance(engine);
			keyGen.init(keySize);
			SecretKey key = keyGen.generateKey();
			return bytesToHex(key.getEncoded());
		} catch (NoSuchAlgorithmException nsae) {
			throw new EncryptionException(nsae);
		} catch (InvalidParameterException ipe){
			throw new EncryptionException(ipe);
		}
	}

	@Override
	public String generateKey(String password, String salt) throws EncryptionException {
		try{
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 1024, keySize);
			SecretKey secret = factory.generateSecret(spec);
			return bytesToHex(secret.getEncoded());
		} catch (NoSuchAlgorithmException nsae) {
			throw new EncryptionException(nsae);
		} catch (InvalidKeySpecException ikse){
			throw new EncryptionException(ikse);
		}
	}
	
	@Override
	public String encrpyt(String key, String data) throws EncryptionException {
		return doFunction(Cipher.ENCRYPT_MODE, key, data);
	}

	@Override
	public String decrpyt(String key, String data) throws EncryptionException {
		return doFunction(Cipher.DECRYPT_MODE, key, data);
	}

	private SecretKey bytesToKey(byte[] bytes, String engine)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException {
		SecretKey key = new SecretKeySpec(bytes, 0, bytes.length, engine);
		return key;
	}

	private String doFunction(int mode, String key, String data)
			throws EncryptionException {
		try {
			Cipher cipher = Cipher.getInstance(engine+"/ECB/PKCS5Padding");
			cipher.init(mode, bytesToKey(hexToBytes(key), engine));
			if (mode==Cipher.DECRYPT_MODE) {
				return new String(cipher.doFinal(hexToBytes(data)));
			}else {
				return bytesToHex(cipher.doFinal(data.getBytes()));
			}
		} catch (NoSuchAlgorithmException nsae) {
			throw new EncryptionException(nsae);
		} catch (InvalidKeySpecException ikse) {
			throw new EncryptionException(ikse);
		} catch (InvalidKeyException ike) {
			throw new EncryptionException(ike);
		} catch (BadPaddingException bpe) {
			throw new EncryptionException(bpe);
		} catch (IllegalBlockSizeException ibse) {
			throw new EncryptionException(ibse);
		} catch (NoSuchPaddingException nspe) {
			throw new EncryptionException(nspe);
		}
	}

	@Override
	public String getEngine() {
		return engine;
	}

}
