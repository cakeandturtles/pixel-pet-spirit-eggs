var PixelPet = function(petSpeciesObj){
	this.name = "???";
	this.species = petSpeciesObj.species;
	this.PetFormEnum = { EGG: "EGG", BABY: "BBY", ADOLESCENT: "ADO", ADULT: "ADU" };
	this.petForm = this.PetFormEnum.EGG;
	this.formChange = false;
	this.currentDescription = "The egg is warm but doesn't move much.";
	
	this.mood = 0;
	this.maxMood = 255;
	
	this.lastEventTime = new Date().getTime() / 1000; //In seconds
	this.nextEventTime = this.lastEventTime + 60; //In seconds
	this.expTimer = this.lastEventTime;
	this.lastTime = this.lastEventTime;

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
		var thisTime = new Date().getTime() / 1000;
		this.expTimer += (thisTime - this.lastTime);
		if (this.expTimer >= this.nextEventTime){
			if (this.petForm == this.PetFormEnum.EGG){	
				//PET HATCHED FROM THE EGG
				this.EggHatched();
				this.formChange = true;
				//SET LIMIT FOR EVOLUTION!!!
				this.lastEventTime = new Date().getTime() / 1000; //In seconds
				this.nextEventTime = this.lastEventTime + 240; //In seconds
				this.expTimer = this.lastEventTime;
			}else{
			}
		}
		this.lastTime = thisTime;
	};
	
	this.GetMoodRatio = function(){
		return Math.round((this.mood/this.maxMood)*126.0);
	};
	
	this.GetExpRatio = function(){
		var expRatio = (this.expTimer-this.lastEventTime) / (this.nextEventTime-this.lastEventTime);
		return Math.round(expRatio * 126.0);
	};
	
	this.UpdateDescriptionAndAnimationSpeed = function(){
		if (this.petForm == this.PetFormEnum.EGG){
			var timeHatched = this.nextEventTime - this.lastEventTime;
			var currTime = this.expTimer - this.lastEventTime;
			if (currTime >= (timeHatched * 3.0) / 4.0){
				this.frameCountLimit = 12;
				this.currentDescription = "It moves around a lot<br/>It must be close to hatching!";
			}else if (currTime >= timeHatched * 1.5 / 4.0){
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
			this.mood--; //DECREASE MOOD
			if (this.mood < 0) this.mood = 0;
			if (++this.currFrame >= this.maxFrame){
				this.currFrame = 0;
			}
		}

		var petImage = document.getElementById(imageId);
		var petSpriteSettings = ""+((-1)*((this.frameWidth*this.currFrame)+(this.frameWidth*this.aniX)))+"px "+((-1)*this.frameHeight*this.aniY)+"px";
		petImage.style.backgroundPosition=petSpriteSettings;
	};
};