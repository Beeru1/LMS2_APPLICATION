
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/css.css" rel="stylesheet" type="text/css">
<LINK href=./jsp/theme/css.css rel="stylesheet" type="text/css">

<script language="javascript">
	var path = '<%=request.getContextPath()%>';
	var port = '<%= request.getServerPort()%>';
	var serverName = '<%=request.getServerName() %>';
</script>
<SCRIPT language="JavaScript">
var req=null; 
var ctr=1;

var levelCount=0;
var initialElementPath='<bean:write scope="request" name="allParentIdString"/>';
var elementPath=initialElementPath;
var initialParentId=<bean:write property="parentId" name="userReportForm"/>;
var parentId=initialParentId;
var initialLevel=<bean:write property="initialLevel" name="userReportForm"/>;
var parentLevel=initialLevel-1;
var elementLevelNames=new Array();
var childLevel;
var lk=0;
var emptyChildFlag=0;

<logic:iterate name="elementLevelNames" scope="request" id="levelName" >
	elementLevelNames[lk]='<bean:write name="levelName"/>';
	lk++;
</logic:iterate>
var level=elementLevelNames[initialLevel];

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

function loadDropdown(selectBox,e)
{
	e= e || event;
	var tempLevelCount=levelCount;
	
	if ((e.srcElement || e.target).id!="initialSelectBox") {
		var elementId=(e.srcElement || e.target).id;
		var currentElementLevel=elementId.substring(6);
		var table=$id("table_0");
		parentLevel=currentElementLevel;
		for (i=parseInt(currentElementLevel)+1;i<=parseInt(levelCount);i++) {
			table.tBodies[0].removeChild($id("tr_level_"+i));
			elementPath=elementPath.substring(0,elementPath.lastIndexOf("/"));
			tempLevelCount--;
		}
		if(emptyChildFlag==1){
			elementPath=elementPath.substring(0,elementPath.lastIndexOf("/"));
			emptyChildFlag=0;
		}
	}
	else if ((e.srcElement || e.target).id=="initialSelectBox"){
		var table=$id("table_0");
		elementPath=initialElementPath;
		parentLevel=initialLevel;
		for (i=initialLevel+1;i<=levelCount;i++) {
			table.tBodies[0].removeChild($id("tr_level_"+i));
			tempLevelCount--;
		}
	}
	levelCount=tempLevelCount;
	
	parentId=$id((e.srcElement || e.target).id).value;
	
	if (parentId!="") {
		//Obtain an XMLHttpRequest instance
	    req = newXMLHttpRequest();
	    //alert (req);
	    if (!req) {
	        alert("Your browser does not support AJAX! Add Files module is accessible only by browsers that support AJAX. " +
	              "Please check the KM requirements and contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson;
	    elementPath=elementPath+"/"+parentId;
	    //alert("Submitting with element Path: "+elementPath);
	    var url=path+"/userReport.do?methodName=loadElementDropDown&ParentId="+parentId+"&Dummy="+new Date().getTime();
	    //alert(url);
	    	if(levelCount>2)
		{ 
		return;
		}
	    req.open("GET", url, true);
	    req.send(null);
	}else if(parentId==""){
		
		parentLevel=parseInt(parentLevel)-1;
		if((e.srcElement || e.target).id=='initialSelectBox'){
			parentId=initialParentId;
		}else if(parentLevel==initialLevel){
			parentId=$id("initialSelectBox").value;
		}else{
			parentId=$id("level_"+parentLevel).value;
		}
	}
}

function returnJson() {
    if (req.readyState == 4) {
        if (req.status == 200) {
        	//alert(req.responseText);
            var json = eval('(' + req.responseText + ')');
            
			var elements = json.elements;
			level = json.level;
			childLevel=level;
			//alert("Done setting values");
			if (elements.length!=0) {
	            var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            }
	          
	            var form=document.forms[0];
	            
	            var id="level_"+level;
	            levelCount=level;
	            //alert(levelCount);
	            var name="element_"+(++ctr);
	            var selectDropDown = document.createElement("SELECT");
	            selectDropDown.setAttribute("id",id);
	            selectDropDown.setAttribute("name",name);
	             selectDropDown.setAttribute("class","select1");
				if (selectDropDown.addEventListener){
					selectDropDown.addEventListener('change', loadDropdown, false); 
				} else if (selectDropDown.attachEvent){
					selectDropDown.attachEvent('onchange', loadDropdown);
				}
				addOption("",'Select', selectDropDown);
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].elementId, elements[i].elementName, selectDropDown);
		        }
		        var table=$id("table_0");
		        var tr=document.createElement("tr");
		        var td1=document.createElement("td");
		        var td2=document.createElement("td");
		        var bold=document.createElement("b");
		        var trId="tr_"+id;
		        tr.setAttribute("id",trId);
		        td2.appendChild(selectDropDown);
		        var text="Select "+elementLevelNames[level];
		        var newNode = document.createTextNode(text);
		        td1.setAttribute("align","left");
		        td1.setAttribute("width","85px");
		        td1.setAttribute("class","clearfix");
		        bold.appendChild(newNode);
		        td1.appendChild(bold);
   		        table.tBodies[0].appendChild(tr);
		        tr.appendChild(td1);
		        tr.appendChild(td2);
		        emptyChildFlag=0;
		    }else{
				childLevel=parseInt(levelCount)+1;
				emptyChildFlag=1;
		    }
        }
    }
}
function $id(id) {
	return document.getElementById(id);
}

