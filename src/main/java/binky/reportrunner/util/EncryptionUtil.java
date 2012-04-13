package binky.reportrunner.util;

import java.security.NoSuchAlgorithmException;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import binky.dan.utils.encryption.EncryptionException;
import binky.dan.utils.encryption.EncryptionUtilFactory;
import binky.dan.utils.encryption.EncryptionUtilFactory.SymmetricEncryptionEngine;

public class EncryptionUtil {
	
	public String generateKey() throws EncryptionException {
		
		return EncryptionUtilFactory.getSymmetricEncryptionUtil(SymmetricEncryptionEngine.DESede).generateKey();		
	}

	public String hashString(String input) throws NoSuchAlgorithmException {
		PasswordEncoder encoder = new Md5PasswordEncoder();

		return encoder.encodePassword(input, null);
	}
	
	
	public String encrpyt(String key,String data) throws EncryptionException
			{
		
			return  EncryptionUtilFactory.getSymmetricEncryptionUtil(SymmetricEncryptionEngine.DESede).encrpyt(key, data);

	}
	public String decrpyt(String key,String data) throws EncryptionException
	{

	return  EncryptionUtilFactory.getSymmetricEncryptionUtil(SymmetricEncryptionEngine.DESede).decrpyt(key, data);

}

}
