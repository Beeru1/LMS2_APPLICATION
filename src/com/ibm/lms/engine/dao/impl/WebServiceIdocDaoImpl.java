package com.ibm.lms.engine.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ibm.lms.common.ColumnKeys;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.dto.webservice.IDOCDataStatusDTO;
import com.ibm.lms.dto.webservice.RetrieveIDOCLeadDataDTO;
import com.ibm.lms.engine.dao.WebServiceIdocDao;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.services.MasterService;
import com.ibm.lms.services.impl.MasterServiceImpl;

public class WebServiceIdocDaoImpl implements WebServiceIdocDao{

	private WebServiceIdocDaoImpl(){}
	private static WebServiceIdocDaoImpl webServiceIdocDaoImpl  = null;
	
	public static WebServiceIdocDaoImpl getServiceIdocDaoInstance()
	{
		if(webServiceIdocDaoImpl==null)
		{
			webServiceIdocDaoImpl = new WebServiceIdocDaoImpl();
		}
		return webServiceIdocDaoImpl;
		
	}
	Logger logger = Logger.getLogger(WebServiceIdocDaoImpl.class);
	private static String GET_IDOC_DATA="select ild.LEAD_ID,ALTERNATE_NUMBER,upper(IDOC_STEP) AS IDOC_STEP,upper(IDOC_STATUS) AS IDOC_STATUS,STATUS_DATE,NUMBER_ACTIVATED,ldd.LEAD_CAPTURED_DATA_ID,(SELECT upper(PRODUCT_NAME) AS PRODUCT_NAME FROM PRODUCT_MSTR WHERE PRODUCT_ID=ld.PRODUCT_ID) AS PRODUCT_NAME,(SELECT upper(SOURCE_NAME) AS SOURCE_NAME FROM SOURCE_MSTR WHERE SOURCE_ID=ld.SOURCE) AS SOURCE_NAME from IDOC_LEAD_DATA ild,LEAD_DATA ld,LEAD_DETAILS ldd where ild.lead_id=ld.LEAD_ID and ld.LEAD_ID= ldd.LEAD_ID and ild.LEAD_ID=? order by PROCESS_DATE desc fetch first row only ";
	private static String GET_IDOC_DATA_TRANS_ID = "select ild.LEAD_ID,ALTERNATE_NUMBER,IDOC_STEP,IDOC_STATUS,STATUS_DATE,NUMBER_ACTIVATED,ldd.LEAD_CAPTURED_DATA_ID,(SELECT PRODUCT_NAME FROM PRODUCT_MSTR WHERE PRODUCT_ID=ld.PRODUCT_ID) AS PRODUCT_NAME,(SELECT SOURCE_NAME FROM SOURCE_NAME WHERE SOURCE_ID=ld.SOURCE) AS SOURCE_NAME from IDOC_LEAD_DATA ild,lead_data ld,Lead_details ldd where ild.lead_id=ld.LEAD_ID and ld.LEAD_ID=ldd.LEAD_ID and ldd.LEAD_CAPTURED_DATA_ID=? order by PROCESS_DATE desc fetch first row only";
	private static String GET_IDOC_DATA_MOBILENO = "select ild.LEAD_ID,ild.ALTERNATE_NUMBER,upper(ild.IDOC_STEP) AS IDOC_STEP,upper(ild.IDOC_STATUS) AS IDOC_STATUS,ild.STATUS_DATE,ild.NUMBER_ACTIVATED,ldd.LEAD_CAPTURED_DATA_ID,(SELECT upper(PRODUCT_NAME) AS PRODUCT_NAME FROM PRODUCT_MSTR WHERE PRODUCT_ID=ld.PRODUCT_ID) AS PRODUCT_NAME,(SELECT upper(SOURCE_NAME) AS SOURCE_NAME FROM SOURCE_MSTR WHERE SOURCE_ID=ld.SOURCE) AS SOURCE_NAME from IDOC_LEAD_DATA ild,lead_data ld,LEAD_DETAILS ldd  where ild.lead_id=ld.LEAD_ID and ld.LEAD_ID=ldd.LEAD_ID  and ld.SOURCE in (select SOURCE_ID from SOURCE_MSTR where upper(SOURCE_NAME)=upper(?))  and ld.PRODUCT_ID in (select PRODUCT_ID from PRODUCT_SYNONYM where upper(PRODUCT_SYNONYM_NAME)=upper(?) and STATUS='A' fetch first row only) and (ild.ALTERNATE_NUMBER=? or NUMBER_ACTIVATED=?) ";
	private final static String IS_VALID_MOBILENO = "SELECT LEAD_ID FROM IDOC_LEAD_DATA WHERE ALTERNATE_NUMBER=? OR NUMBER_ACTIVATED=?";
	private final static String IS_VALID_PRODUCT ="select distinct PRODUCT_ID from PRODUCT_SYNONYM where PRODUCT_SYNONYM_NAME=? and STATUS='A'";
	//private static String GET_IDOC_DATA ="select ild.LEAD_ID,ild.ALTERNATE_NUMBER,ild.IDOC_STEP,ild.IDOC_STATUS,ild.STATUS_DATE,ild.NUMBER_ACTIVATED,ldd.LEAD_CAPTURED_DATA_ID from IDOC_LEAD_DATA ild,lead_data ld,LEAD_DETAILS ldd where ild.lead_id=ld.LEAD_ID and ld.LEAD_ID= ldd.LEAD_ID ";
	private final static String IS_VALID_LEADID = "SELECT LD.LEAD_ID FROM LEAD_DATA LD ,LEAD_DETAILS LDT,IDOC_LEAD_DATA ILD WHERE LD.LEAD_ID=LDT.LEAD_ID AND LD.LEAD_ID=ILD.LEAD_ID AND ILD.LEAD_ID=? WITH UR";
	private final static String IS_VALID_TXNID = "SELECT LD.LEAD_ID FROM LEAD_DATA LD ,LEAD_DETAILS LDT,IDOC_LEAD_DATA ILD  WHERE LD.LEAD_ID=LDT.LEAD_ID AND ILD.LEAD_ID=LD.LEAD_ID AND  LDT.LEAD_CAPTURED_DATA_ID=? WITH UR";
	private static final String SQL_SELECT_IDOC_DATACOUNT_LOGS = "SELECT TOTALRECEIVE, PROCESSED, REJECT, ACCEPT FROM IDOC_DATACOUNT_LOGS where TRANSACTION_DATE= current date WITH UR";
	private static final String SQL_INSERT_IDOC_DATACOUNT_LOGS = "INSERT INTO IDOC_DATACOUNT_LOGS(TOTALRECEIVE, PROCESSED, REJECT, ACCEPT, TRANSACTION_DATE) VALUES(?,?,?,?,current date)";
	private static final String GET_IDOC_DATA_MOB_SRC="select ild.LEAD_ID,ild.ALTERNATE_NUMBER,upper(ild.IDOC_STEP) AS IDOC_STEP,upper(ild.IDOC_STATUS) AS IDOC_STATUS,ild.STATUS_DATE,ild.NUMBER_ACTIVATED,ldd.LEAD_CAPTURED_DATA_ID,(SELECT upper(PRODUCT_NAME) AS PRODUCT_NAME FROM PRODUCT_MSTR WHERE PRODUCT_ID=ld.PRODUCT_ID) AS PRODUCT_NAME,(SELECT upper(SOURCE_NAME) AS SOURCE_NAME FROM SOURCE_MSTR WHERE SOURCE_ID=ld.SOURCE) AS SOURCE_NAME from IDOC_LEAD_DATA ild,lead_data ld,LEAD_DETAILS ldd  where ild.lead_id=ld.LEAD_ID and ld.LEAD_ID=ldd.LEAD_ID  and ld.SOURCE in (select SOURCE_ID from SOURCE_MSTR where upper(SOURCE_NAME)=upper(?)) and (ild.ALTERNATE_NUMBER=? or NUMBER_ACTIVATED=?) ";

