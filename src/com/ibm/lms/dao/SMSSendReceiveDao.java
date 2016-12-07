package com.ibm.lms.dao;

public interface SMSSendReceiveDao {
	
	public Long isValidLead(String leadId) throws Exception;
	public boolean updateWonLostLead(String leadId) throws Exception;
	public void updateLost(String leadId,String status,String msIsdn) throws Exception;
	public void updateLeadWon(Long leadId, String Satus, String msIsdn,
			String cafNo) throws Exception;
	public void updateLeadMobNo(Long leadId,String mobNo) throws Exception;
	public long logSMS(String header,String body) throws Exception;
	public void updateSMSLog(long Id,String msisdn) throws Exception;
	

}
