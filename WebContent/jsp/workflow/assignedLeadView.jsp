<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ibm.lms.wf.dto.LeadDetailDTO"%>

<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%>
<%@page import="com.ibm.lms.dto.UserMstr"%>
<%@page import="com.ibm.lms.common.PropertyReader"%><LINK href="./theme/css.css" rel="stylesheet" type="text/css">

<LINK href="./jsp/theme/css.css" rel="stylesheet" type="text/css">

<LINK href="theme/text.css" rel="stylesheet" type="text/css">



<html:form action="qualifiedLeads.do">
	<html:hidden name="LeadForm" property="methodName" />
	<html:hidden name="LeadForm" property="leadID" />
	<div class="box2">                            
		<div class="content-upload" style="margin-bottom: 0px;">
          <h1><bean:message key="view.leadDetail" /></h1>
        </div>
         
			<fieldset>
							<legend><h5>Lead Detail</h5></legend>
							<logic:present name="detail" scope="request">
							<logic:notEmpty name="detail" scope="request">
							<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Lead ID &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="LeadForm"	property="leadID" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Customer Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="customer_name" /></td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Circle Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="circle_name" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>State Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="state_name" /></td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>City Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="city_name" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Zone Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="zone_name" /></td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Email ID &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="email" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>RSU ID &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="rsu_id" /></td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Pin code &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="pincode" /></td>
								 <% UserMstr userBean= (UserMstr) session.getAttribute("USER_INFO");
			 					String actorId =userBean.getKmActorId();
								  if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString()) || PropertyReader.getAppValue("channel.partner").equalsIgnoreCase(actorId)){
								 %>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Mobile Number &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="prospect_mobile_number" /></td>
						
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Landline Number &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="landline_number" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Alternate Contact Number &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="alternate_contact_number" /></td>
								<%} %>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Address &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="address1" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>&nbsp;&nbsp;</td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>
							</table>
							</logic:notEmpty>
							</logic:present>
						</fieldset><br/>
						
 </div>
</html:form>
