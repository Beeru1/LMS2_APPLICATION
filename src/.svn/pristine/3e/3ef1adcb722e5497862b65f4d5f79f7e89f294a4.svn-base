package com.ibm.lms.services.impl;

import java.util.ArrayList;

import com.ibm.lms.dao.LinkMstrDao;
import com.ibm.lms.dao.impl.LinkMstrDaoImpl;
import com.ibm.lms.dto.LinkMstrDto;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.KmLinkMstrService;

public class LinkMstrServiceImpl implements KmLinkMstrService{

	public ArrayList editLink(LinkMstrDto kmLinkMstrDto) throws LMSException,DAOException{
		ArrayList list = null;
		LinkMstrDao dao = LinkMstrDaoImpl.linkMstrDaoInstance();//changed by srikant new LinkMstrDaoImpl();
		list = dao.editLink(kmLinkMstrDto);
		return list;
	}
	
	public void createLink(){
		
	}
	
	public void deleteLink(){
		
	}
	
	public ArrayList viewLinks() throws LMSException,DAOException{
		
		ArrayList list = null;
		LinkMstrDaoImpl dao = LinkMstrDaoImpl.linkMstrDaoInstance();//changed by srikant  new LinkMstrDaoImpl();
		list = dao.viewLinks();
		
		return list;
	}
	
	public ArrayList getUserRoleList(String actorId) throws LMSException,DAOException{
		ArrayList list = null;
		LinkMstrDao dao = LinkMstrDaoImpl.linkMstrDaoInstance();//changed by srikant  new LinkMstrDaoImpl();
		list = dao.getUserRoleList(actorId);
		
		return list;
	}
	
	public ArrayList viewLinks(String elementId) throws LMSException,DAOException{
		ArrayList list = null;
		LinkMstrDao dao = LinkMstrDaoImpl.linkMstrDaoInstance();//changed by srikant  new LinkMstrDaoImpl();
		list = dao.viewLinks(elementId);
		
		return list;
	}
	
	public int getMaxRowCountForElement(int elementId) throws DAOException{
		int max_count = 0;
		
		LinkMstrDao dao = LinkMstrDaoImpl.linkMstrDaoInstance();//changed by srikant  new LinkMstrDaoImpl();
		max_count = dao.getMaxRowCountForElement(elementId);
		
		return max_count;

	}
	
	public int getLinkIdForElement(int elementId) throws DAOException{
		int linkId = 0;
		
		LinkMstrDao dao = LinkMstrDaoImpl.linkMstrDaoInstance();//changed by srikant  new LinkMstrDaoImpl();
		linkId = dao.getLinkIdForElement(elementId);
		
		return linkId;

	}
}
