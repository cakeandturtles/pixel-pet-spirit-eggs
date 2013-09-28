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

import com.cakeandturtles.pixelpets.PPApp.PocketType;
import com.cakeandturtles.pixelpets.items.Container;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.items.fruits.FoodPocket;
import com.cakeandturtles.pixelpets.managers.Settings;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.example.pixelpets.R;

public class InventoryActivity extends Activity {
	private PPApp appState;
	private List<PetItem> _inventory;
	private Handler handler;
	private Runnable runnable;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);
		
		appState = ((PPApp)getApplicationContext());
		appState.LoadUser();
		_inventory = appState.MyInventory.getInventory();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inventory, menu);
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
		
		SetInitialPocket(appState.currInvPocket);
		UpdateInventoryDisplay();
		
		runnable = new Runnable(){
			@Override
			public void run(){
				for (int i = 0; i < 4; i++){
					PixelPet pet = appState.getActivePets()[i];
					if (pet == null) break;
					if (pet.JustEvolved && !appState.notifications[i])
						appState.NotifyOfPetFormChange(pet, i);
				}
				handler.postDelayed(this, 60);
			}
		};
		handler = new Handler();
		handler.postDelayed(runnable, 60);
		
		appState.StopRunAndHandle();
	}
	
	private void SetInitialPocket(PocketType desiredPocket)
	{		
		if ((desiredPocket == null || desiredPocket == PocketType.PET_FOOD) && appState.MyInventory.inventoryContains(new FoodPocket())){
			appState.currInvPocket = PocketType.PET_FOOD;
			_inventory = ((Container)appState.MyInventory.getFromInventory(new FoodPocket()))._collection;
		}
		else
			appState.currInvPocket = null;
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
	public void onBackPressed()
	{
		BackToGarden(null);
	}
	
	private void ClickItem(final int index){
		PetItem item = _inventory.get(index);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if (item.Quantity == 1) builder.setTitle(item.Name);
		else builder.setTitle(item.Name + " (" + item.Quantity + ")");
			
		final View itemView = LayoutInflater.from(this).inflate(R.layout.item_dialog, null);
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
		((ImageView)itemView.findViewById(R.id.item_image_area)).scrollTo((item.RelAniX)*size, item.RelAniY * size);
		
		TextView nameText = ((TextView)itemView.findViewById(R.id.item_text_area));
		nameText.setText(item.Description);
		
		builder.setView(itemView);
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		if (item.Effect != null){
			builder.setPositiveButton("Use Item", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					UseItem(index);
					dialog.cancel();
				}
			});
			AlertDialog itemDialog = builder.create();
			itemDialog.show();
		}else{
			AlertDialog itemDialog = builder.create();
			itemDialog.show();
		}
	}
	
	public void UseItem(final int index)
	{
		PetItem item = _inventory.get(index);
		if (item.Effect.NeedsATarget){
			Intent intent = new Intent(this, UseItemActivity.class);
			intent.putExtra("petItem", item);
			startActivity(intent);
		}else{
			appState.BattleUsedItem = item;
			appState.BattleUsedItemOnIndex = 0;
			finish();
		}
		
		UpdateInventoryDisplay();
	}
	
	public void BackToGarden(View view)
	{
		super.onBackPressed();
	}
	
	public void ResetColor(View v){
		v.setBackgroundResource(0);
	}
	
	public void UpdateInventoryDisplay()
	{		
		LinearLayout inventoryLayout = (LinearLayout)findViewById(R.id.inventory_layout);
		inventoryLayout.removeAllViews();
		
		int numItems = _inventory.size();
		
		for (int i = 0; i <= numItems / 3; i++){
			LinearLayout itemRow = NewItemRow();
			View horizontalSeperator = NewHorizontalSeperator();
			
			for (int j = 0; j < 3; j++){
				int index = i*3 + j;
				if (index >= numItems)
					index = -1;
				
				if (j > 0){// && index >= 0){
					View verticalSeperator = NewVerticalSeperator();
					itemRow.addView(verticalSeperator);
				}
				RelativeLayout itemFrame = NewItemFrame(index);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemRow.getLayoutParams().width, itemRow.getLayoutParams().height, 0.33f);
				itemRow.addView(itemFrame, params);
			}
			
			inventoryLayout.addView(itemRow);
			if (!(i == numItems/3 && numItems % 3 == 0))
				inventoryLayout.addView(horizontalSeperator);
		}
		
		if (numItems == 0){
			TextView noItemText = NewNoItemText();
			View horizontalSeperator = NewHorizontalSeperator();
			
			inventoryLayout.addView(noItemText);
			inventoryLayout.addView(horizontalSeperator);
		}
		Button backButton = NewBackButton();
		inventoryLayout.addView(backButton);
	}
	
	private LinearLayout NewItemRow(){
		LinearLayout itemRow = new LinearLayout(this);
		//Resources r = getResources();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		//int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
		//params.setMargins(0, 0, 0, marginBottom);
		itemRow.setLayoutParams(params);
		//itemRow.setBaselineAligned(false);
		itemRow.setWeightSum(1);
		return itemRow;
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
	
	private RelativeLayout NewItemFrame(final int itemIndex)
	{
		RelativeLayout itemFrame = new RelativeLayout(this);
		Resources r = getResources();
		int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, r.getDisplayMetrics());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, RelativeLayout.LayoutParams.WRAP_CONTENT);
		itemFrame.setLayoutParams(params);
		
		if (itemIndex < 0) return itemFrame;
			
		itemFrame.setClickable(true);
		itemFrame.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundColor(getResources().getColor(R.color.lightblue));
					return true;
				}else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.setBackgroundResource(0);
					ClickItem(itemIndex);
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_CANCEL){
					ResetColor(v);
					return true;
				}
				return false;
			}
		});
		
		ImageView itemImage = NewItemImage(itemIndex);
		TextView itemText = NewItemText(itemIndex);
		itemFrame.addView(itemImage);
		RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams)itemText.getLayoutParams();
		params2.addRule(RelativeLayout.BELOW, itemImage.getId());
		itemFrame.addView(itemText);
		
		return itemFrame;
	}
	
	private ImageView NewItemImage(int index)
	{
		PetItem item = _inventory.get(index);
		
		ImageView itemImage = new ImageView(this);
		itemImage.setId(index+1000);
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, r.getDisplayMetrics());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		itemImage.setLayoutParams(params);
		itemImage.setAdjustViewBounds(false);
		itemImage.setContentDescription(getText(R.string.item_description));
		itemImage.setScaleType(ScaleType.MATRIX);
		itemImage.setImageResource(R.drawable.item_sheet_small);
		
		itemImage.scrollTo(item.RelAniX*size, item.RelAniY * size);
		
		return itemImage;
	}
	
	private TextView NewItemText(int index)
	{
		PetItem item = _inventory.get(index);
		
		TextView itemText = new TextView(this);
		Resources r = getResources();
		int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, height);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		itemText.setLayoutParams(params);
		itemText.setTextSize(12);
		itemText.setGravity(Gravity.CENTER_VERTICAL);
		if (item.Quantity > 1)
			itemText.setText(item.Name + " (" + item.Quantity + ")");
		else
			itemText.setText(item.Name);
		
		return itemText;
	}

	private TextView NewNoItemText()
	{		
		TextView itemText = new TextView(this);
		Resources r = getResources();
		int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, r.getDisplayMetrics());
		int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, marginTop, 0, marginBottom);
		itemText.setLayoutParams(params);
		itemText.setTextSize(18);
		itemText.setText(R.string.no_items);
		
		return itemText;
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
