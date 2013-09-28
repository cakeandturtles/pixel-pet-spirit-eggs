package com.cakeandturtles.pixelpets.pets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.graphics.Color;

import com.cakeandturtles.pixelpets.PPApp;
import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.attacks.LevelAttack;
import com.cakeandturtles.pixelpets.attacks.Struggle;
import com.cakeandturtles.pixelpets.attacks.statuses.Berserk;
import com.cakeandturtles.pixelpets.attacks.wild.Headbutt;
import com.cakeandturtles.pixelpets.items.PetItem;
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
	
	//LEVEL UP attack List
	public List<LevelAttack> LevelAttackList;
	
	//Battle Attributes	
	public Attack[] Attacks;
	public enum BattleType { Wild, Plant, Water, Fire, Poison, Light, Dark, Air, Earth, Insect, Ice, Glitch, None }
	public BattleType TruePrimaryType;
	public BattleType PrimaryType;
	public BattleType TrueSecondaryType;
	public BattleType SecondaryType;
	public BattleEffect PhysicalStatus;
	public BattleEffect MentalStatus;
	public int BaseHP;
	public int BaseBaseHP;
	public int HP;
	public int BaseSpeed;
	public int BaseBaseSpeed;
	public int SpeedModifier;
	public int BaseAttack;
	public int BaseBaseAttack;
	public int AttackModifier;
	public int BaseDefense;
	public int BaseBaseDefense;
	public int DefenseModifier;
	
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
		
		InitializeLevelUpAttackList();
		Attacks = new Attack[]{ new Headbutt(), null, null, null };
		TruePrimaryType = primaryType;
		PrimaryType = primaryType;
		TrueSecondaryType = secondaryType;
		SecondaryType = secondaryType;
		MentalStatus = null;
		PhysicalStatus = null;
		BaseHP = 10;
		BaseBaseHP = BaseHP;
		HP = BaseHP;
		BaseSpeed = 3;
		BaseBaseSpeed = 85;
		BaseAttack = 3;
		BaseBaseAttack = 85;
		BaseDefense = 3;
		BaseBaseDefense = 85;
		
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
	
	protected void SetBattleAttributes(int hp, int speed, int attack, int defense)
	{
		BaseBaseHP = hp;
		BaseBaseSpeed = speed;
		BaseBaseAttack = attack;
		BaseBaseDefense = defense;
		UpdateBaseStats();
		ResetBattleStats(true, true, true, true, true);
	}
	
	protected void UpdateBaseStats()
	{
		BaseHP = (int)(Math.round((float)((float)insight/4.0 + BaseBaseHP + 50.0) * Level)/45.0) + 10;
		BaseSpeed = (int)(Math.round((float)((float)ambition/4.0 + BaseBaseSpeed) * Level)/45.0) + 5;
		BaseAttack = (int)(Math.round((float)((float)diligence/4.0 + BaseBaseAttack) * Level)/45.0) + 5;
		BaseDefense = (int)(Math.round((float)((float)empathy/4.0 + BaseBaseDefense) * Level)/45.0) + 5;
	}
	
	public void ResetBattleStats(boolean resetHP, boolean resetPP, boolean resetTypes, boolean resetMentalStatus, boolean resetPhysicalStatus){
		if (resetHP)
			HP = BaseHP;
		SpeedModifier = 0;
		AttackModifier = 0;
		DefenseModifier = 0;
		
		if (resetMentalStatus){
			MentalStatus = null;
		}if (resetPhysicalStatus){
			PhysicalStatus = null;
		}
		if (resetPP){
			for (int i = 0; i < Attacks.length; i++){
				if (Attacks[i] != null)
					Attacks[i].NumUses = Attacks[i].BaseNumUses;
			}
		}
		if (resetTypes){
			PrimaryType = TruePrimaryType;
			SecondaryType = TrueSecondaryType;
		}
	}
	
	public boolean IsEnergyFullyRestored()
	{
		if (HP < BaseHP)
			return false;
		for (int i = 0; i < 4; i++){
			if (Attacks[i] != null){
				if (Attacks[i].NumUses < Attacks[i].BaseNumUses){
					return false;
				}
			}
		}
		return true;
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
	
	public void RestoreHealthAndPP(){
		long currentTime = System.currentTimeMillis();
		long currentDiff = currentTime - EnergyRestoreTimer;
		long timeLimit = 20000L;
		if (Hunger < 60) timeLimit *= 1.5;
		if (Hunger < 50) timeLimit *= 2.0;
		if (Hunger < 25) timeLimit *= 4.0;
		
		int multiplier = Math.round(currentDiff / timeLimit);
		
		if (!IsEnergyFullyRestored()){
			if (multiplier >= 1){
				Hunger -= multiplier;
				int addend = Math.round((float)BaseHP/8) * multiplier;
				if (addend < 1) addend = 1;
				HP += addend;
				if (HP > BaseHP){
					HP = BaseHP;
				}
				
				for (int i = 0; i < 4; i++){
					if (Attacks[i] != null){
						addend = Math.round((float)Attacks[i].BaseNumUses/8) * multiplier;
						if (addend < 1) addend = 1;
						Attacks[i].NumUses += addend;
						if (Attacks[i].NumUses > Attacks[i].BaseNumUses)
							Attacks[i].NumUses = Attacks[i].BaseNumUses;
					}
				}
				
				EnergyRestoreTimer = System.currentTimeMillis();
			}
		}
	}
	
	public int BattleSpeed(){
		return (int)Math.round((float)BaseSpeed * (Math.pow(Math.cbrt(4), SpeedModifier)));
	}
	
	public int BattleAttack(){
		return (int)Math.round((float)BaseAttack * (Math.pow(Math.cbrt(4), AttackModifier)));
	}

	public int BattleDefense(){
		return (int)Math.round((float)BaseDefense * (Math.pow(Math.cbrt(4), DefenseModifier)));
	}
	
	public String BuffSpeed(int modifier){
		if (modifier < 0) return DebuffSpeed(modifier*-1);
		
		int originalStat = SpeedModifier;
		SpeedModifier += modifier;
		if (SpeedModifier > 3) SpeedModifier = 3;
		int newStat = SpeedModifier;
		switch (newStat - originalStat){
			case 0:
				return Name + "'s speed can't go any higher.";
			case 1:
				return Name + "'s speed is raised.";
			case 2:
				return Name + "'s speed is greatly raised.";
			case 3: case 4: case 5:
				return Name + "'s speed is incredibly raised.";
			case 6:
				return Name + "'s speed is MAXIMUM.";
			default: return "";
		}
	}
	
	public String DebuffSpeed(int modifier){
		if (modifier < 0) return BuffSpeed(modifier*-1);
		
		int originalStat = SpeedModifier;
		SpeedModifier -= modifier;
		if (SpeedModifier < -3) SpeedModifier = -3;
		int newStat = SpeedModifier;
		switch (originalStat - newStat){
			case 0:
				return Name + "'s speed can't go any lower.";
			case 1:
				return Name + "'s speed is lowered.";
			case 2:
				return Name + "'s speed is greatly lowered.";
			case 3: case 4: case 5:
				return Name + "'s speed is incredibly lowered.";
			case 6:
				return Name + "'s speed is MINIMUM.";
			default: return "";
		}
	}
	
	public String BuffAttack(int modifier){
		if (modifier < 0) return DebuffAttack(modifier*-1);
		
		int originalStat = AttackModifier;
		AttackModifier += modifier;
		if (AttackModifier > 3) AttackModifier = 3;
		int newStat = AttackModifier;
		switch (newStat - originalStat){
			case 0:
				return Name + "'s attack can't go any higher.";
			case 1:
				return Name + "'s attack is raised.";
			case 2:
				return Name + "'s attack is greatly raised.";
			case 3: case 4: case 5:
				return Name + "'s attack is incredibly raised.";
			case 6:
				return Name + "'s attack is MAXIMUM.";
			default: return "";
		}
	}
	
	public String DebuffAttack(int modifier){
		if (modifier < 0) return BuffAttack(modifier*-1);
		
		int originalStat = AttackModifier;
		AttackModifier -= modifier;
		if (AttackModifier < -3) AttackModifier = -3;
		int newStat = AttackModifier;
		switch (originalStat - newStat){
			case 0:
				return Name + "'s attack can't go any lower.";
			case 1:
				return Name + "'s attack is lowered.";
			case 2:
				return Name + "'s attack is greatly lowered.";
			case 3: case 4: case 5:
				return Name + "'s attack is incredibly lowered.";
			case 6:
				return Name + "'s attack is MINIMUM.";
			default: return "";
		}
	}
	
	public String BuffDefense(int modifier){
		if (modifier < 0) return DebuffDefense(modifier*-1);
		
		int originalStat = DefenseModifier;
		DefenseModifier += modifier;
		if (DefenseModifier > 3) DefenseModifier = 3;
		int newStat = DefenseModifier;
		switch (newStat - originalStat){
			case 0:
				return Name + "'s defense can't go any higher.";
			case 1:
				return Name + "'s defense is raised.";
			case 2:
				return Name + "'s defense is greatly raised.";
			case 3: case 4: case 5:
				return Name + "'s defense is incredibly raised.";
			case 6:
				return Name + "'s defense is MAXIMUM.";
			default: return "";
		}
	}
	
	public String DebuffDefense(int modifier){
		if (modifier < 0) return BuffDefense(modifier*-1);
		
		int originalStat = DefenseModifier;
		DefenseModifier -= modifier;
		if (DefenseModifier < -3) DefenseModifier = -3;
		int newStat = DefenseModifier;
		switch (originalStat - newStat){
			case 0:
				return Name + "'s defense can't go any lower.";
			case 1:
				return Name + "'s defense is lowered.";
			case 2:
				return Name + "'s defense is greatly lowered.";
			case 3: case 4: case 5:
				return Name + "'s defense is incredibly lowered.";
			case 6:
				return Name + "'s defense is MINIMUM.";
			default: return "";
		}
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
		RestoreHealthAndPP();
		UpdateAnimation();
	}
	
	public int GetScaledExp(float divisor){
		int result = Math.round((float)(ExpToNextLevel - (int)Math.pow(Level, 3))/divisor);
		if (result < 5) result = 5; //FORCE A MINIMUM of 5 EXP!!!
		return result;
	}
	
	public List<PetItem> GetItemDrops()
	{
		return new ArrayList<PetItem>();
	}
	
	public void LevelUp()
	{
		if (Level < MaxLevel){
			Level++;
			if (Exp < ExpToNextLevel) Exp = ExpToNextLevel;
			ExpToNextLevel = (int)Math.pow(Level+1, 3);
		}else Exp = 0;
		
		UpdateBaseStats();
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
	
	public void InitializeLevelUpAttackList()
	{
		LevelAttackList = new ArrayList<LevelAttack>();
	}
	
	public void LearnLevelUpAttacks()
	{
		int counter = 0;
		for (int i = LevelAttackList.size()-1; i >= 0; i--){
			if (LevelAttackList.get(i).Level > Level) continue;
			Attacks[counter] = LevelAttackList.get(i).Attack;
			counter++;
			if (counter == 4) return;
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
		
		BaseHP = baby.BaseHP;
		HP = BaseHP;
		BaseSpeed = baby.BaseSpeed;
		BaseAttack = baby.BaseAttack;
		BaseDefense = baby.BaseDefense;
		UpdateBaseStats();
		Attacks = baby.Attacks; //??? TODO:: UPDATE ATTACKS!!!
		
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
			UpdateBaseStats();
			ResetBattleStats(true, true, true, true, true);
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
				if (HP <= 0){
					CurrFrame = 0;
					FrameCount = 0;
					PetDescription = Name + " is resting.";
				}else if (HP <= BaseHP/2){
					FrameDelay = 20;
					PetDescription = Name + " is pretty tired...";
				}else{
					FrameDelay = 10;
					PetDescription = Name + " is thrashing about!";
				}
			}else if (Hunger >= 25){
				if (HP <= 0){
					CurrFrame = 0;
					FrameCount = 0;
					PetDescription = Name + " is hungry and resting.";
				}else if (HP <= BaseHP/2){
					FrameDelay = 20;
					PetDescription = Name + " is hungry and tired...";
				}else{
					FrameDelay = 10;
					PetDescription = Name + " is pretty hungry...";
				}
			}else{
				if (HP <= 0){
					CurrFrame = 0;
					FrameCount = 0;
					PetDescription = Name + " is starving and resting.";
				}else if (HP <= BaseHP/2){
					FrameDelay = 20;
					PetDescription = Name + " is starving and tired...";
				}else{
					FrameDelay = 10;
					PetDescription = Name + " is starving!!!";
				}
			}
		}
	}
	
	public Attack PickNextAttack()
	{
		int i;
		
		if (Attacks[3] != null)
			i = PPApp.AppRandom.nextInt(4);
		else if (Attacks[2] != null)
			i = PPApp.AppRandom.nextInt(3);
		else
			i = PPApp.AppRandom.nextInt(2);
		
		int counter = 0;
		while (Attacks[i] == null || Attacks[i].NumUses <= 0 || 
				(MentalStatus != null && MentalStatus.getClass().equals(Berserk.class) && Attacks[i].BasePower <= 0)){
			i++;
			if (i >= Attacks.length)
				i = 0;
			counter++;
			if (counter > 3) break;
		}
		
		if (Attacks[i] != null && Attacks[i].NumUses > 0)
			return Attacks[i];
		return new Struggle();
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
