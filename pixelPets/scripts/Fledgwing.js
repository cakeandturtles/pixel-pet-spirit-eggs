var Fledgwing = function(){
	PixelPet.call(this);
	this.name = "Fledgwing";
	this.species = "Fledgwing";
	this.aniX = 6;
	this.aniY = 0;
};

//inherit from PixelPet
Fledgwing.prototype = new PixelPet();

//correct the constructor pointer because it points to PixelPet
Fledgwing.prototype.constructor = Fledgwing;
