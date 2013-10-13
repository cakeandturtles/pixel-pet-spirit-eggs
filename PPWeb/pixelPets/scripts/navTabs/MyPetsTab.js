var myPetsTabHTML = "";
myPetsTabHTML += "<div id='Pet1Button' class=\"navButton\" style=\"background:#ffffff; float:left;padding-left:30px;padding-right:20px;width:45px;text-align:left;\" onclick='myPetsTabgotoPet1()'>PET 1</div>";
myPetsTabHTML += "<div id='Pet2Button' class=\"navButton\" style=\"float:left;padding-left:30px;padding-right:20px;width:45px;text-align:left;\" onclick='myPetsTabgotoPet2()'>PET 2</div>";
myPetsTabHTML += "<div id='Pet3Button' class=\"navButton\" style=\"float:left;padding-left:30px;padding-right:20px;width:45px;text-align:left;\" onclick='myPetsTabgotoPet3()'>PET 3</div>";
myPetsTabHTML += "<div id='Pet4Button' class=\"navButton\" style=\"float:left;padding-left:30px;padding-right:20px;width:45px;text-align:left;\" onclick='myPetsTabgotoPet4()'>PET 4</div>";
myPetsTabHTML += "<div class=\"clearer\"></div><br/>";
myPetsTabHTML += "<div id='myPetsTabNameSpecies' style=\"float:left;cursor:pointer;\" onclick='myPetsTabNameNotify(false);'>??? the Mysterious Egg</div>				<div id='myPetsTabLevel' style=\"float:right;\">Lvl. ???</div>";
myPetsTabHTML += "<div class=\"clearer\"></div><br/>";
myPetsTabHTML += "<div style='position:relative; left:36%; width:350px; height:128px;'>";
myPetsTabHTML += "<div class='genericPetImageContainer' style='float:left;' onclick='myPetsTabPetPet()'>";
myPetsTabHTML += "<div id=\"myPetsTabPetImage\" class='genericPetImage'></div>";
myPetsTabHTML += "</div>";
myPetsTabHTML += "<div style='float:left;margin-top:10px;'>";
myPetsTabHTML += "<div id=\"myPetsTabReleasePet\" class='actionButton' onclick='myPetsTabReleasePet();'>Release Pet</div></div>";
myPetsTabHTML += "</div><div class=\"clearer\"></div>";
myPetsTabHTML += "<div style='width:180px;margin:8px auto;position:relative;left:-10px;'>";
myPetsTabHTML += "<div style='float:left;'>Exp:&nbsp;</div>";
myPetsTabHTML += "<div style='float:left; width:128px;height:16px;background:#000000;'>"
myPetsTabHTML += "<div id='myPetsTabPetEXP' style='width:100px;height:14px;background:#00ff00;margin:1px;'></div>";
myPetsTabHTML += "</div></div>";
myPetsTabHTML += "<div class=\"clearer\"></div>";
myPetsTabHTML += "<div id='moodContainer' style='width:200px;margin:8px auto;position:relative;left:-14px;'>";
myPetsTabHTML += "<div style='float:left;'>Mood:&nbsp;</div>";
myPetsTabHTML += "<div style='float:left; width:128px; height:16px;background:#000000;'>";
myPetsTabHTML += "<div id='myPetsTabPetMood' style='width:100px;height:14px;background:#ff00ff;margin:1px;'></div></div>";
myPetsTabHTML += "<div class=\"clearer\"></div></div><br/>";
myPetsTabHTML += "<div id=\"myPetsTabPetDescription\" style=\"height:32px;\">It moves around a lot.<br/>It must be close to hatching!</div><br/></div>";
myPetsTabHTML += "<div id='talkToPetContainer' style='width:350px; position:absolute; bottom:10px; left:30%; right:30%;'>";
myPetsTabHTML += "<input id='myPetsTabTalkBox' style='width:200px;float:left;margin-top:10px;' type='text' onKeyPress='myPetsTabTalkEnter(event);' />";
myPetsTabHTML += "<div class='actionButton' style='float:left;' onclick='myPetsTabTalk();'>Say Something</div>";
myPetsTabHTML += "</div>";

var myPetsTabSelectedTab;
var myPetsTabUpdate;
var myPetsTabPet;

