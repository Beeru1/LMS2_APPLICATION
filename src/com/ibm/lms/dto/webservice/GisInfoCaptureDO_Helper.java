/**
 * GisInfoCaptureDO_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.dto.webservice;

public class GisInfoCaptureDO_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(GisInfoCaptureDO.class);

    static {
        typeDesc.setOption("buildNum","gm1318.02");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("leadId");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "leadId"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("status");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "status"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("subStatus");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "subStatus"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("rsuCode");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "rsuCode"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("remarks");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "remarks"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("productBought");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "productBought"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("rentalPlan");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "rentalPlan"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("paymentCollected");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "paymentCollected"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("paymentType");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "paymentType"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("paymentAmount");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "paymentAmount"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("address1");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "address1"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("sentBy");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "sentBy"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
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
          new GisInfoCaptureDO_Ser(
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
          new GisInfoCaptureDO_Deser(
            javaType, xmlType, typeDesc);
    };

}
