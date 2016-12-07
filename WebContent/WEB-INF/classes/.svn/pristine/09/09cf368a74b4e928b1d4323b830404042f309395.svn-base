package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.km.dto.KmExcelMstrDTO;
import com.ibm.lms.exception.LMSException;
/**
 * @author Anveeksha & Neeraj
 * for waiver matrix uplaod 
 * save the data of uploaded excel to database
 */
public interface KmExcelMstrDao {
	/**
	 * @param dto
	 * @return ArrayList of Bill Plans without Rate Details
	 * @throws LMSException
	 */
	public ArrayList createTempTable(KmExcelMstrDTO dto) throws LMSException ;
	public int getErrorStatus() throws LMSException;
	/**
	 * @param userID,filePath
	 * @throws LMSException
	 */
	public void updateMasterTables(String userID,String filePath) throws LMSException;
}
