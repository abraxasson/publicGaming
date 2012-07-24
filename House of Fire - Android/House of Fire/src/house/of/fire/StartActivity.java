package house.of.fire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity{
	
	private final static String TAG = StartActivity.class.getSimpleName();
		
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
	}
	
	public void startClicked(View view){
		startActivity(new Intent(StartActivity.this, LogInActivity.class));
	}
	
	public void instructionsClicked(View view){
		startActivity(new Intent(StartActivity.this, InstructionsActivity.class));
	}   

}