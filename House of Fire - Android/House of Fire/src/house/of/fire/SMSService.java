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
		
		if(msg.contains("blitz") && msg.length()==5){
			udpClient.sendObject(new SMSInfoMessage(SMSInfoMessage.LIGHTNING));
		}
		else if (msg.contains("regen") && msg.length()==5){
			udpClient.sendObject(new SMSInfoMessage(SMSInfoMessage.RAIN));
		}
		else if (msg.contains("wasserdruck") && msg.length()==11){
			udpClient.sendObject(new SMSInfoMessage(SMSInfoMessage.PRESSURE));
		}
		else if (msg.contains("rain") && msg.length()==5){
			udpClient.sendObject(new SMSInfoMessage(SMSInfoMessage.RAIN));
		}
		else if (msg.contains("lightning") && msg.length()==9){
			udpClient.sendObject(new SMSInfoMessage(SMSInfoMessage.PRESSURE));
		}
		else if (msg.contains("pressure") && msg.length()==8){
			udpClient.sendObject(new SMSInfoMessage(SMSInfoMessage.PRESSURE));
		}
		Log.d(TAG, "Send effect: " + msg);
		
		
		udpClient.setActive(false);

		this.stopSelf();
		
		return Service.START_NOT_STICKY;
	}
	
	

}
