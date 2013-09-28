package com.cakeandturtles.pixelpets.adventures;

import java.io.Serializable;
import java.util.List;

import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.managers.Inventory;

public class Quest implements Serializable{
	private static final long serialVersionUID = -5849090056811986001L;
	
	public String Name;
	public String Description;
	public List<PetItem> NeededItems;
	
	public boolean IsQuestComplete(Inventory inventory){
		//TODO:: NOTE:: THIS DOES NOT ACTUALLY SUBTRACT THE ITEMS FROM YOUR INVENTORY!!!
		
		boolean questComplete = true;
		for (int i = 0; i < NeededItems.size(); i++){
			PetItem item = inventory.getFromInventory(NeededItems.get(i));
			if (item == null || item.Quantity < NeededItems.get(i).Quantity){
				questComplete = false;
				break;
			}
		}
		
		return questComplete; 
	}
	
	public String GetItems(){
		String result = "";
		for (int i = 0; i < NeededItems.size(); i++){
			PetItem item = NeededItems.get(i);
			if (i > 0) result += ", ";
			result += item.Quantity + " " + item.Name;
		}
		return result;
	}
	
	public PetItem[] GetItemsAsArray(){
		PetItem[] result = new PetItem[NeededItems.size()];
		for (int i = 0; i < NeededItems.size(); i++){
			result[i] = NeededItems.get(i);
		}
		return result;
	}
}