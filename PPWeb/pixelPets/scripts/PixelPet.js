var GetRandomPet = function(){
	var rand = Math.floor(Math.random()*7);
	if (rand == 0)
		return new PixelPet(Hog);
	else if (rand == 1)
		return new PixelPet(Pillar);
	else if (rand == 2)
		return new PixelPet(Squirm);
	else if (rand == 3)
		return new PixelPet(Peep);
	else if (rand == 4)
		return new PixelPet(Sprout);
	else if (rand == 5)
		return new PixelPet(Blubby);
	else if (rand == 6)
		return new PixelPet(Glob);
};

var PixelPet = function(petSpeciesObj){
	this.name = "???";
	this.petSpeciesObj = petSpeciesObj;
	this.speciesIndex = 0;
	this.species = petSpeciesObj.species[0];
	this.prevSpecies = this.species;
	this.PetFormEnum = { EGG: "EGG", BABY: "BBY", ADOLESCENT: "ADO", ADULT: "ADU" };
	this.petForm = this.PetFormEnum.EGG;
	this.formChange = false;
	this.currentDescription = "The egg is warm but doesn't move much.";
	
	this.wasHappyTeen = false;
	this.mood = 0;
	this.maxMood = 255;
	this.emotion = 0;
	
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
	
	this.BabyGrow = function(){
		if (this.petForm == this.PetFormEnum.BABY){
			this.petForm = this.PetFormEnum.ADOLESCENT;
			if (this.mood >= 128){
				this.wasHappyTeen = true;
				this.aniY++;
				this.speciesIndex++;
			}else{
				this.wasHappyTeen = false;
				this.aniY += 2;
				this.speciesIndex += 2;
			}
			this.species = this.petSpeciesObj.species[this.speciesIndex];
		}
	}
	
	this.AdolescentGrow = function(){
		if (this.petForm == this.PetFormEnum.ADOLESCENT){
			this.petForm = this.PetFormEnum.ADULT;
			if (this.mood >= 128){
				if (this.wasHappyTeen){
					this.aniY += 2;
					this.speciesIndex += 2;
				}
				else{ 
					this.aniY += 3;
					this.speciesIndex += 3;
				}
			}else{
				if (this.wasHappyTeen){
					this.aniY += 3;
					this.speciesIndex += 3;
				}
				else{ 
					this.aniY += 4;
					this.speciesIndex += 4;
				}
			}
			this.species = this.petSpeciesObj.species[this.speciesIndex];
		}
	}
	
	this.Update = function(imageId){
		if (this.emotion > 0) this.emotion--;
		if (this.emotion < 0) this.emotion++;
		if (this.emotion > 0){ //ALTER MOOD
			this.mood++;
			this.expTimer++;
		}
		else if (this.emotion < 0)
			this.mood--;
		if (this.mood < 0) this.mood = 0;
			if (this.mood > 255) this.mood = 255;
	
		this.UpdatePetForm();
		this.UpdateDescriptionAndAnimationSpeed();
		this.UpdateAnimation(imageId);
	};
	
	this.UpdatePetForm = function(){
		var thisTime = new Date().getTime() / 1000;
		this.expTimer += (thisTime - this.lastTime);
		if (this.expTimer >= this.nextEventTime){
			this.lastEventTime = new Date().getTime() / 1000; //In seconds
			this.expTimer = this.lastEventTime;
			
			if (this.petForm == this.PetFormEnum.EGG){	//PET HATCHED FROM THE EGG
				this.EggHatched();
				this.formChange = true;
				
				//SET LIMIT FOR EVOLUTION!!!
				this.nextEventTime = this.lastEventTime + 240; //In seconds
			}else if (this.petForm == this.PetFormEnum.BABY){ //PET GROW FROM BABY TO TEEN
				this.BabyGrow();
				this.formChange = true;
				
				//SET LIMIT FOR EVOLUTION!!!
				this.nextEventTime = this.lastEventTime + 480; //In seconds
			}else if (this.petForm == this.PetFormEnum.ADOLESCENT){ //PET GROW FROM TEEN TO ADULT
				this.AdolescentGrow();
				this.formChange = true;
				
				//SET LIMIT FOR EVOLUTION!!!
				this.nextEventTime = this.lastEventTime + 960; //In seconds
			}else{ //PetFormEnum.ADULT
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
		
		if (this.emotion != 0)
			this.frameCountLimit /= 2;
	};
	
	this.UpdateAnimation = function(imageId){
		if (++this.frameCount >= this.frameCountLimit){
			this.frameCount = 0;
			this.mood--; 
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