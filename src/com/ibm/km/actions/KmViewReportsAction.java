package com.ibm.km.actions;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.km.dto.KmContentExpireReportDto;
import com.ibm.km.dto.KmContentStatusReportDto;
import com.ibm.km.dto.KmDocumentHitsCountsReportDto;
import com.ibm.km.dto.KmIportalFeedbackReportDto;
import com.ibm.km.dto.KmObsoleteIdsReportDto;
import com.ibm.km.dto.KmScrollerMstr;
import com.ibm.km.dto.KmUserLoginStatusReportDto;
import com.ibm.km.dto.NoUsageReportDto;
import com.ibm.km.forms.KmViewReportsFormBean;

public class KmViewReportsAction extends DispatchAction {

	public ActionForward initViewReports(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		KmViewReportsFormBean reportFormBean = (KmViewReportsFormBean) form;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String date = sdf.format(new java.util.Date().getTime() - 24*60*60*1000);
		//logger.info(date.toString());
		date = date.substring(0,10);
		reportFormBean.setReportDate(date);
		
		//logger.info("Inside initViewReports!!!!");

		return mapping.findForward("success");
	}

	public ActionForward displayReports(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//logger.info("Inside displayReports!!!!");
		KmViewReportsFormBean formBean = (KmViewReportsFormBean) form;
		//logger.info("reportType==" + formBean.getReportType());
		String forward = null;
		ArrayList list = new ArrayList();

		String date = formBean.getReportDate();

		if (formBean.getReportType().equalsIgnoreCase("noUsageReport")) {
			list = readNoUsageReport(date);
			forward = "noUsageReport";
		} else if (formBean.getReportType().equalsIgnoreCase(
				"scrollerUpdationReport")) {
			list = readScrollerUpdationReport(date);
			forward = "scrollerUpdationReport";
		} else if (formBean.getReportType().equalsIgnoreCase(
				"contentStatusReport")) {
			list = readContentStatusReport(date);
			forward = "contentStatusReport";
		} else if (formBean.getReportType().equalsIgnoreCase(
				"obsoleteIdsReport")) {
			list = readObsoleteIdsReport(date);
			forward = "obsoleteIdsReport";
		} else if (formBean.getReportType().equalsIgnoreCase(
				"contentExpiryReport")) {
			list = readContentExpiryReport(date);
			forward = "contentExpiryReport";
		} else if (formBean.getReportType().equalsIgnoreCase(
				"documentHitCountReport")) {
			list = readDocumentHitCountReport(date);
			forward = "documentHitCountReport";
		} else if (formBean.getReportType().equalsIgnoreCase(
				"iportalFeedbackReport")) {
			list = readIportalFeedbackReport(date);
			forward = "iportalFeedbackReport";
		} else if (formBean.getReportType().equalsIgnoreCase(
				"userLoginStatusReport")) {
			list = readUserLoginStatusReport(date);
			forward = "userLoginStatusReport";
		}
		
		//logger.info("\n list "+list);
		
		if(list == null)
		{
			ActionMessages messages = new ActionMessages();
		    messages.add("msg1",new ActionMessage("report.not.generated"));
		    saveMessages(request, messages);
			forward = "success";
		}	

		formBean.setReportList(list);
		formBean.setInitStatus("false");
		return mapping.findForward(forward);
	}

