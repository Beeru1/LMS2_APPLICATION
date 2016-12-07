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

	response.setContentType("application/vnd.ms-excel");
	response.setHeader("content-Disposition","attachment;filename=SiteLeadReportCountDetails.xls");

%>
</head>
<body>
	<table width="100%" border="0" cellspacing="0" cellpadding="2">
		<tr class="textwhite">
			<th class="labelText" align="center"   ><span class="mTop5">S.NO</span></th>
			<th class="labelText" align="center"  ><span class="mTop5">LEAD ID</span></th>
			<th class="labelText" align="center"  ><span class="mTop5">NAME</span></th>
			<th class="labelText" align="center"  ><span class="mTop5">ADDRESS</span></th>
			<th class="labelText" align="center"  ><span class="mTop5">MOBILE</span></th>
			<th class="labelText" align="center"  ><span class="mTop5">LATITUDE</span></th>
			<th class="labelText" align="center"  ><span class="mTop5">LONGITUDE</span></th>
			<th class="labelText" align="center" ><span class="mTop5">STATUS</span></th>
			<th class="labelText" align="center"  ><span class="mTop5">SUB STATUS</span></th>
			<th class="labelText" align="center" ><span class="mTop5">ASSIGNED TO</span></th>
			<th class="labelText" align="center"  ><span class="mTop5">ASSIGNED SINCE</span></th>
			<th class="labelText" align="center"  ><span class="mTop5">LAST MODIFIED BY</span></th>
			
			<th class="labelText" align="center"  ><span class="mTop5">REMARKS</span></th>
		</tr>
		
		<logic:present name="FtdMtdCountdetails" scope="request">		
		<logic:empty  scope="request" name="FtdMtdCountdetails">
			<TR class="lightBg"><TD class="text" align="center" colspan="3"><bean:message
				
					key="assignmentDownload.NotFound" /></TD>
				
			</TR>
		</logic:empty>
		</logic:present>
			
	<logic:present name="FtdMtdCountdetails" scope="request">	
		<logic:notEmpty name="FtdMtdCountdetails" scope="request">
			 	<logic:iterate id="report"  name="FtdMtdCountdetails" scope="request"  indexId="i" >
				<TR >	
					<TD align="center" valign="top" style="font-size:12px;"><%=(i.intValue() + 1)%>.</TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="leadId" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="customerName" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="address" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="customerMobile" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="longitude" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="latitude" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="leadStatus" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="leadSubStatus" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="assignedto" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="assignedsince" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="lastmodifiedby" /></TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="remarks" /></TD>
				</TR>
			</logic:iterate>  
		</logic:notEmpty>
	</logic:present>
	
	</table>
	</body>
</html>
