package com.cakeandturtles.pixelpets.pets;

public class Mudhog extends PixelPet{ //The Wild Boarlet Pet
	private static final long serialVersionUID = -6546378157507470217L;

	public Mudhog(){
		this("", 0L);
	}

	public Mudhog(String trainerName, long trainerId){
		super("Mudhog", "Mudhog", BattleType.Earth, BattleType.Wild, trainerName, trainerId);
		RelAniX = 4;
		RelAniY = 2;
		LevelWhenEvolve = 35; //IMPORTANT
		CurrentForm = PetForm.Secondary;
		
		RandomizeGender(2);
	}
}
