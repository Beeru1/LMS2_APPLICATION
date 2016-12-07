<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="kmUserBean" name="USER_INFO"  type="com.ibm.lms.dto.UserMstr" scope="session" />

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
function validate()
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
	
		
	if(date1 =='' && date2 ==''){
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
    }
	
	
	document.reportsFormBean.methodName.value = "viewLeadLifecycleReport";
	document.reportsFormBean.submit();
}

function process(date){
   var parts = date.split("-");
   var dates = new Date(parts[1] + "-" + parts[0] + "-" + parts[2]);
   return dates.getTime();
}

function loadCircleDropdown()
{
	//alert("loadCircleDropdown");
	    req = newXMLHttpRequest();
	    var url;
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson;
	    var selectedLobId = document.reportsFormBean.lobId.value;
		//alert("selectedlob id :::::"+selectedLobId);

		var actorId = <%= kmUserBean.getKmActorId() %>;
		var loginId = '<%= kmUserBean.getUserLoginId() %>';
	
		if(actorId == '3'){
			url='ajaxSupport.do?mt=getCircleBasedOnLobForCo&selectedLobId='+selectedLobId+"&loginId="+ loginId;
		}
		else
		{
	    	url= 'ajaxSupport.do?mt=getCircleBasedOnLob&selectedLobId='+selectedLobId;
	    }
	    
	    req.open("GET", url, true);
	    req.send(null);
		

}

// called on populate circle List 
    function returnJson() {
    if (req.readyState == 4) {
        if (req.status == 200) {
        
            var json = eval('(' + req.responseText + ')');
            
            //alert("json "+json);        
			var elements = json.elements;

		
		cleanSelectBox("createSingle");

		if (elements.length!= -1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           
	            	
	         
	            var selectDropDown1 = document.getElementById("createSingle");	
	            
	         
	            cleanSelectBox("createSingle");	  
	                      	
	            var opt1 = "Select Circles";
			
				addOption("",opt1, selectDropDown1);       	           
				for (var i = 0; i < elements.length; i++) {
		          
		            addOption(elements[i].circleMstrId, elements[i].circleName, selectDropDown1);
		        }	
		        return true;	        
		    }	    
        }
       }
        
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
</script>
<html:form action="/viewReports" >
<html:hidden property="methodName"/>
<html:hidden name="reportsFormBean" property="param" />
	   <div class="box2">
        <div class="content-upload" style="height:250px ">
        <h1><bean:message key="viewReport.heading" /></h1>
        
        	<center><font color="#FF0000"><strong>
			<html:messages id="msg" message="true">
					<bean:write name="msg"/>  
			</html:messages></strong></font></center>

	 <div class="content-upload">
          <ul class="list2 form1" > 
          
            <li class="clearfix ">
			 <span class="text2 fll" style="width: 155px;"><strong>Lob Name</strong></span>
					<td ><html:select property="lobId" styleId="initialSelectBox" name="reportsFormBean" styleClass="select1" onchange="javascript:loadCircleDropdown();">
								<html:option value=""><bean:message key="createUser.select" /></html:option>
								<logic:notEmpty name="reportsFormBean" property="lobList" >
									<bean:define id="lobs" name="reportsFormBean" property="lobList" /> 
										<html:options labelProperty="lobName" property="lobId"  collection="lobs" />
								</logic:notEmpty>
					</html:select>
					</li>
          <li class="clearfix alt">
				
			 <span class="text2 fll" style="width: 155px;">Select Circle </span></td>
					<html:select property="circleMstrId" styleId="createSingle" name="reportsFormBean" styleClass="select1" >
								<html:option value="">Select Circle</html:option>
								<logic:notEmpty name="reportsFormBean" property="circleList" >
									<bean:define id="circles" name="reportsFormBean" property="circleList" /> 
										<html:options labelProperty="circleName" property="circleId"  collection="circles" />
								</logic:notEmpty>
					</html:select>
			</li>
			
						</li>
   <!--       <li class="clearfix alt">
				
			 <span class="text2 fll" style="width: 155px;">Channel Partner </span></td>
					<html:select property="chPartnerId" styleId="createPartnerId" name="reportsFormBean" styleClass="select1">
								<html:option value="">Select Channel Partner</html:option>
								<logic:notEmpty name="reportsFormBean" property="partnerList" >
									<bean:define id="circles" name="reportsFormBean" property="partnerList" /> 
										<html:options labelProperty="userFname" property="userId"  collection="circles" />
								</logic:notEmpty>
					</html:select>
			</li>
	  	
			<li class="clearfix ">
				
			 <span class="text2 fll" style="width: 155px;">Select Status </span></td>
					<html:select property="statusId" styleId="statusId" name="reportsFormBean" styleClass="select1">
								<html:option value="">Select Status</html:option>
								<logic:notEmpty name="reportsFormBean" property="statusList" >
									<bean:define id="status" name="reportsFormBean" property="statusList" /> 
										<html:options labelProperty="leadStatus" property="leadStatusId"  collection="status" />
								</logic:notEmpty>
					</html:select>
			</li>  
		-->		
		  <li class="clearfix alt">
          	<span class="text2 fll" style="width: 155px;" ><strong>Start Date &nbsp;&nbsp;&nbsp; <FONT color="red" size="1">*</FONT></strong> </span>
          	<input type="text" name="startDate" value="<bean:write name="reportsFormBean" property="startDate"/>" readonly="readonly" class="tcal calender2 fll" align="left">
			<span class="text2 fll" style="width: 155px;" ><strong>&nbsp;&nbsp;&nbsp;&nbsp;End Date <FONT color="red" size="1">*</FONT></strong> </span>
			<input type="text" name="endDate" value="<bean:write name="reportsFormBean" property="endDate"/>" readonly="readonly" class="tcal calender2 fll" align="left">
			
			</li> 
			
      </ul> 
    </div>
         <br><br>
          <div class="button" style="margin-left: 150px">
              <input class="submit-btn1 red-btn fll" type="button" value="" onclick="return validate();" />
            </div>
            
  </div>
  </div>
</html:form>

 