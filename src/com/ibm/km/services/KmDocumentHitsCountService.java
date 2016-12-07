package com.ibm.km.services;

import java.util.ArrayList;

import com.ibm.km.forms.KmDocumentHitsCountFormBean;
import com.ibm.lms.exception.DAOException;

public interface KmDocumentHitsCountService {
	
	public ArrayList getTopBarLinks(KmDocumentHitsCountFormBean bean) throws DAOException;

	public ArrayList getBottomBarLinks(KmDocumentHitsCountFormBean bean) throws DAOException;

}
