<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="kmUserBean" name="USER_INFO"  type="com.ibm.lms.dto.UserMstr" scope="session" />

<script language="JavaScript" type="text/javascript"><!--
//author:Nancy Agrawal

 function newXMLHttpRequest1() {

    var xmlreq1 = false;

    if (window.XMLHttpRequest) {
        // Create XMLHttpRequest object in non-Microsoft browsers
        xmlreq1 = new XMLHttpRequest();

    } else if (window.ActiveXObject) {

        // Create XMLHttpRequest via MS ActiveX
        try {
            // Try to create XMLHttpRequest in later versions
            // of Internet Explorer

            xmlreq1 = new ActiveXObject("Msxml2.XMLHTTP");

        } catch (e1) {

            // Failed to create required ActiveXObject

            try {
                // Try version supported by older versions
                // of Internet Explorer

                xmlreq1 = new ActiveXObject("Microsoft.XMLHTTP");

            } catch (e2) {

                // Unable to create an XMLHttpRequest with ActiveX
            }
        }
    }

    return xmlreq1;
    
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
//validation function
function validate()
{
	
	 var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1;
	var yyyy = today.getFullYear();
	if(dd<10){dd='0'+dd}
	if(mm<10){mm='0'+mm}
	var curr_dt=dd+'-'+mm+'-'+yyyy;
	var date1 = document.reportsFormBean.startDate.value;
	var date2 = document.reportsFormBean.endDate.value;	
	//var lob = document.forms[0].lobId.value;	
	
	//if(lob =='' ){
		//alert("Please Select Lob");
		//return false;
	//}
		
	//if(date1 =='' && date2 ==''){
		//alert("Please Select the Dates");
		//return false;
	//}
	
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
	 //var noOfDays = limitdays(date1 , date2);
	 	// var days = document.forms[0].param.value;
	 	 //alert("noOfDays**"+noOfDays+"*********and days="+days);
        //if (noOfDays > days)
    //{
    	//alert("Difference between start date and end date cannot exceed " + days +" days");
		//return false;
    //}
	document.forms[0].methodName.value = "viewiDOCReport";
	document.form[0].submit();
}

function cleanSelectBox(selectBox)
  	{
  	var obj = document.getElementById(selectBox);
    if (obj == null) return;
	if (obj.options == null) return;
	while (obj.options.length > 0) 
	{
		obj.remove(0);
	}
   } 



function importExcel()
{
document.forms[0].methodName.value= "excel_Import";
document.forms[0].submit();
}





function process(date){
   var parts = date.split("-");
   var dates = new Date(parts[1] + "-" + parts[0] + "-" + parts[2]);
   return dates.getTime();
}
</script>

<html:form action="/viewReports">
<html:hidden property="methodName"/>
<html:hidden name="reportsFormBean" property="param" />
	   <div class="box2">
        <div class="content-upload" style="height:250px ">
        <h1><bean:message key="viewiDOCReports.heading" /></h1>
       	<center><font color="#FF0000"><strong>
			<html:messages id="msg" message="true">
					<bean:write name="msg"/>  
			</html:messages></strong></font></center>

	 <div class="content-upload">
          <ul class="list2 form1" >
          
          <!-- 
            <li class="clearfix ">
			 <span class="text2 fll" style="width: 155px;">Lob</span>
					<td ><html:select property="lobId" styleId="initialSelectBox" name="reportsFormBean" styleClass="select1"></td>
			<html:option value="">
				<bean:message key="createUser.select" />
			</html:option>
			<logic:notEmpty name="reportsFormBean" property="lobList">
				<bean:define id="lobs" name="reportsFormBean" property="lobList" />
				<html:options labelProperty="lobName" property="lobId" collection="lobs" />
			</logic:notEmpty>
			</html:select></li>
			-->
			</br>
			<li class="clearfix alt">
          	<span class="text2 fll" style="width: 155px;" >Start Date &nbsp;  </span>
          	<input type="text" name="startDate" value="<bean:write name="reportsFormBean" property="startDate"/>" readonly="readonly" class="tcal calender2 fll" align="left">
			<span class="text2 fll" style="width: 155px;" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;End Date  &nbsp;&nbsp;</span>
			<input type="text" name="endDate" value="<bean:write name="reportsFormBean" property="endDate"/>" readonly="readonly" class="tcal calender2 fll" align="left">
			
			</li>
			</ul>
			</div>
          <li class="clearfix alt">
          <br><br>
          
          <div class="button" style="margin-left: 300px">
          <tr>
           <span>  <input class="submit-btn1 red-btn fll" type="submit" value="" onclick="return validate();" /></span><!--
            <img  src="images/excel.gif" width="39" height="35" border="0" onclick="importExcel();" /> </li>
            --></tr>
            </div>
            
 </div>
   </div>
</html:form>
</body>
          