<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<LINK href="./theme/css.css" rel="stylesheet" type="text/css">

<LINK href="./jsp/theme/css.css" rel="stylesheet" type="text/css">
<bean:define id="recordslimitdays" name="recordslimitdays" scope="session"></bean:define>
<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript"><!--

function importExcel()
{
document.LeadForm.methodName.value= "excelImport";
document.LeadForm.submit();
}
function uploadExcel()
{
 if(document.LeadForm.newFile.value==""){
		alert("Please Select the file");
		return false;
	}else if(!(/^.*\.xls$/.test(document.LeadForm.newFile.value)) && !(/^.*\.xlsx$/.test(document.LeadForm.newFile.value)) ) {
			alert("Please select a xls or xlsx file only.");
			return false;
				
	}else{  
		document.LeadForm.methodName.value= "uploadExcel";
		//document.LeadForm.submit();
		return true;
	}
}

function limitdays(date1 , date2){

     var parts = date1.split("-");
   var dates = new Date(parts[1] + "-" + parts[0] + "-" + parts[2]); 
   var parts1 = date2.split("-");
   var dates1 = new Date(parts1[1] + "-" + parts1[0] + "-" + parts1[2]); 
    // The number of milliseconds in one day
    var ONE_DAY = 1000 * 60 * 60 * 24;
    // Convert both dates to milliseconds
    var date1_ms = dates.getTime();
    var date2_ms = dates1.getTime();
    // Calculate the difference in milliseconds
    var difference_ms = Math.abs(date1_ms - date2_ms);    
    // Convert back to days and return    
   
    return Math.round(difference_ms/ONE_DAY);
}

function validateForm()
{
	var recordslimitdays = '<%=recordslimitdays%>';
	var limitTime = recordslimitdays * 	86400000;
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;//January is 0!
	var yyyy = today.getFullYear();
	if(dd<10){dd='0'+dd}
	if(mm<10){mm='0'+mm}
	var curr_dt=dd+'-'+mm+'-'+yyyy;
	var date1 = document.LeadForm.startDate.value;
	var date2 = document.LeadForm.endDate.value;
	if(date1 ==''){
		alert("Please Select Start Date");
		return false;
	}	
	if(date2 ==''){
		alert("Please Select End Date");
		return false;
	}	
	if(process(date1) > process(curr_dt)){
		alert("Selected Start Date Cannot be future date");
		return false;
	}
	if(process(date2) > process(curr_dt)){
		alert("Selected End Date Cannot be future date");
		return false;
	}
	if(process(date1) > process(date2)){
		alert("Start Date cannot be greater than End date");
		return false;
	}
<!--	if((process(date2) - process(date1)) > limitTime)-->
<!--	{-->
<!--		alert("Difference between dates cannot be greater than "+ recordslimitdays);-->
<!--		return false;-->
<!--	}-->
		var noOfDays = limitdays(date1 , date2);
	 	 var days = document.LeadForm.param.value;
        if (noOfDays > days)
    {
    	alert("Difference between start date and end date cannot exceed " + days +" days");
		return false;
    }
	document.LeadForm.methodName.value= "searchLeadFeasibility";
	document.LeadForm.submit();
return false;
}

function process(date){
   var parts = date.split("-");
   var dates = new Date(parts[1] + "-" + parts[0] + "-" + parts[2]);
   return dates.getTime();
}

function forwardLeads()
{
	
var oForm = document.LeadForm;
if(!isleadsChecked()){
alert("Select Leads to forward!");return false;
}
document.getElementById("forward").style.display = "block";
return false;
}
function isleadsChecked()
	{
		var idsChecked = false;
		var oForm = document.forms[0].leadIDs[0];
		if(undefined == oForm)
		{
			if (document.forms[0].leadIDs.checked == true)
				idsChecked = true;
		}
		else{
		oForm = document.forms[0].leadIDs;
		for (counter = 0; counter < oForm.length; counter++)
		{
			if ( oForm[counter].checked == true)
			{
				idsChecked = true;
				break;
			}
		}
		}
		return idsChecked;
	}
