package com.ibm.lms.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ResourceReader {
	protected static Logger logger = Logger.getLogger(ResourceReader.class);
// For production Path
	private static String path="/lms_common/LMS2/Property/";
	//private static String path="C:\\lms_common\\LMS2\\Property\\";
	private static String bundleName;
	private static FileInputStream fis  = null;
	private static Properties applicationResources = new Properties();
	
   
	// To refresh the bundle 
	public static boolean refreshFromBundle = false;
	

	
	static
	{
		try
		{
		logger.info("************static block here******************");
		applicationResources.clear();
		setBundleName(path+"PropertyResources");
		fis = new FileInputStream(bundleName);
		applicationResources.load(fis);
		}
		catch(Exception ex)
		{
		}
	}
	
	
	public static String getResourceValue(String propertyKey) {
		if (propertyKey == null || propertyKey.trim().length() <= 0) {
			return null;
		};
		//Date currentDate = new Date();
		logger.info("sjkdhksahdkjsahdsa============="+refreshFromBundle);
		try {
			if (refreshFromBundle) {
				applicationResources.clear();
				setBundleName(path+"PropertyResources");
				fis = new FileInputStream(bundleName);
				applicationResources.load(fis);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return applicationResources.getProperty(propertyKey);
	}
	
	
	public static void setBundleName(String string) {
		bundleName = string.replace('.', '/') + ".properties";
		try {
			logger.info("boundle name***********"+bundleName);
			fis = new FileInputStream(bundleName);
			applicationResources.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Error in configuring properties file name : "+ e.getMessage());
		}
	}
}