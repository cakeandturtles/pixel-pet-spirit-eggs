package com.cakeandturtles.pixelpets.adventures;

import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.Tadpox;

public class PoisonRandomBattle extends Adventure{
	private static final long serialVersionUID = 8787110045052095358L;

	public PoisonRandomBattle(PixelPet activePet, int consecutiveCounter){
		super(activePet);
		EnemyPets = new String[]{ (new Tadpox("", 0L)).Species };
	}
}
