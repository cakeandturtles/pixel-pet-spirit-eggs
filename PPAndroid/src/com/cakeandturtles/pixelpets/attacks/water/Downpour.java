package com.cakeandturtles.pixelpets.attacks.water;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class Downpour extends Attack{
	private static final long serialVersionUID = -6600216967335665991L;

	public Downpour(){
		super("Downpour", "Your pet drenches the foe with a heavy stream of water, lowering their defense.",
				BattleType.Water, 0, 100, 20);
		CanBeCriticalOrTypeAdv = false;
	}
	
	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
		AttackResult attackResult = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);		
		BattleEffect downpourEffect = new BattleEffect("Downpour", -1){
			private static final long serialVersionUID = 7465003786991414070L;

			@Override
			public String ActivateEffect(PixelPet user, PixelPet target)
			{
				super.ActivateEffect(user, target);
				return target.DebuffDefense(1);
			}
		};
		attackResult.Effect = downpourEffect;
		return attackResult;
	}
}
