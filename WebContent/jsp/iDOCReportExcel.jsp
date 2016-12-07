<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<!-- author: Nancy Agrawal. -->

<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">

<%
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition","attachment;filename=iDOCReport.xls");

%>

</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	<TR class="text white">
            <TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Lead ID</TH> 
             <TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Order Id</TH> 
			<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Alternate no/Prospect Mobile number</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Mobile</TH> <% }%>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">PRODUCT</TH>
                <TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">TVDATE</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">CHANNEL_PARTNER_LMS</TH>
			    <TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">CHANNEL_PARTNER_IDOC</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">CAFNO</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">DE1STATUS</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">DE1DATE</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">TVSTATUS</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">TVCRMDATE</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">TVREASON</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">INWARDSTATUS</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">INWARDDATE</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%"> INWARDUSER</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%"> DEAUDITSTATUS</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%"> DEAUDITDATE</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">DEAUDITUSER</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%"> EVSTATUS</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">EVDATE</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">EVREASON </TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">EVUSER</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">De-dupe Status</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">De-dupe Date</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">SCAN_STATUS </TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">DE2 Date-Time</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">DE2_User</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">DE2_Status</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">WHXDATE</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">WHXUSER</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Party_Creation_Status</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Party_Creation_Date</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Party_Creation_Reason</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">ORDER_CREATION_STATUS</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">ORDER_CREATION_DATE_IDOC</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">ORDER_REMARKS </TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">ORDER_COMPLETION_DATE_IDOC</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">ORDER_CREATION_AGENT_IDOC</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">Lead Creation Time</TH>
				<TH style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%">CHANNEL_CODE</TH>
				

				
				
			</TR>
		<logic:present name="reportList" scope="request">		
		<logic:empty  scope="request" name="reportList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message key="assignmentDownload.NotFound" /></TD>
				
			</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="reportList" scope="request">	
		<logic:notEmpty name="reportList" scope="request">
			
			<logic:iterate id="report" name="reportList" scope="request"   >
				
				<TR class="lightBg">				
					
					
					    <TD class="txt" align="center"><bean:write name="report" property="leadId" /></TD>
						<TD class="txt" align="center"><bean:write name="report" property="order_id" /></TD>
						<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
						<TD class="txt" align="center"><bean:write name="report" property="customerMobile" /></TD>
						<TD class="txt" align="center"><bean:write name="report" property="alternateMobileNumber" /></TD> 
						<%} %>
						<TD classs="txt" align="center"><bean:write name="report" property="product"/></TD>
						<TD class="txt" align="center"><bean:write name="report" property="tvDate" /></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="channelPartner" /></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="channelPartnerIdoc" /> </TD>
						<TD classs="txt" align="center"><bean:write name="report" property="cafNo" /> </TD>
						<TD classs="txt" align="center"><bean:write name="report" property="de1Status"/> </TD>
						<TD classs="txt" align="center"><bean:write name="report" property="de1Date"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="tvStatus"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="tvCrmDate"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="tvReason"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="inWardStatus"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="inWardDate"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="inWardUser"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="deAuditStatus"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="deAditDate"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="deAuditUser"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="evStatus"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="evDate"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="evReason"/> </TD>
						<TD classs="txt" align="center"><bean:write name="report" property="evUser"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="dedupe_Status"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="deDupe_Date"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="scanStatus"/> </TD>
						<TD classs="txt" align="center"><bean:write name="report" property="de2DateTime"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="de2_User"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="de2_Status"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="wxhDate"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="whxUser"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="party_Creation_Status"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="party_Creation_Date"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="party_Creation_Reason"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="orderCreationStatus"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="orderCreationIdoc"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="orderRemarks" /></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="orderCompletionDateIdoc"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="orderCreationAgentIdoc"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="lead_Creation_Time"/></TD>
						<TD classs="txt" align="center"><bean:write name="report" property="channelCode"/></TD>
						
					
				</TR>




			
						
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
