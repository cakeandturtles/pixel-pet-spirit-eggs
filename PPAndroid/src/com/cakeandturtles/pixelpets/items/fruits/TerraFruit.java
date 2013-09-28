package com.cakeandturtles.pixelpets.items.fruits;

import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class TerraFruit extends PetItem{
	private static final long serialVersionUID = 3075291844474386850L;
	
	public TerraFruit(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public TerraFruit(){
		super("Terra Fruit", "An edible EARTH TYPE fruit.\nLoved by PLANT, WATER, and AIR TYPE pets.", 6, 5);
		CollectableType = CollectableTypes.Food; //IMPORTANT!!!
		
		Effect = new FruitEffect(BattleType.Earth);
	}
}