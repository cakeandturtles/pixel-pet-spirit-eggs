package com.cakeandturtles.pixelpets;

import com.cakeandturtles.pixelpets.managers.Settings;
import com.example.pixelpets.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
	private PPApp appState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		appState = ((PPApp)getApplicationContext());
		appState.LoadSettings();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);	
		menu.findItem(R.id.notifications).setChecked(appState.getSettings().NotifyUser);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		menu.findItem(R.id.notifications).setChecked(appState.getSettings().NotifyUser);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public void onResume(){
		super.onResume();
	
	}

	/** Called when the user clicks the Start button */
    public void start(View view){
    	//Do something in response to button
    	Intent intent = new Intent(this, GardenActivity.class);
    	startActivity(intent);
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
}
