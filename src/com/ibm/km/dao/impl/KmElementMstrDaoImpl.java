// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   KmElementMstrDaoImpl.java

package com.ibm.km.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

//import com.ibm.km.common.MyLabelValueBean;
import com.ibm.km.dao.KmElementMstrDao;
import com.ibm.km.dto.KmElementMstr;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.exception.LMSException;
import com.ibm.lms.exception.UserMstrDaoException;

public class KmElementMstrDaoImpl
    implements KmElementMstrDao
{

    public KmElementMstrDaoImpl()
    {
    }
    
 public HashMap<String, String> getAllCircleDesc()    throws LMSException
 {
    Connection con;
    ResultSet rs;
    PreparedStatement ps;
    HashMap<String, String> circleMap = new HashMap<String, String>();
    con = null;
    rs = null;
    ps = null;
    try
    {
        con = DBConnection.getDBConnection();
        ps = con.prepareStatement(SQL_GET_CIRCLE_DESC);
        rs = ps.executeQuery(); 
        while(rs.next())
        {
        	circleMap.put(rs.getString("ELEMENT_ID"),rs.getString("ELEMENT_NAME"));
        }

    }
    catch(SQLException e)
    {
        e.printStackTrace();
        logger.info(e);
        throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
    }
    catch(Exception e)
    {
        e.printStackTrace();
        logger.info(e);
        throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
    }
   
  finally{
    try
    {
        DBConnection.releaseResources(con, ps, rs);
    }
    catch(DAOException e)
    {
        e.printStackTrace();
        throw new LMSException(e.getMessage(), e);
    }
    
   }
    return circleMap;
}
    
    public ArrayList<Integer> getAllElementsAsPerLevel(int levelId)
    throws LMSException
{
    Connection con;
    ResultSet rs;
    PreparedStatement ps;
    ArrayList<Integer> elementList;
    con = null;
    rs = null;
    ps = null;
    elementList = new ArrayList<Integer>();
    try
    {
        con = DBConnection.getDBConnection();
        ps = con.prepareStatement(SQL_GET_ELEMENTS_AS_PER_LEVEL);
        ps.setInt(1,levelId);
        rs = ps.executeQuery(); 
        while(rs.next())
        {
        	elementList.add(rs.getInt("ELEMENT_ID"));
        }

    }
    catch(SQLException e)
    {
        e.printStackTrace();
        logger.info(e);
        throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
    }
    catch(Exception e)
    {
        e.printStackTrace();
        logger.info(e);
        throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
    }
   
  finally{
    try
    {
        DBConnection.releaseResources(con, ps, rs);
    }
    catch(DAOException e)
    {
        e.printStackTrace();
        throw new LMSException(e.getMessage(), e);
    }
    
   }
    return elementList;
 }//getAllElementsAsPerLevel
    
    public KmElementMstr getElementDetails(int elementId)
    throws LMSException
{
    Connection con;
    KmElementMstr dto = new KmElementMstr();
    ResultSet rs;
    PreparedStatement ps;
    con = null;
    rs = null;
    ps = null;
    try
    {
        con = DBConnection.getDBConnection();
        ps = con.prepareStatement(SQL_GET_ELEMENT);
        ps.setInt(1,elementId);
        rs = ps.executeQuery(); 
        
        if(rs.next())
        {
        	dto.setElementId(rs.getInt("ELEMENT_ID")+"");
        	dto.setElementName(rs.getString("ELEMENT_NAME"));
        	dto.setElementDesc(rs.getString("ELEMENT_DESC"));
        	dto.setParentId(rs.getInt("PARENT_ID")+"");
        	dto.setElementLevel(rs.getInt("ELEMENT_LEVEL_ID")+"");
        	dto.setPanStatus(rs.getString("PAN_STATUS"));
        	dto.setStatus(rs.getString("STATUS"));
        }

    }
    catch(SQLException e)
    {
        e.printStackTrace();
        logger.info(e);
        throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
    }
    catch(Exception e)
    {
        e.printStackTrace();
        logger.info(e);
        throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
    }
   
  finally{
    try
    {
        DBConnection.releaseResources(con, ps, rs);
    }
    catch(DAOException e)
    {
        e.printStackTrace();
        throw new LMSException(e.getMessage(), e);
    }
    
   }
    return dto;
 }//getElementDetails
    
    
    
    
    
    public ArrayList getChildren(String elementId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        ArrayList elementList;
        con = null;
        rs = null;
        ps = null;
        elementList = new ArrayList();
        try
        {
            StringBuffer query = new StringBuffer("SELECT * FROM LMS.KM_ELEMENT_MSTR WHERE  STATUS = 'A' AND PARENT_ID=? AND ELEMENT_LEVEL_ID != 0 AND PAN_STATUS != 'Y'");
            con = DBConnection.getDBConnection();
            logger.info(elementId);
            ps = con.prepareStatement(query.append("  ORDER BY lower(ELEMENT_NAME) with ur ").toString());
            KmElementMstr dto = new KmElementMstr();
            ps.setInt(1, Integer.parseInt(elementId));
            for(rs = ps.executeQuery(); rs.next(); elementList.add(dto))
            {
                dto = new KmElementMstr();
                dto.setElementName(rs.getString("ELEMENT_NAME"));
                dto.setElementId(rs.getString("ELEMENT_ID"));
                dto.setElementLevel(rs.getString("ELEMENT_LEVEL_ID"));
            }

            logger.info((new StringBuilder("List is returned :")).append(elementList.size()).toString());
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            logger.info(e);
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.info(e);
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
       
      finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        
      }
        return elementList;
    }

    
    
    public ArrayList getChildren(String parentId, String elementLevelId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        ArrayList elementList;
        con = null;
        rs = null;
        ps = null;
        elementList = new ArrayList();
        try
        {
            StringBuffer query = new StringBuffer("SELECT * FROM LMS.KM_ELEMENT_MSTR WHERE  STATUS = 'A' AND PARENT_ID=? AND ELEMENT_LEVEL_ID != 0 AND PAN_STATUS != 'Y'");
            query.append(" AND ELEMENT_LEVEL_ID=?").toString();
            con = DBConnection.getDBConnection();
            logger.info(parentId);
            ps = con.prepareStatement(query.append(" with ur ").toString());
            KmElementMstr dto = new KmElementMstr();
            ps.setInt(1, Integer.parseInt(parentId));
            ps.setInt(2, Integer.parseInt(elementLevelId));
            for(rs = ps.executeQuery(); rs.next(); elementList.add(dto))
            {
                dto = new KmElementMstr();
                dto.setElementName(rs.getString("ELEMENT_NAME"));
                dto.setElementId(rs.getString("ELEMENT_ID"));
                dto.setElementLevel(rs.getString("ELEMENT_LEVEL_ID"));
            }

            logger.info((new StringBuilder("List is returned :")).append(elementList.size()).toString());
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            logger.info(e);
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.info(e);
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        finally{
       
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return elementList;
    }

    public ArrayList getChildrenWithPath(String parentId, String root)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        ArrayList elementList;
        con = null;
        rs = null;
        ps = null;
        elementList = new ArrayList();
        try
        {
            StringBuffer query = new StringBuffer("WITH NEE(ELEMENT_ID,  CHAIN) AS (SELECT  ELEMENT_ID, CAST(ELEMENT_NAME AS VARCHAR(240)) FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ? UNION ALL SELECT NPLUS1.ELEMENT_ID, NEE.CHAIN || '/' || NPLUS1.ELEMENT_NAME FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT NEE.ELEMENT_ID, ELE.ELEMENT_NAME, CHAIN AS ELEMENT_PATH FROM LMS.KM_ELEMENT_MSTR ELE inner join NEE on ELE.ELEMENT_ID=NEE.ELEMENT_ID WHERE   ELE.PARENT_ID = ? AND ELE.ELEMENT_LEVEL_ID!=0 AND ELE.STATUS='A' AND ELE.PAN_STATUS = 'N' ");
            con = DBConnection.getDBConnection();
            logger.info(parentId);
            ps = con.prepareStatement(query.append(" with ur ").toString());
            KmElementMstr dto = new KmElementMstr();
            ps.setInt(1, Integer.parseInt(root));
            ps.setInt(2, Integer.parseInt(parentId));
            for(rs = ps.executeQuery(); rs.next(); logger.info((new StringBuilder("Element: ")).append(elementList).toString()))
            {
                dto = new KmElementMstr();
                dto.setElementName(rs.getString("ELEMENT_NAME"));
                dto.setElementId(rs.getString("ELEMENT_ID"));
                String path = rs.getString("ELEMENT_PATH");
                String documentStringPath = "";
                if(path.indexOf("/") + 1 < path.lastIndexOf("/"))
                    documentStringPath = path.substring(path.indexOf("/") + 1, path.lastIndexOf("/"));
                dto.setPath(documentStringPath);
                elementList.add(dto);
            }

            logger.info((new StringBuilder("List is returned :")).append(elementList.size()).toString());
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            logger.info(e);
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.info(e);
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return elementList;
    }

    public ArrayList getAllChildrenWithPath(String parentId, String root)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        ArrayList elementList;
        con = null;
        rs = null;
        ps = null;
        elementList = new ArrayList();
        try
        {
            StringBuffer query = new StringBuffer("WITH NEE(ELEMENT_ID,  CHAIN) AS (SELECT  ELEMENT_ID, CAST(ELEMENT_NAME AS VARCHAR(240)) FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ? UNION ALL SELECT NPLUS1.ELEMENT_ID, NEE.CHAIN || '/' || NPLUS1.ELEMENT_NAME FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT NEE.ELEMENT_ID, ELE.ELEMENT_NAME, ELE.ELEMENT_DESC,CHAIN AS ELEMENT_PATH FROM LMS.KM_ELEMENT_MSTR ELE inner join NEE on ELE.ELEMENT_ID=NEE.ELEMENT_ID WHERE ELE.PARENT_ID = ? AND ELE.ELEMENT_LEVEL_ID!=0 AND ELE.STATUS='A' ");
            KmElementMstr dto = new KmElementMstr();
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            ps.setInt(1, Integer.parseInt(root));
            ps.setInt(2, Integer.parseInt(parentId));
            for(rs = ps.executeQuery(); rs.next(); elementList.add(dto))
            {
                dto = new KmElementMstr();
                dto.setElementName(rs.getString("ELEMENT_NAME"));
                dto.setElementId(rs.getString("ELEMENT_ID"));
                dto.setElementDesc(rs.getString("ELEMENT_DESC"));
                String elementStringPath = "";
                String elementPath = rs.getString("ELEMENT_PATH");
                if(elementPath.indexOf("/") + 1 < elementPath.lastIndexOf("/"))
                    elementStringPath = elementPath.substring(elementPath.indexOf("/") + 1, elementPath.lastIndexOf("/"));
                dto.setPath(elementStringPath);
            }

            logger.info("Element List is Returned");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return elementList;
    }

    public ArrayList getAllChildren(String parentId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        con = null;
        rs = null;
        ps = null;
        ArrayList arraylist;
        try
        {
            ArrayList elementList = new ArrayList();
            StringBuffer query = new StringBuffer("SELECT * FROM LMS.KM_ELEMENT_MSTR WHERE  STATUS = 'A' AND PARENT_ID=? AND ELEMENT_LEVEL_ID != 0 ");
            KmElementMstr dto = new KmElementMstr();
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append("order by lower(element_name)  with ur").toString());
            ps.setInt(1, Integer.parseInt(parentId));
            for(rs = ps.executeQuery(); rs.next(); elementList.add(dto))
            {
                dto = new KmElementMstr();
                dto.setElementName(rs.getString("ELEMENT_NAME"));
                dto.setElementId(rs.getString("ELEMENT_ID"));
                dto.setElementLevel(rs.getString("ELEMENT_LEVEL_ID"));
            }

            logger.info("Element List is Returned");
            arraylist = elementList;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return arraylist;
        
    }

    public ArrayList getAllPANChildren(String parentId)   throws LMSException
    {
    Connection con;
    ResultSet rs;
    PreparedStatement ps;
    con = null;
    rs = null;
    ps = null;
    ArrayList arraylist;
    try
    {
        ArrayList elementList = new ArrayList();
        StringBuffer query = new StringBuffer("SELECT * FROM LMS.KM_ELEMENT_MSTR WHERE  STATUS = 'A' AND PARENT_ID=? AND ELEMENT_LEVEL_ID != 0 and PAN_STATUS = 'Y'");
        KmElementMstr dto = new KmElementMstr();
        con = DBConnection.getDBConnection();
        ps = con.prepareStatement(query.append("order by lower(element_name)  with ur").toString());
        ps.setInt(1, Integer.parseInt(parentId));
        for(rs = ps.executeQuery(); rs.next(); elementList.add(dto))
        {
            dto = new KmElementMstr();
            dto.setElementName(rs.getString("ELEMENT_NAME"));
            dto.setElementId(rs.getString("ELEMENT_ID"));
            dto.setElementLevel(rs.getString("ELEMENT_LEVEL_ID"));
        }

        logger.info("Element List is Returned");
        arraylist = elementList;
    }
    catch(SQLException e)
    {
        e.printStackTrace();
        throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
    }
    catch(Exception e)
    {
        e.printStackTrace();
        throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
    }finally{
    try
    {
        DBConnection.releaseResources(con, ps, rs);
    }
    catch(DAOException e)
    {
        e.printStackTrace();
        throw new LMSException(e.getMessage(), e);
    }
    }
    return arraylist;
    
}
    
    public ArrayList getAllChildrenElements(String parentId)
    throws LMSException
{
    Connection con;
    ResultSet rs;
    PreparedStatement ps;
    con = null;
    rs = null;
    ps = null;
    ArrayList arraylist;
    try
    {
        ArrayList elementList = new ArrayList();
        StringBuffer query = new StringBuffer("SELECT * FROM LMS.KM_ELEMENT_MSTR WHERE  STATUS = 'A' AND PARENT_ID=? ");
        KmElementMstr dto = new KmElementMstr();
        con = DBConnection.getDBConnection();
        ps = con.prepareStatement(query.append("order by lower(element_name)  with ur").toString());
        ps.setInt(1, Integer.parseInt(parentId));
        for(rs = ps.executeQuery(); rs.next(); elementList.add(dto))
        {
            dto = new KmElementMstr();
            dto.setElementName(rs.getString("ELEMENT_NAME"));
            dto.setElementId(rs.getString("ELEMENT_ID"));
            dto.setElementLevel(rs.getString("ELEMENT_LEVEL_ID"));
            dto.setElementDesc(rs.getString("ELEMENT_DESC"));
            dto.setParentId(rs.getString("PARENT_ID"));
            dto.setPanStatus(rs.getString("PAN_STATUS"));
            dto.setStatus(rs.getString("STATUS"));
        }

        logger.info("Element List is Returned");
        arraylist = elementList;
    }
    catch(SQLException e)
    {
        e.printStackTrace();
        throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
    }
    catch(Exception e)
    {
        e.printStackTrace();
        throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
    }finally{
    try
    {
        DBConnection.releaseResources(con, ps, rs);
    }
    catch(DAOException e)
    {
        e.printStackTrace();
        throw new LMSException(e.getMessage(), e);
    }
    }
    return arraylist;
    
}

    public ArrayList getAllChildren(String parentId, String levelId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        con = null;
        rs = null;
        ps = null;
        ArrayList arraylist;
        try
        {
            ArrayList elementList = new ArrayList();
            StringBuffer query = new StringBuffer("select ele1.element_id,ele1.element_name,ele1.element_level_id,ele2.element_id,ele2.element_name as parent_name from LMS.KM_element_mstr ele1 inner join LMS.KM_element_mstr ele2  on  ele1.parent_id = ele2.element_id  where ele1.parent_id=ele2.element_id and ele1.element_level_id=? ");
            KmElementMstr dto = new KmElementMstr();
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" order by lower(ele2.element_name), lower(ele1.element_name) with ur").toString());
            ps.setInt(1, Integer.parseInt(levelId));
            for(rs = ps.executeQuery(); rs.next(); elementList.add(dto))
            {
                dto = new KmElementMstr();
                dto.setElementName((new StringBuilder(String.valueOf(rs.getString("PARENT_NAME")))).append(":").append(rs.getString("ELEMENT_NAME")).toString());
                dto.setElementId(rs.getString("ELEMENT_ID"));
                dto.setElementLevel(rs.getString("ELEMENT_LEVEL_ID"));
                dto.setParentName(rs.getString("PARENT_NAME"));
            }

            logger.info("Element List is Returned");
            arraylist = elementList;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return arraylist;
  
    }

    public String getElementLevelName(String elementLevelId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        con = null;
        rs = null;
        ps = null;
        String s;
        try
        {
            logger.info((new StringBuilder("elementId ")).append(elementLevelId).toString());
            String elementLevelName = "";
            StringBuffer query = new StringBuffer("SELECT ELEMENT_LEVEL_NAME FROM LMS.KM_ELEMENT_LEVEL_MSTR WHERE ELEMENT_LEVEL_ID = ?");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            ps.setInt(1, Integer.parseInt(elementLevelId));
            for(rs = ps.executeQuery(); rs.next();)
                elementLevelName = rs.getString("ELEMENT_LEVEL_NAME");

            logger.info((new StringBuilder("elementLevelNAme ")).append(elementLevelName).toString());
            s = elementLevelName;
        }
        catch(SQLException e)
        {
            logger.info(e);
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            logger.info(e);
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        finally
        {
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return s;
        
    }

    public String getElementLevelId(String elementId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        con = null;
        rs = null;
        ps = null;
        String s;
        try
        {
            String elementLevelId = "";
            StringBuffer query = new StringBuffer("SELECT ELEMENT_LEVEL_ID FROM  LMS.KM_ELEMENT_MSTR  WHERE  ELEMENT_ID = ?");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur ").toString());
            ps.setInt(1, Integer.parseInt(elementId));
            for(rs = ps.executeQuery(); rs.next();)
                elementLevelId = rs.getString("ELEMENT_LEVEL_ID");

            s = elementLevelId;
        }
        catch(SQLException e)
        {
            logger.info(e);
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            logger.info(e);
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
       finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
       }
        return s;
       
    }

    public String getAllParentIdString(String rootId, String elementId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String parentString;
        String trimmedParentString;
        con = null;
        rs = null;
        ps = null;
        parentString = null;
        trimmedParentString = "";
        try
        {
            StringBuffer query = new StringBuffer("WITH NEE(ELEMENT_ID,CHAIN, ELEMENT_LEVEL_ID) AS (SELECT  ELEMENT_ID, CAST(CAST(ELEMENT_ID AS CHARACTER(5))AS VARCHAR(60)) ,ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR  WHERE ELEMENT_ID =? UNION ALL SELECT NPLUS1.ELEMENT_ID, NEE.CHAIN || '/' || CAST(NPLUS1.ELEMENT_ID AS CHARACTER(5)), NPLUS1.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT CHAIN AS PARENT_STRING FROM NEE WHERE NEE.ELEMENT_ID=?");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            ps.setInt(1, Integer.parseInt(rootId));
            ps.setInt(2, Integer.parseInt(elementId));
            rs = ps.executeQuery();
            if(rs.next())
                parentString = rs.getString("PARENT_STRING");
            for(StringTokenizer tokenized = new StringTokenizer(parentString); tokenized.hasMoreTokens();)
                trimmedParentString = (new StringBuilder(String.valueOf(trimmedParentString))).append(tokenized.nextToken()).toString();

        }
        catch(SQLException e)
        {
            logger.info(e);
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            logger.info(e);
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
       finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
       }
        return trimmedParentString;
    }

    public String getAllParentNameString(String rootId, String elementId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String parentString;
        con = null;
        rs = null;
        ps = null;
        parentString = null;
        String trimmedParentString = "";
        try
        {
            StringBuffer query = new StringBuffer("WITH NEE(ELEMENT_ID,CHAIN, ELEMENT_LEVEL_ID) AS (SELECT  ELEMENT_ID, CAST(ELEMENT_NAME AS VARCHAR(350)) ,ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR  WHERE ELEMENT_ID =? UNION ALL SELECT NPLUS1.ELEMENT_ID, NEE.CHAIN || '>' || NPLUS1.ELEMENT_NAME, NPLUS1.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT CHAIN AS PARENT_STRING FROM NEE WHERE NEE.ELEMENT_ID=?");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            ps.setInt(1, Integer.parseInt(rootId));
            ps.setInt(2, Integer.parseInt(elementId));
            rs = ps.executeQuery();
            if(rs.next())
                parentString = rs.getString("PARENT_STRING");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            logger.info(e);
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.info(e);
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return parentString;
    }

    public List getElementLevelNames()
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        List list;
        int i;
        con = null;
        rs = null;
        ps = null;
        list = new ArrayList();
        i = 0;
        List list1;
        try
        {
            StringBuffer query = new StringBuffer("SELECT ELEMENT_LEVEL_NAME,ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_LEVEL_MSTR ORDER BY ELEMENT_LEVEL_ID");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur ").toString());
            for(rs = ps.executeQuery(); rs.next();)
            {
                list.add(i, rs.getString("ELEMENT_LEVEL_NAME"));
                i++;
            }

            logger.info((new StringBuilder("Element Level Names: ")).append(list).toString());
            list1 = list;
        }
        catch(SQLException e)
        {
            logger.info(e);
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            logger.info(e);
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return list1;
       
    }

    public String getParentId(String elementId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String parentId;
        con = null;
        rs = null;
        ps = null;
        parentId = null;
        try
        {
            StringBuffer query = new StringBuffer("SELECT PARENT_ID FROM  LMS.KM_ELEMENT_MSTR  WHERE  ELEMENT_ID = ?");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur ").toString());
            ps.setInt(1, Integer.parseInt(elementId));
            rs = ps.executeQuery();
            if(rs.next())
                parentId = rs.getString("PARENT_ID");
        }
        catch(SQLException e)
        {
            logger.info(e);
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            logger.info(e);
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return parentId;
    }

    public KmElementMstr getPanChild(String parentId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        KmElementMstr dto;
        con = null;
        rs = null;
        ps = null;
        dto = null;
        try
        {
            StringBuffer query = new StringBuffer("SELECT * FROM LMS.KM_ELEMENT_MSTR WHERE  STATUS = 'A'  AND PAN_STATUS='Y' AND PARENT_ID= ?  ");
            con = DBConnection.getDBConnection();
            logger.info(parentId);
            ps = con.prepareStatement(query.append(" with ur ").toString());
            ps.setInt(1, Integer.parseInt(parentId));
            for(rs = ps.executeQuery(); rs.next(); dto.setElementId(rs.getString("ELEMENT_ID")))
            {
                dto = new KmElementMstr();
                dto.setElementName(rs.getString("ELEMENT_NAME"));
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            logger.info(e);
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.info(e);
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
       finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
       }
        return dto;
    }

    public String insertElement(KmElementMstr elementMstrDTO)
        throws LMSException
    {
        Connection con;
        PreparedStatement pstmt;
        ResultSet rs;
        con = null;
        pstmt = null;
        rs = null;
        String s="";
        try
        {
            StringBuffer query = new StringBuffer("SELECT NEXTVAL FOR LMS.KM_ELEMENT_ID_SEQ FROM SYSIBM.SYSDUMMY1");
            StringBuffer query1 = new StringBuffer("INSERT INTO LMS.KM_ELEMENT_MSTR(ELEMENT_ID, ELEMENT_NAME,ELEMENT_DESC, PARENT_ID, ELEMENT_LEVEL_ID, PAN_STATUS,STATUS, CREATED_BY, CREATED_DT, UPDATED_BY, UPDATED_DT)VALUES(?, ?, ?, ?, ?, ?, ?, ?, CURRENT TIMESTAMP,?, CURRENT TIMESTAMP)");
            con = DBConnection.getDBConnection();
            pstmt = con.prepareStatement(query.append(" with ur").toString());
            rs = pstmt.executeQuery();
            rs.next();
            int elementId = Integer.parseInt(rs.getString(1));
            pstmt = con.prepareStatement(query1.toString());
            pstmt.setInt(1, elementId);
            pstmt.setString(2, elementMstrDTO.getElementName());
            pstmt.setString(3, elementMstrDTO.getElementDesc());
            
            pstmt.setInt(4, Integer.parseInt(elementMstrDTO.getParentId()));
            pstmt.setInt(5, Integer.parseInt(elementMstrDTO.getElementLevel()));
            pstmt.setString(6, elementMstrDTO.getPanStatus());
            pstmt.setString(7, elementMstrDTO.getStatus());
            pstmt.setInt(8, Integer.parseInt(elementMstrDTO.getCreatedBy()));
            pstmt.setInt(9, Integer.parseInt(elementMstrDTO.getUpdatedBy()));
            pstmt.executeUpdate();
            s = (new StringBuilder(String.valueOf(elementId))).toString();
        }
        catch(UserMstrDaoException km)
        {
            km.printStackTrace();
            throw new LMSException(km.getMessage(), km);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        catch(DAOException e1)
        {
            e1.printStackTrace();
            
        }
        finally{
        try
        {
            DBConnection.releaseResources(con, pstmt, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return s;
        
     
    }

    public ArrayList getAllDocuments(String parentId)
        throws LMSException
    {
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList documentList;
        con = null;
        ps = null;
        rs = null;
        documentList = new ArrayList();
        KmElementMstr elementDto = new KmElementMstr();
        String element_Id = null;
        try
        {
            StringBuffer query = new StringBuffer("SELECT B.ELEMENT_ID, B.ELEMENT_NAME,C.DOCUMENT_NAME,C.DOCUMENT_ID  FROM  LMS.KM_ELEMENT_MSTR B inner join LMS.KM_DOCUMENT_MSTR C on B.ELEMENT_ID = C.ELEMENT_ID WHERE B.PARENT_ID=?    AND B.ELEMENT_LEVEL_ID = 0 AND C.STATUS = 'A'");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            ps.setInt(1, Integer.parseInt(parentId));
        //    KmElementMstr elementDto;
            for(rs = ps.executeQuery(); rs.next(); documentList.add(elementDto))
            {
                elementDto = new KmElementMstr();
                rs.getString("ELEMENT_ID");
                elementDto.setElementId(rs.getString("ELEMENT_ID"));
                elementDto.setElementName(rs.getString("ELEMENT_NAME"));
                elementDto.setDocumentId(rs.getString("DOCUMENT_ID"));
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.error((new StringBuilder("Exception occured while find.Exception Message: ")).append(e.getMessage()).toString());
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return documentList;
    }

    public ArrayList getDocList(String parentId)    throws LMSException
{
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    ArrayList documentList;
    con = null;
    ps = null;
    rs = null;
    documentList = new ArrayList();
    KmElementMstr elementDto = new KmElementMstr();
    String element_Id = null;
    try
    {
        StringBuffer query = new StringBuffer("SELECT B.ELEMENT_ID,  C.DOC_NAME,C.DOCUMENT_DISPLAY_NAME,C.DOCUMENT_ID  FROM  LMS.KM_ELEMENT_MSTR B inner join LMS.KM_DOCUMENT_MSTR C on B.ELEMENT_ID = C.ELEMENT_ID WHERE B.PARENT_ID=? AND B.ELEMENT_LEVEL_ID = 0 AND C.STATUS = 'A'");
        con = DBConnection.getDBConnection();
        ps = con.prepareStatement(query.append(" with ur").toString());
        ps.setInt(1, Integer.parseInt(parentId));
    //    KmElementMstr elementDto;
        for(rs = ps.executeQuery(); rs.next(); documentList.add(elementDto))
        {
            elementDto = new KmElementMstr();
           // rs.getString("ELEMENT_ID");
            elementDto.setElementId(rs.getString("ELEMENT_ID"));
            elementDto.setElementName(rs.getString("DOC_NAME"));
            elementDto.setDocumentName(rs.getString("DOCUMENT_DISPLAY_NAME"));
            elementDto.setDocumentId(rs.getString("DOCUMENT_ID"));
        }

    }
    catch(SQLException e)
    {
        e.printStackTrace();
        throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
    }
    catch(Exception e)
    {
        e.printStackTrace();
        logger.error((new StringBuilder("Exception occured while find.Exception Message: ")).append(e.getMessage()).toString());
        throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
    }
    finally{
    try
    {
        DBConnection.releaseResources(con, ps, rs);
    }
    catch(DAOException e)
    {
        e.printStackTrace();
        throw new LMSException(e.getMessage(), e);
    }
    }
    return documentList;
}
  	
    	
    public boolean moveElements(String movedDocumentList[], String parentId)
        throws LMSException
    {
        Connection con;
        PreparedStatement pstmt;
        ResultSet rs;
        boolean success;
        con = null;
        pstmt = null;
        rs = null;
        success = false;
        try
        {
            StringBuffer query = new StringBuffer("UPDATE LMS.KM_ELEMENT_MSTR SET PARENT_ID = ? WHERE ELEMENT_ID = ?");
            con = DBConnection.getDBConnection();
            pstmt = con.prepareStatement(query.append(" with ur").toString());
            for(int i = 0; i < movedDocumentList.length; i++)
            {
                pstmt.setInt(1, Integer.parseInt(parentId));
                pstmt.setInt(2, Integer.parseInt(movedDocumentList[i]));
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            con.commit();
            success = true;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
           
        }
        catch(DAOException e)
        {
            e.printStackTrace();
           
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        
     
      finally{
        try
        {
            DBConnection.releaseResources(con, pstmt, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
      }
        return success;
    }

    public String[] checkExistingElement(String elementName, String parentId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        con = null;
        rs = null;
        ps = null;
        String[] str = new String[2];
        str[0]="false";
        try
        {
            StringBuffer query = new StringBuffer("SELECT * FROM LMS.KM_ELEMENT_MSTR WHERE UPPER(ELEMENT_NAME) = UPPER(?) AND PARENT_ID = ? AND ELEMENT_LEVEL_ID!=0 AND STATUS='A'");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur ").toString());
            ps.setString(1, elementName);
            ps.setInt(2, Integer.parseInt(parentId));
            rs = ps.executeQuery();
            if(rs.next())
            {
            	int elemId = rs.getInt("ELEMENT_ID");
            	str[0] = "true";
            	str[1] = elemId + "";
            }    
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return str;
    }

    public String getElementName(String elementId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String elementName;
        con = null;
        rs = null;
        ps = null;
        elementName = "";
        String s;
        try
        {
            StringBuffer query = new StringBuffer("SELECT ELEMENT_NAME FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ?  ");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur ").toString());
            ps.setInt(1, Integer.parseInt(elementId));
            for(rs = ps.executeQuery(); rs.next();)
                elementName = rs.getString("ELEMENT_NAME");

            s = elementName;
        }
        catch(Exception e)
        {
                throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }}
        return s;
     
    }

    public ArrayList getAllLevelChildren(String parentId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        ArrayList elementList;
        con = null;
        rs = null;
        ps = null;
        elementList = new ArrayList();
        try
        {
            StringBuffer query = new StringBuffer("WITH NEE(ELEMENT_ID) AS (SELECT  ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ? UNION ALL SELECT NPLUS1.ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT NEE.ELEMENT_ID, ELE.ELEMENT_NAME, ELE.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR ELE inner join NEE on ELE.ELEMENT_ID=NEE.ELEMENT_ID WHERE  ELE.ELEMENT_LEVEL_ID!=0 AND ELE.STATUS='A' ");
            con = DBConnection.getDBConnection();
            logger.info(parentId);
            ps = con.prepareStatement(query.append(" with ur").toString());
            KmElementMstr dto = new KmElementMstr();
            ps.setInt(1, Integer.parseInt(parentId));
            for(rs = ps.executeQuery(); rs.next(); logger.info((new StringBuilder("Element: ")).append(elementList).toString()))
            {
                dto = new KmElementMstr();
                dto.setElementName(rs.getString("ELEMENT_NAME"));
                dto.setElementId(rs.getString("ELEMENT_ID"));
                dto.setElementLevel(rs.getString("ELEMENT_LEVEL_ID"));
                elementList.add(dto);
            }

            logger.info((new StringBuilder("List is returned :")).append(elementList.size()).toString());
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            logger.info(e);
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.info(e);
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return elementList;
    }

    public void updateLevel(int elementId, int newLevel) throws LMSException {
        Connection con;
        PreparedStatement pstmt;
        ResultSet rs;
        con = null;
        pstmt = null;
        rs = null;
        try
        {
            con = DBConnection.getDBConnection();
            StringBuffer query = new StringBuffer("UPDATE LMS.KM_ELEMENT_MSTR ELE SET ELE.ELEMENT_LEVEL_ID=? WHERE ELE.ELEMENT_ID=? ");
            pstmt = con.prepareStatement(query.append(" with ur").toString());
            pstmt.setInt(1, newLevel);
            pstmt.setInt(2, elementId);
            pstmt.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        catch(DAOException e1)
        {
            e1.printStackTrace();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
           
        }
      
      finally{
      
        try
        {
            DBConnection.releaseResources(con, pstmt, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
      }
    
    }
    
    public void updateElementLevel(ArrayList childrenList, int levelDiff)
        throws LMSException
    {
        Connection con;
        PreparedStatement pstmt;
        ResultSet rs;
        con = null;
        pstmt = null;
        rs = null;
        try
        {
            con = DBConnection.getDBConnection();
            StringBuffer query = new StringBuffer("UPDATE LMS.KM_ELEMENT_MSTR ELE SET ELE.ELEMENT_LEVEL_ID=ELE.ELEMENT_LEVEL_ID+? WHERE status ='A' and ( ELE.ELEMENT_ID=? ");
            Iterator i = childrenList.iterator();
            i.next();
            for(; i.hasNext(); i.next())
                query.append(" OR ELE.ELEMENT_ID=? ").toString();

            query.append(" )").toString();
            pstmt = con.prepareStatement(query.append(" with ur").toString());
            pstmt.setInt(1, levelDiff);
            int count = 1;
            for(Iterator iterator = childrenList.iterator(); iterator.hasNext(); pstmt.setInt(++count, Integer.parseInt(((KmElementMstr)iterator.next()).getElementId())));
            pstmt.executeUpdate();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        catch(DAOException e1)
        {
            e1.printStackTrace();
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
           
        }
      
      finally{
      
        try
        {
            DBConnection.releaseResources(con, pstmt, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
      }
    }

    public String[] getChildrenIds(String elementId)
        throws LMSException
    {
        Connection con;
        PreparedStatement pstmt;
        ResultSet rs;
        ArrayList list;
        con = null;
        pstmt = null;
        rs = null;
        list = new ArrayList();
        String as[];
        try
        {
            con = DBConnection.getDBConnection();
            StringBuffer query = new StringBuffer("WITH NEE(ELEMENT_ID) AS (SELECT  ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ? UNION ALL SELECT NPLUS1.ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT ele.ELEMENT_ID FROM NEE, LMS.KM_ELEMENT_MSTR ele where NEE.ELEMENT_ID=ele.ELEMENT_ID AND ele.STATUS='A' ");
            pstmt = con.prepareStatement(query.append("  with ur ").toString());
            pstmt.setString(1, elementId);
            rs = pstmt.executeQuery();
            int i = 0;
            for(; rs.next(); list.add(rs.getString("ELEMENT_ID")));
            String categoryID[] = (String[])list.toArray(new String[list.size()]);
            as = categoryID;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }finally{
        try
        {
            DBConnection.releaseResources(con, pstmt, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return as;
       
    }
    

    public ArrayList getChildrenIds(String parentId, String elementLevelId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        ArrayList elementIdList =new ArrayList();
        con = null;
        rs = null;
        ps = null;
      
        try
        {
            StringBuffer query = new StringBuffer("SELECT ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR WHERE  STATUS = 'A' AND PARENT_ID=? AND ELEMENT_LEVEL_ID != 0 AND PAN_STATUS != 'Y'");
            query.append(" AND ELEMENT_LEVEL_ID=?").toString();
            con = DBConnection.getDBConnection();
            logger.info(parentId);
            ps = con.prepareStatement(query.append(" with ur ").toString());
            KmElementMstr dto = new KmElementMstr();
            ps.setInt(1, Integer.parseInt(parentId));
            ps.setInt(2, Integer.parseInt(elementLevelId));
            for(rs = ps.executeQuery(); rs.next(); elementIdList.add(rs.getString("ELEMENT_ID")))
            

            logger.info((new StringBuilder("List is returned :")).append(elementIdList.size()).toString());
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            logger.info(e);
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.info(e);
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        finally{
       
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return elementIdList;
    }

    public void editElement(KmElementMstr dto)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        con = null;
        rs = null;
        ps = null;
        try
        {
            StringBuffer query = new StringBuffer("UPDATE LMS.KM_ELEMENT_MSTR SET ELEMENT_NAME = ? , ELEMENT_DESC = ? WHERE ELEMENT_ID = ?");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            ps.setString(1, dto.getElementName());
            ps.setString(2, dto.getElementDesc());
            ps.setInt(3, Integer.parseInt(dto.getElementId()));
            int rows = ps.executeUpdate();
            logger.info("Element Details Updated Successfully");
        }
        catch(SQLException e)
        {
            logger.info(e);
            e.printStackTrace();
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            logger.info((new StringBuilder("Exception : ")).append(e).toString());
            e.printStackTrace();
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
       finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
       }
        
    }

    public KmElementMstr getElemetDto(String elementId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        KmElementMstr dto;
        con = null;
        rs = null;
        ArrayList fileList = new ArrayList();
        ps = null;
        dto = null;
        try
        {
            StringBuffer query = new StringBuffer("WITH nee(element_id,chain) AS ( SELECT  ELEMENT_ID, cast(element_name as VARCHAR(240)) FROM LMS.KM_ELEMENT_MSTR WHERE element_id = ?  UNION ALL \tSELECT nplus1.element_id,nee.chain || '/' || nplus1.element_name  FROM LMS.KM_ELEMENT_MSTR as nplus1, nee  WHERE nee.element_id=nplus1.PARENT_ID) SELECT CHAIN ,ele.ELEMENT_ID, ele.ELEMENT_NAME,ele.ELEMENT_NAME,ele.ELEMENT_DESC FROM NEE inner join LMS.KM_ELEMENT_MSTR ele on ele.ELEMENT_ID = NEE.ELEMENT_ID WHERE   ele.ELEMENT_ID = ?");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            ps.setInt(1, Integer.parseInt("1"));
            ps.setInt(2, Integer.parseInt(elementId));
            for(rs = ps.executeQuery(); rs.next(); dto.setElementDesc(rs.getString("ELEMENT_DESC")))
            {
                dto = new KmElementMstr();
                logger.info(rs.getString("chain"));
                String path = rs.getString("chain");
                logger.info(path);
                String documentStringPath = "";
                if(path.indexOf("/") + 1 < path.lastIndexOf("/"))
                    documentStringPath = path.substring(path.indexOf("/") + 1, path.lastIndexOf("/"));
                dto.setElementStringPath(documentStringPath);
                dto.setElementId(rs.getString("ELEMENT_ID"));
                dto.setElementName(rs.getString("ELEMENT_NAME"));
            }

        }
        catch(SQLException e)
        {
            logger.info(e);
            e.printStackTrace();
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            logger.info((new StringBuilder("Exception : ")).append(e).toString());
            e.printStackTrace();
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
      finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }}
        return dto;
    }

    public String[] getElements(String elementId)
        throws LMSException
    {
        Connection con;
        PreparedStatement pstmt;
        ResultSet rs;
        ArrayList list;
        con = null;
        pstmt = null;
        rs = null;
        list = new ArrayList();
        String as[];
        try
        {
            StringBuffer query = new StringBuffer("WITH NEE(ELEMENT_ID) AS (SELECT  ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ? UNION ALL SELECT NPLUS1.ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT nee.ELEMENT_ID FROM NEE, LMS.KM_ELEMENT_MSTR ele where NEE.ELEMENT_ID=ele.ELEMENT_ID AND ele.ELEMENT_LEVEL_ID!=0 and ele.STATUS='A'");
            con = DBConnection.getDBConnection();
            pstmt = con.prepareStatement(query.append("  with ur ").toString());
            pstmt.setString(1, elementId);
            rs = pstmt.executeQuery();
            int i = 0;
            for(; rs.next(); list.add(rs.getString("ELEMENT_ID")));
            String elements[] = (String[])list.toArray(new String[list.size()]);
            as = elements;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }finally{
        try
        {
            DBConnection.releaseResources(con, pstmt, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return as;
       
     
    }

    public String[] getElements(String elementIds[])
        throws LMSException
    {
        Connection con;
        PreparedStatement pstmt;
        ResultSet rs;
        ArrayList list;
        con = null;
        pstmt = null;
        rs = null;
        list = new ArrayList();
        String as[];
        try
        {
            String query = "WITH NEE(ELEMENT_ID) AS (SELECT  ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ? UNION ALL SELECT NPLUS1.ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID)  SELECT nee.ELEMENT_ID FROM NEE, LMS.KM_ELEMENT_MSTR ele where NEE.ELEMENT_ID=ele.ELEMENT_ID AND ele.ELEMENT_LEVEL_ID=0 and ele.STATUS='A' with ur ";
            con = DBConnection.getDBConnection();
            for(int i = 0; i < elementIds.length; i++)
            {
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, elementIds[i]);
                for(rs = pstmt.executeQuery(); rs.next(); list.add(rs.getString("ELEMENT_ID")));
            }

            String elements[] = (String[])list.toArray(new String[list.size()]);
            logger.info((new StringBuilder("No. of documents : ")).append(elements.length).toString());
            as = elements;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }finally{
        try
        {
            DBConnection.releaseResources(con, pstmt, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return as;
       

       
    }

    public void deleteElements(String elements[], String updatedBy)
        throws LMSException
    {
        Connection con;
        PreparedStatement pstmt;
        ResultSet rs;
        StringBuffer query;
        con = null;
        pstmt = null;
        rs = null;
        query = new StringBuffer("UPDATE LMS.KM_ELEMENT_MSTR SET STATUS= 'D', UPDATED_DT= current timestamp, UPDATED_BY = ? WHERE STATUS='A' and (ELEMENT_ID = ? ");
        for(int i = 1; i < elements.length; i++)
            query.append(" OR ELEMENT_ID = ? ").toString();

        query.append(" ) ").toString();
        try
        {
            con = DBConnection.getDBConnection();
            pstmt = con.prepareStatement(query.append(" with ur").toString());
            pstmt.setString(1, updatedBy);
            for(int i = 0; i < elements.length; i++)
                pstmt.setInt(i + 2, Integer.parseInt(elements[i]));

            int documentsUpdated = pstmt.executeUpdate();
            logger.info((new StringBuilder("No.Of Elements Updated :")).append(documentsUpdated).toString());
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
      finally{
        try
        {
            DBConnection.releaseResources(con, pstmt, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
       
      }
    }

    public String getElementId(String documentId)
        throws LMSException
    {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "UPDATE LMS.KM_ELEMENT_MSTR SET STATUS= 'D', UPDATED_DT= current timestamp, UPDATED_BY = ? WHERE STATUS='A' and (ELEMENT_ID = ? ";
        return null;
    }

    public String getCircleId(String elementId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        String parentString;
        con = null;
        rs = null;
        ps = null;
        parentString = null;
        String trimmedParentString = "";
        String s;
        try
        {
            StringBuffer query = new StringBuffer("WITH NEE(ELEMENT_ID,CHAIN, ELEMENT_LEVEL_ID) AS (SELECT  ELEMENT_ID, CAST(CAST(ELEMENT_ID AS CHARACTER(5))AS VARCHAR(60)) ,ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR  WHERE ELEMENT_ID =1  UNION ALL  SELECT NPLUS1.ELEMENT_ID, NEE.CHAIN || '/' || CAST(NPLUS1.ELEMENT_ID AS CHARACTER(5)), NPLUS1.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT CHAIN AS PARENT_STRING FROM NEE WHERE NEE.ELEMENT_ID=? ");
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            ps.setInt(1, Integer.parseInt(elementId));
            rs = ps.executeQuery();
            if(rs.next())
                parentString = rs.getString("PARENT_STRING");
            s = parentString.substring(12, 17).trim();
        }
        catch(SQLException e)
        {
            logger.info(e);
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }}
        return s;
      
     
    }

    public String getPanCircleId()
        throws LMSException
    {
        Connection con;
        PreparedStatement pstmt;
        ResultSet rs;
        String elementId;
        con = null;
        pstmt = null;
        rs = null;
        elementId = null;
        String s;
        try
        {
            StringBuffer query = new StringBuffer("SELECT * FROM LMS.KM_ELEMENT_MSTR WHERE PAN_STATUS='Y'");
            con = DBConnection.getDBConnection();
            pstmt = con.prepareStatement(query.append(" with ur ").toString());
            rs = pstmt.executeQuery();
            if(rs.next())
                elementId = rs.getString("ELEMENT_ID");
            s = elementId;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }finally{
        try
        {
            DBConnection.releaseResources(con, pstmt, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }}
        return s;
      
   
    }

    public String[] getDocs(String movedElementList[])
        throws LMSException
    {
        Connection con;
        PreparedStatement pstmt;
        ResultSet rs;
        StringBuffer query;
        ArrayList list;
        con = null;
        pstmt = null;
        rs = null;
        query = new StringBuffer("SELECT doc.DOCUMENT_ID AS DOC_ID FROM LMS.KM_ELEMENT_MSTR ele,LMS.KM_DOCUMENT_MSTR doc WHERE ele.ELEMENT_LEVEL_ID = 0 AND ele.ELEMENT_ID=doc.ELEMENT_ID AND( ele.ELEMENT_ID = ? ");
        for(int i = 1; i < movedElementList.length; i++)
            query.append(" OR ele.ELEMENT_ID = ? ").toString();

        query.append(" ) ").toString();
        list = new ArrayList();
        String as[];
        try
        {
            con = DBConnection.getDBConnection();
            pstmt = con.prepareStatement(query.append(" with ur ").toString());
            int i;
            for(i = 0; i < movedElementList.length; i++)
                pstmt.setInt(i + 1, Integer.parseInt(movedElementList[i]));

            rs = pstmt.executeQuery();
            i = 0;
            for(; rs.next(); list.add(rs.getString("DOC_ID")));
            String elements[] = (String[])list.toArray(new String[list.size()]);
            as = elements;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }finally{
        try
        {
            DBConnection.releaseResources(con, pstmt, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }}
        return as;
       
        
        
    }

    public ArrayList getAllChildrenRec(String parentId, String elementLevel)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        con = null;
        rs = null;
        ps = null;
        ArrayList arraylist;
        try
        {
            ArrayList elementList = new ArrayList();
            StringBuffer query = new StringBuffer(" WITH NEE(ELEMENT_ID) AS  (SELECT  ELEMENT_ID   FROM LMS.KM_ELEMENT_MSTR  WHERE ELEMENT_ID = ?  UNION ALL  SELECT NPLUS1.ELEMENT_ID   FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE  WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID)  SELECT NEE.ELEMENT_ID, ELE.ELEMENT_NAME, ELE.ELEMENT_LEVEL_ID,ele.parent_id ,ele1.element_name AS PARENT_NAME  FROM LMS.KM_ELEMENT_MSTR ELE inner join NEE  on ELE.ELEMENT_ID=NEE.ELEMENT_ID inner join LMS.KM_ELEMENT_MSTR ele1 on ele1.element_id=ele.parent_id  WHERE   ELE.ELEMENT_LEVEL_ID=? AND ELE.STATUS='A' ");
            KmElementMstr dto = new KmElementMstr();
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            ps.setString(1, parentId);
            ps.setInt(2, Integer.parseInt(elementLevel));
            for(rs = ps.executeQuery(); rs.next(); elementList.add(dto))
            {
                dto = new KmElementMstr();
                dto.setElementName((new StringBuilder(String.valueOf(rs.getString("PARENT_NAME")))).append(":").append(rs.getString("ELEMENT_NAME")).toString());
                dto.setElementId(rs.getString("ELEMENT_ID"));
                dto.setElementLevel(rs.getString("ELEMENT_LEVEL_ID"));
                dto.setParentName(rs.getString("PARENT_NAME"));
            }

            logger.info("Element List is Returned");
            arraylist = elementList;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }}
        return arraylist;
       
        
      
    }

    public LinkedHashMap getCategoryMapElements(String elementId, String favCategoryId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        LinkedHashMap categoryMap;
        LinkedHashMap newCategoryMap;
        ArrayList subCategoryList;
        int parentLevel;
        int childLevel;
        con = null;
        rs = null;
        ps = null;
        categoryMap = new LinkedHashMap();
        newCategoryMap = new LinkedHashMap();
        subCategoryList = new ArrayList();
        parentLevel = 4;
        childLevel = 5;
        try
        {
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement("WITH nee(element_id,element_name,status,element_level_id,parent_id) AS ( SELECT  ELEMENT_ID, element_name ,status, element_level_id, parent_id  FROM LMS.KM_ELEMENT_MSTR WHERE element_id =? UNION ALL  SELECT nplus1.element_id, nplus1.element_name,nplus1.status ,nplus1.element_level_id, nplus1.parent_id FROM LMS.KM_ELEMENT_MSTR as nplus1, nee  WHERE nee.element_id=nplus1.PARENT_ID and nplus1.status='A')  SELECT A.parent_id as CATEGORY_ID, B.ELEMENT_NAME AS CATEGORY_NAME, A.element_id AS SUB_CATEGORY_ID ,A.element_name  AS SUB_CATEGORY_NAME, a.element_level_id ELEMENT_LEVEL_ID,b.element_level_id  AS  PARENT_LEVEL_ID FROM  nee A inner join LMS.KM_element_mstr B on a.parent_id = b.element_id left join LMS.KM_DOCUMENT_MSTR doc on doc.element_id = a.element_id  where    a.element_level_id in(?,?,?) and B.status='A'   and ((doc.approval_status = 'A'  and  current date between date(doc.PUBLISHING_START_DT) and date(doc.PUBLISHING_END_DT) and doc.status = 'A')or doc.approval_status is null)  order by parent_level_id,a.parent_id, lower(a.element_name) desc WITH UR");
            ps.setInt(1, Integer.parseInt(elementId));
            ps.setInt(2, childLevel);
            ps.setInt(3, parentLevel);
            ps.setInt(4, 0);
            rs = ps.executeQuery();
            String oldCategoryName = null;
            String oldCategoryId = null;
            int elementLevelId = 0;
            int parentLevelId = 0;
            int i = 0;
            while(rs.next()) 
            {
                elementLevelId = rs.getInt("ELEMENT_LEVEL_ID");
                parentLevelId = rs.getInt("PARENT_LEVEL_ID");
                /*if(elementLevelId == parentLevel)
                    categoryMap.put(new MyLabelValueBean(rs.getString("SUB_CATEGORY_NAME"), rs.getString("SUB_CATEGORY_ID")), new ArrayList());
                else*/
                if((elementLevelId == childLevel || elementLevelId == 0) && parentLevelId < 5)
                    if(i == 0)
                    {
                        oldCategoryName = rs.getString("CATEGORY_NAME");
                        oldCategoryId = rs.getString("CATEGORY_ID");
                        subCategoryList.add(new LabelValueBean(rs.getString("SUB_CATEGORY_NAME"), rs.getString("SUB_CATEGORY_ID")));
                        i++;
                    } else
                    if(i > 0)
                    {/*
                        if(!oldCategoryName.equals(rs.getString("CATEGORY_NAME")))
                        {
                            categoryMap.put(new MyLabelValueBean(oldCategoryName, oldCategoryId), subCategoryList);
                            if(parentLevelId < childLevel)
                            {
                                oldCategoryName = rs.getString("CATEGORY_NAME");
                                oldCategoryId = rs.getString("CATEGORY_ID");
                                subCategoryList = new ArrayList();
                            }
                        }*/
                        subCategoryList.add(new LabelValueBean(rs.getString("SUB_CATEGORY_NAME"), rs.getString("SUB_CATEGORY_ID")));
                    }
            }
            /*if(i > 0)
                categoryMap.put(new MyLabelValueBean(oldCategoryName, oldCategoryId), subCategoryList);
       */ }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
     
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
       
        try
        {
            if(favCategoryId != null)
            {
                Iterator myVeryOwnIterator = categoryMap.keySet().iterator();
                LabelValueBean bean = null;
                LabelValueBean favCategoryBean = null;
                ArrayList favCategoryList = new ArrayList();
                while(myVeryOwnIterator.hasNext()) 
                {
                    bean = (LabelValueBean)myVeryOwnIterator.next();
                    if(bean.getValue().equals(favCategoryId))
                        newCategoryMap.put(bean, categoryMap.get(bean));
                }
                for(myVeryOwnIterator = categoryMap.keySet().iterator(); myVeryOwnIterator.hasNext();)
                {
                    bean = (LabelValueBean)myVeryOwnIterator.next();
                    if(!bean.getValue().equals(favCategoryId))
                    {
                        favCategoryBean = bean;
                        favCategoryList = (ArrayList)(ArrayList)categoryMap.get(bean);
                        newCategoryMap.put(bean, favCategoryList);
                    }
                }

                return newCategoryMap;
            }
        }
        catch(Exception e)
        {
            return categoryMap;
        }
        return categoryMap;
    }

    public ArrayList getAllChildrenNoPan(String parentId, String levelId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        con = null;
        rs = null;
        ps = null;
        ArrayList arraylist;
        try
        {
            ArrayList elementList = new ArrayList();
            StringBuffer query = new StringBuffer("select ele1.element_id,ele1.element_name,ele1.element_level_id,ele2.element_id,ele2.element_name as parent_name from LMS.KM_element_mstr ele1 inner join LMS.KM_element_mstr ele2  on  ele1.parent_id = ele2.element_id  where ele1.parent_id=ele2.element_id and ele1.element_level_id=? ");
            KmElementMstr dto = new KmElementMstr();
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query.append(" and ele1.pan_status !='Y' order by lower(ele2.element_name), lower(ele1.element_name) with ur").toString());
            ps.setInt(1, Integer.parseInt(levelId));
            for(rs = ps.executeQuery(); rs.next(); elementList.add(dto))
            {
                dto = new KmElementMstr();
                dto.setElementName((new StringBuilder(String.valueOf(rs.getString("PARENT_NAME")))).append(":").append(rs.getString("ELEMENT_NAME")).toString());
                dto.setElementId(rs.getString("ELEMENT_ID"));
                dto.setElementLevel(rs.getString("ELEMENT_LEVEL_ID"));
                dto.setParentName(rs.getString("PARENT_NAME"));
            }

            logger.info("Element List is Returned");
            arraylist = elementList;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }finally{
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }}
     return arraylist;
      
    }

    public boolean getCircleStatus(String circleId)
        throws LMSException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        boolean isRestricted;
        con = null;
        rs = null;
        ps = null;
        isRestricted = false;
        try
        {
            ArrayList elementList = new ArrayList();
            String query = "SELECT RESTRICTED FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ? AND RESTRICTED = 'Y' WITH UR ";
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(circleId));
            for(rs = ps.executeQuery(); rs.next();)
                isRestricted = true;

            logger.info("Element List is Returned");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }finally{
      
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }
        }
        return isRestricted;
    }

    public HashMap getSubCategoryMapElements(String elementId, int parentLevel, int childLevel)
        throws LMSException
    {
    	LinkedHashMap categoryMap = new LinkedHashMap();
    	
    	/*Connection con;
        ResultSet rs;
        PreparedStatement ps;
        
        ArrayList subCategoryList;
        con = null;
        rs = null;
        ps = null;
        categoryMap = new LinkedHashMap();
        subCategoryList = new ArrayList();
        try
        {
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement("WITH nee(element_id,element_name,status,element_level_id,parent_id) AS ( SELECT  ELEMENT_ID, element_name ,status, element_level_id, parent_id  FROM LMS.KM_ELEMENT_MSTR WHERE element_id =? UNION ALL  SELECT nplus1.element_id, nplus1.element_name,nplus1.status ,nplus1.element_level_id, nplus1.parent_id FROM LMS.KM_ELEMENT_MSTR as nplus1, nee  WHERE nee.element_id=nplus1.PARENT_ID and nplus1.status='A')  SELECT A.parent_id as CATEGORY_ID, B.ELEMENT_NAME AS CATEGORY_NAME, A.element_id AS SUB_CATEGORY_ID ,A.element_name  AS SUB_CATEGORY_NAME, a.element_level_id ELEMENT_LEVEL_ID,b.element_level_id  AS  PARENT_LEVEL_ID FROM  nee A inner join LMS.KM_element_mstr B on a.parent_id = b.element_id left join LMS.KM_DOCUMENT_MSTR doc on doc.element_id = a.element_id  where    a.element_level_id in(?,?,?) and B.status='A'   and ((doc.approval_status = 'A'  and  current date between date(doc.PUBLISHING_START_DT) and date(doc.PUBLISHING_END_DT) and doc.status = 'A')or doc.approval_status is null)  order by parent_level_id,a.parent_id, lower(a.element_name) desc WITH UR");
            ps.setInt(1, Integer.parseInt(elementId));
            ps.setInt(2, childLevel);
            ps.setInt(3, parentLevel);
            ps.setInt(4, 0);
            rs = ps.executeQuery();
            String oldCategoryName = null;
            String oldCategoryId = null;
            int elementLevelId = 0;
            int parentLevelId = 0;
            int i = 0;
            while(rs.next()) 
            {
                elementLevelId = rs.getInt("ELEMENT_LEVEL_ID");
                parentLevelId = rs.getInt("PARENT_LEVEL_ID");
                if(elementLevelId == parentLevel)
                    //categoryMap.put(new MyLabelValueBean(rs.getString("SUB_CATEGORY_NAME"), rs.getString("SUB_CATEGORY_ID")), new ArrayList());
                else
                if((elementLevelId == childLevel || elementLevelId == 0) && parentLevelId < childLevel)
                    if(i == 0)
                    {
                        oldCategoryName = rs.getString("CATEGORY_NAME");
                        oldCategoryId = rs.getString("CATEGORY_ID");
                        subCategoryList.add(new LabelValueBean(rs.getString("SUB_CATEGORY_NAME"), rs.getString("SUB_CATEGORY_ID")));
                        i++;
                    } else
                    if(i > 0)
                    {
                        if(!oldCategoryName.equals(rs.getString("CATEGORY_NAME")))
                        {
                            categoryMap.put(new MyLabelValueBean(oldCategoryName, oldCategoryId), subCategoryList);
                            if(parentLevelId < childLevel)
                            {
                                oldCategoryName = rs.getString("CATEGORY_NAME");
                                oldCategoryId = rs.getString("CATEGORY_ID");
                                subCategoryList = new ArrayList();
                            }
                        }
                        subCategoryList.add(new LabelValueBean(rs.getString("SUB_CATEGORY_NAME"), rs.getString("SUB_CATEGORY_ID")));
                    }
            }
            if(i > 0)
                categoryMap.put(new MyLabelValueBean(oldCategoryName, oldCategoryId), subCategoryList);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
        
        try
        {
           DBConnection.releaseResources(con, ps, rs);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
       */
        return categoryMap;
    }

    public KmElementMstr getCompleteElementDetails(String elementId)
        throws DAOException
    {
        Connection con;
        ResultSet rs;
        PreparedStatement ps;
        KmElementMstr element;
        con = null;
        rs = null;
        ps = null;
        element = null;
        try
        {
            ArrayList elementList = new ArrayList();
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(" select ele.ELEMENT_ID,ele.ELEMENT_NAME,ele.ELEMENT_LEVEL_ID,doc.DOCUMENT_ID,doc.DOCUMENT_NAME,doc.DOCUMENT_PATH from LMS.KM_ELEMENT_MSTR ele left join LMS.KM_DOCUMENT_MSTR doc on ele.ELEMENT_ID = doc.ELEMENT_ID where ele.ELEMENT_ID = ? and ( doc.approval_status = 'A' or doc.approval_status is null)  with ur ");
            ps.setInt(1, Integer.parseInt(elementId));
            rs = ps.executeQuery();
            if(rs.next())
            {
                element = new KmElementMstr();
                element.setElementId(elementId);
                element.setElementLevel(rs.getString("ELEMENT_LEVEL_ID"));
                element.setElementName(rs.getString("ELEMENT_NAME"));
                element.setDocumentId(rs.getString("DOCUMENT_ID"));
                element.setDocumentName(rs.getString("DOCUMENT_NAME"));
                element.setDocumentPath(rs.getString("DOCUMENT_PATH"));
            }
            logger.info("Complete details of document is returned ");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
      finally{
      
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }}
        return element;
    }

  /*  public volatile HashMap getCategoryMapElements(String s, String s1)
        throws KmException
    {
        return getCategoryMapElements(s, s1);
    }*/

    protected static final String SQL_USER_LEVEL_ID = "SELECT ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR ELE, LMS.KM_USER_MSTR USR WHERE USR.USER_ID = ? AND ELE.ELEMENT_ID = USR.ELEMENT_ID";
    protected static final String SQL_UPDATE_PARENT = "UPDATE LMS.KM_ELEMENT_MSTR SET PARENT_ID = ? WHERE ELEMENT_ID = ?";
    protected static final String SQL_EDIT_ELEMENT = "UPDATE LMS.KM_ELEMENT_MSTR SET ELEMENT_NAME = ? , ELEMENT_DESC = ? WHERE ELEMENT_ID = ?";
    protected static final String SQL_GET_ELEMENT_NAME = "SELECT ELEMENT_NAME FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ?  ";
    protected static final String SQL_GET_ELEMENT_DETAILS = "WITH nee(element_id,chain) AS ( SELECT  ELEMENT_ID, cast(element_name as VARCHAR(240)) FROM LMS.KM_ELEMENT_MSTR WHERE element_id = ?  UNION ALL \tSELECT nplus1.element_id,nee.chain || '/' || nplus1.element_name  FROM LMS.KM_ELEMENT_MSTR as nplus1, nee  WHERE nee.element_id=nplus1.PARENT_ID) SELECT CHAIN ,ele.ELEMENT_ID, ele.ELEMENT_NAME,ele.ELEMENT_NAME,ele.ELEMENT_DESC FROM NEE inner join LMS.KM_ELEMENT_MSTR ele on ele.ELEMENT_ID = NEE.ELEMENT_ID WHERE   ele.ELEMENT_ID = ?";
    protected static final String SQL_GET_DOCUMENTS = "SELECT B.ELEMENT_ID, B.ELEMENT_NAME,C.DOCUMENT_NAME,C.DOCUMENT_ID  FROM  LMS.KM_ELEMENT_MSTR B inner join LMS.KM_DOCUMENT_MSTR C on B.ELEMENT_ID = C.ELEMENT_ID WHERE B.PARENT_ID=?    AND B.ELEMENT_LEVEL_ID = 0 AND C.STATUS = 'A'";
    protected static final String SQL_SELECT_ELEMENT = "ELEMENT_ID, ELEMENT_NAME FROM KM_ELEMENT_MSTR WHERE ELEMENT_ID = ?";
    protected static final String SQL_GET_CHILDREN = "SELECT * FROM LMS.KM_ELEMENT_MSTR WHERE  STATUS = 'A' AND PARENT_ID=? AND ELEMENT_LEVEL_ID != 0 AND PAN_STATUS != 'Y'";
    protected static final String SQL_GET_ALL_CHILDREN = "SELECT * FROM LMS.KM_ELEMENT_MSTR WHERE  STATUS = 'A' AND PARENT_ID=? AND ELEMENT_LEVEL_ID != 0 ";
    protected static final String SQL_GET_LEVEL_CHILDREN = "select ele1.element_id,ele1.element_name,ele1.element_level_id,ele2.element_id,ele2.element_name as parent_name from LMS.KM_element_mstr ele1 inner join LMS.KM_element_mstr ele2  on  ele1.parent_id = ele2.element_id  where ele1.parent_id=ele2.element_id and ele1.element_level_id=? ";
    protected static final String SQL_GET_LEVEL_CHILDREN_REC = " WITH NEE(ELEMENT_ID) AS  (SELECT  ELEMENT_ID   FROM LMS.KM_ELEMENT_MSTR  WHERE ELEMENT_ID = ?  UNION ALL  SELECT NPLUS1.ELEMENT_ID   FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE  WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID)  SELECT NEE.ELEMENT_ID, ELE.ELEMENT_NAME, ELE.ELEMENT_LEVEL_ID,ele.parent_id ,ele1.element_name AS PARENT_NAME  FROM LMS.KM_ELEMENT_MSTR ELE inner join NEE  on ELE.ELEMENT_ID=NEE.ELEMENT_ID inner join LMS.KM_ELEMENT_MSTR ele1 on ele1.element_id=ele.parent_id  WHERE   ELE.ELEMENT_LEVEL_ID=? AND ELE.STATUS='A' ";
    protected static final String SQL_GET_CHILDREN_PATH = "WITH NEE(ELEMENT_ID,  CHAIN) AS (SELECT  ELEMENT_ID, CAST(ELEMENT_NAME AS VARCHAR(240)) FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ? UNION ALL SELECT NPLUS1.ELEMENT_ID, NEE.CHAIN || '/' || NPLUS1.ELEMENT_NAME FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT NEE.ELEMENT_ID, ELE.ELEMENT_NAME, CHAIN AS ELEMENT_PATH FROM LMS.KM_ELEMENT_MSTR ELE inner join NEE on ELE.ELEMENT_ID=NEE.ELEMENT_ID WHERE   ELE.PARENT_ID = ? AND ELE.ELEMENT_LEVEL_ID!=0 AND ELE.STATUS='A' AND ELE.PAN_STATUS = 'N' ";
    protected static final String SQL_GET_ALL_CHILDREN_PATH = "WITH NEE(ELEMENT_ID,  CHAIN) AS (SELECT  ELEMENT_ID, CAST(ELEMENT_NAME AS VARCHAR(240)) FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ? UNION ALL SELECT NPLUS1.ELEMENT_ID, NEE.CHAIN || '/' || NPLUS1.ELEMENT_NAME FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT NEE.ELEMENT_ID, ELE.ELEMENT_NAME, ELE.ELEMENT_DESC,CHAIN AS ELEMENT_PATH FROM LMS.KM_ELEMENT_MSTR ELE inner join NEE on ELE.ELEMENT_ID=NEE.ELEMENT_ID WHERE ELE.PARENT_ID = ? AND ELE.ELEMENT_LEVEL_ID!=0 AND ELE.STATUS='A' ";
    protected static final String SQL_GET_PAN_CHILD = "SELECT * FROM LMS.KM_ELEMENT_MSTR WHERE  STATUS = 'A'  AND PAN_STATUS='Y' AND PARENT_ID= ?  ";
    protected static final String SQL_GET_ALL_LEVEL_CHILDREN = "WITH NEE(ELEMENT_ID) AS (SELECT  ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ? UNION ALL SELECT NPLUS1.ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT NEE.ELEMENT_ID, ELE.ELEMENT_NAME, ELE.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR ELE inner join NEE on ELE.ELEMENT_ID=NEE.ELEMENT_ID WHERE  ELE.ELEMENT_LEVEL_ID!=0 AND ELE.STATUS='A' ";
    protected static final String SQL_ELEMENT_LEVEL_NAME = "SELECT ELEMENT_LEVEL_NAME FROM LMS.KM_ELEMENT_LEVEL_MSTR WHERE ELEMENT_LEVEL_ID = ?";
    protected static final String SQL_ELEMENT_LEVEL_ID = "SELECT ELEMENT_LEVEL_ID FROM  LMS.KM_ELEMENT_MSTR  WHERE  ELEMENT_ID = ?";
    protected static final String SQL_PARENT_ID_STRING = "WITH NEE(ELEMENT_ID,CHAIN, ELEMENT_LEVEL_ID) AS (SELECT  ELEMENT_ID, CAST(CAST(ELEMENT_ID AS CHARACTER(5))AS VARCHAR(60)) ,ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR  WHERE ELEMENT_ID =? UNION ALL SELECT NPLUS1.ELEMENT_ID, NEE.CHAIN || '/' || CAST(NPLUS1.ELEMENT_ID AS CHARACTER(5)), NPLUS1.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT CHAIN AS PARENT_STRING FROM NEE WHERE NEE.ELEMENT_ID=?";
    protected static final String SQL_PARENT_NAME_STRING = "WITH NEE(ELEMENT_ID,CHAIN, ELEMENT_LEVEL_ID) AS (SELECT  ELEMENT_ID, CAST(ELEMENT_NAME AS VARCHAR(350)) ,ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR  WHERE ELEMENT_ID =? UNION ALL SELECT NPLUS1.ELEMENT_ID, NEE.CHAIN || '>' || NPLUS1.ELEMENT_NAME, NPLUS1.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT CHAIN AS PARENT_STRING FROM NEE WHERE NEE.ELEMENT_ID=?";
    protected static final String SQL_GET_ALL_LEVEL_NAMES = "SELECT ELEMENT_LEVEL_NAME,ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_LEVEL_MSTR ORDER BY ELEMENT_LEVEL_ID";
    protected static final String SQL_PARENT_ID = "SELECT PARENT_ID FROM  LMS.KM_ELEMENT_MSTR  WHERE  ELEMENT_ID = ?";
    protected static final String SQL_GET_ELEMENT_ID = "SELECT NEXTVAL FOR LMS.KM_ELEMENT_ID_SEQ FROM SYSIBM.SYSDUMMY1";
    public static final String SQL_INSERT_ELEMENT = "INSERT INTO LMS.KM_ELEMENT_MSTR(ELEMENT_ID, ELEMENT_NAME,ELEMENT_DESC, PARENT_ID, ELEMENT_LEVEL_ID, PAN_STATUS,STATUS, CREATED_BY, CREATED_DT, UPDATED_BY, UPDATED_DT)VALUES(?, ?, ?, ?, ?, ?, ?, ?, CURRENT TIMESTAMP,?, CURRENT TIMESTAMP)";
    protected static final String SQL_COUNT_ELEMENT = "SELECT * FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_NAME=? AND PARENT_ID = ? AND ELEMENT_LEVEL_ID!=0 AND STATUS='A'";
    protected static final String SQL_UPDATE_ELEMENT_LEVEL = "UPDATE LMS.KM_ELEMENT_MSTR ELE SET ELE.ELEMENT_LEVEL_ID=ELE.ELEMENT_LEVEL_ID+? WHERE status ='A' and ( ELE.ELEMENT_ID=? ";
    protected static final String SQL_GET_CHILDREN_IDS = "WITH NEE(ELEMENT_ID) AS (SELECT  ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ? UNION ALL SELECT NPLUS1.ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT ele.ELEMENT_ID FROM NEE, LMS.KM_ELEMENT_MSTR ele where NEE.ELEMENT_ID=ele.ELEMENT_ID AND ele.STATUS='A' ";
    protected static final String SQL_GET_ELEMENTS = "WITH NEE(ELEMENT_ID) AS (SELECT  ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ? UNION ALL SELECT NPLUS1.ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT nee.ELEMENT_ID FROM NEE, LMS.KM_ELEMENT_MSTR ele where NEE.ELEMENT_ID=ele.ELEMENT_ID AND ele.ELEMENT_LEVEL_ID!=0 and ele.STATUS='A'";
    protected static final String SQL_GET_DOC_ELEMENTS = "WITH NEE(ELEMENT_ID) AS (SELECT  ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ? UNION ALL SELECT NPLUS1.ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID)  SELECT nee.ELEMENT_ID FROM NEE, LMS.KM_ELEMENT_MSTR ele where NEE.ELEMENT_ID=ele.ELEMENT_ID AND ele.ELEMENT_LEVEL_ID=0 and ele.STATUS='A'";
    protected static final String SQL_DELETE_ELEMENTS = "UPDATE LMS.KM_ELEMENT_MSTR SET STATUS= 'D', UPDATED_DT= current timestamp, UPDATED_BY = ? WHERE STATUS='A' and (ELEMENT_ID = ? ";
    protected static final String SQL_GET_PAN_CIRCLE = "SELECT * FROM LMS.KM_ELEMENT_MSTR WHERE PAN_STATUS='Y'";
    protected static final String SQL_GET_PATH_FOR_CIRCLE_ID = "WITH NEE(ELEMENT_ID,CHAIN, ELEMENT_LEVEL_ID) AS (SELECT  ELEMENT_ID, CAST(CAST(ELEMENT_ID AS CHARACTER(5))AS VARCHAR(60)) ,ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR  WHERE ELEMENT_ID =1  UNION ALL  SELECT NPLUS1.ELEMENT_ID, NEE.CHAIN || '/' || CAST(NPLUS1.ELEMENT_ID AS CHARACTER(5)), NPLUS1.ELEMENT_LEVEL_ID FROM LMS.KM_ELEMENT_MSTR AS NPLUS1, NEE WHERE NEE.ELEMENT_ID=NPLUS1.PARENT_ID) SELECT CHAIN AS PARENT_STRING FROM NEE WHERE NEE.ELEMENT_ID=? ";
    protected static final String SQL_GET_DOCUMENT_ELEMENTS = "SELECT doc.DOCUMENT_ID AS DOC_ID FROM LMS.KM_ELEMENT_MSTR ele,LMS.KM_DOCUMENT_MSTR doc WHERE ele.ELEMENT_LEVEL_ID = 0 AND ele.ELEMENT_ID=doc.ELEMENT_ID AND( ele.ELEMENT_ID = ? ";
    protected static final String SQL_GET_ELEMENTS_MAP = "WITH nee(element_id,element_name,status,element_level_id,parent_id) AS ( SELECT  ELEMENT_ID, element_name ,status, element_level_id, parent_id  FROM LMS.KM_ELEMENT_MSTR WHERE element_id =? UNION ALL  SELECT nplus1.element_id, nplus1.element_name,nplus1.status ,nplus1.element_level_id, nplus1.parent_id FROM LMS.KM_ELEMENT_MSTR as nplus1, nee  WHERE nee.element_id=nplus1.PARENT_ID and nplus1.status='A')  SELECT A.parent_id as CATEGORY_ID, B.ELEMENT_NAME AS CATEGORY_NAME, A.element_id AS SUB_CATEGORY_ID ,A.element_name  AS SUB_CATEGORY_NAME, a.element_level_id ELEMENT_LEVEL_ID,b.element_level_id  AS  PARENT_LEVEL_ID FROM  nee A inner join LMS.KM_element_mstr B on a.parent_id = b.element_id left join LMS.KM_DOCUMENT_MSTR doc on doc.element_id = a.element_id  where    a.element_level_id in(?,?,?) and B.status='A'   and ((doc.approval_status = 'A'  and  current date between date(doc.PUBLISHING_START_DT) and date(doc.PUBLISHING_END_DT) and doc.status = 'A')or doc.approval_status is null)  order by parent_level_id,a.parent_id, lower(a.element_name) desc WITH UR";
    protected static final String SQL_GET_COMPLETE_ELEMENT = " select ele.ELEMENT_ID,ele.ELEMENT_NAME,ele.ELEMENT_LEVEL_ID,doc.DOCUMENT_ID,doc.DOCUMENT_NAME,doc.DOCUMENT_PATH from LMS.KM_ELEMENT_MSTR ele left join LMS.KM_DOCUMENT_MSTR doc on ele.ELEMENT_ID = doc.ELEMENT_ID where ele.ELEMENT_ID = ? and ( doc.approval_status = 'A' or doc.approval_status is null)  with ur ";
    protected static final String SQL_GET_ELEMENTS_AS_PER_LEVEL = "SELECT ELEMENT_ID FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_LEVEL_ID = ? AND STATUS = 'A' WITH UR ";
    protected static final String SQL_GET_ELEMENT = "SELECT * FROM LMS.KM_ELEMENT_MSTR WHERE ELEMENT_ID = ? WITH UR ";
    protected static final String SQL_GET_CIRCLE_DESC = "select ELEMENT_ID , ELEMENT_NAME from KM_ELEMENT_MSTR where ELEMENT_LEVEL_ID = 3 and STATUS = 'A' with UR";
    
    private static final Logger logger = Logger.getLogger(KmElementMstrDaoImpl.class);

////Added by Harpreet For Copy of Element
    
    public int insertElement(String elementId,String parentId)throws LMSException{
    
    	Connection con=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        try
        {
            con = DBConnection.getDBConnection();
            ps = con.prepareStatement(" select ele.ELEMENT_ID,ele.ELEMENT_NAME,ele.ELEMENT_LEVEL_ID,doc.DOCUMENT_ID,doc.DOCUMENT_NAME,doc.DOCUMENT_PATH from LMS.KM_ELEMENT_MSTR ele left join LMS.KM_DOCUMENT_MSTR doc on ele.ELEMENT_ID = doc.ELEMENT_ID where ele.ELEMENT_ID = ? and ( doc.approval_status = 'A' or doc.approval_status is null)  with ur ");
            ps.setInt(1, Integer.parseInt(elementId));
            ps.setInt(2, Integer.parseInt(parentId));
            ps.executeUpdate();
               logger.info("Complete details of document is returned ");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("SQLException: ")).append(e.getMessage()).toString(), e);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new LMSException((new StringBuilder("Exception: ")).append(e.getMessage()).toString(), e);
        }
      finally{
      
        try
        {
            DBConnection.releaseResources(con, ps, rs);
        }
        catch(DAOException e)
        {
            e.printStackTrace();
            throw new LMSException(e.getMessage(), e);
        }}

    	
    return 1;	
    }
	
	public int insertdocument(String elementId)throws LMSException{
		
		return 1;	
	}
	
	public boolean phyCopy(String docPath)throws LMSException{
		
	return true;
	}
    
    
    
    
    
}
