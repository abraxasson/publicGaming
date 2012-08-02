package house.of.fire;



import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;


public class GameOverActivity extends Activity {

	public static final int SOUND_GAME_OVER = 1;

	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	Timer timerSound;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lostgame);

		initSounds();
		timerSound = new Timer();

	}
	
	class SoundTimerTask extends TimerTask {

		@Override
		public void run() {

			playSound(SOUND_GAME_OVER);

		}
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		timerSound.schedule(new SoundTimerTask(), 350);
	}

	
	@Override
	protected void onStop() {
		
		super.onStop();
	}

	private void initSounds() {
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap = new HashMap<Integer, Integer>();
		soundPoolMap.put(SOUND_GAME_OVER, soundPool.load(this, R.raw.loser, 1));
	}

	 public void playSound(int sound) {
	 /* Updated: The next 4 lines calculate the current volume in a scale of
	 0.0 to 1.0 */
	 AudioManager mgr = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
	 float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
	 float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	 float volume = streamVolumeCurrent / streamVolumeMax;
	
	 /* Play the sound with the correct volume */
	 soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);
	
	 }


	public void retryButtonClicked(View view) {
		Intent intent = new Intent(GameOverActivity.this, LogInActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(GameOverActivity.this, StartActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}
