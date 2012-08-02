package house.of.fire;

import hof.net.android.AndroidServer;
import hof.net.userMessages.LevelInfoMessage;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LevelActivity extends Activity {
	
	public static final String EXTRA_LEVEL = "level";
	public static final String EXTRA_MEDAL= "medal";
	public static final String EXTRA_LASTLEVEL= "lastlevel";
	public static boolean isActive;
	public static boolean level_isActive;
	
	TextView outputText;
	boolean lastLevel = false;
	
	
	AndroidServer server;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        
        outputText = (TextView) findViewById(R.id.outputText_level);
        
    }



	@Override
	protected void onStart() {
		super.onStart();
		
		server = AndroidServer.getInstance(this, AndroidServer.PORT);
		Intent intent = getIntent();
		
		isActive = true;
		level_isActive = false;
		
		if (intent != null){
			int level = intent.getIntExtra(EXTRA_LEVEL, 1);
			outputText.setText(getString(R.string.level_completed, level));
			
			lastLevel = intent.getBooleanExtra(EXTRA_LASTLEVEL, false);
			
			int medalType = intent.getIntExtra(EXTRA_MEDAL, LevelInfoMessage.NO_MEDAL);
			
			if (medalType != LevelInfoMessage.NO_MEDAL){
				saveNewMedalState(medalType);
				
				// if there is a medal -> show it (load the proper medal image)
				ImageView medalView = (ImageView) findViewById(R.id.medal_imageview);
				RelativeLayout rLayout = (RelativeLayout) findViewById (R.id.RelativeLayoutLevel);


				
				if (medalType == LevelInfoMessage.GOLD_MEDAL){
//					findViewById(R.layout.activity_level).setBackgroundResource(R.drawable.background_fire);
					rLayout.setBackgroundResource(R.drawable.house3);
					medalView.setImageResource(R.drawable.gold);
					outputText.setText(getString(R.string.gold_outputText));


				}
				else if (medalType == LevelInfoMessage.SILVER_MEDAL){
//					findViewById(R.layout.activity_level).setBackgroundResource(R.drawable.background_water);
					rLayout.setBackgroundResource(R.drawable.house2);
					medalView.setImageResource(R.drawable.silber);
//					rLayout.setBackground(drawable2);
					outputText.setText(getString(R.string.silver_outputText));

				}
				else if (medalType == LevelInfoMessage.BRONZE_MEDAL){
					medalView.setImageResource(R.drawable.bronze);
					outputText.setText(getString(R.string.bronze_outputText));
				}
				
			}
			else {
				// hide medal view
				findViewById(R.id.medal_imageview).setVisibility(View.GONE);
			}
		}
	}
	
	



	@Override
	protected void onStop() {
		super.onStop();
		isActive = false;
		server.close();
	}



	private void saveNewMedalState(int medalType) {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit = prefs.edit();
		
		if (medalType == LevelInfoMessage.GOLD_MEDAL){
			int goldMedal = prefs.getInt(StartActivity.PREF_MEDAL_GOLD, 0);
			goldMedal++;
			edit.putInt(StartActivity.PREF_MEDAL_GOLD, goldMedal);
			edit.commit();
		}
		else if (medalType == LevelInfoMessage.SILVER_MEDAL){
			int silverMedal = prefs.getInt(StartActivity.PREF_MEDAL_SILVER, 0);
			silverMedal++;
			edit.putInt(StartActivity.PREF_MEDAL_SILVER, silverMedal);
			edit.commit();
		}
		else if (medalType == LevelInfoMessage.BRONZE_MEDAL){
			int bronzeMedal = prefs.getInt(StartActivity.PREF_MEDAL_BRONZE, 0);
			bronzeMedal++;
			edit.putInt(StartActivity.PREF_MEDAL_BRONZE, bronzeMedal);
			edit.commit();
		}
		
	}

	
	@Override
	public void onBackPressed() {
		if (lastLevel){
			Intent intent = new Intent(LevelActivity.this, StartActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent);
		}
		else {
			level_isActive = true;
			Intent intent = new Intent(LevelActivity.this, ControllerActivity.class);
//			intent.putExtra(ControllerActivity.EXTRA_WATER_LEVEL, ControllerActivity.MAX_WATER_LEVEL);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent);
		}
		
	}

    
}
