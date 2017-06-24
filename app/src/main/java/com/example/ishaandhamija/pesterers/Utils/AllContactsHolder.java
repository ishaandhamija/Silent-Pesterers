package com.example.ishaandhamija.pesterers.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ishaandhamija.pesterers.Activities.MainActivity;
import com.example.ishaandhamija.pesterers.DBUtils.ContactDetails;
import com.example.ishaandhamija.pesterers.DBUtils.DBHelper;
import com.example.ishaandhamija.pesterers.R;

import java.util.ArrayList;

/**
 * Created by ishaandhamija on 24/06/17.
 */

public class AllContactsHolder extends RecyclerView.ViewHolder {

    ImageView allContactsPhoto;
    TextView allContactsName, allContactsNumber;
    View view;
    DBHelper mydb;

    public AllContactsHolder(View itemView, final Context ctx) {
        super(itemView);

        this.allContactsName = (TextView) itemView.findViewById(R.id.allContactsName);
        this.allContactsPhoto = (ImageView) itemView.findViewById(R.id.allContactsPhoto);
        this.allContactsNumber = (TextView) itemView.findViewById(R.id.allContactsNumber);
        this.view = itemView;

        mydb = new DBHelper(ctx);

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);

                alertDialog.setTitle("Remove");
                alertDialog.setMessage("Are you sure you want remove this contact?");
                alertDialog.setIcon(R.mipmap.ic_bin);

                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        mydb.deleteData(allContactsName.getText().toString().trim());
                        Log.d("UU", "onClick: " + MainActivity.getOnDelete());
                        MainActivity.getOnDelete().onDel();
                    }
                });

                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alertDialog.show();
                return true;
            }
        });

    }
}
