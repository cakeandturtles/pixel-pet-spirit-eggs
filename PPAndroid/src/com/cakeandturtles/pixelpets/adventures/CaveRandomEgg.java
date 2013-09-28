package com.cakeandturtles.pixelpets.adventures;

import com.cakeandturtles.pixelpets.managers.TrainerObject;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.Puglett;

public class CaveRandomEgg extends Adventure{
	private static final long serialVersionUID = -866036327226187254L;

	public CaveRandomEgg(PixelPet activePet, TrainerObject trainer){
		super(activePet);
		Description = activePet.Name + " stumbles across a lonely egg sitting in a bed of gravel and mud.";
		
		PixelPet egg = new Puglett(trainer.TrainerName, trainer.TrainerID);
		Option1 = new AdventureOption("Take Egg", 0, 0, 1);
		Option1.Result = "You reach down and take the egg from the muddy gravel.";
		Option1.EggEncounter = egg;
		eggEncounter = egg;
		
		Option2 = new AdventureOption("Leave it", 0, 0, 1);
		Option2.Result = "You leave the egg where it rests.";
	}
}
