package house.of.fire;

import hof.net.userMessages.LevelInfoMessage;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelActivity extends Activity {
	
	public static final String EXTRA_LEVEL = "level";
	public static final String EXTRA_MEDAL= "medal";
	
	Context context;
	TextView outputText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        
        outputText = (TextView) findViewById(R.id.outputText_level);
    }



	@Override
	protected void onStart() {
		super.onStart();
		
		int level = getIntent().getIntExtra(EXTRA_LEVEL, 1);
		outputText.setText(getString(R.string.level_completed, level));
		
		int medalType = getIntent().getIntExtra(EXTRA_MEDAL, LevelInfoMessage.NO_MEDAL);
		
		if (medalType != LevelInfoMessage.NO_MEDAL){
			saveNewMedalState(medalType);
			
			// if there is a medal -> show it (load the proper medal image)
			ImageView medalView = (ImageView) findViewById(R.id.medal_imageview);
			
			if (medalType == LevelInfoMessage.GOLD_MEDAL){
				medalView.setImageResource(R.drawable.gold);
				outputText.setText("Goldmedaille erhalten!");
			}
			else if (medalType == LevelInfoMessage.SILVER_MEDAL){
				medalView.setImageResource(R.drawable.silber);
				outputText.setText("Silbermedaille erhalten!");
			}
			else if (medalType == LevelInfoMessage.BRONZE_MEDAL){
				medalView.setImageResource(R.drawable.bronze);
				outputText.setText("Bronzemedaille erhalten!");
			}
			
		}
		else {
			// hide medal view
			findViewById(R.id.medal_imageview).setVisibility(View.GONE);
		}
		
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
			edit.putInt(StartActivity.PREF_MEDAL_GOLD, silverMedal);
			edit.commit();
		}
		else if (medalType == LevelInfoMessage.BRONZE_MEDAL){
			int bronzeMedal = prefs.getInt(StartActivity.PREF_MEDAL_BRONZE, 0);
			bronzeMedal++;
			edit.putInt(StartActivity.PREF_MEDAL_GOLD, bronzeMedal);
			edit.commit();
		}
		
	}

	@Override
	protected void onStop() {
		
		super.onStop();
	}
	
	@Override
	public void onBackPressed() {
		context.startActivity(new Intent(context, ControllerActivity.class));
		super.onBackPressed();
	}

    
}
