package com.cakeandturtles.pixelpets.attacks.air;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class MimicSong extends Attack{
	private static final long serialVersionUID = -2734528455834216717L;

	public MimicSong(){
		super("Mimic Song", "Your pet imitates the last attack the foe used.", BattleType.Air, 0, 100, 25);
		CanBeCriticalOrTypeAdv = false;
	}
	
	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, final Attack lastUserAttack, final Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{		
		if (lastTargetAttack != null && !lastTargetAttack.Name.getClass().equals(this.getClass())){
			final AttackResult result = lastTargetAttack.UseAttack(user, target, lastUserAttack, null, appRandom, party);
			final String typeAdvantage = lastTargetAttack.GetTypeEffectiveString(target.PrimaryType, target.SecondaryType, lastTargetAttack.CanBeCriticalOrTypeAdv);
			
			if (result.Effect != null){
				final BattleEffect lastTargetEffect = (BattleEffect)result.Effect;
				BattleEffect mimicSongEffect = new BattleEffect("MimicSong", -1){
					private static final long serialVersionUID = -3202606393318917448L;
					@Override
					public String ActivateEffect(PixelPet user, PixelPet target){
						String activateEffect = lastTargetEffect.ActivateEffect(user, target);
						if (!typeAdvantage.trim().isEmpty())
						{
							if (!activateEffect.trim().isEmpty())
								return typeAdvantage + "\n•" + activateEffect;
							else return typeAdvantage;
						}
						return activateEffect;
					}
					@Override
					public String NullifyEffect(PixelPet user, PixelPet target){ 
						return lastTargetEffect.NullifyEffect(user, target);
					}
					@Override
					public Object[] PersistentEffect(){
						return lastTargetEffect.PersistentEffect(); 
					};
					
				};
				result.Effect = mimicSongEffect;
			}else{
				String additionalResult = (String)result.RecoilText;
				if (!typeAdvantage.trim().isEmpty())
					result.RecoilText = typeAdvantage.substring(1);
				if (!additionalResult.trim().isEmpty()){
					if (!typeAdvantage.trim().isEmpty())
						result.RecoilText = typeAdvantage + "\n•" + additionalResult;
					else result.RecoilText = additionalResult;
				}
			}
			return result;
		}
		
		return new AttackResult(0, 0, "But it failed.", null, null);
	}
}
