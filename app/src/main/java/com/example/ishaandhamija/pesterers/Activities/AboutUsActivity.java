package com.example.ishaandhamija.pesterers.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ishaandhamija.pesterers.R;

public class AboutUsActivity extends AppCompatActivity {

    TextView mail;
    ImageView fb, gh, li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        mail = (TextView) findViewById(R.id.mail);
        fb = (ImageView) findViewById(R.id.fb);
        gh = (ImageView) findViewById(R.id.gh);
        li = (ImageView) findViewById(R.id.li);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.facebook.com/ishaan.dhamija.5");
            }
        });

        gh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://github.com/ishaandhamija");
            }
        });

        li.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage("https://www.linkedin.com/in/ishaan-dhamija-108ba385/");
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    void openWebPage(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    void sendEmail(){
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setType("message/rfc822");
        email.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ishaandhamija@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Feedback/Query");
        startActivity(Intent.createChooser(email, "Send mail..."));
    }

}
