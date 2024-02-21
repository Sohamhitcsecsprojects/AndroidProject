package com.example.secretkey.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.secretkey.R;


public class AboutUsFragment extends Fragment {

    ImageView imgGmail, imgLinkedin, imgInstagram;
    TextView txtGitHub;

    public AboutUsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about_us, container, false);

        imgGmail = view.findViewById(R.id.imgGmail);
        txtGitHub = view.findViewById(R.id.txtGitHub);
        imgLinkedin = view.findViewById(R.id.imgLinkedin);
        imgInstagram = view.findViewById(R.id.imgInstagram);

        imgGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "imsoham.maity@gmail.com"));
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {

                }
            }
        });

        txtGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent = new Intent();
             intent.setAction(Intent.ACTION_VIEW);
             intent.addCategory(Intent.CATEGORY_BROWSABLE);
             intent.setData(Uri.parse("https://github.com/Sohamhitcsecsprojects/AndroidProject.git"));
             startActivity(intent);
            }
        });

        return view;
    }
}