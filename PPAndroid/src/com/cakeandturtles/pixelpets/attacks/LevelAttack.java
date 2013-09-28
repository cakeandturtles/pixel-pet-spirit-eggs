package com.cakeandturtles.pixelpets.attacks;

import java.io.Serializable;

public class LevelAttack implements Serializable{
	private static final long serialVersionUID = -1006738046900125887L;
	
	public int Level;
	public Attack Attack;
	
	public LevelAttack(int level, Attack attack)
	{
		Level = level;
		Attack = attack;
	}
}
