package com.cakeandturtles.pixelpets.pets;

public class Sinisnake extends PixelPet{ //The Rude Viper Pet
	private static final long serialVersionUID = 2506035222681635725L;
	
	public Sinisnake(){
		this("", 0L);
	}

	public Sinisnake(String trainerName, long trainerId){
		super("Sinisnake", "Sinisnake", BattleType.Poison, BattleType.Dark, trainerName, trainerId);
		RelAniX = 2;
		RelAniY = 1;
		LevelWhenEvolve = 35; //IMPORTANT
		CurrentForm = PetForm.Secondary; //IMPORTANT!!!
		
		RandomizeGender(2);
	}
}
