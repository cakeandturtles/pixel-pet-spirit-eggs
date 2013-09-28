package com.cakeandturtles.pixelpets;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.BattleType;
import com.example.pixelpets.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.LightingColorFilter;
import android.text.Html;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LearnNewMoveActivity extends Activity {
	private PPApp appState;
	private PixelPet _activePet;
	private Attack newAttack;
	private int petIndex;
	private ImageView _petView;
	private Handler handler;
	private Runnable runnable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn_new_move);
		
		appState = ((PPApp)getApplicationContext());
		_activePet = appState.getTempActivePet();
		newAttack = appState.learnNewAttack;
		petIndex = appState.tempIndex;
		InitializeViews();
		
		Bundle extras = getIntent().getExtras();
		if (extras != null){
			petIndex = extras.getInt("com.cakeandturtles.pixelpets.petIndex");
			_activePet = appState.getActivePets()[petIndex];
		}
		
		UpdateStatistics();
		HandleAndDrawPet();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.learn_new_move, menu);
		return true;
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
				if (appState.MyAdventures.InBattle)
					_activePet.UpdateAnimation();
				else{
					for (int i = 0; i < 4; i++){
						PixelPet pet = appState.getActivePets()[i];
						if (pet == null) continue;
						if (pet == _activePet) pet.Update();
						else pet.UpdatePetForm();
					}
				}
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
		appState.StartRunAndHandle();
		appState.SaveUser();
	}
	
	@Override
	public void onBackPressed()
	{
		BackButton(null);
	}
	
	public void BackButton(View view){
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Forget New Attack?");
		alert.setMessage("Do you wish to not learn the new Attack");
		alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
				LearnNewMoveActivity.super.onBackPressed();
			}
		});
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		alert.show();
	}
	
	private void InitializeViews(){
		_petView = (ImageView)findViewById(R.id.pet_image_area);
	}
	
	private void NullifyViews(){
		_petView = null;
	}
	
	public void TapPet(View view)
	{
		_activePet.TapPet();
		HandleAndDrawPet();
	}
	
	public void ViewMove0(View view){
		Attack attack = newAttack;
		ViewAttack(attack, "New Pet Attack", false, -1);
	}
	
	public void ViewMove1(View view){
		Attack attack = _activePet.Attacks[0];
		ViewAttack(attack, "Pet Attack", true, 0);
	}
	
	public void ViewMove2(View view){
		Attack attack = _activePet.Attacks[1];
		ViewAttack(attack, "Pet Attack", true, 1);
	}
	
	public void ViewMove3(View view){
		Attack attack = _activePet.Attacks[2];
		if (attack != null)
			ViewAttack(attack, "Pet Attack", true, 2);
	}
	
	public void ViewMove4(View view){
		Attack attack = _activePet.Attacks[3];
		if (attack != null)
			ViewAttack(attack, "Pet Attack", true, 3);
	}
	
	public void ViewAttack(Attack attack, String title, boolean canDeleteMove, final int index){
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
		
		if (!canDeleteMove){
			message += "<br/><br/>Do you want to stop trying to learn " + newAttack.Name + "?";
			alert.setMessage(Html.fromHtml(message));
			alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.cancel();
					LearnNewMoveActivity.this.finish();
				}
			});
		}else{
			message += "<br/><br/>Do you want to forget " + attack.Name + " in order to learn " + newAttack.Name + "?";
			alert.setMessage(Html.fromHtml(message));
			alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					_activePet.Attacks[index] = newAttack;
					dialog.cancel();
					LearnNewMoveActivity.this.finish();
				}
			});
		}
	
		alert.setNegativeButton("No", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		alert.show();
	}
	
	private void UpdateStatistics(){
			((TextView)findViewById(R.id.attack_text_area)).setText("Which attack do you wish to\nforget to learn "+newAttack.Name);
		
			//Set all the Text Areas to accurately reflect Active Pet Attributes
			((TextView)findViewById(R.id.pet_name)).setText(_activePet.Name);
			((TextView)findViewById(R.id.pet_species)).setText(_activePet.GetSpeciesAndGender());
			((TextView)findViewById(R.id.pet_level)).setText("Lvl. "+_activePet.Level);
			((TextView)findViewById(R.id.primary_type)).setText(_activePet.PrimaryType.toString());
			((TextView)findViewById(R.id.primary_type)).setBackgroundColor(_activePet.GetBackgroundColor(_activePet.PrimaryType));
			if (_activePet.SecondaryType != BattleType.None && _activePet.SecondaryType != null){
				((TextView)findViewById(R.id.comma)).setText(",");
				((TextView)findViewById(R.id.secondary_type)).setText(_activePet.SecondaryType.toString());
				((TextView)findViewById(R.id.secondary_type)).setBackgroundColor(_activePet.GetBackgroundColor(_activePet.SecondaryType));
			}else{
				((TextView)findViewById(R.id.comma)).setText("");
				((TextView)findViewById(R.id.secondary_type)).setText("");
			}
			
			((Button)findViewById(R.id.pet_move0)).setText(Html.fromHtml(newAttack.Name + "<br/><small>" + newAttack.NumUses + "/" + newAttack.BaseNumUses + "</small>"));
			((Button)findViewById(R.id.pet_move0)).getBackground().setColorFilter(new LightingColorFilter(0xFF000000, newAttack.GetBackgroundColor()));
			((Button)findViewById(R.id.pet_move0)).setClickable(true);
			
			((Button)findViewById(R.id.pet_move1)).setText(Html.fromHtml(_activePet.Attacks[0].Name + "<br/><small>" + _activePet.Attacks[0].NumUses + "/" + _activePet.Attacks[0].BaseNumUses + "</small>"));
			((Button)findViewById(R.id.pet_move1)).getBackground().setColorFilter(new LightingColorFilter(0xFF000000, _activePet.Attacks[0].GetBackgroundColor()));
			((Button)findViewById(R.id.pet_move1)).setClickable(true);
			if (_activePet.Attacks[1] == null){
				((Button)findViewById(R.id.pet_move2)).setText("--------");
				((Button)findViewById(R.id.pet_move2)).setClickable(false);
				((Button)findViewById(R.id.pet_move2)).getBackground().clearColorFilter();
			}
			else{
				((Button)findViewById(R.id.pet_move2)).setText(Html.fromHtml(_activePet.Attacks[1].Name + "<br/><small>" + _activePet.Attacks[1].NumUses + "/" + _activePet.Attacks[1].BaseNumUses + "</small>"));
				((Button)findViewById(R.id.pet_move2)).getBackground().setColorFilter(new LightingColorFilter(0xFF000000, _activePet.Attacks[1].GetBackgroundColor()));
				((Button)findViewById(R.id.pet_move2)).setClickable(true);
			}
			if (_activePet.Attacks[2] == null){
				((Button)findViewById(R.id.pet_move3)).setText("--------");
				((Button)findViewById(R.id.pet_move3)).setClickable(false);
				((Button)findViewById(R.id.pet_move3)).getBackground().clearColorFilter();
			}
			else{
				((Button)findViewById(R.id.pet_move3)).setText(Html.fromHtml(_activePet.Attacks[2].Name + "<br/><small>" + _activePet.Attacks[2].NumUses + "/" + _activePet.Attacks[2].BaseNumUses + "</small>"));
				((Button)findViewById(R.id.pet_move3)).getBackground().setColorFilter(new LightingColorFilter(0xFF000000, _activePet.Attacks[2].GetBackgroundColor()));
				((Button)findViewById(R.id.pet_move3)).setClickable(true);
			}
			if (_activePet.Attacks[3] == null){
				((Button)findViewById(R.id.pet_move4)).setText("--------");
				((Button)findViewById(R.id.pet_move4)).setClickable(false);
				((Button)findViewById(R.id.pet_move4)).getBackground().clearColorFilter();
			}
			else{
				((Button)findViewById(R.id.pet_move4)).setText(Html.fromHtml(_activePet.Attacks[3].Name + "<br/><small>" + _activePet.Attacks[3].NumUses + "/" + _activePet.Attacks[3].BaseNumUses + "</small>"));
				((Button)findViewById(R.id.pet_move4)).getBackground().setColorFilter(new LightingColorFilter(0xFF000000, _activePet.Attacks[3].GetBackgroundColor()));
				((Button)findViewById(R.id.pet_move4)).setClickable(true);
			}
	}
	
	public void HandleAndDrawPet(){
		UpdateStatistics();
		
		_petView.setImageResource(_activePet.GetDrawableId(false));
		
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
		_petView.scrollTo((_activePet.CurrFrame + _activePet.RelAniX)*size, _activePet.RelAniY * size);
	}
}
