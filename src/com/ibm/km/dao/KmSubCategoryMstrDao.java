/* 
 * KmSubCategoryMstrDao.java
 * Created: January 14, 2008
 * 
 * 
 */ 

package com.ibm.km.dao;

import java.util.ArrayList;

import com.ibm.km.dto.KmSubCategoryMstr;
import com.ibm.km.exception.KmSubCategoryMstrDaoException;
import com.ibm.lms.exception.LMSException;


/** 
  * DAO for the <b>KM_SUB_CATEGORY_MSTR</b> database table.
  * Provides methods to perform insert, update, delete and select queries.
  * <br><pre>
  * Table: KM_SUB_CATEGORY_MSTR
  * ----------------------------------------------
  *     column: SUB_CATEGORY_ID        NUMBER null
  *     column: SUB_CATEGORY_NAME        VARCHAR2 null
  *     column: SUB_CATEGORY_DESC        VARCHAR2 null
  *     column: CATEGORY_ID        NUMBER null
  *     column: STATUS        CHAR null
  *     column: CIRCLE_ID        NUMBER null
  *     column: UPDATED_BY        NUMBER null
  *     column: CREATED_BY        NUMBER null
  *     column: CREATED_DT        DATE null
  *     column: UPDATE_DT        DATE null
  * 
  * Primary Key(s):  SUB_CATEGORY_ID
  * </pre>
  * 
  */ 
public interface KmSubCategoryMstrDao {

 /** 

	* Inserts a row in the KM_SUB_CATEGORY_MSTR table.
	* @param dto  DTO Object holding the data to be inserted.
	* @return  The no. of rows inserted. 
	* @throws KmSubCategoryMstrDaoException In case of an error
 **/ 

    public  int insert(KmSubCategoryMstr dto) throws LMSException;

  	/**
  	* @param subCategoryName
  	* @param categoryId
  	* @return true or false
  	*/
 	public boolean checkOnSubCategoryName(String subCategoryName, String categoryId)throws LMSException;

	/**
	 * @param categoryId
	 * @return categorylist
	 */
	public ArrayList getSubCategories(String categoryId)throws LMSException;

}

