var updateFormData = function(){
	var tempPet;
	
	document.getElementById("form_name").value = accountInfoUsername;
	document.getElementById("form_pass").value = accountInfoPassword;
	
	//TRY TO UPDATE FORM DATA FOR PET 1
	if (userPets.length >= 1 && userPets[0] != null){
		tempPet = userPets[0];
		document.getElementById("form_pet1exists").checked = true;
		document.getElementById("form_pet1canRelease").checked = tempPet.canRelease;
		document.getElementById("form_pet1petSpecies").value = tempPet.petSpeciesObj.species[0];
		document.getElementById("form_pet1speciesIndex").value = Math.round(tempPet.speciesIndex);
		document.getElementById("form_pet1petForm").value = tempPet.petForm;
		document.getElementById("form_pet1wasHappyTeen").checked = tempPet.wasHappyTeen;
		document.getElementById("form_pet1mood").value = Math.round(tempPet.mood);
		document.getElementById("form_pet1nextEventTime").value = Math.round(tempPet.nextEventTime);
		document.getElementById("form_pet1expTimer").value = Math.round(tempPet.expTimer);
		document.getElementById("form_pet1aniY").value = Math.round(tempPet.aniY);
		document.getElementById("form_pet1name").value = tempPet.name;
		document.getElementById("form_pet1formChange").checked = tempPet.formChange;
	}else{
		document.getElementById("form_pet1exists").checked = false;
		document.getElementById("form_pet1canRelease").checked = false;
		document.getElementById("form_pet1petSpecies").value = "";
		document.getElementById("form_pet1speciesIndex").value = -1;
		document.getElementById("form_pet1petForm").value = "";
		document.getElementById("form_pet1wasHappyTeen").checked = false;
		document.getElementById("form_pet1mood").value = -1;
		document.getElementById("form_pet1nextEventTime").value = -1;
		document.getElementById("form_pet1expTimer").value = -1;
		document.getElementById("form_pet1aniY").value = -1;
		document.getElementById("form_pet1name").value = "";
		document.getElementById("form_pet1formChange").checked = false;
	}
	//TRY TO UPDATE FORM DATA FOR PET 2
	if (userPets.length >= 2 && userPets[1] != null){
		tempPet = userPets[1];
		document.getElementById("form_pet2exists").checked = true;
		document.getElementById("form_pet2canRelease").checked = tempPet.canRelease;
		document.getElementById("form_pet2petSpecies").value = tempPet.petSpeciesObj.species[0];
		document.getElementById("form_pet2speciesIndex").value = Math.round(tempPet.speciesIndex);
		document.getElementById("form_pet2petForm").value = tempPet.petForm;
		document.getElementById("form_pet2wasHappyTeen").checked = tempPet.wasHappyTeen;
		document.getElementById("form_pet2mood").value = Math.round(tempPet.mood);
		document.getElementById("form_pet2nextEventTime").value = Math.round(tempPet.nextEventTime);
		document.getElementById("form_pet2expTimer").value = Math.round(tempPet.expTimer);
		document.getElementById("form_pet2aniY").value = Math.round(tempPet.aniY);
		document.getElementById("form_pet2name").value = tempPet.name;
		document.getElementById("form_pet2formChange").checked = tempPet.formChange;
	}else{
		document.getElementById("form_pet2exists").checked = false;
		document.getElementById("form_pet2canRelease").checked = false;
		document.getElementById("form_pet2petSpecies").value = "";
		document.getElementById("form_pet2speciesIndex").value = -1;
		document.getElementById("form_pet2petForm").value = "";
		document.getElementById("form_pet2wasHappyTeen").checked = false;
		document.getElementById("form_pet2mood").value = -1;
		document.getElementById("form_pet2nextEventTime").value = -1;
		document.getElementById("form_pet2expTimer").value = -1;
		document.getElementById("form_pet2aniY").value = -1;
		document.getElementById("form_pet2name").value = "";
		document.getElementById("form_pet2formChange").checked = false;
	}
	//TRY TO UPDATE FORM DATA FOR PET 3
	if (userPets.length >= 3 && userPets[2] != null){
		tempPet = userPets[2];
		document.getElementById("form_pet3exists").checked = true;
		document.getElementById("form_pet3canRelease").checked = tempPet.canRelease;
		document.getElementById("form_pet3petSpecies").value = tempPet.petSpeciesObj.species[0];
		document.getElementById("form_pet3speciesIndex").value = Math.round(tempPet.speciesIndex);
		document.getElementById("form_pet3petForm").value = tempPet.petForm;
		document.getElementById("form_pet3wasHappyTeen").checked = tempPet.wasHappyTeen;
		document.getElementById("form_pet3mood").value = Math.round(tempPet.mood);
		document.getElementById("form_pet3nextEventTime").value = Math.round(tempPet.nextEventTime);
		document.getElementById("form_pet3expTimer").value = Math.round(tempPet.expTimer);
		document.getElementById("form_pet3aniY").value = Math.round(tempPet.aniY);
		document.getElementById("form_pet3name").value = tempPet.name;
		document.getElementById("form_pet3formChange").checked = tempPet.formChange;
	}else{
		document.getElementById("form_pet3exists").checked = false;
		document.getElementById("form_pet3canRelease").checked = false;
		document.getElementById("form_pet3petSpecies").value = "";
		document.getElementById("form_pet3speciesIndex").value = -1;
		document.getElementById("form_pet3petForm").value = "";
		document.getElementById("form_pet3wasHappyTeen").checked = false;
		document.getElementById("form_pet3mood").value = -1;
		document.getElementById("form_pet3nextEventTime").value = -1;
		document.getElementById("form_pet3expTimer").value = -1;
		document.getElementById("form_pet3aniY").value = -1;
		document.getElementById("form_pet3name").value = "";
		document.getElementById("form_pet3formChange").checked = false;
	}
	//TRY TO UPDATE FORM DATA FOR PET 4
	if (userPets.length >= 4 && userPets[3] != null){
		tempPet = userPets[3];
		document.getElementById("form_pet4exists").checked = true;
		document.getElementById("form_pet4canRelease").checked = tempPet.canRelease;
		document.getElementById("form_pet4petSpecies").value = tempPet.petSpeciesObj.species[0];
		document.getElementById("form_pet4speciesIndex").value = Math.round(tempPet.speciesIndex);
		document.getElementById("form_pet4petForm").value = tempPet.petForm;
		document.getElementById("form_pet4wasHappyTeen").checked = tempPet.wasHappyTeen;
		document.getElementById("form_pet4mood").value = Math.round(tempPet.mood);
		document.getElementById("form_pet4nextEventTime").value = Math.round(tempPet.nextEventTime);
		document.getElementById("form_pet4expTimer").value = Math.round(tempPet.expTimer);
		document.getElementById("form_pet4aniY").value = Math.round(tempPet.aniY);
		document.getElementById("form_pet4name").value = tempPet.name;
		document.getElementById("form_pet4formChange").checked = tempPet.formChange;
	}else{
		document.getElementById("form_pet4exists").checked = false;
		document.getElementById("form_pet4canRelease").checked = false;
		document.getElementById("form_pet4petSpecies").value = "";
		document.getElementById("form_pet4speciesIndex").value = -1;
		document.getElementById("form_pet4petForm").value = "";
		document.getElementById("form_pet4wasHappyTeen").checked = false;
		document.getElementById("form_pet4mood").value = -1;
		document.getElementById("form_pet4nextEventTime").value = -1;
		document.getElementById("form_pet4expTimer").value = -1;
		document.getElementById("form_pet4aniY").value = -1;
		document.getElementById("form_pet4name").value = "";
		document.getElementById("form_pet4formChange").checked = false;
	}
};