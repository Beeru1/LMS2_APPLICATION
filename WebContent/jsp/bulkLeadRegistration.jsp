<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html><HEAD>

<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<TITLE>Bulk Lead Registration</TITLE>


<script>

function formsubmit(){

     
    if(document.getElementById("leadType").value=="0") {
			alert("Please select lead type.");
			return false;
				
	}
	else
     if(document.bulkLeadRegistrationFormBean.newFile.value=="")
     {
		alert("Please Select the file");
		return false;
	 }
	else
	if(!(/^.*\.xls$/.test(document.bulkLeadRegistrationFormBean.newFile.value)) && !(/^.*\.xlsx$/.test(document.bulkLeadRegistrationFormBean.newFile.value))) {
			alert("Please select xls or xlsx file only.");
			return false;
				
	}
	
	else
	{  
	if(document.getElementById("leadType").value == "1")
		document.forms[0].methodName.value="uploadOpenLeads";
	else	
		if(document.getElementById("leadType").value == "2")
		  document.forms[0].methodName.value="uploadQualifiedLeads";
	
	return true;
	}
	
}

</script>
</HEAD>
  					

<html:form name="bulkLeadRegistrationFormBean" type="com.ibm.lms.forms.BulkLeadRegistrationFormBean" action="/bulkLeadRegistration" enctype="multipart/form-data">
	
	<html:hidden name="bulkLeadRegistrationFormBean" property="methodName" value="uploadOpenLeads"/>
	
			
		<div class="box2">
        <div class="content-upload">
        
        <H1>Bulk Lead Registration</H1>
        
			
		 <FONT color="red" style="font-size: small; margin-left: 10px;">
		 
			<html:messages id="msg" message="true">
            <bean:write name="msg"/>  
            </html:messages>			
            
            <logic:equal name="bulkLeadRegistrationFormBean" property="isError" value="true">
			   <a href="./bulkLeadRegistration.do?methodName=openErrLog" target="_new">click here</a>
		    </logic:equal>
            			
		    </FONT> 
					
			 
			 <ul class="list2 form1 "> 
			 
			 <li class="clearfix alt" > 
			 
			 <span style="margin-left: 360px"> 
			
			 <logic:equal name="bulkLeadRegistrationFormBean" property="downloadTemplate" value="true">
			<a  href="./bulkLeadRegistration.do?methodName=downloadTemplate&leadType=1"
			target="_new" style="font-size:small; ">Click here to download <b style="color: red">Open Leads</b></a> /    
		    
			 <a  href="./bulkLeadRegistration.do?methodName=downloadTemplate&leadType=2"
			target="_new" style="font-size:small"><b style="color: red">Qualified Leads </b> template</a>   
		    </logic:equal>
		    
		    </span>
		    
		    </li>
		    
		    <li class="clearfix" > 
			 
			<span class="text2 fll width160"><strong>Lead Type</strong><font color=red>*</font>  </span>
			 <p class="clearfix fll" style="width: 232px"> 
			 	<html:select styleId="leadType" property="leadType" name="bulkLeadRegistrationFormBean" styleClass="select" >
					<html:option value="0" >Select Lead Type</html:option>
					<html:option value="1" >Open Leads</html:option>
					<html:option value="2">Qualified Leads</html:option>
					
				</html:select>
			</p>
		    
		    </li>
			 
			  <li class="clearfix alt" >
				<span class="text2 fll width160"><strong>Select File:</strong><font color=red>*</font>  </span>	

				<p class="clearfix fll margin-r20"> 
				   <html:file property="newFile"/> 
				</p>		
			  </li>
			   
	   <li class="clearfix" style="padding-left:170px;">
			<span class="text2 fll">&nbsp;</span>
			<input type="image" src="images/submit.jpg" style="margin-right:10px;" onclick="return formsubmit();" styleClass="red-btn fll"/>&nbsp;&nbsp;&nbsp;&nbsp;	
		</li>
				
	 <li class="clearfix alt" >
				<span class="text2 fll"><strong><font color="red" style="font-size: small; margin-left: 10px;">Note: Address and City Code are mandatory for uploading Qualified Telemedia Leads </font></strong> </span>	
	</li>	
	
			</ul>
			
			
	</div>
	</div>		

			
</html:form>

</html:html>

