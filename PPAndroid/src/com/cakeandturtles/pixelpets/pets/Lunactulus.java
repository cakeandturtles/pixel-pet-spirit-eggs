package com.cakeandturtles.pixelpets.pets;

import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.attacks.LevelAttack;
import com.cakeandturtles.pixelpets.attacks.air.Flurry;
import com.cakeandturtles.pixelpets.attacks.dark.Unnerve;
import com.cakeandturtles.pixelpets.attacks.insect.BugBite;
import com.cakeandturtles.pixelpets.attacks.insect.Swarm;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.collectables.DryLeaf;
import com.cakeandturtles.pixelpets.items.collectables.MoonLeaf;
import com.cakeandturtles.pixelpets.items.collectables.StarLeaf;
import com.cakeandturtles.pixelpets.items.fruits.BugFruit;
import com.cakeandturtles.pixelpets.items.fruits.SkyFruit;


public class Lunactulus extends PixelPet{ //The Luna Moth Pet
	private static final long serialVersionUID = -5519329403843990841L;
	
	public Lunactulus(){
		this("", 0L);
	}

	public Lunactulus(String trainerName, long trainerId){
		super("Lunactulus", "Lunactulus", BattleType.Insect, BattleType.Air, trainerName, trainerId);
		RelAniX = 4;
		RelAniY = 0;
		CurrentForm = PetForm.Tertiary; //IMPORTANT!!!
		
		RandomizeGender(2);
		SetBattleAttributes(64, 72, 48, 72);
		
		Attacks[0] = new BugBite();
		Attacks[1] = new Flurry();
	}
	
	//EVERYONE MUST OVERRIDE LEVELUPATTACKLIST
	@Override
	public void InitializeLevelUpAttackList()
	{
		LevelAttackList = new Flutterpod().LevelAttackList;
		LevelAttackList.add(new LevelAttack(12, new Flurry()));
		LevelAttackList.add(new LevelAttack(16, new Unnerve()));
		LevelAttackList.add(new LevelAttack(20, new Swarm()));
	}
	
	//EVERYONE MUST OVERRIDE EVOLVE!!!
	@Override
	public PixelPet Evolve()
	{
		return this;
	}
	
	//EVERYONE MUST OVERRIDE GETITEMDROPS()
	@Override
	public List<PetItem> GetItemDrops()
	{
		List<PetItem> spoils = new ArrayList<PetItem>();
		int rand = PPApp.AppRandom.nextInt(6);
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
