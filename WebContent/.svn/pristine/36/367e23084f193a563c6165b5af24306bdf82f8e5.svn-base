// addFile.js

var req=null; 
var ctr=1;
alert("i ma her");

/*
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

function loadDropdown(parentId)
{   
	alert("hi");
    //Obtain an XMLHttpRequest instance
    req = newXMLHttpRequest();
    //alert (req);
    if (!req) {
        alert("Your browser does not support AJAX! Add Files module is accessible only by browsers that support AJAX. " +
              "Please check the KM requirements and contact your System Administrator");
        return;
    }
    
    req.onreadystatechange = returnJson;
    var form1 = document.forms[0];
    parentId=this.value;
    var url=path+"/addFile.do?method=loadElementDropDown&ParentId="+parentId+"&Dummy="+new Date().getTime();
    req.open("GET", url, true);
    req.send(null);
}

function returnJson() {
    if (req.readyState == 4) {
        if (req.status == 200) {
        	alert(req.responseText);
            var json = eval('(' + req.responseText + ')');
            
			var elements = json.elements;
			var level = json.level;

			//alert("Done setting values");
            var addOption = function (value, text, selectBox)
            {
                var optn = document.createElement("OPTION");
                optn.text = text;
                optn.value = value;
                selectBox.options.add(optn);
            }
            
            var form=document.forms[0];
            
            var addSelect = function (id,name)
            {
                var selectDropDown = document.createElement("SELECT");
                selectDropDown.id = id;
				selectDropDown.name = name;
				//selectDropDown.onchange = loadDropDown;
				if (selectDropDown.addEventListener){
					selectDropDown.addEventListener('click', loadDropDown, false); 
				} else if (selectDropDown.attachEvent){
					selectDropDown.attachEvent('onclick', loadDropDown);
				}
				for (var i = 0; i < elements.length; i++) {
		            addOption(elements[i].elementId, elements[i].elementName, selectDropDown);
		        }
				form.appendChild(selectBox);
            }
                        
            var id="level_"+level;
            var name="element_"+ctr++;
            addSelect(id,name)
            
        }
    }
}
*/