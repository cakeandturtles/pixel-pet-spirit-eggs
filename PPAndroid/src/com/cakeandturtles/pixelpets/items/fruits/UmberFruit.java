package com.cakeandturtles.pixelpets.items.fruits;

import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class UmberFruit extends PetItem{
	private static final long serialVersionUID = 7772057705266450307L;
	
	public UmberFruit(int quantity)
	{
		this();
		Quantity = quantity;
	}

	public UmberFruit(){
		super("Umber Fruit", "An edible DARK TYPE fruit.\nLoved by LIGHT, FIRE, and INSECT TYPE pets.", 2, 5);
		CollectableType = CollectableTypes.Food; //IMPORTANT!!!
		
		Effect = new FruitEffect(BattleType.Dark);
	}
}

