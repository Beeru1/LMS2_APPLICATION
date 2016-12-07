<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>
<HEAD>
<LINK href="theme/text.css" rel="stylesheet" type="text/css">
<TITLE>Csv Bulk Upload</TITLE>

<script language="javascript" src="jScripts/KmValidations.js">
</script>

<script language="JavaScript" type="text/JavaScript">
function limitText(textArea, length) {
if (textArea.value.length > length) {
alert ("Please limit comments length to "+length+" characters.");
textArea.value = textArea.value.substr(0,length);
}
}

var req=null; 
var ctr=1;

var levelCount=0;
var initialElementPath=<%= request.getAttribute("allParentIdString") %>
var elementPath = initialElementPath;
var initialParentId=<bean:write property="parentId" name="bulkUserUploadFormBean"/>;
var parentId=initialParentId;
var initialLevel=<bean:write property="initialLevel" name="bulkUserUploadFormBean"/>;
var parentLevel=initialLevel-1;
var elementLevelNames=new Array();
var childLevel;
var lk=0;
var emptyChildFlag=0;
var flag=0;

elementLevelNames[0] = 'Document';
elementLevelNames[1] = 'Country';
elementLevelNames[2] = 'LOB';
elementLevelNames[3] = 'Circle';
elementLevelNames[4] = 'Category';


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
	flag=0;
	var countLevel=levelCount;

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
		
	    var url=path+"/kmScrollerMstr.do?methodName=loadElementDropDown&ParentId="+parentId+"&Dummy="+new Date().getTime();
	    //alert(url);
	    req.open("GET", url, true);
	    req.send(null);
	}else if(parentId==""){
		//showTextBoxes();
		//changeTrLabel(parentLevel);
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
			
			
			//Bug resolved 
			if(window.element){
			}
			else{
				level1=parentLevel;
			}
			childLevel=level;
			var maxlevel=elementLevelNames.length-1;
			var currentLevel=parseInt(parentLevel)+1;
			
		
		if(currentLevel>3)
			{
			 	return;
			}
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
	            var name="element_"+(++ctr);
	            var selectDropDown = document.createElement("SELECT");
	            selectDropDown.setAttribute("id",id);
	            selectDropDown.setAttribute("name",name);
	            selectDropDown.setAttribute("class","select1 fll width180");
				if (selectDropDown.addEventListener){
					selectDropDown.addEventListener('change', loadDropdown, false); 
				} else if (selectDropDown.attachEvent){
					selectDropDown.attachEvent('onchange', loadDropdown);
				}
				addOption("-1",' -- Select Circle -- ', selectDropDown);
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].elementId, elements[i].elementName, selectDropDown);
		        }
		        var table=$id("table_0");
		        var tr=document.createElement("tr");
		           
		        var sp=document.createElement("span");  
		        sp.setAttribute("class","text2 fll");
		        var st=document.createElement("strong"); 
		        var td1=document.createElement("td");
		        var td2=document.createElement("td");
		        var trId="tr_"+id;
		        tr.setAttribute("id",trId);
		        td2.appendChild(selectDropDown);
		        var text="Select "+elementLevelNames[level]+":";
		        var newNode = document.createTextNode(text);
		        td1.setAttribute("align","left");
		        td1.appendChild(newNode);
		        st.appendChild(td1);
		        sp.appendChild(st);
   		        table.tBodies[0].appendChild(tr);
		        tr.appendChild(sp);
		        tr.appendChild(td2);
		        emptyChildFlag=0;
		    }else{
				levelCount=level1;
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




<script>

function openFile(fileName){
window.location.href="userBulkUpload.do?methodName=openFile&fileName="+fileName;
}
function formsubmit(){
  
  var elemType = elementLevelNames[initialLevel] ;
	
	if(document.bulkUserUploadFormBean.loginActorId.value=="1" || document.bulkUserUploadFormBean.loginActorId.value=="5"){
		if(document.bulkUserUploadFormBean.initialSelectBox.value=="-1"){
			alert("Please Select "+elemType); 
			return false;
		
		}
	} 
	
	if(document.bulkUserUploadFormBean.loginActorId.value=="1"){
		document.bulkUserUploadFormBean.circleElementId.value = document.getElementById("level_3").value;
		if(document.getElementById("level_3").value=="-1"){
			alert("Please Select Circle"); 
				
			return false;
		
		}
	} 
	
	
	 if(document.bulkUserUploadFormBean.uploadType.value=="0"){
		alert("Please Select the purpose of uploading");
		return false;
	}
	else if(document.bulkUserUploadFormBean.newFile.value==""){
		alert("Please Select the file");
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
	}else{  
		document.forms[0].methodName.value='uploadFile';
		return true;
	}
	
}

</script>
</HEAD>
  					


<BODY>

<html:form action="/userBulkUpload" enctype="multipart/form-data" onsubmit="return formsubmit();" >
	
	<html:hidden name="bulkUserUploadFormBean" property="methodName" value="uploadFile"/>
	<html:hidden name="bulkUserUploadFormBean" property="loginActorId" />
	<html:hidden name="bulkUserUploadFormBean" property="circleElementId"/>
			
		<div class="box2">
        <div class="content-upload">
        
        <H1>Bulk User Uploading</H1>
        
			
			<TABLE border="0" align="center" width="100%">
			<tr><td width="258"><br><br></td></tr>
			<tr align="center">
			
			<td colspan="2"  class="error">
			<strong> 
          	<html:messages id="msg" message="true">
                 <bean:write name="msg"/>  
                          
             </html:messages>
            </strong>
            </td>
		   </tr>
			<TR align="center"><TD  colspan="2" >
			
			<font color="RED" ><strong>
						
						 
					<html:errors/>
	 
				</strong></font>
			<TD ></TR>
			
		</TABLE>	
			
			 <ul class="list2 form1 ">
			 
			 <li class="clearfix alt" >
			
			<logic:notEqual name="bulkUserUploadFormBean" property="loginActorId" value="2">
	                    <table id="table_0" align="left">
							<tr>
							  <td style="width:153px" align="left" width="113">
							  <span class="text2 fll "><strong>Select <script type="text/javascript">document.write(elementLevelNames[initialLevel]);</script>:</strong></span>
								</td>
							  <td style="width:238px" align="left" class="pBot2" width="306"><html:select styleClass="select1 fll width180" property="initialSelectBox" styleId="initialSelectBox" onchange="javascript:loadDropdown(this);" >
		                        	<html:option value="-1"> -- Select LOB -- </html:option>
		                        	<logic:present name="firstList" scope="request">
		                        	<html:options collection="firstList" property="elementId" labelProperty="elementName" />
		                        	</logic:present>
		                        </html:select>
		                      </td>
		                    </tr>
		                    <tr>
		                    <td height="5px"> </td>
		                    </tr>
	                    </table>	
			</logic:notEqual>	
			
			</li>	
			
			<li class="clearfix">
			
			<span class="text2 fll width160"><strong>Select Purpose:</strong> </span>
			
			<p class="clearfix fll margin-r20"> 
				
				<html:select  property="uploadType" styleClass="select1 fll width180">
					<option selected="selected" value="0">-- Select Purpose --</option>
					<option value="1">Creation </option>
					<option value="2">Updation </option>
					<option value="3">Deletion</option>	
				</html:select>
				
			 </p>
			
			</li>
			
			 <li class="clearfix alt" >
				<span class="text2 fll width160"><strong>Select File: </strong> </span>	

				<p class="clearfix fll margin-r20"> 
				   <html:file property="newFile"/> 
				</p>		
			  </li>
			   
	   <li class="clearfix" style="padding-left:170px;">
			<span class="text2 fll">&nbsp;</span>
			<input type="image" src="images/submit.jpg" style="margin-right:10px;" onclick="return formsubmit();" styleClass="red-btn fll"/>&nbsp;&nbsp;&nbsp;&nbsp;	
		</li>
					
	
			</ul>
			
			
	</div>
	</div>		

			
</html:form>
</BODY>
</html:html>

