package com.cakeandturtles.pixelpets.attacks;

import java.io.Serializable;
import java.util.Random;

import android.graphics.Color;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public abstract class Attack implements Serializable{
	private static final long serialVersionUID = -2187163042105387636L;
	
	public String Name;
	public String Description;
	public int Priority;
	public boolean CanBeCriticalOrTypeAdv;
	public BattleType AttackType;
	public int BasePower; //0 through 100
	public int NumUses;
	public int BaseNumUses;
	
	public Attack(String name, String description, BattleType type, int basePower, int accuracy, int numUses)
	{
		Name = name;
		Description = description;
		Priority = 0;
		CanBeCriticalOrTypeAdv = true;
		AttackType = type;
		BasePower = basePower;
		//Accuracy = accuracy;
		NumUses = numUses;
		BaseNumUses = numUses;
	}
	
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		AttackResult result = new AttackResult();
		/**Returns an array of six values: DamageToTarget Pet and DamageToUser Pet && Recoil ResultText && TargetBattleEffect and UserBattleEffect && FIELD EFFECT**/
		int typeEffectiveness = GetTypeEffectiveness(AttackType, target.PrimaryType, CanBeCriticalOrTypeAdv);
		if (target.SecondaryType != null && target.SecondaryType != BattleType.None)
			typeEffectiveness += GetTypeEffectiveness(AttackType, target.SecondaryType, CanBeCriticalOrTypeAdv);

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
	
	public String GetTypeEffectiveString(BattleType PrimaryType, BattleType SecondaryType, boolean canBeCriticalOrTypeAdv){
		if (!CanBeCriticalOrTypeAdv || AttackType == BattleType.None) return "";
		int typeEffectiveness = GetTypeEffectiveness(AttackType, PrimaryType, canBeCriticalOrTypeAdv);
		if (SecondaryType != null && SecondaryType != BattleType.None)
			typeEffectiveness += GetTypeEffectiveness(AttackType, SecondaryType, canBeCriticalOrTypeAdv);
		
		if (typeEffectiveness == -2)
			return "•Terrible attack type...";
		if (typeEffectiveness == -1)
			return "•Bad attack type.";
		if (typeEffectiveness == 1)
			return "•Good type advantage!";
		if (typeEffectiveness == 2)
			return "•Extreme type advantage!!!";
		return "";
	}
	
	public static int GetTypeEffectiveness(BattleType atkType, BattleType defType, boolean canBeCriticalOrTypeAdv)
	{
		if (!canBeCriticalOrTypeAdv || atkType == BattleType.None) return 0;
		
		//method returns 1 if super effective
		//0 if neutral effective
		//-1 if not very effective
		if (atkType == defType){
			if (atkType == BattleType.Wild)
				return 1;
			return -1;
		}
		
		 if (atkType == BattleType.Glitch || defType == BattleType.Glitch){
			switch (PPApp.AppRandom.nextInt(3)){
				case 0: return 1;
				case 1: return 0;
				case 2: return -1;
			}
		}
		if (atkType == BattleType.Wild){
			if (defType == BattleType.Light || defType == BattleType.Air)
				return 1;
			if (defType == BattleType.Plant || defType == BattleType.Dark)
				return -1;
		}else if (atkType == BattleType.Plant){
			if (defType == BattleType.Wild || defType == BattleType.Water || defType == BattleType.Light || defType == BattleType.Air || defType == BattleType.Earth)
				return 1;
			if (defType == BattleType.Fire || defType == BattleType.Poison || defType == BattleType.Dark || defType == BattleType.Insect || defType == BattleType.Ice)
				return -1;
		}else if (atkType == BattleType.Water){
			if (defType == BattleType.Fire || defType == BattleType.Poison || defType == BattleType.Earth || defType == BattleType.Insect)
				return 1;
			if (defType == BattleType.Plant || defType == BattleType.Air || defType == BattleType.Ice)
				return -1;
		}else if (atkType == BattleType.Fire){
			if (defType == BattleType.Plant || defType == BattleType.Dark || defType == BattleType.Insect || defType == BattleType.Ice)
				return 1;
			if (defType == BattleType.Water || defType == BattleType.Light || defType == BattleType.Air || defType == BattleType.Earth)
				return -1;
		}else if (atkType == BattleType.Poison){
			if (defType == BattleType.Plant || defType == BattleType.Insect)
				return 1;
			if (defType == BattleType.Water || defType == BattleType.Dark || defType == BattleType.Earth)
				return -1;
		}else if (atkType == BattleType.Light){
			if (defType == BattleType.Fire || defType == BattleType.Dark || defType == BattleType.Ice)
				return 1;
			if (defType == BattleType.Wild || defType == BattleType.Plant || defType == BattleType.Insect)
				return -1;
		}else if (atkType == BattleType.Dark){
			if (defType == BattleType.Wild || defType == BattleType.Plant || defType == BattleType.Poison)
				return 1;
			if (defType == BattleType.Fire || defType == BattleType.Light || defType == BattleType.Insect)
				return -1;
		}else if (atkType == BattleType.Air){
			if (defType == BattleType.Water || defType == BattleType.Fire || defType == BattleType.Earth || defType == BattleType.Insect)
				return 1;
			if (defType == BattleType.Wild || defType == BattleType.Plant || defType == BattleType.Ice)
				return -1;
		}else if (atkType == BattleType.Earth){
			if (defType == BattleType.Fire || defType == BattleType.Poison || defType == BattleType.Ice)
				return 1;
			if (defType == BattleType.Plant || defType == BattleType.Water || defType == BattleType.Air)
				return -1;
		}else if (atkType == BattleType.Insect){
			if (defType == BattleType.Plant || defType == BattleType.Light || defType == BattleType.Dark)
				return 1;
			if (defType == BattleType.Water || defType == BattleType.Fire || defType == BattleType.Poison || defType == BattleType.Air)
				return -1;
		}else if (atkType == BattleType.Ice){
			if (defType == BattleType.Plant || defType == BattleType.Water || defType == BattleType.Air)
				return 1;
			if (defType == BattleType.Fire || defType == BattleType.Light || defType == BattleType.Earth)
				return -1;
		}
		return 0;
	}
	
	public int GetBackgroundColor(){
		if (AttackType == BattleType.Earth){
			return Color.rgb(202, 138, 99);
		}else if (AttackType == BattleType.Plant){
			return Color.rgb(96, 219, 36);
		}else if (AttackType == BattleType.Poison){
			return Color.rgb(165, 112, 182);
		}else if (AttackType == BattleType.Insect){
			return Color.rgb(209, 156, 0);
		}else if (AttackType == BattleType.Water){
			return Color.rgb(106, 158, 246);
		}else if (AttackType == BattleType.Light){
			return Color.rgb(255, 255, 147);
		}else if (AttackType == BattleType.Dark){
			return Color.rgb(111, 84, 84);
		}else if (AttackType == BattleType.Air){
			return Color.rgb(203, 178, 226);
		}else if (AttackType == BattleType.Wild){
			return Color.rgb(244, 214, 195);
		}else if (AttackType == BattleType.Glitch){
			return Color.rgb(184, 184, 184);
		}else if (AttackType == BattleType.None){
			return Color.rgb(184, 184, 184);
		}else if (AttackType == BattleType.Fire){
			return Color.rgb(253, 134, 48);
		}else if (AttackType == BattleType.Ice){
			return Color.rgb(143, 254, 255);
		}
		return 0;
	}
}
