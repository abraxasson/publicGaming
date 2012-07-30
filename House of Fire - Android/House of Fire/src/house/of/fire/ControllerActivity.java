package house.of.fire;

import hof.net.android.AndroidServer;
import hof.net.userMessages.ButtonInfoMessage;
import hof.net.userMessages.LogoutInfoMessage;
import hof.net.userMessages.SensorInfoMessage;
import hof.net.userMessages.WaterPressureInfoMessage;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ControllerActivity extends Activity implements SensorEventListener {

	private final static String TAG = ControllerActivity.class.getSimpleName();
	public final static String EXTRA_WATER_LEVEL = "waterLevel";
	public final static int MAX_WATER_LEVEL = 100;
	
	private final static int REQUEST_CODE_WATER_ACTIVITY = 100;
	
	public static final int SOUND_WATER_EMPTY = 1;
	
	private final static int SENSOR_TIME = 500; // in ms

	private int state = ButtonInfoMessage.NORMAL;
	private SensorManager mSensorManager;
	private Sensor mACCELEROMETER;
	ImageButton pfeil_links;
	ImageButton pfeil_rechts;
	TextView outputName;
	// Button logOut;
	Button button_pump;
	VerticalProgressBar water_rating;
	// SeekBar water_bar;

	int waterLevel = MAX_WATER_LEVEL; // von 0 bis 100
	String playerName;
	int playerColor;
	Handler handler = new Handler();
	Timer timerWaterRating;
	Timer timerWaterPressure;
	Vibrator vibrator;
	
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	
	long sensorTimeStamp = 0;
	
	ServiceConnection conn = new ServiceConnection() {
		
		public void onServiceDisconnected(ComponentName name) {
		}
		
		public void onServiceConnected(ComponentName name, IBinder service) {
		}
	};
	

	// vorrübergehende Lösung
	private UdpClientThread udpClient;

	boolean isleft = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controller);

		pfeil_links = (ImageButton) findViewById(R.id.pfeil_links);
		pfeil_rechts = (ImageButton) findViewById(R.id.pfeil_rechts);
		outputName = (TextView) findViewById(R.id.output_name);
		button_pump = (Button) findViewById(R.id.button_pump);
		water_rating = (VerticalProgressBar) findViewById(R.id.water_rating);
		water_rating.setEnabled(false);
		water_rating.setMax(100);

		pfeil_links.setOnTouchListener(pfeil_linksListener);
		pfeil_rechts.setOnTouchListener(pfeil_rechtsListener);
		// logOut.setOnClickListener(logOutButton_Listener);
		button_pump.setOnClickListener(button_pumpListener);

		// udpClient = new UdpClientThread();
		// System.out.println("Test");
		// udpClient.start();

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mACCELEROMETER = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		WaterActivity.isActive = false;
		
		// Get instance of Vibrator from current Context
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		initSounds();
		
		// String name = (String)bundle.get("EXTRA_PLAYER_NAME");
		// Intent intent = getIntent();
		// int color = intent.getIntegerExtra(LogInActivity.EXTRA_PLAYER_COLOR);
		// String name = intent.getStringExtra(LogInActivity.EXTRA_PLAYER_NAME);

		// setFullscreen();

	}
	
	private void initSounds() {
	     soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	     soundPoolMap = new HashMap<Integer, Integer>();
	     soundPoolMap.put(SOUND_WATER_EMPTY, soundPool.load(this, R.raw.loser, 1));
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}
	
	public void playSound(int sound) {
	    /* Updated: The next 4 lines calculate the current volume in a scale of 0.0 to 1.0 */
	    AudioManager mgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
	    float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	    float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);    
	    float volume = streamVolumeCurrent / streamVolumeMax;
	    
	    /* Play the sound with the correct volume */
	    soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);     
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mACCELEROMETER,
				SensorManager.SENSOR_DELAY_UI);
		
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		bindService(new Intent(this, NetworkService.class), conn , Context.BIND_AUTO_CREATE);
		
		udpClient = new UdpClientThread();
		udpClient.start();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		playerName = prefs.getString(LogInActivity.PREF_PLAYER_NAME, "");
		outputName.setText(playerName);
		
		findViewById(R.id.player_name_background).setBackgroundColor(Color.rgb(AndroidServer.r,
				AndroidServer.g, AndroidServer.b));
		
		// playerColor = prefs.getInt(LogInActivity.PREF_PLAYER_COLOR,
		// Color.RED);

		Intent receivedIntent = getIntent();
		
		if(receivedIntent != null && receivedIntent.hasExtra(EXTRA_WATER_LEVEL)){
			waterLevel = receivedIntent.getIntExtra(EXTRA_WATER_LEVEL, MAX_WATER_LEVEL);
		}
		water_rating.setProgress(waterLevel);
		
		
		timerWaterRating = new Timer();
		timerWaterRating.scheduleAtFixedRate(new WaterTankTimerTask(water_rating), 500, 200);

		timerWaterPressure = new Timer();
		timerWaterPressure.scheduleAtFixedRate(new WaterPressureTimerTask(), 0, 500);
		
		

	}

	@Override
	protected void onStop() {
		super.onStop();
		if (conn != null)
			unbindService(conn);;
		
		udpClient.sendObject(new ButtonInfoMessage(ButtonInfoMessage.NORMAL));
		udpClient.setActive(false);
		timerWaterRating.cancel();
		timerWaterPressure.cancel();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_WATER_ACTIVITY) {
			if (data != null){
				waterLevel = data.getIntExtra(EXTRA_WATER_LEVEL, 0);
				water_rating.setProgress(waterLevel);
				Log.d(TAG, "waterlevel: " + waterLevel);
			}
		}
	}

	private OnTouchListener pfeil_linksListener = new OnTouchListener() {

		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				if (state == ButtonInfoMessage.NORMAL) {
					state = ButtonInfoMessage.LEFT;
					udpClient.sendObject(new ButtonInfoMessage(state));
				}

				break;

			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				if (state == ButtonInfoMessage.LEFT) {
					state = ButtonInfoMessage.NORMAL;
					udpClient.sendObject(new ButtonInfoMessage(state));
				}
				break;
			}

			return false;
		}
	};

	private OnTouchListener pfeil_rechtsListener = new OnTouchListener() {

		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				if (state == ButtonInfoMessage.NORMAL) {
					state = ButtonInfoMessage.RIGHT;
					udpClient.sendObject(new ButtonInfoMessage(state));
				}

				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				if (state == ButtonInfoMessage.RIGHT) {
					state = ButtonInfoMessage.NORMAL;
					udpClient.sendObject(new ButtonInfoMessage(state));
				}

				break;
			}

			return false;
		}
	};

	private OnClickListener button_pumpListener = new OnClickListener() {
		public void onClick(View v) {
			// startActivity(new Intent(ControllerActivity.this,
			// WaterActivity.class));

			startWaterActivity();
			

		}

		
	};

	// private OnClickListener logOutButton_Listener = new OnClickListener() {
	// public void onClick(View v) {
	//
	// finish();
	//
	//
	// //sendMessage("Benutzer hat sich ausgeloggt!");
	// //udpClient.sendObject(new LogoutInfoMessage());
	// }
	// };
	
	private void startWaterActivity() {
		Intent intent = new Intent(ControllerActivity.this,
				WaterActivity.class);
		intent.putExtra(EXTRA_WATER_LEVEL, waterLevel);
		startActivityForResult(intent, REQUEST_CODE_WATER_ACTIVITY);
	}

	public void finish() {
		udpClient.sendObject(new LogoutInfoMessage());
//		server.close();
		super.finish();
	}

	public void onAccuracyChanged(Sensor sensor, int value) {


	}

	public void onSensorChanged(SensorEvent event) {
		final long now = System.currentTimeMillis();
		
		if (now > sensorTimeStamp+SENSOR_TIME){
			final float[] values = event.values;
			//Log.d(TAG, values[0] + " " + values[1] + " " + values[2]);

			udpClient.sendObject(new SensorInfoMessage(values[0], values[1], values[2]));
			sensorTimeStamp = now;
		}

	}

	
	class WaterTankTimerTask extends TimerTask{
		
		VerticalProgressBar vpb;
		
		public WaterTankTimerTask (VerticalProgressBar vpb){
			this.vpb = vpb;
		}

		@Override
		public void run() {
			handler.post(new Runnable() {
				
				public void run() {
					if(vpb.getProgress() <= 0 && WaterActivity.isActive == false){
						timerWaterRating.cancel();

						// Vibrate for 300 milliseconds
						vibrator.vibrate(300);
						playSound(SOUND_WATER_EMPTY);
						startWaterActivity();
						
					}
					
					else{
						//Log.d(TAG, ""+vpb.getProgress());
						waterLevel = vpb.getProgress()-1;
						vpb.setProgress(waterLevel);
						vpb.postInvalidate();
					}
				}
			});
			
		}
	}
	
	class WaterPressureTimerTask extends TimerTask{

		@Override
		public void run() {
			
			udpClient.sendObject(new WaterPressureInfoMessage(waterLevel));
			
		}
	}
	
//	public void alertDialog(){
//		alertDialog = new AlertDialog.Builder(context).create();
//		alertDialog.setTitle("Herzlichen Glückwunsch!");
//		alertDialog.setMessage("Sie sind in Level" + AndroidServer.level + " aufgestiegen.");
//		alertDialog.show();
//	}
}
