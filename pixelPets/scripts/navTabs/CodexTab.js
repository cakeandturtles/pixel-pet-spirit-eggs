var codexTabHTML = "";
codexTabHTML += "<div id='Page1Button' class=\"navButtonCodex\" onclick='codexTabgotoPage1()'>PAGE 1</div>";
codexTabHTML += "<div id='Page2Button' class=\"navButtonCodex\" onclick='codexTabgotoPage2()'>PAGE 2</div>";
codexTabHTML += "<div id='Page3Button' class=\"navButtonCodex\" onclick='codexTabgotoPage3()'>PAGE 3</div>";
codexTabHTML += "<div id='Page4Button' class=\"navButtonCodex\" onclick='codexTabgotoPage4()'>PAGE 4</div>";
codexTabHTML += "<div id='Page5Button' class=\"navButtonCodex\" onclick='codexTabgotoPage5()'>PAGE 5</div>";
codexTabHTML += "<div id='Page6Button' class=\"navButtonCodex\" onclick='codexTabgotoPage6()'>PAGE 6</div>";
codexTabHTML += "<div id='Page7Button' class=\"navButtonCodex\" onclick='codexTabgotoPage7()'>PAGE 7</div>";
codexTabHTML += "<div id='Page8Button' class=\"navButtonCodex\" onclick='codexTabgotoPage8()'>PAGE 8</div>";
codexTabHTML += "<div id='Page9Button' class=\"navButtonCodex\" onclick='codexTabgotoPage9()'>PAGE 9</div>";
codexTabHTML += "<div class=\"clearer\"></div>";

codexTabHTML += "<div style='width:540px;position:relative;left:5%;margin-top:-5px;'>";
codexTabHTML += "<div class='codexImageContainer' onclick='codexTabShowPet0()'>"; //IMAGE 0
codexTabHTML += "<div id=\"codexImage0\" class='genericPetImage'></div></div>";
codexTabHTML += "<div class='codexImageContainer' onclick='codexTabShowPet1()'>"; //IMAGE 1
codexTabHTML += "<div id=\"codexImage1\" class='genericPetImage'></div></div>";
codexTabHTML += "<div class='codexImageContainer' onclick='codexTabShowPet2()'>"; //IMAGE 2
codexTabHTML += "<div id=\"codexImage2\" class='genericPetImage'></div></div>";
codexTabHTML += "<div class='codexImageContainer' onclick='codexTabShowPet3()'>"; //IMAGE 3
codexTabHTML += "<div id=\"codexImage3\" class='genericPetImage'></div></div>";
codexTabHTML += "<div class='codexImageContainer' onclick='codexTabShowPet4()'>"; //IMAGE 4
codexTabHTML += "<div id=\"codexImage4\" class='genericPetImage'></div></div>";
codexTabHTML += "<div class='codexImageContainer' onclick='codexTabShowPet5()'>"; //IMAGE 5
codexTabHTML += "<div id=\"codexImage5\" class='genericPetImage'></div></div>";
codexTabHTML += "<div class='codexImageContainer' onclick='codexTabShowPet6()'>"; //IMAGE 6
codexTabHTML += "<div id=\"codexImage6\" class='genericPetImage'></div></div>";
codexTabHTML += "<div class='codexImageContainer' onclick='codexTabShowPet7()'>"; //IMAGE 7
codexTabHTML += "<div id=\"codexImage7\" class='genericPetImage'></div></div>";
codexTabHTML += "<div class='codexImageContainer' onclick='codexTabShowPet0()'>"; //IMAGE 8
codexTabHTML += "<div id=\"codexImage8\" class='genericPetImage'></div></div>";

codexTabHTML += "</div>";

var codexTabSelectedTab;
var codexTabLastSelectedTab = null;
var codexCurrPagePets;

var codexTabScriptBegin = function(){
	//Local navigation/Local theme control!
	if (codexTabSelectedTab == null){
		codexTabSelectFirstTab();
	}
	tabThemeControl = codexTabThemeControl;
	
	currTabUpdate = codexTabSetUpBody;
	codexTabSetUpBody();
};

