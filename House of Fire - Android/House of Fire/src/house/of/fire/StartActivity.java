package house.of.fire;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class StartActivity extends Activity{
	
	private final static String TAG = StartActivity.class.getSimpleName();
	
	public final static String PREF_MEDAL_BRONZE = "bronze";
	public final static String PREF_MEDAL_SILVER = "silver";
	public final static String PREF_MEDAL_GOLD = "gold";
	
	private final static String PREF_FIRST_TIME_USER = "first_time_user";
		
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
	}
	
		
	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int bronzeMedal = prefs.getInt(PREF_MEDAL_BRONZE, 0);
		int silverMedal = prefs.getInt(PREF_MEDAL_SILVER, 0);
		int goldMedal = prefs.getInt(PREF_MEDAL_GOLD, 0);
		
		boolean firstTime = prefs.getBoolean(PREF_FIRST_TIME_USER, true);
		
		if (firstTime){
			createFirstTimeUserDialog().show();
		}
		
		TextView textView = (TextView) findViewById(R.id.gold_text);
		textView.setText(getString(R.string.gold_text, goldMedal));
		
		textView = (TextView) findViewById(R.id.silver_text);
		textView.setText(getString(R.string.silver_text, silverMedal));
		
		textView = (TextView) findViewById(R.id.bronze_text);
		textView.setText(getString(R.string.bronze_text, bronzeMedal));
	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
	}


	public void startClicked(View view){
		startActivity(new Intent(StartActivity.this, LogInActivity.class));
	}
	
	public void instructionsClicked(View view){
		startActivity(new Intent(StartActivity.this, InstructionsActivity.class));
	}
	
	public Dialog createFirstTimeUserDialog(){
		View view = LayoutInflater.from(StartActivity.this).inflate(R.layout.dialog_first_time, null);
		CheckBox cb = (CheckBox) view.findViewById(R.id.again_cb);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(StartActivity.this);
					prefs.edit().putBoolean(PREF_FIRST_TIME_USER, false).commit();
				}
			}
		});
		
		return new AlertDialog.Builder(StartActivity.this)
			.setTitle(R.string.app_name)
			.setView(view)
			.setPositiveButton("OK", null)
			.setNegativeButton(R.string.wlan_settings, new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					try {
						startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
					} catch (ActivityNotFoundException e) {
						e.printStackTrace();
					}
				}
			})
		.create();
	}

}