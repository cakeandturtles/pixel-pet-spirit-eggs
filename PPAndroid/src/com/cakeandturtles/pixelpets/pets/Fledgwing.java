package com.cakeandturtles.pixelpets.pets;

import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.attacks.LevelAttack;
import com.cakeandturtles.pixelpets.attacks.air.ClearAir;
import com.cakeandturtles.pixelpets.attacks.air.Flurry;
import com.cakeandturtles.pixelpets.attacks.air.MimicSong;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.collectables.DryLeaf;
import com.cakeandturtles.pixelpets.items.collectables.MoonLeaf;
import com.cakeandturtles.pixelpets.items.collectables.StarLeaf;
import com.cakeandturtles.pixelpets.items.fruits.SkyFruit;


public class Fledgwing extends PixelPet{ //The Fledgling Songbird Pet
	private static final long serialVersionUID = -7548867975434206194L;

	public Fledgwing(){
		this("", 0L);
	}

	public Fledgwing(String trainerName, long trainerId){
		super("Fledgwing", "Fledgwing", BattleType.Air, null, trainerName, trainerId);
		RelAniX = 2;
		RelAniY = 1;
		LevelWhenEvolve = 15;
		
		RandomizeGender(2);
		SetBattleAttributes(17, 20, 12, 15);
		
		Attacks[0] = new Flurry();
	}
	
	//EVERYONE MUST OVERRIDE LEVELUPATTACKLIST
	@Override
	public void InitializeLevelUpAttackList()
	{
		super.InitializeLevelUpAttackList();
		LevelAttackList.add(new LevelAttack(0, new Flurry()));
		LevelAttackList.add(new LevelAttack(5, new MimicSong()));
		LevelAttackList.add(new LevelAttack(12, new ClearAir()));
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
			case 3: spoils.add(new SkyFruit()); break;
			default: break;
		}
		return spoils;
	}
}
