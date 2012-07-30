package house.of.fire;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameOverActivity extends Activity {
	
//	private Context context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lostgame);
    }
    

	public void retryButtonClicked(View view){
		Intent intent = new Intent(GameOverActivity.this,
				LogInActivity.class);
//		Intent intent = new Intent(context, LogInActivity.class); 
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {
	}

	
}
