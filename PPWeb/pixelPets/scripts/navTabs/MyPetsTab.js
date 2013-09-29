var myPetsTabHTML = "";
myPetsTabHTML += "<div id='Pet1Button' class=\"navButton\" style=\"background:#ffffff; float:left;padding-left:20px;padding-right:20px;\" onclick='myPetsTabgotoPet1()'>PET 1</div>";
myPetsTabHTML += "<div id='Pet2Button' class=\"navButton\" style=\"float:left;padding-left:20px;padding-right:20px;\" onclick='myPetsTabgotoPet2()'>PET 2</div>";
myPetsTabHTML += "<div id='Pet3Button' class=\"navButton\" style=\"float:left;padding-left:20px;padding-right:20px;\" onclick='myPetsTabgotoPet3()'>PET 3</div>";
myPetsTabHTML += "<div id='Pet4Button' class=\"navButton\" style=\"float:left;padding-left:20px;padding-right:20px;\" onclick='myPetsTabgotoPet4()'>PET 4</div>";
myPetsTabHTML += "<div class=\"clearer\"></div><br/>";
myPetsTabHTML += "<div id='myPetsTabNameSpecies' style=\"float:left;cursor:pointer;\" onclick='myPetsTabNameNotify(false);'>??? the Mysterious Egg</div>				<div id='myPetsTabLevel' style=\"float:right;\">Lvl. ???</div>";
myPetsTabHTML += "<div class=\"clearer\"></div><br/>";
myPetsTabHTML += "<div class='genericPetImageContainer'>";
myPetsTabHTML += "<div id=\"myPetsTabPetImage\" class='genericPetImage' onclick='myPetsTabPetPet()'></div>";
myPetsTabHTML += "</div>";
myPetsTabHTML += "<div style='width:180px;margin:8px auto;position:relative;left:-10px;'>";
myPetsTabHTML += "<div style='float:left;'>Exp:&nbsp;</div>";
myPetsTabHTML += "<div style='float:left; width:128px;height:16px;background:#000000;'>"
myPetsTabHTML += "<div id='myPetsTabPetEXP' style='width:100px;height:14px;background:#00ff00;margin:1px;'></div>";
myPetsTabHTML += "</div></div>";
myPetsTabHTML += "<div class=\"clearer\"></div>";
myPetsTabHTML += "<div style='width:200px;margin:8px auto;position:relative;left:-14px;'>";
myPetsTabHTML += "<div style='float:left;'>Mood:&nbsp;</div>";
myPetsTabHTML += "<div style='float:left; width:128px; height:16px;background:#000000;'>";
myPetsTabHTML += "<div id='myPetsTabPetMood' style='width:100px;height:14px;background:#ff00ff;margin:1px;'></div>";
myPetsTabHTML += "</div><div class=\"clearer\"></div><br/>";
myPetsTabHTML += "<div id=\"myPetsTabPetDescription\" style=\"height:32px;\">It moves around a lot.<br/>It must be close to hatching!</div><br/>";

var myPetsTabSelectedTab;
var myPetsTabUpdate;
var myPetsTabPet;

var myPetsTabScriptBegin = function(){
	//Local navigation/Local theme control!
	if (myPetsTabSelectedTab == null)
		myPetsTabSelectedTab = "Pet1";
	tabThemeControl = myPetsTabThemeControl;
	
	document.getElementById("myPetsTabPetImage").style.backgroundImage = "url('pixelPets/images/eggs_and_pets_big.png')";
	
	currTabUpdate = myPetsTabUpdate;
	myPetsTabUpdate();
};

var myPetsTabScriptEnd = function(){
	currTabUpdate = function(){};
	tabThemeControl = function(){};
};

var myPetsTabUpdate = function(){
	myPetsTabUpdateTabs();
	myPetsTabUpdatePet();
	myPetsTabSetUpPetBody();
	myPetsTabUpdateActionButtons();
};

////////////////////////////////////////////////////////////////////////////////////////////////////
var myPetsTabUpdatePet = function(){
	myPetsTabPet = myPetsTabGetSelectedPet();
	if (doTitleNotify)
		myPetsTabPet.Update("myPetsTabPetImageNotify");
	else myPetsTabPet.Update("myPetsTabPetImage");
	
	if (myPetsTabPet.formChange){
		myPetsTabPet.formChange = false;
		myPetsTabNameNotify(true);
	}
};