var codexTabScriptEnd = function(){
	tabThemeControl = function(){ };
};

/////////////////////////////////////////////////////////////////////////
var codexTabSelectFirstTab = function(){
	var pageIndex = 1;
	for (var i = 0; i < userCodex.length; i++){
		if (userCodex[i].inCodex){
			pageIndex = Math.floor((i+1) / 9); 
			break;
		}
	}
	codexTabSelectedTab = "Page" + (pageIndex+1);
};

var codexTabSetUpBody = function(){
	if (codexTabLastSelectedTab == null || codexTabLastSelectedTab != codexTabSelectedTab
		|| codexCurrPagePets == null){
		codexCurrPagePets = codexTabGetPagePets();
		codexTabLastSelectedTab = codexTabSelectedTab;
	}
	
	for (var i = 0; i < 9; i++){
		var tempCodexImageName = "codexImage" + i;
		if (i >= codexCurrPagePets.length){
			document.getElementById(tempCodexImageName).style.visibility = "hidden";
		}else if (!codexCurrPagePets[i].inCodex){
			document.getElementById(tempCodexImageName).className = "unknownPetImage";
		}else{
			document.getElementById(tempCodexImageName).className = "genericPetImage";
			codexCurrPagePets[i].ResetAnimation();
			codexCurrPagePets[i].UpdateAnimation(tempCodexImageName);
		}
		
		var codexPageButton = "Page"+(i+1)+"Button";
		var noPetsOnPage = true;
		var startIndex = i * 9;
		var endIndex = startIndex + 9;
		for (var j = startIndex; j < endIndex; j++){
			if (userCodex[j].inCodex){
				noPetsOnPage = false;
				break;
			}
		}
		if (noPetsOnPage){
			document.getElementById(codexPageButton).className = "navButtonCodexInactive";
			document.getElementById(codexPageButton).onclick = blankFunction;
		}
		else{ 
			document.getElementById(codexPageButton).className = "navButtonCodex";
			document.getElementById(codexPageButton).onclick = eval("codexTabgotoPage"+(i+1)+";");
		}
	}
};

var blankFunction = function(){
};

var codexTabThemeControl = function(){
	setNavButton(codexTabSelectedTab);
};

//PET NOTIFY INFOGRAPHIC///////////////////////////////////////////////////////
var codexTabInfoNotify = function(codexPet){
	nameNotification = true;
	
	var note = document.getElementById("notificationBody");
	var noteHTML = "<div class='genericPetImageContainer' style='cursor:default;margin-top:5px;'>";
	if (codexPet != null)
		noteHTML += "<div id=\"codexTabPetInfoNotify\" class='genericPetImage'></div>";
	else noteHTML += "<div id=\"codexTabPetInfoNotify\" class='unknownPetImage'></div>";
	noteHTML += "</div><br/>";
	noteHTML += "<div style='text-align:left;'>";
	noteHTML += "<b>Species:</b> "
	if (codexPet == null)
		noteHTML += "Mystery";
	else if (codexPet.petForm == "EGG")
		noteHTML += codexPet.eggSpecies;
	else noteHTML += codexPet.species;
	noteHTML += "<br/><b style='margin-left:1px;'> &nbsp;&nbsp;&nbsp;&nbsp;Level: </b>";
	if (codexPet == null)
		noteHTML += "???";
	else noteHTML += codexPet.petForm;
	noteHTML += "<br/><div style='clear:both;'></div>";
	noteHTML += "<div style='float:left;width:72px;'><b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Info:</b></div>";
	if (codexPet != null){
		noteHTML += "<div style='float:left;width:236px;margin-left:7px;'>" + codexPet.codexDescription + "</div>";
	}else{
		noteHTML += "<div style='float:left;width:236px;margin-left:7px;'>" + "It is a mystery." + "</div>";
	}
	noteHTML += "</div>";
	noteHTML += "<div style='clear:both;'></div>";
	noteHTML += "<br/><div class='actionButton' style='float:none;width:80px;margin:0px auto; margin-top:10px;' onclick='closeNotification();'>Okay</div>";
	note.innerHTML = noteHTML;
		
	openNotification();
	if (codexPet != null)
		codexPet.UpdateAnimation("codexTabPetInfoNotify");
};

