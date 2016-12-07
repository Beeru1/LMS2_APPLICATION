package com.ibm.lms.services.impl;

import com.ibm.lms.dao.SMSSendReceiveDao;
import com.ibm.lms.dao.impl.SMSSendReceiveDaoImpl;
import com.ibm.lms.dto.SMSDto;
import com.ibm.lms.engine.helper.InstanceProperties;
import com.ibm.lms.services.SMSSendReceiveService;

import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

import java.io.*;
public class SMSSendReceiveServiceImpl implements SMSSendReceiveService {

	private static Logger logger=Logger.getLogger(SMSSendReceiveServiceImpl.class);
	@Override
	public SMSDto parseXml(String xmlString) throws IOException {
		
		
		Long  headerLead=null;
		String status="";
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("xmlReceived "+xmlString);
		ByteArrayInputStream input=new ByteArrayInputStream(xmlString.getBytes());
		Document doc = null;
		try {
			doc = builder.parse(input);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Element root=doc.getDocumentElement();
		NodeList nodeListSMS=root.getElementsByTagName("sms");
		NodeList nodeListUd=((Element)nodeListSMS.item(0)).getElementsByTagName("ud");
		String smsMessage=nodeListUd.item(0).getTextContent().trim();
		
		
		NodeList nodeListSrc=((Element)nodeListSMS.item(0)).getElementsByTagName("source");
		Node nodemsIsdn=nodeListSrc.item(0).getChildNodes().item(0).getChildNodes().item(0);
		String msIsdn=nodemsIsdn.getTextContent();
		
		NodeList nodeListHeader=((Element)nodeListSMS.item(0)).getElementsByTagName("header-lead");
		NodeList nodeListStatus=((Element)nodeListSMS.item(0)).getElementsByTagName("header-status");
		
		if(nodeListHeader!=null && nodeListHeader.item(0)!=null){
			String headerLeadStr=nodeListHeader.item(0).getTextContent();
			if(headerLeadStr!=null && headerLeadStr.trim()!="" && headerLeadStr.matches("\\d+")){
				headerLead=Long.parseLong(headerLeadStr);
				
			}
		}
		if(nodeListStatus!=null && nodeListStatus.item(0)!=null){
			status=nodeListStatus.item(0).getTextContent();
			
		}
			
		SMSDto smsDto=new SMSDto();
		smsDto.setHeaderLead(headerLead);
		smsDto.setHeaderStatus(status);
		smsDto.setMsIsdn(msIsdn);
		smsDto.setSmsText(smsMessage);
		
		logger.info("Mobile no-"+msIsdn);
		logger.info("Message is-"+smsMessage);
		
		return smsDto;
		
		

	}

	@Override
	public Long isValidLead(String leadId) throws Exception {
		
		SMSSendReceiveDao smsDao=new SMSSendReceiveDaoImpl();
		Long lead=smsDao.isValidLead(leadId);
		
		return lead;
	}

	@Override
	public boolean updateWonLostLead(String leadId) throws Exception {
		// TODO Auto-generated method stub
		
		SMSSendReceiveDao smsDao=new SMSSendReceiveDaoImpl();
		
		return smsDao.updateWonLostLead(leadId);
	}

	@Override
	public void updateLeadLost(Long leadId, String Status,String msIsdn) throws Exception {
		// TODO Auto-generated method stub
		SMSSendReceiveDao smsDao=new SMSSendReceiveDaoImpl();
		smsDao.updateLost(leadId.toString(), Status, msIsdn);
		
	}

	@Override
	public void updateLeadWon(Long leadId, String Satus, String msIsdn,
			String cafNo) throws Exception {
		
		// TODO Auto-generated method stub
		SMSSendReceiveDao smsDao=new SMSSendReceiveDaoImpl();
		smsDao.updateLeadWon(leadId, Satus, msIsdn,cafNo);
		
	}

	@Override
	public void leadMobNoUpdate(Long lead, String cellNo) throws Exception {
		// TODO Auto-generated method stub
		SMSSendReceiveDao smsDao=new SMSSendReceiveDaoImpl();
		smsDao.updateLeadMobNo(lead, cellNo);
	}

	@Override
	public long logSMS(String header, String body) throws Exception {
		SMSSendReceiveDao smsDao=new SMSSendReceiveDaoImpl();
		return smsDao.logSMS(header, body);
		
	}
	
    public void updateSMSLog(long Id,String msisdn) throws Exception{
    	
    	SMSSendReceiveDao smsDao=new SMSSendReceiveDaoImpl();
    	smsDao.updateSMSLog(Id, msisdn);
    	
    	
    }

}
