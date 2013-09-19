var gotoMyPets = function(){
	stopTabSpecificScripts();
	selectedNav = "myPets";
	
	document.getElementById("mainBody").innerHTML = myPetsTabHTML;
	myPetsTabScriptBegin();
	setTheme();
};

var gotoTrainer = function(){
	stopTabSpecificScripts();
	selectedNav = "trainer";
	
	document.getElementById("mainBody").innerHTML = trainerTabHTML;
	trainerTabScriptBegin();
	setTheme();
};

var gotoInventory = function(){
	stopTabSpecificScripts();
	selectedNav = "inventory";
	
	setTheme();
};

var gotoGarden = function(){
	stopTabSpecificScripts();
	selectedNav = "garden";
	
	setTheme();
};

var gotoCodex = function(){
	stopTabSpecificScripts();
	selectedNav = "codex";
	
	setTheme();
};

var stopTabSpecificScripts = function(){
	switch(selectedNav){
		case "myPets":
			myPetsTabScriptEnd();
			break;
		case "trainer":
			trainerTabScriptEnd();
			break;
		case "inventory":
			
			break;
		case "garden":
			
			break;
		case "codex":
			break;
		default: break;
	}
};