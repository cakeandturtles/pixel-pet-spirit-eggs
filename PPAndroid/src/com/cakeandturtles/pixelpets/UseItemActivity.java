package com.cakeandturtles.pixelpets;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.managers.Settings;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.example.pixelpets.R;

public class UseItemActivity extends Activity {
	private PPApp appState;
	private PixelPet[] _activePets;
	private PetItem petItem;
	private boolean noMoreItems = false;
	private Handler handler;
	private Runnable runnable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_use_item);
		
		appState = ((PPApp)getApplicationContext());
		_activePets = appState.getActivePets();
		HandleAndDrawPets();
		UpdateTextDisplay();
		UpdateSeperators();
		
		Bundle extras = getIntent().getExtras();
		if (extras != null && petItem == null){
			petItem = (PetItem)extras.get("petItem");
		}
		ResetTitle();
		
		View[] petBoxes = new View[]{ findViewById(R.id.pet_box1), findViewById(R.id.pet_box2), findViewById(R.id.pet_box3), findViewById(R.id.pet_box4)};
		
		for (int i = 0; i < 4; i++){
			if (_activePets[i] != null){
				_activePets[i].CurrFrame = 0;
				_activePets[i].FrameCount = 0;
			
				petBoxes[i].setOnTouchListener(new OnTouchListener(){
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (noMoreItems) return false;
						
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
		runnable = new Runnable(){
			@Override
			public void run(){
				appState.ClearNotifications(true, null);
				UpdateTextDisplay();
				HandleAndDrawPets();
				for (int i = 0; i < 4; i++){
					if (_activePets[i] != null){
						_activePets[i].Update();
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

	public void BackToPrevious(View view)
	{
		finish();
	}

	public void ClickOnBox(final View view)
	{
		ResetColor(view);
		
		PixelPet tempPet = appState.getTempActivePet();
		
		if (petItem.Effect.NoEffect(tempPet)){
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			if (petItem.CollectableType != PetItem.CollectableTypes.Food){
				dialog.setTitle("No Effect");
				dialog.setMessage("Using " + petItem.Name + " on " + tempPet.Name + " will currently have no effect.");
			}else{
				dialog.setTitle(tempPet.Name + " is Full");
				dialog.setMessage(tempPet.Name + " is bloated and refuses to eat the " + petItem.Name);
			}
			
			dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.cancel();
				}
			});
			
			dialog.create().show();
		}else{
			String effect = petItem.Effect.DoItemEffect(appState.getTempActivePet(), null);
				
			appState.MyInventory.removeOneFromInventory(petItem);
			petItem.Quantity--;
			if (!appState.MyInventory.inventoryContains(petItem)){
				noMoreItems = true;
				NoMoreItems();
			}
			DisplayEffectDialog(effect);
			ResetColor(view);
		}
		ResetTitle();
	}
	
	public void DisplayEffectDialog(String effect)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Result");
		dialog.setMessage(effect);
		dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		dialog.create().show();
	}
	
	public void NoMoreItems()
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Ran Out...");
		dialog.setMessage("You have no more " + petItem.Name + "!");
		dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				UseItemActivity.this.finish();
			}
		});
		dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		
		dialog.create().show();
	}
	
	public void ResetTitle()
	{
		if (petItem.Quantity != 1)
			setTitle("Use Item: " + petItem.Name + " (" + petItem.Quantity + ")");
		else
			setTitle("Use Item: " + petItem.Name);
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
		TextView[] petHungerText = new TextView[]{ (TextView)findViewById(R.id.pet_hunger1), (TextView)findViewById(R.id.pet_hunger2), (TextView)findViewById(R.id.pet_hunger3), (TextView)findViewById(R.id.pet_hunger4)};
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
					if (appState.getSettings().NotifyUser)
						appState.NotifyOfPetFormChange(_activePets[i], i);
				}
			}
			
			petImages[i].scrollTo((_activePets[i].CurrFrame + _activePets[i].RelAniX)*size, _activePets[i].RelAniY * size);
		}
	}
}
