package com.cakeandturtles.pixelpets.managers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.items.Container;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.fruits.FoodPocket;

public class Inventory implements Serializable{
	private static final long serialVersionUID = -7172703434035869421L;
	
	public int QuantityLimit;
	private List<PetItem> _inventory;
	public List<PetItem> getInventory(){
		return _inventory;
	}
	
	public Inventory(){
		_inventory = new ArrayList<PetItem>();
		QuantityLimit = 99;
	}
	
	public int addToInventory(PetItem item, Codex codex){
		if (item.CollectableType != PetItem.CollectableTypes.None){
			return addPocketedToInventory(item, codex);
		}
		int index = -1;
		for (int i = 0; i < _inventory.size(); i++){
			if (_inventory.get(i).getClass() == item.getClass()){
				index = i;
				break;
			}
		}
		if (index >= 0){
			int count = 0;
			while (_inventory.get(index).Quantity < QuantityLimit && item.Quantity > 0){
				_inventory.get(index).Quantity++;
				item.Quantity--;
				count++;
			}
			return count;
		}
		else
			_inventory.add(item);
		return item.Quantity;
	}
	
	private int addCollectableToInventoryDirect(PetItem item)
	{
		int index = -1;
		for (int i = 0; i < _inventory.size(); i++){
			if (_inventory.get(i).getClass() == item.getClass()){
				index = i;
				break;
			}
		}
		if (index >= 0){
			int count = 0;
			while (_inventory.get(index).Quantity < QuantityLimit && item.Quantity > 0){
				_inventory.get(index).Quantity++;
				item.Quantity--;
				count++;
			}
			return count;
		}
		else
			_inventory.add(item);
		return item.Quantity;
	}
	
	public void removeOneFromInventory(PetItem item){
		if (item.CollectableType != PetItem.CollectableTypes.None){
			removeOnePocketedFromInventory(item);
			return;
		}
		int index = -1;
		for (int i = 0; i < _inventory.size(); i++){
			if (_inventory.get(i).getClass() == item.getClass()){
				index = i;
				break;
			}
		}
		if (index >= 0){
			_inventory.get(index).Quantity--;
			if (_inventory.get(index).Quantity <= 0)
				_inventory.remove(index);
		}
	}
	
	private void removeOneItemFromInventoryDirect(PetItem item){
		int index = -1;
		for (int i = 0; i < _inventory.size(); i++){
			if (_inventory.get(i).getClass() == item.getClass()){
				index = i;
				break;
			}
		}
		if (index >= 0){
			_inventory.get(index).Quantity--;
			if (_inventory.get(index).Quantity <= 0)
				_inventory.remove(index);
		}
	}
	
	public boolean inventoryContains(PetItem item){
		if (item.CollectableType != PetItem.CollectableTypes.None)
			return inventoryContainsPocketed(item);
		
		int index = -1;
		for (int i = 0; i < _inventory.size(); i++){
			if (_inventory.get(i).getClass() == item.getClass()){
				index = i;
				break;
			}
		}
		if (index >= 0)
			return true;
		return false;
	}
	
	private boolean inventoryContainsItemDirect(PetItem item){
		int index = -1;
		for (int i = 0; i < _inventory.size(); i++){
			if (_inventory.get(i).getClass() == item.getClass()){
				index = i;
				break;
			}
		}
		if (index >= 0)
			return true;
		return false;
	}
	
	public PetItem getFromInventory(PetItem item){
		if (item.CollectableType != PetItem.CollectableTypes.None)
			return getPocketedFromInventory(item);
		
		for (int i = 0; i < _inventory.size(); i++){
			if (_inventory.get(i).getClass() == item.getClass()){
				return _inventory.get(i);
			}
		}
		return null;
	}
	
	private PetItem getCollectableFromInventoryDirect(PetItem item){
		for (int i = 0; i < _inventory.size(); i++){
			if (_inventory.get(i).getClass() == item.getClass()){
				return _inventory.get(i);
			}
		}
		return null;
	}
	
	/////////////////////////////POCKET FUNCTIONS///////////////////////
	private Container GetNewPocket(PetItem item){
		if (item.CollectableType == PetItem.CollectableTypes.Food)
			return new FoodPocket();		
		return null;
	}
	
	private Container GetExistingPocket(PetItem item){
		if (item.CollectableType == PetItem.CollectableTypes.Food)
			return (Container)getFromInventory(new FoodPocket());
		
		return null;
	}
	
	private int addPocketedToInventory(PetItem item, Codex codex){
		Container pocket = GetNewPocket(item);
		Container realPocket = GetExistingPocket(item);
		
		if (realPocket != null)
			return realPocket.addToContainer(item, QuantityLimit);
		else if (pocket != null){
			pocket.addToContainer(item, QuantityLimit);
			addToInventory(pocket, codex);
			return item.Quantity;
		}else
			return addCollectableToInventoryDirect(item);
	}
	
	private void removeOnePocketedFromInventory(PetItem item){
		Container realBag = GetExistingPocket(item);
		if (realBag != null)
			realBag.removeOneFromContainer(item);
		else removeOneItemFromInventoryDirect(item);
	}
	
	private boolean inventoryContainsPocketed(PetItem item){
		Container realBag = GetExistingPocket(item);
		if (realBag != null)
			return realBag.containerContains(item);
		else return inventoryContainsItemDirect(item);
	}
	
	private PetItem getPocketedFromInventory(PetItem item){
		Container realBag = GetExistingPocket(item);
		if (realBag != null)
			return realBag.getFromContainer(item);
		else return getCollectableFromInventoryDirect(item);
	}
}