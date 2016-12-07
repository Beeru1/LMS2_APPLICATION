package com.ibm.lms.engine.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.ibm.lms.engine.exception.LmsCommonException;

/**
 * Title:        XMLUtils
 * Description:  <p>Utility class for Building the XML Document
 *               This class should not be used for any other pupose 
 *               except parsinf XML</p>     
 * 
 * @version      0.1   
 */
public class XMLUtils {
    private static XMLUtils handle = null;

    /**
    * XMLUtils constructor.
    * 
    */
    private XMLUtils() {
        super();
    }

    /**
     * return instance of XML utils
     *
     * @return
     * @concurrency $none
     */
    public static synchronized XMLUtils getInstance() {
        if (null == handle) {
            handle = new XMLUtils();
        }
        return handle;
    }

    /**
     * This method is used to save the XML Document to physical file
     *
     * @param doc Docuemnt to be stored
     * @param fileName Physical File
     * @throws LmsCommonException 
     */
    public static void saveToXMLFile(Document doc, String fileName)
        throws LmsCommonException {
        XMLOutputter outputter = new XMLOutputter();
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            outputter.output(doc, out);            
        } catch (IOException e) {
            e.printStackTrace();
            throw new LmsCommonException(e.toString());
        }
    }

    /**
     * <p> Builds the given input XML File to generate the Document output</p>
     *
     * @param  inputStream inputstream of the XML file
     * @param validate
     * @return Document prepared from the input file
     * @throws LmsCommonException 
     */
    public Document build(InputStream inputStream, boolean validate)
        throws LmsCommonException {
        Document document = null;

        try {
            SAXBuilder builder = new SAXBuilder(validate);
            document = builder.build(inputStream);
        } catch (JDOMException je) {
            throw new LmsCommonException("Error parsing XML document" + je.getMessage()); //$NON-NLS-1$

        } catch (Exception je) {
            throw new LmsCommonException("Error parsing XML document" + je.getMessage()); //$NON-NLS-1$

        }

        return document;
    }

    /**
     * <p> Builds the given input XML File to generate the Document output</p>
     *
     * @param  fileName Filename to be parsed
     * @return Document prepared from the input file
     * @throws LmsCommonException 
     */
    public Document build(String fileName) throws LmsCommonException {
        return build(fileName, false);
    }

    /**
     * <p> Builds the given input XML File to generate the Document output</p>
     *
     * @param  fileName input XML File
     * @param validate
     * @return Document prepared from the input file
     * @throws LmsCommonException 
     */
    public Document build(String fileName, boolean validate)
        throws LmsCommonException {
        Document document = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(fileName));
            document = build(fileInputStream, validate);
        } catch (FileNotFoundException fnfe) {
            throw new LmsCommonException("Error creating xml document:" + fnfe.getMessage()); //$NON-NLS-1$
        } finally {
            if (null != fileInputStream) {
                try {
                    fileInputStream.close();
                } catch (IOException ioexc) {
                    ioexc.printStackTrace();
                    throw new LmsCommonException("Error creating xml document:" + ioexc.getMessage()); //$NON-NLS-1$

                }
            }
        }
        return document;
    }
    
        
}
