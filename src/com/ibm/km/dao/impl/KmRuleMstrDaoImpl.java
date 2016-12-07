package com.ibm.km.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.km.dao.KmRuleMstrDao;
import com.ibm.km.dto.KmRuleMstr;
import com.ibm.km.exception.KmRuleMstrDaoException;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.exception.DAOException;
public class KmRuleMstrDaoImpl  implements KmRuleMstrDao {


 /** 
	* Logger for this class. Use logger.log(message) for logging. Refer to @link http://java.sun.com/j2se/1.4.2/docs/guide/util/logging/overview.html for logging options and configuration.
 **/ 

 private static Logger logger = Logger.getLogger(KmRuleMstrDaoImpl.class.toString());


    protected static final String SQL_INSERT_WITH_ID = "INSERT INTO KM_RULE_MSTR (RULE_ID, RULE_NAME, RULE_DESC, CIRCLE_ID, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY, STATUS) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    protected static final String SQL_SELECT = "SELECT KM_RULE_MSTR.RULE_ID, KM_RULE_MSTR.RULE_NAME, KM_RULE_MSTR.RULE_DESC, KM_RULE_MSTR.CIRCLE_ID, KM_RULE_MSTR.CREATED_DT, KM_RULE_MSTR.CREATED_BY, KM_RULE_MSTR.UPDATED_DT, KM_RULE_MSTR.UPDATED_BY, KM_RULE_MSTR.STATUS FROM KM_RULE_MSTR ";

    protected static final String SQL_UPDATE = "UPDATE KM_RULE_MSTR SET RULE_ID = ?, RULE_NAME = ?, RULE_DESC = ?, CIRCLE_ID = ?, CREATED_DT = ?, CREATED_BY = ?, UPDATED_DT = ?, UPDATED_BY = ?, STATUS = ? WHERE RULE_ID = ?";

    protected static final String SQL_DELETE = "DELETE FROM KM_RULE_MSTR WHERE RULE_ID = ?";

