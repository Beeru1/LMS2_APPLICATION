package com.ibm.lms.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.engine.helper.InstanceProperties;
import com.ibm.misc.BASE64Encoder;


/**
 * This Class had method to send SMS as XML object over HTTP connection
 * @author Kundan
 */
public class SendSMSXML {
	static PostMethod postMethod = null;
	static HttpClient client = null;
	private static Logger logger=Logger.getLogger(SendSMSXML.class);

	
	/**
	 * This method creates an XML string of destination Address & SMS text, initialize connection from
	 * MB (Message Broker) and creates a POST method to send XML packets over HTTP connection.
	 * @param mobileNo
	 * @param message
	 */
	public  void sendSms(String mobileNo, String message) {

		try {
			String placeHolderString;
			IEncryption enc_dec = new Encryption();
			//logger.info("INSIDE SEND SMS");
			String ip =PropertyReader.getAppValue("sms.xml.mb.ip").trim();
			String port = PropertyReader.getAppValue("sms.xml.mb.port").trim();
			String userId = PropertyReader.getAppValue("sms.xml.mb.userid").trim();
			String password = enc_dec.decrypt(PropertyReader.getAppValue("sms.xml.mb.pass").trim());
			String smsSender = PropertyReader.getAppValue("sms.xml.mb.src").trim(); 
			
			StringBuffer sbXmlMsg = new StringBuffer("");
			sbXmlMsg.append("<?xml version=\"1.0\" encoding=\"US-ASCII\"?><message>");
			sbXmlMsg.append("<sms type=\"mt\"><destination");
			sbXmlMsg.append("><address><number type=\"national\">");
			sbXmlMsg.append(mobileNo);
			sbXmlMsg.append("</number></address></destination><source><address>");
			sbXmlMsg.append("<alphanumeric>"+smsSender+"</alphanumeric>");
			sbXmlMsg.append("</address></source>");
			sbXmlMsg.append("<rsr type=\"all\" />");
			sbXmlMsg.append("<ud type=\"text\" encoding=\"default\">");
			sbXmlMsg.append(new StringBuffer()).append((message));
			sbXmlMsg.append("</ud></sms></message>");
			placeHolderString = sbXmlMsg.toString();

				/* Obtaining connection from First Hope */
			getConnection(ip, Integer.parseInt(port), userId, password);

			/* Sending SMS using XML over HTTP */
			StringRequestEntity requestEntity =	new StringRequestEntity(placeHolderString);
			postMethod.setRequestEntity(requestEntity);
			client.executeMethod(postMethod);
			//logger.info("SENDING....");
			//Print Response Body
			String responseString = postMethod.getResponseBodyAsString();
			logger.info("ACK : " + responseString);

		} catch (Exception e) {
			e.printStackTrace();			
		} finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
	}

	/**
	 * This method initialize a client and a connection from Message Broker
	 * @param mb_ip
	 * @param mb_port
	 * @param userId
	 * @param password
	 * @return
	 */
	private void getConnection(String mb_ip,int mb_port,String userId,String password)
	{
		client = new HttpClient();
		client.setHttpConnectionManager(new MultiThreadedHttpConnectionManager());
		client.getHostConfiguration();
		String url ="http"+"://"+mb_ip+ ":" +mb_port;

		try {
			postMethod = new PostMethod(url);
			postMethod.setRequestHeader("Content-Type", "text/xml");
			postMethod.setRequestHeader("Authorization", "Basic " + encode(userId + ":" + password));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Encoding message broker credentials
	 * @param userId:password
	 * @return Encoded userId & password
	 */
	private String encode(String source) {
		BASE64Encoder enc = new BASE64Encoder();		
		return (enc.encode(source.getBytes()));
	}
}
