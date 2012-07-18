package house.of.fire;

import hof.net.android.AndroidServer;
import hof.net.userMessages.PlayerInfoMessage;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class LogInActivity extends Activity {
	
	
	public final static String EXTRA_PLAYER_NAME = "playerName";
	public final static String EXTRA_PLAYER_COLOR = "playerColor";

	private AndroidServer server;
	private UdpClientThread udpClient;
	EditText nameEditText;
	TextView outputText;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		nameEditText = (EditText) findViewById(R.id.nameEditText);
		outputText = (TextView) findViewById(R.id.output_text);
	}
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		//server = new AndroidServer();
		//server.start();
		
		//udpClient =  new UdpClientThread();
		//udpClient.start();
		
		
	}



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		//server.setActive(false);
		//udpClient.setActive(false);	
		
	}
	
	
	public void onLogInButtonClicked(View view) {
		
		String playerName = nameEditText.getText().toString();
		if(playerName.length() <= 0) {
			//outputText.setText("Sie haben keinen Namen eingegeben!");
		}
		else if(playerName.length() <=2) {
			outputText.setText("Ihr Name ist zu kurz!");
		}
		else if(playerName.length() > 12){
			outputText.setText("Ihr Name ist zu lang!");
		}
		else{
		Log.d("login", playerName);
		outputText.setText("");
		//udpClient.sendMessage(name);
		//udpClient.sendObject(new PlayerInfoMessage(playerName));
		
		Intent intent = new Intent(LogInActivity.this, ControllerActivity.class);
		//intent.putExtra(EXTRA_PLAYER_COLOR, Color.RED);
		intent.putExtra(EXTRA_PLAYER_NAME, playerName);
		startActivity(intent);
		}
	}

}
