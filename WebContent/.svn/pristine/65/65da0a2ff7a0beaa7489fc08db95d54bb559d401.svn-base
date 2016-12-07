<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
 <style type="text/css">
      tr:nth-child(2n+1) {
             background-color: #DCDCDC;
        }
</style>
<script language="javascript"  src="<%=request.getContextPath()+"/jScripts/ajaxCall.js"%>"></script>
<script language="javascript"  src="<%=request.getContextPath()+"/jScripts/jquery-1.4.2.min.js"%>"></script>
<script language="JavaScript" type="text/javascript">


 
function GridActionforCountDetails(headerid,reporttype)
{
	
	
	document.getElementById("myTablediv").style.display="block";
	
	var tableHeaderRowCount = 1;
	var table = document.getElementById('myTable');
	var rowCount = table.rows.length;
	for (var i = tableHeaderRowCount; i < rowCount; i++) {
	    table.deleteRow(tableHeaderRowCount);
	}
	var data ="";
	if(headerid==2||headerid==11){
	
		 data = "methodName=PopulateGridfordetails&headerid="+headerid+"&reporttype="+reporttype+"&leadstatusid=300";
	}else if(headerid==3||headerid==12) {
		 data = "methodName=PopulateGridfordetails&headerid="+headerid+"&reporttype="+reporttype+"&leadstatusid=500";
	}
	else if(headerid==4||headerid==13) {
			 data = "methodName=PopulateGridfordetails&headerid="+headerid+"&reporttype="+reporttype+"&leadstatusid=600";	
			}
		
	
	else{
	
	 data = "methodName=PopulateGridfordetails&headerid="+headerid+"&reporttype="+reporttype+"&leadstatusid=0";
	
	}
	doAjax("viewReports.do", "GET", false, Gridfordetails, data);
	
	
	
} 


function GridActionforCountDetailsExcel()
{
	document.reportsFormBean.reportType.value=document.getElementById("reporttype").value;
	document.reportsFormBean.methodName.value= "PopulateGridfordetailsExcelDownload";//&headerid="+headerid+"&reporttype="+reporttype;
	document.reportsFormBean.submit(); 
	
} 


var Gridfordetails = function()
{		
	
	
	if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
  	{
		var xmldoc = xmlHttpRequest.responseXML.documentElement;
		if (xmldoc == null) return;
		//optionValues="";
		optionValues = xmldoc.getElementsByTagName("row");
		for (var i = 0; i < optionValues.length; i++){
		
			var sno=i+1;
			var leadId = optionValues[i].getAttribute("leadId");
	        var customerName = optionValues[i].getAttribute("customerName");
	        var address = optionValues[i].getAttribute("address");
	        var customerMobile = optionValues[i].getAttribute("customerMobile");
	        var longitude = optionValues[i].getAttribute("longitude");
	        var latitude = optionValues[i].getAttribute("latitude");
	        var leadStatus =optionValues[i].getAttribute("leadStatus");
	        var leadSubStatus = optionValues[i].getAttribute("leadSubStatus");
	        var assignedto = optionValues[i].getAttribute("assignedto");
	        var assignedsince = optionValues[i].getAttribute("assignedsince");
	        var lastmodifiedby =optionValues[i].getAttribute("lastmodifiedby");
	        var remarks =optionValues[i].getAttribute("remarks");
	        //headerID
	        document.reportsFormBean.headerid.value =optionValues[i].getAttribute("headerID");
	    

	        var table = document.getElementById("myTable");
	        var row = table.insertRow(sno);
	        var cell1 = row.insertCell(0);
	        var cell2 = row.insertCell(1);
	        var cell3 = row.insertCell(2);
	        var cell4 = row.insertCell(3);
	        var cell5 = row.insertCell(4);
	        var cell6 = row.insertCell(5);
	        var cell7 = row.insertCell(6);
	        var cell8 = row.insertCell(7);
	        var cell9 = row.insertCell(8);
	        var cell10 = row.insertCell(9);
	        var cell11 = row.insertCell(10);
	        var cell12 = row.insertCell(11);
	        var cell13 = row.insertCell(12);
	        
	        cell1.innerHTML = sno;
	        cell2.innerHTML = leadId;
	        cell3.innerHTML = customerName;
	        cell4.innerHTML = address;
	        cell5.innerHTML = customerMobile;
	        cell6.innerHTML = longitude;
	        cell7.innerHTML = latitude;
	        cell8.innerHTML = leadStatus;
	        cell9.innerHTML = leadSubStatus;
	        cell10.innerHTML = assignedto;
	        cell11.innerHTML = assignedsince;
	        cell12.innerHTML=lastmodifiedby;
	        cell13.innerHTML = remarks;
	        cell4.style=" word-break: break-all; width: 140px; ";
	        table.style="font-size:12px;";
	        
	       
	  
	      }
		
}
	
}
	



