var myPetsTabHTML = "";
myPetsTabHTML += "<div id='Pet1Button' class=\"navButton\" style=\"background:#ffffff; float:left;padding-left:20px;padding-right:20px;\" onclick='myPetsTabgotoPet1()'>PET 1</div>";
myPetsTabHTML += "<div id='Pet2Button' class=\"navButton\" style=\"float:left;padding-left:20px;padding-right:20px;\" onclick='myPetsTabgotoPet2()'>PET 2</div>";
myPetsTabHTML += "<div id='Pet3Button' class=\"navButton\" style=\"float:left;padding-left:20px;padding-right:20px;\" onclick='myPetsTabgotoPet3()'>PET 3</div>";
myPetsTabHTML += "<div id='Pet4Button' class=\"navButton\" style=\"float:left;padding-left:20px;padding-right:20px;\" onclick='myPetsTabgotoPet4()'>PET 4</div>";
myPetsTabHTML += "<div class=\"clearer\"></div><br/>";
myPetsTabHTML += "<div id='myPetsTabNameSpecies' style=\"float:left;\">??? the Mysterious Egg</div>				<div style=\"float:right;\">Lvl. ???</div>";
myPetsTabHTML += "<div class=\"clearer\"></div><br/>";
myPetsTabHTML += "<div class='genericPetImageContainer'>";
myPetsTabHTML += "<div id=\"myPetsTabPetImage\" class='genericPetImage'></div>";
myPetsTabHTML += "</div><br/>";
myPetsTabHTML += "<div id=\"petDescription\">It moves around a lot.<br/>It must be close to hatching!</div><br/>";
myPetsTabHTML += "<div id=\"myPetsTabActionButtons\" style=\"display: inline-block;\">";
myPetsTabHTML += "<div class=\"actionButton\">Rub Egg</div>";
myPetsTabHTML += "<div class=\"actionButton\">Shake Egg</div>";
myPetsTabHTML += "<div class=\"actionButton\">Talk to Egg</div>";
myPetsTabHTML += "</div>";
				
var myPetsTabSelectedTab;
var myPetsTabAnimation;
var myPetsTabPet;

var myPetsTabScriptBegin = function(){
	//Local navigation/Local theme control!
	if (myPetsTabSelectedTab == null)
		myPetsTabSelectedTab = "Pet1";
	tabThemeControl = myPetsTabThemeControl;
	
	document.getElementById("myPetsTabPetImage").style.backgroundImage = "url('pixelPets/images/eggs_and_pets_big.png')";
	myPetsTabSetUpPetBody();
	myPetsTabAnimation = setInterval(function(){main()},750);
	
	var main = function(){
		myPetsTabPet.UpdateAnimation("myPetsTabPetImage");
	};
};

var myPetsTabScriptEnd = function(){
	tabThemeControl = function(){ };
	myPetsTabAnimation = window.clearInterval(myPetsTabAnimation);
};

var myPetsTabThemeControl = function(){
	setNavButton(myPetsTabSelectedTab);
};

var myPetsTabGetPetIndex = function(){
	switch (myPetsTabSelectedTab){
		case "Pet1":
			return 0;
		case "Pet2":
			return 1;
		case "Pet3":
			return 2;
		case "Pet4":
			return 3;
		default: return 0;
	}
};

var myPetsTabSetUpPetBody = function(){
	myPetsTabPet = userPets[myPetsTabGetPetIndex()];
	document.getElementById("myPetsTabNameSpecies").innerHTML = myPetsTabPet.getNameAndSpecies();
	myPetsTabPet.UpdateAnimation("myPetsTabPetImage");
};

var myPetsTabgotoPet1 = function(){
	myPetsTabSelectedTab = "Pet1";
	myPetsTabSetUpPetBody();
	setTheme();
};

var myPetsTabgotoPet2 = function(){
	myPetsTabSelectedTab = "Pet2";
	myPetsTabSetUpPetBody();
	setTheme();
};

var myPetsTabgotoPet3 = function(){
	myPetsTabSelectedTab = "Pet3";
	myPetsTabSetUpPetBody();
	setTheme();
};

var myPetsTabgotoPet4 = function(){
	myPetsTabSelectedTab = "Pet4";
	myPetsTabSetUpPetBody();
	setTheme();
};