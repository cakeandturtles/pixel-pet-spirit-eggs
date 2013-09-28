package com.cakeandturtles.pixelpets;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cakeandturtles.pixelpets.managers.Settings;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;
import com.example.pixelpets.R;

public class DaycareViewStatsActivity extends Activity {
	private PPApp appState;
	private PixelPet _activePet;
	private int petIndex;
	private ImageView _petView;
	private Button _nextPetButton;
	private Button _prevPetButton;
	private Handler handler;
	private Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daycare_view_stats);
		
		appState = ((PPApp)getApplicationContext());
		
		petIndex = 0;
		Bundle extras = getIntent().getExtras();
		if (extras != null){
			int index = extras.getInt("com.cakeandturtles.pixelpets.daycareIndex");
			petIndex = index;
		}
		_activePet = appState.TheDaycare.LocateInStorage(petIndex);
		InitializeViews();
		
		UpdateNextPrevButtons();
		UpdateStatistics();
		HandleAndDrawPet();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.daycare_view_stats, menu);
		menu.findItem(R.id.notifications).setChecked(appState.getSettings().NotifyUser);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		menu.findItem(R.id.notifications).setChecked(appState.getSettings().NotifyUser);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.notifications:
            	Settings settings = appState.getSettings();
            	settings.NotifyUser = !settings.NotifyUser;
                item.setChecked(settings.NotifyUser);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	
	@Override
	public void onResume()
	{
		super.onResume();
		if (_petView == null)
			InitializeViews();
		
		runnable = new Runnable(){
			@Override
			public void run(){
				HandleAndDrawPet();
				UpdateStatistics();
				_activePet.UpdateAnimation();
				for (int i = 0; i < 4; i++){
					PixelPet pet = appState.getActivePets()[i];
					if (pet == null) continue;
					pet.UpdatePetForm();
				}
				handler.postDelayed(this, 60);
			}
		};
		handler = new Handler();
		handler.postDelayed(runnable, 0);
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
		appState.StartRunAndHandle();
		appState.SaveUser();
	}
	
	private void InitializeViews()
	{
		_petView = (ImageView)findViewById(R.id.pet_area);
		_nextPetButton = (Button)findViewById(R.id.next_pet_button);
		_prevPetButton = (Button)findViewById(R.id.prev_pet_button);
	}
	
	private void NullifyViews()
	{
		_petView = null;
		_nextPetButton = null;
		_prevPetButton = null;
	}
	
	public void NextPet(View view){
		if (petIndex < appState.TheDaycare.GetStoredPets().size()){
			petIndex++;
			_activePet = appState.TheDaycare.LocateInStorage(petIndex);
			UpdateStatistics();
			HandleAndDrawPet();
			UpdateNextPrevButtons();
		}
	}
	
	public void PrevPet(View view){
		if (petIndex > 0){
			petIndex--;
			_activePet = appState.TheDaycare.LocateInStorage(petIndex);
			UpdateStatistics();
			HandleAndDrawPet();
			UpdateNextPrevButtons();
		}
	}
	
	private void UpdateNextPrevButtons()
	{
		if (petIndex <= 0){
			_prevPetButton.setClickable(false);
			_prevPetButton.setVisibility(View.GONE);
		}else{
			_prevPetButton.setClickable(true);
			_prevPetButton.setVisibility(View.VISIBLE);
		}
		
		if (petIndex >= appState.TheDaycare.GetStoredPets().size()-1){
			_nextPetButton.setClickable(false);
			_nextPetButton.setVisibility(View.GONE);
		}else{
			_nextPetButton.setClickable(true);
			_nextPetButton.setVisibility(View.VISIBLE);
		}
	}
	
	public void TapPet(View view)
	{
		_activePet.TapPet();
		HandleAndDrawPet();
	}
	
	public void NameYourPet(View view){
		appState.NameYourPet(_activePet, view, this);
	}
	
	public void NotifyUserTriggerNameDialog()
	{
		for (int i = 0; i < 4; i++){
			PixelPet pet = appState.getActivePets()[i];
			if (pet == null) continue;
			if (pet.JustEvolved){
				if (!appState.notifications[i] && appState.getSettings().NotifyUser){
					appState.NotifyOfPetFormChange(pet, i);
					appState.SaveUser();
				}
			}
		}
	}
	
	public void HandleAndDrawPet(){
		NotifyUserTriggerNameDialog();
		UpdateStatistics();
		
		_petView.setImageResource(_activePet.GetDrawableId(false));
		
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
		_petView.scrollTo((_activePet.CurrFrame + _activePet.RelAniX)*size, _activePet.RelAniY * size);
	}
	
	private void UpdateStatistics(){
		if (_activePet.CurrentForm == PixelPet.PetForm.Egg){
			((TextView)findViewById(R.id.pet_name)).setText("???");
			((TextView)findViewById(R.id.pet_species)).setText("Mysterious Egg");
			((TextView)findViewById(R.id.pet_level)).setText("Lvl. "+_activePet.Level);
			((TextView)findViewById(R.id.date_hatched)).setText("???");
			((TextView)findViewById(R.id.primary_type)).setText("???");
			((TextView)findViewById(R.id.primary_type)).setBackgroundColor(getResources().getColor(android.R.color.transparent));
			((TextView)findViewById(R.id.comma)).setText("");
			((TextView)findViewById(R.id.secondary_type)).setText("");
			((TextView)findViewById(R.id.pet_exp)).setText("???");
			((TextView)findViewById(R.id.pet_exp_tolevel)).setText("???");
			((TextView)findViewById(R.id.pet_hunger)).setText("???");
			((TextView)findViewById(R.id.pet_ambition)).setText("???");
			((TextView)findViewById(R.id.pet_empathy)).setText("???");
			((TextView)findViewById(R.id.pet_insight)).setText("???");
			((TextView)findViewById(R.id.pet_diligence)).setText("???");
			((TextView)findViewById(R.id.pet_charm)).setText("???");
		}
		else{
			//Set all the Text Areas to accurately reflect Active Pet Attributes
			((TextView)findViewById(R.id.pet_name)).setText(_activePet.Name);
			((TextView)findViewById(R.id.pet_species)).setText(_activePet.GetSpeciesAndGender());
			((TextView)findViewById(R.id.pet_level)).setText("Lvl. "+_activePet.Level);
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
			String dateHatched = dateFormat.format(new Date(_activePet.TimeEggHatched));
			((TextView)findViewById(R.id.date_hatched)).setText(dateHatched);
			((TextView)findViewById(R.id.primary_type)).setText(_activePet.PrimaryType.toString());
			if (_activePet.PrimaryType == PixelPet.BattleType.Dark || _activePet.PrimaryType == PixelPet.BattleType.Poison)
				((TextView)findViewById(R.id.primary_type)).setTextColor(getResources().getColor(R.color.white));
			((TextView)findViewById(R.id.primary_type)).setBackgroundColor(_activePet.GetBackgroundColor(_activePet.PrimaryType));
			if (_activePet.SecondaryType != BattleType.None && _activePet.SecondaryType != null){
				((TextView)findViewById(R.id.comma)).setText(",");
				((TextView)findViewById(R.id.secondary_type)).setText(_activePet.SecondaryType.toString());
				if (_activePet.SecondaryType == PixelPet.BattleType.Dark || _activePet.PrimaryType == PixelPet.BattleType.Poison)
					((TextView)findViewById(R.id.secondary_type)).setTextColor(getResources().getColor(R.color.white));
				((TextView)findViewById(R.id.secondary_type)).setBackgroundColor(_activePet.GetBackgroundColor(_activePet.SecondaryType));
			}else{
				((TextView)findViewById(R.id.comma)).setText("");
				((TextView)findViewById(R.id.secondary_type)).setText("");
			}
			((TextView)findViewById(R.id.pet_exp)).setText(Integer.toString(_activePet.Exp)+" EXP");
			if (_activePet.Level < _activePet.MaxLevel)
				((TextView)findViewById(R.id.pet_exp_tolevel)).setText(Integer.toString(_activePet.ExpToNextLevel)+" EXP");
			else ((TextView)findViewById(R.id.pet_exp_tolevel)).setText("Max Level");
			((TextView)findViewById(R.id.pet_hunger)).setText(_activePet.GetHungerString());
			
			((TextView)findViewById(R.id.pet_ambition)).setText(_activePet.GetAmbition());
			((TextView)findViewById(R.id.pet_empathy)).setText(_activePet.GetEmpathy());
			((TextView)findViewById(R.id.pet_insight)).setText(_activePet.GetInsight());
			((TextView)findViewById(R.id.pet_diligence)).setText(_activePet.GetDiligence());
			((TextView)findViewById(R.id.pet_charm)).setText(_activePet.GetCharm());
		}
	}
	
	public void BackToMenu(View view)
	{
		finish();
	}
}
