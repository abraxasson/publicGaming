package hof.core;

import com.badlogic.gdx.Game;


public class HouseOfFireGame extends Game {

	MainMenuScreen mainMenuScreen;
	WaitingForPlayersScreen waitingForPlayersScreen;
	
	@Override
	public void create() {
		mainMenuScreen = new MainMenuScreen(this);
		waitingForPlayersScreen = new WaitingForPlayersScreen(this);
		
		this.setScreen(mainMenuScreen);
	}	
}
