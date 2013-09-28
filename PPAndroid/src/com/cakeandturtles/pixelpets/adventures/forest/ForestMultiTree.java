package com.cakeandturtles.pixelpets.adventures.forest;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.adventures.Adventure;
import com.cakeandturtles.pixelpets.adventures.AdventureOption;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.fruits.BugFruit;
import com.cakeandturtles.pixelpets.items.fruits.SkyFruit;
import com.cakeandturtles.pixelpets.items.fruits.VeggieFruit;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class ForestMultiTree extends Adventure{
	private static final long serialVersionUID = 9001817595598372240L;

	public ForestMultiTree(PixelPet activePet){
		super(activePet);
		Description = activePet.Name + " finds the Multi-Tree!";
		RelAniX = 0;
		RelAniY = 1;
		
		Option1 = new AdventureOption("Shake Tree", 0, RelAniX, RelAniY);
		Option1.Result = activePet.Name + " shake the Multi-Tree!";
		Option1.AmbitionModifier = 5;
		
		if (PPApp.AppRandom.nextInt(3) == 0){
			Option1.Result += "\nSURPRISE ATTACK!!!\nA swarm of bees flies out and stings " + activePet.Name + "!";
		}else{
			Option1.Result += "\nSome leaves (and fruits) fall, and " + activePet.Name + " picks some up before leaving.";
			VeggieFruit vegetables = new VeggieFruit(); vegetables.Quantity = 0;
			BugFruit buggies = new BugFruit(); buggies.Quantity = 0;
			SkyFruit skies = new SkyFruit(); skies.Quantity = 0;
			for (int i = 0; i < 5; i++){
				int rand = PPApp.AppRandom.nextInt(3);
				if (rand == 0) vegetables.Quantity++;
				else if (rand == 1) buggies.Quantity++;
				else if (rand == 2) skies.Quantity++;
			}
			Option1.ResultingItems = new PetItem[]{ vegetables, buggies, skies };
		}
		
		Option2 = new AdventureOption("Nevermind", 0, RelAniX, RelAniY);
		Option2.Result = "You and " + activePet.Name + " decide to leave the Tree alone.";
		Option2.AmbitionModifier = -5;
	}
}
