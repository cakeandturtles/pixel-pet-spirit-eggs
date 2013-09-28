package com.cakeandturtles.pixelpets.attacks.insect;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class Swarm extends Attack{
	private static final long serialVersionUID = -8802464799171044017L;

	public Swarm(){
		super("Swarm", "Your pet summons a swarm of tiny bugs to attack the foe, dealing damage equal to the user's level.", BattleType.Insect, 0, 100, 30);
		CanBeCriticalOrTypeAdv = false;
	}
	
	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
		AttackResult attackResult = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);		
		attackResult.DamageToTarget = user.Level;
		return attackResult;
	}
}
