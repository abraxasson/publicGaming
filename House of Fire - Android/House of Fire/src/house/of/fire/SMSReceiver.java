package house.of.fire;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();

		Object messages[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];

		for (int i = 0; i < messages.length; i++) {
			smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
		}
		
		String msg = smsMessage[0].getMessageBody();
		msg.toLowerCase();
		if (((msg.contains("blitz") || msg.contains("regen")) && msg.length()==5) || (msg.contains("wasserdruck") && msg.length()==11)){
			// Send sms to Game Server
			
		}

		// show first message
		//Toast toast = Toast.makeText(context,
		//		"Received SMS: " + smsMessage[0].getMessageBody(), Toast.LENGTH_LONG);
		//toast.show();
		
		
	}
}
