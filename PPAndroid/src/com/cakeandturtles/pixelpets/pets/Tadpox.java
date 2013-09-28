package com.cakeandturtles.pixelpets.pets;

import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.attacks.LevelAttack;
import com.cakeandturtles.pixelpets.attacks.NormalAttack;
import com.cakeandturtles.pixelpets.attacks.poison.PoisonJet;
import com.cakeandturtles.pixelpets.attacks.poison.SprayAcid;
import com.cakeandturtles.pixelpets.attacks.water.Downpour;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.fruits.PoisonFruit;


public class Tadpox extends PixelPet{ //The Poisonous Tadpole Pet
	private static final long serialVersionUID = 3244691121340225893L;
	
	public Tadpox(){
		this("", 0L);
	}

	public Tadpox(String trainerName, long trainerId){
		super("Tadpox", "Tadpox", BattleType.Poison, null, trainerName, trainerId);
		LevelWhenEvolve = 15;
		RelAniX = 0;
		RelAniY = 1;
		
		RandomizeGender(2);
		SetBattleAttributes(12, 18, 14, 12);
		
		Attacks[0] = new NormalAttack();
		Attacks[1] = new PoisonJet();
	}
	
	//EVERYONE MUST OVERRIDE LEVELUPATTACKLIST
	@Override
	public void InitializeLevelUpAttackList()
	{
		super.InitializeLevelUpAttackList();
		LevelAttackList.add(new LevelAttack(0, new NormalAttack()));
		LevelAttackList.add(new LevelAttack(0, new PoisonJet()));
		LevelAttackList.add(new LevelAttack(5, new Downpour()));
		LevelAttackList.add(new LevelAttack(10, new SprayAcid()));
	}
	
	//EVERYONE MUST OVERRIDE EVOLVE!!!
	@Override
	public PixelPet Evolve()
	{
		PixelPet evolution = null;
		if (insight > ambition)
			evolution = new Froaklet(TrainerName, TrainerID);
		else if (insight < ambition)
			evolution = new Sinisnake(TrainerName, TrainerID);
		else{
			if (PPApp.AppRandom.nextInt(1) <= 0)
				evolution = new Froaklet(TrainerName, TrainerID);
			else
				evolution = new Sinisnake(TrainerName, TrainerID);
		}
		
		if (evolution != null)
			evolution.EvolveFrom(this);
		return evolution;
	}
	
	//EVERYONE MUST OVERRIDE GETITEMDROPS()
	@Override
	public List<PetItem> GetItemDrops()
	{
		List<PetItem> spoils = new ArrayList<PetItem>();
		int rand = PPApp.AppRandom.nextInt(2);
		switch (rand){
			case 0: spoils.add(new PoisonFruit()); break;
			default: break;
		}
		return spoils;
	}
}
