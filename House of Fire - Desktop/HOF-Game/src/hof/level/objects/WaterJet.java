package hof.level.objects;

import hof.core.utils.Assets;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class WaterJet {

	private Firefighter player;
	private ParticleEffect waterJet;
	
	public WaterJet(Firefighter player) {
		this.player = player;
		waterJet = Assets.loadWaterParticles();
	}
	
	public void update () {
		
	}
}
