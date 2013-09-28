package com.cakeandturtles.pixelpets.pets;

import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.attacks.LevelAttack;
import com.cakeandturtles.pixelpets.attacks.NormalAttack;
import com.cakeandturtles.pixelpets.attacks.plant.Absorb;
import com.cakeandturtles.pixelpets.attacks.plant.Photosynthesis;
import com.cakeandturtles.pixelpets.attacks.wild.Headbutt;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.collectables.DryLeaf;
import com.cakeandturtles.pixelpets.items.collectables.MoonLeaf;
import com.cakeandturtles.pixelpets.items.collectables.StarLeaf;
import com.cakeandturtles.pixelpets.items.fruits.VeggieFruit;

public class Marmoss extends PixelPet //The Mossy Monkey Pet
{
	private static final long serialVersionUID = -2826505002261302446L;
	
	public Marmoss(){
		this("", 0L);
	}

	public Marmoss(String trainerName, long trainerId){
		super("Marmoss", "Marmoss", BattleType.Plant, null, trainerName, trainerId);
		RelAniX = 0;
		RelAniY = 0;
		
		RandomizeGender(2);
		SetBattleAttributes(15, 18, 17, 12);
		
		Attacks[0] = new NormalAttack();
		Attacks[1] = new Absorb();
	}
	
	//EVERYONE MUST OVERRIDE LEVELUPATTACKLIST
	@Override
	public void InitializeLevelUpAttackList()
	{
		super.InitializeLevelUpAttackList();
		LevelAttackList.add(new LevelAttack(0, new NormalAttack()));
		LevelAttackList.add(new LevelAttack(0, new Absorb()));
		LevelAttackList.add(new LevelAttack(5, new Photosynthesis()));
		LevelAttackList.add(new LevelAttack(10, new Headbutt()));
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
		int rand = PPApp.AppRandom.nextInt(5);
		switch (rand){
			case 0: spoils.add(new StarLeaf()); break;
			case 1: spoils.add(new MoonLeaf()); break;
			case 2: spoils.add(new DryLeaf()); break;
			case 3: spoils.add(new VeggieFruit()); break;
			default: break;
		}
		return spoils;
	}
}
