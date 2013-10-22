var AddHogPetsToCodex = function(){
	//ADD EGG TO CODEX
	var CodexPet = new PixelPet(Hog);
	CodexPet.codexDescription = "A warm and fuzzy reverse-spotted egg.";
	userCodex.push(CodexPet);
	
	//ADD HOG TO CODEX
	var CodexPet = new PixelPet(Hog);
	CodexPet.EggHatched();
	CodexPet.codexDescription = "Hog likes to run fast. Since they technically don't have any well-formed feet or legs, this is very difficult for them, and Hogs will often just roll around instead.";
	userCodex.push(CodexPet);
	
	//ADD LEMOO TO CODEX
	CodexPet = new PixelPet(Hog);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.codexDescription = "Content with their legless life, Hogs who are happy grow a single raccoon-like tail that they spin around quickly to hover around wherever they need to go.";
	userCodex.push(CodexPet);
	
	//ADD SPINEHOG TO CODEX
	CodexPet = new PixelPet(Hog);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.codexDescription = "The spikes on the back of Spinehog are actually very soft. They look menacing to frighten those who would otherwise call Spinehog mean names.<br/>Adolescent form of lonely Hogs.";
	userCodex.push(CodexPet);
	
	//ADD MEEPKIN TO CODEX
	CodexPet = new PixelPet(Hog);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Ook! Ook! The fox-like Lemoo has donned cool shades and decided to grow limbs after all. Is fond of fruit and rock-and-roll.<br/>Adult form of happy Lemoo.";
	userCodex.push(CodexPet);
	
	//ADD TEDDY TO CODEX
	CodexPet = new PixelPet(Hog);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.mood = 0;
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Nobody knows the true intentions of Teddy. They have very sharp claws, but use them only to scratch their belly and to help them eat berries.<br/>Adult form of lonely Lemoo.";
	userCodex.push(CodexPet);
	
	//ADD NOSEHOG TO CODEX
	CodexPet = new PixelPet(Hog);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.mood = 255;
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "An exotic form of Spinehogs who grow up happy, Nosehog is a strange Frankenstein of various other animal forms. As a result, Nosehog is wary of fire.";
	userCodex.push(CodexPet);
	
	//ADD MAMMO TO CODEX
	CodexPet = new PixelPet(Hog);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Mammo never forgets. They carry around flash-cards and study everything that ever happened to them. The size of a Mammo's flash-card stack is a measure of their wisdom.<br/>Adult form of unhappy Spinehogs.";
	userCodex.push(CodexPet);
	
	//ADD EGG TO CODEX
	var CodexPet = new PixelPet(Hog);
	CodexPet.codexDescription = "A warm and fuzzy reverse-spotted egg.";
	userCodex.push(CodexPet);
};