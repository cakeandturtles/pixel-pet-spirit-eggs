package com.cakeandturtles.pixelpets.adventures.forest;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.adventures.Adventure;
import com.cakeandturtles.pixelpets.adventures.AdventureOption;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.battle.GhostPuppet;
import com.cakeandturtles.pixelpets.items.battle.Substitute;
import com.cakeandturtles.pixelpets.items.medicine.BluePotion;
import com.cakeandturtles.pixelpets.items.medicine.GreenPotion;
import com.cakeandturtles.pixelpets.items.medicine.PhoenixDown;
import com.cakeandturtles.pixelpets.items.medicine.RedPotion;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class ForestTreasure extends Adventure{
	private static final long serialVersionUID = 3295441657763407702L;

	public ForestTreasure(PixelPet activePet){
		super(activePet);
		Description = activePet.Name + " stumbles across a shiny treasure chest.";
		RelAniX = 0;
		RelAniY = 0;
		
		Option1 = new AdventureOption("Open It", 0, RelAniX, RelAniY);
		Option1.AmbitionModifier = 5;
		int rand = PPApp.AppRandom.nextInt(3);
		if (rand < 2){
			Option1.Result = activePet.Name + " opens the chest. \nInside is some treasure!";
			PetItem treasure = null; 
			if (rand == 0) treasure = new RedPotion();
			else if (rand == 1) treasure = new GreenPotion();
			else if (rand == 2) treasure = new BluePotion();
			else if (rand == 3) treasure = new Substitute();
			else if (rand == 4) treasure = new PhoenixDown();
			else if (rand == 5) treasure = new GhostPuppet();
			treasure.Quantity = 3;
			Option1.ResultingItems = new PetItem[]{ treasure };
		}else{
			Option1.Result = activePet.Name + " opens the chest. \nSURPRISE ATTACK!!!\n"+activePet.Name + " loses half HP.";
			Option1.HPMod = (int)Math.round((float)activePet.BaseHP/2.0)*(-1);
		}
		
		Option2 = new AdventureOption("Nevermind", 0, RelAniX, RelAniY);
		Option2.Result = "You and " + activePet.Name + " decide to ignore the chest.";
		Option2.AmbitionModifier = -5;
	}
}
