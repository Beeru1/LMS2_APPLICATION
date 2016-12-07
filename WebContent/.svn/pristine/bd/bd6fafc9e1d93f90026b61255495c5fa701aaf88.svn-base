<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html><HEAD>

<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<TITLE>Channel Partner Capability Upload</TITLE>


<script>

function formsubmit(){
      
       
     if(document.bulkMstrForm.newFile.value==""){
		alert("Please Select the file");
		return false;
	}else if(!(/^.*\.xls$/.test(document.bulkMstrForm.newFile.value)) && !(/^.*\.xlsx$/.test(document.bulkMstrForm.newFile.value)) ) {
			alert("Please select a xls or xlsx file only.");
			return false;
				
	}else if(navigator.userAgent.indexOf("MSIE") != -1) 
	      {
		   if(document.bulkMstrForm.newFile.value.indexOf(":\\") == -1 ) {
			alert("Please Select a proper file.")
			return false;
		   }
		}else{  
		    document.forms[0].mstrType.value="15";
		    document.forms[0].methodName.value='uploadExcel';
			return true;
		}
	
}

</script>
</HEAD>
  					

<html:form name="bulkMstrForm" type="com.ibm.lms.forms.BulkMstrFormBean" action="/bulkMstr" enctype="multipart/form-data">
	
	<html:hidden name="bulkMstrForm" property="methodName" value="uploadExcel"/>
	<html:hidden name="bulkMstrForm" property="mstrType" value="15"/>
	
			
		<div class="box2">
        <div class="content-upload">
        
        <H1>Channel Partner Capability Upload</H1>
        
			
		 <FONT color="red" style="font-size: small; margin-left: 10px;">
		 
			<html:messages id="msg" message="true">
            <bean:write name="msg"/>  
            </html:messages>			
            
            <logic:equal name="bulkMstrForm" property="isError" value="true">
            
			   <a href="./bulkMstr.do?methodName=openErrLog&filePath=<%=request.getAttribute("filePath")%>+&mstrType=15" target="_new">click here</a>
		    </logic:equal>
            			
		    </FONT> 
			 <ul class="list2 form1 "> 
			 
			 <li class="clearfix" > 
			 
			 <logic:equal name="bulkMstrForm" property="downloadTemplate" value="true">
			 <u> <a class="flr" href="./bulkMstr.do?methodName=downloadTemplate&mstrType=15" target="_new" ><FONT color="blue" size="2">Click here to download Channel Partner Capability Upload</FONT></a><br> </u> 
			 </logic:equal>
		       
		    
		    </li>
			 
			 <li class="clearfix alt" >
				<span class="text2 fll width160"><strong>Select File: </strong> </span>	

				<p class="clearfix fll margin-r20"> 
				   <html:file property="newFile"/> 
				</p>		
			  </li>
			   <li class="clearfix" style="padding-left:170px;">
			<span class="text2 fll">&nbsp;</span>
			<input type="image" src="images/submit.jpg" style="margin-right:10px;" onclick="return formsubmit();" styleClass="red-btn fll"/>&nbsp;&nbsp;&nbsp;&nbsp;</li>
			</ul>
	</div>
	</div>			
</html:form>
</html:html>

