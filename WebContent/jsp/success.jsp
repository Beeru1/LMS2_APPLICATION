<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<FONT color="red"><html:errors/></FONT>
    <TABLE align="center" border="0"  cellpadding="0" cellspacing="0"> 
		<TBODY>
		 <tr>
			<td  align="center" class="error">
				<strong> 
		          	 <html:messages id="msg" message="true">
		                 <bean:write name="msg"/> 
		             </html:messages>
	            </strong>
            </td>
		   </tr>
		</TBODY>
	</TABLE>	