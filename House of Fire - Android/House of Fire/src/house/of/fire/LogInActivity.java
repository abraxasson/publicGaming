package house.of.fire;

import hof.net.android.AndroidServer;
import hof.net.android.SSDPNetworkClient;
import hof.net.userMessages.PlayerInfoMessage;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
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
	public final static String PREF_PLAYER_COLOR = "playerColor";

	private AndroidServer server;
	private SSDPNetworkClient explorer;
	EditText nameEditText;
	TextView outputText;
	Button playButton;

	String playerName;
	int playerColor;
	public static ProgressDialog progressDialog;

	// ProgressDialog dialog = ProgressDialog.show(LogInActivity.this, "",
	// "Loading. Please wait...", true);
	UdpClientThread udpClient;

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

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				// if (nameEditText.length() >= 12){
				// String text = nameEditText.getEditableText().toString();
				// text = text.substring(0, text.length()-2);
				// s = "";
				// }

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				// if (nameEditText.length() >= 12){
				// String text = nameEditText.getEditableText().toString();
				// text = text.substring(0, text.length()-2);
				// s = "";
				// }
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

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		playerName = prefs.getString(PREF_PLAYER_NAME, "");
		nameEditText.setText(playerName);

		server = new AndroidServer(4711);
		server.start();
		server.setContext(this);

		udpClient = new UdpClientThread();
		// try {
		// udpClient.setIa(InetAddress.getLocalHost());
		// } catch (UnknownHostException e) {
		// e.printStackTrace();
		// }
		udpClient.start();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		
		super.onStop();

		// udpClient.setActive(false);

	}

	public void onLogInButtonClicked(View view) {

		playerName = nameEditText.getText().toString();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = prefs.edit();
		editor.putString(PREF_PLAYER_NAME, playerName);
		editor.commit();

		Log.d(TAG, playerName);

		udpClient.sendObject(new PlayerInfoMessage(playerName));

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Bitte warten...");
		progressDialog.setCancelable(true);
		progressDialog.setOnCancelListener(new OnCancelListener() {

			public void onCancel(DialogInterface dialog) {
				Log.d(TAG, "Abbruch");

				// TODO inform network about cancellation
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

		// TODO save playerColor in prefs

	}

	public void startGame(Context context) {
		progressDialog.dismiss();

		server.setActive(false);
		Intent intent = new Intent(LogInActivity.this, ControllerActivity.class);
		context.startActivity(intent);
	}

}
