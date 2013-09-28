package com.cakeandturtles.pixelpets.adventures.forest;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.adventures.Adventure;
import com.cakeandturtles.pixelpets.pets.PixelPet;

public class ForestGhostEncounter extends Adventure{
	private static final long serialVersionUID = 2443001086469300312L;

	public ForestGhostEncounter(PixelPet activePet){
		super(activePet);
		Description = activePet.Name + " was startled by a Forest Spirit:\n\"Leave this plaAaAaAace, " + activePet.Name + "!\"\n\n";
		activePet.HP -= activePet.BaseHP / 4;
		Description += activePet.Name + " loses some HP out of fear...\n";
		if (activePet.HP <= 0){
			activePet.HP = 0;
			Description += activePet.Name + " is OUT OF ENERGY.\n";
		}
		
		int helper = 0;
		switch (PPApp.AppRandom.nextInt(5)){
			case 0: 
				helper = activePet.ModifyAmbition(-5);
				if (helper < 0) Description += "\n" + activePet.Name + " LOSES AMBITION...";
				else Description += "\n" + activePet.Name + "'s AMBITION is zero.";
				break;
			case 1: 
				helper = activePet.ModifyCharm(-5); 
				if (helper < 0) Description += "\n" + activePet.Name + " LOSES CHARM...";
				else Description += "\n" + activePet.Name + "'s CHARM is zero.";
				break;
			case 2: 
				helper = activePet.ModifyEmpathy(-5); 
				if (helper < 0) Description += "\n" + activePet.Name + " LOSES EMPATHY...";
				else Description += "\n" + activePet.Name + "'s EMPATHY is zero.";
				break;
			case 3: 
				helper = activePet.ModifyInsight(-5); 
				if (helper < 0) Description += "\n" + activePet.Name + " LOSES INSIGHT...";
				else Description += "\n" + activePet.Name + "'s INSIGHT is zero.";
				break;
			case 4: 
				helper = activePet.ModifyDiligence(-5);
				if (helper < 0) Description += "\n" + activePet.Name + " LOSES DILIGENCE...";
				else Description += "\n" + activePet.Name + "'s DILIGENCE is zero.";
				break;
			default: break;
		}
		
		RelAniX = 0;
		RelAniY = 3;
	}
}
