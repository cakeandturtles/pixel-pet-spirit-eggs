var AddSquirmPetsToCodex = function(){
	//ADD EGG TO CODEX
	var CodexPet = new PixelPet(Squirm);
	CodexPet.codexDescription = "A zig-zag patterned, scaly egg.";
	userCodex.push(CodexPet);

	//ADD SQUIRM TO CODEX
	var CodexPet = new PixelPet(Squirm);
	CodexPet.EggHatched();
	CodexPet.codexDescription = "Both squishy and scaley, living in both water and mud, Squirm is a conundrum that has confused scientists for years.";
	userCodex.push(CodexPet);
	
	//ADD DRAGO TO CODEX
	CodexPet = new PixelPet(Squirm);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.codexDescription = "Happy Squirm grow into the tough looking Drago. Despite their tough exterior, they have a heart of gold, and a fondness for soap operas.";
	userCodex.push(CodexPet);
	
	//ADD FROAKLET TO CODEX
	CodexPet = new PixelPet(Squirm);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.codexDescription = "Lonely Squirm grow into good ol' Froaklet. Extends the pouch below his mouth in order to defend territory and attract potential mates. No one knows why they think that doing so is a good idea.";
	userCodex.push(CodexPet);
	
	//ADD GROWLASAUR TO CODEX
	CodexPet = new PixelPet(Squirm);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Raaaauwr!! Growlasaur likes to scare his friends with his roar, but always feels bad afterwords and buys them icecream.<br/>Adult form of happy Drago.";
	userCodex.push(CodexPet);
	
	//ADD HYDRANAMO TO CODEX
	CodexPet = new PixelPet(Squirm);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.mood = 0;
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Hydranamo has identity issues, due to their arms having developed into fully functional heads. Likes to beatbox.<br/>Adult form of lonely Drago.";
	userCodex.push(CodexPet);
	
	//ADD FEEBCROAK TO CODEX
	CodexPet = new PixelPet(Squirm);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.mood = 255;
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "The horn on the back of Feebcroak plays a haunting melody that is said to lure travelers into kissing them, thinking that a Prince or Princess will appear in their place. This is never the case.";
	userCodex.push(CodexPet);
	
	//ADD DRANAMUS TO CODEX
	CodexPet = new PixelPet(Squirm);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Adult form of lonely Froaklets, the sea serpent Dranamus is a great swimmer. Sun-bathing Dranamus may be the explanation for rumors of Mermaids.";
	userCodex.push(CodexPet);
	
	//ADD EGG TO CODEX
	var CodexPet = new PixelPet(Squirm);
	CodexPet.codexDescription = "A zig-zag patterned, scaly egg.";
	userCodex.push(CodexPet);
};