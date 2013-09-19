var Flutterpod = function(){
	PixelPet.call(this);
	this.name = "Flutterpod";
	this.species = "Flutterpod";
	this.aniX = 2;
	this.aniY = 2;
};

//inherit from PixelPet
Flutterpod.prototype = new PixelPet();

//correct the constructor pointer because it points to PixelPet
Flutterpod.prototype.constructor = Flutterpod;
