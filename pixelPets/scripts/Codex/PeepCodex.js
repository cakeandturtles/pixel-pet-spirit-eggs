var AddPeepPetsToCodex = function(){
	//ADD EGG TO CODEX
	var CodexPet = new PixelPet(Peep);
	CodexPet.codexDescription = "A speckled egg.";
	userCodex.push(CodexPet);
	
	//ADD PEEP TO CODEX
	var CodexPet = new PixelPet(Peep);
	CodexPet.EggHatched();
	CodexPet.codexDescription = "Named after the noise it makes after hatching, Peeps love attention and will run circles around you constantly if you do not pet it.";
	userCodex.push(CodexPet);
	
	//ADD FLUFF TO CODEX
	CodexPet = new PixelPet(Peep);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.codexDescription = "Fluff struts his stuff and knows that he's all that.<br/>The adolescent form of happy Peeps.";
	userCodex.push(CodexPet);
	
	//ADD CRACKLE TO CODEX
	CodexPet = new PixelPet(Peep);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.codexDescription = "Strangely enough, Peeps will crawl back into their discarded egg-shell if allowed to become lonely. From inside a faint peeping and sound of disappointment can still be heard.";
	userCodex.push(CodexPet);
	
	//ADD KING FLUFF TO CODEX
	CodexPet = new PixelPet(Peep);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "The \"King\" in the species name is royalty in name only. King Fluff acts as a figurehead and has no actual power.<br/>Adult form of happy Fluffs.";
	userCodex.push(CodexPet);
	
	//ADD ARCHEOTYX TO CODEX
	CodexPet = new PixelPet(Peep);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.mood = 0;
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Reverting back to a more ancient form, Archeotyx represents the \"good ol' days\" where birds could be dinosaur-birds, and no one questioned it.<br/>Adult form of lonely Fluffs.";
	userCodex.push(CodexPet);
	
	//ADD GRIBFIN TO CODEX
	CodexPet = new PixelPet(Peep);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.mood = 255;
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Deciding to crawl back out of their shell, Crackle who are happy become the awesome Gribfin. Flaps its wings furiously to time travel, but can only go about an hour or so back.";
	userCodex.push(CodexPet);
	
	//ADD KIWI TO CODEX
	CodexPet = new PixelPet(Peep);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Having not left its shell but growing up anyways, the egg-shape of the Kiwi can be explained by the fact that its body has grown around the shell, making it a part of its body.";
	userCodex.push(CodexPet);
	
	//ADD EGG TO CODEX
	var CodexPet = new PixelPet(Peep);
	CodexPet.codexDescription = "A speckled egg.";
	userCodex.push(CodexPet);
};