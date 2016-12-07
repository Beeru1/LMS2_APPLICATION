/**
 * PushMstrWebserviceResponse_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.dto;

public class PushMstrWebserviceResponse_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public PushMstrWebserviceResponse_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new PushMstrWebserviceResponse();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_55) {
          ((PushMstrWebserviceResponse)value).setProductName(strValue);
          return true;}
        else if (qName==QName_0_57) {
          ((PushMstrWebserviceResponse)value).setResponseMessage(strValue);
          return true;}
        else if (qName==QName_0_16) {
          ((PushMstrWebserviceResponse)value).setResponseCode(com.ibm.ws.webservices.engine.encoding.ser.SimpleDeserializer.parseint(strValue));
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        if (qName==QName_0_56) {
          ((PushMstrWebserviceResponse)value).setWebserviceResponse((com.ibm.lms.dto.PushMstrWebserviceDTO)objValue);
          return true;}
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_0_57 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "responseMessage");
    private final static javax.xml.namespace.QName QName_0_55 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "productName");
    private final static javax.xml.namespace.QName QName_0_16 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "responseCode");
    private final static javax.xml.namespace.QName QName_0_56 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "webserviceResponse");
}
