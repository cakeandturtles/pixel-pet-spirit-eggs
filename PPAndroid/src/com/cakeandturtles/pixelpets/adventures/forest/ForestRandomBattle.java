package com.cakeandturtles.pixelpets.adventures.forest;

import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.adventures.Adventure;
import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.pets.Chloropillar;
import com.cakeandturtles.pixelpets.pets.Fledgwing;
import com.cakeandturtles.pixelpets.pets.Marmoss;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class ForestRandomBattle extends Adventure{
	private static final long serialVersionUID = 8787110045052095358L;

	public ForestRandomBattle(PixelPet activePet, int consecutiveCounter){
		super(activePet);
		List<PixelPet> possibleEnemies = new ArrayList<PixelPet>();
		possibleEnemies.add(new Chloropillar());
		possibleEnemies.add(new Marmoss());
		possibleEnemies.add(new Fledgwing());
		for (int i = possibleEnemies.size()-1; i >= 0; i--){
			if (consecutiveCounter <= 10){
				if (Attack.GetTypeEffectiveness(possibleEnemies.get(i).PrimaryType, activePet.PrimaryType, true) > 0)
					possibleEnemies.remove(i);
			}
		}
		
		int rand = PPApp.AppRandom.nextInt(possibleEnemies.size());
		EnemyPets = new String[]{ possibleEnemies.get(rand).Species };
	}
}
