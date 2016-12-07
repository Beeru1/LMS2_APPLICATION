package com.ibm.lms.engine.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.ibm.lms.engine.dataobjects.BatchJobHandlerDO;
import com.ibm.lms.engine.exception.LmsCommonException;
import com.ibm.lms.engine.util.XMLUtils;


/**
 * <p> This class acts as the cache for the Batch, it loads the XML file once
 * and stores that as a <code>Document</code>.</p>
 * <p> This class is implemented as Singleton</p>
 * 
 * @verion 0.1
 */
public class BatchBuilder {
    private static BatchBuilder handle = null;
    private static Document handlerDocument = null;
    private static Map queriesMap = null;
    private Map cacheProperties = null;
    
    private static Logger lmsLogger=Logger.getLogger(BatchBuilder.class);
    

    /**
     * Private constructor
     */
    private BatchBuilder() {

        try {
            init();
        } catch (LmsCommonException e) {
            lmsLogger.error(
                "Error in init of batch builder:" + e.getMessage(),  //$NON-NLS-1$
                e);
            e.printStackTrace();
        }
    }

    /**
     * singleton Factory method for obtaining the instance
     *
     * @return instance of the <code>BatchBuilder</code>
     * @concurrency $none
     */
    public static synchronized BatchBuilder getInstance() {
        if (null == handle) {
            handle = new BatchBuilder();
        }
        return handle;
    }

    /**
     * add property to the cache.
     * @param name name of the property
     * @param value value of the property
     */
    private void addProperty(String name, String value) {
        ((Properties) cacheProperties).setProperty(name, value);
    }

    /**
    * get value of the property.
    *
    * @param name name of the property
    * @return String
    */
    public String getProperty(String name) {
        return ((Properties) cacheProperties).getProperty(name, ""); //$NON-NLS-1$
    }

    /**
     * Initialize the singleton
     */
    private void init() throws LmsCommonException {
        XMLUtils utils = XMLUtils.getInstance();
        cacheProperties = new Properties();
        
        InstanceProperties instance = InstanceProperties.getInstance();
        handlerDocument =
            utils.build(
                instance.getConfigFolder() + instance.getHandlersXml(),
                true);

        Document queryDocument =
            utils.build(
                instance.getConfigFolder() + instance.getQueriesXml(),
                true);

        Document propertyDocument =
            utils.build(
                instance.getConfigFolder() + instance.getPropertiesXml(),
                true);

        parseQueriesXML(queryDocument);
        parsePropertiesXML(propertyDocument);
    }

    /**
     * parse properties XML file and store in cache   
     * @param doc Document object of Properties XML File.
     */
    private void parsePropertiesXML(Document doc) {
        Element root = doc.getRootElement();
        List prop = root.getChildren("PROPERTY"); //$NON-NLS-1$
        ListIterator iter = prop.listIterator();
        while (iter.hasNext()) {
            Element property = (Element) iter.next();
            String name = property.getChildText("NAME"); //$NON-NLS-1$
            String value = property.getChildText("VALUE"); //$NON-NLS-1$
            addProperty(name, value);
        }
    }

    /**
     * getHandlers is  used to get the handlers configures in the XML File
     * @return List contaning <code>EmailBatchJobHandlerDO</code> Object for 
     *         each of the handler. 
     */
    public List getHandlers() {
        List<BatchJobHandlerDO> handlers = new ArrayList<BatchJobHandlerDO>();
        Element root = handlerDocument.getRootElement();
        Element batchHandlers = root.getChild("BATCH_HANDLERS"); //$NON-NLS-1$
        List<Element> handlersList = batchHandlers.getChildren("BATCH_HANDLER"); //$NON-NLS-1$
        ListIterator<Element> handlersIter = handlersList.listIterator();

        while (handlersIter.hasNext()) {
            Element handler = (Element) handlersIter.next();
            BatchJobHandlerDO handlerDO = new BatchJobHandlerDO();
            String name = handler.getChild("BATCH_HANDLER_NAME").getTextTrim(); //$NON-NLS-1$
            String className = handler.getChild("BATCH_HANDLER_CLASS").getTextTrim(); //$NON-NLS-1$
            String id = handler.getChild("BATCH_HANDLER_ID").getTextTrim(); //$NON-NLS-1$

            handlerDO.setID(id);
            handlerDO.setName(name);
            handlerDO.setClassName(className);

            handlers.add(handlerDO);
        }
        return handlers;
    }

    /**
     * Parses query XML file. Anc creates a cache of all the queries.
     * @param queryDocument Object of queryXMLDocument
     */
    private void parseQueriesXML(Document queryDocument) {
        queriesMap = new HashMap();
        Element root = queryDocument.getRootElement();

        List queriesList = root.getChildren("QUERY"); //$NON-NLS-1$
        ListIterator queriesIter = queriesList.listIterator();

        while (queriesIter.hasNext()) {
            Element query = (Element) queriesIter.next();
            String id = query.getAttributeValue("ID"); //$NON-NLS-1$
            queriesMap.put(id, query.getTextTrim());
        }
    }

    /**
     * Returns sql query for the query id 
     *
     * @param queryKey key of the query , denoted by ID in XML file
     * @return Query statement
     */
    public String getSQLQuery(String queryKey) {
        return (String) queriesMap.get(queryKey);
    }
}
