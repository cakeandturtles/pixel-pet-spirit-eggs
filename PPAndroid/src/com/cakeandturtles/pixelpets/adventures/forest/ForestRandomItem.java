package com.cakeandturtles.pixelpets.adventures.forest;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.adventures.Adventure;
import com.cakeandturtles.pixelpets.adventures.AdventureOption;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.battle.GhostPuppet;
import com.cakeandturtles.pixelpets.items.battle.Substitute;
import com.cakeandturtles.pixelpets.items.fruits.BugFruit;
import com.cakeandturtles.pixelpets.items.fruits.SkyFruit;
import com.cakeandturtles.pixelpets.items.fruits.VeggieFruit;
import com.cakeandturtles.pixelpets.items.medicine.BluePotion;
import com.cakeandturtles.pixelpets.items.medicine.GreenPotion;
import com.cakeandturtles.pixelpets.items.medicine.PhoenixDown;
import com.cakeandturtles.pixelpets.items.medicine.RedPotion;
import com.cakeandturtles.pixelpets.managers.Inventory;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class ForestRandomItem extends Adventure{
	private static final long serialVersionUID = 7772812689854149827L;

	public ForestRandomItem(PixelPet activePet, Inventory inventory){
		super(activePet);
		
		PetItem item = null;
		int rand = PPApp.AppRandom.nextInt(9);
		if (rand == 0)
			item = new RedPotion();
		else if (rand == 1)
			item = new BluePotion();
		else if (rand == 2)
			item = new GreenPotion();
		else if (rand == 3)
			item = new VeggieFruit();
		else if (rand == 4)
			item = new BugFruit();
		else if (rand == 5)
			item = new SkyFruit();
		else if (rand == 6)
			item = new PhoenixDown();
		else if (rand == 7)
			item = new Substitute();
		else if (rand == 8)
			item = new GhostPuppet();
		Description = activePet.Name + " stumbles across a " + item.Name + " sitting in a bed of leaves.";
		
		
		Option1 = new AdventureOption("Take Item", 0, 0, 1);
		if (!inventory.inventoryContains(item) || inventory.getFromInventory(item).Quantity < inventory.QuantityLimit){
			Option1.Result = "You reach down and take the " + item.Name + " from the leaves.";
			Option1.ResultingItems = new PetItem[] { item };
		}else{
			Option1.Result = "You cannot carry anymore " + item.Name + "!";
		}
		itemEncounter = item;
		
		Option2 = new AdventureOption("Leave it", 0, 0, 1);
		Option2.Result = "You leave the item where it rests.";
	}
}
