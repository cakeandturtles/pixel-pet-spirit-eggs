package com.cakeandturtles.pixelpets.managers;

import java.io.Serializable;

public class Settings implements Serializable{
	private static final long serialVersionUID = -7578253490924130021L;
	
	public boolean NotifyUser;
	
	public Settings()
	{
		NotifyUser = true;
	}
}
