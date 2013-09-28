package com.cakeandturtles.pixelpets.items.medicine;

import com.cakeandturtles.pixelpets.items.ItemEffect;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class GreenPotion extends PetItem{
	private static final long serialVersionUID = 6194446360198810497L;

	public GreenPotion(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public GreenPotion(){
		super("Green Potion", "A mystical green bubbling brew. Restores 50 HP\n(Cannot restore HP if pet is knocked out).", 2, 0);
		CollectableType = CollectableTypes.Medicine; //IMPORTANT!!!
		
		Effect = new GreenPotionEffect();
	}
}

final class GreenPotionEffect extends ItemEffect{
	private static final long serialVersionUID = 5549429380729624216L;

	public GreenPotionEffect(){
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
		if (targetPet.HP > 0) targetPet.HP += 50;
		if (targetPet.HP > targetPet.BaseHP) targetPet.HP = targetPet.BaseHP;
		int restored = targetPet.HP - hp;
		
		String result = targetPet.Name;
		result += " restored " + restored + " HP!";
		return result;
	}
}
