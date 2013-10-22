var AddSproutPetsToCodex = function(){
	//ADD EGG TO CODEX
	var CodexPet = new PixelPet(Sprout);
	CodexPet.codexDescription = "A leafy, veiny egg.";
	userCodex.push(CodexPet);

	//ADD SPROUT TO CODEX
	var CodexPet = new PixelPet(Sprout);
	CodexPet.EggHatched();
	CodexPet.codexDescription = "A clump of dirt came to life by the life-giving seed of Sprout. The leaves on its head can be used to hover down from great heights!";
	userCodex.push(CodexPet);
	
	//ADD BLOOM TO CODEX
	CodexPet = new PixelPet(Sprout);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.codexDescription = "Bloom emits a sweet frangrance from their flower-face. Allows owners to enjoy the smell, but secretly thinks they are weird for doing so.<br/>Adolescent form of happy Sprouts.";
	userCodex.push(CodexPet);
	
	//ADD LEAFER TO CODEX
	CodexPet = new PixelPet(Sprout);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.codexDescription = "Leafer is a lil' cutie. Having grown tiny feet, they enjoy tap-dancing and kicking soda cans.<br/>Adolescent form of lonely Sprouts.";
	userCodex.push(CodexPet);
	
	//ADD ROSELY TO CODEX
	CodexPet = new PixelPet(Sprout);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "The most beautiful pet of them all. Signed portraits of Rosely are exchanged between young couples as signs of their affection.<br/>Adult form of happy Blooms.";
	userCodex.push(CodexPet);
	
	//ADD BUNGUS TO CODEX
	CodexPet = new PixelPet(Sprout);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.mood = 0;
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Gives off a funky odor that makes nearby people dizzy. Really likes pizza but hates pop culture.<br/>Adult form of lonely Blooms.";
	userCodex.push(CodexPet);
	
	//ADD SPINEEDLE TO CODEX
	CodexPet = new PixelPet(Sprout);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.mood = 255;
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Spineedle are very proud of their strength. Will often challenge passerbies to fisticuffs.<br/>Adult form of happy Leafers.";
	userCodex.push(CodexPet);
	
	//ADD PUFFTREE TO CODEX
	CodexPet = new PixelPet(Sprout);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "The lumps on its head are tiny Peeps making nests. Pufftrees are good-hearted, but don't really know how to be nice.<br/>Adult form of lonely Leafers.";
	userCodex.push(CodexPet);
	
	//ADD EGG TO CODEX
	var CodexPet = new PixelPet(Sprout);
	CodexPet.codexDescription = "A leafy, veiny egg.";
	userCodex.push(CodexPet);
};