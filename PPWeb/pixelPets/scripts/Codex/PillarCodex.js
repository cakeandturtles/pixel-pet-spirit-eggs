var AddPillarPetsToCodex = function(){
	//ADD EGG TO CODEX
	var CodexPet = new PixelPet(Pillar);
	CodexPet.codexDescription = "A sticky striped egg.";
	userCodex.push(CodexPet);
	
	//ADD PILLAR TO CODEX
	var CodexPet = new PixelPet(Pillar);
	CodexPet.EggHatched();
	CodexPet.codexDescription = "Pillars are very curious creatures, using their antennae to smell everything around them.<br/>This sometimes grosses people out.";
	userCodex.push(CodexPet);
	
	//ADD CATERBEAK TO CODEX
	CodexPet = new PixelPet(Pillar);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.codexDescription = "A pillar who had a happy childhood grows up to be an entirely normal Caterbeak. There is really nothing to say about them.";
	userCodex.push(CodexPet);
	
	//ADD TURTBUG TO CODEX
	CodexPet = new PixelPet(Pillar);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.codexDescription = "Having been ignored during childhood, Turtbugs don turtle-like shells, because they think their friends will think doing so is cool.";
	userCodex.push(CodexPet);
	
	//ADD MOTHIPEDE TO CODEX
	CodexPet = new PixelPet(Pillar);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Skipping the coccoon stage entirely, happy Caterbeaks turn into the magnificent Mothipede. Flies through the skies, releasing a scaly dust from its wings that makes anyone who breathes it in tell the truth.";
	userCodex.push(CodexPet);
	
	//ADD FLYHORN TO CODEX
	CodexPet = new PixelPet(Pillar);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.mood = 0;
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Having all the most attractive features of Mothipede and Rhynodder, Flyhorn often suffers from feeling like they lack individuality.<br/>Adult form of lonely Caterbeaks.";
	userCodex.push(CodexPet);
	
	//ADD RHYNODDER TO CODEX
	CodexPet = new PixelPet(Pillar);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.mood = 255;
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Rhynodder uses its horn-like protusion to tell the future.<br/>Adult form of happy Turtbugs.";
	userCodex.push(CodexPet);
	
	//ADD PINSECT TO CODEX
	CodexPet = new PixelPet(Pillar);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Doesn't really care much about anyone but themselves. Likes to dance all day and ignore responsibilities.<br/>Adult form of lonely Turtbugs.";
	userCodex.push(CodexPet);
	
	//ADD EGG TO CODEX
	var CodexPet = new PixelPet(Pillar);
	CodexPet.codexDescription = "A sticky striped egg.";
	userCodex.push(CodexPet);
};