package com.cakeandturtles.pixelpets.attacks.poison;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.attacks.statuses.Poisoned;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class PoisonJet extends Attack{
	private static final long serialVersionUID = -4425001902187824651L;

	public PoisonJet(){
		super("Poison Jet", "Your pet shoots a jet of toxic liquids at the foe. There is a slight chance to poison the foe.", BattleType.Poison, 40, 100, 30);
	}
	
	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
		AttackResult attackResult = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);
		if (appRandom.nextInt(4) == 0){
			attackResult.Effect = new Poisoned();
		}
		return attackResult;
	}
}
