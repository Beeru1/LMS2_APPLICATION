/*
 * Created on May 15, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.services.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

//import com.ibm.km.common.FileCopy;
import com.ibm.km.dao.KmElementMstrDao;
import com.ibm.km.dao.impl.KmElementMstrDaoImpl;
import com.ibm.km.dto.KmDocumentMstr;
import com.ibm.km.dto.KmElementMstr;
//import com.ibm.km.search.IndexFiles;
import com.ibm.km.services.AddFileService;
import com.ibm.km.services.KmDocumentService;
import com.ibm.km.services.KmElementMstrService;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

/**
 * @author Anil
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class KmElementMstrServiceImpl implements KmElementMstrService{
	
	private static final Logger logger;

	static {

		logger = Logger.getLogger(KmElementMstrServiceImpl.class);
	}
	
 	public ArrayList getChildren(String parentId) throws LMSException {
 		
 		ArrayList elementList=new ArrayList();
 		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		elementList=dao.getChildren(parentId);
 		return elementList;
	}
	
	public ArrayList getAllChildren(String parentId) throws LMSException {
 		
		ArrayList elementList=new ArrayList();
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		elementList=dao.getAllChildren(parentId);
		return elementList;
	}
	
	
	public ArrayList getAllPANChildren(String parentId) throws LMSException {
 		
		ArrayList elementList=new ArrayList();
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		elementList=dao.getAllPANChildren(parentId);
		return elementList;
	}
	
	public ArrayList getAllChildren(String parentId, String levelId) throws LMSException {
 		
		ArrayList elementList=new ArrayList();
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		elementList=dao.getAllChildren(parentId,levelId);
		return elementList;
	}

	public ArrayList getChildrenWithPath(String parentId, String root) throws LMSException {
 		
		ArrayList elementList=new ArrayList();
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		elementList=dao.getChildrenWithPath(parentId,root);
		return elementList;
	}
	
	public ArrayList getAllChildrenWithPath(String parentId, String root) throws LMSException {
 		
		ArrayList elementList=new ArrayList();
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		elementList=dao.getAllChildrenWithPath(parentId,root);
		return elementList;
	}	
	
	public KmElementMstr getPanChild(String parentId) throws LMSException {
 		
		KmElementMstr elementDTO=new KmElementMstr();
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		elementDTO=dao.getPanChild(parentId);
		return elementDTO;
	}	
	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#getElementLevel(java.lang.String)
	 */
	public String getElementLevelName(String elementLevelId) throws LMSException {
		String elementLevelName="";
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		elementLevelName=dao.getElementLevelName(elementLevelId);
		return elementLevelName;
		
	}
	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#getElementLevelId(java.lang.String)
	 */
	public String getElementLevelId(String elementId) throws LMSException{
		String elementLevelId="";
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		elementLevelId=dao.getElementLevelId(elementId);
		return elementLevelId;
	}
	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#getAllParentId(java.lang.String)
	 */
	public String getAllParentIdString(String rootId, String elementId) throws LMSException{
		String parentIdString="";
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		parentIdString=dao.getAllParentIdString(rootId, elementId);
		return parentIdString;
	}	

	public List getAllElementLevelNames() throws LMSException {
		KmElementMstrDao dao=new KmElementMstrDaoImpl();;
		List list=dao.getElementLevelNames();
		return list;
	}
 	
	public JSONObject getElementsAsJson(String parentId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllChildren(parentId);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			KmElementMstr element=(KmElementMstr)iter.next();
			jsonItems.put(element.toJSONObject());
			level=element.getElementLevel();
		}
		json.put("elements", jsonItems);
		json.put("level", level);
		return json;
	}
	
	public JSONObject getPANElementsAsJson(String parentId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getAllPANChildren(parentId);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			KmElementMstr element=(KmElementMstr)iter.next();
			jsonItems.put(element.toJSONObject());
			level=element.getElementLevel();
		}
		json.put("elements", jsonItems);
		json.put("level", level);
		return json;
	}
	
	public JSONObject getDocPathAsJson(String parentId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		List list = dao.getDocList(parentId);
		
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			KmElementMstr element=(KmElementMstr)iter.next();
			jsonItems.put(element.toJSONDocPathObject());
		}
		json.put("elements", jsonItems);
		return json;
	}
	
	
	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#getAllParentId(java.lang.String)
	 */
	public String getAllParentNameString(String rootId, String elementId) throws LMSException{
		String parentNameString="";
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		parentNameString=dao.getAllParentNameString(rootId,elementId);
		return parentNameString;
	}		
	public String getParentId(String elementId) throws LMSException{
		String parentId="";
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		parentId=dao.getParentId(elementId);
		return parentId;
	}	
	/**
	 * @param elementMstrDTO
	 * @return
	 */
	public String createElement(KmElementMstr elementMstrDTO)throws LMSException{
		String elementId="";
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		elementId=dao.insertElement(elementMstrDTO);
		return elementId;				
	}
	/**
	 * @param elementName
	 * @param parentId
	 * @return
	 */
	public String[] checkExistingElement(String elementName, String parentId)throws LMSException{
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		return dao.checkExistingElement(elementName, parentId);		
	}



	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#moveElements(java.lang.String[], java.lang.String)
	 */
	public boolean moveElementsInDB(String[] movedElementList, String parentId) throws LMSException {
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		return dao.moveElements(movedElementList,parentId);
	}




	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#getElementName(java.lang.String)
	 */
	public String getElementName(String elementId) throws LMSException {
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		return dao.getElementName(elementId);
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#changeAllElementLevel(java.lang.String[], int)
	 */
	public boolean changeAllElementLevel(String[] movedElementList, int levelDiff) throws LMSException {
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		boolean success=false;
		for(int i=0;i<movedElementList.length;i++){
			ArrayList childrenList=dao.getAllLevelChildren(movedElementList[i]);
			dao.updateElementLevel(childrenList,levelDiff);
			success=true;
		}
		return success;
	}
	/**
	 * @param oldPath
	 * @param newPath
	 */
	public boolean moveElementsInFS(String[] movedElementList, String oldPath, String newPath) throws LMSException{
		ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
		//Physical movement of the element
		String path=bundle.getString("folder.path");	
		//FileCopy copy=new FileCopy();
		boolean success=false;		
		for(int i=0;i<movedElementList.length;i++){
			File oldFile=new File(path+oldPath+"/"+movedElementList[i]);
			File newFile=new File(path+newPath+"/"+movedElementList[i]);
			
			newFile.mkdirs();
			logger.info("Old: "+oldFile+" New: "+newFile);
		//	success=oldFile.renameTo(newFile);
			
		//Deleting the document form the old location	
						//success=oldFile.delete();
						success=true;
			if(success==false){
				logger.error("Unable to Move Element: "+oldFile+ " To: "+newFile);	
			}
			
		}		
		return success;			
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#getChildrenIds(java.lang.String)
	 */
	public String[] getChildrenIds(String elementId) throws LMSException {
		KmElementMstrDao dao = new KmElementMstrDaoImpl();
		return dao.getChildrenIds(elementId);
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#getChildrenIds(java.lang.String)
	 */
	public ArrayList getChildrenIds(String elementId,String elementLevelId) throws LMSException {
		KmElementMstrDao dao = new KmElementMstrDaoImpl();
		return dao.getChildrenIds(elementId,elementLevelId);
	}

	

	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#editElement(com.ibm.km.dto.KmElementMstr)
	 */
	public void editElement(KmElementMstr dto) throws LMSException {
		KmElementMstrDao dao = new KmElementMstrDaoImpl();
		dao.editElement(dto);
		
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#getElementDto(java.lang.String)
	 */
	public KmElementMstr getElementDto(String elementId) throws LMSException {
		KmElementMstrDao dao = new KmElementMstrDaoImpl();
		return dao.getElemetDto (elementId);
		
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#deleteElement(java.lang.String)
	 */
	public void deleteElement(String elementId,String updatedBy) throws LMSException {
		KmElementMstrService service = new KmElementMstrServiceImpl();
		KmDocumentService documentService = new KmDocumentServiceImpl();
		
		//children elements from the elementId to the leaf 
		
		String[] elementIds=service.getElements(elementId);
		
		
		String[] allElements=service.getChildrenIds(elementId);
		String[] allElementsFromRoot=new String[allElements.length];
		for(int j=(allElements.length-1),i=0;j>=0;j--,i++){
			allElementsFromRoot[j]=allElements[i];
		}
		
		//children elements from the leaf to the elementId
		
		String[] elements=new String[elementIds.length];
		for(int j=(elementIds.length-1),i=0;j>=0;j--,i++){
			elements[j]=elementIds[i];
		}
		for(int i=0;i<elementIds.length;i++){
			
			String[] documents=documentService.getDocuments(elements[i]);
			logger.info(elementIds[i]+"   :   "+elements[i]+" No.Of documents : "+documents.length);
			if(documents.length>0){
			
				documentService.deleteDocuments(documents,updatedBy);
			}
			logger.info(documents.length+" documents deleted under "+elements[i]);
		}
		service.deleteElements(allElementsFromRoot,updatedBy);
		
		
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#getElements(java.lang.String)
	 */
	public String[] getElements(String elementId) throws LMSException {
		KmElementMstrDao dao = new KmElementMstrDaoImpl();
		return dao.getElements(elementId);
	}
	public String[] getElements(String[] elementId) throws LMSException {
			KmElementMstrDao dao = new KmElementMstrDaoImpl();
			return dao.getElements(elementId);
		}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#deleteElements(java.lang.String[])
	 */
	public void deleteElements(String[] elements,String updatedBy) throws LMSException {
		KmElementMstrDao dao = new KmElementMstrDaoImpl();
		dao.deleteElements(elements,updatedBy);
		
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#getElementId(java.lang.String)
	 */
	public String getElementId(String documentId) throws LMSException {
		KmElementMstrDao dao = new KmElementMstrDaoImpl();
		return dao.getElementId(documentId);
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#getCircleId(java.lang.String)
	 */
	public String getCircleId(String elementId) throws LMSException {
		KmElementMstrDao dao = new KmElementMstrDaoImpl();
		return dao.getCircleId(elementId);
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#getDocs(java.lang.String[])
	 */
	public String[] getDocs(String[] movedElementList) throws LMSException{
		KmElementMstrDao dao = new KmElementMstrDaoImpl();
		return dao.getDocs(movedElementList);
	}

	/* (non-Javadoc)
	 * @see com.ibm.km.services.KmElementMstrService#extractCircleId(java.lang.String)
	 */
	
	public String extractCircleId(String path, int level) throws LMSException {
		
			String circleId = null;
			StringTokenizer token= new StringTokenizer(path,"/");
			int i=0;
		try{	
			while(token.hasMoreTokens()){
				i++;
				String circle=token.nextToken();
				
				if(i==level){
					circleId=circle;
					break;
				}
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return circleId;
			
			
	}
		
		
	


	public ArrayList getAllChildrenRec(String parentId, String elementLevel) throws LMSException {
		KmElementMstrDao dao =  new KmElementMstrDaoImpl();
		return dao.getAllChildrenRec(parentId,elementLevel);
		
	}

	public HashMap retrieveCategoryMap(String circleId, String favCategoryId)throws LMSException{
		HashMap categoryMap=null;
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		categoryMap=dao.getCategoryMapElements(circleId,favCategoryId);
		
		return categoryMap;
		
	}

	public ArrayList getChildren(String parentId, String elementLevelId) throws LMSException {
		ArrayList elementList=new ArrayList();
 		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		elementList=dao.getChildren(parentId,elementLevelId);
 		return elementList;
	}

	public ArrayList getAllChildrenNoPan(String parentId, String elementLevelId) throws LMSException {
		ArrayList elementList=new ArrayList();
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		elementList=dao.getAllChildrenNoPan(parentId,elementLevelId);
		return elementList;
	}

	public JSONObject getElementsAsJsonNoPan(String parentId) throws Exception {
		JSONObject json=new JSONObject();
		JSONArray jsonItems=new JSONArray();
		String level=null;
		List list = getChildren(parentId);
		for (Iterator iter=list.iterator(); iter.hasNext();) {
			KmElementMstr element=(KmElementMstr)iter.next();
			jsonItems.put(element.toJSONObject());
			level=element.getElementLevel();
		}
		json.put("elements", jsonItems);
		json.put("level", level);
		return json;
	}

	public boolean getCircleStatus(String circleId) throws LMSException{
		KmElementMstrDao dao = new KmElementMstrDaoImpl();
		return dao.getCircleStatus(circleId);
		
	}

	public HashMap retrieveSubCategoryMap(String elementId,String parentLevel, String childLevel) throws LMSException {
		HashMap categoryMap=null;
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		categoryMap=dao.getSubCategoryMapElements(elementId,Integer.parseInt(parentLevel),Integer.parseInt(childLevel));
		
		return categoryMap;
	}

	public KmElementMstr getCompleteElementDetails(String elementId) throws LMSException {
		KmElementMstr element = new KmElementMstr();
		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		try{
			element= dao.getCompleteElementDetails(elementId);
		}
		catch (DAOException e) {
			// TODO: handle exception
		}
		return element;
	}

	
	public ArrayList<Integer> getAllElementsAsPerLevel(int levelId)
			throws LMSException {
		ArrayList<Integer> elementList = new ArrayList();
 		KmElementMstrDao dao=new KmElementMstrDaoImpl();
		elementList=dao.getAllElementsAsPerLevel(levelId);
 		return elementList;
	}

	public Map<String, String> getElementPathList(String initialSelectedPath, String[] multipleCircles) throws LMSException {

	Map<String, String>  elementPathMap = new HashMap<String,String>();
	KmElementMstrService kmElementMstrService = new KmElementMstrServiceImpl();
	ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
  	
	
	if(multipleCircles != null)
	{
		String parentId,elementFolderPath = "";
		String[] elementIds = initialSelectedPath.split("/");
		int elementLevel = elementIds.length;
		String[] elementNames = new String[elementLevel - 3];
		boolean isValid;
		for(int x = 0 ; x < (elementLevel - 3) ; x++ )
		{
			elementNames[x]= kmElementMstrService.getElementName(elementIds[x+3]);	
		}
			
		
		for(int i =0 ; i< multipleCircles.length ; i++)
		{
			parentId = multipleCircles[i];
			elementFolderPath = elementIds[0]+"/" + elementIds[1] + "/" + parentId + "/";
			isValid = false;	
			for(int j = 0 ; j < (elementLevel - 3) ; j++)	
			{
				String[] elementStatus =
					kmElementMstrService.checkExistingElement(elementNames[j],parentId);
				
				if(elementStatus[0].equals("true"))
				{
					parentId = elementStatus[1];
					elementFolderPath = elementFolderPath + parentId + "/";
					
					isValid = true;
				}
				else
				{	
					isValid = false;
					break;
				}	
					
			}//Checking Path for 1 Circle at a time
			
			if (isValid) 
			  {
				String path=elementFolderPath;
				elementPathMap.put( multipleCircles[i], path);				
			  } 
			else
			 {
				elementPathMap.put( multipleCircles[i], null);
			 }
		}//Bulk Creation for Multiple Circles	
	}//Should Be Executed if Multiple Circles are Selected
  return elementPathMap;	
 }


	public String copyElement(String elementId, String copyToParentElementId,String userId) throws LMSException {
		//logger.info("Copying Element: " + elementId + " to new element: " + copyToParentElementId );

		KmElementMstrDao dao = new KmElementMstrDaoImpl();
		ArrayList arr = dao.getAllChildrenElements(elementId);
	    String newElementId = "";
	    KmDocumentService kmDocumentService = new KmDocumentServiceImpl();
	    AddFileService addFileService =null;// new AddFileServiceImpl();
	   // FileCopy fc = new FileCopy();
	
	    ResourceBundle bundle = ResourceBundle.getBundle("ApplicationResources");
		String initialDirectoryPath = bundle.getString("folder.path");		
		
		try{
	    KmElementMstr element = getElementDetails(Integer.parseInt(elementId));
	    element.setCreatedBy(userId);
	    element.setUpdatedBy(userId);
	    
	    element.setParentId(copyToParentElementId);
	    newElementId = createElement(element);
	    String temp = getAllParentIdString("1",newElementId) ;
	    int jj = 1;
	    for(; temp.contains("/") ; jj++) {
	    	temp = temp.substring(0, temp.lastIndexOf("/") - 1);
	    }
	    dao.updateLevel(Integer.parseInt(newElementId) , jj);
	    // create new directory if element level is != 0
    	String elementFolderPath = initialDirectoryPath + getAllParentIdString("1",newElementId) ;
		File f = new File(elementFolderPath);
		f.mkdirs();  
		
		if(arr != null && arr.size() > 0) {
			for(int i = 0; i < arr.size(); i++) { // SET Document Id's in the Element dto
		        KmElementMstr dto = (KmElementMstr) arr.get(i);
		        String childElementId = dto.getElementId();
		        String level = dto.getElementLevel();
		        
		        if(level.equals("0") ) { // Document element
				        // read document from db to be copied to new location
				        KmDocumentMstr oldDocument = kmDocumentService.getDocumentByElementId(childElementId);
				        String docName = oldDocument.getDocumentName();
				        String oldDocumentPath = initialDirectoryPath + getAllParentIdString("1",childElementId) ;
				        oldDocumentPath = oldDocumentPath.substring(0,oldDocumentPath.lastIndexOf("/"));
				        
				        logger.info("\ndocName: "+docName + " for Element:"+childElementId);
				        
				        oldDocumentPath = oldDocumentPath + "/" + docName;
				        logger.info("\noldDocumentPath "+oldDocumentPath);
				        
		        		// create new entry in Element Mstr table
		        		KmElementMstr childElement = getElementDetails(Integer.parseInt(childElementId));
		        		childElement.setCreatedBy(userId);
		        		childElement.setUpdatedBy(userId);
		        		
				        childElement.setParentId(newElementId);
				        String newChildElementId = createElement(childElement);
				        oldDocument.setDocumentId(null);
				        oldDocument.setElementId(newChildElementId); // set new Element id
				        String newDocPath = getAllParentIdString("1", newChildElementId);
				        newDocPath = newDocPath.substring(0,newDocPath.lastIndexOf("/"));

				        oldDocument.setDocumentPath(newDocPath);
				     
				        oldDocument.setPublishingStartDate((oldDocument.getPublishingStartDt()+"").substring(0,10));
				        oldDocument.setPublishingEndDate((oldDocument.getPublishingEndDt()+"").substring(0,10));
				        
				        String newDocumentPath = initialDirectoryPath + newDocPath + "/" + docName ;
				        String newDocumentId = addFileService.saveFileInfoInDB(oldDocument);
				        
				       // fc.copy(oldDocumentPath, newDocumentPath);
				        
				        String circleId = "1";
				        String [] elems = newDocPath.split("/");
				        if(elems != null && elems.length > 2)
				        circleId = elems[2];

				    	//Code to update the Index for the above document in Pending state
						/*IndexFiles indexObject = new IndexFiles();				
						
						if(PropertyReader.getAppValue("SINGLE.INDEX").equals("Y")){	
							
							indexObject.initIndex(new File(newDocumentPath), newDocumentId,circleId);
						}	
						else{
							
							indexObject.initIndexNew(new File(newDocumentPath), newDocumentId,circleId);
						}*/
						
				        // perform indexing on newDocumentId
				        
		        } else
		        {
		        	copyElement(childElementId, newElementId,userId);
		        }
		        
			}
		} 
		else		{
			//logger.info("No child for element: " + elementId + " found" );
		}
		
		}
	/* catch (IOException e) {
	    	e.printStackTrace();
	    	throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e); 
    	}*/
		catch(Exception e)
		{
			e.printStackTrace();
	    	throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e); 
	    }
		return newElementId;
	}


	public KmElementMstr getElementDetails(int elementId) throws LMSException {
		
		KmElementMstrDao dao = new KmElementMstrDaoImpl();
		return dao.getElementDetails(elementId);
	}

	public HashMap<String, String> getAllCircleDesc() throws LMSException {
		KmElementMstrDao dao = new KmElementMstrDaoImpl();
		return dao.getAllCircleDesc();
	}
	
}
