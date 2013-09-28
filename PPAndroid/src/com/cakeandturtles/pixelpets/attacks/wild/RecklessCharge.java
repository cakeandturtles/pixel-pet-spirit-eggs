package com.cakeandturtles.pixelpets.attacks.wild;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class RecklessCharge extends Attack{
	private static final long serialVersionUID = -3339124208394421845L;

	public RecklessCharge(){
		super("Reckless Charge", "Your pet wildly charges into the foe, hurting themself in the process.", BattleType.Wild, 80, 100, 20);
	}
	
	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		//GET HURT BY RECOIL
		AttackResult attackResult = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);
		
		attackResult.DamageToUser = (Integer)Math.round((Integer)attackResult.DamageToTarget/2.0f);
		attackResult.RecoilText = user.Name + " is hurt by the attack.";
		
		return attackResult;
	}
}
