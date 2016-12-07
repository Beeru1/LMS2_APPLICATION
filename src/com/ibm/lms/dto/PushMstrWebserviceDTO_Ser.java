/**
 * PushMstrWebserviceDTO_Ser.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * gm1318.02 v5913144247
 */

package com.ibm.lms.dto;

public class PushMstrWebserviceDTO_Ser extends com.ibm.ws.webservices.engine.encoding.ser.BeanSerializer {
    /**
     * Constructor
     */
    public PushMstrWebserviceDTO_Ser(
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
           elemQName = QName_0_60;
           context.qName2String(elemQName, true);
           elemQName = QName_0_61;
           context.qName2String(elemQName, true);
           elemQName = QName_0_62;
           context.qName2String(elemQName, true);
        return attributes;
    }
    protected void addElements(
        java.lang.Object value,
        com.ibm.ws.webservices.engine.encoding.SerializationContext context)
        throws java.io.IOException
    {
        PushMstrWebserviceDTO bean = (PushMstrWebserviceDTO) value;
        java.lang.Object propValue;
        javax.xml.namespace.QName propQName;
        {
          propQName = QName_0_60;
          {
            propValue = bean.getCircleData();
            if (propValue != null) {
              for (int i=0; i<java.lang.reflect.Array.getLength(propValue); i++) {
                serializeChild(propQName, null, 
                    java.lang.reflect.Array.get(propValue, i), 
                    QName_3_63,
                    true,null,context);
              }
            }
          }
          propQName = QName_0_61;
          {
            propValue = bean.getCityData();
            if (propValue != null) {
              for (int i=0; i<java.lang.reflect.Array.getLength(propValue); i++) {
                serializeChild(propQName, null, 
                    java.lang.reflect.Array.get(propValue, i), 
                    QName_3_63,
                    true,null,context);
              }
            }
          }
          propQName = QName_0_62;
          {
            propValue = bean.getPincodeList();
            if (propValue != null) {
              for (int i=0; i<java.lang.reflect.Array.getLength(propValue); i++) {
                if (java.lang.reflect.Array.get(propValue, i) != null && !context.shouldSendXSIType()) {
                  context.simpleElement(propQName, null, java.lang.reflect.Array.get(propValue, i).toString()); 
                } else {
                  serializeChild(propQName, null, 
                    java.lang.reflect.Array.get(propValue, i), 
                    QName_1_3,
                    true,null,context);
                }
              }
            }
          }
        }
    }
    private final static javax.xml.namespace.QName QName_0_62 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "pincodeList");
    private final static javax.xml.namespace.QName QName_0_60 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "circleData");
    private final static javax.xml.namespace.QName QName_1_3 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://www.w3.org/2001/XMLSchema",
                  "string");
    private final static javax.xml.namespace.QName QName_0_61 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://dto.lms.ibm.com",
                  "cityData");
    private final static javax.xml.namespace.QName QName_3_63 = 
           com.ibm.ws.webservices.engine.utils.QNameTable.createQName(
                  "http://common.lms.ibm.com",
                  "DataObject");
}
