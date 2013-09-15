var PixelPet = function(){
	this.currFrame = 0;
	this.maxFrame = 2;
	this.frameWidth = 128;
	this.frameHeight = 128;
	this.aniX = 0;
	this.aniY = 0;
	
	this.UpdateAnimation = function(){
		if (++this.currFrame >= this.maxFrame)
			this.currFrame = 0;

		var petImage = document.getElementById("petImage");
		var petSpriteSettings = ""+((-1)*((this.frameWidth*this.currFrame)+(this.frameWidth*this.aniX)))+"px "+((-1)*this.frameHeight*this.aniY)+"px";
		petImage.style.backgroundPosition=petSpriteSettings;
	};
};