<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<bean:define id="loginActorId" name="LOGIN_USER_ACTOR_ID" type="java.lang.String" scope="session" />

<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">

<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition","attachment;filename=LeadDetailsMTDReport.xls");

%>
</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	
		<tr class="text white" >
		
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Lead Id</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">Customer Name</span></th>
			<%
								  if(request.getAttribute("Mobile_FLAG_MTD") !=null && "N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG_MTD").toString())){
								  
								 %>
			 <th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%" ><span class="mLeft5 mTop5">Mobile Number</span></th> 
			<%} %>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"width="5%" ><span class="mLeft5 mTop5">Lob Name </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">Product Name</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">Plan Id</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">Plan</span></th>
			
			
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">Circle</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"   width="5%" ><span class="mTop5">City</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">Zone Name</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Zone</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Pincode</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Email</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">RSU ID</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Lead Status</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">Sub Status</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">Caf Number</span></th>
			
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Transaction Time</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Lead Assignment Time</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Lead Created</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Lead Submit Time</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Pending With</span></th>

			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">Partner Name</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Actor Name</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Company</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Source</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Sub Source</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Agency Source</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Product Request Type</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Request Category</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Utm Labels</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Utm Source</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Lead Category</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Auto Assign</span></th>
			
				<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Campaign</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Referer Page</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Referer URL</span></th>
						<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">FID</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">CID</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Disposition</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">City Zone Name</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Channel Partner Name</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Address</span></th>
			
			<logic:equal name="loginActorId"  value="1">
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Lead Priority</span></th>
			</logic:equal>
			<logic:equal name="loginActorId"  value="2">
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Lead Priority</span></th>
			</logic:equal>
						<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Disposition Count</span></th>
						<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Disposition Code</span></th>
						<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Last Disposition Date</span></th>
						<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">First Disposition Date</span></th>
						<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Assigned Channel Partner</span></th>
						
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Info Done Olmid</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Sales Executive Number</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Sales Channel Code</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Is Customer</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Latitude</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Longitude</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">Lead Transaction Id</span></th>
						
		</tr>
		<logic:present name="reportList" scope="request">		
		<logic:empty  scope="request" name="reportList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="assignmentDownload.NotFound" /></TD>
				
			</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="reportList" scope="request">	
		<logic:notEmpty name="reportList" scope="request">
			
			<logic:iterate id="report"  name="reportList" scope="request"   >
				
				<TR class="lightBg">

					<TD class="txt" align="center"><bean:write name="report" property="leadId" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="customerName" /></TD>
				 <% if(request.getAttribute("Mobile_FLAG_MTD") !=null && "N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG_MTD").toString())){
								 %>
				<TD class="txt" align="center"><bean:write name="report"property="customerMobile" /></TD> 
				<%} %>
					<TD class="txt" align="center"><bean:write name="report"property="lobName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="productName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="planId" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="plan" /></TD>
					
					<TD class="txt" align="center"><bean:write name="report"property="circleName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="cityName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="zoneName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="zoneCode" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="pinCode" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="email" /></TD>
					
					<TD class="txt" align="center"><bean:write name="report"property="rsuId" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="leadStatus" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="leadSubStatus" /></TD>
					<TD class="txt" align="center"><bean:write name="report" property="cafNumber" /></TD>
				
					<TD class="txt" align="center"><bean:write name="report" property="transactionTime" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="leadAssignmentDate" /></TD>
					
					<TD class="txt" align="center"><bean:write name="report"property="createTime" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="submitTime" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="pendingWith" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="partnerName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="actorName" /></TD>
					
					
					<TD class="txt" align="center"><bean:write name="report"property="company" /></TD>  
					
					
					<TD class="txt" align="center"><bean:write name="report"property="source" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="subSource" /></TD>
					
					<TD class="txt" align="center"><bean:write name="report"property="agencySource" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="requestType" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="requestCategory" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="utmLabels" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="utmSource" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="leadCategory" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="autoAssign" /></TD>
					
					
					<TD class="txt" align="center"><bean:write name="report"property="campaign" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="referPage" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="referUrl" /></TD>
					
					<TD class="txt" align="center"><bean:write name="report"property="fid" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="cid" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="dispRecieved" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="cityZoneName" /></TD>					
					<TD class="txt" align="center"><bean:write name="report"property="channalPartnerName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="address" /></TD>
					
					<logic:equal name="loginActorId"  value="1">
					<TD class="txt" align="center"><bean:write name="report"property="laedPriority" /></TD>
					</logic:equal>
					<logic:equal name="loginActorId"  value="2">
					<TD class="txt" align="center"><bean:write name="report"property="laedPriority" /></TD>
					</logic:equal>
						<TD class="txt" align="center"><bean:write name="report"property="dispositionCount" /></TD>
						<TD class="txt" align="center"><bean:write name="report"property="dispositionCode" /></TD>
						<TD class="txt" align="center"><bean:write name="report"property="lastDispositionDate" /></TD>
						<TD class="txt" align="center"><bean:write name="report"property="firstDispositionDate" /></TD>
						<TD class="txt" align="center"><bean:write name="report"property="assignedChannelPartner" /></TD>
						
						<TD class="txt" align="center"><bean:write name="report"property="info_done_olmId" /></TD>
						<TD class="txt" align="center"><bean:write name="report"property="salesExecutiveNumber" /></TD>
						<TD class="txt" align="center"><bean:write name="report" property="salesChannelCode"/></TD>
						<TD class="txt" align="center"><bean:write name="report" property="isCustomer"/></TD>
						<TD class="txt" align="center"><bean:write name="report" property="latitude"/></TD>
						<TD class="txt" align="center"><bean:write name="report" property="longitude"/></TD>
						<TD class="txt" align="center"><bean:write name="report" property="transactionId"/></TD>
						
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
