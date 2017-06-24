package com.example.ishaandhamija.pesterers.Activities;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ishaandhamija.pesterers.DBUtils.ContactDetails;
import com.example.ishaandhamija.pesterers.DBUtils.DBHelper;
import com.example.ishaandhamija.pesterers.Interfaces.OnDelete;
import com.example.ishaandhamija.pesterers.R;
import com.example.ishaandhamija.pesterers.Utils.AllContactsAdapter;
import com.example.ishaandhamija.pesterers.Utils.AllContactsHolder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView allContactsRV;
    AllContactsAdapter allContactsAdapter;
    ArrayList<ContactDetails> allContactsArrayList;
    DBHelper mydb;
    public static OnDelete onDelete;

    public static final String TAG = "Yolo";
    static final int PICK_CONTACT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mydb = new DBHelper(MainActivity.this);
        allContactsArrayList = new ArrayList<>();

        Cursor res = mydb.getAllData();
        if (res.getCount() != 0) {
            while (res.moveToNext()) {
                Log.d(TAG, "onClick: " + res.getString(1));
                Log.d(TAG, "onClick: " + res.getString(2));
                Log.d(TAG, "onClick: " + "-----------------------");
                allContactsArrayList.add(new ContactDetails(res.getString(1), res.getString(2), null));
            }
            allContactsRV = (RecyclerView) findViewById(R.id.rvList);
            allContactsAdapter = new AllContactsAdapter(this, allContactsArrayList);
            allContactsRV.setLayoutManager(new LinearLayoutManager(this));
            allContactsRV.setAdapter(allContactsAdapter);

        } else {
            Toast.makeText(MainActivity.this, "No Numbers Added", Toast.LENGTH_LONG).show();
        }

        onDelete = new OnDelete() {
            @Override
            public void onDel() {
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });

    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {

                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {

                        String cNumber = null;
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            cNumber = phones.getString(phones.getColumnIndex("data1"));
                            Log.d(TAG, "onActivityResult: " + cNumber);
                        }
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        Log.d(TAG, "onActivityResult: " + name);
                        boolean x = mydb.insertData(name, cNumber, null);
                        allContactsArrayList.add(new ContactDetails(name, cNumber, null));
                        allContactsAdapter.notifyDataSetChanged();
                        if (!x){
                            Toast.makeText(this, "Contact Not Available", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    public static OnDelete getOnDelete(){
        return onDelete;
    }

}
