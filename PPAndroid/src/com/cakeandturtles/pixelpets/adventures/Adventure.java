package com.cakeandturtles.pixelpets.adventures;

import java.io.Serializable;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.adventures.forest.ForestFairyEncounter;
import com.cakeandturtles.pixelpets.adventures.forest.ForestGhostEncounter;
import com.cakeandturtles.pixelpets.adventures.forest.ForestLakeHeal;
import com.cakeandturtles.pixelpets.adventures.forest.ForestMultiTree;
import com.cakeandturtles.pixelpets.adventures.forest.ForestRandomBattle;
import com.cakeandturtles.pixelpets.adventures.forest.ForestRandomEgg;
import com.cakeandturtles.pixelpets.adventures.forest.ForestRandomItem;
import com.cakeandturtles.pixelpets.adventures.forest.ForestTreasure;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.managers.AdventureManager;
import com.cakeandturtles.pixelpets.managers.Inventory;
import com.cakeandturtles.pixelpets.managers.TrainerObject;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public abstract class Adventure implements Serializable{
	private static final long serialVersionUID = 2834339863317130325L;
	
	public String Description;
	public String[] EnemyPets;
	public PixelPet questPet;
	public PixelPet eggEncounter;
	public PetItem itemEncounter;
	public Quest AdventureQuest;
	public AdventureOption Option1;
	public AdventureOption Option2;
	public AdventureOption Option3;
	public AdventureOption Option4;
	public int RelAniX;
	public int RelAniY;
	
	public Adventure(PixelPet activePet){
		Description = "";
		EnemyPets = null;
		questPet = activePet;
		eggEncounter = null;
		itemEncounter = null;
		AdventureQuest = null;
		Option1 = null;
		Option2 = null;
		Option3 = null;
		Option4 = null;
		RelAniX = 0;
		RelAniY = 0;
	}
	
	public void InitialEffect(AdventureManager myAdventures){
		
	}
	
	public void CompleteQuest(Quest whichQuest, boolean isQuestCompleted){
		
	}
	
	public static Adventure GetNewAdventure(PixelPet activePet, int areaIndex, AdventureManager myAdventures, Inventory inventory, TrainerObject trainer, int consecutiveCounter)
	{
		//TODO:: IMPLEMENT METHOD AND ADD ADVENTURES!!!
		if (areaIndex == 0)
			return GetForestAdventure(activePet, myAdventures, inventory, trainer, consecutiveCounter); 
		else if (areaIndex == 1)
			return GetPoisonLakeAdventure(activePet, myAdventures, inventory, trainer, consecutiveCounter);
		else if (areaIndex == 2)
			return GetCaveAdventure(activePet, myAdventures, inventory, trainer, consecutiveCounter);
		else if (areaIndex == 3)
			return new GreedyTreasure(activePet); //TODO::!!
		
		
		return new GreedyTreasure(activePet);
	}
	
	public static Adventure GetNewFairyQuest(PixelPet activePet, int areaIndex, AdventureManager myAdventures, Inventory inventory, TrainerObject trainer)
	{
		//TODO:: IMPLEMENT METHOD AND ADD ADVENTURES!!!
		if (areaIndex == 0)
			return new ForestFairyEncounter(activePet);
		else if (areaIndex == 1){}
		else if (areaIndex == 2){}
		else if (areaIndex == 3){}
		
		return new ForestFairyEncounter(activePet);
	}
	
	public static Adventure GetForestAdventure(PixelPet activePet, AdventureManager myAdventures, Inventory inventory, TrainerObject trainer, int consecutiveCounter){
		int rand = PPApp.AppRandom.nextInt(100 + Math.round(((float)11*consecutiveCounter)/20));
		
		int helper = 10 + consecutiveCounter/10;
		if (rand < helper){} // RANDOM BOSS
		
		helper += 2 + consecutiveCounter/5; //RANDOM EGG
		if (rand < helper)
			return new ForestRandomEgg(activePet, trainer);
		
		helper += 8; //RANDOM TREASURE
		if (rand < helper)
			return new ForestTreasure(activePet);
		
		helper += 8; //RANDOM BATCH ITEM FIND
		if (rand < helper)
			return new ForestMultiTree(activePet);
		
		helper += 5 + consecutiveCounter/20;
		if (rand < helper)
			return new ForestLakeHeal(activePet);
		
		helper += 5 + consecutiveCounter/10;
		if (rand < helper)
			return new ForestRandomItem(activePet, inventory);
		
		helper += 3 + consecutiveCounter/10;
		if (rand < helper)
			return new ForestGhostEncounter(activePet);

		
		//RANDOM BATTLE IS THE DUMP ADVENTURE
		return new ForestRandomBattle(activePet, consecutiveCounter);  
	}
	
	public static Adventure GetPoisonLakeAdventure(PixelPet activePet, AdventureManager myAdventures, Inventory inventory, TrainerObject trainer, int consecutiveCounter){
		int rand = PPApp.AppRandom.nextInt(100);
		
		if (rand < 50){
			return new PoisonRandomEgg(activePet, trainer);
		}
		
		return new PoisonRandomBattle(activePet, consecutiveCounter);
	}
	
	public static Adventure GetCaveAdventure(PixelPet activePet, AdventureManager myAdventures, Inventory inventory, TrainerObject trainer, int consecutiveCounter){
		int rand = PPApp.AppRandom.nextInt(100);
		
		if (rand < 50){
			return new CaveRandomEgg(activePet, trainer);
		}
		
		return new CaveRandomBattle(activePet, consecutiveCounter);
	}
}
