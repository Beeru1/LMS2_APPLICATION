<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%@page import="com.ibm.lms.forms.DownloadLogsFormBean"%><bean:define id="recordslimitdays" name="recordslimitdays" scope="request"></bean:define>
<script language="JavaScript" type="text/javascript">
function validate()
{
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;//January is 0!
	var yyyy = today.getFullYear();
	if(dd<10){dd='0'+dd}
	if(mm<10){mm='0'+mm}
	var curr_dt=dd+'-'+mm+'-'+yyyy;
	var recordslimitdays = '<%=recordslimitdays%>';
	var recordslimitdaysForId='<%=request.getAttribute("recordslimitdaysForId")%>';
	var limitTime1 = recordslimitdays * 86400000;
	var limitTime2=	recordslimitdaysForId * 86400000;
	var date1=document.downloadLogsFormBean.startDate.value;
	var date2= document.downloadLogsFormBean.endDate.value;
	var pattern="~`!#$%^&*+=-[]\\\';./{}|\":<>?";
	var userId=document.downloadLogsFormBean.userId.value;
	var check = true;
	//document.downloadLogsFormBean.flagStatus.value='';
	if(date1==''&& date2=='')
	{
	alert("Please Select dates");
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
	
	if(userId=='')
	{
	if((process(date2) - process(date1)) > limitTime1)
	{
		alert("Difference between dates cannot be greater than "+ recordslimitdays);
		return false;
	}
	}
	
	
	if(userId!='')
	{
	if((process(date2) - process(date1)) > limitTime2)
	{
		alert("Difference between dates cannot be greater than "+ recordslimitdaysForId+" days with UserId");
		return false;
	}
	}
	
	
	if(document.downloadLogsFormBean.llogsId.value == "-1"){
		alert("Please select a report");
		return false;
	}
	//alert(document.downloadLogsFormBean.userId);

	if(userId!='')
	{
	for (var i = 0; i < userId.length; i++) {
	//alert("rrrrrrrrrr");
       if (pattern.indexOf(userId.charAt(i)) != -1) {
           check=false;
       }
    }
    }
    if(check==false)
    {
    	alert("Please separate with commma(,) for multiple userId");
    	return false;
    }
  /*  var str = "How,are,you,doing,today?";
    var res = str.split(',');
    alert("hhhhhh"+res);
    
    for(var i = 0; i < res.length; i++)
    {
   		alert(res[i]);
    }*/
   	//alert("hiiiiii"+document.downloadLogsFormBean.flagStatus.value);
	document.downloadLogsFormBean.methodName.value = "viewLogs";
	document.downloadLogsFormBean.submit();
}

function process(date){
   var parts = date.split("-");
   var dates = new Date(parts[2], parts[1],parts[0]);
   return dates.getTime();
}
function visibleUserIdField()
{
	var id=document.downloadLogsFormBean.llogsId.value;
	document.getElementById("we").style.display= "";
	document.getElementById("wel").style.display= "none";
		document.getElementById("com").style.display= "none";
		document.downloadLogsFormBean.flagStatus.value='';
	//alert(id);
	if(id==16)
	{
		//alert("hiiii");
		document.getElementById("we").style.display= "none";
		document.getElementById("wel").style.display= "";
		document.getElementById("com").style.display= "";
		document.downloadLogsFormBean.flagStatus.value='count';
		
	}
}

function submitForm1()
{
	document.downloadLogsFormBean.flagStatus.value='';
	validate();
}
function submitForm2()
{
	document.downloadLogsFormBean.flagStatus.value='count';
	//alert("hi");
	//alert(document.downloadLogsFormBean.flagStatus.value);
	validate();
}
function reset()
{
	document.downloadLogsFormBean.userId.value='';
	document.downloadLogsFormBean.startDate.value='';
	document.downloadLogsFormBean.endDate.value='';
	document.downloadLogsFormBean.llogsId.value="-1";
}
</script>
<body onload="reset();">
<html:form action="/downloadLogs" >
<html:hidden property="methodName"/>
<html:hidden property="flagStatus" value=""/>
	   <div class="box2">
        <div class="content-upload" style="height:250px ">
        <h1><bean:message key="viewLogs.heading" /></h1>
        
        	<center><font color="#FF0000"><strong>
			<html:messages id="msg" message="true">
					<bean:write name="msg"/>  
			</html:messages></strong></font></center>

		<ul class="list2 form1 ">
			<li class="clearfix">
		 	<font color="red"><bean:write name="downloadLogsFormBean" property="message" /></font>
		 </li>
				<li class="clearfix" id="element">
					<span class="text2 fll width160"><strong><bean:message key="viewLogs.startDate" /> </strong><font color=red>*</font></span>		
					<input type="text" class="tcal calender2 fll" readonly="readonly" name="startDate" />
                	<span class="text2 fll width160">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<strong><bean:message key="viewLogs.endDate" /> </strong><font color=red>*</font></span>		
					<input type="text" class="tcal calender2 fll" readonly="readonly" name="endDate" />
				</li> 
              <li class="clearfix alt" style="height: 40px;">
				<span class="text2 fll width160"><strong><bean:message key="viewLogs.type" /><FONT color="red" size="1">*</FONT></strong></span>
				
				<html:select property="llogsId" name="downloadLogsFormBean" styleId="llogsId" styleClass="select1" onchange="visibleUserIdField();" >
					 	<option value="-1" >Select Logs Type</option>
						
						<logic:notEmpty name="downloadLogsFormBean" property="logTypeList" >
									<bean:define id="elements" name="downloadLogsFormBean" property="logTypeList" /> 
										<html:options labelProperty="logName" property="logsId"  collection="elements" />
						</logic:notEmpty>
				</html:select>
				</li>
				<li class="clearfix alt" id="UsedIdLine">
				<span class="text2 fll width160" id="UserIdLabel"><strong>User Id</strong></span>
				<p class="clearfix fll" style="width: 232px" id="UserIdField"><html:text property="userId" name="downloadLogsFormBean" style="width:150px" styleClass="textbox6"></html:text></p>
				</li>
				
				<li class="clearfix alt" id="UsedIdLine">
				<span class="text2 fll width160" id="UserIdLabel"><strong>Lead Capture Data Id</strong></span>
				<p class="clearfix fll" style="width: 232px" id="LeadCaptureField"><html:text property="leadCaptureId" name="downloadLogsFormBean" style="width:150px" styleClass="textbox6"></html:text></p>
				</li>
				
         </ul>
         <br>
          <div class="button" style="margin-left: 150px" id="we" >
              <input class="submit-btn1 red-btn fll" type="submit" value="" onclick="return validate();" />
            </div>
            <div class="button" style="display:none;margin-left: 150px" id="com" >
             <a class="red-btn" onclick="submitForm1();"><b>Details</b></a>
            </div>
            <div class="button" style="display:none;margin-left: 150px" id="wel" >
             <a class="red-btn" onclick="submitForm2();"><b>Count</b></a>
            </div>
  </div>
  </div>

</html:form>
</body>
 