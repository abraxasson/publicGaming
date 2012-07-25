package house.of.fire;

import hof.net.android.AndroidServer;
import hof.net.android.SSDPNetworkClient;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LogInActivity extends Activity {

	private final static String TAG = LogInActivity.class.getSimpleName();

	public final static String PREF_PLAYER_NAME = "playerName";
//	public final static String PREF_PLAYER_COLOR = "playerColor";

	private AndroidServer server;
	private SSDPNetworkClient explorer;
	EditText nameEditText;
	TextView outputText;
	Button playButton;

	String playerName;
	int playerColor;
	ProgressDialog progressDialog;

	// ProgressDialog dialog = ProgressDialog.show(LogInActivity.this, "",
	// "Loading. Please wait...", true);
	UdpClientThread udpClient;

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

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		playerName = prefs.getString(PREF_PLAYER_NAME, "");
		nameEditText.setText(playerName);

		server = AndroidServer.getInstance(this, AndroidServer.PORT);

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
	}
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		server.close();
	}
	
//	public void finish() {
//		server.close();
//	}

	public void onLogInButtonClicked(View view) {

		playerName = nameEditText.getText().toString();

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = prefs.edit();
		editor.putString(PREF_PLAYER_NAME, playerName);
		editor.commit();

		Log.d(TAG, "Player: " + playerName + " meldet sich an");

		udpClient.sendObject(new PlayerInfoMessage(playerName));

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
		
		
		
		
		// TODO register over network
		
		
		// startGame(this);

		// startActivity(new Intent(LogInActivity.this,
		// ControllerActivity.class));

		// int z = 0;
		// while(!server.getValidation()){
		// alertDialog.show();
		// z+=1;
		// if(z==20000000){
		// break;
		//
		// }
		// }

		

	}

}
