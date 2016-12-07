<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="kmUserBean" name="USER_INFO"  type="com.ibm.lms.dto.UserMstr" scope="session" />

<script language="JavaScript" type="text/javascript">

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
function zoneCheck(){
var actorId = <%= kmUserBean.getKmActorId() %>;
var loginId = '<%= kmUserBean.getUserLoginId() %>';

if(actorId == "4" || actorId == "6" ||  actorId == "15"){

	document.getElementById("createSingleZone").disabled = true;
	
	
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
function process(date){
   var parts = date.split("-");
   var dates = new Date(parts[1] + "-" + parts[0] + "-" + parts[2]);
   return dates.getTime();
}
function validate()
{

//alert("submit");
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
	var lob = document.reportsFormBean.lobId.value;	
	
	if(lob =='' ){
		alert("Please Select Lob");
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
    
	if( document.reportsFormBean.selectType.value == "1")
	  {
	   if(date1 ==''){
		alert("Please Select Start Date");
		return false;
	 	}	
	 if(date2 ==''){
			alert("Please Select End Date");
			return false;
		}
	  }
	  
	document.reportsFormBean.methodName.value = "viewLeadMTDReport";
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
		if(actorId==1 || actorId== 2){		
		url='ajaxSupport.do?mt=getCircleBasedOnLob&selectedLobId='+selectedLobId;
		}
		else{
		
		url='ajaxSupport.do?mt=getCircleBasedOnLobForCo&selectedLobId='+selectedLobId+"&loginId="+ loginId;
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
    function loadPartnerDropdown()
{
		
		//alert("loadPartnerDropdown");
		req1 = newXMLHttpRequest1();
	    //req = newXMLHttpRequest();
	    var url;
	    if (!req1) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req1.onreadystatechange = returnJson1;
	    var selectedLobId = document.reportsFormBean.lobId.value;
	      var circleMstrId = document.reportsFormBean.circleMstrId.value;
		//alert("selectedlob id :::::"+selectedLobId);
		//alert("circleMstrId id :::::"+circleMstrId);
		url='ajaxSupport.do?mt=getChPartnerBasedOnLob&selectedLobId='+selectedLobId+"&circleMstrId="+ circleMstrId;
		
	    
	    req1.open("GET", url, true);
	    req1.send(null);
		

}

// called on populate Channel Partner List 
    function returnJson1() {
    if (req1.readyState == 4) {
        if (req1.status == 200) {
        
            var json = eval('(' + req1.responseText + ')');
            
          //  alert("json "+json);        
			var elements = json.elements;

		
		cleanSelectBox("createPartnerId");

		if (elements.length!= -1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}           
	            	
	         
	            var selectDropDown1 = document.getElementById("createPartnerId");	
	           // alert("selectDropDown1 " + selectDropDown1);
	         
	            cleanSelectBox("createPartnerId");	  
	                      	
	            var opt1 = "Select Channel Partner";
			
				addOption("",opt1, selectDropDown1);       	           
				for (var i = 0; i < elements.length; i++) {
		      
		            addOption(elements[i].userId, elements[i].userFname, selectDropDown1);
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
   
   function loadZoneDropdown()
{

		//loadPartnerDropdown();
		//alert("loadZoneDropdown");	
		/* if(document.reportsFormBean.selectedTypeId.value!="" ){
			document.getElementById("singleZoneId").style.visibility="visible";
		} */		
				
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Create User module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson2;
	   //document.reportsFormBean.selectedTypeId.value;
	    var circleMstrId = document.reportsFormBean.circleMstrId.value;
		//alert("selectedTypeId id :::::"+selectedTypeId);
		//alert("circle Mstr Id id :::::"+circleMstrId);
		//alert(document.reportsFormBean.zoneFlag.value);
		if(document.reportsFormBean.zoneFlag.value = 'CZ')
		{
		 var selectedTypeId =  '2' ;
		  var url= 'ajaxSupport.do?mt=getZoneBasedOnCircle&selectedTypeId='+selectedTypeId+'&circleMstrId='+circleMstrId;
		}
		else {
		 var selectedTypeId =  '1' ;
		  var url= 'ajaxSupport.do?mt=getZoneBasedOnCircle&selectedTypeId='+selectedTypeId+'&circleMstrId='+circleMstrId;
		}
	
	    
	    req.open("GET", url, true);
	    req.send(null);
		

}

// called on populate Zone List 
    function returnJson2() {
    if (req.readyState == 4) {
        if (req.status == 200) {
        
            var json = eval('(' + req.responseText + ')');
            
            //alert("json "+json);        
			var elements = json.elements;

		cleanSelectBox("createSingleZone");
		if (elements.length!= -1)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	     }           
	            	
	            var selectDropDown = document.getElementById("createSingleZone");	
	            
	            cleanSelectBox("createSingleZone");	 
	                      	
	            var opt1 = "Select Zone";
				addOption("-2",opt1, selectDropDown);         	           
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].zoneId, elements[i].zoneName, selectDropDown);
		        }	
		        return true;	        
		    }	    
        }
       }
        
    }
   
</script>
<body onload="zoneCheck();">
<html:form action="/viewReports" >
<html:hidden property="methodName"/>
<html:hidden name="reportsFormBean" property="param" />
	   <div class="box2">
        <div class="content-upload" style="height:250px ">
        <h1><bean:message key="viewReport.heading" /></h1>
        <html:hidden property="zoneFlag" name="reportsFormBean"/>
        	<center><font color="#FF0000"><strong>
			<html:messages id="msg" message="true">
					<bean:write name="msg"/>  
			</html:messages></strong></font></center>

	 <div class="content-upload">
          <ul class="list2 form1" > 
          
            <li class="clearfix ">
			 <span class="text2 fll" style="width: 155px;">Lob Name</span>
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
					<html:select property="circleMstrId" styleId="createSingle" name="reportsFormBean" styleClass="select1" onchange="javascript:loadZoneDropdown();loadPartnerDropdown();">
								<html:option value="">Select Circle</html:option>
								<logic:notEmpty name="reportsFormBean" property="circleList" >
									<bean:define id="circles" name="reportsFormBean" property="circleList" /> 
										<html:options labelProperty="circleName" property="circleMstrId"  collection="circles" />
								</logic:notEmpty>
					</html:select>
			</li>
	 	
			<li class="clearfix" id="singleZoneId" >
		
			 <span class="text2 fll" style="width: 155px;">Select Zone/City Zone </span></td>
					<html:select property="selectedZoneId" styleId="createSingleZone" name="reportsFormBean" disabled="false" styleClass="select1" >
								<html:option value="0">Select Zone</html:option>
								<logic:notEmpty name="reportsFormBean" property="zoneList" >
									<bean:define id="zones" name="reportsFormBean" property="zoneList" /> 
										<html:options labelProperty="zoneName" property="zoneId"  collection="zones" />
								</logic:notEmpty>
					</html:select>
							
		</li> 
		
          <li class="clearfix alt">
				
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
								<html:option value="" >Select Status</html:option>
								<logic:notEmpty name="reportsFormBean" property="statusList" >
									<bean:define id="status" name="reportsFormBean" property="statusList" /> 
										<html:options labelProperty="leadStatus" property="leadStatusId"  collection="status" />
								</logic:notEmpty>
					</html:select>
			</li>  
			
			<li>
			
			<span class="text2 fll " style="width: 155px;">Select Type</span>
			 	<html:select property="selectType" styleId="selectType" name="reportsFormBean" styleClass="select">
					<html:option value="0" >Select Type</html:option>
					<html:option value="1">Updated Date</html:option>
					</html:select>
				</li>
		 <li class="clearfix alt">
          	<span class="text2 fll" style="width: 155px;" >Start Date &nbsp;&nbsp;&nbsp;  </span>
          	<input type="text" name="startDate" value="<bean:write name="reportsFormBean" property="startDate"/>" readonly="readonly" class="tcal calender2 fll" align="left">
			<span class="text2 fll" style="width: 155px;" >&nbsp;&nbsp;&nbsp;&nbsp;End Date </span>
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
</body>
 