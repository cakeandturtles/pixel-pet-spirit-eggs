package com.cakeandturtles.pixelpets.managers;

import java.io.Serializable;

public class AreaManager implements Serializable{
	private static final long serialVersionUID = 7782173494079355012L;
	
	public String Name;
	public boolean IsFairyUnlocked;
	
	public AreaManager(String name){
		Name = name;
		IsFairyUnlocked = false;
	}
}