	public String IDOCDataStatus(IDOCDataStatusDTO [] dataStatusDTO) throws Exception{
		
		// if not null  A- total size
		int total_count = dataStatusDTO.length;
		int reject_count =0;
		int parameterIndex;
		List<IDOCDataStatusDTO> idocdatastatusfilteredlist= new ArrayList<IDOCDataStatusDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
			
			idocdatastatusfilteredlist = FilterIDOCdataStatus(dataStatusDTO);
			int process_count = idocdatastatusfilteredlist.size();
			reject_count = total_count-process_count;
			
			 conn = DBConnection.getDBConnection();
			IDOCLogDataStatus(total_count,process_count,reject_count,0,conn);
			
			int size  = idocdatastatusfilteredlist.size();
			logger.info(reject_count+"reject_count===total_count"+total_count+"process_count=="+process_count+"==================="+idocdatastatusfilteredlist);
			if(size > 0) {
			pstmt = conn.prepareStatement("insert into IDOC_TEMP_DATA(ALTERNATE_CONTACT_NO, PROSPECT_MOBILE_NO, SOURCE, ONLINECAFNO, STATUS_DATE, STATUS, IDOC_STEP, REJECT_REASON ,DATA_PROCESS_STATUS,TRANSACTION_DATE) " +" values(?,?,?,?,?,?,?,?,?,current date)");
			for(int i=0;i<size;i++){
			parameterIndex=1;
			pstmt.setString(parameterIndex++,dataStatusDTO[i].getAlternateContactNumber() );
			pstmt.setString(parameterIndex++, dataStatusDTO[i].getProspectsMobileNumber());
			pstmt.setString(parameterIndex++, dataStatusDTO[i].getSource());
			pstmt.setString(parameterIndex++, dataStatusDTO[i].getOnlineCafNo());
			pstmt.setString(parameterIndex++, dataStatusDTO[i].getStatus_date());
			pstmt.setString(parameterIndex++, dataStatusDTO[i].getStatus());
			pstmt.setString(parameterIndex++, dataStatusDTO[i].getIdocStep());
			pstmt.setString(parameterIndex++, dataStatusDTO[i].getRejectReason());
			pstmt.setString(parameterIndex++, "1");
			pstmt.addBatch();
			}
			pstmt.execute();
			}
		}
		catch(Exception sqle){
			conn.rollback();
			sqle.printStackTrace();
			return "Fail";
		}
		finally
		{
			try
			{
				DBConnection.releaseResources(conn,pstmt,null);
			}
			catch(DAOException e)
			{
						throw new LMSException(e.getMessage(),e);
			}
		}	
		
