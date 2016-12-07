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
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Address </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" bgcolor="yellow"><span class="mTop5"><bean:message key="view.leadID" /></span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="15%"><span class="mTop5"><bean:message key="view.productName" /></span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="15%"><span class="mTop5">Lead Assignment Time</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="15%"><span class="mTop5">Expected Closure Time</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  bgcolor="yellow"><span class="mLeft5 mTop5"><bean:message key="viewAllUser.Status" /></span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Sub Status</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Caf Number</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="25" bgcolor="yellow"><span class="mLeft5 mTop5">Remarks </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Zone </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">City </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">City Zone </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Pincode </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">RSUCode </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Rental</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Online Caf Number</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Allocated Number</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Dialer Remarks</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Transaction Ref.</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">HLR No.</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Payment</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Product Type</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Lead Priority</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Lead Category</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Request Type</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">FID</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">CID</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" height="25" bgcolor="yellow"><span class="mLeft5 mTop5">From Page</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Service</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Plan</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Keyword </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" ><span class="mLeft5 mTop5">Company </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">CityZoneCode</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">City Code</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">ZoneCode</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Appointment Time</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Qual Lead Param</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">User Type</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Plan Id</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">State</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Booster Count</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Freebie Taken</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Booster Taken</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">flag</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Prapaid Number</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Offer</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Download Limit</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Device Mrp</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Voice Benefit</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Data Quota</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Device Taken</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Freebie Count</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Benefit</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Package Duration</span></th>
            <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Sales Channel Code</span></th>
            <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Lattitude</span></th>
            <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  ><span class="mLeft5 mTop5">Longitude</span></th>
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
					<TD class="txt" align="center"><bean:write name="report" property="address" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="leadID" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="productName" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="leadAssignmentTime" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="leadClosureTime" /></TD>
					<TD class="txt" align="center">&nbsp;</TD>
					<TD class="txt" align="center">&nbsp;</TD>
					<TD class="txt" align="center">&nbsp;</TD>
					<TD class="txt" align="center">&nbsp;</TD>
					<TD class="txt" align="center"><bean:write name="report" property="zoneName" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="cityName" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="cityZoneName" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="pinCode" /></TD>	
					<TD class="txt" align="center"><bean:write name="report" property="rsuCode" /></TD>					
					<TD class="txt" align="center"><bean:write name="report" property="rental" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="onlineCafNumber" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="allocatedNumber" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="dialerRemarks" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="transRefNo" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="hlrNo" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="paymentAmt" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="productType" /></TD>
					<TD class="txt" align="center">
							<logic:equal value="HIGH" property="leadPriority" name="report" >
										<font color="red">
											<bean:write	property="leadPriority" name="report" />
										</font>
									</logic:equal>
										<logic:equal value="MEDIUM" property="leadPriority" name="report" >
										<font color="blue">
											<bean:write	property="leadPriority" name="report" />
										</font>
									</logic:equal>
										<logic:equal value="LOW" property="leadPriority" name="report" >
										<font color="green">
											<bean:write	property="leadPriority" name="report" />
										</font>
									</logic:equal>
					
					
					
					</TD>
					<TD class="txt" align="center"><bean:write name="report" property="leadCategory" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="requestType" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="fid" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="cid" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="fromPage" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="service" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="plan" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="keyword" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="company" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="cityZoneCode" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="cityCode" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="zoneCOde" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="appointmentTime" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="qualLeadParam" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="userType" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="planId" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="state" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="boosterCount" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="freebieTaken" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="boosterTaken" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="flag" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="prepaidNumber" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="offer" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="downloadLimit" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="deviceMrp" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="voiceBenefit" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="dataQuota" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="deviceTaken" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="freebieCount" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="benefit" /></TD>
					  <TD class="txt" align="center"><bean:write name="report" property="pkgDuration" /></TD>
					  <TD class="txt" align="center"><bean:write name="report" property="salesChannelCode" /></TD>
					  <TD class="txt" align="center"><bean:write name="report" property="latitude" /></TD>
					  <TD class="txt" align="center"><bean:write name="report" property="longitude" /></TD>
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
