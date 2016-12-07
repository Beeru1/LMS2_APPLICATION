<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ibm.lms.wf.dto.LeadDetailDTO"%>

<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%><LINK href="./theme/css.css" rel="stylesheet" type="text/css">

<LINK href="./jsp/theme/css.css" rel="stylesheet" type="text/css">
<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<script language="javascript"  src="<%=request.getContextPath()+"/jScripts/ajaxCall.js"%>"></script>
<script language="JavaScript" type="text/javascript">
function leadFeasible(obj)
{
	var oForm = document.LeadForm;
	oForm.methodName.value = "qualifyTheFeasibleLead";
	if(oForm.actionType[oForm.actionType.selectedIndex].value == ""){alert("Action Type is required!");oForm.actionType.focus();return false;}
	else
	{
		if(oForm.actionType[oForm.actionType.selectedIndex].value == 311)
		{
		//	if(oForm.rsuCode[oForm.rsuCode.selectedIndex].value == ""){alert("RSU Code is required!");oForm.rsuCode.focus();return false;}
			if(oForm.rsuCode.value == ""){alert("RSU Code is required!");oForm.rsuCode.focus();return false;}
			else{
				if (! /^[0-9a-zA-Z]*$/.test(oForm.rsuCode.value))
				{
				alert ("Only alphanumeric characters are allowed for RSU CODE");
				
				oForm.rsuCode.select();
				
				return false;
				}
			}		
		}
	}
	if((oForm.actionType[oForm.actionType.selectedIndex].value == 311) || (oForm.actionType[oForm.actionType.selectedIndex].value == 315))
	if(oForm.subStatusID[oForm.subStatusID.selectedIndex].value == ""){alert("Sub Status is required!");oForm.subStatusID.focus();return false;}
	if(oForm.closureComments.value == ""){alert("Comments are required!"); oForm.closureComments.focus(); return false;}
	if(oForm.closureComments.value.length > 1000){alert("Comments can not be grater than 1000!"); oForm.closureComments.focus(); 	return false;}

	oForm.submit();
	obj.disabled=true;
	return false;
}
var getSubStatusOnStatusChange = function()
	{
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			var selectObj = document.LeadForm.subStatusID;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select", "");
			for (var i = 0; i < optionValues.length; i++)
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
		}
	}
	
function getFeasibilitySubStatus()
	{
	
		removeAllOptions(document.LeadForm.subStatusID);
		var status = document.LeadForm.actionType;
		//alert(status.value);

		var product_name=document.forms[0].product_name.value;
		var leadID=document.LeadForm.leadID.value;
		
		//alert(product_name);
		//alert(leadID);
		
		if (status.options[status.options.selectedIndex].value == "") return false;
		var data = "methodName=getFeasibilitySubStatusList&status=" + status.options[status.options.selectedIndex].value + "&leadID=" +leadID;
		doAjax("feasibleLeads.do", "GET", false, getSubStatusOnStatusChange, data);
		markMandatory();
	}
function markMandatory()
{

	var status = document.LeadForm.actionType;
		var statusCode =  status.options[status.options.selectedIndex].value;
	//	alert(statusCode);
		if(statusCode == 311 || statusCode == 315)
		{
			document.getElementById("mandatorySubStatus").innerHTML = "<font color=red>*</font>";
			if(statusCode == 311)
			document.getElementById("mandatoryRsu").innerHTML = "<font color=red>*</font>";
			else
			document.getElementById("mandatoryRsu").innerHTML = "";
		}
		else
		{
			document.getElementById("mandatorySubStatus").innerHTML = "";
			document.getElementById("mandatoryRsu").innerHTML = "";
		}
}	
	
</script>
<style>
fieldset.field_set_main {
	border-bottom-width: 2px;
	border-top-width: 2px;
	border-left-width: 2px;
	border-right-width: 2px;
	margin: auto;
	padding: 0px 0px 0px 0px;
	border-color: CornflowerBlue;
}

legend.header {
	text-align: right;
}
</style>

