package house.of.fire;

public class Timer {

	long startTime;
	long duration;
	
	public Timer(int duration) {
		this.duration = duration;
	}
	
	public void start() {
		startTime = System.currentTimeMillis();
	}
	
	public boolean checkTime() {
		long actualTime = System.currentTimeMillis();
		if (actualTime - startTime >= duration) {
			start();
			return true;
		} else {
			return false;
		}
	}
}
