var Lunactulus = function(){
	PixelPet.call(this);
	this.name = "Lunactulus";
	this.species = "Lunactulus";
	this.aniX = 2;
	this.aniY = 4;
};

//inherit from PixelPet
Lunactulus.prototype = new PixelPet();

//correct the constructor pointer because it points to PixelPet
Lunactulus.prototype.constructor = Lunactulus;
