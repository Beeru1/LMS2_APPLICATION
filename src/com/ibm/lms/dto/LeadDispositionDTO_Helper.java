/**
 * LeadDispositionDTO_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.dto;

public class LeadDispositionDTO_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(LeadDispositionDTO.class);

    static {
        typeDesc.setOption("buildNum","gm1318.02");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("agentDispositionCode");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "agentDispositionCode"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("callRetryCount");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "callRetryCount"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("customerHoldTime");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "customerHoldTime"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("customerTalkTime");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "customerTalkTime"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("dialerAgencyCode");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "dialerAgencyCode"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("dispositionCode");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "dispositionCode"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("isDispositionDialer");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "isDispositionDialer"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("ivrTime");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "ivrTime"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("ringingTime");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "ringingTime"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("setupTime");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dto.lms.ibm.com", "setupTime"));
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
          new LeadDispositionDTO_Ser(
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
          new LeadDispositionDTO_Deser(
            javaType, xmlType, typeDesc);
    };

}
