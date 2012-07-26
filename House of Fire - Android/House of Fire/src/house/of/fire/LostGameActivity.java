package house.of.fire;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class LostGameActivity extends Activity {
	
	private Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostgame);
        
 
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

		super.onStart();
	}

	@Override
	protected void onStop() {
//		context.startActivity(new Intent(context, LogInActivity.class));
		super.onStop();
		
	}

//	@Override
//	public void onBackPressed() {
//		context.startActivity(new Intent(context, LogInActivity.class));
//		super.onBackPressed();
//	}

//	@Override
//	public void finishActivity(int requestCode) {
//		// TODO Auto-generated method stub
//		super.finishActivity(requestCode);
//	}
	
//	public void finish() {
//		context.startActivity(new Intent(context, LogInActivity.class));
//	}

	
}
