package com.cakeandturtles.pixelpets.adventures;

import java.io.Serializable;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.adventures.forest.ForestMultiTree;
import com.cakeandturtles.pixelpets.adventures.forest.ForestRandomEgg;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.managers.AdventureManager;
import com.cakeandturtles.pixelpets.managers.Inventory;
import com.cakeandturtles.pixelpets.managers.TrainerObject;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public abstract class Adventure implements Serializable{
	private static final long serialVersionUID = 2834339863317130325L;
	
	public String Description;
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
	
	public static Adventure GetNewAdventure(PixelPet activePet, int areaIndex, AdventureManager myAdventures, Inventory inventory, TrainerObject trainer)
	{
		//TODO:: IMPLEMENT METHOD AND ADD ADVENTURES!!!
		if (areaIndex == 0)
			return GetForestAdventure(activePet, myAdventures, inventory, trainer); 
		else if (areaIndex == 1)
			return GetPoisonLakeAdventure(activePet, myAdventures, inventory, trainer);
		else if (areaIndex == 2)
			return GetCaveAdventure(activePet, myAdventures, inventory, trainer);
		else if (areaIndex == 3)
			return new GreedyTreasure(activePet); //TODO::!!
		
		return new GreedyTreasure(activePet);
	}
	
	public static Adventure GetForestAdventure(PixelPet activePet, AdventureManager myAdventures, Inventory inventory, TrainerObject trainer){
		int rand = PPApp.AppRandom.nextInt(100);
		
		if (rand < 5) //RANDOM EGG
			return new ForestRandomEgg(activePet, trainer);
		
		//RANDOM BATCH ITEM FIND
		return new ForestMultiTree(activePet);  
	}
	
	public static Adventure GetPoisonLakeAdventure(PixelPet activePet, AdventureManager myAdventures, Inventory inventory, TrainerObject trainer){
		int rand = PPApp.AppRandom.nextInt(100);
		
		if (rand < 5)
			return new PoisonRandomEgg(activePet, trainer);
		
		return new ForestMultiTree(activePet);
	}
	
	public static Adventure GetCaveAdventure(PixelPet activePet, AdventureManager myAdventures, Inventory inventory, TrainerObject trainer){
		int rand = PPApp.AppRandom.nextInt(100);
		
		if (rand < 5)
			return new CaveRandomEgg(activePet, trainer);
		
		return new ForestMultiTree(activePet);
	}
}
