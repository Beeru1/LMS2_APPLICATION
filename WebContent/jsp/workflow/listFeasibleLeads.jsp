<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<LINK href="./theme/css.css" rel="stylesheet" type="text/css">
<LINK href="./theme/text.css" rel="stylesheet" type="text/css">
<LINK href="./jsp/theme/css.css" rel="stylesheet" type="text/css">
<bean:define id="recordslimitdays" name="recordslimitdays" scope="session"></bean:define>
<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript">
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
	}else{  
		document.LeadForm.methodName.value= "uploadExcel";
		return true;
	}
}
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
    var days = document.LeadForm.param.value;
   
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
	document.LeadForm.methodName.value= "searchLead";
document.LeadForm.submit();
return false;
}
</script>


<html:form action="feasibleLeads.do" enctype="multipart/form-data">
<html:hidden name="LeadForm" property="methodName" value="searchLead"/>
<html:hidden name="LeadForm" property="leadID" />
<html:hidden name="LeadForm" property="param" />
	<div class="box2">               
		<div class="content-upload" style="margin-bottom: 0px;">
		
          <h1><bean:message key="search.feasibilityLeads" /></h1>
          <FONT color="red" style="font-size: small">
          <strong style="margin-left: 10px"> 
          			<logic:present name = "LeadForm" property="msg"> 
          			<logic:notEmpty  name = "LeadForm" property="msg">               		
          			<bean:write property="msg" name="LeadForm"/>                       
             		</logic:notEmpty> 
             		</logic:present>
             		<logic:equal name="LeadForm" property="isError" value="true">
			   <a href="./feasibleLeads.do?methodName=openErrLog" target="_new">click here</a>
		    </logic:equal>
            		</strong>
            </FONT>	
		
			
		    
        </div>
      
         <div class="content-upload">
          <ul class="list2 form1" >   
		 <li class="clearfix alt">
          	<span class="text2 fll width160 rightAlign" ><strong>Start Date &nbsp;&nbsp;&nbsp;<font color=red></font> </strong> </span>
          	<input type="text" name="startDate" value="<bean:write name="LeadForm" property="startDate"/>" readonly="readonly" class="tcal calender2 fll" align="left">
			<span class="text2 fll width160 rightAlign" ><strong>End Date &nbsp;&nbsp;&nbsp;<font color=red></font></strong> </span>
			<input type="text" name="endDate" value="<bean:write name="LeadForm" property="endDate"/>" readonly="readonly" class="tcal calender2 fll" align="left">
			<span class="text2 fll" >&nbsp;</span><div class="button" >
			<html:submit styleClass="red-btn" value="Search" onclick="return validateForm()"/>
			</li>
			 <li class="clearfix" >
				<span class="text2 fll width160"><strong>Select File: </strong> </span>	

				<p class="clearfix fll margin-r20"> 
				  <html:file property="newFile"></html:file>
				</p>	
				<span class="text2 fll">&nbsp;</span>
			<input type="image" src="images/submit.jpg" style="margin-right:10px;" onclick="return uploadExcel();" styleClass="red-btn fll"/>&nbsp;&nbsp;&nbsp;&nbsp;
			 <img  src="images/excel.gif" width="39" height="35" border="0" onclick="importExcel();" /> </li>
      </ul> 
    </div>
   </div> <br/>
   
   <div class="flr">	
    <div class="content-upload">
      <ul class="list2 form1" >   
      </ul>
     </div>
			  
    </div>
        		
        	<br/><br/>
	
	<div class="box2">               
	 <div class="content-upload" style="margin-bottom: 0px;">
       <h1><bean:message key="view.feasibilityLeads" /></h1>
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
					<TD class="txt" align="left" width="15%"><A
						href="feasibleLeads.do?methodName=viewLeadDetail&leadID=<bean:write name="report" property="leadID"/>" ><FONT color="red"><bean:write name="report"
						property="leadID" /></FONT></A>
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