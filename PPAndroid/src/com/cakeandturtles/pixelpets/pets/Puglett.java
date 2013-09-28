package com.cakeandturtles.pixelpets.pets;

import com.cakeandturtles.pixelpets.PPApp;

public class Puglett extends PixelPet{ //The Burrowing Pig Pet
	private static final long serialVersionUID = 5480899302667824053L;
	
	public Puglett(){
		this("", 0L);
	}

	public Puglett(String trainerName, long trainerId){
		super("Puglett", "Puglett", BattleType.Earth, null, trainerName, trainerId);
		RelAniX = 2;
		RelAniY = 2;
		LevelWhenEvolve = 15; //IMPORTANT
		
		RandomizeGender(2);
	}
	
	//EVERYONE MUST OVERRIDE EVOLVE!!!
	@Override
	public PixelPet Evolve()
	{	
		PixelPet evolution = null;
		if (diligence > ambition)
			evolution = new Puggafrost(TrainerName, TrainerID);
		else if (diligence < ambition)
			evolution = new Mudhog(TrainerName, TrainerID);
		else{
			if (PPApp.AppRandom.nextInt(1) <= 0)
				evolution = new Puggafrost(TrainerName, TrainerID);
			else
				evolution = new Mudhog(TrainerName, TrainerID);
		}
		
		if (evolution != null)
			evolution.EvolveFrom(this);
		return evolution;
	}
}
