package com.cakeandturtles.pixelpets;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.attacks.AttackResult;
import com.cakeandturtles.pixelpets.attacks.BattleEffect;
import com.cakeandturtles.pixelpets.attacks.DirectedAttack;
import com.cakeandturtles.pixelpets.attacks.FieldEffect;
import com.cakeandturtles.pixelpets.attacks.Struggle;
import com.cakeandturtles.pixelpets.attacks.air.MimicSong;
import com.cakeandturtles.pixelpets.attacks.statuses.Afraid;
import com.cakeandturtles.pixelpets.attacks.statuses.Asleep;
import com.cakeandturtles.pixelpets.attacks.statuses.Berserk;
import com.cakeandturtles.pixelpets.attacks.statuses.Confused;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.battle.Substitute;
import com.cakeandturtles.pixelpets.pets.Chloropillar;
import com.cakeandturtles.pixelpets.pets.Marmoss;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;
import com.cakeandturtles.pixelpets.pets.Puglett;
import com.cakeandturtles.pixelpets.pets.Tadpox;
import com.example.pixelpets.R;

public class TrainingActivity extends Activity {
	private PPApp appState;
	private enum BattleStateType { BEGIN, FIGHT, SWITCH, USE_ITEM, BEGIN_ANIMATE_HP, ANIMATE_HP, TAP_TO_CONTINUE, END }
	private BattleStateType battleState;
	private FieldEffect[] fieldEffects;
	private int areaIndex;
	private int battleIndex;
	private int originalIndex;
	private LinkedList<DirectedAttack> attackQueue;
	private int[] HPKeeper;
	private boolean[] WhosAttackedKeeper;
	private Attack[] lastAttackKeeper;
	private PixelPet _activePet;
	private PixelPet _foe;
	private int numEscapeAttempts;
	private List<PetItem> spoils;
	private boolean[] participators;
	private PixelPet[] _enemyTeam;
	private TextView _petName;
	private TextView _petLevel;
	private ImageView _petView;
	private TextView _foeName;
	private TextView _foeLevel;
	private ImageView _foeView;
	private TextView _battleDescription;
	private Button _option1;
	private Button _option2;
	private Button _option3;
	private Button _option4;
	private Handler handler;
	private Runnable runnable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training);
		
		appState = ((PPApp)getApplicationContext());
		for (int i = 0; i < 4; i++){
			if (appState.getActivePets()[i] != null)
				appState.getActivePets()[i].ResetBattleStats(false, false, true, true, false);
		}
		_activePet = appState.getTempActivePet();
		battleState = BattleStateType.BEGIN;
		areaIndex = -1;
		battleIndex = appState.tempIndex;
		originalIndex = appState.tempIndex;
		lastAttackKeeper = new Attack[]{ null, null };
		attackQueue = new LinkedList<DirectedAttack>();
		fieldEffects = new FieldEffect[]{ null, null };
		numEscapeAttempts = 0;
		_foe = GetNewOpponent();
		spoils = new ArrayList<PetItem>();
		participators = new boolean[]{ false, false, false, false };
		participators[battleIndex] = true;
		
		InitiallyUpdateEnemyPets();
		InitializeLongClick();
		
		InitializeViews();
		
		String battleIntro = "•A wild " + _foe.Species + " appears!\n•Go! "+_activePet.Name+"!";
		_battleDescription.setText(battleIntro);
		HandleAndDrawHP();
		HandleAndDrawPet();
	}
	
	private void AddToBeginningOfQueue(DirectedAttack attack)
	{
		attackQueue.addFirst(attack);
	}
	
	private void AdventureAgain(){
		finish();
		Intent intent = new Intent(this, AdventureActivity.class);
		intent.putExtra("com.cakeandturtles.pixelpets.areaIndex", areaIndex);
		startActivity(intent);
	}
	
	private PixelPet GetNewOpponent(){
		Bundle extras = getIntent().getExtras();
		if (extras != null){
			areaIndex = extras.getInt("com.cakeandturtles.pixelpets.areaIndex");
			String[] battlePetSpecies = extras.getStringArray("com.cakeandturtles.pixelpets.battlePets");
			_enemyTeam = new PixelPet[battlePetSpecies.length];
			for (int i = 0; i < battlePetSpecies.length; i++){
				_enemyTeam[i] = appState.MyCodex.GetNewPetBySpecies(battlePetSpecies[i]);
			}
			return _enemyTeam[0];
		}else{
			_enemyTeam = new PixelPet[]{
					new Puglett("", 0L),
					new Marmoss("", 0L),
					new Tadpox("", 0L),
					new Chloropillar("", 0L)
			};
			return _enemyTeam[0];
		}
	}
	
	private void InitiallyUpdateEnemyPets(){
		if (_enemyTeam == null) _enemyTeam = new PixelPet[]{ _foe };
		
		for (int i = 0; i < _enemyTeam.length; i++){
			_enemyTeam[i].CurrentForm = PixelPet.PetForm.Primary;
			//_enemyTeam[i].RandomizePersonalityAttributes();
			
			int level = (int)(appState.MyAdventures.ConsecutiveAdventureCounter/5) + (PPApp.AppRandom.nextInt(5)-2);
			if (level <= 0) level = 1;
			for (int j = 0; j < level; j++){
				_enemyTeam[i].LevelUp();
			}
			
			_enemyTeam[i].UpdatePetForm();
			if (_enemyTeam[i].JustEvolved){
				PixelPet oldPet = _enemyTeam[i];
				_enemyTeam[i] = _enemyTeam[i].Evolve();
				_enemyTeam[i].EvolveFrom(oldPet);
			}
			_enemyTeam[i].UpdatePetForm();
			if (_enemyTeam[i].JustEvolved){
				PixelPet oldPet = _enemyTeam[i];
				_enemyTeam[i] = _enemyTeam[i].Evolve();
				_enemyTeam[i].EvolveFrom(oldPet);
			}
			_enemyTeam[i].Name = "Enemy " + _enemyTeam[i].Name;
			_enemyTeam[i].ResetBattleStats(true, true, true, true, true);
			_enemyTeam[i].LearnLevelUpAttacks();
		}
		_foe = _enemyTeam[0];
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.training, menu);
		return true;
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		appState.MyAdventures.InBattle = true;
		if (_petName == null)
			InitializeViews();
		
		ResetAnimations();
		runnable = new Runnable(){
			@Override
			public void run(){
				UpdateHPAnimateUpdateAttacks();
				HandleAndDrawPet();
				_activePet.UpdateAnimation();
				_foe.UpdateAnimation();
				
				handler.postDelayed(this, 60);
			}
		};
		handler = new Handler();
		handler.postDelayed(runnable, 0);
		appState.StopRunAndHandle();
		
		if (battleState == BattleStateType.USE_ITEM){
			HPKeeper = new int[]{ _activePet.HP, _foe.HP };
			appState.tempIndex = battleIndex;
			_activePet = appState.getTempActivePet();
			if (appState.BattleUsedItem != null && appState.BattleUsedItemOnIndex >= 0){
				if (appState.BattleUsedItem.Effect.NeedsATarget)
					RewriteBattleText("•"+appState.Trainer.TrainerName + " used " + appState.BattleUsedItem.Name + " on " + appState.getActivePets()[appState.BattleUsedItemOnIndex].Name + ".");
				else
					RewriteBattleText("•"+appState.Trainer.TrainerName + " used " + appState.BattleUsedItem.Name + ".");
				if (appState.BattleUsedItemOnIndex == appState.BattleIndex || !appState.BattleUsedItem.Effect.NeedsATarget)
					UseItem(appState.BattleUsedItem);
				appState.BattleUsedItem = null;
				appState.BattleUsedItemOnIndex = -1;
				
				if (battleState != BattleStateType.END){
					WhosAttackedKeeper = new boolean[]{ true, false }; //_activePet, _foe
					QueueActiveEffects(_activePet, _foe);
					AddToAttackQueue(_activePet, _foe, _foe.PickNextAttack());
					QueueActiveEffects(_foe, _activePet);
					QueueActiveEffects(null, null);
					battleState = BattleStateType.ANIMATE_HP;
					InitiateTapToContinue();
				}
			}
			else BackToBasics();
		}else{
			if (appState.tempIndex != battleIndex){
				PetSwitchedIn();
			}
		}
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		NullifyViews();
		
		handler.removeCallbacks(runnable);
		handler = null;
		runnable = null;
		
		for (int i = 0; i < 4; i++){
			if (appState.getActivePets()[i] != null)
				appState.getActivePets()[i].RestartEnergyRestoreTimer();
		}
		appState.SaveUser();
	}
	
	@Override
	public void onBackPressed(){
		if (battleState == BattleStateType.BEGIN){
			BackToGarden(null);
		}else if (battleState == BattleStateType.FIGHT){
			BackToBasics();
		}else if (battleState == BattleStateType.SWITCH){
			BackToBasics();
		}else if (battleState == BattleStateType.END){
			super.onBackPressed();
		}else if (battleState == BattleStateType.TAP_TO_CONTINUE){
			TapToContinue();
		}
	}
	
	private void InitializeViews()
	{
		_petName = (TextView)findViewById(R.id.pet_name);
		_petLevel = (TextView)findViewById(R.id.pet_level);
		_petView = (ImageView)findViewById(R.id.pet_area);
		_foeName = (TextView)findViewById(R.id.foe_name);
		_foeLevel = (TextView)findViewById(R.id.foe_level);
		_foeView = (ImageView)findViewById(R.id.foe_area);
		_battleDescription = (TextView)findViewById(R.id.battle_description);
		_option1 = (Button)findViewById(R.id.battle_button1);
		_option2 = (Button)findViewById(R.id.battle_button2);
		_option3 = (Button)findViewById(R.id.battle_button3);
		_option4 = (Button)findViewById(R.id.battle_button4);
	}
	
	private void NullifyViews()
	{
		_petName = null;
		_petLevel = null;
		_petView = null;
		_foeName = null;
		_foeLevel = null;
		_foeView = null;
		_battleDescription = null;
		_option1 = null;
		_option2 = null;
		_option3 = null;
		_option4 = null;
	}
	
	public void InitializeLongClick(){
		((ImageView)findViewById(R.id.pet_area)).setLongClickable(true);
		((ImageView)findViewById(R.id.pet_area)).setOnLongClickListener(new OnLongClickListener(){
			@Override
			public boolean onLongClick(View v) {
				ViewStats(v);
				return true;
			}
		});
	}
	
	public void ViewStats(View view)
	{
		Intent intent = new Intent(this, ViewStatsActivity.class);
		intent.putExtra("com.cakeandturtles.pixelpets.fromTraining", true);
		startActivity(intent);
	}
	
	private void RewriteBattleText(String str){
		_battleDescription.setText(str);
	}
	
	private void AppendToBattleText(String str){
		String text = (String) _battleDescription.getText();
		if (text == null || text.length() <= 0 || text.isEmpty())
			_battleDescription.setText(str);
		else _battleDescription.setText(_battleDescription.getText() + "\n" + str);
	}
	
	private void UpdateHPAnimateUpdateAttacks()
	{
		if (battleState == BattleStateType.BEGIN_ANIMATE_HP){
			DirectedAttack attack = attackQueue.remove();
			if (!attack.IsRecoil && (_activePet.HP <= 0 || _foe.HP <= 0)){
				attackQueue = new LinkedList<DirectedAttack>();
				BackToBasics();
			}
			else{
				HPKeeper = new int[]{ _activePet.HP, _foe.HP };
				
				if (attack.User != null){					
					if (attack.HasAttack()){
						if (!attack.IsRecoil && !attack.IsFieldEffect && attack.User == _activePet){
							lastAttackKeeper[0] = attack.Attack;
							WhosAttackedKeeper[0] = true;
						}
						if (!attack.IsRecoil && !attack.IsFieldEffect && attack.User == _foe){
							lastAttackKeeper[1] = attack.Attack;
							WhosAttackedKeeper[1] = true;
						}
						
						String str = DoAttack(attack);
						if (str != null && !str.trim().isEmpty()){
							AppendToBattleText(str.trim());
							InitiateTapToContinue();
						}
					}
					if (attack.Effect != null){
						String str = DoEffect(attack);
						if (str != null && !str.trim().isEmpty()){
							AppendToBattleText(str.trim());
							InitiateTapToContinue();
						}
					}
				}
				battleState = BattleStateType.ANIMATE_HP;
			}
		}else if (battleState == BattleStateType.ANIMATE_HP){
			if (HPKeeper[0] > _activePet.HP) HPKeeper[0]--; //ACTIVE PET HP KEEPER
			else if (HPKeeper[0] < _activePet.HP) HPKeeper[0]++;
			if (HPKeeper[1] > _foe.HP) HPKeeper[1]--; //FOE HP KEEPER
			if (HPKeeper[1] < _foe.HP) HPKeeper[1]++; 
			
			View petHP = (View)findViewById(R.id.pet_hp_bar);
			TextView petHPText = (TextView)findViewById(R.id.pet_hp_text);
			View foeHP = (View)findViewById(R.id.foe_hp_bar);
			TextView foeHPText = (TextView)findViewById(R.id.foe_hp_text);
			
			RelativeLayout.LayoutParams petHPParams = (RelativeLayout.LayoutParams)petHP.getLayoutParams();
			RelativeLayout.LayoutParams foeHPParams = (RelativeLayout.LayoutParams)foeHP.getLayoutParams();
			Resources r = getResources();
			int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
			petHPParams.width = (int)(size * ((float)HPKeeper[0] / (float)_activePet.BaseHP));
			foeHPParams.width = (int)(size * ((float)HPKeeper[1] / (float)_foe.BaseHP));
			
			petHP.setLayoutParams(petHPParams);
			foeHP.setLayoutParams(foeHPParams);
			
			petHPText.setText(HPKeeper[0] + "/" + _activePet.BaseHP);
			float ratio = ((float)HPKeeper[1]) / ((float)_foe.BaseHP);
			foeHPText.setText(Math.round(100 * ratio) + "%");
			
			if (HPKeeper[0] == _activePet.HP && HPKeeper[1] == _foe.HP){
				battleState = BattleStateType.TAP_TO_CONTINUE;
				if (!attackQueue.isEmpty() && (attackQueue.peek().IsRecoil || (!attackQueue.peek().HasAttack() && !attackQueue.peek().IsFieldEffect)))
					battleState = BattleStateType.BEGIN_ANIMATE_HP;
			}
		}
	}
	
	private void InitiateTapToContinue(){
		_option1.getBackground().clearColorFilter();
		_option2.setVisibility(View.GONE);
		_option2.setClickable(false);
		_option3.setVisibility(View.GONE);
		_option3.setClickable(false);
		_option4.setVisibility(View.GONE);
		_option4.setClickable(false);
		
		_option1.setText("Tap To Continue");
		LinearLayout.LayoutParams layout = (LinearLayout.LayoutParams)_option1.getLayoutParams();
		layout.weight = 1.0f;
		_option1.setLayoutParams(layout);
	}
	
	private void TapToContinue(){
		if (battleState == BattleStateType.TAP_TO_CONTINUE){
			if (WhosAttackedKeeper[0] && WhosAttackedKeeper[1]){
				QueueActiveEffects(null, null);
				WhosAttackedKeeper = new boolean[]{ false, false };
			}
			
			if (attackQueue.size() > 0){
				RewriteBattleText("");
				battleState = BattleStateType.BEGIN_ANIMATE_HP;
			}
			else BackToBasics();
		}
	}
	
	private void ResetAnimations(){
		_activePet.FrameCount = 0;
		_activePet.CurrFrame = 0;
		_foe.FrameCount = 0;
		_foe.CurrFrame = 0;
	}
	
	public void CheckEndConditions()
	{		
		if (_activePet.HP <= 0)
		{
			boolean battleEnd = true;
			for (int i = 0; i < 4; i++){
				if (appState.getActivePets()[i] != null && appState.getActivePets()[i].CurrentForm != PixelPet.PetForm.Egg && appState.getActivePets()[i].HP > 0)
				{
					battleEnd = false;
					break;
				}
			}
			if (!battleEnd){
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Out of Energy");
				alert.setMessage(_activePet.Name + " is out of energy.\nDo you want to continue?");
				alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						dialog.cancel();
						battleState = BattleStateType.SWITCH;
						Intent intent = new Intent(TrainingActivity.this, PickBattlePetActivity.class);
						startActivity(intent);
					}
				});
				alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						RunAwayFromBattle(true, false);
					}
				});
				alert.setCancelable(false);
				alert.show();
			}
			else
			{
				AppendToBattleText("•" + _activePet.Name + " is out of energy.");
				if (_foe.HP <= 0)
				{
					AppendToBattleText("•" + _foe.Name + " is out of energy.");
					AppendToBattleText("•The match is a draw.");
					
					AlertDialog.Builder alert = new AlertDialog.Builder(this);
					alert.setTitle("Draw.");
					alert.setMessage("Both pets are out of energy.\nThe battle is over.");
					alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int which){
							dialog.cancel();
						}
					});
					alert.show();
					EndBattle(false);
				}else{
					AppendToBattleText("•You lose the battle..");
					AlertDialog.Builder alert = new AlertDialog.Builder(this);
					alert.setTitle("Lose..");
					alert.setMessage("You have been defeated.\nThe battle is over.");
					alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
						public void onClick(DialogInterface dialog, int which){
							dialog.cancel();
						}
					});
					alert.show();
					EndBattle(false);
				}
			}
		}else if (_foe.HP <= 0){
			PixelPet oldFoe = _foe;
			boolean battleEnd = true;
			for (int i = 0; i < _enemyTeam.length; i++){
				if (_enemyTeam[i] != null && _enemyTeam[i].CurrentForm != PixelPet.PetForm.Egg && _enemyTeam[i].HP > 0)
				{
					battleEnd = false;
					_foe.ResetBattleStats(false, false, true, true, false);
					_foe = _enemyTeam[i]; //TODO:: Randomize next enemy???
					FoeSwitchedIn();
					break;
				}
			}
			if (!battleEnd){
				RewriteBattleText("•" + oldFoe.Name + " is out of energy.");
				NewFoeDialog();
				DefeatFoe(oldFoe);
				AppendToBattleText("•" + _foe.Name + " is switched into battle.");
			}else{
				AppendToBattleText("•" + _foe.Name + " is out of energy.");
				AppendToBattleText("•You win the battle!");
				
				DefeatFoe(oldFoe);
				
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Victory!!");
				alert.setMessage("You have won the battle!\nThe battle is over.");
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						dialog.cancel();
					}
				});
				alert.show();
				EndBattle(true);
			}
		}
	}
	
	public void NewFoeDialog(){
		String message = _foe.Name + " draws near!\nWhat is your command?";
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Another Foe!");
		final View fightView = LayoutInflater.from(this).inflate(R.layout.item_dialog, null);
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
		((ImageView)fightView.findViewById(R.id.item_image_area)).scrollTo((_foe.RelAniX)*size, _foe.RelAniY * size);
		((ImageView)fightView.findViewById(R.id.item_image_area)).setImageResource(_foe.GetDrawableId(false));
		
		((TextView)fightView.findViewById(R.id.item_text_area)).setText(message);
		
		builder.setPositiveButton("FIGHT!!!", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		builder.setView(fightView);
		builder.setCancelable(false);
		builder.show();
	}
	
	public void DefeatFoe(PixelPet oldFoe){		
		int size = 0;
		for (int i = 0; i < 4; i++){
			if (!participators[i]) continue;
			else if (appState.getActivePets()[i] == null || appState.getActivePets()[i].HP <= 0)
				continue;
			size++;
		}
		int exp = oldFoe.GetExpYield(appState.Trainer.TrainerID, size);
		AddToSpoils(oldFoe.GetItemDrops());
		
		for (int i = 0; i < 4; i++){
			if (!participators[i]) continue;
			PixelPet pet = appState.getActivePets()[i];
			if (pet == null) continue;
			
			if (pet.Level < pet.MaxLevel && pet.HP > 0){
				int petExp = pet.CalculateRealExp(exp);
				
				AppendToBattleText("•" + pet.Name + " GAINS " + petExp + " EXP!");
				pet.Exp += petExp;
				
				boolean battleEnd = true;
				for (int j = 0; j < _enemyTeam.length; j++){
					if (_enemyTeam[j] != null && _enemyTeam[j].CurrentForm != PixelPet.PetForm.Egg && _enemyTeam[j].HP > 0){
						battleEnd = false;
						break;
					}
				}
				if (!battleEnd){
					AlertDialog.Builder levelUp = null;
					if (pet.Exp >= pet.ExpToNextLevel && pet.HP > 0)
						levelUp = appState.LevelUp(pet, i, this);
					if (levelUp != null) levelUp.show();
				}
			}
			HandleAndDrawHP();
		}
		
		//RESET PARTICIPATORS
		participators = new boolean[] { false, false, false, false };
		participators[battleIndex] = true;
	}
	
	private void AddToSpoils(List<PetItem> drops){
		for (int i = 0; i < drops.size(); i++){
			PetItem item = drops.get(i);
			if (item.Quantity > 0)
				spoils.add(item);
		}
	}
	
	private void TryToRunAwayFromBattle(Attack foeAttack)
	{
		float B = (_foe.BattleSpeed() / 4) % 256;
		float escapeChance = (_activePet.BattleSpeed() * 32) / B + 30 * numEscapeAttempts;
		numEscapeAttempts++;
		
		if (escapeChance > 255)
			RunAwayFromBattle(false, true);
		else{
			int rand = PPApp.AppRandom.nextInt(256);
			if (rand > escapeChance){
				String battleResult = "•" + _activePet.Name + " couldn't run away...";
				AddToAttackQueue(_activePet, _foe, _foe.PickNextAttack());
				battleState = BattleStateType.BEGIN_ANIMATE_HP;
				//battleResult += TryUpdateEffects();
				RewriteBattleText(battleResult);
			}else{
				RunAwayFromBattle(false, true);
			}
		}
	}
	
	private void RunAwayFromBattle(boolean defeated, boolean newLine)
	{
		for (int i = 0; i < 4; i++){
			if (appState.getActivePets()[i] != null)
				appState.getActivePets()[i].RestartEnergyRestoreTimer();
		}
		EndBattle(false);
		if (defeated){
			if (newLine)
				RewriteBattleText("•" + _activePet.Name + " is out of energy...");
			else AppendToBattleText("•" + _activePet.Name + " is out of energy...");
		}
		else{ 
			if (newLine)
				RewriteBattleText("•" + _activePet.Name + " successfully runs away!");
			else AppendToBattleText("•" + _activePet.Name + " runs away!");
		}
		AppendToBattleText("•You've lost the battle...");
	}

	public void EndBattle(boolean victory){
		if (victory){
			for (int i = 0; i < spoils.size(); i++){
				PetItem item = spoils.get(i);
				if (item.Quantity > 0){
					int quantity = appState.MyInventory.addToInventory(item, appState.MyCodex);
					if (quantity > 0)
						AppendToBattleText("•YOU GAIN " + quantity + " " + item.Name + "!");
					else
						AppendToBattleText("•You cannot hold any more " + item.Name + "!");
				}
			}
		}
		for (int i = 0; i < 4; i++){
			PixelPet pet = appState.getActivePets()[i];
			if (pet == null) break;
			pet.UpdatePetForm();
			AlertDialog.Builder levelUp = null;
			if (pet.Level < pet.MaxLevel && pet.Exp >= pet.ExpToNextLevel)
				levelUp = appState.LevelUp(pet, i, this);
			pet.UpdatePetForm();
			
			if (pet.CurrentForm != PixelPet.PetForm.Primary){
				if (pet.JustEvolved){
					appState.PetEvolved(i, this);
				
					if (!appState.notifications[i])
						appState.NotifyOfPetFormChange(pet, i);
					appState.ClearNotifications(false, pet);
				}
			}else{
				if (pet.JustEvolved && !appState.notifications[i])
					appState.NotifyOfPetFormChange(pet, i);
			}
			if (pet == _activePet)
				appState.ClearNotifications(false, pet);
			
			if (levelUp != null)
				levelUp.show();
				
		}
		_activePet = appState.getTempActivePet();
		battleState = BattleStateType.END;
		attackQueue = new LinkedList<DirectedAttack>();
		
		_option2.setVisibility(View.GONE);
		_option2.setOnClickListener(null);
		_option4.setVisibility(View.GONE);
		_option4.setOnClickListener(null);
		
		_option1.setText("Adventure");
		_option3.setText("Back");
		
		PixelPet temp = appState.getActivePets()[originalIndex];
		if (temp != null && temp.CurrentForm != PixelPet.PetForm.Egg && temp.HP > 0)
			appState.tempIndex = originalIndex;
		boolean allDead = true;
		for (int i = 0; i < 4; i++){
			temp = appState.getActivePets()[i];
			if (temp == null) continue;
			else if (temp.CurrentForm != PixelPet.PetForm.Egg && temp.HP > 0)
				allDead = false;
		}
		if (allDead){
			_option1.setVisibility(View.GONE);
			_option1.setClickable(false);
			appState.ResetConsecutiveAdventureCounter(true, this);
			
			LinearLayout.LayoutParams layout = (LinearLayout.LayoutParams)_option3.getLayoutParams();
			layout.weight = 1.0f;
			_option3.setLayoutParams(layout);
			_option3.setText("Back to Garden");
		}
		appState.MyAdventures.InBattle = false;
	}
	
	public void BackToGarden(View view)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Flee?");
		alert.setMessage("Do you really want to run away?");
		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
				TryToRunAwayFromBattle(_foe.PickNextAttack());
			}
		});
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alert.show();
	}
	
	public void BackToBasics(){
		battleState = BattleStateType.BEGIN;
		ResetButtons();
		ResetAnimations();
		CheckEndConditions();
	}
	
	public void ResetButtons()
	{
		_option1.setText(getResources().getString(R.string.battle_button1));
		_option2.setText(getResources().getString(R.string.battle_button2));
		_option3.setText(getResources().getString(R.string.battle_button3));
		_option4.setText(getResources().getString(R.string.battle_button4));
		
		_option1.setTextSize(18);
		_option2.setTextSize(18);
		_option3.setTextSize(18);
		_option4.setTextSize(18);
		
		_option1.setVisibility(View.VISIBLE);
		_option2.setVisibility(View.VISIBLE);
		_option3.setVisibility(View.VISIBLE);
		_option4.setVisibility(View.VISIBLE);
		
		_option1.getBackground().clearColorFilter();
		_option2.getBackground().clearColorFilter();
		_option3.getBackground().clearColorFilter();
		_option4.getBackground().clearColorFilter();
		
		_option1.setClickable(true);
		_option2.setClickable(true);
		_option3.setClickable(true);
		_option4.setClickable(true);
		
		LinearLayout.LayoutParams layout = (LinearLayout.LayoutParams)_option1.getLayoutParams();
		layout.weight = 0.5f;
		_option1.setLayoutParams(layout);
	}
	
	public void StartFight(){
		battleState = BattleStateType.FIGHT;
		Attack[] attacks = _activePet.Attacks;
		_option1.setText(Html.fromHtml(attacks[0].Name + "<br/><small>" + attacks[0].NumUses + "/" + attacks[0].BaseNumUses + "</small>"));
		_option1.setTextSize(16);
		_option1.getBackground().setColorFilter(new LightingColorFilter(0xFF000000, attacks[0].GetBackgroundColor()));
		
		if (attacks[1] != null){
			_option3.setText(Html.fromHtml(attacks[1].Name + "<br/><small>" + attacks[1].NumUses + "/" + attacks[1].BaseNumUses + "</small>"));
			_option3.setTextSize(16);
			_option3.getBackground().setColorFilter(new LightingColorFilter(0xFF000000, attacks[1].GetBackgroundColor()));
		}else{ 
			_option3.setText("------");
			_option3.setClickable(false);
		}
		if (attacks[2] != null){
			_option2.setText(Html.fromHtml(attacks[2].Name + "<br/><small>" + attacks[2].NumUses + "/" + attacks[2].BaseNumUses + "</small>"));
			_option2.setTextSize(16);
			_option2.getBackground().setColorFilter(new LightingColorFilter(0xFF000000, attacks[2].GetBackgroundColor()));
		}else{ 
			_option2.setText("------");
			_option2.setClickable(false);
		}
		if (attacks[3] != null){
			_option4.setText(Html.fromHtml(attacks[3].Name + "<br/><small>" + attacks[3].NumUses + "/" + attacks[3].BaseNumUses + "</small>"));
			_option4.setTextSize(16);
			_option4.getBackground().setColorFilter(new LightingColorFilter(0xFF000000, attacks[3].GetBackgroundColor()));
		}else{
			_option4.setText("------");
			_option4.setClickable(false);
		}
	}
	
	public void TapPet(View view)
	{
		String modifiers = _activePet.Name + " the " + _activePet.GetSpeciesAndGender() +"\n Lvl. " + _activePet.Level;
		modifiers += " ("+_activePet.PrimaryType.toString()+")";
		if (_activePet.SecondaryType != null && _activePet.SecondaryType != BattleType.None)
			modifiers += " / (" + _activePet.SecondaryType.toString() + ")";
		
		modifiers += "\n\nHP:\t\t  " + _activePet.HP + "/" + _activePet.BaseHP;
		if (_activePet.SpeedModifier >= 0)
			modifiers += "\nSpeed:\t\t" + _activePet.BaseSpeed + "  +" + _activePet.SpeedModifier;
		if (_activePet.SpeedModifier < 0)
			modifiers += "\nSpeed:\t\t" + _activePet.BaseSpeed + "  -" + (-1*_activePet.SpeedModifier);
		
		if (_activePet.AttackModifier >= 0)
			modifiers += "\nAttack:\t\t" + _activePet.BaseAttack + "  +" + _activePet.AttackModifier;
		if (_activePet.AttackModifier < 0)
			modifiers += "\nAttack:\t\t" + _activePet.BaseAttack + "  -" + (-1*_activePet.AttackModifier);
		
		if (_activePet.DefenseModifier >= 0)
			modifiers += "\nDefense:\t" + _activePet.BaseDefense + "  +" + _activePet.DefenseModifier;
		if (_activePet.DefenseModifier < 0)
			modifiers += "\nDefense:\t" + _activePet.BaseDefense + "  -" + (-1*_activePet.DefenseModifier);
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Pet Battle Status");
		alert.setMessage(modifiers);
		alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alert.show();
	}
	
	public void TapFoe(View view)
	{
		String modifiers = _foe.Name + " the " + _foe.GetSpeciesAndGender() +"\n Lvl. " + _foe.Level; 
		modifiers += " ("+_foe.PrimaryType.toString()+")";
		if (_foe.SecondaryType != null && _foe.SecondaryType != BattleType.None)
			modifiers += " / (" + _foe.SecondaryType.toString() + ")";
		
		modifiers += "\n\nHP:\t\t  " + ((int)Math.round((float)_foe.HP / (float)_foe.BaseHP)*100) + "%";
		if (_foe.SpeedModifier >= 0)
			modifiers += "\nSpeed:\t\t+" + _foe.SpeedModifier;
		else modifiers += "\nSpeed:\t\t-" + (-1*_foe.SpeedModifier);
		if (_foe.AttackModifier >= 0)
			modifiers += "\nAttack:\t\t+" + _foe.AttackModifier;
		else modifiers += "\nAttack:\t\t-" + (-1*_foe.AttackModifier);
		if (_foe.DefenseModifier >= 0)
			modifiers += "\nDefense:\t+" + _foe.DefenseModifier;
		else modifiers += "\nDefense:\t-" + (-1*_foe.DefenseModifier);
		
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Enemy Battle Status");
		alert.setMessage(modifiers);
		alert.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alert.show();
	}
	
	public void Option1(View view){
		if (battleState == BattleStateType.BEGIN){
			if (_activePet.PickNextAttack().Name == "Struggle"){
				UseAttack(new Struggle(), _foe.PickNextAttack());
			}
			else if (_activePet.MentalStatus != null && _activePet.MentalStatus.getClass().equals(Berserk.class)){
				UseAttack(_activePet.PickNextAttack(), _foe.PickNextAttack());
			}else
				StartFight();
		}else if (battleState == BattleStateType.FIGHT){
			if (_activePet.Attacks[0].NumUses <= 0)
				OutOfPP(_activePet.Attacks[0]);
			else{
				UseAttack(_activePet.Attacks[0], _foe.PickNextAttack());
				//BackToBasics();
			}
		}else if (battleState == BattleStateType.SWITCH){
			
		}else if (battleState == BattleStateType.TAP_TO_CONTINUE){
			TapToContinue();
		}else if (battleState == BattleStateType.END){
			AdventureAgain();
		}
	}
	
	public void Option2(View view){
		if (battleState == BattleStateType.BEGIN){
			Intent intent = new Intent(this, InventoryActivity.class);
			startActivity(intent);
			appState.BattleIndex = battleIndex;
			battleState = BattleStateType.USE_ITEM;
		}else if (battleState == BattleStateType.FIGHT){
			if (_activePet.Attacks[2].NumUses <= 0)
				OutOfPP(_activePet.Attacks[2]);
			else{
				UseAttack(_activePet.Attacks[2], _foe.PickNextAttack());
			}
		}else if (battleState == BattleStateType.SWITCH){
		}else if (battleState == BattleStateType.END){
			
		}
	}
	
	public void Option3(View view){
		if (battleState == BattleStateType.BEGIN){
			Intent intent = new Intent(this, PickBattlePetActivity.class);
			startActivity(intent);
		}else if (battleState == BattleStateType.FIGHT){
			if (_activePet.Attacks[1] == null)
				return;
			else if (_activePet.Attacks[1].NumUses <= 0)
				OutOfPP(_activePet.Attacks[1]);
			else{
				UseAttack(_activePet.Attacks[1], _foe.PickNextAttack());
			}
		}else if (battleState == BattleStateType.SWITCH){
			
		}else if (battleState == BattleStateType.END){
			appState.MyAdventures.ConsecutiveAdventureCounter = 0;
			for (int i = 0; i < 4; i++){
				if (appState.getActivePets()[i] != null){
					appState.getActivePets()[i].ResetBattleStats(false, false, true, true, false);
					appState.getActivePets()[i].RestartEnergyRestoreTimer();
				}
			}
			finish();
		}
	}
	
	public void Option4(View view){
		if (battleState == BattleStateType.BEGIN){
			BackToGarden(view);
		}else if (battleState == BattleStateType.FIGHT){
			if (_activePet.Attacks[3] == null)
				return;
			else if (_activePet.Attacks[3].NumUses <= 0)
				OutOfPP(_activePet.Attacks[3]);
			else{
				UseAttack(_activePet.Attacks[3], _foe.PickNextAttack());
				//BackToBasics();
			}
		}else if (battleState == BattleStateType.SWITCH){
			
		}
	}
	
	public void UseItem(PetItem petItem){
		String effect = petItem.Effect.DoItemEffect(appState.getTempActivePet(), _foe);
		
		appState.MyInventory.removeOneFromInventory(petItem);
		if (!effect.trim().isEmpty())
			AppendToBattleText("•" + effect);
		if (petItem.getClass().equals(new Substitute().getClass())){
			RewriteBattleText("•Asuka put out the Substitute.");
			RunAwayFromBattle(false, false);
		}
	}
	
	public void UseAttack(Attack userAttack, Attack foeAttack){	
		WhosAttackedKeeper = new boolean[]{ false, false };
		HPKeeper = new int[]{ _activePet.HP, _foe.HP };
		if (userAttack.Priority < foeAttack.Priority || (userAttack.Priority == foeAttack.Priority && _activePet.BattleSpeed() > _foe.BattleSpeed())){
			AddToAttackQueue(_foe, _activePet, userAttack);
			QueueActiveEffects(_foe, _activePet);
			AddToAttackQueue(_activePet, _foe, foeAttack);
			QueueActiveEffects(_activePet, _foe);
		}else if (userAttack.Priority > foeAttack.Priority || (userAttack.Priority == foeAttack.Priority && _activePet.BattleSpeed() < _foe.BattleSpeed())){
			AddToAttackQueue(_activePet, _foe, foeAttack);
			QueueActiveEffects(_activePet, _foe);
			AddToAttackQueue(_foe, _activePet, userAttack);
			QueueActiveEffects(_foe, _activePet);
		}else{
			if (PPApp.AppRandom.nextInt(2) == 0){
				AddToAttackQueue(_foe, _activePet, userAttack);
				QueueActiveEffects(_foe, _activePet);
				AddToAttackQueue(_activePet, _foe, foeAttack);
				QueueActiveEffects(_activePet, _foe);
			}else{
				AddToAttackQueue(_activePet, _foe, foeAttack);
				QueueActiveEffects(_activePet, _foe);
				AddToAttackQueue(_foe, _activePet, userAttack);
				QueueActiveEffects(_foe, _activePet);
			}
		}
		RewriteBattleText("");
		battleState = BattleStateType.BEGIN_ANIMATE_HP;
	}
	
	private void AddToAttackQueue(PixelPet target, PixelPet user, Attack attack)
	{
		WhosAttackedKeeper = new boolean[]{ false, false }; //_activePet, _foe
		
		DirectedAttack targetAttack = new DirectedAttack(target, user, null, attack, 0);
		attackQueue.add(targetAttack);
	}
	
	private void QueueActiveEffects(PixelPet target, PixelPet user){
		if (user != null){
			if (user.PhysicalStatus != null){
				if (user.PhysicalStatus.NumTurns > 0){
					DirectedAttack physicalEffect = new DirectedAttack(target, user, user.PhysicalStatus, null, 0);
					physicalEffect.IsPhysicalEffect = true;
					attackQueue.add(physicalEffect);
				}else user.PhysicalStatus = null;
			}
			if (user.MentalStatus != null){
				if (user.MentalStatus.NumTurns > 0){
					DirectedAttack mentalEffect = new DirectedAttack(target, user, user.MentalStatus, null, 0);
					mentalEffect.IsMentalEffect = true;
					attackQueue.add(mentalEffect);
				}else user.MentalStatus = null;
			}
		}else{
			for (int i = 0; i < fieldEffects.length; i++){
				FieldEffect effect = fieldEffects[i];
				if (effect == null) continue;
				if (effect.Effect.NumTurns > 0){
					if (effect.IsUserActivePet){
						effect.Effect.user = _activePet;
						effect.Effect.target = _foe;
					}else{
						effect.Effect.user = _foe;
						effect.Effect.target = _activePet;
					}
					
					DirectedAttack fieldEffect = new DirectedAttack(effect.Effect.target, effect.Effect.user, effect.Effect, null, 0);
					fieldEffect.IsFieldEffect = true;
					attackQueue.add(fieldEffect);
				}
				else fieldEffects[i] = null;
			}
		}
	}
	
	public String DoAttack(DirectedAttack attack){
		String result = "";
		if (!attack.IsRecoil) attack.DecrementPP();
		int realDamage = attack.Damage;
		boolean canAttack = true;
		
		if (attack.Attack != null && !attack.IsRecoil && attack.User.MentalStatus != null){
			if (attack.User.MentalStatus.getClass().equals(Afraid.class)){
				result = "•"+attack.User.Name+" is too scared to attack!";
				canAttack = false;
			}if (attack.User.MentalStatus.getClass().equals(Asleep.class)){
				result = "•"+attack.User.Name+" is asleep...";
				canAttack = false;
			}if (attack.User.MentalStatus.getClass().equals(Confused.class)){
				result = "•"+attack.User.Name+" is confused...";
				if (PPApp.AppRandom.nextInt(2) == 0){
					result += "\n•"+attack.User.Name+" hurt themself in confusion!";
					
					PixelPet user = attack.User;
					realDamage = (Integer)Math.round(((((2.0f * user.Level) / 5.0f + 2.0f) * (float)40 * (float)user.BattleAttack()) / (float)user.BattleDefense()) / 50.0f + 2.0f);
					
					attack.User.HP -= realDamage;
					if (attack.User.HP < 0) 
						attack.User.HP = 0;
					if (attack.User.HP > attack.Target.BaseHP)
						attack.User.HP = attack.Target.BaseHP;
				}
			}
		}

		
		if (attack.Attack != null && canAttack){
			if (!attack.IsRecoil){
				AttackResult attackResult;
				Attack mimickedAttack = null;
				if (attack.User == _activePet){
					attackResult = attack.Attack.UseAttack(attack.User, attack.Target, lastAttackKeeper[0], lastAttackKeeper[1], PPApp.AppRandom, appState.getActivePets());
					mimickedAttack = lastAttackKeeper[1];
				}
				else if (attack.User == _foe){
					attackResult = attack.Attack.UseAttack(attack.User, attack.Target, lastAttackKeeper[1], lastAttackKeeper[0], PPApp.AppRandom, appState.getActivePets());
					mimickedAttack = lastAttackKeeper[0];
				}
				else 
					attackResult = attack.Attack.UseAttack(attack.User, attack.Target, null, null, PPApp.AppRandom, appState.getActivePets());
				realDamage = (Integer)attackResult.DamageToTarget;
				attack.Effect = (BattleEffect)attackResult.Effect;
				
				result = "•"+attack.User.Name+" used "+attack.Attack.Name+"!";
				if (attack.Attack.getClass().equals(new MimicSong().getClass()) && mimickedAttack != null)
					result += "\n•"+attack.User.Name+" used "+mimickedAttack.Name+"!";
				String type = attack.Attack.GetTypeEffectiveString(attack.Target.PrimaryType, attack.Target.SecondaryType, attack.Attack.CanBeCriticalOrTypeAdv);
				if (type != null && !type.trim().isEmpty())
					result += "\n" + type;
				
				
				if (attackResult.FieldEffect != null){
					DirectedAttack fieldEffectActivation = new DirectedAttack(attack.Target, attack.User, (BattleEffect)attackResult.FieldEffect, null, 0);
					fieldEffectActivation.IsFieldEffect = true;
					fieldEffectActivation.IsRecoil = true;
					AddToBeginningOfQueue(fieldEffectActivation);
				}
				
				if (attackResult.DamageToUser != 0 || !attackResult.RecoilText.trim().isEmpty()){
					DirectedAttack userRecoil = new DirectedAttack(attack.User, attack.Target, null, attack.Attack, (Integer)attackResult.DamageToUser);
					String recoilResult = attackResult.RecoilText;
					if (recoilResult != null && !recoilResult.trim().isEmpty())
						userRecoil.ResultText = "•" + recoilResult;
					userRecoil.IsRecoil = true;
					AddToBeginningOfQueue(userRecoil);
				}
				
				if (attackResult.WasACriticalHit)
					result += "\n•Critical Hit!!";
			}else if (attack.ResultText != null)
				result += attack.ResultText;
			
			attack.Target.HP -= realDamage;
			if (attack.Target.HP < 0) 
				attack.Target.HP = 0;
			if (attack.Target.HP > attack.Target.BaseHP)
				attack.Target.HP = attack.Target.BaseHP;
		}
		
		if (attack.Effect != null && canAttack){
			String effectResult = attack.Effect.ActivateEffect(attack.User, attack.Target);
			attack.Effect.NumTurns++;
			if (effectResult != null && !effectResult.trim().isEmpty())
				result += "\n•" + effectResult;
			if (attack.Effect.IsPhysicalEffect && attack.Effect.NumTurns > 0){
				if (attack.User.PhysicalStatus == null)
					attack.User.PhysicalStatus = attack.Effect;
			}
			else if (attack.Effect.IsMentalEffect && attack.Effect.NumTurns > 0)
				attack.User.MentalStatus = attack.Effect;
		}
		return result;
	}
	
	public String DoEffect(DirectedAttack attack)
	{
		String result = "";
		if (attack.IsFieldEffect && attack.Effect != null){ //FieldEffect
			if (!attack.Effect.HasActivated){ //FieldEffect has not been activated
				attack.Effect.NumTurns++;
				boolean isUserActivePet = false;
				if (attack.User == _activePet) isUserActivePet = true;
				
				result = "•" + attack.Effect.ActivateEffect(attack.User, attack.Target);
				if (isUserActivePet)
					fieldEffects[0] = new FieldEffect(attack.Effect, isUserActivePet);
				else fieldEffects[1] = new FieldEffect(attack.Effect, isUserActivePet);
				return result;
			}
		}
		
		attack.Effect.NumTurns--;
		if (attack.Effect.NumTurns <= 0){ //Effect has run out...
			String str = attack.Effect.NullifyEffect(attack.User, attack.Target);
			if (str != null && !str.trim().isEmpty()){
				if (!result.trim().isEmpty())
					result += "\n•"+str;
				else
					result += "•"+str;
			}
			
			if (attack.IsFieldEffect){
				for (int i = 0; i < fieldEffects.length; i++){
					if (fieldEffects[i] == null) continue;
					if (fieldEffects[i].Effect == attack.Effect)
						fieldEffects[i] = null;
				}
			}
			else{
				if (attack.IsPhysicalEffect)
					attack.User.PhysicalStatus = null;
				else if (attack.IsMentalEffect)
					attack.User.MentalStatus = null;
			}
		}
		else{ //Effect is still going
			Object[] effects = attack.Effect.PersistentEffect();
			attack.Effect.target.HP -= (Integer)effects[0];
			if (attack.Effect.target.HP <= 0) 
				attack.Effect.target.HP = 0;
			if (attack.Effect.target.HP > attack.Effect.target.BaseHP) 
				_foe.HP = attack.Effect.target.BaseHP;
			attack.Effect.user.HP -= (Integer)effects[1];
			if (attack.Effect.user.HP <= 0) 
				attack.Effect.user.HP = 0;
			if (attack.Effect.user.HP > attack.Effect.user.BaseHP) 
				attack.Effect.user.HP = attack.Effect.user.BaseHP;
			String str = (String)effects[2];
			if (str != null && !str.trim().isEmpty()){
				if (!result.trim().isEmpty())
					result += "\n•"+str;
				else
					result += "•"+str;
			}
		}
		return result;
	}
	
	public void OutOfPP(Attack attack){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Out of Energy");
		alert.setMessage(_activePet.Name + " has no more energy to use this move!");
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		alert.show();
	}
	
	public void PetSwitchedIn(){
		_activePet = appState.getTempActivePet();
		_activePet.ResetBattleStats(false, false, true, true, false);
		HandleAndDrawHP();
		HandleAndDrawPet();
		battleIndex = appState.tempIndex;
		participators[battleIndex] = true;
		
		String battleResult = "•" + _activePet.Name + " is switched into battle!";
		
		if (battleState != BattleStateType.SWITCH){
			WhosAttackedKeeper = new boolean[]{ true, false }; //_activePet, _foe
			AddToAttackQueue(_activePet, _foe, _foe.PickNextAttack());
			QueueActiveEffects(_foe, _activePet);
			QueueActiveEffects(null, null);
			battleState = BattleStateType.BEGIN_ANIMATE_HP;
		}else
			BackToBasics();
		RewriteBattleText(battleResult);
		HandleAndDrawHP();
	}
	
	public void FoeSwitchedIn(){
		_foe.ResetBattleStats(false, false, true, true, false);
		HandleAndDrawHP();
		HandleAndDrawPet();
		battleIndex = appState.tempIndex;
		
		String battleResult = "•" + _foe.Name + " draws near!";
		
		BackToBasics();
		//if (battleState != BattleStateType.SWITCH){
		//	battleResult += FoeAttackResult(_foe.PickNextAttack()); //TODO::!!! If foe is switched in on attack?
		//}
		
		RewriteBattleText(battleResult);
		HandleAndDrawHP();
	}
	
	public void HandleAndDrawHP(){
		View petHP = (View)findViewById(R.id.pet_hp_bar);
		TextView petHPText = (TextView)findViewById(R.id.pet_hp_text);
		View foeHP = (View)findViewById(R.id.foe_hp_bar);
		TextView foeHPText = (TextView)findViewById(R.id.foe_hp_text);
		
		RelativeLayout.LayoutParams petHPParams = (RelativeLayout.LayoutParams)petHP.getLayoutParams();
		RelativeLayout.LayoutParams foeHPParams = (RelativeLayout.LayoutParams)foeHP.getLayoutParams();
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
		petHPParams.width = (int)(size * ((float)_activePet.HP / (float)_activePet.BaseHP));
		foeHPParams.width = (int)(size * ((float)_foe.HP / (float)_foe.BaseHP));
		
		petHP.setLayoutParams(petHPParams);
		foeHP.setLayoutParams(foeHPParams);
		
		petHPText.setText(_activePet.HP + "/" + _activePet.BaseHP);
		float ratio = ((float)_foe.HP) / ((float)_foe.BaseHP);
		foeHPText.setText(Math.round(100 * ratio) + "%");
	}
	
	public void HandleAndDrawPet(){	
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
		
		_petName.setText(_activePet.Name);
		_petLevel.setText("Lvl. "+_activePet.Level);
		_petView.setImageResource(_activePet.GetDrawableId(false));
		_petView.scrollTo((_activePet.CurrFrame + _activePet.RelAniX)*size, _activePet.RelAniY * size);
		
		_foeName.setText(_foe.Species);
		_foeLevel.setText("Lvl. " + _foe.Level);
		_foeView.setImageResource(_foe.GetDrawableId(false));
		_foeView.scrollTo(((1 - _foe.CurrFrame) + _foe.RelAniX)*size, _foe.RelAniY * size);
	}
}
