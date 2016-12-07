/* 
 * KmActorsDao.java
 * Created: January 14, 2008
 * 
 * 
 */ 

package com.ibm.km.dao;

import com.ibm.km.dto.KmActorMstr;
import com.ibm.km.exception.KmActorsDaoException;


/** 
  * DAO for the <b>KM_ACTORS</b> database table.
  * Provides methods to perform insert, update, delete and select queries.
  * <br><pre>
  * Table: KM_ACTORS
  * ----------------------------------------------
  *     column: KM_ACTOR_ID        NUMBER null
  *     column: KM_ACTOR_NAME        VARCHAR2 null
  *     column: CREATED_DT        DATE null
  *     column: CREATED_BY        NUMBER null
  *     column: UPDATED_DT        DATE null
  *     column: UPDATED_BY        NUMBER null
  * 
  * Primary Key(s):  KM_ACTOR_ID
  * </pre>
  * 
  */ 
public interface KmActorsDao {

 /** 

	* Inserts a row in the KM_ACTORS table.
	* @param dto  DTO Object holding the data to be inserted.
	* @return  The no. of rows inserted. 
	* @throws KmActorsDaoException In case of an error
 **/ 

    public  int insert(KmActorMstr dto) throws KmActorsDaoException;

 /** 

	* Updates a row in the KM_ACTORS table.
	* The Primary Key Object determines wich row gets updated.
	* @param dto  DTO Object holding the data to be updated. The primary key vales determine which row will be updated
	* @return  The no. of rows updated. 
	* @throws KmActorsDaoException In case of an error
 **/ 

    public  int update(KmActorMstr dto) throws KmActorsDaoException;

 /** 

	* Deletes a row in the database based on the primary key supplied.
	* @param kmActorId  The kmActorId value for which the row needs to be deleted
	* @return  The no. of rows deleted.
	* @throws KmActorsDaoException In case of an error
 **/ 

    public  int delete(String kmActorId) throws KmActorsDaoException;

 /** 

	* Returns a single row of the database based on the primary key information supplied. If there is no match, <code>null</code> is returned.
	* @param kmActorId  The kmActorId value for which the row needs to be retrieved.
	* @return DTO Object representing a single row of the table based on the primary key information supplied.If there is no match, <code>null</code> is returned.
 **/ 

    public  KmActorMstr findByPrimaryKey(String kmActorId) throws KmActorsDaoException;

}
