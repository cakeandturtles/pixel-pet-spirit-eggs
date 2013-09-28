package com.cakeandturtles.pixelpets.items;

import java.util.ArrayList;
import java.util.List;

public class Container extends PetItem{
	private static final long serialVersionUID = -106380737316878975L;

	public List<PetItem> _collection;
	
	public Container(String name, String description, int relAniX, int relAniY) {
		super(name, description, relAniX, relAniY);
		_collection = new ArrayList<PetItem>();
	}
	
	public int addToContainer(PetItem item, int quantityLimit){
		int index = -1;
		for (int i = 0; i < _collection.size(); i++){
			if (_collection.get(i).getClass() == item.getClass()){
				index = i;
				break;
			}
		}
		
		if (index >= 0){
			int count = 0;
			while (_collection.get(index).Quantity < quantityLimit && item.Quantity > 0){
				_collection.get(index).Quantity++;
				item.Quantity--;
				count++;
			}
			return count;
		}
		else
			_collection.add(item);
		return item.Quantity;
	}
	public void removeOneFromContainer(PetItem item){
		int index = -1;
		for (int i = 0; i < _collection.size(); i++){
			if (_collection.get(i).getClass() == item.getClass()){
				index = i;
				break;
			}
		}
		
		if (index >= 0){
			_collection.get(index).Quantity--;
			if (_collection.get(index).Quantity <= 0)
				_collection.remove(index);
		}
	}
	public boolean containerContains(PetItem item){
		int index = -1;
		for (int i = 0; i < _collection.size(); i++){
			if (_collection.get(i).getClass() == item.getClass()){
				index = i;
				break;
			}
		}
		
		if (index >= 0)
			return true;
		return false;
	}
	public PetItem getFromContainer(PetItem item){
		for (int i = 0; i < _collection.size(); i++){
			if (_collection.get(i).getClass() == item.getClass()){
				return _collection.get(i);
			}
		}
		return null;
	}
}