	public ArrayList readNoUsageReport(String date) {
		ArrayList list = new ArrayList();
		ResourceBundle bundle = ResourceBundle
				.getBundle("ApplicationResources");
		String path = bundle.getString("reports.path.noUsageReport");

		String thisLine;
		try {
			File file = new File(path + "ListofNoUsageReport_"+date + ".csv");
			FileReader f = new FileReader(file);
			BufferedReader br = new BufferedReader(f);
			DataInputStream inputStream = new DataInputStream(
					new FileInputStream(file));

			while ((thisLine = br.readLine()) != null) {
				NoUsageReportDto dto = new NoUsageReportDto();
				String strar[] = thisLine.split(",");

				for (int j = 0; j < strar.length; j++) {
				//	//logger.info("strar[j]==" + strar[j]);
					switch (j) {
					// case 0: //dto.setUserId(strar[j]);
					// break;
					case 0:
						dto.setUserId(strar[j]);
						break;
					case 1:
						dto.setLob(strar[j]);
						break;
					case 2:
						dto.setRole(strar[j]);
						break;
					case 3:
						dto.setBusinessSegment(strar[j]);
						break;
					case 4:
						dto.setProcess(strar[j]);
						break;
					case 5:
						dto.setActivity(strar[j]);
						break;
					case 6:
						dto.setCircle(strar[j]);
						break;
					case 7:
						dto.setPartner(strar[j]);
						break;
					case 8:
						dto.setLocation(strar[j]);
						break;
					case 9:
						dto.setLastLoginDate(strar[j]);
						break;
					case 10:
						dto.setLastLoginTime(strar[j]);
						break;
					case 11:
						dto.setNotLoggedinSince(strar[j]);
						break;
					default:
						break;
					}
				}

				// arList.add(al);
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList readScrollerUpdationReport(String date) {
		ArrayList list = new ArrayList();
		
		ResourceBundle bundle = ResourceBundle
				.getBundle("ApplicationResources");
		String path = bundle.getString("reports.path.scrollerUpdationReport");

		String thisLine;
		try {
			File file = new File(path + "ListofScrollerUpdation_"+date + ".csv");
			
			if(!file.exists())
			{
				list = null;
				return list;
			}	
			
			FileReader f = new FileReader(file);
			BufferedReader br = new BufferedReader(f);
			
			DataInputStream inputStream = new DataInputStream(
					new FileInputStream(file));

			while ((thisLine = br.readLine()) != null) {
				KmScrollerMstr dto = new KmScrollerMstr();
				String strar[] = thisLine.split(",");

				for (int j = 0; j < strar.length; j++) {
					////logger.info("strar[j]==" + strar[j]);
					switch (j) {
					// case 0: //dto.setUserId(strar[j]);
					// break;
					case 0:
						dto.setMessage(strar[j]);
						break;
					case 1:
						dto.setStartDate(strar[j]);
						break;
					case 2:
						dto.setStartTime(strar[j]);
						break;
					case 3:
						dto.setEndDate(strar[j]);
						break;
					case 4:
						dto.setEndTime(strar[j]);
						break;
					case 5:
						dto.setCircles(strar[j]);
						break;
					default:
						break;
					}
				}

				// arList.add(al);
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList readContentStatusReport(String date) {
		ArrayList list = new ArrayList();
		ResourceBundle bundle = ResourceBundle
				.getBundle("ApplicationResources");
		String path = bundle.getString("reports.path.contentStatusReport");

		String thisLine;
		try {
			File file = new File(path + "ListofContentStatusReport_"+date + ".csv");
			
			if(!file.exists())
			{
				list = null;
				return list;
			}
			
			FileReader f = new FileReader(file);
			BufferedReader br = new BufferedReader(f);
			DataInputStream inputStream = new DataInputStream(
					new FileInputStream(file));

			while ((thisLine = br.readLine()) != null) {
				KmContentStatusReportDto dto = new KmContentStatusReportDto();
				String strar[] = thisLine.split(",");

				for (int j = 0; j < strar.length; j++) {
					////logger.info("strar[j]==" + strar[j]);
					switch (j) {
					// case 0: //dto.setUserId(strar[j]);
					// break;
					case 0:
						dto.setLob(strar[j]);
						break;
					case 1:
						dto.setCircle(strar[j]);
						break;
					case 2:
						dto.setDocumentId(strar[j]);
						break;
					case 3:
						dto.setDocumentName(strar[j]);
						break;
					case 4:
						dto.setUploadedDate(strar[j]);
						break;
					case 5:
						dto.setUploadedTime(strar[j]);
						break;
					case 6:
						dto.setDocumentPath(strar[j]);
						break;
					case 7:
						dto.setUploadedBy(strar[j]);
						break;
					case 8:
						dto.setPublishingEndDate(strar[j]);
						break;
					default:
						break;
					}
				}

				// arList.add(al);
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList readObsoleteIdsReport(String date)
			throws FileNotFoundException, IOException {
		ArrayList list = new ArrayList();
		ResourceBundle bundle = ResourceBundle
				.getBundle("ApplicationResources");
		String path = bundle.getString("reports.path.obsoleteIdsReport");
		String thisLine;
		try {
			File file = new File(path + "ListofObsoleteIds_"+date + ".csv");
			
			if(!file.exists())
			{
				list = null;
				return list;
			}
			
			FileReader f = new FileReader(file);
			BufferedReader br = new BufferedReader(f);
			DataInputStream inputStream = new DataInputStream(
					new FileInputStream(file));

			while ((thisLine = br.readLine()) != null) {
				KmObsoleteIdsReportDto dto = new KmObsoleteIdsReportDto();
				String strar[] = thisLine.split(",");

				for (int j = 0; j < strar.length; j++) {
					////logger.info("strar[j]==" + strar[j]);
					switch (j) {
					case 0:
						dto.setUserId(strar[j]);
						break;
					case 1:
						dto.setLob(strar[j]);
						break;
					case 2:
						dto.setRole(strar[j]);
						break;
					case 3:
						dto.setBusinessSegment(strar[j]);
						break;
					case 4:
						dto.setProcess(strar[j]);
						break;
					case 5:
						dto.setActivity(strar[j]);
						break;
					case 6:
						dto.setCircle(strar[j]);
						break;
					case 7:
						dto.setPartner(strar[j]);
						break;
					case 8:
						dto.setLocation(strar[j]);
						break;
					default:
						break;
					}
				}

				// arList.add(al);
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList readContentExpiryReport(String date) {
		ArrayList list = new ArrayList();
		ResourceBundle bundle = ResourceBundle
				.getBundle("ApplicationResources");
		String path = bundle.getString("reports.path.contentToBeExpiredReport");
		String thisLine;
		try {
			File file = new File(path +"GoingToBeExpired_"+ date + ".csv");
			
			if(!file.exists())
			{
				list = null;
				return list;
			}
			
			FileReader f = new FileReader(file);
			BufferedReader br = new BufferedReader(f);
			DataInputStream inputStream = new DataInputStream(
					new FileInputStream(file));

			while ((thisLine = br.readLine()) != null) {
				KmContentExpireReportDto dto = new KmContentExpireReportDto();
				String strar[] = thisLine.split(",");

				for (int j = 0; j < strar.length; j++) {
					////logger.info("strar[j]==" + strar[j]);
					switch (j) {
					// case 0: //dto.setUserId(strar[j]);
					// break;
					case 0:
						dto.setLob(strar[j]);
						break;
					case 1:
						dto.setCircle(strar[j]);
						break;
					case 2:
						dto.setDocumentPath(strar[j]);
						break;
					// added by ashutosh
					case 3:
						dto.setDocumentId(strar[j]);
						break;
					case 4:
						dto.setDocumentName(strar[j]);
						break;
					case 5:
						dto.setUploadedDate(strar[j]);
						break;
					case 6:
						dto.setUploadedBy(strar[j]);
						break;	
					case 7:
						dto.setPublishingEndDate(strar[j]);
						break;
					default:
						break;				
					}
				}

				// arList.add(al);
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList readDocumentHitCountReport(String date)
			throws FileNotFoundException, IOException {
		ArrayList list = new ArrayList();
		ResourceBundle bundle = ResourceBundle
				.getBundle("ApplicationResources");
		String path = bundle.getString("reports.path.documentHitCountReport");
		String thisLine;
		try {
			File file = new File(path +"DocumentHitCountReport_"+ date + ".csv");
			
			if(!file.exists())
			{
				list = null;
				return list;
			}
			
			FileReader f = new FileReader(file);
			BufferedReader br = new BufferedReader(f);
			DataInputStream inputStream = new DataInputStream(
					new FileInputStream(file));

			while ((thisLine = br.readLine()) != null) {
				KmDocumentHitsCountsReportDto dto = new KmDocumentHitsCountsReportDto();
				String strar[] = thisLine.split(",");

				for (int j = 0; j < strar.length; j++) {
					////logger.info("strar[j]==" + strar[j]);
					switch (j) {
					// case 0: //dto.setUserId(strar[j]);
					// break;
					case 0:
						dto.setLob(strar[j]);
						break;
					case 1:
						dto.setCircle(strar[j]);
						break;
					case 2:
						dto.setDocumentPath(strar[j]);
						break;
					case 3:
						dto.setDocumentName(strar[j]);
						break;
					case 4:
						dto.setDocumentId(strar[j]);
						break;
					case 5:
						dto.setUploadedTime(strar[j]);
						break;
					case 6:
						dto.setUploadedDate(strar[j]);
						break;
					case 7:
						dto.setLastUpdateDate(strar[j]);
						break;
					case 8:
						dto.setUploadedBy(strar[j]);
						break;
					case 9:
						dto.setNoofHits(strar[j]);
						break;
					default:
						break;
					}
				}

				// arList.add(al);
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList readIportalFeedbackReport(String date)
			throws FileNotFoundException, IOException {
		ArrayList list = new ArrayList();
		ResourceBundle bundle = ResourceBundle
				.getBundle("ApplicationResources");
		String path = bundle.getString("reports.path.iportalFeedbackReport");
		String thisLine;
		try {
			File file = new File(path + "ListofIPortalFeedbackReport_"+date + ".csv");
			
			if(!file.exists())
			{
				list = null;
				return list;
			}
			
			FileReader f = new FileReader(file);
			BufferedReader br = new BufferedReader(f);
			DataInputStream inputStream = new DataInputStream(
					new FileInputStream(file));

			while ((thisLine = br.readLine()) != null) {
				KmIportalFeedbackReportDto dto = new KmIportalFeedbackReportDto();
				String strar[] = thisLine.split(",");

				for (int j = 0; j < strar.length; j++) {
					////logger.info("strar[j]==" + strar[j]);
					switch (j) {
					// case 0: //dto.setUserId(strar[j]);
					// break;

					case 0:
						dto.setDateofFeedcak(strar[j]);
						break;
					case 1:
						dto.setTimeofFeedback(strar[j]);
						break;
					case 2:
						dto.setUserLoginId(strar[j]);
						break;
					case 3:
						dto.setLob(strar[j]);
						break;
					case 4:
						dto.setRole(strar[j]);
						break;
					case 5:
						dto.setBusinessSegment(strar[j]);
						break;
					case 6:
						dto.setProcess(strar[j]);
						break;
					case 7:
						dto.setActivity(strar[j]);
						break;
					case 8:
						dto.setCircle(strar[j]);
						break;
					case 9:
						dto.setPartnerName(strar[j]);
						break;
					case 10:
						dto.setLocation(strar[j]);
						break;
					case 11:
						dto.setFeedbackDesc(strar[j]);
						break;
					case 12:
						dto.setClosureDate(strar[j]);
						break;
					case 13:
						dto.setClosureTime(strar[j]);
						break;
					case 14:
						dto.setKmSpoccId(strar[j]);
						break;
					case 15:
						dto.setClosureRemarks(strar[j]);
						break;
					case 16:
						dto.setActionTaken(strar[j]);
						break;
					case 17:
						dto.setClosureTAT(strar[j]);
						break;

					default:
						break;
					}
				}

				// arList.add(al);
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public ArrayList readUserLoginStatusReport(String date)
			throws FileNotFoundException, IOException {
		ArrayList list = new ArrayList();
		ResourceBundle bundle = ResourceBundle
				.getBundle("ApplicationResources");
		String path = bundle.getString("reports.path.userLoginStatusReport");
		String thisLine;
		try {
			File file = new File(path + "ListofUserLoginStatusReport_"+date + ".csv");
			
			if(!file.exists())
			{
				list = null;
				return list;
			}
			
			FileReader f = new FileReader(file);
			BufferedReader br = new BufferedReader(f);
			DataInputStream inputStream = new DataInputStream(
					new FileInputStream(file));

			while ((thisLine = br.readLine()) != null) {
				KmUserLoginStatusReportDto dto = new KmUserLoginStatusReportDto();
				String strar[] = thisLine.split(",");

				for (int j = 0; j < strar.length; j++) {
					////logger.info("strar[j]==" + strar[j]);
					switch (j) {
					// case 0: //dto.setUserId(strar[j]);
					// break;

					case 0:
						dto.setUserLoginId(strar[j]);
						break;
					case 1:
						dto.setLob(strar[j]);
						break;
					case 2:
						dto.setProcess(strar[j]);
						break;
					case 3:
						dto.setBusinessSegment(strar[j]);
						break;
					case 4:
						dto.setCircle(strar[j]);
						break;
					case 5:
						dto.setPartnerName(strar[j]);
						break;
					case 6:
						dto.setActivity(strar[j]);
						break;
					case 7:
						dto.setLocation(strar[j]);
						break;
					case 8:
						dto.setRole(strar[j]);
						break;
					case 9:
						dto.setPbxId(strar[j]);
						break;
					case 10:
						dto.setTotalLoginTime(strar[j]);
						break;
					default:
						break;
					}
				}

				// arList.add(al);
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public String getCurrentYearMonth(String format) {
		Date todaysDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String year_month = formatter.format(todaysDate);
		Calendar cal = Calendar.getInstance();
		int todays_date_num = cal.get(cal.DATE);
		if (todays_date_num == 1) {

			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(todaysDate);
			gc.add(Calendar.DAY_OF_YEAR, -1);
			Date result = gc.getTime();
			year_month = formatter.format(result);
		}
		//logger.info("year_month==" + year_month);
		return year_month;
	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		ArrayList<ArrayList<String>> arList = new ArrayList<ArrayList<String>>();
		ArrayList<String> al = null;

		String thisLine;
		File file = new File("C:/km_reports/noUsageReport/2012 Nov.csv");
		
		FileReader f = new FileReader(file);
		BufferedReader br = new BufferedReader(f);
		DataInputStream inputStream = new DataInputStream(new FileInputStream(
				file));

		while ((thisLine = br.readLine()) != null) {
			al = new ArrayList<String>();
			String strar[] = thisLine.split(",");

			for (int j = 0; j < strar.length; j++) {
				// My Attempt (BELOW)
				// String edit = strar[j].replace('\n', ' ');
				//logger.info(strar[j]);
				al.add(strar[j]);
				////logger.info("strar[j]==" + strar[j]);
			}

			arList.add(al);			
		}
		/*
		 * response.setHeader("Content-Disposition",
		 * "attachment; filename=UserDetails.xls");
		 * 
		 * ServletOutputStream out = response.getOutputStream();
		 * 
		 * workbook.write(out);
		 * 
		 * out.flush();
		 * 
		 * out.close();
		 */

	}

}
