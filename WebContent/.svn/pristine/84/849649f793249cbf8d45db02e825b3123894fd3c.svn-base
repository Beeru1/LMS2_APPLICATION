<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<LINK href="theme/css.css" rel="stylesheet" type="text/css">
<LINK href=./jsp/theme/css.css rel="stylesheet" type="text/css">

<script language="javascript">
	var path = '<%=request.getContextPath()%>';
	var port = '<%= request.getServerPort()%>';
	var serverName = '<%=request.getServerName() %>';
</script>

<script language="javascript">


function newXMLHttpRequest() {

    var xmlreq = false;

    if (window.XMLHttpRequest) {
        xmlreq = new XMLHttpRequest();

    } else if (window.ActiveXObject) {

        try {
            xmlreq = new ActiveXObject("Msxml2.XMLHTTP");

        } catch (e1) {
            try {
                xmlreq = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e2) {
            }
        }
    }
    return xmlreq;
}

function loadDropdown()
{

		if(document.productMappingForm.selectedProductLobId.value == "-1"){
		document.getElementById("productLobDiv").style.visibility="visible";
		}
		else{
		document.getElementById("productLobDiv").style.visibility="hidden";
		}		
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Add Files module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson;
	    var selectedProductLobId = document.productMappingForm.selectedProductLobId.value;
		//alert("Product lob id :::::"+selectedProductLobId);
	    var url= 'ajaxSupport.do?mt=getProductBasedOnLob&selectedProductLobId='+selectedProductLobId;
	    
	    req.open("GET", url, true);
	    req.send(null);
		

}

// called on create product & populate product 
function returnJson() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            var json = eval('(' + req.responseText + ')');
            
			var elements = json.elements;
			//cleanSelectBox("selectedProductId");
		if (elements.length!=0)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}
	                
	            var selectDropDown = document.getElementById("selectedProductId");	       
	            
	            cleanSelectBox("selectedProductId");
	            var opt1 = "Select Product Name";
	            var opt2 = "Create New";
				addOption("-2",opt1, selectDropDown);
				addOption("-1",opt2, selectDropDown);
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].productId, elements[i].productName, selectDropDown);
		        }
		        
		        if(createProduct)
     			{
		 		  document.getElementById("createStatusMsg").innerHTML = "Product created successfully"; 
				  createProduct=false;       
				}  
		 		        
		    }
		   else if (elements.length==0)
		 {		
					var addOptions = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}
	                
	            var selectDropDowns = document.getElementById("selectedProductId");	       
	            
	            cleanSelectBox("selectedProductId");
	            var opta = "Select Product Name";
	            var optb = "Create New";
				addOptions("-2",opta, selectDropDowns);
				addOptions("-1",optb, selectDropDowns); 
		 		        
		    }
		    
        }
       }
        
    }
    
    function returnJson1() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            var json = eval('(' + req.responseText + ')');
            
			var elements = json.elements;
			//cleanSelectBox("selectedProductLobId");
		if (elements.length!=0)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}
	                
	            var selectDropDown = document.getElementById("selectedProductLobId");	       
	            
	            cleanSelectBox("selectedProductLobId");
	            var opt1 = "Select Product LOB";
	            var opt2 = "Create New";
				addOption("-2",opt1, selectDropDown);
				addOption("-1",opt2, selectDropDown);
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].productLobId, elements[i].productLobName, selectDropDown);
		        }	
		        
		         if(createLOB)
     			{
		 		  document.getElementById("createStatusMsg").innerHTML = "Product LOB created successfully"; 
				  createLOB=false;       
				}  	        
		    }
		      
        }
       }
        
    }
    

    
    var createLOB=false;
    function createLOB_()
    {
     createLOB=true;   
     populateDropdownProductLob();     
    }
    
    var createProduct=false;
    function createProduct_()
    {

     createProduct=true; 
     populateDropdownProductName();

    }

    
    function returnJson2() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            var json = eval('(' + req.responseText + ')');
            
			var elements = json.elements;

		cleanSelectBox("createMultiple");

		if (elements.length!=0)
		 {		
					var addOption = function (value, text, selectBox){
	                var optn = document.createElement("OPTION");
	                optn.text = text;
	                optn.value = value;
	                selectBox.options.add(optn);
	            	}
	                
	            var selectDropDown = document.getElementById("createMultiple");	       
	            
	            cleanSelectBox("createMultiple");
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].synonymId, elements[i].synonymName, selectDropDown);
		        }	
		        return true;	        
		    }	    
        }
       }
        
    }
    
    function cleanSelectBox(selectBox)
  	{
  	var obj = document.getElementById(selectBox);
    if (obj == null) return;
	if (obj.options == null) return;
	while (obj.options.length > 0) {
		obj.remove(0);
	}
  }
  
  function populateDropdownProductLob()
{	

		if(document.productMappingForm.newProductLobName.value==""){
		alert("Please Enter new Product LOB"); 
		createLOB=false;
		return false;
		}
		
		document.getElementById("productLobDiv").style.visibility="hidden";		
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Add Files module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson1;
	    var newProductLobName = document.productMappingForm.newProductLobName.value;
		//alert("Product lob Name :::::"+newProductLobName);
	    var url= 'ajaxSupport.do?mt=getNewProductLobAdded&newProductLobName='+newProductLobName;
	    
	    req.open("GET", url, true);
	    req.send(null);
		


}

