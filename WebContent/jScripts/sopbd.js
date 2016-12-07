
 var arr = new Array();
 var arrVas = new Array();
 var arrVoice = new Array();
 var arrMo = new Array();
 var arrCNN = new Array();
 var arrALive = new Array();
 var arrIndex = 0;
 var arrIndexVas = 0;
 var arrIndexVoice = 0;
 var arrIndexMo = 0;
 var arrIndexCNN = 0;
 var arrIndexALive = 0;
 
function Init(s)
{
s.document.designMode = 'On';
arr[arrIndex]=s;
arrIndex++;
}

 function InitVas(s)
{
s.document.designMode = 'On';
arrVas[arrIndexVas]=s;
arrIndexVas++;
}

 function InitVoice(s)
{
s.document.designMode = 'On';
arrVoice[arrIndexVoice]=s;
arrIndexVoice++;
}

 function InitMo(s)
{
s.document.designMode = 'On';
arrMo[arrIndexMo]=s;
arrIndexMo++;
}

 function InitCNN(s)
{
s.document.designMode = 'On';
arrCNN[arrIndexCNN]=s;
arrIndexCNN++;
}

 function InitALive(s)
{
s.document.designMode = 'On';
arrALive[arrIndexALive]=s;
arrIndexALive++;
}


//////////// For Base content
function InitEditModeFrames(s)
{
	var iFrm = s.contentWindow;
	Init(iFrm);
}

 function addMoreContentFields()
 {
	 var ul = document.getElementById("contentList");
    var li = document.createElement("li");
    var liLength = ul.getElementsByTagName("li").length;
    var liClass = "clearfix padd10-0";
    if( liLength == 15)
    {
       alert("Maximum 15 rows can be added");
       return false;
    }   
    
    if( liLength %2 ==1)
    {
       liClass = "clearfix padd10-0 alt";
    }
    li.setAttribute("class", liClass);
    var contentText = "<br> <p class='clearfix fll margin-r20'> <span class='textbox6'> <span class='textbox6-inner'><input type='text' name='header' value='' class='textbox7'></span> </span> </p>";
    	contentText = contentText + "<iframe id='iView"+arrIndex+"' name=c Class='textarea2 fll' frameborder='0'></iframe>";
    	contentText = contentText +  " <input type='hidden' name='content' value='' id='content"+arrIndex+"'><p>";
    li.innerHTML = contentText;
    ul.appendChild(li); 
    var iViewObj = "iView"+arrIndex;
    var winObj = window.document;
    var obj = winObj.getElementById(iViewObj);
    InitEditModeFrames(obj);
    return true;
 }
///////////////////////

//////////// For VAS content
function InitEditVASModeFrames(s)  // c
{
	var iFrm = s.contentWindow;
	InitVas(iFrm);   // c
}

 function addMoreVASContentFields()   // c
 {
    var ul = document.getElementById("contentListVAS"); // c
    var li = document.createElement("li");
    var liLength = ul.getElementsByTagName("li").length;
    var liClass = "clearfix padd10-0";
    
    if( liLength == 15)
    {
       alert("Maximum 15 rows can be added");
       return false;
    }   
    
    
    if( liLength %2 ==1)
    {
       liClass = "clearfix padd10-0 alt";
    }
    li.setAttribute("class", liClass);
    var contentText = "<br> <p class='clearfix fll margin-r20'> <span class='textbox6'> <span class='textbox6-inner'><input type='text' name='headerVas' value='' class='textbox7'></span> </span> </p>";
    	contentText = contentText + "<iframe id='iViewVas"+arrIndexVas+"' name=c Class='textarea2 fll' frameborder='0'></iframe>"; // c
    	contentText = contentText +  " <input type='hidden' name='contentVas' value='' id='contentVas"+arrIndexVas+"'><p>"; // c
    li.innerHTML = contentText;
    ul.appendChild(li); 
    var iViewObj = "iViewVas"+arrIndexVas;  // c
    var winObj = window.document;
    var obj = winObj.getElementById(iViewObj);
    InitEditVASModeFrames(obj); // c
    return true;
 }
///////////////////////


//////////// For Voice content
function InitEditVoiceModeFrames(s)  // c
{
	var iFrm = s.contentWindow;
	InitVoice(iFrm);   // c
}

 function addMoreVoiceContentFields()   // c
 {
    var ul = document.getElementById("contentListVOICE"); // c
    var li = document.createElement("li");
    var liLength = ul.getElementsByTagName("li").length;
    var liClass = "clearfix padd10-0";
    if( liLength == 15)
    {
       alert("Maximum 15 rows can be added");
       return false;
    }   
    
    if( liLength %2 ==1)
    {
       liClass = "clearfix padd10-0 alt";
    }
    li.setAttribute("class", liClass);
    var contentText = "<br> <p class='clearfix fll margin-r20'> <span class='textbox6'> <span class='textbox6-inner'><input type='text' name='headerVoice' value='' class='textbox7'></span> </span> </p>";
    	contentText = contentText + "<iframe id='iViewVoice"+arrIndexVoice+"' name=c Class='textarea2 fll' frameborder='0'></iframe>"; // c
    	contentText = contentText +  " <input type='hidden' name='contentVoice' value='' id='contentVoice"+arrIndexVoice+"'><p>"; // c
    li.innerHTML = contentText;
    ul.appendChild(li); 
    var iViewObj = "iViewVoice"+arrIndexVoice;  // c
    var winObj = window.document;
    var obj = winObj.getElementById(iViewObj);
    InitEditVoiceModeFrames(obj); // c
    return true;
 }
