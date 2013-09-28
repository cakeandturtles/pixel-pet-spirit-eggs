package com.cakeandturtles.pixelpets.attacks;

import com.cakeandturtles.pixelpets.pets.PixelPet;

public class DirectedAttack {
	public PixelPet Target;
	public PixelPet User;
	public int Damage;
	public String ResultText;
	public BattleEffect Effect; 
	public boolean IsPhysicalEffect;
	public boolean IsMentalEffect;
	public boolean IsFieldEffect;
	public boolean IsRecoil;
	public Attack Attack;
	
	public DirectedAttack(PixelPet target, PixelPet user, BattleEffect effect, Attack attack, int damage)
	{
		Target = target;
		User = user;
		Effect = effect;
		IsFieldEffect = false;
		IsRecoil = false;
		Attack = attack;
		Damage = damage;
	}
	
	public boolean HasAttack(){
		if (Attack == null)
			return false;
		return true;
	}
	
	public void DecrementPP(){
		if (Attack == null) return;
		Attack.NumUses--;
		if (Attack.NumUses < 0)
			Attack.NumUses = 0;
	}
}
