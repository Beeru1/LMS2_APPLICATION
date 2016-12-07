function addToStay(url) {
	req = newXMLHttpRequest();
    if (!req) {
        alert("Your browser does not support AJAX! Stay is supported by browsers that support AJAX. " +
              "Please check the KM requirements and contact your System Administrator");
        return;
    }
	req.open("GET", url, false);
	req.send(null);
	document.getElementById("stayConnectLabel").innerHTML= "<strong><font color=red>Document added to stay.</font></strong>";  
	  
}
function connect(stayURL) { 
  if(stayURL ==null || stayURL =="null")
  {
   document.getElementById("stayConnectLabel").innerHTML= "<strong><font color=red>No Document selected for Connect.</font></strong>";  
   return false;
  }
	req = newXMLHttpRequest();
    if (!req) {
        alert("Your browser does not support AJAX! Stay is supported by browsers that support AJAX. " +
              "Please check the KM requirements and contact your System Administrator");
        return true;
    }
    
	req.onreadystatechange = displayDoc;
	req.open("GET", stayURL, true);
	req.send(null);
}
function displayDoc() {
	if (req.readyState == 4) {  
        if (req.status == 200) { 
	      var content = req.responseText;	     
	      content = content.replace("stayConnectDiv","stayConnectDiv2"); 
	      document.getElementById("multiDocViewer").innerHTML =  content;
	      document.getElementById("stayConnectDiv2").style.visibility = 'hidden';   	
        } 
	}
}

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
