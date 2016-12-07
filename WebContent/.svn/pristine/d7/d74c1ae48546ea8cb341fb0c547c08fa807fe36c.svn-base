<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page 
language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
import="com.ibm.lms.dto.UserMstr,java.util.ArrayList,java.util.Iterator,java.util.HashMap"
%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<bean:define id="kmUserBean" name="USER_INFO"  type="UserMstr" scope="session" />

<script type="text/javascript">
var flag= '<%=session.getAttribute("appL1")%>';
var flag2='<%=session.getAttribute("appL2")%>';

if(flag !='0' && flag !=null && flag !='null')
{
   window.onload = function(){
       var text = document.createTextNode(flag);
     document.getElementById('circle').appendChild(text);
   }
}
if(flag2 !='0' && flag2 !=null && flag2 !='null')
{
   window.onload = function(){
   
    var text1 = document.createTextNode(flag2);
     document.getElementById('circle1').appendChild(text1);
   }
   if(flag2 !='0' && flag2 !=null && flag2 !='null' && flag !='0' && flag !=null && flag !='null')
   {
   window.onload = function(){
  
     var text = document.createTextNode(flag);
     document.getElementById('circle').appendChild(text);
     var text1 = document.createTextNode(flag2);
     document.getElementById('circle1').appendChild(text1);
   }
   }
}
</script>

<script language="javascript">
	var path = '<%=request.getContextPath()%>';
	var port = '<%= request.getServerPort()%>';
	var serverName = '<%=request.getServerName() %>';

function newXMLHttpRequests() {

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

// function to handle alphabet key events to get suggestions
function getSuggestionsMenu()
{
	var keyword = trims(document.getElementById("keywordMenu").value);
		
	 if (keyword != "") {
		req = newXMLHttpRequests();
	    
	    if (!req) {
	        alert("Your browser does not support AJAX! " +
	              "Please check the KM requirements and contact your System Administrator");
	        return;
	    } 
	    req.onreadystatechange = returnJsons;	    
	    var url=path+"/documentAction.do?methodName=getSuggestions&keyword="+keyword;
	    req.open("GET", url, true);
	    req.send(null);
	  }
	  else
	  {
	  	hideSuggestionBoxs();
	  }
}

function returnJsons() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            var json = eval('(' + req.responseText + ')');
			var keywordList = json.keywordList;
			cleanSelectBoxs();
			hideSuggestionBoxs();	
		if (keywordList.length > 0) {
			var s= document.getElementById('suggestionBoxMenu');
            for (var i = 0; i < keywordList.length; i++) {
            		s.options[s.options.length]= new Option(keywordList[i].keyword,keywordList[i].keyword);   
		        }
            document.getElementById("suggestionBoxMenu").style.display = 'inline';
			}
        }
    }
   
}
  
function selectSuggestionMenu(selectedItem,event)
{

   if (event.keyCode == 13)
  {
    document.getElementById("keywordMenu").value=selectedItem;
    submitSearchMenu();
  }	
} 
  //Function to populate searct text box with selected suggestion on mouse left click
function selectSuggestionMenu(selectedItem)
{
    document.getElementById("keywordMenu").value=selectedItem;
    hideSuggestionBoxs();
}

function returnToTextBoxMenu(event)
{
  if (event.keyCode == 38)
    {
      var obj = document.getElementById('suggestionBoxMenu');
	  if(obj.options[0].selected == true)
	  {
     	document.getElementById("keywordMenu").focus();
     	hideSuggestionBoxs();
      }
    }	 
}
  
  // function to handle Up and Down key event
  function selectSearchTextMenu(event) 
{
    if (event.keyCode == 40)
    {
     document.getElementById("suggestionBoxMenu").focus();
    }
     return true;
}

//function to handle Enter key event
function submitSearchMenus(event) {
    if (event.keyCode == 13)
    {
        submitSearchMenu();        
    }
     return true;
}

 function cleanSelectBoxs()
  {
  	var obj = document.getElementById('suggestionBoxMenu');
    if (obj == null) return;
	if (obj.options == null) return;
	while (obj.options.length > 0) {
		obj.remove(0);
	}
  }
 function hideSuggestionBoxs()
 {
    document.getElementById("suggestionBoxMenu").style.display = 'none';
 } 
function dotandspaces(txtboxvalue)
{
 var flag=0;
 var strText = txtboxvalue;
 if (strText!="")
 {
 var strArr = new Array();
 strArr = strText.split(" ");
 for(var i = 0; i < strArr.length ; i++)
 {
 if(strArr[i] == "")
 {
  flag=1;              
  break;
 }}}
 
 if (flag==1)
 {
 alert("Please enter any keyword for searching..");
 return false;
  }
  else
  return true;          
}	  
</script>

<SCRIPT>

function setFocuss()
{
	document.kmDocumentMstrForm.keyword.focus();
}

function trims(string) 
{

 while (string.substring(0,1) == ' ') {
    string = string.substring(1,string.length);
  }
  while (string.substring(string.length-1,string.length) == ' ') {
    string = string.substring(0,string.length-1);
  }
return string;
}

function submitSearchMenu(){
  var keywordString = document.getElementById("keywordMenu").value;
  
  keywordString = trims(keywordString);

 if("" == keywordString)
 {
   return false;
 }
 // iPortal_Enhancements changes for complete string search; CSR20111025-00-06938:BFR2
	if(keywordString.charAt(0) == "\"" || keywordString.charAt(keywordString.length-1) == "\"")
	{
	//To be Sent as it is
	}
	else
	{	
	keywordString = "\""+ keywordString +"\"";
	}
	// End of Change
 window.location.href="documentAction.do?methodName=contentSearch&keywordMenu="+keywordString;
 return true;
}
</SCRIPT> 
<style>
#circle {
	width: 100px;
	height: 100px;
	background: red;
	-moz-border-radius: 50px;
	-webkit-border-radius: 50px;
	border-radius: 50px;
	color: white;
}
#circle1 {
	width: 100px;
	height: 100px;
	background: red;
	-moz-border-radius: 50px;
	-webkit-border-radius: 50px;
	border-radius: 50px;
	color: white;
}
</style>
<h1>&nbsp;</h1>


<% String flag= (String)request.getAttribute("FirstLogin") ; %>
      <% if(flag==null) { flag="false" ; } 
       if(!flag.equals("true")) { %>                 
		   <ul class="lleft-side">       
		   <logic:iterate id="moduleMap" name="kmUserBean" property="moduleList">
		   	<% java.util.HashMap mMap=(java.util.HashMap)moduleMap;%>
			  <% if(kmUserBean.isRestricted()==true){ %>
		  	     <li><a href='<bean:write name="moduleMap" property="MODULE_URL"/>'><bean:write name="moduleMap" property="MODULE_NAME"/></a></li>
         	  <%} else{ if(mMap.get("STATUS").equals("A") || mMap.get("STATUS").equals("B")){ %>
            	 <li><a href='<bean:write name="moduleMap" property="MODULE_URL"/>'><bean:write name="moduleMap" property="MODULE_NAME" filter="false"/></a></li>
          	  <%} } %>
		  </logic:iterate>
		  </ul>		 
    <% } %>