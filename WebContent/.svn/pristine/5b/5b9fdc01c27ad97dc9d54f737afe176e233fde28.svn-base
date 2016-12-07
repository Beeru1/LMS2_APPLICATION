
<style>

.menu_container{
    margin: 10px 0 10px 10px;
    padding: 10px;
    overflow: hidden;
}

.menu li{
    float:left; list-style: none;
}

.menu li:first-child a{
    -moz-border-radius-topleft: 3px;
    -moz-border-radius-bottomleft: 3px;
    border-top-left-radius: 3px;
    border-bottom-left-radius: 3px;  
}

.menu li:last-child a{
    -webkit-border-top-right-radius: 3px;
    -webkit-border-bottom-right-radius: 3px;
    -moz-border-radius-topright: 3px;
    -moz-border-radius-bottomright: 3px;
    border-top-right-radius: 3px;
    border-bottom-right-radius: 3px;
    border-right:1px solid #caced9;
}


.menu li a{
    padding:10px 10px;
    border:1px solid #caced9;
    border-top-color:#d9d9d9;
    border-bottom-color:#cccccc;
    display:block;
    font-weight:700;
    color:#666666;
    box-shadow:0 1px 2px rgba(0,0,0,0.15);
    color:#595e6e;
    background: #FFFFFF;
    background: -webkit-linear-gradient(top, #FFFFFF 0%, #e7e8ef 100%);
	background: -moz-linear-gradient(top, #FFFFFF 0%, #e7e8ef 100%);
	background: -ms-linear-gradient(top, #FFFFFF 0%, #e7e8ef 100%);
	background: -o-linear-gradient(top, #FFFFFF 0%, #e7e8ef 100%);
	background: linear-gradient(top, #FFFFFF 0%, #e7e8ef 100%);
    filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#FFFFFF', endColorstr='#e7e8ef',GradientType=0 );
    position:relative;
    cursor:pointer;
    text-decoration:none;
    font-family:Helvetica, Arial;
    text-shadow: 0 1px 1px white;
    font-size: 08px;
    border-right: none;
}

.menu li a:active{
    color:#404040; 
}

.menu li a:hover {
    background: #FFFFFF;
    background: -webkit-linear-gradient(top, #fbfbfb 0%, #f0f0f0 100%);
	background: -moz-linear-gradient(top, #fbfbfb 0%, #f0f0f0 100%);
	background: -ms-linear-gradient(top, #fbfbfb 0%, #f0f0f0 100%);
	background: -o-linear-gradient(top, #fbfbfb 0%, #f0f0f0 100%);
	background: linear-gradient(top, #fbfbfb 0%, #f0f0f0 100%);
    filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#fbfbfb', endColorstr='#f0f0f0',GradientType=0 );
}

.menu li a:hover .notification {
    opacity:0.9;
}

.notification{
    height: 10px;
    -moz-border-radius: 20px;
    border-radius: 20px;
    width: 20px;
    padding: 3px;
    display: inline-block;
    color: #fff;
    text-shadow: 0px 1px 1px #707070;
    font-weight: bold;
    font-family: comic sans;
    text-align: center;
    font-size: 10px;
    top: -13px;
    right: 10px;
    position: absolute;
} 

.pink{
    background-color: #f4a1b2;
    border: 1px solid #cf5161;
    background: -webkit-linear-gradient(top, #f4a1b2 0%, #f56879 100%);
	background: -moz-linear-gradient(top, #f4a1b2 0%, #f56879 100%);
	background: -ms-linear-gradient(top, #f4a1b2 0%, #f56879 100%);
	background: -o-linear-gradient(top, #f4a1b2 0%, #f56879 100%);
	background: linear-gradient(top, #f4a1b2 0%, #f56879 100%);
    filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#f4a1b2', endColorstr='#f56879',GradientType=0 ); 
}



.blue{
    background-color: #91daf6;
    border: 1px solid #7cb7cd;
    background: -webkit-linear-gradient(top, #c2ecfa 0%, #71cef3 100%);
	background: -moz-linear-gradient(top, #c2ecfa 0%, #71cef3 100%);
	background: -ms-linear-gradient(top, #c2ecfa 0%, #71cef3 100%);
	background: -o-linear-gradient(top, #c2ecfa 0%, #71cef3 100%);
	background: linear-gradient(top, #c2ecfa 0%, #71cef3 100%);
    filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#c2ecfa', endColorstr='#71cef3',GradientType=0 );
}
#noti-count {
    top:-15px;
    right:-15px;
    background-color:blue;
    color:#fff;
    padding:4px;
    -webkit-border-radius: 30px;
    -moz-border-radius: 10px;
    border-radius: 30px;
    width:30px;
    height:30px;
    text-align:center;
}

</style> 

<Script>

function promptmessage()
{
var qpprover = '<%=request.getAttribute("qpprover") %>';
 if(qpprover == "L1") 
  {
   window.location ="/LMS2/assignmentApprovals.do?methodName=init";
 }
 else if(qpprover == "L2")
 { window.location ="/LMS2/assignmentsL2.do?methodName=init" ;
 }                 
}

$(document).ready(function() {
	$(".overlay-trigger").overlay({
		absolute: true,
		close: 'a.close-button1',
		closeOnClick: true,
		expose: { 
		        color: '#000000', 
		        loadSpeed: 200, 
		        opacity: 0.7 
		    },
		top: 'center',
		
		left: 'center'
	});
	
	
});


if ("" != '<%=request.getAttribute("warn")%>' && 'null' != '<%=request.getAttribute("warn")%>')
   alert('<%=request.getAttribute("warn")%>');
   
if ("" != '<%=request.getAttribute("RES")%>' && 'null' != '<%=request.getAttribute("RES")%>')
   alert('<%=request.getAttribute("RES")%>');
   
	function showExpiredDocs()
	{
		var count = <%=request.getAttribute("docCount") %>
		if(count != null && count!=0){
		$(function(){ // On DOM ready
    	$('#openOnLoad').click();
		});
		}
	} 
//showExpiredDocs();
</Script>
<link href="common/css/style.css" type="text/css" rel="stylesheet" />
 <div class="wrapper-body">
   <div class="box2">
      <div  class="content-upload" style="height: 600px ">
      
        <h1>Lead Management System</h1><br> 
                <!--<div class="menu_container">
    <ul class="menu">
        <li>
            <a href="#">Approval Notifications
                <span class="notification blue" onclick="return promptmessage();"><%=request.getAttribute("total")%></span>
     
         </a>
        </li>
        </ul>
        </div>
        
        
   -->
   <center><h4><i>Welcome to iLMS </i></h4></center>
   </div>
   
   </div>
   </div>
       



   

<!--<div id="popup1" class="pop-up1" >
	<a href="#" class="close-button1"></a>
	<div class="popup-inner">
	<p>Number of Documents to be expired in next 7 days : <%=request.getAttribute("docCount") %></p>
	</div>
</div>
<a href="#" class="overlay-trigger" rel="#popup1" id="openOnLoad"></a>

-->