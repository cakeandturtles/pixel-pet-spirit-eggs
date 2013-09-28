package com.cakeandturtles.pixelpets.items.fruits;

import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class VeggieFruit extends PetItem{
	private static final long serialVersionUID = 5543959240603949415L;
	
	public VeggieFruit(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public VeggieFruit(){
		super("Veggie Fruit", "An edible PLANT TYPE fruit.\nLoved by POISON, FIRE, INSECT, DARK, and ICE TYPE pets.", 5, 5);
		CollectableType = CollectableTypes.Food; //IMPORTANT!!!
		
		Effect = new FruitEffect(BattleType.Plant);
	}
}
