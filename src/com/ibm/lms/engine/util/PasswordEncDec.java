package com.ibm.lms.engine.util;


import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.lms.engine.helper.InstanceProperties;



public class PasswordEncDec 
{
	
	private static Logger logger=Logger.getLogger(PasswordEncDec.class);
	
	public static void main(String[] args) throws Exception 
	{
		
//		String password ="d5fea#081";// new password
		String password ="y5i/NLzzi1fldC7B7ckdhg==";// new password
		//password=encPassword(password);
		//logger.info(" Encrypted password : "+password);
		password = decPassword(password);
		logger.info(" Decrypted password : "+password);
	}
	
	public static String encPassword(String password) 
	{
		String encPassword="";
		try
		{
			
			IEncryption crypt = new Encryption();
			encPassword = crypt.encrypt(password);
			//logger.info("Encrypted String - "+crypt.encrypt(password));
		}
		catch (EncryptionException e)
		{
			e.printStackTrace();
		}
		return encPassword;
	}	
	
	
	public static String decPassword(String password) 
	{
		String decPassword="";		
		try 
		{
		IEncryption crypt = new Encryption();
		decPassword= crypt.decrypt(password);
		}
		catch (EncryptionException e)
		{
			e.printStackTrace();
		}
		return decPassword;
	}

////   public static String getWebservicesPassword()throws VirtualizationServiceException 
////   {
////	ResourceBundle webserviceResourceBundle = null;
////	String value = "";	
////		try {
////			if (webserviceResourceBundle == null) {
////				webserviceResourceBundle = ResourceBundle
////						.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
////			}
////			value = webserviceResourceBundle.getString("webservices.user.password");
////			value=decPassword(value);	
////	} catch (Exception e) {		
////	e.printStackTrace();
////	}
////	return value;
	//}

}
	


