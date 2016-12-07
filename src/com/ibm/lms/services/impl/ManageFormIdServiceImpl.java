/**
 * 
 */
package com.ibm.lms.services.impl;

import java.util.List;

import com.ibm.lms.dao.FormIdDao;
import com.ibm.lms.dao.impl.FormIdDaoImpl;
import com.ibm.lms.dto.FormIdDto;
import com.ibm.lms.exception.DAOException;
import com.ibm.lms.services.ManageFormIdService;

/**
 * @author Nehil Parashar
 *
 */
public class ManageFormIdServiceImpl implements ManageFormIdService 
{
	@Override
	public long createFid(FormIdDto fiddto) throws DAOException 
	{
		FormIdDao fiddao = FormIdDaoImpl.formIdDaoInstance();//changed by srikant new FormIdDaoImpl();
		return fiddao.createFid(fiddto);
	}
	
	
	public void deleteData(FormIdDto fiddto) throws Exception
	{
		FormIdDao fiddao = FormIdDaoImpl.formIdDaoInstance();//changed by srikant new FormIdDaoImpl();
		fiddao.deleteFidData(fiddto);
	}
	
	
	public FormIdDto getData(long fid) throws Exception
	{
		FormIdDao fiddao = FormIdDaoImpl.formIdDaoInstance();//changed by srikant new FormIdDaoImpl();
		return fiddao.getFidData(fid);
	}


	public void updateData(FormIdDto fiddto) throws Exception 
	{
		FormIdDao fiddao = FormIdDaoImpl.formIdDaoInstance();//changed by srikant new FormIdDaoImpl();
		fiddao.updateFidData(fiddto);	
	}
	
	
	public List<FormIdDto> downloadExcel() throws Exception
	{
		FormIdDao fiddao = FormIdDaoImpl.formIdDaoInstance();//changed by srikant new FormIdDaoImpl();
		return fiddao.downloadExcel();
	}
}
