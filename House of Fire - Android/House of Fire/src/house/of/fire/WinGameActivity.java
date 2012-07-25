package house.of.fire;

import hof.net.android.AndroidServer;
import hof.net.userMessages.LevelInfoMessage;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class WinGameActivity extends Activity {

	private Context context;
	
	public static AlertDialog alertDialog;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wingame);
        
        
        
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
		if (AndroidServer.level < 6){
			alertDialog = new AlertDialog.Builder(context).create();
			alertDialog.setTitle("Herzlichen Glückwunsch!");
			alertDialog.setMessage("Sie sind in Level" + AndroidServer.level + " aufgestiegen.");
			alertDialog.show();
		}
		else if (AndroidServer.event == LevelInfoMessage.FINISHED){
			if (alertDialog != null){
				alertDialog.dismiss();
			}
		}
		else{
		super.onStart();
		}
	}

	@Override
	protected void onStop() {
		
		super.onStop();
	}
	
	public void finish() {
		if (AndroidServer.level >= 6){
		context.startActivity(new Intent(context, LogInActivity.class));
		}
		else{
		}
	}

    
}
