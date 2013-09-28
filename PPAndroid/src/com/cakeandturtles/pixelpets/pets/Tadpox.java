package com.cakeandturtles.pixelpets.pets;

import com.cakeandturtles.pixelpets.PPApp;

public class Tadpox extends PixelPet{ //The Poisonous Tadpole Pet
	private static final long serialVersionUID = 3244691121340225893L;
	
	public Tadpox(){
		this("", 0L);
	}

	public Tadpox(String trainerName, long trainerId){
		super("Tadpox", "Tadpox", BattleType.Poison, null, trainerName, trainerId);
		LevelWhenEvolve = 15;
		RelAniX = 0;
		RelAniY = 1;
		
		RandomizeGender(2);
	}
	
	//EVERYONE MUST OVERRIDE EVOLVE!!!
	@Override
	public PixelPet Evolve()
	{
		PixelPet evolution = null;
		if (insight > ambition)
			evolution = new Froaklet(TrainerName, TrainerID);
		else if (insight < ambition)
			evolution = new Sinisnake(TrainerName, TrainerID);
		else{
			if (PPApp.AppRandom.nextInt(1) <= 0)
				evolution = new Froaklet(TrainerName, TrainerID);
			else
				evolution = new Sinisnake(TrainerName, TrainerID);
		}
		
		if (evolution != null)
			evolution.EvolveFrom(this);
		return evolution;
	}
}
