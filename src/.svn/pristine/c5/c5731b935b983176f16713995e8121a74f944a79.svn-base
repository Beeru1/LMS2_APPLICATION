/*
 * Created on Apr 15, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.ibm.km.dao.KmAlertMstrDao;
import com.ibm.km.dao.impl.KmAlertMstrDaoImpl;
import com.ibm.km.dto.KmAlertMstr;
import com.ibm.km.services.KmAlertMstrService;
import com.ibm.lms.exception.LMSException;

/**
 * @author aditya
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KmAlertMstrServiceImpl implements KmAlertMstrService{

	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmAlertMstrService#createAlert(com.ibm.km.dto.KmAlertMstr)
	 * Service to create a new Alert. 
	 **/
	public int createAlert(KmAlertMstr dto) throws LMSException {
		KmAlertMstrDao dao=new KmAlertMstrDaoImpl();
		int rows = dao.insert(dto);
		return rows;
		
	}
	public String getAlertMessage(String circleId) throws LMSException {
			String messages="";
			KmAlertMstrDao dao=new KmAlertMstrDaoImpl();
			messages = dao.getAlertMessage(circleId);
			if(messages==null ||messages.equals("")){
				messages="No Previous Alert Created.";
			}
			return messages;
		
		}
	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmAlertMstrService#getAlertList(com.ibm.km.dto.KmAlertMstr)
	 */
	public ArrayList getAlertList(KmAlertMstr dto) throws LMSException {
		// TODO Auto-generated method stub
		KmAlertMstrDao dao=new KmAlertMstrDaoImpl();
		ArrayList AlertList=dao.getAlertList(dto);
		return AlertList;
	}
	public String getAlertMessage(KmAlertMstr dto) throws LMSException {
		KmAlertMstrService service=new KmAlertMstrServiceImpl();
		StringBuffer alertMessages = new StringBuffer();
		String message="";
		try{
		
		List alertList=service.getAlertList(dto);
		
		if(alertList!=null ){
			
			if(alertList.size()>0){
				for (int i = 0; i < alertList.size(); i++) {
					dto = (KmAlertMstr) alertList.get(i);

					if (dto != null) {
						alertMessages.append(dto.getCreatedDt().substring(11,16)+" Hrs   :    "+dto.getMessage());
				
						alertMessages.append("\\n");
						alertMessages.append("\\n");
					}
				}
				message=alertMessages.toString();
			}
			
		}}
		catch(Exception e){
			
		}
		return message;
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmAlertMstrService#deleteAlert(com.ibm.km.dto.KmAlertMstr)
	 */
	public String deleteAlert(KmAlertMstr dto) throws LMSException {
		// TODO Auto-generated method stub
		KmAlertMstrDao dao=new KmAlertMstrDaoImpl();
		String message=dao.deleteAlert(dto);
		return message;
	}
	/* KM Phase II alert history report service*/
		/* (non-Javadoc)
		 * @see com.ibm.km.services.KmElementMstrService#getAlertHistoryReport(java.lang.String[])
		 */
		public ArrayList getAlertHistoryReport(String circleId,String timeInterval) throws LMSException{
		ArrayList alertList=new ArrayList();
		KmAlertMstrDao dao=new KmAlertMstrDaoImpl();
		alertList=dao.getAlertHistoryReport(circleId,timeInterval);
		return alertList;
		}

		/*km pase II view alert*/
		/* (non-Javadoc)
		 * @see com.ibm.km.services.KmElementMstrService#getAlerts(java.lang.String[])
		 */
		public ArrayList getAlerts(String circleId,String time) throws LMSException{
			ArrayList ViewAlertList=new ArrayList();
				KmAlertMstrDao dao=new KmAlertMstrDaoImpl();
				ViewAlertList=dao.getAlerts(circleId,time);
				return ViewAlertList;
		}
	/* km phase II edit alert*/
	/**
	* @param dto
	*/
	public int editAlert(KmAlertMstr dto) throws LMSException{
		KmAlertMstrDao dao=new KmAlertMstrDaoImpl();
				int rows = dao.editAlert(dto);
				return rows;
	}

}
