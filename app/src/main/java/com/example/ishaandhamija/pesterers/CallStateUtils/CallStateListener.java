package com.example.ishaandhamija.pesterers.CallStateUtils;

import android.content.Context;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.ishaandhamija.pesterers.Activities.MainActivity;
import com.example.ishaandhamija.pesterers.DBUtils.ContactDetails;

/**
 * Created by ishaandhamija on 22/06/17.
 */

public class CallStateListener extends PhoneStateListener {

    public static final String TAG = "UU";
    public static AudioManager audiomanager;
    Context context;

    public CallStateListener(Context context) {
        this.context = context;
        audiomanager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public void onCallStateChanged(int state, final String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d(TAG, "onCallStateChanged: " + incomingNumber);
                Toast.makeText(context, incomingNumber, Toast.LENGTH_SHORT).show();
                if (incomingNumber != null) {
                    if (MainActivity.getAllContactsArrayList() != null){
                        for (int i=0;i<MainActivity.getAllContactsArrayList().size();i++){
                            String no = MainActivity.getAllContactsArrayList().get(i).getNumber();
                            Log.d(TAG, "onCallStateChanged: no = " + no.trim());
                            if (no.equals(incomingNumber)){
                                audiomanager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                                break;
                            }
                        }
                    }
                }
                break;
        }
    }

    public static AudioManager getAudioManager(){
        return audiomanager;
    }

}
