package com.cakeandturtles.pixelpets.managers;

import java.io.Serializable;

public class TrainerObject implements Serializable{
	private static final long serialVersionUID = -6737727965805755779L;
	
	public String TrainerName;
	public long TrainerID;
	
	public TrainerObject(String trainerName, long trainerId){
		TrainerName = trainerName;
		TrainerID = trainerId;
	}
}
