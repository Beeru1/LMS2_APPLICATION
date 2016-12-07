/**
 * IDOCDataStatusDTO_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.dto.webservice;

public class IDOCDataStatusDTO_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public IDOCDataStatusDTO_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new IDOCDataStatusDTO();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_4_33) {
          ((IDOCDataStatusDTO)value).setProspectsMobileNumber(strValue);
          return true;}
        else if (qName==QName_4_19) {
          ((IDOCDataStatusDTO)value).setAlternateContactNumber(strValue);
          return true;}
        else if (qName==QName_4_44) {
          ((IDOCDataStatusDTO)value).setOnlineCafNo(strValue);
          return true;}
        else if (qName==QName_4_119) {
          ((IDOCDataStatusDTO)value).setStatus(strValue);
          return true;}
        else if (qName==QName_4_148) {
          ((IDOCDataStatusDTO)value).setStatus_date(strValue);
          return true;}
        else if (qName==QName_4_149) {
          ((IDOCDataStatusDTO)value).setRejectReason(strValue);
          return true;}
        else if (qName==QName_4_150) {
          ((IDOCDataStatusDTO)value).setIdocStep(strValue);
          return true;}
        else if (qName==QName_4_71) {
          ((IDOCDataStatusDTO)value).setSource(strValue);
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
    private final static javax.xml.namespace.QName QName_4_19 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "alternateContactNumber");
    private final static javax.xml.namespace.QName QName_4_71 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "source");
    private final static javax.xml.namespace.QName QName_4_150 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "idocStep");
    private final static javax.xml.namespace.QName QName_4_149 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "rejectReason");
    private final static javax.xml.namespace.QName QName_4_44 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "onlineCafNo");
    private final static javax.xml.namespace.QName QName_4_33 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "prospectsMobileNumber");
    private final static javax.xml.namespace.QName QName_4_119 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "status");
    private final static javax.xml.namespace.QName QName_4_148 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "status_date");
}
