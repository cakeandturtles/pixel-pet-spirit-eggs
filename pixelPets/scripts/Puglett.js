var Puglett = function(){
	PixelPet.call(this);
	this.name = "Puglett";
	this.species = "Puglett";
	this.aniX = 0;
	this.aniY = 0;
};

//inherit from PixelPet
Puglett.prototype = new PixelPet();

//correct the constructor pointer because it points to PixelPet
Puglett.prototype.constructor = Puglett;
