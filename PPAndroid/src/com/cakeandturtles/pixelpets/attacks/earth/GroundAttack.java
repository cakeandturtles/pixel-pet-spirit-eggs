package com.cakeandturtles.pixelpets.attacks.earth;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class GroundAttack extends Attack{
	private static final long serialVersionUID = 6773513833986782553L;

	public GroundAttack(){
		super("Ground Attack", "Your pet attacks the foe with mud and rocks.", BattleType.Earth, 40, 100, 30);
	}
}
