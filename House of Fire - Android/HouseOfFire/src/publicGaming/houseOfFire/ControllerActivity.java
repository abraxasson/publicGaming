package publicGaming.houseOfFire;

import publicGaming.housOfFire.R;
import userMessages.*;
import android.app.Activity;
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


public class ControllerActivity extends Activity implements SensorEventListener {
	private static final String TAG = "Prototype";
	
	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
	ImageButton pfeil_links;
	ImageButton pfeil_rechts;
	TextView outputText;
	Button logOut;
	
	
	//vorrübergehende Lösung
	private UdpClientThread udpClient;
	
	boolean isleft = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        
        pfeil_links = (ImageButton) findViewById(R.id.pfeil_links);
        pfeil_rechts = (ImageButton) findViewById(R.id.pfeil_rechts);
        outputText = (TextView) findViewById(R.id.output_text);
        
        pfeil_links.setOnClickListener(pfeil_linksListener);
        pfeil_rechts.setOnClickListener(pfeil_rechtsListener);
        logOut.setOnClickListener(logOutButton_Listener);
        

        udpClient = UdpClientThread.getInstance();
        
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
      
    }



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mSensorManager.unregisterListener(this);
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
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
		udpClient.setActive(false);		
	}




	private OnClickListener pfeil_linksListener = new OnClickListener() {
    	public void onClick(View v) {
    		
    		outputText.setText("Links wurde gedrückt!");
    		
    		//sendMessage("Links wurde gedrueckt!");
    		
    		if(isleft) {
    		}
    		else{
    			isleft = true; {
    			//sendMessage("m");
    			udpClient.sendObject(new InputInfoMessage());
    		}
    		}

    	
    	}
    };
    
    private OnClickListener pfeil_rechtsListener = new OnClickListener() {
    	public void onClick(View v) {
    		
    		outputText.setText("Rechts wurde gedrückt!");
    		
    		//sendMessage("Rechts wurde gedrueckt!");
    		
    		if(isleft) {
    			isleft = false;
    			//sendMessage("m");
    			udpClient.sendObject(new InputInfoMessage());
    		}
    	
    	}
    };
    
    
    
    private OnClickListener logOutButton_Listener = new OnClickListener() {
    	public void onClick(View v) {
    		
    		finish();
    		
    		
    		//sendMessage("Benutzer hat sich ausgeloggt!");
    		udpClient.sendObject(new LogoutInfoMessage());
    	}
    };
	
    
    
    
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
	
	
}
