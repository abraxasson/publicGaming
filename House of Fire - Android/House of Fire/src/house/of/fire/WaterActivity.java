package house.of.fire;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.support.v4.app.NavUtils;
import hof.net.android.AndroidServer;
import hof.net.userMessages.InputInfoMessage;
import hof.net.userMessages.LogoutInfoMessage;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class WaterActivity extends Activity {
	
	public final static String EXTRA_PLAYER_NAME2 = "playerName";
	public final static String EXTRA_PLAYER_COLOR = "playerColor";
	

	ImageButton pfeil_links;
	ImageButton pfeil_rechts;
	TextView outputName;
	//Button logOut;
	Button button_game;
	
	
	//vorrübergehende Lösung
	private UdpClientThread udpClient;
	private AndroidServer server;
	
	boolean isleft = false;
	


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
                
                pfeil_links = (ImageButton) findViewById(R.id.pfeil_links);
                pfeil_rechts = (ImageButton) findViewById(R.id.pfeil_rechts);
                outputName = (TextView) findViewById(R.id.output_name);
                button_game = (Button) findViewById(R.id.button_game);
                
                pfeil_links.setOnClickListener(pfeil_linksListener);
                pfeil_rechts.setOnClickListener(pfeil_rechtsListener);
                //logOut.setOnClickListener(logOutButton_Listener);
                button_game.setOnClickListener(button_gameListener);
                
                //server = new AndroidServer();
                //server.start();
                
                //udpClient = new UdpClientThread();
                //udpClient.start();
                
                
                
                //String name = (String)bundle.get("EXTRA_PLAYER_NAME");
                //Intent intent = getIntent();
                //int color = intent.getIntegerExtra(LogInActivity.EXTRA_PLAYER_COLOR);
                //String name = intent.getStringExtra(ControllerActivity.EXTRA_PLAYER_NAME1);
                
                Intent intent = getIntent();
                String name = intent.getStringExtra(ControllerActivity.EXTRA_PLAYER_NAME1);
                outputName.setText(name);
                
                //setFullscreen();
       
                
              
            }



        	@Override
        	protected void onPause() {
        		// TODO Auto-generated method stub
        		super.onPause();	
        	}



        	@Override
        	protected void onResume() {
        		// TODO Auto-generated method stub
        		super.onResume();
        	}



        	@Override
        	protected void onStart() {
        		// TODO Auto-generated method stub
        		super.onStart();
        		
        		
        	}



        	@Override
        	protected void onStop() {
        		// TODO Auto-generated method stub
        		super.onStop();
        		//server.setActive(false);
        		//udpClient.setActive(false);		
        	}




        	private OnClickListener pfeil_linksListener = new OnClickListener() {
            	public void onClick(View v) {
            		
            		//outputText.setText("Links wurde gedrückt!");
            		
            		//sendMessage("Links wurde gedrueckt!");
            		
            		if(isleft) {
            		}
            		else{
            			isleft = true; {
            			//sendMessage("m");
            			//udpClient.sendObject(new InputInfoMessage());
            		}
            		}

            	
            	}
            };
            
            private OnClickListener pfeil_rechtsListener = new OnClickListener() {
            	public void onClick(View v) {
            		
            		//outputText.setText("Rechts wurde gedrückt!");
            		
            		//sendMessage("Rechts wurde gedrueckt!");
            		
            		if(isleft) {
            			isleft = false;
            			//sendMessage("m");
            			//udpClient.sendObject(new InputInfoMessage());
            		}
            	
            	}
            };
            
            private OnClickListener button_gameListener = new OnClickListener() {
            	public void onClick(View v) {
            		
            		Intent intent = new Intent(WaterActivity.this, ControllerActivity.class);
            		intent.putExtra(EXTRA_PLAYER_NAME2, getIntent().getStringExtra(ControllerActivity.EXTRA_PLAYER_NAME1));
            		startActivity(intent);
            		
            	}
            };
            
            
            
//            private OnClickListener logOutButton_Listener = new OnClickListener() {
//            	public void onClick(View v) {
//            		
//            		finish();
//            		
//            		
//            		//sendMessage("Benutzer hat sich ausgeloggt!");
//            		//udpClient.sendObject(new LogoutInfoMessage());
//            	}
//            };
        	
            
            
            
            private void sendMessage (String message) {
            	//udpClient.sendMessage(message);    	
            }



        	  	
        }

