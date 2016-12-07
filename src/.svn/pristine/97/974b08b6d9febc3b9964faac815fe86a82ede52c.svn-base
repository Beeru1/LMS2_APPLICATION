/*
 * Created on Apr 29, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.km.dto.DocumentPath;
import com.ibm.km.dto.KmWhatsNew;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmWhatsNewDao {

	/**
	 * @param path
	 * @param string
	 * @return
	 */
	public String checkFile(DocumentPath path, String string) throws LMSException;

	/**
	 * @param dto
	 * @return
	 */
	public void saveFileInfoInDB(KmWhatsNew dto)throws LMSException;

	/**
	 * @param oldFile
	 * @param fileName
	 * @param userId
	 */
	public void updateFileInfoInDB(String oldFile, String fileName, int userId, String docDesc)throws LMSException;


	/**
	 * @param circleId
	 * @param categoryId
	 * @param actorId
	 * @return
	 */
	public ArrayList viewFiles(String[] circleIds, String actorId)throws LMSException;

	/**
	 * @param elementId
	 * @return
	 */
	public String[] getCategories(String elementId) throws LMSException;

	

}
