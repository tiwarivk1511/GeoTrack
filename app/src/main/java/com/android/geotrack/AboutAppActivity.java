package com.android.geotrack;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        //Email button working activation
        CardView btnEmail = findViewById(R.id.email);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                composeEmail(new String[]{"vikash15112000@gmail.com"});
            }
        });


        //Calling action handling
        CardView btnCall = findViewById(R.id.call);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialPhoneNumber("+918860661641");
            }
        });

        //instagram profile opening
        CardView btnInstagram = findViewById(R.id.instagram);
        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInstagramProfile();
            }
        });


        // Linkedin Profile opening
        CardView btnLinkedIn = findViewById(R.id.linkedin);

        btnLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkedInProfile();
            }
        });


        //GitHub profile opening
        CardView btnGithub = findViewById(R.id.github);

        btnGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGithubProfile();
            }
        });

    }

    //method to handle the working of email button
    @SuppressLint("QueryPermissionsNeeded")
    private void composeEmail(String[] addresses) { Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);

        // Set the intent to show the chooser only for email apps
        Intent chooserIntent = Intent.createChooser(intent, "Select Email App");
        if (chooserIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooserIntent);
        }
    }

    // Method to handle the working of call button

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));

        // Set the intent to show the chooser only for phone call apps
        Intent chooserIntent = Intent.createChooser(intent, "Select Phone Call App");
        if (chooserIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooserIntent);
        }
    }



    // Method to handle the working of the GitHub button
    @SuppressLint("QueryPermissionsNeeded")
    private void openGithubProfile() {
        String githubUrl = "https://github.com/tiwarivk1511?tab=repositories";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));

        // Check if there is an app capable of handling the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    // Method to handle opening the LinkedIn profile in the default web browser
    @SuppressLint("QueryPermissionsNeeded")
    private void openLinkedInProfile() {
        String linkedinUrl = "https://www.linkedin.com/in/your-linkedin-profile";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl));

        // Check if there's an app capable of handling the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //Method to handle opening the instagram profile in default web browser
    @SuppressLint("QueryPermissionsNeeded")
    private void  openInstagramProfile(){
        String instaUrl = "https://www.instagram.com/tiwari_vk1511/";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(instaUrl));

        // Check if there's an app capable of handling the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}