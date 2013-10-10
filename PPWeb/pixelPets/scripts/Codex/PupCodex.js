var AddPupPetsToCodex = function(){
	//ADD EGG TO CODEX
	var CodexPet = new PixelPet(Pup);
	CodexPet.codexDescription = "A warm and fuzzy spotted egg.";
	userCodex.push(CodexPet);
	
	//ADD PUP TO CODEX
	var CodexPet = new PixelPet(Pup);
	CodexPet.EggHatched();
	CodexPet.codexDescription = "After hatching, pups will cling to their mothers' side for warmth.<br/>Pups make mewing barks in order to lure people into thinking they're cute.";
	userCodex.push(CodexPet);
	
	//ADD CATTA TO CODEX
	CodexPet = new PixelPet(Pup);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.codexDescription = "Catta are a little shy to most people, but form very strong bonds with their guardians.<br/>The adolescent form of happy pups.";
	userCodex.push(CodexPet);
	
	//ADD WOOFER TO CODEX
	CodexPet = new PixelPet(Pup);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.codexDescription = "Having felt rejected during youth, young Pups grow into Woofers, who think they are \"too cool\" to take baths or go to school.";
	userCodex.push(CodexPet);
	
	//ADD MEWGO TO CODEX
	CodexPet = new PixelPet(Pup);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Pretty much the perfect pet, Mewgo are very loving and said to bring good luck to those around them.<br/>Meows sassily sometimes but doesn't really mean it.";
	userCodex.push(CodexPet);
	
	//ADD LEORATH TO CODEX
	CodexPet = new PixelPet(Pup);
	CodexPet.EggHatched();
	CodexPet.BabyGrow();
	CodexPet.mood = 0;
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Having been ignored after being loved as a child, Leoraths are often very confused, and tend to resolve that by getting \"punky\" haircuts and listening to heavy metal bands.";
	userCodex.push(CodexPet);
	
	//ADD SABRE TO CODEX
	CodexPet = new PixelPet(Pup);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.mood = 255;
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "Sabres grow their teeth out and compare them with one another for bragging rights.<br/>They're actually quite useless, and often make eating out of a food bowl difficult.";
	userCodex.push(CodexPet);
	
	//ADD PEGACORN TO CODEX
	CodexPet = new PixelPet(Pup);
	CodexPet.EggHatched();
	CodexPet.mood = 0;
	CodexPet.BabyGrow();
	CodexPet.AdolescentGrow();
	CodexPet.codexDescription = "After not going to school for some time, Woofer who reject all society transcend reality and transform into the legendary Pegacorn.<br/>They use their horns to telepathically communicate.";
	userCodex.push(CodexPet);
	
	//ADD EGG TO CODEX
	var CodexPet = new PixelPet(Pup);
	CodexPet.codexDescription = "A warm and fuzzy spotted egg.";
	userCodex.push(CodexPet);
};