package com.cakeandturtles.pixelpets.items;

import java.io.Serializable;

public abstract class PetItem implements Serializable{
	private static final long serialVersionUID = -8891805320942832395L;
	
	public String Name;
	public String Description;
	public enum CollectableTypes { Leaf, Mushroom, Mineral, Wildflower, Food, None, Battle, Medicine, Treasure};
	public CollectableTypes CollectableType;
	public int Quantity;
	public ItemEffect Effect;
	public int RelAniX;
	public int RelAniY;
	
	public PetItem(String name, int relAniX, int relAniY)
	{
		Name = name;
		Description = "null descript";
		CollectableType = CollectableTypes.None;
		Quantity = 1;
		Effect = null;
		RelAniX = relAniX;
		RelAniY = relAniY;
	}
	
	public PetItem(String name, String description, int relAniX, int relAniY)
	{
		Name = name;
		Description = description;
		CollectableType = CollectableTypes.None;
		Quantity = 1;
		Effect = null;
		RelAniX = relAniX;
		RelAniY = relAniY;
	}
}