var codexTabShowPet0 = function(){
	if (codexCurrPagePets[0].inCodex)
		codexTabInfoNotify(codexCurrPagePets[0]);
	else codexTabInfoNotify(null);
};

var codexTabShowPet1 = function(){
	if (codexCurrPagePets[1].inCodex)
		codexTabInfoNotify(codexCurrPagePets[1]);
	else codexTabInfoNotify(null);
};

var codexTabShowPet2 = function(){
	if (codexCurrPagePets[2].inCodex)
		codexTabInfoNotify(codexCurrPagePets[2]);
	else codexTabInfoNotify(null);
};

var codexTabShowPet3 = function(){
	if (codexCurrPagePets[3].inCodex)
		codexTabInfoNotify(codexCurrPagePets[3]);
	else codexTabInfoNotify(null);
};

var codexTabShowPet4 = function(){
	if (codexCurrPagePets[4].inCodex)
		codexTabInfoNotify(codexCurrPagePets[4]);
	else codexTabInfoNotify(null);
};

var codexTabShowPet5 = function(){
	if (codexCurrPagePets[5].inCodex)
		codexTabInfoNotify(codexCurrPagePets[5]);
	else codexTabInfoNotify(null);
};

var codexTabShowPet6 = function(){
	if (codexCurrPagePets[6].inCodex)
		codexTabInfoNotify(codexCurrPagePets[6]);
	else codexTabInfoNotify(null);
};

var codexTabShowPet7 = function(){
	if (codexCurrPagePets[7].inCodex)
		codexTabInfoNotify(codexCurrPagePets[7]);
	else codexTabInfoNotify(null);
};

//PAGE CONTROL/////////////////////////////////////////////////////////////////
var codexTabGetPagePets = function(){
	var pagePets = new Array();
	var startIndex = 0;
	if (codexTabSelectedTab == "Page2")
		startIndex = 9;
	else if (codexTabSelectedTab == "Page3")
		startIndex = 18;
	else if (codexTabSelectedTab == "Page4")
		startIndex = 27;
	else if (codexTabSelectedTab == "Page5")
		startIndex = 36;
	else if (codexTabSelectedTab == "Page6")
		startIndex = 45;
	else if (codexTabSelectedTab == "Page7")
		startIndex = 54;
	else if (codexTabSelectedTab == "Page8")
		startIndex = 63;
	else if (codexTabSelectedTab == "Page9")
		startIndex = 72;
		
	for (var i = 0; i < 9; i++){
		if (i + startIndex >= userCodex.length)
			break;
		pagePets.push(userCodex[i + startIndex]);
	}
	return pagePets;
};

var codexTabgotoPage1 = function(){
	codexTabSelectedTab = "Page1";
	codexTabSetUpBody();
	setTheme();
};

var codexTabgotoPage2 = function(){
	codexTabSelectedTab = "Page2";
	codexTabSetUpBody();
	setTheme();
};

var codexTabgotoPage3 = function(){
	codexTabSelectedTab = "Page3";
	codexTabSetUpBody();
	setTheme();
};

var codexTabgotoPage4 = function(){
	codexTabSelectedTab = "Page4";
	codexTabSetUpBody();
	setTheme();
};

var codexTabgotoPage5 = function(){
	codexTabSelectedTab = "Page5";
	codexTabSetUpBody();
	setTheme();
};

var codexTabgotoPage6 = function(){
	codexTabSelectedTab = "Page6";
	codexTabSetUpBody();
	setTheme();
};

var codexTabgotoPage7 = function(){
	codexTabSelectedTab = "Page7";
	codexTabSetUpBody();
	setTheme();
};

var codexTabgotoPage8 = function(){
	codexTabSelectedTab = "Page8";
	codexTabSetUpBody();
	setTheme();
};

var codexTabgotoPage9 = function(){
	codexTabSelectedTab = "Page9";
	codexTabSetUpBody();
	setTheme();
};