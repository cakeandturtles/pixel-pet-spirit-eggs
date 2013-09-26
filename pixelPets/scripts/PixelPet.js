var PixelPet = function(petSpeciesObj){
	this.name = "???";
	this.species = petSpeciesObj.species;
	this.PetFormEnum = { EGG: "EGG", BABY: "BBY", ADOLESCENT: "ADO", ADULT: "ADU" };
	this.petForm = this.PetFormEnum.EGG;
	this.formChange = false;
	this.currentDescription = "The egg is warm but doesn't move much.";
	
	this.ambition = 0;
	this.empathy = 0;
	this.insight = 0;
	this.diligence = 0;
	this.charm = 0;
	
	this.timeEggFound = new Date().getTime() / 1000; //In seconds
	this.timeEggHatched = this.timeEggFound + 60; //In seconds

	this.frameCount = 0;
	this.frameCountLimit = 10;
	this.currFrame = 0;
	this.maxFrame = 2;
	this.frameWidth = 128;
	this.frameHeight = 128;
	this.aniX = petSpeciesObj.aniX;
	this.aniY = petSpeciesObj.aniY;
	
	this.getNameAndSpecies = function(){
		if (this.petForm == this.PetFormEnum.EGG)
			return "??? the Mysterious Egg";
		return this.name + " the " + this.species;
	};
	
	this.EggHatched = function(){
		if (this.petForm == this.PetFormEnum.EGG){
			this.petForm = this.PetFormEnum.BABY;
			this.aniY++;
		}
	}
	
	this.Update = function(imageId){
		this.UpdatePetForm();
		this.UpdateDescriptionAndAnimationSpeed();
		this.UpdateAnimation(imageId);
	};
	
	this.UpdatePetForm = function(){
		if (this.petForm == this.PetFormEnum.EGG){
			var currentSeconds = new Date().getTime() / 1000; //In seconds..
			if (currentSeconds >= this.timeEggHatched){
				//PET HATCHED FROM THE EGG
				this.EggHatched();
				this.formChange = true;
			}
		}else{
		}
	};
	
	this.UpdateDescriptionAndAnimationSpeed = function(){
		if (this.petForm == this.PetFormEnum.EGG){
			var timeHatched = this.timeEggHatched - this.timeEggFound;
			var currTime = new Date().getTime() / 1000 - this.timeEggFound;
			if (currTime >= (timeHatched * 2.0) / 4.0){
				this.frameCountLimit = 12;
				this.currentDescription = "It moves around a lot<br/>It must be close to hatching!";
			}else if (currTime >= timeHatched / 4.0){
				this.frameCountLimit = 60;
				this.currentDescription = "It wiggles around now and then.";
			}else{		
				this.frameCountLimit = 120;
				this.currentDescription = "The egg is warm but doesn't move much.";
			}
		}else{
			this.frameCountLimit = 12;
			this.currentDescription = this.name + " is thrashing about!";
		}
	};
	
	this.UpdateAnimation = function(imageId){
		if (++this.frameCount >= this.frameCountLimit){
			this.frameCount = 0;
			if (++this.currFrame >= this.maxFrame)
				this.currFrame = 0;
		}

		var petImage = document.getElementById(imageId);
		var petSpriteSettings = ""+((-1)*((this.frameWidth*this.currFrame)+(this.frameWidth*this.aniX)))+"px "+((-1)*this.frameHeight*this.aniY)+"px";
		petImage.style.backgroundPosition=petSpriteSettings;
	};
};