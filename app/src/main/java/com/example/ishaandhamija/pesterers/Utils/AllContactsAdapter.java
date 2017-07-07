package com.example.ishaandhamija.pesterers.Utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.ishaandhamija.pesterers.DBUtils.ContactDetails;
import com.example.ishaandhamija.pesterers.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ishaandhamija on 24/06/17.
 */

public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsHolder> {

    Context ctx;
    ArrayList<ContactDetails> allContactsArrayList;

    public AllContactsAdapter(Context context, ArrayList<ContactDetails> list) {
        this.ctx = context;
        this.allContactsArrayList = list;
    }

    @Override
    public AllContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = li.inflate(R.layout.all_contact_sample, parent, false);

        return new AllContactsHolder(itemView, ctx);
    }

    @Override
    public void onBindViewHolder(AllContactsHolder holder, int position) {
        final ContactDetails contactDetails = allContactsArrayList.get(position);
        holder.allContactsName.setText(contactDetails.getName());
        holder.allContactsNumber.setText(contactDetails.getNumber());
        holder.allContactsLetter.setText(Character.toString(contactDetails.getName().charAt(0)));
    }

    @Override
    public int getItemCount() {
        return allContactsArrayList.size();
    }
}
