package com.cakeandturtles.pixelpets;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cakeandturtles.pixelpets.adventures.Adventure;
import com.cakeandturtles.pixelpets.adventures.AdventureOption;
import com.cakeandturtles.pixelpets.adventures.GreedyTreasure;
import com.cakeandturtles.pixelpets.adventures.Quest;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.example.pixelpets.R;

public class AdventureActivity extends Activity {
	private PPApp appState;
	private boolean _isSpecialAdventure;
	private boolean _adventureOver;
	private int areaIndex;
	private PixelPet _activePet;
	private Adventure _adventure;
	private TextView _petName;
	private TextView _petLevel;
	private ImageView _petView;
	private ImageView _adventureView;
	private TextView _adventureDescription;
	private Button _option1;
	private Button _option2;
	private Button _option3;
	private Button _option4;
	private Button _adventureAgain;
	private Button _backToMap;
	private Handler handler;
	private Runnable runnable;
	private int _relAniX;
	private int _relAniY;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adventure);
		// Show the Up button in the action bar.
		setupActionBar();
		
		appState = ((PPApp)getApplicationContext());
		areaIndex = -1;
		_isSpecialAdventure = false;
		_adventureOver = false;
		
		_activePet = appState.getTempActivePet();
		if (_activePet.HP <= 0){
			boolean finish = true;
			appState.tempIndex--;
			for (int i = 0; i < 4; i++){
				appState.tempIndex++;
				if (appState.tempIndex >= 4) appState.tempIndex = 0;
				PixelPet temp = appState.getTempActivePet();
				if (temp != null && temp.CurrentForm != PixelPet.PetForm.Egg && temp.HP > 0){
					finish = false;
					break;
				}
			}
			if (finish) finish();
		}_activePet = appState.getTempActivePet();
		
		GetNewAdventure();
		_adventure.InitialEffect(appState.MyAdventures);
		
		InitializeViews();
		InitializeLongClick();
		_adventureAgain.setVisibility(View.GONE);
		_adventureAgain.setClickable(false);
		_backToMap.setVisibility(View.GONE);
		_backToMap.setClickable(false);
		
		UpdateAdventure();
		_relAniX = _adventure.RelAniX;
		_relAniY = _adventure.RelAniY;
		HandleAndDrawPet();
		
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.adventure, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume()
	{
		super.onResume();
		if (_petName == null)
			InitializeViews();
		
		runnable = new Runnable(){
			@Override
			public void run(){
				HandleAndDrawPet();
				_activePet.UpdateAnimation();
				//_activePet.UpdatePetForm();
				handler.postDelayed(this, 60);
			}
		};
		handler = new Handler();
		handler.postDelayed(runnable, 60);
		appState.StopRunAndHandle();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		NullifyViews();
		handler.removeCallbacks(runnable);
		handler = null;
		runnable = null;
		appState.SaveUser();
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
		intent.putExtra("com.cakeandturtles.pixelpets.fromAdventure", true);
		startActivity(intent);
	}
	
	private void InitializeViews(){
		_petName = (TextView)findViewById(R.id.pet_name);
		_petLevel = (TextView)findViewById(R.id.pet_level);
		_petView = (ImageView)findViewById(R.id.pet_area);
		_adventureView = (ImageView)findViewById(R.id.adventure_area);
		_adventureDescription = (TextView)findViewById(R.id.adventure_description);
		_option1 = (Button)findViewById(R.id.adventure_button1);
		_option2 = (Button)findViewById(R.id.adventure_button2);
		_option3 = (Button)findViewById(R.id.adventure_button3);
		_option4 = (Button)findViewById(R.id.adventure_button4);
		_adventureAgain = (Button)findViewById(R.id.adventure_again);
		_backToMap = (Button)findViewById(R.id.back_to_menu);
	}
	
	private void NullifyViews(){
		_petName = null;
		_petLevel = null;
		_petView = null;
		_adventureView = null;
		_adventureDescription = null;
		_option1 = null;
		_option2 = null;
		_option3 = null;
		_option4 = null;
		_backToMap = null;
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		_adventure = null;
		appState.StopRunAndHandle();
	}
	
	@Override
	public void onBackPressed()
	{
		BackToGarden(null);
	}
	
	public void GetNewAdventure()
	{
		Bundle extras = getIntent().getExtras();
		if (extras != null){
			areaIndex = extras.getInt("com.cakeandturtles.pixelpets.areaIndex");
			String specialQuest = extras.getString("com.cakeandturtles.pixelpets.specialAdventure");
			if (specialQuest != null){
				_isSpecialAdventure = true;
				if (specialQuest.equals("FAIRYQUEST")) 
					_adventure = Adventure.GetNewFairyQuest(_activePet, areaIndex, appState.MyAdventures, appState.MyInventory, appState.Trainer);
				else{
					_adventure = Adventure.GetNewAdventure(_activePet, areaIndex, appState.MyAdventures, appState.MyInventory, appState.Trainer, appState.MyAdventures.ConsecutiveAdventureCounter);
				}
			}else{
				appState.MyAdventures.ConsecutiveAdventureCounter++;
				Log.w("pixelPetCounter", "c: "+appState.MyAdventures.ConsecutiveAdventureCounter);
				_adventure = Adventure.GetNewAdventure(_activePet, areaIndex, appState.MyAdventures, appState.MyInventory, appState.Trainer, appState.MyAdventures.ConsecutiveAdventureCounter);
			}
		}else{
			_adventure = Adventure.GetNewAdventure(_activePet, 0, appState.MyAdventures, appState.MyInventory, appState.Trainer, appState.MyAdventures.ConsecutiveAdventureCounter);
		}
		Quest checkQuest = null;
		if (_adventure.AdventureQuest != null)
			checkQuest = appState.MyAdventures.GetQuest(_adventure.AdventureQuest.Name);
		if (_adventure.EnemyPets != null && _adventure.EnemyPets.length > 0){
			Intent intent = new Intent(this, TrainingActivity.class);
			intent.putExtra("com.cakeandturtles.pixelpets.areaIndex", areaIndex);
			intent.putExtra("com.cakeandturtles.pixelpets.battlePets", _adventure.EnemyPets);
			startActivity(intent);
			finish();
			_adventure = new GreedyTreasure(_activePet); //TODO:: A default workaround to prevent crashes
		}else if (checkQuest != null){
			_adventure.CompleteQuest(checkQuest, checkQuest.IsQuestComplete(appState.MyInventory));
		}
	}
	
	public void AdventureAgain(View view){
		finish();
		Intent intent = new Intent(this, AdventureActivity.class);
		intent.putExtra("com.cakeandturtles.pixelpets.areaIndex", areaIndex);
		startActivity(intent);
	}
	
	public void TapPet(View view)
	{
		_activePet.TapPet();
		HandleAndDrawPet();
	}
	
	public void Option1(View view)
	{
		AdventureOption option1 = _adventure.Option1;
		DoOption(option1);
	}
	
	public void Option2(View view)
	{
		AdventureOption option2 = _adventure.Option2;
		DoOption(option2);
	}
	
	public void Option3(View view)
	{
		AdventureOption option3 = _adventure.Option3;
		DoOption(option3);
	}
	
	public void Option4(View view)
	{
		AdventureOption option4 = _adventure.Option4;
		DoOption(option4);
	}
	
	public void DoOption(AdventureOption option)
	{
		String newDescription = option.Result + "\n";
		
		if (option.HPMod != 0){
			_activePet.HP += option.HPMod;
			if (_activePet.HP > _activePet.BaseHP)
				_activePet.HP = _activePet.BaseHP;
			if (_activePet.HP <= 0){
				_activePet.HP = 0;
				newDescription += "\n" + _activePet.Name + " is OUT OF ENERGY!!";
				
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						dialog.cancel();
					}
				});
				alert.setTitle("Out of Energy!");
				alert.setMessage(_activePet.Name + " is OUT OF ENERGY!!");
				alert.show();
			}
		}
			
		
		if (option.EggEncounter != null){
			int index = -1;
			for (int i = 1; i < 4; i++){
				if (appState.getActivePets()[i] == null){
					index = i;
					break;
				}
			}
			appState.MyAdventures.NewEggIndex = index;
			if (index >= 0)
				newDescription += "\n" + appState.AddEggToParty(option.EggEncounter);
			else{
				if (!appState.TheDaycare.IsDaycareFull())
					SwapPartyForEggDialog(option.EggEncounter);
				else
					ReleasePartyDaycareForEggDialog(option.EggEncounter);
			}
		}
		
		if (_activePet.HP > 0){
			if (option.ResultingQuest != null){
				newDescription += option.ResultingQuest.Description;
				newDescription += "\nYou have started a QUEST!";
				appState.MyAdventures.AddQuest(option.ResultingQuest);
			}if (option.SubtractQuest != null){
				appState.MyAdventures.RemoveQuest(option.SubtractQuest);
			}
			
			for (int i = 0; i < option.ResultingItems.length; i++){
				if (option.ResultingItems[i].Quantity <= 0) continue;
				
				PetItem item = option.ResultingItems[i];
				int quantity = appState.MyInventory.addToInventory(item, appState.MyCodex);
				if (quantity > 0)
					newDescription += "\n YOU GAIN " + quantity + " " +option.ResultingItems[i].Name + "!";
				else newDescription += "\nYou cannot hold any more " + item.Name + "...";
			}
			for (int i = 0; i < option.SubtractItems.length; i++){
				if (option.SubtractItems[i].Quantity <= 0) continue;
				
				int quantity = 0;
				for (int j = 0; j < option.SubtractItems[i].Quantity; j++){
					if (appState.MyInventory.inventoryContains(option.SubtractItems[i])){
						appState.MyInventory.removeOneFromInventory(option.SubtractItems[i]);
						quantity++;
					}else break;
				}
				newDescription += "\n YOU LOSE " + quantity + " " +option.SubtractItems[i].Name;
			}
			
			int petExp = _activePet.CalculateRealExp(option.ExpForOption);
			if (_activePet.Level < _activePet.MaxLevel){
				if (petExp > 0)
					newDescription += "\n" + _activePet.Name + " GAINS " + petExp + " EXP!";
				else if (option.ExpForOption < 0)
					newDescription += "\n" + _activePet.Name + " LOSES " + petExp + " EXP..."; //TODO::???
				_activePet.Exp += petExp;
				for (int i = 0; i < 4; i++){
					PixelPet pet = appState.getActivePets()[i];
					if (pet == null) break;
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
			}else{
				for (int i = 0; i < 4; i++){
					PixelPet pet = appState.getActivePets()[i];
					if (pet == null) continue;
					if (pet.CurrentForm == PixelPet.PetForm.Primary && pet.JustEvolved && !appState.notifications[i])
						appState.NotifyOfPetFormChange(pet, i);
				}
			}
			
			if (option.RestoresEnergy){
				newDescription += "\n" + _activePet.Name + " is FULLY RESTORED!";
				_activePet.ResetBattleStats(true, true, true, true, true);
			}
			
			int helper = _activePet.ModifyAmbition(option.AmbitionModifier);
			if (helper > 0) newDescription += "\n" + _activePet.Name + " GAINS AMBITION!";
			else if (helper < 0) newDescription += "\n" + _activePet.Name + " LOSES AMBITION...";
			else if (option.AmbitionModifier < 0) newDescription += "\n" + _activePet.Name + "'s AMBITION is zero.";
			else if (option.AmbitionModifier > 0) newDescription += "\n" + _activePet.Name + "'s AMBITION is max.";
			
			helper = _activePet.ModifyEmpathy(option.EmpathyModifier);
			if (helper > 0) newDescription += "\n" + _activePet.Name + " GAINS EMPATHY!";
			else if (helper < 0) newDescription += "\n" + _activePet.Name + " LOSES EMPATHY...";
			else if (option.EmpathyModifier < 0) newDescription += "\n" + _activePet.Name + "'s EMPATHY is zero.";
			else if (option.EmpathyModifier > 0) newDescription += "\n" + _activePet.Name + "'s EMPATHY is max.";
			
			helper = _activePet.ModifyInsight(option.InsightModifier);
			if (helper > 0) newDescription += "\n" + _activePet.Name + " GAINS INSIGHT!";
			else if (helper < 0) newDescription += "\n" + _activePet.Name + " LOSES INSIGHT...";
			else if (option.InsightModifier < 0) newDescription += "\n" + _activePet.Name + "'s INSIGHT is zero.";
			else if (option.InsightModifier > 0) newDescription += "\n" + _activePet.Name + "'s INSIGHT is max.";
			
			helper = _activePet.ModifyDiligence(option.DiligenceModifier);
			if (helper > 0) newDescription += "\n" + _activePet.Name + " GAINS DILIGENCE!";
			else if (helper < 0) newDescription += "\n" + _activePet.Name + " LOSES DILIGENCE...";
			else if (option.DiligenceModifier < 0) newDescription += "\n" + _activePet.Name + "'s DILIGENCE is zero.";
			else if (option.DiligenceModifier > 0) newDescription += "\n" + _activePet.Name + "'s DILIGENCE is max.";
			
			helper = _activePet.ModifyCharm(option.CharmModifier);
			if (helper > 0) newDescription += "\n" + _activePet.Name + " GAINS CHARM!";
			else if (helper < 0) newDescription += "\n" + _activePet.Name + " LOSES CHARM...";
			else if (option.CharmModifier < 0) newDescription += "\n" + _activePet.Name + "'s CHARM is zero.";
			else if (option.CharmModifier > 0) newDescription += "\n" + _activePet.Name + "'s CHARM is max.";
		}
			
		_adventureDescription.setText(newDescription);
			
		if (_adventure.eggEncounter == null){
			_relAniX = option.RelAniX;
			_relAniY = option.RelAniY;
		}
		
		_adventureOver = true;
		appState.SaveUser();
		RemoveOptionButtons();
	}
	
	public void SwapPartyForEgg(final PixelPet egg){
		appState.tempEggSwapHolder = egg;
		Intent intent = new Intent(this, SwapPartyEggActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void SwapPartyForEggDialog(final PixelPet egg){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
				SwapPartyForEgg(egg);
			}
		});
		alert.setNegativeButton("No", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				_adventureDescription.setText(_adventureDescription.getText() + "\n" + appState.AddEggToParty(egg));
			}
		});
		alert.setTitle("Swap for Egg?");
		alert.setMessage("Do you want to put a member of your party in the Daycare so the Egg can join your party?\n\n(If not, the Egg will just be put in the Daycare.)");
		alert.show();
	}
	
	public void ReleasePartyDaycareForEgg(final PixelPet egg){
		appState.tempEggSwapHolder = egg;
		Intent intent = new Intent(this, SwapPartyEggActivity.class);
		intent.putExtra("com.cakeandturtles.pixelpets.release", true);
		startActivity(intent);
		finish();
	}
	
	public void ReleasePartyDaycareForEggDialog(final PixelPet egg){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
				ReleasePartyDaycareForEgg(egg);
			}
		});
		alert.setNegativeButton("No", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				_adventureDescription.setText(_adventureDescription.getText() + "\nYou leave the egg where it is.");
			}
		});
		alert.setTitle("Release for Egg?");
		alert.setMessage("Your Party and the Daycare are both full.\n\nDo you wish to release a pet from your Party or Daycare into the wild so you can take this Egg?");
		alert.show();
	}
	
	public void BackToGarden(View view)
	{
		if (_adventureOver){
			ExitActivity();
		}else{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			if (_adventure.Option4 != null){
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						DoOption(_adventure.Option4); //TODO:: BY DEFAULT, we'll make OPTION 4 THE "RUN AWAY" OPTION
						dialog.cancel();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						dialog.cancel();
					}
				});
				builder.setTitle("Run Away?");
				builder.setMessage("Do you want to run away from adventure?");
				AlertDialog alert = builder.create();
				alert.show();
				((TextView)alert.findViewById(android.R.id.message)).setTextSize(18);
			}else if (_adventure.Option2 != null){
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						DoOption(_adventure.Option2); //TODO:: BY DEFAULT, we'll make OPTION 2 THE "RUN AWAY" OPTION
						dialog.cancel();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						dialog.cancel();
					}
				});
				builder.setTitle("Run Away?");
				builder.setMessage("Do you want to run away from adventure?");
				AlertDialog alert = builder.create();
				alert.show();
				((TextView)alert.findViewById(android.R.id.message)).setTextSize(18);
			}else{
				ExitActivity();
			}
		}
	}
	
	private void ExitActivity(){
		appState.MyAdventures.ConsecutiveAdventureCounter = 0;
		for (int i = 0; i < 4; i++){
			if (appState.getActivePets()[i] != null)
				appState.getActivePets()[i].RestartEnergyRestoreTimer();
		}
		finish();
	}
	
	public void UpdateAdventure()
	{
		if (_adventure.eggEncounter != null){
			_adventureView.setImageResource(_adventure.eggEncounter.GetDrawableId(false));
			_adventureView.setScaleX(-1.0f);
		}else if (_adventure.itemEncounter != null){
			_adventureView.setImageResource(R.drawable.item_sheet);
		}
		
		_adventureDescription.setText(_adventure.Description);
		if (_adventure.Option1 != null)
			_option1.setText(_adventure.Option1.Name);
		else{
			_option1.setVisibility(View.GONE);
			_option1.setOnClickListener(null);
		}
		if (_adventure.Option2 != null)
			_option2.setText(_adventure.Option2.Name);
		else{
			_option2.setVisibility(View.GONE);
			_option2.setOnClickListener(null);
		}
		if (_adventure.Option3 != null)
			_option3.setText(_adventure.Option3.Name);
		else{
			_option3.setVisibility(View.GONE);
			_option3.setOnClickListener(null);
		}
		if (_adventure.Option4 != null)
			_option4.setText(_adventure.Option4.Name);
		else{
			_option4.setVisibility(View.GONE);
			_option4.setOnClickListener(null);
		}
		
		if (_adventure.Option1 == null && _adventure.Option2 == null && _adventure.Option3 == null && _adventure.Option4 == null){
			RemoveOptionButtons();
		}
	}
	
	public void HandleAndDrawPet(){	
		if (_activePet.CurrentForm != PixelPet.PetForm.Egg)
		{
			_petName.setText(_activePet.Name);
			_petLevel.setText("Lvl. "+_activePet.Level);
			_petView.setImageResource(_activePet.GetDrawableId(false));

		}
		
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
		_petView.scrollTo((_activePet.CurrFrame + _activePet.RelAniX)*size, _activePet.RelAniY * size);
		if (_adventure.eggEncounter != null) 
			_adventureView.scrollTo((_activePet.CurrFrame + _adventure.eggEncounter.RelAniX)*size, _adventure.eggEncounter.RelAniY * size);
		else if (_adventure.itemEncounter != null)
			_adventureView.scrollTo(_adventure.itemEncounter.RelAniX*size, _adventure.itemEncounter.RelAniY * size);
		else
			_adventureView.scrollTo((_activePet.CurrFrame + _relAniX)*size, _relAniY * size);
	}
	
	public void RemoveOptionButtons()
	{
		_option1.setVisibility(View.GONE);
		_option1.setOnClickListener(null);
		_option2.setVisibility(View.GONE);
		_option2.setOnClickListener(null);
		_option3.setVisibility(View.GONE);
		_option3.setOnClickListener(null);
		_option4.setVisibility(View.GONE);
		_option4.setOnClickListener(null);
		
		_adventureAgain.setVisibility(View.VISIBLE);
		_adventureAgain.setClickable(true);
		_backToMap.setVisibility(View.VISIBLE);
		_backToMap.setClickable(true);
		
		boolean allDead = true;
		for (int i = 0; i < 4; i++){
			if (appState.getActivePets()[i] == null) continue;
			else if (appState.getActivePets()[i].CurrentForm != PixelPet.PetForm.Egg && appState.getActivePets()[i].HP > 0)
				allDead = false;
		}
		if (_isSpecialAdventure || allDead){// || appState.MyAdventures.NewEggIndex >= 0){
			_adventureAgain.setVisibility(View.GONE);
			_adventureAgain.setClickable(false);
			if (allDead)
				appState.ResetConsecutiveAdventureCounter(true, this);
			
			LinearLayout.LayoutParams layout = (LinearLayout.LayoutParams)_backToMap.getLayoutParams();
			layout.weight = 1.0f;
			_backToMap.setLayoutParams(layout);
			if (_isSpecialAdventure)
				_backToMap.setText("Back to Map");
			else
				_backToMap.setText("Back to Garden");
		}
	}
}