function createNewProduct()
{

		if(document.productMappingForm.selectedProductId.value == "-1"){
		document.getElementById("productNameDiv").style.visibility="visible";
		}
		else{
		document.getElementById("productNameDiv").style.visibility="hidden";
		
		// To populate product synonym
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Add Files module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson2;
	    var selectedProductId = document.productMappingForm.selectedProductId.value;
		//alert("Selected product Id :::::"+selectedProductId);
	    var url= 'ajaxSupport.do?mt=getProductSynonymList&selectedProductId='+selectedProductId;
	    
	    req.open("GET", url, true);
	    req.send(null);
		
		
		}		
		
}


function populateDropdownProductName()
{
		if(document.productMappingForm.selectedProductLobId.value=="-2" || document.productMappingForm.selectedProductLobId.value=="-1"){
		alert("Please Select Product LOB"); 
		createProduct=false;
		return false;
		}
		
		if(document.productMappingForm.newProductName.value==""){
		alert("Please enter Product Name"); 
		createProduct=false;
		return false;
		}
		document.getElementById("productNameDiv").style.visibility="hidden";		
	    req = newXMLHttpRequest();
	    if (!req) {
	        alert("Your browser does not support AJAX! Add Files module is accessible only by browsers that support AJAX. " +
	              "Please contact your System Administrator");
	        return;
	    }
	    
	    req.onreadystatechange = returnJson;
	    var newProductName = document.productMappingForm.newProductName.value;
	    var productLobId = document.productMappingForm.selectedProductLobId.value;
		//alert("Product Name :::::"+newProductName);
	    var url= 'ajaxSupport.do?mt=getNewProductNameAdded&newProductName='+newProductName+'&productLobId='+productLobId;
	    
	    req.open("GET", url, true);
	    req.send(null);

}

  function validateData()
{	
		if(document.productMappingForm.selectedProductLobId.value=="-2"){
		alert("Please Select Product LOB"); 
		return false;
		}
		
		if(document.productMappingForm.selectedProductLobId.value=="-1"){
		alert("Please create a Product LOB or Select an Existing one"); 
		return false;
		}
		
		if(document.productMappingForm.selectedProductId.value=="-2"){
		alert("Please Select Product Name"); 
		return false;
		}
		
		if(document.productMappingForm.selectedProductId.value=="-1"){
		alert("Please create a Product Name or Select an Existing one"); 
		return false;
		}
		
		if(document.productMappingForm.newProductSynonym.value=="" ){
		alert("Please Enter Product Synonym"); 
		return false;
		}
		
	document.productMappingForm.methodName.value="insert";	
	document.productMappingForm.submit();
}


</script>

