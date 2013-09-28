package com.cakeandturtles.pixelpets.pets;

public class Lunactulus extends PixelPet{ //The Luna Moth Pet
	private static final long serialVersionUID = -5519329403843990841L;
	
	public Lunactulus(){
		this("", 0L);
	}

	public Lunactulus(String trainerName, long trainerId){
		super("Lunactulus", "Lunactulus", BattleType.Insect, BattleType.Air, trainerName, trainerId);
		RelAniX = 4;
		RelAniY = 0;
		CurrentForm = PetForm.Tertiary; //IMPORTANT!!!
		
		RandomizeGender(2);
	}
	
	//EVERYONE MUST OVERRIDE EVOLVE!!!
	@Override
	public PixelPet Evolve()
	{
		return this;
	}
}
