var Tadpox = function(){
	PixelPet.call(this);
	this.name = "Tadpox";
	this.species = "Tadpox";
	this.aniX = 4;
	this.aniY = 0;
};

//inherit from PixelPet
Tadpox.prototype = new PixelPet();

//correct the constructor pointer because it points to PixelPet
Tadpox.prototype.constructor = Puglett;
