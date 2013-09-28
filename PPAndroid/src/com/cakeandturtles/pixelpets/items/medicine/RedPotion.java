package com.cakeandturtles.pixelpets.items.medicine;

import com.cakeandturtles.pixelpets.items.ItemEffect;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class RedPotion extends PetItem{
	private static final long serialVersionUID = 7868359423013845667L;
	
	public RedPotion(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public RedPotion(){
		super("Red Potion", "A mystical red bubbling brew. Restores all of a pet's HP.\n(Cannot restore HP if pet is knocked out)", 1, 0);
		CollectableType = CollectableTypes.Medicine; //IMPORTANT!!!
		
		Effect = new RedPotionEffect();
	}
}

final class RedPotionEffect extends ItemEffect{
	private static final long serialVersionUID = -7286162340246206217L;
	
	public RedPotionEffect(){
		NeedsATarget = true;
	}
	
	public boolean NoEffect(PixelPet targetPet){
		if (targetPet.HP < targetPet.BaseHP && targetPet.HP > 0)
			return false;
		return true;
	}

	public String DoItemEffect(PixelPet targetPet, PixelPet battleFoe)
	{
		targetPet.HP = targetPet.BaseHP;
		String result = targetPet.Name + "'s HP is fully restored!"; 
		return result;
	}
}
