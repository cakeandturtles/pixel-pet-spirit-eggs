package com.cakeandturtles.pixelpets.managers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cakeandturtles.pixelpets.pets.PixelPet;

public class DaycareManager implements Serializable{
	private static final long serialVersionUID = 1951503321824865804L;

	public int DaycareLimit;
	private List<PixelPet> _petStorage;
	public List<PixelPet> GetStoredPets(){ return _petStorage; }
	public boolean IsDaycareFull(){
		if (_petStorage.size() >= DaycareLimit) return true;
		return false;
	}
	public boolean AddToPetStorage (PixelPet pet){
		if (_petStorage.size() < DaycareLimit){
			_petStorage.add(pet);
			return true;
		}return false;
	}
	public PixelPet LocateInStorage(int index){
		return _petStorage.get(index);
	}
	public PixelPet WithdrawFromStorage(PixelPet pet){
		for (int i = 0; i < _petStorage.size(); i++){
			if (_petStorage.get(i) == pet){
				return _petStorage.remove(i);
			}
		}
		return null;
	}
	public void ReleaseFromStorage(PixelPet pet){
		for (int i = 0; i < _petStorage.size(); i++){
			if (_petStorage.get(i) == pet){
				_petStorage.remove(i);
			}
		}
	}
	
	public DaycareManager()
	{
		_petStorage = new ArrayList<PixelPet>();
		DaycareLimit = 225;
	}
}
