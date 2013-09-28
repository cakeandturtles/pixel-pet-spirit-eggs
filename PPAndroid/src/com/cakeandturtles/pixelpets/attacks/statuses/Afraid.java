package com.cakeandturtles.pixelpets.attacks.statuses;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class Afraid extends BattleEffect{
	private static final long serialVersionUID = -4293372491561793049L;
	
	public Afraid()
	{
		this(new Random(System.currentTimeMillis()).nextInt(3) + 1 + 1);
	}

	public Afraid(int numTurns)
	{
		super("Afraid", numTurns);
	}
	
	@Override
	public String ActivateEffect(PixelPet user, PixelPet target){
		super.ActivateEffect(user, target);
		target.MentalStatus = this;
		return target.Name + " becomes AFRAID!";
	};
	
	@Override
	public String NullifyEffect(PixelPet user, PixelPet target){
		if (user.MentalStatus != null){
			if (user.MentalStatus.getClass().equals(this.getClass())){
				user.MentalStatus = null;
				return user.Name + " is no longer AFRAID.";
			}
		}
		return null;
	}
	
	@Override //CHANGE THIS???	
	public Object[] PersistentEffect(){
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet and STRING DESCRIPTION**/
		return new Object[]{ 0, 0, null}; 
	};
}
