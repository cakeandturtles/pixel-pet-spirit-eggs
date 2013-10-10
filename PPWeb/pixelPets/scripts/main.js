var selectedNav;
var hasCodex = true;//false;

var currTabUpdate = function(){};
var tabThemeControl = function(){};
var userPets = [];

var img = new Image().src = 'pixelPets/images/eggs_and_pets_big.png';
var img2 = new Image().src = 'pixelPets/images/eggs_and_pets_happy_big.png';
var img3 = new Image().src = 'pixelPets/images/eggs_and_pets_mad_big.png';

window.onload = function(){
	userPets.push(GetRandomPet());
	InitializeCodex();
	purpleTheme();
	gotoMyPets();
	setInterval(function(){mainUpdate()},60);
	mainUpdate();
};

var mainUpdate = function(){
	currTabUpdate();
	titleNotify();
	updateNavButtonVisibility();
	updateCodex();
	
	for (var i = 0; i < userPets.length; i++){
		if (selectedNav == NavButtonEnum.MY_PETS){
			if (userPets[i] == myPetsTabPet){
				if (nameNotification)
					myPetsTabPet.Update("myPetsTabPetImageNotify");
				else myPetsTabPet.Update("myPetsTabPetImage");
			}else{
				userPets[i].Update("");
			}
		}else{
			userPets[i].Update("");
		}
	}
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
var nameNotification = false;
var releasePet = false;
var openNotification = function(){
	var el = document.getElementById("overlay");
	var el2 = document.getElementById("overlay2");
	el.style.visibility = "visible";
	el2.style.visibility = "visible";
	nameNotification = true;
}

var closeNotification = function() {
     document.getElementById("overlay").style.visibility = 'hidden';
	 document.getElementById("overlay2").style.visibility = 'hidden';
	 doTitleNotify = false;
	 nameNotification = false;
	 
	 if (releasePet){
		releasePet = false;
		//Actually release the pet...
		var releaseIndex = myPetsTabGetPetIndex();
		userPets.splice(releaseIndex, 1);
		if (releaseIndex == userPets.length){
			if (myPetsTabSelectedTab == "Pet4")
				myPetsTabSelectedTab = "Pet3";
			else if (myPetsTabSelectedTab == "Pet3")
				myPetsTabSelectedTab = "Pet2";
			else if (myPetsTabSelectedTab == "Pet2")
				myPetsTabSelectedTab = "Pet1";
		}
		
		if (userPets.length <= 0){ //YOU NEED A NEW PET!!!
			myPetsTabSelectedTab = "Pet1";
			userPets.push(GetRandomPet());
			doTitleNotify = true;
			titleKeeper = "Hello Mysterious Egg!!!    ";
			nameNotification = true;
			
			myPetsTabPet = myPetsTabGetSelectedPet();
			var note = document.getElementById("notificationBody");
			var noteHTML = "<div class='genericPetImageContainer' style='cursor:default;'>";
			noteHTML += "<div id=\"myPetsTabPetImageNotify\" class='genericPetImage'></div>";
			noteHTML += "</div><br/>";
			noteHTML += "As you wave goodbye to your last pet, you find an egg on the ground.";
			noteHTML += ".......";
			noteHTML += "<div style='margin-top:5px;'>You decide to pick it up and start anew!</div>"
			noteHTML += "<br/><div class='actionButton' style='float:none;width:80px;margin:0px auto; margin-top:10px;' onclick='closeNotification();'>Okay!</div>";
			note.innerHTML = noteHTML;
				
			openNotification();
			myPetsTabPet.UpdateAnimation("myPetsTabPetImage");
			myPetsTabPet.UpdateAnimation("myPetsTabPetImageNotify");
		}
	 }
}