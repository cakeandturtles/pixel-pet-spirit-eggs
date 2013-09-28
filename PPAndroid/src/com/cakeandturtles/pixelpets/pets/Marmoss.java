package com.cakeandturtles.pixelpets.pets;

public class Marmoss extends PixelPet //The Mossy Monkey Pet
{
	private static final long serialVersionUID = -2826505002261302446L;
	
	public Marmoss(){
		this("", 0L);
	}

	public Marmoss(String trainerName, long trainerId){
		super("Marmoss", "Marmoss", BattleType.Plant, null, trainerName, trainerId);
		RelAniX = 0;
		RelAniY = 0;
		
		RandomizeGender(2);
	}
	
	//EVERYONE MUST OVERRIDE EVOLVE!!!
	@Override
	public PixelPet Evolve()
	{
		return this;
	}
}
