package com.ibm.lms.engine.util;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.lms.engine.helper.InstanceProperties;
import com.ibm.lms.engine.intefaces.BatchConstants;

/**
 * Common batch utility methods
 * @verion 0.1
 */
public class BatchUtilities {
	
	private static Logger logger=Logger.getLogger(BatchUtilities.class);

    /**
     * Automatically generated constructor for utility class
     */
    private BatchUtilities() {
    }

   

    /**
     * returns int value of the given integer object
     * @param value
     * @return
     */
    public static int getIntValue(Integer value) {
        int intValue = 0;
        if (value != null) {
            intValue = value.intValue();
        }
        return intValue;
    }

    /**
     * returns empty string in case on NULL string
     * @param str
     * @return
     */
    public static String removeNull(String str) {
        if (str == null) {
            return "";  //$NON-NLS-1$
        } else {
            return str.trim();
        }
    }

    /**
     * trims the values in string
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (str == null) {
            return null;
        } else {
            return str.trim();
        }
    }

    /**
     * returns true if the string is null or empty string
     * @param string
     * @return
     */
    public static boolean isNullOrEmpty(String string) {
        if ((string == null) || "".equals(string.trim())) {  //$NON-NLS-1$
            return true;
        } else {
            return false;
        }

    }

    /**
     * ctreats message from the Map
     * @param message
     * @param values
     * @return
     */
    public static String getMessage(String message, Map values) {
        StringBuffer buffer = new StringBuffer(message);
        Iterator itr = values.keySet().iterator();
        boolean first = true;
        while (itr.hasNext()) {
            String key = (String) itr.next();
            String value = (String) values.get(key);
            if (first) {
                first = false;
            } else {
                buffer.append(BatchConstants.COMMA);
            }
            buffer.append(key);
            buffer.append(BatchConstants.EQUAL);
            buffer.append(value);
        }
        return buffer.toString();
    }

    /**
     * creates message from notification object 
     * @param message
     * @param notification
     * @return
     *//*
    public static String getMessage(
        String message,
        Object notification) {
        StringBuffer buffer = new StringBuffer(message);

        buffer.append(ColumnKeys.TX_TYPE_CD);
        buffer.append(BatchConstants.EQUAL);  
        buffer.append(notification.getTxTypeCd());
        buffer.append(BatchConstants.COMMA);  

        buffer.append(ColumnKeys.SRVREQ_ID);
        buffer.append(BatchConstants.EQUAL);  
        buffer.append(notification.getSrvreqId());
        buffer.append(BatchConstants.COMMA);  

        buffer.append(ColumnKeys.SRVREQTX_TM);
        buffer.append(BatchConstants.EQUAL);  
        buffer.append(notification.getSrvreqtxTm());
        buffer.append(BatchConstants.COMMA);  

        buffer.append(ColumnKeys.SRVREQIT_NUM);
        buffer.append(BatchConstants.EQUAL);  
        buffer.append(notification.getSrvreqitNum());
        buffer.append(BatchConstants.COMMA);  

        buffer.append(ColumnKeys.SUPP_INSTAPPL_ID);
        buffer.append(BatchConstants.EQUAL);  
        buffer.append(BatchUtilities.removeNull(notification.getSuppInstapplId()));
        buffer.append(BatchConstants.COMMA);  

        buffer.append(ColumnKeys.SUPP_ID);
        buffer.append(BatchConstants.EQUAL);  
        buffer.append(BatchUtilities.removeNull(notification.getSuppId()));
        buffer.append(BatchConstants.COMMA);  

        buffer.append(ColumnKeys.CAND_NUM);
        buffer.append(BatchConstants.EQUAL);  
        buffer.append(BatchUtilities.removeNull(notification.getCandNum()));
        buffer.append(BatchConstants.COMMA);  

        return buffer.toString();
    }
*/
    /**
     * concat values in the List useing ~
     * @param values
     * @return
     */
    public static String concatKeys(List values) {
        Iterator itr = values.iterator();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; itr.hasNext(); i++) {
            sb.append(itr.next());
            if (i < values.size()) {
                sb.append(BatchConstants.SEPERATOR);
            }
        }
        return sb.toString();
    }
    
    /**
     * getSQLDate from timestamp object
     * @param timestamp
     * @return date
     */
    public static Date getSQLDate(Timestamp timestamp){
        Date date = null;
        if(timestamp != null){
            date=new Date(timestamp.getTime());
        }
        return date;
    }
    
    /**
     * <p>
     * Utility method to return value of key from the property file
     * </p>
     * @param propertyFileName
     * @param key
     * @return
     */
    public static String getHanderProperty(String propertyFileName,String key){
    	
    	String value = null;
    	
    	Map keyValue = readPropertyFile(propertyFileName);
    	
    	return (String)keyValue.get(key);
    }
    
    
    /**
     * <p>
     * Utility method to return the complete Key value map of the property file
     * </p>
     * @param propertyFileName
     * @return
     */
    public static Map readPropertyFile(String propertyFileName){
    	
    	//StringBuffer retValue = new StringBuffer();
    	Map retKeyValueMap = new HashMap();
    	String[] strArr = null;
    	int readByte ;
    	try {
			
    		ResourceBundle resourceBundle = ResourceBundle.getBundle(propertyFileName);
    		Enumeration<String> enumerations = resourceBundle.getKeys();
    		String key = null;
    		String value = null;
    		while(enumerations.hasMoreElements()){
    			key = (String)enumerations.nextElement();
    			value = resourceBundle.getString(key);
    			retKeyValueMap.put(key,value);
    		}
		} catch(Exception ioexcep){
			ioexcep.printStackTrace();
			return null;
		}
		
    	return retKeyValueMap;
    }
    
    /**
     * <p>
     * Utility method to update the property fle with the new key value
     * </p>
     * @param propertyFileName
     * @param key
     * @param newValue
     */
    public static void updatePropertyFile(String propertyFileName, String key, String newValue){
    	
    	try{
    		
    		Map keyValueMap = readPropertyFile(propertyFileName);
    		keyValueMap.put(key,newValue);
    		
    		Properties properties = new Properties();
    		properties.putAll(keyValueMap);	
    		
    		FileOutputStream fos = new FileOutputStream(new File(propertyFileName + ".properties"));
    		
    		properties.store(fos,"#Comment");
    	}catch(Exception excp){
    		excp.printStackTrace();
    	}
    	
    	
    }
    
    public static void main(String args[]){
    	
    	BatchUtilities.readPropertyFile("csafxp");
    	logger.info("Read");
    	
    	
    	
    }
    

}
