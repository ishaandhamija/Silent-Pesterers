package com.example.ishaandhamija.pesterers.CallStateUtils;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.ishaandhamija.pesterers.Activities.MainActivity;
import com.example.ishaandhamija.pesterers.DBUtils.ContactDetails;
import com.example.ishaandhamija.pesterers.DBUtils.DBHelper;
import com.example.ishaandhamija.pesterers.Interfaces.OnCallReceived;

import java.util.ArrayList;

/**
 * Created by ishaandhamija on 22/06/17.
 */

public class CallStateListener extends PhoneStateListener {

    public static final String TAG = "UU";
    public static AudioManager audiomanager;
    Context context;
    DBHelper mydb;
    ArrayList<String> numbersList;

    OnCallReceived onCallReceived = new OnCallReceived() {
        @Override
        public void onSuccess(String incomingNumber) {
            mydb = new DBHelper(context);
            numbersList = new ArrayList<>();
            Cursor res = mydb.getAllData();
            while (res.moveToNext()) {
                numbersList.add(res.getString(2));
            }
            if (numbersList != null){
                for (int i=0;i<numbersList.size();i++){
                    String no = numbersList.get(i);
                    Log.d(TAG, "onCallStateChanged: no = " + no.trim());

                    if (PhoneNumberUtils.compare(no, incomingNumber)){
                        audiomanager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                        break;
                    }
                }
            }
        }
    };

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
                while (incomingNumber == null) {}
                Log.d(TAG, "onCallStateChanged: Yaha aaya kya??incomin");
                onCallReceived.onSuccess(incomingNumber);
                break;
        }
    }



    public static AudioManager getAudioManager(){
        return audiomanager;
    }

}
