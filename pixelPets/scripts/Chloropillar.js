var Chloropillar = function(){
	PixelPet.call(this);
	this.name = "Chloropillar";
	this.species = "Chloropillar";
	this.aniX = 2;
	this.aniY = 0;
};

//inherit from PixelPet
Chloropillar.prototype = new PixelPet();

//correct the constructor pointer because it points to PixelPet
Chloropillar.prototype.constructor = Puglett;
