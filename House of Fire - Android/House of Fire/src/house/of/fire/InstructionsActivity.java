package house.of.fire;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class InstructionsActivity extends Activity {
	
	private final static String TAG = InstructionsActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
    }
    
    public static String getTag() {
		return TAG;
	}
}
