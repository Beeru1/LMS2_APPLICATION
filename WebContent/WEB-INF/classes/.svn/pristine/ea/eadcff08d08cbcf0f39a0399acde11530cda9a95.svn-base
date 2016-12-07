/* 
 * KmRuleMstrDao.java
 * Created: January 14, 2008
 * 
 * 
 */ 

package com.ibm.km.dao;

import com.ibm.km.dto.KmRuleMstr;
import com.ibm.km.exception.KmRuleMstrDaoException;


/** 
  * DAO for the <b>KM_RULE_MSTR</b> database table.
  * Provides methods to perform insert, update, delete and select queries.
  * <br><pre>
  * Table: KM_RULE_MSTR
  * ----------------------------------------------
  *     column: RULE_ID        NUMBER null
  *     column: RULE_NAME        VARCHAR2 null
  *     column: RULE_DESC        VARCHAR2 null
  *     column: CIRCLE_ID        NUMBER null
  *     column: CREATED_DT        DATE null
  *     column: CREATED_BY        NUMBER null
  *     column: UPDATED_DT        DATE null
  *     column: UPDATED_BY        NUMBER null
  *     column: STATUS        CHAR null
  * 
  * Primary Key(s):  RULE_ID
  * </pre>
  * 
  */ 
public interface KmRuleMstrDao {

 /** 

	* Inserts a row in the KM_RULE_MSTR table.
	* @param dto  DTO Object holding the data to be inserted.
	* @return  The no. of rows inserted. 
	* @throws KmRuleMstrDaoException In case of an error
 **/ 

    public  int insert(KmRuleMstr dto) throws KmRuleMstrDaoException;

 /** 

	* Updates a row in the KM_RULE_MSTR table.
	* The Primary Key Object determines wich row gets updated.
	* @param dto  DTO Object holding the data to be updated. The primary key vales determine which row will be updated
	* @return  The no. of rows updated. 
	* @throws KmRuleMstrDaoException In case of an error
 **/ 

    public  int update(KmRuleMstr dto) throws KmRuleMstrDaoException;

 /** 

	* Deletes a row in the database based on the primary key supplied.
	* @param ruleId  The ruleId value for which the row needs to be deleted
	* @return  The no. of rows deleted.
	* @throws KmRuleMstrDaoException In case of an error
 **/ 

    public  int delete(String ruleId) throws KmRuleMstrDaoException;

 /** 

	* Returns a single row of the database based on the primary key information supplied. If there is no match, <code>null</code> is returned.
	* @param ruleId  The ruleId value for which the row needs to be retrieved.
	* @return DTO Object representing a single row of the table based on the primary key information supplied.If there is no match, <code>null</code> is returned.
 **/ 

    public  KmRuleMstr findByPrimaryKey(String ruleId) throws KmRuleMstrDaoException;

}