<html:form action="/createProductMapping">

	<html:hidden name="productMappingForm" property="methodName" />
	<html:hidden name="productMappingForm" property="message" />
	
 <div class="box2">
  <div class="content-upload">
	<table width="100%" align="center" cellspacing="0" cellpadding="0">		
		<tr>
				<td colspan="2" align="center" class="error">
					<strong> 
          			<html:messages id="msg" message="true">
                 		<bean:write name="msg"/>  <br>                          
             		</html:messages>
            		</strong>
            	</td>
		</tr>
		<tr >
			<td colspan="4" class="error" align="center"><strong><html:errors/></strong></td>
		</tr>

		<tr>
			<td colspan="4" class="content-upload"><h1><bean:message
				key="createProductMapping.create" /></h1></td>
		</tr>		
		</table>  	
		 <ul class="list2 form1 ">
		 <li class="clearfix">
		 	<strong><font color="green"><div id="createStatusMsg" /><bean:write name="productMappingForm" property="message" /></font></strong>
		 </li>
		
		<li class="clearfix">
				<span class="text2 fll width160"><strong><bean:message key="createProductMapping.selectproductLob" /><FONT color="red" size="1">*</FONT></strong></span>
				<html:select property="selectedProductLobId" name="productMappingForm" styleId="selectedProductLobId" onchange="javascript:loadDropdown();" styleClass="select1">
						<option value="-2" >Select Product LOB</option>
						<option value="-1" ><bean:message key="createProductMapping.createNew" /></option>
						<logic:notEmpty name="productMappingForm" property="productLobList" >
									<bean:define id="elements" name="productMappingForm" property="productLobList" /> 
										<html:options labelProperty="productLobName" property="productLobId"  collection="elements" />
						</logic:notEmpty>
				</html:select>
		</li>
		<li class="clearfix" id="productLobDiv" style="visibility: hidden;">
				<span class="text2 fll width160"><strong><bean:message key="createProductMapping.newProductLob" /><FONT color="red" size="1">*</FONT></strong></span>
				<p class="clearfix fll margin-r20"> <span class="textbox6"> 
					<span class="textbox6-inner"><html:text styleClass="textbox77" property="newProductLobName" maxlength="40" />
				</span> </span> </p><div class="button"><input class="red-btn" onclick="javascript:createLOB_();" type="button" value="Create" /></div>
		</li>
		<li class="clearfix alt">
				<span class="text2 fll width160"><strong><bean:message key="createProductMapping.selectproductName" /><FONT color="red" size="1">*</FONT></strong></span>
				<html:select property="selectedProductId" name="productMappingForm" styleId="selectedProductId" onchange="javascript:createNewProduct();" styleClass="select1">
						<option value="-2" >Select Product Name</option>
						<option value="-1" ><bean:message key="createProductMapping.createNew" /></option>
						<logic:notEmpty name="productMappingForm" property="productNameList" >
									<bean:define id="elementsProduct" name="productMappingForm" property="productNameList" /> 
										<html:options labelProperty="productName" property="productId"  collection="elementsProduct" />
						</logic:notEmpty>
				</html:select>
		</li>
		<li class="clearfix" id="productNameDiv" style="visibility: hidden;">
				<span class="text2 fll width160"><strong><bean:message key="createProductMapping.newProductName" /><FONT color="red" size="1">*</FONT></strong></span>
				<p class="clearfix fll margin-r20"> <span class="textbox6"> 
					<span class="textbox6-inner"><html:text styleClass="textbox77" property="newProductName" maxlength="40" />
					</span> </span> 
				</p>
					<div class="button">
					<input class="red-btn" onclick="javascript:createProduct_();" type="button" value="Create" />
					</div>
		</li>	
		
		<li class="clearfix">
				<span class="text2 fll width160"><strong><bean:message key="createProductMapping.newProductSynonym" /><FONT color="red" size="1">*</FONT></strong></span>
				<p class="clearfix fll margin-r20"> <span class="textbox6"> 
					<span class="textbox6-inner"><html:text styleClass="textbox77" property="newProductSynonym" maxlength="40" />
				</span> </span> </p><div class="button"><input class="red-btn" onclick="javascript:validateData();" type="button" value="Create" /></div>
		</li>
		<li class="clearfix">
			<span class="text2 fll width160"><strong><bean:message key="createProductMapping.synonymList"/></strong></span>
			 <p class="clearfix fll margin-r20"> <span class="textbox6-inner">
				<html:select property="createMultiple" styleId="createMultiple" multiple="multiple" size="6" style="width:160px;" >
					<logic:notEmpty name="productMappingForm" property="synonymList" >
								<bean:define id="elements" name="productMappingForm" property="synonymList" /> 
								<html:options labelProperty="synonymName" property="synonymId"  collection="elements" />					
					</logic:notEmpty>
				</html:select>
			 </span> </p>
		</li>
	</ul>
	  	<!--

	  	<div class="button-area">
            <div class="button"><a class="red-btn" onclick="validateData()"><b>Submit</b></a></div>
		</div>
		
		-->
	 
	 </div>
	 </div>
</html:form>