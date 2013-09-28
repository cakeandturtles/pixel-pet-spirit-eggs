package com.cakeandturtles.pixelpets;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cakeandturtles.pixelpets.attacks.Attack;
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
	
	public void ViewMove1(View view){
		Attack attack = _activePet.Attacks[0];
		ViewAttack(attack, "Pet Attack");
	}
	
	public void ViewMove2(View view){
		Attack attack = _activePet.Attacks[1];
		ViewAttack(attack, "Pet Attack");
	}
	
	public void ViewMove3(View view){
		Attack attack = _activePet.Attacks[2];
		if (attack != null)
			ViewAttack(attack, "Pet Attack");
	}
	
	public void ViewMove4(View view){
		Attack attack = _activePet.Attacks[3];
		if (attack != null)
			ViewAttack(attack, "Pet Attack");
	}
	
	public void ViewAttack(Attack attack, String title){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(title + "\n(Type: " + attack.AttackType.toString() + ")");
		
		String message = "<b>" + attack.Name + ":</b>";
		message += "<br/><i>\"" + attack.Description;
		message += "\"</i><br/><br/>&nbsp;<b>Power:</b> &nbsp;";
		if (attack.BasePower > 0) message += attack.BasePower;
		else if (attack.BasePower == 0) message += "---";
		else message += "???";
		//message += "\t\t<b>Accuracy:</b> &nbsp;" + attack.Accuracy;
		message += "<br/><b># Uses:</b> &nbsp;" + attack.NumUses + "/" + attack.BaseNumUses;
		alert.setMessage(Html.fromHtml(message));
		
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		alert.show();
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
		
		int width = ((View)findViewById(R.id.stat_red_hp_bar)).getMeasuredWidth();
		View petHP = (View)findViewById(R.id.pet_hp_bar);
		RelativeLayout.LayoutParams petHPParams = (RelativeLayout.LayoutParams)petHP.getLayoutParams();
		petHPParams.width = (int)(width * ((float)_activePet.HP / (float)_activePet.BaseHP));
		petHP.setLayoutParams(petHPParams);
		((TextView)findViewById(R.id.pet_hp)).setText(_activePet.HP + "/" + _activePet.BaseHP);
		
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
			((TextView)findViewById(R.id.pet_hp)).setText("???");
			((TextView)findViewById(R.id.pet_attack)).setText("???");
			((TextView)findViewById(R.id.pet_speed)).setText("???");
			((TextView)findViewById(R.id.pet_defense)).setText("???");
			((Button)findViewById(R.id.pet_move1)).setText("--------");
			((Button)findViewById(R.id.pet_move1)).setClickable(false);
			((Button)findViewById(R.id.pet_move1)).getBackground().clearColorFilter();
			((Button)findViewById(R.id.pet_move1)).setTextSize(18);
			((Button)findViewById(R.id.pet_move2)).setText("--------");
			((Button)findViewById(R.id.pet_move2)).setClickable(false);
			((Button)findViewById(R.id.pet_move2)).getBackground().clearColorFilter();
			((Button)findViewById(R.id.pet_move2)).setTextSize(18);
			((Button)findViewById(R.id.pet_move3)).setText("--------");
			((Button)findViewById(R.id.pet_move3)).setClickable(false);
			((Button)findViewById(R.id.pet_move3)).getBackground().clearColorFilter();
			((Button)findViewById(R.id.pet_move3)).setTextSize(18);
			((Button)findViewById(R.id.pet_move4)).setText("--------");
			((Button)findViewById(R.id.pet_move4)).setClickable(false);
			((Button)findViewById(R.id.pet_move4)).getBackground().clearColorFilter();
			((Button)findViewById(R.id.pet_move4)).setTextSize(18);
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
			
			((TextView)findViewById(R.id.pet_attack)).setText(Integer.toString(_activePet.BaseAttack));
			((TextView)findViewById(R.id.pet_speed)).setText(Integer.toString(_activePet.BaseSpeed));
			((TextView)findViewById(R.id.pet_defense)).setText(Integer.toString(_activePet.BaseDefense));
			((Button)findViewById(R.id.pet_move1)).setText(Html.fromHtml(_activePet.Attacks[0].Name + "<br/><small>" + _activePet.Attacks[0].NumUses + "/" + _activePet.Attacks[0].BaseNumUses + "</small>"));
			((Button)findViewById(R.id.pet_move1)).getBackground().setColorFilter(new LightingColorFilter(0xFF000000, _activePet.Attacks[0].GetBackgroundColor()));
			((Button)findViewById(R.id.pet_move1)).setClickable(true);
			((Button)findViewById(R.id.pet_move1)).setTextSize(16);
			if (_activePet.Attacks[1] == null){
				((Button)findViewById(R.id.pet_move2)).setText("--------");
				((Button)findViewById(R.id.pet_move2)).setClickable(false);
				((Button)findViewById(R.id.pet_move2)).getBackground().clearColorFilter();
				((Button)findViewById(R.id.pet_move2)).setTextSize(18);
			}
			else{
				((Button)findViewById(R.id.pet_move2)).setText(Html.fromHtml(_activePet.Attacks[1].Name + "<br/><small>" + _activePet.Attacks[1].NumUses + "/" + _activePet.Attacks[1].BaseNumUses + "</small>"));
				((Button)findViewById(R.id.pet_move2)).getBackground().setColorFilter(new LightingColorFilter(0xFF000000, _activePet.Attacks[1].GetBackgroundColor()));
				((Button)findViewById(R.id.pet_move2)).setClickable(true);
				((Button)findViewById(R.id.pet_move2)).setTextSize(16);
			}
			if (_activePet.Attacks[2] == null){
				((Button)findViewById(R.id.pet_move3)).setText("--------");
				((Button)findViewById(R.id.pet_move3)).setClickable(false);
				((Button)findViewById(R.id.pet_move3)).getBackground().clearColorFilter();
				((Button)findViewById(R.id.pet_move3)).setTextSize(18);
			}
			else{
				((Button)findViewById(R.id.pet_move3)).setText(Html.fromHtml(_activePet.Attacks[2].Name + "<br/><small>" + _activePet.Attacks[2].NumUses + "/" + _activePet.Attacks[2].BaseNumUses + "</small>"));
				((Button)findViewById(R.id.pet_move3)).getBackground().setColorFilter(new LightingColorFilter(0xFF000000, _activePet.Attacks[2].GetBackgroundColor()));
				((Button)findViewById(R.id.pet_move3)).setClickable(true);
				((Button)findViewById(R.id.pet_move3)).setTextSize(16);
			}
			if (_activePet.Attacks[3] == null){
				((Button)findViewById(R.id.pet_move4)).setText("--------");
				((Button)findViewById(R.id.pet_move4)).setClickable(false);
				((Button)findViewById(R.id.pet_move4)).getBackground().clearColorFilter();
				((Button)findViewById(R.id.pet_move4)).setTextSize(18);
			}
			else{
				((Button)findViewById(R.id.pet_move4)).setText(Html.fromHtml(_activePet.Attacks[3].Name + "<br/><small>" + _activePet.Attacks[3].NumUses + "/" + _activePet.Attacks[3].BaseNumUses + "</small>"));
				((Button)findViewById(R.id.pet_move4)).getBackground().setColorFilter(new LightingColorFilter(0xFF000000, _activePet.Attacks[3].GetBackgroundColor()));
				((Button)findViewById(R.id.pet_move4)).setClickable(true);
				((Button)findViewById(R.id.pet_move4)).setTextSize(16);
			}
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
