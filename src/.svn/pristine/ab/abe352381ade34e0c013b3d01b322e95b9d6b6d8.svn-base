package com.ibm.km.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.km.dao.KmActorMstrDao;
import com.ibm.km.dto.KmActorMstr;
import com.ibm.km.exception.KmActorsDaoException;
import com.ibm.lms.common.DBConnection;
import com.ibm.lms.exception.DAOException;

public class KmActorMstrDaoImpl  implements KmActorMstrDao {


 /** 
	* Logger for this class. Use logger.log(message) for logging. Refer to @link http://java.sun.com/j2se/1.4.2/Docs/guide/util/logging/overview.html for logging options and configuration.
 **/ 

 private static Logger logger = Logger.getLogger(KmActorMstrDaoImpl.class.toString());


    protected static final String SQL_INSERT_WITH_ID = "INSERT INTO KM_ACTORS (KM_ACTOR_ID, KM_ACTOR_NAME, CREATED_DT, CREATED_BY, UPDATED_DT, UPDATED_BY) VALUES (?, ?, ?, ?, ?, ?)";

    protected static final String SQL_SELECT = "SELECT KM_ACTORS.KM_ACTOR_ID, KM_ACTORS.KM_ACTOR_NAME, KM_ACTORS.CREATED_DT, KM_ACTORS.CREATED_BY, KM_ACTORS.UPDATED_DT, KM_ACTORS.UPDATED_BY FROM KM_ACTORS ";

    protected static final String SQL_UPDATE = "UPDATE KM_ACTORS SET KM_ACTOR_ID = ?, KM_ACTOR_NAME = ?, CREATED_DT = ?, CREATED_BY = ?, UPDATED_DT = ?, UPDATED_BY = ? WHERE KM_ACTOR_ID = ?";

    protected static final String SQL_DELETE = "DELETE FROM KM_ACTORS WHERE KM_ACTOR_ID = ?";

    public  int insert(KmActorMstr dto) throws KmActorsDaoException {

		logger.info("Entered insert for table KM_ACTORS");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        int rowsUpdated = 0;
        try {
        	StringBuffer query =new StringBuffer(SQL_INSERT_WITH_ID);
            //String sql = SQL_INSERT_WITH_ID;
            con = getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(query.append(" with ur").toString());
            int paramCount = 1;

            ps.setString(paramCount++,  dto.getKmActorId());
            ps.setString(paramCount++,  dto.getKmActorName());
            ps.setTimestamp(paramCount++,  dto.getCreatedDt());
            ps.setString(paramCount++,  dto.getCreatedBy());
            ps.setTimestamp(paramCount++,  dto.getUpdatedDt());
            ps.setString(paramCount++,  dto.getUpdatedBy());
            rowsUpdated=ps.executeUpdate();
            con.commit();

		logger.info("Row insertion successful on table:KM_ACTORS. Inserted:" + rowsUpdated  + " rows");

        } catch (SQLException e) {

		logger.error("SQL Exception occured while inserting." + "Exception Message: " + e.getMessage());
            throw new KmActorsDaoException("SQLException: " + e.getMessage(), e);
        } catch (Exception e) {

		logger.error("Exception occured while inserting." + "Exception Message: " + e.getMessage());
            throw new KmActorsDaoException("Exception: " + e.getMessage(), e);
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
				DBConnection.releaseResources(null,ps,rs);
            } catch (Exception e) {
				logger.error("DAO Exception occured while inserting." + "Exception Message: " + e.getMessage());
				throw new KmActorsDaoException("Exception: " + e.getMessage(), e);
            }
        }

