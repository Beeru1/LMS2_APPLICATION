/*
 * Created on Dec 5, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.km.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.lms.common.Constants;
import com.ibm.km.dao.KmUserReportDao;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.common.PropertyReader;
import com.ibm.lms.dto.UserMstr;
import com.ibm.lms.exception.LMSException;

public class KmUserReportDaoImpl implements KmUserReportDao {
	
	public static Logger logger = Logger.getLogger(KmFileReportDaoImpl.class);
	
	protected static final String SQL_AGENT_WISE_REPORT1="WITH nee(element_id,element_level_id,status) AS ( SELECT  ELEMENT_ID,ELEMENT_LEVEL_ID,STATUS FROM LMS.KM_ELEMENT_MSTR WHERE element_id =?  UNION ALL 	SELECT nplus1.element_id,nplus1.element_level_id,nplus1.status  FROM LMS.KM_ELEMENT_MSTR as nplus1, nee  WHERE nee.element_id=nplus1.PARENT_ID) SELECT usr.USER_LOGIN_ID,usr.USER_FNAME,usr.USER_LNAME from LMS.KM_USER_MSTR usr inner join nee on usr.element_id = nee.element_id  where nee.status='A' and nee.element_level_id!=0 and usr.status = 'A' and usr.km_actor_id in(4,6)  and usr.user_login_status = 'Y' " ;
	protected static final String SQL_AGENT_WISE_REPORT2="SELECT usr.USER_LOGIN_ID,usr.USER_FNAME,usr.USER_LNAME from LMS.KM_USER_MSTR usr where usr.status ='A' AND ELEMENT_ID = ? and usr.km_actor_id in(4,6) and usr.user_login_status = 'Y'";
	protected static final String SQL_AGENT_WISE_REPORT3="SELECT usr.USER_LOGIN_ID,usr.USER_FNAME,usr.USER_LNAME from LMS.KM_USER_MSTR usr where usr.status = 'A' AND FAV_CATEGORY_ID = ?  and usr.km_actor_id in(4,6) and usr.user_login_status = 'Y' ";
	
		
	protected static final String SQL_CIRCLE_WISE_REPORT1="select  ele.element_name as CIRCLE_NAME,ele1.ELEMENT_NAME , count(distinct(USER_LOGIN_ID)) as LOGGEDIN_USER_COUNT from LMS.KM_login_data usr inner join LMS.KM_ELEMENT_MSTR ele  on usr.element_id=ele.element_id inner join LMS.KM_element_mstr ele1 on ele.PARENT_ID=ele1.ELEMENT_ID  where ele.ELEMENT_LEVEL_ID = 3 and date(login_time) = ?  group by ele1.ELEMENT_NAME,ele.ELEMENT_NAME";

	protected static final String SQL_CIRCLE_WISE_REPORT2="select  ele.element_name as CIRCLE_NAME,ele1.ELEMENT_NAME , count(distinct(USER_LOGIN_ID)) as LOGGEDIN_USER_COUNT from LMS.KM_login_data usr inner join LMS.KM_ELEMENT_MSTR ele on usr.element_id=ele.element_id inner join LMS.KM_element_mstr ele1 on ele.PARENT_ID=ele1.ELEMENT_ID  where ele.ELEMENT_LEVEL_ID = 3 and ele1.ELEMENT_ID= ? and date(login_time) = ? group by ele1.ELEMENT_NAME,ele.ELEMENT_NAME";
	
	protected static final String SQL_CIRCLE_WISE_REPORT3="select ele.element_name as CIRCLE_NAME,ele.ELEMENT_NAME,count(distinct(USER_LOGIN_ID)) as LOGGEDIN_USER_COUNT from LMS.KM_login_data usr inner join LMS.KM_ELEMENT_MSTR ele on usr.element_id=ele.element_id where ele.ELEMENT_ID = ? and date(login_time) = ? group by ele.element_name";
	
	//------  partner type attached by Kundan Kumar 01 Oct 2010
	
//	protected static final String SQL_USER_LOGIN_LOB_REPORT="WITH nee(element_id,element_level_id,status) AS ( SELECT  ELEMENT_ID,ELEMENT_LEVEL_ID,STATUS FROM LMS.KM_ELEMENT_MSTR WHERE element_id = ? UNION ALL 	SELECT nplus1.element_id,nplus1.element_level_id,nplus1.status FROM LMS.KM_ELEMENT_MSTR as nplus1, nee  WHERE nee.element_id=nplus1.PARENT_ID) SELECT usr.USER_LOGIN_ID,usr.USER_FNAME,usr.USER_LNAME,  usr.login_time , usm.PARTNER_NAME from LMS.KM_LOGIN_DATA usr inner join nee on usr.element_id = nee.element_id inner join LMS.KM_USER_MSTR usm on usm.USER_LOGIN_ID = usr.USER_LOGIN_ID where    (date(usr.LOGIN_TIME) between  ? and ? )  order by usr.login_time desc";
//	protected static final String SQL_USER_LOGIN_CIRCLE_REPORT="select (select case when (e.element_level_id)=3 then (select (select element_name from KM_ELEMENT_MSTR where element_id=(em.parent_id)) from KM_ELEMENT_MSTR em where e.element_id=(em.element_id)) else case when (e.element_level_id) =2 then (select element_name from KM_ELEMENT_MSTR em where e.element_id=(em.element_id))end end from km_element_mstr e,KM_USER_MSTR u where u.element_id=e.ELEMENT_ID and u.element_id=? and u.user_login_id=L.USER_LOGIN_ID) as lob,(select case when (e.element_level_id)=4 then (select (select element_name from KM_ELEMENT_MSTR where element_id=(em.parent_id)) from KM_ELEMENT_MSTR em where e.element_id=(em.element_id)) else case when (e.element_level_id) =3 then (select element_name from KM_ELEMENT_MSTR em where e.element_id=(em.element_id))end end from km_element_mstr e,KM_USER_MSTR u where u.element_id=e.ELEMENT_ID and u.element_id=? and u.user_login_id=L.USER_LOGIN_ID) as Circle,(select km_actor_name from KM_USER_MSTR u,KM_ACTORS a where u.km_actor_id=a.Km_actor_id and u.element_id=? and u.user_login_id=L.USER_LOGIN_ID) as role,L.USER_LOGIN_ID,L.USER_FNAME,L.USER_LNAME,L.LOGIN_TIME, U.PARTNER_NAME from LMS.KM_LOGIN_DATA L, LMS.KM_USER_MSTR U WHERE L.USER_LOGIN_ID = U.USER_LOGIN_ID AND L.ELEMENT_ID= ? AND (date(L.LOGIN_TIME) between  ? and ? ) order by L.login_time desc";
	
	//added by ashutosh	
//	protected static final String SQL_USER_LOGIN_CIRCLE_REPORT1="select a.user_login_id,a.USER_FNAME,a.USER_LNAME,a.TOTAL_LOGIN_TIME,a.PARTNER_NAME,a.ROLE, (select element_name from LMS.KM_element_mstr where LMS.KM_element_mstr.element_Id=? fetch first 1 row only) as Circle,(select element_name from LMS.KM_element_mstr ELE where ELE.element_Id=(select PARENT_ID from LMS.KM_element_mstr where element_Id=? ) fetch first 1 row only) as LOB from LMS.KM_USER_MSTR a ";
//	protected static final String SQL_USER_LOGIN_CIRCLE_REPORT1="select a.user_login_id,a.USER_FNAME,a.USER_LNAME, (select sum( timestampdiff(4, char(logout_time - login_time )) ) as LOGIN_TIME from KM_LOGIN_DATA where (logout_time - login_time ) is not null and  USER_LOGIN_ID=a.user_login_id and LOGIN_DATE = current date group by LOGIN_DATE) as TOTAL_LOGIN_TIME, a.PARTNER_NAME,(Select KM_ACTOR_NAME from KM_ACTORS where KM_ACTOR_ID = a.KM_ACTOR_ID ), (CASE WHEN a.KM_ACTOR_ID in (5,1) THEN ((select element_name from km_element_mstr where km_element_mstr.element_Id=? fetch first 1 row only)) WHEN a.KM_ACTOR_ID in (2,3,4,6,7,8) THEN (select element_name from km_element_mstr where km_element_mstr.element_Id= a.element_Id fetch first 1 row only) end) as Circle, (CASE WHEN a.KM_ACTOR_ID in (1) THEN ((select element_name from km_element_mstr where km_element_mstr.element_Id=? fetch first 1 row only)) WHEN a.KM_ACTOR_ID in (5) THEN ((select element_name from km_element_mstr where km_element_mstr.element_Id=a.element_Id fetch first 1 row only)) WHEN a.KM_ACTOR_ID in (2,3,4,6,7,8) THEN (select element_name from km_element_mstr ELE where ELE.element_Id= (select PARENT_ID from km_element_mstr where element_Id=a.element_Id ) fetch first 1 row only) end) as LOB from KM_USER_MSTR a where a.element_Id in  (select KEM.element_Id from KM_ELEMENT_MSTR KEM where KEM.parent_id = ? )";
	
	//added by Karan on 18 Jan 2013
	
    protected static final String SQL_USER_LOGIN_CIRCLE_REPORT="select a.user_login_id,a.USER_FNAME,a.USER_LNAME, " +
    		" (select sum( timestampdiff(4, char(logout_time - login_time )) ) as LOGIN_TIME " +
    		" from LMS.KM_LOGIN_DATA where (logout_time - login_time ) is not null " +
    		" and  USER_LOGIN_ID=a.user_login_id and LOGIN_DATE = current date " +
    		" group by LOGIN_DATE) as TOTAL_LOGIN_TIME," +
    		" a.PARTNER_NAME,(Select KM_ACTOR_NAME from KM_ACTORS where KM_ACTOR_ID = a.KM_ACTOR_ID ), " +
    		" (select element_name from LMS.KM_element_mstr where " +
    		" LMS.KM_element_mstr.element_Id= a.element_Id fetch first 1 row only)  as Circle, " +
    		" (select element_name from LMS.KM_element_mstr ELE where ELE.element_Id= " +
    		" (select PARENT_ID from LMS.KM_element_mstr where element_Id=a.element_Id) " +
    		" fetch first 1 row only) as LOB" +
    		" from LMS.KM_USER_MSTR a where a.KM_ACTOR_ID in (2,3,4,6,7,8) and a.element_Id = ? ";
    
    protected static final String SQL_USER_LOGIN_LOB_REPORT = "select a.user_login_id,a.USER_FNAME,a.USER_LNAME, " +
    		" (select sum( timestampdiff(4, char(logout_time - login_time )) ) as LOGIN_TIME " +
    		" from LMS.KM_LOGIN_DATA where (logout_time - login_time ) is not null " +
    		" and  USER_LOGIN_ID=a.user_login_id and LOGIN_DATE = current date  " +
    		" group by LOGIN_DATE) as TOTAL_LOGIN_TIME, " +
    	    " a.PARTNER_NAME,(Select KM_ACTOR_NAME from KM_ACTORS where KM_ACTOR_ID = a.KM_ACTOR_ID ), " +
    	    " (select element_name from LMS.KM_element_mstr where " +
    	    " LMS.KM_element_mstr.element_Id= a.element_Id fetch first 1 row only)  as Circle, " +
    	    " (select element_name from LMS.KM_element_mstr ELE where ELE.element_Id= " +
    	    " (select PARENT_ID from LMS.KM_element_mstr where element_Id=a.element_Id) " +
    	    " fetch first 1 row only) as LOB from LMS.KM_USER_MSTR a where a.KM_ACTOR_ID = 2 and " +
    	    " a.element_Id in (select KEM.element_Id from KM_ELEMENT_MSTR KEM  where KEM.parent_id = ?) ";
	
	
	protected static final String SQL_USER_LOGIN_FAVOURITE_CATEGORY_REPORT="select L.USER_LOGIN_ID, L.USER_FNAME, L.USER_LNAME, L.LOGIN_TIME ,  U.PARTNER_NAME from KM_LOGIN_DATA L, KM_USER_MSTR U 	WHERE L.USER_LOGIN_ID = U.USER_LOGIN_ID AND L.FAV_CATEGORY_ID= ? AND (date(L.LOGIN_TIME) between  ? and ?) order by L.login_time desc "; 
		
																														                                             
	public ArrayList getUserLoginList( String elementId, String elementLevel)throws LMSException{
					Connection con=null;
					ResultSet rs = null;
					PreparedStatement ps = null;
					UserMstr dto;
					ArrayList userList= new ArrayList();
				
					//logger.info("elementId::::::::::::::::::::::::::::"+elementId);
					//logger.info("elementLevel::::::::::::::::::::::::::::"+elementLevel);
	                  		
			try {
					//String sql=SQL_USER_LOGIN_REPORT;
					con=DBConnection.getDBConnection();
			
		      StringBuffer query=new StringBuffer("");
/*                if(elementLevel.equals("2")||elementLevel.equals("1")){
                	query.append(SQL_USER_LOGIN_LOB_REPORT);
                }
*/
              if(elementLevel.equals("2")){
                	//edited by Karan
                	query.append(SQL_USER_LOGIN_LOB_REPORT);
                }
                else if(elementLevel.equals("3")){ 
                	query.append(SQL_USER_LOGIN_CIRCLE_REPORT);
                }
                    //updated by ashutosh
              
                ps=con.prepareStatement(query.append(" with ur").toString());
					   ps.setInt(1,Integer.parseInt(elementId));
					  
					   //ps.setInt(2,Integer.parseInt(elementId));
					   //ps.setInt(3,Integer.parseInt(elementId));
					   
					  // ps.setInt(3,Integer.parseInt(elementId));
					   //ps.setInt(4,Integer.parseInt(elementId));
				      // ps.setString(5,fromDate );
					   //ps.setString(6,toDate);

					rs = ps.executeQuery();
					
				while(rs.next()){
						dto=new UserMstr();
						dto.setUserId(rs.getString("USER_LOGIN_ID"));
						dto.setUserFname(rs.getString("USER_FNAME"));
						dto.setUserLname(rs.getString("USER_LNAME"));
						
						
						//added by ashutosh for login user status report
						//dto.setLoginTime(rs.getString("TOTAL_LOGIN_TIME").substring(0,16));
						dto.setTotalLoginTime(rs.getDouble("TOTAL_LOGIN_TIME"));
						dto.setPartnerName(rs.getString("PARTNER_NAME"));
						dto.setRole(rs.getString("KM_ACTOR_NAME"));
						dto.setCircle(rs.getString("CIRCLE"));
						dto.setLob(rs.getString("LOB"));
						userList.add(dto);	
						
					}
				logger.info("Exit from agentwise report method");
					return userList;
         
				} catch (SQLException e) {
					logger.info(e);	
					logger.error(e);
//				logger.severe("SQL Exception occured while find." + "Exception Message: " + e.getMessage());
					throw new LMSException("SQLException: " + e.getMessage(), e);
				} catch (Exception e) {

//				logger.severe("Exception occured while find." + "Exception Message: " + e.getMessage());
					logger.info("Exception : "+e);
					logger.error(e);
					throw new LMSException("Exception: " + e.getMessage(), e);
				} finally {
					try {
						if (rs != null)
							rs.close();
						if (ps != null)
							ps.close();
						if (con != null) {
							con.close();
						}
				   } catch (Exception e) {
					   logger.error(e);
					}
				}
		}

	public ArrayList getcircleWisereport(String elementId, String kmActorId,String date)throws LMSException {

			Connection con = null;
			ResultSet rs = null;
		    UserMstr dto = null;
			ArrayList circleList = new ArrayList();
			PreparedStatement ps = null;
			int  totalUserCount=0;
			//KmFileReportFormBean formBean= new KmFileReportFormBean();
			int count = 0;
			String  circle_name;

			logger.info("Entered in circlewise report method");
			try {
				ArrayList countList = new ArrayList();
				String val = null;
StringBuffer query1=new StringBuffer(SQL_CIRCLE_WISE_REPORT1);
StringBuffer query2=new StringBuffer(SQL_CIRCLE_WISE_REPORT2);
StringBuffer query3=new StringBuffer(SQL_CIRCLE_WISE_REPORT3);

			    //String sql1 = SQL_CIRCLE_WISE_REPORT1;
				//String sql2 = SQL_CIRCLE_WISE_REPORT2;
				//String sql3 = SQL_CIRCLE_WISE_REPORT3;
				
				con = DBConnection.getDBConnection();

				if (kmActorId.equals(Constants.SUPER_ADMIN))
				{
				   ps=con.prepareStatement(query1.append(" with ur").toString());
				   ps.setString(1,date);
				   
				}else 
				if(kmActorId.equals(Constants.LOB_ADMIN))
				{
				   ps=con.prepareStatement(query2.append(" with ur").toString());
				   ps.setInt(1,Integer.parseInt(elementId));
				   ps.setString(2,date);
				}else
				 if(kmActorId.equals(Constants.CIRCLE_ADMIN) || kmActorId.equals(Constants.REPORT_ADMIN))
				{
				   ps=con.prepareStatement(query3.append(" with ur").toString());
				   ps.setInt(1,Integer.parseInt(elementId));
				   ps.setString(2,date);
				}

				

				//ps.setInt(1, Integer.parseInt(elementId));
				//ps.setString(2, fromDate);
				rs = ps.executeQuery();
				while (rs.next()) {
					dto=new UserMstr();
					dto.setCircleName(rs.getString("CIRCLE_NAME"));
					dto.setNoOfLoggedInUser(rs.getInt("LOGGEDIN_USER_COUNT"));
					dto.setLobName(rs.getString("ELEMENT_NAME"));
					totalUserCount=totalUserCount+rs.getInt("LOGGEDIN_USER_COUNT");
					dto.setTotal(totalUserCount);
					circleList.add(dto);
				
				
				}

				logger.info("Exit from circlewise report method");
				return circleList;

			} catch (SQLException e) {

				logger.error(
					"SQL Exception occured while generating cirle Wise Report"
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException("SQL Exception: " + e.getMessage(), e);
			} catch (Exception e) {
				logger.error(
					"Exception occured while generating cirle Wise Report"
						+ "Exception Message: "
						+ e.getMessage());
				throw new LMSException(" Exception: " + e.getMessage(), e);
			} finally {
				try {
					DBConnection.releaseResources(con, ps, rs);
				} catch (Exception e) {
					logger.error(
						"DAOException occured while generating cirle Wise Report"
							+ "Exception Message: "
							+ e.getMessage());
					throw new LMSException("DAO Exception: " + e.getMessage(), e);
				}
			}

		}
	public ArrayList getAgentList( String elementId,String eleLevelId,String partner)
		throws LMSException{
					Connection con=null;
					ResultSet rs = null;
					PreparedStatement ps = null;
					UserMstr dto;
					ArrayList agentList= new ArrayList();
				
				
			try {
					//String sql=SQL_AGENT_WISE_REPORT;
				StringBuffer query=new StringBuffer("");
				if(eleLevelId.equals("2")||eleLevelId.equals("1")){
					query.append(SQL_AGENT_WISE_REPORT1);
				}
				else if(eleLevelId.equals("3")){
					query.append(SQL_AGENT_WISE_REPORT2);
				}
				else{
					query.append(SQL_AGENT_WISE_REPORT3);
				}
					con=DBConnection.getDBConnection();
			    
					if (partner.equals(""))
					{
					   ps=con.prepareStatement(query.append(" with ur").toString());
					   ps.setInt(1,Integer.parseInt(elementId));
					   
					}
					else
					{
						
						
							query.append(" AND upper(PARTNER_NAME) = ? with ur ").toString();
							ps=con.prepareStatement(query.toString());
									
							ps.setInt(1, Integer.parseInt(elementId.trim()));
														
							ps.setString(2,partner.toUpperCase());

						
				}
		
			   
					rs = ps.executeQuery();
					
				while(rs.next()){
						dto=new UserMstr();
						dto.setUserId(rs.getString("USER_LOGIN_ID"));
						dto.setFirstName(rs.getString("USER_FNAME"));
						dto.setLastName(rs.getString("USER_LNAME"));
					
						agentList.add(dto);	
					}
				logger.info("Exit from agentwise report method");
					return agentList;
         
				} catch (SQLException e) {
					logger.error(e);
					e.printStackTrace();
//				logger.severe("SQL Exception occured while find." + "Exception Message: " + e.getMessage());
					throw new LMSException("SQLException: " + e.getMessage(), e);
				} catch (Exception e) {

//				logger.severe("Exception occured while find." + "Exception Message: " + e.getMessage());
					logger.error(e);
					throw new LMSException("Exception: " + e.getMessage(), e);
				} finally {
					try {
						if (rs != null)
							rs.close();
						if (ps != null)
							ps.close();
						if (con != null) {
							con.close();
						}
				   } catch (Exception e) {
					   logger.error(e);
					}
				}
		}

	public ArrayList getLockedUserList(String elementId) throws LMSException {
		Connection con=null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		UserMstr dto;
		ArrayList userList= new ArrayList();
		try {
		con=DBConnection.getDBConnection();
		ps=con.prepareStatement(" WITH NEE(ELEMENT_ID,ELEMENT_NAME) AS (SELECT  ELEMENT_ID,ELEMENT_NAME FROM KM_ELEMENT_MSTR  WHERE ELEMENT_ID =? UNION ALL SELECT NPLUS1.ELEMENT_ID,  NPLUS1.ELEMENT_NAME FROM KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) select USER_LOGIN_ID,nee.ELEMENT_NAME AS CIRCLE ,USER_FNAME,USER_LNAME,LOGIN_ATTEMPTED, USER_PSSWRD_EXPRY_DT from KM_USER_MSTR usr inner join nee on usr.element_id = nee.element_id where  current date > date(user_psswrd_expry_dt)  or login_attempted >=3   with ur ");
		int eleId = Integer.parseInt(elementId);
		ps.setInt(1, eleId);
		
		rs=ps.executeQuery();
		while(rs.next()){
				dto=new UserMstr();
				dto.setUserId(rs.getString("USER_LOGIN_ID"));
				dto.setFirstName(rs.getString("USER_FNAME"));
				dto.setLastName(rs.getString("USER_LNAME"));
				dto.setCircleName(rs.getString("CIRCLE"));
				if(rs.getInt("LOGIN_ATTEMPTED")>=3){
					dto.setLockType(PropertyReader.getAppValue("locktype.userlocked"));
				}
				else{
					dto.setLockType(PropertyReader.getAppValue("locktype.passwordexpry"));
				}
				userList.add(dto);	
			}
		logger.info("Exit from agentwise report method");
			//return userList;
 
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
//		logger.severe("SQL Exception occured while find." + "Exception Message: " + e.getMessage());
			throw new LMSException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {

//		logger.severe("Exception occured while find." + "Exception Message: " + e.getMessage());
			logger.error(e);
			throw new LMSException("Exception: " + e.getMessage(), e);
		} finally {
			
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
		   } catch (Exception e) {
			   logger.error(e);
			}
		}
		return userList;
	}

}
