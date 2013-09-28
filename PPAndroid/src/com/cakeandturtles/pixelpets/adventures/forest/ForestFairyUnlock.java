package com.cakeandturtles.pixelpets.adventures.forest;

import com.cakeandturtles.pixelpets.adventures.Adventure;
import com.cakeandturtles.pixelpets.managers.AdventureManager;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class ForestFairyUnlock extends Adventure{
	private static final long serialVersionUID = 4587089125590131833L;

	public void InitialEffect(AdventureManager myAdventures){
		myAdventures.LostWoods.IsFairyUnlocked = true;
	}
	
	public ForestFairyUnlock(PixelPet activePet){
		super(activePet);
		Description = "While searching through the Forest, " + activePet.Name + " comes upon a clearing.\nIn the center is the Forest Fairy:\n\"Come seek my company when you wish to receive my blessing!\"";
		
		RelAniX = 2;
		RelAniY = 0;
	}
}
