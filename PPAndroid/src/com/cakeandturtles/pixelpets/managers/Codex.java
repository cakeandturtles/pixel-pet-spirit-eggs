package com.cakeandturtles.pixelpets.managers;

import java.io.Serializable;

import android.util.Log;

import com.cakeandturtles.pixelpets.pets.Chloropillar;
import com.cakeandturtles.pixelpets.pets.Cladydid;
import com.cakeandturtles.pixelpets.pets.Fledgwing;
import com.cakeandturtles.pixelpets.pets.Flutterpod;
import com.cakeandturtles.pixelpets.pets.Froaklet;
import com.cakeandturtles.pixelpets.pets.Lunactulus;
import com.cakeandturtles.pixelpets.pets.Marmoss;
import com.cakeandturtles.pixelpets.pets.Mudhog;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.Puggafrost;
import com.cakeandturtles.pixelpets.pets.Puglett;
import com.cakeandturtles.pixelpets.pets.Sinisnake;
import com.cakeandturtles.pixelpets.pets.Tadpox;

public class Codex implements Serializable{
	private static final long serialVersionUID = -1600080405043782557L;
	
	private PetCodexEntry[] _petCodex;
	public PetCodexEntry[] GetPetCodex(){ return _petCodex; } 
	public void AddToPetCodex (PixelPet pet){
		for (int i = 0; i < _petCodex.length; i++){
			if (_petCodex[i].Entry.Species.equals(pet.Species)){
				_petCodex[i].Recorded = true;
				break;
			}
		}
	}
	public PixelPet GetNewPetBySpecies(String species){
		for (int i = 0; i < _petCodex.length; i++){
			if (_petCodex[i].Entry.Species.trim().equals(species.trim())){
				try {
					return _petCodex[i].Entry.getClass().newInstance();
				} catch (Exception e) {
					Log.w("pixelpets", "ERROR: " + e.getMessage());
				} 
			}
		}
		return new Chloropillar("", 0L);
	}
	
	public Codex(){
		InitializePetCodex();
	}
	
	public void InitializePetCodex(){
		_petCodex = new PetCodexEntry[]{
			new PetCodexEntry(new Chloropillar("", 0L)),
			new PetCodexEntry(new Flutterpod("", 0L)),
			new PetCodexEntry(new Lunactulus("", 0L)),
			new PetCodexEntry(new Cladydid("", 0L)),
			//new PetCodexEntry(new Mantophyll("", 0L)),
			
			new PetCodexEntry(new Marmoss("", 0L)),
			//
			//
			//
			//
			
			new PetCodexEntry(new Fledgwing("", 0L)),
			//
			//
			//
			//
			
			new PetCodexEntry(new Puglett("", 0L)),
			new PetCodexEntry(new Mudhog("", 0L)),
			//new PetCodexEntry(new Terraboar("", 0L)),
			new PetCodexEntry(new Puggafrost("", 0L)),
			//new PetCodexEntry(new Abomabull("", 0L)),
			
			new PetCodexEntry(new Tadpox("", 0L)),
			new PetCodexEntry(new Froaklet("", 0L)),
			//new PetCodexEntry(new Bufowart("", 0L)),
			new PetCodexEntry(new Sinisnake("", 0L)),
			//new PetCodexEntry(new Nagavile("", 0L)),
		};
	}
}

class PetCodexEntry implements Serializable
{
	private static final long serialVersionUID = 2121711269407151954L;
	
	public PixelPet Entry;
	public boolean Recorded;
	
	public PetCodexEntry(PixelPet entry){
		Entry = entry;
		Recorded = false;
	}
}