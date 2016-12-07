package com.ibm.lms.engine.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ibm.lms.common.PropertyReader;

public class ServerPropertyReader {

	/**
	 * The bundle name
	 */
	
	private static String bundleName;

	private static final Logger logger = Logger.getLogger(ServerPropertyReader.class);

	private static Properties applicationPropertiesResources = new Properties();

	//private static Date lastAccessedDate = new Date();
	private static FileInputStream fis  = null;
	/**
	 * Referesh Interval in minutes
	 */
	//private static final int REFERESH_INTERVAL = 20;

	//private static boolean refreshFromBundle = true;

	private ServerPropertyReader() {
	}static {
		loadPropertyServer();
	}

	public static  String getString(String key) {
		
		String returnVal = applicationPropertiesResources.getProperty(key);
		if (returnVal == null) 
		{
			logger.info("No Value found for key:" + key + " Returning null");
		}
		return returnVal;

	}

	/**
	 * @return
	 */

	/**
	 * @param string
	 */
	public static void loadPropertyServer() {
		//bundleName = string.replace('.', '/') + ".properties";
		//logger.info(bundleName);
		try {
			applicationPropertiesResources.clear();
			fis = new FileInputStream(PropertyReader.getAppValue("property.file.path")+"/PropertiesResources.properties");
			applicationPropertiesResources.load(fis);
			logger.info("1111111");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	

	/**
	 * Returns the Count of the Keys that start with a particular String
	 * 
	 * @param strtWith
	 * @return
	 */
	public static int getKeyCnt(String strtWith) {

		int cnt = 0;
		//Date currentDate = new Date();

		Set keySet = applicationPropertiesResources.keySet();
		Iterator it = keySet.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();

			if (key.startsWith(strtWith)) {
				cnt++;
			}
		}

		return cnt;
	}
	
	


}
