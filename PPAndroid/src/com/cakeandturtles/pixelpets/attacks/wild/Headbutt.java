package com.cakeandturtles.pixelpets.attacks.wild;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class Headbutt extends Attack{
	private static final long serialVersionUID = 6798745427196044491L;

	public Headbutt(){
		super("Headbutt", "Your pet charges into the foe with his head lowered.", BattleType.Wild, 60, 100, 30);
	}
}