</script>
<SCRIPT>
function importExcel(){
 $id("parentId").value=parentId;
document.kmUserReportForm.methodName.value="loginExcelReport";
document.kmUserReportForm.submit();
}
function listUser()
{
	if(document.kmUserReportForm.initialSelectBox.value == "-1")
		{
			alert("Please select the Lob");
			return false;			
		}

        $id("parentId").value=parentId;
        $id("elementLevel").value=parentLevel;
    
		document.kmUserReportForm.methodName.value="userLoginReport";
	//document.kmUserReportForm.submit();
}
function validate()
	{
		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth()+1;//January is 0!
		var yyyy = today.getFullYear();
		if(dd<10){dd='0'+dd}
		if(mm<10){mm='0'+mm}
		var curr_dt=yyyy+'-'+mm+'-'+dd;
		if(document.kmUserReportForm.fromDate.value == "")
		{
			alert("Please enter the from date ");
			return false;			
		}
		else if(document.kmUserReportForm.toDate.value > curr_dt)
		{
			alert("Date cannot be a future date ");
			return false;			
		}else if(document.kmUserReportForm.toDate.value == "")
		{
			alert("Please enter the to date");
			return false;			
		}
		else if(document.kmUserReportForm.toDate.value < document.kmUserReportForm.fromDate.value)
		{
			alert("From Date should not be greater than To Date ");
			return false;			
		}
		else 
		{
		return true;
		}
			
	}
</SCRIPT>
<script language="JavaScript" src="jScripts/KmValidations.js"
	type="text/javascript"> </script>
<script language="JavaScript" type="text/javascript"> </script>
<html:form action="/userReport">
	<html:hidden property="methodName" /> 
	<html:hidden property="parentId" styleId="parentId"/>
	<html:hidden property="levelCount" styleId="levelCount"/>
	<html:hidden property="elementLevel" styleId="elementLevel"/>
	<html:hidden property="initStatus" />
	<html:hidden property="elementId"/>
	<div class="box2">
        <div class="content-upload">
<h1><bean:message key="userLoginStatus.title" /></h1>
	  
	   <table width="100%" align="center" >		
			<tr>
				<td class="pTop4 pLeft10">
						<table width="100%" border="0" cellpadding="5" cellspacing="0" class="text">
							<tr align="left">
								<td class=""><font color="#0B8E7C"><strong>
								<html:messages id="msg" message="true">
             								<bean:write name="msg"/>  
           							</html:messages></strong></font></td>
							</tr>
						</table>
				</td>
			</tr>
			<tr>
				<td class="pTop4 pLeft10">
						<table width="100%" border="0" cellpadding="5" cellspacing="0" class="text">
							<tr align="left">
								<td class=""><font color="#0B8E7C"><strong>
								<html:errors/></strong></font>
							</tr>
						</table>
				</td>
			
			</tr>
		
            <tr >
			  <td >
			<table id="table_0" width="100%" cellspacing="15px" class="form1"	style="margin-top: -5px;" >
				<tbody>
					<tr>
						<td class="width85 text2" valign="middle"><b>
						Select <script type="text/javascript">
										document.write(elementLevelNames[initialLevel]);</script></b></td>
						<td style="width: 238px" align="left" class="pBot2"><html:select
							property="initialSelectBox" styleId="initialSelectBox"  styleClass="select1" 
							onchange="javascript:loadDropdown(this);">
							<html:option value="-1">Select</html:option>
							<logic:present name="firstList" scope="request">
								<html:options collection="firstList" property="elementId"
									labelProperty="elementName" />
							</logic:present>
						</html:select></td>
					</tr>
				</tbody>
			</table>
		</td>
		</tr>
	</table> 
			   
			   <!--
			<ul class="list2 form1">
			<li class="clearfix alt">
			<span class="text2 fll width160"><strong><bean:message  key="userLoginStatus.fromDate" />&nbsp;</strong></span>
				<input type="text" class="tcal calender2 fll" id="date_selector" styleId="date_field" readonly="readonly"   name="fromDate" value="<bean:write property='fromDate' name='kmUserReportForm'/>"/>
            		
                	 
            
			</li>
			<li class="clearfix">
			<span class="text2 fll width160"><strong><bean:message
					key="userLoginStatus.toDate" />&nbsp;</strong></span>
			<input type="text" class="tcal calender2 fll" id="date_selector1" styleId="date_field1" readonly="readonly" name="toDate" value="<bean:write property='toDate' name='kmUserReportForm'/>"/>
            		
                	 
            
			</li>
		
			
			</ul>
				-->	
 
       
       <div class="fll" style="margin-left: 220px;">	                    
                      <input class="submit-btn1 red-btn" type="submit" value="" onclick="return listUser();" />
                 </div>
                 
