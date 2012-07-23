package house.of.fire;

import hof.net.android.AndroidServer;
import hof.net.android.SSDPNetworkClient;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LogInActivity extends Activity {
	
	
	public final static String PREF_PLAYER_NAME = "playerName";
	public final static String PREF_PLAYER_COLOR = "playerColor";

	private AndroidServer server;
	private SSDPNetworkClient explorer;
	EditText nameEditText;
	TextView outputText;
	Button playButton;
	
	
	String playerName;
	int playerColor;
	
	//ProgressDialog dialog = ProgressDialog.show(LogInActivity.this, "", 
            //"Loading. Please wait...", true);
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		nameEditText = (EditText) findViewById(R.id.nameEditText);
		outputText = (TextView) findViewById(R.id.output_text);
		playButton = (Button) findViewById(R.id.button_play);
		playButton.setEnabled(false);
		
		
		nameEditText.addTextChangedListener(new TextWatcher() {
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				//if (nameEditText.length() >= 12){
					//String text = nameEditText.getEditableText().toString();
					//text = text.substring(0, text.length()-2);
					//s = "";
				//}
				
			}
			
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				//if (nameEditText.length() >= 12){
					//String text = nameEditText.getEditableText().toString();
					//text = text.substring(0, text.length()-2);
					//s = "";
				//}
			}
			
			public void afterTextChanged(Editable s) {
				if (nameEditText.length() > 0) {
					playButton.setEnabled(true);
				}

				else {
					playButton.setEnabled(false);
				}
				
				
				
			}
		});
	}
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
		playerName = prefs.getString(PREF_PLAYER_NAME, "");
		nameEditText.setText(playerName);
		
		
		server = new AndroidServer(4711);
		server.start();
		
//		udpClient =  new UdpClientThread();
//		try {
//			udpClient.setIa(InetAddress.getLocalHost());
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
//		udpClient.start();
//		udpClient.sendObject(new PlayerInfoMessage("Marcel"));
	}



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		server.setActive(false);
		super.onStop();
		
		//udpClient.setActive(false);	
		
	}
	
	
	public void onLogInButtonClicked(View view) {
		
		playerName = nameEditText.getText().toString();
		
		SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = prefs.edit();
		editor.putString(PREF_PLAYER_NAME, playerName);
		editor.commit();
		
		Log.d("login", playerName);
		
		
		//ProgressDialog.show(getApplicationContext(), PREF_PLAYER_NAME, "Bitte warten...");
		
		
	
		
		explorer = new SSDPNetworkClient(playerName);
		explorer.start();
		
//		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//		alertDialog.setTitle(playerName);
//		alertDialog.setMessage("Bitte warten ...");
//		alertDialog.show();
		
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Bitte warten...");
		progressDialog.show();
		startGame(this);
		
		//startActivity(new Intent(LogInActivity.this, ControllerActivity.class));
		
		
		
//		int z = 0;
//		while(!server.getValidation()){
//			alertDialog.show();
//			z+=1;
//			if(z==20000000){
//				break;
//				
//			}
//		}
		
		// TODO save playerColor in prefs
		
		
		
	}
	
	public void startGame(Context context){
		// TODO cancel waiting dialog (AlertDialog)
		Intent intent = new Intent(LogInActivity.this, ControllerActivity.class);
		context.startActivity(intent);
	}

}
