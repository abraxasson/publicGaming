package house.of.fire;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class StartActivity extends Activity{
	
	private final static String TAG = StartActivity.class.getSimpleName();
	
	public final static String PREF_MEDAL_BRONZE = "bronze";
	public final static String PREF_MEDAL_SILVER = "silver";
	public final static String PREF_MEDAL_GOLD = "gold";
		
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
		
		TextView textView = (TextView) findViewById(R.id.bronze_text);
		textView.setText(getString(R.string.bronze_text, bronzeMedal));
		
		textView = (TextView) findViewById(R.id.silver_text);
		textView.setText(getString(R.string.silver_text, silverMedal));
		
		textView = (TextView) findViewById(R.id.gold_text);
		textView.setText(getString(R.string.gold_text, goldMedal));
	}



	public void startClicked(View view){
		startActivity(new Intent(StartActivity.this, LogInActivity.class));
	}
	
	public void instructionsClicked(View view){
		startActivity(new Intent(StartActivity.this, InstructionsActivity.class));
	}   

}