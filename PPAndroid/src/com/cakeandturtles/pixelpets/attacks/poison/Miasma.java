package com.cakeandturtles.pixelpets.attacks.poison;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class Miasma extends Attack{
	private static final long serialVersionUID = -5787363523886875881L;

	public Miasma(){
		super("Miasma", "Your pet exudes a noxious air, inflicting the stat decreases they have onto the foe.", BattleType.Poison, 0, 100, 15);
		CanBeCriticalOrTypeAdv = false;
	}
	
	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
		AttackResult attackResult = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);		
		BattleEffect miasmaEffect = new BattleEffect("Miasma", -1){;
			private static final long serialVersionUID = -4171177518142348115L;

			@Override
			public String ActivateEffect(PixelPet user, PixelPet target)
			{
				super.ActivateEffect(user, target);
				String result = "";
				int helper = 0;
				if (user.SpeedModifier < target.SpeedModifier){
					helper = target.SpeedModifier - user.SpeedModifier;
					result = target.DebuffSpeed(helper);
				}
				if (user.AttackModifier < target.AttackModifier){
					helper = target.AttackModifier - user.AttackModifier;
					if (!result.trim().isEmpty()) result += "\n•";
					result += target.DebuffAttack(helper);
				}
				if (user.DefenseModifier < target.DefenseModifier){
					helper = target.DefenseModifier - user.DefenseModifier;
					if (!result.trim().isEmpty()) result += "\n•";
					result += target.DebuffDefense(helper);
				}
				if (result.trim().isEmpty())
					return "But it failed.";
				return result;
			}
		};
		attackResult.Effect = miasmaEffect;
		return attackResult;
	}
}
