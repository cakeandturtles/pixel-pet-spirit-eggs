package com.cakeandturtles.pixelpets.items.fruits;

import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class FrozenFruit extends PetItem{
	private static final long serialVersionUID = 7146397525650406102L;
	
	public FrozenFruit(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public FrozenFruit(){
		super("Frozen Fruit", "An edible ICE TYPE fruit.\nLoved by FIRE, LIGHT, and EARTH TYPE pets.", 0, 6);
		CollectableType = CollectableTypes.Food; //IMPORTANT!!!
		
		Effect = new FruitEffect(BattleType.Ice);
	}
}