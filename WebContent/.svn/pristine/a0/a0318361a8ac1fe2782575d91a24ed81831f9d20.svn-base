
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>

<HEAD>
<%@ page 
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<TITLE></TITLE>
<SCRIPT>
function openDocument()
{
alert(document.bulkUserUploadFormBean.filePath.value);
window.open(document.bulkUserUploadFormBean.filePath.value);

}
</SCRIPT>

</HEAD>

<body onload="openDocument();">
<html:form action="/userBulkUpload">
<html:hidden property="filePath" name="bulkUserUploadFormBean"/>
<html:hidden property="methodName" name="bulkUserUploadFormBean"/>
	<table align="center">
		<tr>
			<td align="left"><br>
			<strong> <html:errors /></strong></td>
		</tr>
	</table>
	
</html:form>
</body>

</html:html>
