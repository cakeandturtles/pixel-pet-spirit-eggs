package com.cakeandturtles.pixelpets;

import com.example.pixelpets.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CodexActivity extends Activity {
	@SuppressWarnings("unused") //TODO:: DELETE ME!!!
	private PPApp appState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_codex);
		
		appState = ((PPApp)getApplicationContext());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.codex, menu);
		return true;
	}
}
