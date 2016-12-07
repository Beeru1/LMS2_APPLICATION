<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html><HEAD>

<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<script language="javascript"  src="<%=request.getContextPath()+"/jScripts/ajaxCall.js"%>"></script>
<TITLE>Bulk Assignment Matrix Download</TITLE>


<script>


function validate()
{
	if( document.bulkAssignmentDownloadFormBean.selectedCircleId.value == "-1")
	  {
	   alert("Please select Circle Type");
	   return false;
	  }
	  
	  if( document.bulkAssignmentDownloadFormBean.selectedproductlobId.value == "-1")
	  {
	   alert("Please select LOB Type");
	   return false;
	  }
	  
	  if( document.bulkAssignmentDownloadFormBean.userType.value==0 && document.bulkAssignmentDownloadFormBean.userType.value!=null )
	  {
	   alert("Please select User Type");
	   return false;
	  }
	  
		document.bulkAssignmentDownloadFormBean.methodName.value = "downloadAssignment";
		document.forms[0].submit();
	 return true;
	}
	
	//---- changind done by bhaskar to get circle list on change of product
 function getCircleForProduct()
	{
	
		if(undefined != document.forms[0].selectedproductlobId)
	  	 {
	 
		    var productIdAndProductLobId;
		    var product;
		    var productLobId;
		    var product = document.forms[0].selectedproductlobId;
		var productLobId = product.options[product.options.selectedIndex].value;
		//alert(productLobId);
		
    	}
    	var data = "methodName=getCircleOnProductChange&productLobId=" + productLobId;
    	
		doAjax("bulkAssignmentDownload.do", "GET", false, getCircleOnProductChange, data);
	}
var getCircleOnProductChange = function()
	{
	
		if (xmlHttpRequest.readyState == 4 || xmlHttpRequest.readyState == "complete")
	  	{
	  
			var xmldoc = xmlHttpRequest.responseXML.documentElement;
			if (xmldoc == null) return;
			optionValues = xmldoc.getElementsByTagName("option");
			
			var selectObj = document.forms[0].selectedCircleId;
			selectObj.options.length = 0;
			selectObj.options[selectObj.options.length] = new Option("Select Circle", "-1");
			selectObj.options[selectObj.options.length] = new Option("All", "-2");
			for (var i = 0; i < optionValues.length; i++){
				selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"), optionValues[i].getAttribute("value"));
				}
		}
	}
	//---- end of changindg done by bhasker to get circle list on change of product
</script>

</HEAD>
  					

<html:form name="bulkAssignmentDownloadFormBean" type="com.ibm.lms.forms.BulkAssignmentDownloadFormBean" action="/bulkAssignmentDownload" enctype="multipart/form-data" >
	
	<html:hidden name="bulkAssignmentDownloadFormBean" property="methodName" />
	<html:hidden name="bulkAssignmentDownloadFormBean" property="msg" />
	
	
	  <div class="box2">
        <div class="content-upload" style="height:250px ">
        <h1><bean:message key="assignmentDownload.heading" /></h1>
        
        	<center><font color="#FF0000"><strong>
			<html:messages id="msg" message="true">
					<bean:write name="msg"/>  
			</html:messages></strong></font></center>

		<ul class="list2 form1 ">
			<li class="clearfix">
		 	<font color="red"><bean:write name="bulkAssignmentDownloadFormBean" property="msg" /></font>
		 
		 <li class="clearfix alt" style="height: 40px;">
				<span class="text2 fll width160"><strong><bean:message key="assignmentDownload.lobName" /><FONT color="red" size="1">*</FONT></strong></span>
				<html:select property="selectedproductlobId" name="bulkAssignmentDownloadFormBean"  styleClass="select1"  onchange="getCircleForProduct()">
						<option value="-1" >Select LOB Type</option>
						
						
						<logic:notEmpty name="productLobList" scope="request">
						<bean:define id="lobs" 	name="productLobList" scope="request"/> 
						<html:options labelProperty="productLobName" property="productLobID" collection="lobs" />
					</logic:notEmpty>
					
				</html:select>
			</li> 
              <li class="clearfix" style="height: 40px;">
				<span class="text2 fll width160"><strong><bean:message key="assignmentDownload.name" /><FONT color="red" size="1">*</FONT></strong></span>
				<html:select property="selectedCircleId" name="bulkAssignmentDownloadFormBean"  styleClass="select1">
						<option value="-1" >Select Circle Type</option>
						<option value="-2">All</option>
						<!--<logic:notEmpty name="bulkAssignmentDownloadFormBean" property="circleTypeList" >
									<bean:define id="elements" name="bulkAssignmentDownloadFormBean" property="circleTypeList" /> 
										<html:options labelProperty="circleName" property="circleId"  collection="elements" />
						</logic:notEmpty>
				--></html:select>
			</li> 
			
			
			
			 <li class="clearfix alt" > 
			 
			<span class="text2 fll width160"><strong>User Type</strong><font color=red>*</font>  </span>
			 <p class="clearfix fll" style="width: 232px"> 
			 	<html:select  property="userType" name="bulkAssignmentDownloadFormBean" styleClass="select" >
					<html:option value="0" >Select User Type</html:option>
					<html:option value="GIS" >GIS User</html:option>
					<html:option value="normal">Normal User</html:option>
					<html:option value="both">Both</html:option>
				</html:select>
			</p>
		     
		    </li>
 
		<li class="clearfix" style="padding-left:10px;">	
		<span class="text2 fll">&nbsp;</span>
					<center><a class="red-btn" style="margin-right:10px;" tabindex="3" onclick="return validate();"><b>Download</b></a></center>
		     </li>  
         </ul>
         
            
  </div>
  </div>
	
	
	
			
</html:form>

</html:html>

