package com.cakeandturtles.pixelpets.attacks.ice;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class Avalanche extends Attack{
	private static final long serialVersionUID = 5215493134828810899L;

	public Avalanche(){
		super("Avalanche", "Your pet attacks with an avalanche of snow and ice. Their is a decent chance this attack lowers the foe's speed.", BattleType.Ice, 60, 100, 25);
	}
	
	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
		AttackResult attackResult = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);		
		BattleEffect avalancheEffect = new BattleEffect("Avalanche", -1){
			private static final long serialVersionUID = 3418629547901423921L;

			@Override
			public String ActivateEffect(PixelPet user, PixelPet target)
			{
				super.ActivateEffect(user, target);
				return target.DebuffSpeed(1);
			}
		};
		if (appRandom.nextInt(2) == 0)
			attackResult.Effect = avalancheEffect;
		return attackResult;
	}
}
