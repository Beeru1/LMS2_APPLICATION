<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
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
	var date=document.reportsFormBean.reportDate.value;
	
	if(date==''){
		alert("Please Select Date");
		return false;
	}
	
	if(process(date) >= process(curr_dt)){
	alert("Selected Date Cannot be a current or future date");
	return false;
	}
	
	if(document.reportsFormBean.selectedReportId.value == "-1"){
		alert("Please select a report");
		return false;
	}
	
	document.reportsFormBean.methodName.value = "viewReport";
	document.reportsFormBean.submit();
}

function process(date){
   var parts = date.split("-");
   var dates = new Date(parts[1] + "-" + parts[0] + "-" + parts[2]);
   return dates.getTime();
}
</script>
<html:form action="/viewReports" >
<html:hidden property="methodName"/>
	   <div class="box2">
        <div class="content-upload" style="height:250px ">
        <h1><bean:message key="viewReports.heading" /></h1>
        
        	<center><font color="#FF0000"><strong>
			<html:messages id="msg" message="true">
					<bean:write name="msg"/>  
			</html:messages></strong></font></center>

		<ul class="list2 form1 ">
			<li class="clearfix">
		 	<font color="red"><bean:write name="reportsFormBean" property="message" /></font>
		 </li>
				<li class="clearfix" id="element">
					<span class="text2 fll width160"><strong><bean:message key="viewReports.date" /> </strong><font color=red>*</font></span>		
					<input type="text" class="tcal calender2 fll" readonly="readonly" name="reportDate" value="<bean:write property='reportDate' name='reportsFormBean'/>"/>
                	
				</li> 
              <li class="clearfix alt" style="height: 40px;">
				<span class="text2 fll width160"><strong><bean:message key="viewReports.type" /><FONT color="red" size="1">*</FONT></strong></span>
				<html:select property="selectedReportId" name="reportsFormBean" styleId="selectedReportId" styleClass="select1">
						<option value="-1" >Select Report Type</option>
						<logic:notEmpty name="reportsFormBean" property="reportTypeList" >
									<bean:define id="elements" name="reportsFormBean" property="reportTypeList" /> 
										<html:options labelProperty="reportName" property="reportId"  collection="elements" />
						</logic:notEmpty>
				</html:select>
			</li> 

         </ul>
         <br><br>
          <div class="button" style="margin-left: 150px">
              <input class="submit-btn1 red-btn fll" type="submit" value="" onclick="return validate();" />
            </div>
            
  </div>
  </div>

</html:form>

 