/*

 * Created on Feb 6, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.ArrayList;

import com.ibm.km.dto.KmDocumentMstr;
import com.ibm.km.dto.KmSearch;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

/**
 * @author namangup
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmDocumentService {
	public ArrayList retrieveCSRDocumentList(int[] circleID) throws LMSException;

	/**
	 * Method to increase Document Hit Count
	 * @param documentID
	 * @param userId
	 * @return
	 * @throws LMSException
	 */
	public KmDocumentMstr increaseDocHitCount(String documentID, String userId)throws LMSException ;

	/**
	 * Method to get Document
	 * @param documentID
	 * @return
	 * @throws LMSException
	 */
	public KmDocumentMstr getDocument(String documentID)throws LMSException ;
	
	/**
	 * Method to delete File Service
	 * @param documentId
	 * @param updatedBy
	 * @throws LMSException
	 */
	public void deleteFileService(String documentId, String updatedBy)throws LMSException;

	public String isFavouriteDocument(String documentId, String userId) throws LMSException;
		
    /**
     * Method  for Keyword file Search
     * @param keyword
     * @param circleId
     * @param uploadedBy
     * @return
     * @throws LMSException
     */
	public ArrayList keywordFileSearch(String keyword, String circleId, String uploadedBy)throws LMSException;

	/**
	 * Method to add to Favourite
	 * @param userId
	 * @param docId
	 * @return
	 * @throws LMSException
	 */
	public int addToFavorites (String userId, String docId) throws LMSException;
	
	/**
	 * method to delete Favourite
	 * @param userId
	 * @param docId
	 * @return
	 * @throws LMSException
	 */
	public int deleteFavorites (String userId, String docId) throws LMSException;
	
	/**
	 * method to check for Favourite
	 * @param userId
	 * @param docId
	 * @return
	 * @throws LMSException
	 */
	public boolean checkForFavorite (String userId, String docId) throws LMSException;
	
	/**
	 * Method to retrieve Document Name
	 * @param documentId
	 * @return
	 * @throws LMSException
	 */
	public String[] getDocumentName(String[] documentId)throws LMSException;

	/**
	 * Method to view File 
	 * @param root
	 * @param parentId
	 * @return
	 * @throws LMSException
	 */
	public ArrayList viewFileService(String root,String parentId)throws LMSException;

	
	/**
	 * Method to change Path
	 * @param movedDocumentList
	 * @param oldPath
	 * @param newPath
	 * @return
	 * @throws LMSException
	 */
	public boolean changePath(String[] movedDocumentList, String oldPath, String newPath) throws LMSException;

	/**
	 * method to get All Documents
	 * @param parentId
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getAllDocuments(String parentId)throws LMSException;

	/**
	 * Method to get Document ID's
	 * @param movedDocumentList
	 * @return
	 * @throws LMSException
	 */
	public String[] getDocumentIds(String[] movedDocumentList) throws LMSException;

	/**
	 * Method to get Document Path
	 * @param documentId
	 * @return
	 * @throws LMSException
	 */
	public String getDocumentPath(String documentId)throws LMSException;

	/**
	 * Method to get Element Id
	 * @param string
	 * @return
	 * @throws LMSException
	 */
	public String getElementId(String string) throws LMSException;

    /**
     * Method to get Document Dto
     * @param documentId
     * @return
     * @throws LMSException
     */
	public KmDocumentMstr getDocumentDto(String documentId) throws LMSException;

	/**
	 * Method to  edit Documennt
	 * @param dto
	 * @throws LMSException
	 */
	public void editDocument(KmDocumentMstr dto)throws LMSException;
	
	/**
	 * Method to retrieve Document Id
	 * @param documentName
	 * @return
	 * @throws LMSException
	 */
	public String getDocumentId(String documentName)throws LMSException;
	
	/**
	 * Method to retrieve Document Version
	 * @param documentId
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getDocumentVersions(String documentId) throws LMSException;

	/**
	 * Method to retrieve Documents
	 * @param parentId
	 * @return
	 * @throws LMSException
	 */
	public String[] getDocuments(String parentId)throws LMSException;

	/**
	 * Method to delete Documents
	 * @param documents
	 * @param updatedBy
	 * @throws LMSException
	 */
	public void deleteDocuments(String[] documents,String updatedBy)throws LMSException;

	/**
	 * Method to get circle Approver
	 * @param circleId
	 * @return
	 * @throws LMSException
	 */
	public String getCircleApprover(String circleId)throws LMSException;

	/**
	 * Method to get Document path
	 * @param string
	 * @return
	 * @throws LMSException
	 */
	public String getDocPath(String string)throws LMSException;

	/**
	 * Method to changeDocument path 
	 * @param documentElementIds
	 * @return
	 * @throws LMSException
	 * @throws Exception
	 */
	public String[] changeDocumentPathsInDb(String[] documentElementIds)throws LMSException, Exception;

	/**
	 * Method to changeDocument path 
	 * @param elementId
	 * @return
	 * @throws LMSException
	 */
	public boolean changeDocumentPathsInDb(String elementId)throws LMSException;

	/**
	 * Method to update Document path
	 * @param allDocuments
	 * @param alldocumentPaths
	 * @return
	 * @throws LMSException
	 */
	public boolean updateDocumentPaths(String[] allDocuments, String[] alldocumentPaths)throws LMSException;

	/**
	 * method to get Document Details
	 * @param string
	 * @return
	 * @throws LMSException
	 */
	public KmDocumentMstr getDocumentDetails(String string) throws LMSException;
	
	/*  KM PHASE II  a new service for archived search   */
	/**
	 * Method to retrieve archived documents
	 * @param dto
	 * @return
	 * @throws LMSException
	 */
	public  ArrayList archivedSearch(KmDocumentMstr dto)  throws LMSException;
	
	/*  KM PHASE II  a new service for csrkeyword search   */
	/**
	 * Method for csr Keyword search
	 * @param dto
	 * @return
	 * @throws LMSException
	 */  
	public  ArrayList csrKeywordSearch(	KmSearch dto)  throws LMSException;

	 /*  KM PHASE II  a new service for retrieving single document   */	
	/**
	 * Method to retrieve single document
	 * @param subCategoryId
	 * @return
	 * @throws LMSException
	 * @throws DAOException
	 */
	public KmDocumentMstr getSingleDoc(String subCategoryId)throws LMSException,DAOException;

	
	public KmDocumentMstr getDocumentByElementId(String elementId) throws LMSException;
	
	public ArrayList<String> docFilterAsPerDocType(ArrayList<String> docIdList,int docType) throws LMSException;
	



	

}
