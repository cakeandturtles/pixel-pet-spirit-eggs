var PetSpecies = function(species, aniX, aniY){
	this.species = [];
	this.species.push(species);
	this.aniX = aniX;
	this.aniY = aniY;
};

//Mammal pets
var Hog = 		new PetSpecies("Hog", 		0, 	0);
Hog.species = ["Hog", "Catta", "Spinehog", "Mewgo", "Meepkin", "Sabre", "Nosehog"];


//Insectoid pets
var Pillar = 	new PetSpecies("Pillar", 	2, 	0);
Pillar.species = ["Pillar", "Caterbeak", "Turtbug", "Mothipede", "Flyhorn", "Rhynodder", "Pinsect"];

//Reptilian/Amphiboid pets
var Squirm = 	new PetSpecies("Squirm", 	4, 	0);
Squirm.species = ["Squirm", "Drago", "Froaklet", "Growlasaur", "Hydranamo", "Feebcroak", "Dranamus"];

//Bird pets
var Peep = 		new PetSpecies("Peep", 		6, 	0);
Peep.species = ["Peep", "Fluff", "Crackle", "King Fluff", "Archeotyx", "Gribfin", "Kiwi"];

//Vegetable pets
var Sprout = 	new PetSpecies("Sprout", 	8, 	0);
Sprout.species = ["Sprout", "Bloom", "Leafer", "Rosely", "Bungus", "Spineedle", "Pufftree"];

//Aquatic pets 1
var Blubby = 	new PetSpecies("Blubby", 	10,	0);
Blubby.species = ["Blubby", "Sporcle", "Dewgeel", "Dopple", "Cranish", "Nargle", "Whalemon"];

//Aquatic pets 2
var Glob = 		new PetSpecies("Glob", 		12,	0);
Glob.species = ["Glob", "Clicks", "Helmutt", "Crawspin", "Octosquid", "Crustle", "Pinprick"];