function leadForward()
{
	var oForm = document.LeadForm;
	oForm.methodName.value = "forwardTheLead";
	if(oForm.forwardTo[oForm.forwardTo.selectedIndex].value == ""){alert("User Name is required!");oForm.forwardTo.focus();return false;}
	if(oForm.forwardComments.value == ""){alert("Comments are required!"); oForm.forwardComments.focus(); return false;}
	if(oForm.forwardComments.value.length >1000){alert("Comments can not be grater than 1000!"); oForm.forwardComments.focus(); return false;}
	oForm.submit();
}
function selectAllCheckbox()
	{
		var oForm = document.forms[0].leadIDs[0];
		if(document.forms[0].selectAll.checked){
		if(undefined == oForm)
		{
		  if(undefined != document.forms[0].leadIDs)
		  document.forms[0].leadIDs.checked = true;
		}
		var oform = document.forms[0].leadIDs;
		for (counter = 0; counter < oform.length; counter++)
		{
				 	oform[counter].checked = true;
			}   	
			}
			else
			{
		if(undefined == oForm)
		{
		  if(undefined != document.forms[0].leadIDs)
		  document.forms[0].leadIDs.checked = false;
		}
		var oform = document.forms[0].leadIDs;
		for (counter = 0; counter < oform.length; counter++)
		{
				 	oform[counter].checked = false;
			}   	
			}
			
	}
</script>


<html:form action="assignedAcceleration.do"  enctype="multipart/form-data">
	<html:hidden name="LeadForm" property="methodName" value="searchLead"/>
	<html:hidden name="LeadForm" property="leadID" />
	<html:hidden name="LeadForm" property="view" />
	<html:hidden name="LeadForm" property="param" />
	<div class="box2">               
	
		<div class="content-upload" style="margin-bottom: 0px;">
          <h1><bean:message key="escalation.feasibility" /></h1>
        </div>
      <tr align="left">
       <strong style="margin-left: 10px"> 
       <FONT color="red" style="font-size: small">
			<td colspan="10"><strong><bean:write property="msg"
				name="LeadForm" /></strong></td>
		</FONT>
		</strong>
		</tr>
		
        <logic:equal name="LeadForm" property="isError" value="true">
			   <a href="./qualifiedLeads.do?methodName=openErrLog" target="_new">click here</a>
		    </logic:equal>
         <div class="content-upload">
          <ul class="list2 form1" >   
		 <li class="clearfix alt">
          	<span class="text2 fll width160 rightAlign"><strong>Starting Date &nbsp;&nbsp;&nbsp;<font color=red></font> </strong> </span>
          	<input type="text" name="startDate"  readonly="readonly" class="tcal calender2 fll" value="<bean:write name="LeadForm" property="startDate"/>">
			<span class="text2 fll width160 rightAlign" ><strong>End Date &nbsp;&nbsp;&nbsp;<font color=red></font></strong> </span>
			<input type="text" name="endDate"  readonly="readonly" class="tcal calender2 fll" value="<bean:write name="LeadForm" property="endDate"/>">
			<span class="text2 fll" >&nbsp;</span><div class="button" >
			<html:submit styleClass="red-btn" value="Search" onclick="return validateForm()"/>
			</li>
			<!--<logic:equal value="N" property="view" name="LeadForm">
			 <li class="clearfix" >
				<span class="text2 fll width160"><strong>Select File: </strong> </span>	

				<p class="clearfix fll margin-r20"> 
				  <html:file property="newFile"></html:file>
				</p>	
				<span class="text2 fll">&nbsp;</span>
				<input type="image" src="images/submit.jpg" style="margin-right:10px;" onclick="return uploadExcel();" styleClass="red-btn fll"/>&nbsp;&nbsp;&nbsp;&nbsp;
			 <img  src="images/excel.gif" width="39" height="35" border="0" onclick="importExcel();" /> </li>
			
			 </logic:equal>-->
