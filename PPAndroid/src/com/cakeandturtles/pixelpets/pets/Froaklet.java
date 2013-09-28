package com.cakeandturtles.pixelpets.pets;



public class Froaklet extends PixelPet{ //The Toxic Toadlet Pet
	private static final long serialVersionUID = 6070067318458186642L;
	
	public Froaklet(){
		this("", 0L);
	}

	public Froaklet(String trainerName, long trainerId){
		super("Froaklet", "Froaklet", BattleType.Poison, BattleType.Water, trainerName, trainerId);
		RelAniX = 0;
		RelAniY = 1;
		LevelWhenEvolve = 35; //IMPORTANT
		CurrentForm = PetForm.Secondary; //IMPORTANT!!!
		
		RandomizeGender(2);
	}
}
