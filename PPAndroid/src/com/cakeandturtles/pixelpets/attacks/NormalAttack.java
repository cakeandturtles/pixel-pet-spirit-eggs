package com.cakeandturtles.pixelpets.attacks;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class NormalAttack extends Attack{
	private static final long serialVersionUID = -5629359185120575176L;

	public NormalAttack(){
		super("Attack", "Your pet attacks the foe.", BattleType.None, 40, 100, 35);
	}
}
