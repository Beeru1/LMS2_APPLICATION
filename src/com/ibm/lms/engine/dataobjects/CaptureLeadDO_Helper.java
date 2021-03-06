/**
 * CaptureLeadDO_Helper.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.engine.dataobjects;

public class CaptureLeadDO_Helper {
    // Type metadata
    private static final com.ibm.ws.webservices.engine.description.TypeDesc typeDesc =
        new com.ibm.ws.webservices.engine.description.TypeDesc(CaptureLeadDO.class);

    static {
        typeDesc.setOption("buildNum","gm1318.02");
        com.ibm.ws.webservices.engine.description.FieldDesc field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("address");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "address"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("alternateContactNumber");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "alternateContactNumber"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("campaign");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "campaign"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("circle");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "circle"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("city");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "city"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("company");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "company"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("email");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "email"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("extraParametrs");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "extraParametrs"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("fid");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "fid"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("fromPage");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "fromPage"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("isCustomer");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "isCustomer"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("keyWord");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "keyWord"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("pinecode");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "pinecode"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("plan");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "plan"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("product");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "product"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("prospectsMobileNumber");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "prospectsMobileNumber"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("prospectsName");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "prospectsName"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("referPage");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "referPage"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("referUrl");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "referUrl"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("service");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "service"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("tid");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "tid"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("utmLabels");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "utmLabels"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("zoneCode");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "zoneCode"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("cityZoneCode");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "cityZoneCode"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("rental");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "rental"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("tag");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "tag"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("onlineCafNo");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "onlineCafNo"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("tranRefno");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "tranRefno"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("pytAmt");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "pytAmt"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("qualLeadParam");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "qualLeadParam"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("adParameter");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "adParameter"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("leadSubmitTime");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "leadSubmitTime"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("extraParams2");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "extraParams2"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("extraParams3");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "extraParams3"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("allocatedNo");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "allocatedNo"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("myopId");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "myopId"));
        field.setXmlType(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(field);
        field = new com.ibm.ws.webservices.engine.description.ElementDesc();
        field.setFieldName("geoIpCity");
        field.setXmlName(com.ibm.ws.webservices.engine.utils.QNameTable.createQName("http://dataobjects.engine.lms.ibm.com", "geoIpCity"));
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
          new CaptureLeadDO_Ser(
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
          new CaptureLeadDO_Deser(
            javaType, xmlType, typeDesc);
    };

}
