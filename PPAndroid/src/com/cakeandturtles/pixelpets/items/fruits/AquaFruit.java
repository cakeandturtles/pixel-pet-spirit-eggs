package com.cakeandturtles.pixelpets.items.fruits;

import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class AquaFruit extends PetItem{
	private static final long serialVersionUID = 4271349881212707304L;
	
	public AquaFruit(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public AquaFruit(){
		super("Aqua Fruit", "An edible WATER TYPE fruit.\nLoved by AIR, ICE, and PLANT TYPE pets.", 1, 5);
		CollectableType = CollectableTypes.Food; //IMPORTANT!!!
		
		Effect = new FruitEffect(BattleType.Water);
	}
}