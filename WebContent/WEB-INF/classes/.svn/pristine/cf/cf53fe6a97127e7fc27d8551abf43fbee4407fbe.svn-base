/**
 * 
 */
package com.ibm.lms.dao;

import java.util.List;

import com.ibm.lms.dto.FormIdDto;
import com.ibm.lms.exception.DAOException;

/**
 * @author Nehil Parashar
 *
 */
public interface FormIdDao 
{
	long createFid(FormIdDto fiddto) throws DAOException;
	long deleteFidData(FormIdDto fiddto) throws DAOException; 
	FormIdDto getFidData(long fid) throws DAOException;
	long updateFidData(FormIdDto fiddto) throws DAOException;
	List<FormIdDto> downloadExcel() throws Exception;
}