</ul> 
</div>
        </div>
        
	 <div class="box2" >               
		<div class="content-upload" style="margin-bottom: 0px;">
          <h1><bean:message key="view.assignedLeads" /></h1>
        </div>
         
        
        <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" >
        
				<logic:empty name="LeadForm" property="assignedLeads">
			<TR class="lightBg">
				<TD class="text" align="center" colspan="6"><bean:message
					key="viewAllUser.NotFound" /></TD>
			</TR>
		</logic:empty>        
			<logic:notEmpty name="LeadForm" property="assignedLeads">
        <tr class="textwhite">
        <logic:equal value="N" name="LeadForm" property="view">
        <td id="selectAll"  style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="5%">
        <logic:notEmpty name="LeadForm" property="assignedLeads">
        <input type="checkbox" name="selectAll" onclick="selectAllCheckbox()"/>
        </logic:notEmpty>
        </td>
        </logic:equal>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="5%"><span class="mTop5">
			&nbsp;SL.</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5"><bean:message
				key="view.leadID" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="15%"><span class="mTop5"><bean:message
				key="view.productName" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="20%"><span class="mTop5"><bean:message
				key="view.primaryUser" /></span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="20%"><span class="mTop5">
			  Start Time</span></td>
			<td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="20%"><span class="mTop5">
			  End Time</span></td>
			  <td style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35" width="20%"><span class="mTop5">
			  Sub Status</span></td>
			
		</tr>
        

            
		
		
	
			<logic:iterate name="LeadForm" id="report" indexId="i" property="assignedLeads">
				<%
				String cssName = "";				
				if( (i.intValue()%2)==1)
				{			
				cssName = "alt";
				}	
				%>
				
				<tr class="<%=cssName %>" >
				<logic:equal value="N" name="LeadForm" property="view">
					<td align="left"  width="5%"><input type="checkbox" id="leadIDs"  value="<bean:write name="report" property="leadID" />" name="leadIDs"/></td>
					</logic:equal>
					<TD class="txt" align="left"  width="5%"> &nbsp;<%=(i.intValue() + 1)%>.&nbsp;</TD>
					<TD class="txt" align="left" width="15%"><A
						href="assignedAcceleration.do?methodName=viewLeadDetails&leadID=<bean:write name="report" property="leadID"/>" ><FONT color="red"><bean:write name="report"
						property="leadID" /></FONT></A>
					</TD>
					<TD class="txt" align="left" width="15%"><bean:write name="report"
						property="product" /></TD>
					<TD class="txt" align="left" width="15%"><bean:write name="report"
						property="primaryUser" /></TD>
					<TD class="txt" align="left" width="20%"><bean:write name="report"
						property="startTime" /></TD>
					<TD class="txt" align="left" width="20%" ><bean:write name="report"
						property="endTime" /></TD>
						<TD class="txt" align="left" width="20%" ><bean:write name="report"
						property="subStatus" /></TD>
				</TR>
			</logic:iterate>
			<logic:equal value="N" name="LeadForm" property="view">
			<input type="button"  style="margin-right:10px;" onclick="return forwardLeads();" class="red-btn fll" height="10px" value="forward"/>&nbsp;&nbsp;&nbsp;&nbsp;	
		</logic:equal>
		</logic:notEmpty>
	
	</table>
	<br>
		<div class="content-upload" style="display:none" id="forward">
			<fieldset>
							<legend><h5>Forward</h5></legend>
							<table border="0" width="100%" cellpadding="1" cellspacing="1">
							<tr>
								<td width="35%" class="text" align="right"><font color="#4b0013"><b>Forward To <font color="#ff0000">*</font>&nbsp;:</b></font></td>
								<td width="65%" class="pTop3 pLeft5 text">
									<html:select styleClass="width180" property="forwardTo" >
										<html:option value="">Select</html:option>
										<logic:present name="userList" scope="request">
										<logic:notEmpty name="userList" scope="request">
											<html:optionsCollection name="userList" value="userLoginId" label="userFname"/>
										</logic:notEmpty>
										</logic:present>
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
			</fieldset>
			</div>
 </div>
</html:form>
