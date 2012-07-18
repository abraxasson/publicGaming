package house.of.fire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartActivity extends Activity{
	
	Button button_play;
	Button button_instructions;

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        
        button_play = (Button) findViewById(R.id.button_play);
        button_instructions = (Button) findViewById(R.id.button_instructions);
        
        button_play.setOnClickListener(buttonPlayListener);
        button_instructions.setOnClickListener(buttonInstructionsListener);
        
	}
	
	
	private OnClickListener buttonPlayListener = new OnClickListener() {
		public void onClick (View v) {
			startActivity(new Intent(StartActivity.this, LogInActivity.class));
		}
		
	};
	
	private OnClickListener buttonInstructionsListener = new OnClickListener() {
		public void onClick (View v) {
			startActivity(new Intent(StartActivity.this, InstructionsActivity.class));
		}
	};
        

}