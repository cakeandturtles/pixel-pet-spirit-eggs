package com.cakeandturtles.pixelpets;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cakeandturtles.pixelpets.managers.Settings;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.example.pixelpets.R;

public class DaycareActivity extends Activity {
	private PPApp appState;
	private List<PixelPet> _daycare;
	private int _sizeKeeper;
	private PixelPet eggSwap;
	private int partySwapIndex;
	private boolean isSelectingMove;
	private int moveIndex;
	private View moveView;
	private boolean release;
	private boolean swap;
	private int currPage;
	private int numPages;
	private Button _nextButton;
	private TextView _pageText;
	private Button _prevButton;
	
	private Handler handler;
	private Runnable runnable;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daycare);
		
		release = false;
		swap = false;
		eggSwap = null;
		partySwapIndex = -1;
		appState = ((PPApp)getApplicationContext());
		appState.LoadUser();
		_daycare = appState.TheDaycare.GetStoredPets();
		
		_sizeKeeper = _daycare.size();
		isSelectingMove = false;
		moveIndex = -1;
		moveView = null;
		
		currPage = 0;
		numPages = (_daycare.size()-1)/9;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.daycare, menu);
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
		Bundle extras = getIntent().getExtras();
		if (extras != null){
			release = extras.getBoolean("com.cakeandturtles.pixelpets.release");
			swap = extras.getBoolean("com.cakeandturtles.pixelpets.swap");
			getIntent().removeExtra("com.cakeandturtles.pixelpets.release");
			getIntent().removeExtra("com.cakeandturtles.pixelpets.swap");
		}if (release){
			setTitle("Release for Egg?");
			eggSwap = appState.tempEggSwapHolder;
		}else if (swap){
			partySwapIndex = appState.tempIndex;
			if (appState.getActivePets()[partySwapIndex].CurrentForm != PixelPet.PetForm.Egg)
				setTitle("Swap for " + appState.getActivePets()[partySwapIndex].Name + "?");
			else
				setTitle("Swap for Mysterious Egg?");
		}
		
		InitiateDaycareDisplay();
		runnable = new Runnable(){
			@Override
			public void run(){
				if (_daycare.size() != _sizeKeeper){
					numPages = (_daycare.size()-1)/9;
					if (currPage > numPages) currPage--;
					if (currPage < 0) currPage = 0;
					_sizeKeeper = _daycare.size();
					InitiateDaycareDisplay();
				}
				for (int i = 0; i < _daycare.size(); i++){
					_daycare.get(i).UpdateAnimation();
				}
				for (int i = 0; i < 4; i++){
					PixelPet pet = appState.getActivePets()[i];
					if (pet == null) break;
					pet.UpdatePetForm();
					if (pet.JustEvolved && !appState.notifications[i])
						appState.NotifyOfPetFormChange(pet, i);
				}
				UpdatePetImages();
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
		if (moveView != null)
			ResetColor(moveView);
		isSelectingMove = false;
		moveIndex = -1;
		moveView = null;
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		appState.StopRunAndHandle();
	}
	
	@Override
	public void onBackPressed()
	{
		BackToGarden(null);
	}
	
	private void ClickPet(final View view, final int index){
		PixelPet pet = _daycare.get(index);
		
		if (isSelectingMove){
			Switch(index);
		}else{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			if (pet.CurrentForm != PixelPet.PetForm.Egg)
				builder.setTitle(pet.Name + " the " + pet.GetSpeciesAndGender() + "\nLvl. " + pet.Level);
			else builder.setTitle("??? the Mysterious Egg");
			
			if (!release && !swap){
				builder.setItems(new String[]{ "View Stats", "Withdraw", "Switch", "Rename", "Release"}, new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						switch (which){
						case 0:
								ViewStats(index);
								break;
							case 1:
								Withdraw(index);
								break;
							case 2:
								view.setBackgroundColor(getResources().getColor(R.color.lightblue));
								StartSwitch(view, index);
								break;
							case 3:
								Rename(index);
								break;
							case 4:
								Release(index);
								break;
							default: break;
						}
					}
				});
			}else if (release){
				builder.setItems(new String[]{ "View Stats", "Release"}, new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						switch (which){
							case 0:
								ViewStats(index);
								break;
							case 1:
								Release(index);
								break;
							default: break;
						}
					}
				});
			}else if (swap){
				builder.setItems(new String[]{ "View Stats", "Swap"}, new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						switch (which){
							case 0:
								ViewStats(index);
								break;
							case 1:
								Swap(index);
								break;
							default: break;
						}
					}
				});
			}
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.cancel();
				}
			});
			
			builder.create().show();
		}
	}
	
	public void ViewStats(int index)
	{
		Intent intent = new Intent(this, DaycareViewStatsActivity.class);
		intent.putExtra("com.cakeandturtles.pixelpets.daycareIndex", index);
		startActivity(intent);
	}
	
	public void Withdraw(int index)
	{
		appState.TryWithdraw(index, this);
	}
	
	public void Swap(int index)
	{
		final PixelPet pet = appState.TheDaycare.GetStoredPets().get(index);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		PixelPet tempPet = appState.getActivePets()[partySwapIndex];
		appState.TheDaycare.GetStoredPets().set(index, tempPet);
		appState.getActivePets()[partySwapIndex] = pet;
		String tempName = "Mysterious Egg";
		if (tempPet.CurrentForm != PixelPet.PetForm.Egg)
			tempName = tempPet.Name;
		
		final View withdrawView = LayoutInflater.from(this).inflate(R.layout.item_dialog, null);
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
		((ImageView)withdrawView.findViewById(R.id.item_image_area)).scrollTo((pet.RelAniX)*size, pet.RelAniY * size);
		((ImageView)withdrawView.findViewById(R.id.item_image_area)).setImageResource(pet.GetDrawableId(false));
		
		String message = "";
		if (pet.CurrentForm == PixelPet.PetForm.Egg){
			builder.setTitle("Party Egg!");
			message += "??? the Mysterious Egg has been added to your active party.\n\n"+tempName + " has been added to the daycare.";
			pet.ResetEggTimer();
		}else{
			builder.setTitle("Party, "+pet.Name + "!");
			message += pet.Name + " the " + pet.GetSpeciesAndGender() + " has been added to your active party.\n\n"+tempName + " has been added to the daycare.";
		}
		((TextView)withdrawView.findViewById(R.id.item_text_area)).setText(message);
		builder.setNegativeButton("Ok", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
				DaycareActivity.this.finish();
			}
		});
		builder.setView(withdrawView);
		builder.show();
	}
	
	public void StartSwitch(View view, int index)
	{
		setTitle("Switch places?");
		isSelectingMove = true;
		moveIndex = index;
		moveView = view;
	}
	
	public void Switch(int index)
	{
		setTitle(getResources().getString(R.string.title_activity_daycare));
		if (moveIndex != index){
			PixelPet swapper = _daycare.get(moveIndex);
			PixelPet swappee = _daycare.get(index);
			_daycare.set(moveIndex, swappee);
			_daycare.set(index, swapper);
		}
		
		ResetColor(moveView);
		isSelectingMove = false;
		moveIndex = -1;
		moveView = null;
	}
	
	public void Rename(int index)
	{
		appState.NameYourPet(_daycare.get(index), new View(this), this);
	}
	
	public void Release(final int index)
	{
		if (!release)
			appState.TryRelease(index, this, true);
		else{
			final PixelPet tempPet = appState.TheDaycare.GetStoredPets().get(index);
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			if (tempPet.CurrentForm != PixelPet.PetForm.Egg){
				dialog.setTitle("Release " + tempPet.Name + "?");
				dialog.setMessage("Release " + tempPet.Name + " into the wild so that the Mysterious Egg may take the place?\n\nWARNING: This is permanent and you cannot get " + tempPet.Name + " back!");
			}
			else{
				dialog.setTitle("Release Mysterious Egg?");
				dialog.setMessage("Release Mysterious Egg into the wild so that the Mysterious Egg may take the place?\n\nWARNING: This is permanent and you cannot get Mysterious Egg back!");
			}
			dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					appState.TheDaycare.GetStoredPets().set(index, eggSwap);
					dialog.cancel();
					appState.finishedReleaseForEgg = true;
					DaycareActivity.this.finish();
				}
			});
			dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			
			dialog.create().show();
		}
	}
	
	public void BackToGarden(View view)
	{
		_nextButton = null;
		_pageText = null;
		_prevButton = null;
		finish();
	}
	
	public void ResetColor(View v){
		v.setBackgroundResource(0);
	}
	
	public void UpdatePetImages()
	{
		int start = 0 + currPage * 9;
		int end = 10 + currPage * 9;
		if (end > _daycare.size()) end = _daycare.size();
		
		for (int i = 0; i < 9; i++){
			int index = i + start;
			if (index >= _daycare.size()) return;
			PixelPet pet = _daycare.get(index);
			
			Resources r = getResources();
			int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, r.getDisplayMetrics());
			((ImageView)findViewById(1000+index)).setImageResource(pet.GetDrawableId(true));
			((ImageView)findViewById(1000+index)).scrollTo((pet.CurrFrame + pet.RelAniX)*size, pet.RelAniY * size);
			if (pet.CurrentForm != PixelPet.PetForm.Egg)
				((TextView)findViewById(index+100000)).setText(pet.Name +" " + pet.GetGender() + "\n" + pet.Species + " L" + pet.Level);
			else ((TextView)findViewById(index+100000)).setText("Mysterious Egg");
		}
	}
	
	public void NextPage(){
		if (currPage < numPages){
			currPage++;
			InitiateDaycareDisplay();
			UpdatePetImages();
		}
	}
	
	public void PrevPage(){
		if (currPage > 0){
			currPage--;
			InitiateDaycareDisplay();
			UpdatePetImages();
		}
	}
	
	public void InitiateDaycareDisplay()
	{
		LinearLayout daycareLayout = (LinearLayout)findViewById(R.id.daycare_layout);
		daycareLayout.removeAllViews();
		
		int numPets = _daycare.size();
		int start = 0 + currPage * 9;
		int rowEnd = 3;
		
		
		
		if (numPets == 0){
			TextView noItemText = NewNoPetText();
			View horizontalSeperator = NewHorizontalSeperator();
			
			daycareLayout.addView(noItemText);
			daycareLayout.addView(horizontalSeperator);
		}else{
			for (int i = 0; i < rowEnd; i++){
				LinearLayout petRow = NewPetRow();
				View horizontalSeperator = NewHorizontalSeperator();
				
				for (int j = 0; j < 3; j++){
					int index = start + i*3 + j;
					if (index >= numPets)
						index = -1;
					
					if (j > 0){
						View verticalSeperator = NewVerticalSeperator();
						petRow.addView(verticalSeperator);
					}
					RelativeLayout petFrame = NewPetFrame(index);
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(petRow.getLayoutParams().width, petRow.getLayoutParams().height, 0.33f);
					petRow.addView(petFrame, params);
				}
				
				daycareLayout.addView(petRow);
				daycareLayout.addView(horizontalSeperator);
			}
		}
		LinearLayout nextPrevButtons = NewNextPrevButtons();
		daycareLayout.addView(nextPrevButtons);
		
		Button backButton = NewBackButton();
		daycareLayout.addView(backButton);
	}
	
	private LinearLayout NewPetRow(){
		LinearLayout petRow = new LinearLayout(this);
		Resources r = getResources();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
		params.setMargins(0, 0, 0, marginBottom);
		petRow.setLayoutParams(params);
		petRow.setBaselineAligned(false);
		petRow.setWeightSum(1);
		return petRow;
	}
	
	private View NewHorizontalSeperator(){
		View horizontalSeperator = new View(this);
		Resources r = getResources();
		int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, r.getDisplayMetrics());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
		int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
		params.setMargins(0, 0, 0, marginBottom);
		horizontalSeperator.setLayoutParams(params);
		horizontalSeperator.setBackgroundColor(Color.LTGRAY);
		return horizontalSeperator;
	}
	
	private View NewVerticalSeperator(){
		View verticalSeperator = new View(this);
		Resources r = getResources();
		int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, r.getDisplayMetrics());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT);
		verticalSeperator.setLayoutParams(params);
		verticalSeperator.setBackgroundColor(Color.LTGRAY);
		return verticalSeperator;
	}
	
	private RelativeLayout NewPetFrame(final int petIndex)
	{
		RelativeLayout petFrame = new RelativeLayout(this);
		Resources r = getResources();
		int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, r.getDisplayMetrics());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, RelativeLayout.LayoutParams.WRAP_CONTENT);
		petFrame.setLayoutParams(params);
			
		if (petIndex >= 0){
			petFrame.setClickable(true);
			petFrame.setOnTouchListener(new OnTouchListener(){
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_DOWN) {
						v.setBackgroundColor(getResources().getColor(R.color.lightblue));
						return true;
					}else if (event.getAction() == MotionEvent.ACTION_UP) {
						v.setBackgroundResource(0);
						ClickPet(v, petIndex);
						return true;
					} else if (event.getAction() == MotionEvent.ACTION_CANCEL){
						ResetColor(v);
						return true;
					}
					return false;
				}
			});
		}
		
		ImageView petImage = NewPetImage(petIndex);
		TextView petText = NewPetText(petIndex);
		petFrame.addView(petImage);
		RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams)petText.getLayoutParams();
		params2.addRule(RelativeLayout.BELOW, petImage.getId());
		petFrame.addView(petText);
		
		return petFrame;
	}
	
	private ImageView NewPetImage(int index)
	{
		PixelPet pet = null;
		if (index >= 0) 
			pet = _daycare.get(index);
		
		ImageView petImage = new ImageView(this);
		petImage.setId(1000+index);
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, r.getDisplayMetrics());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		petImage.setLayoutParams(params);
		petImage.setAdjustViewBounds(false);
		if (pet != null)
			petImage.setContentDescription(pet.PetDescription);
		petImage.setScaleType(ScaleType.MATRIX);
		if (pet != null){
			petImage.setImageResource(pet.GetDrawableId(true));
			petImage.scrollTo(pet.RelAniX*size, pet.RelAniY * size);
		}
		
		return petImage;
	}
	
	private TextView NewPetText(int index)
	{
		PixelPet pet = null;
		if (index >= 0)
			pet = _daycare.get(index);
		
		TextView petText = new TextView(this);
		petText.setId(index+100000);
		Resources r = getResources();
		int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -5, r.getDisplayMetrics());
		int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, height);
		params.bottomMargin = marginBottom;
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		petText.setLayoutParams(params);
		petText.setTextSize(12);
		petText.setGravity(Gravity.CENTER);
		if (pet != null){
			if (pet.CurrentForm == PixelPet.PetForm.Egg)
				petText.setText(pet.Name);
			else
				petText.setText(pet.Name + "\n" + pet.GetSpeciesAndGender() + " Lvl. " + pet.Level);
		}
		
		return petText;
	}

	private TextView NewNoPetText()
	{		
		TextView petText = new TextView(this);
		Resources r = getResources();
		int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
		int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, marginTop, 0, marginBottom);
		petText.setLayoutParams(params);
		petText.setTextSize(18);
		petText.setText(R.string.no_daycare_pets);
		
		return petText;
	}
	
	private LinearLayout NewNextPrevButtons()
	{
		Resources r = getResources();
		
		int buttonSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, r.getDisplayMetrics());
		int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
		LinearLayout nextPrevButtons = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0,  0,  0, marginBottom);
		nextPrevButtons.setLayoutParams(params);
		nextPrevButtons.setWeightSum(1.0f);
		nextPrevButtons.setOrientation(LinearLayout.HORIZONTAL);
		
		RelativeLayout nextButtonFrame = new RelativeLayout(this);
		params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.weight = 0.33f;
		nextButtonFrame.setLayoutParams(params);
		
		_nextButton = new Button(this);
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(buttonSize, buttonSize);
		params2.addRule(RelativeLayout.CENTER_IN_PARENT, 1);
		_nextButton.setLayoutParams(params2);
		_nextButton.setBackgroundResource(R.drawable.round_button);
		_nextButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				NextPage();	
			}
		});
		_nextButton.setTextColor(r.getColor(R.color.transgrey));
		int paddingBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, r.getDisplayMetrics());
		_nextButton.setPadding(0, 0, 0, paddingBottom);
		_nextButton.setText(r.getString(R.string.next_pet_button));
		_nextButton.setTextSize(32);
		if (currPage >= numPages){
			_nextButton.setVisibility(View.GONE);
			_nextButton.setClickable(false);
		}
		nextButtonFrame.addView(_nextButton);
		
		_pageText = new TextView(this);
		params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.weight = 0.34f;
		_pageText.setLayoutParams(params);
		_pageText.setText((currPage+1) + " / " + (numPages+1));
		_pageText.setTextColor(r.getColor(R.color.transgrey));
		_pageText.setTextSize(32);
		_pageText.setGravity(Gravity.CENTER);
		if (numPages == 0)
			_pageText.setVisibility(View.GONE);
		
		RelativeLayout prevButtonFrame = new RelativeLayout(this);
		params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.weight = 0.33f;
		prevButtonFrame.setLayoutParams(params);
		
		_prevButton = new Button(this);
		_prevButton.setLayoutParams(params2);
		_prevButton.setBackgroundResource(R.drawable.round_button);
		_prevButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				PrevPage();	
			}
		});
		_prevButton.setTextColor(r.getColor(R.color.transgrey));
		_prevButton.setPadding(0, 0, 0, paddingBottom);
		_prevButton.setText(r.getString(R.string.prev_pet_button));
		_prevButton.setTextSize(32);
		if (currPage == 0){
			_prevButton.setVisibility(View.GONE);
			_prevButton.setClickable(false);
		}
		prevButtonFrame.addView(_prevButton);

		nextPrevButtons.addView(prevButtonFrame);
		nextPrevButtons.addView(_pageText);
		nextPrevButtons.addView(nextButtonFrame);
		return nextPrevButtons;
	}
	
	private Button NewBackButton()
	{
		Button backButton = new Button(this);
		backButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		backButton.setGravity(Gravity.CENTER);
		backButton.setText(getResources().getText(R.string.back_to_menu));
		backButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				BackToGarden(v);
			}
		});
		return backButton;
	}
}
