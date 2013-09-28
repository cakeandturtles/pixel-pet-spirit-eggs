package com.cakeandturtles.pixelpets.pets;

import com.cakeandturtles.pixelpets.PPApp;

public class Chloropillar extends PixelPet{ //The Leafy Caterpillar Pet
	private static final long serialVersionUID = -7811156323393071863L;
	
	public Chloropillar(){
		this("", 0L);
	}

	public Chloropillar(String trainerName, long trainerId){
		super("Chloropillar", "Chloropillar", BattleType.Insect, null, trainerName, trainerId);
		RelAniX = 2;
		RelAniY = 0;
		LevelWhenEvolve = 8;
		
		RandomizeGender(2);
	}
	
	//EVERYONE MUST OVERRIDE EVOLVE!!!
	@Override
	public PixelPet Evolve()
	{
		PixelPet evolution = null;
		if (empathy > diligence)
			evolution = new Flutterpod(TrainerName, TrainerID);
		else if (empathy < diligence)
			evolution = new Cladydid(TrainerName, TrainerID);
		else{
			if (PPApp.AppRandom.nextInt(1) <= 0)
				evolution = new Flutterpod(TrainerName, TrainerID);
			else
				evolution = new Cladydid(TrainerName, TrainerID);
		}
		
		if (evolution != null)
			evolution.EvolveFrom(this);
		return evolution;
	}
}
