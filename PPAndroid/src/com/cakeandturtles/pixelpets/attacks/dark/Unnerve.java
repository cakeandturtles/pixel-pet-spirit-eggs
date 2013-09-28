package com.cakeandturtles.pixelpets.attacks.dark;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class Unnerve extends Attack{
	private static final long serialVersionUID = -6411922200455246981L;

	public Unnerve(){
		super("Unnerve", "Your pet makes the foe nervous, decreasing their Attack and Speed.", BattleType.Dark, 0, 100, 25);
		CanBeCriticalOrTypeAdv = false;
	}
	
	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
		AttackResult attackResult = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);		
		BattleEffect unnerveEffect = new BattleEffect("Unnerve", -1){
			private static final long serialVersionUID = 7465003786991414070L;

			@Override
			public String ActivateEffect(PixelPet user, PixelPet target)
			{
				super.ActivateEffect(user, target);
				String result = target.DebuffAttack(1);
				result += "\n•" + target.DebuffSpeed(1);
				return result;
			}
		};
		attackResult.Effect = unnerveEffect;
		return attackResult;
	}
}