    public  int insert(KmRuleMstr dto) throws KmRuleMstrDaoException {

		logger.info("Entered insert for table KM_RULE_MSTR");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int rowsUpdated = 0;
        try {
            //String sql = SQL_INSERT_WITH_ID;
            StringBuffer query=new StringBuffer(SQL_INSERT_WITH_ID);
            con = getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(query.append(" with ur").toString());
            int paramCount = 1;

            ps.setString(paramCount++,  dto.getRuleId());
            ps.setString(paramCount++,  dto.getRuleName());
            ps.setString(paramCount++,  dto.getRuleDesc());
            ps.setString(paramCount++,  dto.getCircleId());
            ps.setTimestamp(paramCount++,  dto.getCreatedDt());
            ps.setString(paramCount++,  dto.getCreatedBy());
            ps.setTimestamp(paramCount++,  dto.getUpdatedDt());
            ps.setString(paramCount++,  dto.getUpdatedBy());
            ps.setString(paramCount++,  dto.getStatus());
            rowsUpdated=ps.executeUpdate();
            con.commit();

		logger.info("Row insertion successful on table:KM_RULE_MSTR. Inserted:" + rowsUpdated  + " rows");

        } catch (SQLException e) {

		logger.error("SQL Exception occured while inserting." + "Exception Message: " + e.getMessage());
            throw new KmRuleMstrDaoException("SQLException: " + e.getMessage(), e);
        } catch (Exception e) {

		logger.error("Exception occured while inserting." + "Exception Message: " + e.getMessage());
            throw new KmRuleMstrDaoException("Exception: " + e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (Exception e) {
            }
        }

        return rowsUpdated;
    }

    public  KmRuleMstr findByPrimaryKey(String ruleId) throws KmRuleMstrDaoException {

		logger.info("Entered findByPrimaryKey for table:KM_RULE_MSTR");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
           StringBuffer query=new StringBuffer(SQL_SELECT);
            //String sql =
            query.append(" WHERE KM_RULE_MSTR.RULE_ID = ?").toString();
            con = getConnection();
            ps = con.prepareStatement(query.append("with ur").toString());
            ps.setString(1, ruleId);
            rs = ps.executeQuery();
            return fetchSingleResult(rs);
        } catch (SQLException e) {

		logger.error("SQL Exception occured while find." + "Exception Message: " + e.getMessage());
            throw new KmRuleMstrDaoException("SQLException: " + e.getMessage(), e);
        } catch (Exception e) {

		logger.error("Exception occured while find." + "Exception Message: " + e.getMessage());
            throw new KmRuleMstrDaoException("Exception: " + e.getMessage(), e);
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
            }
        }

    }

    public  int update(KmRuleMstr dto) throws KmRuleMstrDaoException {

		logger.info("Entered update for table KM_RULE_MSTR");

        Connection con = null;
        PreparedStatement ps = null;
        int numRows = -1;

        try {
            //String sql = SQL_UPDATE;
            StringBuffer query=new StringBuffer(SQL_UPDATE);
            con = getConnection();
            ps = con.prepareStatement(query.append("with ur").toString());
            if (dto.getRuleId() ==null)
                ps.setNull(1, Types.DECIMAL);
            else
                ps.setString(1,  dto.getRuleId());
            if (dto.getRuleName() ==null)
                ps.setNull(2, Types.VARCHAR);
            else
                ps.setString(2,  dto.getRuleName());
            if (dto.getRuleDesc() ==null)
                ps.setNull(3, Types.VARCHAR);
            else
                ps.setString(3,  dto.getRuleDesc());
            if (dto.getCircleId() ==null)
                ps.setNull(4, Types.DECIMAL);
            else
                ps.setString(4,  dto.getCircleId());
            if (dto.getCreatedDt() ==null)
                ps.setNull(5, Types.TIMESTAMP);
            else
                ps.setTimestamp(5,  dto.getCreatedDt());
            if (dto.getCreatedBy() ==null)
                ps.setNull(6, Types.DECIMAL);
            else
                ps.setString(6,  dto.getCreatedBy());
            if (dto.getUpdatedDt() ==null)
                ps.setNull(7, Types.TIMESTAMP);
            else
                ps.setTimestamp(7,  dto.getUpdatedDt());
            if (dto.getUpdatedBy() ==null)
                ps.setNull(8, Types.DECIMAL);
            else
                ps.setString(8,  dto.getUpdatedBy());
            if (dto.getStatus() ==null)
                ps.setNull(9, Types.CHAR);
            else
                ps.setString(9,  dto.getStatus());
            ps.setString(10,  dto.getRuleId());
            numRows = ps.executeUpdate();

		logger.info("Update successful on table:KM_RULE_MSTR. Updated:" + numRows  + " rows");

        } catch (SQLException e) {

		logger.error("SQL Exception occured while update." + "Exception Message: " + e.getMessage());
            throw new KmRuleMstrDaoException("SQLException: " + e.getMessage(), e);
        } catch (Exception e) {

		logger.error("Exception occured while update." + "Exception Message: " + e.getMessage());
            throw new KmRuleMstrDaoException("Exception: " + e.getMessage(), e);
        } finally {
            try {
                    if (ps != null)
                        ps.close();
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception e) {
            }
        }
        return numRows;
    }

    public  int delete(String ruleId) throws KmRuleMstrDaoException {

		logger.info("Entered delete for table KM_RULE_MSTR");

        Connection con = null;
        PreparedStatement ps = null;
        int numRows = -1;

        try {
           //String sql = SQL_DELETE;
           StringBuffer query=new StringBuffer(SQL_DELETE);
            con = getConnection();
            ps = con.prepareStatement(query.append("with ur").toString());
            ps.setString(1, ruleId);
            numRows = ps.executeUpdate();

		logger.info("Delete successful on table:KM_RULE_MSTR. Deleted:" + numRows  + " rows");

        } catch (SQLException e) {

		logger.error("SQL Exception occured while delete." + "Exception Message: " + e.getMessage());
            throw new KmRuleMstrDaoException("SQLException: " + e.getMessage(), e);
        } catch (Exception e) {

		logger.error("Exception occured while delete." + "Exception Message: " + e.getMessage());
            throw new KmRuleMstrDaoException("Exception: " + e.getMessage(), e);
        } finally {
            try {
                    if (ps != null)
                        ps.close();
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception e) {
            }
        }
        return numRows;
    }

    protected  KmRuleMstr[] fetchMultipleResults(ResultSet rs) throws SQLException {
        ArrayList results = new ArrayList();
        while (rs.next()) {
            KmRuleMstr dto = new KmRuleMstr();
            populateDto(dto, rs);
            results.add(dto);
        }
        KmRuleMstr retValue[] = new KmRuleMstr[results.size()];
        results.toArray(retValue);
        return retValue;
    }


    protected  KmRuleMstr fetchSingleResult(ResultSet rs) throws SQLException {
        if (rs.next()) {
            KmRuleMstr dto = new KmRuleMstr();
            populateDto(dto, rs);
            return dto;
        } else 
            return null;
    }

    protected static void populateDto(KmRuleMstr dto, ResultSet rs) throws SQLException {
        dto.setRuleId(rs.getString("RULE_ID"));

        dto.setRuleName(rs.getString("RULE_NAME"));

        dto.setRuleDesc(rs.getString("RULE_DESC"));

        dto.setCircleId(rs.getString("CIRCLE_ID"));

        dto.setCreatedDt(rs.getTimestamp("CREATED_DT"));

        dto.setCreatedBy(rs.getString("CREATED_BY"));

        dto.setUpdatedDt(rs.getTimestamp("UPDATED_DT"));

        dto.setUpdatedBy(rs.getString("UPDATED_BY"));

        dto.setStatus(rs.getString("STATUS"));
    }
    
    private Connection getConnection() throws KmRuleMstrDaoException {
		logger.info("Entered getConnection for operation on table:KM_RULE_MSTR");
		try {
       	return DBConnection.getDBConnection();
		}catch(DAOException e) {
		logger.info("Exception Occured while obtaining connection.");
		throw new KmRuleMstrDaoException("Exception while trying to obtain a connection",e);
    }
   }
}