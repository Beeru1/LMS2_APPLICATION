<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<LINK href="./theme/css.css" rel="stylesheet" type="text/css">
<LINK href="./theme/text.css" rel="stylesheet" type="text/css">
<LINK href="./jsp/theme/css.css" rel="stylesheet" type="text/css">
<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript">

function validate()
{
	//alert("validate");
	if(document.KmViewDashboardBean.selectedReportId.value == "-1"){
		alert("Please select a report");
		return false;
	}
	var selectBox = document.getElementById("selectedReportId");
	var date = new Date;
	var hour = date.getHours();
	var restrictedflag=document.KmViewDashboardBean.restrictedflag.value;
	if(restrictedflag=='Y')
	{
	var restrictedTime=document.KmViewDashboardBean.restrictedTime.value;
	var a = restrictedTime.split("-") // Delimiter is a string	
	var restrictStart=a[0];
	var restrictend=a[1];
		var startHour = restrictStart.split(":")[0];
 		var startMinute = restrictStart.split(":")[1];
		var startSecond = "00";
		var startTimeObject = new Date();
 		startTimeObject.setHours(startHour, startMinute, startSecond);
		
		var endHour = restrictend.split(":")[0];
 		var endMinute = restrictend.split(":")[1];
 		var endSecond = "00";
		var endTimeObject = new Date(startTimeObject);
 		endTimeObject.setHours(endHour, endMinute, endSecond);

		var currentobj = new Date();
	//alert("Current Hour is = "+hour);
	
	if((currentobj.getTime() >startTimeObject.getTime())  && (currentobj.getTime() <endTimeObject.getTime()) )
	{
		alert("During the time range "+restrictStart +" Hours to "+restrictend+" Hours DashBoard is Restricted");
		return false;
	}
	}
	else
	{
	
		alert("Report has been made not available!!");
		return false;
	
	}
	
	if(selectBox.options.selectedIndex == 3)
	{
		var startTime=document.KmViewDashboardBean.startHour.value;
		//alert("startTime of Hourly Report is "+startTime);
		if(hour<startTime)
		{
			alert("Hourly Report gives details after "+startTime+"  Hours");
			return false;
		}
	}
	/*if(selectBox.options.selectedIndex == 3)
	{
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;//January is 0!
	var yyyy = today.getFullYear();
	if(dd<10){dd='0'+dd}
	if(mm<10){mm='0'+mm}
	var curr_dt=dd+'-'+mm+'-'+yyyy;
	var date=document.KmViewDashboardBean.reportDate.value;
	
	if(date==''){
		alert("Please Select Date");
		return false;
	}
	alert("date .."+date);
	if(process(date) > process(curr_dt)){
	alert("Inside if");
	alert("Selected Date Cannot be a current or future date");
	return false;
	}
	}*/

	//alert("submit");
	document.KmViewDashboardBean.methodName.value = "viewReport";
	document.KmViewDashboardBean.submit();
}

function process(date){
   var parts = date.split("-");
   var dates = new Date(parts[1] + "-" + parts[0] + "-" + parts[2]);
   return dates.getTime();
}

