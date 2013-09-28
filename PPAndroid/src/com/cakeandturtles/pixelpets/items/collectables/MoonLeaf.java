package com.cakeandturtles.pixelpets.items.collectables;

import com.cakeandturtles.pixelpets.items.ItemEffect;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class MoonLeaf extends PetItem{
	private static final long serialVersionUID = 8418125363779649113L;

	public MoonLeaf(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public MoonLeaf(){
		super("Moon Leaf", "A magical leaf from a Moon Tree. Increases a pet's insight.", 2, 1);
		CollectableType = CollectableTypes.Leaf; //IMPORTANT!!!
		
		Effect = new MoonLeafEffect();
	}
}

final class MoonLeafEffect extends ItemEffect{
	private static final long serialVersionUID = -3596627877167674131L;

	public MoonLeafEffect(){
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
			result = targetPet.Name + "'s INSIGHT increased.";
		else result = "Nothing Happened...";
		return result;
	}
}
