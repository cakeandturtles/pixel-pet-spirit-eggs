var PetSpecies = function(species, eggSpecies, aniX, aniY, index){
	this.species = [];
	this.eggSpecies = eggSpecies;
	this.species.push(species);
	this.aniX = aniX;
	this.aniY = aniY;
	this.index = index;
};

var GetRandomPet = function(){
	var rand = Math.floor(Math.random()*9);
	
	var loop = true;
	while (loop){
		loop = false;
		for (var i = 0; i < userPets.length; i++){
			if (userPets[i].speciesObj.index == rand){
				rand++;
				if (rand > 8) rand = 0;
				loop = true;
				break;
			}
		}
	}
	
	if (rand == 0)
		return new PixelPet(Pup);
	else if (rand == 1)
		return new PixelPet(Hog);
	else if (rand == 2)
		return new PixelPet(Pillar);
	else if (rand == 3)
		return new PixelPet(Squirm);
	else if (rand == 4)
		return new PixelPet(Peep);
	else if (rand == 5)
		return new PixelPet(Sprout);
	else if (rand == 6)
		return new PixelPet(Blubby);
	else if (rand == 7)
		return new PixelPet(Glob);
	else if (rand == 8)
		return new PixelPet(Dribble);
};

//Mammal pets 1
var Pup = 		new PetSpecies("Pup", "Spot Egg", 0, 	0, 0);
Pup.species = ["Pup", "Catta", "Woofer", "Mewgo", "Leorath", "Sabre", "Pegacorn"];

//Mammal pets 2
var Hog = 		new PetSpecies("Hog", "Reverse Spot Egg", 2, 	0, 1);
Hog.species = ["Hog", "Lemoo", "Spinehog", "Meepkin", "Teddy", "Nosehog", "Mammo"];


//Insectoid pets
var Pillar = 	new PetSpecies("Pillar", "Stripe Egg",	4, 	0, 2);
Pillar.species = ["Pillar", "Caterbeak", "Turtbug", "Mothipede", "Flyhorn", "Rhynodder", "Pinsect"];

//Reptilian/Amphiboid pets
var Squirm = 	new PetSpecies("Squirm", "Zig-Zag Egg",	6, 	0, 3);
Squirm.species = ["Squirm", "Drago", "Froaklet", "Growlasaur", "Hydranamo", "Feebcroak", "Dranamus"];

//Bird pets
var Peep = 		new PetSpecies("Peep", "Speckled Egg",	8, 	0, 4);
Peep.species = ["Peep", "Fluff", "Crackle", "King Fluff", "Archeotyx", "Gribfin", "Kiwi"];

//Vegetable pets
var Sprout = 	new PetSpecies("Sprout", "Leafy Egg",	10,	0, 5);
Sprout.species = ["Sprout", "Bloom", "Leafer", "Rosely", "Bungus", "Spineedle", "Pufftree"];

//Aquatic pets 1
var Blubby = 	new PetSpecies("Blubby", "Oily Egg",	12,	0, 6);
Blubby.species = ["Blubby", "Sporcle", "Dewgeel", "Dopple", "Cranish", "Nargle", "Whalemon"];

//Aquatic pets 2
var Glob = 		new PetSpecies("Glob", "Shell Egg",	14,	0, 7);
Glob.species = ["Glob", "Clicks", "Clamshul", "Crawspin", "Octosquid", "Crustle", "Pinprick"];

//Spooky pets
var Dribble = 	new PetSpecies("Dribble", "Blank Egg",	16,	0, 8);
Dribble.species = ["Dribble", "Wicker", "Spookton", "Jackoman", "Regaltaur", "Phantord", "Spooder"];