///////////////////////



//////////// For MO content
function InitEditMoModeFrames(s)  // c
{
	var iFrm = s.contentWindow;
	InitMo(iFrm);   // c
}

 function addMoreMoContentFields()   // c
 {
    var ul = document.getElementById("contentListMO"); // c
    var li = document.createElement("li");
    var liLength = ul.getElementsByTagName("li").length;
    var liClass = "clearfix padd10-0";
    if( liLength == 15)
    {
       alert("Maximum 15 rows can be added");
       return false;
    }   
    
    if( liLength %2 ==1)
    {
       liClass = "clearfix padd10-0 alt";
    }
    li.setAttribute("class", liClass);
    var contentText = "<br> <p class='clearfix fll margin-r20'> <span class='textbox6'> <span class='textbox6-inner'><input type='text' name='headerMo' value='' class='textbox7'></span> </span> </p>";
    	contentText = contentText + "<iframe id='iViewMo"+arrIndexMo+"' name=c Class='textarea2 fll' frameborder='0'></iframe>"; // c
    	contentText = contentText +  " <input type='hidden' name='contentMo' value='' id='contentMo"+arrIndexMo+"'><p>"; // c
    li.innerHTML = contentText;
    ul.appendChild(li); 
    var iViewObj = "iViewMo"+arrIndexMo;  // c
    var winObj = window.document;
    var obj = winObj.getElementById(iViewObj);
    InitEditMoModeFrames(obj); // c
    return true;
 }
///////////////////////


//////////// For CNN content
function InitEditCNNModeFrames(s)  // c
{
	var iFrm = s.contentWindow;
	InitCNN(iFrm);   // c
}

 function addMoreCNNContentFields()   // c
 {
    var ul = document.getElementById("contentListCNN"); // c
    var li = document.createElement("li");
    var liLength = ul.getElementsByTagName("li").length;
    var liClass = "clearfix padd10-0";
    if( liLength == 15)
    {
       alert("Maximum 15 rows can be added");
       return false;
    }   
    
    if( liLength %2 ==1)
    {
       liClass = "clearfix padd10-0 alt";
    }
    li.setAttribute("class", liClass);
    var contentText = "<br> <p class='clearfix fll margin-r20'> <span class='textbox6'> <span class='textbox6-inner'><input type='text' name='headerCNN' value='' class='textbox7'></span> </span> </p>";
    	contentText = contentText + "<iframe id='iViewCNN"+arrIndexCNN+"' name=c Class='textarea2 fll' frameborder='0'></iframe>"; // c
    	contentText = contentText +  " <input type='hidden' name='contentCNN' value='' id='contentCNN"+arrIndexCNN+"'><p>"; // c
    li.innerHTML = contentText;
    ul.appendChild(li); 
    var iViewObj = "iViewCNN"+arrIndexCNN;  // c
    var winObj = window.document;
    var obj = winObj.getElementById(iViewObj);
    InitEditCNNModeFrames(obj); // c
    return true;
 }
///////////////////////


//////////// For Alive content
function InitEditAliveModeFrames(s)  // c
{
	var iFrm = s.contentWindow;
	InitALive(iFrm);   // c
}

 function addMoreAliveContentFields()   // c
 {
    var ul = document.getElementById("contentListALIVE"); // c
    var li = document.createElement("li");
    var liLength = ul.getElementsByTagName("li").length;
    var liClass = "clearfix padd10-0";
    if( liLength == 15)
    {
       alert("Maximum 15 rows can be added");
       return false;
    }   
    
    if( liLength %2 ==1)
    {
       liClass = "clearfix padd10-0 alt";
    }
    li.setAttribute("class", liClass);
    var contentText = "<br> <p class='clearfix fll margin-r20'> <span class='textbox6'> <span class='textbox6-inner'><input type='text' name='headerALive' value='' class='textbox7'></span> </span> </p>";
    	contentText = contentText + "<iframe id='iViewALive"+arrIndexALive+"' name=c Class='textarea2 fll' frameborder='0'></iframe>"; // c
    	contentText = contentText +  " <input type='hidden' name='contentALive' value='' id='contentALive"+arrIndexALive+"'><p>"; // c
    li.innerHTML = contentText;
    ul.appendChild(li); 
    var iViewObj = "iViewALive"+arrIndexALive;  // c
    var winObj = window.document;
    var obj = winObj.getElementById(iViewObj);
    InitEditAliveModeFrames(obj); // c
    return true;
 }
///////////////////////

