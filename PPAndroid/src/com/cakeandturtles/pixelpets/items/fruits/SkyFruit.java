package com.cakeandturtles.pixelpets.items.fruits;

import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class SkyFruit extends PetItem{
	private static final long serialVersionUID = 2192163437140302865L;
	
	public SkyFruit(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public SkyFruit(){
		super("Sky Fruit", "An edible AIR TYPE fruit.\nLoved by PLANT, WILD, and ICE TYPE pets.", 3, 5);
		CollectableType = CollectableTypes.Food; //IMPORTANT!!!
		
		Effect = new FruitEffect(BattleType.Air);
	}
}