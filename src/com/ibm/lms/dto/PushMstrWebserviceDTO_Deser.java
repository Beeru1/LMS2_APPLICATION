/**
 * PushMstrWebserviceDTO_Deser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.dto;

public class PushMstrWebserviceDTO_Deser extends com.ibm.ws.webservices.engine.encoding.ser.BeanDeserializer {
    /**
     * Constructor
     */
    public PushMstrWebserviceDTO_Deser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    /**
     * Create instance of java bean
     */
    public void createValue() {
        value = new PushMstrWebserviceDTO();
    }
    protected boolean tryElementSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryAttributeSetFromString(javax.xml.namespace.QName qName, java.lang.String strValue) {
        return false;
    }
    protected boolean tryElementSetFromObject(javax.xml.namespace.QName qName, java.lang.Object objValue) {
        return false;
    }
    protected boolean tryElementSetFromList(javax.xml.namespace.QName qName, java.util.List listValue) {
        if (qName==QName_0_60) {
          com.ibm.lms.common.DataObject[] array = new com.ibm.lms.common.DataObject[listValue.size()];
          listValue.toArray(array);
          ((PushMstrWebserviceDTO)value).setCircleData(array);
          return true;}
        else if (qName==QName_0_61) {
          com.ibm.lms.common.DataObject[] array = new com.ibm.lms.common.DataObject[listValue.size()];
          listValue.toArray(array);
          ((PushMstrWebserviceDTO)value).setCityData(array);
          return true;}
        else if (qName==QName_0_62) {
          java.lang.String[] array = new java.lang.String[listValue.size()];
          listValue.toArray(array);
          ((PushMstrWebserviceDTO)value).setPincodeList(array);
          return true;}
        return false;
    }
    private final static javax.xml.namespace.QName QName_0_62 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "pincodeList");
    private final static javax.xml.namespace.QName QName_0_60 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "circleData");
    private final static javax.xml.namespace.QName QName_0_61 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "cityData");
}