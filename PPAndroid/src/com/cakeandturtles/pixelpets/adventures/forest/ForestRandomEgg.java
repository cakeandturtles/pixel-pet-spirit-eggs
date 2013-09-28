package com.cakeandturtles.pixelpets.adventures.forest;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.adventures.Adventure;
import com.cakeandturtles.pixelpets.adventures.AdventureOption;
import com.cakeandturtles.pixelpets.managers.TrainerObject;
import com.cakeandturtles.pixelpets.pets.Chloropillar;
import com.cakeandturtles.pixelpets.pets.Fledgwing;
import com.cakeandturtles.pixelpets.pets.Marmoss;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class ForestRandomEgg extends Adventure{
	private static final long serialVersionUID = -8671039793631373577L;

	public ForestRandomEgg(PixelPet activePet, TrainerObject trainer){
		super(activePet);
		Description = activePet.Name + " stumbles across a lonely egg sitting in a bed of leaves.";
		
		PixelPet egg = null;
		int rand = PPApp.AppRandom.nextInt(3);
		if (rand == 0)
			egg = new Chloropillar(trainer.TrainerName, trainer.TrainerID);
		else if (rand == 1)
			egg = new Marmoss(trainer.TrainerName, trainer.TrainerID);
		else if (rand == 2)
			egg = new Fledgwing(trainer.TrainerName, trainer.TrainerID);
		
		
		Option1 = new AdventureOption("Take Egg", 0, 0, 1);
		Option1.Result = "You reach down and take the egg from the leaves.";
		Option1.EggEncounter = egg;
		eggEncounter = egg;
		
		Option2 = new AdventureOption("Leave it", 0, 0, 1);
		Option2.Result = "You leave the egg where it rests.";
	}
}
