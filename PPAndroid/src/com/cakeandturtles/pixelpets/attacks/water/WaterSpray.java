package com.cakeandturtles.pixelpets.attacks.water;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class WaterSpray extends Attack{
	private static final long serialVersionUID = -5009861080112817484L;

	public WaterSpray(){
		super("Water Spray", "Your pet sprays the foe with a concentrated blast of water.", BattleType.Water, 60, 100, 30);
	}
}
