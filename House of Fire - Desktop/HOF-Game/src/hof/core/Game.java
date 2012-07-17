package hof.core;

import hof.net.UdpServerThread;

import com.badlogic.gdx.ApplicationAdapter;


public class Game extends ApplicationAdapter {

	private UdpServerThread udpServer; 
	
	@Override
	public void create() {
		super.create();
		udpServer = new UdpServerThread(4711);
		udpServer.start();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	
}
