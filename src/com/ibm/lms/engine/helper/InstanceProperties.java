package com.ibm.lms.engine.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ibm.lms.engine.exception.LmsException;
import com.ibm.lms.engine.handlers.UpdateLeadDataHandler;
import com.ibm.lms.engine.intefaces.BatchConstants;

/**
 * Stores instance properties
 * @verion 0.1
 */
public class InstanceProperties {
	private static Logger logger=Logger.getLogger(InstanceProperties.class);
    private static InstanceProperties instance = null;

    private String configFolder = null;
    private String dbProperties = null;
    private String handlersXml = null;
    private String logProperties = null;
    private String propertiesXml = null;
    private String queriesXml = null;
    private String dbPropertiesXML = null;

    

   
	private InstanceProperties() {
    }
    /**
    * singleton Factory method for obtaining the instance
    *
    * @return instance of the <code>BatchBuilder</code>
    * @concurrency $none
    */
    public static synchronized InstanceProperties getInstance() {
        if (null == instance) {
            instance = new InstanceProperties();
        }
        return instance;
    }
    
    /**
     * 
     * @return
     */
    public String getDbPropertiesXML() {
		return dbPropertiesXML;
	}
	
    
    /**
     * 
     * @param dbPropertiesXML
     */
    public void setDbPropertiesXML(String dbPropertiesXML) {
		this.dbPropertiesXML = dbPropertiesXML;
	}
	
	
    /**
     * getter
     * @return
     */
    public String getConfigFolder() {
        return configFolder;
    }

    /**
     * getter
     * @return
     */
    public String getDbProperties() {
        return dbProperties;
    }

    /**
     * getter
     * @return
     */
    public String getHandlersXml() {
        return handlersXml;
    }

    /**
     * getter
     * @return
     */
    public String getLogProperties() {
        return logProperties;
    }

    /**
     * getter
     * @return
     */
    public String getPropertiesXml() {
        return propertiesXml;
    }

    private String getProperty(Properties inputProperties, String key)
        throws LmsException {
        String value = inputProperties.getProperty(key);
        if (value == null) {
            throw new LmsException("Instance Property Not Found: " + key); //$NON-NLS-1$
        } else {
            value = value.trim();
        }
        return value;

    }

    /**
     * getter
     * @return
     */
    public String getQueriesXml() {
        return queriesXml;
    }


    /**
     * load properties
     *
     * @throws LmsException
     * @param rootFolder 
     */
    public void loadProperties(String rootFolder) throws LmsException {
        Properties inputProperties = new Properties();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(rootFolder + File.separatorChar + BatchConstants.INIT_BATCH_PROPERTIES); //$NON-NLS-1$
            inputProperties.load(fis);

            configFolder = rootFolder + BatchConstants.CONFIG_FOLDER;
            handlersXml = getProperty(inputProperties, "handlersXml"); //$NON-NLS-1$  //$NON-NLS-2$
            queriesXml = getProperty(inputProperties, "queriesXml"); //$NON-NLS-1$  //$NON-NLS-2$
            propertiesXml = getProperty(inputProperties, "propertiesXml"); //$NON-NLS-1$  //$NON-NLS-2$
            dbPropertiesXML= getProperty(inputProperties, "dbConnectionXml"); //$NON-NLS-1$  //$NON-NLS-2$
            
            logProperties = getProperty(inputProperties, "logProperties"); //$NON-NLS-1$  //$NON-NLS-2$

            logger.info("Instance properties loaded"); //$NON-NLS-1$
        } catch (FileNotFoundException e) {
            throw new LmsException(e.getMessage());
        } catch (IOException e2) {
            throw new LmsException(e2.getMessage());
        } finally {
            try {
                fis.close();
            } catch (IOException e1) {                
                throw new LmsException(e1.getMessage());
            }catch(Exception e){
                logger.info("Error Loading properties.");  //$NON-NLS-1$
                throw new LmsException(e.getMessage());
            }
        }

    }

    /**
     * setter
     * @param string
     */
    public void setConfigFolder(String string) {
        configFolder = string;
    }

    /**
     * setter
     * @param string
     */
    public void setDbProperties(String string) {
        dbProperties = string;
    }

    /**
     * setter
     * @param string
     */
    public void setHandlersXml(String string) {
        handlersXml = string;
    }

    /**
     * setter
     * @param string
     */
    public void setLogProperties(String string) {
        logProperties = string;
    }

    /**
     * setter
     * @param string
     */
    public void setPropertiesXml(String string) {
        propertiesXml = string;
    }

    /**
     * setter
     * @param string
     */
    public void setQueriesXml(String string) {
        queriesXml = string;
    }

//  Commented for DQA PTR p10032006124946

//    /**
//     * setter
//     * @param string
//     */
//    public void setReportFolder(String string) {
//        reportFolder = string;
//    }
//
//    /**
//     * setter
//     * @param string
//     */
//    public void setReportTemplateXml(String string) {
//        reportTemplateXml = string;
//    }

}
