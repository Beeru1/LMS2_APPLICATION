/*
 * Created on May 15, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibm.km.dto.KmElementMstr;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmElementMstrDao {

	public HashMap<String, String> getAllCircleDesc()    throws LMSException;
	/**
	 * @param parentId
	 */
	public ArrayList getChildren(String parentId) throws LMSException;
	
	/**
	 * @param parentId, elementLevelId
	 */
	public ArrayList getChildren(String parentId, String elementLevelId) throws LMSException;	

	/**
	 * @param parentId
	*/
	public ArrayList getAllChildren(String parentId) throws LMSException;
	
	public ArrayList getAllPANChildren(String parentId)   throws LMSException;

	/**
	 * @param parentId
	 */
	public ArrayList getChildrenWithPath(String parentId, String root) throws LMSException;

	/**
	 * @param parentId
	*/
	public ArrayList getAllChildrenWithPath(String parentId, String root) throws LMSException;	
	
	 public ArrayList getDocList(String parentId)    throws LMSException;

	/**
	 * @param elementId
	 * @return elementLevelName
	 */
	public String getElementLevelName(String elementLevelId) throws LMSException;

	/**
	 * @param elementId
	 * @return elementLevelId
	 */
	public String getElementLevelId(String elementId) throws LMSException;

	/**
	 * @param elementId
	 * @return
	 */
	public String getAllParentIdString(String rootId, String elementId) throws LMSException;
	
	public List getElementLevelNames() throws LMSException;

	/**
	 * @param elementId
	 * @return
	 */
	public String getAllParentNameString(String rootId, String elementId) throws LMSException;
	/**
	 * @param elementId
	 * @return
	 */
	public String getParentId(String elementId)throws LMSException;

	/**
	 * @param parentId
	 * @return
	 */
	public KmElementMstr getPanChild(String parentId)throws LMSException;

	/**
	 * @param elementMstrDTO
	 * @return
	 */
	public String insertElement(KmElementMstr elementMstrDTO)throws LMSException;

	/**
	 * @param parentId
	 * @return
	 */
	public ArrayList getAllDocuments(String parentId)throws LMSException;

	/**
	 * @param elementName
	 * @param parentId
	 * @return
	 */
	public String[] checkExistingElement(String elementName, String parentId)throws LMSException;



	/**
	 * @param movedDocumentList
	 * @param parentId
	 * @param documentPath
	 * @return
	 */
	public boolean moveElements(String[] movedDocumentList, String parentId)throws LMSException;



	/**
	 * @param elementId
	 * @return 
	 */
	public String getElementName(String elementId) throws LMSException;

	/**
	 * @param string
	 * @return
	 */
	public ArrayList getAllLevelChildren(String string) throws LMSException;

	/**
	 * @param childrenList
	 * @param levelDiff
	 */
	public void updateElementLevel(ArrayList childrenList, int levelDiff) throws LMSException;

	/**
	 * @param elementId
	 * @return
	 */
	public String[] getChildrenIds(String elementId) throws LMSException;


	/**
	 * @param elementId
	 * @return
	 */
	public ArrayList getChildrenIds(String elementId,String elementLevel) throws LMSException;

	
	/**
	 * @param parentId
	 * @param levelId
	 * @return
	 */
	public ArrayList getAllChildren(String parentId, String levelId) throws LMSException;

	/**
	 * @param dto
	 */
	public void editElement(KmElementMstr dto)throws LMSException;



	/**
	 * @param elementId
	 * @return
	 */
	public KmElementMstr getElemetDto(String elementId)throws LMSException;

	/**
	 * @param elementId
	 * @return
	 */
	public String[] getElements(String elementId)throws LMSException;

	/**
	 * @param elements
	 */
	public String[] getElements(String[] elements)throws LMSException;

	/**
	 * @param elements
	 * @param updatedBy
	 */
	public void deleteElements(String[] elements, String updatedBy)throws LMSException;

	/**
	 * @param documentId
	 * @return
	 */
	public String getElementId(String documentId)throws LMSException;

	/**
	 * @param elementId
	 * @return
	 */
	public String getCircleId(String elementId)throws LMSException;

	/**
	 * @param movedElementList
	 * @return
	 */
	public String[] getDocs(String[] movedElementList)throws LMSException;
	/**
	 * @param parentId
	 * @return
	 */

	public ArrayList getAllChildrenRec(String parentId, String elementLevel)throws LMSException;

	/**
	 * @param circleId
	 * @return
	 */
	public HashMap getCategoryMapElements(String circleId, String favCategoryId) throws LMSException;
	/**
	 * @param circleId
	 * @return
	 */
	public HashMap getSubCategoryMapElements(String elementId,int parentLevel, int childLevel) throws LMSException;

	public ArrayList getAllChildrenNoPan(String parentId, String elementLevelId)throws LMSException;

	public boolean getCircleStatus(String circleId) throws LMSException;

	public KmElementMstr getCompleteElementDetails(String elementId)throws DAOException;
	
	public ArrayList<Integer> getAllElementsAsPerLevel(int levelId) throws LMSException;

    public ArrayList getAllChildrenElements(String parentId) throws LMSException;
    
    public KmElementMstr getElementDetails(int elementId) throws LMSException;

    public void updateLevel(int elementId, int newLevel) throws LMSException ;

}
