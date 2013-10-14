var saveLocalData = function(){
	if (userLoggedIn){
		var login = [];
		login.push(accountInfoUsername);
		login.push(accountInfoPassword);
		writeCookie("pixelpetslogin", JSON.stringify(login), 30);
	}
	else{
		//STORE ALL THE PETS
		writeCookie("pixelpetspet1", JSON.stringify(userPets[0]), 30);
		if (userPets.length > 1 && userPets[1] != null)
			writeCookie("pixelpetspet2", JSON.stringify(userPets[1]), 30);
		if (userPets.length > 2 && userPets[2] != null)
			writeCookie("pixelpetspet3", JSON.stringify(userPets[2]), 30);
		if (userPets.length > 3 && userPets[3] != null)
			writeCookie("pixelpetspet4", JSON.stringify(userPets[3]), 30);
		
		//STORE THE CODEX
		var tempCodex = [];
		for (var i = 0; i < userCodex.length; i++){
			tempCodex.push(userCodex[i].inCodex);
		}
		writeCookie("pixelpetsuserCodex", JSON.stringify(tempCodex), 30);
	}
};

var loadLocalData = function(){
	//TODO:: LOG IN!!!
	var login = readCookie("pixelpetslogin");
	if (login != ''){
		login = JSON.parse(login);
		logInUser(login[0], login[1]);
	}else{
		//RETRIEVE THE PETS!
		var tempPet = readCookie("pixelpetspet1");
		if (tempPet != ''){
			userPets = [];
			userPets.push(ObjectToPet(JSON.parse(tempPet)));
		}
		else return;
		tempPet = readCookie("pixelpetspet2");
		if (tempPet != '')
			userPets.push(ObjectToPet(JSON.parse(tempPet)));
		tempPet = readCookie("pixelpetspet3");
		if (tempPet != '')
			userPets.push(ObjectToPet(JSON.parse(tempPet)));
		tempPet = readCookie("pixelpetspet4");
		if (tempPet != '')
			userPets.push(ObjectToPet(JSON.parse(tempPet)));
		//RETRIEVE THE CODEX
		var tempCodex = readCookie("pixelpetsuserCodex");
		if (tempCodex != ''){
			tempCodex = JSON.parse(tempCodex);
			for (var i = 0; i < userCodex.length; i++){
				userCodex[i].inCodex = tempCodex[i];
			}
		}
	}
};

var ObjectToPet = function(jsonObj){
	var newPet = getNewPetFromSpeciesString(jsonObj.petSpeciesObj.species[0]);
	newPet.inCodex = jsonObj.inCodex;
	newPet.checkedIntoCodex = jsonObj.checkedIntoCodex;
	newPet.canRelease = jsonObj.canRelease;
	newPet.speciesIndex = jsonObj.speciesIndex;
	newPet.species = newPet.petSpeciesObj.species[newPet.speciesIndex];
	newPet.prevSpecies = newPet.species;
	newPet.petForm = jsonObj.petForm;
	newPet.formChange = jsonObj.formChange;
	newPet.wasHappyTeen = jsonObj.wasHappyTeen;
	newPet.mood = jsonObj.mood;
	newPet.nextEventTime = jsonObj.nextEventTime;
	newPet.expTimer = jsonObj.expTimer;
	newPet.aniY = jsonObj.aniY;
	newPet.name = jsonObj.name;
	return newPet;
};


//Functions from http://stackoverflow.com/questions/2137464/javascript-function-save-to-cookies
var writeCookie = function(name, value, days){
	var date, expires;
	if (days){
		date = new Date();
		date.setTime(date.getTime()+(days*24*60*60*1000));
		expires = "; expires="+date.toGMTString();
	}else{
		expires = "";
	}
	document.cookie = name+"="+value+expires+"; path=/";
};

var readCookie = function(name){
	var i, c, ca, nameEQ = name + "=";
    ca = document.cookie.split(';');
    for(i=0;i < ca.length;i++) {
        c = ca[i];
        while (c.charAt(0)==' ') {
            c = c.substring(1,c.length);
        }
        if (c.indexOf(nameEQ) == 0) {
            return c.substring(nameEQ.length,c.length);
        }
    }
    return '';
};

//from http://stackoverflow.com/questions/5976912/clear-cookies-using-javascript
var deleteCookie = function(name) {
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT; path=/';
}