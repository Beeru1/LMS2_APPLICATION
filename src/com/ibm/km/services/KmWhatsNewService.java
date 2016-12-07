/*
 * Created on Apr 29, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.ArrayList;

import org.apache.struts.upload.FormFile;

import com.ibm.km.dto.DocumentPath;
import com.ibm.km.dto.KmWhatsNew;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmWhatsNewService {

	/**
	 * @param pathObject
	 * @param file
	 * @return
	 */
	public String saveFile(DocumentPath pathObject, FormFile file)throws LMSException;

	/**
	 * @param dto
	 * @return
	 */
	public void saveFileInfoInDB(KmWhatsNew dto) throws LMSException;

	/**
	 * @param oldPath
	 * @param newPath
	 * @param file
	 */
	public void updateFile(String oldPath, String newPath, FormFile file)throws LMSException;

	/**
	 * @param oldFile
	 * @param fileName
	 * @param userId
	 */
	public void updateFileInfoInDB(String oldFile, String fileName, int userId, String docDesc)throws LMSException;

	

	/**
	 * @param circleId
	 * @param categoryId
	 * @param string
	 * @return
	 */
	public ArrayList viewFiles(String[] circleIds, String string)throws LMSException;

	/**
	 * @param string
	 * @return
	 */
	public String[] getCategories(String elementId) throws LMSException;

	
	
	
}
