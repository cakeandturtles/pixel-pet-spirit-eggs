package com.cakeandturtles.pixelpets.attacks.water;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class Squirt extends Attack{
	private static final long serialVersionUID = 5748029105148812844L;

	public Squirt(){
		super("Squirt", "Your pet shoots a weak jet of water at the foe.", BattleType.Water, 40, 100, 30);
	}
}
