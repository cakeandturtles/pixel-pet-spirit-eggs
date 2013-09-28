package com.cakeandturtles.pixelpets.items.collectables;

import com.cakeandturtles.pixelpets.items.Container;
import com.cakeandturtles.pixelpets.items.PetItem;

public class CollectablePocket extends Container{
	private static final long serialVersionUID = 3878483319175200999L;

	public CollectablePocket(){
		super("Collectable Bag", "A satchel for holding various collectable items.", 0, 0);
	}
	
	private Container GetNewBag(PetItem item){
		if (item.CollectableType == PetItem.CollectableTypes.Leaf)
			return new LeafBag();
		else if (item.CollectableType == PetItem.CollectableTypes.Mushroom)
			return new ShroomBag();
		else if (item.CollectableType == PetItem.CollectableTypes.Mineral)
			return new MineralBag();
		else if (item.CollectableType == PetItem.CollectableTypes.Wildflower)
			return new FlowerBag();		
		
		return null;
	}
	
	private Container GetExistingBag(PetItem item){
		if (item.CollectableType == PetItem.CollectableTypes.Leaf)
			return (Container)getFromContainer(new LeafBag());
		else if (item.CollectableType == PetItem.CollectableTypes.Mushroom)
			return (Container)getFromContainer(new ShroomBag());
		else if (item.CollectableType == PetItem.CollectableTypes.Mineral)
			return (Container)getFromContainer(new MineralBag());
		else if (item.CollectableType == PetItem.CollectableTypes.Wildflower)
			return (Container)getFromContainer(new FlowerBag());
		
		return null;
	}
	
	@Override
	public int addToContainer(PetItem item, int quantityLimit){
		Container bag = GetNewBag(item);
		Container realBag = GetExistingBag(item);
		
		if (realBag != null)
			return realBag.addToContainer(item, quantityLimit);
		else if (bag != null){
			bag.addToContainer(item, quantityLimit);
			super.addToContainer(bag, quantityLimit);
			return item.Quantity;
		}else
			return super.addToContainer(item, quantityLimit);
	}
	
	@Override
	public void removeOneFromContainer(PetItem item){
		Container realBag = GetExistingBag(item);
		if (realBag != null)
			realBag.removeOneFromContainer(item);
		else super.removeOneFromContainer(item);
	}
	
	@Override
	public boolean containerContains(PetItem item){
		Container realBag = GetExistingBag(item);
		if (realBag != null)
			return realBag.containerContains(item);
		else return super.containerContains(item);
	}
	
	@Override
	public PetItem getFromContainer(PetItem item){
		Container realBag = GetExistingBag(item);
		if (realBag != null)
			return realBag.getFromContainer(item);
		else return super.getFromContainer(item);
	}
}
