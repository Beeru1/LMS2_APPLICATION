/*
 * Created on Jun 3, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dto;

import java.util.List;

/**
 * @author Atul
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DocumentTreeElement {
	private Integer elementId = null;
	private String elementName = null;
	private Integer parentId = null;
	private Integer elementLevelId = null;
	private Integer documentId=null;
	private Integer docType = null;
	private String documentViewUrl = null;
	private List childrenList = null;
	
	
	
	public Integer getDocType() {
		return docType;
	}

	public void setDocType(Integer docType) {
		this.docType = docType;
	}

	public String getDocumentViewUrl() {
		return documentViewUrl;
	}

	public void setDocumentViewUrl(String documentViewUrl) {
		this.documentViewUrl = documentViewUrl;
	}

	/**
	 * @return
	 */
	public Integer getElementId() {
		return elementId;
	}

	/**
	 * @return
	 */
	public String getElementName() {
		return elementName;
	}


	/**
	 * @return
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param integer
	 */
	public void setElementId(Integer integer) {
		elementId = integer;
	}

	/**
	 * @param string
	 */
	public void setElementName(String string) {
		elementName = string;
	}

	/**
	 * @param integer
	 */
	public void setParentId(Integer integer) {
		parentId = integer;
	}

	/**
	 * @return
	 */
	public Integer getElementLevelId() {
		return elementLevelId;
	}

	/**
	 * @param integer
	 */
	public void setElementLevelId(Integer integer) {
		elementLevelId = integer;
	}

	/**
	 * @return
	 */
	public List getChildrenList() {
		return childrenList;
	}

	/**
	 * @param list
	 */
	public void setChildrenList(List list) {
		childrenList = list;
	}

	/**
	 * @return
	 */
	public Integer getDocumentId() {
		return documentId;
	}

	/**
	 * @param integer
	 */
	public void setDocumentId(Integer integer) {
		documentId = integer;
	}

}
