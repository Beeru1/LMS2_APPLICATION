// capLock.js

// newFunction
function checkCapsLock(oControl, e ) {
	if (oControl.value.length!=null  && oControl.value.length == 0)
	{
		var myKeyCode=0;
		var myShiftKey=false;
		var myMsg='Caps Lock is On.';
	
		// Internet Explorer 4+
		if ( document.all ) {
			myKeyCode=e.keyCode;
			myShiftKey=e.shiftKey;
	
		// Netscape 4
		} else if ( document.layers ) {
			myKeyCode=e.which;
			myShiftKey=( myKeyCode == 16 ) ? true : false;
	
		// Netscape 6
		} else if ( document.getElementById ) {
			myKeyCode=e.which;
			myShiftKey=( myKeyCode == 16 ) ? true : false;
		}
	
		// Upper case letters are seen without depressing the Shift key, therefore Caps Lock is on
		if ( ( myKeyCode >= 65 && myKeyCode <= 90 ) && !myShiftKey ) {
			alert( myMsg );
	
		// Lower case letters are seen while depressing the Shift key, therefore Caps Lock is on
		} else if ( ( myKeyCode >= 97 && myKeyCode <= 122 ) && myShiftKey ) {
			alert( myMsg );
		}
	}
}