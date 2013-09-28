package com.cakeandturtles.pixelpets.attacks;

public class FieldEffect {
	public boolean IsUserActivePet;
	public BattleEffect Effect;
	
	public FieldEffect(BattleEffect effect, boolean isUserActivePet){
		Effect = effect;
		IsUserActivePet = isUserActivePet;
	}
}
