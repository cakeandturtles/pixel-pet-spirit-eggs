package com.cakeandturtles.pixelpets.attacks.air;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class ClearAir extends Attack{
	private static final long serialVersionUID = -5348281730281585420L;

	public ClearAir(){
		super("Clear Air", "Your pet emits a pure air, negating all stat changes to all creatures in the battle.", BattleType.Air, 0, 100, 15);
		CanBeCriticalOrTypeAdv = false;
	}
	
	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
		AttackResult attackResult = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);		
		BattleEffect cleanAirEffect = new BattleEffect("CleanAir", -1){;
			private static final long serialVersionUID = 4912575592322107136L;

			@Override
			public String ActivateEffect(PixelPet user, PixelPet target)
			{
				super.ActivateEffect(user, target);
				user.ResetBattleStats(false, false, false, false, false);
				target.ResetBattleStats(false, false, false, false, false);
	
				return "All stat changes are neutralized.";
			}
		};
		attackResult.Effect = cleanAirEffect;
		return attackResult;
	}
}
