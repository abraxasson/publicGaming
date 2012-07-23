package house.of.fire;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.view.View.OnClickListener;

public class InstructionsActivity extends Activity {
	
	private final static String TAG = InstructionsActivity.class.getSimpleName();
	
	Button button_next;
	TextView outputText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        
        button_next = (Button) findViewById(R.id.button_next);
        outputText = (TextView) findViewById(R.id.output_instructions);
       
        button_next.setOnClickListener(buttonNextListener);
        outputText.setText("Hier sollte die Anleitung stehen!");
    }
    
    private OnClickListener buttonNextListener = new OnClickListener() {
    	public void onClick(View v) {
    		startActivity(new Intent(InstructionsActivity.this, LogInActivity.class));
    	}
    };

    
}
