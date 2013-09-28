package com.cakeandturtles.pixelpets.pets;

public class Fledgwing extends PixelPet{ //The Fledgling Songbird Pet
	private static final long serialVersionUID = -7548867975434206194L;

	public Fledgwing(){
		this("", 0L);
	}

	public Fledgwing(String trainerName, long trainerId){
		super("Fledgwing", "Fledgwing", BattleType.Air, null, trainerName, trainerId);
		RelAniX = 2;
		RelAniY = 1;
		LevelWhenEvolve = 15;
		
		RandomizeGender(2);
	}
	
	//EVERYONE MUST OVERRIDE EVOLVE!!!
	@Override
	public PixelPet Evolve()
	{
		return this;
	}
}
