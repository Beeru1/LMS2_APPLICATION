<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="icon" href="images/logo-airtel.ico" type="image/x-icon"/>
<style>
tr:nth-child(even) {background: #FFF}
tr:nth-child(odd) {background: #CCC}
</style>
 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<body>

<logic:present name="LeadDetailsPopUpDetails">
<table class="table10" align="left">
	 <tr>
	  <th>Customer Name</th>
	  <th>Address 1</th>
	  <th>Address 2</th>
	  <th>Product Name</th>
	  <th>Circle</th>
	  <th>Status</th>
	 </tr>
	 <logic:iterate id="netLead" name="LeadDetailsPopUpDetails">
	 	<tr>
	 	   <td class="text1 width260"><bean:write name="netLead" property="customerName"/></td>
	 	   <td class="text1 width260"><html:textarea name="netLead" property="address1" onclick="this.style.height='200px';" onblur="this.style.height='';" readonly="true"></html:textarea>
	 	   <td class="text1 width260"><html:textarea name="netLead" property="address2" onclick="this.style.height='200px';" onblur="this.style.height='';" readonly="true"></html:textarea>
	 	   <td class="text1 width260"><bean:write name="netLead" property="productName"/></td>
	 	   <td class="text1 width260"><bean:write name="netLead" property="circleName"/></td>
	 	   <td class="text1 width260"><bean:write name="netLead" property="leadStatusName"/></td>
	 	</tr>
	 
	 </logic:iterate>
</table>
</logic:present>
	   
</body>
</html>