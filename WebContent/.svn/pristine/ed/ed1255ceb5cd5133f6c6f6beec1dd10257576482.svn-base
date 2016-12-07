
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
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
<bean:define id="kmUserBean" name="USER_INFO"  type="com.ibm.lms.dto.UserMstr" scope="session" />

<LINK href="./jsp/theme/css.css" rel="stylesheet" type="text/css">
<TITLE></TITLE>
<script language="javascript">
var path = '<%=request.getContextPath()%>';
</script>
<SCRIPT language="JavaScript">
// dispDoc.js

var req=null; 

function newXMLHttpRequest() {

    var xmlreq = false;

    if (window.XMLHttpRequest) {
        // Create XMLHttpRequest object in non-Microsoft browsers
        xmlreq = new XMLHttpRequest();

    } else if (window.ActiveXObject) {

        // Create XMLHttpRequest via MS ActiveX
        try {
            // Try to create XMLHttpRequest in later versions
            // of Internet Explorer

            xmlreq = new ActiveXObject("Msxml2.XMLHTTP");

        } catch (e1) {

            // Failed to create required ActiveXObject

            try {
                // Try version supported by older versions
                // of Internet Explorer

                xmlreq = new ActiveXObject("Microsoft.XMLHTTP");

            } catch (e2) {

                // Unable to create an XMLHttpRequest with ActiveX
            }
        }
    }
    return xmlreq;
}

var errors=false;



function $id(id) {
	
	return document.getElementById(id);
}

function disableDropDowns(){
var restricted='<%= kmUserBean.isRestricted()  %>'
var actorId='<%= kmUserBean.getKmActorId()  %>' 
		if(restricted=='true'&& (actorId == '4' || actorId== '6')){
			
			document.getElementById("currentCircleId").disabled='true';	
			document.getElementById("currentLobId").disabled='true';	
		
		}

}



</SCRIPT>
</HEAD>

<BODY class="mLeft10 mTop2 mRight10"  >
abcd
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
		    	<tr>
		    		<td >
					</td>
			    </tr>
			  </table>
  <table width="100%" class="mLeft5" align="left" cellspacing="0" cellpadding="0">
    <tr >
      <td width="100%" height="378" align="left" valign="top" class="pRight10 pTop5">
<table width="98%" align="center" border="0" cellspacing="0" cellpadding="0" class="mTop5">
					
					<tr>
						 <TD colspan="4" align="left" height="20" class="border">
							 <logic:present name="kmDocumentMstrForm">
								 <logic:notEmpty name="CURRENT_PAGE">
								<logic:notEqual name="CURRENT_PAGE" value="DOCUMENT_ERROR"> 
    							
					    							    		
								 <iframe src='.<bean:write name="kmDocumentMstrForm" property="filePath"/>' width="100%" height="640" align="right"></iframe> 
							 	<!--<IFRAME src="docHome.jsp" width="100%" height="500" align="right" ></IFRAME>-->
							  </logic:notEqual>
							  </logic:notEmpty>
							  <logic:notEmpty name="CURRENT_PAGE">
							  <logic:equal name="CURRENT_PAGE" value="DOCUMENT_ERROR">
								<FONT color="red">&nbsp;FILE NOT FOUND.</FONT>
							  </logic:equal>
							  </logic:notEmpty>
							
							  
							  
							  </logic:present>
						 </TD>
                    </tr>
				  

		    </table>
		    
			</td>
    </tr>
  </table>
</BODY>
</html:html>
