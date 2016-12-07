package com.ibm.lms.common;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.ibm.lms.exception.DAOException;

/**
 * Class to manage DB connectins.
 * @author ibm
 * Use getDBConnection() to get a connection from application DB.
 * Use getOLMSConnection() to get a connection from HRMS Oracle DB.
 */

public class DBConnection {
	
	private static Logger logger = Logger.getLogger(DBConnection.class);
	private static DataSource mem_o_datasource;
	private static DataSource hrms_datasource;
	private static int counter=0;
	
	/**
	 * The default private constructor. 
	 */
	public DBConnection() {
	}
	/**
	 * This method does the lookup of the application datasource and store it in a memeber variable to be 
	 * used later, to avoid doing lookups again and again
	 * @exception DAOException - This exception is thrown in case data source cannot be looked up
	 */
	private  static void getDataSource() throws DAOException {
		try {
			synchronized (DBConnection.class){
				if (mem_o_datasource == null) {	
					InitialContext loc_o_ic = new InitialContext();
					mem_o_datasource =
						(DataSource) loc_o_ic.lookup(
							PropertyReader.getValue("DATASOURCE_LOOKUP_NAME"));	
				}
			}
		} catch (NamingException namingException) {
			logger.error("Lookup of Data Source Failed. Reason:" + namingException.getMessage());
			throw new DAOException("Exception Occured while data source lookup.",namingException);
		}
	}
	
	/**
	 * This method does the lookup of the HRMS datasource and store it in a memeber variable to be 
	 * used later, to avoid doing lookups again and again.
	 * @exception DAOException - This exception is thrown in case data source cannot be looked up
	 */
	private  static void getHRMSDataSource() throws DAOException {
		try {
			synchronized (DBConnection.class){
				if (hrms_datasource == null) {
					InitialContext loc_o_ic = new InitialContext();
					hrms_datasource =(DataSource) loc_o_ic.lookup(PropertyReader.getValue("OLMSDS_LOOKUP_NAME"));					
				}
			}
		} catch (NamingException namingException) {
			logger.error("Lookup of LDAP Data Source Failed. Reason:" + namingException.getMessage());
			throw new DAOException("Exception Occured while data source lookup.",namingException);
		}
	}
	
	/**
	 * This method returns the application DB connection using datasource.
	 * @return Connection - The DB connection instance
	 * @exception DAOException - This exception is thrown in case connection is not established
	 */
	public static  Connection getDBConnection() throws com.ibm.lms.exception.DAOException {
		Connection dbConnection = null;
		try {
			counter++;
			if (mem_o_datasource == null) {
					getDataSource();
				}
				dbConnection =
				mem_o_datasource.getConnection();
		} catch (SQLException sqlException) {
			logger.error("Could Not Obtain Connection. Reason:" + sqlException.getMessage() + ". Error Code:" + sqlException.getErrorCode());
			throw new DAOException("Exception Occured while obtaining Connection.",sqlException);
		}
		return dbConnection;
	}
	
	/**
	 * This method returns the HRMS DB connection using datasource.
	 * @return Connection - The HRMS database connection instance.
	 * @exception DAOException - This exception is thrown in case connection is not established.
	 */
	public static  Connection getOLMSConnection() throws com.ibm.lms.exception.DAOException {
		Connection oracleConnection = null;
		try {
			
			if (hrms_datasource == null) {
				getHRMSDataSource();
				}
			oracleConnection =
				hrms_datasource.getConnection();
			logger.info("Connection Obtained.");
		} catch (SQLException sqlException) {
			logger.error("Couldn't connected to HRMS server. Reason:" + sqlException.getMessage() + ". Error Code:" + sqlException.getErrorCode());
			throw new DAOException("Couldn't connected to HRMS server.");
		}
		return oracleConnection;
	}
	
	public static void releaseResources(
		Connection connection,
		Statement statement,
		ResultSet resultSet)
		throws DAOException {
		try {
						counter--;
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (SQLException sqlException) {
			logger.error("Closing of Resources Failed. Reason:" + sqlException.getMessage()+". Error Code:"+ sqlException.getErrorCode());
			throw new DAOException("errors.dbconnection.close_connection");
		}
	}
}
