package com.cakeandturtles.pixelpets.pets;

import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.attacks.LevelAttack;
import com.cakeandturtles.pixelpets.attacks.earth.MudBath;
import com.cakeandturtles.pixelpets.attacks.earth.MudSplash;
import com.cakeandturtles.pixelpets.attacks.ice.Avalanche;
import com.cakeandturtles.pixelpets.attacks.wild.Headbutt;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.fruits.FrozenFruit;
import com.cakeandturtles.pixelpets.items.fruits.TerraFruit;


public class Puggafrost extends PixelPet{ //The Wooly Pig Pet
	private static final long serialVersionUID = -8515323261501928434L;

	public Puggafrost(){
		this("", 0L);
	}

	public Puggafrost(String trainerName, long trainerId){
		super("Puggafrost", "Puggafrost", BattleType.Earth, BattleType.Ice, trainerName, trainerId);
		RelAniX = 6;
		RelAniY = 2;
		LevelWhenEvolve = 35; //IMPORTANT
		CurrentForm = PetForm.Secondary; //ALSO IMPORTANT
		
		RandomizeGender(2);
		SetBattleAttributes(32, 32, 32, 32);
		
		Attacks[0] = new Avalanche();
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
		LevelAttackList.add(new LevelAttack(15, new Avalanche()));
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
			case 1: spoils.add(new FrozenFruit()); break;
			default: break;
		}
		return spoils;
	}
}
