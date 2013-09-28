package com.cakeandturtles.pixelpets.pets;

import java.io.Serializable;
import java.util.UUID;

import android.graphics.Color;

import com.cakeandturtles.pixelpets.PPApp;
import com.example.pixelpets.R;

public abstract class PixelPet implements Serializable{	
	private static final long serialVersionUID = 6247879320823332278L;
	
	//TRAINER ATTRIBUTES
	public String TrainerName;
	public long TrainerID;
	
	//Basic Attributes
	public String PID;
	public String Name;
	public String Species;
	public enum PetGender { Male, Female }
	public PetGender Gender;
	public int Exp;
	public int ExpToNextLevel;
	public int Level;
	public int MaxLevel;
	public int LevelWhenEvolve;
	public String PetDescription;
	
	//Animation Attributes
	public int RelAniX;
	public int RelAniY;
	public int FrameCount;
	public int CurrFrame;
	public int MaxFrame;
	public int FrameDelay;
	
	//State Attributes
	public enum PetForm { Egg, Primary, Secondary, Tertiary }
	public PetForm CurrentForm;
	public boolean JustEvolved;
	public boolean HaveIBeenNamed;
	public long TimeEggFound;
	public long TimeEggHatched;
	
	//Recover Attributes
	public long EnergyRestoreTimer;
	public int Hunger;
	
	//Battle Attributes	
	public enum BattleType { Wild, Plant, Water, Fire, Poison, Light, Dark, Air, Earth, Insect, Ice, Glitch, None }
	public BattleType TruePrimaryType;
	public BattleType PrimaryType;
	public BattleType TrueSecondaryType;
	public BattleType SecondaryType;
	
	//Personality Attributes (Each int value from 0 to 100)
	protected int ambition;
	protected int empathy;
	protected int insight;
	protected int diligence;
	protected int charm;
	
	//Relationship Attributes
	
	//Constructor
	public PixelPet(String name, String species, BattleType primaryType, BattleType secondaryType, String trainerName, long trainerId)
	{
		TrainerName = trainerName;
		TrainerID = trainerId;
	
		Name = name;
		Species = species;
		Gender = PetGender.Female;
		Exp = 0;
		ExpToNextLevel = 8;
		Level = 0;
		LevelWhenEvolve = 0;
		MaxLevel = 50;
		PetDescription = "The egg is warm but doesn't move much.";
		
		RelAniX = 0;
		RelAniY = 0;
		FrameCount = 0;
		CurrFrame = 0;
		MaxFrame = 2;
		FrameDelay = 100;
		
		CurrentForm = PetForm.Egg;
		JustEvolved = false;
		HaveIBeenNamed = true;
		ResetEggTimer();
		
		EnergyRestoreTimer = System.currentTimeMillis();
		Hunger = 100;
		
		TruePrimaryType = primaryType;
		PrimaryType = primaryType;
		TrueSecondaryType = secondaryType;
		SecondaryType = secondaryType;
		
		ambition = 0;
		empathy = 0;
		insight = 0;
		diligence = 0;
		charm = 0;
		
		PID = GenerateUniquePID();
	}
	
	private String GenerateUniquePID(){
		String pid = GetSpeciesAndGender() + TimeEggFound;
		pid += UUID.randomUUID().getLeastSignificantBits() + "";
		return pid;
	}
	
	public void ResetEggTimer()
	{
		TimeEggFound = System.currentTimeMillis();
		TimeEggHatched = TimeEggFound + 90000;
	}
	
	public int CalculateRealExp(int exp)
	{
		if (exp >= 0){
			if (charm >= 255) return exp * 2;
			else if (charm >= 128)
				return (int)Math.round((float)exp * 1.5f);
		}else{
			if (charm >= 255)
				return (int)Math.round((float)exp / 2.0f);
			else if (charm >= 128)
				return (int)Math.round((float)exp / 1.5f);
		}
		return exp;
	}
	
	public int GetExpYield(long trainerId, int numParticipants)
	{
		float t = 1;
		if (trainerId == TrainerID) t = 1.5f;
		float b = 64;
		if (CurrentForm == PetForm.Secondary) b = 128;
		else if (CurrentForm == PetForm.Tertiary) b = 256;
		
		float exp = t * Level * b;
		exp /= 7.0;
		exp /= numParticipants;
		return Math.round(exp) + 1;
	}
	
	protected void RandomizeGender(int x)
	{
		int i = PPApp.AppRandom.nextInt(x) + 0;
		if (i == 0)
			Gender = PetGender.Female;
		else Gender = PetGender.Male;
	}
	
	public void RandomizePersonalityAttributes()
	{
		ambition = PPApp.AppRandom.nextInt(256);
		empathy = PPApp.AppRandom.nextInt(256);
		diligence = PPApp.AppRandom.nextInt(256);
		insight = PPApp.AppRandom.nextInt(256);
		charm = PPApp.AppRandom.nextInt(256);
	}
	
	public void RestartEnergyRestoreTimer(){
		EnergyRestoreTimer = System.currentTimeMillis();
	}
	
