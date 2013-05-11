package hof.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


/**
 * This class encapsulates another sound class to provide safe access to the play method.
 * 
 * @author Florian Genser
 */
public final class Soundplayer {

	private Sound sound;
	private boolean isPlaying;
	
	private Soundplayer(final Sound sound) {
		this.sound = sound;
		isPlaying = false;
	}
	
	/**
	 * Loads the sound file and creates a Sound object.
	 * @param path - the path of the sound file
	 * @return the encapsulating sound player
	 */
	public static Soundplayer load(final String path) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
		return new Soundplayer(sound);
	}
	
	/**
	 * Plays the sound. <br>
	 * Does nothing if the sound already is playing.
	 */
	public void play() {
		play(1);
	}
	
	/**
	 * Plays the sound. <br>
	 * Does nothing if the sound already is playing.
	 * @param volume - the volume in the range [0,1]
	 */
	public void play(final float volume) {
		if (isPlaying) {
			return;
		}
		
		sound.play(volume);
		isPlaying = true;
	}
	
	/**
	 * Plays the sound looping.
	 */
	public void loop() {
		loop(1);
	}
	
	/**
	 * Plays the sound, looping. 
	 * @param volume - volume the volume in the range [0, 1]
	 */
	public void loop(final float volume) {
		if (isPlaying) {
			return;
		}
		
		sound.loop(volume);
		isPlaying = true;
	}
	
	/**
	 * Stops playing the sound.
	 */
	public void stop() {
		sound.stop();
		isPlaying = false;
	}
	
	/**
	 * Releases all the resources.
	 */
	public void dispose() {
		sound.dispose();
	}
}
