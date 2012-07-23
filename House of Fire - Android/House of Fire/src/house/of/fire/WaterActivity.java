package house.of.fire;

import hof.net.android.AndroidServer;
import hof.net.userMessages.InputInfoMessage;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;


public class WaterActivity extends Activity {
	
	private final static String TAG = WaterActivity.class.getSimpleName();
	
	

	ImageButton pfeil_links;
	ImageButton pfeil_rechts;
	TextView outputName;
	//Button logOut;
	//SeekBar water_bar;
	
	int waterLevel = 0;
	
	
	//vorrübergehende Lösung
	private UdpClientThread udpClient;
	private AndroidServer server;
	
	boolean isleft = false;
	
	String name;
	int color;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
                
                pfeil_links = (ImageButton) findViewById(R.id.pfeil_links);
                pfeil_rechts = (ImageButton) findViewById(R.id.pfeil_rechts);
                outputName = (TextView) findViewById(R.id.output_name);
                //water_bar = (SeekBar) findViewById(R.id.waterstatus);
                
                pfeil_links.setOnClickListener(pfeil_linksListener);
                pfeil_rechts.setOnClickListener(pfeil_rechtsListener);
                //logOut.setOnClickListener(logOutButton_Listener);
                //water_bar.setOnSeekBarChangeListener(water_barListener);
                
                
                
                
                //String name = (String)bundle.get("EXTRA_PLAYER_NAME");
                //Intent intent = getIntent();
                //int color = intent.getIntegerExtra(LogInActivity.EXTRA_PLAYER_COLOR);
                //String name = intent.getStringExtra(ControllerActivity.EXTRA_PLAYER_NAME1);
                
                
                
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
        		server = new AndroidServer(4711);
                server.start();
        		udpClient = new UdpClientThread();
                udpClient.start();	
        		
        	}



        	@Override
        	protected void onStop() {
        		// TODO Auto-generated method stub
        		super.onStop();
        		server.setActive(false);
        		udpClient.setActive(false);
        		
        	}

        	@Override
        	public void finish() {
        		
        		//Prepare data intent
        		Intent intent = new Intent();
        		
        		intent.putExtra(ControllerActivity.EXTRA_WATER_LEVEL, waterLevel);
        		
        		// Activity finished ok, return the data
        		setResult(RESULT_OK, intent);
        		super.finish();

        		//intent.putExtra(ControllerActivity.EXTRA_WATER_LEVEL, waterLevel);
        		//intent.putExtra(EXTRA_PLAYER_NAME2, getIntent().getStringExtra(ControllerActivity.EXTRA_PLAYER_NAME1));
        		
        		//setResult(Activity.RESULT_OK, intent);
        		//Prepare data intent
        		
        	}


        	private OnClickListener pfeil_linksListener = new OnClickListener() {
            	public void onClick(View v) {
            		
            		//outputText.setText("Links wurde gedrückt!");
            		
            		//sendMessage("Links wurde gedrueckt!");
            		
            		if(isleft) {
            		}
            		else{
            			isleft = true; {
            				waterLevel += 20;
            				if(waterLevel == 100){
            					finish();
            				}
            			//sendMessage("m");
           			System.out.println("Links");
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
            			waterLevel += 20;
            			if(waterLevel == 100){
            				finish();
            			}
            			//sendMessage("m");
            			System.out.println("Rechts");
            		}
            	
            	}
            };

            
            
//            private OnClickListener logOutButton_Listener = new OnClickListener() {
//            	public void onClick(View v) {
//            		
//            		finish();
//            		
//            		
//            		//sendMessage("Benutzer hat sich ausgeloggt!");
//            		udpClient.sendObject(new LogoutInfoMessage());
//            	}
//            };
        	
            
            
            
            private void sendMessage (String message) {
            	//udpClient.sendMessage(message);    	
            }



        	  	
        }

