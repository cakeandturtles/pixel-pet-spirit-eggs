package com.cakeandturtles.pixelpets;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import android.app.AlertDialog;
import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.InputFilter;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cakeandturtles.pixelpets.attacks.Attack;
import com.cakeandturtles.pixelpets.items.PetItem;
import com.cakeandturtles.pixelpets.managers.AdventureManager;
import com.cakeandturtles.pixelpets.managers.Codex;
import com.cakeandturtles.pixelpets.managers.DaycareManager;
import com.cakeandturtles.pixelpets.managers.Inventory;
import com.cakeandturtles.pixelpets.managers.Settings;
import com.cakeandturtles.pixelpets.managers.TrainerObject;
import com.cakeandturtles.pixelpets.pets.Chloropillar;
import com.cakeandturtles.pixelpets.pets.Fledgwing;
import com.cakeandturtles.pixelpets.pets.Marmoss;
import com.cakeandturtles.pixelpets.pets.PixelPet;
import com.cakeandturtles.pixelpets.pets.PixelPet.PetForm;
import com.example.pixelpets.R;

public class PPApp extends Application{
	public static final String TRAINER_INFO = "com.cakeandturtles.pixelpets.TRAINER_INFO";
	public static final String ADVENTURE_MANAGER = "com.cakeandturtles.pixelpets.ADVENTURE_MANAGER";
	public static final String SETTINGS = "com.cakeandturtles.pixelpets.SETTINGS";
	public static final String ACTIVE_PETS = "com.cakeandturtles.pixelpets.ACTIVE_PETS";
	public static final String DAYCARE = "com.cakeandturtles.pixelpets.DAYCARE";
	public static final String INVENTORY = "com.cakeandturtles.pixelpets.INVENTORY";
	public static final String CODEX_FILE = "com.cakeandturtles.pixelpets.CODEX";
	
	private Handler _appHandler;
	private Runnable _appRunnable;
	public static Random AppRandom;
	
	public int tempIndex = -1;
	public boolean[] notifications;
	public boolean[] clickedNotifications;
	
	public TrainerObject Trainer;
	public Attack learnNewAttack = null;
	public boolean finishedReleaseForEgg = false;
	public PixelPet tempEggSwapHolder = null;
	public enum PocketType { PET_FOOD, MEDICINE, BATTLE, COLLECTABLE, TREASURE };
	public PocketType currInvPocket = null;
	private PixelPet[] _activePets;
	public PixelPet[] getActivePets(){
		return _activePets;
	}
	public int getActivePetCount(){
		int count = 0;
		for (int i = 0; i < _activePets.length; i++){
			if (_activePets[i] != null)
				count++;
		}
		return count;
	}
	public PixelPet getTempActivePet(){
		return _activePets[tempIndex];
	}
	public void setActivePet(PixelPet p, int index){
		_activePets[index] = p;
	}
	public void ShiftActivePetsToCorrectSpots(){
		PixelPet[] newPetSort = new PixelPet[]{ null, null, null, null };
		int index = 0;
		for (int i = 0; i < _activePets.length; i++){
			if (_activePets[i] != null){
				newPetSort[index] = _activePets[i];
				index++;
			}
		}for (int i = 0; i < newPetSort.length; i++){
			_activePets[i] = newPetSort[i];
		}
	}
	
	public DaycareManager TheDaycare;
	public int BattleIndex = -1;
	public PetItem BattleUsedItem = null;
	public int BattleUsedItemOnIndex = -1;
	public AdventureManager MyAdventures;
	public Inventory MyInventory;
	private Settings _settings;
	public Settings getSettings(){
		return _settings;
	}
	public Codex MyCodex;	
	
	public void LoadUser()
	{
		AppRandom = new Random(System.currentTimeMillis());
		
		if (tempIndex < 0)
			tempIndex = 0;
		if (notifications == null)
			notifications = new boolean[]{false, false, false, false};
		if (clickedNotifications == null)
			clickedNotifications = new boolean[]{false, false, false, false};
			
		LoadTrainer();
		LoadPet();
		LoadDaycare();
		LoadInventory();
		LoadAdventureManager();
		LoadSettings();
		LoadCodex();
	}
	
