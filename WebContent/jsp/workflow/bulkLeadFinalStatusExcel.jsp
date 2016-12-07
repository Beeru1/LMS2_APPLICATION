<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">

<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition",
			"attachment;filename=LeadFinalStatusReport.xls");

%>
</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	
		<tr class="text white" >
		<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5"><bean:message
			    key="viewAllUser.SNO" /></span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Lead Id</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Customer Name</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Product Name</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Contact No </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="15%"><span class="mTop5">Circle Name</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="15%"><span class="mTop5">Status</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Sub Status</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Caf Number</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="25" bgcolor="yellow"><span class="mLeft5 mTop5">Remarks </span></th>
		
		</tr>
		<logic:present name="leadFinalStatusLeads" scope="request">		
		<logic:empty  scope="request" name="leadFinalStatusLeads">
			<TR class="lightBg">
				<TD class="text" align="center" colspan="6"><bean:message  key="viewAllUser.NotFound" /></TD>
			</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="leadFinalStatusLeads" scope="request">	
		<logic:notEmpty name="leadFinalStatusLeads" scope="request">
			<logic:iterate id="report" indexId="i" name="leadFinalStatusLeads" scope="request" type="com.ibm.lms.dto.LeadDetailsDTO" >
				
			<TR class="lightBg">
			<TD class="txt" align="center"><%=(i.intValue() + 1)%>.</TD>
			<TD class="txt" align="center"><bean:write name="report" property="leadId" /></TD>
			<TD class="txt" align="center"><bean:write name="report" property="customerName" /></TD>
			<TD class="txt" align="center"><bean:write name="report" property="productName" /></TD>
			<TD class="txt" align="center"><bean:write name="report" property="contactNo" /></TD>
			<TD class="txt" align="center"><bean:write name="report" property="circleName" /></TD>
			
			
			<TD class="txt" align="center"></TD>
			<TD class="txt" align="center"></TD>
			<TD class="txt" align="center"></TD>
			<TD class="txt" align="center"></TD>
			
			</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
