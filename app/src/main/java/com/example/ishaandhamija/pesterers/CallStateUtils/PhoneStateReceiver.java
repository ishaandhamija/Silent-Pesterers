package com.example.ishaandhamija.pesterers.CallStateUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.ishaandhamija.pesterers.DBUtils.DBHelper;

import java.util.ArrayList;

/**
 * Created by ishaandhamija on 24/06/17.
 */

public class PhoneStateReceiver extends BroadcastReceiver {

    AudioManager audioManager;
    DBHelper mydb;
    ArrayList<String> numbersList;
    int[] initialState = new int[10];
    int k = 0;

    @Override
    public void onReceive(final Context context, Intent intent) {

        mydb = new DBHelper(context);
        numbersList = new ArrayList<>();
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        Cursor res = mydb.getAllData();
        while (res.moveToNext()) {
            numbersList.add(res.getString(2));
        }

        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            initialState[k++] = audioManager.getRingerMode();

            Log.d("BCBC", "onReceive: Pehla = " + initialState[0]);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (numbersList != null){
                for (int i=0;i<numbersList.size();i++){
                    String no = numbersList.get(i);
                    if (PhoneNumberUtils.compare(no, incomingNumber)){
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        break;
                    }
                }
            }
        }

        else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){
            Log.d("BCBC", "onReceive: Dusra = " + initialState[0]);
//            if (initialState == 2) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//            }
        }

    }

}
