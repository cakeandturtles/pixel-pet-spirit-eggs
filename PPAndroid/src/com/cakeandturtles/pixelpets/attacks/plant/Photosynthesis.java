package com.cakeandturtles.pixelpets.attacks.plant;

import java.util.Random;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;

public class Photosynthesis extends Attack{
	private static final long serialVersionUID = 8287744529510480948L;

	public Photosynthesis()
	{
		super("Photosynthesis", "Your pet soaks in the energy from the sun and heals some health.", 
				BattleType.Plant, 0, 100, 15);
		CanBeCriticalOrTypeAdv = false;
	}
	
	@Override
	public AttackResult UseAttack(PixelPet user, PixelPet target, Attack lastUserAttack, Attack lastTargetAttack, Random appRandom, final PixelPet[] party)
	{
		//HEAL THE USER 1/3th of health
		int damageToTarget = 0;
		
		int damageToUser = Math.round((float)-user.BaseHP/2);
		
		String recoilText = "";
		if (user.HP >= user.BaseHP)
			recoilText = "But it failed.";
		
		return new AttackResult(damageToTarget, damageToUser, recoilText, null, null);
	}
}
