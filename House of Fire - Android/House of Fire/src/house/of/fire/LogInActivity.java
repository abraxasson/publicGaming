package house.of.fire;

import hof.net.android.AndroidServer;
import hof.net.userMessages.LogoutInfoMessage;
import hof.net.userMessages.PlayerInfoMessage;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogInActivity extends Activity {

	private final static String TAG = LogInActivity.class.getSimpleName();

	public final static String PREF_PLAYER_NAME = "playerName";

	EditText nameEditText;
	TextView outputText;
	Button playButton;

	String playerName;
	int playerColor;
	ProgressDialog progressDialog;

	UdpClientThread udpClient;
	AndroidServer server;
	
	Timer timer;
	Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		nameEditText = (EditText) findViewById(R.id.nameEditText);
		outputText = (TextView) findViewById(R.id.output_text);
		playButton = (Button) findViewById(R.id.button_play);
		playButton.setEnabled(false);

		nameEditText.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
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
		
		handler = new Handler();
		
		
	}

	@Override
	protected void onStart() {

		super.onStart();
		
		server = AndroidServer.getInstance(this, AndroidServer.PORT);
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		playerName = prefs.getString(PREF_PLAYER_NAME, "");
		nameEditText.setText(playerName);

		udpClient = new UdpClientThread();
		udpClient.start();

	}

	@Override
	protected void onStop() {
		super.onStop();
		
		if (timer != null){
			timer.cancel();
		}
		
		if (progressDialog != null){
			progressDialog.dismiss();
		}
		udpClient.setActive(false);
		

		
		server.close();
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void onLogInButtonClicked(View view) {

		playerName = nameEditText.getText().toString();
		if (playerName.length() > 8){
			playerName = playerName.substring(0, 8);
		}

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = prefs.edit();
		editor.putString(PREF_PLAYER_NAME, playerName);
		editor.commit();

		Log.d(TAG, "Player: " + playerName + " meldet sich an");

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(getString(R.string.wait));
		progressDialog.setCancelable(true);

		progressDialog.setOnCancelListener(new OnCancelListener() {

			public void onCancel(DialogInterface dialog) {
				if (timer != null){
					timer.cancel();
				}
				Log.d(TAG, "Abbruch");
				udpClient.sendObject(new LogoutInfoMessage());
				
			}
		});
		progressDialog.show();
		
		//Intent gameIntent = new Intent(this, ControllerActivity.class);
		//gameIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//startActivity(gameIntent);
		
		udpClient.sendObject(new PlayerInfoMessage(playerName));
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				handler.post(new Runnable() {
					
					public void run() {
						progressDialog.dismiss();
						createServerNotFoundDialog().show();
					}
				});
				
			}
		}, 5000);
		
	}
	
	public Dialog createServerNotFoundDialog(){
		return new AlertDialog.Builder(LogInActivity.this)
			.setTitle(R.string.server_not_found_title)
			.setMessage(R.string.server_not_found_msg)
			.setPositiveButton("OK", null)
			.setNegativeButton(R.string.wlan_settings, new OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					try {
						startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
					} catch (ActivityNotFoundException e) {
						e.printStackTrace();
					}
				}
			})
		.create();
	}

	
}
