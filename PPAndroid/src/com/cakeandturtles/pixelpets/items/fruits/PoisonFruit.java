package com.cakeandturtles.pixelpets.items.fruits;

import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class PoisonFruit extends PetItem{
	private static final long serialVersionUID = 1720932139811294095L;
	
	public PoisonFruit(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public PoisonFruit(){
		super("Poison Fruit", "An edible POISON TYPE fruit.\nLoved by EARTH, DARK, and WATER TYPE pets.", 4, 5);
		CollectableType = CollectableTypes.Food; //IMPORTANT!!!
		
		Effect = new FruitEffect(BattleType.Poison);
	}
}