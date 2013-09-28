package com.cakeandturtles.pixelpets.items.medicine;

import com.cakeandturtles.pixelpets.items.ItemEffect;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class Ether extends PetItem{
	private static final long serialVersionUID = -706765974878546716L;

	public Ether(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public Ether(){
		super("Ether", "A mystical fuscia bubbling brew. Restores all of a pet's Attack Energy.", 7, 0);
		CollectableType = CollectableTypes.Medicine; //IMPORTANT!!!
		
		Effect = new EtherEffect();
	}
}

final class EtherEffect extends ItemEffect{
	private static final long serialVersionUID = 7978475149325567920L;

	public EtherEffect(){
		NeedsATarget = true;
	}
	
	public boolean NoEffect(PixelPet targetPet){
		for (int i = 0; i < 4; i++){
			if (targetPet.Attacks[i] != null){
				if (targetPet.Attacks[i].NumUses < targetPet.Attacks[i].BaseNumUses){
					return false;
				}
			}
		}
		return true;
	}

	public String DoItemEffect(PixelPet targetPet, PixelPet battleFoe)
	{
		for (int i = 0; i < 4; i++){
			if (targetPet.Attacks[i] != null)
				targetPet.Attacks[i].NumUses = targetPet.Attacks[i].BaseNumUses;
		}
		String result = targetPet.Name + "'s Attack is fully restored!"; 
		return result;
	}
}
