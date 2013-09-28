package com.cakeandturtles.pixelpets.items.medicine;

import com.cakeandturtles.pixelpets.items.ItemEffect;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class PhoenixDown extends PetItem{
	private static final long serialVersionUID = 1442380754238526153L;
	
	public PhoenixDown(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public PhoenixDown(){
		super("Revive", "A mystical red feather from a mythical bird. Revives pet if knocked out", 4, 0);
		CollectableType = CollectableTypes.Medicine; //IMPORTANT!!!
		
		Effect = new PhoenixDownEffect();
	}
}

final class PhoenixDownEffect extends ItemEffect{
	private static final long serialVersionUID = -250631538265385246L;

	public PhoenixDownEffect(){
		NeedsATarget = true;
	}
	
	public boolean NoEffect(PixelPet targetPet){
		if (targetPet.HP > 0)
			return true;
		return false;
	}

	public String DoItemEffect(PixelPet targetPet, PixelPet battleFoe)
	{
		targetPet.HP = 1;
		String result = targetPet.Name + " is revived!"; 
		return result;
	}
}
