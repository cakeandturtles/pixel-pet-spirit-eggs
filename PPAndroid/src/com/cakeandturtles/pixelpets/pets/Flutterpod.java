package com.cakeandturtles.pixelpets.pets;

import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.attacks.LevelAttack;
import com.cakeandturtles.pixelpets.attacks.insect.BugBite;
import com.cakeandturtles.pixelpets.attacks.insect.Pupate;
import com.cakeandturtles.pixelpets.attacks.plant.Photosynthesis;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.collectables.DryLeaf;
import com.cakeandturtles.pixelpets.items.collectables.MoonLeaf;
import com.cakeandturtles.pixelpets.items.collectables.StarLeaf;
import com.cakeandturtles.pixelpets.items.fruits.BugFruit;
import com.cakeandturtles.pixelpets.items.fruits.SkyFruit;


public class Flutterpod extends PixelPet{ //The Hovering Cocoon Pet
	private static final long serialVersionUID = -316516997764510732L;
	
	public Flutterpod(){
		this("", 0L);
	}

	public Flutterpod(String trainerName, long trainerId){
		super("Flutterpod", "Flutterpod", BattleType.Insect, BattleType.Air, trainerName, trainerId);
		RelAniX = 4;
		RelAniY = 0;
		LevelWhenEvolve = 12; //ALSO IMPORTANT
		CurrentForm = PetForm.Secondary; //IMPORTANT!!!
		
		RandomizeGender(2);
		SetBattleAttributes(41, 21, 20, 46);
		
		Attacks[0] = new BugBite();
		Attacks[1] = new Photosynthesis();
		Attacks[2] = new Pupate();
	}
	
	//EVERYONE MUST OVERRIDE LEVELUPATTACKLIST
	@Override
	public void InitializeLevelUpAttackList()
	{
		LevelAttackList = new Chloropillar().LevelAttackList;
		LevelAttackList.add(new LevelAttack(8, new Pupate()));
	}
	
	//EVERYONE MUST OVERRIDE EVOLVE!!!
	@Override
	public PixelPet Evolve()
	{
		PixelPet evolution = new Lunactulus(TrainerName, TrainerID);
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
			case 4: spoils.add(new SkyFruit()); break;
			default: break;
		}
		return spoils;
	}
}