<table width="100%" class="mTop30" align="center" cellpadding="0" cellspacing="0" >

	<logic:present name="LOGGED_IN_USER_LIST">	
		<tr align="right" >
							<td width="147" class="pLeft10 pTop2"></td>
							<td width="636" class="pTop2" colspan="5"><img  src="images/excel.gif" width="59" height="35" border="0" onclick="importExcel();" /></td>
        </tr>	
	
	<tr class="lightBg">
		<td  colspan="9" height="30px" align="left" width="90%"><FONT size="2"><B>Search Location :</B></FONT>&nbsp;&nbsp;<bean:write name="kmUserReportForm" property="elementPath" />
		&nbsp;&nbsp;&nbsp;</td>
	</tr>
				<tr class="text white">
					<td width="5%" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span class="mTop5">&nbsp;S.No</span></td>
					<td width="17%" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span class="mTop5">User LoginId</span></td>
					<td width="17%" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span class="mTop5">First Name</span></td>										
					<td width="16%" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span class=" mTop5">Last Name</span></td>
					<td width="10%" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span class=" mTop5">Total Login Time</span></td>
					<td width="9%" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span class=" mTop5">Partner Name</span></td>
					<td width="8%" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span class=" mTop5">Circle</span></td>
					<td width="8%" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span class=" mTop5">LOB</span></td>
					<td width="10%" style="background-image:url(./images/left-nav-heading-bg_grey.jpg); background-repeat:repeat-x;" height="35"><span class=" mTop5">Role</span></td>
					<!--<td class="darkRedBg height19"><span class="mLeft5 mTop5">Last LogIn Time</span></td>-->
				</tr>
              	 <logic:notEmpty name="LOGGED_IN_USER_LIST">
              	 <bean:define id="userList" name="LOGGED_IN_USER_LIST" type="java.util.ArrayList" scope="request" />
				<logic:iterate name="userList" id="file" indexId="i" type="com.ibm.lms.dto.UserMstr">
					<%
						String cssName = "";				
						if( i%2==1)
						{			
						cssName = "alt";
						}	
						%>
				    <tr class="<%=cssName%>">	
						<TD height="20px" class="text" >&nbsp;<%=(i.intValue() + 1)%>.&nbsp;</TD>
						<TD class="text" ><bean:write name="file"	property="userId" /></TD>
						<TD class="text" ><bean:write name="file"	property="userFname" /></TD>
						<TD class="text" ><bean:write name="file"	property="userLname" /></TD>
						<TD class="text" ><bean:write name="file"	property="totalLoginTime" /></TD>
						<TD class="text" ><bean:write name="file"	property="partnerName" /></TD>
						<TD class="text" ><bean:write name="file"	property="circle" /></TD>
						<TD class="text" ><bean:write name="file"	property="lob" /></TD>
						<TD class="text" ><bean:write name="file"	property="role" /></TD>					
					</TR>
				</logic:iterate>
						
		</logic:notEmpty>
			<logic:empty name="LOGGED_IN_USER_LIST"  >
				<TR class="lightBg">
				<TD colspan="2" class="error" align="left"><FONT color="red"><bean:message
					key="viewAllFiles.NotFound" /></FONT></TD>
			</TR>
			</logic:empty>
		</logic:present>	
	
		</table>
		<br>
       </div>
       </div>
	
</html:form>
<logic:equal name="kmUserReportForm" property="showFile" value="false" >

</logic:equal>