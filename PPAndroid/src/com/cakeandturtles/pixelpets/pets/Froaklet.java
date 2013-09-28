package com.cakeandturtles.pixelpets.pets;

import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.attacks.LevelAttack;
import com.cakeandturtles.pixelpets.attacks.poison.Miasma;
import com.cakeandturtles.pixelpets.attacks.poison.PoisonJet;
import com.cakeandturtles.pixelpets.attacks.water.Downpour;
import com.cakeandturtles.pixelpets.attacks.water.WaterSpray;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.fruits.AquaFruit;
import com.cakeandturtles.pixelpets.items.fruits.PoisonFruit;


public class Froaklet extends PixelPet{ //The Toxic Toadlet Pet
	private static final long serialVersionUID = 6070067318458186642L;
	
	public Froaklet(){
		this("", 0L);
	}

	public Froaklet(String trainerName, long trainerId){
		super("Froaklet", "Froaklet", BattleType.Poison, BattleType.Water, trainerName, trainerId);
		RelAniX = 0;
		RelAniY = 1;
		LevelWhenEvolve = 35; //IMPORTANT
		CurrentForm = PetForm.Secondary; //IMPORTANT!!!
		
		RandomizeGender(2);
		SetBattleAttributes(32, 32, 32, 32);
		
		Attacks[0] = new PoisonJet();
		Attacks[1] = new Downpour();
		Attacks[2] = new WaterSpray();
	}
	
	//EVERYONE MUST OVERRIDE LEVELUPATTACKLIST
	@Override
	public void InitializeLevelUpAttackList()
	{
		LevelAttackList = new Tadpox().LevelAttackList;
		LevelAttackList.add(new LevelAttack(15, new WaterSpray()));
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
			case 1: spoils.add(new AquaFruit()); break;
			default: break;
		}
		return spoils;
	}
}
