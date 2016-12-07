<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@page import="com.ibm.lms.dto.UserMstr" %>
<script>
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


function searchReport()
{ 


	//var limitTime = recordslimitdays * 	86400000;
	
	 var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;//January is 0!
	var yyyy = today.getFullYear();
	if(dd<10){dd='0'+dd}
	if(mm<10){mm='0'+mm}
	var curr_dt=dd+'-'+mm+'-'+yyyy;
	var date1 = document.reportsFormBean.startDate.value;
	var date2 = document.reportsFormBean.endDate.value;	
	
		
/* 	if(date1 =='' && date2 ==''){
		alert("Please Select Date");
		return false;
	}
	
	if(date1 =='' && date2 !=''){
		alert("Please Select Start Date");
		return false;
	}	
	if(date1 !='' && date2 ==''){
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
	 	 var days = document.reportsFormBean.param.value;
        if (noOfDays > days)
    {
    	alert("Difference between start date and end date cannot exceed " + days +" days");
		return false;
    } */
    	
	 	 var obj = document.reportsFormBean.reportType.value;
	 	
  		 var date = document.reportsFormBean.date.value;
  		 if(obj == "2") {   	 
   		 if(date ==''){
		 alert("Please Select Date");
		 return false;
			}
   	 		}   
	document.reportsFormBean.methodName.value = "viewDashboardReport";
	document.reportsFormBean.submit();
}

//Added By Bhaskar
function importExcel()
{


	//var limitTime = recordslimitdays * 	86400000;
	
	 var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;//January is 0!
	var yyyy = today.getFullYear();
	if(dd<10){dd='0'+dd}
	if(mm<10){mm='0'+mm}
	var curr_dt=dd+'-'+mm+'-'+yyyy;
	var date1 = document.reportsFormBean.startDate.value;
	var date2 = document.reportsFormBean.endDate.value;	
	
	
		
	/* if(date1 =='' && date2 ==''){
		alert("Please Select Date");
		return false;
	}
	
	if(date1 =='' && date2 !=''){
		alert("Please Select Start Date");
		return false;
	}	
	if(date1 !='' && date2 ==''){
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
	 	 var days = document.reportsFormBean.param.value;
        if (noOfDays > days)
    {
    	alert("Difference between start date and end date cannot exceed " + days +" days");
		return false;
    } */
    	
    var obj = document.reportsFormBean.reportType.value;
    
  	var date = document.reportsFormBean.date.value;
  	 if(obj == "2") {   	 
   	if(date ==''){
		alert("Please Select Date");
		return false;
		}
   	 }   
   
	
document.reportsFormBean.methodName.value= "excelImport";
document.reportsFormBean.submit();
}



function submitSearch(event) {
    if (event.keyCode == 13)
    {
       return searchLead();        
    }
     return true;
}


   function cleanSelectBox(selectBox)
  	{
  	var obj = document.getElementById(selectBox);
    if (obj == null) return;
	if (obj.options == null) return;
	while (obj.options.length > 0) {
		obj.remove(0);
	}
  }
  
  function onReportChange()
  	{
  	if(document.getElementById("date1")!=null){
  	document.getElementById("date1").style.visibility="hidden";
  	}
  	
  	var obj = document.getElementById("initialSelectBox").value;
  
  	 if(obj == "2") {    
  	 
   	 document.getElementById("date").style.visibility="visible";
    }   
    else{
    	document.reportsFormBean.date.value = '';
      document.getElementById("date").style.visibility="hidden";
    }
	
  }

</SCRIPT> 

