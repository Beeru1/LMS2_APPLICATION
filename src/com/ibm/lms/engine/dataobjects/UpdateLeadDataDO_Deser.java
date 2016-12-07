/**
 * UpdateLeadDataDO_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.engine.dataobjects;

public class UpdateLeadDataDO_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public UpdateLeadDataDO_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new UpdateLeadDataDO();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        if (qName==QName_2_1) {
          ((UpdateLeadDataDO)value).setLeadId(strValue);
          return true;}
        else if (qName==QName_2_119) {
          ((UpdateLeadDataDO)value).setStatus(strValue);
          return true;}
        else if (qName==QName_2_120) {
          ((UpdateLeadDataDO)value).setSubStatus(strValue);
          return true;}
        else if (qName==QName_2_121) {
          ((UpdateLeadDataDO)value).setCafNumber(strValue);
          return true;}
        else if (qName==QName_2_79) {
          ((UpdateLeadDataDO)value).setRemarks(strValue);
          return true;}
        else if (qName==QName_2_122) {
          ((UpdateLeadDataDO)value).setProductBought(strValue);
          return true;}
        else if (qName==QName_2_123) {
          ((UpdateLeadDataDO)value).setRentalPlan(strValue);
          return true;}
        else if (qName==QName_2_124) {
          ((UpdateLeadDataDO)value).setPaymentCollected(strValue);
          return true;}
        else if (qName==QName_2_125) {
          ((UpdateLeadDataDO)value).setPaymentType(strValue);
          return true;}
        else if (qName==QName_2_126) {
          ((UpdateLeadDataDO)value).setPaymentAmount(strValue);
          return true;}
        else if (qName==QName_2_127) {
          ((UpdateLeadDataDO)value).setCompetitorChosen(strValue);
          return true;}
        else if (qName==QName_2_128) {
          ((UpdateLeadDataDO)value).setSentBy(strValue);
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
    private final static javax.xml.namespace.QName QName_2_1 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dataobjects.engine.lms.ibm.com",
                  "leadId");
    private final static javax.xml.namespace.QName QName_2_120 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dataobjects.engine.lms.ibm.com",
                  "subStatus");
    private final static javax.xml.namespace.QName QName_2_121 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dataobjects.engine.lms.ibm.com",
                  "cafNumber");
    private final static javax.xml.namespace.QName QName_2_127 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dataobjects.engine.lms.ibm.com",
                  "competitorChosen");
    private final static javax.xml.namespace.QName QName_2_124 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dataobjects.engine.lms.ibm.com",
                  "paymentCollected");
    private final static javax.xml.namespace.QName QName_2_122 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dataobjects.engine.lms.ibm.com",
                  "productBought");
    private final static javax.xml.namespace.QName QName_2_128 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dataobjects.engine.lms.ibm.com",
                  "sentBy");
    private final static javax.xml.namespace.QName QName_2_126 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dataobjects.engine.lms.ibm.com",
                  "paymentAmount");
    private final static javax.xml.namespace.QName QName_2_123 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dataobjects.engine.lms.ibm.com",
                  "rentalPlan");
    private final static javax.xml.namespace.QName QName_2_125 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dataobjects.engine.lms.ibm.com",
                  "paymentType");
    private final static javax.xml.namespace.QName QName_2_79 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dataobjects.engine.lms.ibm.com",
                  "remarks");
    private final static javax.xml.namespace.QName QName_2_119 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dataobjects.engine.lms.ibm.com",
                  "status");
}