var myPetsTabScriptBegin = function(){
	//Local navigation/Local theme control!
	if (myPetsTabSelectedTab == null)
		myPetsTabSelectedTab = "Pet1";
	tabThemeControl = myPetsTabThemeControl;
	
	currTabUpdate = myPetsTabUpdate;
	myPetsTabUpdate();
	if (nameNotification)
		myPetsTabPet.UpdateAnimation("myPetsTabPetImageNotify");
	else myPetsTabPet.UpdateAnimation("myPetsTabPetImage");
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
	if (myPetsTabPet.emotion == 0){
		if (myPetsTabPet.mood < 128 && myPetsTabPet.petForm != "EGG"){
			document.getElementById("myPetsTabPetImage").style.backgroundColor = "#00007F";
		}else{
			if (myPetsTabPet.PetPetCounter >= 4)
				document.getElementById("myPetsTabPetImage").style.backgroundColor = "#8F0000";
			else document.getElementById("myPetsTabPetImage").style.backgroundColor = "#000000";
		}
	}else if (myPetsTabPet.emotion > 0){
		document.getElementById("myPetsTabPetImage").style.backgroundColor = "#FF00FF";
	}else{
		document.getElementById("myPetsTabPetImage").style.backgroundColor = "#FF0000";
	}
	
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

	if (myPetsTabPet.petForm == "EGG"){
		document.getElementById("moodContainer").style.visibility = "hidden";
		document.getElementById("talkToPetContainer").style.visibility = "hidden";
	}
	else{ 
		document.getElementById("moodContainer").style.visibility = "visible";
		document.getElementById("talkToPetContainer").style.visibility = "visible";
	}
	if (myPetsTabPet.expTimer > myPetsTabPet.nextTimeEvent)
		myPetsTabPet.expTimer = myPetsTabPet.nextTimeEvent;
	document.getElementById("myPetsTabPetEXP").style.width = myPetsTabPet.GetExpRatio()+"px";
	document.getElementById("myPetsTabPetMood").style.width = myPetsTabPet.GetMoodRatio()+"px";
	document.getElementById("myPetsTabLevel").innerHTML = "Lvl. " + myPetsTabPet.petForm
	document.getElementById("myPetsTabPetDescription").innerHTML = myPetsTabPet.currentDescription;
	
	if (myPetsTabPet.petForm != "EGG" && myPetsTabPet.canRelease){
		document.getElementById("myPetsTabReleasePet").style.visibility = "visible";
	}
	else{
		document.getElementById("myPetsTabReleasePet").style.visibility = "hidden";
	}
};

var myPetsTabNameNotify = function(justHatched){
	doTitleNotify = true;
	nameNotification = true;
	var defaultName = myPetsTabPet.name;
	var justNotify = false;
	if (justHatched && myPetsTabPet.petForm != "BBY")
		justNotify = true;
	
	
	var note = document.getElementById("notificationBody");
	var noteHTML = "<div class='genericPetImageContainer' style='cursor:default;margin-top:5px;'>";
	noteHTML += "<div id=\"myPetsTabPetImageNotify\" class='genericPetImage'></div>";
	noteHTML += "</div><br/>";
	
	if (!justNotify){
		if (justHatched){
			noteHTML += "A " + myPetsTabPet.species + " just hatched from the egg!";
			noteHTML += "<br/><br/>What would you like to name your new " + myPetsTabPet.species + "?";
			titleKeeper = " " + myPetsTabPet.species + " has hatched!    ";
			myPetsTabPet.name = defaultName;
			hasCodex = true;
		}else{
			noteHTML += "Do you want to rename " + myPetsTabPet.name + "?";
			titleKeeper = " Rename " + myPetsTabPet.name + "?    ";
		}
		
		noteHTML += "<br/><br/><input id='myPetsTabNameForm' type='text' value='"+defaultName+"' maxlength='12' onKeyPress='myPetsTabNameEnter(event);' />";
		noteHTML += "<br/><div class='actionButton' style='float:none;width:80px;margin:0px auto;margin-top:10px;' onclick='myPetsTabNamePet();'>Name Pet</div>";
	}else{
		if (myPetsTabPet.petForm == "EGG"){
			titleKeeper = " " + myPetsTabPet.name + " has passed away!    ";
			noteHTML += myPetsTabPet.name + " has had a good life. But it's time for them to pass on.<br/>";
			noteHTML += "...<br/>";
			noteHTML += "....<br/>";
			noteHTML += ".....<br/>";
			noteHTML += "<div style='margin-top:5px;'>" + myPetsTabPet.name + " has changed back into an egg!</div>";
		}else{
			titleKeeper = " " + myPetsTabPet.name + " evolved!    ";
			noteHTML += myPetsTabPet.name + " is beginning to change!<br/>";
			noteHTML += "...<br/>";
			noteHTML += "....<br/>";
			noteHTML += ".....<br/>";
			noteHTML += "<div style='margin-top:5px;'>" + myPetsTabPet.name + " evolved into " + myPetsTabPet.species + "!</div>";
			if (this.name == this.prevSpecies){
				this.name = this.species;
				this.prevSpecies = this.species;
			}
			if (myPetsTabPet.petForm == "ADU"){
				noteHTML += "<br/>Interestingly enough...<div style='margin-top:5px;'>" + myPetsTabPet.name + " laid an egg!</div>";
				if (userPets.length < 4)
					userPets.push(GetRandomPet());
				else
					noteHTML += "<br/>But you have no room for it...";
			}
		}
		noteHTML += "<br/><div class='actionButton' style='float:none;width:80px;margin:0px auto; margin-top:10px;' onclick='closeNotification();'>Okay</div>";
	}
	note.innerHTML = noteHTML;
		
	openNotification();
	myPetsTabPet.UpdateAnimation("myPetsTabPetImageNotify");
};

