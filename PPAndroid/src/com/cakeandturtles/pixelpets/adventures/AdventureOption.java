package com.cakeandturtles.pixelpets.adventures;

import java.io.Serializable;

import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class AdventureOption implements Serializable{
	private static final long serialVersionUID = 6039729581342933150L;
	
	public String Name;
	public String Result;
	public int RelAniX;
	public int RelAniY;
	public PixelPet EggEncounter;
	public int HPMod;
	public int ExpForOption;
	public boolean RestoresEnergy;
	public int AmbitionModifier;
	public int EmpathyModifier;
	public int InsightModifier;
	public int DiligenceModifier;
	public int CharmModifier;
	public Quest ResultingQuest;
	public Quest SubtractQuest;
	public PetItem[] ResultingItems;
	public PetItem[] SubtractItems;
	
	public AdventureOption(String name, int exp, int relAniX, int relAniY){
		Name = name;
		Result = "";
		RelAniX = relAniX;
		RelAniY = relAniY;
		
		EggEncounter = null;
		HPMod = 0;
		ExpForOption = exp;
		RestoresEnergy = false;
		
		//PersonalityModifiers
		AmbitionModifier = 0;
		EmpathyModifier = 0;
		InsightModifier = 0;
		DiligenceModifier = 0;
		CharmModifier = 0;

		ResultingQuest = null;
		SubtractQuest = null;
		ResultingItems = new PetItem[0];
		SubtractItems = new PetItem[0];
	}
	
	public AdventureOption(String name, String result, int exp, int relAniX, int relAniY){
		Name = name;
		Result = result;
		RelAniX = relAniX;
		RelAniY = relAniY;
		
		EggEncounter = null;
		HPMod = 0;
		ExpForOption = exp;
		RestoresEnergy = false;
				
		//PersonalityModifiers
		AmbitionModifier = 0;
		EmpathyModifier = 0;
		InsightModifier = 0;
		DiligenceModifier = 0;
		CharmModifier = 0;
		
		ResultingQuest = null;
		SubtractQuest = null;
		ResultingItems = new PetItem[0];
		SubtractItems = new PetItem[0];
	}
}
