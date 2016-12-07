<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<LINK href="./theme/css.css" rel="stylesheet" type="text/css">
<LINK href="./theme/text.css" rel="stylesheet" type="text/css">
<LINK href="./jsp/theme/css.css" rel="stylesheet" type="text/css">

<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/javascript">
function lookUpDo()
{
	document.LeadForm.submit();
}
</script>
<html:form action="assignedLeads.do">
	<html:hidden name="LeadForm" property="methodName" value="searchLead"/>
	<html:hidden name="LeadForm" property="leadID" />
	<a href="javascript:lookUpDo()">Start Assigning</a>
	</html:form>
	