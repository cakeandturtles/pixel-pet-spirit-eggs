package com.cakeandturtles.pixelpets.attacks;

import java.io.Serializable;

import com.cakeandturtles.pixelpets.pets.PixelPet;

public class BattleEffect implements Serializable{
	private static final long serialVersionUID = 3420340858086093773L;
	public boolean HasActivated;
	public String Name;
	public int NumTurns;
	public boolean IsPhysicalEffect;
	public boolean IsMentalEffect;
	public PixelPet user;
	public PixelPet target;
	
	public BattleEffect(String name, int numTurns)
	{
		HasActivated = false;
		Name = name;
		NumTurns = numTurns;
		IsPhysicalEffect = false;
		IsMentalEffect = false;
	}
	
	public String ActivateEffect(PixelPet user, PixelPet target){
		HasActivated = true;
		this.user = user;
		this.target = target;
		return null;
	};
	public String NullifyEffect(PixelPet user, PixelPet target){ return null;}
	public Object[] PersistentEffect(){
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet and STRING DESCRIPTION**/
		return new Object[]{ 0, 0, null}; 
	};
}
