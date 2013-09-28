package com.cakeandturtles.pixelpets.adventures;

import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.Puglett;

public class CaveRandomBattle extends Adventure{
	private static final long serialVersionUID = 8787110045052095358L;

	public CaveRandomBattle(PixelPet activePet, int consecutiveCounter){
		super(activePet);
		EnemyPets = new String[]{ (new Puglett("", 0L)).Species };
	}
}
