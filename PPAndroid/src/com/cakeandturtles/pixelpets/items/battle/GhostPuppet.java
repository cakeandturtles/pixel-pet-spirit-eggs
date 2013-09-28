package com.cakeandturtles.pixelpets.items.battle;

import com.cakeandturtles.pixelpets.attacks.statuses.Afraid;
import com.cakeandturtles.pixelpets.items.ItemEffect;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class GhostPuppet extends PetItem{
	private static final long serialVersionUID = 7487048877734229757L;
	
	public GhostPuppet(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public GhostPuppet(){
		super("Marionette", "A strange little ghost puppet that can be used to scare the opponent in battle, preventing it from attacking for 1 to 3 turns.", 5, 0);
		CollectableType = CollectableTypes.Battle; //IMPORTANT!!!
		
		Effect = new GhostPuppetEffect();
	}
}

final class GhostPuppetEffect extends ItemEffect{
	private static final long serialVersionUID = 4844910984380728374L;

	public GhostPuppetEffect(){
		NeedsATarget = false;
		CanOnlyBeUsedInBattle = true;
	}
	
	public boolean NoEffect(PixelPet targetPet){
		return false;
	}

	public String DoItemEffect(PixelPet targetPet, PixelPet battleFoe)
	{
		return (new Afraid()).ActivateEffect(targetPet, battleFoe);
	}
}
