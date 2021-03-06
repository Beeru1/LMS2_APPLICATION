/**
 * PushMstrWebserviceResponse_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.dto;

public class PushMstrWebserviceResponse_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(PushMstrWebserviceResponse.class);

    static {
        typeDesc.setOption("buildNum","gm1318.02");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("productName");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "productName"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("webserviceResponse");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "webserviceResponse"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "PushMstrWebserviceDTO"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("responseMessage");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "responseMessage"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("responseCode");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "responseCode"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "int"));
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
          new PushMstrWebserviceResponse_Ser(
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
          new PushMstrWebserviceResponse_Deser(
            javaType, xmlType, typeDesc);
    };

}
