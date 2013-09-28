package com.cakeandturtles.pixelpets;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
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

import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.example.pixelpets.R;

public class WorldMapActivity extends Activity {
	private PPApp appState;
	private Handler handler;
	private Runnable runnable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_world_map);
		
		appState = ((PPApp)getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.world_map, menu);
		return true;
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		UpdateMapDisplay();
		
		runnable = new Runnable(){
			@Override
			public void run(){
				for (int i = 0; i < 4; i++){
					PixelPet pet = appState.getActivePets()[i];
					if (pet == null) break;
					//pet.UpdatePetForm();
					//if (pet.JustEvolved && !appState.notifications[i])
						//appState.NotifyOfPetFormChange(pet, i);
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
	
	private void ClickItem(final int index){
		StartArea(index);
	}
	
	private void StartArea(int index){;
		appState.MyAdventures.CurrentAreaIndex = index;
		Intent intent = new Intent(this, AdventureActivity.class);
		intent.putExtra("com.cakeandturtles.pixelpets.areaIndex", index);
		startActivity(intent);
	}
	
	public void BackToGarden(View view)
	{
		finish();
	}
	
	public void ResetColor(View v){
		v.setBackgroundResource(0);
	}
	
	public String GetAreaName(int areaIndex){
		if (areaIndex == 0)
			return getResources().getString(R.string.area_index0);
		if (areaIndex == 1)
			return getResources().getString(R.string.area_index1);
		if (areaIndex == 2)
			return getResources().getString(R.string.area_index2);
		if (areaIndex == 3)
			return getResources().getString(R.string.area_index3);
		return "nullArea";
	}
	
	
	public void UpdateMapDisplay()
	{
		LinearLayout mapLayout = (LinearLayout)findViewById(R.id.world_map_layout);
		mapLayout.removeAllViews();
		
		for (int i = 0; i < 2; i++){
			LinearLayout areaRow = NewAreaRow();
			
			for (int j = 0; j < 2; j++){
				int index = i*2 + j;
			
				RelativeLayout areaFrame = NewAreaFrame(index);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(areaRow.getLayoutParams().width, areaRow.getLayoutParams().height, 0.50f);
				areaRow.addView(areaFrame, params);
			}
			
			mapLayout.addView(areaRow);
		}
		
		View horizontalSeperator = NewHorizontalSeperator();
		Button backButton = NewBackButton();
		mapLayout.addView(horizontalSeperator);
		mapLayout.addView(backButton);
	}
	
	private LinearLayout NewAreaRow(){
		LinearLayout areaRow = new LinearLayout(this);
		Resources r = getResources();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
		params.setMargins(0, 0, 0, marginBottom);
		areaRow.setLayoutParams(params);
		areaRow.setBaselineAligned(false);
		areaRow.setWeightSum(1);
		return areaRow;
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
	
	private RelativeLayout NewAreaFrame(final int mapIndex)
	{
		RelativeLayout itemFrame = new RelativeLayout(this);
		Resources r = getResources();
		int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, r.getDisplayMetrics());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, RelativeLayout.LayoutParams.WRAP_CONTENT);
		itemFrame.setLayoutParams(params);
		
		if (mapIndex < 0) return itemFrame;
			
		itemFrame.setClickable(true);
		itemFrame.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundColor(getResources().getColor(R.color.lightblue));
					return true;
				}else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.setBackgroundResource(0);
					ClickItem(mapIndex);
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_CANCEL){
					ResetColor(v);
					return true;
				}
				return false;
			}
		});
		
		ImageView areaImage = NewAreaImage(mapIndex);
		TextView areaText = NewAreaText(mapIndex);
		itemFrame.addView(areaImage);
		RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams)areaText.getLayoutParams();
		params2.addRule(RelativeLayout.BELOW, areaImage.getId());
		itemFrame.addView(areaText);
		
		return itemFrame;
	}
	
	private ImageView NewAreaImage(int index)
	{		
		ImageView areaImage = new ImageView(this);
		areaImage.setId(index+1000);
		Resources r = getResources();
		int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96, r.getDisplayMetrics());
		int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, r.getDisplayMetrics());
		int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
		int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -5, r.getDisplayMetrics());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		params.setMargins(0, marginTop, 0, marginBottom);
		areaImage.setLayoutParams(params);
		areaImage.setAdjustViewBounds(false);
		areaImage.setContentDescription(getText(R.string.item_description));
		areaImage.setScaleType(ScaleType.MATRIX);
		areaImage.setImageResource(R.drawable.world_map_sheet);
		
		areaImage.scrollTo((index % 2)*width, (index / 2) * height);
		
		return areaImage;
	}
	
	private TextView NewAreaText(int index)
	{
		TextView areaText = new TextView(this);
		Resources r = getResources();
		int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, height);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		areaText.setLayoutParams(params);
		areaText.setTextSize(14);
		areaText.setGravity(Gravity.CENTER_VERTICAL);
		areaText.setText(GetAreaName(index));
		
		return areaText;
	}
	
	private Button NewBackButton()
	{
		Button backButton = new Button(this);
		backButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		backButton.setGravity(Gravity.CENTER);
		backButton.setText(getResources().getText(R.string.back_to_garden));
		backButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				BackToGarden(v);
			}
		});
		return backButton;
	}
}
