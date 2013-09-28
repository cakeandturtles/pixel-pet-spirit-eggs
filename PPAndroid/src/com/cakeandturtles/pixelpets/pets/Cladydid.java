package com.cakeandturtles.pixelpets.pets;

public class Cladydid extends PixelPet{ //The Katydid Pet
	private static final long serialVersionUID = -4440405922409223223L;

	public Cladydid(){
		this("", 0L);
	}

	public Cladydid(String trainerName, long trainerId){
		super("Cladydid", "Cladydid", BattleType.Insect, BattleType.Plant, trainerName, trainerId);
		RelAniX = 6;
		RelAniY = 0;
		LevelWhenEvolve = 12; //ALSO IMPORTANT
		CurrentForm = PetForm.Secondary; //IMPORTANT!!!
		
		RandomizeGender(2);
	}
	
	//EVERYONE MUST OVERRIDE EVOLVE!!!
	@Override
	public PixelPet Evolve()
	{
		return this;
	}
}
