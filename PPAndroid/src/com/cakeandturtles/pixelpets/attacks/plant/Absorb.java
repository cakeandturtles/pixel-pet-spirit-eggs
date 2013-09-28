package com.cakeandturtles.pixelpets.attacks.plant;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class Absorb extends Attack{
	private static final long serialVersionUID = -2544061026157791721L;

	public Absorb(){
		super("Absorb", "Your pet sucks energy from the foe, using it to restore their own.", BattleType.Plant, 40, 100, 30);
	}
	
	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		AttackResult result = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);
		
		int healUser = (int)Math.round(((Integer)result.DamageToTarget)/2.0);
		
		result.DamageToUser = -healUser;
		if (healUser != 0)
			result.RecoilText = user.Name + " restores some energy.";
		return result;
	}
}
