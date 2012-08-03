package house.of.fire;

import hof.net.android.AndroidServer;
import hof.net.userMessages.ButtonInfoMessage;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class WaterActivity extends Activity {

	private final static String TAG = WaterActivity.class.getSimpleName();

	public final static String EXTRA_WATER_LEVEL = "waterLevel";

	ImageButton pfeil_links;
	ImageButton pfeil_rechts;
	TextView outputName;
	TextView instruction;
	// Button logOut;
	// SeekBar water_bar;
	VerticalProgressBar water_rating;

	int state = ButtonInfoMessage.NORMAL;
	int waterLevel;

	public static boolean isActive = true;
	private Timer timer;
	
	private Handler handler;

	String name;
	int color;
	AndroidServer server;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_water);

		pfeil_links = (ImageButton) findViewById(R.id.pfeil_links);
		pfeil_rechts = (ImageButton) findViewById(R.id.pfeil_rechts);
		outputName = (TextView) findViewById(R.id.output_name);
		// water_bar = (SeekBar) findViewById(R.id.waterstatus);
		water_rating = (VerticalProgressBar) findViewById(R.id.water_rating);
		water_rating.setEnabled(false);
		
		instruction = (TextView)findViewById(R.id.instruction_textview);

		pfeil_links.setOnClickListener(pfeil_linksListener);
		pfeil_rechts.setOnClickListener(pfeil_rechtsListener);
		// logOut.setOnClickListener(logOutButton_Listener);
		// water_bar.setOnSeekBarChangeListener(water_barListener);

		// String name = (String)bundle.get("EXTRA_PLAYER_NAME");
		// Intent intent = getIntent();
		// int color = intent.getIntegerExtra(LogInActivity.EXTRA_PLAYER_COLOR);
		// String name =
		// intent.getStringExtra(ControllerActivity.EXTRA_PLAYER_NAME1);

		// setFullscreen();
		
		handler = new Handler();

	}

	@Override
	protected void onStart() {
		super.onStart();
		
		server = AndroidServer.getInstance(this, AndroidServer.PORT);

		Intent intent = getIntent();
		waterLevel = intent
				.getIntExtra(ControllerActivity.EXTRA_WATER_LEVEL, 0);
		water_rating.setProgress(waterLevel);
		water_rating.postInvalidate();
		state = ButtonInfoMessage.NORMAL;
		isActive = true;
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				handler.post(new Runnable() {
					
					public void run() {
						if (instruction.getVisibility() == View.VISIBLE){
							instruction.setVisibility(View.INVISIBLE);
						}
						else {
							instruction.setVisibility(View.VISIBLE);
						}
						
					}
				});
				
			}
		}, 500, 500);
	}

	@Override
	protected void onStop() {
		super.onStop();
		server.close();
		timer.cancel();
	}

	@Override
	public void finish() {

		if (waterLevel >= 1) {
			isActive = false;
		}

		// Prepare data intent
		Intent intent = new Intent();

		intent.putExtra(EXTRA_WATER_LEVEL, waterLevel);

		// Activity finished ok, return the data
		setResult(RESULT_OK, intent);
		super.finish();

		// intent.putExtra(ControllerActivity.EXTRA_WATER_LEVEL, waterLevel);
		// intent.putExtra(EXTRA_PLAYER_NAME2,
		// getIntent().getStringExtra(ControllerActivity.EXTRA_PLAYER_NAME1));

		// setResult(Activity.RESULT_OK, intent);
		// Prepare data intent

	}

	private OnClickListener pfeil_linksListener = new OnClickListener() {
		public void onClick(View v) {

			// outputText.setText("Links wurde gedrückt!");

			// sendMessage("Links wurde gedrueckt!");

			if (state == ButtonInfoMessage.NORMAL) {
				state = ButtonInfoMessage.LEFT;
				fillWater(9);
			} else if (state == ButtonInfoMessage.RIGHT) {
				state = ButtonInfoMessage.LEFT;
				fillWater(9);
			} else if (state == ButtonInfoMessage.LEFT) {

			}

		}

	};

	private OnClickListener pfeil_rechtsListener = new OnClickListener() {
		public void onClick(View v) {

			// outputText.setText("Rechts wurde gedrückt!");

			// sendMessage("Rechts wurde gedrueckt!");

			if (state == ButtonInfoMessage.NORMAL) {
				state = ButtonInfoMessage.RIGHT;
				fillWater(9);
			} else if (state == ButtonInfoMessage.LEFT) {
				state = ButtonInfoMessage.RIGHT;
				fillWater(9);
			} else if (state == ButtonInfoMessage.RIGHT) {

			}

		}
	};

	private void fillWater(int increase) {
		waterLevel += increase;
		water_rating.setProgress(waterLevel);
		// water_rating.setProgress(water_rating.getProgress()+20);
		water_rating.postInvalidate();
		// muss immer wieder geändert werden, je nachdem wie viele Punkte man
		// bei jedem Buttondruck bekommt

		if (waterLevel >= 100) {
			waterLevel = 100;
			finish();
		}
	}

	public static String getTag() {
		return TAG;
	}

	// private OnClickListener logOutButton_Listener = new OnClickListener() {
	// public void onClick(View v) {
	//
	// finish();
	//
	//
	// //sendMessage("Benutzer hat sich ausgeloggt!");
	// udpClient.sendObject(new LogoutInfoMessage());
	// }
	// };

}
