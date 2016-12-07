<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<LINK href="./theme/css.css" rel="stylesheet" type="text/css">
<LINK href="./theme/text.css" rel="stylesheet" type="text/css">
<LINK href="./jsp/theme/css.css" rel="stylesheet" type="text/css">
<bean:define id="recordslimitdays" name="recordslimitdays" scope="session"></bean:define>
<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript">

function process(date){
   var parts = date.split("-");
   var dates = new Date(parts[1] + "-" + parts[0] + "-" + parts[2]); 
   return dates.getTime();
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
	
	if(document.LeadForm.circleMstrId.value ==''){
		alert("Please Select Circle");
		return false;
	}	
	if(document.LeadForm.statusId.value ==''){
		alert("Please Select Status");
		return false;
	}
	
	
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
		var noOfDays = limitdays(date1 , date2);
	 	 var days = document.LeadForm.param.value;
        if (noOfDays > days)
    {
    	alert("Difference between start date and end date cannot exceed " + days +" days");
		return false;
    }
	/* 	if((date2-date1) > recordslimitdays ){
		alert("esdfdsfd");
		return false;
	} */

document.LeadForm.methodName.value = "searchNewLead";
document.LeadForm.submit();
return false;
}


function searchLead()
{ 
    var leadId = trimAll(document.forms[0].leadId.value);
    document.forms[0].leadId.value = leadId;
    
    var contactNo = trimAll(document.forms[0].contactNo.value);
    document.forms[0].contactNo.value = contactNo;
    
    if(leadId=="" && contactNo=="" )
	{	
		alert("Please enter Lead Id or Mobile No.");
		document.forms[0].leadId.focus();
		return false;
	}
	else
	{
		if(leadId !="" )
		{	
		   if( leadId.length <14 || !isInteger(document.forms[0].leadId) )
			{
			  alert("Please enter 14 digit numeric Lead Id.");
			  document.forms[0].leadId.select();
			  return false;
			}
		}
		
		if(contactNo !="" )
		{	
		   if(contactNo.length <10 || !isInteger(document.forms[0].contactNo) )
			{
			  alert("Please enter 10 digit Mobile No.");
			  document.forms[0].contactNo.select();
			  return false;
			}
		}
	
	document.forms[0].methodName.value="searchLead";
	document.forms[0].submit();
	return true;
	}	
}
</script>


<html:form action="feasibleLeads.do" enctype="multipart/form-data">
<html:hidden name="LeadForm" property="methodName" value="searchLead"/>
<html:hidden name="LeadForm" property="leadID" />
<html:hidden name="LeadForm" property="param" />

	<div class="box2">               
		<div class="content-upload" style="margin-bottom: 0px;">
		
          <h1><bean:message key="new.feasibilityLeads" /></h1>
          <FONT color="red" style="font-size: small">
          <strong style="margin-left: 10px"> 
          			<logic:present name = "LeadForm" property="msg"> 
          			<logic:notEmpty  name = "LeadForm" property="msg">               		
          			<bean:write property="msg" name="LeadForm"/>                       
             		</logic:notEmpty> 
             		</logic:present>
             	
            		</strong>
            </FONT>	
		
			
		    
        </div>
      
         <div class="content-upload">
          <ul class="list2 form1" > 
          
          <li class="clearfix alt">
				
			 <span class="text2 fll" style="width: 155px;">Select Circle <FONT color="red" size="1">*</FONT></span></td>
					<html:select property="circleMstrId" styleId="createSingle" name="LeadForm" styleClass="select1">
								<html:option value="">Select Circle</html:option>
								<logic:notEmpty name="LeadForm" property="circleList" >
									<bean:define id="circles" name="LeadForm" property="circleList" /> 
										<html:options labelProperty="circleName" property="circleId"  collection="circles" />
								</logic:notEmpty>
					</html:select>
			</li>
			
			<li class="clearfix ">
				
			 <span class="text2 fll" style="width: 155px;">Select Status <FONT color="red" size="1">*</FONT></span></td>
					<html:select property="statusId" styleId="statusId" name="LeadForm" styleClass="select1">
								<html:option value="">Select Status</html:option>
								<logic:notEmpty name="LeadForm" property="statusList" >
									<bean:define id="status" name="LeadForm" property="statusList" /> 
										<html:options labelProperty="leadStatusName" property="leadStatusId"  collection="status" />
								</logic:notEmpty>
					</html:select>
			</li>  
			
		 <li class="clearfix alt">
          	<span class="text2 fll" style="width: 155px;" ><strong>Start Date &nbsp;&nbsp;&nbsp;<font color="red"  size="1">*</font> </strong> </span>
          	<input type="text" name="startDate" value="<bean:write name="LeadForm" property="startDate"/>" readonly="readonly" class="tcal calender2 fll" align="left">
			<span class="text2 fll" style="width: 155px;" ><strong>&nbsp;&nbsp;&nbsp;&nbsp;End Date <font color="red" size="1">*</font></strong> </span>
			<input type="text" name="endDate" value="<bean:write name="LeadForm" property="endDate"/>" readonly="readonly" class="tcal calender2 fll" align="left">
			<span class="text2 fll" >&nbsp;</span><div class="button" >
			<html:submit styleClass="red-btn" value="Search" onclick="return validateForm()"/>
			</li>
			
      </ul> 
    </div>
   </div> <br/>
        	<br/><br/>
	
	<div class="box2">               
	 <div class="content-upload" style="margin-bottom: 0px;">
       <h1><bean:message key="view.newfeasibilityLeads" /></h1> <span>Records Found : <bean:write name="LeadForm" property="totalRec"/></span>
     </div>
         
     <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0">
        <tr class="textwhite">
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
			
		</tr>
        

            
		<logic:empty name="LeadForm" property="assignedLeads">
			<TR class="lightBg">
				<TD class="text" align="center" colspan="6"><bean:message
					key="viewAllUser.NotFound" /></TD>
			</TR>
		</logic:empty>
		
		<logic:notEmpty name="LeadForm" property="assignedLeads">
			<logic:iterate name="LeadForm" id="report" indexId="i" property="assignedLeads">
				<%
				String cssName = "";				
				if(( i.intValue()%2)==1)
				{			
				cssName = "alt";
				}	
				%>
				
				<tr class="<%=cssName %>" >
					<TD class="txt" align="left"  width="5%"> &nbsp;<%=(i.intValue() + 1)%>.&nbsp;</TD>
					<TD class="txt" align="left" width="15%"><A	href="feasibleLeads.do?methodName=viewLeadDetails&leadID=
					<bean:write name="report" property="leadID"/>" >
						<FONT color="red"><bean:write name="report"	property="leadID" /></FONT></A>
					</TD>
					<TD class="txt" align="left" width="15%"><bean:write name="report"
						property="product" /></TD>
					<TD class="txt" align="left" width="20%"><bean:write name="report"
						property="primaryUser" /></TD>
					<TD class="txt" align="left" width="20%"><bean:write name="report"
						property="startTime" /></TD>
					<TD class="txt" align="left" width="20%" ><bean:write name="report"
						property="endTime" /></TD>
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	
	</table>	<br>
 </div>
</html:form>