	public String GetHungerString(){
		if (Hunger == 100)
			return "Bloated";
		if (Hunger >= 60)
			return "Full up";
		if (Hunger >= 50)
			return "A little hungry";
		if (Hunger >= 25)
			return "Pretty hungry";
		return "Starving!";
	}
	
	public String He(boolean capitalized){
		if (Gender == PetGender.Male)
		{
			if (capitalized)
				return "He";
			return "he";
		}
		if (capitalized)
			return "She";
		return "she";
	}
	
	public void TapPet()
	{
		FrameCount = FrameDelay;
		UpdateAnimation();
		
		if (CurrentForm == PetForm.Egg)
		{
			if (PPApp.AppRandom.nextInt(2) <= 0)
				IncrementRandomPersonalityAttribute();
			long timeEggHatched = TimeEggHatched - TimeEggFound;
			long currentTime = System.currentTimeMillis() - TimeEggFound;
			if (currentTime >= (timeEggHatched * 2) / 3)
				TimeEggHatched -= 250;
			else if (currentTime >= timeEggHatched / 3)
				TimeEggHatched -= 500;
			else
				TimeEggHatched -= 1000;
		}
	}
	
	public void IncrementRandomPersonalityAttribute()
	{
		switch (PPApp.AppRandom.nextInt(5)){
			case 0: ModifyAmbition(1);
				break;
			case 1: ModifyEmpathy(1);
				break;
			case 2: ModifyInsight(1);
				break;
			case 3: ModifyDiligence(1);
				break;
			case 4: ModifyCharm(1);
				break;
			default: break;
		}
	}
	
	public void Update()
	{
		UpdatePetForm();		
		UpdateAnimation();
	}
	
	public int GetScaledExp(float divisor){
		int result = Math.round((float)(ExpToNextLevel - (int)Math.pow(Level, 3))/divisor);
		if (result < 5) result = 5; //FORCE A MINIMUM of 5 EXP!!!
		return result;
	}
	
	public void LevelUp()
	{
		if (Level < MaxLevel){
			Level++;
			if (Exp < ExpToNextLevel) Exp = ExpToNextLevel;
			ExpToNextLevel = (int)Math.pow(Level+1, 3);
		}else Exp = 0;
	}
	
	public void UpdatePetForm()
	{
		if (CurrentForm == PetForm.Egg)
			UpdateEgg();
		else if (LevelWhenEvolve > 0 && Level >= LevelWhenEvolve){
			JustEvolved = true;
			EvolveForm();
		}
	}
	
	public PixelPet Evolve(){ return this; }
	
	public void EvolveForm(){
		if (CurrentForm == PetForm.Primary)
			CurrentForm = PetForm.Secondary;
		else if (CurrentForm == PetForm.Secondary)
			CurrentForm = PetForm.Tertiary; 
	}
	
	public void EvolveFrom(PixelPet baby)
	{
		//Set up Everything
		TrainerName = baby.TrainerName;
		TrainerID = baby.TrainerID;
		
		if (baby.Name.equals(baby.Species)) 
			Name = Species;
		else Name = baby.Name;
		Gender = baby.Gender;
		Exp = baby.Exp;
		ExpToNextLevel = baby.ExpToNextLevel;
		Level = baby.Level;
		TimeEggFound = baby.TimeEggFound;
		TimeEggHatched = baby.TimeEggHatched;
		
		ambition = baby.ambition;
		empathy = baby.empathy;
		diligence = baby.diligence;
		insight = baby.insight;
		charm = baby.charm;
		
		HaveIBeenNamed = true;
	}
	
	public int GetDrawableId(boolean small){
		if (CurrentForm == PixelPet.PetForm.Primary){
			if (small) return R.drawable.primary_sheet_small;
			return R.drawable.primary_sheet;
		}if (CurrentForm == PixelPet.PetForm.Secondary){
			if (small) return R.drawable.secondary_sheet_small;
			return R.drawable.secondary_sheet;
		}if (CurrentForm == PixelPet.PetForm.Tertiary){
			if (small) return R.drawable.tertiary_sheet_small;
			return R.drawable.tertiary_sheet;
		}
		
		if (small) return R.drawable.egg_sheet_small;
		return R.drawable.egg_sheet;
	}
	
	public void UpdateEgg()
	{
		long timeEggHatched = TimeEggHatched - TimeEggFound;
		long currentTime = System.currentTimeMillis() - TimeEggFound;

		if (currentTime >= timeEggHatched)
		{
			PetDescription = "The egg hatched!!";
			CurrentForm = PetForm.Primary;
			Level = 5;
			ExpToNextLevel = (int)Math.pow(Level+1, 3);
			JustEvolved = true;
		}
		else if (currentTime >= (timeEggHatched * 2) / 3)
		{
			PetDescription = "It moves around a lot. \nIt must be close to hatching!";
			FrameDelay = 10;
		}
		else if (currentTime >= timeEggHatched / 3)
		{
			PetDescription = "It wiggles around now and then.";
			FrameDelay = 50;
		}
	}
	
