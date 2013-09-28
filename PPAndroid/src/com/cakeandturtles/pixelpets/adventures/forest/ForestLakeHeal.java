package com.cakeandturtles.pixelpets.adventures.forest;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.adventures.Adventure;
import com.cakeandturtles.pixelpets.adventures.AdventureOption;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class ForestLakeHeal extends Adventure{
	private static final long serialVersionUID = -4177451239728419409L;

	public ForestLakeHeal(PixelPet activePet){
		super(activePet);
		Description = activePet.Name + " stumbles upon a lake in the woods.";
		RelAniX = 2;
		RelAniY = 2;
		
		Option1 = new AdventureOption("Drink", 0, RelAniX, RelAniY);
		Option1.Result = activePet.Name + " drinks from the Forest Lake.";
			
		if (activePet.IsEnergyFullyRestored()){
			Option1.Result += "\nThe lake water isn't very exceptional this time.\nNothing happens.";
		}else{
			Option1.Result += "\nThe lake water tastes remarkable!";
			Option1.RestoresEnergy = true;
			switch (PPApp.AppRandom.nextInt(5)){
				case 0: Option1.AmbitionModifier = 5; break;
				case 1: Option1.CharmModifier = 5; break;
				case 2: Option1.DiligenceModifier = 5; break;
				case 3: Option1.EmpathyModifier = 5; break;
				case 4: Option1.InsightModifier = 5; break;
				default: break;
			}
		}
		
		Option2 = new AdventureOption("Nevermind", 0, RelAniX, RelAniY);
		Option2.Result = "You and " + activePet.Name + " decide to leave the Lake.";
	}
}
