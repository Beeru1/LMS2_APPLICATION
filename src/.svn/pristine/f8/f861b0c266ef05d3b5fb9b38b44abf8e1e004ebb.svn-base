package com.ibm.lms.dao;

import java.util.ArrayList;

import com.ibm.lms.dto.BulkMstrDTO;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.DAOException;

public interface BulkMstrDao {
	
	public ArrayList<BulkMstrDTO> uploadZone(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException;
	public ArrayList<BulkMstrDTO> uploadCity(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException;
	public ArrayList<BulkMstrDTO> uploadCityZone(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException;
	public ArrayList<BulkMstrDTO> uploadPinCode(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException;
	public ArrayList<BulkMstrDTO> uploadRSUCode(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException;
	public ArrayList<BulkMstrDTO> uploadAutoAssignmentMatrix(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException;
	public ArrayList<BulkMstrDTO> uploadState(ArrayList<BulkMstrDTO> listBulkDto,UserMstr userBean) throws DAOException;
	
	public boolean isValidCircle(BulkMstrDTO bulkDto)throws DAOException;
	public boolean isDuplicateZone(BulkMstrDTO bulkDto)throws DAOException;
	public boolean isValidZoneCode(BulkMstrDTO bulkDto) throws DAOException ; //mstrType=2(City)
	public boolean isDuplicateCity(BulkMstrDTO bulkDto)throws DAOException;
	public boolean isDuplicateState(BulkMstrDTO bulkDto)throws DAOException;
	
	public boolean isDuplicatePinCode(BulkMstrDTO bulkDto)throws DAOException;
	public boolean isDuplicateRSUCode(BulkMstrDTO bulkDto)throws DAOException;
	public boolean isValidCityZoneCode(BulkMstrDTO bulkDto)throws DAOException;
	public boolean isDuplicateCityZone(BulkMstrDTO bulkDto)throws DAOException;
	public boolean isValidCityZoneCodeforPinCode(BulkMstrDTO bulkDto)throws DAOException;
	public ArrayList<BulkMstrDTO> uploadChannelPartner(ArrayList<BulkMstrDTO> listBulkDto, UserMstr userBean)throws DAOException;
	//added by Nancy Agrawal
	public ArrayList<BulkMstrDTO> uploadAgencyAssignmentMatrix(ArrayList<BulkMstrDTO> listBulkDto, UserMstr userBean)throws DAOException;
	public ArrayList<BulkMstrDTO> uploadWorkFlowAutoAssignmentMatrix(ArrayList<BulkMstrDTO> listBulkDto, UserMstr userBean)throws DAOException;
	
	//Added by srikant
	
	public ArrayList<BulkMstrDTO> uploadChannelCode(ArrayList<BulkMstrDTO> listBulkDto, UserMstr userBean)throws DAOException;
	public ArrayList<BulkMstrDTO> uploadChannelPartnerCapability(ArrayList<BulkMstrDTO> listBulkDto, UserMstr userBean)throws DAOException;
	public ArrayList<BulkMstrDTO> escalationUpload(ArrayList<BulkMstrDTO> listBulkDto, UserMstr userBean)throws DAOException;
	
	
	
}
