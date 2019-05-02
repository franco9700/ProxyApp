package com.example.proxyapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ContactoFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_contacto, container, false);
        ImageView facebook = v.findViewById(R.id.facebook_img);
        ImageView instagram = v.findViewById(R.id.instagram_img);
        ImageView twitter = v.findViewById(R.id.twitter_img);
        ImageView mail = v.findViewById(R.id.mail_img);

        facebook.setOnClickListener(this);
        instagram.setOnClickListener(this);
        twitter.setOnClickListener(this);
        mail.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.facebook_img:
                Intent fbIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/congresoproxy/"));
                startActivity(fbIntent);

                break;

            case R.id.instagram_img:
                Intent igIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/congresoproxy/"));
                startActivity(igIntent);

                break;

            case R.id.twitter_img:
                Intent twIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Congreso_Proxy"));
                startActivity(twIntent);

                break;

            case R.id.mail_img:
                Intent mailIntent = new Intent(Intent.ACTION_SEND);
                mailIntent.setType("plain/text");
                mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"congresoproxy@gmail.com"});
                mailIntent.putExtra(Intent.EXTRA_SUBJECT, "¡Hola, Congreso Proxy!");
                startActivity(Intent.createChooser(mailIntent, ""));

                break;
        }
    }
}