<html:form action="feasibleLeads.do">
	<html:hidden name="LeadForm" property="methodName" />
	<html:hidden name="LeadForm" property="leadID" />
	<div class="box2">                            
		<div class="content-upload" style="margin-bottom: 0px;">
          <h1><bean:message key="view.leadDetail" /></h1>
        </div> 
				<fieldset>
							<legend><b>Lead Detail</b></legend>
							<logic:present name="detail" scope="request">
							<logic:notEmpty name="detail" scope="request">
							<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Lead ID &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="LeadForm"	property="leadID" /></td>
								
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Customer Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="customer_name" /></td>
							</tr>
							</table>
					<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Location</font></legend>
						<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Circle Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="circle_name" /></td>
								<html:hidden name="detail" property="circleId" />
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
								
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>City Zone Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="cityZoneName" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Pin code &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="pincode" /></td>
								<td width="25%" align="left"></td>
							</tr>
							<tr>
			<td width="25%" class="text" align="right"><table cellpadding="0"><tr><td><strong><font color="#4b0013">RSU&nbsp;&nbsp;</font></strong></td><td><div id="mandatoryRsu"></div></td></tr></table></td>
								<td width="25%" class="pTop3 pLeft5 text">
									<!--<html:select style="width: 155px;" property="rsuCode">
										<html:option value="">Select</html:option>
										<logic:present name="rsuList" scope="request">
										<logic:notEmpty name="rsuList" scope="request">
										<html:optionsCollection name="rsuList" value="rsuCode" label="rsuCode"/>
										</logic:notEmpty>
										</logic:present>
									</html:select>
								-->
								<%-- <html:text property="rsuCode" maxlength="25" value="<bean:write name="detail" property="rsuCode" />" ></html:text> --%>
								<input type="text" name="rsuCode"     value="<bean:write name="detail" property="rsu_id" />" />
								</td>
							</tr>
						</table>
							</fieldset>
							<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Contact Detail</font></legend>
					<table cellpadding="0">
							<tr>
							<td width="25%" class="text" align="right"><font color="#4b0013"><b>Email ID &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="email" /></td>
									<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Mobile Number &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="prospect_mobile_number" /></td>
								<%} %>
								<html:hidden name="detail" property="prospect_mobile_number" />
							</tr>
							<tr>
							<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Landline Number &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="landline_number" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Alternate Contact Number &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="alternate_contact_number" /></td>
							<%} %>
							</tr>
							<TR>
															<td width="25%" class="text" align="right"><font color="#4b0013"><b>Address &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="address1" /></td>
							</TR>
							</table>
							</fieldset>
							<fieldset class="field_set_main"><legend class="header"><font size=2 color=#ff0000>Lead Other Info</font></legend>
							<table cellpadding="0">
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Product Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="product_name" />
								<html:hidden name="detail" property="product_name"/> </td>
								
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Lead CreatedBy &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="email" /></td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Status &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="status" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Sub Status &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="subStatus" /></td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Source &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="source" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Sub Source &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="subSource" /></td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Request Type &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="request_type" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Campaign &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="compaign" /></td>
							</tr>
							<TR>
								<td width="25%" class="text" align="right">	<font color="#4b0013"><b>Address &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="address1" /></td>
							</TR>
							</table>
							</fieldset>

							</logic:notEmpty>
							</logic:present><br/>
						</fieldset><br/>
						<fieldset>
							<legend><b>Action</b></legend>
							<table border="0" width="100%" cellpadding="1" cellspacing="1">
							<tr>
								<td width="35%" height="30" class="text" align="right"><font color="#4b0013"><b>Activity / Action Type <font color="#ff0000">*</font>&nbsp;:</b></font></td>
								<td width="65%" class="pTop3 pLeft5 text">
									<html:select styleClass="width180" property="actionType" onchange="getFeasibilitySubStatus()">
										<html:option value="">Select</html:option>
										<html:optionsCollection name="actionList" value="ID" label="keyValue"/>
									</html:select>
								</td>
								</tr>
							<tr>
							<tr>
								<td width="35%" height="30" class="text" align="right"><table cellpadding="0"><tr><td><strong><font color="#4b0013">Sub Status</font></strong></td><td><div id="mandatorySubStatus"></div></td><td><strong><font color="#4b0013">:</font></strong></td></tr></table></td>
								<td width="65%" class="pTop3 pLeft5 text">
									<html:select styleClass="width180" property="subStatusID">
										<html:option value="">Select</html:option>
										<logic:present name="subStatusList" scope="request">
										<logic:notEmpty name="subStatusList" scope="request">
										<html:optionsCollection name="subStatusList" value="ID" label="keyValue"/>
										</logic:notEmpty>
										</logic:present>
									</html:select>
								</td>
								</tr>
							<tr>
								<td class="text" align="right" valign="top" style="padding-top:3px;" ><font color="#4b0013"><b>Comments <font color="#ff0000">*</font>&nbsp;:</b></font></td>
								<td class="pTop3 pLeft5" ><html:textarea styleClass="width300" property="closureComments" rows="7"></html:textarea></td>
							</tr>
							<tr>
							<td colspan="2" height="60" align="center"><html:submit styleClass="red-btn" value="Submit" onclick="javascript:return leadFeasible(this)"/></td></tr>
							</table>
						</fieldset>
					
 </div>
</html:form>
