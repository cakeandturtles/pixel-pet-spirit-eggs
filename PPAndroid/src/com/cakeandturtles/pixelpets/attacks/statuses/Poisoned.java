package com.cakeandturtles.pixelpets.attacks.statuses;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class Poisoned extends BattleEffect{
	private static final long serialVersionUID = 3955030956408630757L;
	
	public Poisoned()
	{
		this(new Random(System.currentTimeMillis()).nextInt(5) + 4 + 1);
	}

	public Poisoned(int numTurns)
	{
		super("Poisoned", numTurns);
	}
	
	@Override
	public String ActivateEffect(PixelPet user, PixelPet target){
		super.ActivateEffect(user, target);
		target.MentalStatus = this;
		return target.Name + " becomes POISONED!";
	};
	
	@Override
	public String NullifyEffect(PixelPet user, PixelPet target){
		if (user.MentalStatus != null){
			if (user.MentalStatus.getClass().equals(this.getClass())){
				user.MentalStatus = null;
				return user.Name + " is no longer POISONED.";
			}
		}
		return null;
	}
	
	@Override //CHANGE THIS???	
	public Object[] PersistentEffect(){
		//NumTurns++;
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet and STRING DESCRIPTION**/
		return new Object[]{ 0, (int)Math.round(user.BaseHP / 16.0), user.Name + " is hurt by the POISON."};
	};
}
