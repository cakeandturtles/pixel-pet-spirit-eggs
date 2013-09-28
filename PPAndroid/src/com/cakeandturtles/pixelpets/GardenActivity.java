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
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cakeandturtles.pixelpets.managers.Settings;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.example.pixelpets.R;

public class GardenActivity extends Activity{
	private PPApp appState;
	private int petIndex;
	private PixelPet _activePet;
	private TextView _petName;
	private TextView _petSpecies;
	private TextView _petLevel;
	private ImageView _petView;
	private TextView _petDescription;
	private Button _nextPetButton;
	private Button _prevPetButton;
	private Handler handler;
	private Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_garden);
		
		appState = ((PPApp)getApplicationContext());
		appState.LoadUser();
		petIndex = 0;
		_activePet = appState.getActivePets()[petIndex];
		
		InitializeViews();
		UpdateNextPrevButtons();
		
		InitializeLongClick();
		if (!_activePet.HaveIBeenNamed)
			NameYourPet(null);
		HandleAndDrawPet(true);
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
				HandleAndDrawPet(true);
				for (int i = 0; i < 4; i++){
					PixelPet pet = appState.getActivePets()[i];
					if (pet == null) continue;
					pet.Update();
				}
				handler.postDelayed(this, 60);
			}
		};
		handler = new Handler();
		handler.postDelayed(runnable, 0);
		
		appState.StopRunAndHandle();
		
		UpdatePetIndex();
		appState.tempIndex = petIndex;
		appState.MyAdventures.InBattle = false;
		_activePet = appState.getTempActivePet();
		HandleAndDrawPet(true);
		UpdateNextPrevButtons();
		
		int newIndex = -1;
		Bundle extras = getIntent().getExtras();
		if (extras != null){
			newIndex = extras.getInt("com.cakeandturtles.pixelpets.petIndex");
			getIntent().removeExtra("com.cakeandturtles.pixelpets.petIndex");
		}
		if (appState.MyAdventures.NewEggIndex >= 0)
			newIndex = appState.MyAdventures.NewEggIndex;
		appState.MyAdventures.NewEggIndex = -1;
		if (newIndex < 0){
			for (int i = 0; i < 4; i++){
				if (appState.getActivePets()[i] == null) continue;
				if (appState.getActivePets()[i].JustEvolved 
						&& appState.getActivePets()[i].CurrentForm == PixelPet.PetForm.Primary){
					newIndex = i;
					break;
				}
			}
		}if (newIndex < 0){
			for (int i = 0; i < 4; i++){
				if (appState.getActivePets()[i] == null) continue;
				if (appState.getActivePets()[i].CurrentForm == PixelPet.PetForm.Egg){
					newIndex = i;
					break;
				}
			}
		}
		if (newIndex >= 0){
			if (newIndex < petIndex){
				while(newIndex < petIndex-1){
					petIndex--;
					appState.tempIndex = petIndex;
				}PrevPet(null);
			}if (newIndex > petIndex){
				while (newIndex > petIndex+1){
					petIndex++;
					appState.tempIndex = petIndex;
				}NextPet(null);
			}
		}
		newIndex = -1;
	}
	
	private void UpdatePetIndex(){
		while (petIndex > 0){
			if (appState.getActivePets()[petIndex] == null)
				petIndex--;
			else break;
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
		appState.StartRunAndHandle();
		appState.SaveUser();
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
		getMenuInflater().inflate(R.menu.garden, menu);
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
	
	private void InitializeViews()
	{
		_petName = (TextView)findViewById(R.id.pet_name);
		_petSpecies = (TextView)findViewById(R.id.pet_species);
		_petLevel = (TextView)findViewById(R.id.pet_level);
		_petView = (ImageView)findViewById(R.id.pet_area);
		_petDescription = (TextView)findViewById(R.id.pet_description);
		_nextPetButton = (Button)findViewById(R.id.next_pet_button);
		_prevPetButton = (Button)findViewById(R.id.prev_pet_button);
	}
	
	private void NullifyViews()
	{
		_petName = null;
		_petSpecies = null;
		_petLevel = null;
		_petView = null;
		_petDescription = null;
		_nextPetButton = null;
		_prevPetButton = null;
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
	
	public void NextPet(View view){
		if (petIndex < 3 || appState.getActivePetCount() < petIndex+1 || appState.getActivePets()[petIndex+1] != null){
			petIndex++;
			appState.tempIndex = petIndex;
			_activePet = appState.getActivePets()[petIndex];
			HandleAndDrawPet(true);
			UpdateNextPrevButtons();
		}
	}
	
	public void PrevPet(View view){
		if (petIndex > 0){
			petIndex--;
			appState.tempIndex = petIndex;
			_activePet = appState.getActivePets()[petIndex];
			HandleAndDrawPet(true);
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
		
		if (petIndex >= 3 || (petIndex == 2 && appState.getActivePets()[3] == null) || (petIndex == 1 && appState.getActivePets()[2] == null) || (appState.getActivePets()[1] == null)){
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
		HandleAndDrawPet(false);
	}
	
	public void ViewStats(View view)
	{
		Intent intent = new Intent(this, ViewStatsActivity.class);
		intent.putExtra("com.cakeandturtles.pixelpets.fromGarden", true);
		startActivity(intent);
	}
	
	public void Adventure(View view)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		
		//Do something in response to button
		if (_activePet.CurrentForm == PixelPet.PetForm.Egg){
			alert.setTitle("Eggventure");
			alert.setMessage("An egg cannot go adventuring!");
			alert.show();
		}
		else{
			if (_activePet.HP <= 0){
				alert.setTitle("Out of Energy");
				alert.setMessage("This pet is out of energy and cannot adventure!");
				alert.show();				
			}else{
				for (int i = 0; i < 4; i++){
					if (appState.getActivePets()[i] != null)
						appState.getActivePets()[i].RestartEnergyRestoreTimer();
				}
				Intent intent = new Intent(this, WorldMapActivity.class);
				startActivity(intent);
			}
		}
	}
	
	public void Train(View view)
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		//Do something in response to button
		if (_activePet.CurrentForm == PixelPet.PetForm.Egg){
			alert.setTitle("Egg Fight!");
			alert.setMessage("An egg cannot train or battle!");
			alert.show();
		}
		else{
			if (_activePet.HP <= 0){
				alert.setTitle("Out of Energy");
				alert.setMessage("This pet is out of energy and cannot battle!");
				alert.show();
			}else{
				for (int i = 0; i < 4; i++){
					if (appState.getActivePets()[i] != null)
						appState.getActivePets()[i].RestartEnergyRestoreTimer();
				}
				Intent intent = new Intent(this, TrainingActivity.class);
				startActivity(intent);
			}
		}
	}
	
	public void ManagePets(View view)
	{
		//Do something in response to button
    	Intent intent = new Intent(this, ManageActivity.class);
    	startActivity(intent);
	}
	
	public void Inventory(View view)
	{
		Intent intent = new Intent(this, InventoryActivity.class);
		startActivity(intent);
	}
	
	public void Connect(View view)
	{
		//TODO:: Not Implemented
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		alert.setTitle("Unimplemented");
		alert.setMessage("This feature is currently unimplemented.\nSorry for the inconvenience :(");
		alert.show();
	}
	
	public void Petcyclopedia(View view)
	{
		//TODO:: Not Implemented
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		alert.setTitle("Unimplemented");
		alert.setMessage("This feature is currently unimplemented.\nSorry for the inconvenience :(");
		alert.show();
	}
	
	public void NameYourPet(View view)
	{
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
				
				if (pet == _activePet && pet.CurrentForm == PixelPet.PetForm.Primary){
					NameYourPet(null);
					appState.ClearNotifications(false, pet);
					pet.JustEvolved = false;
				}
				else if (pet == _activePet && pet.CurrentForm != PixelPet.PetForm.Primary){
					appState.PetEvolved(i, this);
					appState.ClearNotifications(false, pet);
					_activePet = appState.getActivePets()[petIndex];
				}
				HandleAndDrawPet(false);
			}
			if (pet == _activePet)
				appState.ClearNotifications(false, pet);
		}
	}
	
	public void HandleAndDrawPet(boolean notifyTrigger){	
		if (notifyTrigger) NotifyUserTriggerNameDialog();
		
		if (_activePet.CurrentForm != PixelPet.PetForm.Egg)
		{
			_petName.setText(_activePet.Name);
			_petSpecies.setText(_activePet.GetSpeciesAndGender());
			_petLevel.setText("Lvl. "+_activePet.Level);
			if (_activePet.CurrentForm == PixelPet.PetForm.Primary)
				_petView.setImageResource(R.drawable.primary_sheet);
			if (_activePet.CurrentForm == PixelPet.PetForm.Secondary)
				_petView.setImageResource(R.drawable.secondary_sheet);
			if (_activePet.CurrentForm == PixelPet.PetForm.Tertiary)
				_petView.setImageResource(R.drawable.tertiary_sheet);
		}else{
			_petName.setText(R.string.pet_name);
			_petSpecies.setText(R.string.pet_species);
			_petLevel.setText(R.string.pet_level);
			_petView.setImageResource(R.drawable.egg_sheet);
		}
		
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
		_petView.scrollTo((_activePet.CurrFrame + _activePet.RelAniX)*size, _activePet.RelAniY * size);
		_petDescription.setText(_activePet.PetDescription);
	}
}
