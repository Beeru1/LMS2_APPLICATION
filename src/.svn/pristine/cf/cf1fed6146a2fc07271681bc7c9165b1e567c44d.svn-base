package com.ibm.lms.dao;

import java.util.ArrayList;

import com.ibm.lms.dto.BulkAssignmentDto;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.DAOException;

public interface BulkAssigmentDao {
	
	public String insertAssignment(ArrayList<BulkAssignmentDto> validListInsert,UserMstr userBean,String flag) throws DAOException;
	public String insertTempAssignment(ArrayList<BulkAssignmentDto> validListInsert,UserMstr userBean) throws DAOException;
	
	public String softDeleteAssignment(ArrayList<BulkAssignmentDto> list,UserMstr userBean) throws DAOException;
	
	public String rejectAssignmentMatrix(ArrayList<BulkAssignmentDto> list,UserMstr userBean,String flag) throws DAOException;
	public String rejectAssignmentMatrixL2(ArrayList<BulkAssignmentDto> list,UserMstr userBean,String flag) throws DAOException;
	
	//public boolean isAssignmentKeyAssignedAsPrimary(String assignmentKey,String status) throws DAOException;
	public ArrayList<BulkAssignmentDto> getAssignmentMatrixList(String olmID) throws DAOException;
	public String softDeleteAssignmentNew(ArrayList<BulkAssignmentDto> validListDelete, UserMstr userBean)throws DAOException;
	public boolean isAssignmentKeyExist(String olmId, String assignmentKey,int primaryAuth,String status, int level, String userType, String channelPartnerId,int flag)
	throws DAOException;
	
	public ArrayList<BulkAssignmentDto> insertintoMainTable(UserMstr userBean) throws DAOException;
	
	public boolean isAssignmentTempKeyExist(String olmId, String assignmentKey,int primaryAuth,String status, int level, String userType, String channelPartnerId,int flag)
	throws DAOException;
	
//	public String softDeleteAssignmentTempNew(ArrayList<BulkAssignmentDto> validListDelete, UserMstr userBean)throws DAOException;
	
	public ArrayList<BulkAssignmentDto> viewAssignmentMatrixStatus(UserMstr userBean)throws DAOException;	
	public BulkAssignmentDto logs(UserMstr userBean,String errLogFileName) throws DAOException;
	}
