
	function addRemove(selectBox1, selectBox2)
	{
		for(var i=0;i<selectBox1.childNodes.length; i++)
		{
			if(selectBox1.childNodes[i].selected)
			{	
				var option = document.createElement("option");
				option.text = selectBox1.childNodes[i].text;
				option.value = selectBox1.childNodes[i].value;
				selectBox2.add(option);
			}
		}
		for(i=selectBox1.childNodes.length-1; i>=0; i--)
		{
			if(selectBox1.childNodes[i].selected)
			{
				selectBox1.remove(selectBox1.selectedIndex);
			}
		}
	}
	
	function addToRight()
	{
		var leftBox = document.getElementById('leftBox');
		var leftSelect = leftBox.childNodes[1];
		var rightBox = document.getElementById('rightBox');
		var rightSelect = rightBox.childNodes[1];
		
		addRemove(leftSelect, rightSelect);
	}
	
	function addToLeft()
	{
		var leftBox = document.getElementById('leftBox');
		var leftSelect = leftBox.childNodes[1];
		var rightBox = document.getElementById('rightBox');
		var rightSelect = rightBox.childNodes[1];
		
		addRemove(rightSelect, leftSelect);
		
		document.getElementById('displayName').value = '';
	}
	
	function addFidToRight()
	{
		var leftBox = document.getElementById('leftFidBox');
		var leftSelect = leftBox.childNodes[1];
		var rightBox = document.getElementById('rightFidBox');
		var rightSelect = rightBox.childNodes[1];
		
		addRemove(leftSelect, rightSelect);
	}
	
	function addFidToLeft()
	{
		var leftBox = document.getElementById('leftFidBox');
		var leftSelect = leftBox.childNodes[1];
		var rightBox = document.getElementById('rightFidBox');
		var rightSelect = rightBox.childNodes[1];
		
		addRemove(rightSelect, leftSelect);
	}
	
	function getTotalSelectedCheckbox()
	{
		var count = 0;
		for(var i=1;i<=24;i++)
		{
			var label = document.getElementById('lbl'+i);
			if(label.childNodes[1].checked)
				count++;
		}
		return count;
	}
	
	function verifyTimingsWithFrequency()
	{
		var td = document.getElementById('frequencyTd');
		var freq = td.childNodes[1].value; 
		
		var totalSelectedCheckbox = getTotalSelectedCheckbox();
		
		if(totalSelectedCheckbox != freq)
		{
			alert("Timings selected must be equal to frequency.");
			return false;
		}
		return true;
	}
	
	function verifyWithFrequency(obj)
	{
		var td = document.getElementById('frequencyTd');
		var freq = td.childNodes[1].value; 
		var totalSelectedCheckbox = getTotalSelectedCheckbox();
		if(obj.checked)
		{
			if(freq < totalSelectedCheckbox)
			{
				alert('Timings selected must be equal to frequency.');
				obj.checked=false;
			}
		}
	}
	
	function verifyFrequency(obj)
	{
		if(obj.value != "")
		{
			if(isNaN(obj.value))
			{
				alert('Frequency can be between 1 and 24');
				obj.value="";
				return false;
			}
			
			if(obj.value <= 0 || obj.value >24)
			{
				alert('Frequency can be between 1 and 24');
				obj.value="";
			}
			return true;
		}
		return true;
	}
	
	function submitForm()
	{
		var reportTd = document.getElementById('reportTd');
		if(reportTd != null)
		{
			var fidRadioParent1 = document.getElementById('radiolbl1');
			var productRadioParent1 = document.getElementById('radiolbl2');
			
			var fidRadioButton1 = fidRadioParent1.childNodes[1]; 
			var productRadioButton1 = productRadioParent1.childNodes[1];
			
			if(fidRadioButton1.checked)
			{
				// send to get fids
				document.forms[0].methodName.value='createRCGetDetails';
				document.forms[0].submit();
			}
			else if(productRadioButton1.checked)
			{
				// send to get products
				document.forms[0].methodName.value='createRCGetDetails';
				document.forms[0].submit();
			}
			else
			{
				// send to get selected products
				document.forms[0].methodName.value='createRCGetSelectedProducts';
				document.forms[0].submit();
			}
		}
		else
		{
			
			var selectedIndex = document.forms[0].selectedReport.selectedIndex; 
			
			if(selectedIndex != 0)
			{
				document.forms[0].methodName.value='getReportConfiguration';
				document.forms[0].submit();
			}
		}
	}
	
	function submitFormBasedOnRole()
	{
		var selectedIndex = document.forms[0].selectedReport.selectedIndex;
		if(selectedIndex != 0)
		{
			document.forms[0].methodName.value='getReportConfiguration';
			document.forms[0].submit();
		}
	}
	
	function verifyEmailRecipients()
	{
		var td = document.getElementById('frequencyTd');
		var freq = td.childNodes[1].value;
		if(freq.trim() == "")
		{
			alert('Frequency can not be empty');
			return false;
		}
		
		td = document.getElementById('toTD');
		var emails =  td.childNodes[1].value;
		
		if(emails.trim() == "")
		{
			alert('To Recipients can not be empty');
			return false;
		}
		
		if(!verifyContents(td.childNodes[1]))
		{
			alert("Incorrect email is provided in TO Recipients");
			return false;
		}
		
		td = document.getElementById('ccTD');
		emails =  td.childNodes[1].value;
		
		if(emails.trim() == "")
		{
			alert('CC Recipients can not be empty');
			return false;
		}
		
		if(!verifyContents(td.childNodes[1]))
		{
			alert("Incorrect email is provided in CC Recipients");
			return false;
		}
		
		return true;
	}
	
	function verifySubject()
	{
		var subjectTd = document.getElementById('subjectTD');
		var textField = subjectTd.childNodes[1];
		if(textField.value.trim() == "")
		{
			alert("Pls. provide the subject.");
			return false;
		}
		return true;
	}
	
	function verifyProducts()
	{
		var productsTd = document.getElementById('productBox');
		if(productsTd != null)
		{
			var selectBox = productsTd.childNodes[1];
			
			for(var i=0; i<selectBox.options.length; i++)
			{
				if(selectBox.options[i].selected)
					return true;
			}
			alert("Pls. select atleast one product.");
			return false;
		}
		return true;
	}
	
	function submitFormToHoldData()
	{
		var reportCombo = document.getElementById('reportBox');
		
		var flag = false;
		
		if(reportCombo == null)
		{
			var reportName = document.getElementById('reportTd');
			var textField = reportName.childNodes[1];

			if(textField.value.trim() == "")
			{
				alert("Report Name can't be empty.");
				return false;
			}

			// verify inputs
			flag = verifyTimingsWithFrequency();
			if(!flag)
				return false;
			
			flag = verifyEmailRecipients();
			if(!flag)
				return false;
			
			flag = verifyFids();
			if(!flag)
				return false;
			
			flag = verifyColumns();
			if(!flag)
				return false;
			
			flag = verifySubject();
			if(!flag)
				return false;
			
			flag = verifyProducts();
			if(!flag)
				return false;

			
			document.forms[0].methodName.value='holdCreatedReportConfiguration';
			document.forms[0].submit();
			return true;
		}
		else
		{
			if(document.forms[0].selectedReport.value == -1)
			{
				alert('Pls. select report to modify.');
				return false;
			}
			
			var selectBox = document.forms[0].selectedReport;
			
			if(selectBox.value != 5)
			{
				// verify inputs
				flag = verifyTimingsWithFrequency();
				if(!flag)
					return false;
				
				flag = verifyEmailRecipients();
				if(!flag)
					return false;
			}
			
			flag = verifyFids();
			if(!flag)
				return false;
			
			flag = verifyColumns();
			if(!flag)
				return false;
		
			document.forms[0].methodName.value='holdReportConfiguration';
			document.forms[0].submit();
			return true;
		}
	}
	
	function submitFormToSaveData()
	{
		var reportCombo = document.getElementById('reportBox');
		
		var flag = false;
		
		if(reportCombo == null)
		{
			var reportName = document.getElementById('reportTd');
			var textField = reportName.childNodes[1];

			if(textField.value.trim() == "")
			{
				alert("Report Name can't be empty.");
				return false;
			}

			// verify inputs
			flag = verifyTimingsWithFrequency();
			if(!flag)
				return false;
			
			flag = verifyEmailRecipients();
			if(!flag)
				return false;
			
			flag = verifyFids();
			if(!flag)
				return false;
			
			flag = verifyColumns();
			if(!flag)
				return false;
			
			flag = verifySubject();
			if(!flag)
				return false;
			
			flag = verifyProducts();
			if(!flag)
				return false;
			
			document.forms[0].methodName.value='saveCreatedReportConfiguration';
			document.forms[0].submit();
			return true;
		}
		else
		{

			if(document.forms[0].selectedReport.value == -1)
			{
				alert('Pls. select report to modify.');
				return false;
			}
			
			var selectBox = document.forms[0].selectedReport;
			
			if(selectBox.value != 5)
			{
				// verify inputs
				flag = verifyTimingsWithFrequency();
				if(!flag)
					return false;
				
				flag = verifyEmailRecipients();
				if(!flag)
					return false;
			}
			
			flag = verifyFids();
			if(!flag)
				return false;
			
			flag = verifyColumns();
			if(!flag)
				return false;
		
			document.forms[0].methodName.value='saveReportConfiguration';
			document.forms[0].submit();
			return true;
		}
	}

	function hidecontrol()
	{
		document.getElementById('hiddenDiv').style.display = 'none';
	}

	function clear(selectBox)
	{
		for(var i=selectBox.childNodes.length-1; i>=0; i--)
		{
			selectBox.remove(selectBox.childNodes[i]);
		}
	}

	function unselectBoxes()
	{
		var leftBox = document.getElementById('leftBox');
		var leftSelect = leftBox.childNodes[1];
		var rightBox = document.getElementById('rightBox');
		var rightSelect = rightBox.childNodes[1];
		unselect(leftSelect);
		unselect(rightSelect);
	}
	
	function unselect(selectBox)
	{
		for(var i=0; i<selectBox.options.length; i++)
		{
			selectBox.options[i].checked = false;
		}
	}
	
	function fillHiddenSelect()
	{
		var box = document.forms[0].updatedColumns;
		clear(box);
		var rightBox = document.getElementById('rightBox');
		var rightSelect = rightBox.childNodes[1];
		
		var hiddenSelect = document.forms[0].updatedColumns;
		
		for(var i=0; i<rightSelect.options.length; i++)
		{
			var option = document.createElement("option");
			option.text = rightSelect.options[i].text;
			option.value = rightSelect.options[i].value;
			option.selected = true;
			hiddenSelect.add(option);
		}
		return true;
	}
	
	function fillHiddenFidSelect()
	{
		var box = document.forms[0].updatedFids;
		clear(box);
		var rightBox = document.getElementById('rightFidBox');
		
		if(rightBox != null)
		{
			var rightSelect = rightBox.childNodes[1];
			var hiddenSelect = document.forms[0].updatedFids;
			
			for(var i=0; i<rightSelect.options.length; i++)
			{
				var option = document.createElement("option");
				option.text = rightSelect.options[i].text;
				option.value = rightSelect.options[i].value;
				option.selected = true;
				hiddenSelect.add(option);
			}
		}
		return true;
	}
	
	var map = {};
	
	function saveDisplayNameOnBlur()
	{
		var rightBox = document.getElementById('rightBox');
		var rightSelect = rightBox.childNodes[1];
		
		if(rightSelect.selectedIndex != -1)
		{
			var selectedValue = rightSelect.options[rightSelect.selectedIndex].value;  
			var val = document.getElementById('displayName').value;
			map[selectedValue] = val.trim();
		}
	}
	
	function showDisplayNameOnSelect()
	{
		var rightBox = document.getElementById('rightBox');
		var rightSelect = rightBox.childNodes[1];
		
		var displayText = document.getElementById('displayName');
		displayText.value = '';
		
		
		if(rightSelect.selectedIndex == -1)
		{
			displayText.value = '';
		}
		else
		{
			if(map[rightSelect.options[rightSelect.selectedIndex].value])
			{
				displayText.value = map[rightSelect.options[rightSelect.selectedIndex].value];
			}
			else
			{
				var box = document.forms[0].displayNames;
				for(var i=0; i<box.options.length;i++)
				{
					if(box.options[i].value == rightSelect.options[rightSelect.selectedIndex].value)
					{
						if(map[rightSelect.options[rightSelect.selectedIndex].value] != '')
						{
							displayText.value = box.options[i].text;
							break;
						}
					}
				}
			}
		}
	}
	
	function saveDisplayNames()
	{
		var rightBox = document.getElementById('rightBox');
		var rightSelect = rightBox.childNodes[1];
		
		var box = document.forms[0].displayNamesMap;
		clear(box);

		for(var i=0; i<rightSelect.options.length; i++)
		{
			if(map[rightSelect.options[i].value])
			{
				var option = document.createElement("option");	
				option.text = rightSelect.options[i].value + '#' +map[rightSelect.options[i].value];
				option.value = rightSelect.options[i].value + '#' +map[rightSelect.options[i].value];
				option.selected = true;
				box.add(option);
			}
		}
	}
	
	function exist(value, selectBox)
	{
		for(var i=0; i<selectBox.options.length; i++)
		{
			var text = selectBox.options[i].text;
			var arr = text.split('#');
			if(arr[0] == value)
				return true;
		}
		return false;
	}
	
	function moveDisplayNamesToMap()
	{
		var box = document.forms[0].displayNamesMap;
		var box2 = document.forms[0].displayNames
		
		for(var i=0; i<box2.options.length; i++)
		{
			var text = box2.options[i].value + '#' +box2.options[i].text;
			var arr = text.split('#')
			if(!exist(arr[0], box))
			{
				if(map[box2.options[i].value] || map[box2.options[i].value] != '')
				{
					var option = document.createElement("option");	
					option.text = box2.options[i].value + '#' +box2.options[i].text;
					option.value = box2.options[i].value + '#' +box2.options[i].text;
					option.selected = true;
					box.add(option);
				}
			}
		}
	}
	
	function verifyContents(textBox)
	{
		if(textBox.value == '')
			return false;
		var array = textBox.value.split(',');
		for(var indx in array)
		{
			if(array[indx] == '')
				continue;
			if(!verifyEmail(array[indx]))
			{
				return false;		
			}
		}
		return true;
	}
	
	function verifyEmail(val)
	{
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;

		return reg.test(val.trim());
    }
    
    function verifyColumns()
    {
    	var rightBox = document.getElementById('rightBox');
		var rightSelect = rightBox.childNodes[1];
		if(rightSelect.options.length == 0)
		{
			alert('Pls. select atleast one column.');
			return false;
		}
		return true;
    }
    
    function verifyFids()
    {
    	var rightBox = document.getElementById('rightFidBox');
    	if(rightBox != null)
    	{
    		var rightSelect = rightBox.childNodes[1];
    		if(rightSelect.options.length == 0)
    		{
    			alert('Pls. select atleast one FID.');
    			return false;
    		}
    	}
		return true;
    }
    
    function submitReportType()
    {
		document.forms[0].methodName.value='getReportConfiguration';
		document.forms[0].submit();
	}

    function submitFormToCreateReport()
    {
    	document.forms[0].methodName.value='createReportConfigurationInit';
		document.forms[0].submit();
	}    
	
    function submitFormToModifyReport()
    {
    	document.forms[0].methodName.value='modifyReportConfiguration';
		document.forms[0].submit();
	}
    
    function deleteSelectedConfiguration()
    {
    	if(confirm('Do you really want to delete the selected configuration?'))
    	{
    		document.forms[0].methodName.value='deleteReportConfiguration';
    		document.forms[0].submit();	
    	}
    }
    
    function verifyTextContents(textBox)
    {
    	var value = textBox.value.trim();
    	if(value == '')
    		return false;
    	
    	if(/^[a-zA-Z0-9- ]*$/.test(value) == false) 
    	{
    	    alert('Value contains illegal characters. \n' + value +'\n Pls. correct');
    	    return false
    	}
    	return true;
    }
