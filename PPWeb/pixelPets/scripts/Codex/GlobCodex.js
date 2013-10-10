var AddGlobPetsToCodex = function(){
	//ADD EGG TO CODEX
	var CodexPet = new PixelPet(Glob);
	CodexPet.codexDescription = "A salty, calcium-fortified egg that looks like a turtle shell.";
	userCodex.push(CodexPet);
	
	//ADD GLOB TO CODEX
	var CodexPet = new PixelPet(Glob);
	CodexPet.EggHatched();
	CodexPet.codexDescription = "A squishy microscopic sea critter. Globs swarm together on the bottoms of boats or on feet that have been standing in the ocean for too long.";
	userCodex.push(CodexPet);
	
	//ADD CLICKS TO CODEX
	CodexPet = new PixelPet(Glob);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.codexDescription = "Although the large claw looks menacing, Clicks only grow them because they think they look cool.<br/>The adolescent form of a happy Glob.";
	userCodex.push(CodexPet);
	
	//ADD CLAMSHUL TO CODEX
	CodexPet = new PixelPet(Glob);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.codexDescription = "Globs who were lonely during their child hood find it best if they \"Clam Up\". They also think that that joke is really funny.";
	userCodex.push(CodexPet);
	
	//ADD CRAWSPIN TO CODEX
	CodexPet = new PixelPet(Glob);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Crawspin grow from happy Clicks who have realised that wearing a mask really complemented their claws.";
	userCodex.push(CodexPet);
	
	//ADD OCTOSQUID TO CODEX
	CodexPet = new PixelPet(Glob);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.mood = 0;
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Disillusioned with the cool claws from their youth, Octosquid have taken up a life of writing. <br/>Various published works can be found under the pseudonym of \"John C. Squidfeller\".";
	userCodex.push(CodexPet);
	
	//ADD CRUSTLE TO CODEX
	CodexPet = new PixelPet(Glob);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.mood = 255;
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Having become comfortable in their shell, happy Clamshuls grow up to become Crustles. <br/>They don't do much, but they're very content!";
	userCodex.push(CodexPet);
	
	//ADD PINPRICK TO CODEX
	CodexPet = new PixelPet(Glob);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Pinpricks are very easily irritable, and require constant petting in order to feel loved.<br/>This proves difficult for many pet owners.";
	userCodex.push(CodexPet);
	
	//ADD EGG TO CODEX
	var CodexPet = new PixelPet(Glob);
	CodexPet.codexDescription = "A salty, calcium-fortified egg that looks like a turtle shell.";
	userCodex.push(CodexPet);
};