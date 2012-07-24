package hof.core;

import hof.core.utils.Assets;
import hof.core.utils.Settings;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;


public class HouseOfFireGame extends Game {

	Screen mainMenuScreen;
	Screen waitingForPlayersScreen;
	Screen playingScreen;
	Screen gameOverScreen;
	Screen levelFinishedScreen;
	
	@Override
	public void create() {
		Assets.load();
		Settings.load();
		
		mainMenuScreen = new MainMenuScreen(this);
		waitingForPlayersScreen = new WaitingForPlayersScreen(this);
		playingScreen = new PlayingScreen(this);
		gameOverScreen = new GameOverScreen(this);
		levelFinishedScreen = new LevelFinishedScreen(this);
		
		this.setScreen(mainMenuScreen);
	}	
	
	@Override
	public void dispose() {
		Assets.dispose();
	}
}
