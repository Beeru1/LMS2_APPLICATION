<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<LINK href="./theme/css.css" rel="stylesheet" type="text/css">

<LINK href="./jsp/theme/css.css" rel="stylesheet" type="text/css">

<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript">
function showValue()
{
var leadStatusId=document.forms[0].leadStatusId.value;
if(leadStatusId==315||leadStatusId==311||leadStatusId==320)
{
document.forms[0].lobId.value='2'
}
}
function markMandatory()
{
	var makeMandatory = false;
	var oForm = document.forms[0];
	var statusID = oForm.leadStatusId[oForm.leadStatusId.selectedIndex].value;
	
	
	 if(makeMandatory)
   	   {
   	   		 document.getElementById("mandatoryRemarks").innerHTML = "<font color=red>*</font>";
   	   }
   	   else
   	   {
   	   	 document.getElementById("mandatoryRemarks").innerHTML = "";
   	   	 isRemarksMandatory = false;
   	   }
}

function searchReport()
{
var leadStatusId = document.forms[0].leadStatusId.value;
var lobId = document.forms[0].lobId.value;
			
if(leadStatusId== "")
{
alert("Please Select leadStatus");
return false;
}    

if(lobId == "")
 {   	 
 alert("Please Select productLob");
return false;
}
if(leadStatusId==315 && lobId!=2)
{
alert("Only telemedia is valid for an Wired/Unwired/Info Inadequate Lead");
return false;
}
			
	document.forms[0].methodName.value = "viewSubStatus";
	document.forms[0].submit();
}

function sumbitSubStatus()
{
var leadStatusId = document.forms[0].leadStatusId.value;
var lobId = document.forms[0].lobId.value;
var leadSubStatus=document.forms[0].leadSubStatus.value;	
if(leadStatusId== "")
{
alert("Please Select leadStatus");
return false;
}

if(lobId == "")
 {   	 
 alert("Please Select productLob");
return false;
}
if(leadSubStatus=="")
{
alert("Please input sub status name!");
return false;
}
if(leadStatusId==315 && lobId!=2||leadStatusId==311 && lobId!=2||leadStatusId==320 && lobId!=2)
{
alert("Only telemedia is valid for an Unwired Lead");
return false;
}
document.forms[0].methodName.value = "addSubStatus";
document.forms[0].submit();
}
</script>

<html:form action="/statusAction">
	<html:hidden property="methodName" value=""/>
	
      <div class="box2">
        <div class="content-upload"> 
        <h1><bean:message key="submitReport1.heading" /></h1>
        <center><font color="#FF0000"><strong>
			<html:messages id="message" message="true">
					<bean:write name="message"/>  
			</html:messages></strong></font></center>
	
	
	<%
				if (null != request.getAttribute("insertsubStatus")) {
						String insertsubStatus = request.getAttribute("insertsubStatus")
								.toString();
						if (!"null".equals(insertsubStatus)) {
							out.print("<center><h4><FONT color=\"green\">"
									+ insertsubStatus + "</FONT></h4></center>");
						}
					}
			%>
			
   <ul class="list2 form1"> 
   <li class="clearfix ">
		    <span class="text2 fll" style="width: 155px;"><strong>Lead Status</strong><font color=red>*</font></span>
				<p class="clearfix fll">
				<html:select property="leadStatusId" name="leadRegistrationFormBean" styleClass="select1" onchange="showValue()">
					<html:option value="">Select Status</html:option>
					<logic:present name="leadStatusList" scope="request">
					<logic:notEmpty name="leadStatusList" scope="request">
					<bean:define id="leadStatusList" 	name="leadStatusList" scope="request"/> 
						<html:options labelProperty="leadStatus" property="leadStatusId" collection="leadStatusList" />
				</logic:notEmpty>
				</logic:present>
				</html:select></p>
				</li>
				
				 <li class="clearfix ">
			 <span class="text2 fll" style="width: 155px;"><strong>Lob Name</strong><font color=red>*</font></span>
					<td ><html:select property="lobId" styleId="initialSelectBox" name="leadRegistrationFormBean" styleClass="select1">
								
								<html:option value="">Select Product</html:option>
								<logic:present name="lobList" scope="request">
								<logic:notEmpty name="lobList" scope="request">
									<bean:define id="lobList" name="lobList" scope="request" /> 
										<html:options labelProperty="lobName" property="lobId"  collection="lobList" />
								</logic:notEmpty>
								</logic:present>
					</html:select>
					</li>
			<li class="clearfix ">
			      <span class="text2 fll" style="width: 155px;"><strong>Sub Status Name</span>  	 
               	  <html:text style="width:150px;" tabindex="2" property="leadSubStatus"  name="leadRegistrationFormBean" style="height:18px; width: 150px; border-color: #000000; border-width:1px; padding-left:3px; padding-top:3px; font-size:14px; font-weight:bold;  color:#2f2f2f;"/>
             	</li>
             			
					<div class="button-area">
		<div class="button">
			<a class="red-btn" onclick="return searchReport()"><b>Search</b>
			</a>
		</div>
		<div class="button">
			<a class="red-btn" onclick="return sumbitSubStatus()"><b>Add</b>
			</a>
		</div>
		
	</div>
			
	
	</ul>
	</div>
	</br>
	</div>
	
	
	<div class="box2">               
	 <div class="content-upload" style="margin-bottom: 0px;">
       <h1><bean:message key="view.leadSubStatus" /></h1>
     </div>
         
     <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
        <tr class="textwhite">
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="5%"><span class="mTop5">&nbsp;SL.</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5">SUB_STATUS</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="20%"><span class="mTop5">SUB_STATUS_DISPLAY</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5">STATUS</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5">PRODUCT_LOB</span></td>
		<!-- 	<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5">SUB_STATUS</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5">STATUS_ID</span></td> -->
			
			
			
			
		</tr>
        

            
		<logic:empty name="leadRegistrationFormBean" property="leadSubStatusList">
			<TR class="lightBg">
				<TD class="text" align="center" colspan="6"><bean:message key="subStatus.not.found" /></TD>
			</TR>
		</logic:empty>
		
		<logic:notEmpty name="leadRegistrationFormBean" property="leadSubStatusList">
			<logic:iterate name="leadRegistrationFormBean" id="report" indexId="i" property="leadSubStatusList">
				<%
				String cssName = "";				
				if(( i.intValue()%2)==1)
				{			
				cssName = "alt";
				}	
				%>
				
				<tr class="<%=cssName %>" >
					<TD class="txt" align="left"  width="5%"> &nbsp;<%=(i.intValue() + 1)%>.&nbsp;</TD>
					<TD class="txt" align="left" width="15%"><A href="statusAction.do?methodName=viewSubStatusDetails&leadStatusId=<bean:write name="report" property="leadStatusId"/>&lobId=<bean:write name="report" property="lobId"/>&subStatusId=<bean:write name="report" property="subStatusId"/>" class="Red11" ><bean:write name="report" property="leadSubStatus" /></A></TD>
					<TD class="txt" align="left" width="20%" ><bean:write name="report" property="leadSubStatusDisplay" /></TD>
					<TD class="txt" align="left" width="20%"><bean:write name="report"	property="leadStatus" /></TD>
					<TD class="txt" align="left" width="20%" ><bean:write name="report" property="lobName"/></TD>
					
					<!--  <TD class="txt" align="left" width="15%"><bean:write name="report" property="subStatusId" /></TD>
					<TD class="txt" align="left" width="20%"><bean:write name="report" property="leadStatusId" /></TD>-->
					
					
					
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	</table>	
	<br>
 </div>
	</html:form>