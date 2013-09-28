package com.cakeandturtles.pixelpets.attacks.dark;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class SneakAttack extends Attack{
	private static final long serialVersionUID = 8031822662190304311L;

	public SneakAttack(){
		super("Sneak Attack", "Your pet quickly sneaks behind the foe, surprising them with an attack before they have a chance to move.", BattleType.Dark, 30, 100, 25);
		Priority = -2;
	}
}
