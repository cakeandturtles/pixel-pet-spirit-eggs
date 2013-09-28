package com.cakeandturtles.pixelpets.items.collectables;

import com.cakeandturtles.pixelpets.items.ItemEffect;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class StarLeaf extends PetItem{
	private static final long serialVersionUID = 7868359423013845667L;
	
	public StarLeaf(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public StarLeaf(){
		super("Star Leaf", "A magical leaf from a Star Tree. Increases a pet's ambition.", 1, 1);
		CollectableType = CollectableTypes.Leaf; //IMPORTANT!!!
		
		Effect = new StarLeafEffect();
	}
}

final class StarLeafEffect extends ItemEffect{
	private static final long serialVersionUID = -7286162340246206217L;
	
	public StarLeafEffect(){
		NeedsATarget = true;
	}
	
	public boolean NoEffect(PixelPet targetPet)
	{
		if (targetPet.GetIntAmbition() >= 100)
			return true;
		
		return false;
	}

	public String DoItemEffect(PixelPet targetPet, PixelPet battleFoe)
	{
		int iResult = targetPet.ModifyAmbition(5);
		String result = "";
		if (iResult > 0)
			result = targetPet.Name + "'s AMBITION increased.";
		else result = "Nothing Happened...";
		return result;
	}
}
