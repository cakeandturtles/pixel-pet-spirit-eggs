var PetSpecies = function(species, aniX, aniY){
	this.species = [];
	this.species.push(species);
	this.aniX = aniX;
	this.aniY = aniY;
};

var GetRandomPet = function(){
	var rand = Math.floor(Math.random()*9);
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
var Pup = 		new PetSpecies("Pup", 		0, 	0);
Pup.species = ["Pup", "Catta", "Woofer", "Mewgo", "Leorath", "Sabre", "Pegacorn"];

//Mammal pets 2
var Hog = 		new PetSpecies("Hog", 		2, 	0);
Hog.species = ["Hog", "Lemoo", "Spinehog", "Meepkin", "Teddy", "Nosehog", "Mammo"];


//Insectoid pets
var Pillar = 	new PetSpecies("Pillar", 	4, 	0);
Pillar.species = ["Pillar", "Caterbeak", "Turtbug", "Mothipede", "Flyhorn", "Rhynodder", "Pinsect"];

//Reptilian/Amphiboid pets
var Squirm = 	new PetSpecies("Squirm", 	6, 	0);
Squirm.species = ["Squirm", "Drago", "Froaklet", "Growlasaur", "Hydranamo", "Feebcroak", "Dranamus"];

//Bird pets
var Peep = 		new PetSpecies("Peep", 		8, 	0);
Peep.species = ["Peep", "Fluff", "Crackle", "King Fluff", "Archeotyx", "Gribfin", "Kiwi"];

//Vegetable pets
var Sprout = 	new PetSpecies("Sprout", 	10,	0);
Sprout.species = ["Sprout", "Bloom", "Leafer", "Rosely", "Bungus", "Spineedle", "Pufftree"];

//Aquatic pets 1
var Blubby = 	new PetSpecies("Blubby", 	12,	0);
Blubby.species = ["Blubby", "Sporcle", "Dewgeel", "Dopple", "Cranish", "Nargle", "Whalemon"];

//Aquatic pets 2
var Glob = 		new PetSpecies("Glob", 		14,	0);
Glob.species = ["Glob", "Clicks", "Clamshul", "Crawspin", "Octosquid", "Crustle", "Pinprick"];

//Spooky pets
var Dribble = 	new PetSpecies("Dribble", 	16,	0);
Dribble.species = ["Dribble", "Wicker", "Spookton", "Jackoman", "Regaltaur", "Phantord", "Spooder"];