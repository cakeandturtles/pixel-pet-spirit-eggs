package com.cakeandturtles.pixelpets.attacks.plant;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class Camouflage extends Attack{
	private static final long serialVersionUID = -7358403829150031855L;

	public Camouflage(){
		super("Camouflage", "Your pet attempts to blend in with its surroundings, changing their elemental types to match the foe's.",
				BattleType.Plant, 0, 100, 15);
		CanBeCriticalOrTypeAdv = false;
	}

	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
		AttackResult attackResult = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);		
		BattleEffect camouflageEffect = new BattleEffect("Camouflage", -1){
			private static final long serialVersionUID = -5407942054071160783L;

			@Override
			public String ActivateEffect(PixelPet user, PixelPet target)
			{
				super.ActivateEffect(user, target);
				user.PrimaryType = target.PrimaryType;
				user.SecondaryType = target.SecondaryType;
				String result = user.Name + " changed type to " + user.PrimaryType.toString();
				if (user.SecondaryType != null)
					result += " / " + user.SecondaryType.toString();
				return result;
			}
		};
		attackResult.Effect = camouflageEffect;
		return attackResult;
	}
}
