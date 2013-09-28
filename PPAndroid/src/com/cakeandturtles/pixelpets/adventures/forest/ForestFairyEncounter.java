package com.cakeandturtles.pixelpets.adventures.forest;

import java.util.ArrayList;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.adventures.Adventure;
import com.cakeandturtles.pixelpets.adventures.AdventureOption;
import com.cakeandturtles.pixelpets.adventures.Quest;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.collectables.DryLeaf;
import com.cakeandturtles.pixelpets.items.collectables.MoonLeaf;
import com.cakeandturtles.pixelpets.items.collectables.StarLeaf;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class ForestFairyEncounter extends Adventure{
	private static final long serialVersionUID = 5069651703490881240L;

	public ForestFairyEncounter(PixelPet activePet){
		super(activePet);
		Description = activePet.Name + " enter's the glade of the Fairy of the Forest.";
		
		Option1 = new AdventureOption("Request Quest", 0, 2, 0);
		Option1.Result = "You humbly beg the Forest Fairy for a quest.";
		AdventureQuest = new ForestFairyQuest();
		Option1.ResultingQuest = AdventureQuest;
		
		Option2 = new AdventureOption("Nevermind", 0, 2, 0);
		Option2.Result = "You and " + activePet.Name + " leave the glade.";
		
		RelAniX = 2;
		RelAniY = 0;
	}
	
	@Override
	public void CompleteQuest(Quest quest, boolean isQuestCompleted){
		int exp = 0;
		if (isQuestCompleted) exp = questPet.GetScaledExp(2);
		
		Option1 = new AdventureOption("Finish Quest", exp, 2, 0);
		if (isQuestCompleted){
			Option1.Result = questPet.Name + " gives the " + quest.GetItems() + " to the Forest Fairy.\n\"Come back to the glade when you want another quest.\"";	
			Option1.SubtractItems = quest.GetItemsAsArray();
			Option1.SubtractQuest = quest;
			Option1.RestoresEnergy = true;
			switch (PPApp.AppRandom.nextInt(5)){
				case 0: Option1.AmbitionModifier = 10; break;
				case 1: Option1.CharmModifier = 10; break;
				case 2: Option1.DiligenceModifier = 10; break;
				case 3: Option1.EmpathyModifier = 10; break;
				case 4: Option1.InsightModifier = 10; break;
				default: break;
			}
		}else{
			Option1.Result = "You do not yet have " + quest.GetItems() + ". \nThe Forest Fairy tells you to come back later.";
		}
		
		Option2 = new AdventureOption("Nevermind", 0, 2, 0);
		Option2.Result = questPet.Name + " and you decide to complete the quest later.";
		
		Option3 = null;
		Option4 = null;
	}
}

class ForestFairyQuest extends Quest{
	private static final long serialVersionUID = 9195070544405311288L;
	
	public ForestFairyQuest(){
		Name = "Forest Fairy Quest";
		
		PetItem neededLeaves;
		switch (PPApp.AppRandom.nextInt(3)){
			case 0: neededLeaves = new StarLeaf(); break;
			case 1: neededLeaves = new MoonLeaf(); break;
			case 2: neededLeaves = new DryLeaf(); break;
			default: neededLeaves = new StarLeaf(); break; //??? not needed if rand int matches up with switch cases
		}
		
		neededLeaves.Quantity = 3;
		
		NeededItems = new ArrayList<PetItem>();
		NeededItems.add(neededLeaves);
		
		Description = "\"Fetch me " + GetItems() +  "!\" the Fairy demands!";
	}
}
