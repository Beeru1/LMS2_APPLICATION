<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<script>

function validate()
{
var leadSubSubStatusId=document.forms[0].leadSubSubStatusId.value;
var leadSubSubStatus=document.forms[0].leadSubSubStatus.value;
var leadStatusId=document.forms[0].leadStatusId.value;
var subStatusId=document.forms[0].subStatusId.value;
var subSubStatusId=document.forms[0].leadSubSubStatusId.value;
var leadSubSubStatusDisplay=document.forms[0].leadSubSubStatusDisplay.value;
if(leadSubSubStatusDisplay=="" || leadSubSubStatus=="")
{
alert("Please input the required fields");
return false;
}
document.forms[0].methodName.value="updateSubSubStatusDetails";
document.forms[0].submit();
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
</style>
<html:form action="/subStatusAction">
	<html:hidden name="leadRegistrationFormBean" property="methodName" />
	<html:hidden name="leadRegistrationFormBean" property="leadSubSubStatusId" />
	<html:hidden name="leadRegistrationFormBean" property="subStatusCode"/>
	<div class="box2">                            
		<div class="content-upload" style="margin-bottom: 0px;">
          <h1><bean:message key="view.subSubStatusDetail" /></h1>
        </div> 
				<fieldset>
							<legend><b>Sub Sub Status Detail</b></legend>
							<logic:present name="detail" scope="request">
							<logic:notEmpty name="detail" scope="request">
							<table border="0" width="100%" cellpadding="1" cellspacing="1" cellspacing="1">
							<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Status ID &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail" property="leadStatusId" /></td>
								<html:hidden name="detail" property="leadStatusId" />
								</tr>
								<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Status &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="leadStatus" /></td>
								</tr>
								<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>SubStatus ID &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="subStatusId" /></td>
								<html:hidden name="detail" property="subStatusId" />
								</tr>
								<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Sub Status  &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="leadSubStatus" /></td>
								</tr>
								<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Sub Sub Status ID &nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="leadSubSubStatusId" />
								<html:hidden name="detail" property="leadSubSubStatusId" />
								</td>
								</tr>
								<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Sub Sub Status &nbsp;&nbsp;</td>
								<td width="25%" align="left"><html:textarea name="detail"	property="leadSubSubStatus" />
								</td>
								</tr>
								<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Sub Sub Status Display &nbsp;&nbsp;</td>
								<td width="25%" align="left"><html:textarea name="detail"	property="leadSubSubStatusDisplay" />
								</td>
								</tr>
								<tr>
								<td width="25%" class="text" align="right"><font color="#4b0013"><b>Product Lob&nbsp;&nbsp;</td>
								<td width="25%" align="left"><bean:write name="detail"	property="lobName" /></td>
								<html:hidden name="detail" property="lobName" />
								<html:hidden name="detail" property="subStatusCode"/>
								</tr>
							</tr>
							</table>
							</logic:notEmpty>
							</logic:present>
							
							</fieldset>
					
				<div class="button">
			<a class="red-btn" onclick="return validate()"><b>Update</b>
			</a>
		</div>
			
			</div>
			</html:form>	
					