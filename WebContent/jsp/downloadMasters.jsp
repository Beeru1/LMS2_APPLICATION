<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<script language="JavaScript" type="text/javascript">
function validate()
{
	if( document.masterDataFormBean.selectedMasterId.value == "-1")
	  {
	   alert("Please select Master Type");
	   return false;
	  }
		document.masterDataFormBean.methodName.value = "downloadData";
		document.forms[0].submit();
	 return true;
}
</script>

<html:form action="/downloadMasterData" >
<html:hidden property="methodName"/>
	
	<div class="box2">
      <div class="content-upload" style="height:200px ">
        <h1><bean:message key="downloadData.heading" /></h1>
        
        	<center><font color="#FF0000"><strong>
			<html:messages id="msg" message="true">
					<bean:write name="msg"/>  
			</html:messages></strong></font></center>

		<ul class="list2 form1 ">
		  <li class="clearfix ">
		 	<strong><font color="red"><bean:write name="masterDataFormBean" property="message" /></font></strong>
		  </li>
          <li class="clearfix "  style="height: 40px;">
			<span class="text2 fll width160"><strong><bean:message key="downloadData.datatype" /><FONT color="red" size="1">*</FONT></strong></span>
				<html:select property="selectedMasterId" name="masterDataFormBean" styleId="selectedMasterId" styleClass="select1">
						<option value="-1" >Select Master Type</option>
						<logic:notEmpty name="masterDataFormBean" property="masterTableList" >
									<bean:define id="elements" name="masterDataFormBean" property="masterTableList" /> 
										<html:options labelProperty="masterTableName" property="masterTableId"  collection="elements" />
						</logic:notEmpty>
				</html:select>
			
		  </li> 
		  
		  	<li class="clearfix">
				<span class="text2 fll width160"><strong><bean:message key="downloadData.lobtype" /></strong></span>
				<html:select property="selectedLobId" name="masterDataFormBean" styleId="selectedLobId" styleClass="select1">
						<option value="-2" >Select LOB Type</option>
						<logic:notEmpty name="masterDataFormBean" property="lobList" >
									<bean:define id="elements" name="masterDataFormBean" property="lobList" /> 
										<html:options labelProperty="lobName" property="lobId"  collection="elements" />
						</logic:notEmpty>
						
				</html:select>
		</li>
		  
		   <li class="clearfix" style="margin-top: 18px;">	
					<center><a class="red-btn" tabindex="3" onclick="return validate();"><b>Download</b></a></center>
		     </li>  
		     
		     
		     
		        <li class="clearfix">
				<span class="text2 fll"><strong><FONT color="red"><bean:message key="downloadData.selectlobtype" /></FONT></strong></span>
		     </li> 
		     
        </ul>
    </div>
  </div>
   
</html:form>

 