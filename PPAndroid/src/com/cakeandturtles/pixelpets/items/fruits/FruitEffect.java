package com.cakeandturtles.pixelpets.items.fruits;

import com.cakeandturtles.pixelpets.items.ItemEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class FruitEffect extends ItemEffect{
	private static final long serialVersionUID = 6370199921166186410L;
	
	BattleType MyType;
	
	public FruitEffect(BattleType myType){
		MyType = myType;
		NeedsATarget = true;
	}
	
	public boolean NoEffect(PixelPet targetPet){
		if (targetPet.Hunger >= 100)
			return true;
		return false;
	}

	public String DoItemEffect(PixelPet targetPet, PixelPet battleFoe)
	{
		int hungerMod = 30;
		int hMMod = 0;
		
		String result = targetPet.Name + " eats the fruit.\n";
		if (hMMod > 0){ 
			hungerMod *= 2;
			result = targetPet.Name + " scarves down the fruit!\n";
		}
		if (hMMod < 0){ 
			hungerMod /= 2;
			result = targetPet.Name + " chews the fruit timidly, and doesn't even finish it.\n";
		}
		targetPet.Hunger += hungerMod;
		if (targetPet.Hunger >= 100) targetPet.Hunger = 100;
		
		if (targetPet.Hunger < 60)
			result += targetPet.Name + " is still hungry!!";
		else if (targetPet.Hunger < 100)
			result += targetPet.Name + " is full up!";
		else if (targetPet.Hunger >= 100)
			result += targetPet.Name + " is bloated!";
		return result;
	}
}