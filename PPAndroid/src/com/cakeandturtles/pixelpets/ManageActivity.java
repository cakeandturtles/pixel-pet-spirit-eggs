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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cakeandturtles.pixelpets.managers.Settings;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.example.pixelpets.R;

public class ManageActivity extends Activity {
	private PPApp appState;
	private PixelPet[] _activePets;
	private boolean isSelectingMove;
	private int moveIndex;
	private View moveView;
	private Handler handler;
	private Runnable runnable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage);
		
		appState = ((PPApp)getApplicationContext());
		_activePets = appState.getActivePets();
		isSelectingMove = false;
		moveIndex = -1;
		moveView = null;
		HandleAndDrawPets();
		UpdateTextDisplay();
		UpdateSeperators();
		UpdatePetBoxes();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		_activePets = appState.getActivePets();
		HandleAndDrawPets();
		UpdateTextDisplay();
		UpdateSeperators();
		UpdatePetBoxes();
		
		runnable = new Runnable(){
			@Override
			public void run(){
				appState.ClearNotifications(true, null);
				View[] petBoxes = new View[]{ findViewById(R.id.pet_box1), findViewById(R.id.pet_box2), findViewById(R.id.pet_box3), findViewById(R.id.pet_box4)};
				UpdateTextDisplay();
				UpdateSeperators();
				HandleAndDrawPets();
				for (int i = 0; i < 4; i++){
					if (_activePets[i] != null){
						_activePets[i].Update();
					
						petBoxes[i].setVisibility(View.VISIBLE);
						petBoxes[i].setClickable(true);
					}else{
						petBoxes[i].setVisibility(View.GONE);
						petBoxes[i].setClickable(false);
					}
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
		handler.removeCallbacks(runnable);
		handler = null;
		runnable = null;
		appState.StartRunAndHandle();
		appState.SaveUser();
		
		setTitle(getResources().getString(R.string.title_activity_manage));
		
		if (moveView != null)
			ResetColor(moveView);
		isSelectingMove = false;
		moveIndex = -1;
		moveView = null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage, menu);
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
	
	public void UpdatePetBoxes()
	{
		View[] petBoxes = new View[]{ findViewById(R.id.pet_box1), findViewById(R.id.pet_box2), findViewById(R.id.pet_box3), findViewById(R.id.pet_box4)};
		
		for (int i = 0; i < 4; i++){
			if (_activePets[i] != null){			
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

	public void BackToGarden(View view)
	{
		finish();
	}
	
	public void AllPets(View view)
	{
		Intent intent = new Intent(this, DaycareActivity.class);
		startActivity(intent);
	}
	
	public void Deposit()
	{
		appState.TryDeposit(appState.tempIndex, this);
	}
	
	public void Release()
	{
		appState.TryRelease(appState.tempIndex, this, false);
	}

	public void ClickOnBox(final View view)
	{
		PixelPet pet = appState.getTempActivePet();
		
		if (isSelectingMove){
			Switch();
		}
		else{
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle(pet.Name + " the " + pet.GetSpeciesAndGender());
			dialog.setItems(new String[]{ "View Stats", "Switch", "Rename", "Deposit", "Release"}, new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					switch (which){
					case 0:
							ViewStats();
							break;
						case 1:
							view.setBackgroundColor(getResources().getColor(R.color.lightblue));
							StartSwitch(view);
							break;
						case 2:
							appState.NameYourPet(_activePets[appState.tempIndex], view, ManageActivity.this);
							break;
						case 3:
							Deposit();
							break;
						case 4:
							Release();
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
	
	public void ViewStats()
	{
		Intent intent = new Intent(this, ViewStatsActivity.class);
		startActivity(intent);
	}
	
	public void StartSwitch(View view)
	{
		setTitle("Switch places?");
		isSelectingMove = true;
		moveIndex = appState.tempIndex;
		moveView = view;
	}
	
	public void Switch()
	{
		setTitle(getResources().getString(R.string.title_activity_manage));
		if (moveIndex != appState.tempIndex){
			PixelPet swapper = appState.getActivePets()[moveIndex];
			PixelPet swappee = appState.getTempActivePet();
			_activePets[moveIndex] = swappee;
			_activePets[appState.tempIndex] = swapper;
		}
		
		ResetColor(moveView);
		isSelectingMove = false;
		moveIndex = -1;
		moveView = null;
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
				}else{
					petNames[i].setText("???");
					petSpecies[i].setText("Mysterious Egg");
				}
				petLevels[i].setText("Lvl. "+_activePets[i].Level);
			}
		}
	}
	
	private void UpdateSeperators()
	{
		View[] seperators = new View[]{ findViewById(R.id.seperator1), findViewById(R.id.seperator2), findViewById(R.id.seperator3), findViewById(R.id.seperator4) };
		for (int i = 0; i < 4; i++){
			if (_activePets[i] == null)
				seperators[i].setVisibility(View.GONE);
			else seperators[i].setVisibility(View.VISIBLE);
		}
	}
	
	private void HandleAndDrawPets()
	{		
		TextView[] petHungerText = new TextView[]{ (TextView)findViewById(R.id.pet_hunger1), (TextView)findViewById(R.id.pet_hunger1), (TextView)findViewById(R.id.pet_hunger1), (TextView)findViewById(R.id.pet_hunger1)};
		View[] petHPBars = new View[] { (View)findViewById(R.id.pet_hp_bar1), (View)findViewById(R.id.pet_hp_bar2), (View)findViewById(R.id.pet_hp_bar3), (View)findViewById(R.id.pet_hp_bar4) };
		View[] petHPRed = new View[] { (View)findViewById(R.id.stat_red_hp_bar1), (View)findViewById(R.id.stat_red_hp_bar2), (View)findViewById(R.id.stat_red_hp_bar3), (View)findViewById(R.id.stat_red_hp_bar4) };
		ImageView[] petImages = new ImageView[]{ (ImageView)findViewById(R.id.pet_area1), (ImageView)findViewById(R.id.pet_area2), (ImageView)findViewById(R.id.pet_area3), (ImageView)findViewById(R.id.pet_area4)};
		
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, r.getDisplayMetrics());
		
		for (int i = 0; i < 4; i++){
			if (_activePets[i] == null) continue;

			int width = petHPRed[i].getMeasuredWidth();
			RelativeLayout.LayoutParams petHPParams = (RelativeLayout.LayoutParams)petHPBars[i].getLayoutParams();
			petHPParams.width = (int)(width * ((float)_activePets[i].Hunger / (float)100));
			petHPBars[i].setLayoutParams(petHPParams);
			petHungerText[i].setText(_activePets[i].GetHungerString());
				
			petImages[i].setImageResource(_activePets[i].GetDrawableId(true));
			
			if (_activePets[i].CurrentForm == PixelPet.PetForm.Primary){
				if (_activePets[i].JustEvolved){
					appState.NameYourPet(_activePets[i], null, this);
					if (appState.getSettings().NotifyUser && !appState.notifications[i])
						appState.NotifyOfPetFormChange(_activePets[i], i);
					_activePets[i].JustEvolved = false;
				}
			}else if (_activePets[i].CurrentForm != PixelPet.PetForm.Primary){
				if (_activePets[i].JustEvolved){
					appState.PetEvolved(i, this);
					if (appState.getSettings().NotifyUser && !appState.notifications[i])
						appState.NotifyOfPetFormChange(_activePets[i], i);
					_activePets[i].JustEvolved = false;
				}
			}
			
			petImages[i].scrollTo((_activePets[i].CurrFrame + _activePets[i].RelAniX) * size, _activePets[i].RelAniY * size);
		}
	}
}
