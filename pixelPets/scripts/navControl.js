var NavButtonEnum = {
	MY_PETS: "myPets", CODEX: "codex"
};

var gotoMyPets = function(){
	stopTabSpecificScripts();
	selectedNav = NavButtonEnum.MY_PETS;
	
	myPetsTabPopulateBody(document.getElementById("mainBody"));
	myPetsTabScriptBegin();
	setTheme();
};

var gotoCodex = function(){
	stopTabSpecificScripts();
	selectedNav = NavButtonEnum.CODEX;
	
	codexTabPopulateBody(document.getElementById("mainBody"));
	codexTabScriptBegin();
	setTheme();
};

var stopTabSpecificScripts = function(){
	switch(selectedNav){
		case NavButtonEnum.MY_PETS:
			myPetsTabScriptEnd();
			break;
		case NavButtonEnum.CODEX:
			codexTabScriptEnd();
			break;
		default: break;
	}
};