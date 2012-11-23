package house.of.fire;

import hof.net.userMessages.SMSInfoMessage;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SMSService extends Service {
	
	private static final String TAG = "SMSService";
	
	private UdpClientThread udpClient;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		
		udpClient = new UdpClientThread();
		udpClient.start();
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "SMS Service destroyed");
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		
		String msg = intent.getStringExtra(SMSReceiver.EXTRA_SMS_MESSAGE);
		msg = msg.toLowerCase();
		
		if(msg.contains("blitz") || msg.contains("light")){
			udpClient.sendObject(new SMSInfoMessage(SMSInfoMessage.LIGHTNING));
		}
		else if (msg.contains("regen") || msg.contains("rain") || msg.contains("wolke") || msg.contains("cloud")){
			udpClient.sendObject(new SMSInfoMessage(SMSInfoMessage.RAIN));
		}
		else if (msg.contains("druck") || msg.contains("pressure") || msg.contains("wasser") || msg.contains("water") || msg.contains("strahl")){
			udpClient.sendObject(new SMSInfoMessage(SMSInfoMessage.PRESSURE));
		}
		Log.d(TAG, "Send effect: " + msg);
		
		
		udpClient.setActive(false);

		this.stopSelf();
		
		return Service.START_NOT_STICKY;
	}
	
	

}
