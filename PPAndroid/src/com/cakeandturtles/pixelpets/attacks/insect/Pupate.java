package com.cakeandturtles.pixelpets.attacks.insect;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class Pupate extends Attack{
	private static final long serialVersionUID = -8097570114663485241L;

	public Pupate(){
		super("Pupate", "Your pet wraps itself in nearby material like a cocoon, maximizing defense and zeroing speed.",
				BattleType.Insect, 0, 100, 10);
		CanBeCriticalOrTypeAdv = false;
	}

	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
		AttackResult attackResult = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);		
		BattleEffect pupateEffect = new BattleEffect("Pupate", -1){
			private static final long serialVersionUID = -4944445456713037460L;

			@Override
			public String ActivateEffect(PixelPet user, PixelPet target)
			{
				super.ActivateEffect(user, target);
				String result = user.BuffDefense(12);
				result += "\n•" + user.DebuffSpeed(12);
				return result;
			}
		};
		attackResult.Effect = pupateEffect;
		return attackResult;
	}
}
