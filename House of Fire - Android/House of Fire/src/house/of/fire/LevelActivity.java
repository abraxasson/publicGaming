package house.of.fire;

import hof.net.android.AndroidServer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class LevelActivity extends Activity {
	
	Context context;
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
		outputText.setText("Sie haben Level " + AndroidServer.level + " geschafft!");
		super.onStart();
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
