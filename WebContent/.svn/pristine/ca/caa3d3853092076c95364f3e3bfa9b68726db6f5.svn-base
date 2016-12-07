<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ibm.lms.wf.dto.LeadDetailDTO"%>

<%@page import="com.ibm.lms.services.MasterService"%>
<%@page import="com.ibm.lms.services.impl.MasterServiceImpl"%><LINK href="./jsp/theme/css.css" rel="stylesheet" type="text/css">
<script language="javascript"  src="<%=request.getContextPath()+"/jScripts/ajaxCall.js"%>"></script>
<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript">
function leadClosure()
{
	var oForm = document.LeadForm;
	oForm.methodName.value = "closeTheLead";
	if(oForm.actionType[oForm.actionType.selectedIndex].value == ""){alert("Action Type is required!");oForm.actionType.focus();return false;}
	
	if(oForm.actionType[oForm.actionType.selectedIndex].value == 500)
	{
		if(oForm.cafNumber.value == ""){alert("Caf Number is required!");oForm.cafNumber.focus();return false;	}
	}
	
	if(oForm.closureComments.value == ""){alert("Comments are required!"); oForm.closureComments.focus(); return false;}
	if(oForm.closureComments.value.length >1000){alert("Comments can not be grater than 1000!"); oForm.closureComments.focus(); return false;}
	oForm.submit();
	return false;
}
function leadForward()
{
	var oForm = document.LeadForm;
	oForm.methodName.value = "forwardTheLead";
	if(oForm.forwardTo[oForm.forwardTo.selectedIndex].value == ""){alert("User Name is required!");oForm.forwardTo.focus();return false;}
	if(oForm.forwardComments.value == ""){alert("Comments are required!"); oForm.forwardComments.focus(); return false;}
	if(oForm.forwardComments.value.length >1000){alert("Comments can not be grater than 1000!"); oForm.forwardComments.focus(); return false;}
	oForm.submit();
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
	
function getSubStatus()
	{
		removeAllOptions(document.LeadForm.subStatusID);
		var status = document.LeadForm.actionType;
		var leadID=document.LeadForm.leadID.value;
		if (status.options[status.options.selectedIndex].value == "") return false;
		var data = "methodName=getFeasibilitySubStatusList&status=" + status.options[status.options.selectedIndex].value +"&leadID=" +leadID;
		doAjax("feasibleLeads.do", "GET", false, getSubStatusOnStatusChange, data);
		//var status = document.LeadForm.actionType;
		var statusID = status.options[status.options.selectedIndex].value;
		if (statusID == "") return false;
		if(statusID == 500)
		{
			document.getElementById("cafDisplay").style.display= "";
			document.getElementById("subStatusDisplay").style.display="";
		}
		else if(statusID == 600)
		{
			document.getElementById("subStatusDisplay").style.display="";
			document.getElementById("cafDisplay").style.display= "none";
		}
		else
		{
		document.getElementById("subStatusDisplay").style.display="none";
			document.getElementById("cafDisplay").style.display= "none";
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

<html:form action="leadFinalStatusUpdate.do">
	<html:hidden name="LeadForm" property="methodName" />
	
	<logic:present name="leadID" scope="request">
	<bean:define id="leadID" name="leadID" type="java.lang.String" scope="request"  />
	<html:hidden property="leadID" value='<%=leadID%>'/>
	</logic:present>
	<div class="box2">                            
		<div class="content-upload" style="margin-bottom: 0px;">
          <h1><bean:message key="view.leadDetail" /></h1>
        </div>
         <br> 
				<fieldset class="field_set_main"><legend class="header"><font
					size=2 color=#ff0000>Lead Detail</font></legend>
							<logic:present name="detail" scope="request">
							<logic:notEmpty name="detail" scope="request">
							<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
							<tr>
								<td width="25%" class="text height12" align="right"><font color="#4b0013"><b>Product Name &nbsp;&nbsp;</td>
								<td width="25%" align="left" class="height12" colspan="3"><bean:write name="detail"	property="product_name" /></td>
								<td width="25%" class="text height12" align="right"><font color="#4b0013"><b>Customer Name &nbsp;&nbsp;</td>
								<td width="25%" align="left" class="height12"><bean:write name="detail"	property="customer_name" /></td>
							</tr>

							<tr>
								<td width="25%" class="text height12" align="right"><font color="#4b0013"><b>Lead ID &nbsp;&nbsp;</td>
								<td width="25%" align="left" class="height12"><bean:write name="detail"	property="leadID" /></td>
								
							</tr>
							</table>
							</fieldset>
							<fieldset class="field_set_main"><legend class="header"><font
								size=2 color=#ff0000>Location</font></legend>
								<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Circle Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="circle_name" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>State Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="state_name" /></td>
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Zone Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="zone_name" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>City Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="city_name" /></td>

							</tr>

							<tr>
								
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>City Zone Name &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="cityZoneName" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Email ID &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="email" /></td>
								
							</tr>
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Pin code &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="pincode" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>RSU ID &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="rsu_id" /></td>
							</tr>
						</table>
						</fieldset>
						<fieldset class="field_set_main"><legend class="header"><font
								size=2 color=#ff0000>Contact Details</font></legend>
								<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
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
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Address &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="address1" /></td>
								
								<% 
								if("N".equalsIgnoreCase(request.getAttribute("Mobile_FLAG").toString())){
								%>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Mobile Number &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="prospect_mobile_number" /></td>
								<td width="25%" align="left">&nbsp;</td>
								<%} %>
							</tr>
						</table>
						</fieldset>
							<fieldset class="field_set_main"><legend class="header"><font
								size=2 color=#ff0000>Other Lead Details</font></legend>
								<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
							
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Online Caf Number &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="onlineCafNumber" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Transaction Ref No. &nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail"	property="transRefNo" /></td>
								<td width="25%" align="left">&nbsp;</td>
							</tr>	
							<TR>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Rental &nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail"	property="rental" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Lead Category  &nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail"	property="leadCategory" /></td>
		<!--					<td width="25%" class="text" align="right"><font color="#4b0013"><b>Geo IP City &nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail"	property="geoIpCity" /></td>   -->
							</TR>					
							<TR>
								<logic:equal value="1" name="detail" property="lobId">
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Security Deposit &nbsp;&nbsp;</td>
									<td width="25%"  class="text" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Allocated Number &nbsp;&nbsp;</td>
									<td width="25%" class="text" align="left"><bean:write name="detail"	property="allocatedNumber" /></td>
								</logic:equal>
								<logic:equal value="2" name="detail" property="lobId">
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Activation Fee &nbsp;&nbsp;</td>
									<td width="25%"  class="text" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
								
								</logic:equal>
								<logic:equal value="23" name="detail" property="lobId">
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Dongle MRP: &nbsp;&nbsp;</td>
									<td width="25%"  class="text" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
								</logic:equal>
								<logic:equal value="24" name="detail" property="lobId">
									<td width="25%" class="text" align="right"><font color="#4b0013"><b>Dongle MRP: &nbsp;&nbsp;</td>
									<td width="25%"  class="text" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
								</logic:equal>
								<logic:notEqual value="1" name="detail" property="lobId">
									<logic:notEqual value="2" name="detail" property="lobId">
										<logic:notEqual value="23" name="detail" property="lobId">
											<logic:notEqual value="24" name="detail" property="lobId">
												
													<td width="25%" class="text" align="right"><font color="#4b0013"><b>Payment Details: &nbsp;&nbsp;</td>
													<td width="25%"  class="text" align="left"><bean:write name="detail"	property="paymentAmt" /></td>
												
											</logic:notEqual>
										</logic:notEqual>
									</logic:notEqual>
								</logic:notEqual>
							</TR>
							<TR>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Product Type&nbsp;&nbsp;</td>
								<td width="25%"  class="text" align="left"><bean:write name="detail"	property="productType" /></td>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Lead Priority &nbsp;&nbsp;</td>
								<td width="25%" class="text" align="left"><bean:write name="detail"	property="leadPriority" /></td>

							</TR>
							<TR>
															<td width="25%" class="text" align="right"><font color="#4b0013"><b>Plan ID &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="myOpId" /></td>
							</TR>	
							<TR>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Remarks &nbsp;&nbsp;</td>
								<td width="25%" colspan="3" class="text" align="left"><bean:write name="detail"	property="remarks" /></td>
							</TR>
							</table>
						</fieldset>
							</table>
							</logic:notEmpty>
							</logic:present>
						</fieldset><br/>
						<fieldset>
							<legend><b>Action</b></legend>
							<table border="0" width="100%" cellpadding="1" cellspacing="1">
							<tr>
								<td width="35%" class="text" align="right"><font color="#4b0013"><b>Activity / Action Type <font color="#ff0000">*</font>&nbsp;:</b></font></td>
								<td width="65%" class="pTop3 pLeft5 text">
									<html:select styleClass="width180" property="actionType" onchange="getSubStatus()">
										<html:option value="">Select</html:option>
										<html:optionsCollection name="actionList" value="ID" label="keyValue"/>
									</html:select>
								</td>
							</tr>
							<tr style="display:none" id="subStatusDisplay">
								<td width="35%" class="text" align="right"><font color="#4b0013"><b>Sub status <font color="#ff0000"></font>&nbsp;:</b></font></td>
								<td width="65%" class="pTop3 pLeft5 text">
									<html:select styleClass="width180" property="subStatusID" >
										<html:option value="">Select</html:option>
										<logic:present name="subStatusList" scope="request">
										<logic:notEmpty name="subStatusList" scope="request">
										<html:optionsCollection name="subStatusList" value="ID" label="keyValue"/>
										</logic:notEmpty>
										</logic:present>
									</html:select>
								</td>
							</tr>
								<tr style="display:none" id="cafDisplay">
								<td class="text" align="right" valign="top" style="padding-top:3px;"><font color="#4b0013"><b>CAF Number/Customer ID <font color="#ff0000">*</font>&nbsp;:</b></font></td>
								<td class="pTop3 pLeft5"><html:text styleClass="width180" property="cafNumber" maxlength="20"></html:text></td>
							</tr>
							<tr>
								<td class="text" align="right" valign="top" style="padding-top:3px;"><font color="#4b0013"><b>Comments <font color="#ff0000">*</font>&nbsp;:</b></font></td>
								<td class="pTop3 pLeft5"><html:textarea styleClass="width300" property="closureComments" rows="7"></html:textarea></td>
							</tr>
							<tr>
							<td colspan="2" align="center"><html:submit styleClass="red-btn" value="Submit" onclick="javascript:return leadClosure()"/></td></tr>
							</table>
						</fieldset><br/>
						<!-- <fieldset>
							<legend><h5>Forward</h5></legend>
							<table border="0" width="100%" cellpadding="1" cellspacing="1">
							<tr>
								<td width="35%" class="text" align="right"><font color="#4b0013"><b>Forward To <font color="#ff0000">*</font>&nbsp;:</b></font></td>
								<td width="65%" class="pTop3 pLeft5 text">
									<html:select styleClass="width180" property="forwardTo" >
										<html:option value="">Select</html:option>
										<html:optionsCollection name="userList" value="userLoginId" label="userFname"/>
									</html:select>
								</td>
							</tr>
							<tr>
								<td class="text" align="right" valign="top" style="padding-top:3px;"><font color="#4b0013"><b>Comments <font color="#ff0000">*</font>&nbsp;:</b></font></td>
								<td class="pTop3 pLeft5"><html:textarea styleClass="width300" property="forwardComments" rows="7"></html:textarea></td>
							</tr>
							<tr>
							<td colspan="2" align="center"><html:submit styleClass="red-btn" value="Forward" onclick="javascript:return leadForward()"/></td></tr>
						
							</table>
						</fieldset>-->
 </div>
</html:form>
