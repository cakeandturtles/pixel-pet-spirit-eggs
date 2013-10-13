//NAVIGATION BUTTON FUNCTIONS///////////////////////////////////////////////////////////////////////
var resetNavButtons = function(){
	var navButtons = getElementsByClass("navButton");
	for (var i = 0; i < navButtons.length; i++){
		navButtons[i].style.background = lightColor;
		navButtons[i].style.color = fontColor;
	}
	navButtons = getElementsByClass("navButtonCodex");
	for (var i = 0; i < navButtons.length; i++){
		navButtons[i].style.background = lightColor;
		navButtons[i].style.color = fontColor;
		navButtons[i].style.borderColor = "#000000";
	}
	navButtons = getElementsByClass("navButtonCodexInactive");
	for (var i = 0; i < navButtons.length; i++){
		navButtons[i].style.background = lightColor;
		navButtons[i].style.color = darkColor;
		navButtons[i].style.borderColor = darkColor;
	}
};

var setNavButton = function(navName){
	if (navName == null) return;
	document.getElementById(navName + "Button").style.background = "#ffffff";
	document.getElementById(navName + "Button").style.color = "#000000";
};
var updateNavButtonVisibility = function(){
	if (hasCodex)
		document.getElementById("codexButton").style.visibility = "visible";
	else document.getElementById("codexButton").style.visibility = "hidden";
	
	if (!hasCodex)
		document.getElementById("myPetsButton").style.visibility = "hidden";
	else document.getElementById("myPetsButton").style.visibility = "visible";
};

//THEME CONTROL/////////////////////////////////////////////////////////////////////////////////////
var darkColor = "#aaaaaa";
var lightColor = "#dddddd";
var fontColor = "#000000";
var blackOutThemeBorders = function(){
	document.getElementById("purpleTheme").style.borderColor = "#000000";
	document.getElementById("orangeTheme").style.borderColor = "#000000";
	document.getElementById("blueTheme").style.borderColor = "#000000";
	document.getElementById("redTheme").style.borderColor = "#000000";
	document.getElementById("greenTheme").style.borderColor = "#000000";
	document.getElementById("greyTheme").style.borderColor = "#000000";
};

var setTheme = function(){
	resetNavButtons();
	setNavButton(selectedNav);
	tabThemeControl();
	document.getElementById("ppMain").style.background = lightColor;
	document.getElementsByTagName("body")[0].style.background = darkColor;
	document.getElementsByTagName("body")[0].style.color = fontColor;
	document.getElementById("overlay").style.background = darkColor;
	document.getElementById("notification").style.background = lightColor;
	document.getElementById("notification").style.color = fontColor;
};

var darkPurple = "#ceb6c3";
var lightPurple = "#f0e9ec";
var purpleFont = "#000000";
var purpleTheme = function(){
	darkColor = darkPurple;
	lightColor = lightPurple;
	fontColor = purpleFont;
	blackOutThemeBorders();
	document.getElementById("purpleTheme").style.borderColor = "#ffffff";
	setTheme();
};

var darkOrange = "#fae161";
var lightOrange = "#fffb9a";
var orangeFont = "#000000";
var orangeTheme = function(){
	darkColor = darkOrange;
	lightColor = lightOrange;
	fontColor = orangeFont;
	blackOutThemeBorders();
	document.getElementById("orangeTheme").style.borderColor = "#ffffff";
	setTheme();
};

var darkBlue = "#92b6db";
var lightBlue = "#c7e1eb";
var blueFont = "#000000";
var blueTheme = function(){
	darkColor = darkBlue;
	lightColor = lightBlue;
	fontColor = blueFont;
	blackOutThemeBorders();
	document.getElementById("blueTheme").style.borderColor = "#ffffff";
	setTheme();
};

var darkRed = "#faaa70";
var lightRed = "#ffdd9e";
var redFont = "#000000";
var redTheme = function(){
	darkColor = darkRed;
	lightColor = lightRed;
	fontColor = redFont;
	blackOutThemeBorders();
	document.getElementById("redTheme").style.borderColor = "#ffffff";
	setTheme();
};

var darkGreen = "#a4e676";
var lightGreen = "#c6ff9f";
var greenFont = "#000000";
var greenTheme = function(){
	darkColor = darkGreen;
	lightColor = lightGreen;
	fontColor = greenFont;
	blackOutThemeBorders();
	document.getElementById("greenTheme").style.borderColor = "#ffffff";
	setTheme();
};

var darkGrey = "#0f1613";
var lightGrey = "#31493c";
var greyFont = "#ffffff";
var greyTheme = function(){
	darkColor = darkGrey;
	lightColor = lightGrey;
	fontColor = greyFont;
	blackOutThemeBorders();
	document.getElementById("greyTheme").style.borderColor = "#ffffff";
	setTheme();
};