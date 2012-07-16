package com.example.prototype;

import userMessages.PlayerInfoMessage;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class LogInActivity extends Activity {
	
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
		
		udpClient =  new UdpClientThread();
		udpClient.start();
	}



	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		udpClient.setActive(false);		
	}
	
	
	public void onLogInButtonClicked(View view) {
		
		String name = nameEditText.getText().toString();
		if(name.length() == 0) {
			outputText.setText("Sie haben keinen Namen eingegeben!");
		}
		else{
		Log.d("login", name);
		
		//udpClient.sendMessage(name);
		udpClient.sendObject(new PlayerInfoMessage(name));
		startActivity(new Intent(this, Prototype.class));
		}
	}

}
