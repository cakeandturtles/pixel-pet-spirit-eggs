package com.cakeandturtles.pixelpets.pets;

import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.attacks.LevelAttack;
import com.cakeandturtles.pixelpets.attacks.NormalAttack;
import com.cakeandturtles.pixelpets.attacks.earth.GroundAttack;
import com.cakeandturtles.pixelpets.attacks.earth.MudBath;
import com.cakeandturtles.pixelpets.attacks.wild.Headbutt;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.fruits.TerraFruit;


public class Puglett extends PixelPet{ //The Burrowing Pig Pet
	private static final long serialVersionUID = 5480899302667824053L;
	
	public Puglett(){
		this("", 0L);
	}

	public Puglett(String trainerName, long trainerId){
		super("Puglett", "Puglett", BattleType.Earth, null, trainerName, trainerId);
		RelAniX = 2;
		RelAniY = 2;
		LevelWhenEvolve = 15; //IMPORTANT
		
		RandomizeGender(2);
		SetBattleAttributes(18, 12, 14, 20);
		
		Attacks[0] = new NormalAttack();
		Attacks[1] = new GroundAttack();
	}
	
	//EVERYONE MUST OVERRIDE EVOLVE!!!
	@Override
	public PixelPet Evolve()
	{	
		PixelPet evolution = null;
		if (diligence > ambition)
			evolution = new Puggafrost(TrainerName, TrainerID);
		else if (diligence < ambition)
			evolution = new Mudhog(TrainerName, TrainerID);
		else{
			if (PPApp.AppRandom.nextInt(1) <= 0)
				evolution = new Puggafrost(TrainerName, TrainerID);
			else
				evolution = new Mudhog(TrainerName, TrainerID);
		}
		
		if (evolution != null)
			evolution.EvolveFrom(this);
		return evolution;
	}
	
	//EVERYONE MUST OVERRIDE LEVELUPATTACKLIST
	@Override
	public void InitializeLevelUpAttackList()
	{
		super.InitializeLevelUpAttackList();
		LevelAttackList.add(new LevelAttack(0, new NormalAttack()));
		LevelAttackList.add(new LevelAttack(0, new GroundAttack()));
		LevelAttackList.add(new LevelAttack(5, new MudBath()));
		LevelAttackList.add(new LevelAttack(10, new Headbutt()));
	}
	
	//EVERYONE MUST OVERRIDE GETITEMDROPS()
	@Override
	public List<PetItem> GetItemDrops()
	{
		List<PetItem> spoils = new ArrayList<PetItem>();
		int rand = PPApp.AppRandom.nextInt(2);
		switch (rand){
			case 0: spoils.add(new TerraFruit()); break;
			default: break;
		}
		return spoils;
	}
}
