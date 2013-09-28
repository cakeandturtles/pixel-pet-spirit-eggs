package com.cakeandturtles.pixelpets.attacks.poison;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class SprayAcid extends Attack{
	private static final long serialVersionUID = -2100656263620899651L;

	public SprayAcid(){
		super("Spray Acid", "Your pet sprays the foe with an acidic attack that ignores the foe's resisting elemental types.", BattleType.Poison, 40, 100, 25);
	}
	
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack)
	{
		AttackResult result = new AttackResult();
		
		/**Returns an array of six values: DamageToTarget Pet and DamageToUser Pet && Recoil ResultText && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
		int typeEffectiveness = GetTypeEffectiveness(AttackType, target.PrimaryType, CanBeCriticalOrTypeAdv);
		if (typeEffectiveness < 0) typeEffectiveness = 0;
		if (target.SecondaryType != null && target.SecondaryType != BattleType.None){
			int temp = GetTypeEffectiveness(AttackType, target.SecondaryType, CanBeCriticalOrTypeAdv);
			if (temp > 0)
				typeEffectiveness += temp;
		}

		int damageToTarget = 0;
		if (BasePower > 0){
			damageToTarget = Math.round(((((2.0f * user.Level) / 5.0f + 2.0f) * (float)BasePower * (float)user.BattleAttack()) / (float)target.BattleDefense()) / 50.0f + 2.0f);
			
			//APPLYING RANDOM CRITICAL HIT
			int rand = PPApp.AppRandom.nextInt(256);
			if (rand > 223){ 
				damageToTarget *= 2;
				result.WasACriticalHit = true;
			}
			
			//APPLYING PENALIZATION OF NOT USING SAME TYPE ATTACK
			if (AttackType != BattleType.None && user.PrimaryType != AttackType && (user.SecondaryType == null || user.SecondaryType != AttackType))
				damageToTarget = Math.round((float)damageToTarget * 0.75f);
			
			//APPLY TYPE EFFECTIVENESS!!!
			damageToTarget = (int)Math.round((float)damageToTarget * Math.pow(Math.sqrt(3), typeEffectiveness)); 
			
			if (damageToTarget < 1)
				damageToTarget = 1;
		}
		int damageToUser = 0;
		
		result.DamageToTarget = damageToTarget;
		result.DamageToUser = damageToUser;
		return result;
	}
}
