package com.cakeandturtles.pixelpets.items.medicine;

import com.cakeandturtles.pixelpets.items.ItemEffect;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class BluePotion extends PetItem{
	private static final long serialVersionUID = 9013588908984865957L;
	
	public BluePotion(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public BluePotion(){
		super("Blue Potion", "A mystical blue bubbling brew. Restores 100 HP\n(Cannot restore HP if pet is knocked out).", 3, 0);
		CollectableType = CollectableTypes.Medicine; //IMPORTANT!!!
		
		Effect = new BluePotionEffect();
	}
}

final class BluePotionEffect extends ItemEffect{
	private static final long serialVersionUID = 7800840322999831827L;

	public BluePotionEffect(){
		NeedsATarget = true;
	}
	
	public boolean NoEffect(PixelPet targetPet){
		if (targetPet.HP < targetPet.BaseHP && targetPet.HP > 0)
			return false;
		return true;
	}

	public String DoItemEffect(PixelPet targetPet, PixelPet battleFoe)
	{
		int hp = targetPet.HP;
		if (targetPet.HP > 0) targetPet.HP += 100;
		if (targetPet.HP > targetPet.BaseHP) targetPet.HP = targetPet.BaseHP;
		int restored = targetPet.HP - hp;
		
		String result = targetPet.Name;
		result += " restored " + restored + " HP!";
		return result;
	}
}
