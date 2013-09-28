var myPetsTabHTML = "";
myPetsTabHTML += "<div id='Pet1Button' class=\"navButton\" style=\"background:#ffffff; float:left;padding-left:20px;padding-right:20px;\" onclick='myPetsTabgotoPet1()'>PET 1</div>";
myPetsTabHTML += "<div id='Pet2Button' class=\"navButton\" style=\"float:left;padding-left:20px;padding-right:20px;\" onclick='myPetsTabgotoPet2()'>PET 2</div>";
myPetsTabHTML += "<div id='Pet3Button' class=\"navButton\" style=\"float:left;padding-left:20px;padding-right:20px;\" onclick='myPetsTabgotoPet3()'>PET 3</div>";
myPetsTabHTML += "<div id='Pet4Button' class=\"navButton\" style=\"float:left;padding-left:20px;padding-right:20px;\" onclick='myPetsTabgotoPet4()'>PET 4</div>";
myPetsTabHTML += "<div class=\"clearer\"></div><br/>";
myPetsTabHTML += "<div id='myPetsTabNameSpecies' style=\"float:left;cursor:pointer;\" onclick='myPetsTabNameNotify(false);'>??? the Mysterious Egg</div>				<div id='myPetsTabLevel' style=\"float:right;\">Lvl. ???</div>";
myPetsTabHTML += "<div class=\"clearer\"></div><br/>";
myPetsTabHTML += "<div class='genericPetImageContainer'>";
myPetsTabHTML += "<div id=\"myPetsTabPetImage\" class='genericPetImage'></div>";
myPetsTabHTML += "</div><br/>";
myPetsTabHTML += "<div id=\"myPetsTabPetDescription\" style=\"height:32px;\">It moves around a lot.<br/>It must be close to hatching!</div><br/>";
myPetsTabHTML += "<div id=\"myPetsTabActionButtons\" style=\"display: inline-block;\">";
myPetsTabHTML += "<div id=\"myPetsTabRubPet\" class=\"actionButton\" onclick='myPetsTabRubPet(this)'>Rub Egg</div>";
myPetsTabHTML += "<div id=\"myPetsTabShakePet\"  class=\"actionButton\" onclick='myPetsTabShakePet(this)'>Shake Egg</div>";
myPetsTabHTML += "<div id=\"myPetsTabTalkPet\" class=\"actionButton\" onclick='myPetsTabTalkPet(this)'>Talk to Egg</div>";
myPetsTabHTML += "</div>";

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

	document.getElementById("myPetsTabLevel").innerHTML = "Lvl. " + myPetsTabPet.petForm
	document.getElementById("myPetsTabPetDescription").innerHTML = myPetsTabPet.currentDescription;
};

var myPetsTabNameNotify = function(justHatched){
	doTitleNotify = true;
	var defaultName = myPetsTabPet.name;
	
	var note = document.getElementById("notificationBody");
	var noteHTML = "<div class='genericPetImageContainer'>";
	noteHTML += "<div id=\"myPetsTabPetImageNotify\" class='genericPetImage' style=\"background-image:url('pixelPets/images/eggs_and_pets_big.png')\"></div>";
	noteHTML += "</div><br/>";
	if (justHatched){
		noteHTML += "A " + myPetsTabPet.species + " just hatched from the egg!";
		noteHTML += "<br/><br/>What would you like to name your new " + myPetsTabPet.species + "?";
		titleKeeper = " " + myPetsTabPet.species + " has hatched!    ";
		defaultName = myPetsTabPet.species;
		//hasInventory = true;
		hasGarden = true;
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
	if (myPetsTabRubPetTimer > 0) myPetsTabRubPetTimer--;
	else document.getElementById("myPetsTabRubPet").className = "actionButton";

	if (myPetsTabShakePetTimer > 0) myPetsTabShakePetTimer--;
	else document.getElementById("myPetsTabShakePet").className = "actionButton";
	
	if (myPetsTabTalkPetTimer > 0) myPetsTabTalkPetTimer--;
	else document.getElementById("myPetsTabTalkPet").className = "actionButton";
	
	if (myPetsTabPet.petForm == "EGG"){
		document.getElementById("myPetsTabRubPet").innerHTML = "Rub Egg";
		document.getElementById("myPetsTabShakePet").innerHTML = "Shake Egg";
		document.getElementById("myPetsTabTalkPet").innerHTML = "Talk to Egg";
	}else{
		document.getElementById("myPetsTabRubPet").innerHTML = "Pet Pet";
		document.getElementById("myPetsTabShakePet").innerHTML = "Shake Pet";
		document.getElementById("myPetsTabTalkPet").innerHTML = "Talk to Pet";
	}
};

var myPetsTabRubPetTimer = 0;
var myPetsTabRubPet = function(button){
	if (myPetsTabRubPetTimer > 0) return;
	
	if (myPetsTabPet != null && myPetsTabPet.petForm == "EGG"){
		myPetsTabPet.frameCount++;
		myPetsTabPet.UpdateAnimation("myPetsTabPetImage");
		myPetsTabPet.timeEggHatched--;
	}
	//deactivate
	button.className = "actionButtonDisabled";
	myPetsTabRubPetTimer = 5;
};

var myPetsTabShakePetTimer = 0;
var myPetsTabShakePet = function(button){
	if (myPetsTabShakePetTimer > 0) return;

	if (myPetsTabPet != null && myPetsTabPet.petForm == "EGG"){
		myPetsTabPet.frameCount = myPetsTabPet.frameCountLimit;
		myPetsTabPet.UpdateAnimation("myPetsTabPetImage");
		if (myPetsTabPet.frameCountLimit >= 5)
			myPetsTabPet.timeEggHatched-=2;
		myPetsTabPet.ambition++;
	}
	//deactivate
	button.className = "actionButtonDisabled";
	myPetsTabShakePetTimer = 10;
};

var myPetsTabTalkPetTimer = 0;
var myPetsTabTalkPet = function(button){
	if (myPetsTabTalkPetTimer > 0) return;
	
	if (myPetsTabPet != null && myPetsTabPet.petForm == "EGG"){
		myPetsTabPet.UpdateAnimation("myPetsTabPetImage");
		myPetsTabPet.timeEggHatched--;
		myPetsTabPet.empathy+=2;
	}
	//deactivate
	button.className = "actionButtonDisabled";
	myPetsTabTalkPetTimer = 20;
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
	for (var i = 0; i < 4; i++){
		if (i >= userPets.length){
			document.getElementById("Pet"+(i+1)+"Button").style.visibility = 'hidden';
		}else document.getElementById("Pet"+(i+1)+"Button").style.visibility = 'visible';
	}
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