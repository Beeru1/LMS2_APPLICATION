package com.ibm.lms.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;
import org.json.JSONObject;

import com.ibm.lms.dto.BulkAssignmentDto;
import com.ibm.lms.dto.BulkUploadMsgDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.wf.forms.AssignmentMatrixFormBean;

public interface BulkAssigmentService {
	
	public BulkUploadMsgDto uploadAssignmentMatrix(FormFile file,UserMstr userBean) throws LMSException ;
	public String CreateAssignmentMatrix(AssignmentMatrixFormBean form,UserMstr userBean) throws LMSException;
	public List<BulkAssignmentDto> getAssignmentMatrixList(String olmID) throws LMSException;
	public String deleteAssignmentMatrix(String[] ids,UserMstr userBean) throws LMSException;
	public String rejectAssignmentmatrix(String[] ids,UserMstr userBean,String flag, AssignmentMatrixFormBean commonForm) throws LMSException;
	public JSONObject getElementsAsJsonZoneFromZoneMaster(String circleMstrId) throws Exception;
	public ArrayList getAllChildrenZoneFromZoneMaster(String circleMstrId) throws Exception;
	public JSONObject getElementsAsJsonCity(String zoneCode) throws Exception;
	public ArrayList getAllChildrenCity(String zoneCode) throws Exception;
	public JSONObject getElementsAsJsonCityZone(String cityCode) throws Exception;
	public JSONObject getElementsAsJsonPincode(String cityZoneCode) throws Exception;
	public JSONObject getElementsAsJsonRsu(String cityZoneCode) throws Exception ;
	public JSONObject getElementsAsJsonZoneFromZoneMasterNew(String circleMstrId)throws Exception ;
	public JSONObject getElementsAsJsonCityNew(String circleMstrId)throws Exception ;
	public JSONObject getElementsAsJsonCityZoneNew(String circleMstrId)throws Exception ;
	public JSONObject getElementsAsJsonCityZone1(String zoneCode)throws Exception ;
	public List<BulkAssignmentDto> viewAssignmentMatrixStatus(UserMstr userBean) throws LMSException ;
	
	
	
}
