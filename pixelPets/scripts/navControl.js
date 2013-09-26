var NavButtonEnum = {
	MY_PETS: "myPets", INVENTORY: "inventory", GARDEN: "garden", CODEX: "codex"
};

var gotoMyPets = function(){
	stopTabSpecificScripts();
	selectedNav = NavButtonEnum.MY_PETS;
	
	document.getElementById("mainBody").innerHTML = myPetsTabHTML;
	myPetsTabScriptBegin();
	setTheme();
};

var gotoInventory = function(){
	stopTabSpecificScripts();
	selectedNav = NavButtonEnum.INVENTORY;
	
	document.getElementById("mainBody").innerHTML = inventoryTabHTML;
	inventoryTabScriptBegin();
	setTheme();
};

var gotoGarden = function(){
	stopTabSpecificScripts();
	selectedNav = NavButtonEnum.GARDEN;
	
	document.getElementById("mainBody").innerHTML = gardenTabHTML;
	gardenTabScriptBegin();
	setTheme();
};

var gotoCodex = function(){
	stopTabSpecificScripts();
	selectedNav = NavButtonEnum.CODEX;
	
	document.getElementById("mainBody").innerHTML = codexTabHTML;
	codexTabScriptBegin();
	setTheme();
};

var stopTabSpecificScripts = function(){
	switch(selectedNav){
		case NavButtonEnum.MY_PETS:
			myPetsTabScriptEnd();
			break;
		case NavButtonEnum.INVENTORY:
			inventoryTabScriptEnd();
			break;
		case NavButtonEnum.GARDEN:
			gardenTabScriptEnd();
			break;
		case NavButtonEnum.CODEX:
			codexTabScriptEnd();
			break;
		default: break;
	}
};