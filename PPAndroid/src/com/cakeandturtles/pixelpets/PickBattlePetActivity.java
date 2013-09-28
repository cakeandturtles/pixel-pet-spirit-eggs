package com.cakeandturtles.pixelpets;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.example.pixelpets.R;

public class PickBattlePetActivity extends Activity {
	private PPApp appState;
	private int currIndex;
	private int lastIndex;
	private PixelPet[] _activePets;
	private Handler handler;
	private Runnable runnable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pick_battle_pet);
		
		appState = ((PPApp)getApplicationContext());
		currIndex = appState.tempIndex;
		lastIndex = currIndex;
		_activePets = appState.getActivePets();
		HandleAndDrawPets();
		UpdateTextDisplay();
		UpdateSeperators();
		
		View[] petBoxes = new View[]{ findViewById(R.id.pet_box1), findViewById(R.id.pet_box2), findViewById(R.id.pet_box3), findViewById(R.id.pet_box4)};
		
		for (int i = 0; i < 4; i++){
			if (_activePets[i] != null){
				_activePets[i].CurrFrame = 0;
				_activePets[i].FrameCount = 0;
				if (_activePets[i].HP <= 0){
					((LinearLayout)petBoxes[i]).setBackgroundColor(Color.parseColor("#66ff0000"));
				}
				
				petBoxes[i].setOnTouchListener(new OnTouchListener(){
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if(event.getAction() == MotionEvent.ACTION_DOWN) {
							v.setBackgroundColor(getResources().getColor(R.color.lightblue));
							return true;
						}else if (event.getAction() == MotionEvent.ACTION_UP) {
							v.setBackgroundResource(0);
							if (v.getId() == R.id.pet_box1)
								currIndex = 0;
							else if (v.getId() == R.id.pet_box2)
								currIndex = 1;
							else if (v.getId() == R.id.pet_box3)
								currIndex = 2;
							else if (v.getId() == R.id.pet_box4)
								currIndex = 3;
							ClickOnBox(v);
							return true;
						} else if (event.getAction() == MotionEvent.ACTION_CANCEL){
							ResetColor(v);
							return true;
						}
						return false;
					}
				});
			}
			else{
				petBoxes[i].setVisibility(View.GONE);
				petBoxes[i].setOnClickListener(null);
			}
		}
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		appState.tempIndex = lastIndex;
		currIndex = lastIndex;
		
		runnable = new Runnable(){
			@Override
			public void run(){
				HandleAndDrawPets();
				for (int i = 0; i < 4; i++){
					if (_activePets[i] != null)
						_activePets[i].UpdateAnimation();
				}
				handler.postDelayed(this, 60);
			}
		};
		handler = new Handler();
		handler.postDelayed(runnable, 60);
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		handler.removeCallbacks(runnable);
		handler = null;
		runnable = null;
		appState.SaveUser();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pick_battle_pet, menu);
		return true;
	}
	
	@Override
	public void onBackPressed(){
		BackToBattle(null);
	}

	public void BackToBattle(View view)
	{
		PixelPet pet = appState.getActivePets()[appState.tempIndex];
		if (pet.HP <= 0){
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Out of Energy");
			dialog.setMessage("You must select a pet to fight!");
			dialog.setNegativeButton("Ok",  new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.cancel();
				}
			});
			dialog.show();
		}
		else
			finish();
	}

	public void ClickOnBox(View view)
	{
		ResetColor(view);
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Pick an option");
		dialog.setItems(new String[]{ "Switch", "View Stats" }, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				switch (which){
					case 0:
						Switch();
						break;
					case 1:
						ViewStats();
						break;
					default: break;
				}
			}
		});
		dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		
		dialog.create().show();
	}
	
	public void ResetColor(View v){
		if (v.getId() == R.id.pet_box1){
			if (_activePets[0] != null && _activePets[0].HP <= 0)
				v.setBackgroundColor(Color.parseColor("#66ff0000"));
			else
				v.setBackgroundResource(0);
		}else if (v.getId() == R.id.pet_box2){
			if (_activePets[1] != null && _activePets[1].HP <= 0)
				v.setBackgroundColor(Color.parseColor("#66ff0000"));
			else
				v.setBackgroundResource(0);
		}else if (v.getId() == R.id.pet_box3){
			if (_activePets[2] != null && _activePets[2].HP <= 0)
				v.setBackgroundColor(Color.parseColor("#66ff0000"));
			else
				v.setBackgroundResource(0);
		}
		else if (v.getId() == R.id.pet_box4){
			if (_activePets[3] != null && _activePets[3].HP <= 0)
				v.setBackgroundColor(Color.parseColor("#66ff0000"));
			else
				v.setBackgroundResource(0);
		}
	}
	
	public void ViewStats()
	{
		appState.tempIndex = currIndex;
		Intent intent = new Intent(this, ViewStatsActivity.class);
		startActivity(intent);
	}
	
	public void Switch()
	{
		PixelPet pet = appState.getActivePets()[currIndex];
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setNegativeButton("Ok",  new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		
		if (pet.CurrentForm == PixelPet.PetForm.Egg){
			dialog.setTitle("Unhatched Egg");
			dialog.setMessage("This egg has not hatched and cannot fight!");
			dialog.show();
		}else if (pet.HP <= 0){
			dialog.setTitle("Out of Energy");
			dialog.setMessage("This pet is out of energy and cannot battle!");
			dialog.show();
		}else if (lastIndex == currIndex){
			dialog.setTitle("Already in Battle");
			dialog.setMessage("This pet is already fighting!");
			dialog.show();
		}
		else{
			appState.getTempActivePet().ResetBattleStats(false, false, true, true, false);
			appState.tempIndex = currIndex;
			finish();
		}
	}
	
	private void UpdateTextDisplay()
	{
		TextView[] petNames = new TextView[]{ (TextView)findViewById(R.id.pet_name1), (TextView)findViewById(R.id.pet_name2), (TextView)findViewById(R.id.pet_name3), (TextView)findViewById(R.id.pet_name4)};
		TextView[] petSpecies = new TextView[]{ (TextView)findViewById(R.id.pet_species1), (TextView)findViewById(R.id.pet_species2), (TextView)findViewById(R.id.pet_species3), (TextView)findViewById(R.id.pet_species4)};
		TextView[] petLevels = new TextView[]{ (TextView)findViewById(R.id.pet_level1), (TextView)findViewById(R.id.pet_level2), (TextView)findViewById(R.id.pet_level3), (TextView)findViewById(R.id.pet_level4)};
		TextView[] petHP = new TextView[]{ (TextView)findViewById(R.id.pet_hp1), (TextView)findViewById(R.id.pet_hp2), (TextView)findViewById(R.id.pet_hp3), (TextView)findViewById(R.id.pet_hp4)};
		
		for (int i = 0; i < 4; i++){
			if (_activePets[i] != null){
				if (_activePets[i].CurrentForm != PixelPet.PetForm.Egg){
					petNames[i].setText(_activePets[i].Name);
					petSpecies[i].setText(_activePets[i].GetSpeciesAndGender());
				}
				petLevels[i].setText("Lvl. "+_activePets[i].Level);
				petHP[i].setText(_activePets[i].HP + "/" + _activePets[i].BaseHP);
			}
		}
	}
	
	private void UpdateSeperators()
	{
		View[] seperators = new View[]{ findViewById(R.id.seperator1), findViewById(R.id.seperator2), findViewById(R.id.seperator3), findViewById(R.id.seperator4) };
		for (int i = 0; i < 4; i++){
			if (_activePets[i] == null){
				seperators[i].setVisibility(View.GONE);
			}
		}
	}
	
	private void HandleAndDrawPets()
	{		
		View[] petHP = new View[] { (View)findViewById(R.id.pet_hp_bar1), (View)findViewById(R.id.pet_hp_bar2), (View)findViewById(R.id.pet_hp_bar3), (View)findViewById(R.id.pet_hp_bar4) };
		View[] petHPRed = new View[] { (View)findViewById(R.id.stat_red_hp_bar1), (View)findViewById(R.id.stat_red_hp_bar2), (View)findViewById(R.id.stat_red_hp_bar3), (View)findViewById(R.id.stat_red_hp_bar4) };
		for (int i = 0; i < 4; i++){
			if (_activePets[i] == null) continue;
			
			int width = petHPRed[i].getMeasuredWidth();
			RelativeLayout.LayoutParams petHPParams = (RelativeLayout.LayoutParams)petHP[i].getLayoutParams();
			petHPParams.width = (int)(width * ((float)_activePets[i].HP / (float)_activePets[i].BaseHP));
			petHP[i].setLayoutParams(petHPParams);
		}
		
		ImageView[] petImages = new ImageView[]{ (ImageView)findViewById(R.id.pet_area1), (ImageView)findViewById(R.id.pet_area2), (ImageView)findViewById(R.id.pet_area3), (ImageView)findViewById(R.id.pet_area4)};
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, r.getDisplayMetrics());
		
		for (int i = 0; i < 4; i++){
			if (_activePets[i] == null) continue;
				
			petImages[i].setImageResource(_activePets[i].GetDrawableId(true));
			petImages[i].scrollTo((_activePets[i].CurrFrame + _activePets[i].RelAniX) * size, _activePets[i].RelAniY * size);
		}
	}
}
