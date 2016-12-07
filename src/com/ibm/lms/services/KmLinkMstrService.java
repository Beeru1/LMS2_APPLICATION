package com.ibm.lms.services;

import java.util.ArrayList;

import com.ibm.lms.dto.LinkMstrDto;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

public interface KmLinkMstrService {
	
	public ArrayList editLink(LinkMstrDto kmLinkMstrDto)throws LMSException,DAOException;
	
	public void deleteLink();
	
	public void createLink();
	
	public ArrayList viewLinks() throws LMSException,DAOException;
	
	public ArrayList viewLinks(String elementId) throws LMSException,DAOException;
	
	public ArrayList getUserRoleList(String actorId) throws LMSException,DAOException;
	
	public int getMaxRowCountForElement(int elementId) throws DAOException;
	
	public int getLinkIdForElement(int elementId) throws DAOException;
}
