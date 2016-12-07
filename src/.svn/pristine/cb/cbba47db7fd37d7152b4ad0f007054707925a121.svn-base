/*
 * Created on Nov 26, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.io.IOException;
import java.util.ArrayList;

import com.ibm.lms.dto.FileDto;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface BulkUserService {
	
	/**
	 * Methods for Bulk Uploading
	 * @param filePath
	 * @param userId
	 * @param elementId
	 * @throws IOException
	 * @throws LMSException
	 */
   public void bulkUpload()throws IOException, LMSException;
			
	/**
	 * Method for bulk Deletion
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws LMSException
	 */		
	public FileDto bulkDelete(String filePath)throws IOException, LMSException;

	/**
	 * Method to retrieve Bulk upload Details
	 * @param string
	 * @return
	 */
	public FileDto getBulkUploadDetails() throws LMSException;
	
	/**
	 * Method to retrieve Bulk delete Details 
	 * @param string
	 * @return
	 * @throws LMSException
	 */
	 
	public ArrayList getBulkDeleteDetails(String string) throws LMSException;


	/**
	 * Method to Upload a File
	 * @param fileDto
	 * @return
	 * @throws LMSException
	 */
	 
	public int uploadFile(FileDto fileDto)throws LMSException;


	/**
	 * Method  to Update File Status
	 * @param dto
	 * @throws LMSException
	 */
	 
	public void updateFileStatus(FileDto dto)throws LMSException;

    /**
     * Method to retrieve Bulk User Files
     * @param date
     * @return
     * @throws LMSException
     */
	public ArrayList getBulkUserFiles(String circleId,String date) throws LMSException;

}