function importExcel()
{

if(document.KmViewDashboardBean.selectedReportId.value == "-1"){
		alert("Please select a report");
		return false;
	}
	var selectBox = document.getElementById("selectedReportId");
	var date = new Date;
	var hour = date.getHours();
	var restrictedflag=document.KmViewDashboardBean.restrictedflag.value;
	if(restrictedflag=='Y')
	{
	var restrictedTime=document.KmViewDashboardBean.restrictedTime.value;
	var a = restrictedTime.split("-") // Delimiter is a string	
	var restrictStart=a[0];
	var restrictend=a[1];
		var startHour = restrictStart.split(":")[0];
 		var startMinute = restrictStart.split(":")[1];
		var startSecond = "00";
		var startTimeObject = new Date();
 		startTimeObject.setHours(startHour, startMinute, startSecond);
		
		var endHour = restrictend.split(":")[0];
 		var endMinute = restrictend.split(":")[1];
 		var endSecond = "00";
		var endTimeObject = new Date(startTimeObject);
 		endTimeObject.setHours(endHour, endMinute, endSecond);

		var currentobj = new Date();
	//alert("Current Hour is = "+hour);
	
	if((currentobj.getTime() >startTimeObject.getTime())  && (currentobj.getTime() <endTimeObject.getTime()) )
	{
		alert("During the time range "+restrictStart +" Hours to "+restrictend+" Hours DashBoard is Restricted");
		return false;
	}
	}
	else
	{
	
		alert("Report has been made not available!!");
		return false;
	
	}
	
	if(selectBox.options.selectedIndex == 3)
	{
		var startTime=document.KmViewDashboardBean.startHour.value;
		//alert("startTime of Hourly Report is "+startTime);
		if(hour<startTime)
		{
			alert("Hourly Report gives details after "+startTime+"  Hours");
			return false;
		}
	}

document.KmViewDashboardBean.methodName.value= "excelImport";
document.KmViewDashboardBean.submit();
}
/*
function hideAndShow()
{
	//alert("hideAndShow");
	var selectBox = document.getElementById("selectedReportId");
	var datefield = document.getElementById("element");
	alert("selectBox.options.selectedIndex"+selectBox.options.selectedIndex);
	if(selectBox.options.selectedIndex == 3)
	{
		datefield.style.display= "block";
	}
	else
	{
		datefield.style.display= "none";
	}	
}*/
//function setReport()
//{
//document.KmViewDashboardBean.selectedReportId.value = "-1";
//}
</script>
<body onload=setReport();>
<html:form action="/dashBoard" >
<html:hidden property="methodName"/>
<html:hidden property="restrictedTime"/>
<html:hidden property="restrictedflag"/>
<html:hidden property="startHour"/>
	   <div class="box2">
        <div class="content-upload" style="height:250px ">
        <h1><bean:message key="dashboard.heading" /></h1>
        
        	<center><font color="#FF0000"><strong>
			<html:messages id="msg" message="true">
					<bean:write name="msg"/>  
			</html:messages></strong></font></center>

		<ul class="list2 form1 ">
			<li class="clearfix">
		 
			 </li>
			
              <li class="clearfix alt" style="height: 40px;">
				<span class="text2 fll width160"><strong><bean:message key="viewReports.type" /><FONT color="red" size="1">*</FONT></strong></span>
				<html:select property="selectedReportId" name="KmViewDashboardBean" styleId="selectedReportId" styleClass="select1" > <!-- onchange="hideAndShow();" -->
						<option value="-1" >Select Report Type</option>
						<logic:notEmpty name="KmViewDashboardBean" property="reportTypeList" >
									<bean:define id="elements" name="KmViewDashboardBean" property="reportTypeList" /> 
										<html:options labelProperty="reportName" property="reportId"  collection="elements" />
						</logic:notEmpty>
				</html:select>
			</li> 
			
			<li class="clearfix" id="element">
				<!--	<span class="text2 fll width160"><strong><bean:message key="viewReports.date" /> </strong><font color=red>*</font></span>	-->	
						<input type="hidden" class="tcal calender2 fll" readonly="readonly" name="reportDate" value="<bean:write property='reportDate' name='KmViewDashboardBean'/>"/>
                	
				</li>  
			<li>
			<font color="red" style="font-size: small;"> <bean:write property='message' name='KmViewDashboardBean'/> <font/>
			</li>
         </ul>
         <br><br>
          
          <li class="clearfix"><span class="text2 fll">&nbsp;</span>
              <input  class="submit-btn1 red-btn fll" type="submit" value="" onclick="return validate();" />&nbsp;&nbsp;&nbsp;&nbsp;
			 <img  src="images/excel.gif" width="39" height="35" border="0" onclick="importExcel();" />
           
            </li>
            
  </div>
  </div>
  <table width="100%" align="left" border="0" cellpadding="0" cellspacing="0">
        <tr class="textwhite">
			<td height="35" width="5%"><span class="mTop5">&nbsp;SL.</span></td>
			<td  height="35" width="15%"><span class="mTop5"><bean:message 	key="view.week" /></span></td>
			<td  height="35" width="10%"><span class="mTop5"><bean:message 	key="view.submittedLead" /></span></td>
			<td  height="35" width="10%"><span class="mTop5"><bean:message
				key="view.createdLead" /></span></td>
			<td  height="35" width="20%"><span class="mTop5"><bean:message
				key="view.senttoDialerLeads" /></span></td>
				<td  height="35" width="15%"><span class="mTop5"><bean:message
				key="view.qualifiedLeads" /></span></td>
			<td  height="35" width="15%"><span class="mTop5"><bean:message
				key="view.feasibilityLeads" /></span></td>
			
			
			<td  height="35" width="10%"><span class="mTop5"><bean:message
				key="view.AssignedLeads" /></span></td>	
		</tr>
        

            
		<logic:empty name="KmViewDashboardBean" property="reportDataList">
			<TR class="lightBg">
				<TD class="text" align="center" colspan="6"><bean:message
					key="viewAllUser.NotFound" /></TD>
			</TR>
		</logic:empty>
		
		<logic:notEmpty name="KmViewDashboardBean" property="reportDataList">
			<logic:iterate name="KmViewDashboardBean" id="report" indexId="i" property="reportDataList">
				<%
				
				String cssName = "";				
				if(( i.intValue()%2)==1)
				{			
			
				cssName = "alt";
				}	
				%>
				
				<tr class="<%=cssName %>" >
					<TD class="txt" align="left"  width="5%"> &nbsp;<%=(i.intValue() + 1)%>.&nbsp;</TD>
					<TD class="txt" align="left" width="20%"><bean:write name="report"
						property="param" /></TD>
					<TD class="txt" align="left" width="5%"><bean:write name="report"
						property="submittedLead" /></TD>
					<TD class="txt" align="left" width="10%"><bean:write name="report"
						property="createdLead" /></TD>
					<TD class="txt" align="left" width="20%"><bean:write name="report"
						property="senttoDialerLeads" /></TD>
						<TD class="txt" align="left" width="15%" ><bean:write name="report"
						property="qualifiedLeads" /></TD>
					<TD class="txt" align="left" width="15%" ><bean:write name="report"
						property="feasibilityLeads" /></TD>
					<TD class="txt" align="left" width="10%" ><bean:write name="report"
						property="assignedLeads" /></TD>	
				</TR>
			</logic:iterate>
		</logic:notEmpty>
	
	</table>
  
  
  </html:form>

 </body>