package com.ajman.ded.ae.utility.otpSms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Ahmed on 12/9/2017.
 */

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "OtpReader";

    private static SmsListener otpListener;

    /**
     * The Sender number string.
     */
    private static String receiverString;

    /**
     * Binds the sender string and listener for callback.
     *
     * @param listener
     * @param sender
     */
    public static void bind(SmsListener listener, String sender) {
        otpListener = listener;
        receiverString = sender;
    }

    /**
     * Unbinds the sender string and listener for callback.
     */
    public static void unbind() {
        otpListener = null;
        receiverString = null;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if (bundle != null) {

            final Object[] pdusArr = (Object[]) bundle.get("pdus");

            for (int i = 0; i < pdusArr.length; i++) {

                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusArr[i]);
                String senderNum = currentMessage.getDisplayOriginatingAddress();
                String message = currentMessage.getDisplayMessageBody();
                Log.i(TAG, "senderNum: " + senderNum + " message: " + message);

                if (!TextUtils.isEmpty(receiverString) && senderNum.contains(receiverString)) { //If message received is from required number.
                    if (otpListener != null) {
                        otpListener.messageReceived(message);
                    }
                }
            }
        }
    }
}