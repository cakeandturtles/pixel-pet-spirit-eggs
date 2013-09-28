package com.cakeandturtles.pixelpets.attacks.earth;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class MudBath extends Attack{
	private static final long serialVersionUID = -2258951752070554423L;

	public MudBath(){
		super("Mud Bath", "Your pet bathes in the mud, increasing defense.",
				BattleType.Earth, 0, 100, 20);
		CanBeCriticalOrTypeAdv = false;
	}

	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
		AttackResult attackResult = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);		
		BattleEffect mudBathEffect = new BattleEffect("MudBath", -1){
			private static final long serialVersionUID = -8737788030056069331L;

			@Override
			public String ActivateEffect(PixelPet user, PixelPet target)
			{
				super.ActivateEffect(user, target);
				return user.BuffDefense(1);
			}
		};
		attackResult.Effect = mudBathEffect;
		return attackResult;
	}
}
