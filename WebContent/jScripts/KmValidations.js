// KMValidations.js

//the noise string that contains characters that are not allowed
//var NOISE_STRING = "'-&-,@ ";
var NOISE_STRING = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVXYZ";



/**
 * Checks if the given input box is empty. Returns true if its empty or contains
 * whitespace characters only. Else a false.
 *
 * @param	objInput	The input text object.
 * @return	True if empty else false.
 */
function isWhiteSpace(s)
{
  var whitespace = " \t\n\r";
  if(isEmpty(s)) return true;

  for(i = new Number(0); i < s.length; i++)
    if(whitespace.indexOf(s.charAt(i)) == -1) 
	  return false;

  return true;
}
function isEmpty(objInput)
{
	//whitespace characters
 	var whitespace = " \b\t\n\r";
 	
	
	var theInput = trimValue(objInput);
	var theLength = theInput.length;

	// Is the text field empty?
  	if((theInput == null) || (theLength == 0))
	{
    	return (true);
  	}

	// Search through string's characters one by one
  	// until we find a non-whitespace character.
	// When we do, return false; if we don't, return true.
	for (var i = 0; i < theLength ; i++)
  	{
    	// Check that current character isn't whitespace.
    	var theChar = theInput.charAt(i);

    	if (whitespace.indexOf(theChar) == -1)
    	{
	    	return (false);
    	}
  	}//for loop ends

  	// All characters are whitespace.
  	return (true);
}// function isEmpty ends


/**
 * Removes the leading and trailing white spaces from the value in the given text object.
 * The trimmed string is set as the value of the given object and the same is returned by
 * the function.
 *
 * @param	objInput	The input text object.
 * @return 	The trimmed string.
 */
function trimValue(objInput)
{
	strValue = objInput.value;

	var leftIndex;
	var rigthIndex;

	//get the location of the first character that is not a white space.
	for(var i = 0; i < strValue.length; i++)
	{
		if(strValue.charAt(i) != " ")
		{
			leftIndex = i;
			break;
		}
	}

	//get the location of the last character that is not a white space.
	for(var i = strValue.length; i > 0; i--)
	{
		if(strValue.charAt(i - 1) != " ")
		{
			//not i - 1 as the last place is not included while
			// performing a substring
			rigthIndex = i;
			break;
		}
	}

	var strReturn = strValue.substring(leftIndex, rigthIndex);

	//set the same value to the input object
	objInput.value = strReturn;

	//return the trimmed string
	return strReturn;
}


/**
 * Checks if the data entered is an integer. eg 12, 12000, etc.
 * ie its a number with no decimal entries.
 * true if its a number, else false
 *
 * @param	objInput	The input text object.
 * @return 	True if a number else false.
 */
function isInteger(objInput)
{
	if (isEmpty(objInput))
	{
		return false;
	}

	theInput = trimValue(objInput);
	theLength = theInput.length ;

  	for (var i = 0 ; i < theLength ; i++)
  	{
    	if (theInput.charAt(i) < '0' || theInput.charAt(i) > '9' || theInput.charAt(i)<'a')
    	{
      		 //the text field has a non numeric entry
        	return(false);
    	}
	}// for ends

  	return (true);
}// function isInteger ends
function isName(objInput)
{
	if (isEmpty(objInput))
	{
		return false;
	}

	theInput = trimValue(objInput);
	theLength = theInput.length ;

  	for (var i = 0 ; i < theLength ; i++)
  	{
    	if (theInput.charAt(i) < '0' || theInput.charAt(i) > '9')
    	{
      		 //the text field has a non numeric entry
        	return(false);
    	}
	}// for ends

  	return (true);
}// function isInteger ends



/**
 * Checks if the data entered is a number. eg 12.1, 12000, etc.
 * ie a number with only one decimal entry
 * true if its a number, else false
 *
 * @param	objInput	The input text object.
 * @return 	True if a number else false.
 */
function isFloat(objInput)
{
	if (isEmpty(objInput))
	{
		return false;
	}

	theInput = trimValue(objInput);
	theLength = theInput.length ;

  	var dec_count = 0 ;

  	for (var i = 0; i < theLength; i++)
  	{
    	if(theInput.charAt(i) == '.')
		{
			// the text field has decimal point entry
			dec_count = dec_count + 1;
		}
  	}

	if(dec_count > 1)
	{
		return(false);
	}

  	//check if number field contains only a single '.'
  	if (dec_count == 1 && dec_count == theLength)
  	{
  		return false;
  	}

  	for (var i = 0 ; i < theLength ; i++)
  	{
    	if (theInput.charAt(i) < '0' || theInput.charAt(i) > '9')
    	{
        	if(theInput.charAt(i) != '.')
      		{
      		 	//the text field has alphabet entry
        		return(false);
      		}
    	}
	 }// for ends

  	return (true);
}// function isFloat ends

/**
 * Checks if the entry is a valid email address in terms of format.
 * Email address must be of form a@b.c i.e.
 * there must be at least one character before and afterthe '.'
 * the characters @ and . are both required
 * Returns true for a valid email address else a false.
 *
 * @param	objInput	The input text object.
 * @return 	True for a valid email id else false.
 */
