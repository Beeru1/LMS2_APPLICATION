/**
 * UpdateLeadDataDO_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.engine.dataobjects;

public class UpdateLeadDataDO_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public UpdateLeadDataDO_Ser(
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
           elemQName = QName_2_1;
           context.qName2String(elemQName, true);
           elemQName = QName_2_119;
           context.qName2String(elemQName, true);
           elemQName = QName_2_120;
           context.qName2String(elemQName, true);
           elemQName = QName_2_121;
           context.qName2String(elemQName, true);
           elemQName = QName_2_79;
           context.qName2String(elemQName, true);
           elemQName = QName_2_122;
           context.qName2String(elemQName, true);
           elemQName = QName_2_123;
           context.qName2String(elemQName, true);
           elemQName = QName_2_124;
           context.qName2String(elemQName, true);
           elemQName = QName_2_125;
           context.qName2String(elemQName, true);
           elemQName = QName_2_126;
           context.qName2String(elemQName, true);
           elemQName = QName_2_127;
           context.qName2String(elemQName, true);
           elemQName = QName_2_128;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        UpdateLeadDataDO bean = (UpdateLeadDataDO) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_2_1;
          propValue = bean.getLeadId();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_2_119;
          propValue = bean.getStatus();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_2_120;
          propValue = bean.getSubStatus();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_2_121;
          propValue = bean.getCafNumber();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_2_79;
          propValue = bean.getRemarks();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_2_122;
          propValue = bean.getProductBought();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_2_123;
          propValue = bean.getRentalPlan();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_2_124;
          propValue = bean.getPaymentCollected();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_2_125;
          propValue = bean.getPaymentType();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_2_126;
          propValue = bean.getPaymentAmount();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_2_127;
          propValue = bean.getCompetitorChosen();
          if (propValue != null && !context.shouldSendXSIType()) {
            context.simpleElement(propQName, null, propValue.toString()); 
          } else {
            serializeChild(propQName, null, 
              propValue, 
              QName_1_3,
              true,null,context);
          }
          propQName = QName_2_128;
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
    private final static javax.xml.namespace.QName QName_1_3 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
    private final static javax.xml.namespace.QName QName_2_79 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dataobjects.engine.lms.ibm.com",
                  "remarks");
    private final static javax.xml.namespace.QName QName_2_119 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dataobjects.engine.lms.ibm.com",
                  "status");
}
