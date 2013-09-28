package com.cakeandturtles.pixelpets.attacks.statuses;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class Asleep extends BattleEffect{
	private static final long serialVersionUID = 1614222975602241231L;
	
	public Asleep()
	{
		this(new Random(System.currentTimeMillis()).nextInt(3) + 1 + 1);
	}

	public Asleep(int numTurns)
	{
		super("Asleep", numTurns);
	}
	
	@Override
	public String ActivateEffect(PixelPet user, PixelPet target){
		super.ActivateEffect(user, target);
		user.MentalStatus = this;
		return user.Name + " falls asleep!";
	};
	
	@Override
	public String NullifyEffect(PixelPet user, PixelPet target){
		if (user.MentalStatus != null){
			if (user.MentalStatus.getClass().equals(this.getClass())){
				user.MentalStatus = null;
				return user.Name + " wakes up!";
			}
		}
		return null;
	}
	
	@Override //CHANGE THIS???	
	public Object[] PersistentEffect(){
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet and STRING DESCRIPTION**/
		if (user.HP < user.BaseHP)
			return new Object[]{ 0, (-1)*(int)Math.round(user.BaseHP / 4), user.Name + " restores some HP from resting."};
		return new Object[] { 0, 0, null };
	};
}