var myPetsTabSetUpPetBody = function(){
	var nameAndSpecies = document.getElementById("myPetsTabNameSpecies");
	nameAndSpecies.innerHTML = myPetsTabPet.getNameAndSpecies();
	if (myPetsTabPet.petForm == "EGG"){
		nameAndSpecies.style.cursor = "default";
		nameAndSpecies.onclick = function(){};
	}else{
		nameAndSpecies.style.cursor = "pointer";
		nameAndSpecies.onclick = function(){ myPetsTabNameNotify(false); }
	}

	document.getElementById("myPetsTabPetEXP").style.width = myPetsTabPet.GetExpRatio()+"px";
	document.getElementById("myPetsTabPetMood").style.width = myPetsTabPet.GetMoodRatio()+"px";
	document.getElementById("myPetsTabLevel").innerHTML = "Lvl. " + myPetsTabPet.petForm
	document.getElementById("myPetsTabPetDescription").innerHTML = myPetsTabPet.currentDescription;
};

var myPetsTabNameNotify = function(justHatched){
	doTitleNotify = true;
	var defaultName = myPetsTabPet.name;
	
	var note = document.getElementById("notificationBody");
	var noteHTML = "<div class='genericPetImageContainer'>";
	noteHTML += "<div id=\"myPetsTabPetImageNotify\" class='genericPetImage' style=\"background-image:url('pixelPets/images/eggs_and_pets_big.png')\" onclick='myPetsTabPetPet()'></div>";
	noteHTML += "</div><br/>";
	if (justHatched){
		noteHTML += "A " + myPetsTabPet.species + " just hatched from the egg!";
		noteHTML += "<br/><br/>What would you like to name your new " + myPetsTabPet.species + "?";
		titleKeeper = " " + myPetsTabPet.species + " has hatched!    ";
		defaultName = myPetsTabPet.species;
		hasCodex = true;
	}else{
		noteHTML += "Do you want to rename " + myPetsTabPet.name + "?";
		titleKeeper = " Rename " + myPetsTabPet.name + "?    ";
	}
	
	noteHTML += "<br/><br/><input id='myPetsTabNameForm' type='text' value='"+defaultName+"' maxlength='12' onKeyPress='myPetsTabNameEnter(event);' />";
	noteHTML += "<br/><div class='actionButton' style='float:none;width:80px;margin:0px auto;margin-top:10px;' onclick='myPetsTabNamePet();'>Name Pet</div>"
	note.innerHTML = noteHTML;
	
	openNotification();
	myPetsTabPet.UpdateAnimation("myPetsTabPetImageNotify");
};

//ACTION BUTTONS////////////////////////////////////////////////////////////////////////////////////
var myPetsTabNameEnter = function(e){
	var code = (e.keyCode ? e.keyCode : e.which);
	if (code == 13) //ENTER KEY
		myPetsTabNamePet();
};
var myPetsTabNamePet = function(){
	var petName = document.getElementById("myPetsTabNameForm").value;
	myPetsTabPet.name = petName;
	closeNotification();
};

var myPetsTabUpdateActionButtons = function(){	
};

var myPetsTabPetPet = function(){
	myPetsTabPet.frameCount = myPetsTabPet.frameCountLimit;
	myPetsTabPet.UpdateAnimation("myPetsTabPetImage");
	if (myPetsTabPet != null){
		myPetsTabPet.expTimer++;
		myPetsTabPet.mood+=2;
	}
};


////////////////////////////////////////////////////////////////////////////////////////////////////

var myPetsTabThemeControl = function(){
	setNavButton(myPetsTabSelectedTab);
};

var myPetsTabGetSelectedPet = function(){
	return userPets[myPetsTabGetPetIndex()];
}

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

/////////////////NAV SPECIFIC//////////////////////////////////////////
var myPetsTabUpdateTabs = function(){
	var counter = 0;
	for (var i = 0; i < 4; i++){
		if (i >= userPets.length){
			document.getElementById("Pet"+(i+1)+"Button").style.visibility = 'hidden';
		}else{ 
			document.getElementById("Pet"+(i+1)+"Button").style.visibility = 'visible';
			counter++;
		}
	}
	if (counter <= 1)
		document.getElementById("Pet1Button").style.visibility = "hidden";
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