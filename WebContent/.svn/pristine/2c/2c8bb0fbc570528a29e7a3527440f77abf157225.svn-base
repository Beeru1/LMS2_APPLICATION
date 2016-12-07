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
	response.setHeader("content-Disposition","attachment;filename=BulkAssignmentMatrixDownload.xls");

%>
</head>
<body>
	<table width="75%" class="mTop30" align="center" border="1">
	
		<tr class="text white" >
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" bgcolor="yellow" ><span class="mLeft5 mTop5">PRODUCT_LOB_ID</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%"  ><span class="mLeft5 mTop5">PRODUCT_ID</span></th>
						<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%"  ><span class="mLeft5 mTop5">REQUEST_CATEGORY_ID</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%" bgcolor="yellow" ><span class="mLeft5 mTop5">OLM_ID</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%" bgcolor="yellow" ><span class="mLeft5 mTop5">CIRCLE_ID</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"width="5%" ><span class="mLeft5 mTop5">ZONE_CODE </span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">CITY_CODE</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">CITY_ZONE_CODE</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mTop5">PINCODE</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;"   width="5%" ><span class="mTop5">RSU_ID</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"  width="5%" ><span class="mLeft5 mTop5">SECONDARY_OLM_ID</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">USER_TYPE</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;"width="5%"   ><span class="mLeft5 mTop5">CREATED</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%" ><span class="mLeft5 mTop5">CREATED_BY</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">UPDATED</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">UPDATED_BY</span></th>
			
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">LEVEL1_CC</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">LEVEL2_CC</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">LEVEL3_CC</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">LEVEL4_CC</span></th>
			
			<th  style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5"></span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">LOB_NAME</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">CIRCLE_NAME</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">CITY_NAME</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">ZONE_NAME</span></th>
			<th style="background-image:url(./images/left-nav-heading-bg.jpg); background-repeat:repeat-x;" width="5%"  ><span class="mLeft5 mTop5">PARTNER_NAME</span></th>
			
		
		</tr>
		<logic:present name="assignmentList" scope="request">		
		<logic:empty  scope="request" name="assignmentList">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="assignmentDownload.NotFound" /></TD>
				
			</TR>
		</logic:empty>
		</logic:present>
		<logic:present name="assignmentList" scope="request">	
		<logic:notEmpty name="assignmentList" scope="request">
			
			<logic:iterate id="report"  name="assignmentList" scope="request"   >
				
				<TR class="lightBg">
					
					<TD class="txt" align="center"><bean:write name="report" property="productLobId" /></TD>
					<TD class="txt" align="center">
					<logic:notEqual property="productId" name="report"  value="-1">
						<bean:write name="report" property="productId" />
					</logic:notEqual>
					</TD>
					<TD class="txt" align="center">
					<logic:notEqual property="requestCategoryId" name="report"  value="-1">
						<bean:write name="report" property="requestCategoryId" />
					</logic:notEqual>
					</TD>
					<TD class="txt" align="center"><bean:write name="report"property="olmId" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="circleId" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="zoneId" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="cityId" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="cityZoneCode" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="pincode" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="rsuId" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="secondaryOlmId" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="userType" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="created" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="createdBy" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="updated" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="updatedBy" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="level1CC" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="level2CC" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="level3CC" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="level4CC" /></TD>
					<TD class="txt" align="center" bgcolor="#FFFFFF" ></TD>
					<TD class="txt" align="center"><bean:write name="report"property="productName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="circleName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="cityName" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="zonename" /></TD>
					<TD class="txt" align="center"><bean:write name="report"property="partnerName" /></TD>
					
					
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	</table>
</body>
</html>
