package com.cakeandturtles.pixelpets.pets;

import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.attacks.LevelAttack;
import com.cakeandturtles.pixelpets.attacks.insect.BugBite;
import com.cakeandturtles.pixelpets.attacks.plant.Camouflage;
import com.cakeandturtles.pixelpets.attacks.plant.Photosynthesis;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.collectables.DryLeaf;
import com.cakeandturtles.pixelpets.items.collectables.MoonLeaf;
import com.cakeandturtles.pixelpets.items.collectables.StarLeaf;
import com.cakeandturtles.pixelpets.items.fruits.BugFruit;
import com.cakeandturtles.pixelpets.items.fruits.VeggieFruit;


public class Cladydid extends PixelPet{ //The Katydid Pet
	private static final long serialVersionUID = -4440405922409223223L;

	public Cladydid(){
		this("", 0L);
	}

	public Cladydid(String trainerName, long trainerId){
		super("Cladydid", "Cladydid", BattleType.Insect, BattleType.Plant, trainerName, trainerId);
		RelAniX = 6;
		RelAniY = 0;
		LevelWhenEvolve = 12; //ALSO IMPORTANT
		CurrentForm = PetForm.Secondary; //IMPORTANT!!!
		
		RandomizeGender(2);
		SetBattleAttributes(32, 32, 32, 32);
		
		Attacks[0] = new BugBite();
		Attacks[1] = new Photosynthesis();
		Attacks[2] = new Camouflage();
	}
	
	//EVERYONE MUST OVERRIDE LEVELUPATTACKLIST
	@Override
	public void InitializeLevelUpAttackList()
	{
		LevelAttackList = new Chloropillar().LevelAttackList;
		LevelAttackList.add(new LevelAttack(8, new Camouflage()));
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
			case 4: spoils.add(new VeggieFruit()); break;
			default: break;
		}
		return spoils;
	}
}
