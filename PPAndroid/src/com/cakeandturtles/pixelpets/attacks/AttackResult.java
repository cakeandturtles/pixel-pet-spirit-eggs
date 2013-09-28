package com.cakeandturtles.pixelpets.attacks;

import java.io.Serializable;

public class AttackResult implements Serializable{
	private static final long serialVersionUID = 6381441337214605494L;
	/**Returns an array of six values: DamageToTarget Pet and DamageToUser Pet && Recoil ResultText && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
	public int DamageToTarget; //0
	public int DamageToUser; //1
	public String RecoilText; //2
	public BattleEffect Effect; //3//4
	public BattleEffect FieldEffect; //5
	public boolean WasACriticalHit;
	
	public AttackResult()
	{
		this(0, 0, "", null, null);
	}
	
	public AttackResult(int damageToTarget, int damageToUser, String recoilText, BattleEffect effect, BattleEffect fieldEffect)
	{
		DamageToTarget = damageToTarget;
		DamageToUser = damageToUser;
		RecoilText = recoilText;
		//TargetEffect = targetEffect;
		Effect = effect;
		FieldEffect = fieldEffect;
		WasACriticalHit = false;
	}
}
