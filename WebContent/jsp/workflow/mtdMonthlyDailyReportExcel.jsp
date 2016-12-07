<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">

<%

String filename="";

if(request.getAttribute("Report_FLAG") !=null && "Daily".equalsIgnoreCase(request.getAttribute("Report_FLAG").toString())){
filename=	"SiteLeadDailyReport";
	
}else if(request.getAttribute("Report_FLAG") !=null && "Monthly".equalsIgnoreCase(request.getAttribute("Report_FLAG").toString())){

	filename="SiteLeadMonthlyReport";
	
}else if(request.getAttribute("Report_FLAG") !=null && "Quarterly".equalsIgnoreCase(request.getAttribute("Report_FLAG").toString())){

	filename="SiteLeadQuarterlyReport";
	
}
	
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition","attachment;filename="+filename+".xls");

%>
</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	
		<tr class="text white" >
		
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">NAME</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"width="5%" ><span class="mLeft5 mTop5">LEAD COUNT</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"width="5%" ><span class="mLeft5 mTop5">PERCENTAGE </span></th>
		</tr>
		<logic:present name="reportList" scope="request">		
		<logic:empty  scope="request" name="reportList">
			<TR class="lightBg"><TD class="text" align="center" colspan="3"><bean:message
				
					key="assignmentDownload.NotFound" /></TD>
				
			</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="reportList" scope="request">	
		<logic:notEmpty name="reportList" scope="request">
			
			<logic:iterate id="report"  name="reportList" scope="request"   >
				
				<TR class="lightBg">
					 <TD class="txt" align="center"><bean:write name="report" property="columnName" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="count" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="percent" /></TD>
						
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
