var userCodex = new Array();

var InitializeCodex = function(){
	userCodex = new Array();
	AddPupPetsToCodex();
	AddHogPetsToCodex();
	AddPillarPetsToCodex();
	AddSquirmPetsToCodex();
	AddPeepPetsToCodex();
	AddSproutPetsToCodex();
	AddBlubbyPetsToCodex();
	AddGlobPetsToCodex();
	AddDribblePetsToCodex();
};

var updateCodex = function(){
	for (var i = 0; i < userPets.length; i++){
		if (userPets[i] == null || userPets[i].checkedIntoCodex)
			continue;
		else{
			for (var j = 0; j < userCodex.length; j++){
				if (userCodex[j] == null) continue;
				if (userCodex[j].species == userPets[i].species && userPets[i].petForm == userCodex[j].petForm){
					userCodex[j].inCodex = true;
					userPets[i].checkedIntoCodex = true;
				}
			}
		}
	}
};