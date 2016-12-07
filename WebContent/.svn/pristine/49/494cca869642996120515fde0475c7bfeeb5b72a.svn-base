<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%>
<%@page import="com.ibm.lms.dto.UserMstr"%>
<%@page import="com.ibm.lms.common.PropertyReader"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">

<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition",
			"attachment;filename=AssignedLeads.xls");

%>
</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	
		<tr class="text white" >
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5"><bean:message key="viewAllUser.SNO" /></span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Customer Name</span></th>
			  <% UserMstr userBean= (UserMstr) session.getAttribute("USER_INFO");
			 					String actorId =userBean.getKmActorId();
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString()) || PropertyReader.getAppValue("channel.partner").equalsIgnoreCase(actorId)){
								%>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5"><bean:message key="viewAllUser.MobileNumber" /></span></th>
			<% }%>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" bgcolor="yellow"><span class="mTop5"><bean:message key="view.leadID" /></span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="15%"><span class="mTop5"><bean:message key="view.productName" /></span></th>

			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  bgcolor="yellow"><span class="mLeft5 mTop5"><bean:message key="viewAllUser.Status" /></span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Sub Status</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="25" bgcolor="yellow"><span class="mLeft5 mTop5">Remarks </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="25" bgcolor="yellow"><span class="mLeft5 mTop5">Forward To </span></th>
		<!--	<th bgcolor="a90000" align="center">&nbsp;</th>
			<th bgcolor="a90000" align="center">&nbsp;</th>  -->
		</tr>
		<logic:present name="assignedList" scope="request">		
		<logic:empty  scope="request" name="assignedList">
			<TR class="lightBg">
				<TD class="text" align="center" colspan="11"><bean:message
				
					key="viewAllUser.NotFound" /></TD>
			</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="assignedList" scope="request">	
		<logic:notEmpty name="assignedList" scope="request">
			<logic:iterate id="report" indexId="i" name="assignedList"scope="request"  >
				
				<TR class="lightBg">
					<TD class="text" align="center"><%=(i.intValue() + 1)%>.</TD>
					<TD class="txt" align="center"><bean:write name="report" property="customerName" /></TD>
					<%  if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())|| PropertyReader.getAppValue("channel.partner").equalsIgnoreCase(actorId)){
								 %>
					<TD class="txt" align="center"><bean:write name="report" property="mobileNumber" /></TD>
					<%} %>
					
					<TD class="txt" align="center"><bean:write name="report" property="leadID" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="productName" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="status" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="subStatus" /></TD>
					<TD class="txt" align="center">&nbsp</TD>
					<TD class="txt" align="center">&nbsp</TD>
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
