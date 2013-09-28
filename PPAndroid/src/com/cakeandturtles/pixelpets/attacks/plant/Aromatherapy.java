package com.cakeandturtles.pixelpets.attacks.plant;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class Aromatherapy extends Attack{
	private static final long serialVersionUID = 8460557895935498782L;

	public Aromatherapy(){
		super("Aromatherapy", "Your pet lets out a pleasant aroma, restoring all the status of all pets in your party.", BattleType.Plant, 0, 100, 20);
		CanBeCriticalOrTypeAdv = false;
	}
	
	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		/**Returns an array of three values: DamageToTarget Pet and DamageToUser Pet && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
		AttackResult attackResult = super.UseAttack(user, target, lastUserAttack, lastTargetAttack, appRandom, party);		
		BattleEffect aromatherapyEffect = new BattleEffect("Aromatherapy", -1){;
			private static final long serialVersionUID = -5541879116824532667L;

			@Override
			public String ActivateEffect(PixelPet user, PixelPet target)
			{
				super.ActivateEffect(user, target);
				String result = "A pleasant aroma surrounds your party.";
				for (int i = 0; i < 4; i++){
					if (party[i] != null){
						party[i].MentalStatus = null;
						party[i].PhysicalStatus = null;
					}
				}
				return result;
			}
		};
		attackResult.Effect = aromatherapyEffect;
		return attackResult;
	}
}
