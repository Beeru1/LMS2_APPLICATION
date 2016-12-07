/**
 * LeadDispositionDTO_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.dto;

public class LeadDispositionDTO_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public LeadDispositionDTO_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new LeadDispositionDTO();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_0_4) {
          ((LeadDispositionDTO)value).setAgentDispositionCode(strValue);
          return true;}
        else if (qName==QName_0_5) {
          ((LeadDispositionDTO)value).setCallRetryCount(strValue);
          return true;}
        else if (qName==QName_0_6) {
          ((LeadDispositionDTO)value).setCustomerHoldTime(strValue);
          return true;}
        else if (qName==QName_0_7) {
          ((LeadDispositionDTO)value).setCustomerTalkTime(strValue);
          return true;}
        else if (qName==QName_0_8) {
          ((LeadDispositionDTO)value).setDialerAgencyCode(strValue);
          return true;}
        else if (qName==QName_0_9) {
          ((LeadDispositionDTO)value).setDispositionCode(strValue);
          return true;}
        else if (qName==QName_0_10) {
          ((LeadDispositionDTO)value).setIsDispositionDialer(strValue);
          return true;}
        else if (qName==QName_0_11) {
          ((LeadDispositionDTO)value).setIvrTime(strValue);
          return true;}
        else if (qName==QName_0_12) {
          ((LeadDispositionDTO)value).setRingingTime(strValue);
          return true;}
        else if (qName==QName_0_13) {
          ((LeadDispositionDTO)value).setSetupTime(strValue);
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
    private final static javax.xml.namespace.QName QName_0_6 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "customerHoldTime");
    private final static javax.xml.namespace.QName QName_0_13 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "setupTime");
    private final static javax.xml.namespace.QName QName_0_5 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "callRetryCount");
    private final static javax.xml.namespace.QName QName_0_8 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "dialerAgencyCode");
    private final static javax.xml.namespace.QName QName_0_7 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "customerTalkTime");
    private final static javax.xml.namespace.QName QName_0_4 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "agentDispositionCode");
    private final static javax.xml.namespace.QName QName_0_11 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "ivrTime");
    private final static javax.xml.namespace.QName QName_0_10 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "isDispositionDialer");
    private final static javax.xml.namespace.QName QName_0_12 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "ringingTime");
    private final static javax.xml.namespace.QName QName_0_9 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "dispositionCode");
}
