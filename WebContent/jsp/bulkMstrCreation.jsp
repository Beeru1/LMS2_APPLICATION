<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<html:html><HEAD>

<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<TITLE>Bulk Master creation</TITLE>


<script>

function formsubmit(){
      
      
      
       
     if(document.bulkMstrForm.mstrType.value=="" || document.bulkMstrForm.mstrType.value=="-2"){
		alert("Please Select Master Type");
		return false;
	}
      
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
			document.forms[0].methodName.value='uploadExcel';
			return true;
		}
	
}

</script>
</HEAD>
  					

<html:form name="bulkMstrForm" type="com.ibm.lms.forms.BulkMstrFormBean" action="/bulkMstr" enctype="multipart/form-data">
	
	<html:hidden name="bulkMstrForm" property="methodName" value="uploadExcel"/>
	
			
		<div class="box2">
        <div class="content-upload">
        
        <H1>Bulk Master Management</H1>
        
			
		 <FONT color="red" style="font-size: small; margin-left: 10px;">
		 
			<html:messages id="msg" message="true">
            <bean:write name="msg"/>  
            </html:messages>			
            
            <logic:equal name="bulkMstrForm" property="isError" value="true">
			   <a href="./bulkMstr.do?methodName=openErrLog&filePath=<%=request.getAttribute("filePath")%>" target="_new">click here</a>
		    </logic:equal>
            			
		    </FONT> 
			 <ul class="list2 form1 "> 
			 
			 <li class="clearfix" > 
			 
			 <logic:equal name="bulkMstrForm" property="downloadTemplate" value="true">
			 <u> <a class="flr" href="./bulkMstr.do?methodName=downloadTemplate&mstrType=1" target="_new" ><FONT color="blue" size="2">Click here to download Zone template</FONT></a><br> </u> 
			 <u> <a class="flr" href="./bulkMstr.do?methodName=downloadTemplate&mstrType=2" target="_new" ><FONT color="blue" size="2">Click here to download City template</FONT></a><br> </u>
			 <u> <a class="flr" href="./bulkMstr.do?methodName=downloadTemplate&mstrType=3" target="_new" ><FONT color="blue" size="2">Click here to download City Zone template</FONT></a><br> </u>  
		   	 <u> <a class="flr" href="./bulkMstr.do?methodName=downloadTemplate&mstrType=4" target="_new" ><FONT color="blue" size="2">Click here to download PinCode template</FONT></a><br> </u>
		   	 <u> <a class="flr" href="./bulkMstr.do?methodName=downloadTemplate&mstrType=5" target="_new" ><FONT color="blue" size="2">Click here to download RSU template</FONT></a><br> </u>
		   	 <u> <a class="flr" href="./bulkMstr.do?methodName=downloadTemplate&mstrType=6" target="_new" ><FONT color="blue" size="2">Click here to download Auto Assignment Matrix template</FONT></a></br> </u>
		     <u> <a class="flr" href="./bulkMstr.do?methodName=downloadTemplate&mstrType=7" target="_new" ><FONT color="blue" size="2">Click here to download Channel Partner Template</FONT></a> </br></u>
		     <u> <a class="flr" href="./bulkMstr.do?methodName=downloadTemplate&mstrType=8" target="_new" ><FONT color="blue" size="2">Click here to download State Template</FONT></a> <br></u>
		     <u> <a class="flr" href="./bulkMstr.do?methodName=downloadTemplate&mstrType=9" target="_new" ><FONT color="blue" size="2">Click here to download Agency Assignment Template</FONT></a><br> </u>
		      <u> <a class="flr" href="./bulkMstr.do?methodName=downloadTemplate&mstrType=10" target="_new" ><FONT color="blue" size="2">Click here to download Workflow Auto Assignment Template</FONT></a><br> </u> 
		       <u><a class="flr" href="./bulkMstr.do?methodName=downloadTemplate&mstrType=11" target="_new" ><FONT color="blue" size="2">Click here to download Sales Channel Code Template</FONT></a><br></u>
				<u><a class="flr" href="./bulkMstr.do?methodName=downloadTemplate&mstrType=12" target="_new" ><FONT color="blue" size="2">Click here to download Escalation Matrix Upload Template</FONT></a><br></u>
		       </logic:equal>
		       
		    
		    </li>
			 
		<li class="clearfix">
				<span class="text2 fll width160"><strong>Select Master<FONT color="red">*</FONT></strong></span>
				<html:select property="mstrType" name="bulkMstrForm" styleId="selectedLobId" styleClass="select1">
						<option value="-2" >Select Master Type</option>
						<option value="1" >Zone</option>
						<option value="2" >City</option>
						<option value="3" >City Zone</option>
						<option value="4" >PinCode</option>
						<option value="5" >RSU</option>
						<option value="6" >Auto Assignment Matrix</option>
						<option value="7" >Channel Partner</option>
						<option value="8" >State</option>
						<option value="9">Agency Assignment Matrix</option>
						<option value="10" >WorkFlow Auto Assignment</option>
						<option value="11">Channel Id  Upload</option>
						<option value="12">Escalation Matrix Upload</option>
		
				</html:select>
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