//ACTION BUTTONS////////////////////////////////////////////////////////////////////////////////////
var myPetsTabTalkEnter = function(e){
	var code = (e.keyCode ? e.keyCode : e.which);
	if (code == 13) //ENTER KEY
		myPetsTabTalk();
};

var myPetsTabTalk = function(){
	var talkText = document.getElementById("myPetsTabTalkBox").value;
	if (talkText && talkText.trim()){
		myPetsTabPet.currentDescription = "<b>You said:</b> " + talkText.trim() + "<br/><br/>" + GetRandomResponse();
		myPetsTabPet.talkResponse = 100;
		var rand = Math.floor(Math.random()*3);
		if (rand == 0)
			myPetsTabPet.emotion = -40;
		else myPetsTabPet.emotion = 40;
	}
	document.getElementById("myPetsTabTalkBox").value = "";
};

var myPetsTabNameEnter = function(e){
	var code = (e.keyCode ? e.keyCode : e.which);
	if (code == 13) //ENTER KEY
		myPetsTabNamePet();
};
var myPetsTabNamePet = function(){
	var petName = document.getElementById("myPetsTabNameForm").value;
	petName = petName.replace(/\,/g,""); //Remove commas
	petName = petName.replace(/\&/g,""); //Remove &s
	petName = petName.replace(/\=/g,""); //Remove =s
	petName = petName.replace(/\./g,""); //Remove periods
	petName = petName.replace(/\@/g,""); //Remove @s
	petName = petName.replace(/\</g,""); //Remove <s
	petName = petName.replace(/\>/g,""); //Remove >s
	petName = petName.replace(/\//g,""); //Remove /s
	petName = petName.replace(/\\/g,""); //Remove \s
	petName = petName.replace(/\?/g,""); //Remove ?s
	petName = petName.replace(/\`/g,""); //Remove `s
	petName = petName.replace(/\~/g,""); //Remove ~s
	petName = petName.replace(/\!/g,""); //Remove !s
	petName = petName.replace(/\#/g,""); //Remove #s
	petName = petName.replace(/\$/g,""); //Remove $s
	petName = petName.replace(/\%/g,""); //Remove %s
	petName = petName.replace(/\^/g,""); //Remove ^s
	petName = petName.replace(/\*/g,""); //Remove *s
	petName = petName.replace(/\(/g,""); //Remove (s
	petName = petName.replace(/\)/g,""); //Remove )s
	petName = petName.replace(/\+/g,""); //Remove +s
	petName = petName.replace(/\;/g,""); //Remove ;s
	petName = petName.replace(/\:/g,""); //Remove :s
	petName = petName.replace(/\"/g,""); //Remove "s
	petName = petName.replace(/\'/g,""); //Remove 's
	petName = petName.replace(/\{/g,""); //Remove {s
	petName = petName.replace(/\}/g,""); //Remove }s
	petName = petName.replace(/\[/g,""); //Remove [s
	petName = petName.replace(/\]/g,""); //Remove ]s
	myPetsTabPet.name = petName;
	closeNotification();
};

var myPetsTabUpdateActionButtons = function(){	
};

var myPetsTabPetPet = function(){
	if (myPetsTabPet.emotion != 0) return;

	myPetsTabPet.frameCount = myPetsTabPet.frameCountLimit;
	myPetsTabPet.UpdateAnimation("myPetsTabPetImage");
	if (myPetsTabPet != null){
		myPetsTabPet.expTimer++;
		myPetsTabPet.mood+=2;
		
		if (myPetsTabPet.petForm != "EGG"){
			myPetsTabPet.PetCounter++;
			if (myPetsTabPet.PetCounter >= 5){
				myPetsTabPet.talkResponse = 0;
				myPetsTabPet.PetCounter = 0;
				myPetsTabPet.AntiPetPetCounter = 0;
				if (myPetsTabPet.mood >= 128)
					myPetsTabPet.PetPetCounter++;				
				if (myPetsTabPet.PetPetCounter >= 5){
					myPetsTabPet.emotion = -80;
					myPetsTabPet.PetPetCounter--;
					return;
				}
				
				myPetsTabPet.emotion = 40;
			}
		}else{
			myPetsTabPet.expTimer++;
		}
	}
};

var myPetsTabReleasePet = function(){
	doTitleNotify = true;
	titleKeeper = "Release " + myPetsTabPet.name + "???    ";
	nameNotification = true;
	
	var note = document.getElementById("notificationBody");
	var noteHTML = "<div class='genericPetImageContainer' style='cursor:default;'>";
	noteHTML += "<div id=\"myPetsTabPetImageNotify\" class='genericPetImage'></div>";
	noteHTML += "</div><br/>";
	noteHTML += "Do you want to release " + myPetsTabPet.name + "?<br/>";
	noteHTML += myPetsTabPet.name + " will live freely in the wild for the rest of their years!<br/><br/>"
	noteHTML += "Are you sure you want to release " + myPetsTabPet.name + "???<br/>";
	noteHTML += "<br/><div class='actionButton' style='float:left;width:60px;margin-left:65px;; margin-top:5px;' onclick='YesReleasePet();'>Yes</div>";
	noteHTML += "<div class='actionButton' style='float:left;width:60px;margin-left:20px; margin-top:5px;' onclick='NoReleasePet();'>No</div>";
	noteHTML += "<div class=\"clearer\"></div>";
	note.innerHTML = noteHTML;
		
	openNotification();
	myPetsTabPet.UpdateAnimation("myPetsTabPetImageNotify");
};

var YesReleasePet = function(){
	doTitleNotify = true;
	titleKeeper = "Goodbye " + myPetsTabPet.name + "!!!    ";
	nameNotification = true;
	releasePet = true;
	
	var note = document.getElementById("notificationBody");
	var noteHTML = "<div class='genericPetImageContainer' style='cursor:default;'>";
	noteHTML += "<div id=\"myPetsTabPetImageNotify\" class='genericPetImage'></div>";
	noteHTML += "</div><br/>";
	noteHTML += "Goodbye forever " + myPetsTabPet.name + "!!<br/>";
	noteHTML += ".......";
	noteHTML += "<div style='margin-top:5px;'>" + myPetsTabPet.name + " has gone off to frolic with other wildlings!</div>"
	noteHTML += "<br/><div class='actionButton' style='float:none;width:80px;margin:0px auto; margin-top:10px;' onclick='closeNotification();'>Okay</div>";
	note.innerHTML = noteHTML;
		
	openNotification();
	myPetsTabPet.UpdateAnimation("myPetsTabPetImageNotify");
};

var NoReleasePet = function(){
	closeNotification();
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
			if (userPets[i].formChange){
				if (userPets[i].frameCount % 4 == 0){
					var text = document.getElementById("Pet"+(i+1)+"Button").innerHTML;
					if (text == "PET " + (i+1))
						text = "PET " + (i+1) + "!";
					else if (text == "PET " + (i+1) + "!")
						text = "PET " + (i+1) + "!!";
					else if (text == "PET " + (i+1) + "!!")
						text = "PET " + (i+1) + "!!!";
					else text = "PET " + (i+1);
					document.getElementById("Pet"+(i+1)+"Button").innerHTML = text;
				}
			}else if (userPets[i].mood < 128 && userPets[i].petForm != "EGG"){
				if (userPets[i].frameCount % 4 == 0){
					var text = document.getElementById("Pet"+(i+1)+"Button").innerHTML;
					if (text == "PET " + (i+1))
						text = "PET " + (i+1) + ".";
					else if (text == "PET " + (i+1) + ".")
						text = "PET " + (i+1) + "..";
					else if (text == "PET " + (i+1) + "..")
						text = "PET " + (i+1) + "...";
					else text = "PET " + (i+1);
					document.getElementById("Pet"+(i+1)+"Button").innerHTML = text;
				}
			}
			else document.getElementById("Pet"+(i+1)+"Button").innerHTML = "PET " + (i+1);
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