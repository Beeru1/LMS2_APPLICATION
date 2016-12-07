/*
 * Created on Nov 26, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.lms.dto.FileDto;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface BulkUserDao {
	
	public int bulkUserDeleteFile(FileDto dto) throws LMSException;

	/**
	 * @param fileId
	 * @return
	 */
	public FileDto getBulkUploadDetails() throws LMSException;
	
	/**
	 * Method for bulk upload
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	public int bulkUserUploadFile(FileDto dto) throws LMSException;

		/**
		 * @param fileId
		 * @return
		 */
		public ArrayList getBulkDeleteDetails(String fileId) throws LMSException;

		/**
		 * @param fileId
		 */
		public void getBulkDeleteDetails(int fileId) throws LMSException;

		/**
		 * @param fileId
		 */
		public void updateFileStatus(FileDto dto)throws LMSException;
       
		/**
		 * Method to get bulk user
		 * @param date
		 * @return
		 * @throws LMSException
		 */
		public ArrayList getBulkUserFiles(String circleId,String date)throws LMSException;
	
	

}