        return rowsUpdated;
    }

    public  KmActorMstr findByPrimaryKey(String kmActorId) throws KmActorsDaoException {

		logger.info("Entered findByPrimaryKey for table:KM_ACTORS");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
			StringBuffer query =new StringBuffer(SQL_SELECT);
           // String sql = SQL_SELECT + " WHERE KM_ACTORS.KM_ACTOR_ID = ?";
           query.append(" WHERE KM_ACTORS.KM_ACTOR_ID = ?").toString();
            con = getConnection();
            ps = con.prepareStatement(query.append(" with ur ").toString());
            ps.setString(1, kmActorId);
            rs = ps.executeQuery();
			logger.info("Exit from findByPrimaryKey for table:KM_ACTORS");
            return fetchSingleResult(rs);
        } catch (SQLException e) {

		logger.error("SQL Exception occured while find." + "Exception Message: " + e.getMessage());
            throw new KmActorsDaoException("SQLException: " + e.getMessage(), e);
        } catch (Exception e) {

		logger.error("Exception occured while find." + "Exception Message: " + e.getMessage());
            throw new KmActorsDaoException("Exception: " + e.getMessage(), e);
        } finally {
            try {
				DBConnection.releaseResources(con,ps,rs);
            } catch (Exception e) {
				logger.error("DAO Exception occured while find." + "Exception Message: " + e.getMessage());
            }
        }

    }

    public  int update(KmActorMstr dto) throws KmActorsDaoException {

		logger.info("Entered update for table KM_ACTORS");

        Connection con = null;
        PreparedStatement ps = null;
        int numRows = -1;

        try {
			StringBuffer query =new StringBuffer(SQL_UPDATE);
           // String sql = SQL_UPDATE;
            con = getConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            if (dto.getKmActorId() ==null)
                ps.setNull(1, Types.DECIMAL);
            else
                ps.setString(1,  dto.getKmActorId());
            if (dto.getKmActorName() ==null)
                ps.setNull(2, Types.VARCHAR);
            else
                ps.setString(2,  dto.getKmActorName());
            if (dto.getCreatedDt() ==null)
                ps.setNull(3, Types.TIMESTAMP);
            else
                ps.setTimestamp(3,  dto.getCreatedDt());
            if (dto.getCreatedBy() ==null)
                ps.setNull(4, Types.DECIMAL);
            else
                ps.setString(4,  dto.getCreatedBy());
            if (dto.getUpdatedDt() ==null)
                ps.setNull(5, Types.TIMESTAMP);
            else
                ps.setTimestamp(5,  dto.getUpdatedDt());
            if (dto.getUpdatedBy() ==null)
                ps.setNull(6, Types.DECIMAL);
            else
                ps.setString(6,  dto.getUpdatedBy());
            ps.setString(7,  dto.getKmActorId());
            numRows = ps.executeUpdate();

		logger.info("Update successful on table:KM_ACTORS. Updated:" + numRows  + " rows");

        } catch (SQLException e) {

		logger.error("SQL Exception occured while update." + "Exception Message: " + e.getMessage());
            throw new KmActorsDaoException("SQLException: " + e.getMessage(), e);
        } catch (Exception e) {

		logger.error("Exception occured while update." + "Exception Message: " + e.getMessage());
            throw new KmActorsDaoException("Exception: " + e.getMessage(), e);
        } finally {
            try {
				DBConnection.releaseResources(con,ps,null);
                } catch (Exception e) {
				logger.error("DAO Exception occured while updating." + "Exception Message: " + e.getMessage());
				throw new KmActorsDaoException(e.getMessage(),e);
            }
        }
        return numRows;
    }

    public  int delete(String kmActorId) throws KmActorsDaoException {

		logger.info("Entered delete for table KM_ACTORS");

        Connection con = null;
        PreparedStatement ps = null;
        int numRows = -1;

        try {
			StringBuffer query =new StringBuffer(SQL_DELETE);
           // String sql = SQL_DELETE;
            con = getConnection();
            ps = con.prepareStatement(query.append(" with ur").toString());
            ps.setString(1, kmActorId);
            numRows = ps.executeUpdate();

		logger.info("Delete successful on table:KM_ACTORS. Deleted:" + numRows  + " rows");

        } catch (SQLException e) {

		logger.error("SQL Exception occured while delete." + "Exception Message: " + e.getMessage());
            throw new KmActorsDaoException("SQLException: " + e.getMessage(), e);
        } catch (Exception e) {

		logger.error("Exception occured while delete." + "Exception Message: " + e.getMessage());
            throw new KmActorsDaoException("Exception: " + e.getMessage(), e);
        } finally {
            try {
				DBConnection.releaseResources(con,ps,null);
                } catch (Exception e) {
				logger.error("DAO Exception occured while deleting." + "Exception Message: " + e.getMessage());
				throw new KmActorsDaoException("Exception: " + e.getMessage(), e);
            }
        }
        return numRows;
    }

    protected  KmActorMstr[] fetchMultipleResults(ResultSet rs) throws SQLException {
        ArrayList results = new ArrayList();
        while (rs.next()) {
            KmActorMstr dto = new KmActorMstr();
            populateDto(dto, rs);
            results.add(dto);
        }
        KmActorMstr retValue[] = new KmActorMstr[results.size()];
        results.toArray(retValue);
        return retValue;
    }


    protected  KmActorMstr fetchSingleResult(ResultSet rs) throws SQLException {
        if (rs.next()) {
            KmActorMstr dto = new KmActorMstr();
            populateDto(dto, rs);
            return dto;
        } else 
            return null;
    }

    protected static void populateDto(KmActorMstr dto, ResultSet rs) throws SQLException {
        dto.setKmActorId(rs.getString("KM_ACTOR_ID"));

        dto.setKmActorName(rs.getString("KM_ACTOR_NAME"));

        dto.setCreatedDt(rs.getTimestamp("CREATED_DT"));

        dto.setCreatedBy(rs.getString("CREATED_BY"));

        dto.setUpdatedDt(rs.getTimestamp("UPDATED_DT"));

        dto.setUpdatedBy(rs.getString("UPDATED_BY"));

    }


    private Connection getConnection() throws KmActorsDaoException {

		logger.info("Entered getConnection for operation on table:KM_ACTORS");

		try {
       	return DBConnection.getDBConnection();
		}catch(DAOException e) {

		logger.info("Exception Occured while obtaining connection.");

		throw new KmActorsDaoException("Exception while trying to obtain a connection",e);
    }

   }

	/* (non-Javadoc)
	 * @see com.ibm.km.dao.KmActorMstrDao#getActorName()
	 */
	public KmActorMstr[] getActorName() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
