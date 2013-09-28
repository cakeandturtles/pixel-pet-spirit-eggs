package com.cakeandturtles.pixelpets.attacks.air;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class Flurry extends Attack{
	private static final long serialVersionUID = -6368095625174031650L;

	public Flurry(){
		super("Flurry", "Your pet creates a gust of wind and attacks the foe with it.", BattleType.Air, 40, 100, 30);
	}
}
