package com.cakeandturtles.pixelpets;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cakeandturtles.pixelpets.managers.Settings;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.example.pixelpets.R;

public class SwapPartyEggActivity extends Activity {
	private PPApp appState;
	private PixelPet eggSwap;
	private PixelPet[] _activePets;
	private int TempIndexRemember;
	private boolean release;
	private Handler handler;
	private Runnable runnable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swap_party_egg);
		
		release = false;
		appState = ((PPApp)getApplicationContext());
		TempIndexRemember = appState.tempIndex;
		eggSwap = appState.tempEggSwapHolder;
		_activePets = appState.getActivePets();
		HandleAndDrawPets();
		UpdateTextDisplay();
		UpdateSeperators();
		
		View[] petBoxes = new View[]{ findViewById(R.id.pet_box1), findViewById(R.id.pet_box2), findViewById(R.id.pet_box3), findViewById(R.id.pet_box4)};
		
		for (int i = 0; i < 4; i++){
			if (_activePets[i] != null){
				_activePets[i].CurrFrame = 0;
				_activePets[i].FrameCount = 0;
			
				petBoxes[i].setOnTouchListener(new OnTouchListener(){
					@Override
					public boolean onTouch(View v, MotionEvent event) {						
						if(event.getAction() == MotionEvent.ACTION_DOWN) {
							v.setBackgroundColor(getResources().getColor(R.color.lightblue));
							return true;
						}else if (event.getAction() == MotionEvent.ACTION_UP) {
							v.setBackgroundResource(0);
							if (v.getId() == R.id.pet_box1)
								appState.tempIndex = 0;
							else if (v.getId() == R.id.pet_box2)
								appState.tempIndex = 1;
							else if (v.getId() == R.id.pet_box3)
								appState.tempIndex = 2;
							else if (v.getId() == R.id.pet_box4)
								appState.tempIndex = 3;
							ClickOnBox(v);
							return true;
						} else if (event.getAction() == MotionEvent.ACTION_CANCEL){
							ResetColor(v);
							return true;
						}
						return false;
					}
				});
			}else{
				petBoxes[i].setVisibility(View.GONE);
				petBoxes[i].setOnClickListener(null);
			}
		}
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		Bundle extras = getIntent().getExtras();
		if (extras != null){
			release = extras.getBoolean("com.cakeandturtles.pixelpets.release");
			getIntent().removeExtra("com.cakeandturtles.pixelpets.release");
		}
		if (!release){
			((Button)findViewById(R.id.all_pets_button)).setVisibility(View.GONE);
			((Button)findViewById(R.id.all_pets_button)).setClickable(false);
			
			LinearLayout.LayoutParams layout = (LinearLayout.LayoutParams)((Button)findViewById(R.id.back_to_menu)).getLayoutParams();
			layout.weight = 1.0f;
			((Button)findViewById(R.id.back_to_menu)).setLayoutParams(layout);
			((Button)findViewById(R.id.back_to_menu)).setText("Back to Map");
		}else{
			setTitle("Release for Egg?");
			if (appState.finishedReleaseForEgg){
				appState.finishedReleaseForEgg = false;
				appState.tempEggSwapHolder = null;
				finish();
			}
		}
		
		runnable = new Runnable(){
			@Override
			public void run(){
				appState.ClearNotifications(true, null);
				UpdateTextDisplay();
				HandleAndDrawPets();
				for (int i = 0; i < 4; i++){
					if (_activePets[i] != null){
						_activePets[i].UpdateAnimation();
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
		handler.removeCallbacks(runnable);
		handler = null;
		runnable = null;
		appState.StartRunAndHandle();
		appState.SaveUser();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.use_item, menu);
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
	public void onBackPressed(){
		BackToPrevious(null);
	}
	
	public void AllPets(View view)
	{
		if (release){
			Intent intent = new Intent(this, DaycareActivity.class);
			intent.putExtra("com.cakeandturtles.pixelpets.release", true);
			startActivity(intent);
		}
	}

	public void BackToPrevious(View view)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Nevermind?");
		if (!release){
			dialog.setMessage("Just put egg in the Daycare instead?");
			dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					appState.TheDaycare.AddToPetStorage(eggSwap);
					dialog.cancel();
					appState.tempIndex = TempIndexRemember;
					appState.tempEggSwapHolder = null;
					SwapPartyEggActivity.super.onBackPressed();
				}
			});
		}else{
			dialog.setMessage("Just leave the egg instead?");
			dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					appState.tempEggSwapHolder = null;
					SwapPartyEggActivity.super.onBackPressed();
				}
			});
		}
		dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		dialog.create().show();
	}

	public void ClickOnBox(final View view)
	{
		ResetColor(view);
		
		final PixelPet tempPet = appState.getTempActivePet();
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		if (!release){
			dialog.setTitle("Swap " + tempPet.Name + "?");
			dialog.setMessage("Put " + tempPet.Name + " in the daycare so that the Mysterious Egg may take the place?");
			dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					appState.setActivePet(eggSwap, appState.tempIndex);
					appState.TheDaycare.AddToPetStorage(tempPet);
					dialog.cancel();
					SwapPartyEggActivity.this.finish();
					appState.MyAdventures.NewEggIndex = appState.tempIndex;
					appState.tempEggSwapHolder = null;
				}
			});
		}else{
			if (tempPet.CurrentForm != PixelPet.PetForm.Egg){
				dialog.setTitle("Release " + tempPet.Name + "?");
				dialog.setMessage("Release " + tempPet.Name + " into the wild so that the Mysterious Egg may take the place?\n\nWARNING: This is permanent and you cannot get " + tempPet.Name + " back!");
			}else{
				dialog.setTitle("Release Mysterious Egg?");
				dialog.setMessage("Release Mysterious Egg into the wild so that the Mysterious Egg may take the place?\n\nWARNING: This is permanent and you cannot get Mysterious Egg back!");
			}
			dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					appState.setActivePet(eggSwap, appState.tempIndex);
					dialog.cancel();
					SwapPartyEggActivity.this.finish();
					appState.MyAdventures.NewEggIndex = appState.tempIndex;
					appState.tempEggSwapHolder = null;
				}
			});
		}
		dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		dialog.create().show();
	}
	
	public void ResetColor(View v){
		if (v.getId() == R.id.pet_box1){
			v.setBackgroundResource(0);
		}else if (v.getId() == R.id.pet_box2){
			v.setBackgroundResource(0);
		}else if (v.getId() == R.id.pet_box3){
			v.setBackgroundResource(0);
		}else if (v.getId() == R.id.pet_box4){
			v.setBackgroundResource(0);
		}
	}
	
	private void UpdateTextDisplay()
	{
		TextView[] petNames = new TextView[]{ (TextView)findViewById(R.id.pet_name1), (TextView)findViewById(R.id.pet_name2), (TextView)findViewById(R.id.pet_name3), (TextView)findViewById(R.id.pet_name4)};
		TextView[] petSpecies = new TextView[]{ (TextView)findViewById(R.id.pet_species1), (TextView)findViewById(R.id.pet_species2), (TextView)findViewById(R.id.pet_species3), (TextView)findViewById(R.id.pet_species4)};
		TextView[] petLevels = new TextView[]{ (TextView)findViewById(R.id.pet_level1), (TextView)findViewById(R.id.pet_level2), (TextView)findViewById(R.id.pet_level3), (TextView)findViewById(R.id.pet_level4)};
		
		for (int i = 0; i < 4; i++){
			if (_activePets[i] != null){
				if (_activePets[i].CurrentForm != PixelPet.PetForm.Egg){
					petNames[i].setText(_activePets[i].Name);
					petSpecies[i].setText(_activePets[i].GetSpeciesAndGender());
				}
				petLevels[i].setText("Lvl. "+_activePets[i].Level);
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
		ImageView[] petImages = new ImageView[]{ (ImageView)findViewById(R.id.pet_area1), (ImageView)findViewById(R.id.pet_area2), (ImageView)findViewById(R.id.pet_area3), (ImageView)findViewById(R.id.pet_area4)};
		
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, r.getDisplayMetrics());
		
		for (int i = 0; i < 4; i++){
			if (_activePets[i] == null) continue;
				
			petImages[i].setImageResource(_activePets[i].GetDrawableId(true));
			if (_activePets[i].CurrentForm == PixelPet.PetForm.Primary){
				if (_activePets[i].JustEvolved){
					appState.NameYourPet(_activePets[i], null, this);
					if (appState.getSettings().NotifyUser)
						appState.NotifyOfPetFormChange(_activePets[i], i);
					_activePets[i].JustEvolved = false;
				}
			}else if (_activePets[i].CurrentForm != PixelPet.PetForm.Primary){
				if (_activePets[i].JustEvolved){
					appState.PetEvolved(i, this);
					if (appState.getSettings().NotifyUser)
						appState.NotifyOfPetFormChange(_activePets[i], i);
				}
			}
			
			petImages[i].scrollTo((_activePets[i].CurrFrame + _activePets[i].RelAniX)*size, _activePets[i].RelAniY * size);
		}
	}
}