	public void UpdateAnimation()
	{				
		UpdateFrameDelay();
		FrameCount++;
		if (FrameCount >= FrameDelay)
		{
			FrameCount = 0;
			CurrFrame++;
			if (CurrFrame >= MaxFrame)
			{
				CurrFrame = 0;
			}
		}
	}
	
	private void UpdateFrameDelay()
	{
		if (CurrentForm != PetForm.Egg)
		{
			if (Hunger >= 60){
				FrameDelay = 10;
				PetDescription = Name + " is thrashing about!";
			}else if (Hunger >= 25){
				FrameDelay = 20;
				PetDescription = Name + " is pretty hungry...";
			}else{
				FrameDelay = 40;
				PetDescription = Name + " is starving!!!";
			}
		}
	}
	
	public String GetGender()
	{
		if (Gender == PetGender.Female)
			return "♀";
		else return "♂";
	}
	
	public String GetSpeciesAndGender()
	{
		String speciesAndGender = Species;
		if (Gender == PetGender.Female)
			speciesAndGender += "♀";
		else speciesAndGender += "♂";
		return speciesAndGender;
	}
	
	//Getters for Personality Attributes
	public String GetAmbition()
    {
		String result = "• Ambition:\t ";
		result += ambition;
        return result;
    }
	
	public int GetIntAmbition(){ return ambition; }

    public String GetEmpathy()
    {
    	String result = "• Empathy:\t ";
		result += empathy;
        return result;
    }
    
    public int GetIntEmpathy(){ return empathy; }

    public String GetInsight()
    {
    	String result = "• Insight:\t ";
		result += insight;
        return result;
    }
    
    public int GetIntInsight(){ return insight; }

    public String GetDiligence()
    {
    	String result = "• Diligence:\t ";
		result += diligence;
        return result;
    }
    
    public int GetIntDiligence(){ return diligence; }

    public String GetCharm()
    {
    	String result = "• Charm:\t ";
		result += charm;
        return result;
    }
    
    public int GetIntCharm(){ return charm; }
    
    //Modifiers for Personality Attributes
    public int ModifyAmbition(int x)
    {
    	if (x > 0 && ambition >= 255) return 0;
    	if (x < 0 && ambition <= 0) return 0;
    	if (x == 0) return 0;
    	
    	ambition += x;
    	if (ambition > 255)
    		ambition = 255;
    	else if (ambition < 0)
    		ambition = 0;
    	return Math.abs(x)/x;
    }
    
    public int ModifyEmpathy(int x)
    {
    	if (x > 0 && empathy >= 255) return 0;
    	if (x < 0 && empathy <= 0) return 0;
    	if (x == 0) return 0;
    	
    	empathy += x;
    	if (empathy > 255)
    		empathy = 255;
    	else if (empathy < 0)
    		empathy = 0;
    	return Math.abs(x)/x;
    }
    
    public int ModifyInsight(int x)
    {
    	if (x > 0 && insight >= 255) return 0;
    	if (x < 0 && insight <= 0) return 0;
    	if (x == 0) return 0;
    	
    	insight += x;
    	if (insight > 255)
    		insight = 255;
    	else if (insight < 0)
    		insight = 0;
    	return Math.abs(x)/x;
    }
    
    public int ModifyDiligence(int x)
    {
    	if (x > 0 && diligence >= 255) return 0;
    	if (x < 0 && diligence <= 0) return 0;
    	if (x == 0) return 0;
    	
    	diligence += x;
    	if (diligence > 255)
    		diligence = 255;
    	else if (diligence < 0)
    		diligence = 0;
    	return Math.abs(x)/x;
    }
    
    public int ModifyCharm(int x)
    {
    	if (x > 0 && charm >= 255) return 0;
    	if (x < 0 && charm <= 0) return 0;
    	if (x == 0) return 0;
    	
    	charm += x;
    	if (charm > 255)
    		charm = 255;
    	else if (charm < 0)
    		charm = 0;
    	return Math.abs(x)/x;
    }
    
    public int GetBackgroundColor(BattleType type){
		if (type == BattleType.Earth){
			return Color.rgb(202, 138, 99);
		}else if (type == BattleType.Plant){
			return Color.rgb(96, 219, 36);
		}else if (type == BattleType.Poison){
			return Color.rgb(165, 112, 182);
		}else if (type == BattleType.Insect){
			return Color.rgb(209, 156, 0);
		}else if (type == BattleType.Water){
			return Color.rgb(106, 158, 246);
		}else if (type == BattleType.Light){
			return Color.rgb(255, 255, 147);
		}else if (type == BattleType.Dark){
			return Color.rgb(111, 84, 84);
		}else if (type == BattleType.Air){
			return Color.rgb(203, 178, 226);
		}else if (type == BattleType.Wild){
			return Color.rgb(244, 214, 195);
		}else if (type == BattleType.Glitch){
			return Color.rgb(184, 184, 184);
		}else if (type == BattleType.Fire){
			return Color.rgb(253, 134, 48);
		}else if (type == BattleType.Ice){
			return Color.rgb(143, 254, 255);
		}
		return 0;
	}
}
