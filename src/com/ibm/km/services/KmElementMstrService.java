/*
 * Created on May 15, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.ibm.km.dto.KmElementMstr;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface KmElementMstrService {
	
	/*
	 * Method to return Circle Element ID & Circle Name in a Map
	 */
	
	 public HashMap<String, String> getAllCircleDesc()    throws LMSException;
	 
	/**
	 * Method to retrieve children
	 * @param parentId
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getChildren(String parentId) throws LMSException;
	

	public Map<String, String> getElementPathList(String initialSelectedPath, String[] multipleCircles) throws LMSException ;

	/**
	 * Method to retrieve children
	 * @param parentId
	 * @return
	 * @throws LMSException
	 */
	// Bug resolved : MASDB00103891
	public ArrayList getChildren(String parentId, String elementLevelId) throws LMSException;
	/**
	 * Method to retrieve all children based on parentId
	 * @param parentId
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getAllChildren(String parentId) throws LMSException;
	
	/**
	 * Method to retrieve all children based on parent Id ,level Id
	 * @param parentId
	 * @param levelId
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getAllChildren(String parentId, String levelId) throws LMSException;
	
	/**
	 * method to retrieve children with path
	 * @param parentId
	 * @param root
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getChildrenWithPath(String parentId, String root) throws LMSException;
	
	/**
	 * Method to retrieve all Children with path
	 * @param parentId
	 * @param root
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getAllChildrenWithPath(String parentId, String root) throws LMSException;
	
	/**
	 * Method to get Pan Child
	 * @param parentId
	 * @return
	 * @throws LMSException
	 */
	public KmElementMstr getPanChild(String parentId) throws LMSException;

	/**
	 * Method to get element level Name
	 * @param elementLevelId
	 * @return
	 * @throws LMSException
	 */
	public String getElementLevelName(String elementLevelId) throws LMSException;

	/**
	 * Method to get Element Level id
	 * @param elementId
	 * @return
	 * @throws LMSException
	 */
	public String getElementLevelId(String elementId) throws LMSException;

	/**
	 * Method to get  Child Elements   
	 * @param parentId
	 * @return JSONObject
	 * @throws Exception
	 */
	public JSONObject getElementsAsJson(String parentId) throws Exception;
	/**
	 * Method to get  Child Elements   
	 * @param parentId
	 * @return JSONObject
	 * @throws Exception
	 */
	
	public JSONObject getPANElementsAsJson(String parentId) throws Exception ;
	
	/**
	 * Method to get Document Paths
	 */
	public JSONObject getDocPathAsJson(String parentId) throws Exception;
	
	
	public JSONObject getElementsAsJsonNoPan(String parentId) throws Exception;


	/**
	 * method to retrieve all parent Id
	 * @param rootId
	 * @param elementId
	 * @return
	 * @throws LMSException
	 */
	public String getAllParentIdString(String rootId, String elementId) throws LMSException;
	
    /**
     * method to get all element level Name
     * @return
     * @throws LMSException
     */
	public List getAllElementLevelNames() throws LMSException;

	/**
	 * Method to get all parent Name
	 * @param rootId
	 * @param elementId
	 * @return
	 * @throws LMSException
	 */
	public String getAllParentNameString(String rootId, String elementId) throws LMSException;
	
	/**
	 * Method to get All Parent Id
	 * @param elementId
	 * @return
	 * @throws LMSException
	 */
	public String getParentId(String elementId) throws LMSException;
	
    /**
     * Method to Create Element
     * @param elementMstrDTO
     * @return
     * @throws LMSException
     */
	public String createElement(KmElementMstr elementMstrDTO)throws LMSException;
	
	/**
	 * Method to check Existing Element
	 * @param elementName
	 * @param parentId
	 * @return
	 * @throws LMSException
	 */
	public String[] checkExistingElement(String elementName, String parentId)throws LMSException;


	
	/**
	 * Method to move elements in DB
	 * @param movedDocumentList
	 * @param parentId
	 * @return
	 * @throws LMSException
	 */
	public boolean moveElementsInDB(String[] movedDocumentList, String parentId)throws LMSException;


	/**
	 * Method to get Element by Name
	 * @param elementId
	 * @return
	 * @throws LMSException
	 */
	public String getElementName(String elementId)throws LMSException;

	/**
	 * Method to change Element Level
	 * @param movedElementList
	 * @param levelDiff
	 * @return
	 * @throws LMSException
	 */
	public boolean changeAllElementLevel(String[] movedElementList, int levelDiff) throws LMSException;

	/**
	 * Method to move Elements
	 * @param movedElementList
	 * @param oldPath
	 * @param newPath
	 * @return
	 * @throws LMSException
	 */
	public boolean moveElementsInFS(String[] movedElementList, String oldPath, String newPath) throws LMSException;

	/**
	 * Method to get Children Id
	 * @param string
	 * @return
	 * @throws LMSException
	 */
	public String[] getChildrenIds(String string)throws LMSException;
	
	/**
	 * Method to get Children Id
	 * @param string
	 * @return
	 * @throws LMSException
	 */
	public ArrayList getChildrenIds(String string,String elementLevel)throws LMSException;
	
	
	/**
	 * Method to Edit Elements
	 * @param dto
	 * @throws LMSException
	 */
	public void editElement(KmElementMstr dto) throws LMSException;

	/**
	 * Method to get Element Details
	 * @param elementId
	 * @return
	 * @throws LMSException
	 */
	public KmElementMstr getElementDto(String elementId)throws LMSException;

	/**
	 * Method to Delete Elements
	 * @param elementId
	 * @param updatedBy
	 * @throws LMSException
	 */
	public void deleteElement(String elementId, String updatedBy)throws LMSException;

	/**
	 * Method to get Elements
	 * @param element
	 * @return
	 * @throws LMSException
	 */
	public String[] getElements(String element)throws LMSException;
	
	/**
	 * Method to get Elements
	 * @param element
	 * @return
	 * @throws LMSException
	 */
	public String[] getElements(String[] element)throws LMSException;

	/**
	 * Method to delete Elements
	 * @param elements
	 * @param updatedBy
	 * @throws LMSException
	 */
	public void deleteElements(String[] elements, String updatedBy)throws LMSException;

	/**
	 * Method to get Element Id
	 * @param documentId
	 * @return
	 * @throws LMSException
	 */
	public String getElementId(String documentId)throws LMSException;
	
	/**
	 * Method to get Circle Id
	 * @param elementId
	 * @return
	 * @throws LMSException
	 */
	 public String getCircleId(String elementId)throws LMSException;

	/**
	 * Method to get Documents
	 * @param movedElementList
	 * @return
	 * @throws LMSException
	 */
	 public String[] getDocs(String[] movedElementList)throws LMSException;

	/**
	 * Method to extract circle Id
	 * @param string
	 * @param level
	 * @return
	 * @throws LMSException
	 */
	 public String extractCircleId(String string,int level)throws LMSException;

	/**
	 * Method to get all children records
	 * @param elementId
	 * @param elementLevel
	 * @return
	 * @throws LMSException
	 */
	 public ArrayList getAllChildrenRec(String elementId, String elementLevel) throws LMSException;
	 
	/**
	 * Method to retrieve Category map
	 * @param circleId
	 * @param favCategoryId
	 * @return
	 * @throws LMSException
	 */ 
	public HashMap retrieveCategoryMap(String circleId,String favCategoryId)throws LMSException;
	
	/**
	 * Method to retrieve Category map
	 * @param circleId
	 *
	 * @return
	 * @throws LMSException
	 */ 
	public HashMap retrieveSubCategoryMap(String elementId,String parentLevel, String childLevel)throws LMSException;


	public ArrayList getAllChildrenNoPan(String parentId, String elementLevelId) throws LMSException;

/*
 * @param circleId
 * return whether the circle is restricted or not
 */
	public boolean getCircleStatus(String circleId)throws LMSException;


	public KmElementMstr getCompleteElementDetails(String subCategoryId) throws LMSException;
	
	public ArrayList<Integer> getAllElementsAsPerLevel(int levelId) throws LMSException;

	public String copyElement(String elementId, String path,String userId) throws LMSException;
	
	 public KmElementMstr getElementDetails(int elementId) throws LMSException;;

}
