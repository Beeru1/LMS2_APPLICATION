/**
 * GisInfoCaptureDO_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.dto.webservice;

public class GisInfoCaptureDO_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public GisInfoCaptureDO_Ser(
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
           elemQName = QName_4_119;
           context.qName2String(elemQName, true);
           elemQName = QName_4_120;
           context.qName2String(elemQName, true);
           elemQName = QName_4_107;
           context.qName2String(elemQName, true);
           elemQName = QName_4_79;
           context.qName2String(elemQName, true);
           elemQName = QName_4_122;
           context.qName2String(elemQName, true);
           elemQName = QName_4_123;
           context.qName2String(elemQName, true);
           elemQName = QName_4_124;
           context.qName2String(elemQName, true);
           elemQName = QName_4_125;
           context.qName2String(elemQName, true);
           elemQName = QName_4_126;
           context.qName2String(elemQName, true);
           elemQName = QName_4_129;
           context.qName2String(elemQName, true);
           elemQName = QName_4_128;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        GisInfoCaptureDO bean = (GisInfoCaptureDO) value;
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
          propQName = QName_4_119;
          propValue = bean.getStatus();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_120;
          propValue = bean.getSubStatus();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_107;
          propValue = bean.getRsuCode();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_79;
          propValue = bean.getRemarks();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_122;
          propValue = bean.getProductBought();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_123;
          propValue = bean.getRentalPlan();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_124;
          propValue = bean.getPaymentCollected();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_125;
          propValue = bean.getPaymentType();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_126;
          propValue = bean.getPaymentAmount();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_129;
          propValue = bean.getAddress1();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_4_128;
          propValue = bean.getSentBy();
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
    private final static javax.xml.namespace.QName QName_4_107 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "rsuCode");
    private final static javax.xml.namespace.QName QName_4_124 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "paymentCollected");
    private final static javax.xml.namespace.QName QName_4_122 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "productBought");
    private final static javax.xml.namespace.QName QName_4_128 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "sentBy");
    private final static javax.xml.namespace.QName QName_4_129 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "address1");
    private final static javax.xml.namespace.QName QName_4_126 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "paymentAmount");
    private final static javax.xml.namespace.QName QName_4_123 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "rentalPlan");
    private final static javax.xml.namespace.QName QName_4_125 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "paymentType");
    private final static javax.xml.namespace.QName QName_4_79 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "remarks");
    private final static javax.xml.namespace.QName QName_1_3 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
    private final static javax.xml.namespace.QName QName_4_119 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "status");
    private final static javax.xml.namespace.QName QName_4_1 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "leadId");
    private final static javax.xml.namespace.QName QName_4_120 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://webservice.dto.lms.ibm.com",
                  "subStatus");
}
