/* 
 * KmCategoryMstrDao.java
 * Created: January 14, 2008
 * 
 * 
 */ 

package com.ibm.km.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.ibm.km.dto.KmCategoryMstr;
import com.ibm.km.exception.KmCategoryMstrDaoException;
import com.ibm.lms.exception.LMSException;


/** 
  * DAO for the <b>KM_CATEGORY_MSTR</b> database table.
  * Provides methods to perform insert, update, delete and select queries.
  * <br><pre>
  * Table: KM_CATEGORY_MSTR
  * ----------------------------------------------
  *     column: CATEGORY_ID        NUMBER null
  *     column: CATEGORY_NAME        VARCHAR2 null
  *     column: CATEGORY_DESC        VARCHAR2 null
  *     column: STATUS        CHAR null
  *     column: CIRCLE_ID        NUMBER null
  *     column: UPDATED_BY        NUMBER null
  *     column: CREATED_BY        NUMBER null
  *     column: CREATED_DT        DATE null
  *     column: UPDATE_DT        DATE null
  * 
  * Primary Key(s):  CATEGORY_ID
  * </pre>
  * 
  */ 
public interface KmCategoryMstrDao {

 /** 

	* Inserts a row in the KM_CATEGORY_MSTR table.
	* @param dto  DTO Object holding the data to be inserted.
	* @return  The no. of rows inserted. 
	* @throws KmCategoryMstrDaoException In case of an error
 **/ 

	public  int insert(KmCategoryMstr dto) throws LMSException;

 

 
 /** 

	* Returns a single row of the database based on the primary key information supplied. If there is no match, <code>null</code> is returned.
	* @param categoryId  The categoryId value for which the row needs to be retrieved.
	* @return DTO Object representing a single row of the table based on the primary key information supplied.If there is no match, <code>null</code> is returned.
 **/ 

 
	/**
	 * @param circleId
	 * @return arraylist
	 */
	public ArrayList getCategories(String[] circleId)throws LMSException;



/**
 * @param categoryName
 * @param circleId
 * @return true or false
 */
public boolean checkOnCategoryName(String categoryName, String circleId) throws LMSException;




/**
 * @param circleId
 * @return
 */
public HashMap getCategoryMap(String circleId) throws LMSException;

/**
 * @param circleId
 * @return
 */
public HashMap getCategoryMapElements(String circleId, String favCategoryId) throws LMSException;



/**
 * @param circleId
 * @return no.of categories
 */
public int getNoOfCategories(String circleId)throws LMSException;

}
