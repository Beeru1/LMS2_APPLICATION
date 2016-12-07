package com.ibm.lms.services;

import java.io.IOException;

import com.ibm.lms.dto.SMSDto;

public interface SMSSendReceiveService {
	
	
	public SMSDto parseXml(String xmlString) throws IOException;
	public Long isValidLead(String leadId) throws Exception;
	public boolean updateWonLostLead(String leadId) throws Exception;
	public void updateLeadLost(Long leadId,String Status,String msIsdn) throws Exception;
	public void updateLeadWon(Long leadId,String Satus,String msIsdn,String cafNo) throws Exception;
	public void leadMobNoUpdate(Long lead,String cellNo) throws Exception;
	public long logSMS(String header,String body) throws Exception;
	public void updateSMSLog(long Id,String msisdn) throws Exception;
	
	

}