<html:form action="/viewReports" enctype="multipart/form-data" method="post" >
	<html:hidden property="methodName" value=""/>
      <div class="box2">
      <html:hidden name="reportsFormBean" property="param" />
        <div class="content-upload">
          <h1>DashBoard Report</h1>            
          
             
             <ul class="list2 form1" >
                <li class="clearfix alt">
         <span class="text2 fll" style="width: 155px;"><strong>Report Type</strong></span>
				<html:select property="reportType" styleId="initialSelectBox" name="reportsFormBean" styleClass="select1" onchange="return onReportChange();" >
								<html:option value="">Select ReportType</html:option>
								<html:option value="1">MTD </html:option>
								<html:option value="2">Daily </html:option>
								
										
								
					</html:select>
         
         </li>
          <li class="clearfix " id="date"  style="visibility: hidden;" >
   	       
          	<span class="text2 fll width160 rightAlign"><strong>Select Date &nbsp;&nbsp;&nbsp;<font color=red></font> </strong> </span>
          	<input type="text" name="date"  readonly="readonly" class="tcal calender2 fll" value="<bean:write name="reportsFormBean" property="date"/>">
			
			
			</li>
        	<logic:equal name="reportsFormBean" property="reportType" value="2">
   	         <li class="clearfix " id="date1"  >
   	       
          	<span class="text2 fll width160 rightAlign"><strong>Select Date &nbsp;&nbsp;&nbsp;<font color=red></font> </strong> </span>
          	<input type="text" name="date"  readonly="readonly" class="tcal calender2 fll" value="<bean:write name="reportsFormBean" property="date"/>">
			
			
			</li>
			</logic:equal>
			
			  <li class="clearfix " id="dates" style="display:none">
          	<span class="text2 fll width160 rightAlign"><strong>Start Date &nbsp;&nbsp;&nbsp;<font color=red></font> </strong> </span>
          	<input type="text" name="startDate"  readonly="readonly" class="tcal calender2 fll" value="<bean:write name="reportsFormBean" property="startDate"/>">
			<span class="text2 fll width160 rightAlign" ><strong>End Date &nbsp;&nbsp;&nbsp;<font color=red></font></strong> </span>
			<input type="text" name="endDate"  readonly="readonly" class="tcal calender2 fll" value="<bean:write name="reportsFormBean" property="endDate"/>">
			<span class="text2 fll" >&nbsp;</span>
			
			</li>		
   	         <li class="clearfix alt" >
					
				<span class="text2 fll">&nbsp;</span>
			<center><input type="image" src="images/submit.jpg"   style="margin-right:10px;" onclick="return searchReport();" styleClass="red-btn fll" />&nbsp;&nbsp;&nbsp;&nbsp;
			 <img  src="images/excel.gif" width="39" height="35" border="0" onclick="importExcel();" /></center> </li>
		    
            </ul>
            
            
       </div>
       <br>
     </div>
     
  <logic:notEqual name="reportsFormBean" property="initStatus" value="true">
	   
	   <logic:notEmpty name="LEAD_LIST">
			<bean:define id="leadList" name="LEAD_LIST" type="java.util.ArrayList" scope="request"  />
		</logic:notEmpty>	
		<logic:notEmpty name="leadList" >
		 <div class="boxt2">
	      <div class="content-upload clearfix">
	       <div class="content-table-type2 clearfix">
	       <h1 >Search Result </h1>
			<table width="100%" border="0" cellspacing="0" cellpadding="2">
			<TR >
                <TH width="1%"  align="left" valign="top">SL#</TH>
                <TH width="10%" align="left" valign="top">LOB </TH>
                <TH width="18%" align="left" valign="top">Product </TH>
				<TH width="20%" align="left" valign="top">Open + Verification</TH>
				<TH width="10%" align="left" valign="top">Qualified + Wired + Assigned</TH>
                <TH width="10%" align="left" valign="top">Feasibility</TH>
				<TH width="23%" align="left" valign="top">Unwired + Lost</TH>
				<TH width="13%" align="left" valign="top">Info_inedequate</TH>
				<TH width="13%" align="left" valign="top">Won</TH>
				<TH width="13%" align="left" valign="top">Total</TH>
			</TR>
		
			<logic:iterate name="leadList" type="com.ibm.lms.dto.DashBoardReportDTO" id="report" indexId="i">
			 <!--  String cssName = ""; if( i%2==1){cssName = "alt";}%> -->
				<TR ><!-- class="=cssName%>"> -->	
					<TD class="txt" width="1%" align="left" valign="top" style="font-size:12px;" ><%=(i.intValue() + 1)%>.</TD>					
					<TD class="txt" width="10%" align="left" valign="top"  style="font-size:12px;" ><bean:write name="report" property="lobName"  /></TD>
					<TD class="txt" width="18%" align="left" valign="top"  style="font-size:12px;" ><bean:write name="report" property="poductName"  /></TD>
					<TD class="txt" width="20%" align="left" valign="top"  style="font-size:12px;" ><bean:write name="report" property="open_veri" /></TD>
					<TD class="txt" width="10%" align="left" valign="top"  style="font-size:12px;" ><bean:write name="report" property="quali_wired_assi" /></TD>
					<TD class="txt" width="10%" align="left" valign="top"  style="font-size:12px;" ><bean:write name="report" property="faesibility" /></TD>
					<TD class="txt" width="23%" align="left" valign="top"  style="font-size:12px;" ><bean:write name="report" property="unwir_lost" /></TD>
					<TD class="txt" width="13%" align="left" valign="top"  style="font-size:12px;" ><bean:write name="report" property="infoinedu" /></TD>
					<TD class="txt" width="13%" align="left" valign="top"  style="font-size:12px;" ><bean:write name="report" property="won" /></TD>
					<TD class="txt" width="13%" align="left" valign="top"  style="font-size:12px;" ><bean:write name="report" property="total" /></TD>
				</TR>
			</logic:iterate>  
		</table>
	   </div>
	  </div>
     </div>
	</logic:notEmpty>
   
 </logic:notEqual>
 
 <script>
  
   
 </SCRIPT> 
 
 
</html:form>