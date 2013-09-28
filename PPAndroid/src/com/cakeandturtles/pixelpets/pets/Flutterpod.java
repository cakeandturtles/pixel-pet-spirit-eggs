package com.cakeandturtles.pixelpets.pets;

public class Flutterpod extends PixelPet{ //The Hovering Cocoon Pet
	private static final long serialVersionUID = -316516997764510732L;
	
	public Flutterpod(){
		this("", 0L);
	}

	public Flutterpod(String trainerName, long trainerId){
		super("Flutterpod", "Flutterpod", BattleType.Insect, BattleType.Air, trainerName, trainerId);
		RelAniX = 4;
		RelAniY = 0;
		LevelWhenEvolve = 12; //ALSO IMPORTANT
		CurrentForm = PetForm.Secondary; //IMPORTANT!!!
		
		RandomizeGender(2);
	}
	
	//EVERYONE MUST OVERRIDE EVOLVE!!!
	@Override
	public PixelPet Evolve()
	{
		PixelPet evolution = new Lunactulus(TrainerName, TrainerID);
		evolution.EvolveFrom(this);
		return evolution;
	}
}