function GridAction()
{
	
	
	var reportTime = document.reportsFormBean.reportTime.value;	
	if(reportTime =='-1' ){
		alert("Please Select reportType");
		return false;
	}else {
	
	document.reportsFormBean.methodName.value= "searchDatatoPopulateGrid";
	document.reportsFormBean.submit();
	}
	
} 

function validate()
{

	var reportTime = document.reportsFormBean.reportTime.value;	
	
	if(reportTime =='-1' ){
		alert("Please Select reportType");
		return false;
	}
else {
	document.reportsFormBean.methodName.value = "viewLeadMTDReportDay_Month_wise";
	
	document.reportsFormBean.submit();
	}
	
	

}


   
</script>
<body>
<html:form action="/viewReports" >
<html:hidden property="methodName"/>
<html:hidden name="reportsFormBean" property="param" />
<html:hidden property="headerid"/>
<html:hidden property="reportType"/>
	   <div class="box2">
        <div class="content-upload" style="height:150px ">
        <h1><bean:message key="viewReport.heading" /></h1>
       
        	
	 <div class="content-upload">
          <ul class="list2 form1" > 
             <li class="clearfix alt">
			 <span class="text2 fll" style="width: 155px;">Download Report</span>
						<html:select property="reportTime" name="reportsFormBean" styleId="initialSelectBox" styleClass="select1" >
					 	<option value="-1" >Select Report Type</option>
						
						<logic:notEmpty name="reportsFormBean" property="reportTypeList" >
									<bean:define id="elements" name="reportsFormBean" property="reportTypeList" /> 
										<html:options labelProperty="reportName" property="reportId"  collection="elements" />
						</logic:notEmpty>
				</html:select>
					
					
				
					
					
					</li>

      </ul> 
    </div>
         <br><br>
          <div class="button" style="margin-left: 150px">
           <!--    <input class="submit-btn1 red-btn fll" type="button" value="" onclick="return validate();" /> -->
          
          <table><tr><td width="20%"  align="left" valign="top"><input   type="button" value="search" onclick="GridAction()" /></td>
          <td width="20%"  align="left" valign="top"><input   type="button" value="Download" onclick="return validate();" /></td>
          
          </tr></table>
           
     </div>
            
          
             
  </div>
  </div>
 
  
 
    
  <logic:notEqual name="reportsFormBean" property="reportinitStatus" value="false">
  
  
		 <div class="boxt2">
	      <div class="content-upload clearfix">
	       <div class="content-table-type2 clearfix">
	       <h1 >Search Result </h1>
			<table width="100%" border="0" cellspacing="0" cellpadding="2">
			<TR >
                <TH width="1%"  align="center" valign="top">SL#</TH>
                
                <TH width="30%" align="center" valign="top">HEADER</TH>
                
                <TH width="18%" align="center" valign="top"> COUNT</TH>
                
                  <TH width="18%" align="center" valign="top"> PERCENTAGE</TH>
                  
                  
                
                
        
				
			</TR>
			
		<logic:empty  scope="request" name="reportListFtdMtd">
			<TR class="lightBg"><TD class="text" align="center" colspan="11"><bean:message
				
					key="assignmentDownload.NotFound" /></TD>
				
			</TR>
		</logic:empty>
		
		<logic:present name="reportListFtdMtd" scope="request">	
		<logic:notEmpty name="reportListFtdMtd" scope="request">
		
	
			<logic:iterate id="report"  name="reportListFtdMtd" scope="request"  indexId="i" >
			
			 <!--  String cssName = ""; if( i%2==1){cssName = "alt";}%> -->
				<TR ><!-- class="=cssName%>"> -->	
					<TD align="center" valign="top" style="font-size:12px;"><%=(i.intValue() + 1)%>.</TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="columnName" /></TD>
					<TD align="center" valign="top" style="font-size:12px;">
						<logic:equal name="report" property="count" value="0">
							<bean:write name="report" property="count" />
						</logic:equal>
						<logic:notEqual name="report" property="count" value="0">
						
					     
					     <A HREF='#' class="Red11" onclick="GridActionforCountDetails('<bean:write name="report" property="headerid" />','<bean:write name="report" property="reportType" />')" ><bean:write name="report" property="count" /></A>
					    
					     
					     
					     </logic:notEqual>
					</TD>
					<TD align="center" valign="top" style="font-size:12px;"><bean:write name="report" property="percent" /></TD>
					
					
					
				
				
				
				</TR>
			
	
			
			
				
				<input type="hidden" id="reporttype" value="<bean:write name="report" property="reportType" />" />
				
				
			</logic:iterate>  
			</logic:notEmpty>
	</logic:present>
		</table>
	   </div>
	  </div>
     </div>
	</logic:notEqual>  

 <div id="myTablediv" style="display:none">
   <div class="boxt2">
	      <div >
	      <div class="content-table-type2 clearfix" style="height: 400px; overflow:auto;">
	
	  <h1 style="font-family:Arial, Helvetica, sans-serif; margin:0 0px 0px 0px; padding:0 370px 0 10px; background-color:#04b2e4; border-radius:4px 4px 0 0; font-family:Arial, Helvetica, sans-serif; font-size:18px; color:#FFF; height:34px;">Details Result&nbsp; &nbsp; <A HREF='#' class="Red11" onclick="GridActionforCountDetailsExcel()" ><img  src="images/excel.gif" width="25" height="30" border="0"/></A>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
	       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
	       
						</h1>
			
						
						
					
	<table id="myTable"  width="100%" border="0" cellspacing="0" cellpadding="2">
	

	
		<tr class="textwhite">
			<th class="labelText" align="center" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">S.NO</span></th>
			<th class="labelText" align="center" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">LEAD ID</span></th>
			<th class="labelText" align="center" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">NAME</span></th>
			<th class="labelText" align="center" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">ADDRESS</span></th>
			<th class="labelText" align="center" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">MOBILE</span></th>
			<th class="labelText" align="center" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">LATITUDE</span></th>
			<th class="labelText" align="center" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">LONGITUDE</span></th>
			<th class="labelText" align="center" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x; " height="15" ><span class="mTop5">STATUS</span></th>
			<th class="labelText" align="center" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x; " height="15" ><span class="mTop5">SUB STATUS</span></th>
			<th class="labelText" align="center" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x; " height="15" ><span class="mTop5">ASSIGNED TO</span></th>
			<th class="labelText" align="center" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x; " height="15" ><span class="mTop5">ASSIGNED SINCE</span></th>
			<th class="labelText" align="center" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">LAST MODIFIED BY</span></th>
			<th class="labelText" align="center" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="15"  ><span class="mTop5">REMARKS</span></th>
		</tr>
		 <tbody>
		 
		 </tbody>	

	
	</table>
	   </div>
	  </div>
     </div>
     </div>
 


</html:form>

</body>
 