		return "Success";
		
	}
	

	private List<IDOCDataStatusDTO> FilterIDOCdataStatus(IDOCDataStatusDTO[] dataStatusDTO) throws Exception {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs1=null;
		Set<String> idocdatastatusfilteredlist= new HashSet<String>();
		List<IDOCDataStatusDTO> filterdataStatusDTOs = new ArrayList<IDOCDataStatusDTO>();
		try{
			con = DBConnection.getDBConnection();
			ps =con.prepareStatement("select status from IDOC_LMS_MASTER_DATA where STATUS_TYPE='IDOC' WITH UR");
			rs1=ps.executeQuery();
			while(rs1.next()){
				idocdatastatusfilteredlist.add(rs1.getString("STATUS").toUpperCase());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			try
			{
				DBConnection.releaseResources(con,ps,rs1);
			}
			catch(DAOException e)
			{
						throw new LMSException(e.getMessage(),e);
			}
		}	
		
		for(int i=0;i<dataStatusDTO.length;i++){
			if(idocdatastatusfilteredlist !=null && idocdatastatusfilteredlist.contains(dataStatusDTO[i].getIdocStep().toUpperCase()))
			{
				filterdataStatusDTOs.add(dataStatusDTO[i]);
			}
		}
		
		
		return filterdataStatusDTOs;
	}
	
	private void IDOCLogDataStatus(int total,int processed,int rejected ,int accepted,Connection conn)throws Exception{
		// TODO Auto-generated method stub
		//Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		
		
		try {
			//conn = DBConnection.getDBConnection();
			//selecting based on current date
			ps = conn.prepareStatement(SQL_SELECT_IDOC_DATACOUNT_LOGS);
			logger.info(SQL_SELECT_IDOC_DATACOUNT_LOGS);
			rs = ps.executeQuery();
			if(!rs.next()){
				//insert query
				ps1=conn.prepareStatement(SQL_INSERT_IDOC_DATACOUNT_LOGS);
				ps1.setLong(1, total);
				ps1.setLong(2, processed);
				ps1.setLong(3, rejected);
				ps1.setLong(4, accepted);
				logger.info("ps1"+ps1.toString());
				ps1.executeUpdate();
				logger.info("executed suceess");
			}else {
				StringBuffer buffer =  new StringBuffer("UPDATE IDOC_DATACOUNT_LOGS SET");
				buffer.append(" TOTALRECEIVE = TOTALRECEIVE+"+total);
				buffer.append(",PROCESSED = PROCESSED+"+processed);
				buffer.append(",ACCEPT = ACCEPT+"+accepted);
				buffer.append(",REJECT = REJECT+"+rejected);
				buffer.append(" WHERE TRANSACTION_DATE= current date");
				logger.info(buffer);
				ps2=conn.prepareStatement(buffer.toString());
				ps2.executeUpdate();
			}
				
		}
		catch (Exception e) {
			// TODO: handle exception
			logger.info("Caught exception during db operation->>>>>"+e);
		}
	}


	@Override
	public RetrieveIDOCLeadDataDTO[] getIDOCDataList(String leadId,
			String mobileNo, String product, String source,
			String transactionId) throws Exception {
		

		//logger.info("Control in daoimpl------");
		logger.info("Inside the RetrieveIDOCWebServiceDaoImpl");

		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RetrieveIDOCLeadDataDTO[] idocDtos = null;
		List<RetrieveIDOCLeadDataDTO>  RetrieveIDOCLeadDataDTOs =null;
		Long lead=null;
		Pattern pattern = Pattern.compile(".*[^0-9].*");
		Boolean mobileflag = false;
		MasterService masterService = new MasterServiceImpl();
		try{
			
			conn = DBConnection.getDBConnection();
			StringBuffer query = new StringBuffer(GET_IDOC_DATA);
			if(transactionId !=null && transactionId.contains("-")) {
				String tempTrxId [] = transactionId.split("-");
				String  Trxn = tempTrxId[1];
				transactionId = Trxn.trim(); 
				 
				}
			lead  =isValidLeadId(leadId, conn, transactionId);
			/*if(lead==null && transactionId!=null && transactionId.trim().length()>0 && !pattern.matcher(transactionId).matches())
			{
				//logger.info("Control in transactionid");
				//query.append("and ldd.LEAD_CAPTURED_DATA_ID=? ");
				pstmt = conn.prepareStatement(GET_IDOC_DATA_TRANS_ID);
				pstmt.setLong(1, Long.parseLong(transactionId));
				rs = pstmt.executeQuery();
				if(rs!=null)
					RetrieveIDOCLeadDataDTOs= resultSetData(rs);
			}
			lead = isValidLeadId(leadId, conn, transactionId);*/
			if(lead !=null && lead>0)
			{
				//logger.info("control in leadid");
				//query.append("and ild.LEAD_ID=? order by STATUS_DATE desc fetch first row only");
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setLong(1, lead);
				rs = pstmt.executeQuery();
				if(rs!=null )
					RetrieveIDOCLeadDataDTOs= resultSetData(rs);
				
			}
			 
			else if(mobileNo!=null && product!=null && source!=null && mobileNo.trim().length()>0 && product.trim().length()>0 && source.trim().length()>0)
			{
			    mobileflag=isValidMobileno(mobileNo,conn,product);
			    //logger.info("Control here ------");
				if(mobileflag){
				String dataRows=masterService.getParameterName("IDOC_MOBILE_DATAROWS");
				if(dataRows==null || dataRows.trim().length()<=0)
				{
					dataRows="1";
				}
				StringBuffer mobileNoQuery = new StringBuffer(GET_IDOC_DATA_MOBILENO); 
				mobileNoQuery.append("order by PROCESS_DATE desc fetch first "+dataRows+" row only");
				//logger.info(GET_IDOC_DATA_MOBILENO);
				//if(!pattern.matcher(mobileNo).matches() && !pattern.matcher(product).matches() && !pattern.matcher(source).matches()){
				//logger.info("control in mobileno");
				//query.append("and ld.SOURCE in (select SOURCE_ID from SOURCE_MSTR where upper(SOURCE_NAME)=upper(?)) and ld.PRODUCT_ID in (select PRODUCT_ID from PRODUCT_MSTR where upper(PRODUCT_NAME)=upper(?)) and ild.ALTERNATE_NUMBER=? order by STATUS_DATE desc fetch first row only");
				pstmt = conn.prepareStatement(mobileNoQuery.toString());
				
				pstmt.setString(1,source);
				pstmt.setString(2,product);
				pstmt.setString(3,mobileNo);
				pstmt.setString(4,mobileNo);
				rs = pstmt.executeQuery();
				if(rs!=null)
					RetrieveIDOCLeadDataDTOs= resultSetData(rs);
				//}
				}
				
			}else if(mobileNo!=null && source!=null && mobileNo.length()>0 && source.length()>0 && (null == product || product.trim().equalsIgnoreCase(""))){
				
				 mobileflag=isValidMobileno(mobileNo,conn,product);
				    //logger.info("Control here ------");
					if(mobileflag){
					String dataRows=masterService.getParameterName("IDOC_MOBILE_DATAROWS");
					if(dataRows==null || dataRows.trim().length()<=0)
					{
						dataRows="1";
					}
					StringBuffer mobileNoQuery = new StringBuffer(GET_IDOC_DATA_MOB_SRC); 
					mobileNoQuery.append("order by PROCESS_DATE desc fetch first "+dataRows+" row only");
					//logger.info(GET_IDOC_DATA_MOBILENO);
					//if(!pattern.matcher(mobileNo).matches() && !pattern.matcher(product).matches() && !pattern.matcher(source).matches()){
					//logger.info("control in mobileno");
					//query.append("and ld.SOURCE in (select SOURCE_ID from SOURCE_MSTR where upper(SOURCE_NAME)=upper(?)) and ld.PRODUCT_ID in (select PRODUCT_ID from PRODUCT_MSTR where upper(PRODUCT_NAME)=upper(?)) and ild.ALTERNATE_NUMBER=? order by STATUS_DATE desc fetch first row only");
					pstmt = conn.prepareStatement(mobileNoQuery.toString());
					
					pstmt.setString(1,source);
					//pstmt.setString(2,product);
					pstmt.setString(2,mobileNo);
					pstmt.setString(3,mobileNo);
					rs = pstmt.executeQuery();
					if(rs!=null)
						RetrieveIDOCLeadDataDTOs= resultSetData(rs);
					//}
					}
					
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Error inside the getIDOCDataList method", e);
		}
		finally 
		{
			try 
			{
				DBConnection.releaseResources(conn, pstmt, rs);
			} 
			catch (Exception e) 
			{		
				e.printStackTrace();
				//throw new DAOException(e.getMessage(), e);
				logger.error("Error inside the getIDOCDataList method for release resource", e);
			}
		}
		if(RetrieveIDOCLeadDataDTOs != null && RetrieveIDOCLeadDataDTOs.size() >0)
		idocDtos = RetrieveIDOCLeadDataDTOs.toArray(new RetrieveIDOCLeadDataDTO [RetrieveIDOCLeadDataDTOs.size()]); 
	return idocDtos;
	}
	private List<RetrieveIDOCLeadDataDTO> resultSetData(ResultSet rs) {
		RetrieveIDOCLeadDataDTO dto =null;
		List<RetrieveIDOCLeadDataDTO> leadList=null;
		
		try {
			logger.info("Inside ResultsetData method");
			leadList=new ArrayList<RetrieveIDOCLeadDataDTO>();
			while(rs.next())
			{				
				dto=new RetrieveIDOCLeadDataDTO();
				dto.setLeadId(String.valueOf(rs.getLong(ColumnKeys.LEAD)));
				//logger.info("truu"+rs.getLong("LEAD_ID"));
				dto.setTransactionId(String.valueOf(rs.getLong(ColumnKeys.TXN_ID)));
				dto.setProspectMobileNo(rs.getString(ColumnKeys.ALTERNATE_NUMBER));
				//dto.setSource(rs.getString("SOURCE"));
				dto.setIdocVerificationName(rs.getString(ColumnKeys.IDOC_STEP));
				dto.setIdocStatus(rs.getString(ColumnKeys.IDOC_STATUS));
				dto.setStatusUpdateDt(rs.getString(ColumnKeys.STATUS_DATE));
				dto.setActivateNumber(rs.getString(ColumnKeys.NUMBER_ACTIVATED));
				dto.setProduct(rs.getString(ColumnKeys.PRODUCT_NAME));
				dto.setSource(rs.getString(ColumnKeys.SOURCE_NAME));
				
				
				leadList.add(dto);
		}
			
		} catch (Exception e) {
			logger.error("Error inside resultsetdata method", e);
			e.printStackTrace();
		}
		
		//if(leadList !=null && leadList.size() >0 )
		//dtos  = leadList.toArray(new RetrieveLeadDataDTO [leadList.size()]);
		return leadList; 
		
	}
	private Long isValidLeadId(String leadId , Connection con ,String trxnId)  throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Long lead_Id = null ;
		String [] tempTrxId =null;
		Pattern pattern = Pattern.compile(".*[^0-9].*");
		try 
		{
			if(leadId !=null && leadId.length() >0 && !pattern.matcher(leadId).matches())
			{
				
				ps = con.prepareStatement(IS_VALID_LEADID);
				ps.setLong(1, Long.parseLong(leadId.toString()));
				rs = ps.executeQuery();
				if(rs.next())
				{
					return rs.getLong(1);
				}
			}
		 if(trxnId !=null && trxnId.length() >0 && lead_Id ==null && !pattern.matcher(trxnId).matches())
			{
				//logger.info("transaction id block");
				if(trxnId.contains("-")) {
				 tempTrxId = trxnId.split("-");
				 trxnId = tempTrxId[1];
				}
				if(!pattern.matcher(trxnId.trim()).matches()) {
				ps = con.prepareStatement(IS_VALID_TXNID);
				//logger.info("transction"+trxnId);
				ps.setLong(1, Long.parseLong(trxnId));
				rs = ps.executeQuery();
				if(rs.next())
				{
					return rs.getLong(1);
				}
				}
			}
			
				
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("error"+e);
			logger.info("Error in side validleadid method"+e);
			//throw new DAOException("Exception occured while getting city details :  "+ e.getMessage(),e);
		}
		finally 
		{
			try 
			{
				DBConnection.releaseResources(null, ps, rs);
			} 
			catch (Exception e) 
			{				
				//throw new DAOException(e.getMessage(), e);
			}
		}
		
		return lead_Id;
	}
	private boolean isValidMobileno(String mobileno , Connection con ,String productid)  throws DAOException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Boolean returnFlag= false;
		Pattern pattern = Pattern.compile(".*[^0-9].*");
		try 
		{
			if(mobileno !=null && mobileno.length() >0 && !pattern.matcher(mobileno).matches())
			{
				
				ps = con.prepareStatement(IS_VALID_MOBILENO);
				ps.setString(1, mobileno);
				ps.setString(2, mobileno);
				rs = ps.executeQuery();
				if(rs.next())
				{
					returnFlag=true;
				}
			}
		 if(productid !=null && productid.length() >0 )
			{
				//logger.info("transaction id block");
				ps = con.prepareStatement(IS_VALID_PRODUCT);
				//logger.info("transction"+trxnId);
				ps.setString(1, productid);
				rs = ps.executeQuery();
				if(rs.next())
				{
					returnFlag=true;
				}
				}
			
				
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("error"+e);
			logger.info("Error in side validmobileno method"+e);
			//throw new DAOException("Exception occured while getting city details :  "+ e.getMessage(),e);
		}
		finally 
		{
			try 
			{
				DBConnection.releaseResources(null, ps, rs);
			} 
			catch (Exception e) 
			{				
				//throw new DAOException(e.getMessage(), e);
			}
		}
		
		return returnFlag;
	}
}
