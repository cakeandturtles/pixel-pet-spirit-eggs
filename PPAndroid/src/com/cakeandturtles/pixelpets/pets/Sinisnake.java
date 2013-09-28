package com.cakeandturtles.pixelpets.pets;

import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.attacks.LevelAttack;
import com.cakeandturtles.pixelpets.attacks.dark.SneakAttack;
import com.cakeandturtles.pixelpets.attacks.dark.Unnerve;
import com.cakeandturtles.pixelpets.attacks.poison.Miasma;
import com.cakeandturtles.pixelpets.attacks.poison.PoisonJet;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.fruits.PoisonFruit;
import com.cakeandturtles.pixelpets.items.fruits.UmberFruit;


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
		SetBattleAttributes(24, 40, 40, 24);
		
		Attacks[0] = new PoisonJet();
		Attacks[1] = new SneakAttack();
	}
	
	//EVERYONE MUST OVERRIDE LEVELUPATTACKLIST
	@Override
	public void InitializeLevelUpAttackList()
	{
		LevelAttackList = new Tadpox().LevelAttackList;
		LevelAttackList.add(new LevelAttack(15, new SneakAttack()));
		LevelAttackList.add(new LevelAttack(20, new Unnerve()));
		LevelAttackList.add(new LevelAttack(25, new Miasma()));
	}
	
	//EVERYONE MUST OVERRIDE GETITEMDROPS()
	@Override
	public List<PetItem> GetItemDrops()
	{
		List<PetItem> spoils = new ArrayList<PetItem>();
		int rand = PPApp.AppRandom.nextInt(3);
		switch (rand){
			case 0: spoils.add(new PoisonFruit()); break;
			case 1: spoils.add(new UmberFruit()); break;
			default: break;
		}
		return spoils;
	}
}
