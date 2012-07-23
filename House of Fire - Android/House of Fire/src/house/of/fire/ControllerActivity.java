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
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class ControllerActivity extends Activity implements SensorEventListener{
	
	private static final String TAG = "House of Fire";
	public final static String EXTRA_WATER_LEVEL = "waterLevel";
	
	private final static int REQUEST_CODE_WATER_ACTIVITY = 100;

	
	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
	ImageButton pfeil_links;
	ImageButton pfeil_rechts;
	TextView outputName;
	//Button logOut;
	Button button_pump;
	//SeekBar water_bar;
	
	int waterLevel = 100; // von 0 bis 100
	String playerName;
	int playerColor;
	
	//vorrübergehende Lösung
	private UdpClientThread udpClient;
	private AndroidServer server;
	
	boolean isleft = false;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
                
                pfeil_links = (ImageButton) findViewById(R.id.pfeil_links);
                pfeil_rechts = (ImageButton) findViewById(R.id.pfeil_rechts);
                outputName = (TextView) findViewById(R.id.output_name);
                button_pump = (Button) findViewById(R.id.button_pump);
                //water_bar = (SeekBar) findViewById(R.id.waterstatus);
                
                pfeil_links.setOnClickListener(pfeil_linksListener);
                pfeil_rechts.setOnClickListener(pfeil_rechtsListener);
                //logOut.setOnClickListener(logOutButton_Listener);
                button_pump.setOnClickListener(button_pumpListener);
                
                
                
//                udpClient = new UdpClientThread();
//                System.out.println("Test");
//                udpClient.start();
                
                
                mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
                mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                
                
                //String name = (String)bundle.get("EXTRA_PLAYER_NAME");
                //Intent intent = getIntent();
                //int color = intent.getIntegerExtra(LogInActivity.EXTRA_PLAYER_COLOR);
                //String name = intent.getStringExtra(LogInActivity.EXTRA_PLAYER_NAME);
                
                
                //setFullscreen();
       
                
              
            }



        	@Override
        	protected void onPause() {
        		// TODO Auto-generated method stub
        		super.onPause();
        		mSensorManager.unregisterListener(this);
        	}



        	@Override
        	protected void onResume() {
        		super.onResume();
        		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        	}



        	@Override
        	protected void onStart() {
        		super.onStart(); 
        		server = new AndroidServer(4711);
                server.start();
        		udpClient = new UdpClientThread();
                System.out.println("Test");
                udpClient.start();
                
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        		playerName = prefs.getString(LogInActivity.PREF_PLAYER_NAME, "");
        		playerColor = prefs.getInt(LogInActivity.PREF_PLAYER_COLOR, Color.RED);
        		
                outputName.setText(playerName);
                // TODO set color in user inteface
        		
        		
        	}



        	@Override
        	protected void onStop() {
        		super.onStop();
        		server.setActive(false);
        		udpClient.setActive(false);		
        	}
        	

        	@Override
			protected void onActivityResult(int requestCode, int resultCode,
					Intent data) {
				
				super.onActivityResult(requestCode, resultCode, data);
				
				if (requestCode == REQUEST_CODE_WATER_ACTIVITY) {
					
					waterLevel = data.getIntExtra(EXTRA_WATER_LEVEL, 0);
					Log.d(TAG, "waterlevel: " + waterLevel);
				}
			}






			private OnClickListener pfeil_linksListener = new OnClickListener() {
            	public void onClick(View v) {
            		
            		//outputText.setText("Links wurde gedrückt!");
            		
            		//sendMessage("Links wurde gedrueckt!");
            		System.out.println("Links wurde gedrückt");
            		
            		

            		
            			udpClient.sendObject(new InputInfoMessage());
            		
            		}

            	
            	};
         
            
            private OnClickListener pfeil_rechtsListener = new OnClickListener() {
            	public void onClick(View v) {
            		
            		//outputText.setText("Rechts wurde gedrückt!");
            		
            		//sendMessage("Rechts wurde gedrueckt!");
            		System.out.println("Rechts wurde gedrückt");
            		
            		
            			//sendMessage("m");
            			udpClient.sendObject(new InputInfoMessage());
            		
            	
            	}
            };
            
            private OnClickListener button_pumpListener = new OnClickListener() {
            	public void onClick(View v) {
            		//startActivity(new Intent(ControllerActivity.this, WaterActivity.class));
            		
            		Intent intent = new Intent(ControllerActivity.this, WaterActivity.class);
            		intent.putExtra(LogInActivity.PREF_PLAYER_NAME, playerName);
            		intent.putExtra(LogInActivity.PREF_PLAYER_COLOR, playerColor);
            		startActivityForResult(intent, REQUEST_CODE_WATER_ACTIVITY);
            		
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
            
            public void finish() {
            	udpClient.sendObject(new LogoutInfoMessage());
            	super.finish();
            }
        	
            
            
            
            private void sendMessage (String message) {
            	//udpClient.sendMessage(message);    	
            }



        	public void onAccuracyChanged(Sensor sensor, int value) {
        		// TODO Auto-generated method stub
        		
        	}



        	public void onSensorChanged(SensorEvent event) {
        		// TODO Auto-generated method stub
        		float[] values = event.values;
        		Log.d(TAG, values[0] + " " + values[1] + " " + values[2]);
        		
        		//sendMessage(values[0] + " " + values[1] + " " + values[2]);
        	}
        	
        	private void setFullscreen() {
        		requestWindowFeature(Window.FEATURE_NO_TITLE);
        		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        				WindowManager.LayoutParams.FLAG_FULLSCREEN);
        	}

        	
        	
        }

