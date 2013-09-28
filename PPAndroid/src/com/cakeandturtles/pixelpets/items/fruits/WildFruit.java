package com.cakeandturtles.pixelpets.items.fruits;

import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class WildFruit extends PetItem{
	private static final long serialVersionUID = -662122663496338550L;
	
	public WildFruit(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public WildFruit(){
		super("Wild Fruit", "An edible WILD TYPE fruit.\nLoved by WILD, PLANT, and DARK TYPE pets.", 1, 6);
		CollectableType = CollectableTypes.Food; //IMPORTANT!!!
		
		Effect = new FruitEffect(BattleType.Wild);
	}
}