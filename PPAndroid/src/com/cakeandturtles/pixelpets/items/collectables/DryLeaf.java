package com.cakeandturtles.pixelpets.items.collectables;

import com.cakeandturtles.pixelpets.items.ItemEffect;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class DryLeaf extends PetItem{
	private static final long serialVersionUID = -4803175784048085180L;
	
	public DryLeaf(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public DryLeaf(){
		super("Dry Leaf", "A magical leaf from a Dry Tree. Increases a pet's diligence.", 3, 1);
		CollectableType = CollectableTypes.Leaf; //IMPORTANT!!!
		
		Effect = new DryLeafEffect();
	}
}

final class DryLeafEffect extends ItemEffect{
	private static final long serialVersionUID = 5150909381859231735L;

	public DryLeafEffect(){
		NeedsATarget = true;
	}
	
	public boolean NoEffect(PixelPet targetPet)
	{
		if (targetPet.GetIntInsight() >= 100)
			return true;
		
		return false;
	}

	public String DoItemEffect(PixelPet targetPet, PixelPet battleFoe)
	{
		int iResult = targetPet.ModifyInsight(5);
		String result = "";
		if (iResult > 0)
			result = targetPet.Name + "'s DILIGENCE increased.";
		else result = "Nothing Happened...";
		return result;
	}
}
