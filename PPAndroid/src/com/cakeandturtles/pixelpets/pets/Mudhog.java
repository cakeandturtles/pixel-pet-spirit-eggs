package com.cakeandturtles.pixelpets.pets;

import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.attacks.LevelAttack;
import com.cakeandturtles.pixelpets.attacks.earth.MudBath;
import com.cakeandturtles.pixelpets.attacks.earth.MudSplash;
import com.cakeandturtles.pixelpets.attacks.wild.Headbutt;
import com.cakeandturtles.pixelpets.attacks.wild.RecklessCharge;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.fruits.TerraFruit;
import com.cakeandturtles.pixelpets.items.fruits.WildFruit;


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
		SetBattleAttributes(24, 40, 40, 24);
		
		Attacks[0] = new RecklessCharge();
		Attacks[1] = new MudBath();
		Attacks[2] = new Headbutt();
		Attacks[3] = new MudSplash();
	}
	
	//EVERYONE MUST OVERRIDE LEVELUPATTACKLIST
	@Override
	public void InitializeLevelUpAttackList()
	{
		super.InitializeLevelUpAttackList();
		LevelAttackList = new Puglett().LevelAttackList;
		LevelAttackList.add(new LevelAttack(15, new RecklessCharge()));
		LevelAttackList.add(new LevelAttack(20, new MudSplash()));
	}
	
	//EVERYONE MUST OVERRIDE GETITEMDROPS()
	@Override
	public List<PetItem> GetItemDrops()
	{
		List<PetItem> spoils = new ArrayList<PetItem>();
		int rand = PPApp.AppRandom.nextInt(3);
		switch (rand){
			case 0: spoils.add(new TerraFruit()); break;
			case 1: spoils.add(new WildFruit()); break;
			default: break;
		}
		return spoils;
	}
}
