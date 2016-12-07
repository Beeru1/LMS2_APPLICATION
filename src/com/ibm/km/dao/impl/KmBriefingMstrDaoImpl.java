// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KmBriefingMstrDaoImpl.java

package com.ibm.km.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.ibm.km.dao.KmBriefingMstrDao;
import com.ibm.km.dto.KmBriefingMstr;
import com.ibm.km.forms.KmBriefingMstrFormBean;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;

public class KmBriefingMstrDaoImpl
    implements KmBriefingMstrDao
{

    public KmBriefingMstrDaoImpl()
    {
    }

    public void insert(KmBriefingMstr dto)
        throws LMSException
    {
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        logger.info("Entered insert for table KM_BRIEFING_MSTR");
        con = null;
        ps = null;
        rs = null;
        int rowsUpdated = 0;
        try
        {
            StringBuffer query = new StringBuffer("INSERT INTO LMS.KM_BRIEFING_MSTR (BRIEFING_ID, BRIEFING_HEADING, BRIEFING_DETAILS, CIRCLE_ID, CREATED_BY, CREATED_DT, DISPLAY_DT,CATEGORY_ID) VALUES ( NEXTVAL FOR LMS.KM_BRIEFING_ID, ?, ?, ?, ?,  current timestamp, timestamp_format(?, 'YYYY-MM-DD HH24:MI:SS'),?)");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            ps.setString(1, dto.getBriefingHeading());
            ps.setString(2, dto.getBriefingDetails());
            ps.setInt(3, Integer.parseInt(dto.getCircleId()));
            ps.setInt(4, Integer.parseInt(dto.getCreatedBy()));
            ps.setString(5, (new StringBuilder(String.valueOf(dto.getDisplayDt()))).append(" 00:00:00").toString());
            ps.setString(6, dto.getCategoryId());
            rowsUpdated = ps.executeUpdate();
            logger.info((new StringBuilder("Row insertion successful on table:KM_BRIEFING_MSTR. Inserted:")).append(rowsUpdated).append(" rows").toString());
        }
        catch(SQLException e)
        {
            logger.error((new StringBuilder("SQL Exception occured while inserting.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.error((new StringBuilder("Exception occured while inserting.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
      finally{
        try
        {
           
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(Exception e)
        {
            logger.error((new StringBuilder("DAO Exception occured while inserting.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
      }
    }

    public ArrayList view(String circleId, String categoryId, String date)
        throws LMSException
    {
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList briefingList;
        logger.info("Entered view Briefings for table KM_BRIEFING_MSTR");
        con = null;
        ps = null;
        rs = null;
        briefingList = new ArrayList();
        try
        {
            StringBuffer query = new StringBuffer("SELECT * FROM LMS.KM_BRIEFING_MSTR WHERE");
            if(categoryId != null)
            {
                query.append(" CIRCLE_ID=? AND month(DISPLAY_DT) = month(current timestamp) and year(DISPLAY_DT) = year(current timestamp) ");
                query.append(" AND ( CATEGORY_ID  = ? OR CATEGORY_ID IS NULL ) ");
            } else
            {
                query.append(" CIRCLE_ID=? AND month(DISPLAY_DT) = month(current timestamp) and year(DISPLAY_DT) = year(current timestamp) AND CATEGORY_ID IS NULL  ");
            }
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            logger.info((new StringBuilder("Circle ID: ")).append(circleId).append(" Date: ").append(date).toString());
            ps.setString(1, circleId);
            //ps.setString(2, (new StringBuilder(String.valueOf(date))).append(" 00:00:00").toString());
            if(categoryId != null)
                ps.setString(2, categoryId);
            rs = ps.executeQuery();
            KmBriefingMstr briefing = null;
            for(; rs.next(); briefingList.add(briefing))
            {
                briefing = new KmBriefingMstr();
                briefing.setBriefingId(rs.getString("BRIEFING_ID"));
                briefing.setBriefingHeading(rs.getString("BRIEFING_HEADING"));
                briefing.setBriefingDetails(rs.getString("BRIEFING_DETAILS"));
                briefing.setCreatedDt(rs.getString("CREATED_DT").substring(0,19));
                //logger.info(  briefing.getCreatedDt());
                
            }

            logger.info("Briefing View successful on table:KM_BRIEFING_MSTR.");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            logger.error((new StringBuilder("SQL Exception occured while Viewing.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            logger.error((new StringBuilder("Exception occured while Viewing.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }finally{
     
        try
        {
            
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(Exception e)
        {
            logger.error((new StringBuilder("DAO Exception occured while Viewing.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
      
        }
        return briefingList;
    }

    public ArrayList editBriefings(String circleId, String fromDate, String endDate, int userId)
        throws LMSException
    {
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList briefingList;
        logger.info("Entered edit Briefings for table KM_BRIEFING_MSTR");
        con = null;
        ps = null;
        rs = null;
        String query = null;
        int km_actor_id = 0;
        query = "select KM_ACTOR_ID from LMS.KM_USER_MSTR where USER_ID = ? ";
        ////logger.info("Inside KmBriefingMstrDaoImpl.editBriefings!!!!");
        try{
        	con = DBConnection.getDBConnection();
        	////logger.info("Inside KmBriefingMstrDaoImpl.editBriefings!!!!created conn");
        	ps = con.prepareStatement(query);
        	ps.setInt(1,userId);
        	rs = ps.executeQuery();
        	////logger.info("Inside KmBriefingMstrDaoImpl.editBriefings!!!!executed query");
        	while(rs.next()){
        		km_actor_id = rs.getInt("KM_ACTOR_ID");
        		////logger.info("Inside KmBriefingMstrDaoImpl.editBriefings!!!!km_actor_id=="+km_actor_id+"circleId=="+circleId);
        	}
        
        }catch(SQLException sqle){
        	sqle.printStackTrace();
        }catch(DAOException daoe){
        	daoe.printStackTrace();
        }
        
        briefingList = new ArrayList();
        int i = 0;
        try
        {
            con = DBConnection.getDBConnection();
            if(km_actor_id == 1){
            	query = "SELECT e.ELEMENT_NAME,e.ELEMENT_LEVEL_ID,b.BRIEFING_ID,b.BRIEFING_HEADING, b.BRIEFING_DETAILS,b.CREATED_DT, b.DISPLAY_DT,b.CATEGORY_ID,ele.ELEMENT_NAME AS CATEGORY_NAME FROM LMS.KM_BRIEFING_MSTR b INNER JOIN LMS.KM_ELEMENT_MSTR e on e.ELEMENT_ID = b.CIRCLE_ID left join LMS.KM_ELEMENT_MSTR ele on ele.ELEMENT_ID = b.CATEGORY_ID WHERE DATE(b.CREATED_DT) >= DATE(CURRENT DATE - 3 DAYS) AND DATE(b.CREATED_DT) <= DATE(CURRENT DATE)  AND    b.CIRCLE_ID=? with ur";
            	ps = con.prepareStatement(query);
            	//ps.setString(1, (new StringBuilder(String.valueOf(endDate))).append(" 00:00:00").toString());
            	ps.setString(1, circleId);
            }else{	
            	query = "SELECT e.ELEMENT_NAME,e.ELEMENT_LEVEL_ID,b.BRIEFING_ID,b.BRIEFING_HEADING, b.BRIEFING_DETAILS,b.CREATED_DT, b.DISPLAY_DT,b.CATEGORY_ID,ele.ELEMENT_NAME AS CATEGORY_NAME FROM LMS.KM_BRIEFING_MSTR b INNER JOIN LMS.KM_ELEMENT_MSTR e on e.ELEMENT_ID = b.CIRCLE_ID left join LMS.KM_ELEMENT_MSTR ele on ele.ELEMENT_ID = b.CATEGORY_ID WHERE DATE(b.DISPLAY_DT) >= DATE(timestamp_format(?, 'YYYY-MM-DD HH24:MI:SS')) AND DATE(b.DISPLAY_DT) <= DATE(timestamp_format(?, 'YYYY-MM-DD HH24:MI:SS'))  AND    b.CIRCLE_ID=? with ur";
            	ps = con.prepareStatement(query);
            	ps.setString(1, (new StringBuilder(String.valueOf(fromDate))).append(" 00:00:00").toString());
            	ps.setString(2, (new StringBuilder(String.valueOf(endDate))).append(" 00:00:00").toString());
            	ps.setString(3, circleId);
            }
            rs = ps.executeQuery();
            KmBriefingMstr briefing = null;
            for(; rs.next(); briefingList.add(briefing))
            {
                briefing = new KmBriefingMstr();
                briefing.setBriefingId(rs.getString("BRIEFING_ID"));
                briefing.setBriefingHeading(rs.getString("BRIEFING_HEADING"));
                briefing.setCreatedDt(rs.getString("CREATED_DT").substring(0, 11));
                briefing.setDisplayDt(rs.getString("DISPLAY_DT").substring(0, 11));
                briefing.setDisplayDt(rs.getString("DISPLAY_DT").substring(0, 11));
                if(rs.getString("CATEGORY_NAME") == null)
                    briefing.setFavCategory("");
                else
                    briefing.setFavCategory(rs.getString("CATEGORY_NAME"));
            }

            logger.info("Briefing edit on table:KM_BRIEFING_MSTR.");
        }
        catch(SQLException e)
        {
            logger.error((new StringBuilder("SQL Exception occured while Editing Briefings.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            logger.error((new StringBuilder("Exception occured while Editing Briefings.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        finally{
       
        try
        {
           
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(Exception e)
        {
            logger.error((new StringBuilder("DAO Exception occured while Viewing.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
 
        }
        return briefingList;
    }

    public KmBriefingMstr updateBriefings(int briefingId)
        throws LMSException
    {
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        KmBriefingMstr briefing;
        logger.info("Entered edit Briefings for table KM_BRIEFING_MSTR");
        con = null;
        ps = null;
        rs = null;
        ArrayList briefingList = new ArrayList();
        KmBriefingMstrFormBean formBean = new KmBriefingMstrFormBean();
        briefing = null;
        try
        {
            StringBuffer query = new StringBuffer("SELECT BRIEFING_ID,BRIEFING_HEADING, BRIEFING_DETAILS,CREATED_DT, DISPLAY_DT FROM LMS.KM_BRIEFING_MSTR WHERE  BRIEFING_ID=? ");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            ps.setInt(1, briefingId);
            for(rs = ps.executeQuery(); rs.next(); briefing.setDisplayDt((new StringBuilder()).append(rs.getDate("DISPLAY_DT")).toString()))
            {
                briefing = new KmBriefingMstr();
                briefing.setBriefingId(rs.getString("BRIEFING_ID"));
                briefing.setBriefingHeading(rs.getString("BRIEFING_HEADING"));
                String briefDetails = rs.getString("BRIEFING_DETAILS");
                StringTokenizer stoken = null;
                stoken = new StringTokenizer(briefDetails, "|");
                int count = stoken.countTokens();
                if(count != 0)
                {
                    String arBriefDetails[] = new String[count];
                    for(int i = 0; i < count; i++)
                        arBriefDetails[i] = stoken.nextToken().trim();

                    briefing.setArrBriefDetails(arBriefDetails);
                }
            }

            logger.info("Briefing edit on table:KM_BRIEFING_MSTR.");
        }
        catch(SQLException e)
        {
            logger.error((new StringBuilder("SQL Exception occured while populating the briefings for edit.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            logger.error((new StringBuilder("Exception occured while Viewing populating the briefings for edit. Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        finally{
        try
        {
          
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(Exception e)
        {
            logger.error((new StringBuilder("DAO Exception occured while populating the briefings for edit.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        }
    
        
        return briefing;
    }

    public void updateinDbBriefings(String briefingId, String briefHeading, String arrBriefingDetails[], String displayDt)
        throws LMSException
    {
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        logger.info("Entered in update in DB Briefings for table KM_BRIEFING_MSTR");
        con = null;
        ps = null;
        rs = null;
        try
        {
            StringBuffer query = new StringBuffer("UPDATE  LMS.KM_BRIEFING_MSTR  SET BRIEFING_HEADING=?, BRIEFING_DETAILS=?, DISPLAY_DT= timestamp_format(?, 'YYYY-MM-DD HH24:MI:SS') WHERE  BRIEFING_ID=? ");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            ps.setString(1, briefHeading);
            String briefingDetails = "";
            for(int i = 0; i < arrBriefingDetails.length; i++)
                briefingDetails = (new StringBuilder(String.valueOf(briefingDetails))).append("|").append(arrBriefingDetails[i]).toString();

            briefingDetails = briefingDetails.substring(1, briefingDetails.length());
            ps.setString(2, briefingDetails);
            ps.setString(3, (new StringBuilder(String.valueOf(displayDt))).append(" 00:00:00").toString());
            ps.setInt(4, Integer.parseInt(briefingId));
            int result = ps.executeUpdate();
            logger.info("Briefing update on table:KM_BRIEFING_MSTR.");
        }
        catch(SQLException e)
        {
            logger.error((new StringBuilder("SQL Exception occured while update.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            logger.error((new StringBuilder("Exception occured while Update.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        finally{
        try
        {
           
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(Exception e)
        {
            logger.error((new StringBuilder("DAO Exception occured while Update.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
     
       
        }
    }

    private static final Logger logger = Logger.getLogger(KmBriefingMstrDaoImpl.class);
    protected static final String SQL_INSERT = "INSERT INTO LMS.KM_BRIEFING_MSTR (BRIEFING_ID, BRIEFING_HEADING, BRIEFING_DETAILS, CIRCLE_ID, CREATED_BY, CREATED_DT, DISPLAY_DT,CATEGORY_ID) VALUES ( NEXTVAL FOR LMS.KM_BRIEFING_ID, ?, ?, ?, ?,  current timestamp, timestamp_format(?, 'YYYY-MM-DD HH24:MI:SS'),?)";
    protected static final String SQL_VIEW_BRIEFINGS = "SELECT * FROM LMS.KM_BRIEFING_MSTR WHERE CIRCLE_ID=? AND DATE(DISPLAY_DT)= DATE(timestamp_format(?, 'YYYY-MM-DD HH24:MI:SS')) ";
    protected static final String SQL_EDIT_BRIEFINGS = "SELECT e.ELEMENT_NAME,e.ELEMENT_LEVEL_ID,b.BRIEFING_ID,b.BRIEFING_HEADING, b.BRIEFING_DETAILS,b.CREATED_DT, b.DISPLAY_DT,b.CATEGORY_ID,ele.ELEMENT_NAME AS CATEGORY_NAME FROM LMS.KM_BRIEFING_MSTR b INNER JOIN LMS.KM_ELEMENT_MSTR e on e.ELEMENT_ID = b.CIRCLE_ID left join LMS.KM_ELEMENT_MSTR ele on ele.ELEMENT_ID = b.CATEGORY_ID WHERE DATE(b.DISPLAY_DT) >= DATE(timestamp_format(?, 'YYYY-MM-DD HH24:MI:SS')) AND DATE(b.DISPLAY_DT) <= DATE(timestamp_format(?, 'YYYY-MM-DD HH24:MI:SS'))  AND    b.CIRCLE_ID=? with ur";
    protected static final String SQL_SELECT_BRIEFINGS = "SELECT BRIEFING_ID,BRIEFING_HEADING, BRIEFING_DETAILS,CREATED_DT, DISPLAY_DT FROM LMS.KM_BRIEFING_MSTR WHERE  BRIEFING_ID=? ";
    protected static final String SQL_UPDATE_BRIEFINGS = "UPDATE  LMS.KM_BRIEFING_MSTR  SET BRIEFING_HEADING=?, BRIEFING_DETAILS=?, DISPLAY_DT= timestamp_format(?, 'YYYY-MM-DD HH24:MI:SS') WHERE  BRIEFING_ID=? ";

}
