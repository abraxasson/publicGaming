package house.of.fire;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
	
	public static final String EXTRA_SMS_MESSAGE = "message";
	
	@Override
	public void onReceive(Context context, Intent intent) {

		Bundle bundle = intent.getExtras();

		Object messages[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];

		for (int i = 0; i < messages.length; i++) {
			smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
		}
		
		Intent serviceIntent = new Intent(context, SMSService.class);
		
		String msg = smsMessage[0].getMessageBody();
		msg = msg.toLowerCase().trim();
		
		serviceIntent.putExtra(EXTRA_SMS_MESSAGE, msg);
		
		context.startService(serviceIntent);

	}
}
