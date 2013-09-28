package com.cakeandturtles.pixelpets.items.battle;

import com.cakeandturtles.pixelpets.items.ItemEffect;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class Substitute extends PetItem{
	private static final long serialVersionUID = -8124676083362920225L;
	
	public Substitute(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public Substitute(){
		super("Substitute", "A wooden carving of a pet. Can be used in battle to distract the foe and run away.", 6, 0);
		CollectableType = CollectableTypes.Battle; //IMPORTANT!!!
		
		Effect = new SubstituteEffect();
	}
}

final class SubstituteEffect extends ItemEffect{
	private static final long serialVersionUID = -6821265392038602610L;

	public SubstituteEffect(){
		NeedsATarget = false;
		CanOnlyBeUsedInBattle = true;
	}
	
	public boolean NoEffect(PixelPet targetPet){
		return false;
	}

	public String DoItemEffect(PixelPet targetPet, PixelPet battleFoe)
	{
		return "";
	}
}
