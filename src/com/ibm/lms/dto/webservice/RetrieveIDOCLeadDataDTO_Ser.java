/**
 * RetrieveIDOCLeadDataDTO_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.dto.webservice;

public class RetrieveIDOCLeadDataDTO_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public RetrieveIDOCLeadDataDTO_Ser(
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType, 
           com.ibm.ws.webservices.engine.description.TypeDesc _typeDesc) {
        super(_javaType, _xmlType, _typeDesc);
    }
    public void serialize(
        javax.xml.namespace.QName name,
        org.xml.sax.Attributes attributes,
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        context.startElement(name, addAttributes(attributes, value, context));
        addElements(value, context);
        context.endElement();
    }
    protected org.xml.sax.Attributes addAttributes(
        org.xml.sax.Attributes attributes,
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
           javax.xml.namespace.QName
           elemQName = QName_4_1;
           context.qName2String(elemQName, true);
           elemQName = QName_4_153;
           context.qName2String(elemQName, true);
           elemQName = QName_4_154;
           context.qName2String(elemQName, true);
           elemQName = QName_4_32;
           context.qName2String(elemQName, true);
           elemQName = QName_4_71;
           context.qName2String(elemQName, true);
           elemQName = QName_4_155;
           context.qName2String(elemQName, true);
           elemQName = QName_4_156;
           context.qName2String(elemQName, true);
           elemQName = QName_4_157;
           context.qName2String(elemQName, true);
           elemQName = QName_4_158;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        RetrieveIDOCLeadDataDTO bean = (RetrieveIDOCLeadDataDTO) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_4_1;
          propValue = bean.getLeadId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_153;
          propValue = bean.getTransactionId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_154;
          propValue = bean.getProspectMobileNo();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_32;
          propValue = bean.getProduct();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_71;
          propValue = bean.getSource();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_155;
          propValue = bean.getIdocVerificationName();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_156;
          propValue = bean.getIdocStatus();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_157;
          propValue = bean.getStatusUpdateDt();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_158;
          propValue = bean.getActivateNumber();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
        }
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
    private final static javax.xml.namespace.QName QName_1_3 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
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
