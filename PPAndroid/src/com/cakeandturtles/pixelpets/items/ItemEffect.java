package com.cakeandturtles.pixelpets.items;

import java.io.Serializable;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public abstract class ItemEffect implements Serializable{
	private static final long serialVersionUID = 7080214740056297432L;
	public boolean NeedsATarget;
	public boolean CanOnlyBeUsedInBattle = false;
	public Attack Attack = null;
	
	public abstract boolean NoEffect(PixelPet targetPet);
	public abstract String DoItemEffect(PixelPet targetPet, PixelPet battleFoe);
}
