//
// Generated By:JAX-WS RI IBM 2.2.1-11/28/2011 08:28 AM(foreman)- (JAXB RI IBM 2.2.3-11/28/2011 06:21 AM(foreman)-)
//


package com.ibm.lms.webservice.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "createLeadDataResponse", namespace = "http://webservice.lms.ibm.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "createLeadDataResponse", namespace = "http://webservice.lms.ibm.com/")
public class CreateLeadDataResponse {

    @XmlElement(name = "return", namespace = "")
    private com.ibm.lms.dto.CreateLeadVer1ResponseMessage _return;

    /**
     * 
     * @return
     *     returns CreateLeadVer1ResponseMessage
     */
    public com.ibm.lms.dto.CreateLeadVer1ResponseMessage getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(com.ibm.lms.dto.CreateLeadVer1ResponseMessage _return) {
        this._return = _return;
    }

}