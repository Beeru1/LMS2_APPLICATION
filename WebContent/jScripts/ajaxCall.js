	var xmlHttpRequest = null;
	var activeXObjects = ["Microsoft.XMLHTTP", "Msxml2.XMLHTTP"];

	if (window.ActiveXObject) // code for IE6, IE5
	{
		for (counter = 0; counter < activeXObjects.length; counter++)
		{
			try
			{
				xmlHttpRequest = new ActiveXObject(activeXObjects[counter]);
				if (xmlHttpRequest != null) break;
			}
			catch (err)
			{
			}
		}
	}
	else if (window.XMLHttpRequest) // code for IE7+, Firefox, Chrome, Opera, Safari
		xmlHttpRequest = new XMLHttpRequest();
	else if (window.XDomainRequest) // code for IE8
		xmlHttpRequest = new XDomainRequest();

	function doAjax(url, method, async, responseHandler, data)
	{
		url = url || "";
		method = method || "GET";
		async = async || true;
		data = data || null;

		if (url == "")
		{
			alert("URL can not be null/blank");
			return false;
		}
		// If AJAX supported
		if (xmlHttpRequest != null)
		{
			// Open Http Request connection
			if (method == "GET")
			{
				url = url + "?" + data;
				data = null;
			}
			xmlHttpRequest.open(method, url, async);
			// Set request header (optional if GET method is used)
			if (method == "POST")
			{
				xmlHttpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			}
			// Assign (or define) response-handler/callback when ReadyState is changed.
			xmlHttpRequest.onreadystatechange = responseHandler;
			// Send data
			xmlHttpRequest.send(data);
		}
		else
		{
			alert("Please use browser with Ajax support.!");
		}
	}
	
	
	
	function doSyncAjax(url, method, responseHandler, data)
	{
		url = url || "";
		method = method || "GET";
		async = false;
		data = data || null;

		if (url == "")
		{
			alert("URL can not be null/blank");
			return false;
		}
		// If AJAX supported
		if (xmlHttpRequest != null)
		{
			// Open Http Request connection
			if (method == "GET")
			{
				url = url + "?" + data;
				data = null;
			}
			xmlHttpRequest.open(method, url, async);
			// Set request header (optional if GET method is used)
			if (method == "POST")
			{
				xmlHttpRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			}
			// Assign (or define) response-handler/callback when ReadyState is changed.
			xmlHttpRequest.onreadystatechange = responseHandler;
			// Send data
			xmlHttpRequest.send(data);
		}
		else
		{
			alert("Please use browser with Ajax support.!");
		}
	}