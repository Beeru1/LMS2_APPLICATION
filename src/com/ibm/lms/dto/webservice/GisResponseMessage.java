
package com.ibm.lms.dto.webservice;




public class GisResponseMessage {

   
    private String responseCode="";
   
    private String responseMsg ="";

    /**
     * Gets the value of the responseCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * Sets the value of the responseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseCode(String value) {
        this.responseCode = value;
    }

    /**
     * Gets the value of the responseMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseMsg() {
        return responseMsg;
    }

    /**
     * Sets the value of the responseMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseMsg(String value) {
        this.responseMsg = value;
    }

}
