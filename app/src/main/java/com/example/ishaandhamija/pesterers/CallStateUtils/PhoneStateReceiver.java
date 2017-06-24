package com.example.ishaandhamija.pesterers.CallStateUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

/**
 * Created by ishaandhamija on 24/06/17.
 */

public class PhoneStateReceiver extends BroadcastReceiver {

    TelephonyManager tm;

    @Override
    public void onReceive(final Context context, Intent intent) {

        CallStateListener callStateListener = new CallStateListener(context);

        tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        tm.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            callStateListener.getAudioManager().setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }

    }

}
