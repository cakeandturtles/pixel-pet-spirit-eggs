package com.cakeandturtles.pixelpets.managers;

import java.io.Serializable;

import android.util.Log;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.NormalAttack;
import com.cakeandturtles.pixelpets.attacks.air.ClearAir;
import com.cakeandturtles.pixelpets.attacks.air.Flurry;
import com.cakeandturtles.pixelpets.attacks.air.MimicSong;
import com.cakeandturtles.pixelpets.attacks.dark.SneakAttack;
import com.cakeandturtles.pixelpets.attacks.dark.Unnerve;
import com.cakeandturtles.pixelpets.attacks.earth.GroundAttack;
import com.cakeandturtles.pixelpets.attacks.earth.MudBath;
import com.cakeandturtles.pixelpets.attacks.earth.MudSplash;
import com.cakeandturtles.pixelpets.attacks.ice.Avalanche;
import com.cakeandturtles.pixelpets.attacks.insect.BugBite;
import com.cakeandturtles.pixelpets.attacks.insect.Pupate;
import com.cakeandturtles.pixelpets.attacks.insect.Swarm;
import com.cakeandturtles.pixelpets.attacks.plant.Absorb;
import com.cakeandturtles.pixelpets.attacks.plant.Aromatherapy;
import com.cakeandturtles.pixelpets.attacks.plant.Camouflage;
import com.cakeandturtles.pixelpets.attacks.plant.Photosynthesis;
import com.cakeandturtles.pixelpets.attacks.poison.Miasma;
import com.cakeandturtles.pixelpets.attacks.poison.PoisonJet;
import com.cakeandturtles.pixelpets.attacks.poison.SprayAcid;
import com.cakeandturtles.pixelpets.attacks.water.Downpour;
import com.cakeandturtles.pixelpets.attacks.water.Squirt;
import com.cakeandturtles.pixelpets.attacks.water.WaterSpray;
import com.cakeandturtles.pixelpets.attacks.wild.Headbutt;
import com.cakeandturtles.pixelpets.attacks.wild.RecklessCharge;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.collectables.DryLeaf;
import com.cakeandturtles.pixelpets.items.collectables.MoonLeaf;
import com.cakeandturtles.pixelpets.items.collectables.StarLeaf;
import com.cakeandturtles.pixelpets.items.fruits.AquaFruit;
import com.cakeandturtles.pixelpets.items.fruits.BugFruit;
import com.cakeandturtles.pixelpets.items.fruits.FrozenFruit;
import com.cakeandturtles.pixelpets.items.fruits.PoisonFruit;
import com.cakeandturtles.pixelpets.items.fruits.SkyFruit;
import com.cakeandturtles.pixelpets.items.fruits.TerraFruit;
import com.cakeandturtles.pixelpets.items.fruits.UmberFruit;
import com.cakeandturtles.pixelpets.items.fruits.VeggieFruit;
import com.cakeandturtles.pixelpets.items.fruits.WildFruit;
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
	
	private AttackDexEntry[] _attackDex;
	public AttackDexEntry[] GetAttackDex(){ return _attackDex; }
	public void AddToAttackDex(Attack attack){
		for (int i = 0; i < _attackDex.length; i++){
			if (_attackDex[i].Entry.Name.equals(attack.Name)){
				_attackDex[i].Recorded = true;
				break;
			}
		}
	}
	
	private CollectableEntry[] _foodDex;
	public CollectableEntry[] GetFoodDex(){ return _foodDex; }
	public void AddToFoodDex(PetItem food){
		for (int i = 0; i < _foodDex.length; i++){
			if (_foodDex[i].Entry.Name.equals(food.Name)){
				_foodDex[i].Recorded = true;
				break;
			}
		}
	}
	private CollectableEntry[] _treasureDex;
	public CollectableEntry[] GetTreasureDex(){ return _treasureDex; }
	public void AddToTreasureDex(PetItem treasure){
		for (int i = 0; i < _treasureDex.length; i++){
			if (_treasureDex[i].Entry.Name.equals(treasure.Name)){
				_treasureDex[i].Recorded = true;
				break;
			}
		}
	}
	
	private CollectableEntry[] _leafDex;
	public CollectableEntry[] GetLeafDex(){ return _leafDex; }
	public void AddToLeafDex(PetItem leaf){
		for (int i = 0; i < _leafDex.length; i++){
			if (_leafDex[i].Entry.Name.equals(leaf.Name)){
				_leafDex[i].Recorded = true;
				break;
			}
		}
	}
	private CollectableEntry[] _shroomDex;
	public CollectableEntry[] GetShroomDex(){ return _shroomDex; }
	public void AddToShroomDex(PetItem shroom){
		for (int i = 0; i < _shroomDex.length; i++){
			if (_shroomDex[i].Entry.Name.equals(shroom.Name)){
				_shroomDex[i].Recorded = true;
			}
		}
	}
	private CollectableEntry[] _mineralDex;
	public CollectableEntry[] GetMineralDex(){ return _mineralDex; }
	public void AddToMineralDex(PetItem mineral){
		for (int i = 0; i < _mineralDex.length; i++){
			if (_mineralDex[i].Entry.Name.equals(mineral.Name)){
				_mineralDex[i].Recorded = true;
			}
		}
	}
	private CollectableEntry[] _wildflowerDex;
	public CollectableEntry[] GetWildflowerDex(){ return _wildflowerDex; }
	public void AddToWildflowerDex(PetItem flower){
		for (int i = 0; i < _wildflowerDex.length; i++){
			if (_wildflowerDex[i].Entry.Name.equals(flower.Name)){
				_wildflowerDex[i].Recorded = true;
			}
		}
	}
	
	public Codex(){
		InitializePetCodex();
		InitializeAttackDex();
		InitializeFoodDex();
		InitializeTreasureDex();
		InitializeLeafDex();
		InitializeShroomDex();
		InitializeMineralDex();
		InitializeWildflowerDex();
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
	
	public void InitializeAttackDex(){
		_attackDex = new AttackDexEntry[]{
			//WILD ATTACKS
			new AttackDexEntry(new Headbutt()),
			new AttackDexEntry(new RecklessCharge()),
				
			//PLANT ATTACKS
			new AttackDexEntry(new Absorb()),
			new AttackDexEntry(new Aromatherapy()),
			new AttackDexEntry(new Camouflage()),
			new AttackDexEntry(new Photosynthesis()),
			
			//WATER ATTACKS
			new AttackDexEntry(new Downpour()),
			new AttackDexEntry(new Squirt()), 
			new AttackDexEntry(new WaterSpray()), 
			
			//FIRE ATTACKS
			
			//POISON ATTACKS
			new AttackDexEntry(new Miasma()), 
			new AttackDexEntry(new PoisonJet()),
			new AttackDexEntry(new SprayAcid()), 
			
			//LIGHT ATTACKS
			
			//DARK ATTACKS
			new AttackDexEntry(new SneakAttack()),
			new AttackDexEntry(new Unnerve()),
			
			//AIR ATTACKS
			new AttackDexEntry(new ClearAir()), 
			new AttackDexEntry(new Flurry()),
			new AttackDexEntry(new MimicSong()), 
				
			//EARTH ATTACKS
			new AttackDexEntry(new GroundAttack()),
			new AttackDexEntry(new MudBath()),
			new AttackDexEntry(new MudSplash()),
			
			//INSECT ATTACKS
			new AttackDexEntry(new BugBite()),
			new AttackDexEntry(new Pupate()), 
			new AttackDexEntry(new Swarm()), 
			
			//ICE ATTACKS
			new AttackDexEntry(new Avalanche()),
			
			//GLITCH ATTACKS
			
			//NONE ATTACKS
			new AttackDexEntry(new NormalAttack())
		};
	}
	
	public void InitializeFoodDex(){
		_foodDex = new CollectableEntry[]{
			new CollectableEntry(new AquaFruit()),
			new CollectableEntry(new BugFruit()),
			new CollectableEntry(new FrozenFruit()), 	
			new CollectableEntry(new PoisonFruit()),
			new CollectableEntry(new SkyFruit()), 
			new CollectableEntry(new TerraFruit()),
			new CollectableEntry(new UmberFruit()),
			new CollectableEntry(new VeggieFruit()),
			new CollectableEntry(new WildFruit())
		};
	}
	public void InitializeTreasureDex(){
		_treasureDex = new CollectableEntry[]{
				
		};
	}
	
	public void InitializeLeafDex(){
		_leafDex = new CollectableEntry[]{
			new CollectableEntry(new DryLeaf()),
			new CollectableEntry(new MoonLeaf()),
			new CollectableEntry(new StarLeaf())
			
		};
	}
	
	public void InitializeShroomDex(){
		_shroomDex = new CollectableEntry[]{
		};
	}
	
	public void InitializeMineralDex(){
		_mineralDex = new CollectableEntry[]{
		};
	}
	
	public void InitializeWildflowerDex(){
		_wildflowerDex = new CollectableEntry[]{
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

class AttackDexEntry implements Serializable
{
	private static final long serialVersionUID = -8277388052979881556L;
	
	public Attack Entry;
	public boolean Recorded;
	
	public AttackDexEntry(Attack entry){
		Entry = entry;
		Recorded = false;
	}
}

class CollectableEntry implements Serializable
{
	private static final long serialVersionUID = 478077563531851647L;
	
	public PetItem Entry;
	public boolean Recorded;
	
	public CollectableEntry(PetItem entry){
		Entry = entry;
		Recorded = false;
	}
}