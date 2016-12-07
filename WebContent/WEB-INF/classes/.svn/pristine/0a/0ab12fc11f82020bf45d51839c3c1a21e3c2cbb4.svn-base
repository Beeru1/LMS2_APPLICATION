/*
 * Created on Apr 15, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.ibm.km.dao.KmScrollerMstrDao;
import com.ibm.km.dao.impl.KmScrollerMstrDaoImpl;
import com.ibm.km.dto.KmAlertMstr;
import com.ibm.km.dto.KmScrollerMstr;
import com.ibm.km.services.KmScrollerMstrService;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KmScrollerMstrServiceImpl implements KmScrollerMstrService{

	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmScrollerMstrService#createScroller(com.ibm.km.dto.KmScrollerMstr)
	 * Service to create a new scroller. 
	 **/
	public int createScroller(KmScrollerMstr dto) throws LMSException {
		KmScrollerMstrDao dao=new KmScrollerMstrDaoImpl();
		int rows = dao.insert(dto);
		return rows;
		
	}
	public String getScrollerMessage(String circleId) throws LMSException {
			String messages="";
			KmScrollerMstrDao dao=new KmScrollerMstrDaoImpl();
			messages = dao.getScrollerMessage(circleId);
			if(messages==null ||messages.equals("")){
				messages="Welcome To iPortal.";
			}
			return messages;
		
		}
	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmScrollerMstrService#getScrollerList(com.ibm.km.dto.KmScrollerMstr)
	 */
	public ArrayList getScrollerList(KmScrollerMstr dto) throws LMSException {
		// TODO Auto-generated method stub
		KmScrollerMstrDao dao=new KmScrollerMstrDaoImpl();
		ArrayList scrollerList=dao.getScrollerList(dto);
		return scrollerList;
	}
	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmScrollerMstrService#deleteScroller(com.ibm.km.dto.KmScrollerMstr)
	 */
	public String deleteScroller(KmScrollerMstr dto) throws LMSException {
		// TODO Auto-generated method stub
		KmScrollerMstrDao dao=new KmScrollerMstrDaoImpl();
		String message=dao.deleteScroller(dto);
		return message;
	}
	public int editScroller(KmAlertMstr dto) throws LMSException, LMSException {
		KmScrollerMstrDao dao = new KmScrollerMstrDaoImpl();
		return dao.editScroller(dto);
	}

	public int createAllLobScroller(KmScrollerMstr dto) throws LMSException {
		KmScrollerMstrDao dao=new KmScrollerMstrDaoImpl();
		int rows = dao.createAllLobScroller(dto);
		return rows;
	}
	
	public String getBulkScrollerMessage(List<Integer> elementIds) throws LMSException {
		String messages="";
		KmScrollerMstrDao dao=new KmScrollerMstrDaoImpl();
		messages = dao.getBulkScrollerMessage(elementIds);
		if(messages==null ||messages.equals("")){
			messages="Welcome To iPortal.";
		}
		return messages;
	}

}
