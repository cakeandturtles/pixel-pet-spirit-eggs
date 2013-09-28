package com.cakeandturtles.pixelpets.attacks;

import java.util.Random;

import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class Struggle extends Attack{
	private static final long serialVersionUID = -7048455950733421461L;

	public Struggle(){
		super("Struggle", "Your pet is out of energy, but still tries to fight.", BattleType.None, 30, 100, 99);
	}
	
	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party){
		AttackResult attackResult = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);
		attackResult.DamageToUser = (Integer)attackResult.DamageToTarget / 2;
		attackResult.RecoilText = user.Name + " hurts themself in the struggle.";
		
		return attackResult;
	}
}
