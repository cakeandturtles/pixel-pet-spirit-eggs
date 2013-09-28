package com.cakeandturtles.pixelpets.items.fruits;

import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class BugFruit extends PetItem{
	private static final long serialVersionUID = 5359775889486757487L;
	
	public BugFruit(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public BugFruit(){
		super("Bug Fruit", "An edible INSECT TYPE fruit.\nLoved by WATER, FIRE, POISON, and AIR TYPE pets.", 7, 5);
		CollectableType = CollectableTypes.Food; //IMPORTANT!!!
		
		Effect = new FruitEffect(BattleType.Insect);
	}
}