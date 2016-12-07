/**
 * IDOCStatusServiceResponse_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.dto.webservice;

public class IDOCStatusServiceResponse_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public IDOCStatusServiceResponse_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new IDOCStatusServiceResponse();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_4_16) {
          ((IDOCStatusServiceResponse)value).setResponseCode(strValue);
          return true;}
        else if (qName==QName_4_17) {
          ((IDOCStatusServiceResponse)value).setResponseMsg(strValue);
          return true;}
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        return false;
    }
    private final static javax.xml.namespace.QName QName_4_17 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "responseMsg");
    private final static javax.xml.namespace.QName QName_4_16 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "responseCode");
}
