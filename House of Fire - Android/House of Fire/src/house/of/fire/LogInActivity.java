package house.of.fire;

import hof.net.android.AndroidServer;
import hof.net.userMessages.LogoutInfoMessage;
import hof.net.userMessages.PlayerInfoMessage;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
		progressDialog.setMessage("Bitte warten...");
		progressDialog.setCancelable(true);

		progressDialog.setOnCancelListener(new OnCancelListener() {

			public void onCancel(DialogInterface dialog) {
				Log.d(TAG, "Abbruch");
				udpClient.sendObject(new LogoutInfoMessage());
			}
		});
		progressDialog.show();
		
		udpClient.sendObject(new PlayerInfoMessage(playerName));
	}
	
	
}
