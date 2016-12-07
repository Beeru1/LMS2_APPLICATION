<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>
<HEAD>
<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<TITLE>Csv Bulk Delete</TITLE>
<logic:notEmpty name="BULK_USER">
<bean:define id="bulkUserFile" name="BULK_USER"   type="java.util.ArrayList" scope="request" />
</logic:notEmpty>
<script language="javascript" src="jScripts/KmValidations.js">
</script>
<script>
function frmsubmit(){

	
		
	
	
	 if(document.bulkUserUploadFormBean.newFile.value==""){
		alert("Please Enter The File Location");
		document.fileUpload.thefile.focus();
		return false;
	}else if(!(/^.*\.csv$/.test(document.bulkUserUploadFormBean.newFile.value))) {
			//Extension not proper.
			alert("Please select a .csv file only.");
			return false;
				
	}else if(navigator.userAgent.indexOf("MSIE") != -1) {
		if(document.bulkUserUploadFormBean.newFile.value.indexOf(":\\") == -1 ) {
			alert("Please Select a proper file.")
			return false;
		}
	}  
	
		document.forms[0].methodName.value='bulkUserDelete';
		document.forms[0].submit();
}

</script>
</HEAD>
  					
<center><font color="RED" size="2"><strong> 
<html:errors property="BUS005" />
<html:errors property="SYS001" />
<html:errors property="SYS002" />
<html:errors property="BUS004" />

 </strong></font></center>

<BODY>

<html:form action="/userBulkUpload" enctype="multipart/form-data" method="post">
	
	<html:hidden name="bulkUserUploadFormBean" property="methodName" />
	<html:hidden name="bulkUserUploadFormBean" property="loginActorId" />
			
			<TABLE border="0" align="center" width="100%">
			<TR><TD class="norm" width="735" align="center">
			<font color="RED" ><strong>
						
						 <html:messages id="msg" message="true">
	  						 <bean:write name="msg"/>  
						  </html:messages>
	 
						</strong></font>
			<TD></TR>
			<TR><TD class="norm" width="735" align="center">
			<font color="RED" ><strong>
						
						 
					<html:errors/>
	 
				</strong></font>
			<TD></TR>
			<tr align="center">
			<td colspan="5"><span class="heading">Bulk User Deletion</span></td>
			</tr>
			<br/>
			<br/>
				
		
			
			
				<TR>
					<TD  align="right">Select File : </TD>
					<TD width="568">

						
						<html:file property="newFile"/>
					  </TD>
				</TR>
				
				<TR>
					<TD class="txt1" colspan="2">&nbsp;&nbsp;</TD>
				</TR>

				<TR>
					<TD class="txt" colspan="2">&nbsp;&nbsp;</TD>
				</TR>
					<tr>
						<td colspan="2" align="center">
							<img src="images/submit_button.jpg"  onclick=" return frmsubmit();"/>
				
						</td>
					</tr>
					
					
			</TABLE>
	<logic:notEmpty name="BULK_USER">
		<table width="100%" cellpadding="3" border="0">
		<tr>
			<td class="darkRedBg height19" width="4%"><span class="mLeft5 mTop5"><bean:message key="viewAllFiles.SNO" /></span></td>
			
			<td class="darkRedBg height19" width="23%"><span class="mLeft5 mTop5">File Name</span></td>
			
			<td  class="darkRedBg height19" width="23%"><span class="mLeft5 mTop5">Total Users</span></td>
			<td class="darkRedBg height19" width="14%"><span class="mLeft5 mTop5">Users Deleted</span></td>	
			<td class="darkRedBg height19" width="17%"><span class="mLeft5 mTop5">Status</span></td>
			<td class="darkRedBg height19" width="17%"><span class="mLeft5 mTop5">Error Message</span></td>
		
		</tr>
		
			
		<logic:notEmpty name="bulkUserFile" >
			<logic:iterate name="bulkUserFile" type="com.ibm.lms.dto.FileDto" id="report" indexId="i">
				<tr  align="left"  >
					
					<TD   align="center" width="4%"><span class="mLeft5 mTop5 mBot5"><%=(i.intValue() + 1)%>.</span></TD>
					<TD   align="left" width="23%"><span class="mLeft5 mTop5 mBot5"><TEXTAREA readonly="readonly" rows="2" cols="45"><bean:write name="report"
						property="fileName" /></TEXTAREA></span></TD>
					<TD class="text" align="center"  width="25%" ><bean:write name="report"
						property="totalUsers" /></TD>
					
					<TD   align="center" width="14%"><span class="mLeft5 mTop5 mBot5"><bean:write name="report"
						property="usersDeleted" /></span></TD>	
					<TD   align="center" width="17%"><span class="mLeft5 mTop5 mBot5"><bean:write name="report"
						property="status" /></span></TD>
					<TD   align="center" width="17%"><span class="mLeft5 mTop5 mBot5"><bean:write name="report"
						property="errorMessage" /></span></TD>
					
				</TR>
			</logic:iterate>  
		</logic:notEmpty>
		</table>
			</logic:notEmpty>
			
</html:form>
</BODY>
</html:html>

