
<!-- Begin
//scroller width
var swidth=830

//scroller height
var sheight=25


//scroller's speed;
var sspeed=8

var wholemessage=''

wholemessage='<div style="margin-top:2px;" ><font face=arial size=2 color="white"><B>Welcome To Knowledge Management Portal.</B></font></font></DIV>'
function setMessage(message, sliderId){
wholemessage=message
return ca_start();
}

function ca_start(){
return docascroll();
if (document.all) return
if (document.getElementById){
document.getElementById("slider").style.visibility="show"
ns6marquee(document.getElementById('slider'))

}
else if(document.layers){
document.slider1.visibility="show"
ns4marquee(document.slider1.document.slider2)
}

}
function ns4marquee(whichlayer){
ns4layer=eval(whichlayer)
ns4layer.document.write(wholemessage)
ns4layer.document.close()
sizeup=ns4layer.document.height
ns4layer.top-=sizeup
ns4slide()
}
function ns4slide(){
if (ns4layer.top>=sizeup*(-1)){
ns4layer.top-=sspeed
setTimeout("ns4slide()",100)
}
else{
ns4layer.top=sheight
ns4slide()
}
}
function ns6marquee(whichdiv){
ns6div=eval(whichdiv)
ns6div.innerHTML=wholemessage
ns6div.style.top=sheight

sizeup=sheight
ns6slide()
}
function ns6slide(){


if (parseInt(ns6div.style.top)>=sizeup*(-1)){
ns6div.style.top=parseInt(ns6div.style.top)-sspeed

setTimeout("ns6slide()",100)
}
else{
ns6div.style.top=sheight
ns6slide()
}
}
//  End -->
function docascroll() {
	var sliderInnerHtml='';
	sliderInnerHtml=sliderInnerHtml+'<span style="borderWidth:0; width:98%; height:25;"><ilayer width=98% height=25 name="slider1" bgcolor="#FFFFFF" visibility=hide><layer name="slider2" onMouseover="sspeed=0;" onMouseout="sspeed=6"></layer></ilayer>';
	if (document.all){
		sliderInnerHtml=sliderInnerHtml+'<marquee id="ieslider" scrollAmount=3 width="100%" height=20 direction=left style="background-image:url(./images/user-name.gif); background-repeat:repeat-x; padding-left:15px;">'
		sliderInnerHtml=sliderInnerHtml+wholemessage;
//		ieslider.onmouseover=new Function("ieslider.scrollAmount=0");
//		ieslider.onmouseout=new Function("if (document.readyState=='complete') ieslider.scrollAmount=3");
		sliderInnerHtml=sliderInnerHtml+'</marquee>';
	}
	sliderInnerHtml=sliderInnerHtml+'</span>';
	return sliderInnerHtml
}

// end -->



