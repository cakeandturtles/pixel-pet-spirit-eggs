var selectedNav;
var hasCodex = false;

var currTabUpdate = function(){};
var tabThemeControl = function(){};
var userPets = [
	new PixelPet(Sprout)
];

window.onload = function(){
	purpleTheme();
	gotoMyPets();
	setInterval(function(){mainUpdate()},60);
	mainUpdate();
};

var mainUpdate = function(){
	currTabUpdate();
	titleNotify();
	updateNavButtonVisibility();
};

//TITLE NOTIFICATION////////////////////////////////////////////////
var titleKeeper = "Pixel Pets!";
var doTitleNotify = false;
var titleCounter = 0;
var titleNotify = function(){
	if (!doTitleNotify){ 
		document.title = "Pixel Pets!";
		titleCounter = 0;
		return;
	}
	titleCounter++;
	if (titleCounter % 4 != 0) return;
	
	titleKeeper = titleKeeper.substring(1) + titleKeeper.substring(0, 1);
	document.title = titleKeeper;
};


//AUXILLARY FUNCTIONS///////////////////////////////////////////////////////////////////////////////
//Warning, may return array containing different tag types?
//Modified from http://stackoverflow.com/questions/3808808/how-to-get-element-by-class-in-javascript
var getElementsByClass = function(matchClass){ 
	var elems = document.getElementsByTagName('*');
	var elemsOfClass = new Array();
	for (var i = 0; i < elems.length; i++){
		if ((' ' + elems[i].className + ' ').indexOf(' ' + matchClass + ' ') > -1){
			elemsOfClass.push(elems[i]);
		}
	}
	return elemsOfClass;
};

//NOTIFICATION CONTROL (from http://stackoverflow.com/questions/12411136/javascript-modal-dialog)
var openNotification = function(){
	var el = document.getElementById("overlay");
	var el2 = document.getElementById("overlay2");
	el.style.visibility = "visible";
	el2.style.visibility = "visible";
}

var closeNotification = function() {
     document.getElementById("overlay").style.visibility = 'hidden';
	 document.getElementById("overlay2").style.visibility = 'hidden';
	 doTitleNotify = false;
}