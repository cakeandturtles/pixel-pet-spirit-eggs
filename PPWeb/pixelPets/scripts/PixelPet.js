var PixelPet = function(petSpeciesObj){
	this.inCodex = false;
	this.checkedIntoCodex = false;
	
	this.canRelease = false;
	this.petSpeciesObj = petSpeciesObj;
	this.speciesIndex = 0;
	this.species = petSpeciesObj.species[0];
	this.name = this.species;
	this.eggSpecies = petSpeciesObj.eggSpecies;
	this.prevSpecies = this.species;
	this.PetFormEnum = { EGG: "EGG", BABY: "BBY", ADOLESCENT: "ADO", ADULT: "ADU" };
	this.petForm = this.PetFormEnum.EGG;
	this.formChange = false;
	this.currentDescription = "The egg is warm but doesn't move much.";
	this.codexDescription = "TODO";
	this.talkResponse = 0;
	
	this.PetCounter = 0;
	this.PetPetCounter = 0;
	this.AntiPetPetCounter = 0;
	
	this.wasHappyTeen = false;
	this.mood = 0;
	this.maxMood = 255;
	this.emotion = 0;
	
	this.lastEventTime = 0;
	this.nextEventTime = 120; //In seconds
	this.expTimer = 0;

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
	
	this.RevertToEgg = function(){
		if (this.petForm == this.PetFormEnum.ADULT){
			this.frameCount = 0;
			this.currFrame = 0;
			this.petForm = this.PetFormEnum.EGG;
			this.speciesIndex = 0;
			this.species = this.petSpeciesObj.species[this.speciesIndex];
			this.aniY = 0;
		}
	};
	
	this.EggHatched = function(){
		if (this.petForm == this.PetFormEnum.EGG){
			this.petForm = this.PetFormEnum.BABY;
			this.aniY++;
			this.mood = 132;
			this.checkedIntoCodex = false;
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
			this.checkedIntoCodex = false;
		}
	}
	
	this.AdolescentGrow = function(){
		if (this.petForm == this.PetFormEnum.ADOLESCENT){
			this.canRelease = true;
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
			this.checkedIntoCodex = false;
		}
	}
	
	this.Update = function(imageId){
		if (this.emotion > 0) this.emotion--;
		if (this.emotion < 0) this.emotion++;
		if (this.emotion > 0){ //ALTER MOOD
			this.mood++;
			this.expTimer+=1;
		}
		else if (this.emotion < 0)
			this.mood--;
		if (this.mood < 0) this.mood = 0;
		if (this.mood > 255) this.mood = 255;
		
		if (this.talkResponse > 0)
			this.talkResponse--;
	
		this.UpdatePetForm();
		this.UpdateDescriptionAndAnimationSpeed();
		this.UpdateAnimation(imageId);
	};
	
	this.UpdatePetForm = function(){
		this.expTimer+=0.1;
		if (this.expTimer >= this.nextEventTime){
			this.lastEventTime = 0; 
			this.expTimer = 0;
			this.emotion = 0;
			this.PetCounter = 0;
			this.PetPetCounter = 0;
			this.AntiPetPetCounter = 0;
			this.talkResponse = 0;
			
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
				this.nextEventTime = this.lastEventTime + 560; //In seconds
			}else{ //PetFormEnum.ADULT
				this.RevertToEgg();
				this.formChange = true;
				
				//SET LIMIT FOR EGG HATCH
				this.nextEventTime = 160; //In seconds
			}
			
			if (this.name == this.prevSpecies){
				this.name = this.species;
				this.prevSpecies = this.species;
			}
			if (userLoggedIn){
				dataUpdateCounter = 230;
			}
		}
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
				if (this.talkResponse == 0)
					this.currentDescription = "It moves around a lot<br/>It must be close to hatching!";
			}else if (currTime >= timeHatched * 1.5 / 4.0){
				this.frameCountLimit = 60;
				if (this.talkResponse == 0)
					this.currentDescription = "It wiggles around now and then.";
			}else{		
				this.frameCountLimit = 120;
				if (this.talkResponse == 0)
					this.currentDescription = "The egg is warm but doesn't move much.";
			}
		}else{
			this.frameCountLimit = 12;
			
			if (this.talkResponse == 0){
				if (this.emotion < 0)
					this.currentDescription = "You've upset " + this.name + "!!!<br/>>:( &nbsp;>:( &nbsp;>:(";
				else if (this.emotion > 0)
					this.currentDescription = this.name + " is very happy :D!!!";
				else{
					if (this.mood < 128){
						if (this.mood < 64){
							this.currentDescription = this.name + " is crying :,(!!";
						}else{
							this.currentDescription = this.name + " is feeling lonely :(";
						}
					}else if (this.PetPetCounter >= 4){
						this.currentDescription = this.name + " is irritated... >:(<br/>Leave him be for a second?";
					}
					else{
						this.currentDescription = this.name + " is thrashing about :)!";
					}
				}	
			}
		}
		
		if (this.emotion != 0)
			this.frameCountLimit /= 2;
	};
	
	this.ResetAnimation = function(){
		this.frameCount = 0;
		this.currFrame = 0;
	};
	
	this.UpdateAnimation = function(imageId){
		if (++this.frameCount >= this.frameCountLimit){
			this.frameCount = 0;
			this.mood--; 
			if (this.mood < 0) this.mood = 0;
			
			if (++this.currFrame >= this.maxFrame){
				this.currFrame = 0;
			}
		}else if (this.frameCount >= this.frameCountLimit-1){
			this.PetCounter--;
			if (this.PetCounter < 0)
				this.PetCounter = 0;
		
			if (this.emotion == 0){
				this.AntiPetPetCounter++;
				if (this.AntiPetPetCounter >= 10){
					this.AntiPetPetCounter = 0;
					this.PetPetCounter = 0;
				}
			}
		}

		if (imageId != ""){
			var petImage = document.getElementById(imageId);
			if (petImage == null) return;
			var petSpriteSettings = ""+((-1)*((this.frameWidth*this.currFrame)+(this.frameWidth*this.aniX)))+"px "+((-1)*this.frameHeight*this.aniY)+"px";
			petImage.style.backgroundPosition=petSpriteSettings;
		}
	};
};