	public void SaveUser()
	{
		SaveTrainer();
		SavePet();
		SaveDaycare();
		SaveInventory();
		SaveAdventureManager();
		SaveSettings();
		SaveCodex();
	}
	
	private void InitializePets()
	{
		_activePets = new PixelPet[]{ null, null, null, null };
		_activePets[0] = new Fledgwing(Trainer.TrainerName, Trainer.TrainerID);
		_activePets[1] = new Chloropillar(Trainer.TrainerName, Trainer.TrainerID);
		_activePets[2] = new Marmoss(Trainer.TrainerName, Trainer.TrainerID);
	}
	
	private void LoadTrainer()
	{
		FileInputStream fis;
		ObjectInputStream inputStream;
		try
		{
			fis = openFileInput(TRAINER_INFO);
			inputStream = new ObjectInputStream(fis);
			Trainer = (TrainerObject)inputStream.readObject();
			inputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (Trainer == null)
			Trainer = new TrainerObject("Asuka", 123456789L); //TODO:: WHEN WE MAKE USERS IN A DATABASE... GENERATE UNIQUE ID
	}
	
	private void LoadPet()
	{
		FileInputStream fis;
		ObjectInputStream inputStream;
		try
		{
			fis = openFileInput(ACTIVE_PETS);
			inputStream = new ObjectInputStream(fis);
			_activePets = (PixelPet[])inputStream.readObject();
			inputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (_activePets == null || _activePets[0] == null)
			InitializePets();
	}
	
	private void LoadDaycare()
	{
		FileInputStream fis;
		ObjectInputStream inputStream;
		try
		{
			fis = openFileInput(DAYCARE);
			inputStream = new ObjectInputStream(fis);
			TheDaycare = (DaycareManager) inputStream.readObject();
			inputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (TheDaycare == null)
			TheDaycare = new DaycareManager();
	}
	
	private void LoadInventory()
	{
		FileInputStream fis;
		ObjectInputStream inputStream;
		try
		{
			fis = openFileInput(INVENTORY);
			inputStream = new ObjectInputStream(fis);
			MyInventory = (Inventory) inputStream.readObject();
			inputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (MyInventory == null)
			MyInventory = new Inventory();
	}
	
	public void LoadAdventureManager()
	{
		FileInputStream fis;
		ObjectInputStream inputStream;
		try
		{
			fis = openFileInput(ADVENTURE_MANAGER);
			inputStream = new ObjectInputStream(fis);
			MyAdventures = (AdventureManager)inputStream.readObject();
			inputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (MyAdventures == null)
			MyAdventures = new AdventureManager();
	}
	
	public void LoadSettings()
	{
		FileInputStream fis;
		ObjectInputStream inputStream;
		try
		{
			fis = openFileInput(SETTINGS);
			inputStream = new ObjectInputStream(fis);
			_settings = (Settings)inputStream.readObject();
			inputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (_settings == null)
			_settings = new Settings();
	}
	
	public void LoadCodex()
	{
		FileInputStream fis;
		ObjectInputStream inputStream;
		try
		{
			fis = openFileInput(CODEX_FILE);
			inputStream = new ObjectInputStream(fis);
			MyCodex = (Codex)inputStream.readObject();
			inputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if (MyCodex == null)
			MyCodex = new Codex();
	}
	
	private void SaveTrainer()
	{
		FileOutputStream fos;
		ObjectOutputStream outputStream;
		try
		{
			fos = openFileOutput(TRAINER_INFO, Context.MODE_PRIVATE);
			outputStream = new ObjectOutputStream(fos);
			outputStream.writeObject(Trainer);
			outputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void SavePet()
	{
		FileOutputStream fos;
		ObjectOutputStream outputStream;
		try
		{
			fos = openFileOutput(ACTIVE_PETS, Context.MODE_PRIVATE);
			outputStream = new ObjectOutputStream(fos);
			outputStream.writeObject(_activePets);
			outputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void SaveDaycare()
	{
		FileOutputStream fos;
		ObjectOutputStream outputStream;
		try
		{
			fos = openFileOutput(DAYCARE, Context.MODE_PRIVATE);
			outputStream = new ObjectOutputStream(fos);
			outputStream.writeObject(TheDaycare);
			outputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void SaveInventory()
	{
		FileOutputStream fos;
		ObjectOutputStream outputStream;
		try
		{
			fos = openFileOutput(INVENTORY, Context.MODE_PRIVATE);
			outputStream = new ObjectOutputStream(fos);
			outputStream.writeObject(MyInventory);
			outputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void SaveAdventureManager()
	{
		FileOutputStream fos;
		ObjectOutputStream outputStream;
		try
		{
			fos = openFileOutput(ADVENTURE_MANAGER, Context.MODE_PRIVATE);
			outputStream = new ObjectOutputStream(fos);
			outputStream.writeObject(MyAdventures);
			outputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void SaveSettings()
	{
		FileOutputStream fos;
		ObjectOutputStream outputStream;
		try
		{
			fos = openFileOutput(SETTINGS, Context.MODE_PRIVATE);
			outputStream = new ObjectOutputStream(fos);
			outputStream.writeObject(_settings);
			outputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void SaveCodex()
	{
		FileOutputStream fos;
		ObjectOutputStream outputStream;
		try
		{
			fos = openFileOutput(CODEX_FILE, Context.MODE_PRIVATE);
			outputStream = new ObjectOutputStream(fos);
			outputStream.writeObject(MyCodex);
			outputStream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
//////SHARED METHODS ACROSS ACTIVITIES!!!!////////////////////////////////////////////////////////////////////////////
	public void StartRunAndHandle(){
		_appRunnable = new Runnable(){
			@Override
			public void run(){
				for (int i = 0; i < 4; i++){
					if (_activePets[i] == null)
						continue;
					_activePets[i].UpdatePetForm();
					if (_activePets[i].JustEvolved && !notifications[i]){
						if (_settings == null || _settings.NotifyUser)
							NotifyOfPetFormChange(_activePets[i], i);
					}
				}
				_appHandler.postDelayed(this, 60);
			}
		};
		_appHandler = new Handler();
		_appHandler.postDelayed(_appRunnable, 60);
	}
	
	public void StopRunAndHandle(){
		if (_appHandler != null)
			_appHandler.removeCallbacks(_appRunnable);
		_appHandler = null;
		_appRunnable = null;
	}
	
	public void ResetConsecutiveAdventureCounter(boolean allDead, Context context)
	{
		MyAdventures.ConsecutiveAdventureCounter = 0;
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		alert.setTitle("Game Over!");
		alert.setMessage("Gathering your fallen party, you scurry back to the garden to rest.");
		alert.show();
	}
	
	public String AddEggToParty(PixelPet egg){
		String result = "";
		
		egg.ResetEggTimer();
		if (getActivePetCount() < 4){
			for (int i = 1; i < 4; i++){
				if (_activePets[i] == null){
					_activePets[i] = egg;
					break;
				}
			}
			result = "The Mysterious Egg has been added to your PARTY!";
		}else{
			//TODO:: Check for limitation to daycare size...
			TheDaycare.AddToPetStorage(egg);
			result = "The Mysterious Egg has been put into DAYCARE!";
		}
		
		return result;
	}
	
	public void ClearNotifications(boolean clearAll, PixelPet pet)
	{
		NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		if (clearAll){
			mNotifyMgr.cancelAll();
			notifications = new boolean[]{ false, false, false, false};
		}
		else{
			int notifyID = 000;
			for (int i = 0; i < 4; i++){
				if (_activePets[i] == pet)
					notifyID = i;
			}
			mNotifyMgr.cancel(notifyID);
			notifications[notifyID] = false;
		}
	}
	
	public void UpdateNotifyPets(Context context){
		PixelPet[] activePets = getActivePets();
		for (int i = 0; i < 4; i++){
			if (activePets[i] == null) continue;
			AlertDialog.Builder levelUp = null;
			if (activePets[i].Level < activePets[i].MaxLevel && activePets[i].Exp >= activePets[i].ExpToNextLevel)
				levelUp = LevelUp(activePets[i], i, context);
			activePets[i].UpdatePetForm();
			
			if (activePets[i].CurrentForm == PixelPet.PetForm.Primary){
				if (activePets[i].JustEvolved){
					NameYourPet(activePets[i], null, context);
					if (getSettings().NotifyUser)
						NotifyOfPetFormChange(activePets[i], i);
					activePets[i].JustEvolved = false;
				}
			}else if (activePets[i].CurrentForm != PixelPet.PetForm.Primary){
				if (activePets[i].JustEvolved){
					PetEvolved(i, context);
					if (getSettings().NotifyUser)
						NotifyOfPetFormChange(activePets[i], i);
				}
			}
			if (levelUp != null)
				levelUp.show();
		}
		ClearNotifications(true, null);
	}
	
	public void NotifyOfPetFormChange(PixelPet pet, int index)
	{
		MyCodex.AddToPetCodex(pet);
		if (!_settings.NotifyUser) return;
		
		AudioManager audio = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		if (audio.getRingerMode() != AudioManager.RINGER_MODE_SILENT){
			Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			v.vibrate(400); // Vibrate for 400 milliseconds
		}
		

		if (index >= 0)
			notifications[index] = true;
		else return;
		
		String title = "Pet Evolved!";
		String description = pet.Name + " has evolved into " + _activePets[index].Species +"!";
		if (pet.CurrentForm == PixelPet.PetForm.Primary){
			title = "Egg Hatch!";
			description = pet.GetSpeciesAndGender() + " has hatched from an egg!";
		}
		
		NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this);
		notifyBuilder.setSmallIcon(R.drawable.ic_launcher);
		notifyBuilder.setContentTitle(title);
		notifyBuilder.setContentText(description);
		
		Intent resultIntent = new Intent(this, GardenActivity.class);
		resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		resultIntent.putExtra("com.cakeandturtles.pixelpets.petIndex", index);
		PendingIntent resultPendingIntent = PendingIntent.getActivity(this, index, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notifyBuilder.setContentIntent(resultPendingIntent);
		
		NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNotifyMgr.notify(index, notifyBuilder.build());
	}
	
	public void NameYourPet(final PixelPet activePet, View view, Context context)
	{
		if (activePet.CurrentForm == PixelPet.PetForm.Egg){
			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.cancel();
				}
			});
			alert.setTitle("Not Hatched");
			alert.setMessage("The egg has not yet hatched, so you cannot name it yet!!");
			alert.show();
			return;
		}
		activePet.HaveIBeenNamed = false;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Name your Pet");
		final View nameView = LayoutInflater.from(context).inflate(R.layout.name_dialog, null);
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
		((ImageView)nameView.findViewById(R.id.name_image_area)).setImageResource(activePet.GetDrawableId(false));
		((ImageView)nameView.findViewById(R.id.name_image_area)).scrollTo((activePet.RelAniX)*size, activePet.RelAniY * size);
		
		final TextView nameText = ((TextView)nameView.findViewById(R.id.name_text_area));
		if (view == null)
			nameText.setText(String.format("A %s has hatched!\nName your new %s?", activePet.Species, activePet.GetSpeciesAndGender()));
		else
			nameText.setText(String.format("Do you wish to rename \n%s the %s?", activePet.Name, activePet.GetSpeciesAndGender()));
		final EditText nameEdit = ((EditText)nameView.findViewById(R.id.name_edit_area));
		
		int maxLength = 12;
		InputFilter[] fArray = new InputFilter[1];
		fArray[0] = new InputFilter.LengthFilter(maxLength);
		nameEdit.setFilters(fArray);
		nameEdit.setText(activePet.Name);
		nameEdit.setImeActionLabel("Name", KeyEvent.KEYCODE_ENTER);
		TextView.OnEditorActionListener nameListener = new TextView.OnEditorActionListener(){
			public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
			   if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) { 
				   String name = nameEdit.getText().toString();
					if (name == null || name.trim().isEmpty())
						name = activePet.Species;
					
					nameEdit.setText(name);
					// hide virtual keyboard
		            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		            imm.hideSoftInputFromWindow(nameEdit.getWindowToken(), 0);
			   }
			   return true;
			}
		};
		nameEdit.setOnEditorActionListener(nameListener);
		
		builder.setView(nameView);
		builder.setPositiveButton("Name Pet", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				String name = nameEdit.getText().toString();
				if (name == null || name.trim().isEmpty())
					activePet.Name = activePet.Species;
				else 
					activePet.Name = name;
				dialog.cancel();
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		AlertDialog nameDialog = builder.create(); 
		nameDialog.setOnDismissListener(new OnDismissListener(){
			@Override
			public void onDismiss(final DialogInterface dialog) {
				activePet.HaveIBeenNamed = true;
				SaveUser();
				dialog.dismiss();
			}
		});
	
		nameDialog.show();
	}
	
	public void PetEvolved(final int index, Context context)
	{
		PixelPet prevPet = _activePets[index];
		if (prevPet.CurrentForm == PixelPet.PetForm.Egg)
			return;
		int hp = prevPet.BaseHP;
		int attack = prevPet.BaseAttack;
		int speed = prevPet.BaseSpeed;
		int defense = prevPet.BaseDefense;
		PixelPet newPet = prevPet.Evolve();
		MyCodex.AddToPetCodex(newPet);
		newPet.EvolveFrom(prevPet);
		if (hp > newPet.BaseHP) newPet.BaseHP = hp;
		if (attack > newPet.BaseAttack) newPet.BaseAttack = attack;
		if (speed > newPet.BaseSpeed) newPet.BaseSpeed = speed;
		if (defense > newPet.BaseDefense) newPet.BaseDefense = defense;
		_activePets[index] = newPet;
		for (int i = 0; i < newPet.LevelAttackList.size(); i++){
			if (newPet.LevelAttackList.get(i).Level == newPet.Level){
				TryLearnNewAttack(newPet.LevelAttackList.get(i).Attack, newPet, index, context);
			}
		}
		
		String message = prevPet.Name + " has GROWN into " + newPet.Species +"!\n";
		message += "HP: +" + (newPet.BaseHP - hp);
		message += "\tSpd: +" + (newPet.BaseSpeed - speed);
		message += "\nAtk: +" + (newPet.BaseAttack - attack);
		message += "\tDef: +" + (newPet.BaseDefense - defense);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Metamorphosis!");
		final View evolveView = LayoutInflater.from(context).inflate(R.layout.item_dialog, null);
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
		((ImageView)evolveView.findViewById(R.id.item_image_area)).scrollTo((newPet.RelAniX)*size, newPet.RelAniY * size);
		((ImageView)evolveView.findViewById(R.id.item_image_area)).setImageResource(newPet.GetDrawableId(false));
		
		((TextView)evolveView.findViewById(R.id.item_text_area)).setText(message);
		
		
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		builder.setView(evolveView);
		builder.show();
	}
	
	public AlertDialog.Builder LevelUp(PixelPet levelPet, int index, Context context){
		int hp = levelPet.BaseHP;
		int attack = levelPet.BaseAttack;
		int speed = levelPet.BaseSpeed;
		int defense = levelPet.BaseDefense;
		if (levelPet.Level >= levelPet.MaxLevel || levelPet.Exp < levelPet.ExpToNextLevel) 
			return null;
		while (levelPet.Exp >= levelPet.ExpToNextLevel){
			levelPet.LevelUp();
			for (int i = 0; i < levelPet.LevelAttackList.size(); i++){
				if (levelPet.LevelAttackList.get(i).Level == levelPet.Level){
					TryLearnNewAttack(levelPet.LevelAttackList.get(i).Attack, levelPet, index, context);
				}
			}
		}
		if (hp > levelPet.BaseHP) levelPet.BaseHP = hp;
		if (attack > levelPet.BaseAttack) levelPet.BaseAttack = attack;
		if (speed > levelPet.BaseSpeed) levelPet.BaseSpeed = speed;
		if (defense > levelPet.BaseDefense) levelPet.BaseDefense = defense;
		levelPet.HP += (levelPet.BaseHP - hp);
		
		String message = levelPet.Name + " is now Level " + levelPet.Level + "!\n";
		message += "HP: +" + (levelPet.BaseHP - hp);
		message += "\tSpd: +" + (levelPet.BaseSpeed - speed);
		message += "\nAtk: +" + (levelPet.BaseAttack - attack);
		message += "\tDef: +" + (levelPet.BaseDefense - defense);
		
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		alert.setTitle("Level up!");
		alert.setMessage(message);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		return alert;
	}
	
	public void TryLearnNewAttack(final Attack attack, final PixelPet pet, final int index, final Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		String message = "<b>" + attack.Name + ":</b> (Type: " + attack.AttackType.toString() + ")";
		message += "<br/><i>\"" + attack.Description;
		message += "\"</i><br/><br/>&nbsp;<b>Power:</b> &nbsp;";
		if (attack.BasePower > 0) message += attack.BasePower;
		else if (attack.BasePower == 0) message += "---";
		else message += "???";
		message += "<br/><b># Uses:</b> &nbsp;" + attack.NumUses + "/" + attack.BaseNumUses;
		
		boolean hasSpace = false;
		for (int i = 0; i < 4; i++){
			if (pet.Attacks[i] == null){
				hasSpace = true;
				pet.Attacks[i] = attack;

				builder.setTitle(pet.Name + " learned new Attack!");
				builder.setNegativeButton("Ok", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						dialog.cancel();
					}
				});
				break;
			}
		}
		if (!hasSpace){
			builder.setTitle(pet.Name + " can learn new Attack!");
			message += "<br/><br/>Do you want to forget a move to learn " + attack.Name + "?";
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					learnNewAttack = attack;
					Intent intent = new Intent(context, LearnNewMoveActivity.class);
					intent.putExtra("com.cakeandturtles.pixelpets.petIndex", index);
					context.startActivity(intent);
				}
			});
			builder.setCancelable(false);
		}
		
		builder.setMessage(Html.fromHtml(message));
		
		builder.show();
	}
	
	public void TryWithdraw(final int index, final Context context){
		final PixelPet pet = TheDaycare.GetStoredPets().get(index);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		
		if (getActivePetCount() < 4){
			for (int i = 0; i < 4; i++){
				if (_activePets[i] == null){
					_activePets[i] = TheDaycare.WithdrawFromStorage(pet);
					break;
				}
			}
			
			final View withdrawView = LayoutInflater.from(context).inflate(R.layout.item_dialog, null);
			Resources r = getResources();
			int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
			((ImageView)withdrawView.findViewById(R.id.item_image_area)).scrollTo((pet.RelAniX)*size, pet.RelAniY * size);
			((ImageView)withdrawView.findViewById(R.id.item_image_area)).setImageResource(pet.GetDrawableId(false));
			
			String message = "";
			if (pet.CurrentForm == PixelPet.PetForm.Egg){
				builder.setTitle("Party Egg!");
				message = "??? the Mysterious Egg has been added to your active party.";
				pet.ResetEggTimer();
			}else{
				builder.setTitle("Party, "+pet.Name + "!");
				message = pet.Name + " the " + pet.GetSpeciesAndGender() + " has been added to your active party.";
			}
			((TextView)withdrawView.findViewById(R.id.item_text_area)).setText(message);
			builder.setNegativeButton("Ok", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.cancel();
				}
			});
			builder.setView(withdrawView);
		}else{
			builder.setTitle("Party Full");
			builder.setMessage("Your party is full, and you cannot take any more pets with you!");
			builder.setNegativeButton("Ok..", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.cancel();
				}
			});
		}
		builder.show();
	}
	
	public void TryDeposit(final int index, final Context context){
		final PixelPet pet = _activePets[index];
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (!TheDaycare.IsDaycareFull()){
			final View depositView = LayoutInflater.from(context).inflate(R.layout.item_dialog, null);
			Resources r = getResources();
			int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
			((ImageView)depositView.findViewById(R.id.item_image_area)).scrollTo((pet.RelAniX)*size, pet.RelAniY * size);
			((ImageView)depositView.findViewById(R.id.item_image_area)).setImageResource(pet.GetDrawableId(false));
			String message = "Do you want to deposit "+pet.Name+" the "+pet.GetSpeciesAndGender()+" into the Daycare?";
			
			if (pet.CurrentForm == PetForm.Egg){
				message = "Deposit the mysterious egg into daycare?\n\n(It might hatch soon if you keep it with you...)";
			}
			if (getActivePetCount() > 1){
				builder.setTitle("Deposit Pet?");
				((TextView)depositView.findViewById(R.id.item_text_area)).setText(message);
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						TheDaycare.AddToPetStorage(pet);
						setActivePet(null, index);
						ShiftActivePetsToCorrectSpots();
						dialog.cancel();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						dialog.cancel();
					}
				});
			}else{
				builder.setTitle("Last Pet");
				message = "You cannot deposit this pet, as you must have at least one in your party!";
				((TextView)depositView.findViewById(R.id.item_text_area)).setText(message);
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which){
						dialog.cancel();
					}
				});
			}
			builder.setView(depositView);
		}else{
			builder.setTitle("Daycare Full");
			builder.setMessage("The Daycare is full, and you cannot store anymore pets in it!\n\nDo you want to Swap a pet from the Daycare with this pet?");
			builder.setPositiveButton("Swap", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					Intent intent = new Intent(context, DaycareActivity.class);
					intent.putExtra("com.cakeandturtles.pixelpets.swap", true);
					context.startActivity(intent);
				}
			});
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.cancel();
				}
			});
		}
		builder.show();
	}
		
	public void TryRelease(final int index, final Context context, final boolean isInDaycare){
		PixelPet getPet;
		if (isInDaycare)
			getPet = TheDaycare.LocateInStorage(index);
		else getPet = _activePets[index];
		final PixelPet pet = getPet;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		final View releaseView = LayoutInflater.from(context).inflate(R.layout.item_dialog, null);
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
		((ImageView)releaseView.findViewById(R.id.item_image_area)).scrollTo((pet.RelAniX)*size, pet.RelAniY * size);
		((ImageView)releaseView.findViewById(R.id.item_image_area)).setImageResource(pet.GetDrawableId(false));
		
		if (pet.CurrentForm == PetForm.Egg){
			builder.setTitle("Release Egg?");
			String message = "Are you sure you wish to release the Mysterious Egg?\n\nWARNING: This is permanent and you cannot get EGG back!";
			((TextView)releaseView.findViewById(R.id.item_text_area)).setText(message);
			builder.setPositiveButton("Yes...", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					if (isInDaycare)
						TheDaycare.ReleaseFromStorage(pet);
					else{
						setActivePet(null, index);
						ShiftActivePetsToCorrectSpots();
					}
					dialog.cancel();
					GoodbyePet(pet, context);
				}
			});
			builder.setNegativeButton("No!!!", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.cancel();
				}
			});
		}
		else if (getActivePetCount() > 1 || isInDaycare){
			builder.setTitle("Release Pet?");
			String message = "Are you sure you wish to release "+pet.Name+" the "+pet.GetSpeciesAndGender()+"?\n\nWARNING: This is permanent and you cannot get "+pet.Name+" back!";
			((TextView)releaseView.findViewById(R.id.item_text_area)).setText(message);
			builder.setPositiveButton("Yes...", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					if (isInDaycare)
						TheDaycare.ReleaseFromStorage(pet);
					else{
						setActivePet(null, index);
						ShiftActivePetsToCorrectSpots();
					}
					dialog.cancel();
					GoodbyePet(pet, context);
				}
			});
			builder.setNegativeButton("No!!!", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.cancel();
				}
			});
		}else{
			builder.setTitle("Last Pet");
			String message = "You cannot release this pet, as it is your last one!";
			((TextView)releaseView.findViewById(R.id.item_text_area)).setText(message);
			builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					dialog.cancel();
				}
			});
		}
		builder.setView(releaseView);
		builder.show();
	}
	
	public void GoodbyePet(final PixelPet pet, Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		final View goodbyeView = LayoutInflater.from(context).inflate(R.layout.item_dialog, null);
		Resources r = getResources();
		int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 128, r.getDisplayMetrics());
		((ImageView)goodbyeView.findViewById(R.id.item_image_area)).scrollTo((pet.RelAniX)*size, pet.RelAniY * size);
		((ImageView)goodbyeView.findViewById(R.id.item_image_area)).setImageResource(pet.GetDrawableId(false));
		
		String message = "";
		String negativeButtonText = "Goodbye "+pet.Name+"!";
		
		if (pet.CurrentForm == PixelPet.PetForm.Egg){
			builder.setTitle("Goodbye Forever, Egg");
			pet.RandomizePersonalityAttributes();
			message = "You place the egg back where you found it.\n\nA motherly looking " + pet.Evolve().Evolve().Species + " approaches it as you leave.";
			negativeButtonText = "Goodbye Mysterious Egg!";
		}else{
			builder.setTitle("Goodbye Forever, "+pet.Name);
			message = pet.Name + " is released back into the wild..\n\n"+pet.He(true)+" will never forget your time together!";
		}
		((TextView)goodbyeView.findViewById(R.id.item_text_area)).setText(message);
		builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which){
				dialog.cancel();
			}
		});
		builder.setView(goodbyeView);
		builder.show();
	}
}
