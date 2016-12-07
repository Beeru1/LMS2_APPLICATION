/**
 * RetrieveIDOCLeadDataDTO_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.dto.webservice;

public class RetrieveIDOCLeadDataDTO_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(RetrieveIDOCLeadDataDTO.class);

    static {
        typeDesc.setOption("buildNum","gm1318.02");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("leadId");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "leadId"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("transactionId");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "transactionId"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("prospectMobileNo");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "prospectMobileNo"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("product");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "product"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("source");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "source"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("idocVerificationName");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "idocVerificationName"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("idocStatus");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "idocStatus"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("statusUpdateDt");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "statusUpdateDt"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("activateNumber");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://webservice.dto.lms.ibm.com", "activateNumber"));
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
          new RetrieveIDOCLeadDataDTO_Ser(
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
          new RetrieveIDOCLeadDataDTO_Deser(
            javaType, xmlType, typeDesc);
    };

}
