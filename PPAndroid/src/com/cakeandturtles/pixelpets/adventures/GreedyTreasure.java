package com.cakeandturtles.pixelpets.adventures;

import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.battle.GhostPuppet;
import com.cakeandturtles.pixelpets.items.battle.Substitute;
import com.cakeandturtles.pixelpets.items.medicine.Ether;
import com.cakeandturtles.pixelpets.items.medicine.PhoenixDown;
import com.cakeandturtles.pixelpets.items.medicine.RedPotion;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class GreedyTreasure extends Adventure{
	private static final long serialVersionUID = 8787110045052095358L;

	public GreedyTreasure(PixelPet activePet){
		super(activePet);
		Description = activePet.Name + " stumbles across a shiny treasure chest. "+activePet.He(true)+" hears a voice say \"Do not be consumed by greed!\"";
		RelAniX = 0;
		RelAniY = 0;
		
		Option1 = new AdventureOption("Open it", questPet.GetScaledExp(5), RelAniX, RelAniY);
		Option1.Result = activePet.Name + " opens the treasure chest. Inside there are two gold dubloons and a stern warning about greed.";
		Option1.AmbitionModifier = 10;
		Option1.EmpathyModifier = -5;
		
		Option2 = new AdventureOption("Wait around", 0, RelAniX, RelAniY);
		Option2.Result = "You and " + activePet.Name + " just hang around for a while. Not much happens.";
		
		Option3 = new AdventureOption("Search area", questPet.GetScaledExp(8), RelAniX, RelAniY);
		Option3.Result = activePet.Name + " searches the area and finds a mysterious glowing rock. It shouts, \"Put me down!\" but you take it with you anyway.";
		Option3.ResultingItems = new PetItem[]{ new RedPotion(4), new Ether(3), new Substitute(3), new GhostPuppet(3), new PhoenixDown(3) };
		Option3.EmpathyModifier = 5;
		
		Option4 = new AdventureOption("Run away", 0, RelAniX, RelAniY);
		Option4.Result = "Heeding the warning, you and " + activePet.Name + " flee back to the garden.";
		Option4.AmbitionModifier = -10;
	}
}
