package com.cakeandturtles.pixelpets.pets;



public class Puggafrost extends PixelPet{ //The Wooly Pig Pet
	private static final long serialVersionUID = -8515323261501928434L;

	public Puggafrost(){
		this("", 0L);
	}

	public Puggafrost(String trainerName, long trainerId){
		super("Puggafrost", "Puggafrost", BattleType.Earth, BattleType.Ice, trainerName, trainerId);
		RelAniX = 6;
		RelAniY = 2;
		LevelWhenEvolve = 35; //IMPORTANT
		CurrentForm = PetForm.Secondary; //ALSO IMPORTANT
		
		RandomizeGender(2);
	}
}
