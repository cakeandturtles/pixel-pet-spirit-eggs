package com.cakeandturtles.pixelpets.pets;

import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.attacks.LevelAttack;
import com.cakeandturtles.pixelpets.attacks.NormalAttack;
import com.cakeandturtles.pixelpets.attacks.insect.BugBite;
import com.cakeandturtles.pixelpets.attacks.plant.Photosynthesis;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.collectables.DryLeaf;
import com.cakeandturtles.pixelpets.items.collectables.MoonLeaf;
import com.cakeandturtles.pixelpets.items.collectables.StarLeaf;
import com.cakeandturtles.pixelpets.items.fruits.BugFruit;


public class Chloropillar extends PixelPet{ //The Leafy Caterpillar Pet
	private static final long serialVersionUID = -7811156323393071863L;
	
	public Chloropillar(){
		this("", 0L);
	}

	public Chloropillar(String trainerName, long trainerId){
		super("Chloropillar", "Chloropillar", BattleType.Insect, null, trainerName, trainerId);
		RelAniX = 2;
		RelAniY = 0;
		LevelWhenEvolve = 8;
		
		RandomizeGender(2);
		SetBattleAttributes(19, 14, 8, 23);
		
		Attacks[0] = new NormalAttack();
		Attacks[1] = new BugBite();
	}
	
	//EVERYONE MUST OVERRIDE LEVELUPATTACKLIST
	@Override
	public void InitializeLevelUpAttackList()
	{
		super.InitializeLevelUpAttackList();
		LevelAttackList.add(new LevelAttack(0, new NormalAttack()));
		LevelAttackList.add(new LevelAttack(0, new BugBite()));
		LevelAttackList.add(new LevelAttack(5, new Photosynthesis()));
	}
	
	//EVERYONE MUST OVERRIDE EVOLVE!!!
	@Override
	public PixelPet Evolve()
	{
		PixelPet evolution = null;
		if (empathy > diligence)
			evolution = new Flutterpod(TrainerName, TrainerID);
		else if (empathy < diligence)
			evolution = new Cladydid(TrainerName, TrainerID);
		else{
			if (PPApp.AppRandom.nextInt(1) <= 0)
				evolution = new Flutterpod(TrainerName, TrainerID);
			else
				evolution = new Cladydid(TrainerName, TrainerID);
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
		int rand = PPApp.AppRandom.nextInt(5);
		switch (rand){
			case 0: spoils.add(new StarLeaf()); break;
			case 1: spoils.add(new MoonLeaf()); break;
			case 2: spoils.add(new DryLeaf()); break;
			case 3: spoils.add(new BugFruit()); break;
			default: break;
		}
		return spoils;
	}
}
