package house.of.fire;

import android.app.Activity;
import android.os.Bundle;

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