function isEmailAddress(objInput)
{
	if (isEmpty(objInput))
	{
		return false;
	}

	theInput = trimValue(objInput);
	theLength = theInput.length;

 	// there must be >= 1 character before @, so
  	// start looking from character[1]
  	// (i.e. second character in the text field)
  	// look for '@'
	var i = 1;
	var cnt = 0;

	for(j=0;j<=theLength;j++)
	{
		if (theInput.charAt(j) == '@')
			cnt += 1;
	}

	if (cnt != 1)
	{
		//This cant be a email address
		return(false);
	}

	while ((i < theLength) && (theInput.charAt(i) != "@"))
	{
		// search till the last character
		i++ ;
	}

	if ((i >= theLength) || (theInput.charAt(i) != "@"))
	{
		// did not find the '@' character hence can't be email address.
		return (false);
	}
	else
	{
		// go 2 characters forward so that '@' and . are not simultaneous.
		i += 2;
	}

	// look for . (dot)
	while ((i < theLength) && (theInput.charAt(i) != "."))
	{
		// keep searching for '.'
		i++ ;
	}

	// there must be at least one character after the '.'
	if ((i >= theLength - 1) || (theInput.charAt(i) != "."))
	{
		// didn't find a '.' so its not a valid email ID
		return (false);
	}
	if(hasSpecialCharactersEmail(objInput))
	{
		return false;
	}
	else
	{
		// finally its got to be email ID
		return (true);
	}
}// function isEmailAddress ends
/**
 * Checks the given textfield for special characters. It inturn calls the actual function
 * that does the verification.
 *
 * @param	objEntry	The text object containing text
 * @return	True if special characters are found else false
 */
function hasSearchSpecialChars(objEntry)
{
	return hasSpecialChars(objEntry, NOISE_STRING);
}

/**
 * Verifies the supplied text object for existence of special characters.
 *
 * @param	objEntry	The text object containing text
 * @param	searchChars	The characters that are disallowed
 * @return	True if special characters are found else false
 */
function hasSpecialChars(objEntry, searchChars)
{
	if (isEmpty(objEntry))
	{
		return false;
	}

	theInput = trimValue(objEntry);
	theLength = theInput.length;

	// theNoisyString Is a special Characters String
	var theNoisyString;

	if (searchChars == "")
		theNoisyString = "";	//everything allowed
	else
		theNoisyString = searchChars;


	for (var i = 0; i < theLength+2 ; i++)
	{
		// Check that current character isn't noisy.
		theChar = theInput.charAt(i);
		//(") was not checked due to String constraint
		// check it first

		if(theChar =='"')
		{
			return(true);
		}

		if ((theNoisyString.indexOf(theChar) != -1) )
		{
			return (true);
		}
	}//for loop ends

	return (false);
 } // function hasSpecialChars(theEntry) ends

/**
 * This function checks whether the contents of the textfield is a valid date.
 * Date should be in DD/MM/YYYY format.
 *
 * @param objEntry The textfield containing the date value
 * @return boolean - true if valid date/ false if not
 */
 function hasSpecialCharactersEmail(field){   
	var SpecialCharacters="`~!$^&*()=+><{}[]+|=?':;\\\" ";
	if (field.value.length >= 0)	{
		for(i=0; i<SpecialCharacters.length; i++)	{
			if(field.value.indexOf(SpecialCharacters.substr(i, 1))>= 0)	{ 
					return true;
			}
		}
		return false;
	}	
	return false;
}
function getRule(cond)
{
	var Id = document.getElementById("schemeValue1").value;
	
   //alert(Id);
	var url="cheme.do?method=getRuleScheme&Id="+Id+"&cond="+cond;
//	var url="initCreateRequisition.do?method=getChange&Id="+Id;
	
	if(window.XmlHttpRequest) {
		req = new XmlHttpRequest();
	}else if(window.ActiveXObject) {
		req=new ActiveXObject("Microsoft.XMLHTTP");
	}
	if(req==null) {
		alert("Browser Doesnt Support AJAX");
		return;
	}
	req.onreadystatechange = getOnRule;
	
	req.open("POST",url,false);
	req.send();
}
function getOnRule()
{
	if (req.readyState==4 || req.readyState=="complete") 
  	{
		var xmldoc = req.responseXML.documentElement;
		if(xmldoc == null){		
			return;
		}
		if(document.getElementById("schemeValue1").value!="s")
		{
		optionValues = xmldoc.getElementsByTagName("option");
		var selectObj = document.getElementById("ruleValue");
		selectObj.options.length = 0;
		selectObj.options[selectObj.options.length] = new Option("Select Rule","-1");
		for(var i=0; i<optionValues.length; i++){
			selectObj.options[selectObj.options.length] = new Option(optionValues[i].getAttribute("text"),optionValues[i].getAttribute("value"));
			}
		}
	}
}
function removeAllOptions(element, defaultOption)
{
	while (element.options.length > 0)
		element.options[element.options.length - 1] = null;
	if (defaultOption != null)
		element.options[element.options.length] = defaultOption;
}
