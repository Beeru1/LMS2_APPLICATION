package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.km.dto.KmCsrLatestUpdatesDto;
import com.ibm.lms.exception.DAOException;

public interface KmCsrLatestUpdatesDao {
	
	public ArrayList initGetUpdates() throws DAOException;
	
	/**
	 * Method to insert Latest Updates into db.
	 */
	public  int insertLatestUpdates(KmCsrLatestUpdatesDto dto) throws DAOException ;

}
