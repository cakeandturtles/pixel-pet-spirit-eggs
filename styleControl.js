window.onload = function(){
	purpleTheme();
	gotoMyPets();
};

//NAVIGATION BUTTON FUNCTIONS///////////////////////////////////////////////////////////////////////
var selectedNav = "myPets";
var resetNavButtons = function(){
	document.getElementById("myPetsButton").style.background = lightColor;
	document.getElementById("myPetsButton").style.color = fontColor;
	document.getElementById("trainerButton").style.background = lightColor;
	document.getElementById("trainerButton").style.color = fontColor;
	document.getElementById("inventoryButton").style.background = lightColor;
	document.getElementById("inventoryButton").style.color = fontColor;
	document.getElementById("gardenButton").style.background = lightColor;
	document.getElementById("gardenButton").style.color = fontColor;
	document.getElementById("codexButton").style.background = lightColor;
	document.getElementById("codexButton").style.color = fontColor;
	
	switch(selectedNav){
		case "myPets":
			document.getElementById("myPetsButton").style.background = "#ffffff";
			document.getElementById("myPetsButton").style.color = "#000000";
			break;
		case "trainer":
			document.getElementById("trainerButton").style.background = "#ffffff";
			document.getElementById("trainerButton").style.color = "#000000";
			break;
		case "inventory":
			document.getElementById("inventoryButton").style.background = "#ffffff";
			document.getElementById("inventoryButton").style.color = "#000000";
			break;
		case "garden":
			document.getElementById("gardenButton").style.background = "#ffffff";
			document.getElementById("gardenButton").style.color = "#000000";
			break;
		case "codex":
			document.getElementById("codexButton").style.background = "#ffffff";
			document.getElementById("codexButton").style.color = "#000000";
			break;
		default: break;
	}
};

var gotoMyPets = function(){
	selectedNav = "myPets";
	resetNavButtons();
};

var gotoTrainer = function(){
	selectedNav = "trainer";
	resetNavButtons();
};

var gotoInventory = function(){
	selectedNav = "inventory";
	resetNavButtons();
};

var gotoGarden = function(){
	selectedNav = "garden";
	resetNavButtons();
};

var gotoCodex = function(){
	selectedNav = "codex";
	resetNavButtons();
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
	document.getElementById("main").style.background = lightColor;
	document.getElementsByTagName("body")[0].style.background = darkColor;
	document.getElementsByTagName("body")[0].style.color = fontColor;
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