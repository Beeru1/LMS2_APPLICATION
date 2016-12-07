<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">

<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition", "attachment;filename=FeasibilityLeads.xls");

%>
</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	
		<tr class="text white">
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Party_Type(M)</span></th>
			<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5"><bean:message	key="viewAllUser.MobileNumber" /></span></th>
			<%} %>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">First Name(M)</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Last Name(M)</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Appointment Address1(M)</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Appointment Address2(M)</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mTop5">Circle(M)</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" bgcolor="yellow"><span class="mTop5"><bean:message	key="view.leadID" /></span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" bgcolor="yellow"><span class="mLeft5 mTop5">RSU</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" bgcolor="yellow"><span class="mLeft5 mTop5"><bean:message	key="viewAllUser.Status" /></span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" bgcolor="yellow"><span class="mLeft5 mTop5">Sub Status</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" bgcolor="yellow"><span class="mLeft5 mTop5">Remarks </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mTop5">Zone</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mTop5">City</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mTop5">City Zone</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Pincode</span></th>
						<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Landline</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Alternate mobile Number</span></th>
			<%} %>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mTop5">Source </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mTop5">Sub Source</span></th>	
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mTop5">Request Type</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mTop5">Campaign</span></th>	
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mTop5"><bean:message	key="view.productName" /></span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mTop5">Number Of Connections</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mTop5">OLM ID of agent who created the lead- inbound/outbound</span></th>
		</tr>
		<logic:present name="fisibilityList" scope="request">		
		<logic:empty  scope="request" name="fisibilityList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message key="viewAllUser.NotFound" /></TD>	</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="fisibilityList" scope="request">	
		<logic:notEmpty name="fisibilityList" scope="request">
			<logic:iterate id="report" indexId="i" name="fisibilityList"scope="request"  >
				
				<TR class="lightBg">
					<TD class="txt" align="center">&nbsp;</TD>
					<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
					<TD class="txt" align="center"><bean:write name="report" property="mobileNumber" /></TD>
					<%} %>
					<TD class="txt" align="center"><bean:write name="report" property="customerName" /></TD>
					<TD class="txt" align="center">&nbsp;</TD>
					<TD class="txt" align="center"><bean:write name="report" property="address" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="address2" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="circleName" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="leadID" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="rsuCode" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="status" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="subStatus" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="dialerRemarks" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="zoneName" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="cityName" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="cityZoneName" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="pinCode" /></TD>
					<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
					<TD class="txt" align="center">&nbsp;</TD>
					<TD class="txt" align="center">&nbsp;</TD>
					<%} %>
					<TD class="txt" align="center"><bean:write name="report" property="source" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="subSource" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="requestType" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="compaign" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="productName" /></TD>
					<TD class="txt" align="center">&nbsp;</TD>
					<TD class="txt" align="center">&nbsp;</TD>
					
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
