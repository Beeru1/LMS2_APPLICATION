/**
 * PushMstrWebserviceDTO_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.dto;

public class PushMstrWebserviceDTO_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(PushMstrWebserviceDTO.class);

    static {
        typeDesc.setOption("buildNum","gm1318.02");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("circleData");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "circleData"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://common.lms.ibm.com", "DataObject"));
        field.setMaxOccurs(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("cityData");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "cityData"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://common.lms.ibm.com", "DataObject"));
        field.setMaxOccurs(true);
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("pincodeList");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "pincodeList"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        field.setMaxOccurs(true);
        typeDesc.addFieldDesc(field);
    };

    /**
     * Return type metadata object
     */
    public static com.ibm.ws.webservices.engine.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static com.ibm.ws.webservices.engine.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class javaType,  
           javax.xml.namespace.QName xmlType) {
        return 
          new PushMstrWebserviceDTO_Ser(
            javaType, xmlType, typeDesc);
    };

    /**
     * Get Custom Deserializer
     */
    public static com.ibm.ws.webservices.engine.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class javaType,  
           javax.xml.namespace.QName xmlType) {
        return 
          new PushMstrWebserviceDTO_Deser(
            javaType, xmlType, typeDesc);
    };

}
