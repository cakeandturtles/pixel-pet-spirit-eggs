package com.cakeandturtles.pixelpets.attacks.statuses;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class Berserk extends BattleEffect{
	private static final long serialVersionUID = -1132038802697193513L;
	
	public Berserk()
	{
		this(new Random(System.currentTimeMillis()).nextInt(3) + 2 + 1);
	}

	public Berserk(int numTurns)
	{
		super("Berserk", numTurns);
	}
	
	@Override
	public String ActivateEffect(PixelPet user, PixelPet target){
		super.ActivateEffect(user, target);
		target.MentalStatus = this;
		return target.Name + " begins BERSERKing!";
	};
	
	@Override
	public String NullifyEffect(PixelPet user, PixelPet target){
		if (user.MentalStatus != null){
			if (user.MentalStatus.getClass().equals(this.getClass())){
				user.MentalStatus = null;
				return user.Name + " calms down.";
			}
		}
		return null;
	}
	
	@Override //CHANGE THIS???	
	public Object[] PersistentEffect(){
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet and STRING DESCRIPTION**/
		return new Object[]{ 0, 0, user.Name + " is BERSERKing!"}; 
	};
}
