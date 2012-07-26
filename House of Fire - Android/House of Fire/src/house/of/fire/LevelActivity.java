package house.of.fire;

import hof.net.android.AndroidServer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class LevelActivity extends Activity {
	
	TextView outputText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        
        outputText = (TextView) findViewById(R.id.outputText_level);
    }

	@Override
	protected void onPause() {
		
		super.onPause();
	}

	@Override
	protected void onResume() {
		
		super.onResume();
	}

	@Override
	protected void onStart() {
		outputText.setText("Sie haben Level " + AndroidServer.level + " " + "geschafft!");
		super.onStart();
	}

	@Override
	protected void onStop() {
		
		super.onStop();
	}

    
}
