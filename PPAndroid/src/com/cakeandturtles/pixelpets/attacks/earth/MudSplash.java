package com.cakeandturtles.pixelpets.attacks.earth;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class MudSplash extends Attack{
	private static final long serialVersionUID = -6330079065664284153L;

	public MudSplash(){
		super("Mud Splash", "Your pet splashes in the mud, attacking the foe with a decent chance to lower their speed.", BattleType.Earth, 50, 100, 30);
	}
	
	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
		AttackResult attackResult = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);		
		BattleEffect mudSplashEffect = new BattleEffect("MudSplash", -1){
			private static final long serialVersionUID = 7465003786991414070L;

			@Override
			public String ActivateEffect(PixelPet user, PixelPet target)
			{
				super.ActivateEffect(user, target);
				return target.DebuffSpeed(1);
			}
		};
		if (appRandom.nextInt(2) == 0)
			attackResult.Effect = mudSplashEffect;
		return attackResult;
	}
}
