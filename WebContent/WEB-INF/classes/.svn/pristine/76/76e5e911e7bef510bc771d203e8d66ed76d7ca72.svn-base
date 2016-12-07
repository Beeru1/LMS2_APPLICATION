package com.ibm.km.forms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.ibm.lms.dto.LinkMstrDto;

public class KmLinkMstrFormBean extends ActionForm{
	
	private int linkId = 0;

	private String linkTitle = null;
	private String linkPath = null;
	private int linkId0 = 0;
	private String linkTitle0 = null;
	private String linkPath0 = null;
	private int linkId1 = 0;
	private String linkTitle1 = null;
	private String linkPath1 = null;
	private int linkId2 = 0;
	private String linkTitle2 = null;
	private String linkPath2 = null;
	private List linkList = new ArrayList();
	private String methodName=null;
	private String[] linkId4 = null;
	private String[] linkTitle4 = null;
	private String[] linkPath4 = null;
	List listKmLink = new ArrayList(); 
	private String elementId = "";
	private String elementName="";
	private String selectedCombo="";
	private ArrayList elementList = null;
	
	public LinkMstrDto getKmLinkMstrDto(int index) {
		//to check if employee object exists at specified index  
		while (this.linkList.size() <= index) {   
			linkList.add(new LinkMstrDto());  }  
		return ((LinkMstrDto) linkList.get(index));  
		}

	public String getSelectedCombo() {
		return selectedCombo;
	}
	public void setSelectedCombo(String selectedCombo) {
		this.selectedCombo = selectedCombo;
	}
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	public ArrayList getElementList() {
		return elementList;
	}
	public void setElementList(ArrayList elementList) {
		this.elementList = elementList;
	}
	public String getElementId() {
		return elementId;
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	public List getLinkList() {
		return linkList;
	}
	public void setLinkList(List linkList) {
		this.linkList = linkList;
	}
	public List getListKmLink() 
	{ 
	return listKmLink ;
	} 
	public void setListKmLink(List list) 
	{ listKmLink  = list; }
	
	public String[] getLinkId4() {
		return linkId4;
	}
	public void setLinkId4(String[] linkId4) {
		this.linkId4 = linkId4;
	}
	public String[] getLinkTitle4() {
		return linkTitle4;
	}
	public void setLinkTitle4(String[] linkTitle4) {
		this.linkTitle4 = linkTitle4;
	}
	public String[] getLinkPath4() {
		return linkPath4;
	}
	public void setLinkPath4(String[] linkPath4) {
		this.linkPath4 = linkPath4;
	}
	public int getLinkId() {
		return linkId;
	}
	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}
	public String getLinkTitle() {
		return linkTitle;
	}
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}
	public String getLinkPath() {
		return linkPath;
	}
	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public int getLinkId0() {
		return linkId0;
	}
	public void setLinkId0(int linkId0) {
		this.linkId0 = linkId0;
	}
	public String getLinkTitle0() {
		return linkTitle0;
	}
	public void setLinkTitle0(String linkTitle0) {
		this.linkTitle0 = linkTitle0;
	}
	public String getLinkPath0() {
		return linkPath0;
	}
	public void setLinkPath0(String linkPath0) {
		this.linkPath0 = linkPath0;
	}
	public int getLinkId1() {
		return linkId1;
	}
	public void setLinkId1(int linkId1) {
		this.linkId1 = linkId1;
	}
	public String getLinkTitle1() {
		return linkTitle1;
	}
	public void setLinkTitle1(String linkTitle1) {
		this.linkTitle1 = linkTitle1;
	}
	public String getLinkPath1() {
		return linkPath1;
	}
	public void setLinkPath1(String linkPath1) {
		this.linkPath1 = linkPath1;
	}
	public int getLinkId2() {
		return linkId2;
	}
	public void setLinkId2(int linkId2) {
		this.linkId2 = linkId2;
	}
	public String getLinkTitle2() {
		return linkTitle2;
	}
	public void setLinkTitle2(String linkTitle2) {
		this.linkTitle2 = linkTitle2;
	}
	public String getLinkPath2() {
		return linkPath2;
	}
	public void setLinkPath2(String linkPath2) {
		this.linkPath2 = linkPath2;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		this.linkTitle0 = null;
		this.linkPath0 = null;
		this.linkTitle1 = null;
		this.linkPath1 = null;
		this.linkTitle2 = null;
		this.linkPath2 = null;
		this.linkList = null;
	}


}
