package house.of.fire;

import hof.net.android.AndroidServer;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NetworkService extends Service {
	private static final String TAG = NetworkService.class.getSimpleName();

	private AndroidServer server;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate()");
		server = AndroidServer.getInstance(getApplicationContext(), AndroidServer.PORT);
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy()");
		server.close();
		
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand()");
		return super.onStartCommand(intent, flags, startId);
	}
	
	

}
