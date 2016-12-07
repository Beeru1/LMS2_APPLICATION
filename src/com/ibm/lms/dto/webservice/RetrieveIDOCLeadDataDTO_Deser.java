/**
 * RetrieveIDOCLeadDataDTO_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.dto.webservice;

public class RetrieveIDOCLeadDataDTO_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public RetrieveIDOCLeadDataDTO_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new RetrieveIDOCLeadDataDTO();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_4_1) {
          ((RetrieveIDOCLeadDataDTO)value).setLeadId(strValue);
          return true;}
        else if (qName==QName_4_153) {
          ((RetrieveIDOCLeadDataDTO)value).setTransactionId(strValue);
          return true;}
        else if (qName==QName_4_154) {
          ((RetrieveIDOCLeadDataDTO)value).setProspectMobileNo(strValue);
          return true;}
        else if (qName==QName_4_32) {
          ((RetrieveIDOCLeadDataDTO)value).setProduct(strValue);
          return true;}
        else if (qName==QName_4_71) {
          ((RetrieveIDOCLeadDataDTO)value).setSource(strValue);
          return true;}
        else if (qName==QName_4_155) {
          ((RetrieveIDOCLeadDataDTO)value).setIdocVerificationName(strValue);
          return true;}
        else if (qName==QName_4_156) {
          ((RetrieveIDOCLeadDataDTO)value).setIdocStatus(strValue);
          return true;}
        else if (qName==QName_4_157) {
          ((RetrieveIDOCLeadDataDTO)value).setStatusUpdateDt(strValue);
          return true;}
        else if (qName==QName_4_158) {
          ((RetrieveIDOCLeadDataDTO)value).setActivateNumber(strValue);
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
    private final static javax.xml.namespace.QName QName_4_156 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "idocStatus");
    private final static javax.xml.namespace.QName QName_4_153 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "transactionId");
    private final static javax.xml.namespace.QName QName_4_157 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "statusUpdateDt");
    private final static javax.xml.namespace.QName QName_4_71 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "source");
    private final static javax.xml.namespace.QName QName_4_32 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "product");
    private final static javax.xml.namespace.QName QName_4_154 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "prospectMobileNo");
    private final static javax.xml.namespace.QName QName_4_155 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "idocVerificationName");
    private final static javax.xml.namespace.QName QName_4_158 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "activateNumber");
    private final static javax.xml.namespace.QName QName_4_1 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "leadId");
}